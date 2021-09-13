@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls"
)

package util

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.set


/**
 *

For each j, we can compute LimbLength(j) by finding the minimum value of
( Di,j + Dj,k − Di,k)/2 (D_{i,j} + D_{j,k} - D_{i,k} ) / 2
over all pairs of leaves i and k.

 * See also:
 * stepik: https://stepik.org/lesson/240336/step/11?unit=212682
 * rosalind: http://rosalind.info/problems/ba7b/
 */

/**

Code Challenge: Solve the Limb Length Problem.

Input: An integer n, followed by an integer j between 0 and n - 1,
followed by a space-separated additive distance matrix D (whose elements are integers).

Output: The limb length of the leaf in Tree(D) corresponding
to row j of this distance matrix (use 0-based indexing).

 */

class PhylogenyLimbLength {

    fun calculateLimbLength(matrixSize: Int, rowNumber: Int, m: D2Array<Int>): Int {

        return 0
    }

}
