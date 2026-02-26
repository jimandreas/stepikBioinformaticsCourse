@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.BurrowsWheelerMatching
import algorithms.BurrowsWheelerMatchingWithCheckpoints
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
     * Rosalind: https://rosalind.info/problems/ba9o/
     *
     * Youtube:
     * Inexact Matching (9/10)
     * https://www.youtube.com/watch?v=Vjnm-jF1PBQ
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
    @DisplayName("Burrows Wheeler Approximate Pattern Matching Seed Generation Test")
    fun burrowsWheelerApproximatePatternMatchingSeedGenerationTest() {
        val seed01 = "AB"
        val i01 = 1
        val expectedResult01 = listOf(Pair("A", 0), Pair("B", 1))
        val result01 = bwmwc.makeSeeds(seed01, i01)
        assertEquals(expectedResult01, result01)

        val seed02 = "AABB"
        val i02 = 1
        val expectedResult02 = listOf(Pair("AA", 0), Pair("BB", 2))
        val result02 = bwmwc.makeSeeds(seed02, i02)
        assertEquals(expectedResult02, result02)

        val seed03 = "AABBCCC"
        val i03 = 2
        val expectedResult03 = listOf(Pair("AA", 0), Pair("BB", 2), Pair("CCC", 4))
        val result03 = bwmwc.makeSeeds(seed03, i03)
        assertEquals(expectedResult03, result03)

        val seed04 = "acttaggctcgggataatccgga"
        val i04 = 3
        val expectedResult04 = listOf(Pair("actta", 0), Pair("ggctc", 5), Pair("gggat", 10), Pair("aatccgga", 15))
        val result04 = bwmwc.makeSeeds(seed04, i04)
        assertEquals(expectedResult04, result04)
    }

    @Test
    @DisplayName("Burrows Wheeler Approximate Pattern Matching Mismatch Test")
    fun burrowsWheelerApproximatePatternMatchingMismatchTest() {
        val originalString01 = "AA"
        val substring01 = "AB"
        val i01 = 1
        val offsetList01 = listOf(0)
        val expectedResult01 = listOf(0)
        val pairResult01 = bwmwc.bwtEncodeWithSuffixArray(originalString01) // return value ignored
        val result01 = bwmwc.checkOffsetsForMatches(substring01, i01, 0, offsetList01)
        assertEquals(expectedResult01, result01)

        val originalString02 = "ABCDEF"
        val substring02 = "BBB"
        val i02 = 2 // allow two mismatches
        val offsetList02 = listOf(0, 1, 2, 3)   // places where match could occur
        val expectedResult02 = listOf(0, 1)
        val pairResult02 = bwmwc.bwtEncodeWithSuffixArray(originalString02)  // return value ignored
        val result02 = bwmwc.checkOffsetsForMatches(substring02, i02, 0, offsetList02)
        assertEquals(expectedResult02, result02)

        // test the seed offset
        val originalString03 = "AB"
        val substring03 = "AA"
        val i03 = 1 // allow two mismatches
        val offsetList03 = listOf(1)   // places where match could occur
        val expectedResult03 = listOf(0)
        val pairResult03 = bwmwc.bwtEncodeWithSuffixArray(originalString03)  // return value ignored
        val result03 = bwmwc.checkOffsetsForMatches(substring03, i03, 1, offsetList03)
        assertEquals(expectedResult03, result03)
    }

    @Test
    @DisplayName("Burrows Wheeler Approximate Pattern Matching Simple Test")
    fun burrowsWheelerApproximatePatternMatchingSimpleTest() {

        val sampleInput = "CAC$"
        val pairResult = bwmwc.bwtEncodeWithSuffixArray(sampleInput)
        val encodedString = pairResult.first
        val indexArray = pairResult.second
        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val symbols = """
            AA
        """.trimIndent().lines()

        val mismatchCount = 1

        val result = bwmwc.burrowsWheelerMismatchTolerantReadMappingForSymbolSet(symbols, mismatchCount, indexArray)

        val expectedResult = """
            AA: 0 1
        """.trimIndent().lines()
        val resultList = prettyPrint(result)
        //println(resultList.joinToString("\n"))

        assertContentEquals(expectedResult, resultList)
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
        val sampleInput = loader.getResourceAsString("BurrowsWheelerMismatchTolerantStepikInput.txt").lines()

        val string = sampleInput[0]
        val symbols = sampleInput[1].split(" ")
        val mismatchCount = sampleInput[2].toInt()

        val pairResult = bwmwc.bwtEncodeWithSuffixArray(string)
        val encodedString = pairResult.first
        val indexArray = pairResult.second

        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val result = bwmwc.burrowsWheelerMismatchTolerantReadMappingForSymbolSet(symbols, mismatchCount, indexArray)

        val resultList = prettyPrint(result)
        //println(resultList.joinToString("\n"))  //accepted result
        val expectedResult = loader.getResourceAsString("BurrowsWheelerMismatchTolerantStepikExpectedResult.txt").lines()
        assertContentEquals(expectedResult, resultList)

    }

    @Test
    @DisplayName("Burrows Wheeler Approximate Pattern Matching Rosalind Quiz Test")
    fun burrowsWheelerApproximatePatternMatchingRosalindQuizTest() {

        val loader = Foo()
        val sampleInput = loader.getResourceAsString("BurrowsWheelerMismatchTolerantRosalindInput.txt").lines()

        val string = sampleInput[0] + '$'
        val symbols = sampleInput[1].split(" ")
        val mismatchCount = sampleInput[2].toInt()

        val pairResult = bwmwc.bwtEncodeWithSuffixArray(string)
        val encodedString = pairResult.first
        val indexArray = pairResult.second

        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val result = bwmwc.burrowsWheelerMismatchTolerantReadMappingForSymbolSet(symbols, mismatchCount, indexArray)

        val resultList : MutableList<String> = mutableListOf()
        val resultListInt : MutableList<Int> = mutableListOf()
        for (patternPair in result) {
            val sym = patternPair.first
            val offsets = patternPair.second
            if (offsets.isEmpty()) {
                //println("$sym:")
                //resultList.add("$sym:")
            } else {
                //println("$sym: ${offsets.sorted().joinToString(" ")}")
                resultList.add("$sym: ${offsets.sorted().joinToString(" ")}")
                resultListInt.addAll(offsets)
            }
        }
        val resultInts = resultListInt.sorted().toList()

        // check the expected "rosalind ints"
        val expectedResultInts = loader.getResourceAsString("BurrowsWheelerMismatchTolerantRosalindExpectedInts.txt")
            .split(" ").map { it.toInt() }

        //println(resultInts.joinToString(" "))

        for (i in 0 until resultInts.size) {
            if (expectedResultInts[i] != resultInts[i]) {
                println("mismatch at $i e: ${expectedResultInts[i]} r: ${resultInts[i]}")
            }
        }
    }


    class Foo {
        fun getResourceAsString(path: String): String {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText()
            return retStr
        }
    }


}