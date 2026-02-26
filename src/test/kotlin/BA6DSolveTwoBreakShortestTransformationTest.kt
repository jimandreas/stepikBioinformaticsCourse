@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.TwoBreakDistance
import algorithms.TwoBreakGenomesToBreakpointGraph
import algorithms.TwoBreakOnGenomeGraph
import algorithms.TwoBreakSortingShortestTransformation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 *

2-Break Sorting Problem: Find a shortest transformation of one genome into another by 2-breaks.

Input: Two genomes with circular chromosomes on the same set of synteny blocks.
Output: The sequence of genomes resulting from applying a shortest sequence of
2-breaks transforming one genome into the other.

Also Known As:  Shortest Rearrangement Scenario (see Pseudocode below)

 * See also:
 * rosalind: http://rosalind.info/problems/ba6d/
 *
 * Part 7 of 9 of a series of lectures on "Are There Fragile Regions in the Human Genome?"
 *
 * youtube: @link: https://www.youtube.com/watch?v=NXc_25w11P0
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

internal class BA6DSolveTwoBreakShortestTransformationTest {

    lateinit var twoBreakDistance: TwoBreakDistance
    lateinit var twoBreakUtils: TwoBreakGenomesToBreakpointGraph
    lateinit var twoBreak: TwoBreakOnGenomeGraph
    lateinit var twoBreakSorting : TwoBreakSortingShortestTransformation

    @BeforeEach
    fun setUp() {
        twoBreakDistance = TwoBreakDistance()
        twoBreakUtils = TwoBreakGenomesToBreakpointGraph()
        twoBreak = TwoBreakOnGenomeGraph()
        twoBreakSorting = TwoBreakSortingShortestTransformation()
    }

    /**
    Sample Input:

    (+1 -2 -3 +4)
    (+1 +2 -4 -3)

    Sample Output:

    (+1 -2 -3 +4)
    (+1 -2 -3)(+4)
    (+1 -2 -4 -3)
    (-3 +1 +2 -4)
    */

    @Test
    @DisplayName("two break shortest transformation sample input Test 01")
    fun twoBreakDistanceSampleTest01() {

        val genomeP = "(+1 -2 -3 +4)"
        val genomeQ = "(+1 +2 -4 -3)"

        val result = twoBreakSorting.shortestRearrangementScenario(
            doSetupGenomeList( genomeP), doSetupGenomeList( genomeQ))
    }

    @Test
    @DisplayName("two break shortest transformation tiny Test 02")
    fun twoBreakDistanceTinyTest02() {

        val genomeP = "(+1 +2)"
        val genomeQ = "(+1 -2)"

        val result = twoBreakSorting.shortestRearrangementScenario(
            doSetupGenomeList( genomeP), doSetupGenomeList( genomeQ))
    }

    @Test
    @DisplayName("two break shortest transformation Extra Dataset 03")
    fun twoBreakDistanceExtraDatasetTest03() {

        val genomeP = "(+9 -8 +12 +7 +1 -14 +13 +3 -5 -11 +6 -2 +10 -4)"
        val genomeQ = "(-11 +8 -10 -2 +3 +4 +13 +6 +12 +9 +5 +7 -14 -1)"

        val result = twoBreakSorting.shortestRearrangementScenario(
            doSetupGenomeList( genomeP), doSetupGenomeList( genomeQ))
    }

