@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

class BurrowsWheelerTransform {

    /**
     * Construct the Burrows-Wheeler transform of a string.
     * Stepik: https://stepik.org/lesson/240380/step/5?unit=212726
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
     * See also:
     * https://en.wikipedia.org/wiki/Burrows%E2%80%93Wheeler_transform
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
            val lastChar = m[i-1][len-1]
            var newString = "$lastChar${m[i-1].substring(0, len-1)}"
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
        val slist = inString.toCharArray()
        val slistsorted = slist.sorted()

        /*
        the lookup needed is from the sorted list
        to the encoded list, taking into account
        the character repeats
        */
        //val lookupMap : MutableMap<Int, Int> = mutableMapOf()
        val charMap: MutableMap<Char, MutableList<Int>> = mutableMapOf()

        // map a char to a list of indexes in the encoded string
        for (i in 0 until slist.size) {
            charMap.addTo(slist[i], i)
        }

        // map char at each index in the sorted list
        // to what occurrence it is of that char

        val mapElementToCharOccurrence : MutableMap<Int, Pair<Char, Int>> = mutableMapOf()
        // counter for each char
        val countCharOccurrences: MutableMap<Char, Int> = mutableMapOf()

        for (i in 0 until slist.size) {
            val c = slistsorted[i]
            countCharOccurrences.incrementMatches(c)
            val num = countCharOccurrences[c]!! // how many of this char have been seen

            mapElementToCharOccurrence[i] = Pair(c, num)
        }

        /*
         * match the '$' by its index with its counterpart in the sorted list.
         * From the pair of (character and occurrence) find the new index in the
         * encoded list.
         */
        var curIndex = slist.indexOf('$')
        val str = StringBuilder()
        val firstChar = slistsorted[curIndex]
        str.append(firstChar)

        for (i in 1 until slist.size) {
            val p = mapElementToCharOccurrence[curIndex]!!

            val c = p.first
            val occurence = p.second
            val nextIndex = charMap[c]!![occurence-1]

            val nextChar = slistsorted[nextIndex]
            str.append(nextChar)
            curIndex = nextIndex
        }

        return str.toString()

    }


}