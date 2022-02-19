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


internal class S10C09P05HiddenMarkovProfileWithPseudocountsTest {

    lateinit var hmmp: HiddenMarkovModelsHMMProfile

    @BeforeEach
    fun setUp() {
        hmmp = HiddenMarkovModelsHMMProfile()
        hmmp.debugOutput = true
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


    /**
    This dataset makes sure that your code is correctly
    applying the pseudocounts to the transition matrix.
    Make sure that your implementation of profile HMMs
    without pseudocounts passes all test cases before
    doing this problem. This dataset only has one character
    in its multiple alignment, so only the only observed
    transitions were from state S to state M1 to state E.
    All other transitions that are possible given the design
    of the profile HMM are not observed. If your output does
    not match the correct output your code is likely
    incorrectly applying the pseudocounts. Make sure that
    you are adding the specified pseudocount to the transition
    and emission probabilities, not the counts. A common mistake
    is to add pseudocounts to the profile created for the transition
    and emission matrices and then normalize the result. This how
    pseudocounts were used in Motif Finding. Make sure that your code
    is first finding the emission and transition matrices of the
    basic profile HMM and then adding pseudocounts to those
    probabilities and normalizing. Also make sure that all unseen
    but possible transitions are accounted for. In this dataset states
    I0, D1, and I1 are never visited but have transition probabilities
    because of the use of pseudocounts.
     */
    @Test
    @DisplayName("Profile Alignment Test Dataset 1")
    fun profileAlignmentDataset1() {
        val inputData = """
            0.1 0.01
            --------
            A
            --------
            A
        """.trimIndent()

        val expectedResultsString = """
                S	I0	M1	D1	I1	E
            S	0	0.00971	0.981	0.00971	0	0
            I0	0	0.333	0.333	0.333	0	0
            M1	0	0	0	0	0.00980	0.990
            D1	0	0	0	0	0.5	0.5
            I1	0	0	0	0	0.5	0.5
            E	0	0	0	0	0	0
            --------
                A
            S	0
            I0	1.0
            M1	1.0
            D1	0
            I1	1.0
            E	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )
        //checkResultingOutputDataStructure(expectedStruct, result)

        val outputMessagesFilePath = "zzhiddenMarkov.txt"

        val outFile = File(outputMessagesFilePath)
        val writer = outFile.bufferedWriter()

        uglyPrintTransitionMatrix(writer)
        writer.write("--------\n")
        uglyPrintEmissionsMatrix(writer)
        writer.close()
    }

    /**
    This dataset makes sure that your code is correctly applying
    the pseudocounts to the emission matrix. This dataset is
    identical to Test Dataset 1 except that there is one more
    character in the alphabet: B. The emission matrix should
    include a column for the emission probabilities of character B.
    Without pseudocounts our emission matrix for this profile HMM
    would have 0 probability of emitting B. Once pseudocounts are
    added the emission probabilities for the non-silent states change.
    If your output does not match the correct output make sure that
    you are applying pseudocounts to the emission matrix. Remember
    that pseudocounts are added to the probabilities, not the profile
    counts. Also be sure that non-silent states that were never seen
    in the multiple alignment (I0, I1) have non-zero emission probabilities.
    States like these should have equal probabilities of emitting any character.
     */
    @Test
    @DisplayName("Profile Alignment Test Dataset 2")
    fun profileAlignmentDataset2() {
        val inputData = """
            0.1 0.01
            --------
            A B
            --------
            A
        """.trimIndent()

        val expectedResultsString = """
                S	I0	M1	D1	I1	E
            S	0	0.00971	0.981	0.00971	0	0
            I0	0	0.333	0.333	0.333	0	0
            M1	0	0	0	0	0.00980	0.990
            D1	0	0	0	0	0.5	0.5
            I1	0	0	0	0	0.5	0.5
            E	0	0	0	0	0	0
            --------
                A	B
            S	0	0
            I0	0.5	0.5
            M1	0.990	0.00980
            D1	0	0
            I1	0.5	0.5
            E	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )
        //checkResultingOutputDataStructure(expectedStruct, result)

        val outputMessagesFilePath = "zzhiddenMarkov.txt"

        val outFile = File(outputMessagesFilePath)
        val writer = outFile.bufferedWriter()

        uglyPrintTransitionMatrix(writer)
        writer.write("--------\n")
        uglyPrintEmissionsMatrix(writer)
        writer.close()
    }

