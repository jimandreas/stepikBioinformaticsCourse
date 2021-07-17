@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody")

import util.EulerianPathStrings
import util.deBruijnDirectedGraphConversion
import util.deBruijnGraphFromKmers
import util.pathToGenome

/**
 * @link: https://stepik.org/lesson/240261/step/6?unit=212607
 *
Code Challenge: Solve the Eulerian Path Problem.

Input: The adjacency list of a directed graph that has an Eulerian path.
Output: An Eulerian path in this graph.

Have to convert to symbolic matching as one node is missing in the test dataset.

 */


fun main() {


    val input = listOf(
        "AG",
        "AT",
        "AA",
        "GA",
        "GG",
        "GT",
        "TA",
        "TG",
        "TT",
        "AT"

    )


    val output = deBruijnGraphFromKmers(input)
    val theList = deBruijnDirectedGraphConversion(output)
    println("theList = $theList")

    val ep = EulerianPathStrings()
    val resultMap = ep.eulerCycleMap(theList)
    println("resultMap = $resultMap")

    val path = ep.eulerCycleConvertData(resultMap)
    ep.setGraph(path)
    val solution = ep.solveEulerianPath()

    val resultString = pathToGenome(solution)
    println(resultString)

}