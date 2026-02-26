@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls",  "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER",  "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment",
    "VARIABLE_WITH_REDUNDANT_INITIALIZER"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set

class BurrowsWheelerTransform {

    /**
     * Construct the Burrows-Wheeler transform of a string.
     * Rosalind: https://rosalind.info/problems/ba9i/
     *
     * String Compression and the Burrows-Wheeler Transform (4/10)
     * https://www.youtube.com/watch?v=G7YBi04HOEY
     *
     * Inverting Burrows-Wheeler (5/10)
     * https://www.youtube.com/watch?v=DqdjbK68l3s
     *
     * Using Burrows-Wheeler for Pattern Matching (6/10)
     * https://www.youtube.com/watch?v=z5EDLODQPtg
     *
     * Finding the Matched Patterns (7/10)
     * https://www.youtube.com/watch?v=gUK2JvCCUio
     *
     * Setting Up Checkpoints (8/10)
     * https://www.youtube.com/watch?v=byNR4CbYiPQ
     *
     * See also:
     * https://en.wikipedia.org/wiki/Burrows%E2%80%93Wheeler_transform
     *
     * online calculator:
     * https://calcoolator.eu/burrows-wheeler-transform-encoder-decoder-
     */


    /**

    Burrows-Wheeler Transform Construction Problem: Construct the Burrows-Wheeler transform of a string.

    Input: A string Text.
    Output: BWT(Text).

    Code Challenge: Solve the Burrows-Wheeler Transform Construction Problem.
     */

    fun bwtEncode(inString: String): String {
        var m: MutableList<String> = mutableListOf()
        val len = inString.length

        // build the rotated matrix
        m.add(inString)
        for (i in 1 until inString.length) {
            val lastChar = m[i - 1][len - 1]
            var newString = "$lastChar${m[i - 1].substring(0, len - 1)}"
            m.add(newString)
        }

        // sort
        val sorted = m.sorted()

        // return last column as a string

        val str = StringBuilder()
        sorted.map {
            str.append(it.last())
        }

        return str.toString()
    }

    /**
     * decode a string that was encoded using the Burrows Wheeler Transform.
     * Assumption: the string contains a single "$" terminator that was used
     * in the encoding.
     *
     * Steps:
     * sort the [inString] lexicographically
     *
     * form an index table from the [inString] to the sorted
     *
     */
    fun bwtDecode(inString: String): String {
        lastArrayStringToBeDecoded = inString.toList()
        firstStringSortedString = lastArrayStringToBeDecoded.sorted()

        lastColumn = lastArrayStringToBeDecoded.toList()
        firstColumn = firstStringSortedString



        // map a char to a list of indexes in the encoded string
        for (i in 0 until lastArrayStringToBeDecoded.size) {
            charMap.addTo(lastArrayStringToBeDecoded[i], i)
        }

        // counter for each char
        val countCharOccurrences: MutableMap<Char, Int> = mutableMapOf()

        for (i in 0 until lastArrayStringToBeDecoded.size) {
            val c = firstStringSortedString[i]
            countCharOccurrences.incrementMatches(c)
            val num = countCharOccurrences[c]!! // how many of this char have been seen

            mapElementToCharOccurrence[i] = Pair(c, num)
        }

        /*
         * match the '$' by its index with its counterpart in the sorted list.
         * From the pair of (character and occurrence) find the new index in the
         * encoded list.
         */
        var curIndex = lastArrayStringToBeDecoded.indexOf('$')
        val str = StringBuilder()
        val firstChar = firstStringSortedString[curIndex]
        str.append(firstChar)

        for (i in 1 until lastArrayStringToBeDecoded.size) {
            val p = mapElementToCharOccurrence[curIndex]!!

            val c = p.first
            val occurence = p.second
            val nextIndex = charMap[c]!![occurence - 1]

            val nextChar = firstStringSortedString[nextIndex]
            str.append(nextChar)
            curIndex = nextIndex
        }

        buildLastToFirstArray()

        return str.toString()

    }

