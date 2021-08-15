@file:Suppress("UnnecessaryVariable")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.*

internal class UtilTests {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("util: frequenceTableTest function test")
    fun testFrequencyTable() {

        val testString = "abcdabcd"  // should find two instances of abcd in the string
        val resultMap = frequencyTable(testString, 4)
        val abcd = resultMap["abcd"]
        assertNotNull(abcd)
        assertEquals(2, abcd)
    }

    @Test
    @DisplayName("util: frequenceTableTest function test - null return")
    fun testFrequencyTableNullReturn() {

        val testString = "abc"  // no string of length 4!
        val resultMap = frequencyTable(testString, 4)
        assertEquals(0, resultMap.size)
    }

    @Test
    @DisplayName("util: frequenceTableTest function test - overlapping")
    fun testFrequencyTableOverlappingString() {

        val testString = "ababababacd"  // no string of length 4!
        val resultMap = frequencyTable(testString, 4)
        val abcd = resultMap["baba"]
        assertNotNull(abcd)
        assertEquals(3, abcd)
    }

    @Test
    @DisplayName("util: findClumps function test - basic function")
    fun testFindClumpsBasicTest() {

        // the window size is 6 characters long - so ab ab ab should produce a map of "ab" to 3 occurrences
        // dups are discarded.

        val testString = "ababababacdedfdababababacd"
        val resultMap = findClumps(testString, 2, 6, 3)

        assertEquals(2, resultMap.size)
        val result1 = resultMap[0]
        val result2 = resultMap[1]
        assertEquals(Pair("ab", 3), result1)
        assertEquals(Pair("ba", 3), result2)
    }

    /**
     *
    Code Challenge: Implement Neighbors to find the d-neighborhood of a string.

    Input: A string Pattern and an integer d.
    Output: The collection of strings Neighbors(Pattern, d).

    Sample Input:

    ACG
    1

    Sample Output:

    CCG TCG GCG AAG ATG AGG ACA ACC ACT ACG
     */
    @Test
    @DisplayName("test neighborhood")
    fun testNeighborhood() {
        val result = neighbors("ACG", 1).sortedDescending()
        val expectedResult =
            listOf("CCG", "TCG", "GCG", "AAG", "ATG", "AGG", "ACA", "ACC", "ACT", "ACG").sortedDescending()

        assertEquals(expectedResult, result)

        val result2 = neighbors("CATGGACGGCGA", 2).sortedDescending()
        for (s in result2) {
            print("$s ")
        }

    }

    @Test
    @DisplayName("test neighborhood - corner cases")
    fun testNeighborhoodCornerCases() {
        val result = neighbors("A", 1).sortedDescending()
        val expectedResult = listOf("A", "C", "G", "T").sortedDescending()
        assertEquals(expectedResult, result)

        val resultE = neighbors("", 1).sortedDescending()
        val expectedResultE = listOf("")
        assertEquals(expectedResultE, resultE)

    }


