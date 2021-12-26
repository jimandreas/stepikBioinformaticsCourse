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
        var expectedResult = "TA"

        pmst.createSuffixTree(inputString1, inputString2)
        val result = pmst.shortestNonsharedString()
//        println(nonSharedResult)
        assertEquals(expectedResult, result)

//        val theTree = pmst.printTreeDebug(pmst.root)
//        println(theTree.joinToString(" "))


    }

    @Test
    @DisplayName("Shortest Non-Shared Substring Experiment Test")
    fun shortestNonSharedSubstringExperimentTest() {

        var inputString1 = "CAAC$"
        var inputString2 = "CAC#"
        var expectedResult = "AA"
        pmst.createSuffixTree(inputString1, inputString2)
        var result = pmst.shortestNonsharedString()
        //println(result)
        assertEquals(expectedResult, result)

        inputString1 = "GAGT$"
        inputString2 = "GAGC#"
        expectedResult = "T"
        pmst = PatternMatchingSuffixTrees()
        pmst.createSuffixTree(inputString1, inputString2)
        result = pmst.shortestNonsharedString()
        //println(result)
        assertEquals(expectedResult, result)

        inputString1 = "CGAGCATA$"
        inputString2 = "ATACGAGC#"
//        expectedResult = "AT or CA"
        expectedResult = "CA"
        pmst = PatternMatchingSuffixTrees()
        pmst.createSuffixTree(inputString1, inputString2)
        result = pmst.shortestNonsharedString()
//        println(result)
        assertEquals(expectedResult, result)

//        val theTree = pmst.printTreeDebug(pmst.root)
//        println(theTree.joinToString(" "))

    }

    @Test
    @DisplayName("Shortest Non-Shared Substring Rosalind Quiz Test")
    fun shortestNonSharedSubstringRosalindQuizTest() {

        val inputString1 = "TTCGCGCCCCGTGTAAGTGGTAGTTGCTGTTGTAAGAAGCCGCTATATTTAGCTTACTACCATGCAGATCGCGACTACTACTATAACTGAAGCGGAGCAAGTCTAACAACCTACTCGGGCGGCGAATCCATCATGACTTAATTAGGTCAAGTTCGGAACCTGTCGGTTGAGTATAGGATACGCCGTGCCGACATTTCGAGAAGTTATTCATAACTAGTGGGACGAAGTGGGAATACAGGATTCAATTAGTATAATTCAAGTCGGGCTTTTGCGGCAAACAGGGCGGATTTTTGACCTTCGTATTCTAAACAGTTACTGACAGTAACATGGCGATAAGTCTTCACCGCGCTCAACCCTCTTGACATCCAGGACGCTTAGTAGACATCTTTCGATCCGACATCAAAGCTCTTGCATCATGCATCCGGACTTTATCTTGCTTGTCCGGCCCCCGACATCGCGTACCTATTCCACTCAAAAGGGGCCACAACGCCCCCTTGGCTGGCTTGGGCGCGAAAGCGTGTACTTCCGGGGGAAGCGCTCAAGTAGAAGACACAGCTTCTAAGCCATGATGTCGCATTACGTGGAAGAGTCTAATCCACCCCGTCTAGACGTGTGAACGACCGAACTGAGGCTTACATGAGGTTCTGCCGTGGATAGGG$"
        val inputString2 = "CCGCTAAAAGCCAAACCTAGAATCCCGGCTGAAACTTAATTTAGAGGCTTGCGGGCTCAGCGGACCCATGGGAGGCTAGTTGTTAACGCGACTTGAGAACCCGCCCACACCCCATGTAAGATGCCTCGTGGGTGATACGGTGACTCAGGGAGATCGAGATACGAGACATTGTATTAGAGCATAGAGGGTAACCAGTCGTGGGTCCCGGCAATTATTGGAGATAAGTGCGAATTTGCCTAGACTGCATAATAAGAACATTTCCTGAGTGGCGATTGGATGACTAATTTGCGTTCTTTAAAGTGATAAAGGCCTGGGAGTAGACGCTTCCTTAAATTAAGCTTGGGCTACTCTTGGATTCGTGAAAGCTCTATCGTGTGTGCCTTGGGTACTCAGACGCTACAAAATAAACAAAGAATTAATCAATGAACTAACCAACGAAGTAAGCAAGGATATACATAGATTTATTCATTGATCTATCCATCGATGTATGCATGGACACAGACTTACTCACTGACCTACCCACCGACGTACGCACGGAGAGTTAGTCAGTGAGCTAGCCAGCGAGGTAGGCAGGGTTTTCTTTGTTCCTTCGTTGCTTGGTCTCTGTCCCTCCGTCGCTCGGTGTGCCTGCGTGGCTGGGCCCCGCCGGCGCGGGGAAA#"
        val expectedResult = "AGCGC" // accepted answer
        pmst.createSuffixTree(inputString1, inputString2)
        val result = pmst.shortestNonsharedString()
        //println(result)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("Shortest Non-Shared Substring Stepik Quiz Test")
    fun shortestNonSharedSubstringStepikQuizTest() {

        val inputString1 = "AAAATAAACAAAGAATTAATCAATGAACTAACCAACGAAGTAAGCAAGGATATACATAGATTTATTCATTGATCTATCCATCGATGTATGCATGGACACAGACTTACTCACTGACCTACCCACCGACGTACGCACGGAGAGTTAGTCAGTGAGCTAGCCAGCGAGGTAGGCAGGGTTTTCTTTGTTCCTTCGTTGCTTGGTCTCTGTCCCTCCGTCGCTCGGTGTGCCTGCGTGGCTGGGCCCCGCCGGCGCGGGGAAACACCTAATCTGAACCGATATTGGAACAAATGTCGCGTTGACACACCAAGTTCTCCGGGATGTTCAAGGACCCGGAAAGAATCCGACCTACGCACTGTGTCTTGTACATAATTTGAGGGGGTGTCTCCGACTACAATAAGTATCGGAGGCCGGAATGTTCCCGGACGTAATGGCTACGCTTGGATGGATGAAGAGACAATACCGGCACGCATCGTCTTGGTCCGGAAAATAGCTCATCCTCAAAGACCATGGGATGGAGCTCCTCCTAGGTCGTTTTGCGATTGAGGACACCTCTCGAGGGACAGCTGCTGCGGACCCTATATGCTTTCCTCCGCGCAAATGAAGCCCATGATGGACTTCCTCATTATTTGTATAGACACCCCCTAGACTCCCACGAAAGA$"
        val inputString2 = "AAAATAAACAAAGAATTAATCAATGAACTAACCAACGAAGTAAGCAAGGATATACATAGATTTATTCATTGATCTATCCATCGATGTATGCATGGACACAGACTTACTCACTGACCTACCCACCGACGTACGCACGGAGAGTTAGTCAGTGAGCTAGCCAGCGAGGTAGGCAGGGTTTTCTTTGTTCCTTCGTTGCTTGGTCTCTGTCCCTCCGTCGCTCGGTGTGCCTGCGTGGCTGGGCCCCGCCGGCGCGGGGAAA#"
        val expectedResult = "TCCCA" // accepted answer
        pmst.createSuffixTree(inputString1, inputString2)
        val result = pmst.shortestNonsharedString()
        //println(result)
        assertEquals(expectedResult, result)
    }


}