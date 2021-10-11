@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set

/**
 *
 * See also:  Neighbour Joining   Anders Gorm Pedersen
 * Youtube: https://www.youtube.com/watch?v=Dj24mCLQYUE
 *
 *
 * Limb Length:
 * stepik: https://stepik.org/lesson/240340/step/7?unit=212686
 * rosalind: http://rosalind.info/problems/ba7e/
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

class NeighborJoining {

    /**

    Code Challenge: Implement NeighborJoining.

    Input: An integer n, followed by an n x n distance matrix.

    Output: An adjacency list for the tree resulting from
    applying the neighbor-joining algorithm.
    Edge-weights should be accurate to two decimal places
    (they are provided to three decimal places in the sample output below).

    Note on formatting: The adjacency list must have consecutive
    integer node labels starting from 0. The n leaves must be labeled
    0, 1, ..., n - 1 in order of their appearance in the distance matrix.
    Labels for internal nodes may be labeled in any order but must start
    from n and increase consecutively.
     */

    // the actual implementation is ITERATIVE, not RECURSIVE.
    // psuedo code (for reference) from : http://rosalind.info/problems/ba7e/
    val psuedoCode = """
    NeighborJoining(D)
    n ← number of rows in D
    if n = 2
        T ← tree consisting of a single edge of length D1,2
        return T
    D* ← neighbor-joining matrix constructed from the distance matrix D
    find elements i and j such that D*i,j is a minimum non-diagonal element of D*
    Δ ← (TotalDistanceD(i) - TotalDistanceD(j)) /(n - 2)
    limbLengthi ← (1/2)(Di,j + Δ)
    limbLengthj ← (1/2)(Di,j - Δ)
    add a new row/column m to D so that Dk,m = Dm,k = (1/2)(Dk,i + Dk,j - Di,j) for any k
    D ← D with rows i and j removed
    D ← D with columns i and j removed
    T ← NeighborJoining(D)
    add two new limbs (connecting node m with leaves i and j) to the tree T
    assign length limbLengthi to Limb(i)
    assign length limbLengthj to Limb(j)
    return T
    """.trimIndent()

    /*
     * proposed data structures:
     *
     * 1) original distrance matrix - nxn where n is the number of leaves
     * 2) a matrix of n + (n-1) - for the leaf distances plus the cluster distances
     *       each cluster is "named" as n, n+1, n+2 etc - corresponds to the
     *       internal node number
     * 3) a "zero" in the distance matrix makes the leaf invisible - as it is now
     *    part of a cluster
     * 4) a list of list of integers lists the clusters
     *    Initially (as per the pseudoCode) the cluster list consists of n lists each of leaves
     *      { 0 .. n-1 }
     * 5) The resulting graph  - which is a map of Int to a map of Int to Float (distance)
     *      Each internal node maps to at most two other nodes - two leaves, two internal nodes, or one of each
     *
     */

    // next internal node number
    var nextNode = 0

    fun neighborJoiningStart(matrixSize: Int, m: D2Array<Float>): Map<Int, Map<Int, Float>> {

        nextNode = matrixSize
        // binary tree with distances
        val theMap : MutableMap<Int, MutableMap<Int, Float>> = mutableMapOf()
        // cluster list - lists members in a cluster
        val clusters: MutableMap<Int, MutableList<Int>> = mutableMapOf()
        // cluster total distance
        val clusterDistance : MutableMap<Int, Float> = mutableMapOf()

        // NEW for NeighborJoining: Total Distance
        val totalDistance: D1Array<Float> = mk.d1array(matrixSize){0f}

        // init clusters - leaf nodes naturally don't have members
        for (i in 0 until matrixSize) {
            clusters[i] = mutableListOf()
        }

        // and now the working loop
        while (clusters.size != 1) {

            val originalMatrixDoubled = doubleSized(matrixSize, m)
            val qMatrix = mk.d2array(matrixSize * 2, matrixSize * 2) { 0f }

            updateDistances(
                clusters =  clusters,
                clusterDistance =  clusterDistance,
                theMap =  theMap,
                matrixSize =  matrixSize,
                distanceMatrix = originalMatrixDoubled,
                qMatrix = qMatrix,
                totalDistance = totalDistance
            )

            val minPair = minCoordinates(matrixSize * 2, qMatrix)
            //println(minPair)

            addClusterAndRemoveOldClusters(
                coord =  minPair,
                clusters =  clusters,
                clusterDistance =  clusterDistance,
                theMap =  theMap,
                matrixSize =  matrixSize,
                distanceMatrix = originalMatrixDoubled,
                qMatrix = qMatrix,
                totalDistance = totalDistance
            )
        }

        return sortMapAndDistanceLists(theMap)

    }

    /*
     * this is updated following guideance shown here:
     * https://www.youtube.com/watch?v=Dj24mCLQYUE
     */
    fun updateDistances(
        clusters: MutableMap<Int, MutableList<Int>>,
        clusterDistance: MutableMap<Int, Float>,
        theMap: MutableMap<Int, MutableMap<Int, Float>>,
        matrixSize: Int,
        distanceMatrix: D2Array<Float>,
        qMatrix: D2Array<Float>,
        totalDistance: D1Array<Float>
    ) {

        // operating matrix size is the number of clusters
        val nSize = clusters.size

        // calculate total distances
        for (i in 0 until matrixSize) {
            var sum = 0f
            for (j in 0 until matrixSize) {
                if (i == j) {
                    continue
                }
                sum += distanceMatrix[i, j]
            }
            totalDistance[i] = sum
        }

        // recalculate for Dstar:
        // D*(i,j) = (n - 2) · D(i,j) - TotalDistanceD(i) - TotalDistanceD(j).

        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                if (i == j) {
                    continue
                }
                val dijScaled = distanceMatrix[i, j]* (nSize-2)
                val ui = totalDistance[i]
                val uj = totalDistance[j]
                qMatrix[i, j] =  dijScaled - ui - uj
            }
        }

        // first add in cluster scores
        val clustersToScore = clusters.filter { it.key >= matrixSize }.toList()
        for (cluster in clustersToScore) {
            val clusterIndex = cluster.first
            val leaves = cluster.second // a list of Ints
            var distance = 0f

            // now walk each row / column and add in the cross values for the cluster
            for (i in 0 until matrixSize) {
//                if (leaves.contains(i)) {      //
//                    continue
//                }
                if (!clusters.containsKey(i)) { // exclude leaves that are part of a cluster
                    continue
                }
                for (l in leaves) {
                    distance += qMatrix[i, l]
                }
                distance /= leaves.size
                qMatrix[i, clusterIndex] = distance
                qMatrix[clusterIndex, i] = distance
                distance = 0f
            }
        }

        // now score clusters against each other
        for (c1 in clustersToScore) {

            val cluster1Index = c1.first
            val leaves1 = c1.second // a list of Ints

            for (c2 in clustersToScore) {
                if (c1 == c2) {
                    continue
                }

                val cluster2Index = c2.first
                val leaves2 = c2.second // a list of Ints
                var distance = 0f

                for (l1 in leaves1) {
                    for (l2 in leaves2) {
                        distance += qMatrix[l1, l2]
                    }
                }
                distance /= leaves1.size * leaves2.size
                qMatrix[cluster1Index, cluster2Index] = distance
                qMatrix[cluster2Index, cluster1Index] = distance
            }
        }


        // second zero any leaves that are no longer a cluster key (i.e. are part of a cluster)
        for (i in 0 until matrixSize) {
            if (!clusters.containsKey(i)) {
                for (z in 0 until matrixSize) {
                    qMatrix[i, z] = 0f
                    qMatrix[z, i] = 0f
                }
            }
        }
    }

    fun addClusterAndRemoveOldClusters(
        coord: Pair<Int, Int>,
        clusters: MutableMap<Int, MutableList<Int>>,
        clusterDistance: MutableMap<Int, Float>,
        theMap: MutableMap<Int, MutableMap<Int, Float>>,
        matrixSize: Int,
        distanceMatrix: D2Array<Float>,
        qMatrix: D2Array<Float>,
        totalDistance: D1Array<Float>
    ) {
        val first = coord.first
        val second = coord.second
        val distance = distanceMatrix[first, second].toFloat()

        // using the formulas described here:
        // https://youtu.be/Dj24mCLQYUE?t=278

        when {
            // easy case: just a cluster of two leaf nodes
            first < matrixSize && second < matrixSize -> {

                clusters[nextNode] = mutableListOf(
                    first, second
                )
                clusterDistance[nextNode] = distance

                val distFirst = 0.5f * distance + 0.5f * (totalDistance[first] - totalDistance[second])
                val distSecond = 0.5f * distance + 0.5f * (totalDistance[second] - totalDistance[first])

                theMap[nextNode] = mutableMapOf(Pair(first, distFirst))
                theMap[nextNode]!![second] =  distSecond
                theMap[first] = mutableMapOf(Pair(nextNode, distFirst))
                theMap[second] = mutableMapOf(Pair(nextNode, distSecond))
                clusters.remove(first)
                clusters.remove(second)
                nextNode++
                return
            }
            // the first cluster is an internal node and the second is an leaf
            first >= matrixSize && second < matrixSize -> {
                val previousLeaves = clusters[first]
                previousLeaves!!.add(second)
                clusters[nextNode] = previousLeaves

                clusterDistance[nextNode] = distance

                // calculate distance for internal node
                val internalNodeDistance = distance - clusterDistance[first]!!
                theMap[nextNode] = mutableMapOf(Pair(first, internalNodeDistance))
                // now add the leaf node with basic distance
                theMap[nextNode]!![second] =  distance

                // now make the reverse pointers
                theMap[first]!![nextNode] = internalNodeDistance
                theMap[second] = mutableMapOf(Pair(nextNode, distance))
                clusters.remove(first)
                clusters.remove(second)
                nextNode++
                return
            }

            // the first cluster is leaf and the second is an internal node
            first < matrixSize && second >= matrixSize -> {

                val previousLeaves = clusters[second]
                previousLeaves!!.add(first)
                clusters[nextNode] = previousLeaves
                clusterDistance[nextNode] = distance

                // calculate distance for internal node
                val internalNodeDistance = distance - clusterDistance[second]!!
                theMap[nextNode] = mutableMapOf(Pair(second, internalNodeDistance))
                // now add the leaf node with basic distance
                theMap[nextNode]!![first] = distance

                // now make the reverse pointers
                theMap[second]!![nextNode] = internalNodeDistance
                theMap[first] = mutableMapOf(Pair(nextNode, distance))
                clusters.remove(first)
                clusters.remove(second)
                nextNode++
                return
            }

            // both clusters are internal nodes
            first >= matrixSize && second >= matrixSize -> {

                // combine both sets of leaves
                val previousLeaves = clusters[first]
                previousLeaves!!.addAll(clusters[second]!!)
                clusters[nextNode] = previousLeaves
                clusterDistance[nextNode] = distance

                val internalNodeDistanceFirst = distance - clusterDistance[first]!!
                theMap[nextNode] = mutableMapOf(Pair(first, internalNodeDistanceFirst))

                val internalNodeDistanceSecond = distance - clusterDistance[second]!!
                theMap[nextNode]!![second] = internalNodeDistanceSecond

                theMap[first]!![nextNode] = internalNodeDistanceFirst
                theMap[second]!![nextNode] = internalNodeDistanceSecond

                clusters.remove(first)
                clusters.remove(second)
                nextNode++
                return
            }
        }
        println("ERROR should not reach this point")
    }

    fun minCoordinates(matrixSize: Int, m: D2Array<Float>): Pair<Int, Int> {
        var minValue = Float.MAX_VALUE
        var iMin = 0
        var jMin = 0
        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                val testVal = m[i, j]
                if (testVal == 0f) {  // zero values are excluded from min
                    continue
                }
                if (m[i, j] < minValue) {
                    minValue = m[i, j]
                    iMin = i
                    jMin = j
                }
            }
        }
        return Pair(iMin, jMin)
    }
    fun doubleSized(matrixSize: Int, m: D2Array<Float>): D2Array<Float> {
        val mdoubled: D2Array<Float> = mk.d2array(matrixSize * 2, matrixSize * 2) { 0f }
        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                mdoubled[i, j] = m[i, j]
            }
        }
        return mdoubled
    }

    fun sortMapAndDistanceLists(unorderedMap: Map<Int, Map<Int, Float>>): Map<Int, Map<Int, Float>> {
        // now sort the keys, and then the distances in the lists
        val mapSortedResult = unorderedMap.toSortedMap()
        val returnSortedList: MutableMap<Int, Map<Int, Float>> = mutableMapOf()
        for (keyNode in mapSortedResult) {
            val theList = keyNode.value.toSortedMap()
            returnSortedList[keyNode.key] = theList
        }
        return returnSortedList
    }

}
