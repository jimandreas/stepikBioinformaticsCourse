@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty"
)

import algorithms.SmallParsimonyNearestNeighborInterchange
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

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
internal class S07C10P08SmallParsimonyNearestNeighborInterchangeTest {

    lateinit var nni: SmallParsimonyNearestNeighborInterchange

    @BeforeEach
    fun setUp() {
        nni = SmallParsimonyNearestNeighborInterchange()
    }

    @AfterEach
    fun tearDown() {
    }


    @Test
    @DisplayName("Nearest Neighbor Interchange sample test")
    fun nearestNeighborOfTreeSampleTest() {
        val sampleInput = """
5
GCAGGGTA->5
TTTACGCG->5
CGACCTGA->6
GATTCCAC->6
5->TTTACGCG
5->GCAGGGTA
5->7
TCCGTAGT->7
7->5
7->6
7->TCCGTAGT
6->GATTCCAC
6->CGACCTGA
6->7
        """.trimIndent().lines().toMutableList()

        nni.parseInputStringsUnrooted(sampleInput)

        val result = nni.voteOnDnaStringsAndBuildChangeList()

        val t = nni.printTree()
        println(t.sorted().joinToString("\n"))


        val expectedOutputString = """
22
TCCGTAGT->TCAGCGGA:4
GATTCCAC->GAACCCGA:4
CGACCTGA->GAACCCGA:3
TTTACGCG->TCAGCGGA:5
TCAGCGGA->TTTACGCG:5
TCAGCGGA->GCAGCGGA:1
TCAGCGGA->TCCGTAGT:4
GCAGGGTA->GCAGCGGA:2
GCAGCGGA->GAACCCGA:3
GCAGCGGA->GCAGGGTA:2
GCAGCGGA->TCAGCGGA:1
GAACCCGA->GATTCCAC:4
GAACCCGA->CGACCTGA:3
GAACCCGA->GCAGCGGA:3

21
TCCGTAGT->TCTGCGGA:4
GATTCCAC->GCTGCGGA:5
CGACCTGA->GCAGCGGA:4
TTTACGCG->TCTGCGGA:4
TCTGCGGA->TTTACGCG:4
TCTGCGGA->GCTGCGGA:1
TCTGCGGA->TCCGTAGT:4
GCAGGGTA->GCAGCGGA:2
GCTGCGGA->GCAGCGGA:1
GCTGCGGA->GATTCCAC:5
GCTGCGGA->TCTGCGGA:1
GCAGCGGA->CGACCTGA:4
GCAGCGGA->GCAGGGTA:2
GCAGCGGA->GCTGCGGA:1
        """.trimIndent().lines().toMutableList()



    }

}