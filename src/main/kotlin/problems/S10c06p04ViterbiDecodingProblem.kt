@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

package problems

import algorithms.HiddenMarkovModelsHMM
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.set

/**
 *

stepik: https://stepik.org/lesson/240400/step/4?unit=212746

Every hidden path π in the HMM now corresponds to a
path from source to sink in the Viterbi graph with
product weight Pr(x, π). Therefore, the Decoding Problem
reduces to finding a path in the Viterbi graph of largest
product weight over all paths connecting source to sink.

Exercise Break: Find the maximum product weight path in the
Viterbi graph for the crooked dealer HMM (whose HMM diagram
is reproduced below) when x = “HHTT”.  Express your answer
as a string of four "F" and "B" symbols.
 */

fun main() {

    val s = Setup()
    val hmm = HiddenMarkovModelsHMM()

    val testInput = """
            HHTT
            --------
            H T
            --------
            F B
            --------
                F	B
            F	0.9 0.1
            B	0.1 0.9
            --------
                H       T
            F	0.5     0.5	
            B	0.75    0.25
        """.trimIndent().lines()

    val d = s.createFromInputString(testInput)

    val result = hmm.viterbiBestPath(
        d.emissionStringx,
        d.emissionCharList,
        d.statesCharList,
        d.transitionMatrix,
        d.emissionMatrix
    )

    println(result)

    if (result == "FFFF") {
        println("Correct!!")
    }
}


private class Setup {

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

    fun createFromInputString(inputStringList: List<String>): InputData {
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
}
