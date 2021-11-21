@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "UnnecessaryVariable"
)

import algorithms.SmallParsimonyUnrootedTree
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

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
        val expectedOutput = loader.getResourceAsStrings("SmallParsimonyUnrootedTreeExtraDatasetSolution.txt")

        val expectedList = expectedOutput.toMutableList()
        val expectedHammingDistance = expectedList[0].toInt()
        expectedList.removeFirst()

        spurt.parseInputStrings(sampleInput.toMutableList())

        val numNodes = spurt.leafHashMap.keys.size
        val numEdges = spurt.edgeMap.keys.size
        println("num Nodes = $numNodes num Edges = $numEdges")



        val result = spurt.buildChangeList()
        println(spurt.totalHammingDistance)
        println(result.joinToString("\n"))

    }

    @Test
    @DisplayName("Small Parsimony Unrooted Tree Rosalind Quiz Dataset test")
    fun smallParsimonyUnrootedTreeRosalindQuizDatasetTest() {

        val loader = Foo()
        val sampleInput = loader.getResourceAsStrings("SmallParsimonyUnrootedTreeRosalindStepikQuiz2Dataset.txt")
//        val expectedList = expectedOutput.toMutableList()
//        val expectedHammingDistance = expectedList[0].toInt()
//        expectedList.removeFirst()

        spurt.parseInputStrings(sampleInput.toMutableList())

        val numNodes = spurt.leafHashMap.keys.size
        val numEdges = spurt.edgeMap.keys.size
        println("num Nodes = $numNodes num Edges = $numEdges")

        val result = spurt.buildChangeList()
        println(spurt.totalHammingDistance)
        println(result.joinToString("\n"))

    }


    private fun parseExpectedResult(e: MutableList<String>) {

        val nodeNumList : MutableList<Int> = mutableListOf()

        // https://stackoverflow.com/a/45380326
        // val reversed = map.entries.associateBy({ it.value }) { it.key }

        // build map of String to Int dna to node num
        val m : HashMap<String, Int> = hashMapOf()
        for (k in spurt.leafHashMap.keys) {
            val dnaL = spurt.leafHashMap[k]!!.left!!.dnaString!!
            val dnaR = spurt.leafHashMap[k]!!.right!!.dnaString!!
            m[dnaL] = spurt.leafHashMap[k]!!.id
            m[dnaR] = spurt.leafHashMap[k]!!.id
        }

        for (i in 0 until e.size) {
            val d = e[i].split("->")[0]

//            if (m[d] != null) {
//                println("String $d maps to node ${m[d]}")
//            }
        }

    }


    class Foo {
        fun getResourceAsStrings(path: String): List<String> {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText().lines()
            return retStr
        }
    }

}