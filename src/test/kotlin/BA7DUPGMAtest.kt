@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.DistancesBetweenLeaves
import algorithms.Phylogeny
import algorithms.UPGMA
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


/**
 *
 * See also:  Ultrametric Trees - Unweighted Pair Group Method with Arithmetic Mean  UPGMA
 * Youtube: https://www.youtube.com/watch?v=27aOeJ2hSwA
 *
 * and also: Creating a Phylogenetic Tree
 * https://www.youtube.com/watch?v=09eD4A_HxVQ
 *
 * Limb Length:
 * rosalind: http://rosalind.info/problems/ba7d/
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

internal class BA7DUPGMAtest {

    lateinit var ll: Phylogeny
    lateinit var dbl: DistancesBetweenLeaves
    lateinit var upgma: UPGMA


    @BeforeEach
    fun setUp() {
        ll = Phylogeny()
        dbl = DistancesBetweenLeaves()
        upgma = UPGMA()
    }

    // example from:
    // https://www.youtube.com/watch?v=8-8eZdeqUsw
    // C100 Week 5 Discussion
    @Test
    @DisplayName("UPGMA sample from youtube test")
    fun upgmaSampleFromYoutubeTest() {
        val sampleInput = """
5
0 10 12 8 7
10 0 4 4 14
12 4 0 6 16
8 4 6 0 12
7 14 16 12 0
        """.trimIndent()

        val expectedOutputString = """
0->7:3.500
1->5:2.000
2->5:2.000
3->6:2.500
4->7:3.500
5->1:2.000
5->2:2.000
5->6:0.500
6->3:2.500
6->5:0.500
6->8:3.500
7->0:3.500
7->4:3.500
7->8:2.500
8->6:3.500
8->7:2.500
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = upgma.upgmaStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)


    }

    // example from:

    @Test
    @DisplayName("UPGMA sample test")
    fun upgmaSampleTest() {
        val sampleInput = """
4
0	20	17	11
20	0	20	13
17	20	0	10
11	13	10	0
        """.trimIndent()

        val expectedOutputString = """
0->5:7.000
1->6:8.833
2->4:5.000
3->4:5.000
4->2:5.000
4->3:5.000
4->5:2.000
5->0:7.000
5->4:2.000
5->6:1.833
6->5:1.833
6->1:8.833
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = upgma.upgmaStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)


    }

    // example from:
    // https://bioinformaticsalgorithms.com/data/extradatasets/evolution/UPGMA.txt

    @Test
    @DisplayName("UPGMA extra dataset test")
    fun upgmaExtraDatasetTest() {
        val sampleInput = """
27
0 558 767 179 821 142 317 535 297 834 675 450 808 729 586 792 292 51 101 67 122 900 257 500 569 88 442
558 0 119 623 974 855 389 185 548 361 266 636 284 958 635 255 372 640 128 718 64 619 752 846 105 927 995
767 119 0 982 176 433 684 903 547 744 585 369 795 309 510 734 594 581 695 845 172 247 391 521 299 220 610
179 623 982 0 170 79 302 613 566 494 330 649 210 819 826 413 544 322 951 488 572 705 476 560 192 377 955
821 974 176 170 0 909 608 50 954 103 202 52 378 645 357 733 262 412 897 551 181 787 672 611 854 591 699
142 855 433 79 909 0 781 491 132 394 972 203 789 782 168 597 643 756 835 797 435 133 520 763 54 910 768
317 389 684 302 608 781 0 757 77 353 667 946 209 321 908 90 499 719 151 99 221 419 334 485 741 701 936
535 185 903 613 50 491 757 0 350 971 337 131 525 754 235 347 349 264 959 704 549 860 466 364 721 546 356
297 548 547 566 954 132 77 350 0 254 314 573 447 376 671 94 858 748 702 303 561 401 824 56 617 218 916
834 361 744 494 103 394 353 971 254 0 290 523 420 968 664 116 440 543 931 312 265 990 204 944 938 930 657
675 266 585 330 202 972 667 337 314 290 0 755 791 346 899 578 336 234 921 524 539 928 503 315 999 711 467
450 636 369 649 52 203 946 131 573 523 755 0 989 189 198 518 737 126 139 287 432 891 324 462 143 227 765
808 284 795 210 378 789 209 525 447 420 791 989 0 565 456 790 138 785 874 917 60 289 725 934 121 1000 817
729 958 309 819 645 782 321 754 376 968 346 189 565 0 519 245 552 694 81 877 678 842 215 751 300 57 277
586 635 510 826 357 168 908 235 671 664 899 198 456 519 0 393 600 911 276 844 894 590 478 71 532 631 550
792 255 734 413 733 597 90 347 94 116 578 518 790 245 393 0 697 761 461 490 464 967 459 501 448 832 624
292 372 594 544 262 643 499 349 858 440 336 737 138 552 600 697 0 508 316 166 820 871 288 584 130 301 477
51 640 581 322 412 756 719 264 748 543 234 126 785 694 911 761 508 0 230 576 919 775 425 801 985 626 814
101 128 695 951 897 835 151 959 702 931 921 139 874 81 276 461 316 230 0 282 713 238 137 913 239 682 592
67 718 845 488 551 797 99 704 303 312 524 287 917 877 844 490 166 576 282 0 406 91 637 177 969 162 326
122 64 172 572 181 435 221 549 561 265 539 432 60 678 894 464 820 919 713 406 0 807 106 522 258 847 123
900 619 247 705 787 133 419 860 401 990 928 891 289 842 590 967 871 775 238 91 807 0 964 368 455 320 457
257 752 391 476 672 520 334 466 824 204 503 324 725 215 478 459 288 425 137 637 106 964 0 882 451 559 716
500 846 521 560 611 763 485 364 56 944 315 462 934 751 71 501 584 801 913 177 522 368 882 0 598 601 184
569 105 299 192 854 54 741 721 617 938 999 143 121 300 532 448 130 985 239 969 258 455 451 598 0 207 630
88 927 220 377 591 910 701 546 218 930 711 227 1000 57 631 832 301 626 682 162 847 320 559 601 207 0 984
442 995 610 955 699 768 936 356 916 657 467 765 817 277 550 624 477 814 592 326 123 457 716 184 630 984 0
        """.trimIndent()

        val expectedOutputString = """
0->28:25.500
1->36:59.500
2->36:59.500
3->37:67.750
4->27:25.000
5->29:27.000
6->33:45.000
7->27:25.000
8->30:28.000
9->39:117.250
10->44:168.000
11->35:45.750
12->32:30.000
13->31:28.500
14->41:131.667
15->33:45.000
16->44:168.000
17->28:25.500
18->38:68.500
19->34:45.500
20->32:30.000
21->34:45.500
22->38:68.500
23->30:28.000
24->29:27.000
25->31:28.500
26->48:235.375
27->4:25.000
27->35:20.750
27->7:25.000
28->17:25.500
28->40:101.125
28->0:25.500
29->24:27.000
29->5:27.000
29->37:40.750
30->8:28.000
30->42:128.125
30->23:28.000
31->25:28.500
31->13:28.500
31->45:201.125
32->12:30.000
32->43:134.375
32->20:30.000
33->39:72.250
33->6:45.000
33->15:45.000
34->42:110.625
34->19:45.500
34->21:45.500
35->41:85.917
35->27:20.750
35->11:45.750
36->1:59.500
36->43:104.875
36->2:59.500
37->3:67.750
37->49:189.155
37->29:40.750
38->40:58.125
38->18:68.500
38->22:68.500
39->9:117.250
39->33:72.250
39->46:113.417
40->38:58.125
40->28:101.125
40->45:103.000
41->35:85.917
41->14:131.667
41->50:127.068
42->48:79.250
42->30:128.125
42->34:110.625
43->32:134.375
43->46:66.292
43->36:104.875
44->47:67.292
44->16:168.000
44->10:168.000
45->31:201.125
45->47:5.667
45->40:103.000
46->39:113.417
46->43:66.292
46->49:26.238
47->44:67.292
47->50:23.443
47->45:5.667
48->26:235.375
48->52:59.289
48->42:79.250
49->51:29.874
49->46:26.238
49->37:189.155
50->41:127.068
50->47:23.443
50->51:28.045
51->52:7.884
51->50:28.045
51->49:29.874
52->48:59.289
52->51:7.884
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = upgma.upgmaStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)


    }

    // example from:

    @Test
    @DisplayName("UPGMA quiz")
    fun upgmaQuizTest() {
        val sampleInput = """
25
0 799 680 1202 1169 1188 1024 792 691 709 765 1223 1127 750 993 949 719 685 899 1130 1035 1141 1109 954 830
799 0 942 695 821 1074 1231 670 886 1076 921 1056 1181 1030 789 829 869 1097 1222 667 912 677 751 1020 862
680 942 0 808 834 820 1018 999 1015 735 739 718 831 675 863 1175 930 1212 859 688 649 1248 1166 1114 903
1202 695 808 0 957 775 778 770 850 1224 762 721 1053 657 963 847 1004 1180 1075 1039 711 889 858 1002 1218
1169 821 834 957 0 861 1113 813 804 803 745 911 1184 1244 979 1245 908 1226 835 1081 1249 1066 1174 1060 1040
1188 1074 820 775 861 0 843 893 1063 839 1124 864 906 1176 1208 833 1107 1119 627 660 702 923 1008 1161 1019
1024 1231 1018 778 1113 843 0 972 1246 697 801 714 1171 701 1102 1142 734 674 736 976 1199 909 655 1031 970
792 670 999 770 813 893 972 0 729 827 1016 989 891 1073 644 687 1120 1238 868 890 1227 1236 668 1201 1022
691 886 1015 850 804 1063 1246 729 0 918 1156 738 787 871 766 781 1144 873 1112 841 920 716 1034 907 1089
709 1076 735 1224 803 839 697 827 918 0 1192 673 992 1037 988 747 637 1147 936 818 744 1195 1013 983 1163
765 921 739 762 745 1124 801 1016 1156 1192 0 741 991 926 867 854 964 757 883 874 1121 1003 1164 771 764
1223 1056 718 721 911 864 714 989 738 673 741 0 732 790 1046 682 1151 707 933 632 816 1230 748 1229 690
1127 1181 831 1053 1184 906 1171 891 787 992 991 732 0 1247 1173 807 794 662 1103 1190 1240 648 740 819 1242
750 1030 675 657 1244 1176 701 1073 871 1037 926 790 1247 0 639 1092 773 1205 1143 1014 842 856 666 700 725
993 789 863 963 979 1208 1102 644 766 988 867 1046 1173 639 0 1160 885 669 1215 663 642 1220 997 1234 1198
949 829 1175 847 1245 833 1142 687 781 747 854 682 807 1092 1160 0 898 1051 809 1186 1150 806 1179 635 1128
719 869 930 1004 908 1107 734 1120 1144 637 964 1151 794 773 885 898 0 774 705 1027 1038 1043 769 728 679
685 1097 1212 1180 1226 1119 674 1238 873 1147 757 707 662 1205 669 1051 774 0 776 1090 643 935 982 706 872
899 1222 859 1075 835 627 736 868 1112 936 883 933 1103 1143 1215 809 705 776 0 1028 652 905 967 975 980
1130 667 688 1039 1081 660 976 890 841 818 874 632 1190 1014 663 1186 1027 1090 1028 0 960 720 1191 876 955
1035 912 649 711 1249 702 1199 1227 920 744 1121 816 1240 842 642 1150 1038 643 652 960 0 678 631 797 1068
1141 677 1248 889 1066 923 909 1236 716 1195 1003 1230 648 856 1220 806 1043 935 905 720 678 0 1213 1000 710
1109 751 1166 858 1174 1008 655 668 1034 1013 1164 748 740 666 997 1179 769 982 967 1191 631 1213 0 1146 1045
954 1020 1114 1002 1060 1161 1031 1201 907 983 771 1229 819 700 1234 635 728 706 975 876 797 1000 1146 0 1091
830 862 903 1218 1040 1019 970 1022 1089 1163 764 690 1242 725 1198 1128 679 872 980 955 1068 710 1045 1091 0
       """.trimIndent()

        // accepted answer
        val expectedOutputString = """
0->34:340.000
1->32:335.000
2->34:340.000
3->35:366.250
4->36:372.500
5->25:313.500
6->33:337.000
7->32:335.000
8->37:375.750
9->29:318.500
10->36:372.500
11->27:316.000
12->31:324.000
13->30:319.500
14->30:319.500
15->28:317.500
16->29:318.500
17->33:337.000
18->25:313.500
19->27:316.000
20->26:315.500
21->31:324.000
22->26:315.500
23->28:317.500
24->40:411.250
25->5:313.500
25->18:313.500
25->43:120.750
26->20:315.500
26->22:315.500
26->39:77.875
27->11:316.000
27->19:316.000
27->40:95.250
28->15:317.500
28->23:317.500
28->41:109.167
29->9:318.500
29->16:318.500
29->38:68.125
30->13:319.500
30->14:319.500
30->39:73.875
31->12:324.000
31->21:324.000
31->37:51.750
32->1:335.000
32->7:335.000
32->35:31.250
33->6:337.000
33->17:337.000
33->43:97.250
34->0:340.000
34->2:340.000
34->38:46.625
35->3:366.250
35->32:31.250
35->42:62.208
36->4:372.500
36->10:372.500
36->46:90.795
37->8:375.750
37->31:51.750
37->41:50.917
38->29:68.125
38->34:46.625
38->45:71.357
39->26:77.875
39->30:73.875
39->42:35.083
40->24:411.250
40->27:95.250
40->44:39.292
41->28:109.167
41->37:50.917
41->48:65.408
42->35:62.208
42->39:35.083
42->47:52.426
43->25:120.750
43->33:97.250
43->44:16.292
44->40:39.292
44->43:16.292
44->45:7.440
45->38:71.357
45->44:7.440
45->46:5.313
46->36:90.795
46->45:5.313
46->47:17.589
47->42:52.426
47->46:17.589
47->48:11.190
48->41:65.408
48->47:11.190
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = upgma.upgmaStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)


    }

//    private fun printGraph(g: Map<Int, Map<Int, Float>>) {
//
//        val fmt = NumberFormat.getNumberInstance(Locale.ROOT)
//        fmt.maximumFractionDigits = 3
//        fmt.minimumFractionDigits = 3
//
//        for (e in g) {
//            val key = e.key
//            val theMap = e.value
//
//            for (c in theMap) {
//                print("$key->")
//                val f = fmt.format(c.value)
//                println("${c.key}:$f")
//            }
//        }
//    }


    /**
     * compare two maps of structure:
     *  MutableMap<Int, Map<Int, Float>> = mutableMapOf()
     */
    private fun checkEdgesAreEqual(a: Map<Int, Map<Int, Float>>, b: Map<Int, Map<Int, Float>>) {

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
                assertEquals(distanceA!!.toDouble(), distanceB!!.toDouble(), 0.01)
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

    private fun parseMatrixInput(matrixSize: Int, lines: List<String>): D2Array<Float> {
        val theMatrix = mk.d2array(matrixSize, matrixSize) { 0f }

        for (i in 0 until matrixSize) {
            val l = lines[i].trim().split("\t", " ", "  ", "   ", "    ")
            for (j in 0 until matrixSize) {
                theMatrix[i, j] = l[j].toFloat()
            }
        }
        verifyMatrix(matrixSize, theMatrix)
        return theMatrix
    }

    /**
     * verify that the matrix is symmetric  - all rows are equal to columns for the same index
     */
    private fun verifyMatrix(matrixSize: Int, m: D2Array<Float>) {
        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                val a = m[i, j]
                val b = m[j, i]
                assertEquals(a, b)
            }
        }
    }

    private fun parseSampleInput(nodeToNodePlusDistance: List<String>): MutableMap<Int, MutableMap<Int, Float>> {
        val edgeMap: MutableMap<Int, MutableMap<Int, Float>> = mutableMapOf()
        for (e in nodeToNodePlusDistance) {
            val sourceDest = e.split("->")
            val destNodeAndWeightPair = sourceDest[1].split(":")
            val sourceNodeNumber = sourceDest[0].toInt()
            if (edgeMap.containsKey(sourceNodeNumber)) {
                edgeMap[sourceNodeNumber]!![destNodeAndWeightPair[0].toInt()] = destNodeAndWeightPair[1].toFloat()
            } else {
                edgeMap[sourceNodeNumber] = mutableMapOf(Pair(destNodeAndWeightPair[0].toInt(), destNodeAndWeightPair[1].toFloat()))
            }
        }
        return edgeMap
    }

    /**
     * pretty print a 2D matrix
     */
//    private fun printMatrix(matrixSize: Int, gArr: D2Array<Int>) {
//        val outStr = StringBuilder()
//        for (i in 0 until matrixSize) {
//            for (j in 0 until matrixSize) {
//                val numVal = String.format("%5d", gArr[i, j])
//                outStr.append(numVal)
//                if (j < matrixSize - 1) {
//                    outStr.append(" ")
//                }
//            }
//            outStr.append("\n")
//        }
//        println(outStr.toString())
//    }
}