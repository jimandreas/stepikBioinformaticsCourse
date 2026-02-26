import algorithms.EulerianPathStrings
import algorithms.deBruijnDirectedGraphConversion
import algorithms.deBruijnGraphFromKmers
import algorithms.pathToGenome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * see also:
 * From Euler's Theorem to an Algorithm for Finding Eulerian Cycles
 *
You now have a method to assemble a genome, since the String Reconstruction Problem
reduces to finding an Eulerian path in the de Bruijn graph generated from reads.

We can therefore summarize this solution using the following pseudocode,
which relies on three problems that we have already solved:

The de Bruijn Graph Construction Problem;
The Eulerian Path Problem;
The String Spelled by a Genome Path Problem.

 NOTES:
 1) the EulerPath program was duplicated and modified to work on strings rather than integers.

 */
internal class S03c08p07StringReconstructionProblemTest {

    private val ep = EulerianPathStrings()

    @Test
    @DisplayName("test eulerCycleParseLineStrings 01")
    fun testEulerCycleParseLineStrings() {

        val eulerConnectionString = """
            AGG -> GGG
            GGG -> AGG
        """.trimIndent()

        val result = ep.eulerCycleMap(eulerConnectionString)
        val path = ep.eulerCycleConvertData(result)
        ep.setGraph(path)
        val solution = ep.solveEulerianPath()
        val expectedValue = listOf("GGG", "AGG", "GGG")
        assertEquals(expectedValue, solution)
    }

    @Test
    @DisplayName("test eulerCycleParseLineStrings 02")
    fun testEulerCycleParseLineStrings02() {

        val input = listOf(
            "GG",
            "AC",
            "GA",
            "CT"
        )

        val output = deBruijnGraphFromKmers(input)
        val theList = deBruijnDirectedGraphConversion(output)

        val resultMap = ep.eulerCycleMap(theList)
        val path = ep.eulerCycleConvertData(resultMap)
        ep.setGraph(path)
        val solution = ep.solveEulerianPath()

        val resultString = pathToGenome(solution)
        val expectedValue = "GGACT"
        assertEquals(expectedValue, resultString)
    }

    @Test
    @DisplayName("test eulerCycleParseLineStrings 03")
    fun testEulerCycleParseLineStrings03() {

        val input = listOf(
            "AAC",
            "AAC",
            "ACG",
            "ACT",
            "CGA",
            "GAA"
        )

        val output = deBruijnGraphFromKmers(input)
        val theList = deBruijnDirectedGraphConversion(output)

        val resultMap = ep.eulerCycleMap(theList)
        val path = ep.eulerCycleConvertData(resultMap)
        ep.setGraph(path)
        val solution = ep.solveEulerianPath()

        val resultString = pathToGenome(solution)
        val expectedValue = "AACGAACT"
        assertEquals(expectedValue, resultString)
    }

}