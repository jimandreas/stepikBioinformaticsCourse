@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "UNUSED_PARAMETER"
)

package util

import ResourceReader
import kotlin.math.max

/**
 * Code Challenge: Solve the Alignment with Affine Gap Penalties Problem.

Input: Two amino acid strings v and w (each of length at most 100).

Output: The maximum alignment score between v and w, followed by an
alignment of v and w achieving this maximum score.
Use the BLOSUM62 scoring matrix, a gap opening penalty of 11, and a gap extension penalty of 1.

Note: this is a modified version of the AlignmentGlobal code.

A modal flag indicates whether to use the BLOSUM62 match/mismatch matrix.

The changes involve using the scoring matrix to calculate the winning value
for each cell.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240307/step/8?unit=212653
 * rosalind: @link: http://rosalind.info/problems/ba5j/
 * book (5.12): @link: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 * youtube: @link: https://www.youtube.com/watch?v=Npv180dQ_4Y
 */

class AlignmentAffineGap(
    val mMatchValue: Int,
    val uMismatchValue: Int,
    val sigmaGapPenalty: Int,
    val epsilonGapExtensionPenalty: Int,
    val useBLOSUM62: Boolean = false
) {

    // three affine gap based scoring matrices

    lateinit var lower: Array<IntArray>
    lateinit var middle: Array<IntArray>
    lateinit var upper: Array<IntArray>

    var alignmentScoreResult = 0

    init {
        readBLOSUM62()
    }

    enum class Dir { NOTSET, MATCHMISMATCH, INSERTION_RIGHT, DELETION_DOWN }

    /**
     * @return Triple of score, alignedRow, alignedCol
     */
    fun affineAlignment(sRow: String, tCol: String): Triple<Int, String, String> {

        val resultMatrix = backtrack(sRow, tCol)

        val strCol = StringBuilder()
        val strRow = StringBuilder()

        val strPair = outputLCS(resultMatrix, sRow, tCol, sRow.length, tCol.length, strRow, strCol)

        //debugPrintout(maxVal, sRow, tCol, strPair.first, strPair.second, resultMatrix)

        return Triple(alignmentScoreResult, strPair.first, strPair.second)
    }

    /**
     * backtrack setup
     *   This algorithm lines up two strings in the row and column directions
     *   of a 2D matrix.
     *
     *   Scoring is now based on the interaction of the three scoring matrices:
     *
     *   upper - for deletions in wRow
     *   middle - for matches and mismatches between wRow and vColumn
     *   lower - for insertions
     *
     *   The Dir enum is used to indicate the state of each entry in the backtrack2D matrix
     */

    fun backtrack(wRow: String, vColumn: String): Array<Array<Dir>> {
        val nRows: Int = vColumn.length
        val mCols: Int = wRow.length

        upper = Array(nRows + 1) { IntArray(mCols + 1) }
        middle = Array(nRows + 1) { IntArray(mCols + 1) }
        lower = Array(nRows + 1) { IntArray(mCols + 1) }

        val backtrack2D = Array(nRows + 1) { Array(mCols + 1) { Dir.NOTSET } }

        /*
         * Initialize: top row:
         * upper: 0, sigma then epsilon
         * middle: same as top
         * bottom: -infinity
         */
        upper[0][0] = 0
        middle[0][0] = 0
        lower[0][0] = Int.MIN_VALUE
        upper[0][1] = -sigmaGapPenalty
        middle[0][1] = -sigmaGapPenalty
        lower[0][1] = Int.MIN_VALUE

        for (j in 2..mCols) {  // no loop if mCols = 1
            upper[0][j] = upper[0][j-1] + (j-1) * -epsilonGapExtensionPenalty
            middle[0][j] = middle[0][j-1] + (j-1) * -epsilonGapExtensionPenalty
            lower[0][j] = Int.MIN_VALUE
            backtrack2D[0][j] = Dir.INSERTION_RIGHT
        }

        /*
         * Initialize: left column:
         * upper: -infinity
         * middle: same as bottom
         * bottom: 0, sigma then epsilon
         */

        for (i in 2..nRows) {
            upper[i][0] = Int.MIN_VALUE
            middle[i][0] = middle[i-1][0] + (i-1) * -epsilonGapExtensionPenalty
            lower[i][0] = lower[i-1][0] + (i-1) * -epsilonGapExtensionPenalty
            backtrack2D[i][0] = Dir.DELETION_DOWN
        }

        /*
         * now follow the affine scoring for the interior portion of the matrix
         */
        for (iRow in 1..nRows) {
            for (jCol in 1..mCols) {

                var diag = score(vColumn[iRow - 1], wRow[jCol - 1])
                var up = -sigmaGapPenalty
                var left = -sigmaGapPenalty

                diag += middle[iRow - 1][jCol - 1]
                up += middle[iRow - 1][jCol]
                left += middle[iRow][jCol - 1]

                val maxCellVal = max(diag, max(up, left))
                middle[iRow][jCol] = maxCellVal

                when {
                    maxCellVal == diag -> {
                        backtrack2D[iRow][jCol] = Dir.MATCHMISMATCH
                    }
                    maxCellVal == left -> {
                        backtrack2D[iRow][jCol] = Dir.INSERTION_RIGHT
                    }
                    maxCellVal == up -> {
                        backtrack2D[iRow][jCol] = Dir.DELETION_DOWN
                    }
                }
            }
        }
        alignmentScoreResult = middle[nRows][mCols]

        return backtrack2D
    }

    fun outputLCS(
        backtrack2D: Array<Array<Dir>>,
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
            backtrack2D[i][j] == Dir.DELETION_DOWN -> {
                return outputLCS(backtrack2D, wRow, vColumn, j,i - 1,
                    strCol.insert(0, vColumn[i - 1]),
                    strRow.insert(0, '-')
                )
            }
            backtrack2D[i][j] == Dir.INSERTION_RIGHT -> {
                return outputLCS(backtrack2D, wRow, vColumn, j - 1, i,
                    strCol.insert(0, '-'),
                    strRow.insert(0, wRow[j - 1])
                )
            }
            else -> {
                return outputLCS(
                    backtrack2D, wRow, vColumn, j - 1,i - 1,
                    strCol.insert(0, vColumn[i - 1]),
                    strRow.insert(0, wRow[j - 1])
                )
            }
        }
    }

    /**
     * A nicely formatted printout of the score matrix with v (row/horizontal/s) string
     * across and the w (column/t) string down at the axes.
     */
    fun debugPrintout(maxVal: Int, sRow: String, tCol: String, wRowSolution: String, vColumnSolution: String, backtrack2D: Array<Array<AlignmentLocal.Dir>>) {

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
                val numVal = String.format("%+3d", middle[iRow][jCol])
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

    private val aminos =
        listOf('A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y')

    private var blosum62: Array<IntArray> = Array(20) { IntArray(20) }

    fun aminoToOffset(amino: Char): Int {
        return aminos.indexOf(amino)
    }

    /**
     * the scoring function is modal.   If the useBLOSUM62 flag is set, then
     * the scoring matrix is used. Otherwise the match score and match penalty set
     * up in the class are used.
     */
    fun score(amino1: Char, amino2: Char): Int {
        val a1 = aminoToOffset(amino1)
        val a2 = aminoToOffset(amino2)
        var result = 0
        if (useBLOSUM62) {
            result = blosum62[a1][a2]
        } else {
            if (a1 == a2) {
                result = mMatchValue
            } else {
                result = -uMismatchValue
            }
        }
        return result
    }


    private fun readBLOSUM62() {

        val r = ResourceReader()
        val matrix = r.getResourceAsText("BLOSUM62.txt")

        val reader = matrix.reader()
        val lines = reader.readLines()

        for (i in 1 until lines.size) {
            val values = lines[i].split("  ", " ")
            //println(values)
            if (values.size != 21) {
                println("BAD ${values.size}")
                break
            }
            for (j in 1 until 21) {
                blosum62[i - 1][j - 1] = values[j].toInt()
            }
        }

    }
}