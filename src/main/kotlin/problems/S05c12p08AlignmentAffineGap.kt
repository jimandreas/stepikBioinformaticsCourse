@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems

import algorithms.AlignmentAffineGap

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
 * book (5.12):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

fun main() {

    val sample = """
HDLYQMIVMPGVGINDNCRSWARPHMETDICQHRATCMYIQFTGIMPLVDTQPPSPFCTWVHINWDPTSDYQAH
HDLYPMVGINDNCRSNARPHMNLPINTMICQHRATCMYKWPLMMCSDQYCTGIMPLVDTQPPSTFCTWVHINWDPTSDYQAH
          """.trimIndent()

    val reader = sample.reader()
    val lines = reader.readLines()

    val sRow = lines[0]
    val tCol = lines[1]

    val match = 0
    val mismatch = 0
    val gap = 11
    val epsilonGapExtension = 1

    val aag = AlignmentAffineGap(match, mismatch, gap, epsilonGapExtension, useBLOSUM62 = true)

    val result = aag.affineAlignment(sRow, tCol)
    println(result.first)
    println(result.second)
    println(result.third)

    // Correct!!

}


