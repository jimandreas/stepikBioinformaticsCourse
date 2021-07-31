@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.AlignmentLinearSpace

/**
 * Code Challenge: Solve the Middle Edge in Linear Space Problem (for protein strings).

Input: Two amino acid strings.
Output: A middle edge in the alignment graph in the form "(i, j) (k, l)", where (i, j) connects to (k, l).

 * See also:
 * stepik: @link:
 * https://stepik.org/lesson/240308/step/12?unit=212654  MiddleEdge problem
 * https://stepik.org/lesson/240308/step/14?unit=212654  LinearSpaceAlignment
 * rosalind: @link:
 * http://rosalind.info/problems/ba5k/  MiddleEdge
 * http://rosalind.info/problems/ba5l/  LinearSpaceAlignment
 * book (5.13): @link: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 * youtube: @link: https://www.youtube.com/watch?v=3TfDm8GpWRU  Space-Efficient Sequence Alignment
 */

internal class S05C13c14AlignmentLinearSpace {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }


    /**
     * TEST DATASET 1:
     * This test makes sure that your code can handle runs of indels in the reconstructed alignment.
     */
    @Test
    @DisplayName("linear space alignment test 01")
    fun findMiddleEdgeTest01() {

        val sample = """
            Input:
            1 5 1
            TT
            CC
            Output:
            -4
            --TT
            CC--

        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 2:
     * This test makes sure that your code correctly mismatches characters when the ideal alignment requires it.
     */
    @Test
    @DisplayName("linear space alignment test 02")
    fun findMiddleEdgeTest02() {

        val sample = """
            Input:
            1 1 5
            TT
            CC
            Output:
            -2
            TT
            CC
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 3:
     * This test makes sure that your code correctly aligns the
     * upper and lower sub-matrices after recursive calls.
     */
    @Test
    @DisplayName("linear space alignment test 03")
    fun findMiddleEdgeTest03() {

        val sample = """
            Input:
            1 5 1
            GAACGATTG
            GGG
            Output:
            -3
            GAACGATTG
            G---G---G
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 4:
     * This test makes sure that your code correctly handles
     * inputs in which the match score is not equal to one.
     */

    @Test
    @DisplayName("linear space alignment test 04")
    fun findMiddleEdgeTest04() {

        val sample = """
            Input:
            2 3 1
            GCG
            CT
            Output:
            -1
            GCG-
            -C-T
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 5:
     * This test makes sure that your code correctly handles
     * inputs in which string t is one character long.
     */

    @Test
    @DisplayName("linear space alignment test 05")
    fun findMiddleEdgeTest05() {

        val sample = """
            Input:
            1 2 3
            ACAGCTA
            G
            Output:
            -17
            ACAGCTA
            ---G---
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 6:
     * This test makes sure that your code correctly
     * handles inputs in which string s is one character long.
     */

    @Test
    @DisplayName("linear space alignment test 06")
    fun findMiddleEdgeTest06() {

        val sample = """
            Input:
            3 4 1
            A
            CGGAGTGCC
            Output:
            -5
            ---A-----
            CGGAGTGCC
        """.trimIndent()

        runTest(sample)
    }

    /**
     * SAMPLE PROBLEM
     */
    @Test
    @DisplayName("sample dataset from word file")
    fun findMiddleEdgeSampleTest() {

        val sample = """
            Input:
            1 1 2
            GAGA
            GAT
            Output:
            -1
            GAGA
            GA-T
        """.trimIndent()

        runTest(sample)
    }

    /**
    SAMPLE GIVEN DATASET PROBLEM

     */
    @Test
    @DisplayName("sample dataset extra")
    fun findMiddleEdgeGivenProblemTest() {

        val sample = """
            Input:
            0 0 5
            PLEASANTLY
            MEANLY
            Output:
            8
            PLEASANTLY
            -MEA--N-LY
        """.trimIndent()

        runTest(sample, true)
    }

    /**
    Extra Dataset

     */
/*    @Test
    @DisplayName("extra dataset extra")
    fun findMiddleEdgeExtraDatasetTest() {

        val sample = """
            Input:
            0 0 5
            LWFKFLQCIFQYFKDQQETNCIWTFSPFSEHICQRVCQVYWNWNTPSSRTSDPRELFANSTIHNNRCGEWRYMFYHTRTLVQTAPLMKETLHSDGKHSMYCEQRHFFRSSYLIKVNYDVSHYLELYTFSEIPWKLTTHGWDGFSWFLLVNSCCTFDIDGKCGILSQCGMSRAFRTRQEDAYHFQTSLMHLHLHLHVQEGKHEKADLFAQFYNMLPMHGGTCGRNTEPSDLFDSATMNKYMAEHPASCKACPNVSKECFVYWWSHDFTKKHKLIEFSCGRDTGQTTQRTWNVDENEGGKWIWRFHYFMRAKALQIDPKFKPYWNEPRAIMRPGHVTAAPCICAQHSQNETAVCNRDQMHIHAIEFQQYHSRAFGEVQTWCDIGKENENDFIYEQHWWLVGGTEGMAGVIWKFVCARCRTQDCDFWKTCLTYSAQPMMKVYDTIFYVNSINPWEFEDHPSQCDKCVQSIPTDAKYAICGKFVISHWLYWTPQKFEECVHNNVRCAPMGNRLWGTACMVIQNVWLRPSMGSHFSCILNVGGSNINIQGKETWTHVPILHMHEIDLISTASSGMETCKPCFLSGPTIHMGFSYEIRAQPYSRDYFCMDWMQEADEVDHNRCETVQPTLPLLQQFEWKTSCMGQRWITIFCDHCQIVCFSTFFCVMPTFLPNTSILDKFYCIYLSISWTHYCNVHALGFIMRLHYSYMGWKEHKRMHAWDIGLDELWAQEGIQRAQLWCGDEFEVAKYPEWITEARTAIATRPWFHNCYIKPWWIREKHLWFGKESKLDHGHRGAMFTPVANDNTEWMHHWYMFCWAGSKNRLKRQIKEKLIFIIKFMITEFGLFLMIDYTQCYIAWMWAYTGIACYIDWEKCLKHDLTTTDLGCCVYRLFKWYEVRHRAPPQVNTRLPWSQIPMVAIQCNIVDECKEQWHFSYKASFVVEYLCPGCCTNGNRWQWYQVKETPFMYAFAASIFGFHHENLVVFITGSVTIPNGLFGCIAWTSPKPVQKTPASANTIIAYDKCILMG
            TWLNSACYGVNFRRLNPMNKTKWDCWTWVPMVMAAQYLCRIFIPVMDHWEFFGDWGLETWRLGIHDHVKIPNFRWSCELHIREHGHHFKTRFLKHNQFTQCYGLMPDPQFHRSYDVACQWEVTMSQGLMRFHRQNQIEKQRDRTSTYCMMTIGPGFTSNGYDPFVTITITPVQEPVENWFTPGGSMGFMIISRYMQMFFYLTRFSDMTYLVGVHCENYVCWNNVAKFLNGNLQGIFDQGERAYHQFVTWHSYSQYSRCSVGRYACEQAMSRVNSKMTWHWPIRDQGHEHFSEQYLSEKRNPPCNPRIGNAGQHFYEIHRIAHRVAMCNWAPQGQHPGGPTPHDVETCLWLWSLCLKGSDRGYVDRPWMFLADQLGEANLTLITMFHGCTRGCLMWFMDWEECVCSYSVVNPRCHGSEQWSVQNLGWRTCDTLISLWEPECDKHNTPPCLHWEFEDHPSQLRPVMMCDKYVQSIPTDAKWAWTYSKDFVISHWLIWTPIKLEECVFPQINRLWGTACNQGSQKIVIQNVWLRPSSFFQERSKCSDSSCILNVGGSNVNITGKETRTHVPILHMHEIDLISTASSGMRHNLILPHGMLMLHMNWHHSTRAMNPYSSLKLIPWTFQVCETDDRDQNVATHVADPCHKGEDQEIRCCKGGVDHQWKGDRMWMMCMPDMNYVKQDQAPSGTCEGACENYPADKDKCYMIFTIVFDYRRCTKKVCIWISGFPVDAFNLISIANAGFFCCWLEPTELKWRRTFYLGKGTQGWMCTFPHRNIIPVIICAGFGRWVQGEVPFRPVAQISAHSSDRRQGHHPPGTNMCHDYGDQYPIKRVGMQVEEDDGASYCDCAADWKLADMYEADHLSIGVIDFTDWIYPKNGGIWSEIIKSHFHWYHWETPQNTVGAFNTIVGINGSDMCIYHGNTQWEFGWCWKWLNHGHMRNQGPCHLGILEGRISKFAQVTSWWWQTKHDKDWSIEPYGRHWGEAGRPYTYNYCWMRWAIVYNHGNVISVELVPFMDEYPGKCNKEDVQFELFSPMQA
            Output:
            (512, 510) (513, 511)
        """.trimIndent()

        runTest(sample, true)
    }*/

    fun runTest(sample: String, useBLOSUM62: Boolean = false) {
        val reader = sample.reader()
        val lines = reader.readLines()
        val parms = lines[1].split(" ").map { it.toInt() }
        val match = parms[0]
        val mismatch = parms[1]
        val gap = parms[2]

        val linear = AlignmentLinearSpace(match, mismatch, gap, useBLOSUM62)

        val sRow = lines[2]
        val tCol = lines[3]
        val result = linear.alignmentLinearSpace(sRow, tCol)

        val sRowAlignedExpected = lines[6]
        val tColAlignedExpected = lines[7]
        val sRowResult = result.second
        val tColResult = result.third

        println("RowE: $sRowAlignedExpected")
        println("RowR: $sRowResult")
        println("ColE: $tColAlignedExpected")
        println("ColR: $tColResult")

        val scoreExpected = lines[5].toInt()
        val scoreResult = result.first

        assertEquals(sRowAlignedExpected, sRowResult)
        assertEquals(tColAlignedExpected, tColResult)

        assertEquals(scoreExpected, scoreResult)

    }

}