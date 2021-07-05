@file:Suppress("UNUSED_VARIABLE")

import org.junit.jupiter.api.*

import util.*
import kotlin.test.assertEquals

internal class UtilTestsSpectrumScoring {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * Cyclopeptide Scoring Problem: Compute the score of a cyclic peptide against a spectrum.

    Input: An amino acid string Peptide and a collection of integers Spectrum.
    Output: The score of Peptide against Spectrum, Score(Peptide, Spectrum).

    Code Challenge: Solve the Cyclopeptide Scoring Problem.

    Extra Dataset

    Sample Input:

    NQEL
    0 99 113 114 128 227 257 299 355 356 370 371 484

    Sample Output:

    11
     */
    @Test
    @DisplayName("test - Cyclopeptide Scoring Problem01")
    fun testCyclopeptideScoring01() {
        val peptide = "NQEL"
        val givenSpectrum = listOf( 0, 99, 113, 114, 128, 227, 257, 299, 355, 356, 370, 371, 484)
        val expectedScore = 11

        val result = cyclopeptideScore(peptide, givenSpectrum)
        assertEquals(expectedScore, result)
    }



}