import algorithms.frequentWordsWithMismatches
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * @link: https://stepik.org/lesson/240221/step/10?unit=212567

1.8 Some Hidden Messages are More Elusive than Others

We now redefine the Frequent Words Problem to account for both mismatches and reverse complements.
Recall that Patternrc refers to the reverse complement of Pattern.

Frequent Words with Mismatches and Reverse Complements Problem:
Find the most frequent k-mers (with mismatches and reverse complements) in a string.

Input: A DNA string Text as well as integers k and d.
Output: All k-mers Pattern maximizing the sum Countd(Text, Pattern)+ Countd(Text, Patternrc) over all possible k-mers.

Code Challenge: Solve the Frequent Words with Mismatches and Reverse Complements Problem.

Sample Input:

ACGTTGCATGTCGCATGATGCATGAGAGCT
4 1

Sample Output:

ATGT ACAT

See also:
stepik: https://stepik.org/lesson/240221/step/10?unit=212567
rosalind: http://rosalind.info/problems/ba1j/
 */

internal class S01c08p10WordsWithMismatchesTest {

    /**
     *  TEST DATASET 1:
    Input :
    AAAAAAAAAA
    2 1
    Output :
    AT TA
    This dataset checks that your code includes k-mers that do not actually appear in Text .
    Notice here that, although AT nor TA actually appear in Text , they are valid because they appear
    in Text with up to 1 mismatch (i.e. 0 or 1 mismatch).
     */
    @Test
    @DisplayName( "test frequent words problem including reverse complement scan")
    fun testFrequentWordsProblemWithReverseComplementScan() {

        val g = "AAAAAAAAAA"
        val k = 2 // kmer length
        val d = 1 // hamming distance

        val expectedResult = "AT TA "
        val matchList = frequentWordsWithMismatches(g, k, d, scanReverseComplements = true).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }

    /**
     *  TEST DATASET 2:
    Input :
    AGTCAGTC
    4 2
    Output :
    AATT GGCC
    This dataset makes sure that your code is not accidentally swapping k and d .
     */
    @Test
    @DisplayName( "test frequent words problem including reverse complement scan not swapping")
    fun testFrequentWordsProblemWithReverseComplementScanNotSwapping() {

        val g = "AGTCAGTC"
        val k = 4 // kmer length
        val d = 2 // hamming distance

        val expectedResult = "AATT GGCC "
        val matchList = frequentWordsWithMismatches(g, k, d, scanReverseComplements = true).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }

    /**
     *  TEST DATASET 3:
    Input :
    AATTAATTGGTAGGTAGGTA
    4 0
    Output :
    AATT
    This dataset makes sure you are finding k-mers in both Text and the Reverse Complement
    of Text .
     */
    @Test
    @DisplayName( "test frequent words problem including reverse complement scan both directions")
    fun testFrequentWordsProblemWithReverseComplementScanBothDirections() {

        val g = "AATTAATTGGTAGGTAGGTA"
        val k = 4 // kmer length
        val d = 0 // hamming distance

        val expectedResult = "AATT "
        val matchList = frequentWordsWithMismatches(g, k, d, scanReverseComplements = true).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }

    /**
     *  TEST DATASET 4:
    Input :
    ATA
    3 1
    Output :
    AAA AAT ACA AGA ATA ATC ATG ATT CAT CTA GAT GTA TAA TAC TAG TAT
    TCT TGT TTA TTT
    This dataset first checks that k-mers with exactly d mismatches are being found. Then, it
    checks that k-mers with less than d mismatches are being allowed (i.e. you are not only allowing
    k-mers with exactly d mismatches). Next, it checks that you are not returning too few k-mers.
    Last, it checks that you are not returning too many k-mers.
     */
    @Test
    @DisplayName( "test frequent words problem including reverse complement exact")
    fun testFrequentWordsProblemWithReverseComplementExact() {

        val g = "ATA"
        val k = 3 // kmer length
        val d = 1 // hamming distance

        val expectedResult = "AAA AAT ACA AGA ATA ATC ATG ATT CAT CTA GAT GTA TAA TAC TAG TAT TCT TGT TTA TTT "
        val matchList = frequentWordsWithMismatches(g, k, d, scanReverseComplements = true).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }


    /**
     *  TEST DATASET 5:
    Input :
    AAT
    3 0
    Output :
    AAT ATT
    This dataset checks that your code is looking at BOTH Text and its Reverse Complement
    (i.e. not just looking at Text , and not just looking at the Reverse Complement of Text , but looking
    at both).
     */
    @Test
    @DisplayName( "test frequent words problem including reverse short string")
    fun testFrequentWordsProblemWithReverseShortString() {

        val g = "AAT"
        val k = 3 // kmer length
        val d = 0 // hamming distance

        val expectedResult = "AAT ATT "
        val matchList = frequentWordsWithMismatches(g, k, d, scanReverseComplements = true).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }

    /**
     *  TEST DATASET 6:
    Input :
    TAGCG
    2 1
    Output :
    CA CC GG TG
    This dataset checks that your code correctly delimiting your output (i.e. using spaces) and
    verifies that your k-mers are actually of length k .
     */
    @Test
    @DisplayName( "test frequent words problem including length k")
    fun testFrequentWordsProblemWithReverseLengthK() {

        val g = "TAGCG"
        val k = 2 // kmer length
        val d = 1 // hamming distance

        val expectedResult = "CA CC GG TG "
        val matchList = frequentWordsWithMismatches(g, k, d, scanReverseComplements = true).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }



}