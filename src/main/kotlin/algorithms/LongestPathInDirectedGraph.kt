@file:Suppress(
    "SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody", "unused",
    "MemberVisibilityCanBePrivate", "LiftReturnOrAssignment"
)

package algorithms

import java.lang.Integer.max

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
 * stepik: @link: https://stepik.org/lesson/240303/step/7?unit=212649
 * rosalind: @link: http://rosalind.info/users/jimandreas/
 * book (5.8):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

class LongestPathInDirectedGraph {
    // each conn in the connList is a pair of { nodeNum, connWeight }

    var startNodeNum = 0
    var endNodeNum = 0

    data class Edge(
        val nodeNum: Int,
        val connList: MutableList<Pair<Int, Int>>,
        var maxConn: Pair<Int, Int> = Pair(-1, -1)
    )

    /**
     * backtrack setup
     *   Like in the LongestCommonSubsequenceLCS algorithmm -
     *   the backtrack algorithm sets up the path with the highest weights.
     *   The outputDAG routine (below) then follows this in reverse.
     */

    fun backtrack(node: Int, fromNode: Int, weight: Int, list: MutableMap<Int, Edge>): Int {

        val thisNode = list[node]
        if (thisNode == null) {
            println("ERROR node $node is not in list")
            return 0
        }
        var maxWeight = weight
        /*
         * for each edge, recursively call backtrack adding up the weights
         */

        for (anEdge in thisNode.connList) {
            val toNode = anEdge.first
            val toNodeWeight = anEdge.second
            val maxConn = thisNode.maxConn

            /*
             * if the toNode is an endnode, then just create its max pair
             */
            val listEntry = list[toNode]
            if (listEntry != null && listEntry.connList.size == 0) {
                maxWeight += toNodeWeight
                if (listEntry.maxConn.second < maxWeight) {
                    listEntry.maxConn = Pair(node, maxWeight)
                }
                if (maxConn.second < maxWeight) {
                    thisNode.maxConn = Pair(fromNode, maxWeight)
                }
            } else {
                // otherwise recurse further
                val totalWeight = backtrack(toNode, node, weight + toNodeWeight, list)
                if (maxConn.second < totalWeight) {
                    thisNode.maxConn = Pair(fromNode, totalWeight)
                }
                maxWeight = max(maxWeight, totalWeight)
            }
        }
        return maxWeight
    }


    /**
     * output the highest weight path in the list from the start node to the end node
     *    The graph has already been walked recursively and the highest weight noted.
     *    Now just traverse the graph in reverse and build the connection list.
     */
    fun outputDAG(list: MutableMap<Int, Edge>): Pair<Int, List<Int>> {

        val weight = list[endNodeNum]!!.maxConn.second   // already calculated

        var curNodeNum = endNodeNum
        val outList: MutableList<Int> = mutableListOf()
        outList.add(curNodeNum)

        while (curNodeNum != startNodeNum) {
            val node = list[curNodeNum]
            if (node != null) {
                curNodeNum = node.maxConn.first
                outList.add(0, curNodeNum) // insert the node at the start of the list
            } else {
                println("ERROR no node at $curNodeNum")
                break
            }
        }

        return Pair(weight, outList)
    }


    /**
     * Solve the Longest Path in a DAG Problem
     * scan a well formed string of

    0   // starting node
    4   // ending node
    0->1:7   // node connection with weight
    0->2:4

    No error checking is performed.
     */
    fun readDirectedGraphInfo(connList: String): MutableMap<Int, Edge> {

        val list: MutableMap<Int, Edge> = mutableMapOf()
        val reader = connList.reader()
        val lines = reader.readLines()

        startNodeNum = lines[0].toInt()
        endNodeNum = lines[1].toInt()

        /*
         * add connections - if the node already exists in the list, just add new pairs of
         * node nums and weights
         */
        for (i in 2 until lines.size) {
            val arc = lines[i].split("->")
            val nodeNum = arc[0].toInt()

            val toNodeWeight = arc[1].split(":")
            val nodeAndWeight = Pair(toNodeWeight[0].toInt(), toNodeWeight[1].toInt())
            val connNode = nodeAndWeight.first

            if (!list.containsKey(nodeNum)) {
                list[nodeNum] = Edge(nodeNum, mutableListOf(nodeAndWeight))
            } else {
                list[nodeNum]!!.connList.add(nodeAndWeight)
            }
            // add a placeholder for all edges
            if (!list.containsKey(connNode)) {
                list[connNode] = Edge(connNode, mutableListOf())
            }
        }

        /*
         * now do a list fixup - insert any missing nodes with a dummy entry
         */

        for (i in startNodeNum..endNodeNum) {
            if (!list.containsKey(i)) {
                list[i] = Edge(i, mutableListOf()) // dummy entry
            }

        }
        return list
    }

}