@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty"
)

import algorithms.Clustering
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.math.exp
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 * See also:
 * Stepik: https://stepik.org/lesson/240359/step/3?unit=212705
 * Rosalind: https://rosalind.info/problems/ba8b/
 *
 * Youtube:
 * Clustering as an Optimization Problem (Farthest First Traversal)
 * https://youtu.be/JAC0GqadoiA?t=369
 *
 * The Lloyd Algorithm for k-Means Clustering
 * https://www.youtube.com/watch?v=9rp1pzYn3hY
 */

internal class S08C07P02ClustersSquaredErrorDistortionTest {

    lateinit var cluster: Clustering

    @BeforeEach
    fun setUp() {
        cluster = Clustering()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Clusters Squared Error Distortion sample test")
    fun clustersSquaredErrorDistortionSampleTest() {
        val inputString = """
            2 2
            2.31 4.55
            5.96 9.08
            --------
            3.42 6.03
            6.23 8.25
            4.76 1.64
            4.47 4.33
            3.95 7.61
            8.93 2.97
            9.74 4.03
            1.73 1.28
            9.72 5.01
            7.27 3.77
        """.trimIndent().lines().toMutableList()

        // numCentersK: Int, numDimensionsM: Int

        val lineOne = inputString.removeFirst().split(" ")
        val numCentersK = lineOne[0].toInt()
        val numDimensionsM = lineOne[1].toInt()
        val centers: MutableList<List<Double>> = mutableListOf()
        val points: MutableList<List<Double>> = mutableListOf()

        parseInputStringDistortion(
            numCentersK,
            numDimensionsM,
            inputString,
            centers,
            points)

        val result = cluster.squaredErrorDistortion(numCentersK, numDimensionsM, centers, points)

        val expectedResult = 18.246
        println(result)

    }

    /**
     * parse the test input, return in the mutable lists
     */
    private fun parseInputStringDistortion(
        numCentersK: Int,
        numDimensionsM: Int,
        inputString: MutableList<String>,
        centers: MutableList<List<Double>>,
        points: MutableList<List<Double>>) {

        for (i in 0 until numCentersK) {
            val p = inputString.removeFirst().split(" ")
            val point = p.map { it.toDouble() }
            centers.add(point)
        }
        inputString.removeFirst() // remove ---- line

        for (i in 0 until inputString.size) {
            val p = inputString[i].split(" ")
            val point = p.map { it.toDouble() }
            points.add(point)
        }
    }


}