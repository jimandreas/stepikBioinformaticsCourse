@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER"
)

package algorithms

import ResourceReader
import kotlin.math.max

/**
 * Code Challenge: Solve the Local Alignment Problem.

Input: Two protein strings written in the single-letter amino acid alphabet.

Output: The maximum score of a local alignment of the strings,
followed by a local alignment of these strings achieving the maximum score.
Use the PAM250 scoring matrix for matches and mismatches as well as the indel penalty σ = 5.

Note: this could be combined with the Global Alignment code.  Most of the code change
occurs in the outputLCS function.  But in the interests of clarity and reduction of complexity,
the changes to find the maximum score will occur only here.

MODIFIED: to accept:
A match score m, a mismatch penalty μ, a gap penalty σ

These are set up when the class is instantiated.

A modal flag indicates whether to use the PAM250 match/mismatch matrix.

The changes involve using the scoring matrix to calculate the winning value
for each cell.

 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba5e/
 * book (5.10):  http://rosalind.info/problems/ba5f/
 */

class AlignmentLocal(
    val mMatchValue: Int,
    val uMismatchValue: Int,
    val sigmaGapPenalty: Int,
    val usePAM250: Boolean = false
) {

    private var blosum62: Array<IntArray> = Array(20) { IntArray(20) }

    enum class Dir { NOTSET, MATCHMISMATCH, INSERTION_RIGHT, DELETION_DOWN }

    var alignmentScoreResult = 0

    lateinit var align2Dvalues: Array<IntArray>

    init {
        readPAM250()
    }

    /**
     * localAlignment
     *
     *
     * @return Triple of score, alignedRow, alignedCol
     */
    fun localAlignment(sRow: String, tCol: String): Triple<Int, String, String> {

        val resultMatrix = backtrack(sRow, tCol)
        val max = findMax(sRow, tCol)
        val maxVal = max.first
        val maxRow = max.third
        val maxCol = max.second

        val strCol = StringBuilder()
        val strRow = StringBuilder()

        //val strPair = outputLCS(resultMatrix, sRow, tCol, sRow.length, tCol.length, strRow, strCol)
        val strPair = outputLCS(resultMatrix, sRow, tCol, maxRow, maxCol, strRow, strCol)

        return Triple(maxVal, strPair.first, strPair.second)
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
     */

    fun backtrack(wRow: String, vColumn: String): Array<Array<Dir>> {
        val nRows: Int = vColumn.length
        val mCols: Int = wRow.length

        val align2D = Array(nRows + 1) { IntArray(mCols + 1) }
        val backtrack2D = Array(nRows + 1) { Array(mCols + 1) { Dir.NOTSET } }

        for (i in 0..nRows) {
            //align2D[i][0] = i * -sigmaGapPenalty
            align2D[i][0] = 0 // free ride value
            //backtrack2D[i][0] = Dir.DELETION_DOWN
            backtrack2D[i][0] = Dir.NOTSET
        }
        for (j in 0..mCols) {
            //align2D[0][j] = j * -sigmaGapPenalty
            align2D[0][j] = 0 // free ride value
            //backtrack2D[0][j] = Dir.INSERTION_RIGHT
            backtrack2D[0][j] = Dir.NOTSET
        }


        for (iRow in 1..nRows) {
            for (jCol in 1..mCols) {

                // add the forced basing of the score to zero here
                val freeTaxiRideScore = 0
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
        alignmentScoreResult = align2D[nRows][mCols]
        align2Dvalues = align2D
        return backtrack2D
    }

    fun findMax(wRow: String, vColumn: String): Triple<Int, Int, Int> {
        val nRows: Int = vColumn.length
        val mCols: Int = wRow.length

        var maxVal = Int.MIN_VALUE
        var rowMaxCoord = 0
        var colMaxCoord = 0

        for (iRow in 1..nRows) {
            for (jCol in 1..mCols) {
                if (align2Dvalues[iRow][jCol] > maxVal) {
                    maxVal = align2Dvalues[iRow][jCol]
                    rowMaxCoord = iRow
                    colMaxCoord = jCol
                }
            }
        }
        return Triple(maxVal, rowMaxCoord, colMaxCoord)
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
            backtrack2D[i][j] == Dir.NOTSET -> { // indicates the end of the Local Alignment
                return Pair(strRow.toString(), strCol.toString())
            }
            backtrack2D[i][j] == Dir.DELETION_DOWN -> {
                return outputLCS(
                    backtrack2D, wRow, vColumn, j, i - 1,
                    strCol.insert(0, vColumn[i - 1]),
                    strRow.insert(0, '-')
                )
            }
            backtrack2D[i][j] == Dir.INSERTION_RIGHT -> {
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
     * the scoring function is modal.   If the usePAM250 flag is set, then
     * the scoring matrix is used. Otherwise the match score and match penalty set
     * up in the class are used.
     */
    fun score(amino1: Char, amino2: Char): Int {
        val a1 = aminoToOffset(amino1)
        val a2 = aminoToOffset(amino2)
        var result = 0
        if (usePAM250) {
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

    private val aminos =
        listOf('A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y')

    fun aminoToOffset(amino: Char): Int {
        return aminos.indexOf(amino)
    }

    private fun readPAM250() {

        val r = ResourceReader()
        val matrix = r.getResourceAsText("PAM250.txt")

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