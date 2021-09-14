@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import algorithms.GreedySorting
import kotlin.test.assertEquals

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

internal class S06C04p04GreedySortingTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }


    /**
     * TEST DATASET 1:
     * This test does a basic test of the GreedySorting algorithm
     */
    @Test
    @DisplayName("linear space alignment test 01")
    fun findMiddleEdgeTest01() {

        val permutation = listOf(1, 2, 3)
        val expectedString = ""

        runTest(permutation, expectedString)
    }

    /**
     * TEST DATASET 2:
     * This test does a basic test of the GreedySorting algorithm
     */
    @Test
    @DisplayName("linear space alignment test 02")
    fun findMiddleEdgeTest02() {

        val permutation = listOf(1, 3, 2)
        val expectedString = """
+1 -2 -3
+1 +2 -3
+1 +2 +3

        """.trimIndent()

        runTest(permutation, expectedString)
    }

    /**
     * TEST DATASET 2:
     * This test does a basic test of the GreedySorting algorithm
     */
    @Test
    @DisplayName("linear space alignment test 03")
    fun findMiddleEdgeTest03() {

        val sampleInput = "-3 +4 +1 +5 -2"
        val permutation = sampleInput.split(' ').map { it.toInt() }

        val expectedString = """
-1 -4 +3 +5 -2
+1 -4 +3 +5 -2
+1 +2 -5 -3 +4
+1 +2 +3 +5 +4
+1 +2 +3 -4 -5
+1 +2 +3 +4 -5
+1 +2 +3 +4 +5

        """.trimIndent()

        runTest(permutation, expectedString)
    }

    /**
     *  A test function to home in on the answer to
     *  @link: https://stepik.org/lesson/240319/step/5?unit=212665
     */
    @Test
    @DisplayName("Determine maximum")
    fun findMaxReversalsTest() {

        for (i in 3..100) {

            val testList = (i downTo 1).toList().toTypedArray().toList()
            val w = genWorstCaseP(i)
            val gs = GreedySorting(w.size)
            gs.gArr = w.toNDArray()
            gs.sort()

            val pos = gs.outStr.toString().lines().size - 1

//            val testListN = testList.map { if (it%2 == 0) {it} else {-it} }
            val testListN = testList.map { -it }
            val gsN = GreedySorting(testListN.size)
            gsN.gArr = testListN.toNDArray()
            gsN.sort()

            val neg = gsN.outStr.toString().lines().size - 1

    //       println("$i pos $pos neg $neg")

        }
    }

    fun genWorstCaseP(size: Int): List<Int> {
        val m = mk.d1array(size) { 0 }
        for (i in 1..size) {
            if (i == size) {
                if (i % 2 == 1) {
                    m[i / 2] = i
                } else {
                    m[i / 2] = -(i - 1)
                }
            } else if (i % 2 == 1) {
                m[i / 2] = -(i + 1)
            } else {
                m[size - i / 2] = i - 1
            }
        }
        return m.toList()
    }


    fun runTest(p: List<Int>, expectedString: String) {
        val gs = GreedySorting(p.size)
        gs.gArr = p.toNDArray()
        gs.sort()

        val resultString = gs.outStr.toString()

        assertEquals(expectedString, resultString)
    }

}