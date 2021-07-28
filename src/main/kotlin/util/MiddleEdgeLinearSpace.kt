@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "UNUSED_PARAMETER", "UNUSED_VARIABLE",
    "UNUSED_CHANGED_VALUE"
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
    var alignmentPath: MutableList<Pair<Int, Int>> = mutableListOf()

    init {
        readBLOSUM62()
    }

    /**
     * @param sRow is the string across the top
     * @param tCol is the string down the left side of the scoring matrix
     * @return two pairs of row,col coordinates of an edge
     */
    fun findMiddleEdge(sRow: String, tCol: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {

        val halfWayColumn = kotlin.math.min(sRow.length / 2 /* + 0 + sRow.length % 2 */, sRow.length - 1)

        return calculateMiddleEdge(sRow, tCol, halfWayColumn)

    }

    fun calculateMiddleEdge(
        sRow: String, tCol: String,
        halfWayRow: Int
    ): Pair<Pair<Int, Int>, Pair<Int, Int>> {

        val middleNode = findMiddleNode(sRow, tCol, halfWayRow)

        return Pair(Pair(0, 0), Pair(0, 0))
    }

    /**
     * Strategy:
     * 1) Initialize to node 0,0
     * Loop:
     * score the next row down
     * check which is the next highest scoring node (right down diag)
     * if the path goes right, then iterate until it does not or reaches the middle
     * if the path goes down or diag, then loop
     */
    fun findMiddleNode(
        sRow: String, tCol: String,
        halfWayColumn: Int
    ): Pair<Int, Int> {

        var curNode = Pair(1, 1)
        alignmentPath.add(Pair(0, 0))
        var pathLength = 1

        val scoreRow: Array<Int> = Array(halfWayColumn + 1) { 0 }
        val tColLen = tCol.length

        for (j in 0..halfWayColumn) {
            scoreRow[j] = j * -sigmaGapPenalty
        }

        var previousRow = scoreRow
        for (iRow in 1..tColLen) {
            val newScoreRow = scoresForThisRow(sRow, tCol, iRow, halfWayColumn, previousRow)
            for (j in curNode.second..halfWayColumn) {
                when (whereTo(previousRow, newScoreRow, curNode)) {
                    'D' -> {  // down
                        val downNode = Pair(curNode.first, curNode.second - 1)
                        pathLength++
                        alignmentPath.add(downNode)
                        curNode = Pair(downNode.first+1, downNode.second+1)
                        break
                    }
                    'M' -> {  // match/mismatch (diag to the right)
                        val diagNode = Pair(curNode.first, curNode.second)
                        pathLength++
                        alignmentPath.add(diagNode)
                        curNode = Pair(diagNode.first+1, diagNode.second+1)
                        break
                    }
                    'R' -> {  // right
                        val rightNode = Pair(curNode.first - 1, curNode.second)
                        pathLength++
                        alignmentPath.add(rightNode)
                        curNode = Pair(rightNode.first+1, rightNode.second+1)
                        continue
                    }
                }
            }
            previousRow = newScoreRow
            val lastNode = alignmentPath[pathLength - 1]
            val columnReached = lastNode.second
            if (columnReached >= halfWayColumn) {
                break
            }
        }
        return alignmentPath[pathLength - 1]
    }

    /*
     * curNode is the diag down from the previous node.
     * where to go with the alignment path is based on the highest scoring node
     * to the left, or up, or self (diag) relative to the previous node.
     */
    fun whereTo(previousRow: Array<Int>, newScoreRow: Array<Int>, curNode: Pair<Int, Int>): Char {
        val iRow = curNode.first
        val jCol = curNode.second
        val upVal = previousRow[jCol]
        val leftVal = newScoreRow[jCol - 1]
        val diagVal = newScoreRow[jCol]
        val maxVal = max(upVal, max(leftVal, diagVal))
        when {
            maxVal == leftVal -> {
                return 'D'
            }
            maxVal == diagVal -> {
                return 'M'
            }
            maxVal == upVal -> {
                return 'R'
            }
        }
        println("ERRROR in the logic")
        return 'N'
    }

    fun scoresForThisRow(
        sRow: String, tCol: String,
        iRow: Int,
        numRowsOfColString: Int,
        previousRow: Array<Int>
    ): Array<Int> {

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