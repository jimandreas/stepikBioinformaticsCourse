@file:Suppress("UNUSED_VARIABLE", "UnnecessaryVariable")

import org.junit.jupiter.api.*

import algorithms.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertEquals

internal class UtilityArrayTests {

    @Test
    @DisplayName("UtilityArrayTests - tests of array manipulation functions1")
    fun arrayManipulationTest1() {

        val inputArray = """
            0.0 1.0 2.0
            3.0 4.0 5.0
            6.0 7.0 8.0
        """.trimIndent().lines().toMutableList()

        val testMatrix = parseMatrix(inputArray)

        // remove column 1 and check result
        val t1 = deepCopyArray(testMatrix)

        val result1 = removeColumn(1, t1)

        val expectedResult1 = """
            0.0 2.0
            3.0 5.0
            6.0 8.0
        """.trimIndent()

        checkResult(expectedResult1, result1)

        // remove row 1 and check result
        val t2 = deepCopyArray(testMatrix)

        val result2 = removeRow(1, t2)

        val expectedResult2 = """
            0.0 1.0 2.0
            6.0 7.0 8.0
        """.trimIndent()

        checkResult(expectedResult2, result2)

    }
    private fun parseMatrix(matrixStrings: MutableList<String>): MutableList<MutableList<Double>> {

        val matrix: MutableList<MutableList<Double>> = mutableListOf()

        for (row in 0 until matrixStrings.size) {
            val rowValues = matrixStrings[row].split(" ").map { it.toDouble()}.toMutableList()
            matrix.add(rowValues)
        }

        return matrix
    }

    private fun checkResult(str: String, result: List<List<Double>>) {
        val lines = str.lines()
        val expectedResultList: MutableList<List<Double>> = mutableListOf()
        val coords = lines.map { it.split(" ") }
        for (line in 0 until coords.size) {
            for (i in 0 until coords[line].size) {
                val rcoord = result[line][i]
                val expectedcoord = coords[line][i].toDouble()
                assertEquals(rcoord, expectedcoord, 0.01)
            }
        }
    }
}