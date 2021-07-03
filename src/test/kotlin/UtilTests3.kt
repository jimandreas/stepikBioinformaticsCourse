@file:Suppress("UNUSED_VARIABLE")

import org.junit.jupiter.api.*

import util.*
import java.lang.RuntimeException
import kotlin.math.exp
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class UtilTests3 {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * The String Spelled by a Genome Path Problem.
     * first test - the denerate case of single character input
     */
    @Test
    @DisplayName("util: path to string test 01")
    fun testPathToString01() {
        val inputString = listOf("A", "B", "C")
        val result = pathToGenome(inputString)

        val expectedResult = "ABC"
        assertEquals(expectedResult, result)

    }

    /**
     * The String Spelled by a Genome Path Problem.
     * first test - the denerate case of single character input
     */
    @Test
    @DisplayName("util: path to string test 02")
    fun testPathToString02() {
        val inputString = listOf("AB", "BC", "CD")
        val result = pathToGenome(inputString)

        val expectedResult = "ABCD"
        assertEquals(expectedResult, result)
    }

    /**
     * basic test of prefix and suffix operations on ReadPair
     */

    @Test
    @DisplayName("util: test prefix and suffix for ReadPair")
    fun testReadPair() {
        val testPair = ReadPair("1234", "5678")

        val pre = testPair.prefix()
        assertEquals(pre, ReadPair("123", "567"))

        val suffix = testPair.suffix()
        assertEquals(suffix, ReadPair("234", "678"))
    }

    /**
     *  basic test on reassembling a string based on a list of ReadPairs
     */
    @Test
    @DisplayName("util: test reassembling strings from a list of ReadPairs")
    fun testReassembleString() {
        val k = 2 // kmer length
        val d = 1 // gap length
        val pairList = listOf(
            ReadPair("AB", "DE"),
            ReadPair("BC", "EF")
        )
        val result = reassembleStringFromPairs(k, d, pairList)
        assertEquals("ABCDEF", result)
    }

    /**
     *  basic test on reassembling a string based on a list of ReadPairs
     */
    @Test
    @DisplayName("util: test reassembling strings from a list of ReadPairs 02")
    fun testReassembleString02() {
        val k = 3 // kmer length
        val d = 2 // gap length
        val pairList = listOf(
            ReadPair("ABC", "FGH"),
            ReadPair("BCD", "GHI"),
            ReadPair("CDE", "HIJ")
        )
        val result = reassembleStringFromPairs(k, d, pairList)
        assertEquals("ABCDEFGHIJ", result)
    }

    /**
     *  basic test on reassembling a string based on a list of ReadPairs
     */
    @Test
    @DisplayName("util: test reassembling strings from a list of ReadPairs 03")
    fun testReassembleString03() {
        val k = 2 // kmer length
        val d = 2 // gap length
        val pairList = listOf(
            ReadPair("AB", "EF"),
            ReadPair("BC", "FG"),
            ReadPair("CD", "GH"),
            ReadPair("DE", "HI")
        )
        val result = reassembleStringFromPairs(k, d, pairList)
        assertEquals("ABCDEFGHI", result)
    }

    /**
     * Code Challenge: Implement LinearSpectrum.

    Input: An amino acid string Peptide.
    Output: The linear spectrum of Peptide.

    Extra Dataset

    Sample Input: NQEL

    Sample Output:
    0 113 114 128 129 242 242 257 370 371 484
     */
    @Test
    @DisplayName("util: test linearSpectrum function 01")
    fun testLinearSpectrumFunction01() {
        val peptide = "NQEL"
        val expectedResult = listOf(0,113,114,128,129,242,242,257,370,371,484)

        val result = linearSpectrum(peptide)
        assertEquals(expectedResult, result)
    }


}