@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue"
)

import algorithms.PatternMatching
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

/**
 * See also:
 * Stepik: https://stepik.org/lesson/240376/step/4?unit=212722
 * Rosalind: https://rosalind.info/problems/ba9a/
 *
 * Using the Trie 2/10
 * https://www.youtube.com/watch?v=9U0ynguwoNA
 *
 * From a Trie to a Suffix Tree (3/10)
 * https://www.youtube.com/watch?v=LB-ANFydv30
 */

internal class S09C03P04TrieConstructionTest {

    lateinit var pm: PatternMatching

    @BeforeEach
    fun setUp() {
        pm = PatternMatching()
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * Code Challenge: Solve the Trie Construction Problem.
     *
     * Input: A space-separated collection of strings Patterns.
     *
     * Output: The adjacency list corresponding to Trie(Patterns),
     * in the following format. If Trie(Patterns) has n nodes, first
     * label the root with 0 and then label the remaining nodes with
     * the integers 1 through n - 1 in any order you like.
     * Each edge of the adjacency list of Trie(Patterns)
     * will be encoded by a triple: the first two members of the
     * triple must be the integers labeling the initial and terminal
     * nodes of the edge, respectively; the third member of the triple
     * must be the symbol labeling the edge.
     */
    @Test
    @DisplayName("Pattern Matching Trie Construction Problem")
    fun patternMatchingTrieConstructionSampleTest() {

        val inputString = "ATAGA ATC GAT".split(" ")

        val result = pm.trieConstruction(inputString)

        val expectedResult = """
            0 1 A
            1 2 T
            2 3 A
            3 4 G
            4 5 A
            2 6 C
            0 7 G
            7 8 A
            8 9 T
        """.trimIndent().lines().toMutableList().sorted()

        val parseResult = parseResult(result)

        //println(parseResult.joinToString("\n"))

        assertContentEquals(expectedResult, parseResult)


    }

    fun parseResultRosalindFormatting(result: Map<Int, Map<Char, Int>>): List<String> {
        val outList: MutableList<String> = mutableListOf()
        val str = StringBuilder()
        for (k in result.keys.sorted()) {
            for (c in result[k]!!.keys) {
                str.append("$k->${result[k]!![c]}:$c")
                outList.add(str.toString())
                str.clear()
            }
        }
        return outList
    }

    fun parseResult(result: Map<Int, Map<Char, Int>>): List<String> {
        val outList: MutableList<String> = mutableListOf()
        val str = StringBuilder()
        for (k in result.keys.sorted()) {
            for (c in result[k]!!.keys) {
                str.append("$k ${result[k]!![c]} $c")
                outList.add(str.toString())
                str.clear()
            }
        }
        return outList
    }

}