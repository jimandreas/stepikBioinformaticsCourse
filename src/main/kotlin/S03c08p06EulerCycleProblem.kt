@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody")

import util.EulerianPathSymbolicMap
import java.util.*

/**
 * @link:
 * Stepik: https://stepik.org/lesson/240261/step/6?unit=212607
 * Rosalind: http://rosalind.info/problems/ba3g/
 *
Code Challenge: Solve the Eulerian Path Problem.

Input: The adjacency list of a directed graph that has an Eulerian path.
Output: An Eulerian path in this graph.

Have to convert to symbolic matching as one node is missing in the test dataset.

*/

private val ep = EulerianPathSymbolicMap()

fun main() {
    val connectionString = """
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

    val result = ep.eulerCycleMap(connectionString)
    val path = ep.eulerCycleConvertData(result)

    ep.setGraph(path)
    val solution = ep.solveEulerianPath()

    print(solution.joinToString(separator = "->"))


}