    @Test
    @DisplayName("test neighborhood - exam question")
    fun testNeighborhoodExamQuestion() {
        val result2 = neighbors("CATGGACGGCGA", 2).sortedDescending()
        var answer = ""
        for (s in result2) {
            answer += "$s "
        }
        // note: trailing space added :-)
        val expectedResult =
            "TTTGGACGGCGA TGTGGACGGCGA TCTGGACGGCGA TATTGACGGCGA TATGTACGGCGA TATGGTCGGCGA TATGGGCGGCGA TATGGCCGGCGA TATGGATGGCGA TATGGAGGGCGA TATGGACTGCGA TATGGACGTCGA TATGGACGGTGA TATGGACGGGGA TATGGACGGCTA TATGGACGGCGT TATGGACGGCGG TATGGACGGCGC TATGGACGGCGA TATGGACGGCCA TATGGACGGCAA TATGGACGGAGA TATGGACGCCGA TATGGACGACGA TATGGACCGCGA TATGGACAGCGA TATGGAAGGCGA TATGCACGGCGA TATGAACGGCGA TATCGACGGCGA TATAGACGGCGA TAGGGACGGCGA TACGGACGGCGA TAAGGACGGCGA GTTGGACGGCGA GGTGGACGGCGA GCTGGACGGCGA GATTGACGGCGA GATGTACGGCGA GATGGTCGGCGA GATGGGCGGCGA GATGGCCGGCGA GATGGATGGCGA GATGGAGGGCGA GATGGACTGCGA GATGGACGTCGA GATGGACGGTGA GATGGACGGGGA GATGGACGGCTA GATGGACGGCGT GATGGACGGCGG GATGGACGGCGC GATGGACGGCGA GATGGACGGCCA GATGGACGGCAA GATGGACGGAGA GATGGACGCCGA GATGGACGACGA GATGGACCGCGA GATGGACAGCGA GATGGAAGGCGA GATGCACGGCGA GATGAACGGCGA GATCGACGGCGA GATAGACGGCGA GAGGGACGGCGA GACGGACGGCGA GAAGGACGGCGA CTTTGACGGCGA CTTGTACGGCGA CTTGGTCGGCGA CTTGGGCGGCGA CTTGGCCGGCGA CTTGGATGGCGA CTTGGAGGGCGA CTTGGACTGCGA CTTGGACGTCGA CTTGGACGGTGA CTTGGACGGGGA CTTGGACGGCTA CTTGGACGGCGT CTTGGACGGCGG CTTGGACGGCGC CTTGGACGGCGA CTTGGACGGCCA CTTGGACGGCAA CTTGGACGGAGA CTTGGACGCCGA CTTGGACGACGA CTTGGACCGCGA CTTGGACAGCGA CTTGGAAGGCGA CTTGCACGGCGA CTTGAACGGCGA CTTCGACGGCGA CTTAGACGGCGA CTGGGACGGCGA CTCGGACGGCGA CTAGGACGGCGA CGTTGACGGCGA CGTGTACGGCGA CGTGGTCGGCGA CGTGGGCGGCGA CGTGGCCGGCGA CGTGGATGGCGA CGTGGAGGGCGA CGTGGACTGCGA CGTGGACGTCGA CGTGGACGGTGA CGTGGACGGGGA CGTGGACGGCTA CGTGGACGGCGT CGTGGACGGCGG CGTGGACGGCGC CGTGGACGGCGA CGTGGACGGCCA CGTGGACGGCAA CGTGGACGGAGA CGTGGACGCCGA CGTGGACGACGA CGTGGACCGCGA CGTGGACAGCGA CGTGGAAGGCGA CGTGCACGGCGA CGTGAACGGCGA CGTCGACGGCGA CGTAGACGGCGA CGGGGACGGCGA CGCGGACGGCGA CGAGGACGGCGA CCTTGACGGCGA CCTGTACGGCGA CCTGGTCGGCGA CCTGGGCGGCGA CCTGGCCGGCGA CCTGGATGGCGA CCTGGAGGGCGA CCTGGACTGCGA CCTGGACGTCGA CCTGGACGGTGA CCTGGACGGGGA CCTGGACGGCTA CCTGGACGGCGT CCTGGACGGCGG CCTGGACGGCGC CCTGGACGGCGA CCTGGACGGCCA CCTGGACGGCAA CCTGGACGGAGA CCTGGACGCCGA CCTGGACGACGA CCTGGACCGCGA CCTGGACAGCGA CCTGGAAGGCGA CCTGCACGGCGA CCTGAACGGCGA CCTCGACGGCGA CCTAGACGGCGA CCGGGACGGCGA CCCGGACGGCGA CCAGGACGGCGA CATTTACGGCGA CATTGTCGGCGA CATTGGCGGCGA CATTGCCGGCGA CATTGATGGCGA CATTGAGGGCGA CATTGACTGCGA CATTGACGTCGA CATTGACGGTGA CATTGACGGGGA CATTGACGGCTA CATTGACGGCGT CATTGACGGCGG CATTGACGGCGC CATTGACGGCGA CATTGACGGCCA CATTGACGGCAA CATTGACGGAGA CATTGACGCCGA CATTGACGACGA CATTGACCGCGA CATTGACAGCGA CATTGAAGGCGA CATTCACGGCGA CATTAACGGCGA CATGTTCGGCGA CATGTGCGGCGA CATGTCCGGCGA CATGTATGGCGA CATGTAGGGCGA CATGTACTGCGA CATGTACGTCGA CATGTACGGTGA CATGTACGGGGA CATGTACGGCTA CATGTACGGCGT CATGTACGGCGG CATGTACGGCGC CATGTACGGCGA CATGTACGGCCA CATGTACGGCAA CATGTACGGAGA CATGTACGCCGA CATGTACGACGA CATGTACCGCGA CATGTACAGCGA CATGTAAGGCGA CATGGTTGGCGA CATGGTGGGCGA CATGGTCTGCGA CATGGTCGTCGA CATGGTCGGTGA CATGGTCGGGGA CATGGTCGGCTA CATGGTCGGCGT CATGGTCGGCGG CATGGTCGGCGC CATGGTCGGCGA CATGGTCGGCCA CATGGTCGGCAA CATGGTCGGAGA CATGGTCGCCGA CATGGTCGACGA CATGGTCCGCGA CATGGTCAGCGA CATGGTAGGCGA CATGGGTGGCGA CATGGGGGGCGA CATGGGCTGCGA CATGGGCGTCGA CATGGGCGGTGA CATGGGCGGGGA CATGGGCGGCTA CATGGGCGGCGT CATGGGCGGCGG CATGGGCGGCGC CATGGGCGGCGA CATGGGCGGCCA CATGGGCGGCAA CATGGGCGGAGA CATGGGCGCCGA CATGGGCGACGA CATGGGCCGCGA CATGGGCAGCGA CATGGGAGGCGA CATGGCTGGCGA CATGGCGGGCGA CATGGCCTGCGA CATGGCCGTCGA CATGGCCGGTGA CATGGCCGGGGA CATGGCCGGCTA CATGGCCGGCGT CATGGCCGGCGG CATGGCCGGCGC CATGGCCGGCGA CATGGCCGGCCA CATGGCCGGCAA CATGGCCGGAGA CATGGCCGCCGA CATGGCCGACGA CATGGCCCGCGA CATGGCCAGCGA CATGGCAGGCGA CATGGATTGCGA CATGGATGTCGA CATGGATGGTGA CATGGATGGGGA CATGGATGGCTA CATGGATGGCGT CATGGATGGCGG CATGGATGGCGC CATGGATGGCGA CATGGATGGCCA CATGGATGGCAA CATGGATGGAGA CATGGATGCCGA CATGGATGACGA CATGGATCGCGA CATGGATAGCGA CATGGAGTGCGA CATGGAGGTCGA CATGGAGGGTGA CATGGAGGGGGA CATGGAGGGCTA CATGGAGGGCGT CATGGAGGGCGG CATGGAGGGCGC CATGGAGGGCGA CATGGAGGGCCA CATGGAGGGCAA CATGGAGGGAGA CATGGAGGCCGA CATGGAGGACGA CATGGAGCGCGA CATGGAGAGCGA CATGGACTTCGA CATGGACTGTGA CATGGACTGGGA CATGGACTGCTA CATGGACTGCGT CATGGACTGCGG CATGGACTGCGC CATGGACTGCGA CATGGACTGCCA CATGGACTGCAA CATGGACTGAGA CATGGACTCCGA CATGGACTACGA CATGGACGTTGA CATGGACGTGGA CATGGACGTCTA CATGGACGTCGT CATGGACGTCGG CATGGACGTCGC CATGGACGTCGA CATGGACGTCCA CATGGACGTCAA CATGGACGTAGA CATGGACGGTTA CATGGACGGTGT CATGGACGGTGG CATGGACGGTGC CATGGACGGTGA CATGGACGGTCA CATGGACGGTAA CATGGACGGGTA CATGGACGGGGT CATGGACGGGGG CATGGACGGGGC CATGGACGGGGA CATGGACGGGCA CATGGACGGGAA CATGGACGGCTT CATGGACGGCTG CATGGACGGCTC CATGGACGGCTA CATGGACGGCGT CATGGACGGCGG CATGGACGGCGC CATGGACGGCGA CATGGACGGCCT CATGGACGGCCG CATGGACGGCCC CATGGACGGCCA CATGGACGGCAT CATGGACGGCAG CATGGACGGCAC CATGGACGGCAA CATGGACGGATA CATGGACGGAGT CATGGACGGAGG CATGGACGGAGC CATGGACGGAGA CATGGACGGACA CATGGACGGAAA CATGGACGCTGA CATGGACGCGGA CATGGACGCCTA CATGGACGCCGT CATGGACGCCGG CATGGACGCCGC CATGGACGCCGA CATGGACGCCCA CATGGACGCCAA CATGGACGCAGA CATGGACGATGA CATGGACGAGGA CATGGACGACTA CATGGACGACGT CATGGACGACGG CATGGACGACGC CATGGACGACGA CATGGACGACCA CATGGACGACAA CATGGACGAAGA CATGGACCTCGA CATGGACCGTGA CATGGACCGGGA CATGGACCGCTA CATGGACCGCGT CATGGACCGCGG CATGGACCGCGC CATGGACCGCGA CATGGACCGCCA CATGGACCGCAA CATGGACCGAGA CATGGACCCCGA CATGGACCACGA CATGGACATCGA CATGGACAGTGA CATGGACAGGGA CATGGACAGCTA CATGGACAGCGT CATGGACAGCGG CATGGACAGCGC CATGGACAGCGA CATGGACAGCCA CATGGACAGCAA CATGGACAGAGA CATGGACACCGA CATGGACAACGA CATGGAATGCGA CATGGAAGTCGA CATGGAAGGTGA CATGGAAGGGGA CATGGAAGGCTA CATGGAAGGCGT CATGGAAGGCGG CATGGAAGGCGC CATGGAAGGCGA CATGGAAGGCCA CATGGAAGGCAA CATGGAAGGAGA CATGGAAGCCGA CATGGAAGACGA CATGGAACGCGA CATGGAAAGCGA CATGCTCGGCGA CATGCGCGGCGA CATGCCCGGCGA CATGCATGGCGA CATGCAGGGCGA CATGCACTGCGA CATGCACGTCGA CATGCACGGTGA CATGCACGGGGA CATGCACGGCTA CATGCACGGCGT CATGCACGGCGG CATGCACGGCGC CATGCACGGCGA CATGCACGGCCA CATGCACGGCAA CATGCACGGAGA CATGCACGCCGA CATGCACGACGA CATGCACCGCGA CATGCACAGCGA CATGCAAGGCGA CATGATCGGCGA CATGAGCGGCGA CATGACCGGCGA CATGAATGGCGA CATGAAGGGCGA CATGAACTGCGA CATGAACGTCGA CATGAACGGTGA CATGAACGGGGA CATGAACGGCTA CATGAACGGCGT CATGAACGGCGG CATGAACGGCGC CATGAACGGCGA CATGAACGGCCA CATGAACGGCAA CATGAACGGAGA CATGAACGCCGA CATGAACGACGA CATGAACCGCGA CATGAACAGCGA CATGAAAGGCGA CATCTACGGCGA CATCGTCGGCGA CATCGGCGGCGA CATCGCCGGCGA CATCGATGGCGA CATCGAGGGCGA CATCGACTGCGA CATCGACGTCGA CATCGACGGTGA CATCGACGGGGA CATCGACGGCTA CATCGACGGCGT CATCGACGGCGG CATCGACGGCGC CATCGACGGCGA CATCGACGGCCA CATCGACGGCAA CATCGACGGAGA CATCGACGCCGA CATCGACGACGA CATCGACCGCGA CATCGACAGCGA CATCGAAGGCGA CATCCACGGCGA CATCAACGGCGA CATATACGGCGA CATAGTCGGCGA CATAGGCGGCGA CATAGCCGGCGA CATAGATGGCGA CATAGAGGGCGA CATAGACTGCGA CATAGACGTCGA CATAGACGGTGA CATAGACGGGGA CATAGACGGCTA CATAGACGGCGT CATAGACGGCGG CATAGACGGCGC CATAGACGGCGA CATAGACGGCCA CATAGACGGCAA CATAGACGGAGA CATAGACGCCGA CATAGACGACGA CATAGACCGCGA CATAGACAGCGA CATAGAAGGCGA CATACACGGCGA CATAAACGGCGA CAGTGACGGCGA CAGGTACGGCGA CAGGGTCGGCGA CAGGGGCGGCGA CAGGGCCGGCGA CAGGGATGGCGA CAGGGAGGGCGA CAGGGACTGCGA CAGGGACGTCGA CAGGGACGGTGA CAGGGACGGGGA CAGGGACGGCTA CAGGGACGGCGT CAGGGACGGCGG CAGGGACGGCGC CAGGGACGGCGA CAGGGACGGCCA CAGGGACGGCAA CAGGGACGGAGA CAGGGACGCCGA CAGGGACGACGA CAGGGACCGCGA CAGGGACAGCGA CAGGGAAGGCGA CAGGCACGGCGA CAGGAACGGCGA CAGCGACGGCGA CAGAGACGGCGA CACTGACGGCGA CACGTACGGCGA CACGGTCGGCGA CACGGGCGGCGA CACGGCCGGCGA CACGGATGGCGA CACGGAGGGCGA CACGGACTGCGA CACGGACGTCGA CACGGACGGTGA CACGGACGGGGA CACGGACGGCTA CACGGACGGCGT CACGGACGGCGG CACGGACGGCGC CACGGACGGCGA CACGGACGGCCA CACGGACGGCAA CACGGACGGAGA CACGGACGCCGA CACGGACGACGA CACGGACCGCGA CACGGACAGCGA CACGGAAGGCGA CACGCACGGCGA CACGAACGGCGA CACCGACGGCGA CACAGACGGCGA CAATGACGGCGA CAAGTACGGCGA CAAGGTCGGCGA CAAGGGCGGCGA CAAGGCCGGCGA CAAGGATGGCGA CAAGGAGGGCGA CAAGGACTGCGA CAAGGACGTCGA CAAGGACGGTGA CAAGGACGGGGA CAAGGACGGCTA CAAGGACGGCGT CAAGGACGGCGG CAAGGACGGCGC CAAGGACGGCGA CAAGGACGGCCA CAAGGACGGCAA CAAGGACGGAGA CAAGGACGCCGA CAAGGACGACGA CAAGGACCGCGA CAAGGACAGCGA CAAGGAAGGCGA CAAGCACGGCGA CAAGAACGGCGA CAACGACGGCGA CAAAGACGGCGA ATTGGACGGCGA AGTGGACGGCGA ACTGGACGGCGA AATTGACGGCGA AATGTACGGCGA AATGGTCGGCGA AATGGGCGGCGA AATGGCCGGCGA AATGGATGGCGA AATGGAGGGCGA AATGGACTGCGA AATGGACGTCGA AATGGACGGTGA AATGGACGGGGA AATGGACGGCTA AATGGACGGCGT AATGGACGGCGG AATGGACGGCGC AATGGACGGCGA AATGGACGGCCA AATGGACGGCAA AATGGACGGAGA AATGGACGCCGA AATGGACGACGA AATGGACCGCGA AATGGACAGCGA AATGGAAGGCGA AATGCACGGCGA AATGAACGGCGA AATCGACGGCGA AATAGACGGCGA AAGGGACGGCGA AACGGACGGCGA AAAGGACGGCGA "
        assertEquals(expectedResult, answer)
    }

