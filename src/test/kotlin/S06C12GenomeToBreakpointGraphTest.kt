@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.GenomesToBreakpointGraph
import kotlin.test.assertEquals

/**
 *

From Genomes to the Breakpoint Graph

Various functions that handle pieces of the breakpoint graph processing.

 * See also:
 * stepik:
 * @link: https://stepik.org/lesson/240327/step/4?unit=212673
 * https://stepik.org/lesson/240327/step/5?unit=212673
 * https://stepik.org/lesson/240327/step/7?unit=212673
 * https://stepik.org/lesson/240327/step/8?unit=212673
 *
 * rosalind:
 * @link: http://rosalind.info/problems/ba6f/ 6g, 6h, 6i
 *
 */

internal class S06C12GenomeToBreakpointGraphTest {

    lateinit var gtbg: GenomesToBreakpointGraph
    @BeforeEach
    fun setUp() {
        gtbg = GenomesToBreakpointGraph()
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * ChromosomeToCycle 1:
     * Basic test of "Chromosome" (list of +/- digits) to pairs encoding
     * the order of breaks.
     *
     *  Sample Input:

    (+1 -2 -3 +4)

    Sample Output:

    (1 2 4 3 6 5 7 8)
     */
    @Test
    @DisplayName("chromosome To Cycle Test 01")
    fun chromosomeToCycleTest01() {

        val cString = """
            +1 -2 -3 +4
        """.trimIndent()
        val chromosome = cString.split(' ').map { it.toInt() }

        val resultString = """
            1 2 4 3 6 5 7 8
        """.trimIndent()
        val expectedResult = resultString.split(' ').map { it.toInt() }

        val result = gtbg.chromosomeToCycle(chromosome)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("chromosome To Cycle Test 02")
    fun chromosomeToCycleTest02() {

        val cString = """
            +1 +2 -3 -4 +5 -6 +7 +8 -9 +10 -11 -12 +13 -14 +15 -16 +17 +18 +19 -20 +21 -22 -23 -24 +25 +26 +27 -28 +29 -30 -31 +32 +33 +34 -35 +36 +37 +38 -39 +40 -41 -42 +43 -44 -45 -46 -47 +48 +49 -50 +51 -52 -53 -54 +55 +56 +57 -58 -59 -60 +61 +62 +63 -64 +65 +66 +67 -68 -69
        """.trimIndent()
        val chromosome = cString.split(' ').map { it.toInt() }

        val resultString = """
            1 2 3 4 6 5 8 7 9 10 12 11 13 14 15 16 18 17 19 20 22 21 24 23 25 26 28 27 29 30 32 31 33 34 35 36 37 38 40 39 41 42 44 43 46 45 48 47 49 50 51 52 53 54 56 55 57 58 60 59 62 61 63 64 65 66 67 68 70 69 71 72 73 74 75 76 78 77 79 80 82 81 84 83 85 86 88 87 90 89 92 91 94 93 95 96 97 98 100 99 101 102 104 103 106 105 108 107 109 110 111 112 113 114 116 115 118 117 120 119 121 122 123 124 125 126 128 127 129 130 131 132 133 134 136 135 138 137
        """.trimIndent()
        val expectedResult = resultString.split(' ').map { it.toInt() }

        val result = gtbg.chromosomeToCycle(chromosome)
        assertEquals(expectedResult, result)
    }

    /**
     * Code Challenge: Implement CycleToChromosome.

    Input: A sequence Nodes of integers between 1 and 2n.
    Output: The chromosome "Chromosome" containing n synteny blocks
    resulting from applying CycleToChromosome to Nodes.

    Sample Input:

    (1 2 4 3 6 5 7 8)

    Sample Output:

    (+1 -2 -3 +4)

     */

    @Test
    @DisplayName("cycle to chromosome Test 01")
    fun cycleToChromosomeTest01() {

        val cString = """
            1 2 4 3 6 5 7 8
        """.trimIndent()
        val cycle = cString.split(' ').map { it.toInt() }

        val resultString = """
            +1 -2 -3 +4
        """.trimIndent()
        val expectedResult = resultString.split(' ').map { it.toInt() }

        val result = gtbg.cycleToChromosome(cycle)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("cycle to chromosome Test 02")
    fun cycleToChromosomeTest02() {

        val cString = """
            1 2 3 4 5 6 8 7 9 10 11 12 14 13 15 16 17 18 19 20 21 22 23 24 26 25 27 28 29 30 31 32 33 34 36 35 37 38 39 40 41 42 43 44 45 46 48 47 49 50 52 51 54 53 56 55 57 58 60 59 61 62 63 64 65 66 68 67 70 69 72 71 73 74 76 75 77 78 79 80 81 82 83 84 85 86 87 88 89 90 92 91 93 94 96 95 97 98 100 99 102 101 103 104 106 105 108 107 109 110 111 112 113 114 115 116 118 117 120 119 122 121 123 124 125 126 128 127 130 129 132 131 133 134 136 135 137 138 140 139
        """.trimIndent()
        val cycle = cString.split(' ').map { it.toInt() }

        val resultString = """
            +1 +2 +3 -4 +5 +6 -7 +8 +9 +10 +11 +12 -13 +14 +15 +16 +17 -18 +19 +20 +21 +22 +23 -24 +25 -26 -27 -28 +29 -30 +31 +32 +33 -34 -35 -36 +37 -38 +39 +40 +41 +42 +43 +44 +45 -46 +47 -48 +49 -50 -51 +52 -53 -54 +55 +56 +57 +58 -59 -60 -61 +62 +63 -64 -65 -66 +67 -68 +69 -70
        """.trimIndent()
        val expectedResult = resultString.split(' ').map { it.toInt() }

        val result = gtbg.cycleToChromosome(cycle)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("cycle to chromosome Test 03")
    fun cycleToChromosomeTest03() {

        val cString = """
            1 2 3 4 6 5 8 7 9 10 12 11 14 13 16 15 17 18 20 19 22 21 23 24 26 25 28 27 29 30 32 31 34 33 36 35 37 38 39 40 41 42 43 44 46 45 47 48 49 50 51 52 53 54 56 55 57 58 60 59 62 61 63 64 66 65 67 68 70 69 72 71 73 74 75 76 78 77 80 79 82 81 84 83 85 86 87 88 89 90 91 92 94 93 96 95 98 97 100 99 102 101 103 104 105 106 107 108 109 110 112 111 113 114 115 116 118 117 120 119 121 122 124 123 126 125 128 127 129 130 132 131 134 133 135 136 138 137
        """.trimIndent()
        val cycle = cString.split(' ').map { it.toInt() }


        val result = gtbg.cycleToChromosome(cycle).joinToString(" ") { String.format("%+d", it) }
        println("($result)")
    }


    /**
     * Code Challenge: Implement ColoredEdges.

    Input: A genome P.
    Output: The collection of colored edges in the genome graph of P in the form (x, y).

    Sample Input:

    (+1 -2 -3)(+4 +5 -6)

    Sample Output:

    (2, 4), (3, 6), (5, 1), (8, 9), (10, 12), (11, 7)
     */

    @Test
    @DisplayName("colored Edges Test 01")
    fun coloredEdgesTest01() {

        val listOfLists : MutableList<MutableList<Int>> = mutableListOf()
        val cString = """
            (+1 -2 -3)(+4 +5 -6)
        """.trimIndent()
        val cycle = cString.split("(", ")(", ")")
        for (i in 1 until cycle.size) {
            val tempList = cycle[i].map { it.toInt() }
        }

        val resultString = """
            (2, 4), (3, 6), (5, 1), (8, 9), (10, 12), (11, 7)
        """.trimIndent()
        val expectedResult = resultString.split("(|,|), (|)").map { it.toInt() }

//        val result = gtbg.cycleToChromosome(cycle)
//        assertEquals(expectedResult, result)
    }


}