@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.BurrowsWheelerTransform
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class BA9IBurrowsWheelerEncodeTest {

    lateinit var bwt: BurrowsWheelerTransform

    @BeforeEach
    fun setUp() {
        bwt = BurrowsWheelerTransform()
    }
    /**
     * Construct the Burrows-Wheeler transform of a string.
     * Rosalind: https://rosalind.info/problems/ba9i/
     *
     * String Compression and the Burrows-Wheeler Transform (4/10)
     * https://www.youtube.com/watch?v=G7YBi04HOEY
     *
     * Inverting Burrows-Wheeler (5/10)
     * https://www.youtube.com/watch?v=DqdjbK68l3s
     *
     * Using Burrows-Wheeler for Pattern Matching (6/10)
     * https://www.youtube.com/watch?v=z5EDLODQPtg
     *
     * See also:
     * https://en.wikipedia.org/wiki/Burrows%E2%80%93Wheeler_transform
     */


    /**

    Burrows-Wheeler Transform Construction Problem: Construct the Burrows-Wheeler transform of a string.

    Input: A string Text.
    Output: BWT(Text).

    Code Challenge: Solve the Burrows-Wheeler Transform Construction Problem.
     */

    @Test
    @DisplayName("Burrows Wheeler Encode Sample Test")
    fun burrowsWheelerEncodeSampleTest() {

        val inputString = "GCGTGCCTGGTCA$"

        val result = bwt.bwtEncode(inputString)

        val expectedResult = "ACTGGCT\$TGCGGC"

        assertEquals(expectedResult, result)

        val inputString2 = "panamabananas$"
        val result2 = bwt.bwtEncode(inputString2)
        val expectedResult2 = "smnpbnnaaaaa\$a"
        assertEquals(expectedResult2, result2)
    }

}