    @Test
    @DisplayName("test frequent words with mismatches")
    fun testFrequentWordsWithMismatches() {

        val genome2 =
            "CACAGTAGGCGCCGGCACACACAGCCCCGGGCCCCGGGCCGCCCCGGGCCGGCGGCCGCCGGCGCCGGCACACCGGCACAGCCGTACCGGCACAGTAGTACCGGCCGGCCGGCACACCGGCACACCGGGTACACACCGGGGCGCACACACAGGCGGGCGCCGGGCCCCGGGCCGTACCGGGCCGCCGGCGGCCCACAGGCGCCGGCACAGTACCGGCACACACAGTAGCCCACACACAGGCGGGCGGTAGCCGGCGCACACACACACAGTAGGCGCACAGCCGCCCACACACACCGGCCGGCCGGCACAGGCGGGCGGGCGCACACACACCGGCACAGTAGTAGGCGGCCGGCGCACAGCC"
        val kmerSize2 = 10
        val mismatches2 = 2

        val g = genome2
        val k = kmerSize2
        val m = mismatches2

        val matchList = frequentWordsWithMismatches(g, k, m).sorted()

        var answer = ""
        for (s in matchList) {
            answer += "$s "
        }
        // note: trailing space added :-)
        val expectedResult =
            "GCACACAGAC GCGCACACAC "
        assertEquals(expectedResult, answer)
    }

