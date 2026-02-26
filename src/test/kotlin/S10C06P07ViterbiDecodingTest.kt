@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.HiddenMarkovModelsHMM
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


internal class S10C06P07ViterbiDecodingTest {

    lateinit var hmm: HiddenMarkovModelsHMM

    @BeforeEach
    fun setUp() {
        hmm = HiddenMarkovModelsHMM()
    }

    /**
     * https://rosalind.info/problems/ba10c/
     */

    /**

    Decoding Problem

    Given: A string x, followed by the alphabet Σ from which x was constructed,
    followed by the states States, transition matrix Transition, and emission
    matrix Emission of an HMM (Σ, States, Transition, Emission).

    Return: A path that maximizes the (unconditional) probability Pr(x, π)
    over all possible paths π.
     */

    val dataFormat = """
0   xyxzzxyxyy
1   --------
2   x y z
3   --------
4   A B
5   --------
6   	A	B
7   A	0.641	0.359
8   B	0.729	0.271
9   --------
10  	x	y	z
11  A	0.117	0.691	0.192	
12  B	0.097	0.42	0.483
    """.trimIndent()

    data class InputData(
        var emissionStringx: String,
        var emissionCharList: List<String>,
        var statesCharList: List<String>,
        var transitionMatrix: D2Array<Double>,
        var emissionMatrix: D2Array<Double>
    )

