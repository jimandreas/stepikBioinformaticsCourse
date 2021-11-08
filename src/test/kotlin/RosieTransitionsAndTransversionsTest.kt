@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.TransitionsAndTransversions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * See also:
 * http://rosalind.info/problems/tran/
 * Transitions and Transversions
 *
 * Problem

For DNA strings s1 and s2 having the same length,
their transition/transversion ratio R(s1,s2)

is the ratio of the total number of transitions to the
total number of transversions, where symbol substitutions
are inferred from mismatched corresponding symbols as when
calculating Hamming distance (see “Counting Point Mutations”).

Given: Two DNA strings s1 and s2 of equal length (at most 1 kbp).

Return: The transition/transversion ratio R(s1,s2)

 */

internal class RosieTransitionsAndTransversionsTest {

    lateinit var tt: TransitionsAndTransversions
    lateinit var u: Utility

    @BeforeEach
    fun setUp() {
        tt = TransitionsAndTransversions()
        u = Utility()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Rosalind Transitions and Transversions sample test")
    fun rosalindTransitionsTransversionsSampleTest() {
        val sampleInput = """
>Rosalind_0209
GCAACGCACAACGAAAACCCTTAGGGACTGGATTATTTCGTGATCGTTGTAGTTATTGGA
AGTACGGGCATCAACCCAGTT
>Rosalind_2200
TTATCTGACAAAGAAAGCCGTCAACGGCTGGATAATTTCGCGATCGTGCTGGTTACTGGC
GGTACGAGTGTTCCTTTGGGT
        """.trimIndent().lines()

        val expectedResult = 1.21428571429

        val dnaList = u.utilityParseFASTA(sampleInput)
        assertEquals(2, dnaList.size) // should be two strings
        assertEquals(dnaList[0].length, dnaList[1].length) // of equal langth

        val transitionCount = tt.transitionCount(dnaList).toDouble()
        val transversionCount = tt.transversionsCount(dnaList).toDouble()

        val ratio = transitionCount / transversionCount

        assertEquals(expectedResult, ratio, 0.001)

    }

    @Test
    @DisplayName("Rosalind Transitions and Transversions quiz test")
    fun rosalindTransitionsTransversionsQuizTest() {
        val sampleInput = """
>Rosalind_4187
AGATTGATTCACATATCCCCCACTAGATACGGATCCTAGTTTGGGGACAGCAGAATCGAC
CAGTTGTGTTCAGACATTTTTACGATAAAATGCTTGCCCACACGTTAGCGTGTTGGTCCT
ACAGGACTGTAGTACCGCCCCATATGTGAGCCAGTCCGTAACATTCTCGCACAGCAGCAG
GCTTTTTCACGAGGGGCCGGCCCTCTAGAGCAGGTGATCTATAAATTAACGACTGTGCTG
TTGTATCCTCACGATCCGCATTAGGGCGATCGGTGCATCCGCGCAAGCATTGGGGCGCGG
CACCATTAGATTTAGCGTGGAGGCGGTTAGTCTAGCCATTGCTTTATGACAGAACTATGG
GAAAAACTTATGCTACGTGTCGTACGCCTAATGGTCTCGGAACGCTCCGCGAAAAAATCC
CTCGCGTCTAACCGTTGCAGGGGAGGGGTGTCACACAATTCTTTCAGCTCCTCAGAAGAG
GTAGTTATTAGACTGTTCTCGGACGTGTCACATAGACGGTCACTAAAAGGAGGCGAACCA
ATTCCCAAATCATAGTAAGAACTGTCAGATGAACACCAATCAGATGCCTAGCTTGACTAT
TGCACTAACCGTAGGATGTCTTAAACACAATCTTGAGGAGTGATCACACCTCAGCCTATT
GACAGTCCCGCTGGCGGGAGAGTATAATTGTGGCAAGGACCCAGCAGGAAGTGGGATTAA
GTCAACTAAGCACAGATGATACTCAAAGATGTCTAATGTGCCTGAACAGCCAGCGCTCTT
CCGGGTAAGGTACATGAGACACCTTCTTTTACACGGGTCATAATTACATTACTCACGATG
CCACCCATGAAATGCCCCCCGTGGCATGGTGGAGCCCACTTGACCAACTGAAAAGGAGTT
TACCATAGCCTCGACCAGCG
>Rosalind_0420
AGCCTGTTTCACATTCGCCCCACTGCTTGCGGACTCTGGTCTAGAGATAACAGAGTCCGC
TAGCTACGCCCAGACACTTCCGCGGCAAAATGCCAGCCCGCATACTAGCGGGTTAGCCCT
ACTGATACCTTGTGCCGCCCTATATGTGAGCCGGTCGATGACCCCCTCGCATAGCAGCAG
ACCTTTTTACGGGAGGGCGACTTTCTGCAGCAGGTGATCTGTAAAGTAGCGATTGTGCTG
TTGCATCCTAGCAATCGACATTAGGGCGATTGCTGCATCCACGGAGGCACTTGGACACTG
CACCATTAGGTTCGGCGTAGAGGTGGTTTTTGTAACCGCTATTCTATAAAAATACTGTGA
TAGGAACTTATTCGATGCGTTGTACGCCTAATGGTCTCGGGACGCCCTGCCGACAAATCC
CTCACACGTAAGCGTTGAAGAGAAAGGACGACACATAACCCCCTCACTTCCTCAGGGAGA
GTAGTTGCTAGATTGCTTTGTGAAAAAGAACCTAATTGGTTGCCTAAAGGAGGAGAAAGA
ACTTTCATGTTCTAGCGAGGACTGTCAGATGAGCAACAATCAGATACTTAGTTAGACTGT
TACGCTAACAGTAGAATATCGTAACCACGATGTTGAGGAGTGAACACACCTCAACATAGT
GGCAGTCCCGTAGACACGAGGGTCTAATTGCGGCGCGCACGGTGTTGGGGGCTAGATTTT
GACACCTATACATAGATAACACTCAAAGATCTCTAATGCGTCTGGACAGCGGGCGTTCTG
CTAGGTAATGTATATGGCGTTCCTACCATAACGCCGGTCGTGATTCCGTTCATCACGATG
TGGCCCGCGGAACGCTGCCCAAGGAATGGTGGGGCCTACCTGGCAACCTGGCGAGGATTT
TACCATAGCGTCACCTAACG
        """.trimIndent().lines()

        val expectedResult = 1.86138613861

        val dnaList = u.utilityParseFASTA(sampleInput)
        assertEquals(2, dnaList.size) // should be two strings
        assertEquals(dnaList[0].length, dnaList[1].length) // of equal langth

        val transitionCount = tt.transitionCount(dnaList).toDouble()
        val transversionCount = tt.transversionsCount(dnaList).toDouble()

        val ratio = transitionCount / transversionCount

//        println(ratio)
//        println("%.11f".format(ratio))
        assertEquals(expectedResult, ratio, 0.001)

    }


}