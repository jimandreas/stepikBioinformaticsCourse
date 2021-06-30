@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

import util.*

/**
 * @link: https://stepik.org/lesson/240262/step/16?unit=212608
 *
Code Challenge: Implement StringSpelledByGappedPatterns.

Input: Integers k and d followed by a sequence of (k, d)-mers (a1|b1), … , (an|bn)
such that Suffix(ai|bi) = Prefix(ai+1|bi+1) for 1 ≤ i ≤ n-1.
Output: A string Text of length k + d + k + n - 1 such that the
i-th (k, d)-mer in Text is equal to (ai|bi)  for 1 ≤ i ≤ n (if such a string exists).


 */

fun main() {

    val d = 2
    val k = 4
    val pairsString = listOf(
/*        Pair("GACC", "GCGC"),
        Pair("ACCG", "CGCC"),
        Pair("CCGA", "GCCG"),
        Pair("CGAG", "CCGG"),
        Pair("GAGC", "CGGA"),*/

        Pair("GTTT", "ATTT"),
        Pair("TTTA", "TTTG"),
        Pair("TTAC", "TTGT"),
        Pair("TACG", "TGTA"),
        Pair("ACGT", "GTAT"),
        Pair("CGTT", "TATT"),


        )
    val result = stringSpelledByGappedPatterns(pairsString, d, k)
    println(result)

}



