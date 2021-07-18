@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.LongestPathInDirectedGraph
import kotlin.test.assertContentEquals
import kotlin.test.assertNotNull

/**
 * Code Challenge: Find the length of a longest path in the Manhattan Tourist Problem.
 *
 * See also:
 * stepik: @link: https://stepik.org/lesson/240301/step/10?unit=212647
 * rosalind: @link: http://rosalind.info/problems/ba5b/
 * book (5.6):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

internal class S05c08c07LongestPathInDirectedGraphTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    val lpDAG = LongestPathInDirectedGraph()


    @Test
    @DisplayName("longest path in directed graph - parse input test")
    fun longestCommonSubsequenceParseInputTest() {

        val input = """
            0
            2
            0->1:7
            0->2:9
        """.trimIndent()

        val listDag = lpDAG.readDirectedGraphInfo(input)

        // class variables are set properly:
        assertEquals(0, lpDAG.startNodeNum)
        assertEquals(2, lpDAG.endNodeNum)
        // check node 0 contents
        assertEquals(3, listDag.size)
        assertNotNull(listDag[0])
        assertEquals(2, listDag[0]!!.connList.size)

        val connList = listDag[0]!!.connList
        assertEquals(Pair(2,9), connList[1])
    }

    @Test
    @DisplayName("longest path in directed graph - backtrack test")
    fun longestCommonSubsequenceBacktrackTest() {

        val input = """
            0
            4
            0->1:7
            0->2:4
            2->3:2
            1->4:1
            3->4:3
        """.trimIndent()

        val listDag = lpDAG.readDirectedGraphInfo(input)

        lpDAG.backtrack(lpDAG.startNodeNum, -1, 0, listDag)

        val results = lpDAG.outputDAG(listDag)

        //println(results)

        val maxWeight = results.first
        val connList = results.second

        assertEquals(9, maxWeight)
        assertContentEquals(listOf(0, 2, 3, 4), connList)
    }


    @Test
    @DisplayName("longest path in directed graph - backtrack test02")
    fun longestCommonSubsequenceBacktrackTest02() {

        val input = """
        0
        44
        6->26:32
        10->39:30
        26->28:24
        3->16:19
        10->35:35
        10->37:19
        10->31:36
        10->33:32
        10->32:4
        15->23:0
        15->21:0
        22->24:0
        22->27:31
        1->3:36
        5->43:37
        8->30:23
        19->34:11
        12->13:38
        39->40:35
        12->15:29
        27->29:13
        1->42:31
        24->25:2
        1->10:4
        4->30:11
        13->35:17
        24->28:2
        23->25:37
        31->43:7
        31->40:17
        3->28:2
        5->12:39
        5->11:37
        3->4:4
        2->31:23
        14->29:13
        19->27:21
        27->36:20
        31->33:23
        30->40:27
        28->42:29
        21->35:33
        21->37:5
        20->37:24
        2->9:38
        0->14:19
        4->20:0
        1->41:8
        8->14:28
        19->20:13
        4->43:3
        14->31:25
        14->30:22
        13->41:19
        13->40:32
        14->35:10
        10->11:5
        14->38:23
        2->23:9
        2->25:1
        24->40:37
        12->38:38
        20->23:34
        20->21:29
        12->30:10
        12->37:12
        29->44:30
        33->35:15
        33->37:22
        0->36:8
        37->38:17
        10->29:13
        17->44:11
        6->14:5
        10->22:8
        22->37:19
        22->34:3
        32->43:4
        15->36:28
        11->35:20
        2->16:27
        7->10:22
        11->31:19
        16->41:24
        15->30:25
        32->37:29
        0->27:9
        0->28:7
        32->38:0
        12->43:5
        22->35:37
        24->30:7
        24->32:19
        24->38:38
    """.trimIndent()

        val listDag = lpDAG.readDirectedGraphInfo(input)

        lpDAG.backtrack(lpDAG.startNodeNum, -1, 0, listDag)

        val results = lpDAG.outputDAG(listDag)

        //println(results)
        val maxWeight = results.first
        val connList = results.second

        assertEquals(62, maxWeight)
        assertContentEquals(listOf(0, 14, 29, 44), connList)
    }

}