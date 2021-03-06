@file:Suppress("MemberVisibilityCanBePrivate", "ReplaceManualRangeWithIndicesCalls")

import algorithms.GibbsSampler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.Ignore

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

internal class S02c09p04GibbsSamplerTest {

    val gs = GibbsSampler()

    /**
     * Case 1
    Description: A small and hand-solvable dataset taken from the example problem on Stepik.
     */
    @Test
    @Ignore // THIS fails as the correct answer is found only 6% of the time
    @DisplayName("test RandomizedMotifSearch 1")
    fun testRandomizedMotifSearch() {

        val dnaStrings = """
            CGCCCCTCTCGGGGGTGTTCAGTAACCGGCCA
            GGGCGAGGTATGTGTAAGTGCCAAGGTGCCAG
            TAGTACCGAGACCGAAAGAAGTATACAGGCGT
            TAGATCAAGTTTCAGGTGCACGTCGGTGAACC
            AATCCACCAGCTCCACGTGCAATGTTGGCCTA
        """.trimIndent()

        val k = 8 // kmer length

        val expectedResult = """
            TCTCGGGG CCAAGGTG TACAGGCG TTCAGGTG TCCACGTG
        """.trimIndent()

        val reader = dnaStrings.reader()
        val lines = reader.readLines()

        val result = gs.doGibbsSampler(lines, k, 5000)

        assertEquals(expectedResult, result.joinToString(" "))

    }

    @Test
    @Ignore  // this works by iterating
    @DisplayName("test RandomizedMotifSearch 1 iter")
    fun testRandomizedMotifSearchIter() {

        val dnaStrings = """
            CGCCCCTCTCGGGGGTGTTCAGTAACCGGCCA
            GGGCGAGGTATGTGTAAGTGCCAAGGTGCCAG
            TAGTACCGAGACCGAAAGAAGTATACAGGCGT
            TAGATCAAGTTTCAGGTGCACGTCGGTGAACC
            AATCCACCAGCTCCACGTGCAATGTTGGCCTA
        """.trimIndent()

        val k = 8 // kmer length

        val expectedResultList = """
            TCTCGGGG CCAAGGTG TACAGGCG TTCAGGTG TCCACGTG
        """.trimIndent().split(" ")

        val reader = dnaStrings.reader()
        val lines = reader.readLines()

        var winCount = 0

        val scoreList: MutableList<Int> = mutableListOf()
        val iterations = 10000
        for (i in 0..iterations) {
            val result = gs.doGibbsSampler(lines, k, 100)

            var winners = 0
            for (j in 0 until expectedResultList.size) {
                if (result[j] == expectedResultList[j]) {
                    winners++
                }
            }
            if (winners == expectedResultList.size) {
                //println(winners)
                winCount++
                scoreList.add(gs.bestScore)
            }
            //println(winners)

        }
        println("$winCount winners in $iterations tries")
        var diffCount = 0
        for (score in 1 until scoreList.size) {
            if (scoreList[score - 1] != scoreList[score]) {
                diffCount++
            }
        }
        println("$diffCount diffs first value ${scoreList[0]}")


        //assertEquals(expectedResult, result.joinToString(" "))

    }

    /**
     * Case 2
    This dataset checks if your code has an off-by-one error at the beginning of each sequence
    of Dna.
     */
    @Test
    @Ignore
    @DisplayName("test RandomizedMotifSearch 2")
    fun testRandomizedMotifSearch02() {

        val dnaStrings = """
            AATTGGCACATCATTATCGATAACGATTCGCCGCATTGCC
            GGTTAACATCGAATAACTGACACCTGCTCTGGCACCGCTC
            AATTGGCGGCGGTATAGCCAGATAGTGCCAATAATTTCCT
            GGTTAATGGTGAAGTGTGGGTTATGGGGAAAGGCAGACTG
            AATTGGACGGCAACTACGGTTACAACGCAGCAAGAATATT
            GGTTAACTGTTGTTGCTAACACCGTTAAGCGACGGCAACT
            AATTGGCCAACGTAGGCGCGGCTTGGCATCTCGGTGTGTG
            GGTTAAAAGGCGCATCTTACTCTTTTCGCTTTCAAAAAAA
        """.trimIndent()

        val k = 6 // kmer length

        val expectedResultList = """
            CGATAA GGTTAA GGTATA GGTTAA GGTTAC GGTTAA GGCCAA GGTTAA
        """.trimIndent().split(" ")

        val reader = dnaStrings.reader()
        val lines = reader.readLines()

        var winCount = 0

        val scoreList: MutableList<Int> = mutableListOf()
        val iterations = 500
        for (i in 0..iterations) {
            val result = gs.doGibbsSampler(lines, k, 30)

            var winners = 0
            for (j in 0 until expectedResultList.size) {
                if (result[j] == expectedResultList[j]) {
                    winners++
                }
            }
            if (winners == expectedResultList.size) {
                //println(winners)
                winCount++
                scoreList.add(gs.bestScore)
            } else if (gs.bestScore == 7) {
                println("${gs.bestScore}")
            }
            //println(winners)

        }
        println("$winCount winners in $iterations tries")
        var diffCount = 0
        for (score in 1 until scoreList.size) {
            if (scoreList[score - 1] != scoreList[score]) {
                diffCount++
            }
        }
        println("$diffCount diffs first value ${scoreList[0]}")


    }

