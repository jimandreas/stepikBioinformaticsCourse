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


internal class S10C09P14SequenceAlignmentWithProfileHmmTest {

    lateinit var hmmp: HiddenMarkovModelsHMMProfile

    @BeforeEach
    fun setUp() {
        hmmp = HiddenMarkovModelsHMMProfile()
        hmmp.debugOutput = true
    }

    /**
     * https://rosalind.info/problems/ba10g/
     */
    /**
    Sequence Alignment with Profile HMM Problem: Align a new sequence to a family of sequences using a profile HMM.

    Input: A multiple alignment Alignment, a threshold θ, a pseudocount value σ, and a string Text.
    Output: An optimal hidden path emitting Text in HMM(Alignment, θ, σ).

    Code Challenge: Solve the Sequence Alignment with Profile HMM Problem.

    Input: A string x followed by a threshold θ and a pseudocount σ, followed by an alphabet Σ, followed by a multiple alignment Alignment whose strings are formed from Σ.
    Output: An optimal hidden path emitting x in HMM(Alignment, θ, σ).
    */


    @Test
    @DisplayName("Profile Alignment Sample Test")
    fun profileAlignmentSampleTest() {
        val inputData = """
            AEFDFDC
            --------
            0.4 0.01
            --------
            A B C D E F
            --------
            ACDEFACADF
            AFDA---CCF
            A--EFD-FDC
            ACAEF--A-C
            ADDEFAAADF
        """.trimIndent()

        val expectedResultsString = """
        M1 D2 D3 M4 M5 I5 M6 M7 M8
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())

        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )

        println("String X is ${dStruct.stringx}")

    }

    /**
    This dataset makes sure that your code uses match states
    to represent mismatches as well as matches. The optimal
    alignment using the profile HMM with pseudocounts consists
    of a match and a mismatch. If your output does not match
    the correct output make sure that your code is using a
    profile HMM with pseudocounts. If your implementation
    does not use pseudocounts any path through the HMM would
    have a 0 probability, since the character B is never observed
    in the original multiple alignment. If your output is incorrect
    and your code implements a profile HMM with pseudocounts
    then make sure that your initialization for the Viterbi
    algorithm is correct. Unlike previous problems in which the
    Viterbi algorithm was used we do not assume that transitions
    from the starting state are equally likely. Make sure that your
    code uses the transition matrix to determine transitions from
    the starting state.
     */
    @Test
    @DisplayName("Profile Alignment Test Dataset1")
    fun profileAlignmentTestDataset1() {
        val inputData = """
            AB
            --------
            0.4 0.01
            --------
            A B
            --------
            AA
            AA
        """.trimIndent()

        val expectedResultsString = """
        M1 M2
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())

        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )

        println("String X is ${dStruct.stringx}")

    }


    data class InputData(
        var threshold: Double,
        var pseudocountSigma: Double,
        var stringx: String,
        var statesCharList: List<Char>,
        var alignmentStringList: List<String>
    )

    /**
     * Input/Output format:
     * Input:
    AB
    --------
    0.4 0.01
    --------
    A B
    --------
    AA
    AA
    Output:
    M1 M2

     */

    private fun createFromInputString(inputStringList: MutableList<String>): InputData {
        val ws = "\\s+".toRegex() // white space regular expression
        val stringx = inputStringList.removeFirst()
        inputStringList.removeFirst() // remove separator
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
            stringx = stringx,
            statesCharList = statesCharList,
            alignmentStringList = alignmentStringList
        )
    }

}