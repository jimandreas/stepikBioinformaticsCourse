@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.HiddenMarkovModelsHMMProfile
import algorithms.HiddenMarkovModelsHMMProfile.HMMTransitionAndEmissionMatrices
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


internal class S10C08P15HiddenMarkovProfileAlignmentTest {

    lateinit var hmmp: HiddenMarkovModelsHMMProfile

    @BeforeEach
    fun setUp() {
        hmmp = HiddenMarkovModelsHMMProfile()
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

    @Test
    @DisplayName("Profile Alignment Test Dataset 1")
    fun testProfileAlignmentDataset1() {
        val inputData = """
            0.1
            --------
            A B
            --------
            AA
            AA
        """.trimIndent()

        val expectedResultsString = """
            	S	I0	M1	D1	I1	M2	D2	I2	E
            S	0	0	1.0	0	0	0	0	0	0
            I0	0	0	0	0	0	0	0	0	0
            M1	0	0	0	0	0	1.0	0	0	0
            D1	0	0	0	0	0	0	0	0	0
            I1	0	0	0	0	0	0	0	0	0
            M2	0	0	0	0	0	0	0	0	1.0
            D2	0	0	0	0	0	0	0	0	0
            I2	0	0	0	0	0	0	0	0	0
            E	0	0	0	0	0	0	0	0	0
            --------
                A	B
            S	0	0
            I0	0	0
            M1	1.0	0
            D1	0	0
            I1	0	0
            M2	1.0	0
            D2	0	0
            I2	0	0
            E	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
    }


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
        resultStruct: HMMTransitionAndEmissionMatrices) {

        assertContentEquals(expectedStruct.symbols, resultStruct.symbols)
        assertContentEquals(expectedStruct.states, resultStruct.states)
        assertContentEquals(expectedStruct.transitions, resultStruct.transitions)
        assertContentEquals(expectedStruct.emissions, resultStruct.emissions)

    }

    private fun createExpectedOutputDataStructure(symbolList: List<Char>, stringToParse: String)
            : HMMTransitionAndEmissionMatrices {

        val l = stringToParse.lines().toMutableList()
        val ws = "\\s+".toRegex() // white space regular expression
        val transitionsNameList = l.removeFirst().split(ws).toMutableList()
        if (transitionsNameList[0] == "") {
            transitionsNameList.removeFirst()
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


    @Test
    @DisplayName("Construct Profile HMM from Multiple Alignment Sample Test")
    fun probConstructProfileHmmSampleTest() {
        val testInput = """
0.289
--------
A B C D E
--------
EBA
E-D
EB-
EED
EBD
EBE
E-D
E-D
        """.trimIndent().lines()

//        val d = createFromInputString(testInput)
//
//        val result = hmm.viterbiOutcomeLikelihood(
//            d.emissionStringx,
//            d.emissionCharList,
//            d.statesCharList,
//            d.transitionMatrix,
//            d.emissionMatrix
//        )

        //println(result)

    }

}