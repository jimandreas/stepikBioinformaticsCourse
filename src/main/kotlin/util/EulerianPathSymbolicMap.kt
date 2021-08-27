@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody", "unused",
    "MemberVisibilityCanBePrivate"
)

package util

/**
 * find an Eulerian Path through a list of nodes with connected edges.
 *
 * See also:
 * @link: https://www.youtube.com/watch?v=8MpoO2zA2l4
 * @link: https://github.com/williamfiset/Algorithms
 *        EulerianPathDirectedEdgesAdjacencyList.java
 */
//data class EulerConnectionData(val nodeNum: Int, val connections: MutableList<Int>)

class EulerianPathSymbolicMap {
    private var graph: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    private var n = 0
    private var edgeCount = 0
    lateinit var inEdgesMap: HashMap<Int, Int>
    lateinit var outEdgesMap: HashMap<Int, Int>
    private var path: MutableList<Int> = mutableListOf()

    /**
     * @param graph - a list of nodes each with a list of connections
     *   assumes list is zero based and the index into the first
     *   list is the same as the node number
     */
    fun setGraph(graph: MutableMap<Int, MutableList<Int>>) {
        this.graph = graph
        n = graph.size
        inEdgesMap = hashMapOf()
        outEdgesMap = hashMapOf()
    }


    fun solveEulerianPath(): List<Int> {
        initializeVariables()

        if (!errorCheckGraph()) {
            return listOf(0)
        }

        depthFirstSearch(scanForStartingNode())

        return path
    }

    /**
     * set up the count of "out" and "in" edges, and the overall edgecount
     *    Using hashes instead of array indexing as the input set of nodes
     *    is UNCONTROLLED.
     */
    fun initializeVariables() {

        // make sure the in and out edges maps are zeroed
        // for the Euler Path there may be no in-edge
        for (nodeNum in graph) {
            clear(outEdgesMap, nodeNum.key)
            clear(inEdgesMap, nodeNum.key)
        }

        for (nodeNumFrom in graph) {
            for (edgeTo in nodeNumFrom.value) {
                edgeCount++
                increment(outEdgesMap, nodeNumFrom.key)
                increment(inEdgesMap, edgeTo)
            }
        }
    }

    /**
     * add the [key] to the [map].   Increment the count of they key in the map.
     * @param map - a mutable map of type K
     * @param key - they key of type K to add to the map
     * @link https://www.techiedelight.com/increment-value-map-kotlin/
     */
    private fun <K> increment(map: MutableMap<K, Int>, key: K) {
        map.putIfAbsent(key, 0)
        map[key] = map[key]!! + 1
    }

    private fun <K> decrement(map: MutableMap<K, Int>, key: K) {
        map.putIfAbsent(key, 0)
        map[key] = map[key]!! - 1
    }

    private fun <K> clear(map: MutableMap<K, Int>, key: K) {
        map.putIfAbsent(key, 0)
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
        for (node in graph) {
            val i = node.key
            if (outEdgesMap[i]!! - inEdgesMap[i]!! > 1 || inEdgesMap[i]!! - outEdgesMap[i]!! > 1) {
                return false
            } else if (outEdgesMap[i]!! - inEdgesMap[i]!! == 1) {
                startNodes++
            } else if (inEdgesMap[i]!! - outEdgesMap[i]!! == 1) {
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
        for (node in graph) {
            val i = node.key
            if (outEdgesMap[i]!! - inEdgesMap[i]!! == 1) {
                return i
            }
            if (outEdgesMap[i]!! > 0) {
                candidateNode = i
            }
        }
        return candidateNode
    }

    private fun depthFirstSearch(nodeNum: Int) {
        while (outEdgesMap[nodeNum] != 0) {
            val thisNode = graph[nodeNum]

            decrement(outEdgesMap, nodeNum)
            val outEdge = outEdgesMap[nodeNum]!!

            val nextNode = thisNode!![outEdge]
            depthFirstSearch(nextNode)
        }
        path.add(0, nodeNum) // prepend the current node to solution list
    }


    /**
     * scan a list of node connections (node1 -> node2,node3)
     * and return a map of the node to an EulerConnectionData list
     */
    fun eulerCycleMap(connList: String): List<EulerConnectionData> {
        val ep = EulerianPathArrayBased()
        val list: MutableList<EulerConnectionData> = mutableListOf()
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

        return EulerConnectionData(nodeNumber, resultMap)
    }

    /**
     * convert EulerConnectionData list to an map list (not array list)
     *     Error check the list - it should span 0 to N matching the node numbers in the
     *     eulerDataList after sorting.
     */
    fun eulerCycleConvertData(eulerDataList: List<EulerConnectionData>): MutableMap<Int, MutableList<Int>> {
        val outList: MutableMap<Int, MutableList<Int>> = mutableMapOf()

        for (entry in eulerDataList) {
            outList.putIfAbsent(entry.nodeNum, entry.connections)
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