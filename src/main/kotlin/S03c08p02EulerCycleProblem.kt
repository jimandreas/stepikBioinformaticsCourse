@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

/**
 * @link: https://stepik.org/lesson/240261/step/2?unit=212607
 *
Code Challenge: Solve the Eulerian Cycle Problem.

Input: The adjacency list of an Eulerian directed graph.
Output: An Eulerian cycle in this graph.
 */

fun main() {

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

}

data class EulerConnectionData(val nodeNum: Int, val connections: List<Int>)

fun eulerCycleMap( connList: String ): Map<Int, EulerConnectionData> {

    val reader = connList.reader()
    val lines = reader.readLines()
    val map : MutableMap<Int, EulerConnectionData> = mutableMapOf()
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

    val nodeNumber = parseInt(line.substring(0, line.indexOfFirst{it == ' '}))

    val connectionList = line.substringAfter( "-> ").split(',')
    val resultMap : MutableList<Int> = mutableListOf()
    for (conn in connectionList) {
        resultMap.add(parseInt(conn))
    }

    return EulerConnectionData(nodeNumber, resultMap)
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
        0.toInt()
    }
}



