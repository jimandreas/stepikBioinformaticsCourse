@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty", "SameParameterValue"
)

import algorithms.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * See also:
 * Stepik: no equivalent problem
 * Rosalind: https://rosalind.info/problems/ba8d/
 *
 * Youtube:
 * Soft k-Means Clustering 8/9
 * https://www.youtube.com/watch?v=fpM0iZTjLhM
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
    fun clustersHierarchicalSampleTest() {

        val inputString = "ATAGA ATC GAT"


    }

}