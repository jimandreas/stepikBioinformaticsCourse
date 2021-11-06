@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "RedundantIf", "LiftReturnOrAssignment"
)

package algorithms

/**
 * See also:
 * http://rosalind.info/problems/rvco/
 * Complementing a Strand of DNA
 *
 * Problem

Recall that in a DNA string s, 'A' and 'T' are complements of each
other, as are 'C' and 'G'. Furthermore, the reverse complement
of s is the string sc formed by reversing the symbols of s and
then taking the complement of each symbol (e.g., the reverse complement of "GTCA" is "TGAC").

The Reverse Complement program from the SMS 2 package can be run online here.

Given: A collection of n (n≤10) DNA strings.

Return: The number of given strings that match their reverse complements.
 */
class RosalindReverseComplement {

    /**
     *
     */
    fun reverseComplementMatches(l: List<String>): Int {
       var numReverseComplementMatches = 0
        for (dnaString in l) {
            val rc = reverseComplement(dnaString)
            if (rc == dnaString) {
                numReverseComplementMatches++
            }
        }
        return numReverseComplementMatches
    }

}

