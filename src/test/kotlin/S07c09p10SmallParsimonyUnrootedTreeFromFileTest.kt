@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "UnnecessaryVariable"
)

import algorithms.SmallParsimony
import algorithms.SmallParsimony.NodeType
import algorithms.SmallParsimonyUnrootedTree
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

internal class S07c09p10SmallParsimonyUnrootedTreeFromFileTest {

    lateinit var spurt: SmallParsimonyUnrootedTree

    @BeforeEach
    fun setUp() {
        spurt = SmallParsimonyUnrootedTree()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Small Parsimony Unrooted Tree Extra Dataset test")
    fun smallParsimonyUnrootedTreeExtraDatasetTest() {

        val loader = Foo()
        val sampleInput = loader.getResourceAsStrings("SmallParsimonyUnrootedTreeExtraDataset.txt")
        val expectedOutput = loader.getResourceAsStrings("SmallParsimonyExtraDatasetSolution.txt")

        val expectedList = expectedOutput.toMutableList()
        val expectedHammingDistance = expectedList[0].toInt()
        expectedList.removeFirst()

        spurt.parseInputStrings(sampleInput.toMutableList())

        val numNodes = spurt.nodeMapParsed.keys.size
        val numEdges = spurt.edgeMap.keys.size
        println("num Nodes = $numNodes num Edges = $numEdges")

        spurt.findMinTree()

    }


    class Foo {
        fun getResourceAsStrings(path: String): List<String> {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText().lines()
            return retStr
        }
    }

}