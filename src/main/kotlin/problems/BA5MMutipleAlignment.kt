@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems

import algorithms.MultipleLongestCommonSubsequence

/**
 * In the Multiple Longest Common Subsequence Problem,
 * the score of a column of the alignment matrix is equal to 1
 * if all the column's symbols are identical, and 0 if even one symbol disagrees.

Code Challenge: Solve the Multiple Longest Common Subsequence Problem.

Input: Three DNA strings of length at most 10.
Output: The length of a longest common subsequence of these three strings, followed by a multiple alignment of the three strings corresponding to such an alignment.

 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba5m/
 * book (5 Epilogue): @link: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 * youtube: @link: https://www.youtube.com/watch?v=CTPiYiTQcuA&list=PLQ-85lQlPqFNmbPEsMoxb5dM5qtRaVShn&index=10  Multiple Sequence Alignment
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

fun main() {
    val sample = """
GTCGGCGT
ATTCGAGGAA
TACGCATAAC
          """.trimIndent()

    val reader = sample.reader()
    val lines = reader.readLines()

    val i = lines[0]
    val j = lines[1]
    val k = lines[2]

    val multiple = MultipleLongestCommonSubsequence(i, j, k)

    val scoreResult = multiple.score()
    val stringResult = multiple.outputStrings()

    println(scoreResult)
    println(stringResult.first)
    println(stringResult.second)
    println(stringResult.third)

    // correct!

}


