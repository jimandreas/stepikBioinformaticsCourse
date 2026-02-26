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

internal class BA9NBurrowsWheelerMatchingWithCheckpointsTest {

    lateinit var bwm: BurrowsWheelerMatching
    lateinit var bwmwc: BurrowsWheelerMatchingWithCheckpoints

    @BeforeEach
    fun setUp() {
        bwm = BurrowsWheelerMatching()
        bwmwc = BurrowsWheelerMatchingWithCheckpoints()
    }

    /**
     * Rosalind: https://rosalind.info/problems/ba9n/
     *
     * See also:
     * https://en.wikipedia.org/wiki/Burrows%E2%80%93Wheeler_transform
     *
     * Compute Burrows-Wheeler Transform Written by Bernhard Haubold
     * http://guanine.evolbio.mpg.de/cgi-bin/bwt/bwt.cgi.pl
     */


    /**
     * compare the previous countArray implementation with the
     *   count with checkpoints (sparse array with interpolation)
     */
    @Test
    @DisplayName("Burrows Wheeler Better Matching Count checkpoints Test")
    fun burrowsWheelerBetterMatchingCountCheckpointsTest() {
        // repeat the previous test of the count array
        val testString = "ACCGGGTTTT$"
        bwm.initializeCountArrayAndFirstOccurrence(testString)
        val endingValues = bwm.countArray[testString.length, 0 .. bwmwc.symbolSet.length]
        val expectedEndingValues = "1 1 2 3 4"
        val result = endingValues.joinToString(" ")
        assertEquals(expectedEndingValues, result)

        // now compare this result with the checkpoint based calculation.
        //   the values should match

        bwmwc.initializeCountArrayAndFirstOccurrence(testString)
        for (i in 0..testString.length) {
            for (j in 0 until bwmwc.symbolSet.length) {
                val old = bwm.countArray[i, j]
                val new = bwmwc.interpolateCheckpoint(i, j)
                assertEquals(old, new, "mismatch at $i and $j")
            }
        }
    }


    @Test
    @DisplayName("Burrows Wheeler Better Matching Simple Test")
    fun burrowsWheelerCheckpointsMultiplePatternSimpleTest() {

        val sampleInput = "CAC$"
        val pairResult = bwmwc.bwtEncodeWithSuffixArray(sampleInput)
        val encodedString = pairResult.first
        val indexArray = pairResult.second
        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val symbols = """
            AC
        """.trimIndent().lines()

        val result = bwmwc.burrowsWheelerBetterMatchingMultipleWithCheckpoints(symbols, indexArray)

        val expectedResult = """
            AC: 1
        """.trimIndent().lines()
        val resultList = prettyPrint(result)
        //println(resultList.joinToString("\n"))

        assertContentEquals(expectedResult, resultList)
    }




    // https://rosalind.info/problems/ba9n/
    @Test
    @DisplayName("Burrows Wheeler Better Matching Sample Test")
    fun burrowsWheelerCheckpointsMultiplePatternRosalindSampleTest() {

        val sampleInput = "AATCGGGTTCAATCGGGGT$"
        val pairResult = bwmwc.bwtEncodeWithSuffixArray(sampleInput)
        val encodedString = pairResult.first
        val indexArray = pairResult.second
        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val symbols = """
            ATCG
            GGGT
        """.trimIndent().lines()

        val expectedResult = """
            ATCG: 1 11
            GGGT: 4 15
        """.trimIndent().lines()

        val result = bwmwc.burrowsWheelerBetterMatchingMultipleWithCheckpoints(symbols, indexArray)

        val resultList = prettyPrint(result)
        //println(resultList.joinToString("\n"))

        assertContentEquals(expectedResult, resultList)
    }

