import algorithms.medianString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**

2.4 From Motif Finding to Finding a Median String

Code Challenge: Implement MedianString.

Input: An integer k, followed by a collection of strings Dna.
Output: A k-mer Pattern that minimizes d(Pattern, Dna) among all
possible choices of k-mers. (If there are multiple such strings Pattern, then you may return any one.)

 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba2b/

 */

internal class BA2BMedianStringTest {

    @Test
    @DisplayName("test medianString 1")
    fun testMotifEnumerationShortInput() {

        val g = listOf(
            "TGATGATAACGTGACGGGACTCAGCGGCGATGAAGGATGAGT",
            "CAGCGACAGACAATTTCAATAATATCCGCGGTAAGCGGCGTA",
            "TGCAGAGGTTGGTAACGCCGGCGACTCGGAGAGCTTTTCGCT",
            "TTTGTCATGAACTCAGATACCATAGAGCACCGGCGAGACTCA",
            "ACTGGGACTTCACATTAGGTTGAACCGCGAGCCAGGTGGGTG",
            "TTGCGGACGGGATACTCAATAACTAAGGTAGTTCAGCTGCGA",
            "TGGGAGGACACACATTTTCTTACCTCTTCCCAGCGAGATGGC",
            "GAAAAAACCTATAAAGTCCACTCTTTGCGGCGGCGAGCCATA",
            "CCACGTCCGTTACTCCGTCGCCGTCAGCGATAATGGGATGAG",
            "CCAAAGCTGCGAAATAACCATACTCTGCTCAGGAGCCCGATG"
        )
        val k = 6 // kmer length

        val expectedResult = "CGGCGA"
        val result = medianString(g, k)

        assertEquals(expectedResult, result)

    }

    /**
     *
     * TEST DATASET 1:
    Input :
    3
    ACGT
    ACGT
    ACGT
    Output :
    ACG or CGT (not both)
    This dataset checks that your output is the correct length. Notice that there are technically
    two solutions to the problem (“ACG” and “CGT” are equally optimal), but the problem
    specifically states to only return a single output (you can arbitrarily pick any optimal solution).
    Also, since k = 3 in this dataset, we check that your output is exactly of length k (should not be
    any longer or shorter than this).
     */

    @Test
    @DisplayName("test medianString 2")
    fun testMotifEnumeration02() {

        val g = listOf(
            "ACGT",
            "ACGT",
            "ACGT"
        )
        val k = 3 // kmer length

        val expectedResult = "ACG"
        val result = medianString(g, k)

        assertEquals(expectedResult, result)
    }

    /**
     * TEST DATASET 2:
    Input :
    3
    ATA
    ACA
    AGA
    AAT
    AAC
    Output :
    AAA
    This dataset checks if your code considers kmers
    that do not occur in Dna. Notice that
    the best 3mer
    is “AAA”, which does not actually occur in any of the sequences in Dna. It is
    perfectly fine that our optimal median string does not actually occur in any of the sequences in
    Dna (similar to the Frequent Words With Mismatches Problem from the Replication chapter).
     */
    @Test
    @DisplayName("test medianString 3")
    fun testMotifEnumeration03() {

        val g = listOf(
            "ATA",
            "ACA",
            "AGA",
            "AAT",
            "AAC"
        )
        val k = 3 // kmer length

        val expectedResult = "AAA"
        val result = medianString(g, k)

        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("test median string extra dataset")
    fun testMediaStringExtraDataset() {
        val sample = """
            TGATGATAACGTGACGGGACTCAGCGGCGATGAAGGATGAGT
            CAGCGACAGACAATTTCAATAATATCCGCGGTAAGCGGCGTA
            TGCAGAGGTTGGTAACGCCGGCGACTCGGAGAGCTTTTCGCT
            TTTGTCATGAACTCAGATACCATAGAGCACCGGCGAGACTCA
            ACTGGGACTTCACATTAGGTTGAACCGCGAGCCAGGTGGGTG
            TTGCGGACGGGATACTCAATAACTAAGGTAGTTCAGCTGCGA
            TGGGAGGACACACATTTTCTTACCTCTTCCCAGCGAGATGGC
            GAAAAAACCTATAAAGTCCACTCTTTGCGGCGGCGAGCCATA
            CCACGTCCGTTACTCCGTCGCCGTCAGCGATAATGGGATGAG
            CCAAAGCTGCGAAATAACCATACTCTGCTCAGGAGCCCGATG
        """.trimIndent()

        val reader = sample.reader()
        val lines = reader.readLines()

        val k = 6 // kmer length

        val expectedResult = "CGGCGA"
        val result = medianString(lines, k)

        assertEquals(expectedResult, result)

    }
}