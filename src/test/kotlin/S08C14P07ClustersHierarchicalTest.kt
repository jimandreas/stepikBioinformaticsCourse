@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "SameParameterValue",
    "SameParameterValue"
)

import algorithms.ClusteringHierarchical
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

internal class S08C14P07ClustersHierarchicalTest {

    lateinit var cluster: ClusteringHierarchical

    @BeforeEach
    fun setUp() {
        cluster = ClusteringHierarchical()
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

        val expectedResult = """
            4 6
            5 7
            3 4 6
            1 2
            5 7 3 4 6
            1 2 5 7 3 4 6
        """.trimIndent()

        //printResult(result)
        checkResult(expectedResult, result, zeroBasedIndex=true)

    }

    @Test
    @DisplayName("Clusters Soft K Means Extra Dataset test")
    fun clustersHierarchicalExtraDatasetTest() {

        val inputString = """
20
0.00 0.43 0.93 0.85 0.94 0.70 0.95 0.79 0.89 1.34 0.80 0.64 1.42 1.37 0.86 1.20 0.49 0.19 1.48 0.86
0.43 0.00 0.66 0.70 1.48 0.58 0.70 0.55 0.68 1.45 1.31 0.37 1.24 1.69 0.91 1.28 1.04 0.29 0.94 1.05
0.93 0.66 0.00 1.01 1.42 0.80 0.76 1.18 0.21 1.18 1.35 0.82 0.68 0.85 0.75 1.01 0.93 1.03 0.60 0.91
0.85 0.70 1.01 0.00 1.02 0.22 0.89 0.91 0.79 1.47 1.04 1.51 0.71 1.63 0.34 1.05 1.24 0.56 1.09 1.25
0.94 1.48 1.42 1.02 0.00 1.46 1.48 0.95 1.44 1.08 0.30 1.29 1.28 0.65 1.00 0.80 0.80 0.92 1.48 0.65
0.70 0.58 0.80 0.22 1.46 0.00 0.77 1.00 0.65 1.25 1.27 1.36 0.81 1.56 0.37 0.98 1.17 0.68 0.88 1.23
0.95 0.70 0.76 0.89 1.48 0.77 0.00 1.41 1.08 1.61 1.65 0.89 0.76 1.32 0.64 0.70 0.59 1.07 0.49 0.91
0.79 0.55 1.18 0.91 0.95 1.00 1.41 0.00 1.05 0.73 1.08 0.70 1.49 1.17 1.00 1.34 1.45 0.49 1.06 1.30
0.89 0.68 0.21 0.79 1.44 0.65 1.08 1.05 0.00 0.96 1.09 0.94 0.44 1.06 0.90 1.47 1.20 0.79 1.04 1.39
1.34 1.45 1.18 1.47 1.08 1.25 1.61 0.73 0.96 0.00 0.96 1.10 1.05 0.48 1.36 1.26 1.38 1.38 1.03 1.38
0.80 1.31 1.35 1.04 0.30 1.27 1.65 1.08 1.09 0.96 0.00 1.08 1.09 0.79 1.40 1.03 1.02 0.78 1.79 0.86
0.64 0.37 0.82 1.51 1.29 1.36 0.89 0.70 0.94 1.10 1.08 0.00 1.42 1.20 1.61 1.30 0.86 0.68 1.04 0.83
1.42 1.24 0.68 0.71 1.28 0.81 0.76 1.49 0.44 1.05 1.09 1.42 0.00 0.99 0.84 1.20 1.21 1.22 0.97 1.58
1.37 1.69 0.85 1.63 0.65 1.56 1.32 1.17 1.06 0.48 0.79 1.20 0.99 0.00 1.13 0.61 1.00 1.60 0.81 0.83
0.86 0.91 0.75 0.34 1.00 0.37 0.64 1.00 0.90 1.36 1.40 1.61 0.84 1.13 0.00 0.68 0.89 0.86 0.65 1.04
1.20 1.28 1.01 1.05 0.80 0.98 0.70 1.34 1.47 1.26 1.03 1.30 1.20 0.61 0.68 0.00 0.92 1.47 0.47 0.42
0.49 1.04 0.93 1.24 0.80 1.17 0.59 1.45 1.20 1.38 1.02 0.86 1.21 1.00 0.89 0.92 0.00 0.90 1.18 0.49
0.19 0.29 1.03 0.56 0.92 0.68 1.07 0.49 0.79 1.38 0.78 0.68 1.22 1.60 0.86 1.47 0.90 0.00 1.56 1.21
1.48 0.94 0.60 1.09 1.48 0.88 0.49 1.06 1.04 1.03 1.79 1.04 0.97 0.81 0.65 0.47 1.18 1.56 0.00 0.84
0.86 1.05 0.91 1.25 0.65 1.23 0.91 1.30 1.39 1.38 0.86 0.83 1.58 0.83 1.04 0.42 0.49 1.21 0.84 0.00
        """.trimIndent().lines().toMutableList()

        val numElements = inputString.removeFirst().toInt()
        val inputArray = parseMatrix(numElements, inputString)


        val result = cluster.hierarchicalClustering(numElements, inputArray)

        val expectedResult = """
            1 18
            3 9
            4 6
            5 11
            15 4 6
            2 1 18
            16 20
            10 14
            7 19
            13 3 9
            12 2 1 18
            8 12 2 1 18
            17 16 20
            7 19 17 16 20
            15 4 6 13 3 9
            5 11 10 14
            8 12 2 1 18 15 4 6 13 3 9
            7 19 17 16 20 8 12 2 1 18 15 4 6 13 3 9
            5 11 10 14 7 19 17 16 20 8 12 2 1 18 15 4 6 13 3 9
        """.trimIndent()

        //printResult(result)
        checkResult(expectedResult, result, zeroBasedIndex=true)

    }

