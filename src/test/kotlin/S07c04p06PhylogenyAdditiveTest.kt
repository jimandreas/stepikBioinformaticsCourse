@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.DistancesBetweenLeaves
import algorithms.Phylogeny
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 *
 * See also:
 * stepik: https://stepik.org/lesson/240337/step/6?unit=212683
 * rosalind: http://rosalind.info/problems/ba7c/
 */

/**

Code Challenge: Implement AdditivePhylogeny to solve the Distance-Based Phylogeny Problem.

Input: An integer n followed by a space-separated n x n distance matrix.
Output: A weighted adjacency list for the simple tree fitting this matrix.

Note on formatting: The adjacency list must have consecutive integer node labels
starting from 0. The n leaves must be labeled 0, 1, ..., n - 1 in order
of their appearance in the distance matrix. Labels for internal nodes
may be labeled in any order but must start from n and increase consecutively.

 */

internal class S07c04p06PhylogenyAdditiveTest {

    lateinit var ll: Phylogeny
    lateinit var dbl: DistancesBetweenLeaves


    @BeforeEach
    fun setUp() {
        ll = Phylogeny()
        dbl = DistancesBetweenLeaves()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Distances Between Leaves sample test")
    fun phylogenyLimbLengthSampleTest() {
        val sampleInput = """
            4
            0	13	21	22
            13	0	12	13
            21	12	0	13
            22	13	13	0
        """.trimIndent()

        val expectedOutputString = """
            0->4:11
            1->4:2
            2->5:6
            3->5:7
            4->0:11
            4->1:2
            4->5:4
            5->4:4
            5->3:7
            5->2:6
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = ll.additivePhylogenyStart(matrixSize, m)

        printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)

    }

    private fun printGraph(g: Map<Int, List<Pair<Int, Int>>>) {
        for (e in g) {
            val key = e.key
            val connList = e.value

            for (c in connList) {
                print("$key->")
                println("${c.first}:${c.second}")
            }
        }
    }

    @Test
    @DisplayName("Distances Between Leaves hexadecimal test 01")
    fun phylogenyLimbLengthHexTest01() {
        val sampleInput = """
            6
            00 f0 c8 84 e2 c1
            f0 00 38 74 12 31
            c8 38 00 4c 2a 09
            84 74 4c 00 66 45
            e2 12 2a 66 00 23
            c1 31 09 45 23 00
        """.trimIndent()

        // update when finished debugging
        val expectedOutputString = """
            0->6:128
            1->7:16
            2->8:8
            3->6:4
            4->7:2
            5->8:1
            6->0:128
            6->3:4
            6->8:64
            7->1:16
            7->4:2
            7->8:32
            8->2:8
            8->5:1
            8->6:64
            8->7:32
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input, radixIn = 16)
        printit(6, m)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = ll.additivePhylogenyStart(matrixSize, m)

        printGraph(result)

        assertEquals(9, ll.nextNode)
        checkEdgesAreEqual(expectedGraph, result)

    }


    @Test
    @DisplayName("Distances Between Leaves contrived test")
    fun phylogenyLimbLengthContrivedTest01() {

        // this is similar to the previous matrix,
        //    but nodes 1 and 2 (2 and 3 in the diagrams)
        //    have been swapped
        val contrivedSampleInput = """
            4
            0->4:11
            1->5:6
            2->4:2
            3->5:7
            4->0:11
            4->2:2
            4->5:4
            5->4:4
            5->3:7
            5->1:6
        """.trimIndent()

        val r = contrivedSampleInput.reader().readLines().toMutableList()
        val matrixSize = r[0].toInt()
        r.removeAt(0)
        val hackedEdges = parseSampleInput(r)
        // now convert the edges to a distance matrix
        val theInputMatrix = dbl.distancesBetweenLeaves(matrixSize, hackedEdges)
        printit(matrixSize, theInputMatrix)

        // now hand the distance matrix to the additivePhylogeny algo
        val treeMapResult = ll.additivePhylogenyStart(matrixSize, theInputMatrix)

        checkEdgesAreEqual(hackedEdges, treeMapResult)

        val theResultMatrix = dbl.distancesBetweenLeaves(matrixSize, treeMapResult)
        printit(matrixSize, theResultMatrix)
        assertEquals(theInputMatrix, theResultMatrix)
    }

    /**
     * compare two maps of structure:
     *  MutableMap<Int, List<Pair<Int, Int>>> = mutableMapOf()
     *
     * sort both keys and list entries before comparing
     */
    private fun checkEdgesAreEqual(a: Map<Int, List<Pair<Int, Int>>>, b: Map<Int, List<Pair<Int, Int>>>) {
        val a2 = ll.sortMapAndDistanceLists(a)
        val b2 = ll.sortMapAndDistanceLists(b)
        assertEquals(a2.size, b2.size)
        for (entry in a2.keys) {
            assertContentEquals(a2[entry], b2[entry] )
        }
    }



    /**
     * convert a string of the form (with tab or space separated numbers)
    0    13    21    22
    13     0    12    13
    21    12     0    13
    22    13    13     0

    into a D2Array given
    @param matrixSize - the size N of an NxN matrix
     */

    private fun parseMatrixInput(matrixSize: Int, lines: List<String>, radixIn : Int = 10): D2Array<Int> {
        val theMatrix = mk.d2array(matrixSize, matrixSize) {0}

        for (i in 0 until matrixSize) {
            val l = lines[i].trim().split("\t", " ")
            for (j in 0 until matrixSize) {
                theMatrix[i, j] = l[j].toInt(radix = radixIn)
            }
        }
        return theMatrix
    }

    fun parseSampleInput(edges: List<String>): MutableMap<Int, MutableList<Pair<Int, Int>>> {
        val edgeMap: MutableMap<Int, MutableList<Pair<Int, Int>>> = mutableMapOf()
        for (e in edges) {
            val sourceDest = e.split("->")
            val nodeAndWeight = sourceDest[1].split(":")

            val key = sourceDest[0].toInt()
            if (edgeMap.containsKey(key)) {
                edgeMap[sourceDest[0].toInt()]!!.add(Pair(nodeAndWeight[0].toInt(), nodeAndWeight[1].toInt()))
            } else {
                edgeMap[sourceDest[0].toInt()] = mutableListOf(Pair(nodeAndWeight[0].toInt(), nodeAndWeight[1].toInt()))
            }
        }
        return edgeMap
    }

    /**
     * pretty print a 2D matrix
     */
    private fun printit(matrixSize: Int, gArr: D2Array<Int>) {
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