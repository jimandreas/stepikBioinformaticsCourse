@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.PatternMatchingSuffixTrees
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Find the Longest Repeat in a String
 * Stepik: https://stepik.org/lesson/240378/step/5?unit=212724
 * Rosalind: https://rosalind.info/problems/ba9d/
 *
 * SEE ALSO for a tree example:
 * https://stepik.org/lesson/240388/step/2?unit=212734
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

internal class S09C05P05LongestRepeatTest {

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
    @DisplayName("Longest Repeat Problem Sample Test")
    fun longestRepeatProblemSampleTest() {

        val inputString = "ATATCGTTTTATCGTT$"

        pmst.createSuffixTree(inputString)

//        val theTree = pmst.printTreeDebug(pmst.root)
//        println(theTree.joinToString(" "))

        val longest = pmst.longestRepeat()
        //println("LONGEST = $longest")

        val expectedResult = "TATCGTT"

        assertEquals(expectedResult, longest)

    }

    @Test
    @DisplayName("Pattern Matching Suffix Tree Experiment Problem")
    fun patternMatchingSuffixTreeExperimentTest() {

        val inputString = "ABABC$"

        pmst.createSuffixTree(inputString)

//        val theTree = pmst.printTreeDebug(pmst.root)
//        println(theTree.joinToString(" "))

        val longest = pmst.longestRepeat()
        //println("LONGEST = $longest")

        val expectedResult = "AB"

        assertEquals(expectedResult, longest)


    }

    @Test
    @DisplayName("Pattern Matching Suffix Tree Experiment2 Problem")
    fun patternMatchingSuffixTreeExperiment2Test() {

//        val inputString = "AAGCTGAA$"
        val inputString = "AAGAA$"

        pmst.createSuffixTree(inputString)

//        val theTree = pmst.printTreeDebug(pmst.root)
//        println(theTree.joinToString(" "))

        val longest = pmst.longestRepeat()
        //println("LONGEST = $longest")

        val expectedResult = "AA"

        assertEquals(expectedResult, longest)
    }


    @Test
    @DisplayName("Longest Repeat Problem Sample Rosalind Quiz Test")
    fun longestRepeatProblemRosalindQuizTest() {

        val inputString = "ACCTAAACCTGACCGCAGAGTTTCGCATTTTGGCCATGCTTATCTTACATATCGGTTTGGCGTTGTTGTTGTCGGATCACAGCCGACAGGGAGCGGTCTACGAAGATAAACCGGGCACAACTACTGCCCCCCAACCCCTTTCCGTGCGAGCCCTTTTGCCCAGTGATTGTTTTAGACTACTCACCATGGTATCAGGTGGCGGCATAAGATCGCTGGCCATACCAAACAAAGTGCTAACCTCTGTCCCGCAGTGAGGGAGAAGGCGGTCCGCTAGCGTGTGTGGGCATTGTGTGCCATAGCCTATAGAAAGCCAATCGTCGAGTAGCTGCGTTAGTGTACCGCTCTAGCAGCAGAGGCGTACGAGGCTGCTTCCTTTCAGACGTGTTCCACTCCAAGTGCGGCTCAGAACACTCATACTTCAAAACTTCTGCCAACCTGACCCACGGTTTTCACTCGGCATGTGGCCAGCCGATCTATCTGTTTGAACGTTTGTGCTTACAACGCCTCGCTGAAGCCTCTAACAGCTCTACATCAGGGTAAGCACCATTTTTCTTCCCTGAAGCCTGTAGGGTTAAATCTGACAATAGATTTGACTCTAGCGGGGAGGGATCGGGACTCGTCATTGTGTGTATGGTAGGGGAATAGGTTTGAACGAGTAAACGGTGCATACGTAAATATACGAGCCTCGGTACTTTGCAGCTCTGTTACCCGATGAGCCGACACACTTCAAGTGTCCGCTCTATACTTGCTGACCGCAGTTTATTCTTTCTCTATTGCGTCCGTTCTTAGCGTCCACCACAGTGACAAGACGTGACGGGTATCATATCCGAGTGCTGTGTATGGTAGGGGAATAGGTTTGAACGAGTAAACGGTGCATACGTAAATATACGAGGGATCCTTTTGACTACAGGGGCCGAATACTGAGCAACCCGCATTGCATCTATCGAATTTGTGTATGGTAGGGGAATAGGTTTGAACGAGTAAACGGTGCATACGTAAATATACGAGTCTTCCCAAGAGGACTCTACTCAGAGTATACTGACGCCTTGGCGTGTCCGAAGTGTCGTAATCGAATTTGTTTGGGCAGCCAGACCGTAATGTCGTGTGTTTCCCAATACCACATAGGTCGAGAAGGCTAGCGTTCTAGCGCCGGTCTTCGGTGGTATCTGCAGTC$"

        pmst.createSuffixTree(inputString)

//        val theTree = pmst.printTreeDebug(pmst.root)
//        println(theTree.joinToString(" "))

        val longest = pmst.longestRepeat()
        //println("$longest")

        val expectedResult = "TGTGTATGGTAGGGGAATAGGTTTGAACGAGTAAACGGTGCATACGTAAATATACGAG"

        assertEquals(expectedResult, longest)


    }

