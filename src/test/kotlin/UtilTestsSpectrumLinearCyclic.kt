@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate")

import algorithms.peptideMassSpectrum
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


internal class UtilTestsSpectrumLinearCyclic {

    /**
     * Code Challenge: Implement LinearSpectrum.

    Input: An amino acid string Peptide.
    Output: The linear spectrum of Peptide.

    Extra Dataset

    Sample Input: NQEL

    Sample Output:
    0 113 114 128 129 242 242 257 370 371 484

     */
    @Test
    @DisplayName("util: test linearSpectrum function 01")
    fun testLinearSpectrumFunction01() {
        val peptide = "NQEL"
        val expectedResult = listOf(0, 113, 114, 128, 129, 242, 242, 257, 370, 371, 484)

        val result = peptideMassSpectrum(peptide)
        assertEquals(expectedResult, result)
    }

    /**
     * @link: https://stepik.org/lesson/240286/step/2?unit=212632
     * test question for mass spectrum for large linear peptide
     */
    @Test
    @DisplayName("util: test linearSpectrum function 02")
    fun testLinearSpectrumFunction02() {
        val peptide = "IPVDQSNEERYQYEWSYETMFEPVMVRAGGFKVKEGNWQQKNLIMT"
        //val expectedResult = listOf(0,113,114,128,129,242,242,257,370,371,484)

        val result = peptideMassSpectrum(peptide)
        val formattedResult = result.joinToString(separator = " ")

        val expectedResult =
            "0 57 57 57 71 87 87 97 97 99 99 99 99 101 101 113 113 113 114 114 114 114 115 128 128 128 128 128 128 128 128 129 129 129 129 129 129 131 131 131 147 147 156 156 163 163 163 171 185 186 186 186 196 196 201 204 210 214 215 226 226 227 227 227 227 230 230 230 232 232 242 243 243 244 250 255 256 256 257 258 261 273 275 276 278 284 285 291 291 292 292 300 300 309 311 314 314 315 319 325 326 327 329 329 330 330 332 332 340 341 342 345 355 355 356 357 357 361 370 372 373 374 379 379 383 384 386 389 393 402 407 413 414 420 424 426 428 428 429 431 436 439 440 442 444 447 448 454 456 457 458 458 459 460 468 471 472 478 480 483 484 485 485 486 488 488 498 502 504 508 508 514 524 526 527 528 541 543 552 555 556 556 559 559 565 565 565 570 571 572 573 576 577 582 583 587 587 596 599 603 603 605 606 610 611 611 613 613 614 614 615 616 616 631 637 639 640 653 655 666 670 671 672 684 684 687 688 688 691 693 694 700 702 702 704 705 710 711 713 715 715 718 724 727 728 734 734 739 739 741 742 742 743 745 745 753 758 767 769 769 778 782 795 797 797 798 800 801 802 802 814 816 817 819 828 833 833 835 839 841 841 843 846 855 855 856 856 857 858 858 859 868 868 870 870 873 882 887 896 897 898 906 906 910 911 914 916 925 926 929 934 942 944 945 945 956 957 958 964 968 969 969 972 982 984 984 985 986 987 988 989 996 997 998 1011 1012 1019 1021 1024 1029 1034 1041 1042 1043 1043 1044 1045 1054 1054 1060 1063 1069 1071 1073 1073 1073 1081 1083 1086 1089 1090 1097 1097 1097 1102 1111 1112 1116 1117 1120 1127 1128 1141 1141 1142 1143 1148 1149 1155 1161 1167 1170 1171 1172 1173 1173 1174 1175 1183 1190 1197 1198 1202 1202 1210 1211 1212 1214 1217 1217 1218 1219 1225 1225 1226 1230 1236 1242 1244 1248 1249 1256 1259 1269 1269 1270 1270 1275 1290 1297 1299 1301 1301 1301 1304 1304 1312 1313 1313 1318 1321 1324 1326 1329 1330 1338 1339 1341 1345 1347 1358 1358 1364 1365 1372 1373 1380 1382 1384 1384 1398 1398 1398 1400 1404 1405 1411 1417 1422 1428 1429 1429 1433 1433 1437 1441 1442 1449 1452 1453 1455 1457 1458 1462 1469 1469 1471 1472 1486 1486 1493 1499 1508 1510 1512 1527 1527 1529 1534 1536 1540 1540 1543 1545 1547 1548 1550 1551 1556 1557 1559 1561 1562 1565 1567 1568 1569 1570 1584 1585 1590 1597 1599 1599 1600 1621 1627 1628 1634 1637 1649 1654 1655 1656 1656 1658 1663 1665 1669 1671 1674 1676 1676 1679 1683 1684 1687 1689 1692 1696 1698 1712 1713 1713 1714 1714 1726 1726 1731 1750 1753 1755 1762 1763 1769 1777 1777 1778 1783 1784 1784 1786 1791 1794 1797 1801 1805 1812 1812 1812 1813 1815 1820 1823 1826 1827 1840 1842 1843 1845 1852 1855 1862 1864 1877 1883 1884 1891 1897 1900 1906 1906 1908 1909 1910 1912 1914 1919 1926 1929 1936 1940 1941 1941 1941 1943 1944 1947 1957 1963 1969 1976 1976 1983 1987 1992 1995 2001 2006 2008 2011 2012 2018 2023 2028 2028 2031 2035 2038 2039 2042 2053 2055 2057 2058 2069 2070 2073 2075 2075 2077 2082 2092 2105 2107 2115 2116 2123 2129 2132 2137 2139 2139 2140 2142 2146 2152 2156 2156 2159 2162 2167 2170 2184 2184 2186 2198 2202 2203 2206 2206 2214 2238 2238 2238 2244 2253 2255 2260 2263 2266 2268 2268 2269 2270 2271 2279 2281 2283 2283 2285 2285 2287 2290 2303 2309 2315 2337 2342 2342 2343 2366 2366 2367 2368 2369 2380 2382 2382 2384 2385 2391 2392 2394 2397 2399 2407 2407 2414 2415 2416 2418 2423 2434 2456 2465 2467 2471 2471 2479 2484 2495 2496 2496 2506 2511 2513 2514 2515 2519 2520 2522 2523 2528 2529 2535 2546 2547 2555 2570 2579 2581 2594 2595 2598 2600 2608 2610 2610 2611 2613 2614 2634 2634 2642 2642 2642 2647 2648 2651 2652 2657 2660 2683 2694 2697 2698 2708 2710 2710 2710 2711 2723 2726 2726 2739 2755 2761 2762 2763 2766 2770 2771 2773 2776 2780 2797 2807 2809 2811 2820 2823 2825 2828 2837 2837 2840 2841 2853 2854 2855 2874 2886 2886 2890 2891 2894 2898 2906 2920 2924 2925 2934 2939 2940 2940 2948 2951 2953 2956 2957 2981 2981 2983 2984 2987 2987 3003 3017 3019 3026 3037 3038 3039 3052 3053 3054 3062 3081 3082 3084 3085 3096 3098 3109 3111 3112 3116 3118 3118 3120 3136 3140 3150 3166 3166 3167 3185 3195 3210 3210 3211 3212 3213 3219 3224 3225 3226 3247 3248 3248 3249 3253 3266 3267 3279 3281 3292 3313 3313 3323 3325 3326 3339 3339 3341 3348 3363 3366 3376 3376 3380 3381 3396 3405 3410 3411 3412 3420 3428 3439 3441 3453 3455 3468 3476 3477 3497 3504 3504 3510 3511 3525 3527 3533 3539 3540 3540 3552 3556 3567 3568 3582 3590 3598 3618 3624 3632 3639 3639 3655 3655 3667 3668 3669 3681 3683 3695 3696 3726 3731 3737 3746 3752 3753 3754 3783 3784 3795 3797 3812 3823 3824 3825 3840 3844 3851 3854 3859 3865 3882 3909 3912 3913 3939 3951 3952 3953 3964 3968 3969 3972 3975 3979 4011 4022 4026 4065 4067 4068 4076 4080 4081 4083 4092 4103 4108 4135 4154 4154 4165 4178 4182 4194 4195 4204 4209 4221 4266 4269 4278 4279 4282 4282 4291 4307 4323 4323 4367 4368 4392 4397 4410 4410 4420 4422 4436 4437 4465 4496 4523 4524 4525 4538 4549 4550 4551 4578 4593 4624 4637 4652 4652 4653 4663 4680 4706 4721 4750 4752 4765 4767 4781 4794 4834 4849 4866 4878 4880 4881 4895 4962 4963 4979 4982 4993 5009 5076 5076 5092 5110 5124 5189 5189 5223 5225 5302 5320 5324 5421 5433 5534"
        assertEquals(expectedResult, formattedResult)
    }

