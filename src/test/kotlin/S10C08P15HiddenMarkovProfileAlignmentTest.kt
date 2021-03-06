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
//        hmmp.debugOutput = true
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
    fun profileAlignmentDataset1() {
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
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)
    }

    /**
    This dataset makes sure that your code is correctly applying the
    threshold when determining the seed alignment. Since half of
    the second column is gap characters the second column will not
    be a part of the seed alignment (0.5 > threshold of 0.4). If your
    output has the wrong states there is likely an error in application
    of the threshold value. Be sure that all columns with a fraction of
    gap characters greater than the threshold are not in the seed alignment.
    If your outputted transition matrix is incorrect make sure that your
    code follows the rules for profile construction from the seed alignment
    and original multiple alignment. Similarly to Test Dataset 1, the
    only option from the starting state is to go into a match state and
    emit an A. Since the second column is not a part of the seed alignment
    the B character from the first string corresponds to an insertion state.
    The gap in the second string is ignored since it isn’t a part of the
    seed alignment. This means that from state M1 the HMM can either
    transition to I1 and emit a B character or transition to E and terminate.
    Check that your code correctly implements this logic if your output
    doesn’t match the correct output.
     */
    @Test
    @DisplayName("Profile Alignment Test Dataset 2")
    fun profileAlignmentDataset2() {
        val inputData = """
            0.4
            --------
            A B
            --------
            AB
            A-
        """.trimIndent()

        val expectedResultsString = """
                S	I0	M1	D1	I1	E
            S	0	0	1.0	0	0	0
            I0	0	0	0	0	0	0
            M1	0	0	0	0	0.5	0.5
            D1	0	0	0	0	0	0
            I1	0	0	0	0	0	1.0
            E	0	0	0	0	0	0
            --------
                A	B
            S	0	0
            I0	0	0
            M1	1.0	0
            D1	0	0
            I1	0	1.0
            E	0	0
        """.trimIndent()

        hmmp.debugOutput = false
        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)
    }

    @Test
    @DisplayName("Profile Alignment Test Dataset 5")
    fun profileAlignmentDataset5() {
        val inputData = """
            0.5
            --------
            A B
            --------
            AA-
            --A
            --B
        """.trimIndent()

        val expectedResultsString = """
	S	I0	    M1	   D1	I1	E
S	0	0.333	0.667	0	0	0
I0	0	0.5	    0	    0.5	0	0
M1	0	0	    0	    0	0	1.0
D1	0	0	    0	    0	0	1.0
I1	0	0	    0	    0	0	0
E	0	0	    0	    0	0	0
--------
	A	B
S	0	0
I0	1.0	0
M1	0.5	0.5
D1	0	0
I1	0	0
E	0	0
        """.trimIndent()

        hmmp.debugOutput = false
        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)
    }

    /**
     * this is a simple test
     */
    @Test
    @DisplayName("Profile Alignment Test Simple Test 1")
    fun profileAlignmentSimpleTest1() {
        val inputData = """
            0.3
            --------
            A B C
            --------
            CCC
            C--
            C--
        """.trimIndent()

        val expectedResultsString = """
                S    I0   M1   D1   I1   E
            S   0    0    1.00 0    0    0
            I0  0    0    0    0    0    0
            M1  0    0    0    0    0.333 0.667
            D1  0    0    0    0    0    0
            I1  0    0    0    0    0.50 0.50
            E   0    0    0    0    0    0
            --------
                A	B	C
            S	0.0	0.0	0.0
            I0	0.0	0.0	0.0
            M1	0.0	0.0	1.0
            D1	0.0	0.0	0.0
            I1	0.0	0.0	1.0
            E	0.0	0.0	0.0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)
    }

    /**
     * test multiple inserts following a match column
     */
    @Test
    @DisplayName("Profile Alignment Test Simple Test 2")
    fun profileAlignmentSimpleTest2() {
        val inputData = """
            0.3
            --------
            A B C
            --------
            CCC
            C--
            C--
        """.trimIndent()

        val expectedResultsString = """
            S	I0	M1	D1	I1	E
        S	0.0	0.0	1.0	0.0	0.0	0.0
        I0	0.0	0.0	0.0	0.0	0.0	0.0
        M1	0.0	0.0	0.0	0.0	0.333	0.666
        D1	0.0	0.0	0.0	0.0	0.0	0.0
        I1	0.0	0.0	0.0	0.0	0.5	0.5
        E	0.0	0.0	0.0	0.0	0.0	0.0
        --------
            A	B	C
        S	0.0	0.0	0.0
        I0	0.0	0.0	0.0
        M1	0.0	0.0	1.0
        D1	0.0	0.0	0.0
        I1	0.0	0.0	1.0
        E	0.0	0.0	0.0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)
    }

    /**
     * test multiple inserts preceeding a match column
     */
    @Test
    @DisplayName("Profile Alignment Test Simple Test 3")
    fun profileAlignmentSimpleTest3() {
        val inputData = """
            0.3
            --------
            A B C D
            --------
            ABCDC
            ABC--
            ----C
            ----C
        """.trimIndent()

        val expectedResultsString = """
   S    I0    M1    D1  I1  E
S  0.0  0.5   0.5   0.0 0.0 0.0
I0 0.0  0.714 0.143 0.143 0.0 0.0
M1 0.0  0.0   0.0   0.0 0.0 1.0
D1 0.0  0.0   0.0   0.0 0.0 1.0
I1 0.0  0.0   0.0   0.0 0.0 0.0
E  0.0  0.0   0.0   0.0 0.0 0.0
--------
  A B C D
S 0.0 0.0 0.0 0.0
I0 0.286 0.286 0.286 0.143
M1 0.0 0.0 1.0 0.0
D1 0.0 0.0 0.0 0.0
I1 0.0 0.0 0.0 0.0
E 0.0 0.0 0.0 0.0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)
    }

    /**
     * test insert then delete in match column
     */
    @Test
    @DisplayName("Profile Alignment Test Simple Test 4")
    fun profileAlignmentSimpleTest4() {
        val inputData = """
            0.3
            --------
            A B C D
            --------
            A-AA
            B-BB
            C-CC
            DD-D
        """.trimIndent()

        val expectedResultsString = """
	S	I0	M1	D1	I1	M2	D2	I2	M3	D3	I3	E
S	0.0	0.0	1.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0
I0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0
M1	0.0	0.0	0.0	0.0	0.25	0.75	0.0	0.0	0.0	0.0	0.0	0.0
D1	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0
I1	0.0	0.0	0.0	0.0	0.0	0.0	1.0	0.0	0.0	0.0	0.0	0.0
M2	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	1.0	0.0	0.0	0.0
D2	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	1.0	0.0	0.0	0.0
I2	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0
M3	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	1.0
D3	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0
I3	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0
E	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0	0.0
--------
	A	B	C	D
S	0	0	0	0
I0	0	0	0	0
M1	0.25	0.25	0.25	0.25
D1	0	0	0	0
I1	0	0	0	1
M2	0.333	0.333	0.333	0
D2	0	0	0	0
I2	0	0	0	0
M3	0.25	0.25	0.25	0.25
D3	0	0	0	0
I3	0	0	0	0
E	0	0	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)
    }

    @Test
    @DisplayName("Profile Alignment Test STEPIK Sample Dataset")
    fun profileAlignmentStepikSampleTest() {
        val inputData = """
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
        """.trimIndent()

        val expectedResultsString = """
S	I0	M1	D1	I1	M2	D2	I2	E   
S	0	0	1.0	0	0	0	0	0	0
I0	0	0	0	0	0	0	0	0	0
M1	0	0	0	0	0.625	0.375	0	0	0
D1	0	0	0	0	0	0	0	0	0
I1	0	0	0	0	0	0.8	0.2	0	0
M2	0	0	0	0	0	0	0	0	1.0
D2	0	0	0	0	0	0	0	0	1.0
I2	0	0	0	0	0	0	0	0	0
E	0	0	0	0	0	0	0	0	0
--------
	A	B	C	D	E
S	0	0	0	0	0
I0	0	0	0	0	0
M1	0	0	0	0	1.0
D1	0	0	0	0	0
I1	0	0.8	0	0	0.2
M2	0.143	0	0	0.714	0.143
D2	0	0	0	0	0
I2	0	0	0	0	0
E	0	0	0	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)
    }


    @Test
    @DisplayName("Profile Alignment Test ROSALIND Sample Dataset")
    fun profileAlignmentRosalindSampleTest() {
        val inputData = """
0.289
--------
A   B   C   D   E
--------
EBA
EBD
EB-
EED
EBD
EBE
E-D
EBD
        """.trimIndent()

        val expectedResultsString = """
    S   I0  M1  D1  I1  M2    D2    I2  M3    D3    I3  E
S   0   0   1.0 0   0   0     0     0   0     0     0   0
I0  0   0   0   0   0   0     0     0   0     0     0   0
M1  0   0   0   0   0   0.875 0.125 0   0     0     0   0
D1  0   0   0   0   0   0     0     0   0     0     0   0
I1  0   0   0   0   0   0     0     0   0     0     0   0
M2  0   0   0   0   0   0     0     0   0.857 0.143 0   0
D2  0   0   0   0   0   0     0     0   1.0   0     0   0
I2  0   0   0   0   0   0     0     0   0     0     0   0
M3  0   0   0   0   0   0     0     0   0     0     0   1.0
D3  0   0   0   0   0   0     0     0   0     0     0   1.0
I3  0   0   0   0   0   0     0     0   0     0     0   0
E   0   0   0   0   0   0     0     0   0     0     0   0
--------
    A   B   C   D   E
S   0   0   0   0   0
I0  0   0   0   0   0
M1  0   0   0   0   1.0
D1  0   0   0   0   0
I1  0   0   0   0   0
M2  0   0.857   0   0   0.143
D2  0   0   0   0   0
I2  0   0   0   0   0
M3  0.143   0   0   0.714   0.143
D3  0   0   0   0   0
I3  0   0   0   0   0
E   0   0   0   0   0
        """.trimIndent()

        hmmp.debugOutput = false
        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofile(dStruct.threshold, dStruct.statesCharList, dStruct.alignmentStringList)
        checkResultingOutputDataStructure(expectedStruct, result)
    }

    @Test
    @DisplayName("test input string parsing")
    fun inputStringParsingTest() {
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
    
}