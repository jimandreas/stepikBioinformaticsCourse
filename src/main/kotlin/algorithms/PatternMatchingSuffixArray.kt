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


}