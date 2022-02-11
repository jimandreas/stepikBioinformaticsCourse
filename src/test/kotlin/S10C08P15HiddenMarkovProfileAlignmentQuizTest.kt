@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable",
    "LiftReturnOrAssignment"
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


internal class S10C08P15HiddenMarkovProfileAlignmentQuizTest {

    lateinit var hmmp: HiddenMarkovModelsHMMProfile

    @BeforeEach
    fun setUp() {
        hmmp = HiddenMarkovModelsHMMProfile()
        hmmp.debugOutput = false
    }

    /**
     * https://stepik.org/lesson/240402/step/15?unit=212748
     * https://rosalind.info/problems/ba10e/
     */

    /**

    Profile HMM Problem

    Given: A threshold θ, followed by an alphabet Σ, followed by a multiple
    alignment Alignment whose strings are formed from Σ.

    Return: The transition and emission probabilities of the profile HMM HMM(Alignment, θ).
     */

   // Extra dataset

    @Test
    @DisplayName("Profile Alignment Test Extra Dataset")
    fun profileAlignmentExtraDatasetSampleTest() {
        val inputData = """
0.252
--------
A B C D E
--------
DCDABACED
DCCA--CA-
DCDAB-CA-
BCDA---A-
BC-ABE-AE
        """.trimIndent()

        val expectedResultsString = """
	S	I0	M1	D1	I1	M2	D2	I2	M3	D3	I3	M4	D4	I4	M5	D5	I5	E	
S	0	0	1.0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M1	0	0	0	0	0	1.0	0	0	0	0	0	0	0	0	0	0	0	0
D1	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I1	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M2	0	0	0	0	0	0	0	0	0.8	0.2	0	0	0	0	0	0	0	0
D2	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I2	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M3	0	0	0	0	0	0	0	0	0	0	0	1.0	0	0	0	0	0	0
D3	0	0	0	0	0	0	0	0	0	0	0	1.0	0	0	0	0	0	0
I3	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M4	0	0	0	0	0	0	0	0	0	0	0	0	0	0.8	0.2	0	0	0
D4	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I4	0	0	0	0	0	0	0	0	0	0	0	0	0	0.5	0.5	0	0	0
M5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.4	0.6
D5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.0
E	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
--------
	A	B	C	D	E
S	0	0	0	0	0
I0	0	0	0	0	0
M1	0	0.4	0	0.6	0
D1	0	0	0	0	0
I1	0	0	0	0	0
M2	0	0	1.0	0	0
D2	0	0	0	0	0
I2	0	0	0	0	0
M3	0	0	0.25	0.75	0
D3	0	0	0	0	0
I3	0	0	0	0	0
M4	1.0	0	0	0	0
D4	0	0	0	0	0
I4	0.125	0.375	0.375	0	0.125
M5	0.8	0	0	0	0.2
D5	0	0	0	0	0
I5	0	0	0	0.5	0.5
E	0	0	0	0	0
        """.trimIndent()

        //hmmp.debugOutput = true
        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)

//        println("Output:")
//        uglyPrintTransitionMatrix()
//        println("--------")
//        uglyPrintEmissionsMatrix()
    }


    @Test
    @DisplayName("Profile Alignment Test Rosalind Quiz Dataset")
    fun profileAlignmentExtraDatasetRosalindQuizTest() {
        val inputData = """
0.239
--------
A	B	C	D	E
--------
EBAAE-BBB
DB--EABED
A-EAEC-E-
CBCAEDBEB
CBADEDBEB
-BAAEDCED
E-ABEDBBB
        """.trimIndent()

        val expectedResultsString = """
  S I0 M1 D1 I1 M2 D2 I2 M3 D3 I3 M4 D4 I4 M5 D5 I5 M6 D6 I6 M7 D7 I7 M8 D8 I8 E
S 0.0 0.0 0.8571428571428571 0.14285714285714285 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M1 0.0 0.0 0.0 0.0 0.6666666666666666 0.3333333333333333 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D1 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I1 0.0 0.0 0.0 0.0 0.0 0.8 0.2 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M2 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D2 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I2 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M3 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D3 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I3 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M4 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.8571428571428571 0.14285714285714285 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D4 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I4 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.8333333333333334 0.16666666666666666 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M6 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0
D6 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0
I6 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M7 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.8571428571428571 0.14285714285714285 0.0 0.0
D7 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I7 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M8 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0
D8 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0
I8 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
E 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
--------
  A B C D E
S 0.0 0.0 0.0 0.0 0.0
I0 0.0 0.0 0.0 0.0 0.0
M1 0.16666666666666666 0.0 0.3333333333333333 0.16666666666666666 0.3333333333333333
D1 0.0 0.0 0.0 0.0 0.0
I1 0.0 1.0 0.0 0.0 0.0
M2 0.6666666666666666 0.0 0.16666666666666666 0.0 0.16666666666666666
D2 0.0 0.0 0.0 0.0 0.0
I2 0.0 0.0 0.0 0.0 0.0
M3 0.6666666666666666 0.16666666666666666 0.0 0.16666666666666666 0.0
D3 0.0 0.0 0.0 0.0 0.0
I3 0.0 0.0 0.0 0.0 0.0
M4 0.0 0.0 0.0 0.0 1.0
D4 0.0 0.0 0.0 0.0 0.0
I4 0.0 0.0 0.0 0.0 0.0
M5 0.16666666666666666 0.0 0.16666666666666666 0.6666666666666666 0.0
D5 0.0 0.0 0.0 0.0 0.0
I5 0.0 0.0 0.0 0.0 0.0
M6 0.0 0.8333333333333334 0.16666666666666666 0.0 0.0
D6 0.0 0.0 0.0 0.0 0.0
I6 0.0 0.0 0.0 0.0 0.0
M7 0.0 0.2857142857142857 0.0 0.0 0.7142857142857143
D7 0.0 0.0 0.0 0.0 0.0
I7 0.0 0.0 0.0 0.0 0.0
M8 0.0 0.6666666666666666 0.0 0.3333333333333333 0.0
D8 0.0 0.0 0.0 0.0 0.0
I8 0.0 0.0 0.0 0.0 0.0
E 0.0 0.0 0.0 0.0 0.0
        """.trimIndent()

        //hmmp.debugOutput = true
        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)

