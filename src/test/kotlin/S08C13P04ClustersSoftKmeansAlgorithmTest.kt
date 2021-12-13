@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty"
)

import algorithms.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * See also:
 * Stepik: no equivalent problem
 * Rosalind: https://rosalind.info/problems/ba8d/
 *
 * Youtube:
 * Soft k-Means Clustering 8/9
 * https://www.youtube.com/watch?v=fpM0iZTjLhM
 */

internal class S08C13P04ClustersSoftKmeansAlgorithmTest {

    lateinit var cluster: Clustering

    @BeforeEach
    fun setUp() {
        cluster = Clustering()
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * Implement the Soft k-Means Clustering Algorithm
     *
     * Given: Integers k and m, followed by a stiffness parameter β,
     * followed by a set of points Data in m-dimensional space.
     *
     * Return: A set Centers consisting of k points (centers)
     * resulting from applying the soft k-means clustering algorithm.
     * Select the first k points from Data as the first centers for
     * the algorithm and run the algorithm for 100 steps.
     * Results should be accurate up to three decimal places.
     *
     */
    @Test
    @DisplayName("Clusters Soft K Means Sample test")
    fun clustersSoftKmeansAlgorithmSampleTest() {

        val inputString = """
            2 2
            2.7
            1.3 1.1
            1.3 0.2
            0.6 2.8
            3.0 3.2
            1.2 0.7
            1.4 1.6
            1.2 1.0
            1.2 1.1
            0.6 1.5
            1.8 2.6
            1.2 1.3
            1.2 1.0
            0.0 1.9
        """.trimIndent().lines().toMutableList()

        // numCentersK: Int, numDimensionsM: Int

        val lineOne = inputString.removeFirst().split(" ")
        val numCentersK = lineOne[0].toInt()
        val numDimensionsM = lineOne[1].toInt()
        val stiffnessParameter = inputString.removeFirst().toDouble()

        val points: List<List<Double>> = inputString.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        val result = cluster.softKmeansClusteringAlgorithm(numCentersK, numDimensionsM, stiffnessParameter, points)

        val expectedResult = """
            1.662 2.623
            1.075 1.148
        """.trimIndent()

        //printResult(result)
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