    @Test
    @DisplayName("distanceBetweenPatternAndStrings test 1")
    fun testdistanceBetweenPatternAndStrings01() {
        val dnas = listOf("TTACCTTAAC", "GATATCTGTC", "ACGGCGTTCG", "CCCTAAAGAG", "CGTCAGAGGT")
        val pattern = "AAA"
        val expectedDistance = 5

        val result = distanceBetweenPatternAndStrings(pattern, dnas)
        assertEquals(expectedDistance, result)
    }

    @Test
    @DisplayName("distanceBetweenPatternAndStrings test 2")
    fun testdistanceBetweenPatternAndStrings02() {


        val dnas = listOf(
            "TCTGCAGAGCGCAAGCGTCTCAATGTTTTTCGCTGCAAGTGTTAGCTGCGTTTGTCGACACACAAATGAAGTGACCACATCAAACCTAATTATG",
            "TCCCGCTCGAGGACAGACACCGGTGCAGCCGAGGGTATTATAGTCTGCTCGTATGGTTTGTATGGAGGATAATAGATGGGGTGACGAAATGAGA",
            "CCTCCCTGCCAGGTTGGTGAATTTAAGCATAAAGACCTGGAGGACTGACGGGTCTGGTCGACCACCATTCGTCGGTTCTCGAGCGCTGTTTCTA",
            "GATACAGGTCGCTTGAAATGTCCCTGAGACGTCCGGGCCAAGAGCGAAACAAATCTCAGTCCTGGGTGGCGGCAGCGAAGGGATTAGATTACTT",
            "ATGGAACCGTGTCACGGCAGTCTACCTTTTACCCAGAGCTGTATATGACGGTTAGTCCCCGAGGCCATCTCGCACCCTACTGAGCACAAATATA",
            "AATCCCTGCAGGTCAGGCTGTTCTGAAAGAACCCTGCAGGCTGGTGCTATTCCTTAAGACGCCGAGGTTCAGATACTCTCAGCCAGAGAGCAGA",
            "CCTATTAACTTCTCTCTAGATCGTAAACGACTATGGACTTACACGTCCCGCTTTCTTGTCTTGGGTGCCGTCACACTAGCCCACTACAGTCGGG",
            "GGCGCACCTTCGATATTAATGCGAGTCAATTGCCGTTTAAGTCCGCGCGTAGCGGATGCACGTAGGGACTTTAAAGCCCCATGCCAACTGTATA",
            "TCTTGGTTACAATTACCCTCCTGAGCCGATAAGCGCCTCTCAGGCACACTGTGGCAGTTCATCGTGTCCTTCGTCTTATTCCCTGCCCCCAGCA",
            "ATCTCAATGCGATATATATGGTCCTGCCTAAAAATAGAGCTAGTGAGCGTGGTGGTATCATTGTTGACTACCCGGGACAGGCGACACCGTACGA",
            "ATCTATAGAAACATCTAGCACAGGAACACCCATAAAAACACCACTAGCCGACCTGTTCGTCGGTTTTCCATGCGAATGGTAATATTACGTTTGT",
            "ACGCATACGTCAGAAATCTGTGAAAGTACGACGCCCACTGGACCCACGGTCGCTGCGTTCACAATCATTGTGATCCTGGATCCATAACGCCTAA",
            "GACCAAGGCCCGAAAGTGTGGCGAGATAAGAAAACCCACTCCATATGACCGTGGATCCCGTGGGAGTCCTCCTTTCATCCCTCTAGCGCTAGAA",
            "ATACCCCCGTAGTTTTCGGACTCAAAAAGTATTTATGTGACTTGCAATAGTAGAGACCGGCGTCTTTGCCGCTGTCACCTGCTGTATGATCACC",
            "CGATGGTACCCAAGAGTGGTCGGTCTACCAAGCATCGCGACCCGCTTTCGTTCACCAGCCGAGAGTGCCATGTAAGTCAGCAGCTAACACCCTG",
            "TGTCTCAAGCATTACTATGGCTGGCCTCGTTATTAGATTCTGCCTAAGCCGCATGGGCGCGTGACCGTGGGCACATTGCCCTGAATTACCCGCC",
            "AGGTGCAATTAACTGTACTAAGCTCAAGATCTCAATCCGCAAAGGCCATTCAGGCGACTCCGTGTGGTGCCTATACCGCACAAACGTAGGAGCG",
            "CATTAAGACGGATTGGGACCCGCGGGAGAAAGGCGCGTTGGAGCGAGGGGGTCATTTGCACTGCCGAGCAGACGCGTCTAGGTCATCTTCTCTA",
            "CCGAAAAGCCCAAAGGTTTTATGCAAGTTCCACGAGAATCGTCGAGCGCTGAGGTACGGCCACCGAATCACAATTCCATTCACATTCGCGGAGT",
            "TTTCTTTGGCAGATGTCCTCCGGACTTCCGGAATGAAGCTTCAGGGCGCGCCTATCGCGAAACCCCGACGTGACGGAACGTCCTGTCCTGGACT",
            "AGTTGCCCAAGGTTTGGACCTGCCCGGTTTTCTGGCCGCGTATGAAGTATTCCAGTAGATCCCTGCGATACTTCTCGCTACGCTAGGTCCGATA",
            "CTCGTAAATAAACGGGATAACAAAGTGTTATCGATTATGTATAGCGGTCAGGGTGTCTGCACTTATCGGAGTCGGGAGGAGACCCTTCCTAAGA",
            "CGCTTATGTCGTAAGTATAGCAACTAAGGGGCCATATGAACAAGTGTAGTACACGCGGCACCAGGTACCCTTAGGCGTTGTTCTTCGGAGGTTT",
            "CTAAAGACCCCGCGTGGAATTAGAGGGAGCTCCAACGATAGAAATGAAGGAGGGGGCTTGTATAAATCCAACTGAAGGTGAACGAAGCTATAGA",
            "CGTTTCCCTACACGGCCAATGAACAGCGGGCGCATAACATCTCTTAGCCCTGACTTCTGCGACGCATATATTTACCGGGACATGATCACAGTGT",
            "TTACAGCACAGCAAGAAATTCCGTGCTTGGGGGATCTACCGTGCATACTCCTGAGCCCAGATATTGGCCCGTAAGAATCATGCATTGAAAATAA",
            "TGGTCTCACCAGTGGGCACCGCGTATTGTCAGGCAAGAGCAATTAGTGATTCGTACATAGAGGTCTTCAATCTCATGTATAGAGTCGCTGGTTC",
            "ATGCACCTAGAAGAACATAGTTAAGAAGCGTCATGGAGCTACAGAGTTCCGTGGCAATGTACAGTAGCTGTTGCCAATTTGTTGGGACTCAGCC",
            "AGGTCTCTTCGAAATAGGTAGTGCCGGAACCATACTGAGAGTCTGCAAACGTTGCTTCGGAATGTACCTGTTGAGGACACTACTCTGACGGGCG",
            "GCGATCACCCGCGACTCGTTCACTGGTACTTCCAATGCACTGCGCCCAATGCGCAATACGCGAATTCTCTTAAATGATCTCAAATATATGAGCT"
        )


        val pattern = "AATTGGG"
        val expectedDistance = 76

        val result = distanceBetweenPatternAndStrings(pattern, dnas)
        assertEquals(expectedDistance, result)
    }

