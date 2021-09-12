@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.*

/**
 *

Distances Between Leaves Problem: Compute the distances between leaves in a weighted tree.

Input:  An integer n followed by the adjacency list of a weighted tree with n leaves.

Output: An n x n matrix (di,j), where di,j is the length of the path between leaves i and j.

 * See also:
 * stepik: https://stepik.org/lesson/240335/step/12?unit=212681
 * rosalind: http://rosalind.info/problems/ba7a/
 */

/**

Code Challenge: Solve the Distances Between Leaves Problem.
The tree is given as an adjacency list of a graph whose leaves
are integers between 0 and n - 1; the notation a->b:c
means that node a is connected to node b by an edge of weight c.
The matrix you return should be space-separated.

 */
internal class S07C02p12DistancesBetweenLeavesTest {

    lateinit var dbl: DistancesBetweenLeaves


    @BeforeEach
    fun setUp() {
        dbl = DistancesBetweenLeaves()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Distances Between Leaves sample test")
    fun distancesBetweenLeavesSampleTest() {
        val sampleInput = """
            4
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

        val expectedOutput = """
            0	13	21	22
            13	0	12	13
            21	12	0	13
            22	13	13	0
        """.trimIndent()

        val r = sampleInput.reader().readLines().toMutableList()
        val matrixSize = r[0].toInt()
        r.removeAt(0)
        val edges = parseSampleInput(r)

        // distancesBetweenLeaves(leafCount: Int, g: MutableMap<Int, MutableList<Pair<Int, Int>>>)
        val result = dbl.distancesBetweenLeaves(matrixSize, edges)
        printit(matrixSize, result)
    }

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

}