@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls",  "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER",  "CanBeVal", "SimplifyBooleanWithConstants",
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

class BurrowsWheelerMatching {

    /**
     * Using Burrows-Wheeler for Pattern Matching (6/10)
     * https://www.youtube.com/watch?v=z5EDLODQPtg
     *
     * Finding the Matched Patterns (7/10)
     * https://www.youtube.com/watch?v=gUK2JvCCUio
     *
     * Setting Up Checkpoints (8/10)
     * https://www.youtube.com/watch?v=byNR4CbYiPQ
     *
     * Inexact Matching (9/10)
     * https://www.youtube.com/watch?v=Vjnm-jF1PBQ
     *
     * Further Applications of Read Mapping (10/10)
     * https://www.youtube.com/watch?v=IN0CnomTE84
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
     * Rosalind: https://rosalind.info/problems/ba9m/
     *
     * To improve BWMATCHING, we introduce a function
     * Countsymbol(i, LastColumn), which returns the number of
     * occurrences of symbol in the first i positions of LastColumn.
     * For example, Count"n”(10, "smnpbnnaaaaa$a”) = 3, and Count"a”(4, "smnpbnnaaaaa$a”) = 0.
     *
     * Define FirstOccurrence(symbol) as the first position of
     * symbol in FirstColumn. If Text = "panamabananas$",
     * then FirstColumn is "$aaaaaabmnnnps", and the array holding
     * all values of FirstOccurrence is [0, 1, 7, 8, 9, 11, 12].
     * For DNA strings of any length, the array FirstOccurrence contains only five elements.
     *
     * Implement countSymbol:
     *    First set up a routine to implement a count matrix
     */
    lateinit var countArray: D2Array<Int>  // count matrix
    val firstOccurrence: MutableList<Int> = mutableListOf()

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
        countArray = mk.d2array(strlen + 1, symlen) { 0 }
        val countOfSymbolsArray = mk.d1array(symlen) { 0 }

        for (i in 0 until strlen) {
            val c = bwEncodedString[i]
            val symIndex = symbolMap[c]!!
            countOfSymbolsArray[symIndex]++

            for (j in 0 until symlen) {
                countArray[i + 1, j] = countOfSymbolsArray[j]
            }
        }

        // now init the firstOccurrence array (list)
        val sortedEncodedString = bwEncodedString.toCharArray().sorted()
        var currentSymbol = symbolMap[sortedEncodedString[0]]!!
        firstOccurrence.add(0)
        for (i in 1 until strlen) {
            val nextSymbol = symbolMap[sortedEncodedString[i]]!!
            if (nextSymbol != currentSymbol) {
                currentSymbol = nextSymbol
                firstOccurrence.add(i)
            }
        }
    }

    /**
     * this BWMatching assumes that the string to be searched has been decoded -
     *   this supplies the needed information in the lastToFirst array
     */

    fun burrowsWheelerBetterMatchingMultiple(symbols: List<String>): List<Int> {
        val outputList: MutableList<Int> = mutableListOf()
        for (s in symbols) {
            outputList.add(burrowsWheelerBetterMatching(s))
        }
        return outputList
    }

    fun burrowsWheelerBetterMatching(patternIn: String): Int {

        if (firstOccurrence.isEmpty()) {  // error check - must call first: initializeCountArrayAndFirstOccurrence()
            return 0
        }

        var top = 0
        var bottom = lastColumn.length - 1
        var pattern = patternIn

        while (top <= bottom) {
            val len = pattern.length
            if (len == 0) {
                return bottom - top + 1
            }

            val symbol = pattern[len - 1]
            pattern = pattern.substring(0, len - 1)

            val subst = lastColumn.substring(top, bottom+1)
            if (subst.indexOf(symbol) == -1) {
                return 0
            }

            val firstO = firstOccurrence[symbolMap[symbol]!!]
            top =  firstO + countArray[top, symbolMap[symbol]!!]
            bottom = firstO + countArray[bottom + 1, symbolMap[symbol]!!] - 1

        }

        return 0
    }
}