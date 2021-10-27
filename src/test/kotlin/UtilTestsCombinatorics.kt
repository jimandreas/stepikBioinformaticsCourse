@file:Suppress("ReplaceManualRangeWithIndicesCalls")

import algorithms.Combinatorics
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class UtilTestsCombinatorics {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * see: https://en.wikipedia.org/wiki/Factorial
     */
    @DisplayName("factorial test")
    @Test
    fun testFactorial() {
        val zero = Combinatorics.fact(0)
        assertEquals(1, zero)
        val one = Combinatorics.fact(1)
        assertEquals(1, one)
        val two = Combinatorics.fact(2)
        assertEquals(2, two)
        val three = Combinatorics.fact(3)
        assertEquals(6, three)
        val four = Combinatorics.fact(4)
        assertEquals(24, four)

        val big = Combinatorics.fact(11)
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
        val one = Combinatorics.select(1, 1)
        assertEquals(1, one)
        val two = Combinatorics.select(2, 1)
        assertEquals(2, two)
        val three = Combinatorics.select(3, 1)
        assertEquals(3, three)

        val v1 = Combinatorics.select(6, 2)
        assertEquals(15, v1)

        val v2 = Combinatorics.select(8, 4)
        assertEquals(70, v2)

        val err = Combinatorics.select(1, -1)
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

        //val p1 = Combinatorics.permutation(2)
        val p2 = Combinatorics.permutation(6)
        println(Combinatorics.fact(6))
        for (i in 0 until p2.size) {
            val l = p2[i]
            println(l.joinToString(" "))
        }

    }
}