    @Test
    @DisplayName("Burrows Wheeler Better Matching Case2 Test")
    fun burrowsWheelerCheckpointsMultiplePatternRosalindCase2Test() {

        val sampleInput = "ATATATATAT$"
        val pairResult = bwmwc.bwtEncodeWithSuffixArray(sampleInput)
        val encodedString = pairResult.first
        val indexArray = pairResult.second
        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val symbols = """
            GT AGCT TAA AAT AATAT
        """.trimIndent().split(" ")

        val expectedResult = """
            GT:
            AGCT:
            TAA:
            AAT:
            AATAT:
        """.trimIndent().lines()

        val result = bwmwc.burrowsWheelerBetterMatchingMultipleWithCheckpoints(symbols, indexArray)

        val resultList = prettyPrint(result)
        //println(resultList.joinToString("\n"))

        assertContentEquals(expectedResult, resultList)
    }

    @Test
    @DisplayName("Burrows Wheeler Better Matching Case4 Test")
    fun burrowsWheelerCheckpointsMultiplePatternRosalindCase4Test() {

        val sampleInput = "AAACAA$"
        val pairResult = bwmwc.bwtEncodeWithSuffixArray(sampleInput)
        val encodedString = pairResult.first
        val indexArray = pairResult.second
        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val symbols = """
            AA
        """.trimIndent().split(" ")

        val expectedResult = """
            AA: 0 1 4
        """.trimIndent().lines()

        val result = bwmwc.burrowsWheelerBetterMatchingMultipleWithCheckpoints(symbols, indexArray)

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
    @DisplayName("Burrows Wheeler Better Matching Stepik Quiz Test")
    fun burrowsWheelerBetterMatchingStepikQuizTest() {

        val loader = Foo()
        val sampleInput = loader.getResourceAsString("BurrowsWheelerMultipleStepikString.txt")

        val symbolString = loader.getResourceAsString("BurrowsWheelerMultipleStepikPatterns.txt")
        val symbols = symbolString.split(" ")

        val pairResult = bwmwc.bwtEncodeWithSuffixArray(sampleInput)
        val encodedString = pairResult.first
        val indexArray = pairResult.second

        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val result = bwmwc.burrowsWheelerBetterMatchingMultipleWithCheckpoints(symbols, indexArray)

        val resultList : MutableList<String> = mutableListOf()
        for (patternPair in result) {
            val sym = patternPair.first
            val offsets = patternPair.second
            if (offsets.isEmpty()) {
                //println("$sym:")
                resultList.add("$sym:")
            } else {
                //println("$sym: ${offsets.sorted().joinToString(" ")}")
                resultList.add("$sym: ${offsets.sorted().joinToString(" ")}")
            }
        }

        val expectedResult = loader.getResourceAsString("BurrowsWheelerMultipleStepikExpectedResult.txt").lines()
        assertContentEquals(expectedResult, resultList)

    }

    @Test
    @DisplayName("Burrows Wheeler Better Matching Rosalind Quiz Test")
    fun burrowsWheelerBetterMatchingRosalindQuizTest() {

        val loader = Foo()
        val sampleInput = loader.getResourceAsString("BurrowsWheelerMultipleRosalindString.txt")

        val symbolString = loader.getResourceAsString("BurrowsWheelerMultipleRosalindPatterns.txt")
        val symbols = symbolString.lines()

        val pairResult = bwmwc.bwtEncodeWithSuffixArray(sampleInput)
        val encodedString = pairResult.first
        val indexArray = pairResult.second

        bwmwc.initializeCountArrayAndFirstOccurrence(encodedString)

        val result = bwmwc.burrowsWheelerBetterMatchingMultipleWithCheckpoints(symbols, indexArray)

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
        //println(resultListInt.sorted().joinToString(" "))

        val expectedResult = loader.getResourceAsString("BurrowsWheelerMultipleRosalindExpectedResult.txt").lines()
        assertContentEquals(expectedResult, resultList)

        // and check the expected "rosalind ints"
        val expectedResultInts = loader.getResourceAsString("BurrowsWheelerMultipleRosalindExpectedInts.txt")
            .split(" ").map { it.toInt() }

        assertContentEquals(expectedResultInts, resultListInt.sorted())


    }
  
    class Foo {
        fun getResourceAsString(path: String): String {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText()
            return retStr
        }
    }


}