    @Test
    @DisplayName("Clusters Soft K Means Rosalind Quiz test")
    fun clustersHierarchicalRosalindQuizTest() {

        val inputString = """
20
0.00 0.99 0.54 0.64 0.65 0.69 0.83 0.95 1.70 1.06 0.71 0.99 0.52 0.79 0.78 0.97 1.31 1.29 0.77 0.87
0.99 0.00 1.18 0.99 0.49 1.11 1.31 1.57 1.30 1.51 0.50 0.81 0.89 0.83 1.19 0.80 0.80 1.30 0.79 0.56
0.54 1.18 0.00 0.48 1.19 0.23 0.38 0.80 1.31 1.35 1.36 0.96 1.07 0.99 0.66 0.88 1.57 0.93 1.26 1.34
0.64 0.99 0.48 0.00 0.68 0.53 1.16 0.82 1.11 0.91 1.42 0.41 0.79 1.34 0.93 1.04 1.19 1.21 0.89 0.83
0.65 0.49 1.19 0.68 0.00 1.44 1.34 1.13 1.39 0.65 0.56 0.43 0.42 0.94 1.42 0.92 0.61 1.21 0.46 0.26
0.69 1.11 0.23 0.53 1.44 0.00 0.76 1.07 1.11 1.59 1.48 1.17 1.20 1.36 0.37 1.12 1.66 0.86 1.46 1.50
0.83 1.31 0.38 1.16 1.34 0.76 0.00 0.92 1.12 1.11 0.92 1.34 1.30 0.80 0.90 0.63 1.07 0.60 1.42 1.25
0.95 1.57 0.80 0.82 1.13 1.07 0.92 0.00 0.78 0.58 1.27 0.61 0.65 0.70 0.91 0.71 1.18 1.32 0.71 1.31
1.70 1.30 1.31 1.11 1.39 1.11 1.12 0.78 0.00 0.68 1.23 0.72 1.03 1.42 1.15 0.88 0.76 0.77 1.54 0.99
1.06 1.51 1.35 0.91 0.65 1.59 1.11 0.58 0.68 0.00 1.01 0.59 0.63 1.14 1.46 1.05 0.55 0.89 0.82 0.59
0.71 0.50 1.36 1.42 0.56 1.48 0.92 1.27 1.23 1.01 0.00 1.05 0.64 0.64 1.17 0.46 0.47 1.16 0.84 0.53
0.99 0.81 0.96 0.41 0.43 1.17 1.34 0.61 0.72 0.59 1.05 0.00 0.34 1.04 1.46 0.83 0.91 1.39 0.82 0.44
0.52 0.89 1.07 0.79 0.42 1.20 1.30 0.65 1.03 0.63 0.64 0.34 0.00 0.85 1.24 0.90 1.07 1.42 0.78 0.52
0.79 0.83 0.99 1.34 0.94 1.36 0.80 0.70 1.42 1.14 0.64 1.04 0.85 0.00 1.30 0.78 1.17 1.66 0.52 0.96
0.78 1.19 0.66 0.93 1.42 0.37 0.90 0.91 1.15 1.46 1.17 1.46 1.24 1.30 0.00 0.80 1.24 0.77 1.05 1.79
0.97 0.80 0.88 1.04 0.92 1.12 0.63 0.71 0.88 1.05 0.46 0.83 0.90 0.78 0.80 0.00 0.52 1.07 0.95 1.02
1.31 0.80 1.57 1.19 0.61 1.66 1.07 1.18 0.76 0.55 0.47 0.91 1.07 1.17 1.24 0.52 0.00 0.74 0.84 0.57
1.29 1.30 0.93 1.21 1.21 0.86 0.60 1.32 0.77 0.89 1.16 1.39 1.42 1.66 0.77 1.07 0.74 0.00 1.53 1.23
0.77 0.79 1.26 0.89 0.46 1.46 1.42 0.71 1.54 0.82 0.84 0.82 0.78 0.52 1.05 0.95 0.84 1.53 0.00 0.92
0.87 0.56 1.34 0.83 0.26 1.50 1.25 1.31 0.99 0.59 0.53 0.44 0.52 0.96 1.79 1.02 0.57 1.23 0.92 0.00
        """.trimIndent().lines().toMutableList()

        val numElements = inputString.removeFirst().toInt()
        val inputArray = parseMatrix(numElements, inputString)


        val result = cluster.hierarchicalClustering(numElements, inputArray)

        val expectedResult = """
            3 6
            5 20
            12 13
            5 20 12 13
            11 16
            17 11 16
            4 3 6
            14 19
            8 10
            7 18
            1 4 3 6
            15 1 4 3 6
            2 5 20 12 13
            9 8 10
            17 11 16 2 5 20 12 13
            14 19 17 11 16 2 5 20 12 13
            7 18 15 1 4 3 6
            9 8 10 14 19 17 11 16 2 5 20 12 13
            7 18 15 1 4 3 6 9 8 10 14 19 17 11 16 2 5 20 12 13
        """.trimIndent()

        //printResult(result)
        checkResult(expectedResult, result, zeroBasedIndex=true)

    }

