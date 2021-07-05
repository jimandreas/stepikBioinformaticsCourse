@file:Suppress("UNUSED_VARIABLE")

import org.junit.jupiter.api.*

import util.*
import kotlin.test.assertEquals

internal class UtilTestsCyclopeptideSequencing {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }


    /**
     * Code Challenge: Implement CyclopeptideSequencing
     *
     *  Sample Input:

    0 113 128 186 241 299 314 427

    Sample Output:

    186-128-113 186-113-128 128-186-113 128-113-186 113-186-128 113-128-186
     */
    @Test
    @DisplayName("test CyclopeptideSequencing base case")
    fun testCyclopeptideSequencingBaseCase() {
        val spectrum = listOf(0, 113, 128, 186, 241, 299, 314, 427)

        val results = cyclopeptideSequencing(spectrum)
        println(results)
    }

    

}