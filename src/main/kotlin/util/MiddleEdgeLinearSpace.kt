@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "UNUSED_PARAMETER", "UNUSED_VARIABLE"
)

package util

import ResourceReader
import kotlin.math.max

/**
 * Code Challenge: Solve the Middle Edge in Linear Space Problem (for protein strings).

Input: Two amino acid strings.
Output: A middle edge in the alignment graph in the form "(i, j) (k, l)",
where (i, j) connects to (k, l).

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

class MiddleEdgeLinearSpace(
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
            halfWayRow = kotlin.math.min(sRow.length / 2 /* + 0 + sRow.length % 2 */, sRow.length - 1)
            halfWayCol = kotlin.math.min(tCol.length / 2 /* + 1 + tCol.length % 2 */, tCol.length - 1)
                
        }
        return calculateMiddleEdge(sRow, tCol, halfWayRow, halfWayCol)

    }

    fun calculateMiddleEdge(
        sRow: String, tCol: String,
        numColsOfRowString: Int,
        numRowsOfColString: Int
    ): Pair<Pair<Int, Int>, Pair<Int, Int>> {

        val middleNode = findMiddleNode(sRow, tCol, numColsOfRowString, numRowsOfColString)

        return Pair(Pair(0,0), Pair(0,0))
    }

    fun findMiddleNode(
        sRow: String, tCol: String,
        numColsOfRowString: Int,
        numRowsOfColString: Int
    ): Pair<Int, Int> {
        val scoreRow: Array<Int> = Array(numColsOfRowString + 1) { 0 }

        for (j in 0..numColsOfRowString) {
            scoreRow[j] = j * -sigmaGapPenalty
        }

        // iterate through the rows scoring as we go. (Linear Space concept)
        var previousRow = scoreRow
        for (iRow in 1..numRowsOfColString) {
            val newScoreRow = scoresForThisRow(sRow, tCol,iRow, numColsOfRowString, previousRow)
            previousRow = newScoreRow
        }

        val maxColVal = previousRow.indices.maxByOrNull { previousRow[it] } ?: -1 // https://stackoverflow.com/a/20774842
        return Pair(numRowsOfColString, maxColVal)
    }


    fun scoresForThisRow(
        sRow: String, tCol: String,
        iRow: Int,
        numRowsOfColString: Int,
        previousRow: Array<Int>) : Array<Int> {

        val scoreRow: Array<Int> = Array(numRowsOfColString + 1) { 0 }
        scoreRow[0] = -sigmaGapPenalty * iRow

        for (j in 1..numRowsOfColString) {
            var diag = score(tCol[iRow - 1], sRow[j - 1])
            var up = -sigmaGapPenalty
            var left = -sigmaGapPenalty

            diag += previousRow[j - 1]
            up += previousRow[j]
            left += scoreRow[j - 1]

            val maxCellVal = max(diag, max(up, left))
            scoreRow[j] = maxCellVal
        }
        return scoreRow
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