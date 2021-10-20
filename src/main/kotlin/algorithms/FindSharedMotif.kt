@file:Suppress("unused")

package algorithms

/**
 * See also:
 * http://rosalind.info/problems/lcsm/
 * Finding a Shared Motif  (Longest Common Substring)
 *
 * Problem

A common substring of a collection of strings is a substring of every
member of the collection. We say that a common substring is a longest
common substring if there does not exist a longer common substring.
For example, "CG" is a common substring of "ACGTACGT" and "AACCGTATA",
but it is not as long as possible; in this case, "CGTA" is a longest
common substring of "ACGTACGT" and "AACCGTATA".

Note that the longest common substring is not necessarily unique;
for a simple example, "AA" and "CC" are both longest common
substrings of "AACC" and "CCAA".

Given: A collection of k (k≤100) DNA strings of length at most 1 kbp each in FASTA format.

Return: A longest common substring of the collection. (If multiple solutions exist, you may return any single solution.)

 */

class FindSharedMotif {

    // NOTE that the strings DO NOT have to all be the same length
    fun findMostLikelyCommonAncestor(sList: List<String>): String {

        // brute force search, keep going until nothing works

        // find the longest string to use for our sampling

        val longestString = sList.maxByOrNull {
            it.length
        }
        val len = longestString!!.length

        var tryThisLen = len   // optimistic - but try the full length string first
        while (len > 0) {
            for (i in 0 until len - tryThisLen + 1) {
                // get test pattern
                val testString = longestString.substring(i, i + tryThisLen)
                var success = true
                for (s in sList) {
                    if (!s.contains(testString)) {
                        success = false
                        break
                    }
                }
                if (success) {
                    return testString
                }
            }
            tryThisLen--
        }
        return ""
    }
}