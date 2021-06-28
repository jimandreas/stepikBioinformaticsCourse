@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody")

import util.*
import java.util.*

/**
 * @link: https://stepik.org/lesson/240261/step/11?unit=212607
 *
Code Challenge: Solve the k-Universal Circular String Problem.

Input: An integer k.
Output: A k-universal circular string.

Challenge - generate a series of binary numbers of width k
 from 0 to 2^k-1
*/

fun Int.to32bitString(): String =
    Integer.toBinaryString(this).padStart(Int.SIZE_BITS, '0')

fun main() {

    val k = 9  // this is the target "width" of the circular string (e.g. kmer size)

    val endValue = 1.shl(k)
    val input: MutableList<String> = mutableListOf()
    for (i in 0 until endValue) {
        input.add(Integer.toBinaryString(i).padStart(k, '0'))
    }
    val ep = EulerianPathStrings()
    val output = deBruijnGraphFromKmers(input)
    val theList = deBruijnDirectedGraphConversion(output)

    val resultMap = ep.eulerCycleMap(theList)
    val path = ep.eulerCycleConvertData(resultMap)
    ep.setGraph(path)
    val solution = ep.solveEulerianPath()

    val resultString = pathToGenome(solution)
    val truncatedEnd = resultString.substring(0, resultString.length - k+1)
    println(truncatedEnd)

    // correct!!
}