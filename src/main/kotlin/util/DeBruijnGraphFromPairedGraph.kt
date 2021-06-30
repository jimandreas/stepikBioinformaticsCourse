@file:Suppress("UnnecessaryVariable", "unused", "UNUSED_PARAMETER")

package util

import java.lang.StringBuilder

/**
 * from a [d] list of paired Kmer strings
 *   produce a map of kmer to kmer connections
 *   as a list of ReadPair objects.
 */

fun deBruijnGraphFromPairedGraph(d: List<ReadPair>): Map<ReadPair, List<ReadPair>> {

    val m: MutableMap<ReadPair, MutableList<ReadPair>> = mutableMapOf()

    for (e in d) {
        val prefix = e.prefix()
        val suffix = e.suffix()
        if (m.containsKey(prefix)) {
            m[prefix]!!.add(suffix)
        } else {
            m[prefix] = mutableListOf(suffix)
        }
    }
    return m
}

/**
 * utility function to re-concatenate a list of ReadPair fragments while maintaining the
 * gap length between the base pairs.
 */
fun reassembleStringFromPairs(kmerLength: Int, dGapLength: Int, pairList: List<ReadPair>): String {
    val stringSize = pairList.size-1 + kmerLength + dGapLength + kmerLength
    val s = StringBuilder(stringSize)
    for (i in 0 until stringSize) {
        s.append(' ')
    }

    for (i in pairList.indices) {
        s.deleteRange(i, i+kmerLength)
        s.insertRange(i, pairList[i].p1, 0, kmerLength)
        s.deleteRange(i + kmerLength + dGapLength, i + kmerLength + dGapLength +kmerLength)
        s.insertRange(i + kmerLength + dGapLength, pairList[i].p2, 0, kmerLength)
    }
    return s.toString()
}


