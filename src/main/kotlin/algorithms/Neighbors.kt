@file:Suppress("LiftReturnOrAssignment", "UNUSED_PARAMETER")

package algorithms

/**
 *

1.11 CS: Generating the Neighborhood of a String

Now, consider a (k − 1)-mer Pattern’ belonging to Neighbors(Suffix(Pattern), d).
By the definition of the d-neighborhood Neighbors(Suffix(Pattern), d),
we know that HammingDistance(Pattern′, Suffix(Pattern)) is either equal to d
or less than d. In the first case, we can add FirstSymbol(Pattern)
to the beginning of Pattern’ in order to obtain a k-mer belonging to Neighbors(Pattern, d).
In the second case, we can add any symbol to the beginning of Pattern’
and obtain a k-mer belonging to Neighbors(Pattern, d).

For example, to generate Neighbors(CAA,1), first form
Neighbors(AA,1) = {AA, CA, GA, TA, AC, AG, AT}.
The Hamming distance between AA and each of six of these neighbors is 1.
Firstly, concatenating C with each of these patterns results in six patterns
(CAA, CCA, CGA, CTA, CAC, CAG, CAT) that belong to Neighbors(CAA, 1).
Secondly, concatenating any nucleotide with AA results in four patterns
(AAA, CAA, GAA, and TAA) that belong to Neighbors(CAA, 1). Thus, Neighbors(CAA, 1) comprises ten patterns.

 */

fun neighbors(pattern: String, hammingDistance: Int): List<String> {
    if (hammingDistance == 0) {
        return listOf(pattern)
    }

    if (pattern.isEmpty()) {
        return listOf("")
    }

    if (pattern.length == 1) {
        return listOf("A", "C", "G", "T")
    }

    val neighborhood : MutableList<String> = arrayListOf()
    val suffixOfPattern = pattern.substring(1, pattern.length)
    val suffixNeighbors = neighbors(suffixOfPattern, hammingDistance)
    for (s in suffixNeighbors) {
        if (hammingDistance(suffixOfPattern, s) < hammingDistance) {
            for (n in listOf("A", "C", "G", "T")) {
                neighborhood.add("$n$s")
            }
        } else {
            neighborhood.add("${pattern[0]}$s")
        }
    }
    return neighborhood
}

fun frequentWordsWithMismatches(
    givenString: String,
    length: Int,
    hammingDistance: Int,
    scanReverseComplements: Boolean = false): List<String> {

    //val patterns : MutableList<String> = arrayListOf()
    val freqMap : HashMap<String, Int> = hashMapOf()

    for (i in 0..givenString.length - length) {
        val pattern = givenString.substring(i, i + length)
        val neighborhood = neighbors(pattern, hammingDistance)
        for (s in neighborhood) {
            increment(freqMap, s)  // add s if necessary, otherwise increment it
        }
        if (scanReverseComplements) {
            val reversedNeighborhood = neighbors(reverseComplement(pattern), hammingDistance)
            for (s in reversedNeighborhood) {
                increment(freqMap, s)
            }
        }
    }

    val mymap = freqMap.toList()
    val max = mymap.maxOf{it.second}
    val returnList = freqMap.filter {(/* str */ _, value) -> value == max }.keys
    return returnList.toList()

}

fun neighborsOfGivenLength(
    givenString: String,
    length: Int,
    hammingDistance: Int,
    scanReverseComplements: Boolean = false): List<String> {

    val patterns : MutableList<String> = arrayListOf()

    for (i in 0..givenString.length - length) {
        val pattern = givenString.substring(i, i + length)
        val neighborhood = neighbors(pattern, hammingDistance)
        patterns += neighborhood
        if (scanReverseComplements) {
            val reversedNeighborhood = neighbors(reverseComplement(pattern), hammingDistance)
            patterns += reversedNeighborhood
        }
    }
    return patterns.sorted().distinct()
}


/**
 *
Implanted Motif Problem: Find all (k, d)-motifs in a collection of strings.

Input: A collection of strings Dna, and integers k and d.
Output: All (k, d)-motifs in Dna.

Brute force (also known as exhaustive search) is a general problem-solving technique
that explores all possible solution candidates and checks whether each candidate
solves the problem. Such algorithms require little effort to design and are
guaranteed to produce a correct solution, but they may take an enormous amount of time,
and the number of candidates may be too large to check.
 */
fun motifEnumeration(
    dnaList: List<String>,
    kmer: Int,
    distance: Int,
    scanReverseComplements: Boolean = false): List<String> {

    var accumulatorList : List<String> = mutableListOf()
    for (g in dnaList) {
        val neighborList = neighborsOfGivenLength(g, kmer, distance)
        if (accumulatorList.isEmpty()) {
            accumulatorList = neighborList.sorted()
        } else {
            accumulatorList = accumulatorList.intersect(neighborList.toSet()).toList()
        }
    }
    accumulatorList = accumulatorList.sorted().distinct()
    return accumulatorList
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
