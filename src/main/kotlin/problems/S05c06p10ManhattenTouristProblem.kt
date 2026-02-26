@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems

import algorithms.ManhattanTouristProblem


/**
 * rosalind: @link: http://rosalind.info/problems/ba5b/
 *
Code Challenge: Find the length of a longest path in the Manhattan Tourist Problem.

Input: Integers n and m, followed by an n × (m + 1) matrix Down and
an (n + 1) × m matrix Right. The two matrices are separated by the "-" symbol.

Output: The length of a longest path from source (0, 0) to sink (n, m)
in the rectangular grid whose edges are defined by the matrices Down and Right.
 */

fun main() {

    val quizDataset = """
12 6
2 1 2 2 4 0 4
2 2 1 4 0 3 4
3 2 0 1 4 3 3
4 4 3 1 2 1 0
1 1 4 4 3 3 3
3 3 3 1 2 2 2
3 4 3 2 0 2 2
0 2 4 2 4 1 1
2 1 4 3 3 3 0
3 0 2 4 3 0 2
1 0 3 3 4 3 1
3 4 3 3 4 3 1
-
3 1 3 1 3 0
1 1 3 3 0 3
1 0 1 4 2 3
0 0 3 1 4 2
4 4 0 3 0 3
1 4 2 1 1 1
0 0 2 2 1 3
3 0 0 4 3 0
1 2 0 3 2 1
1 4 2 4 1 4
3 2 3 4 2 2
2 3 4 4 0 4
0 4 4 1 0 0
        """.trimIndent()

    val mt = ManhattanTouristProblem()
    val result = mt.readManhattanMatrices(quizDataset)
    val count = mt.findLongestPathInManhatten(result!!)

    println(count)
}


