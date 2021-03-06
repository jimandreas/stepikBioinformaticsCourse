@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems

import algorithms.AlignmentLocal

/**
 * Code Challenge: Solve the Local Alignment Problem.

Input: Two protein strings written in the single-letter amino acid alphabet.

Output: The maximum score of a local alignment of the strings,
followed by a local alignment of these strings achieving the maximum score.
Use the PAM250 scoring matrix for matches and mismatches as well as the indel penalty σ = 5.

Note: this could be combined with the Global Alignment code.  Most of the code change
occurs in the outputLCS function.  But in the interests of clarity and reduction of complexity,
the changes to find the maximum score will occur only here.

MODIFIED: to accept:
A match score m, a mismatch penalty μ, a gap penalty σ

These are set up when the class is instantiated.

A modal flag indicates whether to use the PAM250 match/mismatch matrix.

The changes involve using the scoring matrix to calculate the winning value
for each cell.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240305/step/10?unit=212651
 * rosalind: @link: http://rosalind.info/problems/ba5e/
 * book (5.10):  http://rosalind.info/problems/ba5f/
 */



fun main() {

    val sample = """
THTQRRCYNNFTILKLCPKQDFAALSLCHWRMEIESVDAHMWSNPGIHIWPLFSAWNREDYDWKVFAMMEYYKGDHTYTWKSIKTMIYSRMYHSSQYGMGDGEKRIEEAAHLTMAFETESRDVCKSAARYKLWEFDFAKEGYIDTRTCWQHMIFWKRHLAHDHDMQRIVGCTHNVCHDAPILVGTHDVHGIATIMCEGQVVDAEMDVKVQLKYDPMINKAYEPPHLSCVSWKARFKVHGNQITVNVGMLKEREKTGFCFARSFHTVSVWIGAPMFATVWWKTIVYEYPPCEQRQGFIEILPPAIPNRDRRGIWMGQQHASWGIVFDKTAMGKALMVDKLFHSQWCQCNWANELKFLVKMQFLHMWFTQFMCTLQVILCQPCIGWVGPMTDTFHDIYVPWNPDGYQDVPPLSGSEVQIKMHHCAQGPTISGPLQCEHTPYARAQFRWVFVQSYYYHIVGNRHCKTVVMYFQRYMPPKEWSGQVWTDTWLYSHVFKVEIMFLVGEMCEYCTNFNIGLQHAIREYNNVNRYCNIQCAGFWHKFRMNPWQIEHSHWQETGIYWFPHPLSDGAHACQAKSVFMRFSTQLPNDNSLILINFGLMTPTWQCKRHESRYYALMSYDEKHECHMCKDHFNCKQSMELPRNADQPTLKQNCCNSMQCKDYYPKEVSGNWMPHHKCIDWATKDSQSQQWFYQYKADTAQLGFKVVNKKCCISAHMVDIKLKGKRMCTEISHYFWHCTNFVPHVQIKNNEGQHWSAGNNEPSVEWVEIRAIQNIMGCEQCTFERGVNVGYTKLLFQRMKWFFKSYFKEWWESGHDEDYIMRYMQVCAAPSHGHVDLDHPRVRCQSRETHCRKAYWSWVKCLQQMYFINFIRCHKRVDPDKAWGQEDEVMRWLSPHWAPLPYL
TWSWHRSFSEITYMEMNKPVDINEGQSHMEGAANNWECRMRTDKGQIYKDWEMLFSPEYGRQPIDSKRPSFVEKILKIYSKLPEVIMSNCWQLYEDNWSRCKNAIAMVQECDFNENWPPGHMFYVVKEPYARQVIFAVPMSDYDHQAWPTQLRPIYGAHIHVEHFEMLDHYYYIDIDGGLYIAQIFKTAGIQFWDEHHRRFPEYIHWDMRESRHKRCVQWHPWHWDPTSHRKQVRQEPIRIRALAIWGKWMMQWKAPFVNVGKLKEMTGMNISARSFHTVSVWIGAYYWDPMFATVWWKTIVYGWIEIESGMSPAIPRHHPRIWMGQQHARWGIVFDKTMQNMGQDLSQWCQCNWAMELKFLIKMQFCKVGCGVILCQPCIQMWVGPMTDTFPGIYLSYRESVCEDVHWLSGSPVQIKMHHNAQYPTISGPLMHIMMCEPTAQFRWVFVQDYYYHVVMYFQRYMGPKEWSGQVGTDTWLYSHVFKVEIGCEYCTDFNIGLQHAIREYNNVNRYCNIQCAGFWHKFRYLKHSHWQETCQAYDGAHMKSVFMKFNCLCLIMWSMTPWWQCWESRYQALNSYDEKHENFHSVAHMCKDHFDRKQISIVWMKNADRIDNEDNAYTRMMHTMRYRFNKWHFLTNKQAAPDICWCPSDYRIIVVKFGAAMDIHKFGAGGHMQGLIFLSMMLTTLMIWAMQLDDFHFYQNEGKVPSLQYWGKMQQCRVNWSWGILWLYQQPFMKNVSIMHRYYDQIDHQAPYPVCYAKNESREDYHTIPVQICCLKFDGTSGNRKNGTSKDGNLIWCHFIDSYVSDTHDGCYIDFRINREKIWVWKTMYSKLIEKHL
           """.trimIndent()

    val reader = sample.reader()
    val lines = reader.readLines()

    val la = AlignmentLocal(0, 0, 5, usePAM250 = true)
    val sRow = lines[0]
    val tCol = lines[1]

    val result = la.localAlignment(sRow, tCol)
    val scoreResult = result.first
    val sRowResult = result.second
    val tColResult = result.third

    println(scoreResult)
    println(sRowResult)
    println(tColResult)

    // worked!!

}


