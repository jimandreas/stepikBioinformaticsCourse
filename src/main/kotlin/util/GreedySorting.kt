@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable")

package util

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.indexOf

/**
 * Implement GreedySorting

Given: A signed permutation P.

Return: The sequence of permutations corresponding to applying
GreedySorting to P, ending with the identity permutation.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240319/step/4?unit=212665
 * rosalind: @link: http://rosalind.info/problems/ba6a/
 *
 * Transforming Men into Mice
 *
 * youtube: @link: https://www.youtube.com/watch?v=lCoUp2Bq8OA
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

class GreedySorting(val size: Int) {
    var gArr = mk.d1array(size) { 0 }
    val gArrB = mk.d1array(size) { 0 }
    val outStr = StringBuilder()

    fun sort(): Int {

        for (k in 1 until size + 1) {
            
            if (kotlin.math.abs(gArr[k-1]) != k) {
                reverseIt(k)
            }

            if (gArr[k-1] < 0) {
                gArr[k-1] = -gArr[k-1]
                printit()
            }
        }
        return 0
    }


    private fun reverseIt(k: Int) {
        var idx = gArr.indexOf(k)
        if (idx == -1) {
            idx = gArr.indexOf(-k)
        }

        /*
         * make a reversed copy with reversed signs
         */
        var j = 0
        for (i in idx downTo k - 1) {
            gArrB[j++] = -gArr[i]
        }

        /*
         * now copy the copy back into place in gArr
         */
        j = 0
        for (i in k..idx + 1) {
            gArr[i - 1] = gArrB[j++]
        }
        printit()

        if (gArr[k-1] < 0) {
            gArr[k-1] = -gArr[k-1]
            printit()
        }
    }

    private fun printit() {
        for (i in 0 until gArr.size) {
            val numVal = String.format("%+d", gArr[i])
            outStr.append(numVal)
            if (i < gArr.size - 1) {
                outStr.append(" ")
            }
        }
        outStr.append("\n")
    }
}
