@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.GlobalAlignment
import util.LongestPathInDirectedGraph
import kotlin.test.assertContentEquals
import kotlin.test.assertNotNull

/**
Code Challenge: Solve the Global Alignment Problem.

Input: Two protein strings written in the single-letter amino acid alphabet.
Output: The maximum alignment score of these strings followed by an alignment
achieving this maximum score. Use the BLOSUM62 scoring matrix for matches and
mismatches as well as the indel penalty σ = 5.
 *
 * See also:
 * stepik: @link: https://stepik.org/lesson/240305/step/3?unit=212651
 * rosalind: @link: http://rosalind.info/problems/ba5e/
 * book (5.9):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

internal class S05c09c03GlobalAlignmentTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    val ga = GlobalAlignment()


    @Test
    @DisplayName("global alignment - matrix input test")
    fun globalAlignmentMatrixInputTest() {

        val test1 = ga.score('Y', 'Y')
        assertEquals(7, test1)

        val test2 = ga.score('A', 'A')
        assertEquals(4, test2)
    }



}