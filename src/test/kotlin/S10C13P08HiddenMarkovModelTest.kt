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


internal class S10C05P08HiddenMarkovTest {

    lateinit var hmm: HiddenMarkovModelsHMM

    @BeforeEach
    fun setUp() {
        hmm = HiddenMarkovModelsHMM()
    }

    /**
     * Stepik: https://stepik.org/lesson/240399/step/8?unit=212745
     * Rosalind: https://rosalind.info/problems/ba10a/
     */

    /**

    Code Challenge: Solve the Probability of a Hidden Path Problem.

    Input: A hidden path π followed by the states States and
    transition matrix Transition of an HMM (Σ, States, Transition, Emission).

    Output: The probability of this path, Pr(π).


     */

    @Test
    @DisplayName("Hidden Markov Simple 01 Test")
    fun hiddenMarkovSimple01Test() {
        val pathPi = "ABABB"
        val matrixList = """
            A	0.377	0.623
            B	0.26	0.74
        """.trimIndent().lines()
        val matrix = parseMatrix(matrixList)

        val result = hmm.runMarkovModel(pathPi, matrixList.size, matrix)

        //println(String.format("%.8g", result))

        val expectedReslt = 0.0373380098
        assertEquals(expectedReslt, result, 0.0001)
    }

    @Test
    @DisplayName("Hidden Markov Simple 02 Test")
    fun hiddenMarkovSimple02Test() {
        val pathPi = "BCAACAADD"
        val matrixList = """
            A	0.1	0	0.3	0.6
            B	0.5	0.4	0.1	0
            C	0.3	0.3	0.3	0.1
            D	0.2	0.5	0.1	0.2
        """.trimIndent().lines()
        val matrix = parseMatrix(matrixList)

        val result = hmm.runMarkovModel(pathPi, matrixList.size, matrix)

        //println(String.format("%.8g", result))

        val expectedReslt = 8.10e-07
        assertEquals(expectedReslt, result, 0.01e-07)
    }

    @Test
    @DisplayName("Hidden Markov Sample Test")
    fun hiddenMarkovSampleTest() {
        val pathPi = "AABBBAABABAAAABBBBAABBABABBBAABBAAAABABAABBABABBAB"
        val matrixList = """
            A   0.194   0.806
            B   0.273   0.727
        """.trimIndent().lines()
        val matrix = parseMatrix(matrixList)

        val result = hmm.runMarkovModel(pathPi, matrixList.size, matrix)

        //println(String.format("%.8g", result))

        val expectedReslt = 5.01732865318e-19
        assertEquals(expectedReslt, result, expectedReslt/0.001)
    }

    @Test
    @DisplayName("Hidden Markov Rosalind Quiz Test")
    fun hiddenMarkovRosalindQuizTest() {
        val pathPi = "BBABAABBABBAABBAABBAABBABAABBAABBBABBABBAABABAABAA"
        val matrixList = """
            A	0.605	0.395	
            B	0.587	0.413
        """.trimIndent().lines()
        val matrix = parseMatrix(matrixList)

        val result = hmm.runMarkovModel(pathPi, matrixList.size, matrix)

        //println(String.format("%.8g", result))

        val expectedReslt = 2.4668901e-16
        assertEquals(expectedReslt, result, expectedReslt/0.001)
    }

    @Test
    @DisplayName("Hidden Markov Stepik Quiz Test")
    fun hiddenMarkovStepikQuizTest() {
        val pathPi = "ABAABBBAABBBABBBBAABBBBBAABBBBBABBBBABABBBAABBAABA"
        val matrixList = """
            A	0.898	0.102	
            B	0.416	0.584
        """.trimIndent().lines()
        val matrix = parseMatrix(matrixList)

        val result = hmm.runMarkovModel(pathPi, matrixList.size, matrix)

        //println(String.format("%.8g", result))

        val expectedReslt = 2.6177390e-21
        assertEquals(expectedReslt, result, expectedReslt/0.001)
    }

    private fun parseMatrix(strList: List<String>): D2Array<Double> {
        val size = strList.size
        val arr = mk.d2array(size, size) { 0.0 }
        for (i in 0 until size) {
            val tokens = strList[i].split("\\s+".toRegex())
            for (j in 0 until size) {
                arr[i, j]  = tokens[j+1].toDouble()
            }
        }
        return arr
    }
}