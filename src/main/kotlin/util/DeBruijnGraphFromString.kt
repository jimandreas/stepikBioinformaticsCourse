@file:Suppress("unused")

package util

/**

Code Challenge: Solve the De Bruijn Graph from a String Problem.

Input: An integer k and a string Text.
Output: DeBruijnk(Text), in the form of an adjacency list.

 * @link:
 * Stepik: https://stepik.org/lesson/240257/step/6?unit=212603
 * Rosalind: http://rosalind.info/problems/ba3d/
 */

/**
 *  Sample Input:

4
AAGATTCTCTAAGA

Sample Output:

AAG -> AGA,AGA
AGA -> GAT
ATT -> TTC
CTA -> TAA
CTC -> TCT
GAT -> ATT
TAA -> AAG
TCT -> CTA,CTC
TTC -> TCT
 */

class DeBruijnGraphFromString {

    fun deBruijnGraph(d: String, kmerLength: Int): Map<String, List<String>> {

        val resultMap : MutableMap<String, MutableList<String>> = mutableMapOf()
        val strLen = d.length

        // walk a kmer size window through the dnaString

        for (i in 0 until strLen - kmerLength+1) {
            val baseKmer = d.substring(i, i + kmerLength-1)
            val followOnKmer = d.substring(i+1, i+kmerLength)
            // if this is a new base kmer, add it and its follow-on kmer
            if (resultMap.containsKey(baseKmer)) {
                resultMap[baseKmer]!!.add(followOnKmer)
            } else {
                resultMap[baseKmer] = mutableListOf(followOnKmer)
            }
        }
        return resultMap
    }
}