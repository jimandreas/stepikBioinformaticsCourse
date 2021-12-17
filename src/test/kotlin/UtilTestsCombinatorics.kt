@file:Suppress("ReplaceManualRangeWithIndicesCalls")

import algorithms.UtilityCombinatorics
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class UtilTestsUtilityCombinatorics {

    /**
     * see: https://en.wikipedia.org/wiki/Factorial
     */
    @DisplayName("factorial test")
    @Test
    fun testFactorial() {
        val zero = UtilityCombinatorics.fact(0)
        assertEquals(1, zero)
        val one = UtilityCombinatorics.fact(1)
        assertEquals(1, one)
        val two = UtilityCombinatorics.fact(2)
        assertEquals(2, two)
        val three = UtilityCombinatorics.fact(3)
        assertEquals(6, three)
        val four = UtilityCombinatorics.fact(4)
        assertEquals(24, four)

        val big = UtilityCombinatorics.fact(11)
        assertEquals(39916800, big)

    }

    /**
     * Binomial Coefficient - Factorial formula
     * calculate n select k = n! / ( k! * (n - k)!)
     * https://en.wikipedia.org/wiki/Binomial_coefficient
     *
     * Read it as: n Select k items
     */

    @DisplayName("Binomial Coefficient test - n Select k")
    @Test
    fun binomialCoefficientTest() {
        val one = UtilityCombinatorics.select(1, 1)
        assertEquals(1, one)
        val two = UtilityCombinatorics.select(2, 1)
        assertEquals(2, two)
        val three = UtilityCombinatorics.select(3, 1)
        assertEquals(3, three)

        val v1 = UtilityCombinatorics.select(6, 2)
        assertEquals(15, v1)

        val v2 = UtilityCombinatorics.select(8, 4)
        assertEquals(70, v2)

        val err = UtilityCombinatorics.select(1, -1)
        assertEquals(0, err)
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

    @DisplayName("Permutation test")
    @Test
    fun permutationTest() {

        //val p1 = UtilityCombinatorics.permutation(2)
        val p2 = UtilityCombinatorics.permutation(6)
        println(UtilityCombinatorics.fact(6))
        for (i in 0 until p2.size) {
            val l = p2[i]
            println(l.joinToString(" "))
        }

    }
}