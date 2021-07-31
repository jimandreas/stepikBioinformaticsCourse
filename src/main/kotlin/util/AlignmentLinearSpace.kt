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

class AlignmentLinearSpace(
    val mMatchValue: Int,
    val uMismatchValue: Int,
    val sigmaGapPenalty: Int,
    val useBLOSUM62: Boolean = false
) {


    /**
     * Alignment using linear space (plus a bit of recursion!!)
     *
    * @return Triple of score, alignedRow, alignedCol
    */
    fun alignmentLinearSpace(sRow: String, tCol: String): Triple<Int, String, String> {
      return Triple(0, "", "")
    }


}