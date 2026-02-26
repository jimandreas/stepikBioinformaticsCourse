@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "UnnecessaryVariable",
    "UnnecessaryVariable"
)

import algorithms.Clustering
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * See also:
 * Rosalind: https://rosalind.info/problems/ba8c/
 *
 * Youtube:
 * Clustering as an Optimization Problem (Farthest First Traversal)
 * https://youtu.be/JAC0GqadoiA?t=369
 *
 * The Lloyd Algorithm for k-Means Clustering
 * https://www.youtube.com/watch?v=9rp1pzYn3hY
 */

internal class S08C07P03ClustersLloydAlgorithmKmeansFromFileTest {

    lateinit var cluster: Clustering

    @BeforeEach
    fun setUp() {
        cluster = Clustering()
    }

    @Test
    @DisplayName("Clusters Lloyd Algorithm k means extra dataset test")
    fun clustersLloydAlgorithmKmeansExtraDatasetTest() {
        val loader = Foo()
        val sampleInput = loader.getResourceAsStrings("ClustersLloydAlgorithmExtraDatasetInput.txt")

        // numCentersK: Int, numDimensionsM: Int

        val inputString = sampleInput.toMutableList()
        val lineOne = inputString.removeFirst().split(" ")
        val numCentersK = lineOne[0].toInt()
        val numDimensionsM = lineOne[1].toInt()
        val points: List<List<Double>> = inputString.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        val result = cluster.lloydAlgorithmKmeans(numCentersK, numDimensionsM,  points)

        //printResult(result)

        val expectedResult = """
7.561 6.167 16.568 6.078 7.096
18.232 6.147 5.468 6.578 6.053
7.037 17.299 6.927 5.495 7.028
7.712 7.233 6.916 18.717 6.838
6.042 6.279 5.708 7.014 17.408
5.158 4.559 5.113 5.144 4.719
        """.trimIndent()

        checkResult(expectedResult, result)

    }

    @Test
    @DisplayName("Clusters Lloyd Algorithm k means rosalind quiz test")
    fun clustersLloydAlgorithmKmeansRosalindQuizTest() {
        val loader = Foo()
        val sampleInput = loader.getResourceAsStrings("ClustersLloydAlgorithmRosalindQuizInput.txt")

        // numCentersK: Int, numDimensionsM: Int

        val inputString = sampleInput.toMutableList()
        val lineOne = inputString.removeFirst().split(" ")
        val numCentersK = lineOne[0].toInt()
        val numDimensionsM = lineOne[1].toInt()
        val points: List<List<Double>> = inputString.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        val result = cluster.lloydAlgorithmKmeans(numCentersK, numDimensionsM,  points)

        //printResult(result)

        val expectedResult = """
6.741 7.796 17.563 6.516
17.535 7.106 6.565 6.354
5.174 4.520 5.235 4.780
6.911 5.986 6.971 17.497
6.303 16.193 5.592 6.777
        """.trimIndent()

        checkResult(expectedResult, result)

    }

    @Test
    @DisplayName("Clusters Lloyd Algorithm k means stepik quiz test")
    fun clustersLloydAlgorithmKmeansStepikQuizTest() {
        val loader = Foo()
        val sampleInput = loader.getResourceAsStrings("ClustersLloydAlgorithmStepikQuizInput.txt")

        // numCentersK: Int, numDimensionsM: Int

        val inputString = sampleInput.toMutableList()
        val lineOne = inputString.removeFirst().split(" ")
        val numCentersK = lineOne[0].toInt()
        val numDimensionsM = lineOne[1].toInt()
        val points: List<List<Double>> = inputString.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        val result = cluster.lloydAlgorithmKmeans(numCentersK, numDimensionsM,  points)

        //printResult(result)

        val expectedResult = """
6.983 7.092 5.955 17.357
4.701 3.505 5.168 5.094
4.549 12.070 5.144 5.860
15.399 7.361 3.956 4.836
5.653 6.410 17.643 6.225
7.294 20.767 8.669 6.948
18.056 5.247 11.845 7.512
        """.trimIndent()

        checkResult(expectedResult, result)

    }


    private fun printResult(result: List<List<Double>>) {
        for (r in result) {
            for (i in 0 until r.size) {
                print(String.format("%5.3f", r[i]))
                if (i == r.size - 1) {
                    print("\n")
                } else {
                    print(" ")
                }
            }
        }
    }

    private fun checkResult(str: String, result: List<List<Double>>) {
        val lines = str.lines()
        val expectedResultList: MutableList<List<Double>> = mutableListOf()
        val coords =  lines.map { it.split(" ") }
        for (line in 0 until coords.size) {
            for (i in 0 until coords[line].size) {
                val rcoord = result[line][i]
                val expectedcoord = coords[line][i].toDouble()
                assertEquals(rcoord, expectedcoord, 0.01)
            }
        }
    }

    class Foo {
        fun getResourceAsStrings(path: String): List<String> {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText().lines()
            return retStr
        }
    }


}