@file:Suppress(
    "UnnecessaryVariable", "unused", "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment",
    "IntroduceWhenSubject", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VARIABLE"
)

package util

/**
 *
Edit Distance Problem: Find the edit distance between two strings.

Input: Two strings.
Output: The edit distance between these strings.

Code Challenge: Solve the Edit Distance Problem.
These are set up when the class is instantiated.

This routine calls into the GlobalAlignment backtrack function to do the string to string work.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240306/step/3?unit=212652
 * rosalind: @link: http://rosalind.info/problems/ba5g/
 * book (5.11):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

class EditDistance(
    val mMatchValue: Int,
    val uMismatchValue: Int,
    val sigmaGapPenalty: Int,
    val useBLOSUM62: Boolean = false
) {

    fun calcEditDistance(sRow: String, tCol: String): Int {
        val ga = GlobalAlignment(mMatchValue, uMismatchValue, sigmaGapPenalty, useBLOSUM62)
        val resultArray = ga.backtrack(sRow, tCol)

        val editResult = ga.alignmentScoreResult
        return editResult
    }


}