import algorithms.EulerianPathSymbolicMap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 *  testing the euler cycle code - converted to handle the nodes as hash key entries
 *  rather than array indexes.
 */
internal class S03c08p06EulerianCycleSYMBOLICTest {

    private val ep = EulerianPathSymbolicMap()

    @Test
    @DisplayName("test eulerCycleParseLine 01")
    fun testEulerCycleParseLine() {

        val eulerConnectionString = """
            1 -> 2
            2 -> 1,2
        """.trimIndent()

        val result = ep.eulerCycleMap(eulerConnectionString)
        val path = ep.eulerCycleConvertData(result)
        ep.setGraph(path)
        val solution = ep.solveEulerianPath()
        val expectedValue = listOf(2, 2, 1, 2)
        assertEquals(expectedValue, solution)
    }

    /**
     *  A rather nice regular test case supplied in the test dataset
     *    Very symmetric!
     */
    @Test
    @DisplayName("test eulerCycleMap 02")
    fun testEulerCycleMap02() {

        val eulerConnectionString = """
            0 -> 1,2,3,4
            1 -> 0,2,3,4
            2 -> 0,1,3,4
            3 -> 0,1,2,4
            4 -> 0,1,2,3
        """.trimIndent()
        val result = ep.eulerCycleMap(eulerConnectionString)

        val path = ep.eulerCycleConvertData(result)
        assertEquals(result.size, path.size)

        ep.setGraph(path)
        val solution = ep.solveEulerianPath()
        val expectedValue = listOf(4, 3, 4, 2, 4, 1, 4, 0, 3, 2, 3, 1, 3, 0, 2, 1, 2, 0, 1, 0, 4)
        assertEquals(expectedValue, solution)
    }

    @Test
    @DisplayName("test eulerCycleMap 03")
    fun testEulerCycleMap03() {

        val eulerConnectionString = """
            1 -> 10
            10 -> 2,3,4
            2 -> 1
            3 -> 10
            4 -> 5
            5 -> 10
        """.trimIndent()
        val result = ep.eulerCycleMap(eulerConnectionString)

        val path = ep.eulerCycleConvertData(result)
        assertEquals(result.size, path.size)

        ep.setGraph(path)
        val solution = ep.solveEulerianPath()
        val expectedValue = listOf(5,10,3,10,2,1,10,4,5)
        assertEquals(expectedValue, solution)
    }

}