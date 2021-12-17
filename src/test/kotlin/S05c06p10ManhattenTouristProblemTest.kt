@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate")

import algorithms.ManhattanTouristProblem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

/**
 * Code Challenge: Find the length of a longest path in the Manhattan Tourist Problem.
 *
 * See also:
 * stepik: @link: https://stepik.org/lesson/240301/step/10?unit=212647
 * rosalind: @link: http://rosalind.info/problems/ba5b/
 * book (5.6):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

internal class S05c06p10ManhattenTouristProblemTest {

    val mt = ManhattanTouristProblem()

    val manhattenTestInputString01 = """
            4 4
            1 0 2 4 3
            4 6 5 2 1
            4 4 5 2 1
            5 6 8 5 3
            -
            3 2 4 0
            3 2 4 2
            0 7 3 3
            3 3 0 2
            1 3 2 2
       """.trimIndent()

    val manhattenTestInputString02 = """
            2 2
            1 0 1
            0 1 0
            -
            2 3
            4 5
            6 7
       """.trimIndent()

    @Test
    @DisplayName("manhatten tourist test - test parser")
    fun manhattenTouristTestParser01() {


        val result = mt.readManhattanMatrices(manhattenTestInputString01)
        assertNotNull(result)
        assertEquals(4, result.row)
        assertEquals(4, result.col)
        assertEquals(20, result.downList.size)
        assertEquals(20, result.rightList.size)

        assertEquals(3, result.downList[4*5-1])
        assertEquals(2, result.rightList[4*5-1])

    }

    @Test
    @DisplayName("manhatten tourist test 01")
    fun manhattenTouristTest01() {

        val result = mt.readManhattanMatrices(manhattenTestInputString01)
        assertNotNull(result)
        val count = mt.findLongestPathInManhatten(result)

        val expectedCount = 34
        assertEquals(expectedCount, count)

    }

    @Test
    @DisplayName("manhatten tourist test 02")
    fun manhattenTouristTest02() {

        val result = mt.readManhattanMatrices(manhattenTestInputString02)
        assertNotNull(result)
        val count = mt.findLongestPathInManhatten(result)

        val expectedCount = 14
        assertEquals(expectedCount, count)

    }



    @Test
    @DisplayName("manhatten tourist test extra 03")
    fun manhattenTouristTest03() {

        val extraDataset = """
17 9
2 3 4 0 3 1 1 1 1 1
4 2 3 4 3 3 0 4 1 1
4 4 0 1 4 3 2 0 2 2
4 3 0 3 4 4 3 2 4 4
0 1 0 1 3 0 3 0 3 4
3 2 4 4 4 3 1 0 0 0
3 4 3 1 2 3 0 0 4 0
2 4 3 4 1 2 0 3 2 0
1 4 4 1 4 4 3 1 1 4
3 1 2 2 3 3 0 4 0 0
0 2 1 4 1 3 1 3 1 0
1 0 4 0 4 3 3 2 3 1
2 0 0 4 3 4 0 3 0 0
4 1 0 4 3 2 1 1 3 1
2 4 4 3 3 4 0 0 4 3
1 0 2 3 3 0 4 0 2 0
3 1 0 3 2 3 2 2 1 4
-
1 0 4 4 3 3 1 0 4
0 2 0 3 3 0 1 2 1
3 2 3 1 1 4 2 4 4
1 3 4 4 2 1 1 1 4
1 4 2 2 3 1 3 2 3
0 3 1 0 1 0 4 1 4
1 3 4 4 1 0 3 2 1
2 3 1 2 3 2 2 2 3
3 2 1 4 0 2 4 2 4
4 0 2 0 1 3 1 4 4
1 3 0 2 2 1 0 3 2
1 4 0 4 4 1 2 4 2
0 2 4 3 4 0 3 2 2
2 3 4 4 0 4 3 0 4
1 0 4 1 3 3 1 4 2
4 3 4 3 2 3 2 2 0
0 1 2 2 4 4 2 4 2
2 3 1 4 4 3 4 0 3
        """.trimIndent()

        val result = mt.readManhattanMatrices(extraDataset)
        assertNotNull(result)
        val count = mt.findLongestPathInManhatten(result)

        val expectedCount = 84
        assertEquals(expectedCount, count)

    }

    @Test
    @DisplayName("manhatten tourist test4")
    fun manhattenTouristTest04() {

        val extraDataset = """
            5 3
            20 5 0 10
            0 5 10 0
            10 10 0 15
            0 20 20 25
            30 10 5 30
            -
            0 30 15
            10 20 10
            10 10 20
            20 25 30
            15 35 40
            15 10 25
        """.trimIndent()

        val result = mt.readManhattanMatrices(extraDataset)
        assertNotNull(result)
        val count = mt.findLongestPathInManhatten(result)

        val expectedCount = 175
        assertEquals(expectedCount, count)

    }

    @Test
    @DisplayName("manhatten tourist test5")
    fun manhattenTouristTest05() {

        val extraDataset = """
            3 5
            0 5 10 0 10 10 
            15 0 20 20 25 30 
            10 5 30 15 0 20
            -
            0 30 15 10 20 
            10 10 10 20 20 
            25 30 15 35 40
            15 10 25 15 20
        """.trimIndent()

        val result = mt.readManhattanMatrices(extraDataset)
        assertNotNull(result)
        val count = mt.findLongestPathInManhatten(result)

        val expectedCount = 180
        assertEquals(expectedCount, count)

    }


}