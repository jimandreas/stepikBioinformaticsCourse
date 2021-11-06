@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.OverlapGraphsOofN
import algorithms.RosalindReverseComplement
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 * See also:
 * http://rosalind.info/problems/rvco/
 * Complementing a Strand of DNA
 *
 * Problem

Recall that in a DNA string s, 'A' and 'T' are complements of each
other, as are 'C' and 'G'. Furthermore, the reverse complement
of s is the string sc formed by reversing the symbols of s and
then taking the complement of each symbol (e.g., the reverse complement of "GTCA" is "TGAC").

The Reverse Complement program from the SMS 2 package can be run online here.

Given: A collection of n (n≤10) DNA strings.

Return: The number of given strings that match their reverse complements.
 */

internal class RosieReverseComplementRVCO {

    lateinit var rcSum: RosalindReverseComplement
    lateinit var u: Utility

    @BeforeEach
    fun setUp() {
        rcSum = RosalindReverseComplement()
        u = Utility()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Rosalind Reverse Complement RVCO sample test")
    fun rosalindOverlapGraphSampleTest() {
        val sampleInput = """
>Rosalind_64
ATAT
>Rosalind_48
GCATA
        """.trimIndent().lines()

        val expectedResult = 1

        val dnaList = u.utilityParseFASTA(sampleInput).toMutableList()
        val numMatches = rcSum.reverseComplementMatches(dnaList)

        assertEquals(expectedResult, numMatches)

    }

    @Test
    @DisplayName("Rosalind Reverse Complement RVCO Dry Run test")
    fun rosalindOverlapGraphDryRunTest() {
        val sampleInput = """
>Rosalind_5527
GTCAGACCCTAACTGCCTCCGTTTCTCAGCTATTACCAGATAATCAGGCGCCTGATTATC
TGGTAATAGCTGAGAAACGGAGGCAGTTAGGGTCTGAC
>Rosalind_8914
TACATCATAAGCAGGAGCCCCCAAATGTTGCAAGAGATCGCGCGATCTCTTGCAACATTT
GGGGGCTCCTGCTTATGATGTA
>Rosalind_9304
CACGATGAACCCGGACAGTGCCTCAGCATGACCCCCGTAACAAGGCTTATATAAGCCTTG
TTACGGGGGTCATGCTGAGGCACTGTCCGGGTTCATCGTG
>Rosalind_6765
CCTGAAATATCCACCACTGAAGAAGTCCGCGGGATCTAGGCTCTAGAGCCTAGATCCCGC
GGACTTCTTCAGTGGTGGATATTTCAGG
>Rosalind_9263
AAAATGTCTGGTCGTGATCGCCAACTCTAAGCGCACTGGAACGTTCCCTGGTCAGTTTTT
ATCCCCTTTCGCGGCCTTCGTATCCCACAGCCG
>Rosalind_6793
ACGAGCATCATTGGCTGAAGGGCTTTTTGATGCTAGAAATGTTATTGTGCATATGCGGGT
CGCTTTGAAAATCGCTTCTATCCAGTTTCG
>Rosalind_8949
ACTCGGCCTTCTCGTAAAAGTGAGGCGCTGGATGTTTAAGGTAGAGTTCGGCATGGGGAG
CCTCCCCGGCCTCACTCGATCTTAACGCGCGGT
>Rosalind_8568
ACTTATAAAGTGTGCTAGTGATACGGCAAAGTTGCCGTCACATTGTTAACAATGTGACGG
CAACTTTGCCGTATCACTAGCACACTTTATAAGT
>Rosalind_1641
TTGACGCATGAACGTCACTCAGAGGGATGTGAGAGTGGCCACTTCATATGAAGTGGCCAC
TCTCACATCCCTCTGAGTGACGTTCATGCGTCAA
>Rosalind_4238
GGAGCAAGGATAACTTAGTTACTGTGGAGCCGCACCATAATATATTTGCAAATATATTAT
GGTGCGGCTCCACAGTAACTAAGTTATCCTTGCTCC
        """.trimIndent().lines()

        val expectedResult = 7
        val dnaList = u.utilityParseFASTA(sampleInput).toMutableList()
        val numMatches = rcSum.reverseComplementMatches(dnaList)

        //println(numMatches)
        assertEquals(expectedResult, numMatches)

    }


    @Test
    @DisplayName("Rosalind Reverse Complement RVCO Quiz test")
    fun rosalindOverlapGraphQuizTest() {
        val sampleInput = """
>Rosalind_5324
GACGAAAGCTTTAGAGCTCCTAGAAAGCCTACGTCGCCTTTTTAAAAAGGCGACGTAGGC
TTTCTAGGAGCTCTAAAGCTTTCGTC
>Rosalind_8135
CCGGTGTCAGTACTAATAGCCTGTGACAAAGAGGCCTATCCATACCAAGGCAGCGGTGCT
GCTCAACTGGGGGGATTCCCCGATTGTCCCACACGG
>Rosalind_4785
ATTGCAATATATTGTCATGGTAACATTCGATCCTATGTGGTGCGCCTCGGCTGCGCTCGC
GACTTTACCTCAAAGATCCGATAGATCC
>Rosalind_6490
GCGTCATCAGGCGTAGAATCTGAGGTCGCTCTAAACGGGCTCTCACCGTCGTCCCAGGCA
CGTTTGATAACACCCGGAGGT
>Rosalind_8397
TAGAATCTTTCCGATTAAGTCTTGTGACCCTTTTGGGGACGCGCGTCCCCAAAAGGGTCA
CAAGACTTAATCGGAAAGATTCTA
>Rosalind_6968
CGAGGACAAGGTTAGCCTGACAGCAGAACACTTGTGCCTACCGCGCGCGGTAGGCACAAG
TGTTCTGCTGTCAGGCTAACCTTGTCCTCG
>Rosalind_4807
AGCACCGATCACCAGCATAATGGTGCGTTCAGTGGTGCAGCTGCACCACTGAACGCACCA
TTATGCTGGTGATCGGTGCT
>Rosalind_7860
CTCCAACTTGTAAAAGGGTATTGGTGTTTGGAGGCTTACCCTCCATGGAGGGTAAGCCTC
CAAACACCAATACCCTTTTACAAGTTGGAG
>Rosalind_0900
TCCCTAAGACCTACTAGGAAACTAGGTCGACCCACAGACCCGGCATATGTGCATGTGTGT
CCACTCAATCACTCCCCGATCCTGCGCTTGTTATGATTGT
>Rosalind_2047
ACAATCTAATTGGGCACCGTAGCTTGTGGCGAGTGACTCCACATATACGAGCCGCCGCCT
TCATTAGGGGCATTGACGATATTCGCCTG
        """.trimIndent().lines()

        val expectedResult = 5
        val dnaList = u.utilityParseFASTA(sampleInput).toMutableList()
        val numMatches = rcSum.reverseComplementMatches(dnaList)

        //println(numMatches)
        assertEquals(expectedResult, numMatches)

    }
}