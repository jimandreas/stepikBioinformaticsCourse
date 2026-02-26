@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate")

import algorithms.LongestCommonSubsequenceLCS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

/**
 * Code Challenge: Find the length of a longest path in the Manhattan Tourist Problem.
 *
 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba5b/
 * book (5.6):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

internal class S05c08p05LongestCommonSubsequenceTest {

    val lcs = LongestCommonSubsequenceLCS()


    @Test
    @DisplayName("longest common subsequence - backtrack test")
    fun longestCommonSubsequenceBacktrackTest() {

        val result = lcs.backtrack("AC", "ABC")
        assertNotNull(result)


    }

    @Test
    @DisplayName("longest common subsequence LCS test")
    fun longestCommonSubsequenceLCSTest() {

        val result = lcs.backtrack("ABC", "DBC")
        assertNotNull(result)

        val str = StringBuilder()
        val lcsString = lcs.outputLCS(result, "ABC", 3, 3, str)

        val expectedResult = "BC"
        assertEquals(expectedResult, lcsString.toString())
    }

    @Test
    @DisplayName("longest common subsequence LCS test 02")
    fun longestCommonSubsequenceLCSTest02() {

        val sCol = "AAC"
        val sRow = "ATA"
        val result = lcs.backtrack(sCol, sRow)
        assertNotNull(result)

        val str = StringBuilder()
        val lcsString = lcs.outputLCS(result, sCol, sCol.length, sRow.length, str)

        val expectedResult = "AA"
        assertEquals(expectedResult, lcsString.toString())

    }

    @Test
    @DisplayName("longest common subsequence LCS test 03")
    fun longestCommonSubsequenceLCSTest03() {

        val sCol = "AAABC"
        val sRow = "BCBCD"
        val result = lcs.backtrack(sCol, sRow)
        assertNotNull(result)

        val str = StringBuilder()
        val lcsString = lcs.outputLCS(result, sCol, sCol.length, sRow.length, str)

        val expectedResult = "BC"
        assertEquals(expectedResult, lcsString.toString())

    }

}