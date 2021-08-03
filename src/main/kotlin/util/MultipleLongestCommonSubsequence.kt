@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "UNUSED_PARAMETER", "UNUSED_VARIABLE",
    "UNUSED_CHANGED_VALUE", "CanBeParameter"
)

package util

import org.jetbrains.kotlinx.multik.api.d3array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set

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
class MultipleLongestCommonSubsequence(val iS: String, val jS: String, val kS: String) {

    val scoreArray = mk.d3array(iS.length + 1, jS.length + 1, kS.length + 1) { 0 }
    val backtrackArray = mk.d3array(iS.length + 1, jS.length + 1, kS.length + 1) { 0 }

    fun score(): Int {

        if (iS.isEmpty() || jS.isEmpty() || kS.isEmpty()) {
            return 0
        }
        for (i in 1..iS.length) {
            for (j in 1..jS.length) {
                for (k in 1..kS.length) {
                    scoreCalc(i, j, k)
                }
            }
        }
        return  scoreArray[iS.length, jS.length, kS.length]
    }

    fun scoreCalc(i: Int, j: Int, k: Int) {
        val curScore = if (equivalence(i, j, k)) {
            1
        } else {
            0
        }
        val axes = listOf(
            scoreArray[i - 1, j, k],
            scoreArray[i, j - 1, k],
            scoreArray[i, j, k - 1],
            scoreArray[i - 1, j - 1, k],
            scoreArray[i - 1, j, k - 1],
            scoreArray[i, j - 1, k - 1],
            scoreArray[i - 1, j - 1, k - 1] + curScore
        )
        val maxVal = axes.maxOf { it }
        scoreArray[i,j,k] = maxVal
        backtrackArray[i, j, k] = axes.indexOf(maxVal)
    }

    fun equivalence(i: Int, j: Int, k: Int): Boolean {
        if (iS[i-1] == jS[j-1] && jS[j-1] == kS[k-1]) {
            return true
        }
        return false
    }

    fun outputStrings(): Triple<String, String, String> {
        val retString = Triple("", "", "")
        return retString
    }
}