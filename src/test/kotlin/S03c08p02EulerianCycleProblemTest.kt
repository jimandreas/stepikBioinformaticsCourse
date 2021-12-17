import algorithms.EulerConnectionData
import algorithms.EulerianPathArrayBased
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class S03c08p02EulerianCycleProblemTest {

    private val ep = EulerianPathArrayBased()

    @Test
    @DisplayName("test eulerCycleParseLine 01")
    fun testEulerCycleParseLine() {

        val eulerConnectionString = "0 -> 1,2,3"

        val result = ep.eulerCycleParseLine(eulerConnectionString)

        val expectedResult = EulerConnectionData(0, mutableListOf(1, 2, 3))

        assertEquals(expectedResult, result)
    }

    /**
     * data is from
     * @link: https://stepik.org/lesson/240261/step/2?unit=212607
     *
     *
     * Code Challenge: Solve the Eulerian Cycle Problem.

    Input: The adjacency list of an Eulerian directed graph.
    Output: An Eulerian cycle in this graph.

     *
     */
    @Test
    @DisplayName("test eulerCycleMap 01")
    fun testEulerCycleMap01() {

        val eulerConnectionString = """
            0 -> 3
            1 -> 0
            2 -> 1,6
            3 -> 2
            4 -> 2
            5 -> 4
            6 -> 5,8
            7 -> 9
            8 -> 7
            9 -> 6
        """.trimIndent()
        val result = ep.eulerCycleMap(eulerConnectionString)

        val path = ep.eulerCycleConvertData(result)
        assertEquals(result.size, path.size)

        ep.setGraph(path)
        val solution = ep.solveEulerianPath()
        val expectedSolution = listOf(9, 6, 5, 4, 2, 1, 0, 3, 2, 6, 8, 7, 9)
        assertEquals(expectedSolution, solution)
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

}