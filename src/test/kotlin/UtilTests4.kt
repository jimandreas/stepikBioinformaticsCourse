@file:Suppress("UNUSED_VARIABLE", "UnnecessaryVariable")

import org.junit.jupiter.api.*

import algorithms.*
import kotlin.test.assertEquals

internal class UtilTests4 {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     *  touch test the deep copy of a map utility function
     */
    @Test
    @DisplayName("test Deep Copy utility")
    fun testDeepCopy() {
        val foo :MutableMap<Int, MutableList<Int>> = mutableMapOf()
        foo[1] = mutableListOf(2, 3)
        foo[10] = mutableListOf(11, 12)

        val myDeepCopy = foo.deepCopy()

        val bar = foo.toMutableMap()
        bar[1]!!.removeLast()
        bar[10]!!.removeFirst()

        assertEquals(bar[1]!!, listOf(2))
        assertEquals(foo[1]!!, listOf(2))
        assertEquals(myDeepCopy[1]!!, listOf(2, 3))
    }

}