    /**
     *
     * TEST DATASET 1:
    Input:
    TAA
    TTTATTT CCTACAC GGTAGAG
    Output:
    3
    This dataset checks multiple potential mistakes.
    First, it checks that you are actually using all three sequences of Dna (and not just a
    single sequence). The Hamming Distance between Pattern and each individual sequence in Dna
    is 1, so if your code returns a total score of 1, we fail it for this reason.
    Next, it checks if you are only using the first k-mer in each sequence of Dna. For
    example, if you do this, you would output d(TAA,TTT) + d(TAA,CCT) + d(TAA,GGT) which is
    8, instead of the correct answer of 3.
    Finally, it checks if you are only using the last k-mer in each sequence of Dna. For example, if
    you do this, you would output d(TAA,TTT) + d(TAA,CAC) + d(TAA,GAG) which is 6, instead
    of the correct answer of 3.
     */

    @Test
    @DisplayName("distanceBetweenPatternAndStrings test 3")
    fun testdistanceBetweenPatternAndStrings03() {
        val dnas = listOf("TTTATTT", "CCTACAC", "GGTAGAG")
        val pattern = "TAA"
        val expectedDistance = 3

        val result = distanceBetweenPatternAndStrings(pattern, dnas)
        assertEquals(expectedDistance, result)
    }

