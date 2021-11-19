@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.SmallParsimonyUnrootedTree
import algorithms.SmallParsimonyUnrootedTree.NodeType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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
        """.trimIndent()


        val input = sampleInput.reader().readLines().toMutableList()

        val expectedHammingDistance = 17

        spurt.parseInputStrings(sampleInput.lines().toMutableList())

        val result = spurt.buildChangeList()
        println(spurt.totalHammingDistance)
        println(result.joinToString("\n"))

    }

    @Test
    @DisplayName("Small Parsimony Unrooted Tree debug test")
    fun smallParsimonyUnrootedTreeDebugTest() {
        val sampleInput = """
8
AA->8
8->AA
AC->8
8->AC
AG->9
9->AG
AT->9
9->AT
CA->10
10->CA
CC->10
10->CC
TT->11
11->TT
GG->11
11->GG
8->9
9->8
9->10
10->9
10->11
11->10
        """.trimIndent()


        val input = sampleInput.reader().readLines().toMutableList()

        val expectedHammingDistance = 17

        spurt.parseInputStrings(sampleInput.lines().toMutableList())

        val result = spurt.buildChangeList()
        println(spurt.totalHammingDistance)
        println(result.joinToString("\n"))
    }

    /**
     * walk the node map and print the node connections
     * or the node to dna string
     *   this is valid for the initial map.
     */
    fun printMap() {
        val m = spurt.nodeMapParsed
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
}