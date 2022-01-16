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
}