    /**
     * TEST DATASET 3:
    Input:
    AAA
    TTTTAAA CCCCAAA GGGGAAA
    Output:
    0
    This dataset checks if your code has an off-by-one error at the end of each sequence of Dna.
    Notice that each sequence has a perfect match of “AAA” at the very end, so if your code returns
    a nonzero answer to this test dataset, it must have missed the last k-mer of each.
     */

    @Test
    @DisplayName("distanceBetweenPatternAndStrings test 4")
    fun testdistanceBetweenPatternAndStrings04() {
        val dnas = listOf("TTTTAAA", "CCCCAAA", "GGGGAAA")
        val pattern = "AAA"
        val expectedDistance = 0

        val result = distanceBetweenPatternAndStrings(pattern, dnas)
        assertEquals(expectedDistance, result)
    }

    /**
     * https://stepik.org/lesson/240248/step/1?unit=212594
     * given problem
     */
    @Test
    @DisplayName("distanceBetweenPatternAndStrings exam")
    fun testdistanceBetweenPatternAndStringsExam() {
        val dnas = listOf(
            "ATAAAGCAGTTAAGATTCCTTAGATCTTCAAAGCATAATCTCTGGCTATTTGTCACGTATATGACCCTCTACGTACCCAGGTCCAGCTTCT",
            "TTTGCCGCCGCAACCGGCGATCCGCGGTTGATCTCACATGTTGTTGACATGCATGCTATCGCCAATTGCTGCAGCGTAGATCCGGCTTCTC",
            "GCACTACCCCTCACAACTAGGTTCAACGACGACAAGGTAGGCGATTCAAAGCAGCGTCCGACATACTAAACGGAATTCTAGGTCTGGGGTA",
            "AACAACCCACCACTTTTCTGGAATAACTGACCGGACGATTTAGAGTCCGTGGGTAGGTATCCATTGAGGGGCACGCATGGGGTCCGGTAAA",
            "TTCGAAGGGTTGTATAATTGATACTGGGAACCCCAACGCTCCTTCCGGGTGAGAGGCCCACTCATGTGATCACGGAAATCGGATCTGCGCG",
            "TGGCGTTATCAAAGATAATCTGGTGGACTCAAACTGGGAACCTATAAGTTTACTGATATTCACTGACCCCTTCCGCCGGAGGGGGAATTAA",
            "CTAACAATCTCGAACCGTCTTTCTGCCCTCTGATACTTCCAGAGCAGCACCTCGCTTAGATCTTCGTTTAACAGATACTGGCATCTTCGTC",
            "CCTTCTGCTACCAGTCGCTGCTGATATGTCGATGTGACCATGCAAGATCCCGCTACCCAGACAAAATATAGCTTGATAATTCTGGATATGT",
            "ATTACGCAGCGCGGCCCCCGCAGCTGCGACCCCGACTCTCGAGTAGACGAAGGCTTTACTGTCCGCCGCCGGGGCACGGGGTGCGTTTCAC",
            "TCATCGTGGAGGTTATTCACCTTCAGATGTGATTCCCAAGGGCCCGCTCACTTGTTACTCTAGTGAGTCTTTTAGGGGTCACTCGACAGCA",
            "TTGGTGGACAGCGGTCTCTTTGAAGCCGTAATCGTGGAAACGGCTTCAATAGGTTTCTCCCGTAGATGAGTGCCCGTTCCGCGTGAATTGG",
            "GAATACAACACCAACTTGCAGAAATGGGTGGAGAGTGCGACCAACTCGACATTGCCGCTAGATCCGTTCAAGGCGGTGACCATGAAGGGGC",
            "TGGGATTCATCACAATCTATCACAATTACCATGCAAGGTCTCTTTTAACACCAACTTTCCTGCTATTAGGGATTCTATGGGCACCTGGTTG",
            "GCCCGGGGATCGTATTAGGCCAGGCTTCCCCGAGGCGTGGGAGGGACACTGGCGGGCACGTGTACCCACATTTATGTTTCTGACTGTTCGG",
            "ACTTATGGTCCTGTTCCTCATCCTCTGTTCTGAACAAAACAGACCGAGAAACATATACACTAGGCACGAACGAAGCTCTAGTGCTGTGCAT",
            "TGGGCCACGTTTGCCCTAAACGTGCACCTAGCTAGTTAAATCTAATGGCTCCTGATTGTCATGATAAGAGGAGGTCCGCAGCCCTGACATC",
            "GTCGGACCTTATGCCCGGAAACAGCAATTAAATTGTTAGCCGGTCAGGGTCCTAGAATAGATGCTGCAGGTGCGCTAGATGACATAACATA",
            "ATGTATCGTACCGTTCCTCAGTGTATAACAAGTAGTGAACAAACCTTGAAGATACACACAGTCGATATCGGTCGTCTACACCGTGACTCAT",
            "GTCACCCATGCCGTAAGGCCAAGCGGGCGTAGGCGCCACAGCACCTCTAGTCGCCATTTCCACATACTCGATCCGCCTGTCCATCTTAATA",
            "CCATGGCATGTCCGGAAATTGCCCTCATCTGCAAGCACCCCTCGCCCACGACCGCCTTCGAACAAACCCCGACCTCCGGGTTAAGCGACGT",
            "AGCGCCGTTTGTGTGAGTGAGACCTATTCGTCGTAGTGTCGACATGCGATACGGCCCCTCCAACGGTGGGACGGTGCCATACGTACCGAGG",
            "CTTAAATGAGCACAGTACCATAACATCTCCAAAAATGAGGCCACGATGAAGGATAGAGAATCGTATTGTCCAAGCCGGCACGTTCTATCGC",
            "CGGCCATTATCGCCTAGAGGAAGCCCCTCAGCCAGGGCTAGGCCTCTGAAAAACGGCAACGGTCTGAGCTTACACAATTACCTCTGTAAGT",
            "GTGGCTATGGCGCGATTGTCGTTGGGGCGTGCAGCCCGCGGGCCCCGGTGCACGAAGTTATCACCCCGTTCCAGGCTAAGAATGGACAGCA",
            "CCCTGGGTTTTTTAGAATCTAATTCGGAACCCAGGGACTGATCGGCTCGTAAGTCAAAGCTATCAGATGAGAAATACATTCTGCTAGCATC",
            "CCAGATCTCACGAAGACGAATAAATAAGTTTATTGCTATGATGACGAGCAGTGGGAGGCGGATCGTAGGAACATGAGAGTAATTCTGTAAT",
            "CATCTTAGGGGGACACCGGTGAAGCCCGTCGGTATCGTAGATCAGAAAAGGTGCGCCGAATGGTACACGTACGTTAGTCCCCCCATGATGG",
            "GGACGCCTACTTATTCAGAGACGGCTGTCAATTGAGATAGCGGGAACTTCCATGATCTACGCGAAGAGCTCGGTCTCTCTGCATGTTGAAT",
            "ACCAGACGTTGCTTAAGTGTGCGGCATAGCACACCAATGCCGTGGGCGCTGCAGTAAATGGACGATGAGAGCCCGAATGCCTTCCCCGACG",
            "GGAGGTGGGACGTGTTCTACCCTCGCTCTGTGCTATCGCTGAGCGTTGTGGCGTTAAGGTCGTCACACACCCGAATTATAACACGCGAACT",
            "AATGTATTCCGTTGCGTCGGCATAGTGCGCTAATTACCCAGAAGCTCCGTTTGATAAGGTCGATGGACGCTCAGTAAGTTGACAGACTCGT",
            "TGAGCCTAATGGACGTCATTTGGTAATTCTCCATCCGGGTATTAACCTTGTTATGTTATCAGGACTAAGTATGTCCCGCACTCGTCGTAGT",
            "TCGGAAGTGGCGTATGGATCCACTCTGGACGCTGAAATTCGACGAAGACTCGGTGTTTGACAGGGCCCACGGCATCATCTCATTATGCCGA"
        )
        val pattern = "CCTCAT"
        val expectedDistance = 52  // correct

        val result = distanceBetweenPatternAndStrings(pattern, dnas)
        // println("$result")
        assertEquals(expectedDistance, result)
    }

