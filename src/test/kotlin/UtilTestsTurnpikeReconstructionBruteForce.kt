@file:Suppress("UNUSED_VARIABLE", "UnnecessaryVariable")

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import algorithms.TurnpikeReconstructionBruteForce
import java.util.concurrent.TimeUnit
import kotlin.test.Ignore
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class UtilTestsTurnpikeReconstructionBruteForce {

    private val tpbf = TurnpikeReconstructionBruteForce()

    /**
     * Turnpike Problem: Given all pairwise distances between points on a line segment, reconstruct the positions of those points.

    Input: A collection of integers L.
    Output: A set of integers A such that ∆A = L.

    Code Challenge: Solve the Turnpike Problem.
    @link: https://stepik.org/lesson/240294/step/1?unit=212640
    @link: http://rosalind.info/problems/pdpl/
    @link: http://rosalind.info/glossary/difference-multiset/
     */


    /**
     * test turnpike multiset quality - see the module for the quality requirements
     */
    @Test
    @DisplayName("test turnpike multiset quality")
    fun testTurnpikeMultisetQualityControl() {
        val sampleInput = listOf(-10, -8, -7, -6, -5, -4, -3, -3, -2, -2, 0, 0, 0, 0, 0, 2, 2, 3, 3, 4, 5, 6, 7, 8, 10)

        val result = tpbf.turnpikeQualityControl(sampleInput)
        val expectedResult = listOf(2, 2, 3, 3, 4, 5, 6, 7, 8, 10)
        assertEquals(expectedResult, result)

        // now break the input a little bit and make sure the control returns an error

        val sampleInputBroken = listOf(-8, -7, -6, -5, -4, -3, -3, -2, -2, 0, 0, 0, 0, 0, 2, 2, 3, 3, 4, 5, 6, 7, 8, 10)
        val expectedResult2 = emptyList<Int>()
        val result2 = tpbf.turnpikeQualityControl(sampleInputBroken)
        assertEquals(expectedResult2, result2)

    }

    /**
     * test turnpikeSizeControl
     *    An N must exists such that N(N-1)/2 = size of the array passed in
     */
    @Test
    @DisplayName("test turnpikeSizeControl")
    fun testturnpikeSizeControl() {
        val sampleInput = listOf(2, 2, 3, 3, 4, 5, 6, 7, 8, 10) // first standard test
        val expectedResult = 5
        val result = tpbf.turnpikeSizeControl(sampleInput)
        assertEquals(expectedResult, result)

        // try with broken list (remove leading 2)

        val sampleInput2 = listOf(2, 3, 3, 4, 5, 6, 7, 8, 10)
        val expectedResult2 = 0
        val result2 = tpbf.turnpikeSizeControl(sampleInput2)
        assertEquals(expectedResult2, result2)

        val sampleInput3: List<Int> = emptyList() // error case
        val expectedResult3 = 0
        val result3 = tpbf.turnpikeSizeControl(sampleInput3)
        assertEquals(expectedResult3, result3)
    }

    /**
     * Turnpike Problem: Given all pairwise distances between points on a line segment, reconstruct the positions of those points.

    Input: A collection of integers L.
    Output: A set of integers A such that ∆A = L.

    Code Challenge: Solve the Turnpike Problem.

    @link: https://stepik.org/lesson/240294/step/1?unit=212640

     */

    @Test
    @DisplayName("test the actual turnpikeReconstructionProblem algorithm")
    fun testTurnpikeReconstructionProblem01() {

        val sampleInput = listOf(-10, -8, -7, -6, -5, -4, -3, -3, -2, -2, 0, 0, 0, 0, 0, 2, 2, 3, 3, 4, 5, 6, 7, 8, 10)
        val output = tpbf.turnpikeReconstructionProblem(sampleInput)

//       println(output.joinToString(separator = " "))

        // check the answer

        val diffs = doDiffs(output)
        assertContentEquals(diffs.sorted(), sampleInput.sorted())
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.MINUTES)
    @DisplayName("test the turnpikeReconstructionProblem algorithm with a random dataset")
    fun testturnpikeReconstructionProblemWithRandomData() {
        val testList: MutableList<Int> = mutableListOf()
        for (i in 0..7) {
            testList.add((1..99).random())
        }
        val testListDistinct = testList.sorted().distinct().toMutableList()
        testListDistinct.add(0, 0)
        testListDistinct.add(testListDistinct.size, 100)

        // now have a list of integers from 0 to 100 with 0 and 100 wrapping the list
        val diffs = doDiffs(testListDistinct).sorted()

//       println(tpbf.turnpikeQualityControl(diffs))

        val output = tpbf.turnpikeReconstructionProblem(diffs)
//       println(output)
//       println("$testListDistinct  ORIGINAL")

//        if (output.containsAll(testListDistinct) && testListDistinct.containsAll(output)) {
//           println("Answer matches input set")
//        }

        val diffsOnOutput = doDiffs(output)
        val check = diffsOnOutput.containsAll(diffs) && diffs.containsAll(diffsOnOutput)

//        if (check) {
//           println("answer checks with diffs")
//        }

        assertTrue(check)
    }

    // not yet operational
    // link: https://bioinformaticsalgorithms.com/data/extradatasets/antibiotics/turnpike.txt

    @Test
    @Ignore
    @DisplayName("test the actual turnpikeReconstructionProblem algorithm 02")
    fun testTurnpikeReconstructionProblem02() {

        val loader = Foo()
        // sample large input:
        // link:
        val sampleInput = loader.getResourceAsList("S04c04p19TurnpikeReconstructionInput.txt")
        val output = tpbf.turnpikeReconstructionProblem(sampleInput)
//       println(output.joinToString(separator = " "))

        // check the answer

        val diffs = doDiffs(output)
        assertContentEquals(diffs.sorted(), sampleInput.sorted())

    }

    class Foo {
        fun getResourceAsList(path: String): List<Int> {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText()
            val list: List<Int> = retStr.split(" ").map { it.toInt() }.toList()
            return list
        }

    }

    private fun doDiffs(list: List<Int>): List<Int> {
        val accum: MutableList<Int> = mutableListOf()

        for (i in list) {
            for (j in list) {
                accum.add(i - j)
            }
        }
        //println(accum.sorted())
        return accum
    }
}