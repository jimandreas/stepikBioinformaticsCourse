@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.DistancesBetweenLeaves
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
import kotlin.test.assertEquals


/**
 *
 * See also:  Ultrametric Trees - Unweighted Pair Group Method with Arithmetic Mean  UPGMA
 * Youtube: https://www.youtube.com/watch?v=27aOeJ2hSwA
 *
 * and also: Creating a Phylogenetic Tree
 * https://www.youtube.com/watch?v=09eD4A_HxVQ
 *
 * Limb Length:
 * stepik: https://stepik.org/lesson/240339/step/8?unit=212685
 * rosalind: http://rosalind.info/problems/ba7d/
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

internal class S07c06p08UPGMAtest {

    lateinit var ll: Phylogeny
    lateinit var dbl: DistancesBetweenLeaves
    lateinit var upgma: UPGMA


    @BeforeEach
    fun setUp() {
        ll = Phylogeny()
        dbl = DistancesBetweenLeaves()
        upgma = UPGMA()
    }

    @AfterEach
    fun tearDown() {
    }

    // example from:
    // https://www.youtube.com/watch?v=8-8eZdeqUsw
    // C100 Week 5 Discussion
    @Test
    @DisplayName("UPGMA sample from youtube test")
    fun upgmaSampleFromYoutubeTest() {
        val sampleInput = """
5
0 10 12 8 7
10 0 4 4 14
12 4 0 6 16
8 4 6 0 12
7 14 16 12 0
        """.trimIndent()

        val expectedOutputString = """
0->5:7.000
1->6:8.833
2->4:5.000
3->4:5.000
4->2:5.000
4->3:5.000
4->5:2.000
5->0:7.000
5->4:2.000
5->6:1.833
6->5:1.833
6->1:8.833
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
                assertEquals(distanceA, distanceB, "Failed distance equivalence")
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