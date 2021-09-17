@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import algorithms.Phylogeny

/**
 *
 * See also:
 * stepik: https://stepik.org/lesson/240337/step/6?unit=212683
 * rosalind: http://rosalind.info/problems/ba7c/
 */

/**

Code Challenge: Implement AdditivePhylogeny to solve the Distance-Based Phylogeny Problem.

Input: An integer n followed by a space-separated n x n distance matrix.
Output: A weighted adjacency list for the simple tree fitting this matrix.

Note on formatting: The adjacency list must have consecutive integer node labels
starting from 0. The n leaves must be labeled 0, 1, ..., n - 1 in order
of their appearance in the distance matrix. Labels for internal nodes
may be labeled in any order but must start from n and increase consecutively.

 */

internal class S07c04p06PhylogenyAdditiveTest {

    lateinit var ll: Phylogeny


    @BeforeEach
    fun setUp() {
        ll = Phylogeny()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Distances Between Leaves sample test")
    fun phylogenyLimbLengthSampleTest() {
        val sampleInput = """
            4
            0	13	21	22
            13	0	12	13
            21	12	0	13
            22	13	13	0
        """.trimIndent()

        val expectedOutputString = """
            0->4:11
            1->4:2
            2->5:6
            3->5:7
            4->0:11
            4->1:2
            4->5:4
            5->4:4
            5->3:7
            5->2:6
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        expectedResultStrings.removeFirst()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = ll.additivePhylogenyStart(matrixSize, m)


    }

    /**
     * convert a string of the form (with tab or space separated numbers)
    0    13    21    22
    13     0    12    13
    21    12     0    13
    22    13    13     0

    into a D2Array given
    @param matrixSize - the size N of an NxN matrix
     */

    private fun parseMatrixInput(matrixSize: Int, lines: List<String>): D2Array<Int> {
        val theMatrix = mk.d2array(matrixSize, matrixSize) {0}

        for (i in 0 until matrixSize) {
            val l = lines[i].trim().split("\t", " ")
            for (j in 0 until matrixSize) {
                theMatrix[i, j] = l[j].toInt()
            }
        }
        return theMatrix
    }

    fun parseSampleInput(edges: List<String>): MutableMap<Int, MutableList<Pair<Int, Int>>> {
        val edgeMap: MutableMap<Int, MutableList<Pair<Int, Int>>> = mutableMapOf()
        for (e in edges) {
            val sourceDest = e.split("->")
            val nodeAndWeight = sourceDest[1].split(":")

            val key = sourceDest[0].toInt()
            if (edgeMap.containsKey(key)) {
                edgeMap[sourceDest[0].toInt()]!!.add(Pair(nodeAndWeight[0].toInt(), nodeAndWeight[1].toInt()))
            } else {
                edgeMap[sourceDest[0].toInt()] = mutableListOf(Pair(nodeAndWeight[0].toInt(), nodeAndWeight[1].toInt()))
            }
        }
        return edgeMap
    }

    /**
     * pretty print a 2D matrix
     */
    private fun printit(matrixSize: Int, gArr: D2Array<Int>) {
        val outStr = StringBuilder()
        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                val numVal = String.format("%5d", gArr[i, j])
                outStr.append(numVal)
                if (j < matrixSize - 1) {
                    outStr.append(" ")
                }
            }
            outStr.append("\n")
        }
        println(outStr.toString())
    }

    @Test
    @DisplayName("Distances Between Leaves extraDataset test")
    fun phylogenyLimbLengthExtraDatasetTest() {
        val sampleInput = """
25
2
0 6806 3415 3666 8467 1175 6105 4705 1537 5183 4463 2616 2156 9275 3315 7970 4217 2632 7561 8857 4047 9129 4972 3729 8378
6806 0 9639 9890 3615 7399 2689 10929 6371 11407 2849 5972 8380 4423 4905 3118 3723 8856 2709 4005 3737 4277 11196 9953 3526
3415 9639 0 1319 11300 2886 8938 2358 4370 2836 7296 5449 2541 12108 6148 10803 7050 1061 10394 11690 6880 11962 2625 1382 11211
3666 9890 1319 0 11551 3137 9189 2035 4621 2513 7547 5700 2792 12359 6399 11054 7301 1312 10645 11941 7131 12213 2302 1059 11462
8467 3615 11300 11551 0 9060 4350 12590 8032 13068 4510 7633 10041 1978 6566 2249 5384 10517 2904 1560 5398 1832 12857 11614 1207
1175 7399 2886 3137 9060 0 6698 4176 2130 4654 5056 3209 1627 9868 3908 8563 4810 2103 8154 9450 4640 9722 4443 3200 8971
6105 2689 8938 9189 4350 6698 0 10228 5670 10706 2148 5271 7679 5158 4204 3853 3022 8155 3444 4740 3036 5012 10495 9252 4261
4705 10929 2358 2035 12590 4176 10228 0 5660 2166 8586 6739 3831 13398 7438 12093 8340 2351 11684 12980 8170 13252 1955 1736 12501
1537 6371 4370 4621 8032 2130 5670 5660 0 6138 4028 2181 3111 8840 2880 7535 3782 3587 7126 8422 3612 8694 5927 4684 7943
5183 11407 2836 2513 13068 4654 10706 2166 6138 0 9064 7217 4309 13876 7916 12571 8818 2829 12162 13458 8648 13730 1033 2214 12979
4463 2849 7296 7547 4510 5056 2148 8586 4028 9064 0 3629 6037 5318 2562 4013 1380 6513 3604 4900 1394 5172 8853 7610 4421
2616 5972 5449 5700 7633 3209 5271 6739 2181 7217 3629 0 4190 8441 2481 7136 3383 4666 6727 8023 3213 8295 7006 5763 7544
2156 8380 2541 2792 10041 1627 7679 3831 3111 4309 6037 4190 0 10849 4889 9544 5791 1758 9135 10431 5621 10703 4098 2855 9952
9275 4423 12108 12359 1978 9868 5158 13398 8840 13876 5318 8441 10849 0 7374 3057 6192 11325 3712 716 6206 1332 13665 12422 2015
3315 4905 6148 6399 6566 3908 4204 7438 2880 7916 2562 2481 4889 7374 0 6069 2316 5365 5660 6956 2146 7228 7705 6462 6477
7970 3118 10803 11054 2249 8563 3853 12093 7535 12571 4013 7136 9544 3057 6069 0 4887 10020 2407 2639 4901 2911 12360 11117 2160
4217 3723 7050 7301 5384 4810 3022 8340 3782 8818 1380 3383 5791 6192 2316 4887 0 6267 4478 5774 1148 6046 8607 7364 5295
2632 8856 1061 1312 10517 2103 8155 2351 3587 2829 6513 4666 1758 11325 5365 10020 6267 0 9611 10907 6097 11179 2618 1375 10428
7561 2709 10394 10645 2904 8154 3444 11684 7126 12162 3604 6727 9135 3712 5660 2407 4478 9611 0 3294 4492 3566 11951 10708 2815
8857 4005 11690 11941 1560 9450 4740 12980 8422 13458 4900 8023 10431 716 6956 2639 5774 10907 3294 0 5788 914 13247 12004 1597
4047 3737 6880 7131 5398 4640 3036 8170 3612 8648 1394 3213 5621 6206 2146 4901 1148 6097 4492 5788 0 6060 8437 7194 5309
9129 4277 11962 12213 1832 9722 5012 13252 8694 13730 5172 8295 10703 1332 7228 2911 6046 11179 3566 914 6060 0 13519 12276 1869
4972 11196 2625 2302 12857 4443 10495 1955 5927 1033 8853 7006 4098 13665 7705 12360 8607 2618 11951 13247 8437 13519 0 2003 12768
3729 9953 1382 1059 11614 3200 9252 1736 4684 2214 7610 5763 2855 12422 6462 11117 7364 1375 10708 12004 7194 12276 2003 0 11525
8378 3526 11211 11462 1207 8971 4261 12501 7943 12979 4421 7544 9952 2015 6477 2160 5295 10428 2815 1597 5309 1869 12768 11525 0
        """.trimIndent()

        val expectedOutput = 534
        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        val whichRow = input[1].trim().toInt()
        input.removeFirst()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val result = ll.calculateLimbLength(matrixSize, whichRow, m)

        assertEquals(expectedOutput, result)

    }

    @Test
    @DisplayName("Distances Between Leaves Quiz test")
    fun phylogenyLimbLengthQuizTest() {
        val sampleInput = """
26
18
0 1074 7935 9267 9446 3878 3991 9334 4397 2462 1499 3011 2783 4125 3473 2861 3214 5915 4521 1145 1188 3479 6649 6395 4766 8234
1074 0 6997 8329 8508 3658 3771 8396 3459 2242 561 2791 1845 3905 2535 1923 2276 4977 3583 925 704 3259 5711 5457 3828 7296
7935 6997 0 2594 2773 10519 10632 2661 5286 9103 6650 9652 6170 10766 6384 5440 5991 3040 4600 7786 7565 10120 1520 2706 3453 1561
9267 8329 2594 0 1071 11851 11964 1227 6618 10435 7982 10984 7502 12098 7716 6772 7323 4372 5932 9118 8897 11452 2852 4038 4785 1429
9446 8508 2773 1071 0 12030 12143 1406 6797 10614 8161 11163 7681 12277 7895 6951 7502 4551 6111 9297 9076 11631 3031 4217 4964 1608
3878 3658 10519 11851 12030 0 1363 11918 6981 2858 4083 2091 5367 547 6057 5445 5798 8499 7105 3257 3772 2231 9233 8979 7350 10818
3991 3771 10632 11964 12143 1363 0 12031 7094 2971 4196 2204 5480 1610 6170 5558 5911 8612 7218 3370 3885 2344 9346 9092 7463 10931
9334 8396 2661 1227 1406 11918 12031 0 6685 10502 8049 11051 7569 12165 7783 6839 7390 4439 5999 9185 8964 11519 2919 4105 4852 1496
4397 3459 5286 6618 6797 6981 7094 6685 0 5565 3112 6114 2632 7228 2846 1902 2453 3266 1872 4248 4027 6582 4000 3746 2117 5585
2462 2242 9103 10435 10614 2858 2971 10502 5565 0 2667 1991 3951 3105 4641 4029 4382 7083 5689 1841 2356 2459 7817 7563 5934 9402
1499 561 6650 7982 8161 4083 4196 8049 3112 2667 0 3216 1498 4330 2188 1576 1929 4630 3236 1350 1129 3684 5364 5110 3481 6949
3011 2791 9652 10984 11163 2091 2204 11051 6114 1991 3216 0 4500 2338 5190 4578 4931 7632 6238 2390 2905 1692 8366 8112 6483 9951
2783 1845 6170 7502 7681 5367 5480 7569 2632 3951 1498 4500 0 5614 1708 1096 1449 4150 2756 2634 2413 4968 4884 4630 3001 6469
4125 3905 10766 12098 12277 547 1610 12165 7228 3105 4330 2338 5614 0 6304 5692 6045 8746 7352 3504 4019 2478 9480 9226 7597 11065
3473 2535 6384 7716 7895 6057 6170 7783 2846 4641 2188 5190 1708 6304 0 1310 1663 4364 2970 3324 3103 5658 5098 4844 3215 6683
2861 1923 5440 6772 6951 5445 5558 6839 1902 4029 1576 4578 1096 5692 1310 0 917 3420 2026 2712 2491 5046 4154 3900 2271 5739
3214 2276 5991 7323 7502 5798 5911 7390 2453 4382 1929 4931 1449 6045 1663 917 0 3971 2577 3065 2844 5399 4705 4451 2822 6290
5915 4977 3040 4372 4551 8499 8612 4439 3266 7083 4630 7632 4150 8746 4364 3420 3971 0 2580 5766 5545 8100 1754 1500 1433 3339
4521 3583 4600 5932 6111 7105 7218 5999 1872 5689 3236 6238 2756 7352 2970 2026 2577 2580 0 4372 4151 6706 3314 3060 1431 4899
1145 925 7786 9118 9297 3257 3370 9185 4248 1841 1350 2390 2634 3504 3324 2712 3065 5766 4372 0 1039 2858 6500 6246 4617 8085
1188 704 7565 8897 9076 3772 3885 8964 4027 2356 1129 2905 2413 4019 3103 2491 2844 5545 4151 1039 0 3373 6279 6025 4396 7864
3479 3259 10120 11452 11631 2231 2344 11519 6582 2459 3684 1692 4968 2478 5658 5046 5399 8100 6706 2858 3373 0 8834 8580 6951 10419
6649 5711 1520 2852 3031 9233 9346 2919 4000 7817 5364 8366 4884 9480 5098 4154 4705 1754 3314 6500 6279 8834 0 1420 2167 1819
6395 5457 2706 4038 4217 8979 9092 4105 3746 7563 5110 8112 4630 9226 4844 3900 4451 1500 3060 6246 6025 8580 1420 0 1913 3005
4766 3828 3453 4785 4964 7350 7463 4852 2117 5934 3481 6483 3001 7597 3215 2271 2822 1433 1431 4617 4396 6951 2167 1913 0 3752
8234 7296 1561 1429 1608 10818 10931 1496 5585 9402 6949 9951 6469 11065 6683 5739 6290 3339 4899 8085 7864 10419 1819 3005 3752 0
        """.trimIndent()

        val expectedOutput = 593 // accepted answer
        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        val whichRow = input[1].trim().toInt()
        input.removeFirst()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val result = ll.calculateLimbLength(matrixSize, whichRow, m)

        // println(result)
        assertEquals(expectedOutput, result)

    }



}