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


}