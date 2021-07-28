@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.AlignmentLinearSpaceMiddleEdge
import util.MiddleEdgeLinearSpace

/**
 * Code Challenge: Solve the Middle Edge in Linear Space Problem (for protein strings).

Input: Two amino acid strings.
Output: A middle edge in the alignment graph in the form "(i, j) (k, l)", where (i, j) connects to (k, l).

 * See also:
 * stepik: @link:
 * https://stepik.org/lesson/240308/step/12?unit=212654  MiddleEdge problem
 * https://stepik.org/lesson/240308/step/14?unit=212654  LinearSpaceAlignment
 * rosalind: @link:
 * http://rosalind.info/problems/ba5k/  MiddleEdge
 * http://rosalind.info/problems/ba5l/  LinearSpaceAlignment
 * book (5.13): @link: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 * youtube: @link: https://www.youtube.com/watch?v=3TfDm8GpWRU  Space-Efficient Sequence Alignment
 */

internal class S05C13c12FindMiddleEdgeTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * TEST DATASET 1:
     * This test makes sure that your code can identify horizontal middle edges.
     */
    @Test
    @DisplayName("middle edge test 01")
    fun findMiddleEdgeTest01() {

        val sample = """
            Input:
            1 5 1
            TTTT
            CC
            Output:
            (0, 2) (0, 3)
            
            was original:
            (0, 2) (0, 3)	OR	(1, 2) (1, 3)
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 2:
     * This test makes sure that your code can handle finding the middle edge when the first string has an odd length.
     */
    @Test
    @DisplayName("middle edge test 02")
    fun findMiddleEdgeTest02() {

        val sample = """
            Input:
            1 1 2
            GAT
            AT
            Output:
            (0, 1) (1, 2)
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 3:
     * This test makes sure that your code can identify vertical middle edges.
     */
    @Test
    @DisplayName("middle edge test 03")
    fun findMiddleEdgeTest03() {

        val sample = """
            Input:
            1 1 1
            TTTT
            TTCTT
            Output:
            (3, 2) (4, 3)
            
            was original:
            (2, 2) (3, 2)	OR	(3, 2) (4, 3) 
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 4:
     * This test makes sure that your code correctly identifies the middle edge
     * when the maximum value in Length is the last value.
     */

    @Test
    @DisplayName("middle edge test 04")
    fun findMiddleEdgeTest04() {

        val sample = """
            Input:
            1 5 1
            GAACCC
            G
            Output:
            (1, 3) (1, 4)
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 5:
     * This test makes sure that your code correctly handle inputs in which
     * the match score is not equal to one.
     */

    @Test
    @DisplayName("middle edge test 05")
    fun findMiddleEdgeTest05() {

        val sample = """
            Input:
            2 3 1
            ACAGT
            CAT
            Output:
            (1, 2) (2, 3)


        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 6:
     * This test makes sure that your code correctly handle inputs
     * where the length of string s is equal to one.
     */

    @Test
    @DisplayName("middle edge test 06")
    fun findMiddleEdgeTest06() {

        val sample = """
            Input:
            2 5 3
            T
            AATCCC
            Output:
            (0, 0) (1, 0)
            
            original was:
            (0, 0) (1, 0)	OR	(1, 0) (2, 0)	OR	(2, 0) (3, 1)

        """.trimIndent()

        runTest(sample)
    }


    /**
     * SAMPLE PROBLEM
     */
    @Test
    @DisplayName("sample dataset from word file")
    fun findMiddleEdgeSampleTest() {

        val sample = """
            Input:
            1 1 2
            GAGA
            GAT
            Output:
            (2, 2) (2, 3)

        """.trimIndent()

        runTest(sample)
    }

    /**
    SAMPLE GIVEN DATASET PROBLEM

     Note:
     global alignment gives this solution
     * PLEASANTLY
     * -MEAS-N-LY

     */
    @Test
    @DisplayName("sample dataset extra")
    fun findMiddleEdgeExtraTest() {

        val sample = """
            Input:
            0 0 5
            MEASNLY
            PLEASANTLY
            Output:
            (4, 3) (5, 4)
        """.trimIndent()

        runTest(sample, true)
    }

    fun runTest(sample: String, useBLOSUM62: Boolean = false) {
        val reader = sample.reader()
        val lines = reader.readLines()
        val parms = lines[1].split(" ").map { it.toInt() }
        val match = parms[0]
        val mismatch = parms[1]
        val gap = parms[2]

        val alsme = MiddleEdgeLinearSpace(match, mismatch, gap, useBLOSUM62)

        val sRow = lines[2]
        val tCol = lines[3]
        val result = alsme.findMiddleEdge(sRow, tCol)

        val pairsExpected = lines[5]

        val pair1 = result.first.toList().joinToString(", ")
        val pair2 = result.second.toList().joinToString(", ")
        val resultString = "($pair1) ($pair2)"
        println(resultString)

        assertEquals(pairsExpected, resultString)

    }

}