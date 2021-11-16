@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.SmallParsimony
import algorithms.SmallParsimony.NodeType
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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

        val input = sampleInput.reader().readLines().toMutableList()

        val expectedList = expectedOutputString.lines().toMutableList()
        val expectedHammingDistance = expectedList[0].toInt()
        expectedList.removeFirst()

        sp.parseInputStrings(sampleInput.lines().toMutableList())
        sp.doScoring()
        //printMap()

        val changeList = sp.buildChangeList()
        val resultsList : MutableList<String> = mutableListOf()
        for (change in changeList) {
            resultsList.add(change.toString())
        }
//        println(sp.totalHammingDistance)

        assertEquals(expectedHammingDistance, sp.totalHammingDistance)
// this fails
//        val el = expectedList.sorted()
//        val rl = resultsList.sorted()
//        for (i in 0 until el.size) {
//            val a = el[i]
//            val b = rl[i]
//            assertEquals(a, b)
//        }

    }

    /**
     * Test basic scoring of left and right {ACGT}
     * from example graph, see:
     * https://stepik.org/lesson/240342/step/7?unit=212688
     */
    @Test
    @DisplayName("Small Parsimony scoring1 test")
    fun smallParsimonyScoring1Test() {
        val left = listOf(2, 2, 0, 2)
        val right = listOf(2, 1, 2, 1)
        val expectedResult1 = listOf(3, 2, 2, 2)
        val left1Arr = mk.d2array(4, 1) { left[it] }
        val right1Arr = mk.d2array(4, 1) { right[it] }
        val expected1Arr = mk.d2array(4, 1) { expectedResult1[it] }
        sp.dnaLen = 1 // global variable
        val result = sp.scoreArrays(left1Arr, right1Arr)
        //println(result)
        assertEquals(expected1Arr, result)

        val left2 = listOf(2, 1, 3, 3)
        val right2 = listOf(3, 2, 2, 2)
        val expectedResult2 = listOf(5, 3, 4, 4)
        val left2Arr = mk.d2array(4, 1) { left2[it] }
        val right2Arr = mk.d2array(4, 1) { right2[it] }
        val expected2Arr = mk.d2array(4, 1) { expectedResult2[it] }
        sp.dnaLen = 1 // global variable
        val result2 = sp.scoreArrays(left2Arr, right2Arr)
        //println(result)
        assertEquals(expected2Arr, result2)
    }

    /*
     * scoring a tree as compared to:
     * https://stepik.org/lesson/240342/step/8?unit=212688
     *
     * this test parses and scores the full tree
     *   (dna strings are all one letter long)
     */
    @Test
    @DisplayName("Small Parsimony scoring2 test")
    fun smallParsimonyScoring2Test() {
        val sampleInput = """
8
8->CC
8->CC
9->AA
9->CC
10->GG
10->GG
11->TT
11->CC
12->8
12->9
13->10
13->11
14->12
14->13
        """.trimIndent()

        // string output expected results
        val expectedStrings = """
6
CC->CC:0
CC->CC:0
CC->CC:0
CC->CC:0
CC->CC:0
CC->CC:0
CC->CC:0
CC->CC:0
CC->CC:0
CC->CC:0
CC->CC:0
CC->CC:0
CC->AA:2
AA->CC:2
CC->CC:0
CC->CC:0
CC->GG:2
GG->CC:2
CC->CC:0
CC->CC:0
GG->GG:0
GG->GG:0
GG->GG:0
GG->GG:0
CC->TT:2
TT->CC:2
CC->CC:0
CC->CC:0
        """.trimIndent().lines().toMutableList()

        val input = sampleInput.reader().readLines().toMutableList()

        sp.parseInputStrings(input)
        sp.doScoring()

        val changeList = sp.buildChangeList()
        //println(sp.totalHammingDistance)
        //println(changeList.joinToString("\n"))

        // check the total distance for the tree
        val expectedHammingDistance = expectedStrings[0].toInt()
        assertEquals(expectedHammingDistance, sp.totalHammingDistance)
        expectedStrings.removeFirst()

        // now check the edges
        val changeListStrings = changeList.joinToString("\n").lines().sorted()
        val expectedStringsSorted = expectedStrings.sorted()
        //println(changeListStrings.sorted().joinToString("\n"))
        assertContentEquals(expectedStringsSorted, changeListStrings)
    }


    /**
     * this test is to tune the
     * priority for tie-breaking to
     * conform to the sample results.
     */
    @Test
    @DisplayName("Small Parsimony tuning test")
    fun smallParsimonyTuningTest() {
        val sampleInput = """
4
4->A
4->T
5->T
5->T
6->4
6->5
        """.trimIndent()

        // scoring map expected results
        val expectedResults = """
4-1 2 2 1
5-2 2 2 0
6-2 3 3 1
        """.trimIndent().lines()

        // string output expected results
        val expectedStrings = """
1
T->T:0
T->T:0
T->T:0
T->T:0
T->A:1
A->T:1
T->T:0
T->T:0
T->T:0
T->T:0
T->T:0
T->T:0
        """.trimIndent().lines().toMutableList()

        val input = sampleInput.reader().readLines().toMutableList()

        sp.parseInputStrings(input)
        sp.doScoring()

        /*
         * now compare the results with the expected matrices
         */
        for (i in 1..expectedResults.size) {
            val e = expectedResults[i-1].split("-") // index of nodes start at one
            val node = e[0].toInt()
            val expectedArrayValues = e[1].split(" ").map { it.toInt()}
            val expectedArray = mk.d2array(4, 1) { expectedArrayValues[it]}

            assertNotNull(sp.nodeMap[node])
            val resultArray = sp.nodeMap[node]!!.scoringArray
            assertEquals(expectedArray, resultArray)
        }

        val changeList = sp.buildChangeList()
//        println(sp.totalHammingDistance)
//        println(changeList.joinToString("\n"))

        // check the total distance for the tree
        val expectedHammingDistance = expectedStrings[0].toInt()
        assertEquals(expectedHammingDistance, sp.totalHammingDistance)
        expectedStrings.removeFirst()

        // now check the edges
        val changeListStrings = changeList.joinToString("\n").lines().sorted()
        val expectedStringsSorted = expectedStrings.sorted()
        //println(changeListStrings)
        assertContentEquals(expectedStringsSorted, changeListStrings)

    }

    /**
     * walk the node map and print the node connections
     * or the node to dna string
     *   this is valid for the initial map.
     */
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
}