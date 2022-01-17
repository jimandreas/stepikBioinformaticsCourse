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


internal class S10C13P10HiddenMarkovOutcomeProbabilityTest {

    lateinit var hmm: HiddenMarkovModelsHMM

    @BeforeEach
    fun setUp() {
        hmm = HiddenMarkovModelsHMM()
    }

    /**
     * Stepik: https://stepik.org/lesson/240399/step/10?unit=212745
     * Rosalind: https://rosalind.info/problems/ba10b/
     */

    /**

    Given: A string [emissionStringx],
    followed by the alphabet Σ from which x was constructed,
    followed by a hidden path π [pathPi], followed by the
    states States and emission [matrix] Emission of an HMM (Σ, States, Transition, Emission).

    Return: The conditional probability Pr(x|π) that string x will be emitted by the HMM given the hidden path π.
     */

    val dataFormat = """
0    zzzyx
1    --------
2    x y z
3    --------
4    BAAAA
5    --------
6    A B
7    --------
8    x	y	z
9    A	0.176	0.596	0.228
10   B	0.225	0.572	0.203

    """.trimIndent()

    data class InputData(
        var emissionStringx: String,
        var emissionCharList: List<String>,
        var pathPi: String,
        var pathCharList: List<String>,
        var emissionMatrix: D2Array<Double>
    )

    private fun createFromInputString(inputStringList: List<String>): InputData {
        val ws = "\\s+".toRegex() // white space regular expression
        val emissionStringx = inputStringList[0]
        val emissionCharList = inputStringList[2].split(ws)
        val pathPi = inputStringList[4]
        val pathCharList = inputStringList[6].split(ws)

        val numRows = pathCharList.size
        val numCols = emissionCharList.size
        val arr = mk.d2array(numRows, numCols) { 0.0 }
        for (i in 9 until 9 + numRows) {
            val tokens = inputStringList[i].split(ws)
            for (j in 0 until numCols) {
                arr[i-9, j]  = tokens[j+1].toDouble()
            }
        }

        return InputData(
            emissionStringx =  emissionStringx,
            emissionCharList = emissionCharList,
            pathPi = pathPi,
            pathCharList = pathCharList,
            emissionMatrix = arr
        )

    }

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Simple 01 Test")
    fun probOutcomeHiddenPathSimple01Test() {
        val testInput = """
            zzzyx
            --------
            x y z
            --------
            BAAAA
            --------
            A B
            --------
            	x	y	z
            A	0.176	0.596	0.228
            B	0.225	0.572	0.203
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.calculateEmissionProbabilty(
            d.emissionStringx,
            d.emissionCharList,
            d.pathPi,
            d.pathCharList,
            d.emissionMatrix
        )

        //println(String.format("%.8g", result))

        val expectedResult = 0.001106941474
        assertEquals(expectedResult, result, expectedResult / 1000)
    }

    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Extra Dataset Test")
    fun probOutcomeHiddenPathExtraDatasetTest() {
        val testInput = """
            zyyyxzxzyyzxyxxyyzyzzxyxyxxxxzxzxzxxzyzzzzyyxzxxxy
            --------
            x y z
            --------
            BAABBAABAABAAABAABBABBAAABBBABBAAAABAAAABBAAABABAA
            --------
            A B
            --------
                x	y	z
            A	0.093	0.581	0.325	
            B	0.77	0.21	0.02
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.calculateEmissionProbabilty(
            d.emissionStringx,
            d.emissionCharList,
            d.pathPi,
            d.pathCharList,
            d.emissionMatrix
        )

        //println(String.format("%.8g", result))

        val expectedResult = 3.42316482177e-35
        assertEquals(expectedResult, result, expectedResult / 1000)
    }


    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Rosalind Quiz Test")
    fun probOutcomeHiddenPathRosalindQuizTest() {
        val testInput = """
            yzxzzyzzzzxxyxyxzxxxyyzzzyxyxyyyxzxyzyzxxyzzxzxzxx
            --------
            x	y	z
            --------
            ABBBABAAABBBBAAAAABBBABBBAABABAABBABBBABBAAAAAABBA
            --------
            A	B
            --------
                x	y	z
            A	0.304	0.198	0.498	
            B	0.364	0.057	0.579
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.calculateEmissionProbabilty(
            d.emissionStringx,
            d.emissionCharList,
            d.pathPi,
            d.pathCharList,
            d.emissionMatrix
        )

        //println(String.format("%.8g", result))

        val expectedResult = 7.9933013e-28 // accepted answer
        assertEquals(expectedResult, result, expectedResult / 1000)
    }


    @Test
    @DisplayName("Probability of an Outcome Given a Hidden Path Stepik Quiz Test")
    fun probOutcomeHiddenPathStepikQuizTest() {
        val testInput = """
            xxyzyxyyzxyyxxyyzzxxzzzzzxzxyzyyxxzyxzyzzyzzxzyyyx
            --------
            x y z
            --------
            AABBABABBBBBAAABABBABBBBABAAABABAABAABBAAAAAABAABA
            --------
            A B
            --------
                x	y	z
            A	0.12	0.711	0.169	
            B	0.266	0.376	0.358
        """.trimIndent().lines()

        val d = createFromInputString(testInput)

        val result = hmm.calculateEmissionProbabilty(
            d.emissionStringx,
            d.emissionCharList,
            d.pathPi,
            d.pathCharList,
            d.emissionMatrix
        )

        //println(String.format("%.8g", result))

        val expectedResult = 3.3633313e-28 //accepted answer
        assertEquals(expectedResult, result, expectedResult / 1000)
    }

}