    /**
     *
    Rosalind extra dataset
     */
    @Test
    @Ignore
    @DisplayName("test RandomizedMotifSearch ExtraDataset")
    fun testRandomizedMotifSearchExtraDataset() {

        val dnaStrings = """
CTCTTGATTAAGGAGATGTAAAACTCTTTCCGGACATTAACTTGTCGATTGGTTCGTTTTATGATTGTTAGCCCATACAACGAGTGCTACTTTCGACGATTACCTGGCAACAATAGACAAGTCAGGGCCGCGGAAGACTGATCCCCTATACAGACCGTTATCATGCTACGAGAACGGTTGTCTAGCAACTCTTAGCTACGTGTGACGTCCACCGGCGTCGAGCCTGGCGACTATTAAATTCGCATGCGCTAAAAGCACCTGTTATAAACGGCTGTCAGCGATGTTCGGCCGATATGCGCATCTTCGTTTCCTCTTGATTAAGGAG
ATGTAAAACTCTTTCCGGACATTAACTTGTCGATTGGTTCGTTTTATGATTGTTAGCCCATACAACGAGTGCTACTTTCGACGATTACCTGGCAACAATAGACAAGTCAGGGCCGCGGAAGACTGATCCCCTATACAGACCGTTATCATGCTACGAGAACGGTTGTCTAGCAACTCTTAGCTACGTGTGACGTCCACCGGCGTCGAGCCTGGCGACTATTAAATTCGCATGCGCTAAAAGCACCTGTTATAAACGGCTGTCAGCGATGTTCGGCCGATATGCGCATCTTCGTAAGCGCACCGGGGTGTTCCTCTTGATTAAGGAG
GAGATGATAGGTTGGCCGGTTCGCCTCGATACGGTCCACGCCTGCTGGAATCTAGCTAGACAATTGCTTAGTGGATTCATTCTCCTCACCCCTGTAATTTACCCTTACCGGGGTGGGGAGGAAATACTCCACGTAGAACACGTTTACGAGCCTAAGGGCCGAGAATCACATAAGGCGTCTAACTATTAAGTGCCTTTGGTATCGATTATTGTGTTTTTCCCCATGCCCGCAGTCCTCCACTTAATAGACTGCTATCAACTATGGTAAATCAATTTCCACGATCGGGCTCTCGAACTTCTGTGTTATCCGATACGTCGCCGAAATC
GCCTAATTGAATTATAAAGTATTTCGTCCGACATATCGCCATGTTGACTGTATGCGCATGGAATTCGCTTCGAGAAGTTCCTCGGGGTGAGGCACGTTTTGAAGAACCCGGAAGCTCCTTCGGTTGAGCCTAAGTTTACTCTATAGGCAATCTCACCATCCGCGTCCACCCAATCGCGTGAGGTAAGATCTAAGTCCGGCTGCAAGTATCCATAAGGCCCCTTGCGGATGGTCACGTCTCTTAGCAAGGAGTCAATGAGATCGGCCCTCCCTACCCTTAGTCTATGTTTTGGCATAAGCATTGGGAATTGTGTAGGATATGTGAG
CGTTTCATCTACATGACATTGCTGCTACGACATGCGTGTCGCCCTCCTGGAGCCCAGTGTTGATCACCGTGGGAACGTTCCTAATAGCTGAAGTGAGGACTGGGAATTCGTTCACTTGACGTCTCACCTGTCGATTTATGCATTTGAAGCTCAATTTGGGGGTAAATTGGAATGAGAGCGAAGAGACGTTTACCTATCCTTCTAATAGGAAACTTCTAGTTGGATGATGAGATAAGTTTTATGGGGTGTATATTGGGCGTCAATGAACCCTCGCCAGTGTAAACACCAATTTCCATTGAGGTTGGGTGGTAGAGTCCGCGGGACA
TAGACTAACCCACACGTAACCAATTGGTTTTTCGGACAGGGTGAAGGGATGTGTGCATCGAAAGTTTTTAGCTACGACTGTAATATCCACTTCACCTCTGTCCACCAGTACAATCCAGGTAATAAATCTCCTCTGGCTGGTGCTTTAAAGGGAGTCTGTTTCACGATCCTTGAACAGGTGCGTCTCACGAGGACGTGTATGAATTTTCATAATAGACGTGTTCCCGAGCCACCAACAGGAGCGTGCCTGATTCGGAAGATGCAAAGCCAATTGCATACCACCTGCACAGGAGGAGGCATGGATCGCAAGTTTACCGGGTGCAAGG
CCTACTTGACAAGCGTAGGCGCGGTACGCAAGTGTTGCGTTCTCCCTCGCAACACCCGTCAGTGCTACGGGGACGGGTTTTACGACTTGACGCTCTTCCGGCCACCTGCATTAACTCGACGGAATGAGCACGGCTCGGTAGGCGATCGAGTATGCGTCATGGGAAAATAGGAATCGGACGCCCCTCGGGCATATTAAGCCTGCGTTCGTGTTGTCCTTACGATATTAGCCTACCAAGTTTCGAGGGGTGCCAAGCTCAAGTGATCCGGAACTTTGCTTTACCACCACCGCCATCCAGGGCATTATACATCGCTCCCTTGTGACCT
TAATACACATCCTCGGACTCCACATGACGATACCACTAAAAAATCAACGACCTTTCGGCCGCATGATAGGTCATGAGGGGGCAGTTTATTCTCGGTTCCTGTTTACCGGGGTATGGTAAATCTGCAGGGTTGCACACCCGATCAGCTTGTAGGCTTTCGTGCTTTCAGATTTCTAACAATACGTTAAAGATTTTTGAGTTAGAGAAAGAGCGTCGAACATACTGTCGTACCAATTTACTCTTTACGATCATTCGCCCGCAGCATTCCGGTGCAATCGATTATTCGCATAGTCATTCCCCTGTTCCGTGGCTATTCTTCGTACCTT
AATGGGATTGCTGAACAAGAAGGCGGCTTAGACTGTCTATGGCTTCCGATCGGACTAACGGCGAATAATAGTAAGATTACGGATCCCTGACAGCTTCAGTCCGCAAACGACACCACAGGCTCCTGTAGTAAAACAGACAGCCACTATAGCGCGATTGTTGGCCCCCCCTTAAGTTGCTCGGGGTGGTCCAACAGTCCCCAGAAGACATACGACGGGATGTATATAATGAAATTCGCCTTCTTTAAGAAGATGCTCTGGCAGTTTCATATAGGGGCCCGCTGTTGAAAATCGGATGAGTGAGGATACATGCGTTTGCGTTCGTGTC
GATACTCCTATCGCGCAGTGACCTCCCTGCGTTCATATTTAGCCCTACTTTGACGAGACAGATAGCTGGGAAAGCCTATTCGACATATATACTGCGATGACTCCGGAACGTAAAAGAGTAAATCGACATATTTAGTGGCTTGGATTTGAGTAGTATCGCAACCTACGCCGATGCGGAAAATTAAACATACCGGGGTGTCCCATATGAGGGGGGCGAAATCTCCGAGGATTGAGTACTCGTGCCCCCGACTTTTTTTCGACTCGCGGCAATGAAAACCGAAGGAGGCACGAAGTGGTACATGTGTACCCCTCTTTGGTTACTCATG
CGCAGGCTCATTCGTTCACGAACACACGGAACTACCCAGCGCGTTGATGCTCCAAAACGAGGCCACGTTCACAGAACCGAAACACCGATAAAAGCGCGCCAACAACCCGACGACGCACAGGGTGAAATGGCACTTACGGCTCTTTCATGATCTTCGACCGAAGGAATGGAGGGGGTCACCTGGCCCGGCCCGGTGAGTGCTTGTATAGGCGTTTGTACTGAGGTACCAGGACCGGGCGCTGCACAAGCTGCCATTCTAGCGTATTCTCATATCCAAATGGCTCGCAAGTTTAGGAGGGTGGGGCTCCCGCCAGGCCGTCATATCC
ACGTTTCGCAGCTGAGGTAAGGAAACCGGGGTGGAATCACCCTCGAAGCTGGTCGCGCCGGCATCTATTGTTGAGCAGGTCATCACAATTCCTCTATTTCTATGATACAACTTCGACGATCCACGGGATATGTAACGCCGGAACACAGGAGTAAATGTGATTGACAGGGGCTCATCCGTCTGCCCAAACGGCATCTACGCAATGACTGCATAGGTTTTGTGTAAAAGAGTTTGTCATCTACCCAACCAGGACAAGTCAGCCCGCGCAAACGGCCCACGCGCACATATCAAGCCCGTCAGGCGCCCGCAGAAACAGATCCTAAGTT
GTGGCTGTGCGTAACCGTCTAAATGTAAAAGCGCACATGAGGTAAGTTTACACAGGTGACCCAAGTGATCCTGATCGAGATGGGTAACCGCATTTCTGTGAGTCGGGACACTGGGTGTTACCAGTTGCCAGAAATTCGGCGGGGAGTGAGTTCGGTCGGTATTTATGACTAGGTCATTGGGCTGCAGCGCTCCGCAACAGTCCATGGTTTATAGTTGGAACAGACCGGGGTGATTCATTAAAAGAACATTCATCTGCTTAGAAAAATAGATTTACGTTCCGTAGAACCGTAAGAAATTACTGGCTAACCCAACATAAAAGCTGAG
TCGAATCCGCCACATGCAAGGCTCAATGTTGACAACTCTTGTGGAGAAGACATTGCAAGACAGCTTGAAGGAGGTCCGCTAGAGCTAGTCTACGCTCCGTGTCAAAGCCTGGAGAACATACGATAATGAGTTAGACCGGGGCCAATTAGTTTACCGGGGATCGCTGAACAACCGGTCCGTGACCATACACTTAGTTGGGTAGCAATACATCTGGCCCGGTCAATTTCATCTAAGGCACCCGATATGAGGACGTGTGCAATACACATATTTTCGGTGCTGTCATGTCCTGTGAGGTTTGCATGGCTGACCGTACTAGTATTAACAG
GGCCTTAATGAATCGCTCTGTCATGCATGCATTGGGATGGGGACCCCTCCGTTAGCTGTGATGGGTCGAGACGTACGATGTACCGCCCCTTTTACCGGGGTGAGCGATTCTCGTGCGAAATGTTCTCCCACTTTGTCCGGCCGTGCGCGCAGCATACTGGGCAGCCTGCGTTCCCCGCCCCCCCACATGACCACGACTGGGTTCGCCATCGTCAGCTTAAAATCCGTATGGTTAGGGAAGATAGCGTCCAAATGGGAAGCATGCACGTAATTCAGACTGAGTCCCTCTGTATTCTGTCTTGGACGTAATGAAACTCTATAAAACT
CCCTAGCAAAAGCCCTCTTCAATCACTTGCAATTGTTCTTTACCCCCTTAACTCAGCTTGACCATCATCAATCCAAACCGAAGCTTCGGCCACATCCAGTATGCCGAACAAAGGCAGTAGATTATGCGATCATTCTGTTCTATAACTTTCTTTCTACCCTCACCGATCCACATATTAGCTGTGATTTCGAGTCATTCTGATTGACTATTCATGACTTCCGGCTAAAGACAGGTACATGAAGTGAGCCGGGGTGATACAGGAGTGGGATGCTTGGGCAGCGTTCAATTGGAGAAATCGGAGAGTTGCTACCATTCCTGCTGTCTGG
GCGTGACGGCTCTATAAGAGAACTACGACCAATAGTACCGGTGGTCCTCAGCCTTAAATATAGTGTAAAGTCGTCCGGGGTGATTCAAATGGGTGTCTTTAAACTTATTTAGAAGTACATTGTGCCTAGGTTTCCGGGACTTGCCATAATTGAGAGTCCCTCATTCTCGGTGAGGAGCGCCGAAGTCCCGTTAATCTGGCGTGTCCCGTGATGCATCATCTAAGTTATCAGTCAGTCGCACGCACTCCTACATGACTGAACCAGTGCGCGCTGAGATGGTACGCGTGCTCACTGTCCAAGGAGACGGACACGTATCAACTGGCGC
GCCTATGGAATTGCAATGGAGTTATGTCCAGTACAGAGGTGAAAGTTTACCGGACAGAGATTACCAACCCCGGGATTAGGGGAGATCGAGCTGCGGGCTCGTGGGCCAAGTATTCAACGAACAAAGCTTAAGTAAAGCAGCGAAACGCCTACCGGTACAACAGGCGGTTCAGGTGACTACCAATAAAGTAAATGTTCGGACGCAGACGTCTAAGCAAGTGACGGCCTAGGAGTTTACGCCCTACAACCCACCAGCCACCAACGGCAAATAAGTCCCTACTGACCGCGGCATTTTGCGCACCGAACTAGCCGTCAACCACATCACG
CGGTCCATGTCTCTAGCGCAAATGGATAGGTTCTGTATATACGGCACCTGGCCCAGCACGTCTTTACACAATAAACAATAACCCGAGTGGTGTTAGGTGAGACTTACTAAGGGACCCGCGCAACAACGGGTCCAAGGTGACGGATTTTAATCGTTGCGTGTCGATATCTCGCAGCATCTAAGACTGAGAATGGCGGGATTCACTCCTCGGACTAGGACATCTTCCCAAAGTTTACCAATGTGAGAGACAGAGGTGCACCACTAGGCACGGATGTATGCGCGAGCAATTGAACAATACGGTCCTGTTATACAGTTCACGTTAACAC
GTCGGTTCAGAGCAACTTTACATAGAGGAACGGAAAGAGCAACATTCTTCCCAAGTTTACCGTCATGGTTTGCGAGTACAGCGGCCGGCACTACTGGCGGAGTGAGCCACATCGTTGGCTGGGACCGAGAAACTGCGAGTCTTTAAACGGACCCGCGCCCCAGACACTAGTGTTTCCTATGCGCGCGCATAAAAAGCCAGTCCCGGTAACTGGAGTTCAGGACCAAGGAGTTTGGACAAGCTTGCTAATCGAAATACCATTTGTGTTGCGATCTTGGAGCGTGCGTAGCGCTTACGGTCGAAACGTACCCCGCAGTATTATACCC
        """.trimIndent()

        val k = 15 // kmer length

        val expectedResultList = """
ACGTCCACCGGCGTC
AAGCGCACCGGGGTG
ACCCTTACCGGGGTG
AAGTTCCTCGGGGTG
AAGTTTTATGGGGTG
AAGTTTACCGGGTGC
AAGTTTCGAGGGGTG
CTGTTTACCGGGGTA
AAGTTGCTCGGGGTG
AAACATACCGGGGTG
AAGTTTAGGAGGGTG
AAGGAAACCGGGGTG
AAGTTTACACAGGTG
TAGTTTACCGGGGAT
CCTTTTACCGGGGTG
AAGTGAGCCGGGGTG
AAGTCGTCCGGGGTG
AAGTTTACCGGACAG
AAGTTTACCAATGTG
AAGTTTACCGTCATG
        """.trimIndent().split("\n")

        val reader = dnaStrings.reader()
        val lines = reader.readLines()

        var winCount = 0

        val scoreList: MutableList<Int> = mutableListOf()
        val iterations = 100

        var minScore = Int.MAX_VALUE
        var bestResult = emptyList<String>()
        for (i in 0..iterations) {
            val result = gs.doGibbsSampler(lines, k, 2000)

            var winners = 0
            for (j in 0 until expectedResultList.size) {
                if (result[j] == expectedResultList[j]) {
                    winners++
                }
            }
            if (winners == expectedResultList.size) {
                //println(winners)
                winCount++
                scoreList.add(gs.bestScore)
            } else if (gs.bestScore == 7) {
                println("${gs.bestScore}")
            }

            if (gs.bestScore < minScore) {
                minScore = gs.bestScore
                bestResult = result

            }
            //println(winners)

        }
        println("best score $minScore best result $bestResult")
        println("$winCount winners in $iterations tries")
        if (scoreList.size > 1) {
            var diffCount = 0
            for (score in 1 until scoreList.size) {
                if (scoreList[score - 1] != scoreList[score]) {
                    diffCount++
                }
            }

            println("$diffCount diffs first value ${scoreList[0]}")
        }



    }
}