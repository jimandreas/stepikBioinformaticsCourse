@file:Suppress("UNUSED_VARIABLE")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * stepik:   @link: https://stepik.org/lesson/240300/step/10?unit=212646
 * rosalind: @link: http://rosalind.info/problems/ba5a/
 *
Code Challenge: Solve the Change Problem.

Input: An integer money and an array Coins = (coin1, ..., coind).
Output: The minimum number of coins with denominations Coins that changes money.
 */
internal class S05c05p10DPChangeDynamicProgrammingTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("test the dynamic programming function DPChange 01")
    fun testDPChange01() {
        val coins = listOf(50, 25, 20, 10, 5, 1)
        val money = 40
        val result = dpChange(money, coins)
        val expectedResult = 2
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("test the dynamic programming function DPChange Extra 02")
    fun testDPChange02() {
        val coins = listOf(24, 13, 12, 7, 5, 3, 1)
        val money = 8074
        val result = dpChange(money, coins)
        val expectedResult = 338
        assertEquals(expectedResult, result)
    }
}