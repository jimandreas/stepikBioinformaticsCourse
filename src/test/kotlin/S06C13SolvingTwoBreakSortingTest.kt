@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.TwoBreakGenomesToBreakpointGraph
import util.TwoBreakOnGenomeGraph
import java.lang.StringBuilder
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 *

6.13 CS: Solving the 2-Break Sorting Problem

Various functions that handle pieces of the breakpoint graph processing.

 * See also:
 * stepik:
 * @link: https://stepik.org/lesson/240328/step/2?unit=212674
 * https://stepik.org/lesson/240328/step/3?unit=212674
 *
 * rosalind:
 * @link: http://rosalind.info/problems/ba6j/ 6k
 *
 */

internal class S06C13SolvingTwoBreakSortingTest {

    lateinit var twoBreakUtils: TwoBreakGenomesToBreakpointGraph
    lateinit var twoBreak: TwoBreakOnGenomeGraph

    @BeforeEach
    fun setUp() {
        twoBreakUtils = TwoBreakGenomesToBreakpointGraph()
        twoBreak = TwoBreakOnGenomeGraph()
    }

    @AfterEach
    fun tearDown() {
    }

    /**

    Code Challenge: Implement 2-BreakOnGenomeGraph.

    Input: The colored edges of a genome graph GenomeGraph,
    followed by indices i1 , i2 , i3 , and i4 .

    Output: The colored edges of the genome graph resulting from
    applying the 2-break operation 2-BreakOnGenomeGraph(GenomeGraph, i1 , i2 , i3 , i4 ).


    Sample Input:

    (2, 4), (3, 8), (7, 5), (6, 1)
    1, 6, 3, 8

    Sample Output:

    (2, 4), (3, 1), (7, 5), (6, 8)
     */
    @Test
    @DisplayName("two Break on Genome Graph Test 01")
    fun twoBreakOnGenomeGraphTest01() {

        val genomeGraphString = """
            (2, 4), (3, 8), (7, 5), (6, 1)
        """.trimIndent()
        val breakListString = """
            1, 6, 3, 8
        """.trimIndent()

        val genomeGraph = genomeGraphString.parsePairsToList()
        val breakList = breakListString.split(", ").map { it.toInt()}

        val resultString = """
            (2, 4), (3, 1), (7, 5), (6, 8)
        """.trimIndent()
        val expectedResult = resultString.parsePairsToList()

        val result = twoBreak.twoBreakOnGenomeGraph(genomeGraph, breakList)
        assertEquals(expectedResult, result)
    }


    /**
     * Code Challenge: Implement 2-BreakOnGenome.

    Input: A genome P, followed by indices i1 , i2 , i3 , and i4 .
    Output: The genome P' resulting from applying the 2-break operation
    2-BreakOnGenome(GenomeGraph i1 , i2 , i3 , i4 ).

    Sample Input:

    (+1 -2 -4 +3)
    1, 6, 3, 8

    Sample Output:

    (+1 -2)(-3 +4)

     */

    @Test
    @DisplayName("two Break on Genome Test 01")
    fun twoBreakOnGenomeTest01() {

        val genomeString = """
            (+1 -2 -4 +3)
        """.trimIndent()
        val breakListString = """
            1, 6, 3, 8
        """.trimIndent()

        val genomeList = genomeString.parsePairsToList()
        val genomeGraph = twoBreakUtils.chromosomeToCycle(genomeList)
        val breakList = breakListString.split(", ").map { it.toInt()}

        val resultString = """
            (+1 -2)(-3 +4)
        """.trimIndent()

        val expectedListsOfLists: MutableList<List<Int>> = mutableListOf()
        val cycle = resultString.split("(", ")(", ")")
        for (i in 1 until cycle.size - 1) {
            val tempList = cycle[i].split(" ").map { it.toInt() }
            expectedListsOfLists.add(tempList)
        }

        val result = twoBreak.twoBreakOnGenome(genomeGraph, breakList)
        assertContentEquals(expectedListsOfLists, result)
    }

    // save for later....
    fun dotestColoredEdges(chromosomeGivenString: String, expectedResultString: String) {
        val expectedListOfPairs = expectedResultString.parsePairs()


        val listOfLists: MutableList<List<Int>> = mutableListOf()
        val cycle = chromosomeGivenString.split("(", ")(", ")")
        for (i in 1 until cycle.size - 1) {
            val tempList = cycle[i].split(" ").map { it.toInt() }
            listOfLists.add(tempList)
        }

        val resultList = twoBreakUtils.coloredEdges(listOfLists)
        val resultPairs = pairTheList(resultList)

        //println(resultPairs)
        assertContentEquals(expectedListOfPairs, resultPairs)
    }


    // this is to print out the quiz answer
    fun printChromosomes(listC: List<List<Int>>) {
        val str = StringBuilder()

        for (l in listC) {
            str.append("(")
            str.append(l.joinToString(" ") { String.format("%+d", it) })
            str.append(")")
        }
        println(str.toString())
    }

    /**
     * Kotlin: parsing a list of pairs delimited by parentheses into a list of Pairs()
     *  A solution kindly provided by @Joffrey via stackoverflow:
     *  @link: https://stackoverflow.com/a/68682137/3853712
     */

    /*
     * parses: (2, 4), (3, 6), (5, 1), (8, 9), (10, 12), (11, 7)
     * returns list of Pairs
     */
    private fun String.parsePairs(): List<Pair<Int, Int>> = removePrefix("(")
        .removeSuffix(")")
        .split("), (")
        .map { it.parsePair() }

    private fun String.parsePair(): Pair<Int, Int> =
        split(", ").let { it[0].toInt() to it[1].toInt() }

    /*
     * parses: (2, 4), (3, 6), (5, 1), (8, 9), (10, 12), (11, 7)
     * returns list of Ints
     */
    private fun String.parsePairsToList(): List<Int> = removePrefix("(")
        .removeSuffix(")")
        .split("), (", ", ")
        .map { it.toInt() }

    /*
     * takes list of ints
     * returns pairs: (2, 4), (3, 6), (5, 1), (8, 9), (10, 12), (11, 7)
     */
    private fun pairTheList(l: List<Int>): List<Pair<Int, Int>> {
        val outList: MutableList<Pair<Int, Int>> = mutableListOf()
        var i = 0
        while (i < l.size) {
            outList.add(Pair(l[i], l[i + 1]))
            i += 2
        }
        return outList
    }


}