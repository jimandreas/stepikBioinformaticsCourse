@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody", "unused")

package util

/**
 * find an Eulerian Path through a list of nodes with connected edges.
 *
 * See also:
 * @link: https://www.youtube.com/watch?v=8MpoO2zA2l4
 * @link: https://github.com/williamfiset/Algorithms
 *        EulerianPathDirectedEdgesAdjacencyList.java
 */
data class EulerConnectionData(val nodeNum: Int, val connections: MutableList<Int>)

class EulerianPath() {
    private var graph: List<List<Int>> = mutableListOf()
    private var n = 0
    private var edgeCount = 0
    private lateinit var inEdges: Array<Int>
    private lateinit var outEdges: Array<Int>
    private var path: MutableList<Int> = mutableListOf()

    /**
     * @param graph - a list of nodes each with a list of connections
     *   assumes list is zero based and the index into the first
     *   list is the same as the node number
     */
    fun setGraph(graph: List<List<Int>>) {
        this.graph = graph
        n = graph.size
        inEdges = Array(n){0}
        outEdges = Array(n){0}
    }


    fun solveEulerianPath() : List<Int> {
        initializeVariables()

        if (!errorCheckGraph()) {
            return listOf(0)
        }

        depthFirstSearch(scanForStartingNode())

        return path
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
            val thisNode= graph[nodeNum]
            val nextNode = thisNode[--outEdges[nodeNum]]
            depthFirstSearch(nextNode)
        }
        path.add(0,nodeNum) // prepend the current node to solution list
    }


    /**
     * scan a list of node connections (node1 -> node2,node3)
     * and return a map of the node to an EulerConnectionData list
     */
    fun eulerCycleMap(connList: String): List<EulerConnectionData> {
        val ep = EulerianPath()
        val list : MutableList<EulerConnectionData> = mutableListOf()
        val reader = connList.reader()
        val lines = reader.readLines()

        for (s in lines) {
            val connData = ep.eulerCycleParseLine(s)
            list.add(connData)
        }
        return list
    }

    /**
     * given a line of the form:
     * 106 -> 107,395,632,888
     * return a EulerConnectionData record with 106 as the nodeNum and the list of connections
     */
    fun eulerCycleParseLine(line: String): EulerConnectionData {

        val nodeNumber = parseInt(line.substring(0, line.indexOfFirst { it == ' ' }))

        val connectionList = line.substringAfter("-> ").split(',')
        val resultMap: MutableList<Int> = mutableListOf()
        for (conn in connectionList) {
            resultMap.add(parseInt(conn))
        }

        return EulerConnectionData(nodeNumber,  resultMap)
    }

    /**
     * convert EulerConnectionData list to an array list
     *     Error check the list - it should span 0 to N matching the node numbers in the
     *     eulerDataList after sorting.
     */
    fun eulerCycleConvertData(eulerDataList: List<EulerConnectionData>): List<List<Int>> {
        val outList : MutableList<MutableList<Int>> = mutableListOf()
        val sortedList = eulerDataList.sortedBy {
            it.nodeNum
        }
        // error check the sorted list.   The node num must match the array position
        val n = sortedList.size
        if ((sortedList[0].nodeNum != 0) || (sortedList[n-1].nodeNum != n-1)) {
            println("ERROR in euler list conversion - data doesn't match position")
        }
        for (entry in sortedList) {
            outList.add(entry.connections)
        }
        return outList
    }

    private fun parseInt(s: String): Int {
        var s1 = s
        if (s1[0] == ' ') {
            s1 = s.substring(1, s1.length)
        }
        return try {
            s1.toInt()
        } catch (e: RuntimeException) {
            println("ERROR ON PARSE")
            0
        }
    }
}