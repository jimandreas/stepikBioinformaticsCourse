@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment",
    "VARIABLE_WITH_REDUNDANT_INITIALIZER"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set

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
     * Profile HMMs for Sequence Alignment (6/10)
     * https://www.youtube.com/watch?v=vO_6xfLwGao&list=PLQ-85lQlPqFPnk31Uut2ajVkBvlFmMtdx&index=6
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

    /**

    Code Challenge: Implement the Viterbi algorithm solving the Decoding Problem.

    Input: A string x, followed by the alphabet from which x was constructed,
    followed by the states States, transition matrix Transition, and emission
    matrix Emission of an HMM (Σ, States, Transition, Emission).

    Output: A path that maximizes the (unconditional) probability Pr(x, π) over all possible paths π.

     */

    var winningProbability = 0.0
    fun viterbiBestPath(
        emissionStringx: String,
        emissionCharList: List<String>,
        statesCharList: List<String>,
        transitionMatrix: D2Array<Double>,
        emissionMatrix: D2Array<Double>
    ): String {

        val elen = emissionStringx.length
        val states = statesCharList.size

        val viterbiMatrix = mk.d2array(states, elen) { 0.0 }
        val backtrackMatrix = mk.d2array(states, elen) { -1 }

        for (i in 0 until elen) {  // which letter in the emitted string (horizontal)

            val emissionChar = emissionStringx[i]
            val emissionIndex = emissionCharList.indexOf(emissionChar.toString())

            for (j in 0 until states) {  // which letter, A = 0, B = 1 (vertical)

                // state to state probs array for a given alphabet letter {A, B, ...}
                //   e.g. 0: A to A, 1: B to A, 2: C to A, etc
                val transitionValues = transitionMatrix[0..states, j]

                // the previous base probability for a given column (i-1) (emissionIndex)
                //  and rows (A, B, C, ...)  (initially 0.5 for all at start)
                val previousProb = mk.d1array(states) { 0.0 }
                for (stateIndex in 0 until states) {
                    previousProb[stateIndex] =
                        if (i == 0) {
                            0.5
                        } else {
                            viterbiMatrix[stateIndex, i - 1]
                        }
                }

                var maxProb = 0.0
                var maxRowIndex = -1
                // now find the max for the state at this emission letter position
                for (stateIndex in 0 until states) {
                    val previous = previousProb[stateIndex]
                    val transition = if (i == 0) {
                        1.0
                    } else {
                        transitionValues[stateIndex]
                    }
                    // prob for this state (A, B, C, ...) to emit the emissionChar (as emissionIndex)
                    val emissionProb = emissionMatrix[j, emissionIndex]

                    val resultForThisRow = previous * transition * emissionProb

                    if (resultForThisRow > maxProb) {
                        maxProb = resultForThisRow
                        maxRowIndex = stateIndex
                    }
                }

                // OK we have a winner for this row / emission letter.  Record it.

                viterbiMatrix[j, i] = maxProb
                backtrackMatrix[j, i] = maxRowIndex
            }
        }

        /*
         *  The max probabilities for each cell in the viterbi matrix are now set.
         *    Find the max prob in last column, then use the backtrack indexes
         *  to reverse traverse the matrix
         */

        val str = StringBuilder()

        // find the max

        var maxProb = 0.0
        var maxRow = -1
        for (row in 0 until states) {
            val cur = viterbiMatrix[row, elen-1]
            if (cur > maxProb) {
                maxProb = cur
                maxRow = row
            }
        }
        val winningState = statesCharList[maxRow]
        str.append(winningState)

        for (col in elen - 1 downTo 1) {
            val nextRow = backtrackMatrix[maxRow, col]
            val nextLetter = statesCharList[nextRow]
            str.insert(0, nextLetter)
            maxRow = nextRow
        }

        winningProbability = maxProb // the best prob at the end of the matix
        return str.toString()
    }
}