    @Test
    @DisplayName("basic test of allstrings")
    fun basicAllStringsTest() {
        val expectedBasic = listOf("A", "C", "G", "T")
        val retVal = allStrings(1)
        assertEquals(expectedBasic,retVal)
    }

    @Test
    @DisplayName("basic test of allstrings 2 kmer")
    fun allStringsTestTwoKmer() {
        val expectedBasic = listOf(
            "AA", "AC", "AG", "AT",
            "CA", "CC", "CG", "CT",
            "GA", "GC", "GG", "GT",
            "TA", "TC", "TG", "TT"
        ).sorted()
        val retVal = allStrings(2).sorted()
        assertEquals(expectedBasic,retVal)
    }

    @Test
    @DisplayName("basic test symbol to Number extension function")
    fun symbolToNumberTest() {

        val result1: Int = 'A'.symbolToNumber()
        val expectedResult1 = 0
        assertEquals(expectedResult1,result1)

        val result2: Int = 'T'.symbolToNumber()
        val expectedResult2 = 3
        assertEquals(expectedResult2,result2)

        val result3: Int = 'X'.symbolToNumber()
        val expectedResult3 = -1
        assertEquals(expectedResult3,result3)
    }

    /**
     *  http://rosalind.info/problems/ba1l/
     */
    @Test
    @DisplayName("basic test patternToNumber hashing function")
    fun patternToNumberTest() {

        val result1 = patternToNumber("AA")
        val expectedResult1 = 0L
        assertEquals(expectedResult1,result1)

        val result2 = patternToNumber("CC")
        val expectedResult2 = 5L
        assertEquals(expectedResult2,result2)

        val result3 = patternToNumber("AGT")
        val expectedResult3 = 11L
        assertEquals(expectedResult3,result3)

        val extraResult = patternToNumber("AAACTCGTCAGCTCTGTCAGAAGGGAACGG")
        val expectedExtraResult = 8361364705814554L
        assertEquals(extraResult, expectedExtraResult)
        
    }

