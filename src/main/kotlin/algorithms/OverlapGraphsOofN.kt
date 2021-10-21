@file:Suppress("unused")

package algorithms

/**
 * See also:
 * http://rosalind.info/problems/grph/
 * Overlap Graphs
 *
 * Problem

A graph whose nodes have all been labeled can be represented by an
adjacency list, in which each row of the list contains the two node
labels corresponding to a unique edge.

A directed graph (or digraph) is a graph containing directed edges,
each of which has an orientation. That is, a directed edge is represented by
an arrow instead of a line segment; the starting and ending nodes of an
edge form its tail and head, respectively. The directed edge with tail v
and head w is represented by (v,w) (but not by (w,v)). A directed
loop is a directed edge of the form (v,v)

For a collection of strings and a positive integer k, the overlap graph
for the strings is a directed graph Ok in which each string is represented
by a node, and string s is connected to string t with a directed edge when
there is a length k suffix of s that matches a length k prefix of t, as
long as s≠t; we demand s≠t to prevent directed loops in the overlap graph
(although directed cycles may be present).

Given: A collection of DNA strings in FASTA format having total length at most 10 kbp.

Return: The adjacency list corresponding to O3. You may return edges in any order.

KEY HERE is O3 - three characters must match in prefix and suffix!
 */

class OverlapGraphsOofN {

    /**
     * iterate through all strings, matching each against the other
     *    if there is a matching prefix/suffix in a pair, then add the pair to the
     *    suffix->prefix hash map.
     */
    fun overlappingPrefixAndSuffix(mustMatchCount: Int, s: List<String>): HashMap<String, MutableList<String>> {

        val outputMap: HashMap<String, MutableList<String>> = hashMapOf()

        // add a placeholder list for all strings
        for (str in s) {
            outputMap[str] = mutableListOf()
        }

        val numStrings = s.size
        for (i in 0 until numStrings) {
            for (j in i + 1 until numStrings) {
                val si = s[i] // for ease of debugging
                val sj = s[j]
                val silen = si.length
                val sjlen = sj.length
                if (si.substring(0, mustMatchCount) == sj.substring(sjlen - mustMatchCount, sjlen)) {
                    outputMap[sj]!!.add(si)
                }

                if (sj.substring(0, mustMatchCount) == si.substring(silen - mustMatchCount, silen)) {
                    outputMap[si]!!.add(sj)
                }
            }
        }
        return outputMap
    }

}