@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

package problems

import algorithms.greedyMotifSearch

/**
Code Challenge: Implement GreedyMotifSearch with pseudocounts.

Input: Integers k and t, followed by a space-separated collection of strings Dna.
Output: A collection of strings BestMotifs resulting from applying
GreedyMotifSearch(Dna, k, t) with pseudocounts.
If at any step you find more than one Profile-most probable
k-mer in a given string, use the one occurring first.


 * See also:
 * stepik: @link: https://stepik.org/lesson/240242/step/9?unit=212588
 * rosalind: @link: http://rosalind.info/problems/ba2e/
 */

fun main() {

    val sample = """
GCGGTATACGTACATCCTGTAGCCCGCTCGGGCAGAAACCGACACATGCATTCAAAGGGAAGCAGATGAAGAGCGTGGCAACTTGCTGCTATGATAAGGACACGCGCCCCCCAATCTGATTCCGATTAACCTGAAGCATGTGGTTCTGTCCCTGAT
GAGATCTCGCATGGGAATTTATTATGACGGCCCCTCGGTTGAGAGTTCGACGGTCCCAACATTTGGCAAGGACCGTGGATGAGTCATACAAACGGGTCGGTAGAATTATTGTCAACTGGGGAGAGTACTCTTACGTAGTCATTTATCTACAGCGCA
GCACGCATCGGTATTGCTGCGGGGATGCGCTGGTTTTCGGTCCGCCGCGTCCAAACGTATGCACAACAAGCCTTGAGACACTAATCTGAGAGCACGACCACACGGTTAATTTCTCTTCTGACTACTTGTGGTCATTCAAATGAACGATAGTATGGG
ACTCTCTAGACTTTAACATATCGGCTCTTACGAATAACGGCTGCCTTACATCGAGACCTCCGCCAGACCCGGGTCCTCGAAACTCAGGCTAGCGGCGTGACTTTCCCCCATTCAAAAGGTGCATGTCACAAGGTAAACACGAGCAACGGAGCCCGC
AGAATGATGTGCATCAGGCCATTGGGTTTCATGGTAGGTGAAATGCGACATCCAAATGGCAGTAACACCAGACTAGGCGGGTCATCAGACTTGAGGACTTTGCGCGCCGGATCGGTTATAAAGTGTTTGGCAAGAGCATCTAGATATAGACCAGCG
AATTACATAGTTGGCCCTTCTGTACATCGCAATCTCCATACAAATGCTAGTCTGAATCCGAGAAAATCCACTGCGGGCGCTAGATTGCTTAGAGCGTTAGCCATACCGAATTCTGACTACTTCGTGGTGGGGGAATCTTTGAGCGGAAAAAGACCG
ACTCGAAGCTGTCGAACCCCGGTTTGACTAGAGCAGCATACAAAAGGGGCCTTGGTCTTCTAATGTTATCAGTGACGCTGACCGTGGACCGTAGACGGAACTCTCAAGCAACACCACTACACAGATACCCTAATTTGGGGCCTTGGCCGGTCCGAT
CGCATGACGACGCCATATCCCTGCTATCATGCACCTTGTTCCCGCATGGGTGCGTAACGTCATACAAAAGATCGTCGCCAGATCTAAATTCCTCAGGAGACTCCGTTGTCCATAGTACTCCGGTGAACTAATAAAGTTCATTCGATCGGGAACGTC
ACTTGCGGGACACATTCAAATGCCGGCTCACGAAGCACGCATTCTTCCTGATTCACTTAAGGCTGTTCAATATTTCCTCTTAACCCTTTTAGTTTCCCGCGTCGTCGAGTGTGGCTTGACGCTAATGCGCAATGCCTTCAAGTGCTTTTGAGCCTC
GGTGCCTACGCCGCCATTCAAACCATGTAAGGACGCGAAATAAAGCCAAGAATTAGGGGACTTGGTTGGGGTCGGGACTTTCCTCGTCATTCGGTAGCTGAATTACTGACCTTTACAGACCATACAAATGGTGGTATCCTTCGAGAAAAAGGGGGT
CACAATTCGCTAACCTACCGGCCATCGACCGTGCTTAACGACGCATTTCGAGTAGCGGCCCATCCAAATGTCCATTCGAAACTTGACTGTAGACGTCAACATTAGGTAAGGCTCTATCCCCTTTTGGACTCGGGTACTATTCCCAAGTGCTAAGTC
ACCTGGTCACACCCAGGATCGCACTGGGGCATCACCCATGCAAAAGGTATAGAGATCGTCTTTCGCATGAATCGATTCATCCTAGTCGAAGACCCCGTCGAGGTCACACTTCACCGATTTTTCGAGCGGTATAGATGTATACTCCAAGTGATTGTC
GTGCACCACGTGTTTTTTACGGAGAACTATCGATTCGAGGACACCGTATGGCAATGCGGGACGGAAGCAGTCGTAGGTCCGCGGAGATCTAGTTGTCATAAACCACATCCAGCGCATTCGCATGCAAACGCTCGTAGATCTAGCGTATTGACCGAC
AGGATCAATTCGTAGCCGTCCGGGCACAAGGGGCGATTGACCAATTAGTGCGCAGCGTCGCTGTCTGACAAAGTTTCGGGGCACAAACGCCCCCGCGGGCCTCATGACACTTCTCTACGACATCCAAATGACATTCTTAAAGTAAGTTGCTTGAGG
CGGCGTATCGATCTTGGAAGGCGTCCACAACGAGATGGAAAAATCAGTACCGGAGCAACTTACCCTGCTCACCATCCAAAGGGTGAATTCACCTAATTCAGCGTCCCAGGGGCCGTAGCTGGGGGTGCAACGGCCATCCTTTTGCTAATGGGACGC
CCGGCCGCCCTCGATTTGTGCAAGATTAGCCAGCCACTGACCTGCCGGCGTCATCATAGACATACAAATGCCTATACGAGCACGATTTCCAATTAGAGGGTGTAATGGCAGGTACGGCGCATGCTGTGGATGTTCCTCTTTCCTATTAGTTCCATA
AAGCTCGGCGAACCTTCCTCCGATCTCATTGCGGCGATAAGTCACATCGATCGAGTGCTTGCTCAAAGCTTTGACGGCTTCCCTATTTCGACGGACTACGTGAGGGCTTTTGTACGCCTACGAACCCTATAGTACGGGGGCTTCCATGCAAAAGGC
TAGTGGTACTCGACCTCCAATACAGTAAGGGCTGACTCAGCCACCGTGCATACAAAAGTACTTATGTTTGCCACAGGAAGAGACTGGACTAATTAGTCTAGTAGAGCTCAATCGAACAAACAGAACTACTGCCCCGCTGATCCGTGTTCAAGCTAT
ATCCCTATAAACGCCTGCGGTGTGACTCGCTTTCGAACACTCCTCCGGTCAAAGCAAGAGAGGGACATCGATTGGGCGTCGATCTTTCTTCACATACATCCAAAGGGGCCAGGAGCGGAATGAAAGTGTGGGTAGCAATGGTGAAACTAGTATTTT
AGTCGTGGAATAAGGGCTGTACTCGGTTTAGATCGTCGTGTTTGGAGTTAAGTGTAAGTAGTACGTCCCCACGTGAGGACTAGGGGGAGCTTATACGTATAACCAGAAGGTGCTACAGGGCATGCAAAGGGACCACTCGCGTGAAAGGGTATGAGC
GCAACCCATGGGCGCGTTGGTATCAGTGAGTGCGGCCCACGGGATCGGCATTCAAACGCTCCTAGTGGGGGGTCGCTTTCTTGTAATTCTAGAATTGGCTGGTCGGAAGATCAAGGCCACTGGTTGTCAGGGATGTACCTGCGTGCCCTAACATTT
CAGGAAACCATTGAGTGTCGCGGTCATTCAAAAGCACACTCTTTGTATATCGACCAGCAAAAACGCGGACTTCGCGAATGGCGGAATCCCGCCTCAAGGCCGTTAGTAGTCCCATGACCAGCTCAGCGTTCTACACCCAGATTAACTAAAAAACCC
ATCTGCCCTAGTGTATATGGCTCGCTAGGATTAATTGTAGCACCGAGCATTCGGTGAATTATTTAATATTCTCCCCTCAAACCTGCGTCAACAACTTCGAGGTTCACTCATCCAAAGGGGCGTTTGGCGGCGGTGGTAGCGTTATCGCAGGCATCA
CTGCCATGCCGACTCACACTCCGATTGGAATGACCTGTTCCTCGCTTTACGTAAGAATTCACCTGTGTCCAAAGGTAATTGACATTAGTACGTGTCATCGCGGCATCACGAAAGACCCGATGTGGTCCTTAAAATATATGACTGCATGCAAATGTC
TTGTGGTTCTCGAGGGGAATAGCGTAGGTTCTGGAGCGTGTCCGAGTCCATGCAAACGTATGTACCAAGTATTAAACACAACTTGCCCTTAGTCACGTAGCCCAGGCTGTCGGCTGGGAGCAGTATAGATAACCACCCGACGATATCTGTTTAGTA
    """.trimIndent()

    val reader = sample.reader()
    val lines = reader.readLines()

    val kmerLength = 12

    val result = greedyMotifSearch(lines, kmerLength, applyLaplace = true)  // note : do not sort result
    for (i in result) {
        print("$i ")
    }

}



