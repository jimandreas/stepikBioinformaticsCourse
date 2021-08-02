@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "UNUSED_PARAMETER", "UNUSED_VARIABLE",
    "UNUSED_CHANGED_VALUE", "CanBeParameter"
)

package util

import org.jetbrains.kotlinx.multik.api.d3array
import org.jetbrains.kotlinx.multik.api.mk

/**
 * In the Multiple Longest Common Subsequence Problem,
 * the score of a column of the alignment matrix is equal to 1
 * if all the column's symbols are identical, and 0 if even one symbol disagrees.

Code Challenge: Solve the Multiple Longest Common Subsequence Problem.

Input: Three DNA strings of length at most 10.
Output: The length of a longest common subsequence of these three strings, followed by a multiple alignment of the three strings corresponding to such an alignment.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240309/step/5?unit=212655
 * rosalind: @link: http://rosalind.info/problems/ba5m/
 * book (5 Epilogue): @link: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 * youtube: @link: https://www.youtube.com/watch?v=CTPiYiTQcuA&list=PLQ-85lQlPqFNmbPEsMoxb5dM5qtRaVShn&index=10  Multiple Sequence Alignment
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

/*
 * The implementation is an extension of the AlignmentGlobal algorithm
 * with a scoring pass into a scoring matrix and then a backtrack pass
 * to generate the strings.
 *  This is extended into 3 dimensions for the 3 strings in parallel.
 */
class MultipleLongestCommonSubsequence(r: String, s: String, t: String) {

    val scoreArray = mk.d3array(r.length+1, s.length+1, t.length+1) { 0 }

    fun score(): Int {

        return 0
    }

    fun outputStrings(): Triple<String, String, String> {
        val retString = Triple("", "", "")
        return retString
    }
}