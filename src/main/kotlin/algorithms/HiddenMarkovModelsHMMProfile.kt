@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment",
    "VARIABLE_WITH_REDUNDANT_INITIALIZER"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.rangeTo
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.toMutableList

class HiddenMarkovModelsHMMProfile {

    /**
     * See also:
     * Youtube:
     * Profile HMMs for Sequence Alignment (6/10)
     * https://www.youtube.com/watch?v=vO_6xfLwGao&list=PLQ-85lQlPqFPnk31Uut2ajVkBvlFmMtdx&index=6
     *
     * Wikipedia:
     * https://en.wikipedia.org/wiki/Viterbi_algorithm
     *
     * Uses the Kotlin Multik multidimensional array library
     * @link: https://github.com/Kotlin/multik
     * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
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

        val matchColumnsList: MutableList<Int> = mutableListOf()
        val matchColumnsPercentages: MutableList<Double> = mutableListOf()

        /**
         * Helper functions:
         */
        fun percentOfThisChar(thisChar: Char, indexIntoAlignmentString: Int): Double {
            var countOfThisChar = 0
            val count = alignmentStringList.map {
                if (it[indexIntoAlignmentString] == thisChar) {
                    countOfThisChar++
                }
            }
            val percent = countOfThisChar.toDouble() / alignmentStringList.size.toDouble()
            return percent
        }
        /**
         * fillInMatches- function to fill out the emission probabilities
         *   for a match
         */
        fun fillInMatches(matchColumn: Int, actualColumn: Int, e: D2Array<Double>) {
            val emissionArrayRow = matchColumn * 3 + 2  // which row in the emission matrix to fill in

            for (i in 0 until statesCharList.size) {
                val percentOfThisChar = percentOfThisChar(statesCharList[i], actualColumn)
                e[emissionArrayRow, i] = percentOfThisChar
            }
        }

        /**
         * scan the columns of the strings in [alignmentStringList].
         * If the percentage of ('-') characters is under the [threshold]
         * then add the column index to the matchColumnsList and
         * record the percentage of letters in the column in matchColumnsPercentages
         */
        fun scanForMatchColumns() {
            val numColumns = alignmentStringList[0].length

            for (i in 0 until numColumns) {
                var countOfGapCharacters = 0
                val count = alignmentStringList.map {
                    if (it[i] == '-') {
                        countOfGapCharacters++
                    }
                }
                val percent = countOfGapCharacters.toDouble() / alignmentStringList.size.toDouble()
                if (percent < threshold) {
                    matchColumnsList.add(i)
                    matchColumnsPercentages.add(1.0 - percent)
                }
            }
        }

        // STEP 1 - scan the alignment strings for matches that beat the threshold
        scanForMatchColumns()

        //println(matchColumnsList)

        // STEP 2 - allocate the transitions and emissions matrices (t and e)

        val numRows = matchColumnsList.size * 3 + 3
        val t = mk.d2array(numRows, numRows) { 0.0 }
        val e = mk.d2array(numRows, statesCharList.size) { 0.0 }

        // STEP 3 - iterate matching columns list and add heuristics as necessary

        var baseMatchingColumn = 0
        var currentActualColumn = 0
        for (matchColumn in 0 until matchColumnsList.size) {

            /*
             * when equal then there are no insert letters,
             * but there might be delete probabilities. (?)
             */
            if (matchColumnsList[matchColumn] == baseMatchingColumn) {
                // special case start state
                if (baseMatchingColumn == 0) {
                    val p = matchColumnsPercentages[0]
                    t[0, 2] = p     // M1
                    t[0, 3] = 1 - p // D1

                    fillInMatches(0, currentActualColumn, e)
                }
            }

            baseMatchingColumn = matchColumn
        }

        val foo = t.toMutableList()
        val bar = e[0..1..1]
        println(bar)




    }
}


