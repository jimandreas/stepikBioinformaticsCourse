@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty"
)

import algorithms.*
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * See also:
 * Stepik: no equivalent problem
 * Rosalind: https://rosalind.info/problems/ba8d/
 *
 * Youtube:
 * Soft k-Means Clustering 8/9
 * https://www.youtube.com/watch?v=fpM0iZTjLhM
 */

internal class S08C14P07ClustersHierarchicalTest {

    lateinit var cluster: ClusteringHierarchical

    @BeforeEach
    fun setUp() {
        cluster = ClusteringHierarchical()
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * Implement Hierarchical Clustering
     *
     * Given: An integer n, followed by an nxn distance matrix.
     *
     * Return: The result of applying HierarchicalClustering to
     * this distance matrix (using Davg), with each newly created
     * cluster listed on each line.
     */

    @Test
    @DisplayName("Clusters Soft K Means Sample test")
    fun clustersHierarchicalSampleTest() {

        val inputString = """
            7
            0.00 0.74 0.85 0.54 0.83 0.92 0.89
            0.74 0.00 1.59 1.35 1.20 1.48 1.55
            0.85 1.59 0.00 0.63 1.13 0.69 0.73
            0.54 1.35 0.63 0.00 0.66 0.43 0.88
            0.83 1.20 1.13 0.66 0.00 0.72 0.55
            0.92 1.48 0.69 0.43 0.72 0.00 0.80
            0.89 1.55 0.73 0.88 0.55 0.80 0.00
        """.trimIndent().lines().toMutableList()

        val numElements = inputString.removeFirst().toInt()
        val inputArray = parseMatrix(numElements, inputString)


        val result = cluster.hierarchicalClustering(numElements, inputArray)

        println(result)

    }

    private fun parseMatrix(numElements: Int, matrixStrings: MutableList<String>): MutableList<MutableList<Double>> {

        val matrix: MutableList<MutableList<Double>> = mutableListOf()

        for (row in 0 until numElements) {
            val rowValues = matrixStrings[row].split(" ").map { it.toDouble()}.toMutableList()
            matrix.add(rowValues)
        }

        return matrix
    }


}