    @Test
    @DisplayName("Clusters Soft K Means Stepik Quiz test")
    fun clustersHierarchicalStepikQuizTest() {

        val inputString = """
20
0.00 1.28 1.24 1.27 0.87 1.14 0.81 0.81 1.43 1.03 0.41 1.31 1.01 0.67 0.64 0.77 0.57 0.83 1.43 1.06
1.28 0.00 1.16 0.57 1.31 0.69 1.16 1.07 0.87 0.28 0.56 0.95 0.66 1.13 1.02 1.27 1.53 1.34 1.31 0.92
1.24 1.16 0.00 1.08 0.88 0.23 0.88 1.23 0.81 1.58 1.41 1.67 0.67 0.74 0.72 0.82 1.26 0.73 1.01 0.75
1.27 0.57 1.08 0.00 1.73 0.88 1.85 1.48 0.73 0.34 0.90 0.98 1.02 1.31 1.31 1.01 1.27 1.53 0.75 1.03
0.87 1.31 0.88 1.73 0.00 0.93 0.16 0.69 1.08 1.43 1.10 1.14 0.70 1.22 0.58 1.03 0.84 0.39 1.44 0.78
1.14 0.69 0.23 0.88 0.93 0.00 1.01 1.48 1.05 1.13 0.95 1.85 0.41 0.96 0.76 0.87 1.51 0.63 1.32 0.56
0.81 1.16 0.88 1.85 0.16 1.01 0.00 0.44 1.06 1.50 1.03 1.00 0.70 0.83 0.43 1.13 0.97 0.56 1.37 0.92
0.81 1.07 1.23 1.48 0.69 1.48 0.44 0.00 0.89 1.09 0.94 0.58 1.37 0.95 0.83 0.77 0.61 1.33 1.14 1.41
1.43 0.87 0.81 0.73 1.08 1.05 1.06 0.89 0.00 0.87 1.09 0.70 1.20 1.15 0.65 1.45 0.76 1.28 1.05 1.52
1.03 0.28 1.58 0.34 1.43 1.13 1.50 1.09 0.87 0.00 0.47 0.74 1.01 1.40 1.29 1.17 1.02 1.56 1.28 1.06
0.41 0.56 1.41 0.90 1.10 0.95 1.03 0.94 1.09 0.47 0.00 1.18 1.06 1.06 0.60 1.01 0.85 0.92 1.59 1.35
1.31 0.95 1.67 0.98 1.14 1.85 1.00 0.58 0.70 0.74 1.18 0.00 1.43 1.12 1.28 1.39 0.81 1.48 0.62 1.40
1.01 0.66 0.67 1.02 0.70 0.41 0.70 1.37 1.20 1.01 1.06 1.43 0.00 0.80 0.77 1.36 1.59 0.74 1.37 0.20
0.67 1.13 0.74 1.31 1.22 0.96 0.83 0.95 1.15 1.40 1.06 1.12 0.80 0.00 0.80 1.25 1.02 1.03 0.99 0.90
0.64 1.02 0.72 1.31 0.58 0.76 0.43 0.83 0.65 1.29 0.60 1.28 0.77 0.80 0.00 1.26 0.95 0.55 1.42 1.30
0.77 1.27 0.82 1.01 1.03 0.87 1.13 0.77 1.45 1.17 1.01 1.39 1.36 1.25 1.26 0.00 0.91 1.04 0.85 1.03
0.57 1.53 1.26 1.27 0.84 1.51 0.97 0.61 0.76 1.02 0.85 0.81 1.59 1.02 0.95 0.91 0.00 1.10 1.29 1.45
0.83 1.34 0.73 1.53 0.39 0.63 0.56 1.33 1.28 1.56 0.92 1.48 0.74 1.03 0.55 1.04 1.10 0.00 1.18 0.85
1.43 1.31 1.01 0.75 1.44 1.32 1.37 1.14 1.05 1.28 1.59 0.62 1.37 0.99 1.42 0.85 1.29 1.18 0.00 1.15
1.06 0.92 0.75 1.03 0.78 0.56 0.92 1.41 1.52 1.06 1.35 1.40 0.20 0.90 1.30 1.03 1.45 0.85 1.15 0.00
        """.trimIndent().lines().toMutableList()

        val numElements = inputString.removeFirst().toInt()
        val inputArray = parseMatrix(numElements, inputString)


        val result = cluster.hierarchicalClustering(numElements, inputArray)

        val expectedResult = """
            5 7
            13 20
            3 6
            2 10
            1 11
            4 2 10
            18 5 7
            15 18 5 7
            8 12
            13 20 3 6
            17 1 11
            9 8 12
            15 18 5 7 13 20 3 6
            16 19
            14 15 18 5 7 13 20 3 6
            4 2 10 9 8 12
            17 1 11 4 2 10 9 8 12
            16 19 17 1 11 4 2 10 9 8 12
            14 15 18 5 7 13 20 3 6 16 19 17 1 11 4 2 10 9 8 12
        """.trimIndent()

        //printResult(result)
        checkResult(expectedResult, result, zeroBasedIndex=true)

    }



    private fun parseMatrix(numElements: Int, matrixStrings: MutableList<String>): MutableList<MutableList<Double>> {

        val matrix: MutableList<MutableList<Double>> = mutableListOf()

        for (row in 0 until numElements) {
            val rowValues = matrixStrings[row].split(" ").map { it.toDouble()}.toMutableList()
            matrix.add(rowValues)
        }

        return matrix
    }

    private fun printResult(result: List<List<Int>>) {
        for (r in result) {
            for (i in 0 until r.size) {
                print(r[i]+1)
                if (i == r.size - 1) {
                    print("\n")
                } else {
                    print(" ")
                }
            }
        }
    }

    private fun checkResult(str: String, result: List<List<Int>>, zeroBasedIndex:Boolean = true) {
        val lines = str.lines()
        val expectedResultList: MutableList<List<Int>> = mutableListOf()
        val elements =  lines.map { it.split(" ") }
        for (line in 0 until elements.size) {
            for (i in 0 until elements[line].size) {
                var resultElement = result[line][i]
                if (zeroBasedIndex) {
                    resultElement++
                }
                val expectedElement = elements[line][i].toInt()

                assertEquals(resultElement, expectedElement)
            }
        }
    }


}