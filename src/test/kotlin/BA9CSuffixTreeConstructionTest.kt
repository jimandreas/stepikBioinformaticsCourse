@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.PatternMatchingSuffixTrees
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

/**
 * Construct the Suffix Tree of a String
 * Rosalind: https://rosalind.info/problems/ba9c/
 *
 * SEE ALSO for a tree example:
 * also:
 * https://www.bioinformaticsalgorithms.org/faq-chapter-9
 * (see :  What are the edge labels in the suffix tree for "panamabananas$"?)
 *
 * Using the Trie 2/10
 * https://www.youtube.com/watch?v=9U0ynguwoNA
 *
 * From a Trie to a Suffix Tree (3/10)
 * https://www.youtube.com/watch?v=LB-ANFydv30
 *
 * String Compression and the Burrows-Wheeler Transform (4/10)
 * https://www.youtube.com/watch?v=G7YBi04HOEY
 *
 * Inverting Burrows-Wheeler (5/10)
 * https://www.youtube.com/watch?v=DqdjbK68l3s
 */

internal class BA9CSuffixTreeConstructionTest {

    lateinit var pmst: PatternMatchingSuffixTrees

    @BeforeEach
    fun setUp() {
        pmst = PatternMatchingSuffixTrees()
    }

    /**
     * Code Challenge: Solve the Suffix Tree Construction Problem.I
     *
     * Input: A string Text.
     *
     * Output: A space-separated list of the edge labels of SuffixTree(Text). You may return these strings in any order.
     */

    @Test
    @DisplayName("Pattern Matching Suffix Tree Problem")
    fun patternMatchingSuffixTreeSampleTest() {

        val inputString = "ATAAATG$"

        pmst.createSuffixTree(inputString)

        val result = pmst.printTree(pmst.root).sorted()
        //println(result.joinToString(" "))

        // answer in web - it is OK
//        val expectedResult = "AAATG$ G$ T ATG$ TG$ A A AAATG$ G$ T G$ $".split(" ").sorted()
        // this is the answer in the debug datasets PDF - it is incorrect
//        val expectedResult = "$ $ A A AAATG$ AAATG$ ATG G$ G$ G$ T T TG$".split(" ").sorted()
        val expectedResult = "$ A A AAATG$ AAATG$ ATG$ G$ G$ G$ T T TG$".split(" ").sorted()

        assertContentEquals(expectedResult, result)


    }

    @Test
    @DisplayName("Pattern Matching Suffix Tree Simple Problem")
    fun patternMatchingSuffixTreeSimpleTest() {

        val inputString = "AA$"

        pmst.createSuffixTree(inputString)

        val result = pmst.printTree(pmst.root).sorted()
        //println(result.joinToString(" "))

        val expectedResult = "$ $ A A$".split(" ").sorted()

        assertContentEquals(expectedResult, result)


    }



    @Test
    @DisplayName("Pattern Matching Suffix Tree case3 Problem")
    fun patternMatchingSuffixTreeCase3Test() {

        val inputString = "ATCG$"

        pmst.createSuffixTree(inputString)

        val result = pmst.printTree(pmst.root).sorted()
        //println(result.joinToString(" "))

        val expectedResult = "$ ATCG$ CG$ G$ TCG$".split(" ").sorted()

        assertContentEquals(expectedResult, result)


    }

    @Test
    @DisplayName("Pattern Matching Suffix Tree Rosalind Quiz Problem")
    fun patternMatchingSuffixTreeRosalindQuizTest() {

        val inputString =
            "ATTCAGCGAGATAGTAACATAGTGAGTAATCAGGGTGTTGTTGTGAGTATCTAACAATTGACGCCAGGAGCCCTGACTAAAGACTTGAAGATCAGGAAGAGGAAACAGAACTGAGGATACAGATTCCTGATAGTCTGTCTTAGTCGGTCCCTGCGGTCGTCGATCAAACGTCCTACCGCCGTGCGCACGACAGTACGCCGGGGTGGAAATTCCTACATAGGTTAAGCCATTACCTCCGCCGTTCAATGACCTTCGCTGCGCACGTACCAAAATGACGTGTAAGGGCCGACGTTAACCCCACGACGACAACCTTGAGGCATGGGAACGCAACGAACACCACAAGCAAGATGGAAAAGTTAGTCTGTTTGCACCGATAATCGGCCGGCTGTGTACTCATGCTCGCTACGAGGTCAGAACTTGGACAGCAGTCGCAGTAATAGGGTCTCAGTTGCGGTCGTCCTGTTTATAGCTAAAATCCCTGACATTTCAAACTCTGCACCTATTGTAGCCGTGCTGAAATTGCCACTCGGCGTCTAGAGCCGAGCGGGCCCTTCCCTGCGACCAGTGAGCAGTGGTCCAAGTGGACTCAAACCGGCTCACGGACTATGCACATCGATCCCCATTTCTCTGTAGGTTAACTAGTGTATCTGTCTAGGATTCGTGGCAAGTGTCCTAGACGATGAACAATATGCTTATTACGAATTTTCTGCAGAGGAGGAGACTGTCGTGGATCGCTGGGCTAAATTCCTCGAGAAAGTTGAATCAGTGGAATAACATAGTTCTCTACTTTTAACCGTTGTCGAACGGCTAACGCTGTATACACGCGGGGGCGCTCACGTGAAGCTATCAGGTGGGTTTAGCCAGTTCAGTTCAACCCATATAACTCTTACCACGGATGCGCACTTACGATGTCGGGTGCAAAGATACGCCGCCCCTCGGTGGCTAGCAGGATATGGCTCTGCTCATTAC$"

        pmst.createSuffixTree(inputString)

        val result = pmst.printTree(pmst.root).sorted()
        //println(result.joinToString("\n"))
        val loader = Foo()
        val expectedResult = loader.getResourceAsStrings("SuffixTreeRosalindAcceptedAnswer.txt").sorted()
        assertContentEquals(expectedResult, result)


    }

    class Foo {
        fun getResourceAsStrings(path: String): List<String> {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText().lines()
            return retStr
        }
    }


}