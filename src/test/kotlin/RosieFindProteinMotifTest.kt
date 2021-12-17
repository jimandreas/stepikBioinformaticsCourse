@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.FindProteinMotif
import algorithms.UniprotDownloadFASTA
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * See also:
 * Rosalind: http://rosalind.info/problems/mprt/
 *
Problem

To allow for the presence of its varying forms,
a protein motif is represented by a shorthand as follows:
[ X Y ] means "either X or Y" and {X} means "any amino acid except X."
For example, the N-glycosylation motif is written as N{P}[ S or T ]{P}.

 */

internal class RosieFindProteinMotifTest {


    lateinit var u: Utility
    lateinit var download: UniprotDownloadFASTA
    lateinit var findMotif: FindProteinMotif

    @BeforeEach
    fun setUp() {

        u = Utility()
        download = UniprotDownloadFASTA()
        findMotif = FindProteinMotif()

//        println("--------------------------")
//        println("RosieFindProteinMotifTest")
//        println("--------------------------")
    }

    @Test
    @DisplayName("Rosalind Find Protein Motif sample test")
    fun rosalindConsensusSampleTest() {
        val sampleInput = """
A2Z669
B5ZC00
P07204_TRBM_HUMAN
P20840_SAG1_YEAST
        """.trimIndent().lines()

        val expectedResult = """
B5ZC00
85 118 142 306 395
P07204_TRBM_HUMAN
47 115 116 382 409
P20840_SAG1_YEAST
79 109 135 248 306 348 364 402 485 501 614
        """.trimIndent().lines()

        var expectedIndex = 0

        for (id in sampleInput) {
            val fastaText = download.downloadFASTAfromUniprot(id).lines()

            val fastaString = u.utilityParseFASTA(fastaText) // just doing one at a time

            val indexes = findMotif.findMotifs(fastaString[0])

            if (indexes.isNotEmpty()) {
//                println(id)
//                println(indexes.joinToString(" "))

                assertEquals(expectedResult[expectedIndex++], id)
                val expectedIndexes = expectedResult[expectedIndex++].split(" ").map { it.toInt() }.toList()
                assertEquals(expectedIndexes, indexes)
            }
        }
    }

    @Test
    @DisplayName("Rosalind Find Protein Motif quiz test")
    fun rosalindConsensusQuizTest() {
        val sampleInput = """
P21810_PGS1_HUMAN
Q5U1Y9
P05155_IC1_HUMAN
P01046_KNL1_BOVIN
P37803
P39873_RNBR_BOVIN
Q50228
P05113_IL5_HUMAN
A9QYN2
A6LJ74
B5FPF2
Q28409
P08709_FA7_HUMAN
        """.trimIndent().lines()

        val expectedResult = """
P21810_PGS1_HUMAN
270 311
Q5U1Y9
102
P05155_IC1_HUMAN
25 69 81 238 253 352
P01046_KNL1_BOVIN
47 87 168 169 197 204
P37803
110
P39873_RNBR_BOVIN
88
Q50228
55 228
P05113_IL5_HUMAN
47 90
B5FPF2
18
P08709_FA7_HUMAN
205 382
        """.trimIndent().lines()

        var expectedIndex = 0

        for (id in sampleInput) {
            val fastaText = download.downloadFASTAfromUniprot(id).lines()

            val fastaString = u.utilityParseFASTA(fastaText) // just doing one at a time

            val indexes = findMotif.findMotifs(fastaString[0])

            if (indexes.isNotEmpty()) {
//                println(id)
//                println(indexes.joinToString(" "))

                assertEquals(expectedResult[expectedIndex++], id)
                val expectedIndexes = expectedResult[expectedIndex++].split(" ").map { it.toInt() }.toList()
                assertEquals(expectedIndexes, indexes)
            }
        }
    }


}