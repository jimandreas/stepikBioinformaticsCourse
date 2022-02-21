@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.HiddenMarkovModelsHMMProfile
import algorithms.HiddenMarkovModelsHMMProfile.HMMTransitionAndEmissionMatrices
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.BufferedWriter
import java.io.File
import java.lang.StringBuilder
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


internal class S10C09P05HiddenMarkovProfileWithPseudocountsQuizTest {

    lateinit var hmmp: HiddenMarkovModelsHMMProfile

    @BeforeEach
    fun setUp() {
        hmmp = HiddenMarkovModelsHMMProfile()
        //hmmp.debugOutput = true
    }

    /**
     * https://stepik.org/lesson/240403/step/5?unit=212749
     * https://rosalind.info/problems/ba10f/
     */

    /**

    Profile HMM with Pseudocounts Problem

    Given: A threshold θ and a pseudocount σ, followed by an alphabet Σ,
    followed by a multiple alignment Alignment whose strings are formed from Σ.

    Return: The transition and emission probabilities of the profile HMM HMM(Alignment, θ, σ).
     */

    @Test
    @DisplayName("Profile Alignment Test Extra Dataset")
    fun profileAlignmentExtraDataset() {
        val inputData = """
            0.399	0.01
            --------
            A B C D E
            --------
            ED-BCBDAC
            -D-ABBDAC
            ED--EBD-C
            -C-BCB-D-
            AD-BC-CA-
            -DDB-BA-C
        """.trimIndent()

        val expectedResultsString = """
	S	I0	M1	D1	I1	M2	D2	I2	M3	D3	I3	M4	D4	I4	M5	D5	I5	M6	D6	I6	M7	D7	I7	E
S	0	0.495	0.495	0.01	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I0	0	0.01	0.981	0.01	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M1	0	0	0	0	0.172	0.657	0.172	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
D1	0	0	0	0	0.333	0.333	0.333	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I1	0	0	0	0	0.01	0.981	0.01	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M2	0	0	0	0	0	0	0	0.01	0.786	0.204	0	0	0	0	0	0	0	0	0	0	0	0	0	0
D2	0	0	0	0	0	0	0	0.01	0.981	0.01	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I2	0	0	0	0	0	0	0	0.333	0.333	0.333	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M3	0	0	0	0	0	0	0	0	0	0	0.01	0.786	0.204	0	0	0	0	0	0	0	0	0	0	0
D3	0	0	0	0	0	0	0	0	0	0	0.01	0.981	0.01	0	0	0	0	0	0	0	0	0	0	0
I3	0	0	0	0	0	0	0	0	0	0	0.333	0.333	0.333	0	0	0	0	0	0	0	0	0	0	0
M4	0	0	0	0	0	0	0	0	0	0	0	0	0	0.01	0.786	0.204	0	0	0	0	0	0	0	0
D4	0	0	0	0	0	0	0	0	0	0	0	0	0	0.01	0.981	0.01	0	0	0	0	0	0	0	0
I4	0	0	0	0	0	0	0	0	0	0	0	0	0	0.333	0.333	0.333	0	0	0	0	0	0	0	0
M5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.01	0.592	0.398	0	0	0	0	0
D5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.01	0.981	0.01	0	0	0	0	0
I5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.333	0.333	0.333	0	0	0	0	0
M6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.01	0.495	0.495	0	0
D6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.01	0.981	0.01	0	0
I6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.333	0.333	0.333	0	0
M7	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.01	0.99
D7	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.01	0.99
I7	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.5	0.5
E	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
--------
	A	B	C	D	E
S	0	0	0	0	0
I0	0.327	0.01	0.01	0.01	0.644
M1	0.01	0.01	0.168	0.803	0.01
D1	0	0	0	0	0
I1	0.01	0.01	0.01	0.962	0.01
M2	0.2	0.771	0.01	0.01	0.01
D2	0	0	0	0	0
I2	0.2	0.2	0.2	0.2	0.2
M3	0.01	0.2	0.581	0.01	0.2
D3	0	0	0	0	0
I3	0.2	0.2	0.2	0.2	0.2
M4	0.01	0.962	0.01	0.01	0.01
D4	0	0	0	0	0
I4	0.2	0.2	0.2	0.2	0.2
M5	0.2	0.01	0.2	0.581	0.01
D5	0	0	0	0	0
I5	0.2	0.2	0.2	0.2	0.2
M6	0.724	0.01	0.01	0.248	0.01
D6	0	0	0	0	0
I6	0.2	0.2	0.2	0.2	0.2
M7	0.01	0.01	0.962	0.01	0.01
D7	0	0	0	0	0
I7	0.2	0.2	0.2	0.2	0.2
E	0	0	0	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )


//        val outputMessagesFilePath = "zzhiddenMarkov.txt"
//
//        val outFile = File(outputMessagesFilePath)
//        val writer = outFile.bufferedWriter()
//
//        uglyPrintTransitionMatrix(writer)
//        writer.write("--------\n")
//        uglyPrintEmissionsMatrix(writer)
//        writer.close()

        checkResultingOutputDataStructure(expectedStruct, result)
    }

    @Test
    @DisplayName("Profile Alignment Test Rosalind Quiz Dataset")
    fun profileAlignmentRosalindQuizDataset() {
        val inputData = """
            0.391	0.01
            --------
            A	B	C	D	E
            --------
            C-BAB-E-E
            EDDE--EE-
            E--ABBEEE
            ED-A-BDEE
            DED-BBDDE
            -DDA-BEEE
        """.trimIndent()

        val expectedResultsString = """
	S	I0	M1	D1	I1	M2	D2	I2	M3	D3	I3	M4	D4	I4	M5	D5	I5	M6	D6	I6	M7	D7	I7	M8	D8	I8	E
S	0	0.009708737864077669	0.8187702265372169	0.1715210355987055	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I0	0	0.3333333333333333	0.3333333333333333	0.3333333333333333	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M1	0	0	0	0	0.009708737864077669	0.5922330097087378	0.3980582524271845	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
D1	0	0	0	0	0.009708737864077669	0.9805825242718447	0.009708737864077669	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I1	0	0	0	0	0.3333333333333333	0.3333333333333333	0.3333333333333333	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M2	0	0	0	0	0	0	0	0.009708737864077669	0.7378640776699029	0.2524271844660194	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
D2	0	0	0	0	0	0	0	0.009708737864077669	0.49514563106796117	0.49514563106796117	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I2	0	0	0	0	0	0	0	0.3333333333333333	0.3333333333333333	0.3333333333333333	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M3	0	0	0	0	0	0	0	0	0	0	0.009708737864077669	0.7378640776699029	0.2524271844660194	0	0	0	0	0	0	0	0	0	0	0	0	0	0
D3	0	0	0	0	0	0	0	0	0	0	0.009708737864077669	0.9805825242718447	0.009708737864077669	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I3	0	0	0	0	0	0	0	0	0	0	0.3333333333333333	0.3333333333333333	0.3333333333333333	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M4	0	0	0	0	0	0	0	0	0	0	0	0	0	0.3980582524271845	0.3980582524271845	0.2038834951456311	0	0	0	0	0	0	0	0	0	0	0
D4	0	0	0	0	0	0	0	0	0	0	0	0	0	0.9805825242718447	0.009708737864077669	0.009708737864077669	0	0	0	0	0	0	0	0	0	0	0
I4	0	0	0	0	0	0	0	0	0	0	0	0	0	0.009708737864077669	0.656957928802589	0.3333333333333333	0	0	0	0	0	0	0	0	0	0	0
M5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.009708737864077669	0.9805825242718447	0.009708737864077669	0	0	0	0	0	0	0	0
D5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.009708737864077669	0.9805825242718447	0.009708737864077669	0	0	0	0	0	0	0	0
I5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.3333333333333333	0.3333333333333333	0.3333333333333333	0	0	0	0	0	0	0	0
M6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.009708737864077669	0.8187702265372169	0.1715210355987055	0	0	0	0	0
D6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.3333333333333333	0.3333333333333333	0.3333333333333333	0	0	0	0	0
I6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.3333333333333333	0.3333333333333333	0.3333333333333333	0	0	0	0	0
M7	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.009708737864077669	0.7864077669902912	0.2038834951456311	0	0
D7	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.009708737864077669	0.9805825242718447	0.009708737864077669	0	0
I7	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.3333333333333333	0.3333333333333333	0.3333333333333333	0	0
M8	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.00980392156862745	0.9901960784313726
D8	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.00980392156862745	0.9901960784313726
I8	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.5	0.5
E	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
--------
	A	B	C	D	E
S	0	0	0	0	0
I0	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998
M1	0.009523809523809523	0.009523809523809523	0.2	0.2	0.5809523809523809
D1	0	0	0	0	0
I1	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998
M2	0.009523809523809523	0.009523809523809523	0.009523809523809523	0.7238095238095238	0.24761904761904763
D2	0	0	0	0	0
I2	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998
M3	0.009523809523809523	0.24761904761904763	0.009523809523809523	0.7238095238095238	0.009523809523809523
D3	0	0	0	0	0
I3	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998
M4	0.7714285714285715	0.009523809523809523	0.009523809523809523	0.009523809523809523	0.2
D4	0	0	0	0	0
I4	0.009523809523809523	0.9619047619047618	0.009523809523809523	0.009523809523809523	0.009523809523809523
M5	0.009523809523809523	0.9619047619047618	0.009523809523809523	0.009523809523809523	0.009523809523809523
D5	0	0	0	0	0
I5	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998
M6	0.009523809523809523	0.009523809523809523	0.009523809523809523	0.326984126984127	0.6444444444444444
D6	0	0	0	0	0
I6	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998
M7	0.009523809523809523	0.009523809523809523	0.009523809523809523	0.2	0.7714285714285715
D7	0	0	0	0	0
I7	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998
M8	0.009523809523809523	0.009523809523809523	0.009523809523809523	0.009523809523809523	0.9619047619047618
D8	0	0	0	0	0
I8	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998	0.19999999999999998
E	0	0	0	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )


//        val outputMessagesFilePath = "zzhiddenMarkov.txt"
//
//        val outFile = File(outputMessagesFilePath)
//        val writer = outFile.bufferedWriter()
//
//        uglyPrintTransitionMatrix(writer)
//        writer.write("--------\n")
//        uglyPrintEmissionsMatrix(writer)
//        writer.close()

