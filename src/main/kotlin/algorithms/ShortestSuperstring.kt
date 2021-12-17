@file:Suppress("MemberVisibilityCanBePrivate")

package algorithms

/**
 * See also:
 * http://rosalind.info/problems/long/
 * Introduction to Genome Sequencing
 *
 * Problem

Problem

For a collection of strings, a larger string containing every
one of the smaller strings as a substring is called a superstring.

By the assumption of parsimony, a shortest possible superstring
over a collection of reads serves as a candidate chromosome.

Given: At most 50 DNA strings of approximately equal length,
not exceeding 1 kbp, in FASTA format (which represent reads
deriving from the same strand of a single linear chromosome).

The dataset is guaranteed to satisfy the following condition:
there exists a unique way to reconstruct the entire chromosome
from these reads by gluing together pairs of reads that overlap
by more than half their length.

Return: A shortest superstring containing all the given strings
(thus corresponding to a reconstructed chromosome).
 */

/**
 * Algorithm:
 * Leverage the FindSharedMotif class to find the longest shared motif
 *  comparing all pairs of strings.
 * Then merge the two winning strings.   Repeat until no strings are left.
 *
 * Optimize: Hash the common string analysis as a cache.
 */
class ShortestSuperstring {

    val fsm = FindSharedMotif()

    fun shortestSuperstring(stringList: MutableList<String>): String {

        // use a hash map as it can be modified in the loop
        val hashStringList: HashMap<Int, String> = hashMapOf()
        for (i in 0 until stringList.size) {
            hashStringList[i] = stringList[i]
        }

        val hashMotif: HashMap<String, String> = hashMapOf()
        var maxKey = Pair(-1, -1)
        var merged = 0
        var result = ""
        while (merged < stringList.size - 1) {

            var maxLen = Int.MIN_VALUE
            for (i in 0 until stringList.size) {
                for (j in i + 1 until stringList.size) {

                    if (hashStringList[i] == "" || hashStringList[j] == "") {
                        continue
                    }

                    if (maxLen > (hashStringList[i]!!.length + hashStringList[j]!!.length)) {
                        continue
                    }
                    //println("i $i j $j")
                    val keysb = StringBuilder().append(hashStringList[i])
                    keysb.append(hashStringList[j])
                    val key = keysb.toString()

                    if (hashMotif.containsKey(key)) {
                        //println("Found Key in cache $key")

                        if (hashMotif[key]!!.length > maxLen) {

                            maxLen = hashMotif[key]!!.length
                            maxKey = Pair(i, j)
                            //println("from cache i $i j $j New max $maxLen with ${hashMotif[key]}")
                        }
                    } else {
                        val sharedMotif = fsm.findMostLikelyCommonAncestor(
                            listOf(hashStringList[i]!!, hashStringList[j]!!)
                        )
                        //println("i $i j $j of ${stringList.size} $sharedMotif")
                        hashMotif[key] = sharedMotif
                        if (sharedMotif.length > maxLen) {

                            maxLen = sharedMotif.length
                            //println("i $i j $j key $key New max $maxLen with $sharedMotif")

                            maxKey = Pair(i, j)
                        }
                    }
                }
            }

            // now merge the two key strings as given by i, j


            val i = maxKey.first
            val s1 = hashStringList[i]
            val j = maxKey.second
            val s2 = hashStringList[j]

            //println("strings s1 $s1 s2 and $s2 BEFORE")

            val keysb = StringBuilder()
            keysb.append(s1)
            keysb.append(s2)
            val key = keysb.toString()

            val motif = hashMotif[key]
            val mlen = motif!!.length

            val str = StringBuilder()
            when {

                // motif located at start of string S1 and end of S2
                s1!!.substring(0, mlen) == motif && s2!!.substring(s2.length - mlen, s2.length) == motif -> {
                    str.append(s2)
                    str.append(s1.substring(motif.length, s1.length))
                }

                // motif located at start of string S2 and end of S1
                s2!!.substring(0, mlen) == motif && s1.substring(s1.length - mlen, s1.length) == motif -> {
                    str.append(s1)
                    str.append(s2.substring(motif.length, s2.length))
                }

                // motif located at end of string s1 and start of 2
                s1.substring(s1.length - mlen, s1.length) == motif && s2.substring(0, mlen) == motif -> {
                    val idx = s2.indexOf(motif)
                    str.append(s1)
                    str.append(s2.substring(idx + motif.length, s2.length))
                }

                // motif located at end of string s2 and start of s1
                s2.substring(s2.length - mlen, s2.length) == motif && s1.substring(0, mlen) == motif -> {
                    val idx = s1.indexOf(motif)
                    str.append(s2)
                    str.append(s1.substring(idx + motif.length, s1.length))
                }

                else -> {
                    println("****************************************")
                    println("ERROR s1 $s1 and s2 $s2 for motif $motif CONCATENATING")
                    println("****************************************")
                    str.append(s1)
                    str.append(s2)
                }
            }

            result = str.toString()

//            println("strings s1 $s1 s2 and $s2 AFTER")
//            println("********* merging i $i j $j key $key")
//            println("strings $s1 and $s2 with motif $motif result $str")
            hashStringList[i] = result
            hashStringList[j] = ""
            merged++
        }

        return result
    }
}