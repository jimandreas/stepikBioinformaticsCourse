@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.NearestNeighborsOfTree
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
 * Stepik: https://stepik.org/lesson/240343/step/6?unit=212689
 * Rosalind: no equivalent problem
 *
 * "Evolutionary Tree Reconstruction in the Modern Era"
 * Youtube: https://www.youtube.com/watch?v=A48MBlIyl5c
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */
internal class S07c10p06NearestNeighborsOfTreeTest {

    lateinit var nn: NearestNeighborsOfTree

    @BeforeEach
    fun setUp() {
        nn = NearestNeighborsOfTree()
    }

    @AfterEach
    fun tearDown() {
    }


    @Test
    @DisplayName("Nearest Neighbor Of Tree sample test")
    fun nearestNeighborOfTreeSampleTest() {
        val sampleInput = """
5 4
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

        val expectedOutputString = """
1->4
0->5
3->4
2->5
5->2
5->4
5->0
4->1
4->5
4->3

1->5
0->4
3->4
2->5
5->2
5->4
5->1
4->0
4->5
4->3
        """.trimIndent().lines().toMutableList()



    }


}