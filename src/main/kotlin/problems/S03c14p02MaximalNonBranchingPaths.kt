@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

package problems

import algorithms.*


/**
Code Challenge: Implement MaximalNonBranchingPaths.

Input: The adjacency list of a graph whose nodes are integers.
Output: The collection of all maximal nonbranching paths in this graph.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240267/step/2?unit=212613
 * rosalind: @link: http://rosalind.info/problems/ba3m/
 */

fun main() {
    // replace with quiz string
    val connectionString = """
            0 -> 1
            1 -> 2
            2 -> 3,4
        """.trimIndent()

    val mnbp = MaximalNonBranchingPaths()
    val result = mnbp.maximalNonBranchingPaths(connectionString)
    for (n in result) {
        println(n.joinToString(" -> "))
    }

}


