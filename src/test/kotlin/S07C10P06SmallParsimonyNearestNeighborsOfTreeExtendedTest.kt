@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty"
)

import algorithms.SmallParsimonyNearestNeighborsOfTree
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

/**
 *
 * See also:
 * Rosalind: no equivalent problem
 *
 * "Evolutionary Tree Reconstruction in the Modern Era"
 * Youtube: https://www.youtube.com/watch?v=A48MBlIyl5c
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */
internal class S07C10P06SmallParsimonyNearestNeighborsOfTreeExtendedTest {

    lateinit var nn: SmallParsimonyNearestNeighborsOfTree

    @BeforeEach
    fun setUp() {
        nn = SmallParsimonyNearestNeighborsOfTree()
    }

    /**
     * a practice test where one internal node (7) only has one leaf
     */
    @Test
    @DisplayName("Nearest Neighbor Of Tree four test")
    fun nearestNeighborOfTreePracticeFourTest() {
        val sampleInput = """
4 5
0->4
4->0
1->4
4->1
2->5
5->2
3->5
5->3
4->5
5->4
        """.trimIndent().lines().toMutableList()

        val edge = sampleInput.removeFirst().split(" ")
        val a = edge[0].toInt()
        val b = edge[1].toInt()
        val m : MutableMap<Int, MutableList<Int>> = hashMapOf()
        for (s in sampleInput) {
            val conn = s.split("->")
            val from = conn[0].toInt()
            val to = conn[1].toInt()
            if (m.containsKey(from)) {
                m[from]!!.add(to)
            } else {
                m[from] = mutableListOf(to)
            }
        }

        val result = nn.fourNearestNeighbors(a, b, m)

        val prettyPrint = nn.prettyPrintFourNearestNeighbors(a, b, m, result)
        //println(prettyPrint.joinToString("\n"))

        val expectedResults = """
            0: 4 4 4 5 5 
            1: 4 5 5 4 4 
            2: 5 4 5 4 5 
            3: 5 5 4 5 4 
            4: 0,1,5 0,2,5 0,3,5 1,2,5 1,3,5 
            5: 2,3,4 3,1,4 2,1,4 3,0,4 2,0,4 
        """.trimIndent()

        assertContentEquals(expectedResults.lines(), prettyPrint)

    }




}