    /**
    This dataset makes sure that your code is correctly applying
    the correct pseudocount. This dataset is identical to
    Test Dataset 2 except for the value of the pseudocount.
    If your code passed Test Dataset 2 but does not have the correct
    output for this dataset you are likely not using the
    pseudocount value from the input and are adding some constant
    of your own choice. It is also possible that your normalization
    after applying the pseudocounts was not correctly implemented
    and relied on the pseudocount being of some value.
     */

    @Test
    @DisplayName("Profile Alignment Test Dataset 3")
    fun profileAlignmentDataset3() {
        val inputData = """
            0.1 0.5
            --------
            A B
            --------
            A
        """.trimIndent()

        val expectedResultsString = """
                S	I0	M1	D1	I1	E
            S	0	0.2	0.6	0.2	0	0
            I0	0	0.333	0.333	0.333	0	0
            M1	0	0	0	0	0.25	0.75
            D1	0	0	0	0	0.5	0.5
            I1	0	0	0	0	0.5	0.5
            E	0	0	0	0	0	0
            --------
                A	B
            S	0	0
            I0	0.5	0.5
            M1	0.75	0.25
            D1	0	0
            I1	0.5	0.5
            E	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )
        //checkResultingOutputDataStructure(expectedStruct, result)

        val outputMessagesFilePath = "zzhiddenMarkov.txt"

        val outFile = File(outputMessagesFilePath)
        val writer = outFile.bufferedWriter()

        uglyPrintTransitionMatrix(writer)
        writer.write("--------\n")
        uglyPrintEmissionsMatrix(writer)
        writer.close()
    }

    /**
    This dataset makes sure that your code is correctly applying
    the threshold when determining the seed alignment. It is
    analogous to Test Dataset 3 in the Profile HMM problem.
    Since half of the second column is gap characters the second
    column will not be a part of the seed alignment
    (0.5 > threshold of 0.4). If your output has the wrong states
    your implementation of the regular profile HMM is likely
    incorrect. If your implementation passed all the tests for
    the regular profile HMM problem but fails this test then
    be sure that your code does not invalidate that implementation
    when adding pseudocounts
     */

    @Test
    @DisplayName("Profile Alignment Test Dataset 4")
    fun profileAlignmentDataset4() {
        val inputData = """
            0.4 0.01
            --------
            A B
            --------
            AB
            A-
        """.trimIndent()

        val expectedResultsString = """
                S	I0	M1	D1	I1	E
            S	0	0.00971	0.981	0.00971	0	0
            I0	0	0.333	0.333	0.333	0	0
            M1	0	0	0	0	0.5	0.5
            D1	0	0	0	0	0.5	0.5
            I1	0	0	0	0	0.00980	0.990
            E	0	0	0	0	0	0
            --------
                A	B
            S	0	0
            I0	0.5	0.5
            M1	0.990	0.00980
            D1	0	0
            I1	0.00980	0.990
            E	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )
        //checkResultingOutputDataStructure(expectedStruct, result)

        val outputMessagesFilePath = "zzhiddenMarkov.txt"

        val outFile = File(outputMessagesFilePath)
        val writer = outFile.bufferedWriter()

