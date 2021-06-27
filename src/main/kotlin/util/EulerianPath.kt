@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody")
package util

import java.util.*


/**
 * find an Eulerian Path through a list of nodes with connected edges.
 *
 * @param graph - a list of nodes each with a list of connections
 *   assumes list is zero based and the index into the first
 *   list is the same as the node number
 *
 * See also:
 * @link: https://www.youtube.com/watch?v=8MpoO2zA2l4
 * @link: https://github.com/williamfiset/Algorithms
 *        EulerianPathDirectedEdgesAdjacencyList.java
 */
class EulerianPath(private val graph: List<List<Int>>) {
    private var n = 0
    private var edgeCount = 0
    private val inEdges: MutableList<Int> = mutableListOf()
    private val outEdges: MutableList<Int> = mutableListOf()
    private var path: LinkedList<Int>

    init {
        n = graph.size
        path = LinkedList()
    }

    fun solveEulerianPath() : List<Int> {
        initializeVariables()

        if (!errorCheckGraph()) {
            return listOf(0)
        }

        depthFirstSearch(scanForStartingNode())

        val outList : MutableList<Int> = mutableListOf()
        while (!path.isEmpty()) {
            outList.add(path.removeFirst())
        }
        return outList
    }

    /**
     * set up the count of "out" and "in" edges, and the overall edgecount
     */
    private fun initializeVariables() {
        for (i in 0 until n) {
            for (nodeNum in graph[i]) {
                inEdges[nodeNum]++
                outEdges[i]++
                edgeCount++
            }
        }
    }

    /**
     * do error checking on the graph
     *    it is allowable to have one start and one end node with a mismatch of one edge each
     *
     *  The presence of more than one "in" versus "out" edges is an error, as
     *  is more than one "start" or "end" nodes
     */
    private fun errorCheckGraph(): Boolean {
        if (edgeCount == 0) {
            return false
        }
        var startNodes = 0
        var endNodes = 0
        for (i in 0 until n) {
            if (outEdges[i] - inEdges[i] > 1 || inEdges[i] - outEdges[i] > 1) {
                return false
            } else if (outEdges[i] - inEdges[i] == 1) {
                startNodes++
            } else if (inEdges[i] - outEdges[i] == 1) {
                endNodes++
            }
        }

        if ((startNodes == 0 && endNodes == 0) || (startNodes == 1 && endNodes == 1)) {
            return true
        }
        println("Bad graph : mismatched startNodes = $startNodes, endNodes = $endNodes")
        return false
    }

    /**
     * scan for a starting node.
     *    A "start node" with more outgoing edges has preference
     *    otherwise just return a node that has at least one
     *    outgoing edge.   Assumption here is that the graph
     *    could be badly formed and some connection lists have
     *    zero entries (?) and are orphaned nodes
     */
    private fun scanForStartingNode(): Int {
        var candidateNode = 0
        for (i in 0 until n) {
            if (outEdges[i] - inEdges[i] == 1) {
                return i
            }
            if (outEdges[i] > 0) {
                candidateNode = i
            }
        }
        return candidateNode
    }

    private fun depthFirstSearch(nodeNum: Int) {
        while (outEdges[nodeNum] != 0) {
            val nextNode = graph[nodeNum][outEdges[nodeNum]--]
            depthFirstSearch(nextNode)
        }
        path.addFirst(nodeNum)
    }
}