@file:Suppress("UNUSED_VARIABLE", "UnnecessaryVariable")

import org.junit.jupiter.api.*

import algorithms.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
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
        val foo: MutableMap<Int, MutableList<Int>> = mutableMapOf()
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

    /**
     * touch test the compareTwoMaps utility
     */
    @Test
    @DisplayName("test compareTwoMaps extension function")
    fun testCompareTwoMaps() {

        val m1 = mapOf(
            Pair(10, listOf(1, 2)),
            Pair(1, listOf(0))
        )
        // wrong number of keys
        val f1 = mapOf(
            Pair(10, listOf(1, 2))
        )
        assertFalse(m1.compareTwoMaps(f1))

        // wrong key set
        val f2 = mapOf(
            Pair(11, listOf(1, 2)),
            Pair(1, listOf(0))
        )
        assertFalse(m1.compareTwoMaps(f2))

        // should pass (keys reordered)
        val p1 = mapOf(
            Pair(1, listOf(0)),
            Pair(10, listOf(1, 2))
        )
        assertTrue(m1.compareTwoMaps(p1))

        // fail - list doesn't match
        val f3 = mapOf(
            Pair(10, listOf(1, 3)),
            Pair(1, listOf(0))
        )
        assertFalse(m1.compareTwoMaps(f3))
    }

}