@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.TwoBreakDistance
import util.TwoBreakGenomesToBreakpointGraph
import util.TwoBreakOnGenomeGraph
import util.TwoBreakSortingShortestTransformation

/**
 *

2-Break Sorting Problem: Find a shortest transformation of one genome into another by 2-breaks.

Input: Two genomes with circular chromosomes on the same set of synteny blocks.
Output: The sequence of genomes resulting from applying a shortest sequence of
2-breaks transforming one genome into the other.

Also Known As:  Shortest Rearrangement Scenario (see Pseudocode below)

 * See also:
 * stepik: https://stepik.org/lesson/240324/step/5?unit=212670
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

internal class S06C09p05SolveTwoBreakShortestTransformationTest {

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

    @AfterEach
    fun tearDown() {
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


    fun doSetupGenomeList(chromosomeGivenString: String): List<List<Int>>{
        val listOfLists: MutableList<List<Int>> = mutableListOf()
        val cycle = chromosomeGivenString.split("(", ")(", ")")
        for (i in 1 until cycle.size - 1) {
            val tempList = cycle[i].split(" ").map { it.toInt() }
            listOfLists.add(tempList)
        }

        return listOfLists
    }


}