//        println("Output:")
//        uglyPrintTransitionMatrix()
//        println("--------")
//        uglyPrintEmissionsMatrix()
    }


    @Test
    @DisplayName("Profile Alignment Test Rosalind Quiz2 Dataset")
    fun profileAlignmentExtraDatasetRosalindQuiz2Test() {
        val inputData = """
0.21
--------
A	B	C	D	E
--------
-CACABBBC
DAAC--CCB
D-ACABC-C
D-CCABDEC
DAADDBC--
DAACAEDCC
DAABCB-C-
D--EABDBC
D-A-ABCCC
        """.trimIndent()

        val expectedResultsString = """
  S I0 M1 D1 I1 M2 D2 I2 M3 D3 I3 M4 D4 I4 M5 D5 I5 M6 D6 I6 E
S 0.0 0.0 0.8888888888888888 0.1111111111111111 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M1 0.0 0.0 0.0 0.0 0.5 0.375 0.125 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D1 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I1 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M2 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.875 0.125 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D2 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I2 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M3 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.875 0.125 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D3 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I3 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M4 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0
D4 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0
I4 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.875 0.125 0.0 0.0
D5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0
I5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M6 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.875 0.125
D6 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0
I6 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.42857142857142855 0.5714285714285714
E 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
--------
  A B C D E
S 0.0 0.0 0.0 0.0 0.0
I0 0.0 0.0 0.0 0.0 0.0
M1 0.0 0.0 0.0 1.0 0.0
D1 0.0 0.0 0.0 0.0 0.0
I1 0.8 0.0 0.2 0.0 0.0
M2 0.875 0.0 0.125 0.0 0.0
D2 0.0 0.0 0.0 0.0 0.0
I2 0.0 0.0 0.0 0.0 0.0
M3 0.0 0.125 0.625 0.125 0.125
D3 0.0 0.0 0.0 0.0 0.0
I3 0.0 0.0 0.0 0.0 0.0
M4 0.75 0.0 0.125 0.125 0.0
D4 0.0 0.0 0.0 0.0 0.0
I4 0.0 0.0 0.0 0.0 0.0
M5 0.0 0.875 0.0 0.0 0.125
D5 0.0 0.0 0.0 0.0 0.0
I5 0.0 0.0 0.0 0.0 0.0
M6 0.0 0.125 0.5 0.375 0.0
D6 0.0 0.0 0.0 0.0 0.0
I6 0.0 0.21428571428571427 0.7142857142857143 0.0 0.07142857142857142
E 0.0 0.0 0.0 0.0 0.0
        """.trimIndent()

        //hmmp.debugOutput = true
        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)

        val outputMessagesFilePath = "zzhiddenMarkov.txt"

        val outFile = File(outputMessagesFilePath)
        val writer = outFile.bufferedWriter()
        
        uglyPrintTransitionMatrix(writer)
        writer.write("--------\n")
        uglyPrintEmissionsMatrix(writer)
        writer.close()
    }

    @Test
    @DisplayName("Profile Alignment Test Rosalind Quiz3 Dataset")
    fun profileAlignmentExtraDatasetRosalindQuiz3Test() {
        val inputData = """
0.314
--------
A	B	C	D	E
--------
C---ACCA-
-BBAACCE-
CBBCA-CAD
E-BAA-C-D
CDDA-ECAD
ED-A-CCAD
CBEAEC-AD
CBBADCCAE
CDBAA---D
        """.trimIndent()

        val expectedResultsString = """
  S I0 M1 D1 I1 M2 D2 I2 M3 D3 I3 M4 D4 I4 M5 D5 I5 M6 D6 I6 M7 D7 I7 M8 D8 I8 E
S 0.0 0.0 0.8888888888888888 0.1111111111111111 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M1 0.0 0.0 0.0 0.0 0.0 0.75 0.25 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D1 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I1 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M2 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.8571428571428571 0.14285714285714285 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D2 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.5 0.5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I2 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M3 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D3 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.5 0.5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I3 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M4 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.75 0.25 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D4 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I4 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.5714285714285714 0.2857142857142857 0.14285714285714285 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
D5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
I5 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.8333333333333334 0.16666666666666666 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M6 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.8571428571428571 0.14285714285714285 0.0 0.0 0.0 0.0 0.0
D6 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.5 0.5 0.0 0.0 0.0 0.0 0.0
I6 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M7 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.7142857142857143 0.2857142857142857 0.0 0.0
D7 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0
I7 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
M8 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0
D8 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0
I8 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
E 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
--------
  A B C D E
S 0.0 0.0 0.0 0.0 0.0
I0 0.0 0.0 0.0 0.0 0.0
M1 0.0 0.0 0.75 0.0 0.25
D1 0.0 0.0 0.0 0.0 0.0
I1 0.0 0.0 0.0 0.0 0.0
M2 0.0 0.5714285714285714 0.0 0.42857142857142855 0.0
D2 0.0 0.0 0.0 0.0 0.0
I2 0.0 0.0 0.0 0.0 0.0
M3 0.0 0.7142857142857143 0.0 0.14285714285714285 0.14285714285714285
D3 0.0 0.0 0.0 0.0 0.0
I3 0.0 0.0 0.0 0.0 0.0
M4 0.875 0.0 0.125 0.0 0.0
D4 0.0 0.0 0.0 0.0 0.0
I4 0.0 0.0 0.0 0.0 0.0
M5 0.7142857142857143 0.0 0.0 0.14285714285714285 0.14285714285714285
D5 0.0 0.0 0.0 0.0 0.0
I5 0.0 0.0 0.8333333333333334 0.0 0.16666666666666666
M6 0.0 0.0 1.0 0.0 0.0
D6 0.0 0.0 0.0 0.0 0.0
I6 0.0 0.0 0.0 0.0 0.0
M7 0.8571428571428571 0.0 0.0 0.0 0.14285714285714285
D7 0.0 0.0 0.0 0.0 0.0
I7 0.0 0.0 0.0 0.0 0.0
M8 0.0 0.0 0.0 0.8571428571428571 0.14285714285714285
D8 0.0 0.0 0.0 0.0 0.0
I8 0.0 0.0 0.0 0.0 0.0
E 0.0 0.0 0.0 0.0 0.0
        """.trimIndent()

        //hmmp.debugOutput = true
        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)

