@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

package problems

import algorithms.EulerianPathOverReadPairs
import algorithms.deBruijnGraphFromPairedGraph
import algorithms.parseGappedPatterns
import algorithms.reassembleStringFromPairs


/**
 * @link:
 * Rosalind: http://rosalind.info/problems/ba3l/
 *
Code Challenge: Implement StringSpelledByGappedPatterns.
(Rosalind: Gapped Genome Path String Problem)

Input: Integers k and d followed by a sequence of (k, d)-mers (a1|b1), … , (an|bn)
such that Suffix(ai|bi) = Prefix(ai+1|bi+1) for 1 ≤ i ≤ n-1.

Output: A string Text of length k + d + k + n - 1 such that the
i-th (k, d)-mer in Text is equal to (ai|bi)  for 1 ≤ i ≤ n (if such a string exists).

 */

fun main() {

    val k = 4
    val d = 2

    val pairsString = """
        GACC|GCGC
        ACCG|CGCC
        CCGA|GCCG
        CGAG|CCGG
        GAGC|CGGA
            """.trimIndent()

//    val k = 50
//    val d = 200
//    val pairsString = """
//        insert LARGE string
//            """.trimIndent()
    val parsedKmerList = parseGappedPatterns(pairsString)

    val dBGraph = deBruijnGraphFromPairedGraph(parsedKmerList)

    val ep = EulerianPathOverReadPairs()
    ep.setGraph(dBGraph)
    val pathString = ep.solveEulerianPath()

    val result = reassembleStringFromPairs(k-1, d+1, pathString)

    println(result)

    val expectedResult = "GACCGAGCGCCGGA"

    val rLen = result.length
    val eLen = expectedResult.length

    if (result == expectedResult) {
        println("Right Answer!")
    }
}




