@file:Suppress("UNUSED_VARIABLE")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import algorithms.*
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class UtilTests2 {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("util: frequenceTableTest function test")
    fun testFrequencyTable() {

        val matrix = listOf(

/* A: */ .2f, .2f, 0f, 0f, 0f, 0f, .9f, .1f, .1f, .1f, .3f, 0f,
/* C: */ .1f, .6f, 0f, 0f, 0f, 0f, 0f, .4f, .1f, .2f, .4f, .6f,
/* G: */  0f, 0f, 1f, 1f, .9f, .9f, .1f, 0f, 0f, 0f, 0f, 0f,
/* T: */ .7f, .2f, 0f, 0f, .1f, .1f, 0f, .5f, .8f, .7f, .3f, .4f

        )

        val kmer = "TCGTGGATTTCC"
        val prob = probForGivenKmer(kmer, matrix)
//       println(prob)
    }

    /**
     * parseProbabilityMatrix
     * test the character matrix to float list parser
     */
    @Test
    @DisplayName("test parse matrix")
    fun testParseMatrix() {
        val matrixString = """
            0.2 0.2 0.3 0.2 0.3
            0.4 0.3 0.1 0.5 0.1
            0.3 0.3 0.5 0.2 0.4
            0.1 0.2 0.1 0.1 0.2
        """.trimIndent()

        val expectedValue: List<Float> =
            listOf(
                0.2f,
                0.2f,
                0.3f,
                0.2f,
                0.3f,
                0.4f,
                0.3f,
                0.1f,
                0.5f,
                0.1f,
                0.3f,
                0.3f,
                0.5f,
                0.2f,
                0.4f,
                0.1f,
                0.2f,
                0.1f,
                0.1f
            )
        val foo = parseProbabilityMatrix(5, matrixString)
//       println(foo)
    }

    /**
     * test teh createProfile function
     *
     * This is a bit complex so lets break it down:
     *
    one trivial motif:
    "AAAA"
    should produce an ACGT profile of:
    1   1   1   1
    0   0   0   0
    0   0   0   0
    0   0   0   0

     */
    @Test
    @DisplayName("createProfile function trivial input")
    fun testCreateProfileTrivialInput() {
        val inputMotif = listOf("AAAA")
        val expectedOutput = floatArrayOf(
            1.0f, 1.0f, 1.0f, 1.0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
    }

    /**
     * test teh createProfile function with laplace
     *
     * This is a bit complex so lets break it down:
     *
    one trivial motif:
    "AAAA"
    should produce an ACGT profile of:
    1   1   1   1
    0   0   0   0
    0   0   0   0
    0   0   0   0

     */
    @Test
    @DisplayName("createProfile function trivial input with laplace")
    fun testCreateProfileTrivialInputWithLaplace() {
        val inputMotif = listOf("AAAA")
        val expectedOutput = floatArrayOf(
            2.0f/5.0f, 2.0f/5.0f, 2.0f/5.0f, 2.0f/5.0f,   // 5 = one for laplace non-zer base plus 4 for ACGT
            1.0f/5.0f, 1.0f/5.0f, 1.0f/5.0f, 1.0f/5.0f,
            1.0f/5.0f, 1.0f/5.0f, 1.0f/5.0f, 1.0f/5.0f,
            1.0f/5.0f, 1.0f/5.0f, 1.0f/5.0f, 1.0f/5.0f,
        )

        val result = createProfile(inputMotif, applyLaplace = true)
        assertContentEquals(expectedOutput, result, "OOPSIE")
    }

    /**
     * test the createProfile function
     *
     * This is a bit complex so lets break it down:
     *
    one trivial motif:
    "ACGT"
    should produce an ACGT profile of:
    1   0   0   0
    0   1   0   0
    0   0   1   0
    0   0   0   1
     */
    @Test
    @DisplayName("createProfile function trivial input02")
    fun testCreateProfileTrivialInput02() {
        val inputMotif = listOf("ACGT")
        val expectedOutput = floatArrayOf(
            1.0f, 0f, 0f, 0f,
            0f, 1.0f, 0f, 0f,
            0f, 0f, 1.0f, 0f,
            0f, 0f, 0f, 1.0f
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
    }

    /**
     * test the createProfile function
     *
     * Two motif strings now:
     *
    one trivial motif:
    "AAAA"
    "TTTT"
    should produce an ACGT profile of:
    1/2 1/2 1/2 1/2
    0   0   0   0
    0   0   0   0
    1/2 1/2 1/2 1/2
     */
    @Test
    @DisplayName("createProfile function trivial input03")
    fun testCreateProfileTrivialInput03() {
        val inputMotif = listOf(
            "AAAA",
            "TTTT"
        )
        val expectedOutput = floatArrayOf(
            0.5f, 0.5f, 0.5f, 0.5f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0.5f, 0.5f, 0.5f, 0.5f,
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
    }


    /**
     * test the createProfile function
     *
     * Three motif strings now:
     *

    "AAAAAAA"
    "TTTTGGG"
    "CCCCCCC"
    should produce an ACGT profile of:
    1/3 1/3 1/3 1/3 1/3 1/3 1/3
    1/3 1/3 1/3 1/3 1/3 1/3 1/3
    0   0   0   0   1/3 1/3 1/3
    1/3 1/3 1/3 1/3 0   0   0
     */
    @Test
    @DisplayName("createProfile function trivial input04")
    fun testCreateProfileTrivialInput04() {
        val inputMotif = listOf(
            "AAAAAAA",
            "TTTTGGG",
            "CCCCCCC"
        )
        val expectedOutput = floatArrayOf(
            1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f,
            1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f,
            0f, 0f, 0f, 0f, 1 / 3f, 1 / 3f, 1 / 3f,
            1 / 3f, 1 / 3f, 1 / 3f, 1 / 3f, 0f, 0f, 0f
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
    }

    /**
     * test the createProfile function with laplace
     *
     * Three motif strings now:
     *

    "AAAAAAA"
    "TTTTGGG"
    "CCCCCCC"
    should produce an ACGT profile of:
    1/3 1/3 1/3 1/3 1/3 1/3 1/3
    1/3 1/3 1/3 1/3 1/3 1/3 1/3
    0   0   0   0   1/3 1/3 1/3
    1/3 1/3 1/3 1/3 0   0   0
     */
    @Test
    @DisplayName("createProfile function trivial with laplace input04")
    fun testCreateProfileTrivialInputWithLaplace04() {
        val inputMotif = listOf(
            "AAAAAAA",
            "TTTTGGG",
            "CCCCCCC"
        )
        val expectedOutput = floatArrayOf(
            2 / 7f, 2 / 7f, 2 / 7f, 2 / 7f, 2 / 7f, 2 / 7f, 2 / 7f,  // 3 motifs plus ACGT = 7
            2 / 7f, 2 / 7f, 2 / 7f, 2 / 7f, 2 / 7f, 2 / 7f, 2 / 7f,
            1 / 7f, 1 / 7f, 1 / 7f, 1 / 7f, 2 / 7f, 2 / 7f, 2 / 7f,
            2 / 7f, 2 / 7f, 2 / 7f, 2 / 7f, 1 / 7f, 1 / 7f, 1 / 7f,
        )

        val result = createProfile(inputMotif, applyLaplace = true)
        assertContentEquals(expectedOutput, result, "OOPSIE")
    }

    /**
     * test the score function
     *
    one trivial motif:
    "AAAA"
    should produce an ACGT profile of:
    1   1   1   1
    0   0   0   0
    0   0   0   0
    0   0   0   0

    and a score of 0 (no non-matching ACGT to majority
     */
    @Test
    @DisplayName("score function trivial input")
    fun testScoreFunctionTrivialInput() {
        val inputMotif = listOf("AAAA")
        val expectedOutput = floatArrayOf(
            1.0f, 1.0f, 1.0f, 1.0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f
        )
        // pretest - copied from above
        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")

        val score = scoreTheMotifs(inputMotif)
        assertEquals(0, score)

    }



    /**
     * test the score function
     *
     * Two motif strings now:
    "AAAA"
    "TTTT"
    should produce an ACGT profile of:
    1/2 1/2 1/2 1/2
    0   0   0   0
    0   0   0   0
    1/2 1/2 1/2 1/2
     */
    @Test
    @DisplayName("scoreFunction - test for half and half")
    fun testScoreFunctionHalfAndHalf() {
        val inputMotif = listOf(
            "AAAA",
            "TTTT"
        )
        val expectedOutput = floatArrayOf(
            0.5f, 0.5f, 0.5f, 0.5f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0.5f, 0.5f, 0.5f, 0.5f,
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
        // now look at the score - it should be 4 - scoring one point for each mis-match in each column
        val score = scoreTheMotifs(inputMotif)
        assertEquals(4, score)
    }

    /**
     * test the score function
     *
    "AAAA"
    "AAAC"
    "TTTT"
    should produce an ACGT profile of:
    1/2 1/2 1/2 1/2
    0   0   0   0
    0   0   0   0
    1/2 1/2 1/2 1/2
     */
    @Test
    @DisplayName("scoreFunction - test3")
    fun testScoreFunction3() {
        val inputMotif = listOf(
            "AAAA",
            "AAAC",
            "TTTT"
        )
        val expectedOutput = floatArrayOf(
            2/3f, 2/3f, 2/3f, 1/3f,
            0f, 0f, 0f, 1/3f,
            0f, 0f, 0f, 0f,
            1/3f, 1/3f, 1/3f, 1/3f
        )

        val result = createProfile(inputMotif)
        assertContentEquals(expectedOutput, result, "OOPSIE")
        // now look at the score - it should be 5 - 3 x 1 mismatch and 2 on the last column
        val score = scoreTheMotifs(inputMotif)
        assertEquals(5, score)
    }

    /**
     *
     */
    @Test
    @DisplayName("greedy motif search - test 1")
    fun greedyMotifSearch01() {
        val dnaList = listOf(
            "GGCGTTCAGGCA",
            "AAGAATCAGTCA",
            "CAAGGAGTTCGC",
            "CACGTCAATCAC",
            "CAATAATATTCG"
            )

        val expectedResult = listOf(
            "CAG",
            "CAG",
            "CAA",
            "CAA",
            "CAA"
            ).sortedDescending()

        val result = greedyMotifSearch(dnaList, 3).sortedDescending()
        assertContentEquals(expectedResult, result)
    }

    /**
    This dataset checks that your code always picks the first-occurring Profile-most Probable
    k-mer in a given sequence of Dna. In the first sequence (“GCCCAA”), “GCC” and “CCA” are
    both Profile-most Probable k-mers. However, you must return “GCC” since it occurs earlier than
    “CCA”. Thus, if the first sequence of your output is “CCA”, this test case fails your code.
     */
    @Test
    @DisplayName("greedy motif search - test 2")
    fun greedyMotifSearch02() {
        val dnaList = listOf(
            "GCCCAA",
            "GGCCTG",
            "AACCTA",
            "TTCCTT"
            )

        val expectedResult = listOf(
            "GCC",
            "GCC",
            "AAC",
            "TTC"
            ).sortedDescending()

        val result = greedyMotifSearch(dnaList, 3).sortedDescending()
        assertContentEquals(expectedResult, result)
    }

    /**
    This dataset checks if your code has an off-by-one error at the beginning of each
    sequence of Dna. Notice that the first four motifs of the solution occur at the beginning of their
    respective sequences in Dna, so if your code did not check the first k-mer in each sequence of
    Dna, it would not find these sequences.
     */
    @Test
    @DisplayName("greedy motif search - test 3")
    fun greedyMotifSearch03() {
        val dnaList = listOf(
            "GAGGCGCACATCATTATCGATAACGATTCGCCGCATTGCC",
            "TCATCGAATCCGATAACTGACACCTGCTCTGGCACCGCTC",
            "TCGGCGGTATAGCCAGAAAGCGTAGTGCCAATAATTTCCT",
            "GAGTCGTGGTGAAGTGTGGGTTATGGGGAAAGGCAGACTG",
            "GACGGCAACTACGGTTACAACGCAGCAACCGAAGAATATT",
            "TCTGTTGTTGCTAACACCGTTAAAGGCGGCGACGGCAACT",
            "AAGCGGCCAACGTAGGCGCGGCTTGGCATCTCGGTGTGTG",
            "AATTGAAAGGCGCATCTTACTCTTTTCGCTTTCAAAAAAA"
            )
        val kmerLength = 5

        val expectedResult = listOf(
            "GAGGC",
            "TCATC",
            "TCGGC",
            "GAGTC",
            "GCAGC",
            "GCGGC",
            "GCGGC",
            "GCATC"
            ).sortedDescending()

        val result = greedyMotifSearch(dnaList, kmerLength).sortedDescending()
        assertContentEquals(expectedResult, result)
    }

    /**
    This dataset checks if your code has an off-by-one error at the end of each sequence of Dna.
    Notice that the first two motifs of the solution occur at the end of their respective sequences in
    Dna, so if your code did not check the end k-mer in each sequence of Dna, it would not find
    these sequences.
     */
    @Test
    @DisplayName("greedy motif search - test 4")
    fun greedyMotifSearch04() {
        val dnaList = listOf(
            "GCAGGTTAATACCGCGGATCAGCTGAGAAACCGGAATGTGCGT",
            "CCTGCATGCCCGGTTTGAGGAACATCAGCGAAGAACTGTGCGT",
            "GCGCCAGTAACCCGTGCCAGTCAGGTTAATGGCAGTAACATTT",
            "AACCCGTGCCAGTCAGGTTAATGGCAGTAACATTTATGCCTTC",
            "ATGCCTTCCGCGCCAATTGTTCGTATCGTCGCCACTTCGAGTG"
        )
        val kmerLength = 6

        val expectedResult = listOf(
            "GTGCGT",
            "GTGCGT",
            "GCGCCA",
            "GTGCCA",
            "GCGCCA"
            ).sortedDescending()

        val result = greedyMotifSearch(dnaList, kmerLength).sortedDescending()
        assertContentEquals(expectedResult, result)
    }

    /**
    This test dataset checks if your code is correctly breaking ties when calling Profile-most
    Probable k-mer. Specifically, it makes sure that, when you call Profile-most Probable k-mer, in
    the event of a tie, you choose the first-occurring k-mer.
     */
    @Test
    @DisplayName("greedy motif search - test 5")
    fun greedyMotifSearch05() {
        val dnaList = listOf(
            "GACCTACGGTTACAACGCAGCAACCGAAGAATATTGGCAA",
            "TCATTATCGATAACGATTCGCCGGAGGCCATTGCCGCACA",
            "GGAGTCTGGTGAAGTGTGGGTTATGGGGCAGACTGGGAAA",
            "GAATCCGATAACTGACACCTGCTCTGGCACCGCTCTCATC",
            "AAGCGCGTAGGCGCGGCTTGGCATCTCGGTGTGTGGCCAA",
            "AATTGAAAGGCGCATCTTACTCTTTTCGCTTAAAATCAAA",
            "GGTATAGCCAGAAAGCGTAGTTAATTTCGGCTCCTGCCAA",
            "TCTGTTGTTGCTAACACCGTTAAAGGCGGCGACGGCAACT"
        )
        val kmerLength = 5

        val expectedResult = listOf(
            "GCAGC",
            "TCATT",
            "GGAGT",
            "TCATC",
            "GCATC",
            "GCATC",
            "GGTAT",
            "GCAAC"
            ).sortedDescending()

        val result = greedyMotifSearch(dnaList, kmerLength).sortedDescending()
        assertContentEquals(expectedResult, result)
    }


    /**
    This dataset checks if your code has an off-by-one error at the beginning of each
    sequence of Dna. Notice that the all of the motifs of the solution except for the last one occur at
    the beginning of their respective sequences in Dna, so if your code did not check the first k-mer
    in each sequence of Dna, it would not find these sequences.
     */
    @Test
    @DisplayName("greedy motif search with pseudocounts / laplace - test 1")
    fun greedyMotifSearchWithPseudocounts01() {
        val dnaList = listOf(
            "AGGCGGCACATCATTATCGATAACGATTCGCCGCATTGCC",
            "ATCCGTCATCGAATAACTGACACCTGCTCTGGCACCGCTC",
            "AAGCGTCGGCGGTATAGCCAGATAGTGCCAATAATTTCCT",
            "AGTCGGTGGTGAAGTGTGGGTTATGGGGAAAGGCAGACTG",
            "AACCGGACGGCAACTACGGTTACAACGCAGCAAGAATATT",
            "AGGCGTCTGTTGTTGCTAACACCGTTAAGCGACGGCAACT",
            "AAGCGGCCAACGTAGGCGCGGCTTGGCATCTCGGTGTGTG",
            "AATTGAAAGGCGCATCTTACTCTTTTCGCTTTCAAAAAAA",

            )
        val kmerLength = 5

        val expectedResult = listOf(
            "AGGCG",
            "ATCCG",
            "AAGCG",
            "AGTCG",
            "AACCG",
            "AGGCG",
            "AGGCG",
            "AGGCG"
            ).sortedDescending()

        val result = greedyMotifSearch(dnaList, kmerLength).sortedDescending()
        assertContentEquals(expectedResult, result)
    }


}