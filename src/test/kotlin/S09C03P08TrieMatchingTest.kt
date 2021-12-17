@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.PatternMatching
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

/**
 * Construct a Trie from a Collection of Patterns
 * Stepik: https://stepik.org/lesson/240376/step/4?unit=212722
 * Rosalind: https://rosalind.info/problems/ba9a/
 *
 * TrieMatching
 * Stepik: https://stepik.org/lesson/240376/step/8?unit=212722
 * Rosalind: https://rosalind.info/problems/ba9b/
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

internal class S09C03P08TrieMatchingTest {

    lateinit var pm: PatternMatching

    @BeforeEach
    fun setUp() {
        pm = PatternMatching()
    }

    /**
     * Code Challenge: Implement TrieMatching to solve the Multiple Pattern Matching Problem.
     *
     * Input: A string Text and a space-separated collection of strings Patterns.
     *
     * Output: All starting positions in Text where a string from Patterns
     * appears as a substring.
     */
    @Test
    @DisplayName("Pattern Matching Trie Matching Problem")
    fun patternMatchingTrieMatchingSampleTest() {

        val stringToMatch = "AATCGGGTTCAATCGGGGT"
        val patternStrings = "ATCG GGGT".split(" ")

        val result = pm.trieMatching(stringToMatch, patternStrings)

        val expectedResult = """
            ATCG: 1 11
            GGGT: 4 15
        """.trimIndent().lines().toMutableList().sorted()

        val parseResult = parseResultStepikFormatting(result)

        //println(parseResult.joinToString("\n"))

        assertContentEquals(expectedResult, parseResult)

    }


    @Test
    @DisplayName("Pattern Matching Trie Simple Problem")
    fun patternMatchingTrieMatchingSimpleTest() {
        val stringToMatch = "ATATATA"
        val patternStrings = "ATA TAT".split(" ")

        val result = pm.trieMatching(stringToMatch, patternStrings)

        val expectedResult = """
            ATA: 0 2 4
            TAT: 1 3
        """.trimIndent().lines().toMutableList().sorted()

        val parseResult = parseResultStepikFormatting(result)

        //println(parseResult.joinToString("\n"))

        assertContentEquals(expectedResult, parseResult)

    }

    @Test
    @DisplayName("Pattern Matching Trie Stepik Quiz Problem")
    fun patternMatchingTrieMatchingStepikQuiTest() {

        val stringToMatch = "CTCCCTGTGATTGAGAACAGCCCCCGAAGGCGAGATACTCGATTTTGACAAGGGTCCGAAGATATTTTTAGAATCCCGCAGCGTCGTTACGCACTTGTAACACTGCAATTAGCGATCAAGCTCCTTCCAGACGCGGACGCAATTAGCAATTAGCTAAGCAATATCCTCTTCACCTACCATACTGTCTAAGAGGCCTGTGCCCTACTCAATCCACAATAAACGTGAGTGGCTCCCGTTGCCTCTCGCTTCATATTTTAGAACTGTTGCAGCAGTATTATAGTAAGGTAATGATCACCGATAGGTACCCTGGCCCGAATCCCAGAGGCGCTTATGCGATTAACGTCGTTAGTGTTTGCCGGAAGTAGTAACAAATCACTCGCATCACCGATCACCGATTATGACAATATTATAGTCCTCTGCTTATAAGTAGATCCTTCCGAAGGGCATATTATCTATTATCTATTGGCTACCGTTGAGGCCCATCTAAATGTGCTCTAAGATTCATCATCACACTATTCAGCTAGGAGGGGACAGTGGGAAGCTGGAGGGTCGAATCATGATACGATCTTCCGGTAAAATTCTACTAAGGGTTATACTAATCCGAACAATCGGAAAGGGGACGTCACTTCGTCTCCATGTATCTTTGTGCACGCAGGTTGCTGTCGGTACCAGAGGTGGGTGTTAGGTTGAAGTTCAAGCGTGGACCAAGGTGATCTACTTGTTACCTGGGAGGACGTGTAGGGGCCCTAGAGTAACATGGGTCACCGACGTTTATGCTCAGTTAGACTTTTCTTATATTAAAGGAATGCTGGCTGAGTACAGGAATTTACCTTAATGACAGGGTCGTTTTGTGCTTCTTCGTGTAGTAACAAGGGTAGGGGGCTTCACGTGCTGAATGGCGCGGGAATGAGGGACTTAGCATTGAGGGATCCGTAGTCATTTGGTGGTGATCAAGTTACGTGCATTAATTCCTCGGGTCCTATGAAGCTTGTAAATCTTACACGCAACTATTGGGGGGGCCACCGCCCAAGCGAGTCTACTCTGCAGAGGTCCATACGAGCAACGTCACAAGCATGTACGAACGATAGGTTCCTTGTCGTCATTGTTGTTGCGGATACGATGAACACGATAGACGGCGGCGTTTGTGGCAGGCCGCCCGCTTGGTGATGCGGCACACCAGTGTCTGTAAACCCCCCCTAATCCAGCTATCTTGAGGGGGACCAGAGTTCCCTATGGGCTATGAAGGCGAATCCCTTTGGTTGGACAGTCCCGTCGGACTACACATTGCATTAGTCGAACTCACCAACCACTCCATGAAGCCTAATTTGCTACTCAGCGTCGTGCTAACATCCTGGGTCTGAGCAACCTCTATTCGGTGCGTGTGACGCGTTGTAGTGGTAACATGGGGGTCTACAAGTGGATCAGTCTCTTCGAGCGCTGGATAAATATGCGCTGCTACGATCCCCGCGGTGCTTTCTAAACCCTTCGGGGCAGCGCTTTCGTACCGACAGGGGTGTGTAACTTCATTGTCTACCCAACGGTGACCTTGAAGTAGAAAGGGTTACGGCTGTGTGCCGACACAGATAGCTGCCTACACCTGAAAACGGCAGGATTTGGTCAAATCTGTTTACAGTCAGTCGGCTCCCGAGTTAGAATCGAGGATCGAACGTTAAACCTATACTCGTAGTTCGATGTCTCCGTAGCATGGTAAAAGTGCTCGAAAGTATGGACGAGCAGAGCCTGACACCAACCGAGGAGGGCTTTAAGAGAATCATGGCTGTATAGCCGTGTATGGCCTTTCACTAGGCCCGTCGCCTTCAAGGCGAAACATAACCCGCAATGGGGCGCTAACGTTATTACCCCGGGGTCACTCCTTCGTATAGACCTTGCAGTGCAGAAAAGTCGTGGGTTTCCCCGGCTTAATCACCGATCACCGATCTGTAAGCCGGCAACTCTACACCCACCTTGACGAATTCCGAGAGTTGACATCTGGTAACGTAAGCAGGGACTACTGCCGGTAAAGCGATATCGCCATAAGGCTCCATCATTCCTCAAGAATCTAACAACCGTGACGAATACGGACATTCGGCGCCTGTGGAGTTAAAACATGGCGAGGCCCAGCCTGGCGCTTGAATAACGTTAAAGCTGGACATTTACAACATGTGGTCTTACGTCTACCTCACTGCCAGATGTACTGTCGCAAGTTGAATACTGATTTGTAACGATTTCAGGCGCATCTAACGGTATTGGACTCTCGATCCCTTATTCGCACCAGCTAACGACAATCGATAAAGATGTTCAATACTCCTGACGTACATGATGGTATACGTTACAAACTAGGGGAAGTATGCCAGGCTGAGCTCTCAAATTGTATACTCTTGTCCGTAGGTACTGTGCGGGGCAGCCGCGCGATTCCAACTAGCCTCGAACTAAGTGACAAAACACGTAGTAAGCTATCGGCTCCAGCCTTAGATTCAATATAGAGTGCCCGATCAACTATTAACAAGTTGAGAGGTTGGCTGAGGCCATCATTAATAGCACTGGTTCACGTCCACTTAACCAGTCCTCAATCAGCGTTGAACCGCTAGGAGGCAGTGCTCTTTTTTAAATGCCTAGTACTCTTAGGCTCGCATCCTCATTCCAGCAGATAGGCTTGGGATAGGTTCACGAAAGGGGCTCCAACTCGTACTAATTGCGGCTCAACTGTCAAAGAGTACGAAGACTAGTATTCGACGTCAGCCTGCCTACCGGAACCCGGCACGTCCTCGTATCGAGGGGACACGTTCACCCATCCGATAGGCCCACAGGGAACAATGGAAACTTCACCCTCGCCAGTTAGCCTGAACTTAAGTCCGACTCCTGGAGTGGGAGCCTATCCAGCCAACATGGCAGCAATGGCGATATCGGCACAACCGCCGAATCAGGCGCCGCAAGTTTAGGCCTTGGGAACAATACCAATACAAGCTCTTCGTAATTGCTGCAACTAGCGTTGCCTATCCGCAGGCTAGAACGGTTGCGAACGGTACCTGCCTACAGCAAACTTAGCGCGTAATACAGGGTTACAATCGCTACTCCAATCGTAGGGCGCGAGAGGCTGTCCGAACCCTTTGAATTCAACTTAATACACATTCTTGATACGCAGCGGGCCTCGCCTATAAACGGGACTTTTAATTAGCGTTTGATGCGCATTACGCTAAAACTCCTGTATACCTCTGCGGCTCGTTGGTGCCTACCTAGTAGACTCGCTCTATGTCTGACGAGAATATAGAGTTGCTTGATTATCCCTAAGACTTCAATTATGTTTCTCTGGTCCTCCCGTCTGTTTGAAACATCTAACCAACAGAGGGCTACTTACGACCGAGTTGTAAATTTAGTTTAGGCAGTGGTCCAATCTGTATTAATACTTAATCTGTTACGGTCGATGTATCCCTCACTTCCGCGGCCCCGGGTGCTATGTAAATAAGTTCTGGACAGGCAGACCCTACGGCTATCTCTGCTTTATGTCACATGATCGAGCCGCGAACTGTGGAGAGATCATCTTGGCCCTCCAGACGATGGCCGCGTTAGTGTGTGTGGTTGTAACAAAATATAGTGCTCCTGAGGACGGAGGCTCTCTTATGGGCGTGGGCAAACTGCGTTTCCTTATAGCGTGACGGCCTACGGCATAGTCTGTCCACCCGGCCAGTACGAGGCGTGTACCTTCACGAATGAAATCACTGCTTCCTACTTCCGCTCATGGGGCCCGCGATTGGTATAATTTATCTGGGCACCAGCTCACTGCGCCTTCCTGTTGGCTTACCTCGCGAGTTTTATAGGGGTTAGGCTGACGATGGGATGCTCGTAAACGGTAATAGGTCTCTGAAAGTGCTAGATTCTCCCTTTCTAATGGCAAATTCAAGCGAAGGAGCAAATATGGACCACAGAGGGCTCGCGCTATTGGTGTGCATTTAAACAACCCACTTCGGCACTAGTATTAATCCAGGAAGATAGCTAAACTGAAATCCTCCTTAGGTATAGTTAGTACCGACACCAGACTGCGCTGGACGGGCCCGCCTAAAAGGCCTATGGGGGTAAAAGAAGCGCGGGTATCCTTCCTGTATATGCCTCTTCGCAGGGACCATCAACGGGAGCATAATCATTGCCAGCAAAATACCGCCTTAAACCGTAGTTCGTAGTTCGTTGACGGGCGGTCACACGCTAAATAGCAAATAGCAAAGCAACGAATAAAACGACGGCTGTTTATGTGAGCGTCATAACCAGGGGCAACACATACTATCAAGAACCCGAGCTGATACGGCGTGAGTCGGGCGAAGTTTACTTTCACTCAACACAGACCTGTTACCGGGTTATATCATCCGGCTTACTCGAACGGACAGTCTGTCACTCACGGCCAGATAGATGTTATTGGCGAAGGCCCTAGAATAGAAAGAGGGCTGTATGGCCTCCCCGACGCCGCTTGACGGCAGAACAAATTGCAAAGCGCAAACGGGATTTAGAATAAATGACAGGGACCGGTAGAAGGTCTATTAGGGCTGGCTTCGCTCTACCCGAGGCGTACCACCCACCGAGCATCACGTCACGGACCAACATTATGACGAGACATAAAGTCGTCAACAGAGCTTAGTGAGGCGCTACGCGGGCACTTTTCTATCGGTTTGTCGTACACGCACAATGTGGGGCACGGCAGGTGCAATGACACTGATACGTCTATTGTGCCCACAAGACCCTCCATGATGCCGTAAAATTTGATCATTGTTCCCGTTATTAGGTAACACGTTGTCAGGTCCCAATTATCCGCGGTAAGTCGAATGTGGAGCCGTCCAAATGTATTTGCTTTGTTCTTCCCGACTGTATTATGCTAGGCGGTGGGTTTTCTGGCACATGGCCATAGTGGATTTCTTTGCCGGACATGTCAGGAACGAACCAAAGCTGCAATAGAAAGATTCACGTGGCGATACCCAGAGTGCACGAGATGGTGCTTTGTACTATAATTAACTGTAGCATAACTAGATCGTCGCTCTGTTACCTTTGGTTGAGTACCGGTAAATAGGTTTCGAAAATGTAGCATTAAACATGGTCAAGCTTATCTTATATTATCTATGATGACGGTGGATACAACGAAACGAGATCACCAATAGTCGATTCGGCTGACCAGTTTGGTACGTCAGGCGTTCCTTTCGATTACAATCAAGCATAGGAAAACTAAGCCGGTAGACTTTTTCGACTTTGTGCTTTGTGCTTGTACATCTGACGATCGGGGCGCTGTTGGTGGTTATGCAGAGGATGTTCGATTTTTAGTGTACAAGATGGATTCCCCATCATTCGCATAACTTCTGGGCACAATCAATTAGAAATGCCGCCCGCCGCCCGCCACATATATCATACTATACGGATGACCGTTTGGTGCTTACGGTGGAGCAAAATATTTATCATGTGCCTACCTTCGACTGGTAAACCCCGAACAGCGCAACGGAGACTAAGGTAAGCCGTATATTCCCCTTCAGCGTATTGGAGAATGTGTGTTCATATGGCAATTAGCAATTAGCTCCAATTTTGAATAGCGCACTTAGTAAGAGAGCCGAGATATCTGAGTATTATCTATTATCTATCTAGTTAGAATGGGCCAATGCTTAGACTGCAGGAGCCACGTGTAATCAACTGATTCTCTCTTCCTAGTGAGGGGCGCACGGCCAAATTCACGCTGAGTCATAAGCAATTCGCACTAGATGATCGGAGCCTGCAAGAACCCGCGGATCCCGGCCTCAGAGACAGAGCAGGTTCCGCCCGTAAGACTGACTTATCACCGATCACCGATAAAGTCGGGCGGTTATATGACGGTACACCCTCATCTAATATTGTACCAGGATGAGCAATTAGCGACTTAAAATGACCTCAACACCCTCGGTCCAAATAAACGCTGAAAATGAATCTTATGAGCCAATGCTAATGTAGGGTTAATAATTGGGTTTATATTTCGCGGGTGGTAAAGATTGCGGTTAGCGAATAATTATTAGATCGGGATAGGATCCTAATACCCGGCATCTGCTGTGCCTGGCAAAGAGTTCCCTATGGTAATTTTGGCTTCATCCTAATGGCCTTAATACAGAATTGCGGTGTTAGTTTACCCCGCCGCGGGGAAAGATGTACGACGGACGGCATCGTTTTTTGCCGGTCTTTCCGTAGTTGGAACGTGTAGATGAAATATTCTATTGACACGGTGCAAAGAAGTACGGAACTATATCACAAATGTTTTTGTACACCAAAGCCAAACAGTACCAAGTTGTGTACCGAAAACCCTTTGTGTGGCTATACATGCTCGAGTAAGCAAGAAGTGGCTTTTTTCTGACTCAAGCCTTACATACTATCTTACTACTAATAGGACCGGCAAGTTCCGCCTAAGATTCGAGGATCGTCTCGCCAGTGGCATTTAGACAGAATGCCAAGCGAAGCAATCGTATCTCTAATATTATCTATTATCTAGACCTCAAAATTCCGGTGTCAAGTGACGCAGACCAAACCTTGTGGCAACCAACCATCTGTGCACACGAACCTGGTCGGGATGTGAGGTTTGTGCTTTGTGCTTACACAAGTCGCGCGGACGATAATGGGAGAGCGTAGGGCGGATAATATCATAAACAGGACTACAGGCGTGTGGTGCCCAAAGTTCAAGTACCCGGTAGCCTTCTATGCGTTCATGAAAAAGTATACCCCTACCATACATGAGGAGTTCGGCCAATTCGTCTTCTGATCCCCGATCTAAAAAAGACAAACCGTCTGCTATGACTTTATAGCAGATGACCGATAGGCACGATGCAAATGTCTATTTCTTATCTATTAGATGCCCGTACTAGAGGGTAGTTCCTGTGATACGTGAATTATAGTTTCTCGGATTAGAGCAAACAATTAACAGTTTAAGGCACTGTTCACGAGCGGCATTGAGCTCAATCCCTAACCTGCCTTCTACGGGTAAATTTTGGGCCAAGCTTGTTCTATACAGCCACCCACTAGAAGGCAGCAAAGGAGGGGAATTTTATTATCTACCTCTTAAGGTACGCGATGATGGGTGGAGGTTTTATCGGCACATCTTTGTTCGACACGTCGTGACGTTTGATTGTTTGGCCATCAGGCAATTAGCAATTAGCTAAAGATCACTGAGGGCTTGAACTAATATTCGTGATTCCACGCAGCACGTAGTTCGAGATATCGTGGCGCAAATCTTTTGATTTTGGTAAAACCGGTAATTAGCACACCATATTACTGCTACTTCGTCATCTTCGGGTCGATGCAATTTGGAGACATACATGCATAAAGCACATTGTCGTGCCCCAGCGCCCGATCGTGCTGAATGGACAAATTTCCCGAGGATCAATTACATCCCAGAGCCAAGGGTCGGTTGTGTTTGAATTGTATTGGAGCGGCATTTATACAGACTGTCAAAGTTCCGTGCCTGCGGTAGCCACGTTTGTGCTTTGTGCTTCCTGGGATACCGGCCATCCGTGGGGGCCCTCTGTCTCTCCCCATAGTGATCACCGATCACCGATCGACACATATGAGGATCTCTGCGGAGTGGAGGACTCTGCGGGACAGGATGAGAGGGGTTATTCTATACACACCTACCGACATGACTCTCCGAAATTCTTCTTTGTGTTCCATGAAGTAACCCCCACTCTCACTAGGACTCTCAAGATATGAGCAGGGTAGGGGATACGAACAATTTACAACATGTAGCAGATATGATGCTCGACCGGGGGCCCGTGATGTTTGGCGTATTGGTCAGAAACCACCACTGGGGCGGCTATCCAGTAAGATCGCCACCTCAATACCCGGGTTGGGGCTTAGGTCCTACAGTGTTAAGGTTAGACTTAGCATTTAACAACCTGTTATCTGGTGCCCAATAGTAGATTGACCACAGGGAGCGAATTTACTTTCTACCCTTCCGATATACCATTATTCACACCGAAGCGAGGCTAGTGGTATGGGATGTTATTTCATGGCCGGACCCCTCCAAGACGAGATTAGGCGCAAAGCATGTTAGCGATGGTACTACTACAAGAGACCTCCCGATAAACGGGTCCCACTGTCATGGCTGCGGACATAGTAGATTGCAATTGATATTTGTCACTCCGCTAACCGCAATGATCTCTTCAATATGTCTGAGCGCCTCTGGCTGTTAGGCTTCTAGGAAGCAACTTCTCCCTTGGTTCATTTCAAGACTCATCCTAAGTTTACGTCCCCGCCTTCATGCTGCTTAATTCCGACTTACTGCATCTATAATCATAGCTCCTGGCCATGGCGAGTCTTCTTACAATCGCGTAGTTCGATGTTAATAAAGTATGTAGGCTTGCTTCACACCTACCTACGAAGAATTCTGCTATGTAGCAATACGCAGGGCACAGCAGCCCGTTCTCAACAATACTAAAAAACGGCACCTATGCCGCCCGCCGCCCGCCATCATCCTGTGCACGTTGGTCCTAACCCGGCGCTGACTTCAGACTGGGCCGCCCGCCGCCCGCTCTAAAGACGCCTTCCAAAGACCGGGACACAATCTTACTCGCCAAACCGCCTCGCGCGGTGCTGGGACACCACCACGTGGAAGTGCAGACATGGCTTTCGTTAGCATAAAATAATAGTCCAGCGATCGCCTACCATTTGGCTCCGACCGTCCACAACTGCGGTCAATCTAAGTATCGGGAAGACCGTCAACGAAACATGTATTGTCGCTGAATAAACTAAATTGGACGAGATGCATAGGTATCGTTCGTGGGGGACGTATTACATGAATCCTTAGTGTCCTGAACTCGTGTCATTACTGGTACCTTTGAATTGAAGGCAGTACAAATCTAAATATAGCACTCCTCCCCGTATCACGGCTAATGACCTCGATTCCAAAGAGCTGGACAAAAGCTACCATAGACTCCGTGGCGAG"
        val patternStrings = "ATCACCGAT TTTGTGCTT CGTAGTTCG GCCGCCCGC TATTATCTA GCAATTAGC".split(" ")

        val result = pm.trieMatching(stringToMatch, patternStrings)

        // accepted answer
        val expectedResult = """
ATCACCGAT: 290 380 387 1922 1929 5799 5806 7525 7532
CGTAGTTCG: 1682 4163 4170 7189 8311
GCAATTAGC: 104 138 145 5530 5537 5869 7126 7133
GCCGCCCGC: 1151 5355 5362 8433 8440 8497 8504
TATTATCTA: 446 453 5091 5592 5599 6464 6471 7031
TTTGTGCTT: 847 5225 5232 6567 6574 7461 7468
        """.trimIndent().lines().toMutableList().sorted()

        val parseResult = parseResultStepikFormatting(result)

        //println(parseResult.joinToString("\n"))

        assertContentEquals(expectedResult, parseResult)

    }

    @Test
    @DisplayName("Pattern Matching Trie Rosalind Quiz Problem")
    fun patternMatchingTrieMatchingRosalindQuiTest() {

        val stringToMatch = "CTAGGGACGAGCATGCGGATGACGTTTGGAGTTTCTTAGTCCGGGTTGAACCTGTGAGGCCCCAACCATAGATCCCCTGCCAGGGTCTGCAGTTCGAGCCCAAGTTAGCCACTTAACGCGACGACCCCAACCCCCGTATTCTAATATCGATCCTTCCCTAAGTGGACACGGTAACGGATGACGATCCCTTTCCCCGCACCCCGGAATCGTCTGGTGGAACAAACATGAAACATGCTAGGTGCTAGGTGCGACTATGGACTATGGTACAGAGAATGCAGATCTAAGCTCTAAGCTCTTCTTGGTTTATTATCGCAGGTCGCATTAGAGGCTAGGTGCCATAGGGCATAGGGCATCGCTGAGTCGTGGACTATGTGGCAGTTTGACGCTCGTCCAAACATAGGGCATAGGGCACTACGCGGATAACATGAAAATAGGGCAGTAGGATCTACGCCAATACGTAAGCTAGGTGCCTGCTAGGTGCGAACATAGGGCATATCTAAGCTCTAAGCTCGACCCTCTAAGCTCTGAAACATGAAAATGGTGACACTTCGGCAGCATAGGGCATAGGGCAGTACTGGACTATGGACTATGGTTTTGTCGCTGGTTCCGTAGGCGAATCTTACAATATAGGAGCGAGGGTCGGGATTGGAATAAGGATCTTTGCTTACGAGTTTTGGTGGGATCAACTTACAGAGGCTACTATATTTCCGAGTCTAAGCTCAGGGCACATCGGATGACGCGATGGACTCAGGGCGGATGACGATGATTAATTAGTAGATCCCCTGATAGACTAATCCCGGCTGCATACTGATCCTAACTACTCTAAGCTCTGACCACGCTGTACCAATGGGGACCGATTGATTGCTCCCATACACATTCCTGGCTACCTTCATACGAATGATAAAC"
        val patternStrings = "CGGATGACG CATAGGGCA AACATGAAA TCTAAGCTC TGGACTATG GCTAGGTGC".split(" ")

        val result = pm.trieMatching(stringToMatch, patternStrings)

        // accepted answer
        val expectedResult = """
15 174 221 233 240 254 279 286 327 336 343 363 395 402 421 461 472 484 495 502 516 528 555 562 575 582 712 730 753 821
        """.trimIndent().lines().toMutableList().sorted()

        val parseResult = parseResultRosalindFormatting(result)

        //println(parseResult.joinToString("\n"))

        assertContentEquals(expectedResult, parseResult)

    }

    fun parseResultRosalindFormatting(result: Map<String, List<Int>>): List<String> {

        val intList : MutableList<Int> = mutableListOf()

        for (k in result.keys.sorted()) {
            intList.addAll(result[k]!!)
        }
        val outString = intList.sorted().joinToString(" ") { it.toString() }
        return listOf(outString)
    }

    fun parseResultStepikFormatting(result: Map<String, List<Int>>): List<String> {
        val outList: MutableList<String> = mutableListOf()
        val str = StringBuilder()
        for (k in result.keys.sorted()) {

            str.append("$k: ")
            val intList = result[k]!!
            str.append(intList.joinToString(" "))
            outList.add(str.toString())
            str.clear()

        }
        return outList
    }

}