    @Test
    @DisplayName("two break shortest transformation Quiz 04")
    fun twoBreakDistanceQuizTest04() {

        val genomeP = "(+6 -11 -5 -10 -1 -2 -3 +9 -7 +8 -4 -12)"
        val genomeQ = "(-5 -2 -8 -11 +4 +1 -3 +7 +10 +6 +9 +12)"

        val acceptedAnswer = """
            (+6 -11 -5 -10 -1 -2 -3 +9 -7 +8 -4 -12)
            (-11 -5 -2 -3 +9 -7 +8 -4 -12 +6)(-1 -10)
            (-11 -5 -2 -8 +7 -9 +3 -4 -12 +6)(-1 -10)
            (-5 -2 -8 -11)(-1 -10)(+9 -7 -6 +12 +4 -3)
            (-1 -10)(+9 -7 -6 +12 -5 -2 -8 -11 +4 -3)
            (-1 -4 +11 +8 +2 +5 -12 +6 +7 -9 +3 -10)
            (+9 -7 -6 +12 -5 -2 -8 -11 +4 +1 -3)(+10)
            (-7 +3 -1 -4 +11 +8 +2 +5 -12 +6 +9)(+10)
            (+6 +9 -10 -7 +3 -1 -4 +11 +8 +2 +5 -12)
            (-2 -8 -11 +4 +1 -3 +7 +10 +6 +9 +12 -5)
        """.trimIndent()

        //println(genomeP)

        val reader = acceptedAnswer.reader()
        val lines = reader.readLines()

        val result = twoBreakSorting.shortestRearrangementScenario(
            doSetupGenomeList( genomeP), doSetupGenomeList( genomeQ))

        checkAnswer(lines, result)
    }

    @Test
    @DisplayName("two break shortest transformation Quiz 05")
    fun twoBreakDistanceQuizTest05() {

        val genomeP = "(+10 -14 -13 -7 +2 +11 +3 -8 +6 -9 +1 -12 -5 +4)"
        val genomeQ = "(+5 -6 -7 +8 -3 -11 -9 +13 +4 +10 -12 -2 -14 +1)"

        val acceptedAnswer = """
            (+10 -14 -13 -7 +2 +11 +3 -8 +6 -9 +1 -12 -5 +4)
            (-14 -13 -7 +2 +11 +3 -8 +6 -5 +4 +10)(+1 -12 -9)
            (-14 -13 +8 -3 -11 -2 +7 +6 -5 +4 +10)(+1 -12 -9)
            (-14 -13 +2 +11 +3 -8 +7 +6 -5 +4 +10)(+1 -12 -9)
            (-14 -13 +2 +12 -1 +9 +11 +3 -8 +7 +6 -5 +4 +10)
            (-14 -13 +9 +11 +3 -8 +7 +6 -5 +4 +10)(-12 -2 +1)
            (-14 +5 -6 -7 +8 -3 -11 -9 +13 +4 +10)(-12 -2 +1)
            (+3 -8 +7 +6 -5 +14 -1 +2 +12 -10 -4 -13 +9 +11)
            (+3 -8 +7 +6 -5 +14 +2 +12 -10 -4 -13 +9 +11)(+1)
            (+3 -8 +7 +6 -5 -1 +14 +2 +12 -10 -4 -13 +9 +11)
        """.trimIndent()

        //println(genomeP)

        val reader = acceptedAnswer.reader()
        val lines = reader.readLines()

        val result = twoBreakSorting.shortestRearrangementScenario(
            doSetupGenomeList( genomeP), doSetupGenomeList( genomeQ))

        checkAnswer(lines, result)

    }


    fun doSetupGenomeList(chromosomeGivenString: String): List<List<Int>>{
        val listOfLists: MutableList<List<Int>> = mutableListOf()
        val cycle = chromosomeGivenString.split("(", ")(", ")")
        for (i in 1 until cycle.size - 1) {
            val tempList = cycle[i].split(" ").map { it.toInt() }
            listOfLists.add(tempList)
        }

        return listOfLists
    }

    fun checkAnswer(expectedList: List<String>, resultList: List<List<List<Int>>>) {

        assertEquals(expectedList.size, resultList.size)
        for (idx in 0 until expectedList.size) {
            val eString = expectedList[idx]
            val rString = chromosomeToString(resultList[idx])
            assertEquals(eString, rString)
        }
    }

    fun chromosomeToString(listC: List<List<Int>>): String {
        val str = StringBuilder()

        for (l in listC) {
            str.append("(")
            str.append(l.joinToString(" ") { String.format("%+d", it) })
            str.append(")")
        }
        return(str.toString())
    }


}