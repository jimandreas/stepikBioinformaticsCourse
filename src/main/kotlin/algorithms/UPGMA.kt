@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants"
)

package algorithms

/**
 *
 * See also:  Ultrametric Trees - Unweighted Pair Group Method with Arithmetic Mean  UPGMA
 * Youtube: https://www.youtube.com/watch?v=27aOeJ2hSwA
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



}
