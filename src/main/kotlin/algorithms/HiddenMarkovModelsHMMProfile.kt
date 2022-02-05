@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment",
    "VARIABLE_WITH_REDUNDANT_INITIALIZER", "MoveVariableDeclarationIntoWhen", "KotlinConstantConditions"
)

package algorithms

import algorithms.HiddenMarkovModelsHMMProfile.ColType.*
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import java.lang.Integer.max
import java.lang.StringBuilder

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

    // the matrices - t - transitions, tCount - count of occurrences, e - emissions, percent of char
    lateinit var t: D2Array<Double>
    lateinit var tCount: D2Array<Int>
    lateinit var e: D2Array<Double>

    // the column classifications of each transition letter column and lookahead state
    enum class ColType { BEGIN, MATCH, INSERT, DELETE, END }

    // save the percentage of each symbol in a column
    lateinit var percentScan: D2Array<Double>

    var numColumns = 0  // how many letters in each string in theStringList
    var numRows = 0     // how many strings in theStringList
    var theStringList: List<CharArray> = mutableListOf()

    val matchColumnsList: MutableList<Int> = mutableListOf()

    /**
     * traverse each row in theStringList
     *   maintain a state machine to count the occurrences of each state
     */
    fun countOccurrences() {

        for (row in 0 until numRows) {
            var state = BEGIN
            var tCountCol = 0

            // for basing which group of Insert / Match / Delete counters to use:
            var matchGroup = 0
            var matchCol = matchColumnsList[0]

            // iterate through all columns for this row
            var col = 0
            // IMPORTANT - the counts are INSERT REPEATINSERT MATCH DELETE (offsets 0, 1, 2, 3)
            do {
                val matchIndex = matchColumnsList.indexOf(col)
                if (matchColumnsList.contains(col)) {
                    state = MATCH

                    if (theStringList[row][col] != '-') {
                        tCount[row, matchGroup * 4 + 2]++  // increment the match count
                    } else { // it is a delete character
                        tCount[row, matchGroup * 4 + 3]++  // increment the delete count
                    }
                    // advance to next match column for comparisons
                    matchGroup++
                    if (matchColumnsList.size > matchGroup) {
                        matchCol = matchColumnsList[matchGroup]
                    }
                    col++
                } else {
                    if (col < matchCol) {
                        state = INSERT
                        // count the insert characters
                        if (theStringList[row][col] != '-') {
                            tCount[row, matchGroup * 4 + 0]++ // increment the insert count
                        } else {
                            col++
                            continue
                        }
                        col++
                        while (col < numColumns && col < matchCol) {
                            if (theStringList[row][col] != '-') {
                                tCount[row, matchGroup * 4 + 1]++ // increment the REPEAT insert count
                            }
                            col++
                        }
                    }
                }
            } while (col < numColumns)
        }

    }

    /**
     * scan the columns of the strings in theStringList
     * Record the percentage of each symbol in each column of the alignment string set.
     *
     * If the percentage of ('-') characters is under the [threshold]
     * then add the column index to the matchColumnsList
     */
    fun scanForMatchColumns(
        threshold: Double,
        statesCharList: List<Char>
    ) {
        // iterate through the alignmentStringList in a column-wise fashion
        for (col in 0 until numColumns) {
            var countOfGapCharacters = 0

            theStringList.map {
                if (it[col] == '-') {
                    countOfGapCharacters++
                }
            }
            // test if this column is a match or insert column
            val percent = countOfGapCharacters.toDouble() / numRows.toDouble()
            if (percent < threshold) {
                matchColumnsList.add(col)
            }
        }
    }


    fun createHMMprofile(
        threshold: Double,
        statesCharList: List<Char>,
        alignStrIn: List<String>
    )/*: HMMTransitionAndEmissionMatrices*/ {

        numColumns = alignStrIn[0].length
        theStringList = alignStrIn.map { it.toCharArray() }
        numRows = theStringList.size
        numColumns = alignStrIn[0].length

        // STEP 1 - scan the alignment strings

        scanForMatchColumns(threshold, statesCharList)

        // STEP 2 - allocate the transitions and emissions matrices (t and e)

        val numPercentageRows = max(matchColumnsList.size, 1) * 3 + 3
        t = mk.d2array(numPercentageRows, numPercentageRows) { 0.0 }
        e = mk.d2array(numPercentageRows, statesCharList.size) { 0.0 }

        // this is the array to accumulate state counts
        // one row per row in the alignment strings
        //    4 columns per character INSERT INSERTREPEAT MATCH DELETE
        val numCountColumns = max(matchColumnsList.size, 1) * 4
        tCount = mk.d2array(numRows, numCountColumns) { 0 }

        // STEP 3 - increment the HMM states

        countOccurrences()
        prettyPrintCountMatrix()
    }

    fun prettyPrintCountMatrix() {
        val str = StringBuilder()

        // header
        for (i in 0 until matchColumnsList.size) {
            str.append("I$i rI$i M$i D$i ")
        }
        str.append("\n")

        for (row in 0 until numRows) {
            str.append(" ")
            for (i in 0 until matchColumnsList.size) {
                for (j in 0 until 4) {
                    str.append(tCount[row, i*4 + j])
                    str.append("  ")
                }
            }
            str.append("\n")
        }
        println(str.toString())
    }
}


