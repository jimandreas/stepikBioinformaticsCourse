@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER"
)

package util

import ResourceReader
import kotlin.math.max

/**

Code Challenge: Solve the Fitting Alignment Problem.

Input: Two nucleotide strings v and w, where v has length at most 1000 and w has length at most 100.
Output: A highest-scoring fitting alignment between v and w.
for the Quiz: Use the simple scoring method in which matches count +1 and both the mismatch and indel penalties are 1.

Otherwise:

A match score m, a mismatch penalty μ, a gap penalty σ

These are set up when the class is instantiated.

Not used: A modal flag indicates whether to use the PAM250 match/mismatch matrix.

The changes involve using the scoring matrix to calculate the winning value
for each cell.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240306/step/5?unit=212652
 * rosalind: @link: http://rosalind.info/problems/ba5h/
 * book (5.11):  http://rosalind.info/problems/ba5f/
 */

class FittingAlignment(
    val mMatchValue: Int,
    val uMismatchValue: Int,
    val sigmaGapPenalty: Int,
    val usePAM250: Boolean = false
) {


    enum class Dir(val debugChar: Char) { NOTSET('N'), MATCHMISMATCH('M'), INSERTION_RIGHT('R'), DELETION_DOWN('D') }

    var alignmentScoreResult = 0

    lateinit var align2Dvalues: Array<IntArray>

    /**
     * fittingAlignment
     *
     *
     * @return Triple of score, alignedRow, alignedCol
     */
    fun fittingAlignment(sRow: String, tCol: String): Triple<Int, String, String> {

//        val la = LocalAlignment(mMatchValue, uMismatchValue, sigmaGapPenalty)

        val resultMatrix = backtrack(sRow, tCol, iFR = 0, jFR = 0)

        val max = findMax(sRow, tCol)
        val maxVal = max.first
        val maxRow = max.third
        val maxCol = max.second

        val strCol = StringBuilder()
        val strRow = StringBuilder()

        //val strPair = outputLCS(resultMatrix, sRow, tCol, sRow.length, tCol.length, strRow, strCol)
        val strPair = outputLCS(resultMatrix, sRow, tCol, maxRow, maxCol, strRow, strCol)

        //debugPrintout(maxVal, sRow, tCol, strPair.first, strPair.second, resultMatrix)

        return Triple(maxVal, strPair.first, strPair.second)
    }

    fun debugPrintout(maxVal: Int, sRow: String, tCol: String, wRowSolution: String, vColumnSolution: String, backtrack2D: Array<Array<LocalAlignment.Dir>>) {

        val nRows: Int = tCol.length
        val mCols: Int = sRow.length

        // print header
        for (jCol in 0..mCols) {
            val colVal = String.format("%2d", jCol)
            when (jCol) {
                0 -> {print("         0${sRow[0]}")}
                mCols -> {println("")}
                else -> print("  $colVal${sRow[jCol]}")
            }
        }


        // print matrix
        for (iRow in 0..nRows) {
            for (jCol in 0..mCols) {

                val indexB = backtrack2D[iRow][jCol].ordinal
                val backtrackChar = "NMRD"[indexB]
                //String.format("%+5.2f", leftX)
                val numVal = String.format("%+3d", align2Dvalues[iRow][jCol])
                    when (jCol) {
                    0 -> {
                        val rowVal = String.format("%2d", iRow)
                        if (iRow > 0) {
                            print("$rowVal${tCol[iRow-1]}: $numVal$backtrackChar")
                        } else {
                            print("$rowVal : $numVal$backtrackChar")
                        }
                    }
                    else -> print(" $numVal$backtrackChar")
                }
            }
            print("\n")
        }
    }

    fun findMax(wRow: String, vColumn: String): Triple<Int, Int, Int> {
        val nRows: Int = vColumn.length
        val mCols: Int = wRow.length

        var maxVal = Int.MIN_VALUE
        var rowMaxCoord = 0
        var colMaxCoord = 0

//        for (iRow in 1..nRows) {
        val iRow = nRows
        for (jCol in nRows..mCols) {
            if (align2Dvalues[iRow][jCol] > maxVal) {
                maxVal = align2Dvalues[iRow][jCol]
                rowMaxCoord = iRow
                colMaxCoord = jCol
            }
        }
//        }
        return Triple(maxVal, rowMaxCoord, colMaxCoord)
    }


    /**
     * backtrack setup
     *   This algorithm lines up two strings in the row and column directions
     *   of a 2D matrix.  It then fills in each entry in the matrix based on the
     *   comparison of the row and column letters.  The Dir enum is used to
     *   indicate the state of each entry
     *
     *   MODIFIED for local alignment:
     *   There are now 4 paths into each cell.  A min of 0 is imposed for each
     *   cell.   This will increase the competition for a DAG start point.
     *
     *   MODIFIED FURTHER for Fitting Alignment:
     *   allow an override of the "taxi" free ride value for independent axis
     */

    fun backtrack(wRow: String, vColumn: String, iFR: Int = 0, jFR: Int = 0): Array<Array<LocalAlignment.Dir>> {
        val nRows: Int = vColumn.length
        val mCols: Int = wRow.length

        val align2D = Array(nRows + 1) { IntArray(mCols + 1) }
        val backtrack2D = Array(nRows + 1) { Array(mCols + 1) { LocalAlignment.Dir.NOTSET } }

        for (i in 0..nRows) {
            //align2D[i][0] = i * -sigmaGapPenalty
            align2D[i][0] = i * -iFR // free ride value
            //backtrack2D[i][0] = Dir.DELETION_DOWN
            backtrack2D[i][0] = LocalAlignment.Dir.NOTSET
        }
        for (j in 0..mCols) {
            //align2D[0][j] = j * -sigmaGapPenalty
            align2D[0][j] = j * -jFR // free ride value
            //backtrack2D[0][j] = Dir.INSERTION_RIGHT
            backtrack2D[0][j] = LocalAlignment.Dir.NOTSET
        }

        var dRow = '0'
        var dCol = '0'
        for (iRow in 1..nRows) {
            for (jCol in 1..mCols) {

                if (iRow == 6 && jCol == 9 ) {
                    dRow = wRow[jCol-1]
                    dCol = vColumn[iRow-1]
                }

                // add the forced basing of the score to zero here
//                val freeTaxiRideScore = 0
                val freeTaxiRideScore = Int.MIN_VALUE
                var diag = score(vColumn[iRow - 1], wRow[jCol - 1])
                var up = -sigmaGapPenalty
                var left = -sigmaGapPenalty

                diag += align2D[iRow - 1][jCol - 1]
                up += align2D[iRow - 1][jCol]
                left += align2D[iRow][jCol - 1]

                val maxCellVal = max(freeTaxiRideScore, max(diag, max(up, left)))
                align2D[iRow][jCol] = maxCellVal

                when {

                    maxCellVal == diag -> {
                        backtrack2D[iRow][jCol] = LocalAlignment.Dir.MATCHMISMATCH
                    }

                    maxCellVal == left -> {
                        backtrack2D[iRow][jCol] = LocalAlignment.Dir.INSERTION_RIGHT
                    }



                    maxCellVal == up -> {
                        backtrack2D[iRow][jCol] = LocalAlignment.Dir.DELETION_DOWN
                    }
                }
            }
        }
        alignmentScoreResult = align2D[nRows][mCols]
        align2Dvalues = align2D
        return backtrack2D
    }

    fun outputLCS(
        backtrack2D: Array<Array<LocalAlignment.Dir>>,
        wRow: String,
        vColumn: String,
        j: Int,
        i: Int,
        strCol: StringBuilder,
        strRow: StringBuilder
    ): Pair<String, String> {

        if (i <= 0 && j <= 0) {
            return Pair(strRow.toString(), strCol.toString())
        }
        when {
            backtrack2D[i][j] == LocalAlignment.Dir.NOTSET -> { // indicates the end of the Local Alignment
//                return Pair(strRow.toString(), strCol.toString())
//                if (i >= 1 && j >= 1) {
//                    strCol.insert(0, vColumn[i - 1])
//                    strRow.insert(0, wRow[j - 1])
//                }
                return Pair(strRow.toString(), strCol.toString())
            }
            backtrack2D[i][j] == LocalAlignment.Dir.DELETION_DOWN -> {
                return outputLCS(
                    backtrack2D, wRow, vColumn, j, i - 1,
                    strCol.insert(0, vColumn[i - 1]),
                    strRow.insert(0, '-')
                )
            }
            backtrack2D[i][j] == LocalAlignment.Dir.INSERTION_RIGHT -> {
                return outputLCS(
                    backtrack2D, wRow, vColumn, j - 1, i,
                    strCol.insert(0, '-'),
                    strRow.insert(0, wRow[j - 1])
                )
            }
            else -> {
                return outputLCS(
                    backtrack2D, wRow, vColumn, j - 1, i - 1,
                    strCol.insert(0, vColumn[i - 1]),
                    strRow.insert(0, wRow[j - 1])
                )
            }
        }
    }

    /**
     * the scoring function is now just a comparison
     */
    fun score(amino1: Char, amino2: Char): Int {
        var result = 0

        if (amino1 == amino2) {
            result = mMatchValue
        } else {
            result = -uMismatchValue
        }

        return result
    }

    private val aminos =
        listOf('A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y')

    fun aminoToOffset(amino: Char): Int {
        return aminos.indexOf(amino)
    }


}