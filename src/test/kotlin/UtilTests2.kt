import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import util.*
import java.lang.AssertionError

internal class UtilTests2 {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("util: frequenceTableTest function test")
    fun testFrequencyTable() {

        val matrix = listOf(

/* A: */ .2f, .2f, 0f, 0f,  0f,  0f, .9f, .1f, .1f, .1f, .3f,  0f,
/* C: */ .1f, .6f, 0f, 0f,  0f,  0f,  0f, .4f, .1f, .2f, .4f, .6f,
/* G: */  0f,  0f, 1f, 1f, .9f, .9f, .1f,  0f,  0f,  0f,  0f,  0f,
/* T: */ .7f, .2f, 0f, 0f, .1f, .1f,  0f, .5f, .8f, .7f, .3f, .4f

            )

        val kmer = "TCGTGGATTTCC"
        val prob = probForGivenKmer(kmer, matrix)
        println(prob)
    }

    /**
     * parseProbabilityMatrix
     * test the character matrix to float list parser
     */
    @Test
    @DisplayName("test parse matrix")
    fun testParseMatrix() {
        val matrixString = """
            0.2 0.2 0.3 0.2 0.3
            0.4 0.3 0.1 0.5 0.1
            0.3 0.3 0.5 0.2 0.4
            0.1 0.2 0.1 0.1 0.2
        """.trimIndent()

        val expectedValue =
            listOf(0.2f, 0.2f, 0.3f, 0.2f, 0.3f, 0.4f, 0.3f, 0.1f, 0.5f, 0.1f, 0.3f, 0.3f, 0.5f, 0.2f, 0.4f, 0.1f, 0.2f, 0.1f, 0.1f)
        val foo = parseProbabilityMatrix(5, matrixString)
        println(foo)
    }



}