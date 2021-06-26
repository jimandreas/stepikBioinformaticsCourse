import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import util.mostProbableKmer

internal class S03c08p02testEulerianCycleProblem {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("test eulerCycleParseLine 01")
    fun testEulerCycleParseLine() {

        val eulerConnectionString = "101 -> 102,103,104"

        val result = eulerCycleParseLine(eulerConnectionString)

        val expectedResult = EulerConnectionData(101, listOf(102, 103, 104))

        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("test eulerCycleMap 01")
    fun testEulerCycleMap01() {
        //fun eulerCycleMap( connList: String ): Map<Int, EulerConnectionData> {

        val eulerConnectionString = """
            101 -> 102,103,104
            102 -> 105
            105 -> 101
            103 -> 101
            104 -> 101
        """.trimIndent()
        val result = eulerCycleMap(eulerConnectionString)

        val expected1 = EulerConnectionData(101, listOf(102, 103, 104))
        assertEquals(expected1, result[101])
        val expected2 = EulerConnectionData(102, listOf(105))
        assertEquals(expected2, result[102])
        val expected3 = EulerConnectionData(104, listOf(101))
        assertEquals(expected3, result[104])
    }

}