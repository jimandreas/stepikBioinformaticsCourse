@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.LocalAlignment

/**
 * Code Challenge: Solve the Local Alignment Problem.

Input: Two protein strings written in the single-letter amino acid alphabet.

Output: The maximum score of a local alignment of the strings,
followed by a local alignment of these strings achieving the maximum score.
Use the PAM250 scoring matrix for matches and mismatches as well as the indel penalty σ = 5.

Note: this could be combined with the Local Alignment code.  Most of the code change
occurs in the outputLCS function.  But in the interests of clarity and reduction of complexity,
the changes to find the maximum score will occur only here.

MODIFIED: to accept:
A match score m, a mismatch penalty μ, a gap penalty σ

These are set up when the class is instantiated.

A modal flag indicates whether to use the PAM250 match/mismatch matrix.

The changes involve using the scoring matrix to calculate the winning value
for each cell.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240305/step/10?unit=212651
 * rosalind: @link: http://rosalind.info/problems/ba5e/
 * book (5.10):  http://rosalind.info/problems/ba5f/
 */

internal class S05c09c10LocalAlignmentTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }


    /**
     * Test the scoring function - this checks to make sure the PAM250
     * match matrix is set up for those tests that use it.
     */

    @Test
    @DisplayName("local alignment - matrix input test")
    fun localAlignmentMatrixInputTest() {

        val la = LocalAlignment(0, 0, 0, usePAM250 = true)
        val test1 = la.score('Y', 'Y')
        assertEquals(10, test1)

        val test2 = la.score('A', 'A')
        assertEquals(2, test2)
    }

    /*
     * first sample in the Test Dataset
     * This test makes sure that your code correctly parses the first line of input and uses the correct penalties.
     */
    @Test
    @DisplayName("local alignment test 01")
    fun localAlignmentTest01() {

        val sample = """
            Input:
            3 3 1
            AGC
            ATC
            Output:
            4
            A-GC
            AT-C
        """.trimIndent()

        runTest(sample)
    }

    /*
    * second sample in the Test Dataset
    * This test makes sure that the mismatch penalties are being correctly applied.
    */
    @Test
    @DisplayName("local alignment test 02")
    fun localAlignmentTest02() {

        val sample = """
            Input:
            1 1 1
            AT
            AG
            Output:
            1
            A
            A
        """.trimIndent()

        runTest(sample)
    }

    /*
     * TEST DATASET 3:
     * This test makes sure that your code can correctly
     * find the highest scoring alignment, wherever it is in the dynamic programming matrix.
     */
    @Test
    @DisplayName("local alignment test 03")
    fun localAlignmentTest03() {

        val sample = """
            Input:
            1 1 1
            TAACG
            ACGTG
            Output:
            3
            ACG
            ACG
        """.trimIndent()

        runTest(sample)
    }

    /**
     * This test makes sure that your code can handle inputs in
     * which the strings vary drastically in length.
     */

    @Test
    @DisplayName("local alignment test 04")
    fun localAlignmentTest04() {

        val sample = """
            Input:
            3 2 1
            CAGAGATGGCCG
            ACG
            Output:
            6
            CG
            CG
        """.trimIndent()

        runTest(sample)
    }

    /**
     * This dataset checks that your code can handle inputs
     * in which the two strings to be aligned are different lengths.
     */
    @Test
    @DisplayName("local alignment test 05")
    fun localAlignmentTest05() {

        val sample = """
            Input:
            2 3 1
            CTT
            AGCATAAAGCATT
            Output:
            5
            C-TT
            CATT
        """.trimIndent()

        runTest(sample)
    }



    fun runTest(sample: String, usePAM250: Boolean = false) {
        val reader = sample.reader()
        val lines = reader.readLines()
        val parms = lines[1].split(" ").map { it.toInt() }
        val match = parms[0]
        val mismatch = parms[1]
        val gap = parms[2]

        val la = LocalAlignment(match, mismatch, gap, usePAM250)
        val sRow = lines[2]
        val tCol = lines[3]

        val scoreExpected = lines[5].toInt()
        val sRowAlignedExpected = lines[6]
        val tColAlignedExpected = lines[7]

        val result = la.localAlignment(sRow, tCol)

        val scoreResult = result.first
        val sRowResult = result.second
        val tColResult = result.third
        assertEquals(scoreExpected, scoreResult)
        assertEquals(sRowAlignedExpected, sRowResult)
        assertEquals(tColAlignedExpected, tColResult)
    }

    /*
    * the sample problem
    */
    @Test
    @DisplayName("local alignment test 99")
    fun localAlignmentTest99() {

        val sample = """
            Sample Input:
            0 0 5
            MEANLY
            PENALTY
            Sample Output:
            15
            EANL-Y
            ENALTY
        """.trimIndent()

        runTest(sample, usePAM250 = true)
    }

}