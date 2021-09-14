@file:Suppress("LiftReturnOrAssignment", "GrazieInspection")

package algorithms

import java.lang.Integer.min
/**

2.4 From Motif Finding to Finding a Median String
8 out of 11 steps passed
0 out of 5 points  received

Code Challenge: Implement MedianString.

Input: An integer k, followed by a collection of strings Dna.
Output: A k-mer Pattern that minimizes d(Pattern, Dna) among all
possible choices of k-mers. (If there are multiple such strings Pattern, then you may return any one.)

 * See also:
 * stepik: @link: https://stepik.org/lesson/240240/step/9?unit=212586
 * rosalind: @link: http://rosalind.info/problems/ba2b/

 */


/**
 * The first potential issue with implementing MedianString is writing a
 * function to compute d(Pattern, Dna) = ∑ti=1 d(Pattern, Dnai),
 * the sum of distances between Pattern and each string in Dna = {Dna1, ..., Dnat}.
 */
fun distanceBetweenPatternAndStrings(pattern: String, dnaList: List<String>): Int {

    var distance = 0

    for (g in dnaList) {
        var minHammingDistance = Integer.MAX_VALUE
        for (i in 0..g.length - pattern.length) {
            val kmer = g.substring(i, i + pattern.length)
            val dist = hammingDistance(pattern, kmer)
            minHammingDistance = min(minHammingDistance, dist)
        }
        distance += minHammingDistance
    }
    return distance
}

/**
 * return all possible {ACGT} strings of length [kmer]
 * Use a basic iterative approach rather than recursion for speed
 */
fun allStrings(kmer: Int): List<String> {
    if (kmer <= 0) {
        println("ERROR kmer must be > 0")
        return listOf("")
    }
    val allStringsList: MutableList<String> = mutableListOf()
    var fourToTheKmer = 4
    for (i in 1 until kmer) {
        fourToTheKmer *= 4   // 4 to the power of kmer
    }
    for (i in 0 until fourToTheKmer) {
        var k = ""
        var temp = i
        for (j in 0 until kmer) {
            k = k.plus( "ACGT"[temp%4])
            temp /= 4
        }
        allStringsList.add(k)
    }
    return allStringsList
}

/**
 * medianString
 * To solve the Median String Problem, we need to iterate through
 * all possible 4k k-mers Pattern before computing d(Pattern, Dna).
 * We can use a subroutine called AllStrings(k) that returns an array
 * containing all strings of length k.
 *
 * return the string of length [kmer] that represents
 * the candidate with the minimum hamming distance to all
 * strings in the [dnaList]
 */

fun medianString(dnaList: List<String>, kmer: Int): String {
    var minHammingDistance = Integer.MAX_VALUE
    var retKmer = ""
    val allPossibleKmers = allStrings(kmer)
    for (g in allPossibleKmers) {
        val hammingDistance = distanceBetweenPatternAndStrings(g, dnaList)
        if (minHammingDistance > hammingDistance) {
            minHammingDistance = hammingDistance
            retKmer = g
        }
    }
    return retKmer
}

