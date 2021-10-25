package problems

import algorithms.aminoAcidToRnaCodonList

/**
 * see:
 * http://rosalind.info/problems/mrna/
 * Inferring mRNA from Protein

Given: A protein string of length at most 1000 aa.

Return: The total number of different RNA strings from which
the protein could have been translated, modulo 1,000,000.
(Don't neglect the importance of the stop codon in protein translation.)

 */
fun main() {

    val sampleProtein = "MA"
    val totalNum = inferRna(sampleProtein)
    println("MA is $totalNum")

    val quizProtein = "MDNYYTSVEQWWWARLTHGKVTMKNQYEFKVEQNSMLCQCHDWTLFMSNAQDVHVSAIMLYGLLRQQAGGNHTKGGWSVDSWTHTNFWRFEICCEVRNQPTFQIDKRAANVKHMHREYTRIRQYQVKTLMFYGEHLLDGLNERRMGLEYFSQSVGDDCYNGDLQQRPVPWSAWTEHGAWGVWSEKYRRTYPLWNAMGIDPPQPDTEFFWGTEENYRHVNFHTQHEWGECIPEHHADHLRQVDRMLDPYMWRMSLIANVNFHSHIQIKSEKIKIDREIDQEMVWKYTSHERPEMGGTKHLMMGYEYNWGDAKTQPNLMCTDHPKFMSIFRYYEKNLESDALQVCIFTWGHCHQYIEKINQWPHWMFKRVPDMVMCPAVISCIMWQQLMTHKILCMQQAFKIKPAGIEGMFVGWMWTQDSEMRYHRRKPDLTRSLYDWGNPDETCDFGNGQTEACRYCVFYNHMHCISVVHFVQCWTVFPASPNTKEMNCGMISMCKMMAKHSSTFCGECCAVQGRAHYECLQNIDCGQGHHLNRCMIFPGYRGACKSDNILCDLNQYFNHSWPAENDHEGLWNWNTVAIFKWMNDHMPRANPAMPIWAPKDSHEGNWMVIFAWVNFDMLRNSLNKGWSPHVSFFKLQASYNYMYCPSLLTFACKPMEWEMLQLKQSAMLDKITAPIVLMVYWGQPGKQPMVMMWCPCKGCVDHIHTADYIDYGVDTPCEKAARISDQGPVKSYIQTLGEPNWFWTFQPVEDSDRRFFVHVLVQPGPVFMCNRTGVHLAFHHQQPAQDSMGRQPERFYHHADACRWAQLGLCYPWHTYFTQFLLGKSQTYMVIPLAQFFEYAHQECFKKGTEQKKRMQCCTTCKKCDKQHFETTRGYQWFCPGVKPEDGDDESHEAQTSKMCRQGMERRSQVPLPRPEFRLIYAGWVDFFMKKMFDIFDKFCTTYIRWVKPLKKSFFRDPQWESAYGQTTRSMTERHQQTRVRCQNFWRHQLRRGCTHIVY"
    val quizTotal = inferRna(quizProtein)
    println("$quizTotal should be 981184")
}

private fun inferRna(protein: String): Int {
    var totalNumber = 1
    for (c in protein) {
        val cString = c.toString()
        if (!aminoAcidToRnaCodonList.containsKey(cString)) {
            println("Amino $cString not found!")
            continue
        }
        val numCodons = aminoAcidToRnaCodonList[c.toString()]!!.size
        totalNumber *= numCodons
        totalNumber %= 1000000
        //println(totalNumber)
    }
    totalNumber *= 3 // add the stop codons to the possibilities
    totalNumber %= 1000000

    return totalNumber
}