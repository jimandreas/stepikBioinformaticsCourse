@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.AlignmentOverlap
import kotlin.test.Ignore

/**

Code Challenge: Solve the Overlap Alignment Problem.

Input: Two strings v and w, each of length at most 1000.

Output: The score of an optimal overlap alignment of v and w,
followed by an alignment of a suffix v' of v and a prefix w' of w
achieving this maximum score. Use an alignment score in which
matches count +1 and both the mismatch and indel penalties are 2.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240306/step/7?unit=212652
 * rosalind: @link: http://rosalind.info/problems/ba5i/
 * book (5.11):  http://rosalind.info/problems/ba5f/
 */

internal class S05C11C07AlignmentOverlapTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * first sample in the Test Dataset
     * This test makes sure that your dynamic programming matrix is correctly initialized.
     */
    @Test
    @DisplayName("overlap alignment test 01")
    fun overlapAlignmentTest01() {

        val sample = """
            Input:
            1 1 1
            CCAT
            AT
            Output:
            2
            AT
            AT
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 2:
     * This test makes sure that your dynamic programming matrix is
     * correctly penalizing indels in string s.
     * Gaps at the beginning of a suffix of string s must be penalized.
     */
    @Test
    @DisplayName("overlap alignment test 02")
    fun overlapAlignmentTest02() {

        val sample = """
            Input:
            1 5 1
            GAT
            CAT
            Output:
            1
            -AT
            CAT
        """.trimIndent()

        runTest(sample)
    }

    /**
     * This test makes sure that your code isn’t mistakenly implementing
     * fitting or overlap alignment. Both fitting and overlap alignment would
     * output a score of 2, since the first AT of string s will be a perfect
     * match for string t. However, in overlap alignment a suffix of string s
     * must be aligned with a prefix of string t. Therefore the ideal overlap
     * alignment is different than an ideal overlap or fitting alignment for this dataset.
     */
    @Test
    @DisplayName("overlap alignment test 03")
    fun overlapAlignmentTest03() {

        val sample = """
            Input:
            1 5 1
            ATCACT
            AT
            Output:
            1
            ACT
            A-T
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 4:
     * This test makes sure that your code correctly ignores
     * characters at the end of string t if that results in a
     * better alignment score. In overlap alignment only the
     * prefix of string t must be aligned. Adding the G character
     * to the alignment will only hurt the score, so it is not
     * used in an ideal overlap alignment of this dataset.
     */

    @Test
    @DisplayName("overlap alignment test 04")
    fun overlapAlignmentTest04() {

        val sample = """
            Input:
            1 1 5
            ATCACT
            ATG
            Output:
            0
            CT
            AT
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 5:
     * This test makes sure that your code can handle
     * inputs in which the strings vary drastically in length.
     */

    @Test
    @DisplayName("overlap alignment test 05")
    fun overlapAlignmentTest05() {

        val sample = """
            Input:
            3 2 1
            CAGAGATGGCCG
            ACG
            Output:
            5
            -CG
            ACG
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 6:
     * This dataset checks that your code can handle
     * inputs in which the two strings to be aligned are different lengths.
     */

    @Test
    @Ignore
    @DisplayName("overlap alignment test 06")
    fun overlapAlignmentTest06() {

        val sample = """
            Input:
            2 3 1
            CTT
            AGCATAAAGCATT
            Output:
            0
            --CT-T
            AGC-AT
        """.trimIndent()

        runTest(sample)
    }

    /**
        SAMPLE PROBLEM
     */
    @Test
    @DisplayName("sample dataset from word file")
    fun overlapAlignmentSampleTest() {

        val sample = """
            Input:
            1 1 2
            GAGA
            GAT
            Output:
            2
            GA
            GA
        """.trimIndent()

        runTest(sample)
    }


    fun runTest(sample: String, usePAM250: Boolean = true) {
        val reader = sample.reader()
        val lines = reader.readLines()
        val parms = lines[1].split(" ").map { it.toInt() }
        val match = parms[0]
        val mismatch = parms[1]
        val gap = parms[2]

        val oa = AlignmentOverlap(match, mismatch, gap)
        val sRow = lines[2]
        val tCol = lines[3]

        val result = oa.overlapAlignment(sRow, tCol)

        val sRowAlignedExpected = lines[6]
        val tColAlignedExpected = lines[7]
        val sRowResult = result.second
        val tColResult = result.third

        println("RowE: $sRowAlignedExpected")
        println("RowR: $sRowResult")
        println("ColE: $tColAlignedExpected")
        println("ColR: $tColResult")

        val scoreExpected = lines[5].toInt()
        val scoreResult = result.first

        assertEquals(sRowAlignedExpected, sRowResult)
        assertEquals(tColAlignedExpected, tColResult)

        assertEquals(scoreExpected, scoreResult)


    }

}