@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.SmallParsimony
import algorithms.SmallParsimonyUnrootedTree
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 *
 * See also:
 * Stepik: https://stepik.org/lesson/240342/step/12?unit=212688
 * Rosalind: http://rosalind.info/problems/ba7g/
 * Youtube: https://www.youtube.com/watch?v=Rfa-2SvxslE
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

internal class S07c09p10SmallParsimonyUnrootedTreeTest {

    lateinit var spurt: SmallParsimonyUnrootedTree

    @BeforeEach
    fun setUp() {
        spurt = SmallParsimonyUnrootedTree()
    }

    @AfterEach
    fun tearDown() {
    }


    @Test
    @DisplayName("Small Parsimony Unrooted Tree sample test")
    fun smallParsimonyUnrootedTreeSampleTest() {
        val sampleInput = """
            4
            TCGGCCAA->4
            4->TCGGCCAA
            CCTGGCTG->4
            4->CCTGGCTG
            CACAGGAT->5
            5->CACAGGAT
            TGAGTACC->5
            5->TGAGTACC
            4->5
            5->4
        """.trimIndent().lines().toMutableList()

        val expectedResult = """
            17
            TGCGGGAT->TCTGGCAG:4
            TCTGGCAG->TGCGGGAT:4
            TCTGGCAG->TCGGCCAA:3
            TCGGCCAA->TCTGGCAG:3
            TCTGGCAG->CCTGGCTG:2
            CCTGGCTG->TCTGGCAG:2
            TGCGGGAT->CACAGGAT:3
            CACAGGAT->TGCGGGAT:3
            TGCGGGAT->TGAGTACC:5
            TGAGTACC->TGCGGGAT:5
        """.trimIndent().lines().toMutableList()

        spurt.parseInputStringsUnrooted(sampleInput)

        val result = spurt.voteOnDnaStringsAndBuildChangeList()
//        println(spurt.totalHammingDistance)
//        println(result.joinToString("\n"))

        val tree = spurt.printTree()
        println(tree.sorted().joinToString("\n"))

        val expectedHamming = expectedResult.removeFirst().toInt()
        assertEquals(expectedHamming, spurt.totalHammingDistance)

        val sortedExpected = expectedResult.sorted()
        val sortedResult = result.joinToString("\n").lines().sorted()

        assertContentEquals(sortedExpected, sortedResult)

    }


    @Test
    @DisplayName("Small Parsimony Unrooted Tree simple test")
    fun smallParsimonyUnrootedTreeSimpleTest() {
        val sampleInput = """
5
5->TAA
5->ACC
6->AAT
6->AAA
7->AAC
7->5
5->7
7->6
6->7
        """.trimIndent().lines().toMutableList()

        val expectedResult = """

        """.trimIndent().lines().toMutableList()

        spurt.parseInputStringsUnrooted(sampleInput)

        val result = spurt.voteOnDnaStringsAndBuildChangeList()
//        println(spurt.totalHammingDistance)
//        println(result.joinToString("\n"))

        val tree = spurt.printTree()
        println(tree.sorted().joinToString("\n"))

        val expectedHamming = expectedResult.removeFirst().toInt()
        assertEquals(expectedHamming, spurt.totalHammingDistance)

        val sortedExpected = expectedResult.sorted()
        val sortedResult = result.joinToString("\n").lines().sorted()

        assertContentEquals(sortedExpected, sortedResult)

    }


    /**
     * walk the node map and print the node connections
     * or the node to dna string
     *   this is valid for the initial map.
     */
//    fun printMap() {
//        val m = spurt.nodeMap
//        for (e in m.keys) {
//            val n = m[e]!!
//            print("${n.id}: ")
//            if (n.left != null) {
//                val l = n.left!!
//                print(l.id)
//            }
//            if (n.right != null) {
//                val r = n.right!!
//                print(r.id)
//            }
//        }
//        print("\n")
//    }
}
