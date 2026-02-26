@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "UNUSED_PARAMETER", "UNUSED_VARIABLE",
     "CanBeParameter"
)

package algorithms

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

    /*
     * set up the backtrack array with default
     * outputs
     */
    init {
        for (j in 1..jS.length) {
            backtrackArray[0, j, 0] = 1

            for (i in 1..iS.length) {
                backtrackArray[i, j, 0] = 3
            }
            for (k in 1..kS.length) {
                backtrackArray[0, j, k] = 5
            }
        }
        for (k in 1..kS.length) {
            backtrackArray[0, 0, k] = 2
            for (i in 1..iS.length) {
                backtrackArray[i, 0, k] = 4
            }
        }
    }

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
        val iSB = StringBuilder()
        val jSB = StringBuilder()
        val kSB = StringBuilder()
        var i = iS.length
        var j = jS.length
        var k = kS.length
        do {
            when (backtrackArray[i, j, k]) {
                0 -> {
                    iSB.append(iS[--i])
                    jSB.append('-')
                    kSB.append('-')
                }
                1 -> {
                    iSB.append('-')
                    jSB.append(jS[--j])
                    kSB.append('-')
                }
                2 -> {
                    iSB.append('-')
                    jSB.append('-')
                    kSB.append(kS[--k])
                }
                3 -> {
                    iSB.append(iS[--i])
                    jSB.append(jS[--j])
                    kSB.append('-')
                }
                4 -> {
                    iSB.append(iS[--i])
                    jSB.append('-')
                    kSB.append(kS[--k])
                }
                5 -> {
                    iSB.append('-')
                    jSB.append(jS[--j])
                    kSB.append(kS[--k])
                }
                6 -> {
                    iSB.append(iS[--i])
                    jSB.append(jS[--j])
                    kSB.append(kS[--k])
                }
            }
        } while (!(i == 0 && j == 0 && k == 0))

        return Triple(
            iSB.toString().reversed(),
            jSB.toString().reversed(),
            kSB.toString().reversed()
            )
    }
}