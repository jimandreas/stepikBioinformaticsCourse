import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import util.findClumps
import util.frequencyTable

internal class UtilTests {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName( "util: frequenceTableTest function test")
    fun testFrequencyTable() {

        val testString = "abcdabcd"  // should find two instances of abcd in the string
        val resultMap = frequencyTable(testString, 4)
        val abcd = resultMap["abcd"]
        assertNotNull(abcd)
        assertEquals(2, abcd)
    }

    @Test
    @DisplayName( "util: frequenceTableTest function test - null return")
    fun testFrequencyTableNullReturn() {

        val testString = "abc"  // no string of length 4!
        val resultMap = frequencyTable(testString, 4)
        assertEquals(0, resultMap.size)
    }

    @Test
    @DisplayName( "util: frequenceTableTest function test - overlapping")
    fun testFrequencyTableOverlappingString() {

        val testString = "ababababacd"  // no string of length 4!
        val resultMap = frequencyTable(testString, 4)
        val abcd = resultMap["baba"]
        assertNotNull(abcd)
        assertEquals(3, abcd)
    }

    @Test
    @DisplayName("util: findClumps function test - basic function")
    fun testFindClumpsBasicTest() {

        // the window size is 6 characters long - so ab ab ab should produce a map of "ab" to 3 occurrences
        // dups are discarded.

        val testString = "ababababacdedfdababababacd"
        val resultMap = findClumps(testString, 2, 6, 3)

        assertEquals(2, resultMap.size)
        val result1 = resultMap[0]
        val result2 = resultMap[1]
        assertEquals(Pair("ab", 3), result1)
        assertEquals(Pair("ba", 3), result2)
    }

}