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
import org.jetbrains.kotlinx.multik.ndarray.data.rangeTo
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
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
    lateinit var tRepeatInsertCount: D2Array<Int>
    lateinit var e: D2Array<Double>

    lateinit var statesCharList: List<Char>

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

                    val theChar = theStringList[row][col]
                    if (theChar != '-') {
                        tCount[row, matchGroup * 3 + 1]++  // increment the match count
                        e[matchGroup * 3 + 1,statesCharList.indexOf(theChar)]++
                    } else { // it is a delete character
                        tCount[row, matchGroup * 3 + 2]++  // increment the delete count
                        //e[matchGroup * 3 + 2,statesCharList.indexOf(theChar)]++ // no characters in delete emission
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
                        val theChar = theStringList[row][col]
                        if (theChar != '-') {
                            tCount[row, matchGroup * 3 + 0]++ // increment the insert count
                            e[matchGroup * 3 + 1,statesCharList.indexOf(theChar)]++
                        } else {
                            col++
                            continue
                        }
                        col++
                        while (col < numColumns && col < matchCol) {
                            val theChar = theStringList[row][col]
                            if (theChar != '-') {
                                tRepeatInsertCount[row, matchGroup]++ // increment the REPEAT insert count
                                e[matchGroup * 3 + 1,statesCharList.indexOf(theChar)]++
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
        threshold: Double
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
        statesCharListIn: List<Char>,
        alignStrIn: List<String>
    )/*: HMMTransitionAndEmissionMatrices*/ {

        numColumns = alignStrIn[0].length
        theStringList = alignStrIn.map { it.toCharArray() }
        numRows = theStringList.size
        numColumns = alignStrIn[0].length
        statesCharList = statesCharListIn

        // STEP 1 - scan the alignment strings

        scanForMatchColumns(threshold)

        // STEP 2 - allocate the transitions and emissions matrices (t and e)

        val baseGroupCount = max(matchColumnsList.size, 1)
        val numPercentageRows = baseGroupCount * 3 + 3
        t = mk.d2array(numPercentageRows, numPercentageRows) { 0.0 }
        e = mk.d2array(numPercentageRows, statesCharList.size) { 0.0 }

        // this is the array to accumulate state counts
        // one row per row in the alignment strings
        //    3 columns per character INSERT MATCH DELETE plus one more for RepeatInserts
        val numCountColumns = baseGroupCount * 3
        tCount = mk.d2array(numRows, numCountColumns) { 0 }
        tRepeatInsertCount = mk.d2array(numRows, baseGroupCount) { 0 }

        // STEP 3 - increment the HMM states and count emission characters

        countOccurrences()
        prettyPrintCountMatrix()

        // STEP 4 - score transition matrix and emission matrix
        //scoreTransmissionCounts()
        scoreEmissionCounts()
        println(e)
    }

    /**
     * traverse the emission count array, and convert the counts into percentages
     */
    fun scoreEmissionCounts() {
        val numCountRows = max(matchColumnsList.size, 1) * 3 + 2
        val numCountColumns = statesCharList.size
        for (row in 0 until numCountRows) {
            val eRow = e[row, 0 until numCountColumns]
            val sum = eRow.toList().sum()
            if (sum != 0.0) {
                for (col in 0 until numCountColumns) {
                    e[row, col] = e[row, col] / sum
                }
            }
        }
    }

    fun prettyPrintCountMatrix() {
        val str = StringBuilder()

        // header
        for (i in 0 until matchColumnsList.size) {
            str.append("I$i r$i M$i D$i ")
        }
        str.append("\n")

        for (row in 0 until numRows) {
            str.append(" ")
            for (i in 0 until matchColumnsList.size) {
                var col = 0 // have to track index separately due to merging the repeat insert data
                for (j in 0 until 4) {
                    if (j == 1) {
                        str.append(tRepeatInsertCount[row, i])
                    } else {
                        str.append(tCount[row, i*3 + col])
                        col++
                    }
                    str.append("  ")
                }
            }
            str.append("\n")
        }
        println(str.toString())
    }
}


