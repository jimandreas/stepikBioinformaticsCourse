@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems

import algorithms.LongestPathInDirectedGraph

/**
Code Challenge: Solve the Longest Path in a DAG Problem.

Input: An integer representing the starting node to consider in a graph,
followed by an integer representing the ending node to consider,
followed by a list of edges in the graph. The edge notation "0->1:7"
indicates that an edge connects node 0 to node 1 with weight 7.
You may assume a given topological order corresponding to nodes in increasing order.

Output: The length of a longest path in the graph, followed by a longest path.
(If multiple longest paths exist, you may return any one.)
 *
 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba5d/
 * book (5.8):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */


fun main() {

    val input = """
        0
        45
        14->44:14
        6->19:6
        11->38:36
        21->34:29
        9->20:17
        14->26:26
        10->34:29
        18->39:8
        25->27:3
        11->32:6
        10->20:20
        8->35:27
        4->42:4
        27->41:23
        31->34:15
        27->44:38
        25->41:0
        30->31:13
        1->24:18
        8->38:36
        19->22:26
        5->41:17
        12->30:12
        6->9:12
        5->14:11
        0->14:32
        39->43:11
        8->12:21
        11->28:30
        1->22:23
        21->22:19
        35->36:22
        7->23:18
        1->8:5
        26->37:1
        26->34:19
        5->30:4
        2->42:2
        26->38:34
        6->16:37
        6->35:24
        22->38:13
        7->24:37
        21->44:37
        19->37:21
        9->30:13
        13->23:12
        21->43:1
        16->46:38
        24->35:27
        16->44:15
        9->12:1
        11->22:23
        0->12:20
        11->20:14
        38->42:37
        15->22:26
        32->42:13
        32->44:21
        24->47:5
        24->39:6
        28->37:29
        29->44:30
        34->46:23
        12->22:12
        34->45:5
        28->38:33
        4->31:2
        4->15:28
        22->41:34
        17->36:16
        7->15:7
        26->27:20
        17->35:24
        2->24:6
        17->30:39
        13->33:30
        17->18:6
        16->17:20
        26->29:39
        37->40:38
        44->45:17
        3->25:12
            """.trimIndent()

    val lpdag = LongestPathInDirectedGraph()


    val lpDAG = LongestPathInDirectedGraph()
    val listDag = lpDAG.readDirectedGraphInfo(input)

    lpDAG.backtrack(lpDAG.startNodeNum, -1, 0, listDag)

    val results = lpDAG.outputDAG(listDag)

    //println(results)
    val maxWeight = results.first
    val connList = results.second

    println(maxWeight)
    val connString = connList.joinToString("->")
    println(connString)

    /*
    144
    0->14->26->29->44->45
     was correct (rosalind)
     */

}


