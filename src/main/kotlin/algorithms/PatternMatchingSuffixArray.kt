@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

class PatternMatchingSuffixArray {

    /**
     * Construct the Suffix Array of a String
     * Stepik: https://stepik.org/lesson/240379/step/2?unit=212725
     * Rosalind: https://rosalind.info/problems/ba9g/
     *
     */


    /**
     * Suffix Array Construction Problem: Construct the suffix array of a string.
     *
     * Input: A string Text.
     *
     * Output: SuffixArray(Text), as a space-separated collection of integers.
     */

    fun assembleSuffixArray(str: String): List<Int> {

        val suffixMap: MutableMap<String, Int> = mutableMapOf()

        // work through all suffixes of the string, and map the position to the suffix

        for (i in 0 until str.length) {
            suffixMap[str.substring(i, str.length)] = i
        }

        val retList = suffixMap.keys.toList().sorted().map { suffixMap[it]!!}
        return retList
    }

    // https://rosalind.info/problems/ba9h/
    /**
     * Multiple Pattern Matching with the Suffix Array

    Given: A string Text and a collection of strings Patterns.

    Return: All starting positions in Text where a string from Patterns appears as a substring.
     */


    fun patternMatchSuffixArray(str: String, patterns: List<String>): List<Int> {

        val suffixMap: MutableMap<String, Int> = mutableMapOf()

        // work through all suffixes of the string, and map the position to the suffix

        for (i in 0 until str.length) {
            suffixMap[str.substring(i, str.length)] = i
        }

        val keys = suffixMap.keys.toList().sorted()
        val retList: MutableList<Int> = mutableListOf()
        for (i in 0 until keys.size) {

            for (p in patterns) {
                if (keys[i].length < p.length) {
                    continue
                }
                if (p == keys[i].substring(0, p.length)) {
                    val idx = suffixMap[keys[i]]!!
                    retList.add(idx)
                }
            }
        }
        return retList
    }


    /**
    Code Challenge: Construct a partial suffix array.

    Input: A string Text and a positive integer K.
    Output: SuffixArrayK(Text), in the form of a list of ordered pairs
    (i, SuffixArray(i)) for all nonempty entries in the partial suffix array.

     */

    fun assemblePartialArray(str: String, indexModulus: Int): List<Pair<Int, Int>> {

        val suffixMap: MutableMap<String, Int> = mutableMapOf()

        // work through all suffixes of the string, and map the position to the suffix

        for (i in 0 until str.length) {
            suffixMap[str.substring(i, str.length)] = i
        }

        val startingPositionList = suffixMap.keys.toList().sorted().map { suffixMap[it]!!}

        val retList: MutableList<Pair<Int, Int>> = mutableListOf()

        for (i in 0 until startingPositionList.size - 1) {
            val p = startingPositionList[i]
            if (p % indexModulus == 0) {
                retList.add(Pair(i, p))
            }
        }
        return retList
    }
}