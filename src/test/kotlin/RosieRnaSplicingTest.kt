@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.RnaSplicing
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * See also:
 * http://rosalind.info/problems/splc/
 * RNA Splicing
 *
 * Problem

After identifying the exons and introns of an RNA string,
we only need to delete the introns and concatenate the
exons to form a new string ready for translation.

Given: A DNA string s (of length at most 1 kbp) and a collection of substrings of s acting as introns.
All strings are given in FASTA format.

Return: A protein string resulting from transcribing and translating the exons of s
(Note: Only one solution will exist for the dataset provided.)
 */

internal class RosieRnaSplicingTest {

    lateinit var rs: RnaSplicing
    lateinit var u: Utility

    @BeforeEach
    fun setUp() {
        rs = RnaSplicing()
        u = Utility()
    }

    @Test
    @DisplayName("Rosalind Rna Splicing sample test")
    fun rosalindConsensusSampleTest() {
        val sampleInput = """
>Rosalind_10
ATGGTCTACATAGCTGACAAACAGCACGTAGCAATCGGTCGAATCTCGAGAGGCATATGGTCACATGATCGGTCGAGCGTGTTTCAAAGTTTGCGCCTAG
>Rosalind_12
ATCGGTCGAA
>Rosalind_15
ATCGGTCGAGCGTGT
        """.trimIndent().lines()

        val dnaList = u.utilityParseFASTA(sampleInput).toMutableList()

        val dnaString = dnaList[0]
        dnaList.removeFirst()

        val peptideString = rs.rnaSplice(dnaString, introns = dnaList)

//        println(consensusString)
        assertEquals("MVYIADKQHVASREAYGHMFKVCA", peptideString)

    }

    @Test
    @DisplayName("Rosalind Rna Splicing Quiz test")
    fun rosalindConsensusQuizTest() {
        val sampleInput = """
>Rosalind_7596
ATGAAAACGGGTTCGTCCTTTGGTTTGTTAGACTTGAGCACCGATCTAGGGACACCCGTC
CTGCGCGGGTTAGTAGGAACCGACTCATGGCGCTGCCGCAGTGGAACTCCAGAAAAGTGT
AATCTGGCCGTTCTTATACGTCTAGGAGCTTGCTCCGGATGGTGCCCCGCAACCTCTGGC
TATGTTATTAACCGGCCCTCGTAGCTTGGCGAGAAGGGGAGCTACTCCTTGGGGTCTCTT
AGCATTCGTGGATAATTGGCTCTATTAACTCAGAGCGGGACAGAGTGTCGCAGTTCGCAT
GAGGTGGAGCGCGGGACTCATGTCCTAGTAGTGGAGCCCTTCTAGCCCGCTTAAAGGTTC
CCGGACTACTTGAATCAAGTCGTCCTCCGAGAAACCACCTATGTCCTCGGCGATCTCCAA
AATGTTATGGCACAAATACATCACAACAATCAATATGGGGAGCCCTGCTCTTGCCGTTTA
AGAATCTCTATTCTATCTTGCGATGGCTCTCAGAATGTGGGACTCTTCTGTTAATGCGCT
CTCAGCGACGCCAGTTACAGCAGGACTTGACGGGGTACGGTTGCTCAGAGGTCAACCGCT
TTACATCTGCAAGTAGACAGCAGGAGAGCTACCATCGAGATATAAGAGGAAGAAATCAAA
CTGCCGGTTCTGTCCACCAGACACTTTACAAACCTTCCACGCTCTCCGGGCAACACAGAT
GAGGCTACTTGCCTTGACTCGGTCCGCAAGCAAGATGAGTGGCCTCAGGCACCCTATACG
GAACTCTCCCCAGACGTAGGTTGCTGCCTAGTTCGTACTTTCTAAACGGTGTGTTCGAGA
AATGGCTCCTCGCACTCGTATACAATCCCTCTATCATCTCGAAATTGGGTGGCGGCACCA
ACTGGGGGTCCTTTGTACAACCTGGAAACAAACAGTGTTAGAGTCGCGTCCAAATTATGG
GAAGTGA
>Rosalind_4544
TGGCTCCTCG
>Rosalind_4194
GACTTGAGCACCGATCTAGGGACACCC
>Rosalind_7634
AGTCGTCCTCCGAGAAA
>Rosalind_4832
CTTAGCATTCGTGGATAATTGGCTCTATTAACTCAGAGCGGGACA
>Rosalind_9680
ATCACAACAATCAATATGGGGA
>Rosalind_8951
AGCTTGGCGAG
>Rosalind_0596
CATGTCCTAGTAGTGGAGCCCTTCTAGCC
>Rosalind_6321
CTCAGAGGTCAACCGCTTTACATCTGCA
>Rosalind_2099
CTCTCCCCAGACGT
>Rosalind_8215
GGTTCTGTCCACCAGACACTTTACAAACCTTCCACGCT
>Rosalind_4074
CTACTTGCCTTGACTCGGTC
>Rosalind_4607
AAGTGTAATCTGGCC
>Rosalind_6528
GGAGCTTGCTCCGGATGGTGCCCCGCAACCTCTGGCT
>Rosalind_7269
GGTCCTTTGTACA
>Rosalind_0514
GAATGTGGGACTCTTCTGTTAATGCGCTCTCAGCGACGCCAGTTACAGCA
        """.trimIndent().lines()

        val dnaList = u.utilityParseFASTA(sampleInput).toMutableList()

        val dnaString = dnaList[0]
        dnaList.removeFirst()

        val peptideString = rs.rnaSplice(dnaString, introns = dnaList)

//        println(peptideString)

        val expectedPeptide =
            "MKTGSSFGLLVLRGLVGTDSWRCRSGTPEVLIRLMLLTGPRKGSYSLGSECRSSHEVERGTRLKVPGLLESPPMSSAISKMLWHKYALLLPFKNLYSILRWLSGLDGVRLSRQQESYHRDIRGRNQTALRATQMRRKQDEWPQAPYTERLLPSSYFLNGVFEKHSYTIPLSSRNWVAAPTGTWKQTVLESRPNYGK"
        assertEquals(expectedPeptide, peptideString)

    }


}