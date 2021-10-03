@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.DistancesBetweenLeaves
import algorithms.NeighborJoining
import algorithms.Phylogeny
import algorithms.UPGMA
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.text.NumberFormat
import java.util.*
import kotlin.test.assertEquals

/**
 *
 * See also:  Neighbour Joining   Anders Gorm Pedersen
 * Youtube: https://www.youtube.com/watch?v=Dj24mCLQYUE
 *
 * Limb Length:
 * stepik: https://stepik.org/lesson/240340/step/7?unit=212686
 * rosalind: http://rosalind.info/problems/ba7e/
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */


internal class S07c07p07NeighborJoiningTest {

    lateinit var ll: Phylogeny
    lateinit var dbl: DistancesBetweenLeaves
    lateinit var upgma: UPGMA
    lateinit var nj : NeighborJoining


    @BeforeEach
    fun setUp() {
        ll = Phylogeny()
        dbl = DistancesBetweenLeaves()
        upgma = UPGMA()
        nj = NeighborJoining()
    }

    @AfterEach
    fun tearDown() {
    }

    // example from:
    // https://www.youtube.com/watch?v=Dj24mCLQYUE
    // Neighbour Joining  Anders Gorm Pedersen

    @Test
    @DisplayName("Neighbor Joining sample from youtube test")
    fun neighborJoiningSampleFromYoutubeTest() {
        val sampleInput = """
4
0 17 21 27
17 0 12 18
21 12 0 14
27 18 14 0
        """.trimIndent()

        val expectedOutputString = """
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = upgma.upgmaStart(matrixSize, m)

        printGraph(result)

        //checkEdgesAreEqual(expectedGraph, result)


    }

    // example from:
    // https://stepik.org/lesson/240340/step/7?unit=212686

    @Test
    @DisplayName("Neighbor Joining sample test")
    fun neighborJoiningSampleTest() {
        val sampleInput = """
4
0	23	27	20
23	0	30	28
27	30	0	30
20	28	30	0
        """.trimIndent()

        val expectedOutputString = """
0->4:8.000
1->5:13.500
2->5:16.500
3->4:12.000
4->5:2.000
4->0:8.000
4->3:12.000
5->1:13.500
5->2:16.500
5->4:2.000
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = upgma.upgmaStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)


    }

    private fun printGraph(g: Map<Int, Map<Int, Float>>) {

        val fmt = NumberFormat.getNumberInstance(Locale.ROOT)
        fmt.maximumFractionDigits = 3
        fmt.minimumFractionDigits = 3

        for (e in g) {
            val key = e.key
            val theMap = e.value

            for (c in theMap) {
                print("$key->")
                val f = fmt.format(c.value)
                println("${c.key}:$f")
            }
        }
    }


    /**
     * compare two maps of structure:
     *  MutableMap<Int, Map<Int, Float>> = mutableMapOf()
     */
    private fun checkEdgesAreEqual(a: Map<Int, Map<Int, Float>>, b: Map<Int, Map<Int, Float>>) {

        // test 1 - number of keys match
        val aSorted = a.toSortedMap()
        val bSorted = b.toSortedMap()

        assertEquals(aSorted.keys.size, bSorted.keys.size)

        // test 2 - the keys belong to equivalent sets
        for (baseNodeMapA in aSorted) {
            kotlin.test.assertTrue(bSorted.containsKey(baseNodeMapA.key), "Failed base Node equivalence $baseNodeMapA.key")
        }

        // test 3 - for each key, the maps are equivalent sets
        for (ele in aSorted) {
            // test 1A - number of keys match
            val mapA = aSorted[ele.key]
            val mapB = bSorted[ele.key]
            assertEquals(mapA!!.keys.size, mapB!!.keys.size, "Failed equal key size")

            // test 2A - the keys belong to equivalent sets
            for (ele2 in mapA) {
                kotlin.test.assertTrue(mapB.containsKey(ele2.key), "Failed key set equivalence for next node")
            }

            // test 3A - for each key, the maps are equivalent sets
            for (ele2 in mapA) {
                val distanceA = mapA[ele2.key]
                val distanceB = mapB[ele2.key]
                assertEquals(distanceA!!.toDouble(), distanceB!!.toDouble(), 0.01)
            }
        }
    }


    /**
     * convert a string of the form (with tab or space separated numbers)
    0     13    21    22
    13     0    12    13
    21    12     0    13
    22    13    13     0

    into a D2Array given
    @param matrixSize - the size N of an NxN matrix
     */

    private fun parseMatrixInput(matrixSize: Int, lines: List<String>): D2Array<Float> {
        val theMatrix = mk.d2array(matrixSize, matrixSize) { 0f }

        for (i in 0 until matrixSize) {
            val l = lines[i].trim().split("\t", " ", "  ", "   ", "    ")
            for (j in 0 until matrixSize) {
                theMatrix[i, j] = l[j].toFloat()
            }
        }
        return theMatrix
    }

    private fun parseSampleInput(nodeToNodePlusDistance: List<String>): MutableMap<Int, MutableMap<Int, Float>> {
        val edgeMap: MutableMap<Int, MutableMap<Int, Float>> = mutableMapOf()
        for (e in nodeToNodePlusDistance) {
            val sourceDest = e.split("->")
            val destNodeAndWeightPair = sourceDest[1].split(":")
            val sourceNodeNumber = sourceDest[0].toInt()
            if (edgeMap.containsKey(sourceNodeNumber)) {
                edgeMap[sourceNodeNumber]!![destNodeAndWeightPair[0].toInt()] = destNodeAndWeightPair[1].toFloat()
            } else {
                edgeMap[sourceNodeNumber] = mutableMapOf(Pair(destNodeAndWeightPair[0].toInt(), destNodeAndWeightPair[1].toFloat()))
            }
        }
        return edgeMap
    }

    /**
     * pretty print a 2D matrix
     */
    private fun printMatrix(matrixSize: Int, gArr: D2Array<Int>) {
        val outStr = StringBuilder()
        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                val numVal = String.format("%5d", gArr[i, j])
                outStr.append(numVal)
                if (j < matrixSize - 1) {
                    outStr.append(" ")
                }
            }
            outStr.append("\n")
        }
        println(outStr.toString())
    }
}