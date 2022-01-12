@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.BurrowsWheelerMatching
import algorithms.BurrowsWheelerMatchingWithCheckpoints
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.joinToString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class S09C13P04BurrowsWheelerMismatchTolerantTest {

    lateinit var bwm: BurrowsWheelerMatching
    lateinit var bwmwc: BurrowsWheelerMatchingWithCheckpoints

    @BeforeEach
    fun setUp() {
        bwm = BurrowsWheelerMatching()
        bwmwc = BurrowsWheelerMatchingWithCheckpoints()
    }

    /**
     * Stepik: https://stepik.org/lesson/240387/step/10?unit=212733
     * Rosalind: https://rosalind.info/problems/ba9o/
     *
     * See also:
     * https://en.wikipedia.org/wiki/Burrows%E2%80%93Wheeler_transform
     *
     * Compute Burrows-Wheeler Transform Written by Bernhard Haubold
     * http://guanine.evolbio.mpg.de/cgi-bin/bwt/bwt.cgi.pl
     */

    /**

    Code Challenge: Solve the Multiple Approximate Pattern Matching Problem.

    Input: A string Text, followed by a collection of space-separated
    strings Patterns, and an integer d.

    Output: For each string Pattern in Patterns,
    the string Pattern followed by a colon, followed by a
    space-separated collection of all positions where Pattern
    appears as a substring of Text with at most d mismatches.

     */


    @Test
    @DisplayName("Burrows Wheeler Approximate Pattern Matching Simple Test")
    fun burrowsWheelerApproximatePatternMatchingSimpleTest() {

        val sampleInput = "CAC$"
        val pairResult = bwmwc.bwtEncodeWithSuffixArray(sampleInput)
        val encodedString = pairResult.first
        val indexArray = pairResult.second
        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val symbols = """
            AC
        """.trimIndent().lines()

        val mismatchCount = 1

        val result = bwmwc.burrowsWheelerMismatchTolerantReadMappingForSymbolSet(symbols, mismatchCount, indexArray)

        val expectedResult = """
            AC: 1
        """.trimIndent().lines()
        val resultList = prettyPrint(result)
        println(resultList.joinToString("\n"))

//        assertContentEquals(expectedResult, resultList)
    }


    private fun prettyPrint(result: List<Pair<String, List<Int>>>): List<String> {
        val outList : MutableList<String> = mutableListOf()
        for (patternPair in result) {
            val sym = patternPair.first
            val offsets = patternPair.second
            if (offsets.isEmpty()) {
                outList.add("$sym:")
            } else {
                outList.add("$sym: ${offsets.sorted().joinToString(" ")}")
            }
        }
        return outList
    }

    @Test
    @DisplayName("Burrows Wheeler Approximate Pattern Matching Stepik Quiz Test")
    fun burrowsWheelerApproximatePatternMatchingStepikQuizTest() {

        val loader = Foo()
        val sampleInput = loader.getResourceAsString("BurrowsWheelerMultipleStepikString.txt")

        val symbolString = loader.getResourceAsString("BurrowsWheelerMultipleStepikPatterns.txt")
        val symbols = symbolString.split(" ")

        val pairResult = bwmwc.bwtEncodeWithSuffixArray(sampleInput)
        val encodedString = pairResult.first
        val indexArray = pairResult.second

        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val result = bwmwc.burrowsWheelerBetterMatchingMultipleWithCheckpoints(symbols, indexArray)
//
//        val expectedResult = loader.getResourceAsString("BurrowsWheelerMultipleStepikExpectedResult.txt").lines()
//        assertContentEquals(expectedResult, resultList)

    }

  
    class Foo {
        fun getResourceAsString(path: String): String {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText()
            return retStr
        }
    }


}