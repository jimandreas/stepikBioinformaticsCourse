import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import util.*
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class UtilTests2 {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("util: frequenceTableTest function test")
    fun testFrequencyTable() {

        val matrix = listOf(

/* A: */ .2f, .2f, 0f, 0f, 0f, 0f, .9f, .1f, .1f, .1f, .3f, 0f,
/* C: */ .1f, .6f, 0f, 0f, 0f, 0f, 0f, .4f, .1f, .2f, .4f, .6f,
/* G: */  0f, 0f, 1f, 1f, .9f, .9f, .1f, 0f, 0f, 0f, 0f, 0f,
/* T: */ .7f, .2f, 0f, 0f, .1f, .1f, 0f, .5f, .8f, .7f, .3f, .4f

        )

        val kmer = "TCGTGGATTTCC"
        val prob = probForGivenKmer(kmer, matrix)
        println(prob)
    }

    /**
     * parseProbabilityMatrix
     * test the character matrix to float list parser
     */
    @Test
    @DisplayName("test parse matrix")
    fun testParseMatrix() {
        val matrixString = """
            0.2 0.2 0.3 0.2 0.3
            0.4 0.3 0.1 0.5 0.1
            0.3 0.3 0.5 0.2 0.4
            0.1 0.2 0.1 0.1 0.2
        """.trimIndent()

        val expectedValue =
            listOf(
                0.2f,
                0.2f,
                0.3f,
                0.2f,
                0.3f,
                0.4f,
                0.3f,
                0.1f,
                0.5f,
                0.1f,
                0.3f,
                0.3f,
                0.5f,
                0.2f,
                0.4f,
                0.1f,
                0.2f,
                0.1f,
                0.1f
            )
        val foo = parseProbabilityMatrix(5, matrixString)
        println(foo)
    }

    /**
     * test teh createProfile function
     *
     * This is a bit complex so lets break it down:
     *
    one trivial motif:
    "AAAA"
    should produce an ACGT profile of:
    1   1   1   1
    0   0   0   0
    0   0   0   0
    0   0   0   0

     */
    @Test
    @DisplayName("createProfile function trivial input")
    fun testCreateProfileTrivialInput() {
        val inputMotif = listOf("AAAA")
        val expectedOutput = floatArrayOf(
            1.0f, 1.0f, 1.0f, 1.0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
    }

    /**
     * test the createProfile function
     *
     * This is a bit complex so lets break it down:
     *
    one trivial motif:
    "ACGT"
    should produce an ACGT profile of:
    1   0   0   0
    0   1   0   0
    0   0   1   0
    0   0   0   1
     */
    @Test
    @DisplayName("createProfile function trivial input02")
    fun testCreateProfileTrivialInput02() {
        val inputMotif = listOf("ACGT")
        val expectedOutput = floatArrayOf(
            1.0f, 0f, 0f, 0f,
            0f, 1.0f, 0f, 0f,
            0f, 0f, 1.0f, 0f,
            0f, 0f, 0f, 1.0f
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
    }

    /**
     * test the createProfile function
     *
     * Two motif strings now:
     *
    one trivial motif:
    "AAAA"
    "TTTT"
    should produce an ACGT profile of:
    1/2 1/2 1/2 1/2
    0   0   0   0
    0   0   0   0
    1/2 1/2 1/2 1/2
     */
    @Test
    @DisplayName("createProfile function trivial input03")
    fun testCreateProfileTrivialInput03() {
        val inputMotif = listOf(
            "AAAA",
            "TTTT"
        )
        val expectedOutput = floatArrayOf(
            0.5f, 0.5f, 0.5f, 0.5f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0.5f, 0.5f, 0.5f, 0.5f,
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
    }


    /**
     * test the createProfile function
     *
     * Three motif strings now:
     *

    "AAAAAAA"
    "TTTTGGG"
    "CCCCCCC"
    should produce an ACGT profile of:
    1/3 1/3 1/3 1/3 1/3 1/3 1/3
    1/3 1/3 1/3 1/3 1/3 1/3 1/3
    0   0   0   0   1/3 1/3 1/3
    1/3 1/3 1/3 1/3 0   0   0
     */
    @Test
    @DisplayName("createProfile function trivial input04")
    fun testCreateProfileTrivialInput04() {
        val inputMotif = listOf(
            "AAAAAAA",
            "TTTTGGG",
            "CCCCCCC"
        )
        val expectedOutput = floatArrayOf(
            1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f,
            1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f,
            0f, 0f, 0f, 0f, 1 / 3f, 1 / 3f, 1 / 3f,
            1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 0f, 0f, 0f
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
    }

    /**
     * test the score function
     *
    one trivial motif:
    "AAAA"
    should produce an ACGT profile of:
    1   1   1   1
    0   0   0   0
    0   0   0   0
    0   0   0   0

    and a score of 0 (no non-matching ACGT to majority
     */
    @Test
    @DisplayName("score function trivial input")
    fun testScoreFunctionTrivialInput() {
        val inputMotif = listOf("AAAA")
        val expectedOutput = floatArrayOf(
            1.0f, 1.0f, 1.0f, 1.0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f
        )
        // pretest - copied from above
        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")

        val score = scoreTheMotifs(inputMotif)
        assertEquals(0, score)

    }

    /**
     * test the score function
     *
     * Two motif strings now:
     *
    one trivial motif:
    "AAAA"
    "TTTT"
    should produce an ACGT profile of:
    1/2 1/2 1/2 1/2
    0   0   0   0
    0   0   0   0
    1/2 1/2 1/2 1/2
     */
    @Test
    @DisplayName("scoreFunction - test for half and half")
    fun testScoreFunctionHalfAndHalf() {
        val inputMotif = listOf(
            "AAAA",
            "TTTT"
        )
        val expectedOutput = floatArrayOf(
            0.5f, 0.5f, 0.5f, 0.5f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0.5f, 0.5f, 0.5f, 0.5f,
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
        // now look at the score - it should be 4 - scoring one point for each mis-match in each column
        val score = scoreTheMotifs(inputMotif)
        assertEquals(4, score)
    }


}