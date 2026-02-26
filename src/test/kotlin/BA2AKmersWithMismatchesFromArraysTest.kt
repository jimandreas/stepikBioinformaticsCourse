import algorithms.motifEnumeration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**

2.2 Motif Finding Is More Difficult Than You Think
7 out of 10 steps passed
0 out of 10 points  received

Code Challenge: Implement MotifEnumeration (reproduced below).

Input: Integers k and d, followed by a collection of strings Dna.
Output: All (k, d)-motifs in Dna.

MotifEnumeration(Dna, k, d)
Patterns ← an empty set
for each k-mer Pattern in Dna
for each k-mer Pattern’ differing from Pattern by at most d mismatches
if Pattern' appears in each string from Dna with at most d mismatches
add Pattern' to Patterns
remove duplicates from Patterns
return Patterns

 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba2a/
 */

internal class BA2AKmersWithMismatchesFromArraysTest {

    /**
     *   Sample Input:

    3 1
    ATTTGGC
    TGCCTTA
    CGGTATC
    GAAAATT

    Sample Output:

    ATA ATT GTT TTT
     */
    @Test
    @DisplayName( "test motif enumeration 1")
    fun testMotifEnumerationShortInput() {

        val g = listOf(
            "ATTTGGC",
            "TGCCTTA",
            "CGGTATC",
            "GAAAATT"
            )
        val k = 3 // kmer length
        val d = 1 // hamming distance

        val expectedResult = "ATA ATT GTT TTT "
        val matchList = motifEnumeration(g, k, d).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }

