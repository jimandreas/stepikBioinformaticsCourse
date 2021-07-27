@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "UNUSED_PARAMETER", "UNUSED_VARIABLE"
)

package util

import ResourceReader
import kotlin.math.max

/**
 * Code Challenge: Implement LinearSpaceAlignment to solve the Global Alignment Problem for a large dataset.

Input: Two long (10000 amino acid) protein strings written in the single-letter amino acid alphabet.

Output: The maximum alignment score of these strings, followed by an alignment achieving this
maximum score. Use the BLOSUM62 scoring matrix and indel penalty σ = 5.

 * See also:
 * stepik: @link:
 * https://stepik.org/lesson/240308/step/12?unit=212654  MiddleEdge problem
 * https://stepik.org/lesson/240308/step/14?unit=212654  LinearSpaceAlignment
 * rosalind: @link:
 * http://rosalind.info/problems/ba5k/  MiddleEdge
 * http://rosalind.info/problems/ba5l/  LinearSpaceAlignment
 * book (5.13): @link: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 * youtube: @link: https://www.youtube.com/watch?v=3TfDm8GpWRU  Space-Efficient Sequence Alignment
 */

class AlignmentLinearSpaceMiddleEdge(
    val mMatchValue: Int,
    val uMismatchValue: Int,
    val sigmaGapPenalty: Int,
    val useBLOSUM62: Boolean = false
) {

    lateinit var blosum62: Array<IntArray>

    init {
        readBLOSUM62()
    }

    fun findMiddleEdge(sRow: String, tCol: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {

        var halfWayRow = 1
        var halfWayCol = 1
        if (sRow.length == 1) {
            halfWayCol = tCol.length / 2 + 1
        } else if (tCol.length == 1) {
            halfWayRow = sRow.length / 2 + 1
        } else {
            halfWayRow = kotlin.math.min(sRow.length / 2 + 0 + sRow.length % 2, sRow.length - 1)
            halfWayCol = kotlin.math.min(tCol.length / 2 + 1 + tCol.length % 2, tCol.length - 1)
        }
        return middleEdge(sRow, tCol, halfWayRow, halfWayCol)
    }

    fun middleEdge(
        sRow: String, tCol: String,
        numColsOfRowString: Int,
        numRowsOfColString: Int
    ): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val scoreArray: Array<IntArray> = Array(numRowsOfColString + 1) { IntArray(numColsOfRowString + 1) }

        for (i in 0..numRowsOfColString) {
            scoreArray[i][0] = i * -sigmaGapPenalty
        }

        for (j in 0..numColsOfRowString) {
            scoreArray[0][j] = j * -sigmaGapPenalty
        }

        var maxScore = -1000000
        var fromPair = Pair(0, 0)
        var toPair = Pair(0, 0)

        for (iRow in 0..numRowsOfColString) {
            for (jCol in 0..numColsOfRowString) {

                // clip the side searches if the col string is of length 1
                if (iRow > 0 && jCol == 0 && numColsOfRowString == 1 && numRowsOfColString > 1) {
                    if (scoreArray[iRow][jCol] > maxScore) {
                        maxScore = scoreArray[iRow][jCol]
                        fromPair = Pair(iRow - 1, jCol)
                        toPair = Pair(iRow, jCol)
                    }
                } else if (iRow == 0 && jCol > 0 && numColsOfRowString > 1 && numRowsOfColString == 1) {
                    if (scoreArray[iRow][jCol] > maxScore && jCol == numColsOfRowString) {
                        maxScore = scoreArray[iRow][jCol]
                        fromPair = Pair(iRow, jCol - 1)
                        toPair = Pair(iRow, jCol)
                    }
                } else if (iRow > 0 && jCol > 0) {

                    var diag = score(tCol[iRow - 1], sRow[jCol - 1])
                    var up = -sigmaGapPenalty
                    var left = -sigmaGapPenalty

                    diag += scoreArray[iRow - 1][jCol - 1]
                    up += scoreArray[iRow - 1][jCol]
                    left += scoreArray[iRow][jCol - 1]

                    val maxCellVal = max(diag, max(up, left))
                    scoreArray[iRow][jCol] = maxCellVal

                    if (maxCellVal > maxScore && jCol == numColsOfRowString) {
                        maxScore = maxCellVal
                        toPair = Pair(iRow, jCol)
                        when {
                            maxCellVal == diag -> {
                                fromPair = Pair(iRow - 1, jCol - 1)
                            }
                            maxCellVal == left -> {
                                fromPair = Pair(iRow, jCol - 1)
                            }
                            maxCellVal == up -> {
                                fromPair = Pair(iRow - 1, jCol)
                            }
                        }
                    }
                }

            }
        }
        return Pair(fromPair, toPair)
    }


    private val aminos =
        listOf('A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y')


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

        blosum62 = Array(20) { IntArray(20) }
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