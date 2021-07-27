@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.AlignmentGlobal

/**
Code Challenge: Solve the Global Alignment Problem.

Input: Two protein strings written in the single-letter amino acid alphabet.
Output: The maximum alignment score of these strings followed by an alignment
achieving this maximum score. Use the BLOSUM62 scoring matrix for matches and
mismatches as well as the indel penalty σ = 5.
 *
 * See also:
 * stepik: @link: https://stepik.org/lesson/240305/step/3?unit=212651
 * rosalind: @link: http://rosalind.info/problems/ba5e/
 * book (5.10):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

internal class S05C10C03AlignmentGlobalTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }


    /**
     * Test the scoring function - this checks to make sure the BLOSUM62
     * match matrix is set up for those tests that use it.
     */

    @Test
    @DisplayName("global alignment - matrix input test")
    fun globalAlignmentMatrixInputTest() {

        val ga = AlignmentGlobal(0, 0, 0, useBLOSUM62 = true)
        val test1 = ga.score('Y', 'Y')
        assertEquals(7, test1)

        val test2 = ga.score('A', 'A')
        assertEquals(4, test2)
    }

    /*
     * VERY basic test.
     */
    @Test
    @DisplayName("global alignment test 01")
    fun globalAlignmentTest01() {

//        val sCol = "A"
//        val sRow = "A"
//        val resultMatrix = ga.backtrack(sCol, sRow)
//
//        val str = StringBuilder()
//        val lcsStringBuilderString = ga.outputLCS(resultMatrix, sCol, sCol.length, sRow.length, str)
//
//        val max = ga.maxCellVal
//        assertEquals(4, max) // value in BLOSSUM62 table for A=A
//
//        val result = lcsStringBuilderString.toString()
//        val expectedResult = "A"
//        assertEquals(expectedResult, result)
    }

    /*
     * verify corners of the BLOSUM62 matrix (A=A, Y=Y)
     */
    @Test
    @DisplayName("global alignment test 02")
    fun globalAlignmentTest02() {

//        val sCol = "AY"
//        val sRow = "AY"
//        val resultMatrix = ga.backtrack(sCol, sRow)
//
//        val str = StringBuilder()
//        val lcsStringBuilderString = ga.outputLCS(resultMatrix, sCol, sCol.length, sRow.length, str)
//
//        val max = ga.maxCellVal
//        assertEquals(11, max) // value in BLOSSUM62 table for A=A + Y=Y
//
//        val result = lcsStringBuilderString.toString()
//        val expectedResult = "AY"
//        assertEquals(expectedResult, result)
    }

    /*
     * first sample in the Test Dataset
     */
    @Test
    @DisplayName("global alignment test 03")
    fun globalAlignmentTest03() {

        val sample = """
            Input:
            1 1 2
            GAGA
            GAT
            Output:
            -1
            GAGA
            GA-T
        """.trimIndent()

        runTest(sample)
    }

    /*
    * second sample in the Test Dataset
    */
    @Test
    @DisplayName("global alignment test 04")
    fun globalAlignmentTest04() {

        val sample = """
            Input:
            1 3 1
            ACG
            ACT
            Output:
            0
            AC-G
            ACT-
        """.trimIndent()

        runTest(sample)
    }

    @Test
    @DisplayName("global alignment test 05")
    fun globalAlignmentTest05() {

        val sample = """
            Input:
            1 1 1
            AT
            AG
            Output:
            0
            AT
            AG
        """.trimIndent()

        runTest(sample)
    }

    /**
     * 	This test makes sure that your code allows for an output
     * 	beginning with an indel. If your code doesn’t make use of
     * 	the base cases (first row and column of the dynamic programming matrix)
     * 	scores then the correct score of 3 cannot be found.
     * 	Be sure to correctly fill out your bases cases and consider them in your recursive cases.
     */

    @Test
    @DisplayName("global alignment test 06")
    fun globalAlignmentTest06() {

        val sample = """
            Input:
            2 5 1
            TCA
            CA
            Output:
            3
            TCA
            -CA
        """.trimIndent()

        runTest(sample)
    }

    /**
     * This test makes sure that your code can handle multiple indels in a row
     */
    @Test
    @DisplayName("global alignment test 07")
    fun globalAlignmentTest07() {

        val sample = """
            Input:
            1 10 1
            TTTTCCTT
            CC
            Output:
            -4
            TTTTCCTT
            ----CC--
        """.trimIndent()

        runTest(sample)
    }

    /**
     * This test makes sure that your code can handles inputs
     * in which the two strings differ drastically in length.
     */
    @Test
    @DisplayName("global alignment test 08")
    fun globalAlignmentTest08() {

        val sample = """
            Input:
            2 3 2
            ACAGATTAG
            T
            Output:
            -14
            ACAGATTAG
            ------T--
        """.trimIndent()

        runTest(sample)
    }

    /**
     * This test makes sure that your code can handles
     * inputs in which the two strings differ drastically in length.
     * This dataset is similar to the previous dataset
     * except in this dataset string s is much shorter than string t instead of vice-versa.
     * NOTE:
     * original expected:             ------G---
     */
    @Test
    @DisplayName("global alignment test 09")
    fun globalAlignmentTest09() {

        val sample = """
            Input:
            3 1 2
            G
            ACATACGATG
            Output:
            -15
            ---------G
            ACATACGATG
        """.trimIndent()

        runTest(sample)
    }

    fun runTest(sample: String) {
        val reader = sample.reader()
        val lines = reader.readLines()
        val parms = lines[1].split(" ").map { it.toInt() }
        val match = parms[0]
        val mismatch = parms[1]
        val gap = parms[2]

        val ga = AlignmentGlobal(match, mismatch, gap)
        val sRow = lines[2]
        val tCol = lines[3]

        val scoreExpected = lines[5].toInt()
        val sRowAlignedExpected = lines[6]
        val tColAlignedExpected = lines[7]

        val result = ga.globalAlignment(sRow, tCol)

        val scoreResult = result.first
        val sRowResult = result.second
        val tColResult = result.third
        assertEquals(scoreExpected, scoreResult)
        assertEquals(sRowAlignedExpected, sRowResult)
        assertEquals(tColAlignedExpected, tColResult)
    }

    /*
    * the sample problem
    * @link: http://rosalind.info/problems/ba5e/
    * Note that the answer differs from that given in the problem statement.
    */
    @Test
    @DisplayName("global alignment test sample")
    fun globalAlignmentTestSample() {

        val sRow = "PLEASANTLY"
        val tCol = "MEANLY"

        val ga = AlignmentGlobal(0, 0, 5, useBLOSUM62 = true)
        val result = ga.globalAlignment(sRow, tCol)

        val scoreResult = result.first
        val sRowResult = result.second
        val tColResult = result.third

        val expectedScoreResult = 8
        assertEquals(expectedScoreResult, scoreResult)
        val expectedtColResult = "-ME--AN-LY"
        assertEquals(expectedtColResult, tColResult)
    }

    /*
    * the sample problem
    * @link: http://rosalind.info/problems/ba5e/
    * Note that the answer differs from that given in the problem statement.
    */
    @Test
    @DisplayName("Test for future Space-Efficient Sequence Alignment sample")
    fun globalSpaceEfficientBackTestSample() {

        val sRow = "PLEASANTLY"
        val tCol = "MEASNLY"

        /**
         * PLEASANTLY
         * -MEAS-N-LY
         */

        val ga = AlignmentGlobal(0, 0, 5, useBLOSUM62 = true)

        val result = ga.globalAlignment(sRow, tCol)
        val scoreResult = result.first
        val sRowResult = result.second
        val tColResult = result.third

        val resultR = ga.globalAlignment(sRow.reversed(), tCol.reversed())
        val scoreResultR = resultR.first
        val sRowResultR = resultR.second.reversed()
        val tColResultR = resultR.third.reversed()

        println(sRowResult)
        println(sRowResultR)
        println(tColResult)
        println(tColResultR)

    }

}