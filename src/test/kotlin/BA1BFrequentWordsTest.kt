import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import problems.scanForMatchesOfLength

/**
 *  1.2 Hidden Messages in the Replication Origin

Code Challenge: Solve the Frequent Words Problem.

Input: A string Text and an integer k.
Output: All most frequent k-mers in Text.

See also:
rosalind: http://rosalind.info/problems/ba1b/
 */

internal class BA1BFrequentWordsTest {

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