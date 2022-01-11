@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set

/**
 *
 * See also:  Ultrametric Trees - Unweighted Pair Group Method with Arithmetic Mean  UPGMA
 * Youtube: https://www.youtube.com/watch?v=27aOeJ2hSwA
 *
 * and:
 * C100 Week 5 Discussion (for example matrix solution)
 * Youtube: https://www.youtube.com/watch?v=8-8eZdeqUsw
 *
 * Limb Length:
 * stepik: https://stepik.org/lesson/240339/step/8?unit=212685
 * rosalind: http://rosalind.info/problems/ba7d/
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

class UPGMA /* Unweighted Pair Group Method with Arithmetic Mean */{

    /**

    Code Challenge: Implement UPGMA.

    Input: An integer n followed by a space separated n x n distance matrix.

    Output: An adjacency list for the ultrametric tree returned by UPGMA.
    Edge weights should be accurate to two decimal places (answers in the
    sample dataset below are provided to three decimal places).
     */


    val psuedoCode = """
    UPGMA(D, n) /* Unweighted Pair Group Method with Arithmetic Mean */
    Clusters ← n single-element clusters labeled 1, ... , n
    construct a graph T with n isolated nodes labeled by single elements 1, ... , n
    for every node v in T 
         Age(v) ← 0
    while there is more than one cluster
        find the two closest clusters Ci and Cj
        merge Ci and Cj into a new cluster Cnew with |Ci| + |Cj| elements
        add a new node labeled by cluster Cnew to T
        connect node Cnew to Ci and Cj by directed edges 
        Age(Cnew) ← DCi, Cj / 2
        remove the rows and columns of D corresponding to Ci and Cj
        remove Ci and Cj from Clusters
        add a row/column to D for Cnew by computing D(Cnew, C) for each C in Clusters
        add Cnew to Clusters
    root ← the node in T corresponding to the remaining cluster
    for each edge (v, w) in T
        length of (v, w) ← Age(v) - Age(w)
    return T
    """.trimIndent()

    /**
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

    fun upgmaStart(matrixSize: Int, m: D2Array<Float>): Map<Int, Map<Int, Float>> {

        nextNode = matrixSize
        // binary tree with distances
        val theMap : MutableMap<Int, MutableMap<Int, Float>> = mutableMapOf()
        // cluster list - lists members in a cluster
        val clusters: MutableMap<Int, MutableList<Int>> = mutableMapOf()
        // cluster total distance
        val clusterDistance : MutableMap<Int, Float> = mutableMapOf()

        // init clusters - leaf nodes naturally don't have members
        for (i in 0 until matrixSize) {
            clusters[i] = mutableListOf()
        }

        // and now the working loop
        while (clusters.size != 1) {

            val mDoubled = doubleSized(matrixSize, m)
            updateDistances(clusters, clusterDistance, theMap, matrixSize, mDoubled)

            val minPair = minCoordinates(matrixSize * 2, mDoubled)
            //println(minPair)
            addClusterAndRemoveOldClusters(minPair, clusters, clusterDistance, theMap, matrixSize, mDoubled)
        }

        return sortMapAndDistanceLists(theMap)

    }

    fun updateDistances(
        clusters: MutableMap<Int, MutableList<Int>>,
        clusterDistance: MutableMap<Int, Float>,
        theMap: MutableMap<Int, MutableMap<Int, Float>>,
        matrixSize: Int,
        doubledMatrix: D2Array<Float>
    ) {

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
                    distance += doubledMatrix[i, l]
                }
                distance /= leaves.size
                doubledMatrix[i, clusterIndex] = distance
                doubledMatrix[clusterIndex, i] = distance
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
                        distance += doubledMatrix[l1, l2]
                    }
                }
                distance /= leaves1.size * leaves2.size
                doubledMatrix[cluster1Index, cluster2Index] = distance
                doubledMatrix[cluster2Index, cluster1Index] = distance
            }
        }


        // second zero any leaves that are no longer a cluster key (i.e. are part of a cluster)
        for (i in 0 until matrixSize) {
            if (!clusters.containsKey(i)) {
                for (z in 0 until matrixSize) {
                    doubledMatrix[i, z] = 0f
                    doubledMatrix[z, i] = 0f
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
        doubledMatrix: D2Array<Float>
    ) {
        val first = coord.first
        val second = coord.second
        val distance = doubledMatrix[first, second].toFloat() / 2.0f

        when {
            // easy case: just a cluster of two leaf nodes
            first < matrixSize && second < matrixSize -> {

                clusters[nextNode] = mutableListOf(
                    first, second
                )
                clusterDistance[nextNode] = distance

                theMap[nextNode] = mutableMapOf(Pair(first, distance))
                theMap[nextNode]!![second] =  distance
                theMap[first] = mutableMapOf(Pair(nextNode, distance))
                theMap[second] = mutableMapOf(Pair(nextNode, distance))
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
            else /* first >= matrixSize && second >= matrixSize */ -> {

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
