@file:Suppress("UNUSED_VARIABLE", "UnnecessaryVariable")

import org.junit.jupiter.api.*

import util.*
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
     * test of utility function to return the top M elements of a map of elements and their multiplicity
     */
    @Test
    @DisplayName("test of topM function - top M elements of a map of elements and their multiplicity")
    fun testTopMFunction() {
        val testMap: Map<Int, Int> = mapOf(
            57 to 1,   // only one - 57
            58 to 3,   // have three - 58's
            200 to 2   // have two - 200's

        )

        // first test - not enough elements in map
        val result = topM(4, testMap)  // should hit upper limit of entries
        val expectedResult = listOf(57, 58, 200) // results are sorted
        assertEquals(expectedResult, result)

        // check for return of correct shortened list
        val result2 = topM(2, testMap)
        val expectedResult2 = listOf(58, 200) // should not include 57
        assertEquals(expectedResult2, result2)
    }

}