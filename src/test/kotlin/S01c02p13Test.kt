import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

internal class S01c02p13Test {

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
    @DisplayName( "test with made up string for overlapping substrings")
    fun countRepeatedTargetsWithOverlapMadeUpStrings() {

        val genome2 = "blahbbbblahblahblahb"
        val target2 = "bb"
        val foundCount = countRepeatedTargetsWithOverlap(genome2, target2)

        assertEquals(foundCount, 3)

    }

    @Test
    @DisplayName( "test with how now brown cow")
    fun countRepeatedTargetsWithOverlapMadeUpStrings02() {

        val genome2 = "how now brown cow"
        val target2 = "ow"
        val foundCount = countRepeatedTargetsWithOverlap(genome2, target2)

        assertEquals(foundCount, 4)

    }
}