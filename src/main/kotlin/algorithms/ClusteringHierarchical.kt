@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

class ClusteringHierarchical {

    /**
     * See also:
     * Stepik: https://stepik.org/lesson/240366/step/4?unit=212712
     * Rosalind: https://rosalind.info/problems/ba8e/
     *
     * Hierarchical Clustering 9/9
     * https://www.youtube.com/watch?v=Aly7YiDjxZs
     *
     * Uses the Kotlin Multik multidimensional array library
     * @link: https://github.com/Kotlin/multik
     * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
     */

    var farthestFirstTraversalPsuedocode = """
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

    fun hierarchicalClustering(
        numElements: Int,
        distanceMatrix: D2Array<Double>
    ): List<List<Int>> {
        return listOf()
    }


}