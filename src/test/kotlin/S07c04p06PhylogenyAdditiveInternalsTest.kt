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

/**************************************************************************
 * tests for INTERNALS - that is to say - helper functions that
 * modify the connection tree
 ***************************************************************************/

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

internal class S07c04p06PhylogenyAdditiveInternalsTest {

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


    /**
     * test of:
     * walk the connection list for fromNode looking for the toNode.
     * Set the distance for toNode to newDistance.
     *   test setup - change the 4 to 5 node distance from 400 to 99
     */
    @Test
    @DisplayName("phylogeny INTERNAL fixConnList test")
    fun phylogenyINTERNALfixConnListTest() {

        val testTree = """
            0->4:11
            1->4:2
            2->5:6
            3->5:7
            4->0:11
            4->1:2
            4->5:400
            5->4:4
            5->3:7
            5->2:6
        """.trimIndent()

        val expectedOutputString = """
            0->4:11
            1->4:2
            2->5:6
            3->5:7
            4->0:11
            4->1:2
            4->5:99
            5->4:4
            5->3:7
            5->2:6
        """.trimIndent()

        val testTreeMapStrings = testTree.reader().readLines().toMutableList()
        val testTreeMap = parseSampleInput(testTreeMapStrings)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        ll.theCurrentConnectionTree = testTreeMap
        ll.fixConnList(4, 5, 99)

        checkEdgesAreEqual(expectedGraph, ll.theCurrentConnectionTree)

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