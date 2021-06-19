@file:Suppress("UnnecessaryVariable")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

internal class S01c07p10Test {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * Supplied test:
     * TEST DATASET 1:
    Input :
    ACCG
    Output :
    3
    This dataset checks if your code’s indexing is off. Specifically, it verifies that your code
    is not returning an index 1 too high (i.e. 4) or 1 too low (i.e. 2).
     */
    @Test
    @DisplayName( "test with DNA strip supplied by class")
    fun testSmallGenomeForMinSkewValues() {
        val testString = "ACCG"
        val outputList = listOf(3)

        val minList = scanForSkew(testString)

        assertEquals(outputList, minList)
    }

    /**
     * supplied by class:
     * TEST DATASET 2:
    Input :
    ACCC
    Output :
    4
    This dataset checks to see if your code is missing the last symbol of Genome .
     */

    @Test
    @DisplayName( "test with DNA strip end condition")
    fun testSmallGenomeForMinSkewValuesEndCondition() {
        val testString = "ACCC"
        val outputList = listOf(4)

        val minList = scanForSkew(testString)

        assertEquals(outputList, minList)
    }

    /**
     * test supplied by class
     * TEST DATASET 3:
    Input :
    CCGGGT
    Output :
    2
    This dataset makes sure you’re not accidentally finding the maximum skew instead of the
    minimum skew.
     */

    @Test
    @DisplayName( "test with DNA strip skew conditional")
    fun testSmallGenomeForMinSkewValuesSkewConditional() {
        val testString = "CCGGGT"
        val outputList = listOf(2)

        val minList = scanForSkew(testString)

        assertEquals(outputList, minList)
    }

    /**
     * supplied by class:
     * TEST DATASET 4:
    Input :
    CCGGCCGG
    Output :
    2 6
    First, this dataset checks if you are only finding 1 index (and not multiple indices). Then,
    it checks if you are using a delimiter to separate your indices (ideally a space character).
     */

    @Test
    @DisplayName( "test with DNA strip multiple indexes")
    fun testSmallGenomeForMinSkewValuesMultipleIndexes() {
        val testString = "CCGGCCGG"
        val outputList = listOf(2, 6)

        val minList = scanForSkew(testString)

        assertEquals(outputList, minList)
    }

    @Test
    @DisplayName( "test with LARGE DNA strip supplied by class")
    fun testLargeGenomeForMinSkewValues() {

        val loader = Foo()
        val testString = loader.getResourceAsText("S01c07p10testInput.txt")
        val output = "89969 89970 89971 90345 90346"
        val outputList = listOf(89969, 89970, 89971, 90345, 90346)

        val minList = scanForSkew(testString)

        assertEquals(outputList, minList)
    }
}

class Foo {
    fun getResourceAsText(path: String): String {
        val ress = this.javaClass.getResource(path)
        val retStr = ress!!.readText()
        return retStr
    }

}