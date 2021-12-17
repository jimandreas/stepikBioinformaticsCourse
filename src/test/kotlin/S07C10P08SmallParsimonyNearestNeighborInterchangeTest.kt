@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty"
)

import algorithms.SmallParsimonyNearestNeighborInterchange
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

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


    /*
     *  The nearest neighbor solution to this 4-leaf problem
     *     is to swap a C and G leaf.   This gives a tree
     *     with a hamming distance of 1 (C to G from node 4 to 5)
     */

    @Test
    @DisplayName("Nearest Neighbor Interchange very simple test")
    fun nearestNeighborOfTreeVerySimpleTest() {
        val sampleInput = """
4
4->C
4->G
5->C
5->G
5->4
4->5
        """.trimIndent().lines().toMutableList()

        nni.parseInputStringsUnrooted(sampleInput)

        nni.nearestNeighborExchangeHeuristic()

        // solution should only take one iteration of nearest neighbor interchange
        assertEquals(1, nni.resultHammingDistance.size)
        // winning hamming distance is one
        assertEquals(1, nni.resultHammingDistance[0])

        // unpack the winning edge list (internals)
        val resultWinningList = nni.resultEdgeList[0]
        val resultStringList = prettyPrintEdgeList(resultWinningList)

        println(resultStringList.joinToString("\n"))

        // result should exchange node 4 "G" with node 5 "C"
        val expectedResult = """
0->4
1->5
2->4
3->5
4->0 2 5
5->3 1 4
        """.trimIndent().lines()

        assertContentEquals(expectedResult, resultStringList)

    }

    private fun prettyPrintEdgeList(result: Map<Int, MutableList<Int>>): List<String> {

        val outputList :MutableList<String> = mutableListOf()
        for (k in result.keys.sorted()) {
            val str = StringBuilder()
            str.append("$k->")
            str.append(result[k]!!.joinToString(" "))
            outputList.add(str.toString())
        }
        return outputList
    }


    @Test
    @DisplayName("Nearest Neighbor Interchange simple test")
    fun nearestNeighborOfTreeSimpleTest() {
        val sampleInput = """
8
8->C
C->8
8->C
C->8
9->G
G->9
9->G
G->9
10->G
G->10
10->G
G->10
11->C
C->11
11->C
C->11
12->8
8->12
12->9
9->12
13->10
10->13
13->11
11->13
12->13
13->12
        """.trimIndent().lines().toMutableList()

        nni.parseInputStringsUnrooted(sampleInput)

        nni.nearestNeighborExchangeHeuristic()

        // solution should only take one iteration of nearest neighbor interchange
        assertEquals(1, nni.resultHammingDistance.size)
        // winning hamming distance
        assertEquals(1, nni.resultHammingDistance[0])

        // unpack the winning edge list (internals)
        val resultWinningList = nni.resultEdgeList[0]
        val resultStringList = prettyPrintEdgeList(resultWinningList)

        //println(resultStringList.joinToString("\n"))

        // result should exchange node 4 "G" with node 5 "C"
        val expectedResult = """
0->8
1->8
2->9
3->9
4->10
5->10
6->11
7->11
8->0 1 12
9->2 3 13
10->4 5 13
11->6 7 12
12->8 11 13
13->10 9 12
        """.trimIndent().lines()

        assertContentEquals(expectedResult, resultStringList)


    }

    /*
5
5->GCAGGGTA
5->TTTACGCG
6->CGACCTGA
6->GATTCCAC
7->TCCGTAGT
5->7
7->5
7->6
6->7

winner hd = 21
5
7->GCAGGGTA
5->TTTACGCG
7->CGACCTGA
6->GATTCCAC
5->TCCGTAGT
5->6
6->5
7->6
6->7
     */

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

        nni.nearestNeighborExchangeHeuristic()

        // solution should take two iterations of nearest neighbor interchange
        assertEquals(2, nni.resultHammingDistance.size)
        //  winning hamming distance
        assertEquals(21, nni.resultHammingDistance[1])

        // unpack the winning edge list (internals)
        val resultWinningList = nni.resultEdgeList[1]
        val resultStringList = prettyPrintEdgeList(resultWinningList)

        // println(resultStringList.joinToString("\n"))

        val expectedWinningEdgeList = """
            0->5
            1->7
            2->5
            3->6
            4->7
            5->0 2 6
            6->3 5 7
            7->1 4 6
        """.trimIndent().lines().sorted()

        assertEquals(expectedWinningEdgeList, resultStringList.sorted())

