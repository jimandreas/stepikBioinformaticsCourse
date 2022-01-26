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
import kotlin.math.exp
import kotlin.test.assertEquals


internal class S10C07P04ViterbiOutcomeLikelyhoodTest {

    lateinit var hmm: HiddenMarkovModelsHMM

    @BeforeEach
    fun setUp() {
        hmm = HiddenMarkovModelsHMM()
    }

    /**
     * https://stepik.org/lesson/240401/step/4?unit=212747
     * https://rosalind.info/problems/ba10d/
     */

    /**

    Outcome Likelihood Problem

    Given: A string x, followed by the alphabet Σ from which x
    was constructed, followed by the states States, transition
    matrix Transition, and emission matrix Emission of an
    HMM (Σ, States, Transition, Emission).

    Return: The probability Pr(x) that the HMM emits x.
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
    @DisplayName("Probability of an Outcome Given a Hidden Path Sample Test")
    fun probOutcomeHiddenPathSampleTest() {
        val testInput = """
xzyyzzyzyy
--------
x   y   z
--------
A   B
--------
    A   B
A   0.303   0.697 
B   0.831   0.169 
--------
    x   y   z
A   0.533   0.065   0.402 
B   0.342   0.334   0.324
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiOutcomeLikelihood(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)

        val expectedResult = 1.1005510319694847e-06
        assertEquals(expectedResult, result, expectedResult / 1000.0)

    }

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Sample2 Test")
    fun probOutcomeHiddenPathSample2Test() {
        val testInput = """
xzyyz
--------
x   y   z
--------
A   B
--------
    A   B
A   0.303   0.697 
B   0.831   0.169 
--------
    x   y   z
A   0.533   0.065   0.402 
B   0.342   0.334   0.324
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiOutcomeLikelihood(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(String.format("%6.3e", result))

        val expectedResult = 1.544e-03
        assertEquals(expectedResult, result, expectedResult / 100.0)

    }

    /*
This dataset makes sure that your code does not assign a
non-zero likelihood to impossible outputs. This dataset
is incapable of emitting a z character, even though it
is in the alphabet of the HMM. Since the string x has a
z character in it the likelihood of emitting x should be zero.
If your code outputs a non-zero likelihood you are likely
failing to apply the emission probabilities for the character z.
     */
    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Dataset3 Test")
    fun probOutcomeHiddenPathDataset3Test() {
        val testInput = """
xzywyxw
--------
w x y z
--------
A B C
--------
	A	B	C
A	0.7	0.1	0.2	
B	0.5	0.3	0.2
C	0.1	0.4	0.5
--------
	w	x	y	z
A	0.34	0.24	0.42	0
B	0.17	0.49	0.34	0
C	0.22	0.22	0.56	0
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiOutcomeLikelihood(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(String.format("%6.3e", result))

        val expectedResult = 0.0
        assertEquals(expectedResult, result, expectedResult / 100.0)

    }

    /*
This dataset checks to make sure your output is to at
least three significant figures. This is not the same
as three digits past the decimal point. If your output
is incorrect make sure that your code doesn’t round the
final answer to three digits past the decimal point.
Scientific notation using a lowercase e (as in the example output)
is accepted. Do not output scientific notation in the style
of 9.17*10-09 or 9.17x10-09.
     */

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Dataset4 Test")
    fun probOutcomeHiddenPathDataset4Test() {
        val testInput = """
xxxxyxxxzz
--------
x y z
--------
A B C
--------
	A	B	C
A	0.7	0.1	0.2	
B	0.5	0.3	0.2
C	0.1	0.4	0.5
--------
	x	y	z
A	0.24	0.41	0.01
B	0.49	0.33	0.01
C	0.22	0.55	0.01
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiOutcomeLikelihood(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)
        //println(String.format("%6.3e", result))

        val expectedResult = 9.17e-09
        assertEquals(expectedResult, result, expectedResult / 100.0)

    }

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Rosalind Quiz Test")
    fun probOutcomeHiddenPathRosalindQuizTest() {
        val testInput = """
xxyyyyxzyzzyzxyzyxyxzyzyyzyxxxyzzxxxxzyzyxxxzzyzxyxxxyzyzyzxzzxxzzzyyyyyzyxzxxzxyzyyyxyyzyyzxyzyxyyz
--------
x	y	z
--------
A	B
--------
	A	B
A	0.371	0.629	
B	0.793	0.207	
--------
	x	y	z
A	0.23	0.243	0.527	
B	0.308	0.334	0.359
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiOutcomeLikelihood(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)
        //println(String.format("%6.3e", result))

        // accepted answer
        val expectedResult = 4.574e-50
        assertEquals(expectedResult, result, expectedResult / 100.0)

    }

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Stepik Quiz Test")
    fun probOutcomeHiddenPathStepikQuizTest() {
        val testInput = """
yzzyyyzyyzxyyyxzyzxxyyyxyzzyxzxxyzxyzxxxzxzzzxxxyyyzzxzzyzyyxxzzyxzzxxyyyyxyyxxzxxzxxxyxxzxzxyzzyxyy
--------
x y z
--------
A B C D
--------
	A	B	C	D
A	0.108	0.495	0.346	0.051	
B	0.085	0.194	0.215	0.506	
C	0.237	0.146	0.277	0.34	
D	0.199	0.594	0.18	0.027	
--------
	x	y	z
A	0.368	0.313	0.319	
B	0.591	0.073	0.336	
C	0.025	0.485	0.49	
D	0.175	0.453	0.372
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.viterbiOutcomeLikelihood(
            d.emissionStringx,
            d.emissionCharList,
            d.statesCharList,
            d.transitionMatrix,
            d.emissionMatrix
        )

        //println(result)
        //println(String.format("%6.3e", result))

        // accepted answer
        val expectedResult = 2.809e-49
        assertEquals(expectedResult, result, expectedResult / 100.0)

    }

}