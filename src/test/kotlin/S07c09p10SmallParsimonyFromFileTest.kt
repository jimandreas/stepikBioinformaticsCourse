@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "UnnecessaryVariable"
)

import algorithms.SmallParsimony
import algorithms.SmallParsimony.NodeType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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

internal class S07c09p10SmallParsimonyFromFileTest {

    lateinit var sp: SmallParsimony

    @BeforeEach
    fun setUp() {
        sp = SmallParsimony()
    }

    @AfterEach
    fun tearDown() {
    }


    @Test
    @DisplayName("Small Parsimony From File test")
    fun smallParsimonyFromFileTest() {

        val loader = Foo()
//        val sampleInput = loader.getResourceAsStrings("SmallParsimonyExtraDatasetInput.txt")
//        val sampleInput = loader.getResourceAsStrings("SmallParsimonyExtraShortDNA.txt")
        val sampleInput = loader.getResourceAsStrings("SmallParsimonyStepikQuiz.txt")

        val expectedOutputString = """

        """.trimIndent()

        sp.parseInputStrings(sampleInput.toMutableList())
        sp.doScoring()
        //printMap()

        val changeList = sp.buildChangeList()
        println(sp.totalHammingDistance)
        println(changeList.joinToString("\n"))
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


    class Foo {
        fun getResourceAsStrings(path: String): List<String> {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText().lines()
            return retStr
        }
    }

}