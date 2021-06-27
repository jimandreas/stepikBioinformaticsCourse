@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody")

import java.lang.StringBuilder
import java.util.*

/**
 * @link: https://stepik.org/lesson/240261/step/2?unit=212607
 *
Code Challenge: Solve the Eulerian Cycle Problem.

Input: The adjacency list of an Eulerian directed graph.
Output: An Eulerian cycle in this graph.
 */
data class EulerConnectionData(val nodeNum: Int, val connector: Boolean = false, val connections: MutableList<Int>)

fun main() {
    val connString1 = """
        
    """.trimIndent()

    val connString = """
        0 -> 3
        1 -> 0
        2 -> 1,6
        3 -> 2
        4 -> 2
        5 -> 4
        6 -> 5,8
        7 -> 9
        8 -> 7
        9 -> 6
    """.trimIndent()

    val foo : LinkedList<Int>

    val eulerMap = eulerCycleMap(connString)
    val eulerCycleString = walkEulerCycleMap(eulerMap)
    println(eulerCycleString)

}

/**
 * walk an EulerConnectionData map and return the walk string (the Eulerian cycle)
 * Tactic: start with the first node that has multiple connections.
 * then walk to the first node and always take the first node if there is a choice.
 *     Track the nodes visited.
 *
 */
fun walkEulerCycleMap(eulerList: Map<Int, EulerConnectionData>): String {
    val visited: MutableList<Int> = mutableListOf()
    val touched: MutableList<Int> = mutableListOf()

    // scan for a start node - one with more than one connection
    var curNode = 0
    for (e in eulerList) {
        val connData = e.value.connections
        if (connData.size > 1) {
            curNode = e.key
            break
        }
    }

    // now traverse the graph.
    do {
        visited.add(curNode)
        touched.add(curNode)

        var newNode = -1

        val e1 = eulerList[curNode]
        if (e1 == null) {
            // finished
            println("DONE at $curNode")
            break
        }
        val e = e1 as EulerConnectionData
        val conn = e.connections
        if (e.connector) {
            when (conn.size) {
                0 -> {
                    // finished
                    println("DONE at $curNode")
                    break
                }
                else -> { // 2 or more connections
                    for (node in conn) {
                        if (!touched.contains(node)) {
                            newNode = node
                            conn.remove(node)
                            break
                        }
                    }
                    if (newNode == -1) {
                        // no unvisited node found.   try the first node
                        newNode = conn[0]
                        conn.remove(newNode)
                    }
                }
            }
        } else {
            newNode = conn[0]
        }
        curNode = newNode
    } while (true)

    val outline = StringBuilder()
    val iter = visited.iterator().withIndex()
    while (iter.hasNext()) {
        val n = iter.next()
        outline.append(n.value)
        if (n.index != visited.size - 1) {
            outline.append("->")
        }
    }
    return outline.toString()
}


/**
 * scan a list of node connections (node1 -> node2,node3)
 * and return a map of the node to an EulerConnectionData list
 */
fun eulerCycleMap(connList: String): Map<Int, EulerConnectionData> {
    val reader = connList.reader()
    val lines = reader.readLines()
    val map: MutableMap<Int, EulerConnectionData> = mutableMapOf()
    for (s in lines) {
        val connData = eulerCycleParseLine(s)
        map[connData.nodeNum] = connData
    }
    return map
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

    return EulerConnectionData(nodeNumber, resultMap.size > 1, resultMap)
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