        uglyPrintTransitionMatrix(writer)
        writer.write("--------\n")
        uglyPrintEmissionsMatrix(writer)
        writer.close()
    }

    /**
    This dataset makes sure that your code correctly handles deletion states.
    It is analogous to Test Dataset 4 in the Profile HMM problem. If your
    output has the wrong states your implementation of the regular profile
    HMM is likely incorrect. If your implementation passed all the tests
    for the regular profile HMM problem but fails this test then be sure
    that your code does not invalidate that implementation when adding pseudocounts.
     */
    @Test
    @DisplayName("Profile Alignment Test Dataset 5")
    fun profileAlignmentDataset5() {
        val inputData = """
            0.4 0.01
            --------
            A B
            --------
            A-
            -A
            -B
        """.trimIndent()

        val expectedResultsString = """
                S	I0	M1	D1	I1	E
            S	0	0.333	0.657	0.00971	0	0
            I0	0	0.00971	0.00971	0.981	0	0
            M1	0	0	0	0	0.00980	0.990
            D1	0	0	0	0	0.00980	0.990
            I1	0	0	0	0	0.5	0.5
            E	0	0	0	0	0	0
            --------
                A	B
            S	0	0
            I0	0.990	0.00980
            M1	0.5	0.5
            D1	0	0
            I1	0.5	0.5
            E	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )
        //checkResultingOutputDataStructure(expectedStruct, result)

        val outputMessagesFilePath = "zzhiddenMarkov.txt"

        val outFile = File(outputMessagesFilePath)
        val writer = outFile.bufferedWriter()

        uglyPrintTransitionMatrix(writer)
        writer.write("--------\n")
        uglyPrintEmissionsMatrix(writer)
        writer.close()
    }

    /**
    This dataset makes sure that your code allows for insertion
    states to transition to themselves. It is analogous to
    Test Dataset 5 in the Profile HMM problem. If your output
    has the wrong states your implementation of the regular
    profile HMM is likely incorrect. If your implementation
    passed all the tests for the regular profile HMM problem
    but fails this test then be sure that your code does not
    invalidate that implementation when adding pseudocounts.
     */
    @Test
    @DisplayName("Profile Alignment Test Dataset 6")
    fun profileAlignmentDataset6() {
        val inputData = """
            0.5 0.01
            --------
            A B
            --------
            AA-
            --A
            --B
        """.trimIndent()

        val expectedResultsString = """
                S	I0	M1	D1	I1	E
            S	0	0.333	0.657	0.00971	0	0
            I0	0	0.495	0.00971	0.495	0	0
            M1	0	0	0	0	0.00980	0.990
            D1	0	0	0	0	0.00980	0.990
            I1	0	0	0	0	0.5	0.5
            E	0	0	0	0	0	0
            --------
                A	B
            S	0	0
            I0	0.990	0.00980
            M1	0.5	0.5
            D1	0	0
            I1	0.5	0.5
            E	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )
        //checkResultingOutputDataStructure(expectedStruct, result)

        val outputMessagesFilePath = "zzhiddenMarkov.txt"

        val outFile = File(outputMessagesFilePath)
        val writer = outFile.bufferedWriter()

        uglyPrintTransitionMatrix(writer)
        writer.write("--------\n")
        uglyPrintEmissionsMatrix(writer)
        writer.close()
    }

    /**
    This dataset makes sure that your code includes all relevant
    columns in the seed alignment. It is analogous to
    Test Dataset 6 in the Profile HMM problem. Since the threshold
    value in this dataset is so high, all columns in the original
    multiple alignment should be included in the seed alignment.
    If your output has the wrong states your implementation of the
    regular profile HMM is likely incorrect. If your implementation
    passed all the tests for the regular profile HMM problem but fails
    this test then be sure that your code does not invalidate that
    implementation when adding pseudocounts.
     */

    @Test
    @DisplayName("Profile Alignment Test Dataset 7")
    fun profileAlignmentDataset7() {
        val inputData = """
            0.9 0.01
            --------
            A B
            --------
            A-
            -A
        """.trimIndent()

        val expectedResultsString = """
                S	I0	M1	D1	I1	M2	D2	I2	E
            S	0	0.00971	0.495	0.495	0	0	0	0	0
            I0	0	0.333	0.333	0.333	0	0	0	0	0
            M1	0	0	0	0	0.00971	0.00971	0.981	0	0
            D1	0	0	0	0	0.00971	0.981	0.00971	0	0
            I1	0	0	0	0	0.333	0.333	0.333	0	0
            M2	0	0	0	0	0	0	0	0.00980	0.990
            D2	0	0	0	0	0	0	0	0.00980	0.990
            I2	0	0	0	0	0	0	0	0.5	0.5
            E	0	0	0	0	0	0	0	0	0
            --------
                A	B
            S	0	0
            I0	0.5	0.5
            M1	0.990	0.00980
            D1	0	0
            I1	0.5	0.5
            M2	0.990	0.00980
            D2	0	0
            I2	0.5	0.5
            E	0	0
        """.trimIndent()

        val dStruct = createFromInputString(inputData.lines().toMutableList())
        val expectedStruct = createExpectedOutputDataStructure(dStruct.statesCharList, expectedResultsString)
        val result = hmmp.createHMMprofileWithPseudocounts(
            dStruct.threshold,
            dStruct.pseudocountSigma,
            dStruct.statesCharList,
            dStruct.alignmentStringList
        )
        //checkResultingOutputDataStructure(expectedStruct, result)

        val outputMessagesFilePath = "zzhiddenMarkov.txt"

        val outFile = File(outputMessagesFilePath)
        val writer = outFile.bufferedWriter()

        uglyPrintTransitionMatrix(writer)
        writer.write("--------\n")
        uglyPrintEmissionsMatrix(writer)
        writer.close()
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