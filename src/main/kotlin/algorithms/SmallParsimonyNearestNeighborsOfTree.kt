@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

/**
 *
 * See also:
 * Stepik: https://stepik.org/lesson/240343/step/6?unit=212689
 * Rosalind: no equivalent problem
 *
 * "Evolutionary Tree Reconstruction in the Modern Era"
 * Youtube: https://www.youtube.com/watch?v=A48MBlIyl5c
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

class SmallParsimonyNearestNeighborsOfTree {

    /**

    Code Challenge: Solve the Nearest Neighbors of a Tree Problem.

    Input: Two internal nodes a and b specifying an edge e,
    followed by an adjacency list of an unrooted binary tree.

    Output: Two adjacency lists representing the nearest neighbors
    of the tree with respect to e. Separate the adjacency lists with a blank line.
     */

    fun twoNearestNeighbors(
        a: Int,
        b: Int,
        edges: MutableMap<Int, MutableList<Int>>
    ): List<MutableMap<Int, MutableList<Int>>> {

        var connListA = edges[a]!!.filter { it != b }.toMutableList()
        var connListB = edges[b]!!.filter { it != a }.toMutableList()

        var lastA = connListA.removeLast()
        var firstB = connListB.removeFirst()

        connListA.add(firstB)
        connListB.add(lastA)
        connListA.add(b)
        connListB.add(a)

        var edgesVersionA = edges.toMutableMap()
        edgesVersionA[a] = connListA
        edgesVersionA[b] = connListB

        // now update the conns for lastA and firstB

        edgesVersionA[lastA] = edgesVersionA[lastA]!!.filter { it != a }.toMutableList()
        edgesVersionA[lastA]!!.add(b)
        edgesVersionA[firstB] = edgesVersionA[firstB]!!.filter { it != b }.toMutableList()
        edgesVersionA[firstB]!!.add(a)

        /*
         * now the second group - the 2nd edge in A swaps with the 1st edge in B
         */

        connListA = edges[a]!!.filter { it != b }.toMutableList()
        connListB = edges[b]!!.filter { it != a }.toMutableList()

        lastA = connListA.removeLast()
        val lastB = connListB.removeLast()

        connListA.add(lastB)
        connListB.add(lastA)
        connListA.add(b)
        connListB.add(a)

        var edgesVersionB = edges.toMutableMap()
        edgesVersionB[a] = connListA
        edgesVersionB[b] = connListB

        // now update the conns for lastA and firstB  (swap edges to a and b)

        edgesVersionB[lastA] = edgesVersionB[lastA]!!.filter { it != a }.toMutableList()
        edgesVersionB[lastA]!!.add(b)
        edgesVersionB[lastB] = edgesVersionB[lastB]!!.filter { it != b }.toMutableList()
        edgesVersionB[lastB]!!.add(a)

        return listOf(edgesVersionA, edgesVersionB)
    }

}
