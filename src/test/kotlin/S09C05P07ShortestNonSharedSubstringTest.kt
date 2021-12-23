@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.PatternMatchingSuffixTrees
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


internal class S09C05P07ShortestNonSharedSubstringTest {

    lateinit var pmst: PatternMatchingSuffixTrees

    @BeforeEach
    fun setUp() {
        pmst = PatternMatchingSuffixTrees()
    }

    /**
     * Shortest Non-Shared Substring Problem: Find the shortest
     * substring of one string that does not appear in another string.
     *
     * Input: Strings Text1 and Text2.
     *
     * Output: The shortest substring of Text1 that does not appear in Text2.
     *
     * Code Challenge: Solve the Shortest Non-Shared Substring Problem.
     * (Multiple solutions may exist, in which case you may return any one.)
     */

    @Test
    @DisplayName("Shortest Non-Shared Substring Sample Test")
    fun shortestNonSharedSubstringSampleTest() {

        val inputString1 = "CCAAGCTGCTAGAGG$"
        val inputString2 = "CATGCTGGGCTGGCT#"

        pmst.createSuffixTree(inputString1, inputString2)
        val nonSharedResult = pmst.shortestNonsharedString()
        println(nonSharedResult)

//        val theTree = pmst.printTreeDebug(pmst.root)
//        println(theTree.joinToString(" "))


    }

    @Test
    @DisplayName("Shortest Non-Shared Substring Experiment Test")
    fun shortestNonSharedSubstringExperimentTest() {

        var inputString1 = "CAAC$"
        var inputString2 = "CAC#"
        var expectedResult = "AA"
        pmst.createSuffixTree(inputString1+inputString2)

//        val theTree = pmst.printTreeDebug(pmst.root)
//        println(theTree.joinToString(" "))

        var result = pmst.shortestNonsharedString()
        println(result)
//        assertEquals(expectedResult, result)

//        inputString1 = "ABC$"
//        inputString2 = "ABD#"
//        expectedResult = "AB"
//        pmst.createSuffixTree(inputString1, inputString2)
//        result = pmst.longestSharedString()
//        //println(result)
//        assertEquals(expectedResult, result)
//
//        inputString1 = "ABCAAAA$"
//        inputString2 = "BBABCBB#"
//        expectedResult = "ABC"
//        pmst.createSuffixTree(inputString1, inputString2)
//        result = pmst.longestSharedString()
//        //println(result)
//        assertEquals(expectedResult, result)

    }


    @Test
    @DisplayName("Shortest Non-Shared Substring Experiment2 Test")
    fun shortestNonSharedSubstringExperiment2Test() {

        val inputString1 = "GCAGGCTTTTTAATCTGTAGGTATGTAATCGGGGGGTGCAGTTAGGCAGGTAACCCACTAGACGAAGTGCCGGGTGAACGGGATCTGTGTGGTCGGCAGGTGTAGGGGGGCATTAGAATTCTTGGGTTCATTGGTAGAGGACGGTGGATGGGCAACCGGACTCATAATACTCGTAAATTATATAAAGCACTTCGAGGTCACCGAAAAAGTACGATTTTATACGACCGTTCAGGTTCCCGTTAGTTTTCTACAAAGTGCGGGTAGCGTCTTTTTGCGGCCCATCCTTATGCAAGCAGATCTCTCGGGGATTATCCCCATCTCAGCTACGCTCACGATCCAAACGGGAATAAAGCCAATCCGGGTATTAAGCATAACGATGACAGCACAAAGCCAAAGGGCGCGTAGGATTAAAAATCGTGATCGTTATTGCTCCAGAAGCAGTTTCCCGGTGTCCTGATTTAGGGTTACAAGAACGCCAATTTCTCGTACAAACACTGTACTGCAGCAGTAGGATTAAACGGCTCAAGGCTGGATAAATCCATGGTAACGCCGTTCAATGGCTTTGTGTACTGTATGCACATGTTAAAGCGCGTCATGTCATTATCCGATCGCGCAAGTTTCCAGTTCGTAGACTCGGTGGTTAGGTCTATTGACCCTAG$"
        val inputString2 = "TCGACAATGAACTAAGACTCCTACGGAATGAAGCAGCAATCAATGGTTGAATTTCTTTCACTCGAATGTCCAAGTGGATGTTGTGAATCCTATAATAACCAGACGTCGCGGGTGAAGAGATTCCGTCAAGTGAGACACCGTGCGTATACGACCCCTCCCCTTACTCTAAACCTCTCTGCTTTTCACGCTATAATGGTTCCTCCCTGAGCAGGTTGGTCTTGACCCTAGCTACGGTAGTTAACGCCGGTCCCCTATGGCCCATAATACTCCACTTGGCCAAACCGAGATAGGATAGGTCTTCAATTTCTCAGCGGGCTACTCGCGAATTAAATCCCCGGAACGAGATAGGGCGATATGAGAATCTCACACTCGGCCTGGCGTTAACACCTGGGTATGACTTAAAATAAACAAAGAATTAATCAATGAACTAACCAACGAAGTAAGCAAGGATATACATAGATTTATTCATTGATCTATCCATCGATGTATGCATGGACACAGACTTACTCACTGACCTACCCACCGACGTACGCACGGAGAGTTAGTCAGTGAGCTAGCCAGCGAGGTAGGCAGGGTTTTCTTTGTTCCTTCGTTGCTTGGTCTCTGTCCCTCCGTCGCTCGGTGTGCCTGCGTGGCTGGGCCCCGCCGGCGCGGGGAAA#"

//        pmst.createSuffixTree(inputString)

//        val theTree = pmst.printTreeDebug(pmst.root)
//        println(theTree.joinToString(" "))


    }


}