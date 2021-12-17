@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.DistancesBetweenLeaves
import algorithms.Phylogeny
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 *
 * See also:
 * stepik: https://stepik.org/lesson/240337/step/11?unit=212683
 * rosalind: N/A (not applicable)
 */

/**

Since the distance matrix for SARS-like coronaviruses is non-additive,
we will cheat a bit and slightly modify it to make it additive
so that you can apply AdditivePhylogeny to it (see the figure below).

Exercise Break: Construct the simple tree fitting this distance matrix.

 TODO: reformat the graph so that it can be displayed using a phylogeny tool...

 */

val coronavirus_distance_matrix_additive = """
		Cow		Pig		Horse	Mouse	Dog		Cat		Turkey	Civet	Human
Cow		0		295		306     497		1081	1091	1003	956		954
Pig		295		0		309		500		1084	1094	1006	959		957
Horse	306		309     0		489		1073	1083	995		948		946
Mouse	497		500		489		0		1092	1102	1014	967		965
Dog		1081	1084	1073	1092	0		818		1056	1053	1051
Cat		1091	1094	1083	1102	818		0		1066	1063	1061
Turkey	1003	1006	995		1014	1056	1066	0		975		973
Civet	956		959		948		967		1053	1063	975		0		16
Human	954		957		946		965		1051	1061	973		16		0
""".trimIndent()

internal class S07c04p11PhylogenyAdditiveCritterTest {

    lateinit var ll: Phylogeny
    lateinit var dbl: DistancesBetweenLeaves


    @BeforeEach
    fun setUp() {
        ll = Phylogeny()
        dbl = DistancesBetweenLeaves()
    }

    @Test
    @DisplayName("Distances Between Leaves Corona virus distance test")
    fun phylogenyLimbLengthCoronaVirusDistanceTest() {
        // https://bioinformaticsalgorithms.com/data/realdatasets/Evolution/coronavirus_distance_matrix_additive.txt
        val sampleInput = """
9
0 295 306 497 1081 1091 1003 956 954
295 0 309 500 1084 1094 1006 959 957
306 309 0 489 1073 1083 995 948 946
497 500 489 0 1092 1102 1014 967 965
1081 1084 1073 1092 0 818 1056 1053 1051
1091 1094 1083 1102 818 0 1066 1063 1061
1003 1006 995 1014 1056 1066 0 975 973
956 959 948 967 1053 1063 975 0 16
954 957 946 965 1051 1061 973 16 0

        """.trimIndent()

        val expectedOutputString = """
0->9:146
1->9:149
2->10:149
3->11:254
4->12:404
5->12:414
6->13:489
7->15:9
8->15:7
9->0:146
9->1:149
9->10:11
10->2:149
10->9:11
10->11:86
11->3:254
11->10:86
11->14:249
12->4:404
12->5:414
12->13:163
13->6:489
13->12:163
13->14:22
14->11:249
14->13:22
14->15:455
15->7:9
15->8:7
15->14:455
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = ll.additivePhylogenyStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)

        // CLOSE THE LOOP: convert the edge list back to a distance matrix - i.e.
        //   the original test input
        val resultMatrix = dbl.distancesBetweenLeaves(matrixSize, result)
        assertEquals(m, resultMatrix)

    }

    private fun printGraph(g: Map<Int, Map<Int, Int>>) {
        for (e in g) {
            val key = e.key
            val theMap = e.value

            for (c in theMap) {
                print("$key->")
                println("${c.key}:${c.value}")
            }
        }
    }



    /**
     * compare two maps of structure:
     *  MutableMap<Int, Map<Int, Int>> = mutableMapOf()
     */
    private fun checkEdgesAreEqual(a: Map<Int, Map<Int, Int>>, b: Map<Int, Map<Int, Int>>) {

        // test 1 - number of keys match
        val aSorted = a.toSortedMap()
        val bSorted = b.toSortedMap()

        assertEquals(aSorted.keys.size, bSorted.keys.size)

        // test 2 - the keys belong to equivalent sets
        for (baseNodeMapA in aSorted) {
            kotlin.test.assertTrue(bSorted.containsKey(baseNodeMapA.key), "Failed base Node equivalence $baseNodeMapA.key")
        }

        // test 3 - for each key, the maps are equivalent sets
        for (ele in aSorted) {
            // test 1A - number of keys match
            val mapA = aSorted[ele.key]
            val mapB = bSorted[ele.key]
            assertEquals(mapA!!.keys.size, mapB!!.keys.size, "Failed equal key size")

            // test 2A - the keys belong to equivalent sets
            for (ele2 in mapA) {
                kotlin.test.assertTrue(mapB.containsKey(ele2.key), "Failed key set equivalence for next node")
            }

            // test 3A - for each key, the maps are equivalent sets
            for (ele2 in mapA) {
                val distanceA = mapA[ele2.key]
                val distanceB = mapB[ele2.key]
                assertEquals(distanceA, distanceB, "Failed distance equivalence")
            }
        }
    }


    /**
     * convert a string of the form (with tab or space separated numbers)
    0     13    21    22
    13     0    12    13
    21    12     0    13
    22    13    13     0

    into a D2Array given
    @param matrixSize - the size N of an NxN matrix
     */

    private fun parseMatrixInput(matrixSize: Int, lines: List<String>, radixIn: Int = 10): D2Array<Int> {
        val theMatrix = mk.d2array(matrixSize, matrixSize) { 0 }

        for (i in 0 until matrixSize) {
            val l = lines[i].trim().split("\t", " ")
            for (j in 0 until matrixSize) {
                theMatrix[i, j] = l[j].toInt(radix = radixIn)
            }
        }
        return theMatrix
    }

    private fun parseSampleInput(nodeToNodePlusDistance: List<String>): MutableMap<Int, MutableMap<Int, Int>> {
        val edgeMap: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()
        for (e in nodeToNodePlusDistance) {
            val sourceDest = e.split("->")
            val destNodeAndWeightPair = sourceDest[1].split(":")
            val sourceNodeNumber = sourceDest[0].toInt()
            if (edgeMap.containsKey(sourceNodeNumber)) {
                edgeMap[sourceNodeNumber]!![destNodeAndWeightPair[0].toInt()] = destNodeAndWeightPair[1].toInt()
            } else {
                edgeMap[sourceNodeNumber] = mutableMapOf(Pair(destNodeAndWeightPair[0].toInt(), destNodeAndWeightPair[1].toInt()))
            }
        }
        return edgeMap
    }

    /**
     * pretty print a 2D matrix
     */
    private fun printMatrix(matrixSize: Int, gArr: D2Array<Int>) {
        val outStr = StringBuilder()
        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                val numVal = String.format("%5d", gArr[i, j])
                outStr.append(numVal)
                if (j < matrixSize - 1) {
                    outStr.append(" ")
                }
            }
            outStr.append("\n")
        }
        println(outStr.toString())
    }
}