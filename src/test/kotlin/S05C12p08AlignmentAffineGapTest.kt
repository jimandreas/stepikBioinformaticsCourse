@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.AlignmentAffineGap

/**
 * Code Challenge: Solve the Alignment with Affine Gap Penalties Problem.

Input: Two amino acid strings v and w (each of length at most 100).

Output: The maximum alignment score between v and w, followed by an
alignment of v and w achieving this maximum score.
Use the BLOSUM62 scoring matrix, a gap opening penalty of 11, and a gap extension penalty of 1.

Note: this is a modified version of the AlignmentGlobal code.

A modal flag indicates whether to use the BLOSUM62 match/mismatch matrix.

The changes involve using the scoring matrix to calculate the winning value
for each cell.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240307/step/8?unit=212653
 * rosalind: @link: http://rosalind.info/problems/ba5j/
 * book (5.12): @link: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 * youtube: @link: https://www.youtube.com/watch?v=Npv180dQ_4Y
 */

internal class S05C12p08AlignmentAffineGapTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * first sample in the Test Dataset
     * This test makes sure that your code is correctly parsing the gap opening and gap extension penalties.
     */
    @Test
    @DisplayName("affine alignment test 01")
    fun affineAlignmentTest01() {

        val sample = """
            Input:
            1 5 3 1
            TTT
            TT
            Output:
            -1
            TTT
            TT-
            
            was expected
            TTT	OR	TTT	OR	TTT
            -TT		T-T		-TT
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 2:
     * This test makes sure that your code is implementing
     * global alignment with affine gap penalties instead of
     * fitting, overlap, or local alignment with affine gap
     * penalties. All other types of alignment will simply
     * align the two AT substrings and report a score of 2.
     */
    @Test
    @DisplayName("affine alignment test 02")
    fun affineAlignmentTest02() {

        val sample = """
            Input:
            1 5 5 1
            GAT
            AT
            Output:
            -3
            GAT
            -AT
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 3:
     * This test makes sure that your upper and lower matrices
     * are correctly initialized. Be especially careful about your
     * initialization of the first row of the lower matrix and the
     * first column of the upper matrix. Depending on your
     * backtracking implementation it is possible that you will
     * get the correct alignment reported despite having an
     * incorrect score. This is likely due to an issue in your
     * initialization of the upper and lower matrices. If you
     * consistently get a score of -2 instead of the correct -3
     * it is possible that your code is incorrectly considering
     * gaps that span across the two strings as one gap.
     * This is not the case; there should be two gap opening
     * penalties in the alignment for this dataset.
     */
    @Test
    @DisplayName("affine alignment test 03")
    fun affineAlignmentTest03() {

        val sample = """
            Input:
            1 5 2 1
            CCAT
            GAT
            Output:
            -3
            -CCAT
            G--AT
            
            expected from word doc:
            -CCAT		OR	CC-AT
            G--AT			--GAT

        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 4:
     * This test makes sure that your code can handle a
     * gap extension penalty that isn’t equal to one.
     * If your output doesn’t match the correct output it’s
     * likely that your implementation relies on the gap
     * extension penalty being equal to one. Since all
     * previous datasets set the gap extension penalty
     * to one your code could have passed all previous
     * tests without properly using the input to set the
     * gap extension penalty.
     */

    @Test
    @DisplayName("affine alignment test 04")
    fun affineAlignmentTest04() {

        val sample = """
            Input:
            1 2 3 2
            CAGGT
            TAC
            Output:
            -8
            CAGGT
            TAC--

        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 5:
     * This test makes sure that your code can handle inputs
     * in which the two strings are the same length.
     * If your output doesn’t match the correct output make
     * sure that your code doesn’t make any assumptions
     * about the lengths of the input strings. Since no
     * previous dataset contained two strings with the same
     * length your implementation could have passed all
     * previous tests without handling the case where
     * the two strings are the same length.
     */

    @Test
    @DisplayName("affine alignment test 05")
    fun affineAlignmentTest05() {

        val sample = """
            Input:
            2 3 3 2
            GTTCCAGGTA
            CAGTAGTCGT
            Output:
            -8
            --GT--TCCAGGTA
            CAGTAGTC---GT-

        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 6:
     * This test makes sure that your code can handle inputs
     * in which the strings vary drastically in length. If your
     * output doesn’t match the correct output make sure that
     * your implementation doesn’t make any assumptions
     * about the lengths of the strings. Make sure that your
     * three dynamic programming matrices are assigned the
     * correct dimensions given the input strings.
     */

    @Test
    @DisplayName("affine alignment test 06")
    fun affineAlignmentTest06() {

        val sample = """
            Input:
            1 3 1 1
            AGCTAGCCTAG
            GT
            Output:
            -7
            AGCTAGCCTAG
            -G-T-------
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 7:
     * This dataset checks that your code can handle inputs in
     * which the two strings to be aligned are different lengths.
     * This dataset is similar to Test Dataset 6 except that in
     * this dataset string s is shorter than string t.
     */

    @Test
    @DisplayName("affine alignment test 07")
    fun affineAlignmentTest07() {

        val sample = """
            Input:
            2 1 2 1
            AA
            CAGTGTCAGTA
            Output:
            -7
            -A--------A
            CAGTGTCAGTA
        """.trimIndent()
//        -------A--A  (original)
        runTest(sample)
    }

    /**
     * TEST DATASET 8:
     * This dataset checks that your code is actually using
     * three distinct matrices to reconstruct the alignment.
     * It may be tempting to reconstruct the alignment using
     * only the middle matrix but that could lead to subtle errors.
     * If the last A character in string s was not present the
     * ideal alignment would match the T characters.
     * Once the last A character is added mismatching the T
     * in string t with the G in string s yields a higher final score.
     * If your implementation only uses one matrix then you are likely
     * assuming that knowing if a gap is being initialized or
     * extended is sufficient for scoring.
     */

    @Test
    @DisplayName("affine alignment test 08")
    fun affineAlignmentTest08() {

        val sample = """
            Input:
            5 2 15 5
            ACGTA
            ACT
            Output:
            -12
            ACGTA
            ACT--
        """.trimIndent()

        runTest(sample)
    }

    /**
        SAMPLE PROBLEM
     */
    @Test
    @DisplayName("sample dataset from word file")
    fun affineAlignmentSampleTest() {

        val sample = """
            Input:
            1 3 2 1
            GA
            GTTA
            Output:
            -1
            G--A
            GTTA
        """.trimIndent()

        runTest(sample)
    }

    /**
    EXTRA DATASET PROBLEM
     */
    @Test
    @DisplayName("sample dataset extra")
    fun affineAlignmentExtraTest() {

        val sample = """
Input:
0 0 11 1
YHFDVPDCWAHRYWVENPQAIAQMEQICFNWFPSMMMKQPHVFKVDHHMSCRWLPIRGKKCSSCCTRMRVRTVWE
YHEDVAHEDAIAQMVNTFGFVWQICLNQFPSMMMKIYWIAVLSAHVADRKTWSKHMSCRWLPIISATCARMRVRTVWE
Output:
144
YHFDVPDCWAHRYWVENPQAIAQME-------QICFNWFPSMMMK-------QPHVFKV---DHHMSCRWLPIRGKKCSSCCTRMRVRTVWE
YHEDV----AHE------DAIAQMVNTFGFVWQICLNQFPSMMMKIYWIAVLSAHVADRKTWSKHMSCRWLPI----ISATCARMRVRTVWE
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
        val epsilonGapExtension = parms[3]

        val aag = AlignmentAffineGap(match, mismatch, gap, epsilonGapExtension, useBLOSUM62)

        val sRow = lines[2]
        val tCol = lines[3]
        val result = aag.affineAlignment(sRow, tCol)
//        val result = aag.affineAlignment(tCol, sRow)

        val sRowExpected = lines[6]
        val tColExpected = lines[7]
        val sRowResult = result.second
        val tColResult = result.third
        val scoreExpected = lines[5].toInt()
        val scoreResult = result.first

////       println("RowE: $sRowExpected")
////       println("RowR: $sRowResult")
////       println("ColE: $tColExpected")
////       println("ColR: $tColResult")
////       println("ScoreE: $scoreExpected")
////       println("ScoreR: $scoreResult")

        assertEquals(sRowExpected, sRowResult)
        assertEquals(tColExpected, tColResult)
        assertEquals(scoreExpected, scoreResult)


    }

}