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
        ll.fixConnList(
            fromNode = 4,
            toNode = 5,
            newToNode = 5,
            oldDistance = 400,
            newDistance = 99)

        checkEdgesAreEqual(expectedGraph, ll.theCurrentConnectionTree)

    }


    /**
     * test of:
     *   add an intermediate node betwwen nodes 4 and 5 with
     *      a required distance from 4 of 100
     */
    @Test
    @DisplayName("phylogeny INTERNAL addIntermediateNode test")
    fun phylogenyINTERNALaddIntermediateNodeTest() {

        val testTree = """
            0->4:11
            1->4:2
            2->5:6
            3->5:7
            4->0:11
            4->1:2
            4->5:400
            5->4:400
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
            4->6:100
            5->6:300
            5->3:7
            5->2:6
            6->4:100
            6->5:300
        """.trimIndent()

        val testTreeMapStrings = testTree.reader().readLines().toMutableList()
        val testTreeMap = parseSampleInput(testTreeMapStrings)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        ll.theCurrentConnectionTree = testTreeMap
        ll.nextNode = 6
        val newNodeNumber = ll.addIntermediateNode(
            matrixSize = 4,
            searchThisNodesConnections = 4,
            requiredLenToNodeFromThisNode = 100
        )

        assertEquals(6, newNodeNumber)

        checkEdgesAreEqual(expectedGraph, ll.theCurrentConnectionTree)

    }

    /**
     * test of:
     * add new internal node
     *   simply add a new node to an existing internal node.
     */
    @Test
    @DisplayName("phylogeny INTERNAL newInternalNode test")
    fun phylogenyINTERNALnewInternalNodeTest() {

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
            4->5:400
            4->6:42
            5->4:4
            5->3:7
            5->2:6
            6->4:42
        """.trimIndent()

        val testTreeMapStrings = testTree.reader().readLines().toMutableList()
        val testTreeMap = parseSampleInput(testTreeMapStrings)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        ll.theCurrentConnectionTree = testTreeMap
        ll.nextNode = 6
        ll.newInternalNode(
            matrixSize = 4,
            addToThisNodesConnections = 4,
            requiredLenToNodeFromThisNode = 42
        )

        checkEdgesAreEqual(expectedGraph, ll.theCurrentConnectionTree)

    }


    /**
     * test findNodeOrMakeOne() function.
     * Three cases:
     * 1)  tree consists of one internal node and two leaf nodes.
     *     Add an internal node with distance 4
     */
    @Test
    @DisplayName("phylogeny INTERNAL findNodeOrMakeOne test")
    fun phylogenyINTERNALfindNodeOrMakeOneTest() {

        val testTree01 = """
            0->6:1
            3->6:1
            6->0:1
            6->3:1
        """.trimIndent()

        val expected01 = """
            0->6:1
            3->6:1
            6->0:1
            6->3:1
            6->7:4
            7->6:4
        """.trimIndent()

        val testTreeMapStrings01 = testTree01.reader().readLines().toMutableList()
        val testTreeMap01 = parseSampleInput(testTreeMapStrings01)

        val expectedResultStrings01 = expected01.reader().readLines().toMutableList()
        val expectedGraph01 = parseSampleInput(expectedResultStrings01)

        ll.theCurrentConnectionTree = testTreeMap01
        ll.nextNode = 7
        val newNodeNumber01 = ll.findNodeOrMakeOne(
            matrixSize = 4,
            searchThisNodesConnections = 6,
            requiredLenToNodeFromBaseNode = 4
        )
        assertEquals(7, newNodeNumber01)
        assertEquals(8, ll.nextNode)
        checkEdgesAreEqual(expectedGraph01, ll.theCurrentConnectionTree)


    }

    @Test
    @DisplayName("phylogeny INTERNAL findNodeOrMakeOne test02")
    fun phylogenyINTERNALfindNodeOrMakeOneTest02() {
        /**
         * 2)  tree consists of two internal nodes and two leaf nodes.
         * Add an internal node with distance 2 within the internal node connection,
         * breaking the connection of 5 into a 2 distance and a 3 distance
         */
        val testTree02 = """
            0->6:1
            3->6:1
            6->0:1
            6->3:1
            6->7:5
            7->6:5
        """.trimIndent()

        val expected02 = """
            0->6:1
            3->6:1
            6->0:1
            6->3:1
            6->8:2
            7->8:3
            8->6:2
            8->7:3
        """.trimIndent()

        val testTreeMapStrings02 = testTree02.reader().readLines().toMutableList()
        val testTreeMap02 = parseSampleInput(testTreeMapStrings02)

        val expectedResultStrings02 = expected02.reader().readLines().toMutableList()
        val expectedGraph02 = parseSampleInput(expectedResultStrings02)

        ll.theCurrentConnectionTree = testTreeMap02
        ll.nextNode = 8
        val newNodeNumber02 = ll.findNodeOrMakeOne(
            matrixSize = 4,
            searchThisNodesConnections = 6,
            requiredLenToNodeFromBaseNode = 2
        )
        assertEquals(8, newNodeNumber02)
        assertEquals(9, ll.nextNode)
        checkEdgesAreEqual(expectedGraph02, ll.theCurrentConnectionTree)

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

    private fun parseSampleInput(edges: List<String>): MutableMap<Int, MutableList<Pair<Int, Int>>> {
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