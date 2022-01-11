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
import java.text.NumberFormat
import java.util.*

/**
 *
 * See also:
 * https://en.wikipedia.org/wiki/Neighbor_joining
 *
 * Neighbour Joining   Anders Gorm Pedersen
 * Youtube: https://www.youtube.com/watch?v=Dj24mCLQYUE
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
    var verbose = false // for debug printout

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
        val theMap: MutableMap<Int, MutableMap<Int, Float>> = mutableMapOf()
        // joined pair list - identifies which matrix entries are pairs
        val joinedPairMap: MutableMap<Int, MutableList<Int>> = mutableMapOf()
        // cluster total distance
        val clusterDistance: MutableMap<Int, Float> = mutableMapOf()

        // NEW for NeighborJoining: Total Distance
        val totalDistance: D1Array<Float> = mk.d1array(matrixSize * 2) { 0f }

        // init pairs - leaf nodes naturally don't have members
        for (i in 0 until matrixSize) {
            joinedPairMap[i] = mutableListOf()
        }

        val originalMatrixDoubled = doubleSized(matrixSize, m)

        // and now the working loop
        while (joinedPairMap.size > 1) {

            val qMatrix = mk.d2array(matrixSize * 2, matrixSize * 2) { 0f }

            updateDistances(
                joinedPairMap = joinedPairMap,
                clusterDistance = clusterDistance,
                theMap = theMap,
                matrixSize = matrixSize,
                distanceMatrix = originalMatrixDoubled,
                qMatrix = qMatrix,
                totalDistance = totalDistance
            )

            val minPair = minCoordinates(matrixSize * 2, qMatrix)
            //println(minPair)

            addClusterAndRemoveOldClusters(
                coord = minPair,
                joinedPairMap = joinedPairMap,
                clusterDistance = clusterDistance,
                theMap = theMap,
                matrixSize = matrixSize,
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
        joinedPairMap: MutableMap<Int, MutableList<Int>>,
        clusterDistance: MutableMap<Int, Float>,
        theMap: MutableMap<Int, MutableMap<Int, Float>>,
        matrixSize: Int,
        distanceMatrix: D2Array<Float>,
        qMatrix: D2Array<Float>,
        totalDistance: D1Array<Float>
    ) {

        // operating matrix size is the number of clusters
        val nSize = joinedPairMap.size

        /*
         * update the distance matrix for the joined pairs
         * Per the pseudocode:
         * Dk,m = Dm,k = (1/2)(Dk,i + Dk,j - Di,j) for any k
         *    of course k can not equal itself
         */
        val joinedPairsToUpdate = joinedPairMap.filter { it.key >= matrixSize }.toList()
        for (joinedPair in joinedPairsToUpdate) {
            val clusterIndex = joinedPair.first
            val leaves = joinedPair.second // a list of Ints - really a pair of ints but expressed as a list
            val n1 = leaves[0]
            val n2 = leaves[1]

            for (i in 0 until clusterIndex) {
                if (i == n1 || i == n2) {  // don't bother calculating distance to paired node
                    continue
                }
                val d1 = distanceMatrix[n1, i]
                val d2 = distanceMatrix[n2, i]
                val d3 = distanceMatrix[n1, n2]
                if (d1 == 0f || d2 == 0f || d3 == 0f) {
                    continue
                }
                val distance = 0.5f * (d1 + d2 - d3)

                distanceMatrix[i, clusterIndex] = distance
                distanceMatrix[clusterIndex, i] = distance
            }
        }

        // now zero out any distances that are allocated to pairs

        for (i in 0..nextNode) {
            if (!joinedPairMap.containsKey(i)) {
                for (z in 0..nextNode) {
                    distanceMatrix[i, z] = 0f
                    distanceMatrix[z, i] = 0f
                }
            }
        }

        // calculate total distances
        for (i in 0 until nextNode) {
            var sum = 0f
            for (j in 0 until nextNode) {
                if (i == j) {
                    continue
                }
                sum += distanceMatrix[i, j]
            }
            totalDistance[i] = sum
        }

        // recalculate for Dstar / Q* (Q matrix per wikipedia):
        // D*(i,j) = (n - 2) · D(i,j) - TotalDistanceD(i) - TotalDistanceD(j).
        // note that D*(i,j) is called Q*(a,b) in wikipedia

        for (i in 0 until nextNode) {

            for (j in 0 until nextNode) {
                if (i == j) {
                    continue
                }
                // skip elements already allocated to a pair
                if (!joinedPairMap.containsKey(i) || !joinedPairMap.containsKey(j)) {
                    continue
                }

                val dijScaled = distanceMatrix[i, j] * (nSize - 2)
                val ui = totalDistance[i]
                val uj = totalDistance[j]
                qMatrix[i, j] = dijScaled - ui - uj
            }
        }

        if (verbose) {
            prettyPrintDistanceAndQMatrices(
                joinedPairMap = joinedPairMap,
                clusterDistance = clusterDistance,
                theMap = theMap,
                matrixSize = matrixSize,
                distanceMatrix = distanceMatrix,
                qMatrix = qMatrix,
                totalDistance = totalDistance
            )
        }

    }

    /**
     * pretty print the distance and q matrices - only for active elements
     */
    fun prettyPrintDistanceAndQMatrices(
        joinedPairMap: MutableMap<Int, MutableList<Int>>,
        clusterDistance: MutableMap<Int, Float>,
        theMap: MutableMap<Int, MutableMap<Int, Float>>,
        matrixSize: Int,
        distanceMatrix: D2Array<Float>,
        qMatrix: D2Array<Float>,
        totalDistance: D1Array<Float>
    ) {

        val str = StringBuilder()
        val fmt = NumberFormat.getNumberInstance(Locale.ROOT)
        fmt.maximumFractionDigits = 1
        fmt.minimumFractionDigits = 1
        fmt.maximumIntegerDigits = 2
        fmt.minimumIntegerDigits = 1


        var printHeader = true
        str.append("Distance:\n")
        for (i in 0 until matrixSize + joinedPairMap.size) {
            if (!joinedPairMap.containsKey(i)) {
                continue
            }
            // header
            if (printHeader) {
                str.append("   ")
                for (j in 0 until matrixSize + joinedPairMap.size) {
                    if (!joinedPairMap.containsKey(j)) {
                        continue
                    }
                    str.append(String.format("%2d", j))
                    str.append("  ")
                }
                str.append("\n")
                printHeader = false
            }

            str.append(String.format("%2d", i))
            str.append(" ")
            for (j in 0 until matrixSize + joinedPairMap.size) {
                if (!joinedPairMap.containsKey(j)) {
                    continue
                }
                val valToPrint = distanceMatrix[i, j]
                val f = fmt.format(valToPrint)
                str.append(f)
                //str.append(String.format("%2d",distanceMatrix[i, j].toInt() ))
                str.append(" ")

            }
            str.append("\n")
        }

        str.append("\n")

        /*****************************
         * now print the q matrix
         */
        str.append("Q matrix:\n")
        printHeader = true
        for (i in 0 until matrixSize + joinedPairMap.size) {
            if (!joinedPairMap.containsKey(i)) {
                continue
            }
            // header
            if (printHeader) {
                str.append("      ")
                for (j in 0 until matrixSize + joinedPairMap.size) {
                    if (!joinedPairMap.containsKey(j)) {
                        continue
                    }
                    str.append(String.format("%3d", j))
                    str.append("   ")
                }
                str.append("\n")
                printHeader = false
            }

            str.append(String.format("%3d", i))
            str.append(" ")
            for (j in 0 until matrixSize + joinedPairMap.size) {
                if (!joinedPairMap.containsKey(j)) {
                    continue
                }
                val valToPrint = qMatrix[i, j]
                if (valToPrint == 0.0f) {
                    str.append("  ")
                }
                val f = fmt.format(valToPrint)
                str.append(f)
                //str.append(String.format("%3d",qMatrix[i, j].toInt() ))
                str.append(" ")
                if (valToPrint > 0f) { // add another space to fill in for "-" values
                    str.append(" ")
                }
            }
            str.append("\n")
        }

        println(str.toString())

    }

    fun addClusterAndRemoveOldClusters(
        coord: Pair<Int, Int>,
        joinedPairMap: MutableMap<Int, MutableList<Int>>,
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

        // operating matrix size is the number of clusters
        val nSize = joinedPairMap.size

        // using the formulas described here:
        // https://youtu.be/Dj24mCLQYUE?t=278

        when {
            // easy case: just a cluster of two leaf nodes
            first < matrixSize && second < matrixSize -> {

                joinedPairMap[nextNode] = mutableListOf(
                    first, second
                )

                val f = totalDistance[first]  // Sum(d(u,k)) per wikipedia
                val s = totalDistance[second]  // Sum(d(c,k)) per wikipedia

                val distFirst = 0.5f * distance + 0.5f * 1f / (nSize - 2) * (f - s)
                val distSecond = distanceMatrix[first, second] - distFirst

                theMap[nextNode] = mutableMapOf(Pair(first, distFirst))
                theMap[nextNode]!![second] = distSecond
                theMap[first] = mutableMapOf(Pair(nextNode, distFirst))
                theMap[second] = mutableMapOf(Pair(nextNode, distSecond))
                joinedPairMap.remove(first)
                joinedPairMap.remove(second)
                nextNode++
                return
            }
            // the first cluster is an internal node and the second is an leaf
            first >= matrixSize && second < matrixSize -> {
                joinedPairMap[nextNode] = mutableListOf(
                    first, second
                )

                val f = totalDistance[first]  // Sum(d(u,k)) per wikipedia
                val s = totalDistance[second]  // Sum(d(c,k)) per wikipedia

                val distFirst = 0.5f * distance + 0.5f * 1f / (nSize - 2) * (f - s)
                val distSecond = distanceMatrix[first, second] - distFirst

                // calculate distance for internal node
                val internalNodeDistance = distance - clusterDistance[first]!!
                theMap[nextNode] = mutableMapOf(Pair(first, distFirst))
                theMap[nextNode]!![second] = distSecond

                // now make the reverse pointers
                theMap[first]!![nextNode] = distFirst
                theMap[second] = mutableMapOf(Pair(nextNode, distSecond))
                joinedPairMap.remove(first)
                joinedPairMap.remove(second)
                nextNode++
                return
            }

            // the first element is a leaf and the second is an internal node
            first < matrixSize && second >= matrixSize -> {

                joinedPairMap[nextNode] = mutableListOf(
                    first, second
                )

                val f = totalDistance[first]  // Sum(d(u,k)) per wikipedia
                val s = totalDistance[second]  // Sum(d(c,k)) per wikipedia

                val distFirst = 0.5f * distance + 0.5f * 1f / (nSize - 2) * (f - s)
                val distSecond = distanceMatrix[first, second] - distFirst

                theMap[nextNode] = mutableMapOf(Pair(second, distSecond))
                // now add the leaf node with basic distance
                theMap[nextNode]!![first] = distFirst

                // now make the reverse pointers
                theMap[second]!![nextNode] = distSecond
                theMap[first] = mutableMapOf(Pair(nextNode, distFirst))
                joinedPairMap.remove(first)
                joinedPairMap.remove(second)
                nextNode++
                return
            }

            // both clusters are internal nodes
            //   just connect them with each other using the distance matrix
            else /* first >= matrixSize && second >= matrixSize */ -> {
                // special case the end condition
                if (joinedPairMap.size == 2) {
                    theMap[first]!![second] = distance
                    theMap[second]!![first] = distance
                    //joinedPairMap.remove(first)
                    joinedPairMap.remove(second)
                    return
                }

                joinedPairMap[nextNode] = mutableListOf(
                    first, second
                )

                val f = totalDistance[first]  // Sum(d(u,k)) per wikipedia
                val s = totalDistance[second]  // Sum(d(c,k)) per wikipedia

                val distFirst = 0.5f * distance + 0.5f * 1f / (nSize - 2) * (f - s)
                val distSecond = distanceMatrix[first, second] - distFirst

                theMap[first]!![nextNode] = distFirst
                theMap[second]!![nextNode] = distSecond

                theMap[nextNode] = mutableMapOf(Pair(first, distFirst))
                theMap[nextNode]!![second] = distSecond

                joinedPairMap.remove(first)
                joinedPairMap.remove(second)
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