//        val outputMessagesFilePath = "zzhiddenMarkov.txt"
//
//        val outFile = File(outputMessagesFilePath)
//        val writer = outFile.bufferedWriter()
//
//        uglyPrintTransitionMatrix(writer)
//        writer.write("--------\n")
//        uglyPrintEmissionsMatrix(writer)
//        writer.close()
    }

    @Test
    @DisplayName("Profile Alignment Test Rosalind Quiz4 Dataset")
    fun profileAlignmentExtraDatasetRosalindQuiz4Test() {
        val inputData = """
0.311
--------
A	B	C	D	E
--------
CD--B-DAC
ECB---D--
EAB-BC-AC
-CBEBCAAB
E-D--CDAC
        """.trimIndent()

        val expectedResultsString = """
	S	I0	M1	D1	I1	M2	D2	I2	M3	D3	I3	M4	D4	I4	M5	D5	I5	M6	D6	I6	E
S	0	0	0.8	0.2	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M1	0	0	0	0	0	0.75	0.25	0	0	0	0	0	0	0	0	0	0	0	0	0	0
D1	0	0	0	0	0	1.0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I1	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M2	0	0	0	0	0	0	0	0	0.75	0.25	0	0	0	0	0	0	0	0	0	0	0
D2	0	0	0	0	0	0	0	0	1.0	0	0	0	0	0	0	0	0	0	0	0	0
I2	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M3	0	0	0	0	0	0	0	0	0	0	0.75	0.25	0	0	0	0	0	0	0	0	0
D3	0	0	0	0	0	0	0	0	0	0	1.0	0	0	0	0	0	0	0	0	0	0
I3	0	0	0	0	0	0	0	0	0	0	0.42857142857142855	0.42857142857142855	0.14285714285714285	0	0	0	0	0	0	0	0
M4	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.75	0.25	0	0	0	0	0
D4	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.0	0	0	0	0	0	0
I4	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.0	0	0	0
D5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.0	0	0
I5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.0
D6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.0
I6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
E	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
--------
	A	B	C	D	E
S	0	0	0	0	0
I0	0	0	0	0	0
M1	0	0	0.25	0	0.75
D1	0	0	0	0	0
I1	0	0	0	0	0
M2	0.25	0	0.5	0.25	0
D2	0	0	0	0	0
I2	0	0	0	0	0
M3	0	0.75	0	0.25	0
D3	0	0	0	0	0
I3	0	0.42857142857142855	0.42857142857142855	0	0.14285714285714285
M4	0.25	0	0	0.75	0
D4	0	0	0	0	0
I4	0	0	0	0	0
M5	1.0	0	0	0	0
D5	0	0	0	0	0
I5	0	0	0	0	0
M6	0	0.25	0.75	0	0
D6	0	0	0	0	0
I6	0	0	0	0	0
E	0	0	0	0	0
        """.trimIndent()   // accepted answer with no rounding

        //hmmp.debugOutput = true
        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)

