@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.PhylogenyLimbLength

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

internal class S07C03p11PhylogenyLimbLengthTest {

    lateinit var ll: PhylogenyLimbLength


    @BeforeEach
    fun setUp() {
        ll = PhylogenyLimbLength()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Distances Between Leaves sample test")
    fun phylogenyLimbLengthSampleTest() {
        val sampleInput = """
            4
            1
            0	13	21	22
            13	0	12	13
            21	12	0	13
            22	13	13	0
        """.trimIndent()

        val expectedOutput = 2
        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        val whichRow = input[1].trim().toInt()
        input.removeFirst()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val result = ll.calculateLimbLength(matrixSize, whichRow, m)

        assertEquals(expectedOutput, result)

    }

    /**
     * convert a string of the form (with tab or space separated numbers)
    0    13    21    22
    13     0    12    13
    21    12     0    13
    22    13    13     0

    into a D2Array given
    @param matrixSize - the size N of an NxN matrix
     */

    private fun parseMatrixInput(matrixSize: Int, lines: List<String>): D2Array<Int> {
        val theMatrix = mk.d2array(matrixSize, matrixSize) {0}

        for (i in 0 until matrixSize) {
            val l = lines[i].trim().split("\t", " ")
            for (j in 0 until matrixSize) {
                theMatrix[i, j] = l[j].toInt()
            }
        }
        return theMatrix
    }

    /**
     * pretty print a 2D matrix
     */
    private fun printit(matrixSize: Int, gArr: D2Array<Int>) {
        val outStr = StringBuilder()
        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                val numVal = String.format("%5d", gArr[i, j])
                outStr.append(numVal)
                if (j < matrixSize - 1) {
                    outStr.append(" ")
                }
            }
            outStr.append("\n")
        }
        println(outStr.toString())
    }


}