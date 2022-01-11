@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.BurrowsWheelerMatching
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.joinToString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class S09C11P07BurrowsWheelerBetterMatchingTest {

    lateinit var bwm: BurrowsWheelerMatching

    @BeforeEach
    fun setUp() {
        bwm = BurrowsWheelerMatching()
    }
    /**
     *  Pattern Matching with the Burrows-Wheeler Transform
     * Stepik: https://stepik.org/lesson/240383/step/8?unit=212729
     * Rosalind: https://rosalind.info/problems/ba9l/
     *
     * Finding the Matched Patterns (7/10)
     * https://www.youtube.com/watch?v=gUK2JvCCUio
     *
     * Setting Up Checkpoints (8/10)
     * https://www.youtube.com/watch?v=byNR4CbYiPQ
     *
     * See also:
     * https://en.wikipedia.org/wiki/Burrows%E2%80%93Wheeler_transform
     *
     * Compute Burrows-Wheeler Transform Written by Bernhard Haubold
     * http://guanine.evolbio.mpg.de/cgi-bin/bwt/bwt.cgi.pl
     */


    /**
     * Rosalind: https://rosalind.info/problems/ba9m/
     *
     * To improve BWMATCHING, we introduce a function
     * Countsymbol(i, LastColumn), which returns the number of
     * occurrences of symbol in the first i positions of LastColumn.
     * For example, Count"n”(10, "smnpbnnaaaaa$a”) = 3, and Count"a”(4, "smnpbnnaaaaa$a”) = 0.
     *
     * Implement countSymbol:
     *    First set up a routine to implement a count matrix
     */

    // accumulate counts of each symbol in the test string
    //   the default symbol set ($ACGT) is used
    @Test
    @DisplayName("Burrows Wheeler Better Matching Count subroutine Test")
    fun burrowsWheelerBetterMatchingCountSubroutineTest() {
        val testString = "ACCGGGTTTT$"
        bwm.initializeCountArrayAndFirstOccurrence(testString)

        val endingValues = bwm.countArray[testString.length, 0 .. bwm.symbolSet.length]

        val expectedEndingValues = "1 1 2 3 4"
        val result = endingValues.joinToString(" ")
        assertEquals(expectedEndingValues, result)

        val firstOccurrenceString = bwm.firstOccurrence.joinToString(" ")
        // should be first occurrence of "$AABBCD"
        val expectedFirstOccurrence = "0 1 2 4 7"
        assertEquals(expectedFirstOccurrence, firstOccurrenceString)
    }

    /**
     * string is "ABCABD$"
     *    encoded:  D$CAABB
     */
    @Test
    @DisplayName("Burrows Wheeler Better Matching Count simple Test")
    fun burrowsWheelerBetterMatchingCountSimpleTest() {
        val testString = "D\$CAABB"
        bwm.symbolSet = "\$ABCD"  // must be in alphabetic order to align with sorted FirstColumn
        bwm.initializeCountArrayAndFirstOccurrence(testString)

        val endingValues = bwm.countArray[testString.length, 0 .. bwm.symbolSet.length]

        val expectedEndingValues = "1 2 2 1 1"
        val result = endingValues.joinToString(" ")
        assertEquals(expectedEndingValues, result)

        val firstOccurrenceString = bwm.firstOccurrence.joinToString(" ")
        // should be first occurrence of "$AABBCD"
        val expectedFirstOccurrence = "0 1 3 5 6"
        assertEquals(expectedFirstOccurrence, firstOccurrenceString)

        val num = bwm.burrowsWheelerBetterMatching("AB")
        assertEquals(2, num) // should have two matches of "AB"
    }


    @Test
    @DisplayName("Burrows Wheeler Better Matching Sample Test")
    fun burrowsWheelerBetterMatchingSampleTest() {

        val sampleInput = "GGCGCCGC\$TAGTCACACACGCCGTA"
        bwm.initializeCountArrayAndFirstOccurrence(sampleInput)

        val symbols = "ACC CCG CAG"
        val result = bwm.burrowsWheelerBetterMatchingMultiple(symbols.split(" "))

        println(result.joinToString(" "))
        val expectedResult = "2 1 1 0 1"

        //assertEquals(expectedResult, result.joinToString(" "))

    }

    @Test
    @DisplayName("Burrows Wheeler Better Matching Rosalind Quiz Test")
    fun burrowsWheelerBetterMatchingRosalindQuizTest() {

        val loader = Foo()
        val sampleInput = loader.getResourceAsString("BurrowsWheelerBetterMatchingRosalindString.txt")
        bwm.initializeCountArrayAndFirstOccurrence(sampleInput)

        val symbolString = loader.getResourceAsString("BurrowsWheelerBetterMatchingRosalindPatterns.txt")
        val symbols = symbolString.split(" ")
        val result = bwm.burrowsWheelerBetterMatchingMultiple(symbols)

        val expectedResult = loader.getResourceAsString("BurrowsWheelerBetterMatchingRosalindExpectedResult.txt")

        assertEquals(expectedResult, result.joinToString(" "))

    }

    @Test
    @DisplayName("Burrows Wheeler Better Matching Stepik Quiz Test")
    fun burrowsWheelerBetterMatchingStepikQuizTest() {

        val loader = Foo()
        val sampleInput = loader.getResourceAsString("BurrowsWheelerBetterMatchingStepikString.txt")
        bwm.initializeCountArrayAndFirstOccurrence(sampleInput)

        val symbolString = loader.getResourceAsString("BurrowsWheelerBetterMatchingStepikPatterns.txt")
        val symbols = symbolString.split(" ")
        val result = bwm.burrowsWheelerBetterMatchingMultiple(symbols)

        val expectedResult = loader.getResourceAsString("BurrowsWheelerBetterMatchingStepikExpectedResult.txt")

        //println(result.joinToString(" "))
        assertEquals(expectedResult, result.joinToString(" "))

    }

  
    class Foo {
        fun getResourceAsString(path: String): String {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText()
            return retStr
        }
    }
}