    @Test
    @DisplayName( "test motif enumeration extra dataset")
    fun testMotifEnumerationExtraDataset() {

        val g = listOf(
            "TCTGAGCTTGCGTTATTTTTAGACC",
            "GTTTGACGGGAACCCGACGCCTATA",
            "TTTTAGATTTCCTCAGTCCACTATA",
            "CTTACAATTTCGTTATTTATCTAAT",
            "CAGTAGGAATAGCCACTTTGTTGTA",
            "AAATCCATTAAGGAAAGACGACCGT"

        )
        val k = 5 // kmer length
        val d = 2 // hamming distance

        val expectedResult = "AAACT AAATC AACAC AACAT AACCT AACTA AACTC AACTG AACTT AAGAA AAGCT AAGGT AAGTC AATAC AATAT AATCC AATCT AATGC AATTC AATTG ACAAC ACACA ACACC ACACG ACACT ACAGA ACAGC ACATC ACATG ACCAT ACCCT ACCGT ACCTA ACCTC ACCTG ACCTT ACGAC ACGAG ACGAT ACGCT ACGGT ACGTC ACGTT ACTAA ACTAG ACTAT ACTCA ACTCC ACTCG ACTCT ACTGA ACTGC ACTGT ACTTA ACTTC ACTTT AGAAA AGAAC AGAAG AGAAT AGACA AGACT AGATA AGATC AGCAT AGCCA AGCGT AGCTA AGCTC AGCTG AGCTT AGGAT AGGTA AGGTC AGTAA AGTAC AGTAT AGTCC AGTCG AGTCT AGTGA AGTTG ATAAA ATAAC ATACA ATACC ATAGA ATATA ATATC ATATG ATATT ATCAG ATCCC ATCCG ATCCT ATCGA ATCGC ATCTA ATCTC ATCTG ATGAC ATGAT ATGCA ATGCC ATGGA ATGGC ATGTA ATGTC ATTAA ATTAC ATTAG ATTAT ATTCA ATTCC ATTCG ATTGA ATTGC ATTGG ATTGT ATTTA ATTTC ATTTG ATTTT CAAAG CAACC CAACT CAAGA CAAGC CAATA CAATT CACAC CACAG CACCT CACGT CACTA CACTT CAGAA CAGAC CAGAT CAGGT CAGTA CAGTC CATAA CATAC CATAG CATAT CATCC CATCT CATGA CATGT CATTA CATTG CATTT CCAAG CCATA CCATG CCATT CCCGT CCCTA CCCTT CCGAA CCGAC CCGAT CCGCT CCGGT CCGTA CCGTC CCGTG CCGTT CCTAC CCTAT CCTCA CCTCC CCTTA CCTTC CCTTG CCTTT CGAAA CGAAG CGACA CGACT CGAGT CGATA CGATG CGATT CGCAA CGCAT CGCCA CGCGA CGCTA CGCTC CGCTT CGGAC CGGAT CGGCA CGGTA CGGTC CGGTT CGTAA CGTAC CGTCA CGTCG CGTCT CGTTA CGTTT CTAAC CTAAG CTAAT CTACA CTACC CTACG CTACT CTAGA CTAGC CTAGG CTAGT CTATA CTATC CTATG CTATT CTCAT CTCCG CTCGT CTCTA CTCTT CTGAA CTGAG CTGCA CTGCC CTGTA CTGTT CTTAA CTTAC CTTAG CTTAT CTTCA CTTGA CTTTA CTTTC CTTTG CTTTT GAAAT GAACA GAACT GAAGT GAATG GAATT GACAC GACAT GACCA GACCT GACGT GACTT GAGAA GAGAT GAGCT GATAA GATAC GATAG GATAT GATCA GATCC GATCG GATCT GATGT GATTA GATTC GATTG GATTT GCAAT GCACT GCATC GCATT GCCAT GCCGT GCCTA GCCTT GCGAT GCGGT GCGTC GCGTT GCTAA GCTAC GCTAG GCTAT GCTGA GCTGT GCTTA GCTTT GGAAT GGACA GGATA GGATC GGATT GGCTA GGGAT GGTAC GGTAG GGTAT GGTCA GGTCG GGTTA GTAAA GTAAG GTACA GTACC GTACG GTAGA GTATA GTATC GTATG GTATT GTCAA GTCAG GTCCG GTCCT GTCGA GTCGC GTCGT GTCTA GTCTG GTGAA GTGAG GTGCA GTGCG GTTAA GTTAC GTTAG GTTAT GTTCA GTTCC GTTCG GTTGA GTTTA TAAAC TAAAG TAACA TAACC TAACT TAAGA TAAGC TAATA TAATC TACAC TACAG TACCC TACCG TACCT TACGA TACGC TACGT TACTA TACTC TACTG TAGAA TAGAC TAGAG TAGAT TAGCC TAGCG TAGGA TAGTC TATAA TATAC TATAT TATCA TATCC TATCG TATGA TATGC TATGG TATGT TATTA TATTG TCAAC TCAAT TCACC TCACG TCACT TCAGA TCATA TCATG TCCAA TCCAC TCCAG TCCAT TCCCA TCCCT TCCGA TCCGC TCCGT TCCTA TCCTG TCCTT TCGAA TCGAC TCGAT TCGCC TCGCT TCGGA TCGGC TCGGG TCGGT TCGTC TCTAC TCTAG TCTAT TCTCC TCTCT TCTGG TCTGT TCTTA TCTTT TGAAA TGAAC TGAAT TGACA TGACC TGACT TGAGA TGAGC TGAGT TGATA TGATC TGATG TGATT TGCAA TGCAC TGCAG TGCAT TGCCA TGCCG TGCCT TGCGA TGCGT TGCTT TGGAA TGGAT TGGTA TGTAA TGTAG TGTAT TGTCC TGTCG TGTGG TGTTA TTAAA TTAAC TTAAG TTAAT TTACA TTACC TTACG TTACT TTAGA TTAGC TTAGG TTAGT TTATA TTATC TTATG TTATT TTCAA TTCAC TTCAT TTCCA TTCCC TTCCT TTCGA TTCGG TTCGT TTCTA TTCTG TTGAA TTGAC TTGAG TTGAT TTGCA TTGCG TTGGA TTGGG TTGTG TTTAA TTTAC TTTAG TTTAT TTTCA TTTCC TTTCG TTTGA TTTGG TTTTA TTTTG "
        val matchList = motifEnumeration(g, k, d).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }

