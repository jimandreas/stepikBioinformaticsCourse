@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment",
    "VARIABLE_WITH_REDUNDANT_INITIALIZER", "MoveVariableDeclarationIntoWhen", "KotlinConstantConditions",
    "UNUSED_ANONYMOUS_PARAMETER", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE"
)

package algorithms

import algorithms.HiddenMarkovModelsHMMProfile.ColType.*
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.sum
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
    var numMatchColumns = 0

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
                        e[matchGroup * 3 + 2, statesCharList.indexOf(theChar)]++
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
                    state = INSERT
                    // count the insert characters
                    val theChar = theStringList[row][col]
                    if (theChar != '-') {
                        tCount[row, matchGroup * 3 + 0]++ // increment the insert count
                        e[matchGroup * 3 + 1, statesCharList.indexOf(theChar)]++
                    } else {
                        col++
                        continue
                    }
                    col++
                    // keep going until we run out of characters or we hit the next match column
                    while (col < numColumns && !matchColumnsList.contains(col)) {
                        val theNextChar = theStringList[row][col]
                        if (theNextChar != '-') {
                            tRepeatInsertCount[row, matchGroup]++ // increment the REPEAT insert count
                            e[matchGroup * 3 + 1, statesCharList.indexOf(theNextChar)]++
                        }
                        col++
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
    ): HMMTransitionAndEmissionMatrices {

        numColumns = alignStrIn[0].length
        theStringList = alignStrIn.map { it.toCharArray() }
        numRows = theStringList.size
        numColumns = alignStrIn[0].length
        statesCharList = statesCharListIn

        // STEP 1 - scan the alignment strings

        scanForMatchColumns(threshold)

        // STEP 2 - allocate the transitions and emissions matrices (t and e)

        numMatchColumns = max(matchColumnsList.size, 1)
        val numPercentageRowsAndColumns = numMatchColumns * 3 + 3
        t = mk.d2array(numPercentageRowsAndColumns, numPercentageRowsAndColumns) { 0.0 }
        e = mk.d2array(numPercentageRowsAndColumns, statesCharList.size) { 0.0 }

        // this is the array to accumulate state counts
        // one row per row in the alignment strings
        //    3 columns per character INSERT MATCH DELETE plus one more for RepeatInserts
        val numCountColumns = numMatchColumns * 3
        tCount = mk.d2array(numRows, numCountColumns + 1) { 0 }
        tRepeatInsertCount = mk.d2array(numRows, numMatchColumns + 1) { 0 }

        // STEP 3 - increment the HMM states and count emission characters

        countOccurrences()
        prettyPrintCountMatrix()

        // STEP 4 - score transition matrix and emission matrix
        scoreTransmissionCounts()
        scoreEmissionCounts()
        println(e)

        prettyPrintTransitionMatrix()

        val retVal = HMMTransitionAndEmissionMatrices(
            symbols = statesCharListIn,
            states = emptyList(), // not used
            transitions = convertToListOfLists(t),
            emissions = convertToListOfLists(e)
        )
        return retVal
    }

    /**
     * go through the transmission count array and covert it to the transmission state change
     * probability matrix
     */
    fun scoreTransmissionCounts() {
        // Part 1 - score the initial state (S)
        val scores = scoreStartInsertMatchDelete(0)
        t[0, 1] = scores[0]
        t[0, 2] = scores[1]
        t[0, 3] = scores[2]

        // score the initial insert row
        val scoresInitial = scoreInsertTransitionRow(0)
        t[1, 1] = scoresInitial[0] // repeated initial insertions
        t[1, 2] = scoresInitial[1] // initial match shadowed by insertion(s)
        t[1, 3] = scoresInitial[2] // insertion(s) followed by deletion

        for (group in 1..numMatchColumns) {
            val g = (group - 1) * 3  // how many triples of offset
            val scoresMatch = scoreMatchTransitionRow(group)
            t[g + 2, g + 4] = scoresMatch[0] // Mn->In,  e.g. M1->I1
            t[g + 2, g + 5] = scoresMatch[1] // Mn->Mn+1, e.g. M1->M2, or "E"
            if (group != numMatchColumns) {
                t[g + 2, g + 6] = scoresMatch[2] // Mn->Dn+1, e.g. M1->D2
            }

            val scoresDelete = scoreDeleteTransitionRow(group)
            t[g + 3, g + 4] = scoresDelete[0] // Dn->In,  e.g. D1->I1
            t[g + 3, g + 5] = scoresDelete[1] // Dn->Mn+1, e.g. D1->M2, or "E"
            if (group != numMatchColumns) {
                t[g + 3, g + 6] = scoresDelete[2] // Dn->Dn+1, e.g. D1->D2
            }

            val scoresInsert = scoreInsertTransitionRow(group) // trailing insert
            t[g + 4, g + 4] = scoresInsert[0] // repeat insert
            if (group != numMatchColumns) {
                t[g + 4, g + 5] = scoresInsert[1] // insert to match
                t[g + 4, g + 6] = scoresInsert[2] // insert to delete
            }
        }


    }

    /**
     * calculate transition percentages for Delete
     */
    fun scoreDeleteTransitionRow(group: Int): List<Double> {

        val lastDeleteCol = tCount[0..numRows, (group-1) * 3 + 2].toList()
        val lastDeleteSum = lastDeleteCol.sum().toDouble()
        if (lastDeleteSum == 0.0) {
            return listOf(0.0, 0.0, 0.0)
        }

        // the percentage of deletions that goes to insert is the percentage
        //   of the current delete column that transforms to an insert letter
        val insertCol = tCount[0..numRows, group * 3].toList()
        var insertColEntries = lastDeleteCol.filterIndexed { index, i -> lastDeleteCol[index] == 0 }
        val insertColPercent = insertCol.sum().toDouble() / lastDeleteSum

        // if we are at the end of the columns, just use what is left over as the end value
        if (group == numMatchColumns) {
            return listOf(insertColPercent, 1.0 - insertColPercent, 0.0)
        }

        // the percentage that goes to the next match is the percentage of
        //    current delete that are (1) not shadowed by an insert letter
        //    and (2) have a corresponding delete letter
        val nextMatchCol = tCount[0..numRows, group * 3 + 1].toList()
        var nextMatchColEntries =
            nextMatchCol.filterIndexed { index, i -> insertCol[index] == 0 && lastDeleteCol[index] == 1 }
        val nextMatchColPercent = nextMatchColEntries.sum().toDouble() / lastDeleteSum

        // the delete percentage is similar but it must have delete entry,
        //   and the count is the number covered by a delete entry
        var deleteColPercent = 0.0
        val deleteCol = tCount[0..numRows, group * 3 + 2].toList()
        var deleteColEntries = lastDeleteCol.filterIndexed { index, i -> insertCol[index] == 0 && lastDeleteCol[index] == 1 }
        var coveredDeleteEntries =
            nextMatchCol.filterIndexed { index, i -> lastDeleteCol[index] == 1 && deleteCol[index] == 1 }.sum()
        if (coveredDeleteEntries != 0) {
            deleteColPercent = deleteColEntries.sum().toDouble() / coveredDeleteEntries.toDouble()
        }

        return listOf(insertColPercent, nextMatchColPercent, deleteColPercent)
    }

    /**
     * calculate transition percentages for Match to Insert or subsequent Match (or End) or Delete
     */
    fun scoreMatchTransitionRow(group: Int): List<Double> {

        val lastMatchCol = tCount[0..numRows, (group - 1) * 3 + 1].toList()
        val lastMatchNumRows = lastMatchCol.sum()

        // the percentage that goes to insert is the percentage
        //   of the current match column that transforms to an insert letter
        val insertCol = tCount[0..numRows, group * 3].toList()
        var insertColEntries = lastMatchCol.filterIndexed { index, i -> lastMatchCol[index] == 0 }
        val insertColPercent = insertCol.sum().toDouble() / lastMatchNumRows.toDouble()

        // if we are at the end of the columns, just use what is left over as the end value
        if (group == numMatchColumns) {
            return listOf(insertColPercent, 1.0 - insertColPercent, 0.0)
        }

        // the percentage that goes to the next match is the percentage of
        //    current matches that are (1) not shadowed by an insert letter
        //    and (2) have a corresponding match letter
        val nextMatchCol = tCount[0..numRows, group * 3 + 1].toList()
        var nextMatchColEntries =
            nextMatchCol.filterIndexed { index, i -> insertCol[index] == 0 && lastMatchCol[index] == 1 }
        val nextMatchColPercent = nextMatchColEntries.sum().toDouble() / lastMatchNumRows.toDouble()

        // the delete percentage is similar but it must have delete entry,
        //   and now entry in the match column
        var deleteColPercent = 0.0
        val deleteCol = tCount[0..numRows, (group-1) * 3 + 2].toList()
        var deleteColEntries = deleteCol.filterIndexed { index, i -> insertCol[index] == 0 && deleteCol[index] == 1 }
        var coveredMatchEntries =
            nextMatchCol.filterIndexed { index, i -> deleteCol[index] == 1 && nextMatchCol[index] == 0 }.sum()
        if (coveredMatchEntries != 0) {
            deleteColPercent = deleteColEntries.sum().toDouble() / coveredMatchEntries.toDouble()
        }

        return listOf(insertColPercent, nextMatchColPercent, deleteColPercent)
    }

    /**
     * calculate transition percentages for Insert to Match and Delete
     *    values returned - percentage to repeat insert,
     *      to match and then to delete
     */
    fun scoreInsertTransitionRow(group: Int): List<Double> {

        val insertCol = tCount[0..numRows, group * 3]
        val insertColSum = insertCol.sum().toDouble()
        if (insertColSum == 0.0) {
            return listOf(0.0, 0.0, 0.0)
        }
        val repeateInsertSum = tRepeatInsertCount[0..numRows, group].sum()
        // probability of repeating the insert transition
        val repeateInsertPercentage = repeateInsertSum.toDouble() / (insertColSum + repeateInsertSum)

        // calculate percentage of the left over probability to allocate to matching transitions
        //   the matches must be shadowed after the insert letters
        val matchCol = tCount[0..numRows, group * 3 + 1].toList()
        var matchColEntriesSum = matchCol.filterIndexed { index, i -> insertCol[index] == 1 }.sum()
        val matchColShadowedPercent = matchColEntriesSum.toDouble() / insertColSum
        val matchColPercentageOfInsertTransition = (1.0 - repeateInsertPercentage) * matchColShadowedPercent

        // and now allocate to the delete probability
        val deleteCol = tCount[0..numRows, group * 3 + 2].toList()
        var deleteColEntriesSum = deleteCol.filterIndexed { index, i -> insertCol[index] == 1 }.sum()
        val deleteColShadowedPercent = deleteColEntriesSum.toDouble() / insertColSum
        val deleteColPercentageOfInsertTransition = (1.0 - repeateInsertPercentage) * deleteColShadowedPercent

        return listOf(
            repeateInsertPercentage,
            matchColPercentageOfInsertTransition,
            deleteColPercentageOfInsertTransition
        )
    }

    /**
     * calculate the percentage of Insert, Match and Delete.
     *    The Match and Delete entries in this case are "shadowed" by any entries in the Insert column
     */
    fun scoreStartInsertMatchDelete(group: Int): List<Double> {
        val insertCol = tCount[0..numRows, group * 3]
        val insertColPercent = insertCol.sum().toDouble() / numRows.toDouble()

        val matchCol = tCount[0..numRows, group * 3 + 1].toList()
        var matchColEntries = matchCol.filterIndexed { index, i -> insertCol[index] == 0 }
        val matchColPercent = matchColEntries.sum().toDouble() / numRows.toDouble()

        val deleteCol = tCount[0..numRows, group * 3 + 2].toList()
        var deleteColEntries = deleteCol.filterIndexed { index, i -> insertCol[index] == 0 }
        val deleteColPercent = deleteColEntries.sum().toDouble() / numRows.toDouble()

        return listOf(insertColPercent, matchColPercent, deleteColPercent)
    }

    /**
     * traverse the emission count array, and convert the counts into percentages
     */
    fun scoreEmissionCounts() {
        val numCountRows = numMatchColumns * 3 + 2
        val numCountColumns = statesCharList.size
        for (row in 0 until numCountRows) {
            val eRow = e[row, 0 .. numCountColumns]
            val sum = eRow.toList().sum()
            if (sum != 0.0) {
                for (col in 0 until numCountColumns) {
                    e[row, col] = e[row, col] / sum
                }
            }
        }
    }

    /**
     * print a formatted transition matrix for debugging
     */
    fun prettyPrintTransitionMatrix() {
        val str = StringBuilder()

        val baseGroupCount = numMatchColumns
        val numPercentageRowsAndColumns = baseGroupCount * 3 + 3

        // header
        str.append("    S    I0   ")
        for (i in 1..baseGroupCount) {
            str.append("M$i   D$i   I$i   ")
        }
        str.append("E\n")

        for (row in 0 until numPercentageRowsAndColumns) {
            when (row) {
                0 -> str.append("S   ")
                1 -> str.append("I0  ")
                numPercentageRowsAndColumns - 1 -> str.append("E   ")
                else -> {
                    val groupNumOffset = (row - 2) % 3
                    val groupNum = (row - 2) / 3 + 1
                    val label = "${"MDI"[groupNumOffset]}$groupNum  "
                    str.append(label)
                }
            }
            for (col in 0 until numPercentageRowsAndColumns) {
                var outNum = ""
                val num = t[row, col]
                if (num == 0.0) {
                    outNum = "0   "
                } else {
                    outNum = String.format("%4.2f", num)
                }
                str.append("$outNum ")
            }
            str.append("\n")
        }
        println(str.toString())
    }

    fun prettyPrintCountMatrix() {
        val str = StringBuilder()

        // header
        for (i in 1..matchColumnsList.size) {
            str.append("I${i - 1} r$i M$i D$i ")
        }
        str.append("I${matchColumnsList.size}\n")

        for (row in 0 until numRows) {
            str.append(" ")
            for (i in 0 until matchColumnsList.size) {
                var col = 0 // have to track index separately due to merging the repeat insert data
                for (j in 0 until 4) {
                    if (j == 1) {
                        str.append(tRepeatInsertCount[row, i])
                    } else {
                        str.append(tCount[row, i * 3 + col])
                        col++
                    }
                    str.append("  ")
                }
            }
            str.append(tCount[row, matchColumnsList.size * 3 - 1])
            str.append("\n")
        }
        println(str.toString())
    }
}


