@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems

import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.get

/**
 * Number of Breakpoints Problem: Find the number of breakpoints in a permutation.

Input: A permutation.
Output: The number of breakpoints in this permutation.

 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba6b/
 *
 * Transforming Men into Mice
 *
 * youtube: @link: https://www.youtube.com/watch?v=lCoUp2Bq8OA
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */


fun main() {

    val sampleInput = "+3 +4 +5 -12 -8 -7 -6 +1 +2 +10 +9 -11 +13 +14"
    val permutation = sampleInput.split(' ').map { it.toInt() }

    val arr = permutation.toNDArray()

    var numPermutations = 0
    if (arr[0] != 1) {
        numPermutations++
    }
    for (i in 0 until arr.size-1) {
        if (arr[i] + 1 != arr[i + 1]) {
            numPermutations++
        }
    }

    println(numPermutations)

}