//        val expectedOutputString = """
//22
//TCCGTAGT->TCAGCGGA:4
//GATTCCAC->GAACCCGA:4
//CGACCTGA->GAACCCGA:3
//TTTACGCG->TCAGCGGA:5
//TCAGCGGA->TTTACGCG:5
//TCAGCGGA->GCAGCGGA:1
//TCAGCGGA->TCCGTAGT:4
//GCAGGGTA->GCAGCGGA:2
//GCAGCGGA->GAACCCGA:3
//GCAGCGGA->GCAGGGTA:2
//GCAGCGGA->TCAGCGGA:1
//GAACCCGA->GATTCCAC:4
//GAACCCGA->CGACCTGA:3
//GAACCCGA->GCAGCGGA:3
//
//21
//TCCGTAGT->TCTGCGGA:4
//GATTCCAC->GCTGCGGA:5
//CGACCTGA->GCAGCGGA:4
//TTTACGCG->TCTGCGGA:4
//TCTGCGGA->TTTACGCG:4
//TCTGCGGA->GCTGCGGA:1
//TCTGCGGA->TCCGTAGT:4
//GCAGGGTA->GCAGCGGA:2
//GCTGCGGA->GCAGCGGA:1
//GCTGCGGA->GATTCCAC:5
//GCTGCGGA->TCTGCGGA:1
//GCAGCGGA->CGACCTGA:4
//GCAGCGGA->GCAGGGTA:2
//GCAGCGGA->GCTGCGGA:1
//        """.trimIndent().lines().toMutableList()
//
//        val expectedExchanges: MutableList<String> = mutableListOf()
//        var loop = 0
//        do {
//            val expectedHamming = expectedOutputString.removeFirst().toInt()
//            while (expectedOutputString.first() != "") {
//                expectedExchanges.add(expectedOutputString.removeFirst())
//            }
//            val resultList = nni.resultDnaTransformList[0].map { it.toString() }
//            val resultListSorted = resultList.sorted()
//            val expectedSorted = expectedExchanges.sorted()
//            println(expectedSorted.joinToString("\n"))
//            println("")
//            println(resultListSorted.joinToString("\n"))
//            assertEquals(expectedSorted, resultListSorted)
//            loop++
//        } while (expectedOutputString.isNotEmpty())



    }

    @Test
    @DisplayName("Nearest Neighbor Interchange extra dataset test")
    fun nearestNeighborOfTreeExtraDatasetTest() {
        val sampleInput = """
8
AGCTCAGCGCCCCGGAGCACCCTCCTGAAGTATGCACATT->11
CCGCCGTACACACAGTCTTGAACCATTTACCGCAGTTTCC->10
GTCCAAGAGTATGTGAAACCTGCAGTGACGAAGGCGAGAT->8
ACTGAGGTACGGGTTATACCGCGCATCTGCGAGTAAAACA->11
10->CCGCCGTACACACAGTCTTGAACCATTTACCGCAGTTTCC
10->CGGTCCTCTAGGAGCTTGTCTTTATCTGCCGCCGACATGC
10->12
ATTATGGGGCACTGAGCATACGCAAACGACTATGCTTTCC->9
9->ATTATGGGGCACTGAGCATACGCAAACGACTATGCTTTCC
9->TGCGGCGGGCGCGCCCAAACAGCGTGACCAAGTCGATGCA
9->13
8->GTCCAAGAGTATGTGAAACCTGCAGTGACGAAGGCGAGAT
8->CCACACGTGGCTGTTATATGATATTAATATATTTAATCTT
8->13
11->AGCTCAGCGCCCCGGAGCACCCTCCTGAAGTATGCACATT
11->ACTGAGGTACGGGTTATACCGCGCATCTGCGAGTAAAACA
11->12
CCACACGTGGCTGTTATATGATATTAATATATTTAATCTT->8
13->8
13->9
13->12
TGCGGCGGGCGCGCCCAAACAGCGTGACCAAGTCGATGCA->9
12->11
12->10
12->13
CGGTCCTCTAGGAGCTTGTCTTTATCTGCCGCCGACATGC->10
        """.trimIndent().lines().toMutableList()

        nni.parseInputStringsUnrooted(sampleInput)

        nni.nearestNeighborExchangeHeuristic()

        // solution should take two iterations of nearest neighbor interchange
        assertEquals(3, nni.resultHammingDistance.size)
        //  winning hamming distance
        assertEquals(156, nni.resultHammingDistance[2])

        // unpack the winning edge list (internals)
        val resultWinningList = nni.resultEdgeList[2]
        val resultStringList = prettyPrintEdgeList(resultWinningList)

        //println(resultStringList.joinToString("\n"))

        val expectedWinningEdgeList = """
            0->5
            1->7
            2->5
            3->6
            4->7
            5->0 2 6
            6->3 5 7
            7->1 4 6
        """.trimIndent().lines().sorted()

        //assertEquals(expectedWinningEdgeList, resultStringList.sorted())



    }

    @Test
    @DisplayName("Nearest Neighbor Interchange quiz test")
    fun nearestNeighborOfTreeQuizTest() {
        val sampleInput = """
8
AGCCTGCCTTAACCCCAAGCCAATTGTCCTCGGGCCCGTT->8
8->AGCCTGCCTTAACCCCAAGCCAATTGTCCTCGGGCCCGTT
8->GAAAGATGCGCAACGTTGATGAAATTCGCCTGGACCCCAG
8->13
GAAAGATGCGCAACGTTGATGAAATTCGCCTGGACCCCAG->8
GGAGATACCAATATTTCCAAGATATCTGTCCCGGAGAAGT->9
9->GGAGATACCAATATTTCCAAGATATCTGTCCCGGAGAAGT
9->AGTTATGGTCGAATTACCCCACCACGGCCCATTCACTGAT
9->13
AGTTATGGTCGAATTACCCCACCACGGCCCATTCACTGAT->9
ATTTCATATCGAGTCGCTGACTCGAAGGCAGGTTATACGT->10
10->ATTTCATATCGAGTCGCTGACTCGAAGGCAGGTTATACGT
10->GTGGGTGTCTTCTGGATCCCCGATTTCATACGAGTAAGCT
10->12
GTGGGTGTCTTCTGGATCCCCGATTTCATACGAGTAAGCT->10
GCTGGCGCATATAACCGCATGGCAGACCTTCGTGCCGGCT->11
11->GCTGGCGCATATAACCGCATGGCAGACCTTCGTGCCGGCT
11->CTGTAAGCCTATTAGCGGTAAAAGTCTATTTCCGGCCATG
11->12
CTGTAAGCCTATTAGCGGTAAAAGTCTATTTCCGGCCATG->11
12->11
12->10
12->13
13->8
13->9
13->12
        """.trimIndent().lines().toMutableList()

        nni.parseInputStringsUnrooted(sampleInput)

        nni.nearestNeighborExchangeHeuristic()

        // accepted solution was 3 iterations of nearest neighbor interchange
        assertEquals(3, nni.resultHammingDistance.size)
        //  winning hamming distance was 154
        assertEquals(154, nni.resultHammingDistance[2])

        // unpack the winning edge list (internals)
        val resultWinningList = nni.resultEdgeList[2]
        val resultStringList = prettyPrintEdgeList(resultWinningList)

        //println(resultStringList.joinToString("\n"))

//        val expectedWinningEdgeList = """
//            0->5
//            1->7
//            2->5
//            3->6
//            4->7
//            5->0 2 6
//            6->3 5 7
//            7->1 4 6
//        """.trimIndent().lines().sorted()

        //assertEquals(expectedWinningEdgeList, resultStringList.sorted())

//        println()
//        for (i in 0 until nni.resultHammingDistance.size) {
//            println("${nni.resultHammingDistance[i]}")
//            val dnaTransformList = nni.resultDnaTransformList[i].map { it.toString() }
//            println(dnaTransformList.joinToString("\n"))
//            println()
//        }

    }

}