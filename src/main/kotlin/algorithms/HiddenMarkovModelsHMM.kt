@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment",
    "VARIABLE_WITH_REDUNDANT_INITIALIZER"
)

package algorithms

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get

class HiddenMarkovModelsHMM {

    /**
     * See also:
     * Youtube:
     * The Decoding Problem (4/10)
     * https://www.youtube.com/watch?v=Xr0zDtbeLik&list=PLQ-85lQlPqFPnk31Uut2ajVkBvlFmMtdx&index=4
     *
     * The Viterbi Algorithm (5/10)
     * https://www.youtube.com/watch?v=0dVUfYF8ko0&list=PLQ-85lQlPqFPnk31Uut2ajVkBvlFmMtdx&index=5
     *
     * Wikipedia:
     * https://en.wikipedia.org/wiki/Viterbi_algorithm
     */

    fun runMarkovModel(pathPi: String, stateCount: Int, matrix: D2Array<Double>): Double {
        var prob = 1.0 / stateCount.toDouble()

        var currentState = pathPi[0] - 'A'

        for (i in 1 until pathPi.length) {
            val newState = pathPi[i] - 'A'

            val nextProb = matrix[currentState, newState]

            prob *= nextProb

            currentState = newState
        }

        return prob
    }

    /**

     Given: A string [emissionStringx],
     followed by the alphabet Σ from which x was constructed,
     followed by a hidden path π [pathPi], followed by the
     states States and emission [matrix] Emission of an HMM (Σ, States, Transition, Emission).

     Return: The conditional probability Pr(x|π) that string x will be emitted by the HMM given the hidden path π.
     */
    fun calculateEmissionProbabilty(
        emissionStringx: String,
        emissionCharList: List<String>,
        pathPi: String,
        pathCharList: List<String>,
        matrix: D2Array<Double>
    ): Double {

        // there is no initial prob -
        // calculate only probs of emission characters
        //var prob = 1.0 / stateCount.toDouble()


        val baseState = 'A'
        val baseEmission = 'x'

        var prob = 1.0

        for (i in 0 until pathPi.length) {

            val curStateIndex = pathPi[i] - baseState
            val emissionCharIndex = emissionStringx[i] - baseEmission

            val emissionProb = matrix[curStateIndex, emissionCharIndex]

            prob *= emissionProb
        }

        return prob
    }
}