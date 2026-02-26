@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls",  "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER",  "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

class ClusteringHierarchical {

    /**
     * See also:
     * Rosalind: https://rosalind.info/problems/ba8e/
     *
     * Hierarchical Clustering 9/9
     * https://www.youtube.com/watch?v=Aly7YiDjxZs
     *
     * Uses the Kotlin Multik multidimensional array library
     * @link: https://github.com/Kotlin/multik
     * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
     */

    var hierarchicalClusteringPseudocode = """
     HierarchicalClustering(D, n)
        Clusters ← n single-element clusters labeled 1, ... , n
        construct a graph T with n isolated nodes labeled by single elements 1, ... , n
        while there is more than one cluster 
            find the two closest clusters Ci and Cj
            merge Ci and Cj into a new cluster Cnew with |Ci| + |Cj| elements
            add a new node labeled by cluster Cnew to T
            connect node Cnew to Ci and Cj by directed edges
            remove the rows and columns of D corresponding to Ci and Cj
            remove Ci and Cj from Clusters
            add a row/column to D for Cnew by computing D(Cnew, C) for each C in Clusters
            add Cnew to Clusters 
        assign root in T as a node with no incoming edges
        return T
    """.trimIndent()

    /**
     * Implement Hierarchical Clustering
     *
     * Given: An integer n, followed by an nxn distance matrix.
     *
     * Return: The result of applying HierarchicalClustering to
     * this distance matrix (using Davg), with each newly created
     * cluster listed on each line.
     */

    var currentClusters: MutableMap<Int, Cluster> = mutableMapOf()
    lateinit var theMatrix: MutableList<MutableList<Double>>

    class Cluster {
        var elementsList: MutableList<Int> = mutableListOf()
        var active = true

        constructor(elementNumber: Int) {
            elementsList = mutableListOf(elementNumber)
        }

        constructor(elementsInit: MutableList<Int>) {
            elementsList = elementsInit
        }


    }

    fun merge(c1: Cluster, c2: Cluster): Cluster {
        val sumOfElements = c1.elementsList.toMutableList()
        sumOfElements.addAll(c2.elementsList)
        c1.active = false
        c2.active = false
        return Cluster(sumOfElements.toMutableList())
    }

    fun Cluster.contains(element: Int): Boolean {
        return this.elementsList.contains(element)
    }

    /**
     * The actual algorithm.  This algorithm deviates from the pseudoCode
     * in that the matrix is never reduced in size.   It instead relies
     * on the Cluster class marking of "active" to filter out
     * elements added to clusters from the "min" search.
     *
     * Also there is no notion of an explicit "tree" formation.   The
     * problem formulation appears to accept a simple statement of
     * which elements are grouped in a stepwise fashion.   If you use
     * your imagination you can assemble the tree by inspecting this
     * resulting list.
     */
    fun hierarchicalClustering(
        numElements: Int,
        matrixIn: MutableList<MutableList<Double>>
    ): List<List<Int>> {

        IntRange(0, numElements - 1).map { currentClusters[it] = Cluster(it) }
        theMatrix = deepCopyArray(matrixIn)

        var nextNewClusterNumber = numElements
        if (nextNewClusterNumber != matrixIn.size) {
            println("hierarchicalClustering: ERROR number of elements should match nxn matrix size")
            return emptyList()
        }

        for (iter in 0 until numElements) {
            val winningPair = findClosestClusterPair(theMatrix, currentClusters)
            val i = winningPair.first
            val j = winningPair.second

            val newCluster = merge(currentClusters[i]!!, currentClusters[j]!!)
            currentClusters[nextNewClusterNumber++] = newCluster

            // create a new row/col for the distance matrix

            val newMatrixRow: MutableList<Double> = mutableListOf()
            for (idx in 0 until theMatrix[theMatrix.size - 1].size) {
                val clSizei = (currentClusters[i]!!.elementsList.size)
                val clSizej = (currentClusters[j]!!.elementsList.size)
                val newDistance = (theMatrix[idx][i] * clSizei + theMatrix[idx][j] * clSizej) / (clSizei + clSizej)
                newMatrixRow.add(newDistance)
            }
            newMatrixRow.add(0.0)  // for self to self distance
            // add in a column at the end
            for (idx in 0 until theMatrix[theMatrix.size - 1].size) {
                theMatrix[idx].add(newMatrixRow[idx])
            }
            theMatrix.add(newMatrixRow)

        }

        // build list of elements as they were assembled into the tree

        val retList: MutableList<MutableList<Int>> = mutableListOf()
        for (iter in numElements until numElements * 2 -1) {
            retList.add(currentClusters[iter]!!.elementsList)
        }

        return retList
    }

    private fun findClosestClusterPair(
        matrix: MutableList<MutableList<Double>>,
        currentClusters: Map<Int, Cluster>
    ): Pair<Int, Int> {
        var min = Double.MAX_VALUE
        var winningPair = Pair(0, 0)
        for (i in 0 until currentClusters.size) {
            for (j in 0 until matrix[i].size) {
                if (i == j) continue
                if (currentClusters[i]!!.active && currentClusters[j]!!.active) {
                    if (min > matrix[i][j]) {
                        min = matrix[i][j]
                        winningPair = Pair(i, j)
                    }
                }
            }
        }
        return winningPair
    }
}