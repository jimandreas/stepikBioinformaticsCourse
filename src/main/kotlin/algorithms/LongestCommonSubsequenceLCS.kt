@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody", "unused",
    "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment"
)

package algorithms

import kotlin.math.max

/**
Code Challenge: Solve the Longest Common Subsequence Problem.

Input: Two strings s and t.
Output: A longest common subsequence of s and t. (Note: more than one solution may exist, in which case you may output any one.)

 *
 * See also:
 * stepik: @link: https://stepik.org/lesson/240303/step/5?unit=212649
 * rosalind: @link: http://rosalind.info/problems/ba5c/
 * book (5.8):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

class LongestCommonSubsequenceLCS {
    enum class Dir { NOTSET, MATCHMISMATCH, INSERTION_RIGHT, DELETION_DOWN }

    /**
     * backtrack setup
     *   This algorithm lines up two strings in the row and column directions
     *   of a 2D matrix.  It then fills in each entry in the matrix based on the
     *   comparison of the row and column letters.  The Dir enum is used to
     *   indicate the state of each entry
     */

    fun backtrack(vColumn: String, wRow: String): Array<Array<Dir>> {
        val nRows: Int = vColumn.length
        val mCols: Int = wRow.length

        // note that the array is filled with zeros
        val twoD = Array(nRows+1) { IntArray(mCols+1) }
        val backtrack2D = Array(nRows+1) { Array(mCols+1) { Dir.NOTSET } }

        for (iRow in 1..nRows) {
            for (jCol in 1..mCols) {
                var match = 0

                if (vColumn[iRow - 1] == wRow[jCol - 1]) {
                    match = 1
                }
                val cellVal = max(twoD[iRow - 1][jCol], max(twoD[iRow][jCol - 1], twoD[iRow - 1][jCol - 1] + match))
                twoD[iRow][jCol] = cellVal

                when {
                    twoD[iRow][jCol] == twoD[iRow - 1][jCol] -> {
                        backtrack2D[iRow][jCol] = Dir.DELETION_DOWN
                    }
                    twoD[iRow][jCol] == twoD[iRow][jCol - 1] -> {
                        backtrack2D[iRow][jCol] = Dir.INSERTION_RIGHT
                    }
                    twoD[iRow][jCol] == twoD[iRow - 1][jCol - 1] + match -> {
                        backtrack2D[iRow][jCol] = Dir.MATCHMISMATCH
                    }
                }
            }
        }

        return backtrack2D
    }

    fun outputLCS(backtrack2D: Array<Array<Dir>>, vColumn: String, i: Int, j: Int, str: StringBuilder): StringBuilder {
        if (i == 0 || j == 0) {
            return str
        }
        when {
            backtrack2D[i][j] == Dir.DELETION_DOWN -> {
                return outputLCS(backtrack2D, vColumn, i - 1, j, str)
            }
            backtrack2D[i][j] == Dir.INSERTION_RIGHT -> {
                return outputLCS(backtrack2D, vColumn, i, j - 1, str)
            }
            else -> {
                return outputLCS(backtrack2D, vColumn, i - 1, j - 1, str.insert(0, vColumn[i-1]))
            }
        }
    }

}