        checkResultingOutputDataStructure(expectedStruct, result)
    }


    @Test
    @DisplayName("Profile Alignment Test Stepik Quiz Dataset")
    fun profileAlignmentStepikQuizDataset() {
        val inputData = """
            0.219 0.01
            --------
            A B C D E
            --------
            DDC
            BE-
            ---
            BE-
            BED
            BE-
            BE-
            -ED
            BED
        """.trimIndent()

        val expectedResultsString = """
	S	I0	M1	D1	I1	E
S	0	0.7648327939590076	0.11758360302049621	0.11758360302049621	0	0
I0	0	0.009708737864077669	0.9805825242718447	0.009708737864077669	0	0
M1	0	0	0	0	0.5	0.5
D1	0	0	0	0	0.00980392156862745	0.9901960784313726
I1	0	0	0	0	0.00980392156862745	0.9901960784313726
E	0	0	0	0	0	0
--------
	A	B	C	D	E
S	0	0	0	0	0
I0	0.009523809523809523	0.8258503401360543	0.009523809523809523	0.145578231292517	0.009523809523809523
M1	0.009523809523809523	0.009523809523809523	0.009523809523809523	0.1285714285714286	0.8428571428571429
D1	0	0	0	0	0
I1	0.009523809523809523	0.009523809523809523	0.24761904761904763	0.7238095238095238	0.009523809523809523
E	0	0	0	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )


//        val outputMessagesFilePath = "zzhiddenMarkov.txt"
//
//        val outFile = File(outputMessagesFilePath)
//        val writer = outFile.bufferedWriter()
//
//        uglyPrintTransitionMatrix(writer)
//        writer.write("--------\n")
//        uglyPrintEmissionsMatrix(writer)
//        writer.close()

        checkResultingOutputDataStructure(expectedStruct, result)
    }

    /****************************************************
     * Internal parsing validation
     * ***************************************************
     */
    @Test
    @DisplayName("test input string parsing with pseudocount sigma")
    fun inputStringParsingTest() {
        val dataFormat = """
            0.5 0.01
            --------
            A B
            --------
            AA-
            --A
            --B
    """.trimIndent()

        val d = createFromInputString(dataFormat.lines().toMutableList())
        assertEquals(0.5, d.threshold, 0.001)
        assertEquals(0.01, d.pseudocountSigma, 0.001)
        assertContentEquals(listOf('A', 'B'), d.statesCharList)
        assertContentEquals(
            listOf(
                "AA-",
                "--A",
                "--B"
            ), d.alignmentStringList
        )

    }

    data class InputData(
        var threshold: Double,
        var pseudocountSigma: Double,
        var statesCharList: List<Char>,
        var alignmentStringList: List<String>
    )


    /**
     * test the parsing and checking of expected results
     */
    @Test
    @DisplayName("test output string parsing")
    fun outputStringParsingTest() {

        // this is a sample expected result
        val testStringToParse = """
	S	I0	M1	D1	I1	E
S	0	0.333	0.667	0	0	0
I0	0	0	0	1.0	0	0
M1	0	0	0	0	0	1.0
D1	0	0	0	0	0	1.0
I1	0	0	0	0	0	0
E	0	0	0	0	0	0
--------
	A	B
S	0	0
I0	1.0	0
M1	0.5	0.5
D1	0	0
I1	0	0
E	0	0
        """.trimIndent()
        val symbolList = listOf('A', 'B')

        // parse it
        val parseExpectedResult = createExpectedOutputDataStructure(symbolList, testStringToParse)

        // create a result struct as if it came from the algorithm
        val states = listOf("S", "I0", "M1", "D1", "I1", "E")
        val t = listOf(
            listOf(0.0, 0.333, 0.667, 0.0, 0.0, 0.0),
            listOf(0.0, 0.0, 0.0, 1.0, 0.0, 0.0),
            listOf(0.0, 0.0, 0.0, 0.0, 0.0, 1.0),
            listOf(0.0, 0.0, 0.0, 0.0, 0.0, 1.0),
            listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        )
        val e = listOf(
            listOf(0.0, 0.0),
            listOf(1.0, 0.0),
            listOf(0.5, 0.5),
            listOf(0.0, 0.0),
            listOf(0.0, 0.0),
            listOf(0.0, 0.0)
        )

        val resultStruct = HMMTransitionAndEmissionMatrices(
            symbols = symbolList,
            states = states,
            transitions = t,
            emissions = e
        )

        // these should be the same
        checkResultingOutputDataStructure(parseExpectedResult, resultStruct)
    }

    private fun checkResultingOutputDataStructure(
        expectedStruct: HMMTransitionAndEmissionMatrices,
        resultStruct: HMMTransitionAndEmissionMatrices
    ) {

        assertContentEquals(expectedStruct.symbols, resultStruct.symbols)

        // check transitions
        for (rowIndex in 0 until expectedStruct.transitions.size) {
            val rowExpected = expectedStruct.transitions[rowIndex]
            val rowResult = resultStruct.transitions[rowIndex]
            for (col in 0 until rowExpected.size) {
                assertEquals(
                    rowExpected[col],
                    rowResult[col], 0.01,
                    "Transitions matrix mismatch Row: $rowIndex Col: $col"
                )
            }
        }

        // check emissions
        for (rowIndex in 0 until expectedStruct.emissions.size) {
            val rowExpected = expectedStruct.emissions[rowIndex]
            val rowResult = resultStruct.emissions[rowIndex]
            for (col in 0 until rowExpected.size) {
                assertEquals(
                    rowExpected[col],
                    rowResult[col], 0.01,
                    "Emissions matrix mismatch Row: $rowIndex Col: $col"
                )
            }
        }

    }

    private fun createExpectedOutputDataStructure(symbolList: List<Char>, stringToParse: String)
            : HMMTransitionAndEmissionMatrices {

        val l = stringToParse.lines().toMutableList()
        val ws = "\\s+".toRegex() // white space regular expression
        val transitionsNameList = l.removeFirst().split(ws).toMutableList()
        if (transitionsNameList[0] == "") {
            transitionsNameList.removeFirst()
        }

        if (transitionsNameList[transitionsNameList.size - 1] == "") {
            transitionsNameList.removeLast()
        }
        //println("Transitions count: ${transitionsNameList.size}")

        val transitionsList: MutableList<List<Double>> = mutableListOf()
        for (i in 0 until transitionsNameList.size) {
            val lineList = l.removeFirst().split(ws).toMutableList()
            //println("size should be ${transitionsNameList.size + 1} and is ${lineList.size}")
            val first = lineList.removeFirst()
            //println("$first should be equal to ${transitionsNameList[i]}")
            val valueList = lineList.map { it.toDouble() }
            transitionsList.add(valueList)
        }

        // remove "----" separator
        l.removeFirst()

        // parse the names of the symbols - should be equaly to symbolList

        val symbolNameList = l.removeFirst().split(ws).toMutableList()
        if (symbolNameList[0] == "") {
            symbolNameList.removeFirst()
        }
        //println("symbol count: ${symbolNameList.size} should be equal to ${symbolList.size}")

        // now parse the emissions matrix
        val emisssionsList: MutableList<List<Double>> = mutableListOf()
        for (i in 0 until transitionsNameList.size) {
            val lineList = l.removeFirst().split(ws).toMutableList()
            //println("size should be ${symbolNameList.size + 1} and is ${lineList.size}")
            val first = lineList.removeFirst()
            //println("$first should be equal to ${transitionsNameList[i]}")
            val valueList = lineList.map { it.toDouble() }
            emisssionsList.add(valueList)
        }

        return HMMTransitionAndEmissionMatrices(
            symbolList,
            transitionsNameList,
            transitionsList,
            emisssionsList
        )

    }

    private fun createFromInputString(inputStringList: MutableList<String>): InputData {
        val ws = "\\s+".toRegex() // white space regular expression
        val thresholdAndPseudocount = inputStringList[0].split(ws)
        val threshold = thresholdAndPseudocount[0].toDouble()
        val pseudocount = thresholdAndPseudocount[1].toDouble()
        inputStringList.removeFirst()
        inputStringList.removeFirst() // remove separator

        val statesCharList = inputStringList[0].split(ws).map { it[0] }
        inputStringList.removeFirst()
        inputStringList.removeFirst() // remove separator

        val alignmentStringList: MutableList<String> = mutableListOf()
        while (inputStringList.size != 0) {
            alignmentStringList.add(inputStringList.removeFirst())
        }

        return InputData(
            threshold = threshold,
            pseudocountSigma = pseudocount,
            statesCharList = statesCharList,
            alignmentStringList = alignmentStringList
        )
    }

    /**
     * print a formatted transition matrix for debugging
     */
    fun uglyPrintTransitionMatrix(writer: BufferedWriter) {
        val str = StringBuilder()

        val baseGroupCount = hmmp.numMatchColumns
        val numPercentageRowsAndColumns = baseGroupCount * 3 + 3

        // header
        str.append("\tS\tI0\t")
        for (i in 1..baseGroupCount) {
            str.append("M$i\tD$i\tI$i\t")
        }
        str.append("E\n")

        for (row in 0 until numPercentageRowsAndColumns) {
            when (row) {
                0 -> str.append("S\t")
                1 -> str.append("I0\t")
                numPercentageRowsAndColumns - 1 -> str.append("E	")
                else -> {
                    val groupNumOffset = (row - 2) % 3
                    val groupNum = (row - 2) / 3 + 1
                    val label = "${"MDI"[groupNumOffset]}$groupNum\t"
                    str.append(label)
                }
            }
            for (col in 0 until numPercentageRowsAndColumns) {
                var outNum: String
                val num = hmmp.t[row, col]
                if (num == 0.0) {
                    outNum = "0"
                } else {
//                    outNum = String.format("%6.5f", num)
                    outNum = "$num"
                }
                str.append("$outNum")
                if (col != numPercentageRowsAndColumns - 1) {
                    str.append("\t")
                }
            }
            str.append("\n")
        }
        writer.write(str.toString())
    }

    /**
     * print a formatted transition matrix for debugging
     */
    fun uglyPrintEmissionsMatrix(writer: BufferedWriter) {
        val str = StringBuilder()

        val baseGroupCount = hmmp.numMatchColumns
        val numStatesCharList = hmmp.statesCharList.size
        val numPercentageRowsAndColumns = baseGroupCount * 3 + 3

        // header
        str.append("\t")
        for (i in 0 until numStatesCharList) {
            str.append("${hmmp.statesCharList[i]}")
            if (i != numStatesCharList - 1) {
                str.append("\t")
            }
        }
        str.append("\n")

        for (row in 0 until numPercentageRowsAndColumns) {
            when (row) {
                0 -> str.append("S\t")
                1 -> str.append("I0\t")
                numPercentageRowsAndColumns - 1 -> str.append("E\t")
                else -> {
                    val groupNumOffset = (row - 2) % 3
                    val groupNum = (row - 2) / 3 + 1
                    val label = "${"MDI"[groupNumOffset]}$groupNum\t"
                    str.append(label)
                }
            }
            for (col in 0 until numStatesCharList) {
                var outNum: String
                val num = hmmp.e[row, col]
                if (num == 0.0) {
                    outNum = "0"
                } else {
//                    outNum = String.format("%6.5f", num)
                    outNum = "$num"
                }
                str.append(outNum)
                if (col != numStatesCharList - 1) {
                    str.append("\t")
                }
            }
            str.append("\n")
        }
        writer.write(str.toString())
    }

}