//        val outputMessagesFilePath = "zzhiddenMarkov.txt"
//
//        val outFile = File(outputMessagesFilePath)
//        val writer = outFile.bufferedWriter()
//
//        uglyPrintTransitionMatrix(writer)
//        writer.write("--------\n")
//        uglyPrintEmissionsMatrix(writer)
//        writer.close()
    }

    @Test
    @DisplayName("Profile Alignment Test Stepik Quiz Dataset")
    fun profileAlignmentExtraDatasetStepikQuizTest() {
        val inputData = """
0.278
--------
A	B	C	D	E
--------
CAAEDDECE
B-A--DC-E
BBAEDDBDE
BBAE-DDAE
BB-ED-ACE
-BA-DDDCE
        """.trimIndent()

        val expectedResultsString = """
	S	I0	M1	D1	I1	M2	D2	I2	M3	D3	I3	M4	D4	I4	M5	D5	I5	M6	D6	I6	M7	D7	I7	E
S	0	0	0.83333	0.16667	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M1	0	0	0	0	0	0.80000	0.20000	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
D1	0	0	0	0	0	1.00000	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I1	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M2	0	0	0	0	0	0	0	0	0.80000	0.20000	0	0	0	0	0	0	0	0	0	0	0	0	0	0
D2	0	0	0	0	0	0	0	0	1.00000	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I2	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M3	0	0	0	0	0	0	0	0	0	0	0.80000	0.20000	0	0	0	0	0	0	0	0	0	0	0	0
D3	0	0	0	0	0	0	0	0	0	0	1.00000	0	0	0	0	0	0	0	0	0	0	0	0	0
I3	0	0	0	0	0	0	0	0	0	0	0.37500	0.50000	0.12500	0	0	0	0	0	0	0	0	0	0	0
M4	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.00000	0	0	0	0	0	0	0	0	0
D4	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.00000	0	0	0	0	0	0	0	0	0
I4	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0.83333	0.16667	0	0	0	0	0
D5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I5	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.00000	0	0	0
D6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.00000	0	0	0
I6	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
M7	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	1.00000
D7	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
I7	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
E	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
--------
	A	B	C	D	E
S	0	0	0	0	0
I0	0	0	0	0	0
M1	0	0.80000	0.20000	0	0
D1	0	0	0	0	0
I1	0	0	0	0	0
M2	0.20000	0.80000	0	0	0
D2	0	0	0	0	0
I2	0	0	0	0	0
M3	1.00000	0	0	0	0
D3	0	0	0	0	0
I3	0	0	0	0.50000	0.50000
M4	0	0	0	1.00000	0
D4	0	0	0	0	0
I4	0	0	0	0	0
M5	0.16667	0.16667	0.16667	0.33333	0.16667
D5	0	0	0	0	0
I5	0	0	0	0	0
M6	0.20000	0	0.60000	0.20000	0
D6	0	0	0	0	0
I6	0	0	0	0	0
M7	0	0	0	0	1.00000
D7	0	0	0	0	0
I7	0	0	0	0	0
E	0	0	0	0	0
        """.trimIndent() // accepted answer

        //hmmp.debugOutput = true
        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)

