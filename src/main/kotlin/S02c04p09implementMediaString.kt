@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

import util.medianString

/**
 * @link: https://stepik.org/lesson/240240/step/9?unit=212586
 *
2.4 From Motif Finding to Finding a Median String
8 out of 11 steps passed
0 out of 5 points  received

Code Challenge: Implement MedianString.

Input: An integer k, followed by a collection of strings Dna.
Output: A k-mer Pattern that minimizes d(Pattern, Dna) among all
possible choices of k-mers. (If there are multiple such strings Pattern, then you may return any one.)

 */

fun main() {
//    val g = listOf(
//               "AAATTGACGCAT",
//               "GACGACCACGTT",
//               "CGTCAGCGCCTG",
//               "GCTGAGCACCGG",
//               "AGTTCGGGACAG",
//    )

    val g = listOf(
        "CCACGGTCACGTGGCAAGGTAAATGACAGAAAGGTGCCCGCC",
        "GATTTTCCGCCGTAGGGCACATTACGTGGAGGTAAGCGTAAC",
        "AGGGCCCATGTTACCGTGTAACGCGGGAAGATGGTTCGCTGA",
        "ATGCGAGGAAAGTGCTACGGGGATATGCTTGTAGTAGCAAAG",
        "ACCAACGCTACATTTAAATCACCAGGCAAGTGACCGACCCGT",
        "TTCCACGGGAAGCGCCCATTCGGTAGAGCGAATAGAGGCTCA",
        "CCTCTTTACAATGGCAAGGCAGCCCGGACCGTCGAGCGATTA",
        "ACGACTCGCGGCGGCAAGAGGTCGCCAGAGGTCGCATATCTG",
        "TCCTCTTGGGACCGTCCGAGGGCCTAGGGTGGTAAGACATCT",
        "AATAACTGCCCTTAAAGAGGAAAGGTAATTTAATTTCAGGCG"
    )
    val k = 6 // kmer length

    val expectedResult = ""
    val kMerResult = medianString(g, k)

    println(kMerResult)
    // answer : GGCAAG

}