    private fun createFromInputString(inputStringList: List<String>): InputData {
        val ws = "\\s+".toRegex() // white space regular expression
        val emissionStringx = inputStringList[0]
        val emissionCharList = inputStringList[2].split(ws)
        val statesCharList = inputStringList[4].split(ws)
        val numStates = statesCharList.size

        val numColumnsEmissions = emissionCharList.size
        val transistionMatrix = mk.d2array(numStates, numStates) { 0.0 }
        val baseStates = 7
        for (i in baseStates until baseStates + numStates) {
            val tokens = inputStringList[i].split(ws)
            for (j in 0 until numStates) {
                transistionMatrix[i-baseStates, j]  = tokens[j+1].toDouble()
            }
        }

        val baseEmissionProbs = baseStates + numStates + 2
        val emissionMatrix = mk.d2array(numStates, numColumnsEmissions) { 0.0 }
        for (i in baseEmissionProbs until baseEmissionProbs + numStates) {
            val tokens = inputStringList[i].split(ws)
            for (j in 0 until numColumnsEmissions) {
                emissionMatrix[i-baseEmissionProbs, j]  = tokens[j+1].toDouble()
            }
        }

        return InputData(
            emissionStringx =  emissionStringx,
            emissionCharList = emissionCharList,
            statesCharList = statesCharList,
            transitionMatrix = transistionMatrix,
            emissionMatrix = emissionMatrix
        )
    }

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Simple 01 Test")
    fun probOutcomeHiddenPathSimple01Test() {
        val testInput = """
            xyxzz
            --------
            x y z
            --------
            A B
            --------
                A	B
            A	0.641	0.359
            B	0.729	0.271
            --------
                x	y	z
            A	0.117	0.691	0.192	
            B	0.097	0.42	0.483
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiBestPath(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)

        val expectedResult = "AAABA"
        assertEquals(expectedResult, result)

    }

    /*
This dataset makes sure that your code is correctly
using emission probabilities when determining the
optimal path for an emitted string. All transition
probabilities are equal, so the optimal path is dependent
on the emission probabilities at each state. If your code
does not output the correct answer for this dataset it is
likely that your code is incorrectly using the input emission
probability matrix.
Make sure that your recurrence is
〖max(〗⁡〖〖dp〗_(i,j-1) 〗*〖transition〗_(i,s)*〖emission〗_(s,k)  )
for state s and character k. Note that the emission probability used
is the probability of the current state emitting the current character,
not the previous state emitting the current or previous character.
Mixing up which emission probability to use is a common mistake.
     */

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Dataset1 Test")
    fun probOutcomeHiddenPathDataset1Test() {
        val testInput = """
xyxy
--------
x y
--------
A B
--------
	A	B
A	0.5	0.5	
B	0.5	0.5
--------
	x	y
A	0.1	0.9	
B	0.9	0.1
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiBestPath(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)

        val expectedResult = "BABA"
        assertEquals(expectedResult, result)

    }

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Dataset2 Test")
    fun probOutcomeHiddenPathDataset2Test() {
        val testInput = """
xyxy
--------
x y
--------
A B
--------
	A	B
A	0.9	0.1	
B	0.1	0.9
--------
	x	y
A	0.5	0.5	
B	0.5	0.5
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiBestPath(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)

        val expectedResult = "AAAA"
        assertEquals(expectedResult, result)

    }

    /*
This dataset makes sure that your code is not using a
greedy approach to finding the optimal path for the
given string x. Greedy approaches that pick the state
that maximizes the output probability will first choose
state C, since it is guaranteed to emit an x character.
The only possible transition from state C is to state A,
which cannot emit an x character, so the final path CA
from a greedy algorithm will have a 0 probability of
emitting xx. If your code outputs CA instead of BC it
is likely that you are implementing a greedy algorithm.
     */

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Dataset5 Test")
    fun probOutcomeHiddenPathDataset5Test() {
        val testInput = """
xx
--------
x y
--------
A B C
--------
	A	B	C
A	0.7	0.1	0.2	
B	0.5	0.3	0.2
C	1	0	0
--------
	x	y
A	0	1	
B	0.5	0.5
C	1	0
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiBestPath(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)

        val expectedResult = "BC"
        assertEquals(expectedResult, result)

    }

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Extra Dataset Test")
    fun probOutcomeHiddenPathExtraDatasetTest() {
        val testInput = """
zxxxxyzzxyxyxyzxzzxzzzyzzxxxzxxyyyzxyxzyxyxyzyyyyzzyyyyzzxzxzyzzzzyxzxxxyxxxxyyzyyzyyyxzzzzyzxyzzyyy
--------
x y z
--------
A B
--------
	A	B
A	0.634	0.366	
B	0.387	0.613	
--------
	x	y	z
A	0.532	0.226	0.241	
B	0.457	0.192	0.351
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiBestPath(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)

        val expectedResult = "AAAAAAAAAAAAAABBBBBBBBBBBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBAAA"
        assertEquals(expectedResult, result)

    }

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Rosalind Quiz Test")
    fun probOutcomeHiddenPathRosalindQuizTest() {
        val testInput = """
zxxxxzzxxxyyyzyzyzxxzzxyzzxzzzxzxxxyzxxzxzzyxyzzzzyxxxyyxyxyxzzzzxxzyxxzxzzxzzxyyxyxyyxzyyxxyzyxxyzy
--------
x	y	z
--------
A	B	C
--------
	A	B	C
A	0.176	0.14	0.684	
B	0.244	0.176	0.58	
C	0.491	0.48	0.029	
--------
	x	y	z
A	0.377	0.426	0.196	
B	0.11	0.835	0.055	
C	0.569	0.212	0.218
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiBestPath(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)

        // accepted answer
        val expectedResult = "ACACACACACBCBCBCBACACACBCACACACACACBCACACACBCBACACBCACBBCBCBCACACACACACACACACACBBCBCBCACBCACBCBACBCB"
        assertEquals(expectedResult, result)

    }

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Stepik Quiz Test")
    fun probOutcomeHiddenPathStepikQuizTest() {
        val testInput = """
zzyzxyxyxyzyzzxzxxyzyxzzzzyxyyxxyxxxzxxyxxxzyyyxxyyzyyyzzzyzyxxyyxxzzyzzyxyxyxxyzxzzzzyyxyzyzyyxxzzz
--------
x y z
--------
A B C D
--------
	A	B	C	D
A	0.441	0.255	0.279	0.025	
B	0.374	0.225	0.27	0.131	
C	0.219	0.322	0.306	0.153	
D	0.278	0.625	0.079	0.018	
--------
	x	y	z
A	0.395	0.326	0.279	
B	0.25	0.017	0.733	
C	0.31	0.672	0.018	
D	0.737	0.185	0.078
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiBestPath(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)

        // accepted answer
        val expectedResult = "BBCBAAAAACBCBBDBAACBCDBBBBAACCDBCDBDBAACDBDBCCCDBCCBCCCBBBCBAAACCCDBBCBBAAAAAAACBDBBBBCCCCBCBCCCDBBB"
        assertEquals(expectedResult, result)

    }

}