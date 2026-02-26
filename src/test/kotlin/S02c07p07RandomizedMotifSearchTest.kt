@file:Suppress("MemberVisibilityCanBePrivate")

import algorithms.RandomizedMotifSearch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.Ignore

/**
 *
Code Challenge: Implement RandomizedMotifSearch.

Input: Integers k and t, followed by a space-separated collection of strings Dna.
Output: A collection BestMotifs resulting from running
RandomizedMotifSearch(Dna, k, t) 1,000 times. Remember to use pseudocounts!

 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba2f/
 *
 * youtube:
 * @link: https://www.youtube.com/watch?v=sUXe2G2I9IA
 * @link: https://www.youtube.com/watch?v=MP6O_Z2AUDU
 */
internal class S02c07p07RandomizedMotifSearchTest {

    val rms = RandomizedMotifSearch()

    /**
     * Case 1
    Description: A small and hand-solvable dataset taken from the example problem on Stepik.

     Note: because this is random - this test doesn't always pass!  So IGNORE
     */
    @Test
    @Ignore
    @DisplayName("test RandomizedMotifSearch 1")
    fun testRandomizedMotifSearch() {

        val dnaStrings = """
            CGCCCCTCTCGGGGGTGTTCAGTAAACGGCCA
            GGGCGAGGTATGTGTAAGTGCCAAGGTGCCAG
            TAGTACCGAGACCGAAAGAAGTATACAGGCGT
            TAGATCAAGTTTCAGGTGCACGTCGGTGAACC
            AATCCACCAGCTCCACGTGCAATGTTGGCCTA
        """.trimIndent()

        val k = 8 // kmer length

        val expectedResult = """
            TCTCGGGG CCAAGGTG TACAGGCG TTCAGGTG TCCACGTG
        """.trimIndent()

        val reader = dnaStrings.reader()
        val lines = reader.readLines()

        val result = rms.doRandomSearchMultipleRuns(lines, k, 1000)

        assertEquals(expectedResult, result.joinToString(" "))

    }

    /**
     * Case 2
    This dataset checks if your code has an off-by-one error at the beginning of each sequence
    of Dna.
     */
    @Test
    @DisplayName("test RandomizedMotifSearch 2")
    fun testRandomizedMotifSearch02() {

        val dnaStrings = """
            AATTGGCACATCATTATCGATAACGATTCGCCGCATTGCC
            GGTTAACATCGAATAACTGACACCTGCTCTGGCACCGCTC
            AATTGGCGGCGGTATAGCCAGATAGTGCCAATAATTTCCT
            GGTTAATGGTGAAGTGTGGGTTATGGGGAAAGGCAGACTG
            AATTGGACGGCAACTACGGTTACAACGCAGCAAGAATATT
            GGTTAACTGTTGTTGCTAACACCGTTAAGCGACGGCAACT
            AATTGGCCAACGTAGGCGCGGCTTGGCATCTCGGTGTGTG
            GGTTAAAAGGCGCATCTTACTCTTTTCGCTTTCAAAAAAA
        """.trimIndent()

        val k = 6 // kmer length

        val expectedResult = """
            CGATAA GGTTAA GGTATA GGTTAA GGTTAC GGTTAA GGCCAA GGTTAA
        """.trimIndent()

        val reader = dnaStrings.reader()
        val lines = reader.readLines()

        val result = rms.doRandomSearchMultipleRuns(lines, k, 1000)

        assertEquals(expectedResult, result.joinToString(" "))

    }

    /**
     * Case 3
    This dataset checks if your code has an off-by-one error at the end of each sequence of
    Dna.
     */
    @Test
    @Ignore // the random nature means this one doesn't always pass
    @DisplayName("test RandomizedMotifSearch 3")
    fun testRandomizedMotifSearch03() {

        val dnaStrings = """
            GCACATCATTAAACGATTCGCCGCATTGCCTCGATTAACC
            TCATAACTGACACCTGCTCTGGCACCGCTCATCCAAGGCC
            AAGCGGGTATAGCCAGATAGTGCCAATAATTTCCTTAACC
            AGTCGGTGGTGAAGTGTGGGTTATGGGGAAAGGCAAGGCC
            AACCGGACGGCAACTACGGTTACAACGCAGCAAGTTAACC
            AGGCGTCTGTTGTTGCTAACACCGTTAAGCGACGAAGGCC
            AAGCTTCCAACATCGTCTTGGCATCTCGGTGTGTTTAACC
            AATTGAACATCTTACTCTTTTCGCTTTCAAAAAAAAGGCC
        """.trimIndent()

        val k = 6 // kmer length

        val expectedResult = """
            TTAACC ATAACT TTAACC TGAAGT TTAACC TTAAGC TTAACC TGAACA
        """.trimIndent()

        val reader = dnaStrings.reader()
        val lines = reader.readLines()

        val result = rms.doRandomSearchMultipleRuns(lines, k, 1000)

        assertEquals(expectedResult, result.joinToString(" "))

    }

}