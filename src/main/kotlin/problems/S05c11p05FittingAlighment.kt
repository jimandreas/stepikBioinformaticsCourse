@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems

import algorithms.AlignmentFitting

/**

Code Challenge: Solve the Fitting Alignment Problem.

Input: Two nucleotide strings v and w, where v has length at most 1000 and w has length at most 100.
Output: A highest-scoring fitting alignment between v and w.
for the Quiz: Use the simple scoring method in which matches count +1 and both the mismatch and indel penalties are 1.

Otherwise:

A match score m, a mismatch penalty μ, a gap penalty σ

These are set up when the class is instantiated.

Not used: A modal flag indicates whether to use the PAM250 match/mismatch matrix.

The changes involve using the scoring matrix to calculate the winning value
for each cell.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240306/step/5?unit=212652
 * rosalind: @link: http://rosalind.info/problems/ba5h/
 * book (5.11):  http://rosalind.info/problems/ba5f/
 */


fun main() {

    val sample = """
GAACTTTACTAGAAGAAGGAACCCGAAGTAGGCGTGTAATAAATATAATGAGGGGACATCCTGGCTCCTCCGGTCCAGAGCTTTACAGATAGTGCTCCACTCGGATTCACTGGTATTTAGTTAATCCCCTAAGTATAAAACGTTAGGACCTGGACGCATACCGACTAAGGCGACAGAGAGCGCACTGTTATTGGACTGCAACATGCACAACCAGAAATCATACGGTAGACTCTAGATCTTCAACAACTTGGGCTGAGATGTAGGCTTACATGTGGGTAAACTCAACGCGTCACAAGTCACGGATACGGCCACTACGTTTGGACTAGTCCATACATGCGCCGTTAGAGGTAAACTAGTCTGACCAGTCTTCGTCAATAATATGCTCCCCATAATCGAAGTTCTTCCCGTTCTTGGTCCTCTCGGCTCATCAACCGACACTTTGTATCTCGAAGCGTCGTCGCGAGCACTCAGACGCCTAAGTGTGTTATCCGCTGGCAATGTCACCTGTGTGATTATTAGCGCTGGGTTAAAATGTGACCAAAGTAGAGCTCAGGAGATCATTGAGAAGTATAGAGCCTGCGCTATTTTTCCTACTTTCTCATCAGATGAAGGCGGTGTAGTTCACGCGACAAAAGATCGGGCGCGGCCACCGCCCTGAGATAGCATACTACGGTCCGCGCGAACGGAATTCCACAGCGACACGCATCGCAGACTGTGCCAACTTTATGCTGTACGATGCATCCCCGTAACGGTTATTGAACCCCGGCCGGGATAATTGCCGACCGTAGGAAACATGTCGTCGTATTACTCTTTCCGAATGCCCGAAGGATCAATCTGGTAGTTATCTGGCGTTCGGTAAAGCAACGAGGTAC
TAAATCACTTATGGTTCTTTCTTCTTTAATTCTGAGAGAGAAGCAAATTTTGAAACTTTAGCGATCCAATCATAAACCCGTCAT
           """.trimIndent()

    val reader = sample.reader()
    val lines = reader.readLines()

    val sRow = lines[0]
    val tCol = lines[1]

    val match = 1
    val mismatch = 1
    val gap = 1

    val fa = AlignmentFitting(match, mismatch, gap)

    val result = fa.fittingAlignment(sRow, tCol)
    println(result.first)
    println(result.second)
    println(result.third)

    // correct!

}


