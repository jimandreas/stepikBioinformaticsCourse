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
 * Stepik: https://stepik.org/lesson/240360/step/3?unit=212706
 * Rosalind: https://rosalind.info/problems/ba8c/
 *
 * Youtube:
 * Clustering as an Optimization Problem (Farthest First Traversal)
 * https://youtu.be/JAC0GqadoiA?t=369
 *
 * The Lloyd Algorithm for k-Means Clustering
 * https://www.youtube.com/watch?v=9rp1pzYn3hY
 */

internal class S08C07P02ClustersLloydAlgorithmKmeansTest {

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
        val points: List<List<Double>> = inputString.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        val result = cluster.lloydAlgorithmKmeans(numCentersK, numDimensionsM,  points)
    }

}