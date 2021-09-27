@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.DistancesBetweenLeaves
import algorithms.Phylogeny
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

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
    lateinit var dbl: DistancesBetweenLeaves


    @BeforeEach
    fun setUp() {
        ll = Phylogeny()
        dbl = DistancesBetweenLeaves()
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
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = ll.additivePhylogenyStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)

        // CLOSE THE LOOP: convert the edge list back to a distance matrix - i.e.
        //   the original test input
        val resultMatrix = dbl.distancesBetweenLeaves(matrixSize, result)
        assertEquals(m, resultMatrix)

    }

    private fun printGraph(g: Map<Int, Map<Int, Int>>) {
        for (e in g) {
            val key = e.key
            val theMap = e.value

            for (c in theMap) {
                print("$key->")
                println("${c.key}:${c.value}")
            }
        }
    }

    @Test
    @DisplayName("Distances Between Leaves hexadecimal test 01")
    fun phylogenyLimbLengthHexTest01() {
        val sampleInput = """
            6
            00 f0 c8 84 e2 c1
            f0 00 38 74 12 31
            c8 38 00 4c 2a 09
            84 74 4c 00 66 45
            e2 12 2a 66 00 23
            c1 31 09 45 23 00
        """.trimIndent()

        // update when finished debugging
        val expectedOutputString = """
            0->7:128
            1->8:16
            2->6:8
            3->7:4
            4->8:2
            5->6:1
            6->2:8
            6->5:1
            6->7:64
            6->8:32
            7->0:128
            7->3:4
            7->6:64
            8->1:16
            8->4:2
            8->6:32
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input, radixIn = 16)
        //printMatrix(6, m)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = ll.additivePhylogenyStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)

        // CLOSE THE LOOP: convert the edge list back to a distance matrix - i.e.
        //   the original test input
        val resultMatrix = dbl.distancesBetweenLeaves(matrixSize, result)
        assertEquals(m, resultMatrix)

    }


    @Test
    @DisplayName("Distances Between Leaves contrived test")
    fun phylogenyLimbLengthContrivedTest01() {

        // this is similar to the previous matrix,
        //    but nodes 1 and 2 (2 and 3 in the diagrams)
        //    have been swapped
        val contrivedSampleInput = """
            4
            0->4:11
            1->5:6
            2->4:2
            3->5:7
            4->0:11
            4->2:2
            4->5:4
            5->4:4
            5->3:7
            5->1:6
        """.trimIndent()

        val r = contrivedSampleInput.reader().readLines().toMutableList()
        val matrixSize = r[0].toInt()
        r.removeAt(0)
        val hackedEdges = parseSampleInput(r)
        // now convert the edges to a distance matrix
        val theInputMatrix = dbl.distancesBetweenLeaves(matrixSize, hackedEdges)
        //printMatrix(matrixSize, theInputMatrix)

        // now hand the distance matrix to the additivePhylogeny algo
        val treeMapResult = ll.additivePhylogenyStart(matrixSize, theInputMatrix)

        checkEdgesAreEqual(hackedEdges, treeMapResult)

        val theResultMatrix = dbl.distancesBetweenLeaves(matrixSize, treeMapResult)
        //printMatrix(matrixSize, theResultMatrix)
        assertEquals(theInputMatrix, theResultMatrix)
    }

    /**
     * A contrived test where there is a new node to the "left of" and to the "right of"
     * the first internal node (4)
     */
    @Test
    @DisplayName("Distances Between Leaves contrived test02")
    fun phylogenyLimbLengthContrivedTest02() {

        // this is similar to the previous matrix,
        //    but nodes 1 and 2 (2 and 3 in the diagrams)
        //    have been swapped
        val contrivedSampleInput = """
            5
            0->5:1
            5->0:1
            1->7:2
            7->1:2
            2->6:3
            6->2:3
            3->6:4
            6->3:4
            4->7:7
            7->4:7
            5->6:6
            6->5:6
            5->7:5
            7->5:5
        """.trimIndent()

        val r = contrivedSampleInput.reader().readLines().toMutableList()
        val matrixSize = r[0].toInt()
        r.removeAt(0)
        val hackedEdges = parseSampleInput(r)
        // now convert the edges to a distance matrix
        val theInputMatrix = dbl.distancesBetweenLeaves(matrixSize, hackedEdges)
        //printMatrix(matrixSize, theInputMatrix)

        // now hand the distance matrix to the additivePhylogeny algo
        val treeMapResult = ll.additivePhylogenyStart(matrixSize, theInputMatrix)

        checkEdgesAreEqual(hackedEdges, treeMapResult)

        val theResultMatrix = dbl.distancesBetweenLeaves(matrixSize, treeMapResult)
        //printMatrix(matrixSize, theResultMatrix)
        assertEquals(theInputMatrix, theResultMatrix)
    }


    /**
     * compare two maps of structure:
     *  MutableMap<Int, Map<Int, Int>> = mutableMapOf()
     */
    private fun checkEdgesAreEqual(a: Map<Int, Map<Int, Int>>, b: Map<Int, Map<Int, Int>>) {

        // test 1 - number of keys match
        val aSorted = a.toSortedMap()
        val bSorted = b.toSortedMap()

        assertEquals(aSorted.keys.size, bSorted.keys.size)

        // test 2 - the keys belong to equivalent sets
        for (baseNodeMapA in aSorted) {
            kotlin.test.assertTrue(bSorted.containsKey(baseNodeMapA.key), "Failed base Node equivalence $baseNodeMapA.key")
        }

        // test 3 - for each key, the maps are equivalent sets
        for (ele in aSorted) {
            // test 1A - number of keys match
            val mapA = aSorted[ele.key]
            val mapB = bSorted[ele.key]
            assertEquals(mapA!!.keys.size, mapB!!.keys.size, "Failed equal key size")

            // test 2A - the keys belong to equivalent sets
            for (ele2 in mapA) {
                kotlin.test.assertTrue(mapB.containsKey(ele2.key), "Failed key set equivalence for next node")
            }

            // test 3A - for each key, the maps are equivalent sets
            for (ele2 in mapA) {
                val distanceA = mapA[ele2.key]
                val distanceB = mapB[ele2.key]
                assertEquals(distanceA, distanceB, "Failed distance equivalence")
            }
        }
    }


    /**
     * convert a string of the form (with tab or space separated numbers)
    0     13    21    22
    13     0    12    13
    21    12     0    13
    22    13    13     0

    into a D2Array given
    @param matrixSize - the size N of an NxN matrix
     */

    private fun parseMatrixInput(matrixSize: Int, lines: List<String>, radixIn: Int = 10): D2Array<Int> {
        val theMatrix = mk.d2array(matrixSize, matrixSize) { 0 }

        for (i in 0 until matrixSize) {
            val l = lines[i].trim().split("\t", " ")
            for (j in 0 until matrixSize) {
                theMatrix[i, j] = l[j].toInt(radix = radixIn)
            }
        }
        return theMatrix
    }

    private fun parseSampleInput(nodeToNodePlusDistance: List<String>): MutableMap<Int, MutableMap<Int, Int>> {
        val edgeMap: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()
        for (e in nodeToNodePlusDistance) {
            val sourceDest = e.split("->")
            val destNodeAndWeightPair = sourceDest[1].split(":")
            val sourceNodeNumber = sourceDest[0].toInt()
            if (edgeMap.containsKey(sourceNodeNumber)) {
                edgeMap[sourceNodeNumber]!![destNodeAndWeightPair[0].toInt()] = destNodeAndWeightPair[1].toInt()
            } else {
                edgeMap[sourceNodeNumber] = mutableMapOf(Pair(destNodeAndWeightPair[0].toInt(), destNodeAndWeightPair[1].toInt()))
            }
        }
        return edgeMap
    }

    /**
     * pretty print a 2D matrix
     */
    private fun printMatrix(matrixSize: Int, gArr: D2Array<Int>) {
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
    @DisplayName("Distances Between Leaves extra dataset")
    fun phylogenyLimbLengthExtraDataset() {
        val sampleInput = """
29
0 3036 4777 1541 2766 6656 2401 4119 7488 4929 5344 3516 1485 6392 2066 3216 7008 7206 1187 6491 3379 6262 6153 4927 6670 4997 9010 5793 9032
3036 0 6323 3087 4312 8202 1619 5665 9034 2205 6890 966 3031 7938 3612 492 4284 8752 2023 8037 4925 3538 3429 6473 3946 2273 10556 7339 10578
4777 6323 0 4054 2571 3455 5688 1972 4287 8216 2143 6803 4126 3191 3183 6503 10295 4005 4474 3290 2964 9549 9440 1726 9957 8284 5809 2592 5831
1541 3087 4054 0 2043 5933 2452 3396 6765 4980 4621 3567 890 5669 1343 3267 7059 6483 1238 5768 2656 6313 6204 4204 6721 5048 8287 5070 8309
2766 4312 2571 2043 0 4450 3677 1913 5282 6205 3138 4792 2115 4186 1172 4492 8284 5000 2463 4285 1173 7538 7429 2721 7946 6273 6804 3587 6826
6656 8202 3455 5933 4450 0 7567 3851 1082 10095 1872 8682 6005 1992 5062 8382 12174 800 6353 1047 4843 11428 11319 3053 11836 10163 2604 2545 2626
2401 1619 5688 2452 3677 7567 0 5030 8399 3512 6255 2099 2396 7303 2977 1799 5591 8117 1388 7402 4290 4845 4736 5838 5253 3580 9921 6704 9943
4119 5665 1972 3396 1913 3851 5030 0 4683 7558 2539 6145 3468 3587 2525 5845 9637 4401 3816 3686 2306 8891 8782 2122 9299 7626 6205 2988 6227
7488 9034 4287 6765 5282 1082 8399 4683 0 10927 2704 9514 6837 2824 5894 9214 13006 1172 7185 1879 5675 12260 12151 3885 12668 10995 2144 3377 2166
4929 2205 8216 4980 6205 10095 3512 7558 10927 0 8783 2859 4924 9831 5505 1891 3719 10645 3916 9930 6818 2973 2864 8366 3381 1708 12449 9232 12471
5344 6890 2143 4621 3138 1872 6255 2539 2704 8783 0 7370 4693 1608 3750 7070 10862 2422 5041 1707 3531 10116 10007 1741 10524 8851 4226 1233 4248
3516 966 6803 3567 4792 8682 2099 6145 9514 2859 7370 0 3511 8418 4092 1146 4938 9232 2503 8517 5405 4192 4083 6953 4600 2927 11036 7819 11058
1485 3031 4126 890 2115 6005 2396 3468 6837 4924 4693 3511 0 5741 1415 3211 7003 6555 1182 5840 2728 6257 6148 4276 6665 4992 8359 5142 8381
6392 7938 3191 5669 4186 1992 7303 3587 2824 9831 1608 8418 5741 0 4798 8118 11910 2542 6089 1827 4579 11164 11055 2789 11572 9899 4346 2281 4368
2066 3612 3183 1343 1172 5062 2977 2525 5894 5505 3750 4092 1415 4798 0 3792 7584 5612 1763 4897 1785 6838 6729 3333 7246 5573 7416 4199 7438
3216 492 6503 3267 4492 8382 1799 5845 9214 1891 7070 1146 3211 8118 3792 0 3970 8932 2203 8217 5105 3224 3115 6653 3632 1959 10736 7519 10758
7008 4284 10295 7059 8284 12174 5591 9637 13006 3719 10862 4938 7003 11910 7584 3970 0 12724 5995 12009 8897 1442 2699 10445 1088 3447 14528 11311 14550
7206 8752 4005 6483 5000 800 8117 4401 1172 10645 2422 9232 6555 2542 5612 8932 12724 0 6903 1597 5393 11978 11869 3603 12386 10713 2694 3095 2716
1187 2023 4474 1238 2463 6353 1388 3816 7185 3916 5041 2503 1182 6089 1763 2203 5995 6903 0 6188 3076 5249 5140 4624 5657 3984 8707 5490 8729
6491 8037 3290 5768 4285 1047 7402 3686 1879 9930 1707 8517 5840 1827 4897 8217 12009 1597 6188 0 4678 11263 11154 2888 11671 9998 3401 2380 3423
3379 4925 2964 2656 1173 4843 4290 2306 5675 6818 3531 5405 2728 4579 1785 5105 8897 5393 3076 4678 0 8151 8042 3114 8559 6886 7197 3980 7219
6262 3538 9549 6313 7538 11428 4845 8891 12260 2973 10116 4192 6257 11164 6838 3224 1442 11978 5249 11263 8151 0 1953 9699 1104 2701 13782 10565 13804
6153 3429 9440 6204 7429 11319 4736 8782 12151 2864 10007 4083 6148 11055 6729 3115 2699 11869 5140 11154 8042 1953 0 9590 2361 2592 13673 10456 13695
4927 6473 1726 4204 2721 3053 5838 2122 3885 8366 1741 6953 4276 2789 3333 6653 10445 3603 4624 2888 3114 9699 9590 0 10107 8434 5407 2190 5429
6670 3946 9957 6721 7946 11836 5253 9299 12668 3381 10524 4600 6665 11572 7246 3632 1088 12386 5657 11671 8559 1104 2361 10107 0 3109 14190 10973 14212
4997 2273 8284 5048 6273 10163 3580 7626 10995 1708 8851 2927 4992 9899 5573 1959 3447 10713 3984 9998 6886 2701 2592 8434 3109 0 12517 9300 12539
9010 10556 5809 8287 6804 2604 9921 6205 2144 12449 4226 11036 8359 4346 7416 10736 14528 2694 8707 3401 7197 13782 13673 5407 14190 12517 0 4899 1758
5793 7339 2592 5070 3587 2545 6704 2988 3377 9232 1233 7819 5142 2281 4199 7519 11311 3095 5490 2380 3980 10565 10456 2190 10973 9300 4899 0 4921
9032 10578 5831 8309 6826 2626 9943 6227 2166 12471 4248 11058 8381 4368 7438 10758 14550 2716 8729 3423 7219 13804 13695 5429 14212 12539 1758 4921 0 
        """.trimIndent()

        // update when finished debugging
        val expectedOutputString = """
0->55:745
1->48:156
2->52:788
3->54:409
4->53:280
5->49:125
6->51:492
7->50:657
8->31:311
9->41:820
10->47:280
11->46:723
12->45:417
13->44:864
14->43:236
15->42:89
16->33:713
17->40:445
18->39:87
19->38:441
20->37:783
21->36:348
22->35:922
23->34:662
24->33:375
25->32:718
26->29:868
27->30:841
28->29:890
29->28:890
29->31:965
29->26:868
30->27:841
30->34:687
30->47:112
31->8:311
31->40:416
31->29:965
32->41:170
32->25:718
32->35:952
33->24:375
33->16:713
33->36:381
34->52:276
34->23:662
34->30:687
35->32:952
35->22:922
35->36:683
36->33:381
36->21:348
36->35:683
37->20:783
37->53:110
37->50:866
38->19:441
38->44:522
38->49:481
39->18:87
39->51:809
39->55:355
40->17:445
40->31:416
40->49:230
41->32:170
41->42:982
41->9:820
42->41:982
42->15:89
42->48:247
43->53:656
43->54:698
43->14:236
44->38:522
44->13:864
44->47:464
45->12:417
45->54:64
45->55:323
46->11:723
46->51:884
46->48:87
47->10:280
47->30:112
47->44:464
48->42:247
48->46:87
48->1:156
49->5:125
49->38:481
49->40:230
50->52:527
50->37:866
50->7:657
51->46:884
51->6:492
51->39:809
52->2:788
52->50:527
52->34:276
53->43:656
53->37:110
53->4:280
54->3:409
54->45:64
54->43:698
55->0:745
55->39:355
55->45:323
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)
        //printit(matrixSize, m)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = ll.additivePhylogenyStart(matrixSize, m)

        //printGraph(result)

        //checkEdgesAreEqual(expectedGraph, result)

        // CLOSE THE LOOP: convert the edge list back to a distance matrix - i.e.
        //   the original test input
        val resultMatrix = dbl.distancesBetweenLeaves(matrixSize, result)
        assertEquals(m, resultMatrix)

    }


    @Test
    @DisplayName("Distances Between Leaves quiz dataset")
    fun phylogenyLimbLengthQuizDataset() {
        val sampleInput = """
25
0 1004 5374 3780 3739 6717 1607 5077 7957 3253 5959 2951 4244 3438 5355 2823 5932 2256 7239 7749 2015 5521 6327 2142 6779
1004 0 4542 4242 4201 7179 2069 5539 7125 2421 5127 3413 3412 2606 5817 3285 5100 1424 6407 6917 2477 4689 6789 1310 7241
5374 4542 0 8612 8571 11549 6439 9909 4193 3941 2195 7783 2148 2754 10187 7655 2168 4166 3475 3985 6847 1757 11159 3924 11611
3780 4242 8612 0 1959 3065 2861 1425 11195 6491 9197 2325 7482 6676 1703 2393 9170 5494 10477 10987 2747 8759 2675 5380 3127
3739 4201 8571 1959 0 4896 2820 3256 11154 6450 9156 2284 7441 6635 3534 2352 9129 5453 10436 10946 2706 8718 4506 5339 4958
6717 7179 11549 3065 4896 0 5798 3100 14132 9428 12134 5262 10419 9613 2218 5330 12107 8431 13414 13924 5684 11696 1968 8317 1842
1607 2069 6439 2861 2820 5798 0 4158 9022 4318 7024 2032 5309 4503 4436 1904 6997 3321 8304 8814 1096 6586 5408 3207 5860
5077 5539 9909 1425 3256 3100 4158 0 12492 7788 10494 3622 8779 7973 1738 3690 10467 6791 11774 12284 4044 10056 2710 6677 3162
7957 7125 4193 11195 11154 14132 9022 12492 0 6524 2168 10366 4731 5337 12770 10238 2555 6749 2272 1376 9430 3078 13742 6507 14194
3253 2421 3941 6491 6450 9428 4318 7788 6524 0 4526 5662 2811 2005 8066 5534 4499 2045 5806 6316 4726 4088 9038 1803 9490
5959 5127 2195 9197 9156 12134 7024 10494 2168 4526 0 8368 2733 3339 10772 8240 557 4751 1450 1960 7432 1080 11744 4509 12196
2951 3413 7783 2325 2284 5262 2032 3622 10366 5662 8368 0 6653 5847 3900 1564 8341 4665 9648 10158 1918 7930 4872 4551 5324
4244 3412 2148 7482 7441 10419 5309 8779 4731 2811 2733 6653 0 1624 9057 6525 2706 3036 4013 4523 5717 2295 10029 2794 10481
3438 2606 2754 6676 6635 9613 4503 7973 5337 2005 3339 5847 1624 0 8251 5719 3312 2230 4619 5129 4911 2901 9223 1988 9675
5355 5817 10187 1703 3534 2218 4436 1738 12770 8066 10772 3900 9057 8251 0 3968 10745 7069 12052 12562 4322 10334 1828 6955 2280
2823 3285 7655 2393 2352 5330 1904 3690 10238 5534 8240 1564 6525 5719 3968 0 8213 4537 9520 10030 1790 7802 4940 4423 5392
5932 5100 2168 9170 9129 12107 6997 10467 2555 4499 557 8341 2706 3312 10745 8213 0 4724 1837 2347 7405 1053 11717 4482 12169
2256 1424 4166 5494 5453 8431 3321 6791 6749 2045 4751 4665 3036 2230 7069 4537 4724 0 6031 6541 3729 4313 8041 934 8493
7239 6407 3475 10477 10436 13414 8304 11774 2272 5806 1450 9648 4013 4619 12052 9520 1837 6031 0 2064 8712 2360 13024 5789 13476
7749 6917 3985 10987 10946 13924 8814 12284 1376 6316 1960 10158 4523 5129 12562 10030 2347 6541 2064 0 9222 2870 13534 6299 13986
2015 2477 6847 2747 2706 5684 1096 4044 9430 4726 7432 1918 5717 4911 4322 1790 7405 3729 8712 9222 0 6994 5294 3615 5746
5521 4689 1757 8759 8718 11696 6586 10056 3078 4088 1080 7930 2295 2901 10334 7802 1053 4313 2360 2870 6994 0 11306 4071 11758
6327 6789 11159 2675 4506 1968 5408 2710 13742 9038 11744 4872 10029 9223 1828 4940 11717 8041 13024 13534 5294 11306 0 7927 2030
2142 1310 3924 5380 5339 8317 3207 6677 6507 1803 4509 4551 2794 1988 6955 4423 4482 934 5789 6299 3615 4071 7927 0 8379
6779 7241 11611 3127 4958 1842 5860 3162 14194 9490 12196 5324 10481 9675 2280 5392 12169 8493 13476 13986 5746 11758 2030 8379 0
        """.trimIndent()

        // update when finished debugging
        val expectedOutputString = """
0->26:271
1->25:86
2->31:805
3->28:64
4->27:959
5->47:890
6->29:344
7->30:730
8->42:792
9->32:910
10->33:85
11->34:748
12->35:509
13->36:409
14->37:428
15->38:718
16->39:265
17->40:524
18->41:777
19->42:584
20->43:491
21->44:321
22->45:789
23->46:346
24->47:952
25->1:86
25->26:647
25->40:814
26->0:271
26->25:647
26->29:992
27->4:959
27->28:936
27->34:577
28->3:64
28->27:936
28->30:631
29->6:344
29->26:992
29->43:261
30->7:730
30->28:631
30->37:580
31->2:805
31->35:834
31->44:631
32->9:910
32->36:686
32->46:547
33->10:85
33->39:207
33->41:588
34->11:748
34->27:577
34->38:98
35->12:509
35->31:834
35->36:706
36->13:409
36->32:686
36->35:706
37->14:428
37->30:580
37->45:611
38->15:718
38->34:98
38->43:581
39->16:265
39->33:207
39->44:467
40->17:524
40->25:814
40->46:64
41->18:777
41->33:588
41->42:703
42->8:792
42->19:584
42->41:703
43->20:491
43->29:261
43->38:581
44->21:321
44->31:631
44->39:467
45->22:789
45->37:611
45->47:289
46->23:346
46->32:547
46->40:64
47->5:890
47->24:952
47->45:289
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)
        //printMatrix(matrixSize, m)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = ll.additivePhylogenyStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)

        // CLOSE THE LOOP: convert the edge list back to a distance matrix - i.e.
        //   the original test input
        val resultMatrix = dbl.distancesBetweenLeaves(matrixSize, result)
        assertEquals(m, resultMatrix)

    }





}