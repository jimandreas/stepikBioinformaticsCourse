@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.SmallParsimony
import algorithms.SmallParsimony.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.iterator
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.toMutableList
import kotlin.collections.toSortedMap
import kotlin.test.assertEquals

/**
 *
 * See also:
 * Stepik: https://stepik.org/lesson/240342/step/10?unit=212688
 * Rosalind: http://rosalind.info/problems/ba7f/
 * Youtube: https://www.youtube.com/watch?v=h515dSZWEyM
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

internal class S07c09p10SmallParsimonyTest {

    lateinit var sp: SmallParsimony

    @BeforeEach
    fun setUp() {
        sp = SmallParsimony()
    }

    @AfterEach
    fun tearDown() {
    }


    @Test
    @DisplayName("Small Parsimony sample test")
    fun smallParsimonySampleTest() {
        val sampleInput = """
4
4->CAAATCCC
4->ATTGCGAC
5->CTGCGCTG
5->ATGGACGA
6->4
6->5
        """.trimIndent()

        val expectedOutputString = """
16
ATTGCGAC->ATAGCCAC:2
ATAGACAA->ATAGCCAC:2
ATAGACAA->ATGGACTA:2
ATGGACGA->ATGGACTA:1
CTGCGCTG->ATGGACTA:4
ATGGACTA->CTGCGCTG:4
ATGGACTA->ATGGACGA:1
ATGGACTA->ATAGACAA:2
ATAGCCAC->CAAATCCC:5
ATAGCCAC->ATTGCGAC:2
ATAGCCAC->ATAGACAA:2
CAAATCCC->ATAGCCAC:5
        """.trimIndent()

//        val input = sampleInput.reader().readLines().toMutableList()
//        val numLeaves = input[0].trim().toInt()
//        input.removeFirst()
//        val m = parseMatrixInput(numLeaves, input)
//
//        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
//        val expectedGraph = parseSampleInput(expectedResultStrings)
//
//        val result = nj.neighborJoiningStart(numLeaves, m)
//
//        //printGraph(result)
//
//        checkEdgesAreEqual(expectedGraph, result)
//
//        val theResultMatrix = sp.distancesBetweenLeavesFloat(numLeaves, result)
//        //printMatrix(matrixSize, theResultMatrix)
//
//        // This is a NON-ADDITIVE matrix - so this test will fail.
//        //assertEquals(m, theResultMatrix)
    }

    @Test
    @DisplayName("Small Parsimony parsing test")
    fun smallParsimonyParsingTest() {
        val sampleInput = """
4
4->CAAATCCC
4->ATTGCGAC
5->CTGCGCTG
5->ATGGACGA
6->4
6->5
        """.trimIndent()

        val expectedOutputString = """
16
ATTGCGAC->ATAGCCAC:2
ATAGACAA->ATAGCCAC:2
ATAGACAA->ATGGACTA:2
ATGGACGA->ATGGACTA:1
CTGCGCTG->ATGGACTA:4
ATGGACTA->CTGCGCTG:4
ATGGACTA->ATGGACGA:1
ATGGACTA->ATAGACAA:2
ATAGCCAC->CAAATCCC:5
ATAGCCAC->ATTGCGAC:2
ATAGCCAC->ATAGACAA:2
CAAATCCC->ATAGCCAC:5
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()

        sp.parseInputStrings(input)
        printMap()
    }

    /*
     * scoring a tree as compared to:
     * https://stepik.org/lesson/240342/step/8?unit=212688
     */
    @Test
    @DisplayName("Small Parsimony scoring test")
    fun smallParsimonyScoringTest() {
        val sampleInput = """
8
1->C
1->C
2->A
2->C
3->G
3->G
4->T
4->C
5->1
5->2
6->3
6->4
7->5
7->6
        """.trimIndent()

        val expectedOutputString = """

        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()

        sp.parseInputStrings(input)
        printMap()

//        println(sp.nodeMap.toList().joinToString("\n"))
    }

    fun printMap() {
        val m = sp.nodeMap
        for (e in m.keys) {
            val n = m[e]!!
            print("${n.id}: ")
            if (n.left != null) {
                val l = n.left!!
                val r = n.right!!
                if (l.nodeType == NodeType.LEAF) {
                    print(l.dnaString)
                } else {
                    print(l.id)
                }
                print(" ")
                if (r.nodeType == NodeType.LEAF) {
                    print(r.dnaString)
                } else {
                    print(r.id)
                }
            }
            print("\n")
        }
    }


    /**
     * compare two maps of structure:
     *  MutableMap<String, Map<String, Int>> = mutableMapOf()
     */
    private fun checkEdgesAreEqual(a: Map<String, Map<String, Int>>, b: Map<String, Map<String, Int>>) {

        // test 1 - number of keys match
        val aSorted = a.toSortedMap()
        val bSorted = b.toSortedMap()

        assertEquals(aSorted.keys.size, bSorted.keys.size)

        // test 2 - the keys belong to equivalent sets
        for (baseNodeMapA in aSorted) {
            kotlin.test.assertTrue(
                bSorted.containsKey(baseNodeMapA.key),
                "Failed base Node equivalence $baseNodeMapA.key"
            )
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
                assertEquals(distanceA!!.toDouble(), distanceB!!.toDouble(), 0.01)
            }
        }
    }


    private fun parseSampleInput(nodeToNodePlusDistance: List<String>): MutableMap<Int, MutableMap<Int, Float>> {
        val edgeMap: MutableMap<Int, MutableMap<Int, Float>> = mutableMapOf()
        for (e in nodeToNodePlusDistance) {
            val sourceDest = e.split("->")
            val destNodeAndWeightPair = sourceDest[1].split(":")
            val sourceNodeNumber = sourceDest[0].toInt()
            if (edgeMap.containsKey(sourceNodeNumber)) {
                edgeMap[sourceNodeNumber]!![destNodeAndWeightPair[0].toInt()] = destNodeAndWeightPair[1].toFloat()
            } else {
                edgeMap[sourceNodeNumber] =
                    mutableMapOf(Pair(destNodeAndWeightPair[0].toInt(), destNodeAndWeightPair[1].toFloat()))
            }
        }
        return edgeMap
    }

}