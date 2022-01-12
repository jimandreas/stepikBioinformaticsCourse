@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment",
    "VARIABLE_WITH_REDUNDANT_INITIALIZER"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

class BurrowsWheelerMatchingWithCheckpoints {

    /**
     * Setting Up Checkpoints (8/10)
     * https://youtu.be/byNR4CbYiPQ?t=402
     *
     * See also:
     * https://en.wikipedia.org/wiki/Burrows%E2%80%93Wheeler_transform
     *
     * online calculator:
     * https://calcoolator.eu/burrows-wheeler-transform-encoder-decoder-
     *
     * Uses the Kotlin Multik multidimensional array library
     * @link: https://github.com/Kotlin/multik
     * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
     */


    /**
     * Stepik: https://stepik.org/lesson/240386/step/4?unit=212732
     * Rosalind: https://rosalind.info/problems/ba9n/
     */

    /**
     * The checkpoint array is saved only every modulus rows.
     * It is then interpreted by interpolateCountArray()
     */
    companion object {
        const val MODULUS = 5
    }

    var modulus = MODULUS

    val sparseCountArray: MutableList<D1Array<Int>> = mutableListOf()

    val firstOccurrence: MutableMap<Char, Int> = mutableMapOf()

    var symbolSet = "\$ACGT"  // must be in alphabetic order!

    var symbolMap: Map<Char, Int> = mutableMapOf()

    var lastColumn = ""

    fun initializeCountArrayAndFirstOccurrence(bwEncodedString: String) {

        symbolMap = symbolSet.associate {
            it.let { it to symbolSet.indexOf(it) }
        }

        lastColumn = bwEncodedString

        val strlen = bwEncodedString.length
        val symlen = symbolSet.length


        // countArray = mk.d2array(strlen + 1, symlen) { 0 }

        val initialRow = mk.d1array(symlen) { 0 }
        sparseCountArray.add(initialRow)

        val countOfSymbolsArray = mk.d1array(symlen) { 0 }

        /*
         * accumulate counts.  Add a row for every (modulus)
         * rows accumulated.
         * NOTE that the counts occur AFTER the character,
         * therefore the loop starts with index 1 but looks at
         * the 0th character first
         */
        for (i in 1..strlen) {
            val c = bwEncodedString[i - 1]
            val symIndex = symbolMap[c]!!
            countOfSymbolsArray[symIndex]++

            val modRow = i % modulus
            if (modRow == 0) {
                val newRow = mk.d1array(symlen) { 0 }
                for (j in 0 until symlen) {
                    newRow[j] = countOfSymbolsArray[j]
                }
                sparseCountArray.add(newRow)
            }

        }

        // now init the firstOccurrence array (list)
        val sortedEncodedString = bwEncodedString.toCharArray().sorted()
        var currentSymbol = symbolMap[sortedEncodedString[0]]!!
        firstOccurrence[sortedEncodedString[0]] = 0
        for (i in 1 until strlen) {
            val nextSymbol = symbolMap[sortedEncodedString[i]]!!
            if (nextSymbol != currentSymbol) {
                currentSymbol = nextSymbol
                firstOccurrence[sortedEncodedString[i]] = i
            }
        }
    }

    fun interpolateCheckpoint(row: Int, col: Int): Int {

        val sparseRowIndexInArray = row / modulus
        val baseRowNumberInSparseArray = sparseRowIndexInArray * modulus

        val sparseRow = sparseCountArray[sparseRowIndexInArray].deepCopy()

        for (i in baseRowNumberInSparseArray until row) {
            val c = lastColumn[i]  // the list of transformed characters
            val symIndex = symbolMap[c]!!  // which array column to increment
            sparseRow[symIndex]++
        }

        return sparseRow[col] // return the interpolated count for the character associated with col
    }

    /**
     * this BWMatching assumes that the string to be searched has been decoded -
     *   this supplies the needed information in the lastToFirst array
     *
     *   [suffixArray] map of BWT string (transformed string) to the
     *     index of each char in the original string
     */

    fun burrowsWheelerBetterMatchingMultipleWithCheckpoints(symbols: List<String>, suffixArray: List<Int>):
            List<Pair<String, List<Int>>> {

        val returnList : MutableList<Pair<String, List<Int>>> = mutableListOf()
        // for each symbol, build list of offsets where the symbol is found
        for (s in symbols) {
            val offsetList: MutableList<Int> = mutableListOf()
            offsetList.addAll(burrowsWheerMatchingWithCheckpointsAndSuffixArray(s, suffixArray))
            returnList.add(Pair(s, offsetList))
        }
        return returnList
    }