    lateinit var lastArrayStringToBeDecoded: List<Char>
    lateinit var firstStringSortedString: List<Char>
    lateinit var lastToFirstArray: D1Array<Int>
    val mapElementToCharOccurrence: MutableMap<Int, Pair<Char, Int>> = mutableMapOf()
    /*
        the lookup needed is from the sorted list
        to the encoded list (lastArray), taking into account
        the character repeats
        */
    val charMap: MutableMap<Char, MutableList<Int>> = mutableMapOf()

    fun buildLastToFirstArray() {
        lastToFirstArray = mk.d1array(lastArrayStringToBeDecoded.size) { 0 }
        var curIndex = lastArrayStringToBeDecoded.indexOf('$')
        lastToFirstArray[curIndex] = 0  // initial entry is for '$' - points from lastArray to entry 0 in firstArray

        for (i in 1 until lastArrayStringToBeDecoded.size) {

            val p = mapElementToCharOccurrence[curIndex]!!
            val c = p.first             // the character
            val occurence = p.second    // which occurrence
            val nextIndex = charMap[c]!![occurence - 1]

            lastToFirstArray[nextIndex] = curIndex

            val nextChar = firstStringSortedString[nextIndex]
            curIndex = nextIndex
        }
    }

    /**
    9.10 Pattern Matching with the Burrows-Wheeler Transform
    Code Challenge: Implement BWMatching.

    Input: A string BWT(Text), followed by a space-separated collection of Patterns.

    Output: A space-separated list of integers, where the i-th integer
    corresponds to the number of substring matches of the i-th member of Patterns in Text.

    Rosalind: https://rosalind.info/problems/ba9l/
     */

    private val pseudoCode = """
    BWMATCHING(FirstColumn, LastColumn, Pattern, LastToFirst)
        top ← 0
        bottom ← |LastColumn| − 1
        while top ≤ bottom
            if Pattern is nonempty
                symbol ← last letter in Pattern
                remove last letter from Pattern
                if positions from top to bottom in LastColumn contain an occurrence of symbol
                    topIndex ← first position of symbol among positions from top to bottom in LastColumn
                    bottomIndex ← last position of symbol among positions from top to bottom in LastColumn
                    top ← LastToFirst(topIndex)
                    bottom ← LastToFirst(bottomIndex)
                else
                    return 0
            else
                return bottom − top + 1 
    """.trimIndent()



    lateinit var firstColumn: List<Char>
    lateinit var lastColumn: List<Char>

    /**
     * scan the lastColumn characters for the first and last occurrence of [symbol]
     * that appears between the top and bottom indices inclusive.
     */
    private fun findFirstLastInLastColumn(symbol: Char, top: Int, bottom: Int): Pair<Int, Int>? {
        var topIndex = 0
        var bottomIndex = 0
        for (i in top..bottom) {
            val c = lastColumn[i]
            if (c == symbol) {
                topIndex = i
                for (j in i + 1..bottom) {
                    val c2 = lastColumn[j]
                    if (c2 == symbol) {
                        bottomIndex = j
                    }
                }
                if (bottomIndex == 0) {
                    return Pair(topIndex, topIndex)
                }
                return Pair(topIndex, bottomIndex)
            } else if (i == bottom) {
                return null
            }
        }
        return null
    }

    /**
     * this BWMatching assumes that the string to be searched has been decoded -
     *   this supplies the needed information in the lastToFirst array
     */

    fun burrowsWheelerMatchingMultiple(symbols: List<String>): List<Int> {
        val outputList : MutableList<Int> = mutableListOf()
        for (s in symbols) {
            outputList.add(burrowsWheelerMatching(s))
        }
        return outputList
    }

    fun burrowsWheelerMatching(patternIn: String): Int {

        if (mapElementToCharOccurrence.isEmpty()) {
            return 0
        }
        var top = 0
        var bottom = lastColumn.size - 1
        var pattern = patternIn

        while (top <= bottom) {
            val len = pattern.length
            if (len == 0) {
                return bottom - top + 1
            }
            val symbol = pattern[len-1]
            pattern = pattern.substring(0, len-1)

            val topBottomIndices = findFirstLastInLastColumn(symbol, top, bottom) ?: return 0

            // we have a valid top and bottom
            val topIndex = topBottomIndices.first
            val bottomIndex = topBottomIndices.second

            top = lastToFirstArray[topIndex]
            bottom = lastToFirstArray[bottomIndex]
        }

        return 0
    }
}