    /**
     * Generating Theoretical Spectrum Problem: Generate the theoretical spectrum of a cyclic peptide.

    Input: An amino acid string Peptide.
    Output: Cyclospectrum(Peptide).

    Code Challenge: Solve the Generating Theoretical Spectrum Problem.
    Sample Input: LEQN

    Sample Output:

    0 113 114 128 129 227 242 242 257 355 356 370 371 484

     Links:
     Stepik: https://stepik.org/lesson/240279/step/4?unit=212625
     Rosalind: http://rosalind.info/problems/ba4j/

     */
    @Test
    @DisplayName("util: test Cyclic LEQN Spectrum function 03")
    fun testCyclicLEQNspectrumFunction03() {
        val peptide = "LEQN"
        val expectedResult = listOf(0, 113, 114, 128, 129, 227, 242, 242, 257, 355, 356, 370, 371, 484)

        val result = peptideMassSpectrum(peptide, isCyclicPeptide = true)
        assertEquals(expectedResult, result)
    }



    @Test
    @DisplayName("util: test linearSpectrum function 04")
    fun testLinearSpectrumFunction04() {
        val peptide = "MPYENCCCWMFNIRKGQPDFFRKGAVPYVVPMNCIRWS"


        val result = peptideMassSpectrum(peptide, isCyclicPeptide = true)
        println(result.joinToString(" "))
    }


