@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

import java.io.File

/**
 * @link: https://stepik.org/lesson/240257/step/6?unit=212603
 *
Code Challenge: Solve the De Bruijn Graph from a String Problem.

Input: An integer k and a string Text.
Output: DeBruijnk(Text), in the form of an adjacency list.

 */

fun main() {

    //val dnaString = "AAGATTCTCTAAGA"
//     val dnaString = "TTTTTTTTTT"
    val dnaString = "GACTGCCGGTTTACCCAGACCCAGGCCACTAAACCCTTGCCGACTGACGATAGTCCTTTAGGTTTAGCAATCTAGGGTGGAAGCTACTGAGGCGGATTTGCACGGCTGCAAAAAGTGGATAACTGCAGTCAAGTCAAGACAAGATGACGAAGTGCCTAAGTGGTGGTCGTAGTAAGATATTCATCATCTTACATTGATCGGAGTGATTTTTCTTACTCTACAGTTGCGGATTATCAGAGGTATCAGGAGTGATTTTGTCAGGTTAGTCAGCGTTTAAAGCCCTGCTTTGGAGAAGCTGAGGGGTTGTCACGGGCCCTCCCAGATGAGTCGAAAGCTAGCACAAAAGTTAATACGCTCGCGCAATGGGATGAACCATTGAGACCTTTTCCCGCGGTAATGAGTAAAGTAGCACGATGGTCGTTCTTGCTAACCCAATTAAGAAACCTGTAAAAGGGTGACAGGTCGTATTCCCTTTCCCCTCACCTACAATTGCATGACCATTCACGTTGCGCGCGAAATCTCATAACCAAGGGATGGACTGCAATGTGCTTTTTTCGTTATCTGCGTATTTCGAACTCACTCACGCAGACTGCCATGCTGACGGGGCTGCCCACAACCCGTTAGCCCCACAGTGACATTGGTAACGATTCAGCGTCGATGAACGCCCTACGCGTCGTCCCCCCGCATATCGAAGGAGTAACATGTGAATGAGAAGAGGCGTGCTCTGACGCAGCCGCTGAGTCATCTCGGCACATCACAGCCAGTGCTCGTGGACTCAGCTCGTAAGGTGCGGAGATCCGGGGAAGACCAAGCACAGTAGTGAGCGCTCCTATTCCTTGCAACAAACGGCCAGTTGCACCAAAAAGACTTGATCATGGACGGCCACCACAGTTTTCGTGGACAACATGCATGTAGCGCTCGCACTTGAGGCTAAAATAGTATCTCCGTGTCCTCATCATATAGATCGCCGATGCCGAACTCCGCTATGCGCATAACCAGCTTACGAGCAGCAGTCATTTTCTCACCATCGCTTACCGTTTATACATACATCAGATGGGCGAGTTCTAAGAATACATCATGGACGAAACTGTGTCGGAAACCTGATAAGATTTAGAAGTTATTTTCCGATACCGGTAAAGTTCTATATGCCCTGACTATTTAGCAGGACCATGTGTTGTACACCCAGCGACTATGCGGAGGTAGATCAGGTTTAGGAAGTCCCTACTTAGGATACTGAGGCGCGAATTGTGGTCCCCCTATCACGGCAATTACTTGAAGTTTCTAACTTTTGTCGTCCAAGAGGGTCGAAGACAGTAGCATGATTATTTACGTCCTCTTAAGGGGTGGTTTCCATCGCAAGCCCTTTTTCGTTCTTTACGCCATCAGCCGCAAGGCGCTCAACTGCGGGGTATCCTATACGGTGGCATACCGCAAATCACGCTGGTTGAATCGTAGAACAGTGGTAGGGTTCCTCTGGACTAGTCTACCCCAGGTAGCCCCTCGACCCGATGTTCGGGTTGTCGGACCTCGATCGCGTTCCCTGAACTGAAGTCCACGGCGACTACAGATTAATACCTCGCGCTGGGCAAATCCGACAGACCCGGCAGAATGACCGGCTACGTCATGGAGAGATGCGTTGCGTCTTGCACGGACCCCTCATTCTTTGCGTATTACCGCAATTAGTAATACAAAGGTTTGTCAGCACCACACCATACAGATGGAGCGACAATTGGCGTGACCCTACTGACCAGTCGAGCTTTACACCCTAGTCCGAGTTATCGGGCCCTGACATTTAAGAAGCATACTTATAGGATTCGCAATGCCTTCTCCCCATTGTAGGACTACTATATAAGGACCCATTAATAACGCGCCGCCGGTGGTTGGGTAACCCACTGAGTAACAGGGACGATGACTTCTACCATAGAACTAAGAGGGTTGGAGGGTTACGTGCAGAGTAGCCAATCGCTAATCTTGTATAAAGATTAAGCCGCCGGGTTGTG"
// note this string did not produce a correct answer.
    val kmerLength = 12

    val baseKmerList: MutableList<String> = mutableListOf()
    val foundArray: MutableList<MutableList<String>> = mutableListOf()

    deBruijnGraph(dnaString, kmerLength, baseKmerList, foundArray)

    val outputMessagesFilePath = "stringsDeBruijn.txt"

    val outFile = File(outputMessagesFilePath)
    val writer = outFile.bufferedWriter()

    val iter = baseKmerList.iterator().withIndex()
    while (iter.hasNext()) {
        val baseKmer = iter.next()
        print("${baseKmer.value} -> ")
        writer.write("${baseKmer.value} -> ")
        val foundList = foundArray[baseKmer.index]
        print(foundList.joinToString(separator = ","))
        writer.write(foundList.joinToString(separator = ","))
        print("\n")
        writer.write("\n")
    }

    writer.close()


    /*
GCT -> CTT
CTT -> TTC,TTC
TTC -> TCT
TCT -> CTT
     */

}

fun deBruijnGraph(d: String, kmerLength: Int, baseKmerList: MutableList<String>, foundArray: MutableList<MutableList<String>>) {

    val strLen = d.length

    // walk a kmer size window through the dnaString

    for (i in 0 until strLen - kmerLength + 1) {
        val foundList: MutableList<String> = mutableListOf()
        val baseKmer = d.substring(i, i + kmerLength - 1)

        // if this is a new base kmer, add it and its follow-on kmer
        if (!baseKmerList.contains(baseKmer)) {
            baseKmerList.add(baseKmer)
            foundList.add(d.substring(i + 1, i + kmerLength))

            // now scan the remainder of the list for the same base kmer
            for (j in i + 1 until strLen - kmerLength + 1) {

                val testKmer = d.substring(j, j + kmerLength - 1)
                if (baseKmer == testKmer) {
                    val foundKmer = d.substring(i + 1, i + kmerLength)
                    foundList.add(foundKmer)
                }
            }
            foundArray.add(foundList)
        }
    }
}


//    val outputMessagesFilePath = "stringsComposition.txt"
//
//    val outFile = File(outputMessagesFilePath)
//    val writer = outFile.bufferedWriter()

