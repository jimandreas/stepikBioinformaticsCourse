@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

package problems

import algorithms.DeBruijnGraphFromString

/**

Code Challenge: Solve the De Bruijn Graph from a String Problem.

Input: An integer k and a string Text.
Output: DeBruijnk(Text), in the form of an adjacency list.

 * @link:
 * Rosalind: http://rosalind.info/problems/ba3d/
 */

fun main() {

    val dbgfs = DeBruijnGraphFromString()

    //val dnaString = "AAGATTCTCTAAGA"
//     val dnaString = "TTTTTTTTTT"
    val dnaString = "ATCTATGTTTCGGAAGTATAACTACCCCTTTCACTCAGTCTCCCCCTTATTAAAGCTCCGTTTGCGGGATCAAGCGCCGCTCGCTGAGCTAAATAACAACTATTGCGCTTCTTCTATTAAAGTTGGGATGCGGCGTACCATATGTTCCTCCCATGATCCTAATCCGTATATTACCACCAATTCAAACACAGTTTTCCGACTCATAAAACACCGAGATAGGCGGTTGACAGGTTAGAAAATCGACGACCGCCGGGAGGCTCGTCTGGCAGAGTCATCACCTTGGACCGCTTAGTACCTCGGGACAAGATAAGTACTGGTTTACTTCAATAGGCCCTTGGACCAATATACATGATTCGCACCTTAGATCAGGGAACTGTAGGCCCTAAGTCCGTCTATTCGTTCATCTCATAGGATATCCATGTTGAGGAAAAGGGTATGCTGCGCTAGCTGTCTTTTGGTGCGGCCAGAATCAGTAACTTCGACGTTAAATAAGGTTGGGTGCCCATACCTCTGGCAGCTCTGCACGCGTCGTCCTGGGTTATTGGGGAGAATCCGTATACAAACGGTTTATTTGTTCCCTCGTCTGCTTAGTTAAGAATGATCACCCCAGTTATTGCATGCACGAGGGCACGGATAAACCTCGACTTATTGAGGCGTCGACCTTATTGGTAGCATAAAGACGCCTTGTTTTATGCCGCAGAGACCAGGTGCCGCGAAAACTAATGATCACCGTGCAACGCAGGTCACAATACACCTAGCTCACACGGCTAGCAGTGTGGCGAAAGAGAAGGCGCAACATCGCATAAATAACCATATAAGACATCATGAGCAAAAGGAAGTCCTCTCGCATCATTCTTGCTTAGACCTAGTGGCCGGGTCACAGACTTCTGTGTTTTAAACATGCGGTTAAAGCAGACCCGGGACAAGAAGGGAAGGGGGATGAACGTACAACAACAGAGATGGAGGAGAAACTCCATCACCTAGCGTCATGTTGAGTCCCACGTGAGTCCCGCTCAGCCGCTGGGGACGGCAAAACCCTGACTTCTAGGCTTACATGAATGTCTAGTGGCATGAAGTGACATGAGTTCTGTGCAGCACTATTTTCGGCGCGTGTTCCTTTCGGGTCAAATAGTGCCTCTAATGACGTAACCAGGGCACGGAACTAAGCCCTGACACCGGTCCGCTGAACTTCCTACGAGTGGGGCCACTTGAGGCCTTAGCCCATATAAGACGGAGGATGCACTCGAATCAGCACATCCCTCGTTAGTGTCGGAAACTCTTTCGCCGTATGTCGTTGGCCACCCACTGAGGAGGCCCTCCTTCACGACCCTAGGGACACATAAGCCGGACGACCTATGATGCGTTCTTTACAGCCAGCTTCCCCCTGCTAATGAAAGTCGGAGGACTGCTGTTTCATCACCTTATTTGTCTACGATACACTGCGAGTGGAGATTTACGTGTTCACGAAAATCCAATCGGTGGACGGCCTTATGTATCTGAATTCTTACTTCGACTCAGGAGGTCACAGTGCCTACCGTCGTCTACTAGGCCAAGAAGAGATAAGACGATTTAGCCCCCCTCTCCCAAGAGTCACCTAGCCTTGATCGTTCTAAACATTAATGACTCGACGGGAAAGTGCACTGTGGGTAACGCGGTAGTTTGATGCGAACGTAAACGGATGGCTCTCATATGACAGCTACAGAACCAGTGGATCAACACCCACCTGAGAAGATAGGTTTCGCGTAGGTGGAGTATCCCGCCCCACCGATTCTGCAGGTGAGGCACCCATAGCCCAACCGTTTGTAGAGCGCGTGCGGACTTCATCGCTTTCACGCATTGGTACCGGGGCAGAAGCGCGGGGGATCTTATGATGCAACTCATAAGCCTAATTCTCTCATCTACGCTGGCGGACGGGCGTTAGATAATCTAGCTTTCCGCATAACTCGAAGCTAGCGCACGACCGCGATTATCCACTAAGGCCTGGAAAACGGAAAGTACCC"
// note this string did not produce a correct answer.
    val kmerLength = 12

    val result = dbgfs.deBruijnGraph(dnaString, kmerLength)
    val resultString = toOutString(result)
    println(resultString)
}

private fun toOutString(m: Map<String, List<String>>): String {
    val str = StringBuilder()
    val foo = m.toSortedMap()
    for (e in foo) {
        str.append(e.key)
        str.append(" -> ")
        val l = m[e.key]!!.sorted()
        str.append(l.joinToString(","))
        str.append("\n")
    }
    return str.toString()
}


//fun deBruijnGraph(d: String, kmerLength: Int, baseKmerList: MutableList<String>, foundArray: MutableList<MutableList<String>>) {
//
//    val strLen = d.length
//
//    // walk a kmer size window through the dnaString
//
//    for (i in 0 until strLen - kmerLength + 1) {
//        val foundList: MutableList<String> = mutableListOf()
//        val baseKmer = d.substring(i, i + kmerLength - 1)
//
//        // if this is a new base kmer, add it and its follow-on kmer
//        if (!baseKmerList.contains(baseKmer)) {
//            baseKmerList.add(baseKmer)
//            foundList.add(d.substring(i + 1, i + kmerLength))
//
//            // now scan the remainder of the list for the same base kmer
//            for (j in i + 1 until strLen - kmerLength + 1) {
//
//                val testKmer = d.substring(j, j + kmerLength - 1)
//                if (baseKmer == testKmer) {
//                    val foundKmer = d.substring(i + 1, i + kmerLength)
//                    foundList.add(foundKmer)
//                }
//            }
//            foundArray.add(foundList)
//        }
//    }
//}


//    val outputMessagesFilePath = "stringsComposition.txt"
//
//    val outFile = File(outputMessagesFilePath)
//    val writer = outFile.bufferedWriter()