    fun burrowsWheerMatchingWithCheckpointsAndSuffixArray(patternIn: String, suffixArray: List<Int>): List<Int> {

        if (firstOccurrence.isEmpty()) {  // error check - must call first: initializeCountArrayAndFirstOccurrence()
            return emptyList()
        }

        var top = 0
        var bottom = lastColumn.length - 1
        var pattern = patternIn

        while (top <= bottom) {
            val len = pattern.length
            if (len == 0) {
                //return bottom - top + 1
                //println("$patternIn $top $bottom  ${suffixArray[top]} ${suffixArray[bottom]}")
                val retList : MutableList<Int> = mutableListOf()
                for (i in top .. bottom)
                retList.add(suffixArray[i])
                return retList
            }

            val symbol = pattern[len - 1]
            pattern = pattern.substring(0, len - 1)

            val subst = lastColumn.substring(top, bottom + 1)
            if (subst.indexOf(symbol) == -1) {
                return emptyList()
            }

            // if the symbol is not present in the string, then quit
            if (!firstOccurrence.containsKey(symbol)) {
                return emptyList()
            }
            val firstO = firstOccurrence[symbol]!!

            top = firstO + interpolateCheckpoint(top, symbolMap[symbol]!!)
            bottom = firstO + interpolateCheckpoint(bottom + 1, symbolMap[symbol]!!) - 1

        }

        return emptyList()
    }

    /**

        Burrows-Wheeler Transform Construction Problem:
        Construct the Burrows-Wheeler transform of a string.

        Input: A string Text.
        Output: BWT(Text).

        Code Challenge: Solve the Burrows-Wheeler Transform Construction Problem.

        MODIFIED: modify the sort function to retain the original
            offset position.   This forms the "suffix Array" that
            maps the resulting character offset in the transformed
            string to the offset in the original string.
     */

    fun bwtEncodeWithSuffixArray(inString: String): Pair<String, List<Int>> {
        var m: MutableList<String> = mutableListOf()
        val len = inString.length

        // build the rotated matrix
        //   Note that the string is rotated RIGHT here,
        //   i.e. the last character is removed and
        //   inserted at the start.
        //   Other algorithms rotate the string LEFT.
        m.add(inString)
        for (i in 1 until inString.length) {
            val lastChar = m[i - 1][len - 1]
            var newString = "$lastChar${m[i - 1].substring(0, len - 1)}"
            m.add(newString)
        }

        // set up the offset array for the rotated string matrix
        //    each entry indicates the 1st character's offset
        //    to the original string position

        val suffixArray = mk.d1array(len) { 0 }
        for (i in 1 until len) {
            suffixArray[i] = len - i
        }

        // a simple bubble sort of the strings,
        // while maintaining the indexes
        for (i in 0 until len - 1) {
            for (j in 1 until len) {
                if (m[j - 1] > m[j]) {
                    val temp = m[j-1]
                    val tempIdx = suffixArray[j-1]

                    m[j-1] = m[j]
                    suffixArray[j-1] = suffixArray[j]

                    m[j] = temp
                    suffixArray[j] = tempIdx
                }
            }
        }

        // return last column as a string

        val str = StringBuilder()
        m.map {
            str.append(it.last())
        }

        return Pair( str.toString(), suffixArray.toList())
    }


    /**
     * Stepik: https://stepik.org/lesson/240387/step/10?unit=212733
     * Rosalind: https://rosalind.info/problems/ba9o/

    Code Challenge: Solve the Multiple Approximate Pattern Matching Problem.

    Input: A string Text, followed by a collection of space-separated strings
    Patterns, and an integer d.

    Output: For each string Pattern in Patterns, the string Pattern followed
    by a colon, followed by a space-separated collection of all positions
    where Pattern appears as a substring of Text with at most d mismatches.

     */

    fun burrowsWheelerMismatchTolerantReadMappingForSymbolSet(symbols: List<String>, mismatchCounti: Int, suffixArray: List<Int>):
            List<Pair<String, List<Int>>> {

        val returnList : MutableList<Pair<String, List<Int>>> = mutableListOf()
        // for each symbol, build list of offsets where the symbol is found
        for (s in symbols) {
            val offsetList: MutableList<Int> = mutableListOf()
            offsetList.addAll(burrowsWheelerMismatchTolerantReadMapping(s, mismatchCounti, suffixArray))
            returnList.add(Pair(s, offsetList))
        }
        return returnList
    }

    fun burrowsWheelerMismatchTolerantReadMapping(symbol: String, mismatchCounti: Int, suffixArray: List<Int>):
            List<Int> {

        return emptyList()
    }


}