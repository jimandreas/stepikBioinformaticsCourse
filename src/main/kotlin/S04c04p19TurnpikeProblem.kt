@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

import util.*


/**
 * @link: https://stepik.org/lesson/240294/step/1?unit=212640
 * rosalind: @link: http://rosalind.info/problems/pdpl/
 *
Turnpike Problem: Given all pairwise distances between points on a line segment, reconstruct the positions of those points.

Input: A collection of integers L.
Output: A set of integers A such that ∆A = L.

 */

fun main() {

    val sampleInput = listOf(-10, -8, -7, -6, -5, -4, -3, -3, -2, -2, 0, 0, 0, 0, 0, 2, 2, 3, 3, 4, 5, 6, 7, 8, 10)
    val guessTopM = 5
    val convolutionResult = spectralConvolution(sampleInput)

    // override the amino masses used in the leaderboard analysis
    aminoUniqueMasses = topM(guessTopM, convolutionResult)
    println(aminoUniqueMasses)

}



