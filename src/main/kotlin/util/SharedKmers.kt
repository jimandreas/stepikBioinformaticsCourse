@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls"
)

package util

/**
 *

Shared k-mers Problem: Given two strings, find all their shared k-mers.

Input: An integer k and two strings.

Output: All k-mers shared by these strings,
in the form of ordered pairs (x, y) corresponding
to starting positions of these k-mers in the respective strings.

 * See also:
 * stepik: https://stepik.org/lesson/240326/step/5?unit=212672
 * rosalind: http://rosalind.info/problems/ba6e/
 */

/**

Sample Input:

3
AAACTCATC
TTTCAAATC

Sample Output:

(0, 4)
(0, 0)
(4, 2)
(6, 6)

 */

class SharedKmers {

    /**
     * take a kMer length and two DNA strings
     *    Find all shared kMers and reverse complement Kmers
     *    Return a list of pairs of their coordinates in the strings
     */
    fun findSharedKmersHashed(kLen: Int, s1: String, s2: String) : List<Pair<Int, Int>> {

        val s1Kmers = kMers(kLen, s1)
        val s1KmersR = kMersReverseComplements(kLen, s1)
        val s2KmersMap = kMersHashed(kLen, s2)
        val s2KmersReverseMap = kMersHashed(kLen, s2, reverseC = true)

        val totalList : MutableList<Pair<Int, Int>> = mutableListOf()
        for (i in 0 until s1Kmers.size) {
            var l = s2KmersMap[s1Kmers[i]]
            l?.forEach {
                totalList.add(Pair(i, it))
            }
            l = s2KmersReverseMap[s1Kmers[i]]
            l?.forEach {
                totalList.add(Pair(i, it))
            }
        }

        for (i in 0 until s1KmersR.size) {
            var l = s2KmersMap[s1KmersR[i]]
            l?.forEach {
                totalList.add(Pair(i, it))
            }
            l = s2KmersReverseMap[s1KmersR[i]]
            l?.forEach {
                totalList.add(Pair(i, it))
            }
        }

        val sortedList = totalList.sorted()
        val uniqueList = sortedList.distinct()
        return uniqueList
    }

    fun <A: Comparable<A>, B: Comparable<B>> List<Pair<A, B>>.sorted() : List<Pair<A, B>> =
        sortedWith(
            Comparator<Pair<A, B>>{ a, b-> a.first.compareTo(b.first)}.thenBy{it.second}
        )

    private fun getMatchPairs(kMer: String, pos: Int, n: List<String>, r: List<String>): List<Pair<Int, Int>> {
        val retList: MutableList<Pair<Int, Int>> = mutableListOf()

        n.mapIndexed { index, item ->
            if (item == kMer) {
                retList.add(Pair(pos, index))
            }
        }

        r.mapIndexed { index, item ->
            if (item == kMer) {
                retList.add(Pair(pos, index))
            }
        }
        return retList
    }

    private fun kMersHashed(kLen: Int, s: String, reverseC: Boolean = false): Map<String, List<Int>> {
        val sKmers : MutableMap<String, List<Int>> = mutableMapOf()
        for (i in 0 until s.length - kLen+1) {
            var kMer = s.substring(i, i+kLen)
            if (reverseC) {
                kMer = reverseComplement(kMer)
            }
            if (sKmers.containsKey(kMer)) {
                val m = sKmers[kMer]!!.toMutableList()
                m.add(i)
                sKmers[kMer] = m
            } else {
                sKmers[kMer] = listOf(i)
            }
        }
        return sKmers
    }

    private fun kMers(kLen: Int, s: String): List<String> {
        val sKmers : MutableList<String> = arrayListOf()
        for (i in 0 until s.length - kLen+1) {
            val kMer = s.substring(i, i+kLen)
            sKmers.add(kMer)
            //sKmers.add(reverseComplement(s))
        }
        return sKmers
    }

    private fun kMersReverseComplements(kLen: Int, s: String): List<String> {
        val sKmers : MutableList<String> = arrayListOf()
        for (i in 0 until s.length - kLen+1) {
            val kMer = s.substring(i, i+kLen)
            //sKmers.add(s)
            sKmers.add(reverseComplement(kMer))
        }
        return sKmers
    }

    /*
 --> this version is no longer used - the linear search was not efficient enough

    fun findSharedKmers(kLen: Int, s1: String, s2: String) : List<Pair<Int, Int>> {

        val s1Kmers = kMers(kLen, s1)
        val s1KmersR = kMersReverseComplements(kLen, s1)
        val s2Kmers = kMers(kLen, s2)
        val s2KmersR = kMersReverseComplements(kLen, s2)

        val totalList : MutableList<Pair<Int, Int>> = mutableListOf()
        s1Kmers.mapIndexed { index, item ->
            val l = getMatchPairs(item, index, s2Kmers, s2KmersR)
            totalList.addAll(l)
        }

        s1KmersR.mapIndexed { index, item ->
            val l = getMatchPairs(item, index, s2Kmers, s2KmersR)
            totalList.addAll(l)
        }

        val uniqueList = totalList.distinct().sortedBy {
            it.first * 1000 + it.second
        }
        return uniqueList
    }*/

}