    @Test
    @DisplayName("Longest Repeat Problem Sample Stepik Quiz Test")
    fun longestRepeatProblemStepikQuizTest() {

        val inputString = "GTTACGTCACGGCTCATTTGCGGACACCGAAGGATAGCTTAAGATAGTATCGTATTGCATGTCATCAAGTGCGTTACAAACCCCACAGGCGCCATATGAGATAGATTGCGGCTAGCGCCCGCTGCTATAACGAGAGGAGAGAGAACGATACAGGACCCCATCTGGCTGCGAACATGACCTGACTCCGGAATACAGGCGGATATGACTCATGATAATCTGGCGTCGCTTCGCCTTGCAGATGAAACAGCTCCGCACAGCAGGATAAAGTCTACAAGCTGTGGGTATGTTAAGTCTCTCTCTAGAAGGGATTTTCTGCGATGAGGTCACACGTGATGGCGGCAAATAGCACAACAAGATCGAATGTCTAATGCATAGCCCCTTACGGCGACTATCTTAACACCCGCCCATTCGCTATTGTCTTCAGCTTTTCGCGGCTCCGCAACCTATCTTAGCCGACAAAGAGCGCACCCCCGCATCTCACAGTTTGATCAAGGTAGCATGCAGAACGGCTTTGATATTTCTCGATGCAGAACGGCTTTGATATTTCTCGGTTTGTTTAGCGATCTACAGTGCGCGCGGGCCCACAACGTTTGTTTAGCGATCTACAGTGCGCGCGGGCCCACAACGGCGCAAGGCGGTCCAACTTGCGTTCCAGGAACATTTGACTCTCCCAAACCACCGGCTATCTACGATAATACTTGGTTACCTCGGCAGGATCCGAATTACAGGCATGCCAGGAGTTCGTCAAAGATGCAGAACGGCTTTGATATTTCTCGGTTTGTTTAGCGATCTACAGTGCGCGCGGGCCCACAACGCTATAACTGGTGTAAAGACACTTGCTATCTGCATTACACCGAACAACCCCATTCTCCCGCCGCTCTACCTACATTAAACTTACTTCCGCGCACGCTAGAGTATAGTATACTCTGCCGAGGAGGATGCGCCTGCATAGAATGCAAGAGACATATTAGAATCGCCCTGCAAGTGCTAGCGGAGCGAATAAATAGAGCGGAGACCTCTTGCGGCCGAGGCGTCGCTTGCTTCAGCCCCACAAAATTTTACGGAGCTACGGCCCCTGATCACTAAGTCAAGATGTAGCTCAGCCTCGTGTCTAAAGTAACGGTGCCGATGCCAAGAGTCTCTCCAATCAAGAGTGAGTACCTTGCTTGCCGGTCCGCCACTGCATGGTGAG$"

        pmst.createSuffixTree(inputString)

//        val theTree = pmst.printTreeDebug(pmst.root)
//        println(theTree.joinToString(" "))

        val longest = pmst.longestRepeat()
        //println("$longest")

        val expectedResult = "GATGCAGAACGGCTTTGATATTTCTCGGTTTGTTTAGCGATCTACAGTGCGCGCGGGCCCACAACG"

        assertEquals(expectedResult, longest)


    }

    class Foo {
        fun getResourceAsStrings(path: String): List<String> {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText().lines()
            return retStr
        }
    }


}