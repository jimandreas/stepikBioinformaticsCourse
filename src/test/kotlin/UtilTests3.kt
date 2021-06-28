@file:Suppress("UNUSED_VARIABLE")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import util.*
import kotlin.math.exp
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class UtilTests3 {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * The String Spelled by a Genome Path Problem.
     * first test - the denerate case of single character input
     */
    @Test
    @DisplayName("util: path to string test 01")
    fun testPathToString01() {
        val inputString = listOf("A", "B", "C")
        val result = pathToGenome(inputString)

        val expectedResult = "ABC"
        assertEquals(expectedResult, result)

    }

    /**
     * The String Spelled by a Genome Path Problem.
     * first test - the denerate case of single character input
     */
    @Test
    @DisplayName("util: path to string test 02")
    fun testPathToString02() {
        val inputString = listOf("AB", "BC", "CD")
        val result = pathToGenome(inputString)

        val expectedResult = "ABCD"
        assertEquals(expectedResult, result)
    }





}