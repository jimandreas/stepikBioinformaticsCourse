@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

package problems

import algorithms.medianString

/**

2.4 From Motif Finding to Finding a Median String
8 out of 11 steps passed
0 out of 5 points  received

Code Challenge: Implement MedianString.

Input: An integer k, followed by a collection of strings Dna.
Output: A k-mer Pattern that minimizes d(Pattern, Dna) among all
possible choices of k-mers. (If there are multiple such strings Pattern, then you may return any one.)

 * See also:
 * stepik: @link: https://stepik.org/lesson/240240/step/9?unit=212586
 * rosalind: @link: http://rosalind.info/problems/ba2b/

 */

fun main() {
//    val g = listOf(
//               "AAATTGACGCAT",
//               "GACGACCACGTT",
//               "CGTCAGCGCCTG",
//               "GCTGAGCACCGG",
//               "AGTTCGGGACAG",
//    )


    val sample = """
TAGTGCCTTACAGAACAAGGTAGGTCGTGCAACACGAGGCAT
AACCCCTCGGCCGGACATCGCTCCTAAATTGCCTTTGGCAGG
GGTAGGCGGACCCTTCTAGGGTGGACTGGTCGATAGCCGCGC
TGGGCGCCAGCTGGCAGGATATGGGGTCACCCACTTTTGGGT
GGTAGGAAGCGGGGCCAACAGGGGAACTCCTCCTTAGCCTAC
CGGTGTAGCTCTTACTTTAAGCCCGGGAGGCAGCGGTACATG
TAAAGCACCTTCTACTATGGAAGGCTACGTGGATTGTGACAG
AGAGTGGGGAGGAGAATAAGTGTTGAGTTGGAGGAAGGCGTC
GTCTATGGCAGGCGTATACTCTCAACGACACTACGGTTGCTC
ATTATTGGCAGGCCTACCTTATTGGTGACGATGTTCAACAGA
    """.trimIndent()

    val reader = sample.reader()
    val lines = reader.readLines()


    val k = 6 // kmer length

    val expectedResult = ""
    val kMerResult = medianString(lines, k)

    println(kMerResult)
    // answer : GGCAAG

}

