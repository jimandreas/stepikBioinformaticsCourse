import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

/**
 *  1.2 Hidden Messages in the Replication Origin

Code Challenge: Solve the Frequent Words Problem.

Input: A string Text and an integer k.
Output: All most frequent k-mers in Text.

See also:
stepik: https://stepik.org/lesson/240214/step/13?unit=212561
rosalind: http://rosalind.info/problems/ba1b/
 */

internal class S01c02p13FrequentWordsTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName( "test with DNA strip supplied by class")
    fun countRepeatedTargetsWithOverlap() {

        val genome1 = "CGGAAGCGAGATTCGCGTGGCGTGATTCCGGCGGGCGTGGAGAAGCGAGATTCATTCAAGCCGGGAGGCGTGGCGTGGCGTGGCGTGCGGATTCAAGCCGGCGGGCGTGATTCGAGCGGCGGATTCGAGATTCCGGGCGTGCGGGCGTGAAGCGCGTGGAGGAGGCGTGGCGTGCGGGAGGAGAAGCGAGAAGCCGGATTCAAGCAAGCATTCCGGCGGGAGATTCGCGTGGAGGCGTGGAGGCGTGGAGGCGTGCGGCGGGAGATTCAAGCCGGATTCGCGTGGAGAAGCGAGAAGCGCGTGCGGAAGCGAGGAGGAGAAGCATTCGCGTGATTCCGGGAGATTCAAGCATTCGCGTGCGGCGGGAGATTCAAGCGAGGAGGCGTGAAGCAAGCAAGCAAGCGCGTGGCGTGCGGCGGGAGAAGCAAGCGCGTGATTCGAGCGGGCGTGCGGAAGCGAGCGG"
        val runLength1 = 12
        val expectedResult = "CGGCGGGAGATT CGGGAGATTCAA CGTGCGGCGGGA CGTGGAGGCGTG CGTGGCGTGCGG GCGTGCGGCGGG GCGTGGAGGCGT GCGTGGCGTGCG GGAGAAGCGAGA GGAGATTCAAGC GGCGGGAGATTC GGGAGATTCAAG GTGCGGCGGGAG TGCGGCGGGAGA"

        val matchString = scanForMatchesOfLength(genome1, runLength1)

        assertEquals(matchString.trimEnd(), expectedResult )

    }

    @Test
    @DisplayName( "test with how now brown cow")
    fun countRepeatedTargetsWithOverlapMadeUpStrings02() {

        val genome2 = "how now brown cow"
        val runLength2 = 2
        val matchString = scanForMatchesOfLength(genome2, runLength2)
        val expectedResult = "ow"

        assertEquals(matchString.trimEnd(), expectedResult)

    }

    @Test
    @DisplayName( "test to find max TTT string")
    fun findMaxTTTstring() {

        val genome3 =
"""CCGGGTTTTAAATTTT"""
        val runLength3 = 4
        val matchString = scanForMatchesOfLength(genome3, runLength3)
        val expectedResult = "TTTT"

        assertEquals(expectedResult, matchString.trimEnd())

    }

    /* GIVEN:
     *   Sample Input:

    ACGTTGCATGTCGCATGATGCATGAGAGCT
    4

    Sample Output:

    CATG GCAT
     */
    @Test
    @DisplayName( "test with given short sample")
    fun testWithGivenSample() {

        val genome4 =
            """ACGTTGCATGTCGCATGATGCATGAGAGCT"""
        val runLength4 = 4
        val matchString = scanForMatchesOfLength(genome4, runLength4)
        val expectedResult = "CATG GCAT"

        assertEquals(expectedResult, matchString.trimEnd())

    }
}