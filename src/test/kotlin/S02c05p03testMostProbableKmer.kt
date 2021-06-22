import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import util.frequentWordsWithMismatches
import util.medianString
import util.mostProbableKmer
import util.motifEnumeration

internal class S02c05p03testMostProbableKmer {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("test mostProbableKmer 1")
    fun testMostProbableKmer() {

        val genome = "TGCCCGAGCTATCTTATGCGCATCGCATGCGGACCCTTCCCTAGGCTTGTCGCAAGCCATTATCCTGGGCGCTAGTTGCGCGAGTATTGTCAGACCTGATGACGCTGTAAGCTAGCGTGTTCAGCGGCGCGCAATGAGCGGTTTAGATCACAGAATCCTTTGGCGTATTCCTATCCGTTACATCACCTTCCTCACCCCTA"
        val probString = """
            0.364 0.333 0.303 0.212 0.121 0.242
            0.182 0.182 0.212 0.303 0.182 0.303
            0.121 0.303 0.182 0.273 0.333 0.303
            0.333 0.182 0.303 0.212 0.364 0.152
        """.trimIndent()
        val k = 6 // kmer length

        val expectedResult = "TGTCGC"
        val result = mostProbableKmer(genome, k, probString)

        assertEquals(expectedResult, result)

    }

    /**
     *  This dataset checks for off-by-one errors at the beginning of Text. Notice that the optimal
    solution (“AGCAGCTT”) occurs at the very beginning of Text, so if your code does not check
    this k-mer, then your code will output a different (incorrect) k-mer as the solution.
     */
    @Test
    @DisplayName("test mostProbableKmer 2")
    fun testMostProbableKmer2() {

        val genome = "AGCAGCTTTGACTGCAACGGGCAATATGTCTCTGTGTGGATTAAAAAAAGAGTGTCTGATCTGAACTGGTTACCTGCCGTGAGTAAAT"
        val probString = """
            0.7 0.2 0.1 0.5 0.4 0.3 0.2 0.1
            0.2 0.2 0.5 0.4 0.2 0.3 0.1 0.6
            0.1 0.3 0.2 0.1 0.2 0.1 0.4 0.2
            0.0 0.3 0.2 0.0 0.2 0.3 0.3 0.1
        """.trimIndent()
        val k = 8 // kmer length

        val expectedResult = "AGCAGCTT"
        val result = mostProbableKmer(genome, k, probString)

        assertEquals(expectedResult, result)

    }

    /**
     *  This dataset checks for off-by-one errors at the end of Text. Notice that the optimal
    solution (“AAGCAGAGTTTA”) occurs at the very end of Text, so if your code does not check
    this k-mer, then your code will output a different (incorrect) k-mer as the solution.
     */
    @Test
    @DisplayName("test mostProbableKmer 3")
    fun testMostProbableKmer3() {

        val genome = "TTACCATGGGACCGCTGACTGATTTCTGGCGTCAGCGTGATGCTGGTGTGGATGACATTCCGGTGCGCTTTGTAAGCAGAGTTTA"
        val probString = """
            0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.1 0.2 0.3 0.4 0.5
            0.3 0.2 0.1 0.1 0.2 0.1 0.1 0.4 0.3 0.2 0.2 0.1
            0.2 0.1 0.4 0.3 0.1 0.1 0.1 0.3 0.1 0.1 0.2 0.1
            0.3 0.4 0.1 0.1 0.1 0.1 0.0 0.2 0.4 0.4 0.2 0.3
        """.trimIndent()
        val k = 12 // kmer length

        val expectedResult = "AAGCAGAGTTTA"
        val result = mostProbableKmer(genome, k, probString)

        assertEquals(expectedResult, result)

    }



}