@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.AlignmentGlobal

/**
Global alignment reflexive test

This tests the idea that the result of an alignment is independent of
direction.

This work was performed in preparation for the Find Middle Edge - as
part of that algorithm works in reverse (from sink to middle) in forming
the solution path.

 */

internal class S05C10C03AlignmentGlobalReflexiveTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /*
    * the sample problem
    * @link: http://rosalind.info/problems/ba5e/
    * Note that the answer differs from that given in the problem statement.
    *
    * The answer is also different from -MEA--N-LY given in the LinearSpaceAlignment
    * at https://stepik.org/lesson/240308/step/14?unit=212654
    */
    @Test
    @DisplayName("global alignment test sample")
    fun globalAlignmentTestSample() {

        val sRow = "PLEASANTLY"
        val tCol = "MEANLY"

        val ga = AlignmentGlobal(0, 0, 5, useBLOSUM62 = true)
        val result = ga.globalAlignment(sRow, tCol)

        val scoreResult = result.first
        val sRowResult = result.second
        val tColResult = result.third

        val expectedScoreResult = 8
        assertEquals(expectedScoreResult, scoreResult)
        val expectedtColResult = "-ME--AN-LY"
        assertEquals(expectedtColResult, tColResult)
    }

    /*
    * try the "MEASNLY" problem in forwards and reverse directions
    */
    @Test
    @DisplayName("Test for future Space-Efficient Sequence Alignment sample")
    fun globalSpaceEfficientBackTestSample() {

        val sRow = "PLEASANTLY"
        val tCol = "MEASNLY"

        /**
         * PLEASANTLY
         * -MEAS-N-LY
         */

        val ga = AlignmentGlobal(0, 0, 5, useBLOSUM62 = true)

        val result = ga.globalAlignment(sRow, tCol)
        val scoreResult = result.first
        val sRowResult = result.second
        val tColResult = result.third

        val resultR = ga.globalAlignment(sRow.reversed(), tCol.reversed())
        val scoreResultR = resultR.first
        val sRowResultR = resultR.second.reversed()
        val tColResultR = resultR.third.reversed()

        println(sRowResult)
        println(sRowResultR)
        println(tColResult)
        println(tColResultR)

    }

    /*
    * try random strings both forward and backwards
    */
    @Test
    @DisplayName("Test reflexive properly forwards and backwards")
    fun globalAlignmentReflexiveForwardsBackwardsTest() {

        var rowEquivalence = 0
        var colEquivalence = 0
        var scoreEquivalence = 0

        val tries = 100000

        for (i in 0 until tries) {
            val sRow = getRandomAminoString()
            val tCol = getRandomAminoString()

            val ga = AlignmentGlobal(0, 0, 5, useBLOSUM62 = true)

            val result = ga.globalAlignment(sRow, tCol)
            val scoreResult = result.first
            val sRowResult = result.second
            val tColResult = result.third

            val resultR = ga.globalAlignment(sRow.reversed(), tCol.reversed())
            val scoreResultR = resultR.first
            val sRowResultR = resultR.second.reversed()
            val tColResultR = resultR.third.reversed()

//            println(scoreResult)
//            println(scoreResultR)
//            println(sRowResult)
//            println(sRowResultR)
//            println(tColResult)
//            println(tColResultR)

            if (scoreResult == scoreResultR) {
                scoreEquivalence++
            }
            if (sRowResult == sRowResultR) {
                rowEquivalence++
            }
            if (tColResult == tColResultR) {
                colEquivalence++
            }
        }

        println("score $scoreEquivalence row $rowEquivalence col $colEquivalence equivalence in $tries tries")
    }

    /**
     * return a random length (1..99) string of random Amino letters
     */
    private fun getRandomAminoString(): String {

        // this is our menu
        val aminos =
            listOf('A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y')

        val str = StringBuilder()
        val len = (1..99).random()
        for (l in 0 until len) {
            str.append(aminos[(0..aminos.size-1).random()])
        }
        return str.toString()
    }

}