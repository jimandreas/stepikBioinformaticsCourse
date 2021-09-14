@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "ReplaceManualRangeWithIndicesCalls")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import algorithms.AlignmentGlobal

/**
Global alignment reflexive test

This tests the idea that the result of an alignment is independent of
direction.

This work was performed in preparation for the Find Middle Edge - as
part of that algorithm works in reverse (from sink to middle) in forming
the solution path.

 */

internal class S05C10p03AlignmentGlobalReflexiveTest {

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

//       println(sRowResult)
//       println(sRowResultR)
//       println(tColResult)
//       println(tColResultR)

    }

    /*
     * try the "MEANLY" problem in forwards and reverse directions
     */
    @Test
    @DisplayName("Test for MEANLY forwards and backwards Sequence Alignment sample")
    fun globalSpaceEfficientMEANLYSample() {

        val sRow = "PLEASANTLY"
        val tCol = "MEANLY"

        /**
         * PLEASANTLY
         * -MEA--N-LY
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

//       println("$sRowResult FORWARDS")
//       println(sRowResultR)
//       println("$tColResultR BACKARDS")
//       println(tColResultR)
//       println("Score: forwards: $scoreResult backwards: $scoreResultR")

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
        var rowEquivalenceS = 0
        var colEquivalenceS = 0
        var rowPriorityAttempts = 0
        var colPriorityAttempts = 0

        val tries = 100

        for (i in 0 until tries) {
            val sRow = getRandomAminoString()
            val tCol = getRandomAminoString()

            val ga = AlignmentGlobal(0, 0, 5, useBLOSUM62 = true)

            //ga.downPriority = true
            val result = ga.globalAlignment(sRow, tCol)
            val scoreResult = result.first
            val sRowResult = result.second
            val tColResult = result.third
            //ga.downPriority = false

            //ga.downPriority = true
            val resultR = ga.globalAlignment(sRow.reversed(), tCol.reversed())
            val scoreResultR = resultR.first
            val sRowResultR = resultR.second.reversed()
            val tColResultR = resultR.third.reversed()
            //ga.downPriority = false

            /*
             * Conditional probability:
             *    if the reverse string match is not the same,
             *       THEN try switching the scoring priorities (left swapped with up)
             *
             * Yes a global variable is a hack.
             */

//    //       println(scoreResult)
//    //       println(scoreResultR)
//    //       println(sRowResult)
//    //       println(sRowResultR)
//    //       println(tColResult)
//    //       println(tColResultR)

            if (scoreResult == scoreResultR) {
                scoreEquivalence++
            }
            if (sRowResult == sRowResultR) {
                rowEquivalence++
            } else {
                val state = ga.downPriority
                ga.downPriority = true
                val resultRP = ga.globalAlignment(sRow.reversed(), tCol.reversed())
                val sRowResultRP = resultRP.second.reversed()
                val tColResultRP = resultRP.third.reversed()

                if (sRowResult == sRowResultRP) {
                    rowEquivalenceS++

//            //       println(sRowResult)
//            //       println(sRowResultR)
//            //       println("$sRowResultRP ROW MATCH PRIORITY HACKED")
                }
                rowPriorityAttempts++
                ga.downPriority = state
            }
            if (tColResult == tColResultR) {
                colEquivalence++
            } else {
                val stateC = ga.downPriority
                ga.downPriority = true
                val resultRPC = ga.globalAlignment(sRow.reversed(), tCol.reversed())
                val sRowResultRPC = resultRPC.second.reversed()
                val tColResultRPC = resultRPC.third.reversed()
                if (tColResult == tColResultRPC) {
                    colEquivalenceS++

//            //       println(tColResult)
//            //       println(tColResultR)
//            //       println("$tColResultRPC COLUMN MATCH PRIORITY HACKED")
                }
                colPriorityAttempts++
                ga.downPriority = stateC
            }
        }

//       println("score $scoreEquivalence row $rowEquivalence col $colEquivalence equivalence in $tries tries")
//       println("after priority change the following matched:  ")
//       println("row $rowEquivalenceS of attempts $rowPriorityAttempts  col $colEquivalenceS of attempts $colPriorityAttempts")
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
            str.append(aminos[(0 until aminos.size).random()])
        }
        return str.toString()
    }

}