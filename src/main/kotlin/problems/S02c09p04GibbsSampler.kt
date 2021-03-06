@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "CanBeVal")

package problems

import algorithms.GibbsSampler

/**
 *

Code Challenge: Implement GibbsSampler.

Input: Integers k, t, and N, followed by a space-separated collection of strings Dna.
Output: The strings BestMotifs resulting from running GibbsSampler(Dna, k, t, N)
with 20 random starts. Remember to use pseudocounts!

Note: The next lesson features a detailed example of GibbsSampler,
so you may wish to return to this problem later.


 * See also:
 * stepik: @link: https://stepik.org/lesson/240245/step/4?unit=212591
 * rosalind: @link: http://rosalind.info/problems/ba2g/
 */


fun main() {
    val gs = GibbsSampler()
    val dnaStrings = """
GGCGAAAAAGGGGCCGGTCAAAGAAGCTTTATCTATCGATCCGTTAGCGTAGCGCTAACAACCCCGAAACTTTTAGCAGCTACAAAGTAAGTCATAGCGTCCACCTCGCATTCCTGACAGATTCCAGTGGGGCTGCCGCCCGACATTAAGTTCCGCTGATACTAATCGTATGGAATAGAATGGACCATCACTCAACGAATGAGGTTAGCTAGTCGTCTCAGCGTTCGAGTAACTGTAACTTTTCATAGGACCGAAGGGATCACATCAAACTTGTTAAGACTGAACCCTAGTGTATCAACGTATCTGTTCGTTACGAGGCGAAAAAGGGGCC
GGTCAAAGAAGCTTTATCTATCGATCCGTTAGCGTAGCGCTAACAACCCCGAAACTTTTAGCAGCTACAAAGTAAGTCATAGCGTCCACCTCGCATTCCTGACAGATTCCAGTGGGGCTGCCGCCCGACATTAAGTTCCGCTGATACTAATCGTATGGAATAGAATGGACCATCACTCAACGAATGAGGTTAGCTAGTCGTCTCAGCGTTCGAGTAACTGTAACTTTTCATAGGACCGAAGGGATCACATCAAACTTGTTAAGACTGAACCCTAGTGTATCAACGTATCTGTTGAACAAGATAAAGTCCGTTACGAGGCGAAAAAGGGGCC
ACGTGTCACCCCTGTAAACCCCACGTACGGTGGTATTATACCTGTGTCATTTGCTAATTACATGACCACAATGGTGGTATAAGTAATACCGGTACATGGATTTGTTATAGGGTTAATCCGCCGATAAAGTCCAGTAGAGACGTACAGTCTGTACACTAAGCCCCATATCTCGTAACTAGCGCCCCATACTGGCGACAAATCGTGAAGTGACGGCGGCGGGGTCCGTGTATCGCCAGGACAACGGTCTGAGGAGCCAATGTACCGAATAGGAGACGAAGAACGACGAGAATTTGTTCGGTTTTAGGCAACCTCTTGTCGCGCGATCAGTTTG
GGAGGACGCTGGCATGAGATTCTCATGAACACTAACTCGGAGCACAGCACAGGTCACAAGAAACAGCAATGGCCATACATATGTATTATAACGCATTGGTCGGTCGAGTACGAATTCGCCGCTTGTCCGCCGCTCTGCTACGCTGCACCCACCGGACTATGGGTGTGCTCCTATTCCGTTACCACCTCCCCGACAGTTTAACCGAGTGTACGCGGTTAGGGCGCGATCCCTTCACGCCCACTCCACAATGCTTGCTGGAAGAAATTAGAAGCTCCTAAAGTCCTTTCAATAGCTATCGTTTACCTTGTGGTGACACACTTACCGTTTGAAA
CCAGATCTAGGTGATACATGCAGAAACGAACTCGATGCGGAGTTTGAAGCCGTATAAGTCGTTCCTTGTCTTCTGTCATCTTGAGCGGCAAAACATAAACCGTTTGGTGTTTGGACAATCTGCACGTTCAGAATCCGCACTGTCCGAGCTCCTGCCACTGTGGAATCATGGTCCCCGCACCGTATTTCGCTTGCTCCTAATACAGGGGTGTGGAAGTGGTTCATTTCCATAAATGAGTACCGGACCAAAGTGCTGAGTCGTCCCCGAATCGCCGATCGTCGTTATATTGTACGTGGTGGTATAGGGGATATAAGCCCAAAGTCATGAGGGG
TCCTAGAACTAGCTATACGCGGAGCCATTGTTAAGGGTTGTCACTTGGAGCACGGTACACGCAAGAATGAGGACCGAGTCGGAATTCTTGAGAACTTACTTTATCCGCTGTCCATGGTGTCATGAGCGGCGCCCCCGAAGCCCCGAAAGTCAGCTGGATTGGCACCCCTGAACGTGTTCTCTGTTTACAGTTCCTGTAGAGCAGCCAAGCATAACCCAAATCTCAGACGTTGTGTATAACAGTAGCTATCTGTGCAAGTTTCAATCATCTCGAGGCAGCTGTTGGGATACTTATACCTGAATGTCCAGTTTGGCGCGCCGTTACCCGCATT
ACGGTACGGTCGACCTGATGAAGCTAACTCGGCCGACAAGTATCTGTACCACGAAGCTTTTAAAGTCTGATGTATTTTTCGTTGGTTACCGGTCCGAAGCCGTGGTAGGGATGCCAAGTGGGTGCATAAACCGACACTGACTGTTATTTCTTTAACAGGCTACCACACGGGCCTCGTAGACACTATTCCCAGACAGGTGACGCGGGAAGGATAATTGTACCATAAAACTCTACCCCTTAATGGTTTCGTAATCTCTGCTTGGAGGGAGCGTGGGGCTACAGACGATCTAGAACTCAACTAACAGATAGCATGTAGATGCGTATGATTGTGT
AACAGTCACTTTTAAGCGAGATGTTACGGCCTGGTTATCTGTTTAAACATTATGCACGTGGCCGAGATAGTAACTACAATGTCTATGACAAGGGCATCCATACCGAAGTACATAAAGTCATATATCGAATACCACACACGACTCGATTGCACACAATTTTGACAACGCGTGATCCCAGAGCGCACGTGCGTGTATGCACATCCCCATCTGCTACCTCCGGACTGTTTGGGTTTAGAAATTTTAGTACACGGGTTGAGAATGAGAGACTCGCCAAAAAGTAAGTAGGACGATCCCACTGGATAAGGTCACGGTGAACTAGACCCTATTAGGG
CAACCACTATTGGTAGCCCGAGGAACCGTCGGCCGGACGCCTGCTGGATGAATAGGATAAAGTCTCCGTGGTACTCGACCGTTATGGTCTACATCGGTGTATTAAAACGAAGTGAAAATTACCCAGTAAAAGCTTGGTCTCCCAGTTGAAACAATCAGTCCAGGTCCCCATGCCTCACCGCGCGCCATGCAAGACACCGGACCAGTGCCGACGCCGCCCATGGCGGTTCCCTCAGAGAGGCAAGGAGGGTCGTCTAGAGAGAGCGCAATCATACTGATTAAATGTAGCGCCTAGTTTACTCGATTCTGGAGGCCCGTCGATGATTACGCAG
CGGCTTGTTGCCCGGGCCTGCACTCATCGAGACACGTAGCTGCTAGCACTGCGGACGGTTCGCGGAATACTCCGAGTTAAGCACTACAAAGATGGAGTATGTTGCCCCACCTAGTGCACAAGTTGCAACTCTCTCGCTCGGGTCGCGTAAGACACAATAATCCTTCTTGAGCTACGTCATGCCCGCTGTGGGATTTCCGGATTGCGCCGTATACTCATCGGGTTGCTCGGGTCATCGGACGGACTACCTAGTCATGAAGCCGATCCCGTCAGAGAACACAACCAGAGTAGAGGAAAACAACGTATCGGTAGGTATTCGCTACAAAACAAGA
AGCTGGCCAGCCCCTGCGGTAAGGTATGTAGCGCATACCTAGAGGGCGAAGAAGAAGCCCCCAAAGTCTCGGCGCATTTCACTACTGCGGTTCCGTCCAGGGCAGAGAAGCTGAGTTAATCCGCTACTAGATCGACCTACCTCGAGTCCCCAGGCTTGATAGACCGCCCAATCGATACCCGGAGGTTTGCACAGACCGTAAGGAACTCGAATATGGCCTCGGCTTTCGTCCATCTGCTTCCTGTTTGACAGGAGTGGGTCGAATGTGAATTTGGTCAAGGGTCTCGCTCTAATCGAGATGCAAACTTCAAGAATCGCAGTTACGCAGGTTA
AAGGCTCCAAAAACGGAACGAGACTTCGGGCTTTGATATACATGGCTGAGCTGTCGTATGCGGGCGCTCAATGAAGCCGATAGTCTCGTGTACTCCCGATTTTAGATTTTATCGTCTGGACAGTACCCTCGTACATAAACATCTACTAAAGCTGAAACCCCGTGGACGTCCACAGACAGTACTGAAAGGTCTGTAGCCATCTGACACGGCGGCGGCACACGCCCAGTACCAATAATATGCGTTTCATGGAGCTCGTCCTTAGAATTAAACTCTAGCTGCACCGCTGTCGCGGCTGTTGGAATGGGGCCAAGCCAAGTATCGTGTTTCATGC
GGAACGCGTGACACCGAGCCTGTATCAGCTAGAGTTCGCGCACCCTCCATCAATGGGGCCCTACGCACAGCCAAAATGTTTATGGCGCACAATCCTCCCCTTAAGCGGGACCTGATATGTGCCTACCTGTGACGAAGAGGTTGTCACGCGGGCGAAGTTACTTTGCAGCCAGTCAGGGTTCTTGCCGACCTCAAGTGACTCTCGCTTGGTTTGCGACCCGGCCTTTTGTCAGAGTTTACCATAGAGACCAGTACATGTACAAGACAATGACAAGAGTCGAGGTTCGTCCGCGGGAGCAACTGGCAGATCTGGAAGCCGATAATCGCGGGAC
CACCAATTGAAGTTGCATACCCAGATGATAACAGGTGAATCGGTGATCCGGATAAACGTGTCCCTGCACTCGCAGCAGTGAAGCTTGGATTTTGGGCACCATGTGGGCGCGACTTTGGTCCGTCACAGGCACGTTCTAATTCCCAAATAGCTCCTCTGCGAGATAGCATACAAACATCTCCAAACTGAAAGCTCTGTTGTCGTCGCGGTTTCTGGGAAGGCACGGGCGCCGGGCTAGCCGATAAAGTTGTAGCCTTATAGATTAATCCTTACCCTTGTAGCACGTCTAATGGTCGGCACGAGGTTTGCTGCGATTATTGCACTGGACATGG
AGCCTAGTGGTTTTCTCCGGCACGGGTTGGGGCGCGGTATCGGGCAGTCGTTACCTCAGTGACCTAATAGTACGATTGGGGCCTCAACGCTGAAATACGTTCGAGACATTGGTGGACCCCTCTACACGAGTACTTGTGAGCAGCGAAGATCATAAAGTCATGTAGGGCTATACTTTGCATCACGTACTAAATCCCCGACTCGGAGTCCTCTGGTTGTCGAAGATTGCGAGGTCCTAGGCTCGCAGGGACTTGGGTCCAAGTGTTGTGAAGAGGATCTTTCTCTTACCACCAAGCAAGTGCTCTGATTGGCTTCATTGGTGAAATCCTCTAC
AGTCTGCCCTTTCAGACGACTCTCTGGGTCGAAGAGGCTATGGTGACGGACCATATCCCGGGATCAAATGAAGCCGATAAACCTCTCACCAAAGAGAGCTAGGCAAGCGGCGTGAGAAGGTAGGTTGGTAGTGAGCGTCATGAGCTATGACCCTCCTAATGGTTCACGAGTTATGTGTGGCTTTGAAACGTATCTTGCTTGTGCTCGAAAAATTGTACTTGCTAACGGTGTACTCTGCGCACTGGAATCTCCGAGGCGGAGGGATGCTCGAGATAGTACCGGTAGGTCCAGCTTTCTAGCGCACCATCCGCCGAATATAACTCCTAGCTTT
TCTAACGGGCTTTTATATAAACACGCCCGTGCACTGGCAGTCAACCCCGGAGTCCTGCTCCTGACATTGCTAGGTCGACATGGGACCGATAAAGTCATCCTATGAGTCTGTCTCTACTGTCATCTTTCCAGTCACCCGCCCGGACACGCTCCAGTTATAACCGTAAAGTCTTGTTCGAAATCCGCTCGTGTTCCTCGCGCAACAGGACGGGGTCGGAAGTGTAGCACGATCGCTTCGAATCCAACACTGCCGCAGAACCGATAGATAGACTCGTTAGCACCCCTCCCAGGCCTTCTATAGAAGGGCTCTTGTTAAAGCTTTAGCACAGCCA
TTTAGACCGTATTACCCACTAGAATGGTATTGCTTCAGCAGGAGTACGCATCCGTATGAATCCTTCAACTACTTATGACTTTTTCGCATTTGAGGATGGTTAATCCCACTTCATGCCAGGCATCAGACGACGAATCTACTGTTAGAGTAAAACTACCGTTGGTCCCACACTAATGTGTTACCAGTTGTATGTTGGTCGCGTTGATAAATTCCTGCGATCGGGATCGCTGCACAAGTAACCCGTCTGCGACAGCGATAAAGTCCGGACCAGGCCCTTATCTCGAGGCCGACTTCTAAATTAGATCACGTGTAAATAAGCTCGAGAACTTTCC
CTTTGTCCAGATGACAACCTATACTGACTCAACCGTGTGTAGGATACCGAAGATAGGATCGCTAACCCCTCAGTGAGCCGCTAGTCAGACTAACGTCGGAGTGGTCCGCAAATGCTTTGCCGGAAATATTGTACATAGAGAGCGTCTTACCCTCACAATCTGTTGCCTCTTTCTCCAAGCCGATAAAGGTCATGTTTGGAAGTAAATTTAAGACCGTGCTGGATCACACTGTAGGCTGCCTCACGCCGCGTATGCTCCTTCCTCCCCAATTTGTAGGCCAGTCCGAAATGCCTGCGAACTAGCTTGGTAGATATATCACATAACTTAGCGG
CGGTTCGTCGGCCGTAATACGAAATATAGGGTTCCGTCCCGTAGTTCGACTTACTGCGTACAACATATATGATTATGGCAGGACCCCTTAAGTTTATTAGCTAAGAAGCCGAAGGAGTCAGTGTTAGTTAGAATTGAAAGGCTATCCTTGATGAACGCGCATGGCATACAATTACGGGATGCAGGAGAGACCTGTCTGGTAGCCTGGGTTGGTGGTCAGCAGATCAAGTAAGTCTTACTAAAAGTTATCTGCCTAGCGCTCGACGGGCGCGGCTGTGCACTGGGGGTGCTTGACCCAGGCGGAACAGACCAGAGCCGGGCTGGTCGACGTG
        """.trimIndent()

    val k = 15 // kmer length

    val reader = dnaStrings.reader()
    val lines = reader.readLines()

    var winCount = 0

    val scoreList: MutableList<Int> = mutableListOf()
    val iterations = 200

    var minScore = Int.MAX_VALUE
    var bestResult = emptyList<String>()
    for (i in 0..iterations) {
        val result = gs.doGibbsSampler(lines, k, 2000)

        var winners = 0
//        for (j in 0 until expectedResultList.size) {
//            if (result[j] == expectedResultList[j]) {
//                winners++
//            }
//        }
//        if (winners == expectedResultList.size) {
//            //println(winners)
//            winCount++
//            scoreList.add(gs.bestScore)
//        } else if (gs.bestScore == 7) {
//            println("${gs.bestScore}")
//        }

        if (gs.bestScore < minScore) {
            minScore = gs.bestScore
            bestResult = result

        }
        //println(winners)

    }

//    println("$winCount winners in $iterations tries")
//    if (scoreList.size > 1) {
//        var diffCount = 0
//        for (score in 1 until scoreList.size) {
//            if (scoreList[score - 1] != scoreList[score]) {
//                diffCount++
//            }
//        }
//
//        println("$diffCount diffs first value ${scoreList[0]}")
//    }

    println("best score $minScore")
//    println(bestResult.joinToString("\n")) // rosalind format
    println(bestResult.joinToString(" ")) // stepik format

}





