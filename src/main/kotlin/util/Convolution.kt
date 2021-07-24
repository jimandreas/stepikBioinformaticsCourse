@file:Suppress(
    "ControlFlowWithEmptyBody", "UNUSED_VARIABLE", "unused", "ReplaceManualRangeWithIndicesCalls",
    "LiftReturnOrAssignment", "UnnecessaryVariable", "UNUSED_DESTRUCTURED_PARAMETER_ENTRY",
    "ConvertTwoComparisonsToRangeCheck"
)

package util

import java.util.*

/**
 * Convolution
 * Ch 4.9 : @link: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-4
 * youtube: @link: https://www.youtube.com/watch?v=qSJSM1dJ6ZY&list=PLQ-85lQlPqFPdIS_5qv_Q3XWieobVPLlc&index=8
 */
/**
 *
 * Spectral Convolution Problem: Compute the convolution of a spectrum.

Input: A collection of integers Spectrum.
Output: The list of elements in the convolution of Spectrum.

@output: map of diff to multiplicity
 */

fun spectralConvolution(spectrum: List<Int>): Map<Int, Int> {

    val map: MutableMap<Int, Int> = HashMap()

    for (i in 0 until spectrum.size) {
        for (j in 0 until spectrum.size) {
            val diff = spectrum[j] - spectrum[i]
            if (diff > 0) {
                increment(map, diff)
            }
        }
    }
    return map.toMap()
}


/**
 * add the [key] to the [map].   Increment the count of the key in the map.
 * @param map - a mutable map of type K
 * @param key - they key of type K to add to the map
 * @link https://www.techiedelight.com/increment-value-map-kotlin/
 */
private fun <K> increment(map: MutableMap<K, Int>, key: K) {
    map.putIfAbsent(key, 0)
    map[key] = map[key]!! + 1
}

/**
 * utility function to return the top M elements of a map of elements and their multiplicity
 *
 *  select the M most frequent elements between 57 and 200
 *  ties for the same frequency level are included
 *
 * @param topElementsM  how many to return
 * @param mapOfElementsToMultiplicity map of elements to multiplicty (unsorted)
 * @return top M list
 */
fun topM(topElementsM: Int, mapOfElementsToMultiplicity: Map<Int, Int>): List<Int> {

    var threshold = 0

    // filter the elements (keys) to the 57 to 200 inclusive range
    //   and note the multiplicity (value) at the topElement index
    val topListByM = mapOfElementsToMultiplicity
        .toList()
        .filter {(key, value) -> key >= 57 && key <= 200}
        .sortedByDescending { (key, value) -> value }
        .mapIndexed { index, item ->
            if (index == topElementsM-1) {
                threshold = item.second
            }
            item
        }

    // pull out the top elements (keys, now first in the pairs)
    val filterToTopElements = topListByM.filter { it.second >= threshold}.map{
        it.first
    }
    return filterToTopElements
}

/**
Code Challenge: Implement ConvolutionCyclopeptideSequencing.

Input: An integer M, an integer N, and a collection of (possibly repeated) integers Spectrum.

Output: A cyclic peptide LeaderPeptide with amino acids taken only from the top
M elements (and ties) of the convolution of Spectrum that fall between 57 and 200,
and where the size of Leaderboard is restricted to the top N (and ties).

 * stepik: @link: https://stepik.org/lesson/240284/step/7?unit=212630
 * rosalind: @link: http://rosalind.info/problems/ba4i/
 * youtube: @link: https://www.youtube.com/watch?v=qSJSM1dJ6ZY&list=PLQ-85lQlPqFPdIS_5qv_Q3XWieobVPLlc&index=8
 */

fun convolutionCyclopeptideSequencing(
    l: LeaderboardCyclopeptideSequencing,
    topElementsM: Int, leaderBoardN: Int, spectrum: List<Int>): List<Int> {

    val convolutionResult = spectralConvolution(spectrum)

    // override the amino masses used in the leaderboard analysis
    aminoUniqueMasses = topM(topElementsM, convolutionResult)
    println(aminoUniqueMasses)

    val results = l.leaderboardCyclopeptideSequencing(leaderBoardN, spectrum)
    return results

}