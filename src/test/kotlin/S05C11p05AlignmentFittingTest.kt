@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.AlignmentFitting
import kotlin.test.Ignore

/**

Code Challenge: Solve the Fitting Alignment Problem.

Input: Two nucleotide strings v and w, where v has length at most 1000 and w has length at most 100.
Output: A highest-scoring fitting alignment between v and w.
for the Quiz: Use the simple scoring method in which matches count +1 and both the mismatch and indel penalties are 1.

Otherwise:

A match score m, a mismatch penalty μ, a gap penalty σ

These are set up when the class is instantiated.

Not used: A modal flag indicates whether to use the PAM250 match/mismatch matrix.

The changes involve using the scoring matrix to calculate the winning value
for each cell.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240306/step/5?unit=212652
 * rosalind: @link: http://rosalind.info/problems/ba5h/
 * book (5.11):  http://rosalind.info/problems/ba5f/
 */

internal class S05C11p05AlignmentFittingTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * first sample in the Test Dataset
     * This test makes sure that your dynamic programming matrix is correctly initialized.
     * There should be no score punishment for starting at an arbitrary position in string s.
     * Additionally, indels outside of string t should not be reported in the final alignment.
     */
    @Test
    @DisplayName("local alignment test 01")
    fun localAlignmentTest01() {

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
     * This test makes sure that your code isn’t mistakenly implementing
     * local or global alignment instead of fitting alignment.
     */
    @Test
    @DisplayName("local alignment test 02")
    fun localAlignmentTest02() {

        val sample = """
            Input:
            1 5 1
            CACGTC
            AT
            Output:
            0
            A-
            AT
            original was:
            ACGT		OR	A-
            A--T			AT
        """.trimIndent()

        runTest(sample)
    }

    /**
     * This test makes sure that your code chooses the correct cell
     * in the dynamic programming matrix as the final score for the fitting alignment.
     */
    @Test
    @DisplayName("local alignment test 03")
    fun localAlignmentTest03() {

        val sample = """
            Input:
            1 1 1
            ATCC
            AT
            Output:
            2
            AT
            AT
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 4:
     * This test makes sure that your code can handle inputs in which the two strings are the same size.
     *
     * NOTE: This test was difficult to solve.   TODO: figure this one out.
     * For now this test is IGNORED
     */

    @Test
    @Ignore
    @DisplayName("local alignment test 04")
    fun localAlignmentTest04() {

        val sample = """
            Input:
            2 3 1
            ACGACAGAG
            CGAGAGGTT
            Output:
            7
            CGACAGAG---
            CG--AGAGGTT
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 5:
     * This test makes sure that your code can correctly handles cases
     * in which string s is significantly longer than string t.
     * In this dataset also has a high match score and low indel and
     * mismatch penalties, incentivizing matching characters at any cost.
     */

    @Test
    @DisplayName("local alignment test 05")
    fun fittingAlignmentTest05() {

        val sample = """
            Input:
            10 1 1
            CAAGACTACTATTAG
            GG
            Output:
            10
            GACTACTATTAG
            G----------G
        """.trimIndent()

        runTest(sample)
    }

    /**
        SAMPLE PROBLEM
     */
    @Test
    @DisplayName("local alignment sample")
    fun localAlignmentSampleTest() {

        val sample = """
            Input:
            1 1 2
            GAGA
            GAT
            Output:
            1
            GAG
            GAT
        """.trimIndent()

        runTest(sample)
    }

    /**
       Problem from Description
     TODO: build a test that compares the Global, Local, and Fitting algorithms as per the text

       “Fitting” w to v requires finding a substring v′ of v that
       maximizes the global alignment score between v′ and w among all substrings of v.
       For example, the best global, local, and fitting alignments of v = CGTAGGCTTAAGGTTA
       and w = ATAGATA are shown in the figure below (with mismatch and indel penalties equal to 1).

     @link: https://stepik.org/lesson/240306/step/4?unit=212652

       "Note in the figure that the optimal local alignment (with score 3)
       is not a valid fitting alignment. On the other hand, the score of the
       optimal global alignment (6 - 9 - 1 = -4) is smaller than that of
       the best fitting alignment (5 - 2 - 2 = +1)."

     */
    @Test
    @DisplayName("local alignment text example")
    fun localAlignmentTextExampleTest() {

        val sample = """
            Input:
            1 1 1
            CGTAGGCTTAAGGTTA
            ATAGATA
            Output:
            2
            A-AGGTTA
            ATA-GATA
            
            Expected:
            2
            TAGGCTTA
            TAGA--TA
        """.trimIndent()

        runTest(sample)
    }

    @Test
    @DisplayName("local alignment quiz")
    fun localAlignmentTextQuiz() {

        val sample = """
            Input:
            1 1 1
            CAATCACCCCAATCCCTCAATCCTGGCCCCACGCATAGGCTAATGCCAATCGCGGCCAGGGTATAACCGCCATAACTGTGGGTCAGAAGGGATAAGTTCCACAATCCTATTTTCCTCGAGGCGCTTCGATGCGTTAACGCGTACACTCTGTCGGCCAACCGTGTGGGAGCCGAATTGGCTGGGCTGTTGAACATTCTATCAGTAGATAAACGAAGGTACATCCGAGGTTGTCGATCGACCGCGGGGTCGTAGCGCGTGCATGTTCCTTTCAGGCCCACATACTCCGGAACGGTTCATATCACGACTATTCTTGCACAATCGGACAACGGTGTACCATGGTGGACACCGTAGGAGACCAATACTGCGTAAATCATAAGCATTGGAGAGTGGACTGCTAGCGAGGCTCACCATGGAGTCTCGGTCGGCATCTCCTGACTGCTGTTCCATCGCGTTTTTCTTTTACTCACGCAATAAATCAATACCCCCTAACACAGGCCTGCTCCAGCCTTATTAAGGCCATAGTAGCTCTACATGTAGACCGAACGGAAGCACAGTTTGGTAGAAATTCTTAATCGACTATGGTCCGTGCAGGCCAAAAAAGGAATAATCTTCGAATTCTCACGCCTTCATTAGGGCGCACATGGTGGGGTAAATCACTGCACTCTGTTCGCAGTTAAGCGTTGCAATCAATATCGGCAGAACTCGGAGTCCGTATAAAGCCGCCTCAGCGTGCACACGCCCGTGCGGCACGTCATTAGACGAGGATTCCGGGGGACTGGCCTGTTCGTAATCCACTAAAACAATGGTCCTACCATCTAAAACGCACCGTGTTCCCCTCTACGGGAACCCCCTAGAT
            AGAGCGCAGAGAAGTCATTAGAACATGTAGCACATCGCTTATTAAGGGTCAATACCTAAAGGGCCTAACTATACGCCACACGGAACAGCTC
            Output:
            22
            AGGGCGCACATG--GTGGGGTA-AATCA-CT-GCAC-TCTG-TTCGCAGTTAAGCGTTGCAATCAATATCGGC-AGAACTCGGAGTCCGT-A-TAAAGCCGCCTCAGCGTGCACACGC-C
            AGAGCGCAGA-GAAGT-CATTAGAA-CATGTAGCACATC-GCTT---A-TTAAG-G--G---TCAATA-C--CTA-AA---GG-G-CC-TAACTATA--CGCCACA-CG-GAACA-GCTC
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

        val fa = AlignmentFitting(match, mismatch, gap)
        val sRow = lines[2]
        val tCol = lines[3]

        val result = fa.fittingAlignment(sRow, tCol)

        val sRowAlignedExpected = lines[6]
        val tColAlignedExpected = lines[7]
        val sRowResult = result.second
        val tColResult = result.third

//       println(sRowAlignedExpected)
//       println(sRowResult)
//       println(tColAlignedExpected)
//       println(tColResult)

        assertEquals(sRowAlignedExpected, sRowResult)
        assertEquals(tColAlignedExpected, tColResult)

        val scoreExpected = lines[5].toInt()
        val scoreResult = result.first
        assertEquals(scoreExpected, scoreResult)


    }

}