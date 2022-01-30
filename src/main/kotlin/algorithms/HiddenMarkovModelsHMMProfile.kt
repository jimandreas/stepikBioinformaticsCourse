@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment",
    "VARIABLE_WITH_REDUNDANT_INITIALIZER"
)

package algorithms

class HiddenMarkovModelsHMMProfile {

    /**
     * See also:
     * Youtube:
     * Profile HMMs for Sequence Alignment (6/10)
     * https://www.youtube.com/watch?v=vO_6xfLwGao&list=PLQ-85lQlPqFPnk31Uut2ajVkBvlFmMtdx&index=6
     *
     * Wikipedia:
     * https://en.wikipedia.org/wiki/Viterbi_algorithm
     */

    data class HMMTransitionAndEmissionMatrices(
        val symbols: List<Char>,
        val states: List<String>,
        val transitions: List<List<Double>>,
        val emissions: List<List<Double>>
    )

    fun createHMMprofile(
        threshold: Double,
        statesCharList: List<Char>,
        alignmentStringList: List<String>
    )/*: HMMTransitionAndEmissionMatrices*/ {

        val matchColumnList = scanForMatchColumns(threshold, statesCharList, alignmentStringList)
        println(matchColumnList)
    }

    fun scanForMatchColumns(
        threshold: Double,
        statesCharList: List<Char>,
        alignmentStringList: List<String>
    ): List<Int> {
        val numColumns = alignmentStringList[0].length

        val matchColumnsList : MutableList<Int> = mutableListOf()
        for (i in 0 until numColumns) {
            var countOfNonGapCharacters = 0
            val count = alignmentStringList.map {
                if (it[i] != '-') {
                    countOfNonGapCharacters++
                }
            }
            if (countOfNonGapCharacters.toDouble() / numColumns.toDouble() >= threshold) {
                matchColumnsList.add(i)
            }
        }
        return matchColumnsList
    }
}