    /**
     * TEST DATASET 1:
    Input :
    3 0
    ACGT
    ACGT
    ACGT
    Output :
    ACG CGT
    This dataset checks for offbyone
    errors, both at the beginning and at the end. The
    3mers
    “ACG” and “CGT” both appear perfectly in all 3 strings in Dna. Thus, if your output
    doesn’t contain “ACG”, you are most likely not counting the first kmer
    of every string.
    Similarly, if your output doesn’t contain “CGT”, you are most likely not counting the last kmer
    of every string.
     */
    @Test
    @DisplayName( "test motif enumeration off by one")
    fun testMotifEnumerationOffByOne() {

        val g = listOf(
            "ACGT",
            "ACGT",
            "ACGT"
        )
        val k = 3 // kmer length
        val d = 0 // hamming distance

        val expectedResult = "ACG CGT "
        val matchList = motifEnumeration(g, k, d).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }

    /**
     * TEST DATASET 2:
    Input :
    3 1
    AAAAA
    AAAAA
    AAAAA
    Output :
    AAA AAC AAG AAT ACA AGA ATA CAA GAA TAA
    This dataset checks if your code work correctly when d > 0. If your code only counts
    motifs with d = 0 (and not d > 0), your code will only find a single motif (“AAA”, which is the
    only 3mer
    that occurs perfectly in all of the strings of Dna). A correct solution would, in
    addition to “AAA”, find all 3mers
    that differ from “AAA” by exactly one base.
     */

    @Test
    @DisplayName( "test motif enumeration hamming distance")
    fun testMotifEnumerationHammingDistance() {

        val g = listOf(
            "AAAAA",
            "AAAAA",
            "AAAAA"

        )
        val k = 3 // kmer length
        val d = 1 // hamming distance

        val expectedResult = "AAA AAC AAG AAT ACA AGA ATA CAA GAA TAA "
        val matchList = motifEnumeration(g, k, d).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }

    /**
     * TEST DATASET 3:
    Input :
    3 3
    AAAAA
    AAAAA
    AAAAA
    Output :
    AAA AAC AAG AAT ACA ACC ACG ACT AGA AGC AGG AGT ATA ATC ATG ATT
    CAA CAC CAG CAT CCA CCC CCG CCT CGA CGC CGG CGT CTA CTC CTG CTT
    GAA GAC GAG GAT GCA GCC GCG GCT GGA GGC GGG GGT GTA GTC GTG GTT
    TAA TAC TAG TAT TCA TCC TCG TCT TGA TGC TGG TGT TTA TTC TTG TTT
    This dataset checks if your code counts motifs where the number of mismatches is equal
    to d in addition to motifs where the number of mismatches is less than d. For example, in this
    dataset, a correct solution would find ALL 3mers
    (because we are allowing for 3 mismatches).
    However, an incorrect solution that counts mismatches less than d but not mismatches equal to d
    would only find the kmers
    that differ from “AAA” by 1 or 2 bases, not the ones that differ from
    “AAA” by 3 bases.
     */
    @Test
    @DisplayName( "test motif enumeration mismatch count")
    fun testMotifEnumerationMismatchCount() {

        val g = listOf(
            "AAAAA",
            "AAAAA",
            "AAAAA"

        )
        val k = 3 // kmer length
        val d = 3 // hamming distance

        val expectedResult = "AAA AAC AAG AAT ACA ACC ACG ACT AGA AGC AGG AGT ATA ATC ATG ATT CAA CAC CAG CAT CCA CCC CCG CCT CGA CGC CGG CGT CTA CTC CTG CTT GAA GAC GAG GAT GCA GCC GCG GCT GGA GGC GGG GGT GTA GTC GTG GTT TAA TAC TAG TAT TCA TCC TCG TCT TGA TGC TGG TGT TTA TTC TTG TTT "
        val matchList = motifEnumeration(g, k, d).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }


    @Test
    @DisplayName( "test motif enumeration verify error case")
    fun testMotifEnumerationVerifyErrorCase() {

        val g = listOf(
            "AAAAA",
            "AAAAA",
            "AACAA"

        )
        val k = 3 // kmer length
        val d = 0 // hamming distance

        val expectedResult = ""
        val matchList = motifEnumeration(g, k, d).sorted()

        var result = ""
        for (i in matchList) {
            result += "$i "
        }
        assertEquals(expectedResult, result)

    }



}