    /**
     * dataset : rosalind:
     * https://bioinformaticsalgorithms.com/data/extradatasets/antibiotics/theoretical_spectrum.txt
     * Input peptide:

    IAQMLFYCKVATN

    Output see below

     */

    @Test
    @DisplayName("util: test linearSpectrum function 05")
    fun testLinearSpectrumFunction05() {
        val peptide = "IAQMLFYCKVATN"
        val expectedResult = listOf(
            0,
            71,
            71,
            99,
            101,
            103,
            113,
            113,
            114,
            128,
            128,
            131,
            147,
            163,
            170,
            172,
            184,
            199,
            215,
            227,
            227,
            231,
            244,
            259,
            260,
            266,
            271,
            286,
            298,
            298,
            310,
            312,
            328,
            330,
            330,
            372,
            385,
            391,
            394,
            399,
            399,
            399,
            401,
            413,
            423,
            426,
            443,
            443,
            470,
            493,
            498,
            502,
            513,
            519,
            526,
            527,
            541,
            554,
            556,
            557,
            564,
            569,
            590,
            598,
            616,
            626,
            640,
            654,
            657,
            658,
            665,
            670,
            682,
            697,
            697,
            703,
            711,
            729,
            729,
            753,
            753,
            771,
            779,
            785,
            785,
            800,
            812,
            817,
            824,
            825,
            828,
            842,
            856,
            866,
            884,
            892,
            913,
            918,
            925,
            926,
            928,
            941,
            955,
            956,
            963,
            969,
            980,
            984,
            989,
            1012,
            1039,
            1039,
            1056,
            1059,
            1069,
            1081,
            1083,
            1083,
            1083,
            1088,
            1091,
            1097,
            1110,
            1152,
            1152,
            1154,
            1170,
            1172,
            1184,
            1184,
            1196,
            1211,
            1216,
            1222,
            1223,
            1238,
            1251,
            1255,
            1255,
            1267,
            1283,
            1298,
            1310,
            1312,
            1319,
            1335,
            1351,
            1354,
            1354,
            1368,
            1369,
            1369,
            1379,
            1381,
            1383,
            1411,
            1411,
            1482
        )

        val result = peptideMassSpectrum(peptide, isCyclicPeptide = true)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("util: test Cyclic Test Spectrum function 06")
    fun testCyclicTestSpectrumFunction06() {
        val peptide = "KLLKKMNTIGAYKEN"  // test peptide

        // test question answer:
        val expectedResult = listOf(
            0,
            57,
            71,
            101,
            113,
            113,
            113,
            114,
            114,
            128,
            128,
            128,
            128,
            128,
            129,
            131,
            163,
            170,
            214,
            215,
            226,
            234,
            241,
            241,
            241,
            242,
            243,
            245,
            256,
            257,
            259,
            271,
            291,
            291,
            328,
            342,
            346,
            354,
            354,
            355,
            362,
            369,
            371,
            371,
            373,
            385,
            387,
            404,
            419,
            420,
            456,
            459,
            468,
            474,
            482,
            482,
            484,
            491,
            499,
            500,
            501,
            505,
            516,
            532,
            534,
            548,
            587,
            587,
            596,
            597,
            602,
            605,
            610,
            612,
            613,
            614,
            619,
            633,
            644,
            661,
            662,
            662,
            715,
            715,
            715,
            724,
            725,
            725,
            727,
            733,
            741,
            747,
            750,
            762,
            772,
            775,
            775,
            790,
            828,
            828,
            843,
            846,
            853,
            853,
            855,
            855,
            876,
            876,
            878,
            878,
            885,
            888,
            903,
            903,
            941,
            956,
            956,
            959,
            969,
            981,
            984,
            990,
            998,
            1004,
            1006,
            1006,
            1007,
            1016,
            1016,
            1016,
            1069,
            1069,
            1070,
            1087,
            1098,
            1112,
            1117,
            1118,
            1119,
            1121,
            1126,
            1129,
            1134,
            1135,
            1144,
            1144,
            1183,
            1197,
            1199,
            1215,
            1226,
            1230,
            1231,
            1232,
            1240,
            1247,
            1249,
            1249,
            1257,
            1263,
            1272,
            1275,
            1311,
            1312,
            1327,
            1344,
            1346,
            1358,
            1360,
            1360,
            1362,
            1369,
            1376,
            1377,
            1377,
            1385,
            1389,
            1403,
            1440,
            1440,
            1460,
            1472,
            1474,
            1475,
            1486,
            1488,
            1489,
            1490,
            1490,
            1490,
            1497,
            1505,
            1516,
            1517,
            1561,
            1568,
            1600,
            1602,
            1603,
            1603,
            1603,
            1603,
            1603,
            1617,
            1617,
            1618,
            1618,
            1618,
            1630,
            1660,
            1674,
            1731
        )

        val result = peptideMassSpectrum(peptide, isCyclicPeptide = true)
        //println(result.joinToString(separator = " "))
        assertEquals(expectedResult, result)

    }

    /**
     *  Rosalind test question:
     *  http://rosalind.info/problems/ba4c/

    Generating Theoretical Spectrum Problem

    Generate the theoretical spectrum of a cyclic peptide.

    Given: An amino acid string Peptide.

    Return: Cyclospectrum(Peptide).
     */
    @Test
    @DisplayName("util: test Cyclic Rosalind Test Spectrum function 06")
    fun testCyclicRosalindTestSpectrumFunction06() {
        val peptide = "YAWIYFEGSVMSPI"  // test peptide

        // test question answer:
        val expectedResult = listOf(
            0,
            57,
            71,
            87,
            87,
            97,
            99,
            113,
            113,
            129,
            131,
            144,
            147,
            163,
            163,
            184,
            186,
            186,
            186,
            210,
            218,
            230,
            234,
            243,
            257,
            273,
            276,
            276,
            276,
            297,
            299,
            310,
            315,
            317,
            317,
            333,
            347,
            370,
            372,
            373,
            374,
            404,
            414,
            420,
            420,
            423,
            428,
            439,
            444,
            460,
            461,
            462,
            496,
            501,
            503,
            519,
            527,
            531,
            533,
            533,
            533,
            552,
            558,
            583,
            590,
            591,
            609,
            609,
            614,
            630,
            646,
            650,
            662,
            671,
            680,
            682,
            687,
            690,
            696,
            696,
            717,
            737,
            738,
            743,
            761,
            777,
            795,
            795,
            800,
            809,
            809,
            813,
            830,
            834,
            834,
            843,
            848,
            848,
            866,
            882,
            900,
            905,
            906,
            926,
            947,
            947,
            953,
            956,
            961,
            963,
            972,
            981,
            993,
            997,
            1013,
            1029,
            1034,
            1034,
            1052,
            1053,
            1060,
            1085,
            1091,
            1110,
            1110,
            1110,
            1112,
            1116,
            1124,
            1140,
            1142,
            1147,
            1181,
            1182,
            1183,
            1199,
            1204,
            1215,
            1220,
            1223,
            1223,
            1229,
            1239,
            1269,
            1270,
            1271,
            1273,
            1296,
            1310,
            1326,
            1326,
            1328,
            1333,
            1344,
            1346,
            1367,
            1367,
            1367,
            1370,
            1386,
            1400,
            1409,
            1413,
            1425,
            1433,
            1457,
            1457,
            1457,
            1459,
            1480,
            1480,
            1496,
            1499,
            1512,
            1514,
            1530,
            1530,
            1544,
            1546,
            1556,
            1556,
            1572,
            1586,
            1643
        )

        val result = peptideMassSpectrum(peptide, isCyclicPeptide = true)
        //println(result.joinToString(separator = " "))
        assertEquals(expectedResult, result)

    }


    /**
     *  Rosalind test question:
     *  http://rosalind.info/problems/ba4d/

    Counting Peptides with Given Mass Problem

    Compute the number of peptides of given total mass.

    Given: An integer m.

    Return: The number of linear peptides having integer mass m.
     */
    @Test
    @DisplayName("util: test Counting Peptides with Given Mass Problem 07")
    fun testCyclicRosalindTestCountingPeptidesGivenMass07() {

        val aminoMassesSet = listOf(
            57,
            71,
            87,
            97,
            99,
            101,
            103,
            113,
            113, // REPEATED!! (I vs L)
            114,
            115,
            128,
            128, // REPEATED (K vs Q)
            129,
            131,
            137,
            147,
            156,
            163,
            186
        ).toSet()  // convert to set - no repeated values

        val targetMass = 1024L
//        val targetMass = 114L

        val massMap: MutableMap<Long, Long> = mutableMapOf()

        massMap[0L] = 1L

        for (i in 57..targetMass) {
            for (m in aminoMassesSet) {
                val currentCount = if (massMap.containsKey(i)) {
                    massMap[i]!!
                } else {
                    0L
                }
                if (massMap.containsKey(i - m)) {
                    massMap[i] = currentCount + massMap[i - m]!!
                }
            }
        }

//        println(massMap[targetMass])
        assertEquals(massMap[targetMass], 14712706211L)

    }
}