@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "UNUSED_PARAMETER", "UNUSED_VARIABLE",
    "UNUSED_CHANGED_VALUE", "CanBeParameter"
)

package util

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

class AlignmentLinearSpace(
    val mMatchValue: Int,
    val uMismatchValue: Int,
    val sigmaGapPenalty: Int,
    val useBLOSUM62: Boolean = false
) {


    val alsme = MiddleEdgeLinearSpace(mMatchValue, uMismatchValue, sigmaGapPenalty, useBLOSUM62)
    var score = 0

    /**
     * Alignment using linear space (plus a bit of recursion!!)
     *
     * @return Pair of score, alignedRow, alignedCol
     */
    fun alignmentLinearSpace(sRow: String, tCol: String): Triple<Int, String, String> {
        val sRowOut = StringBuilder()
        val tColOut = StringBuilder()

        val output = linearSpaceAlignmentRecursive(
            sRow, tCol,
            0, tCol.length,
            0, sRow.length,
            sRowOut, tColOut
        )
        return Triple(score, sRowOut.toString(), tColOut.toString())
    }

    /*
     * link to:  http://rosalind.info/problems/ba5l/
     */
    val pseudoCode = """
        
LinearSpaceAlignment(v, w, top, bottom, left, right)
  if left = right
    output path formed by bottom − top vertical edges
  if top = bottom
    output path formed by right − left horizontal edges
  middle ← ⌊ (left + right)/2⌋
  midEdge ← MiddleEdge(v, w, top, bottom, left, right)
  midNode ← vertical coordinate of the initial node of midEdge
  LinearSpaceAlignment(v, w, top, midNode, left, middle)
  output midEdge
  if midEdge = "→" or midEdge = "↘"
    middle ← middle + 1
  if midEdge = "↓" or midEdge ="↘"
    midNode ← midNode + 1
  LinearSpaceAlignment(v, w, midNode, bottom, middle, right)
         
         """.trimIndent()

    fun linearSpaceAlignmentRecursive(
        sRow: String, tCol: String,
        top: Int, bottom: Int,
        left: Int, right: Int,
        sRowOut: StringBuilder, tColOut: StringBuilder
    ): Pair<StringBuilder, StringBuilder> {

        if (left == right) {
            if (top != bottom) {
                if (sRow.isNotEmpty()) {
                    sRowOut.append(sRow)
                    tColOut.append(sRow.map { '-' }.joinToString(separator = ""))
                    score -= sRow.length * sigmaGapPenalty
                }
                if (tCol.isNotEmpty()) {
                    sRowOut.append(tCol.map {'-'}.joinToString(separator = ""))
                    tColOut.append(tCol)
                    score -= tCol.length * sigmaGapPenalty
                }
            }

            return Pair(sRowOut, tColOut)
        }
        if (top == bottom) {
            if (left != right) {
                if (sRow.isNotEmpty()) {
                    sRowOut.append(sRow)
                    tColOut.append(sRow.map { '-' }.joinToString(separator = ""))
                    score -= sRow.length * sigmaGapPenalty
                }
                if (tCol.isNotEmpty()) {
                    sRowOut.append(tCol.map {'-'}.joinToString(separator = ""))
                    tColOut.append(tCol)
                    score -= tCol.length * sigmaGapPenalty
                }
            }
            return Pair(sRowOut, tColOut)
        }

        var middle = (left + right) / 2
        val middleEdge = alsme.findMiddleEdge(sRow, tCol)
        var midNodeColIndex = middleEdge.first.first
        var midNodeRowIndex = middleEdge.first.second

        // work up new substrings for top left quadrant
        val sRowTopLeft = sRow.substring(0, midNodeRowIndex)
        val tColTopLeft = tCol.substring(0, midNodeColIndex)

        //println("'$sRowTopLeft' '$tColTopLeft' TOP LEFT middle edge $middleEdge")

        val topLeft = linearSpaceAlignmentRecursive(
            sRowTopLeft, tColTopLeft,
            0, midNodeColIndex,
            0, midNodeRowIndex,
            sRowOut, tColOut
        )

        val dir = middleEdgeDir(middleEdge)

        when (dir) {
            'R' -> {
                middle++
                sRowOut.append(sRow[midNodeRowIndex++])
                tColOut.append('-')
                score -= sigmaGapPenalty
            }
            'D' -> {
                sRowOut.append('-')
                tColOut.append(tCol[midNodeColIndex++])
                score -= sigmaGapPenalty
            }
            'M' -> {
                //middle++
                score += alsme.score(sRow[midNodeRowIndex], tCol[midNodeColIndex])
                sRowOut.append(sRow[midNodeRowIndex++])
                tColOut.append(tCol[midNodeColIndex++])
            }
        }

        // work up new substrings for bottom right

        var sRowBottomRight = ""

        if (midNodeRowIndex < sRow.length) {
            sRowBottomRight = sRow.substring(midNodeRowIndex, sRow.length)
        }

        var tColBottomRight = ""

        if (midNodeColIndex < tCol.length) {
            tColBottomRight = tCol.substring(midNodeColIndex, tCol.length)
        }

        //println("'$sRowBottomRight' '$tColBottomRight' BOTTOM RIGHT")


        val bottomRight = linearSpaceAlignmentRecursive(
            sRowBottomRight, tColBottomRight,
            midNodeRowIndex, sRow.length,
            midNodeColIndex, tCol.length,
            sRowOut, tColOut
        )

        return Pair(sRowOut, tColOut)
    }

    /**
     *  reverse engineer the direction of the edge
     *  given the coordinate pair of the edge.
     *
     *  @return R for row="-', D for column="-", and M for match/mismatch (down and to the right)
     */
    fun middleEdgeDir(middleEdge: Pair<Pair<Int, Int>, Pair<Int, Int>>): Char {
        val r1 = middleEdge.first.first
        val c1 = middleEdge.first.second
        val r2 = middleEdge.second.first
        val c2 = middleEdge.second.second

        when {
            r1 == r2 && c1 <= c2 -> return 'R'
            r1 <= r2 && c1 == c2 -> return 'D'
            r1 < r2 && c1 < c2 -> return 'M'
        }
        return '?'
    }


}