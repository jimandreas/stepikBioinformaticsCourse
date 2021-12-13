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

internal class S08C13P04ClustersSoftKmeansAlgorithmFromFileTest {

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
    @DisplayName("Clusters Soft K Means ExtraDataset test")
    fun clustersSoftKmeansAlgorithmExtraDatasetTest() {

        val loader = Foo()
        val sampleInput = loader.getResourceAsStrings("ClustersSoftKmeansAlgorithmExtraDatasetInput.txt").toMutableList()

        // numCentersK: Int, numDimensionsM: Int

        val lineOne = sampleInput.removeFirst().split(" ")
        val numCentersK = lineOne[0].toInt()
        val numDimensionsM = lineOne[1].toInt()
        val stiffnessParameter = sampleInput.removeFirst().toDouble()

        val points: List<List<Double>> = sampleInput.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        val result = cluster.softKmeansClusteringAlgorithm(numCentersK, numDimensionsM, stiffnessParameter, points)

        val expectedResult = """
5.889 16.921 6.873
20.404 8.236 9.055
3.590 4.853 4.970
11.329 5.448 5.319
5.761 6.494 17.227
        """.trimIndent()

        //printResult(result)
        checkResult(expectedResult, result)

    }


    @Test
    @DisplayName("Clusters Soft K Means Rosalind Quiz test")
    fun clustersSoftKmeansAlgorithmRosalindQuizTest() {

        val loader = Foo()
        val sampleInput = loader.getResourceAsStrings("ClustersSoftKmeansAlgorithmRosalindQuizInput.txt").toMutableList()

        // numCentersK: Int, numDimensionsM: Int

        val lineOne = sampleInput.removeFirst().split(" ")
        val numCentersK = lineOne[0].toInt()
        val numDimensionsM = lineOne[1].toInt()
        val stiffnessParameter = sampleInput.removeFirst().toDouble()

        val points: List<List<Double>> = sampleInput.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        val result = cluster.softKmeansClusteringAlgorithm(numCentersK, numDimensionsM, stiffnessParameter, points)

        val expectedResult = """
7.266 7.619 18.971 7.352 9.791
5.136 4.855 5.138 4.864 8.412
6.412 17.599 6.753 7.180 7.091
17.791 6.797 6.992 6.291 7.560
6.908 6.143 6.675 16.021 7.982
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

    class Foo {
        fun getResourceAsStrings(path: String): List<String> {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText().lines()
            return retStr
        }
    }


}