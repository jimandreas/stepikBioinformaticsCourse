@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.EditDistance

/**
 *
Edit Distance Problem: Find the edit distance between two strings.

Input: Two strings.
Output: The edit distance between these strings.

Code Challenge: Solve the Edit Distance Problem.
These are set up when the class is instantiated.

This routine calls into the GlobalAlignment function to do the string to string work.



 * See also:
 * stepik: @link: https://stepik.org/lesson/240306/step/3?unit=212652
 * rosalind: @link: http://rosalind.info/problems/ba5g/
 * book (5.11):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

internal class S05c11c03EditDistanceTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
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
            GAGA
            GAT
            Output:
            2
        """.trimIndent()

        runTest(sample)
    }

    /*
    * second sample in the Test Dataset
    * This test makes sure that your code correctly adds
    * to the edit distance between the two strings when there are deletions or substitutions.
    */
    @Test
    @DisplayName("local alignment test 02")
    fun localAlignmentTest02() {

        val sample = """
            Input:
            AT
            G
            Output:
            2
        """.trimIndent()

        runTest(sample)
    }

    /*
     * TEST DATASET 3:
     * This test makes sure that your code correctly handles inputs
     * in which the strings to be compared drastically differ in length.
     */
    @Test
    @DisplayName("local alignment test 03")
    fun localAlignmentTest03() {

        val sample = """
            Input:
            CAGACCGAGTTAG
            CGG
            Output:
            10
        """.trimIndent()

        runTest(sample)
    }

    /*
     * TEST DATASET 4:
     * This test makes sure that your code can handle inputs in
     * which the strings vary drastically in length.
     */

    @Test
    @DisplayName("local alignment test 04")
    fun localAlignmentTest04() {

        val sample = """
            Input:
            CGT
            CAGACGGTGACG
            Output:
            9
        """.trimIndent()

        runTest(sample)
    }

    /*
SAMPLE PROBLEM
     */
    @Test
    @DisplayName("local alignment sample")
    fun localAlignmentSampleTest() {

        val sample = """
            Input:
            PLEASANTLY
            MEANLY
            Output:
            5
        """.trimIndent()

        runTest(sample)
    }


    fun runTest(sample: String, usePAM250: Boolean = true) {
        val reader = sample.reader()
        val lines = reader.readLines()

        val sRow = lines[1]
        val tCol = lines[2]

        val expectedEditDistance = lines[4].toInt()

        val match = 0
        val mismatch = 1
        val gap = 1

        val ed = EditDistance(match, mismatch, gap, useBLOSUM62 = false)

        val resultEditDistance = ed.calcEditDistance(sRow, tCol)
//       println(resultEditDistance)
        assertEquals(expectedEditDistance, -resultEditDistance)

    }

}