//        val outputMessagesFilePath = "zzhiddenMarkov.txt"
//
//        val outFile = File(outputMessagesFilePath)
//        val writer = outFile.bufferedWriter()
//
//        uglyPrintTransitionMatrix(writer)
//        writer.write("--------\n")
//        uglyPrintEmissionsMatrix(writer)
//        writer.close()
    }


    // OTHER tests - keep for unit testing
    @Test
    @DisplayName("test input string parsing")
    fun testInputStringParsing() {
        val dataFormat = """
            0.5
            --------
            A B
            --------
            AA-
            --A
            --B
    """.trimIndent()

        val d = createFromInputString(dataFormat.lines().toMutableList())
        assertEquals(0.5, d.threshold, 0.001)
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
        var statesCharList: List<Char>,
        var alignmentStringList: List<String>
    )


    /**
     * test the parsing and checking of expected results
     */
    @Test
    @DisplayName("test output string parsing")
    fun testOutputStringParsing() {

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
                    rowResult[col], rowExpected[col] / 100.0,
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
                    rowResult[col], rowExpected[col] / 100.0,
                    "Emissions matrix mismatch Row: $rowIndex Col: $col")
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
        val threshold = inputStringList[0].toDouble()
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
                    val label =	"${"MDI"[groupNumOffset]}$groupNum\t"
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
                    val label =	"${"MDI"[groupNumOffset]}$groupNum\t"
                    str.append(label)
                }
            }
            for (col in 0 until numStatesCharList) {
                var outNum: String
                val num = hmmp.e[row, col]
                if (num == 0.0) {
                    outNum =	"0"
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