    /**
     * test numberToPattern function
     */
    @Test
    @DisplayName("basic test of numberToPatternFunction")
    fun numberToPatternTest() {

        val result1 = numberToPattern(45, 4)
        val expectedResult1 = "AGTC"
        assertEquals(expectedResult1, result1)

        val result2 = numberToPattern(5353, 7)
        val expectedResult2 = "CCATGGC"
        assertEquals(expectedResult2, result2)
    }

    /**
     * test frequencyArray function
     * http://rosalind.info/problems/ba1k/
     */
    @Test
    @DisplayName("basic test of frequencyArray function")
    fun frequencyArrayTest() {
        val result1 = frequencyArray("ACGCGGCTCTGAAA", 2)
        val resultString = result1.joinToString(" ")
        val expectedResult1 = "2 1 0 0 0 0 2 2 1 2 1 0 0 1 1 0"
        assertEquals(expectedResult1, resultString)

//        val result2 = frequencyArray("ACTTCGCCTAAGTCATTTATCCCGTGGTACGACGCTCCCTTACAGTCTTATATCCCGGTATATACGCAGAAATGCCTACGTCCCCTCGTCCCACACACCAGGGAAGCTGAAATCGCTCATCTACTATGCGTGTACTTCCGGACGAAATCGTCGTCGGCTTCTGTCTGGCGCTGGAGATCCGGGCTTCTTGAGGGACACACCCATTATGACCGTTACAGGACTTACAACTACTCTGAGCAATGATGGTGCTCTGTAACGAACAAACGCACTCACCTCTGTTTCCTGTATGACATCCTCAAATGGATCGACCGTGATGTACTGAGCGAATAAGTGCGGATTACATTTATAGTCAGCTACATTTATTCGCCGCTCGGAGCAGAGTATAATGAATTTATACCACTTGTTAGACTCCTTCTCGCATTTAGCCCCTACCGCAAGTCGGAGCGTTGGGGTGCAATAGAGTTTTCAGTATCTACGTACCGTTAAGTCTCTCGCGTTCTTTCAGCAGGCATCAATATGTTGCTTGCTGTGGGGTCGGGTGGGGCGGAGAGCCAATAAAGTGCATCGGAATTGGCTGCCCTCCTACGAATCCGCAAGATGCGGTGATGCTACGTGATTATGACTACTAGCTTAGTCCC", 6)
//        val resultString2 = result2.joinToString(" ")
//        val expectedResult2 = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 2 0 0 1 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1 0 2 0 0 0 0 0 0 1 0 1 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 1 0 1 0 0 0 0 1 0 0 1 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 2 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 2 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 1 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 1 0 0 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 1 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 1 1 0 1 0 0 0 0 1 0 0 1 0 1 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 1 1 0 0 1 0 0 0 1 0 0 0 0 0 1 1 0 0 0 0 1 0 0 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1 1 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 1 0 1 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 2 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 1 0 0 0 1 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 2 0 0 0 0 0 1 0 0 1 2 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0 0 1 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 1 1 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1 0 1 2 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0 0 1 1 0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 1 1 0 0 0 1 1 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 1 0 1 0 0 3 2 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 1 0 0 0 0 1 0 1 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 1 0 0 2 0 0 0 0 1 0 0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 2 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 1 1 1 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 1 1 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 1 0 1 0 1 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 1 1 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 1 0 0 0 1 0 1 0 0 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 0 0 0 0 2 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 1 1 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 2 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 1 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 1 1 0 0 1 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 2 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 1 0 0 0 2 0 1 0 0 0 0 0 0 0 1 0 1 0 0 0 0 1 1 0 0 1 0 0 0 0 0 0 0 1 1 1 0 0 0 1 1 0 0 0 1 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 1 0 0 0 0 1 1 1 0 0 0 0 0 0 0 2 0 0 0 0 0 0 1 0 0 0 0 3 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 1 1 0 0 0 1 0 0 0 0 1 0 0 0 1 0 1 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 1 0 0 0 1 0 1 0 0 1 0 2 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 1 0 0 1 1 0 1 0 0 0 1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 1 0 0 1 0 0 0 0 0 1 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 2 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 2 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 2 1 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 1 1 1 0 1 0 0 2 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 1 0 0 1 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 2 1 0 1 0 0 2 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0"
//        assertEquals(expectedResult2, resultString2)
    }


}