@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused")

import algorithms.TwoBreakGenomesToBreakpointGraph
import algorithms.TwoBreakOnGenomeGraph
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 *

6.13 CS: Solving the 2-Break Sorting Problem

Various functions that handle pieces of the breakpoint graph processing.

 * See also:
 *
 * rosalind:
 * @link: http://rosalind.info/problems/ba6j/ 6k
 *
 */

internal class S06c13p02p03SolvingTwoBreakSortingTest {

    lateinit var twoBreakUtils: TwoBreakGenomesToBreakpointGraph
    lateinit var twoBreak: TwoBreakOnGenomeGraph

    @BeforeEach
    fun setUp() {
        twoBreakUtils = TwoBreakGenomesToBreakpointGraph()
        twoBreak = TwoBreakOnGenomeGraph()
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

            // was (2, 4), (3, 1), (7, 5), (6, 8)
        val resultString = """
            (2, 4), (7, 5), (3, 1), (6, 8)
        """.trimIndent()
        val expectedResult = resultString.parsePairsToList()

        val result = twoBreak.twoBreakOnGenomeGraph(genomeGraph, breakList)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("two Break on Genome Graph Extra Dataset Test 02")
    fun twoBreakOnGenomeGraphExtraTest02() {

        val genomeGraphString = """
            (2, 4), (3, 5), (6, 8), (7, 10), (9, 12), (11, 13), (14, 15), (16, 17), (18, 19), (20, 21), (22, 24), (23, 25), (26, 28), (27, 30), (29, 32), (31, 33), (34, 35), (36, 37), (38, 40), (39, 42), (41, 44), (43, 45), (46, 47), (48, 49), (50, 51), (52, 53), (54, 56), (55, 58), (57, 59), (60, 61), (62, 64), (63, 66), (65, 68), (67, 69), (70, 72), (71, 73), (74, 75), (76, 77), (78, 80), (79, 81), (82, 83), (84, 86), (85, 88), (87, 90), (89, 92), (91, 94), (93, 96), (95, 98), (97, 99), (100, 102), (101, 104), (103, 106), (105, 107), (108, 109), (110, 111), (112, 113), (114, 116), (115, 117), (118, 120), (119, 121), (122, 124), (123, 126), (125, 128), (127, 129), (130, 132), (131, 134), (133, 136), (135, 1)
        """.trimIndent()
        val breakListString = """
            87, 90, 74, 75
        """.trimIndent()

        val genomeGraph = genomeGraphString.parsePairsToList()
        val breakList = breakListString.split(", ").map { it.toInt()}

        // was (2, 4), (3, 1), (7, 5), (6, 8)
        val resultString = """
            (2, 4), (3, 5), (6, 8), (7, 10), (9, 12), (11, 13), (14, 15), (16, 17), (18, 19), (20, 21), (22, 24), (23, 25), (26, 28), (27, 30), (29, 32), (31, 33), (34, 35), (36, 37), (38, 40), (39, 42), (41, 44), (43, 45), (46, 47), (48, 49), (50, 51), (52, 53), (54, 56), (55, 58), (57, 59), (60, 61), (62, 64), (63, 66), (65, 68), (67, 69), (70, 72), (71, 73), (76, 77), (78, 80), (79, 81), (82, 83), (84, 86), (85, 88), (89, 92), (91, 94), (93, 96), (95, 98), (97, 99), (100, 102), (101, 104), (103, 106), (105, 107), (108, 109), (110, 111), (112, 113), (114, 116), (115, 117), (118, 120), (119, 121), (122, 124), (123, 126), (125, 128), (127, 129), (130, 132), (131, 134), (133, 136), (135, 1), (87, 74), (90, 75)
        """.trimIndent()
        val expectedResult = resultString.parsePairsToList()

        val result = twoBreak.twoBreakOnGenomeGraph(genomeGraph, breakList)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("two Break on Genome Graph Quiz Test 03")
    fun twoBreakOnGenomeGraphQuizTest03() {

        val genomeGraphString = """
            (2, 3), (4, 5), (6, 8), (7, 9), (10, 11), (12, 13), (14, 15), (16, 18), (17, 19), (20, 22), (21, 24), (23, 25), (26, 27), (28, 29), (30, 31), (32, 33), (34, 36), (35, 38), (37, 39), (40, 42), (41, 44), (43, 46), (45, 47), (48, 49), (50, 52), (51, 53), (54, 56), (55, 58), (57, 60), (59, 62), (61, 64), (63, 66), (65, 67), (68, 69), (70, 71), (72, 73), (74, 76), (75, 77), (78, 80), (79, 81), (82, 84), (83, 85), (86, 88), (87, 90), (89, 92), (91, 94), (93, 96), (95, 98), (97, 99), (100, 102), (101, 103), (104, 105), (106, 107), (108, 109), (110, 111), (112, 113), (114, 116), (115, 118), (117, 120), (119, 121), (122, 123), (124, 125), (126, 127), (128, 130), (129, 132), (131, 1)
        """.trimIndent()
        val breakListString = """
            65, 67, 104, 105
        """.trimIndent()

        val genomeGraph = genomeGraphString.parsePairsToList()
        val breakList = breakListString.split(", ").map { it.toInt()}

        // was (2, 4), (3, 1), (7, 5), (6, 8)
        val resultString = """
            (2, 4), (3, 5), (6, 8), (7, 10), (9, 12), (11, 13), (14, 15), (16, 17), (18, 19), (20, 21), (22, 24), (23, 25), (26, 28), (27, 30), (29, 32), (31, 33), (34, 35), (36, 37), (38, 40), (39, 42), (41, 44), (43, 45), (46, 47), (48, 49), (50, 51), (52, 53), (54, 56), (55, 58), (57, 59), (60, 61), (62, 64), (63, 66), (65, 68), (67, 69), (70, 72), (71, 73), (76, 77), (78, 80), (79, 81), (82, 83), (84, 86), (85, 88), (89, 92), (91, 94), (93, 96), (95, 98), (97, 99), (100, 102), (101, 104), (103, 106), (105, 107), (108, 109), (110, 111), (112, 113), (114, 116), (115, 117), (118, 120), (119, 121), (122, 124), (123, 126), (125, 128), (127, 129), (130, 132), (131, 134), (133, 136), (135, 1), (87, 74), (90, 75)
        """.trimIndent()
        val expectedResult = resultString.parsePairsToList()

        val result = twoBreak.twoBreakOnGenomeGraph(genomeGraph, breakList)
        val outString = pairTheList(result).toString()
//       println(outString)
//        assertEquals(expectedResult, result)
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

        val genomeGraph = genomeString.removePrefix("(")
            .removeSuffix(")")
            .split(" ")
            .map { it.toInt() }
        val breakList = breakListString.split(", ").map { it.toInt()}

//        (+1 -2)(-3 +4)
        val resultString = """
            (-2 +1)(3 -4)
        """.trimIndent()

        val expectedListsOfLists: MutableList<List<Int>> = mutableListOf()
        val cycle = resultString.split("(", ")(", ")")
        for (i in 1 until cycle.size - 1) {
            val tempList = cycle[i].split(" ").map { it.toInt() }
            expectedListsOfLists.add(tempList)
        }

        val result = twoBreak.twoBreakOnGenome(listOf(genomeGraph), breakList)
        assertContentEquals(expectedListsOfLists, result)
    }

    @Test
    @DisplayName("two Break on Genome Extra Dataset Test 02")
    fun twoBreakOnGenomeExtraDatasetTest02() {

        val genomeString = """
            (-1 +2 -3 -4 -5 -6 -7 +8 +9 +10 +11 +12 +13 +14 +15 -16 -17 -18 +19 -20 +21 +22 -23 -24 +25 -26 -27 +28 -29 -30 +31 +32 +33 -34 -35 +36 +37 +38 +39 -40 +41 +42 -43 +44 +45 +46 -47 +48 +49 +50 -51 +52 +53 +54 -55 -56 -57 +58 -59 -60 -61 +62 -63 +64 +65 -66 -67)
        """.trimIndent()
        val breakListString = """
            116, 118, 73, 72
        """.trimIndent()

        val genomeGraph = genomeString.removePrefix("(")
            .removeSuffix(")")
            .split(" ")
            .map { it.toInt() }
        val breakList = breakListString.split(", ").map { it.toInt()}

        val expectedString = """             
            (+2 -3 -4 -5 -6 -7 +8 +9 +10 +11 +12 +13 +14 +15 -16 -17 -18 +19 -20 +21 +22 -23 -24 +25 -26 -27 +28 -29 -30 +31 +32 +33 -34 -35 +36 -59 -60 -61 +62 -63 +64 +65 -66 -67 -1)(+38 +39 -40 +41 +42 -43 +44 +45 +46 -47 +48 +49 +50 -51 +52 +53 +54 -55 -56 -57 +58 +37) 
        """.trimIndent()

        val expectedListsOfLists: MutableList<List<Int>> = mutableListOf()
        val cycle = expectedString.split("(", ")(", ")")
        for (i in 1 until cycle.size - 1) {
            val tempList = cycle[i].split(" ").map { it.toInt() }
            expectedListsOfLists.add(tempList)
        }

        val result = twoBreak.twoBreakOnGenome(listOf(genomeGraph), breakList)

        assertEquals(expectedListsOfLists[0], result[0])
        assertEquals(expectedListsOfLists[1], result[1])
    }

    @Test
    @DisplayName("two Break on Genome Quiz Test 03")
    fun twoBreakOnGenomeQuizTest03() {

        val genomeString = """
            (+36 +11 -59 +32 -60 +8 +56 -41 -46 -62 -10 -33 +51 +61 +38 +14 -5 -3 +58 -7 -22 -15 +47 +1 -17 +40 +45 +13 -21 -57 +37 -50 -48 -23 +6 -43 -52 -34 -31 -49 +28 -44 -18 +24 +55 +64 -19 -63 -27 +12 -16 +42 +4 -39 +9 +35 +26 -2 -29 +53 +30 +25 -54 -20)
        """.trimIndent()
        val breakListString = """
            87, 36, 1, 94
        """.trimIndent()

        val genomeGraph = genomeString.removePrefix("(")
            .removeSuffix(")")
            .split(" ")
            .map { it.toInt() }
        val breakList = breakListString.split(", ").map { it.toInt()}


        val result = twoBreak.twoBreakOnGenome(listOf(genomeGraph), breakList)
        printChromosomes(result)
        // (+36 +11 -59 +32 -60 +8 +56 -41 -46 -62 -10 -33 +51 +61 +38 +14 -5 -3 +58 -7 -22 -15 +47 -18 +24 +55 +64 -19 -63 -27 +12 -16 +42 +4 -39 +9 +35 +26 -2 -29 +53 +30 +25 -54 -20)(+1 -17 +40 +45 +13 -21 -57 +37 -50 -48 -23 +6 -43 -52 -34 -31 -49 +28 -44)
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
//       println(str.toString())
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