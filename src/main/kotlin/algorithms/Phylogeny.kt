@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER"
)

package algorithms

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import java.lang.Math.min


/**
 *

For each j, we can compute LimbLength(j) by finding the minimum value of
( Di,j + Dj,k − Di,k ) / 2
over all pairs of leaves i and k.

 * See also:
 * stepik: https://stepik.org/lesson/240336/step/11?unit=212682
 * rosalind: http://rosalind.info/problems/ba7b/
 */



class Phylogeny {

    /**

    Code Challenge: Implement AdditivePhylogeny to solve the Distance-Based Phylogeny Problem.

    Input: An integer n followed by a space-separated n x n distance matrix.

    Output: A weighted adjacency list for the simple tree fitting this matrix.

    Note on formatting: The adjacency list must have consecutive integer node labels
    starting from 0. The n leaves must be labeled 0, 1, ..., n - 1 in order
    of their appearance in the distance matrix.
    Labels for internal nodes may be labeled in any order but must start
    from n and increase consecutively.
     */

    fun additivePhylogeny(matrixSize: Int, m: D2Array<Int>) {

    }

    /**

    Code Challenge: Solve the Limb Length Problem.

    Input: An integer n, followed by an integer j between 0 and n - 1,
    followed by a space-separated additive distance matrix D (whose elements are integers).

    Output: The limb length of the leaf in Tree(D) corresponding
    to row j of this distance matrix (use 0-based indexing).

     */

    /**
     * ( Di,j + Dj,k − Di,k ) / 2
     */
    fun calculateLimbLength(matrixSize: Int, rowNumberJ: Int, m: D2Array<Int>): Int {

        var minValue = Int.MAX_VALUE

        for (i in 0 until matrixSize) {
            for (k in 0 until matrixSize) {

                if (i != rowNumberJ && rowNumberJ != k) {

                    val dij = m[i, rowNumberJ]
                    val djk = m[rowNumberJ, k]
                    val dik = m[i, k]
                    var sum = dij + djk - dik
                    sum = sum / 2

                    minValue = min(minValue, sum)
//                    if (sum == 2) {
//                        println("$i $rowNumberJ $k  $dij $djk $dik")
//                    }
                }

            }
        }
        return minValue
    }

}
