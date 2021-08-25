@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

import util.EulerianPathOverReadPairs
import util.deBruijnGraphFromPairedGraph
import util.parseGappedPatterns
import util.reassembleStringFromPairs


/**
 * @link:
 * Stepik: https://stepik.org/lesson/240266/step/4?unit=212612
 * Rosalind: http://rosalind.info/problems/ba3j/
 *
Code Challenge: Implement StringSpelledByGappedPatterns.
(Rosalind: String Reconstruction from Read-Pairs Problem)

Input: Integers k and d followed by a sequence of (k, d)-mers (a1|b1), … ,
(an|bn) such that Suffix(ai|bi) = Prefix(ai+1|bi+1) for 1 ≤ i ≤ n-1.
Output: A string Text of length k + d + k + n - 1 such that
the i-th (k, d)-mer in Text is equal to (ai|bi)  for 1 ≤ i ≤ n (if such a string exists).

 */

fun main() {

//    val k = 30
//    val d = 100

    val k = 4
    val d = 2

    val pairsString = """
    GAGA|TTGA
    TCGT|GATG
    CGTG|ATGT
    TGGT|TGAG
    GTGA|TGTT
    GTGG|GTGA
    TGAG|GTTG
    GGTC|GAGA
    GTCG|AGAT
""".trimIndent()



    val parsedKmerList = parseGappedPatterns(pairsString)

    val dBGraph = deBruijnGraphFromPairedGraph(parsedKmerList)

    val ep = EulerianPathOverReadPairs()
    ep.setGraph(dBGraph)
    val pathString = ep.solveEulerianPath()

    val result = reassembleStringFromPairs(k - 1, d + 1, pathString)

    println(result)
    // should be: GTGGTCGTGAGATGTTGA
    //            GTGGTCGTGAGATGTTGA

}


