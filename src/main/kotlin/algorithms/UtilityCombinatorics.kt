@file:Suppress("MemberVisibilityCanBePrivate", "unused", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VARIABLE")

package algorithms

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set

object Combinatorics {

    /**
     * take an int [i] and return i! (i factorial)
     */
    fun fact(i: Int): Int {
        var out = 1
        if (i < 0) {
            return out
        }
        for (j in 1..i) {
            out *= j
        }
        return out
    }

    /**
     * Binomial Coefficient - Factorial formula
     * calculate n select k = n! / ( k! * (n - k)!)
     * https://en.wikipedia.org/wiki/Binomial_coefficient
     *
     * Read it as: n Select k items
     */
    fun select(n: Int, k: Int): Int {
        if (n < 0 || k < 0) {
            return 0
        }

        val nFactorial = fact(n)
        val kFactorial = fact(k)
        val nMinusKfact = fact(n - k)

        return (nFactorial / (kFactorial * nMinusKfact))

    }

    /**
     * Permutation of length n
     * https://en.wikipedia.org/wiki/Permutation
     * Used in solution to Rosalind problem:
     * http://rosalind.info/problems/perm/
     * Enumerating Gene Orders
     *
     * Take an input n and return a non-repeating
     * list of all n-factorial integer combinations of
     * length n.
     *
     *  * Uses the Kotlin Multik multidimensional array library
     * @link: https://github.com/Kotlin/multik
     * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
     */

    private var iterationCount = 0
    fun permutation(n: Int): List<List<Int>> {
        val arr = mk.d2array(fact(n), n) { 0 }
        iterationCount = 0
        for (i in 1..n) {
            val r = IntRange(1, n).toMutableList()
            r.remove(i)
            permRecursion(n, 2, listOf(i), r, arr)
        }

        val retList : MutableList<MutableList<Int>> = mutableListOf()
        for (iter in 0 until iterationCount) {
            val rowList :MutableList<Int> = mutableListOf()
            for (col in 0 until n) {
                rowList.add(arr[iter, col])
            }
            retList.add(rowList)
        }
        return retList
    }

    private fun permRecursion(n: Int, lvl: Int, used: List<Int>, available: List<Int>, arr: D2Array<Int>) {
        if (lvl == n) {
            // enter the solution now
            val all = used.toMutableList()
            all.add(available[0])
            for (i in 1..n) {
                arr[iterationCount, i - 1] = all[i - 1]
            }
            iterationCount++
            return
        }

        /*
         * iterate through available and recurse
         */
        for (i in 0 until available.size) {
            val iterVal = available[i]
            val nextUsed = used.toMutableList()
            nextUsed.add(available[i])
            val nextAvailable = available.toMutableList()
            nextAvailable.removeAt(i)
            permRecursion(n, lvl + 1, nextUsed, nextAvailable, arr)
        }
    }

}