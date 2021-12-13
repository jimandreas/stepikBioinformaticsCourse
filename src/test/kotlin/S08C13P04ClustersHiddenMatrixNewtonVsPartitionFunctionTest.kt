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
 * Stepik: https://stepik.org/lesson/240365/step/3?unit=212711
 * Rosalind: https://rosalind.info/problems/ba8d/
 *
 * Youtube:
 * Soft k-Means Clustering 8/9
 * https://www.youtube.com/watch?v=fpM0iZTjLhM
 */

internal class S08C13P04ClustersHiddenMatrixNewtonVsPartitionFunctionTest {

    lateinit var cluster: Clustering

    @BeforeEach
    fun setUp() {
        cluster = Clustering()
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * a touch test of the hiddenMatrixNewtonian weighting function
     * @link: https://stepik.org/lesson/240365/step/4?unit=212711
     */
    @Test
    @DisplayName("Clusters hiddenMatrixNewtonian test")
    fun clustersHiddenMatrixNewtonianTest() {


        val centers = listOf(listOf(-2.5), listOf(2.5))

        val points = listOf(
            listOf(-3.0),
            listOf(-2.0),
            listOf(0.0),
            listOf(2.0),
            listOf(3.0)
        )

        val result1 = hiddenMatrixNewtonian(centers, points)


        val expectedResult1 = mapOf(
            Pair(0, listOf(0.992, 0.988, 0.500, 0.012, 0.008)),
            Pair(1, listOf(0.008, 0.012, 0.500, 0.988, 0.992))
        )

        // there are two centers in this test
        for (i in listOf(0, 1)) {
            val expectedList = expectedResult1[i]!!
            val resultList = result1[i]!!
            for (p in 0 until points.size) {
                assertEquals(expectedList[p], resultList[p], 0.01)
            }
        }

    }

    /**
     * a touch test of the hiddenMatrixPartitionFunction weighting function
     * @link: https://stepik.org/lesson/240365/step/4?unit=212711
     * @link: https://rosalind.info/problems/ba8d/
     */
    @Test
    @DisplayName("Clusters hiddenMatrixPartitionFunction test")
    fun clustersHiddenMatrixPartitionFunctionTest() {


        val centers = listOf(listOf(-2.5), listOf(2.5))

        val points = listOf(
            listOf(-3.0),
            listOf(-2.0),
            listOf(0.0),
            listOf(2.0),
            listOf(3.0)
        )

        val stiffNessB = listOf(0.00000001, 0.5, 1.0, 100.0)

        val expectedTrialResults = listOf(
            mapOf(  // stiffness = 0.0
                Pair(0, listOf(0.500, 0.500, 0.500, 0.500, 0.500)),
                Pair(1, listOf(0.500, 0.500, 0.500, 0.500, 0.500))
            ),
            mapOf(  // stiffness = 0.5
                Pair(0, listOf(0.924, 0.881, 0.500, 0.119, 0.076)),
                Pair(1, listOf(0.076, 0.119, 0.500, 0.881, 0.924))
            ),
            mapOf(  // stiffness = 1.0
                Pair(0, listOf(0.993, 0.982, 0.500, 0.018, 0.007)),
                Pair(1, listOf(0.007, 0.018, 0.500, 0.982, 0.993))
            ),
            mapOf(  // stiffness = 1000
                Pair(0, listOf(1.000, 1.000, 0.500, 0.000, 0.000)),
                Pair(1, listOf(0.000, 0.000, 0.500, 1.000, 1.000))
            )
        )

        for (j in 0 until stiffNessB.size) {
            val result1 = hiddenMatrixPartitionFunction(stiffNessB[j], centers, points)
            // there are two centers in this test
            val expectedResult1 = expectedTrialResults[j]
            for (i in listOf(0, 1)) {
                val expectedList = expectedResult1[i]!!
                val resultList = result1[i]!!
                for (p in 0 until points.size) {
                    assertEquals(expectedList[p], resultList[p], 0.01)
                }
            }
        }


    }

    /**
     * a touch test of the hiddenMatrixPartitionFunction weighting function
     * @link: https://stepik.org/lesson/240365/step/4?unit=212711
     * @link: https://rosalind.info/problems/ba8d/
     */
    @Test
    @DisplayName("Clusters calcNewCenter test")
    fun clustersCalcNewCenterTest() {


        val centers = listOf(listOf(-2.5), listOf(2.5))

        val points = listOf(
            listOf(-3.0),
            listOf(-2.0),
            listOf(0.0),
            listOf(2.0),
            listOf(3.0)
        )


        val hiddenMatrix0 = listOf(0.993, 0.982, 0.500, 0.018, 0.007)
        val hiddenMatrix1 = listOf(0.007, 0.018, 0.500, 0.982, 0.993)

        // see: https://stepik.org/lesson/240365/step/7?unit=212711

        val center0 = cluster.calcNewCenter(hiddenMatrix0, points)
        assertEquals(-1.9544, center0[0], 0.01)

        val center1 = cluster.calcNewCenter(hiddenMatrix1, points)
        assertEquals(1.9544, center1[0], 0.01)
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