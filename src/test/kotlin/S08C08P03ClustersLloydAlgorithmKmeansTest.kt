@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty"
)

import algorithms.Clustering
import algorithms.assignPointsToClusters
import algorithms.clusterCenterOfGravity
import algorithms.compareTwoMaps
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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

internal class S08C08P03ClustersLloydAlgorithmKmeansTest {

    lateinit var cluster: Clustering

    @BeforeEach
    fun setUp() {
        cluster = Clustering()
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * a simple touch test of the center of gravity function (multidimensional)
     */
    @Test
    @DisplayName("Clusters Squared Error Distortion assign points to cluster test")
    fun clustersSquaredErrorDistortionAssignPointsToClusterTest() {

        // centers

        val inputString1 = """
            0.0 0.0
            5.0 5.0
        """.trimIndent().lines().toMutableList()

        val centers1: List<List<Double>> = inputString1.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        // points

        val inputString2 = """
            4.0 4.0
            1.0 1.0
            2.0 2.0
        """.trimIndent().lines().toMutableList()

        val points1: List<List<Double>> = inputString2.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        val result1 = assignPointsToClusters(2, 2, centers1,  points1)
        val expectedResult1 = mapOf(
            Pair(0, listOf(1, 2)),
            Pair(1, listOf(0))
        )

        // uses Utility "compareTwoMaps" for equivalence test
        assertTrue(expectedResult1.compareTwoMaps(result1))

    }


    /**
     * a simple touch test of the center of gravity function (multidimensional)
     */
    @Test
    @DisplayName("Clusters Squared Error Distortion center of gravity test")
    fun clustersSquaredErrorDistortionCenterOfGravityTest() {
        val inputString1 = """
            0.0 0.0
            2.0 2.0
        """.trimIndent().lines().toMutableList()

        val points1: List<List<Double>> = inputString1.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        val result1 = clusterCenterOfGravity( points1)
        val expectedResult1 = listOf(1.0, 1.0)
        assertContentEquals(expectedResult1, result1)

        val inputString2 = """
            0.0 0.0 0.0
            3.0 3.0 10.0
            6.0 6.0 20.0
        """.trimIndent().lines().toMutableList()

        val points2: List<List<Double>> = inputString2.map { subjectString ->
            subjectString.split(" ").map { it.toDouble() }
        }

        val result2 = clusterCenterOfGravity( points2)
        val expectedResult2 = listOf(3.0, 3.0, 10.0)
        assertContentEquals(expectedResult2, result2)
    }


    @Test
    @DisplayName("Clusters Lloyd Algorithm k means sample test")
    fun clustersLloydAlgorithmKmeansSampleTest() {
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

        //printResult(result)

        val expectedResult = """
            1.800 2.867
            1.060 1.140
        """.trimIndent()

        checkResult(expectedResult, result)

    }



    private fun printResult(result: List<List<Double>>) {
        for (r in result) {
            for (i in 0 until r.size) {
                print(String.format("%6.3f", r[i]))
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

}