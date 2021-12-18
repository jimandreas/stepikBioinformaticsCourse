@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue", "UnnecessaryVariable"
)

import algorithms.PatternMatching
import algorithms.PatternMatchingSuffixTrees
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.math.exp
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 * Construct the Suffix Tree of a String
 * Stepik: https://stepik.org/lesson/240378/step/4?unit=212724
 * Rosalind: https://rosalind.info/problems/ba9c/
 *
 * SEE ALSO for a tree example:
 * https://stepik.org/lesson/240388/step/2?unit=212734
 * also:
 * https://www.bioinformaticsalgorithms.org/faq-chapter-9
 * (see :  What are the edge labels in the suffix tree for "panamabananas$"?)
 *
 * Using the Trie 2/10
 * https://www.youtube.com/watch?v=9U0ynguwoNA
 *
 * From a Trie to a Suffix Tree (3/10)
 * https://www.youtube.com/watch?v=LB-ANFydv30
 *
 * String Compression and the Burrows-Wheeler Transform (4/10)
 * https://www.youtube.com/watch?v=G7YBi04HOEY
 *
 * Inverting Burrows-Wheeler (5/10)
 * https://www.youtube.com/watch?v=DqdjbK68l3s
 */

internal class S09C05P04SuffixTreeConstructionTest {

    lateinit var pmst: PatternMatchingSuffixTrees

    @BeforeEach
    fun setUp() {
        pmst = PatternMatchingSuffixTrees()
    }

    /**
     * Code Challenge: Solve the Suffix Tree Construction Problem.I
     *
     * Input: A string Text.
     *
     * Output: A space-separated list of the edge labels of SuffixTree(Text). You may return these strings in any order.
     */

    @Test
    @DisplayName("Pattern Matching Suffix Tree Problem")
    fun patternMatchingSuffixTreeSampleTest() {

        val inputString = "ATAAATG$"

        pmst.createSuffixTree(inputString)

        val result = pmst.printTree(pmst.root).sorted()

        val expectedResult = "AAATG$ G$ T ATG$ TG$ A A AAATG$ G$ T G$ $".split(" ").sorted()

        assertContentEquals(expectedResult, result)


    }

    @Test
    @DisplayName("Pattern Matching Suffix Tree Simple Problem")
    fun patternMatchingSuffixTreeSimpleest() {

        val inputString = "AA$"

        pmst.createSuffixTree(inputString)

        val result = pmst.printTree(pmst.root).sorted()
        println(result.joinToString(" "))

        val expectedResult = "$ $ A A$".split(" ").sorted()

        assertContentEquals(expectedResult, result)


    }

}