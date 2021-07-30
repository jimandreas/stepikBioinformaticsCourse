@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "UNUSED_PARAMETER", "UNUSED_VARIABLE",
    "UNUSED_CHANGED_VALUE", "UNUSED_VALUE", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE"
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

    lateinit var blosum62: Array<IntArray>  // scoring array

    lateinit var halfwayColumnScores: Array<Int>
    lateinit var halfwayColumnScoresNext: Array<Int>

    init {
        if (useBLOSUM62) {
            readBLOSUM62()
        }
    }

    /**
     * @param sRow is the string across the top
     * @param tCol is the string down the left side of the scoring matrix
     * @return two pairs of row,col coordinates of an edge
     */
    fun findMiddleEdge(sRow: String, tCol: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {

        val halfWayColumn = sRow.length / 2

        return calculateMiddleEdge(sRow, tCol, halfWayColumn)

    }

    fun calculateMiddleEdge(
        sRow: String, tCol: String,
        halfwayColumn: Int
    ): Pair<Pair<Int, Int>, Pair<Int, Int>> {

        val middleRow = findMiddleRow(sRow, tCol, halfwayColumn)
        val halfwayColumnScoresSaved = halfwayColumnScores.toList()
        val halfwayColumnScoresNextSaved = halfwayColumnScoresNext.toList()

        var notUsed = findMiddleRowReversed(sRow, tCol, halfwayColumn)
        val scoringColumnNext = halfwayColumnScores.reversed()

        val comboNextScores =
            (scoringColumnNext.indices).map { scoringColumnNext[it] + halfwayColumnScoresNextSaved[it] }
        val minRow = comboNextScores.maxByOrNull { it }.let { comboNextScores.indexOf(it) }

        val whereFromDir =
            whereFrom(sRow, tCol, minRow, halfwayColumn + 1, halfwayColumnScoresSaved, halfwayColumnScoresNextSaved)
        println(whereFromDir)

        // adjust the coordinates given the direction of the edge
        val firstRowCoord = minRow
        var halfwayColumnEndBump = 0
        var firstRowStartBump = 0

        if (whereFromDir == 'M') {
            firstRowStartBump = -1
            halfwayColumnEndBump = 1
        }

        if (whereFromDir == 'U') {
            halfwayColumnEndBump = 0
            firstRowStartBump = -1
        }

        if (whereFromDir == 'R') {
            halfwayColumnEndBump = 1
        }

        return Pair(
            Pair(firstRowCoord + firstRowStartBump, halfwayColumn),
            Pair(firstRowCoord, halfwayColumn + halfwayColumnEndBump)
        )

    }

    fun findMiddleRowReversed(
        sRow: String, tCol: String,
        halfWayColumn: Int
    ): Int {
        val pR = findMiddleRow(sRow.reversed(), tCol.reversed(), sRow.length - halfWayColumn - 1)
        return pR
    }


    /**
     * Strategy:
     * score columns until the halfway column.   Then return the max scoring row.
     */

    fun findMiddleRow(
        sRow: String, tCol: String,
        halfWayColumn: Int
    ): Int {

        val columnLength = tCol.length
        val scoreColumn: Array<Int> = Array(columnLength + 1) { 0 }

        for (i in 0..columnLength) {
            scoreColumn[i] = i * -sigmaGapPenalty
        }
        var previousColumn = scoreColumn
        halfwayColumnScores = previousColumn

        for (j in 1..halfWayColumn + 1) {
            val scoredColumn = scoresForThisColumn(sRow, tCol, columnIndex = j, previousColumn)
            halfwayColumnScoresNext = scoredColumn
            halfwayColumnScores = previousColumn
            previousColumn = scoredColumn
        }
        return previousColumn.indices.maxByOrNull { previousColumn[it] } ?: 0
    }

    fun scoresForThisColumn(
        sRow: String, tCol: String,
        columnIndex: Int,
        previousColumn: Array<Int>
    ): Array<Int> {

        val columnLength = tCol.length
        val scoreColumn: Array<Int> = Array(columnLength + 1) { 0 }
        scoreColumn[0] = columnIndex * -sigmaGapPenalty

        for (i in 1..tCol.length) {
            var diag = score(tCol[i - 1], sRow[columnIndex - 1])
            var up = -sigmaGapPenalty
            var left = -sigmaGapPenalty

            diag += previousColumn[i - 1]
            up += scoreColumn[i - 1]
            left += previousColumn[i]

            val maxCellVal = max(diag, max(up, left))
            scoreColumn[i] = maxCellVal
        }
        return scoreColumn
    }

    enum class Dir { NOTSET, MATCHMISMATCH, INSERTION_RIGHT, DELETION_DOWN }

    /*
    this calculates the direction of the edge back from the connecting node in the middle
     */
    fun whereFrom(
        sRow: String, tCol: String,
        iRow: Int,
        jCol: Int,
        columnScores: List<Int>,
        columnScoresNext: List<Int>
    ): Char {

        val upVal = columnScoresNext[iRow - 1] - sigmaGapPenalty
        val leftVal = columnScores[iRow] - sigmaGapPenalty
        val diagVal = columnScores[iRow - 1] + score(tCol[iRow - 1], sRow[jCol - 1])
        val maxVal = max(upVal, max(leftVal, diagVal))

        when {
            maxVal == upVal -> {
                return 'U'
            }
            maxVal == leftVal -> {
                return 'L'
            }
            maxVal == diagVal -> {
                return 'M'
            }
        }
        println("whereFrom: ****ERROR**** in the logic")
        return 'N'
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


    fun invertSecondHalfPath(
        sRow: String,
        tCol: String,
        alignmentPath: MutableList<Pair<Int, Int>>
    ): MutableList<Pair<Int, Int>> {

        val outputPath = alignmentPath.reversed().map {
            Pair(tCol.length - it.first, sRow.length - it.second)
        }
        return outputPath.toMutableList()
    }

    fun fixConnectedNode(
        sRow: String,
        tCol: String,
        middleNode: Pair<Int, Int>,
        connectedNode: Pair<Int, Int>
    ): Pair<Int, Int> {
        var fixedNode = connectedNode
        while (!isNeighbor(middleNode, fixedNode)) {
            fixedNode = Pair(fixedNode.first - 1, fixedNode.second)
            if (fixedNode.first < 0) {
                println("ERROR underflow")
                break
            }
        }
        return fixedNode
    }

    fun isNeighbor(
        upperNode: Pair<Int, Int>,
        lowerNode: Pair<Int, Int>
    ): Boolean {
        val (r1, c1) = upperNode
        val (r2, c2) = lowerNode
        when {
            r1 + 1 == r2 && c1 + 1 == c2 -> return true
            r1 + 1 == r2 && c1 == c2 -> return true
            r1 == r2 && c1 == c2 -> return true
        }
        return false
    }

    /*
     * curNode is the diag down from the previous node.
     * where to go with the alignment path is based on the highest scoring node
     * to the left, or up, or self (diag) relative to the previous node.
     */
    fun whereTo(
        previousRow: Array<Int>,
        newScoreRow: Array<Int>,
        curNode: Pair<Int, Int>,
        halfWayColumn: Int
    ): Char {
        val iRow = curNode.first
        val jCol = curNode.second
        val upVal = previousRow[jCol]
        val leftVal = newScoreRow[jCol - 1]
        val diagVal = newScoreRow[jCol]
        val maxVal = max(upVal, max(leftVal, diagVal))

        when {
            curNode.second == halfWayColumn + 1 && (maxVal == diagVal || maxVal == upVal) -> {
                return 'B'  // signal to stop here (B = break)
            }
            maxVal == upVal -> {
                return 'R'
            }
            maxVal == leftVal -> {
                return 'D'
            }
            maxVal == diagVal -> {
                return 'M'
            }

        }
        println("ERRROR in the logic")
        return 'N'
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