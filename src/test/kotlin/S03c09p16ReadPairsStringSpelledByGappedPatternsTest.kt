@file:Suppress("UNUSED_VARIABLE")

import algorithms.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
Code Challenge: Implement StringSpelledByGappedPatterns.

Input: Integers k and d followed by a sequence of
(k, d)-mers (a1|b1), … , (an|bn) such that
Suffix(ai|bi) = Prefix(ai+1|bi+1) for 1 ≤ i ≤ n-1.

Output: A string Text of length k + d + k + n - 1
such that the i-th (k, d)-mer in Text is equal to (ai|bi)
for 1 ≤ i ≤ n (if such a string exists).

 * See also:
 * rosalind: http://rosalind.info/problems/ba7a/
 */
internal class S03c09p16ReadPairsStringSpelledByGappedPatternsTest {

//    private val problems.ep = EulerianPathStrings()

    @Test
    @DisplayName("test reconstructing a string 04")
    fun testReconstructingAString04() {

        val k = 4
        val d = 2

        val pairsString = """
            ACAC|CTCT
            ACAT|CTCA
            CACA|TCTC
            GACA|TCTC
        """.trimIndent()

        val parsedList = listOf(
            ReadPair("ACAC", "CTCT"),
            ReadPair("ACAT", "CTCA"),
            ReadPair("CACA", "TCTC"),
            ReadPair("GACA", "TCTC")
        )

        val parsedKmerList = parseGappedPatterns(pairsString)
        assertEquals(parsedList, parsedKmerList)

        val dBGraph = deBruijnGraphFromPairedGraph(parsedKmerList)

        val ep = EulerianPathOverReadPairs()
        ep.setGraph(dBGraph)
        val pathString = ep.solveEulerianPath()

        val result = reassembleStringFromPairs(k-1, d+1, pathString)

        val expectedResult = "GACACATCTCTCA"
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("test reconstructing a string 05")
    fun testReconstructingAString05() {

        val k = 3
        val d = 2

        val pairsString = """
            GGG|GGG
            AGG|GGG
            GGG|GGT
            GGG|GGG
            GGG|GGG
        """.trimIndent()

        val parsedList = listOf(
            ReadPair("GGG", "GGG"),
            ReadPair("AGG", "GGG"),
            ReadPair("GGG", "GGT"),
            ReadPair("GGG", "GGG"),
            ReadPair("GGG", "GGG")
        )

        val parsedKmerList = parseGappedPatterns(pairsString)
        assertEquals(parsedList, parsedKmerList)

        val dBGraph = deBruijnGraphFromPairedGraph(parsedKmerList)

        val ep = EulerianPathOverReadPairs()
        ep.setGraph(dBGraph)
        val pathString = ep.solveEulerianPath()

        val result = reassembleStringFromPairs(k-1, d+1, pathString)

        val expectedResult = "AGGGGGGGGGGT"
        assertEquals(expectedResult, result)

        // HMM first pass I don't get this.   But WAIT, the ORIGINAL is a 3-kmer!  Not a 2-Kmer
//        0  A  G        G  G
//        1     G  G        G  G
//        2        G  G        G  G
//        3           G  G        G  G
//        4              G  G        G  G
//        5                 G  G        G  T
//           1  2  3  4  5  6  7  8  9 10 11

// adjusting the gap to be one higher did the trick!

    }
}