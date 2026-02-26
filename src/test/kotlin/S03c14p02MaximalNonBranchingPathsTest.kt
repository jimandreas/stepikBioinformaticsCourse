@file:Suppress("UNUSED_VARIABLE", "LiftReturnOrAssignment", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

import algorithms.MaximalNonBranchingPaths
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
Code Challenge: Implement MaximalNonBranchingPaths.

Input: The adjacency list of a graph whose nodes are integers.
Output: The collection of all maximal nonbranching paths in this graph.

 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba3m/
 */
internal class S03c14p02MaximalNonBranchingPathsTest {

    val mnbp = MaximalNonBranchingPaths()

    @Test
    @DisplayName("maximal nonBranching Paths test 01")
    fun maximalNonbranchingTest01() {

        val connectionString = """
            0 -> 1
            1 -> 2
            2 -> 3,4
        """.trimIndent()

        val expectedResultString = """
            0->1->2
            2->3
            2->4
        """.trimIndent()

        val result = mnbp.maximalNonBranchingPaths(connectionString)

        checkAnswer(expectedResultString, result)
    }

    @Test
    @DisplayName("maximal nonBranching Paths test 02")
    fun maximalNonbranchingTest02() {

        val connectionString = """
            5 -> 3
            3 -> 4
            1 -> 2
            6 -> 1
            2 -> 6
        """.trimIndent()

            /* was
            5->3->4
            6->1->2->6
             */
        val expectedResultString = """
            5->3->4
            1->2->6->1
        """.trimIndent()

        val result = mnbp.maximalNonBranchingPaths(connectionString)

        checkAnswer(expectedResultString, result)
    }

    @Test
    @DisplayName("maximal nonBranching Paths test 03")
    fun maximalNonbranchingTest03() {

        val connectionString = """
            1 -> 2
            2 -> 3,4,5
            4 -> 6,10
            5 -> 7
            6 -> 10
        """.trimIndent()

        /* was

         */
        val expectedResultString = """
            1->2
            2->3
            2->4
            2->5->7
            4->6->10
            4->10
        """.trimIndent()

        val result = mnbp.maximalNonBranchingPaths(connectionString)

        checkAnswer(expectedResultString, result)
    }

    @Test
    @DisplayName("maximal nonBranching Paths test 04")
    fun maximalNonbranchingTest04() {

        val connectionString = """
            7 -> 10
            10 -> 14
            14 -> 3,5,18
            5 -> 4
            52 -> 13
            4 -> 8
            8 -> 14
            18 -> 19
            19 -> 31
            31 -> 52
        """.trimIndent()

        /* was

         */
        val expectedResultString = """
            7->10->14
            14->3
            14->5->4->8->14
            14->18->19->31->52->13
        """.trimIndent()

        val result = mnbp.maximalNonBranchingPaths(connectionString)

        checkAnswer(expectedResultString, result)
    }

    @Test
    @DisplayName("maximal nonBranching Paths test 05")
    fun maximalNonbranchingTest05() {

        val connectionString = """
            7 -> 3
            3 -> 4
            4 -> 8
            8 -> 9
            9 -> 7
            1 -> 2
            2 -> 5
            5 -> 10
            10 -> 2
            16 -> 111
            111 -> 16
        """.trimIndent()

        /* was
9->7->3->4->8->9
111->16->111
         */
        val expectedResultString = """
            1->2
            2->5->10->2
            7->3->4->8->9->7
            16->111->16
        """.trimIndent()

        val result = mnbp.maximalNonBranchingPaths(connectionString)

        checkAnswer(expectedResultString, result)
    }

    private fun checkAnswer(e: String, r: List<List<Int>>) {
        val eMap = parseExpected(e)
//        if (eMap == r) {
//            println("Success!")
//        }
    }

    private fun parseExpected(eString: String): List<List<Int>> {
        val m : MutableList<List<Int>> = mutableListOf()
        val reader = eString.reader()
        val lines = reader.readLines()
        for (line in lines) {
            var e : List<String>
            if (line.contains(" -> ")) {
                e = line.split(" -> ")
            } else {
                e = line.split("->")
            }
            val l = e.map { it.toInt() }
            m.add(l)
        }
        return m
    }
}