@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody")

import org.junit.jupiter.api.Assertions
import util.EulerianPath
import java.util.*

/**
 * @link: https://stepik.org/lesson/240261/step/6?unit=212607
 *
Code Challenge: Solve the Eulerian Path Problem.

Input: The adjacency list of a directed graph that has an Eulerian path.
Output: An Eulerian path in this graph.

Have to convert to symbolic matching as one node is missing in the test dataset.
*/

private val ep = EulerianPath()

fun main() {
//    val connectionString = """
//        0 -> 3
//        1 -> 0
//        2 -> 1,6
//        3 -> 2
//        4 -> 2
//        5 -> 4
//        6 -> 5,8
//        7 -> 9
//        8 -> 7
//        9 -> 6
//    """.trimIndent()

    val connectionString = """
        0 -> 11,3,56,74
           """.trimIndent()

    val foo : LinkedList<Int>

    val eulerMap = ep.eulerCycleMap(connectionString)

    val result = ep.eulerCycleMap(connectionString)
    val path = ep.eulerCycleConvertData(result)

    ep.setGraph(path)
    val solution = ep.solveEulerianPath()

    print(solution.joinToString(separator = "->"))


}








