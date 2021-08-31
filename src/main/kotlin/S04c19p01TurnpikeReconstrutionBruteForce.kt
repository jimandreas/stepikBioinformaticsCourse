@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

import util.*


/**
 * @link: https://stepik.org/lesson/240294/step/1?unit=212640
 * rosalind: @link: http://rosalind.info/problems/pdpl/
 *
Turnpike Problem: Given all pairwise distances between points on a line segment,
reconstruct the positions of those points.

Input: A collection of integers L.
Output: A set of integers A such that ∆A = L.

 */

fun main() {

    val tpbf = TurnpikeReconstructionBruteForce()

    val sampleInput = listOf(-10, -8, -7, -6, -5, -4, -3, -3, -2, -2, 0, 0, 0, 0, 0, 2, 2, 3, 3, 4, 5, 6, 7, 8, 10)
    val output = tpbf.turnpikeReconstructionProblem(sampleInput)
    println(output.joinToString(separator = " "))

    // check the answer

    val diffs = doDiffs(output)
    if (sampleInput.containsAll(diffs) && diffs.containsAll(sampleInput)) {
        println("Answer checks with diffs")
    }


}

private fun doDiffs(list: List<Int>): List<Int> {
    val accum : MutableList<Int> = mutableListOf()

    for (i in list) {
        for (j in list) {
            accum.add(i-j)
        }
    }
    //println(accum.sorted())
    return accum
}



