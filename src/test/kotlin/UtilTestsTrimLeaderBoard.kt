@file:Suppress("UNUSED_VARIABLE")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import algorithms.trimLeaderboard

internal class UtilTestsTrimLeaderBoard {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * test the trim function -
     *   given a list of peptide strings, and a spectrum,
     *   form a list of the best matches and then trim the list to
     *   the "winner level" of N
     */
    @Test
    @DisplayName("test Trim Leaderboard")
    fun testTrimLeaderboard() {
        val peptides = "LAST ALST TLLT TQAS".split(" ")
        val spectrum = listOf(0, 71, 87, 101, 113, 158, 184, 188, 259, 271, 372)
        val n = 2

        val result = trimLeaderboard(peptides, spectrum, n)

//       println(result)
    }

/*    @Test
    @DisplayName("test Trim Leaderboard 02")
    fun testTrimLeaderboard02() {
        val peptides = "GFAQHVMEGIGLDVKFTNIISCFFDHEWSTCHCKHHNSINHTMSMVF LIGDDDEADNCMMMVQSIKWKTLLRYGAFFTFPFYSYAILHVFYVLW KPMWWAFIFGFCDMKNCFDAPFWMHNSVQWEQHYRCNDVKMMSQLCW MAPRDIRMYFDKYHETAALDSQWIIQQIYHLMNVRKLNRTNRFTSVG FEKYHQQQILIDAQRVRLVHTVARAGPGWVQTGGWQQTCPRYKPYAW NVNPCERSSPPNFSWFMSFWADNSDYGDVIFCCPSVLRTMEMQSKKG WDTDTFFQKAMLKKDETADQIFNLRPYSLTCHNENILGNDNQEKQAG TLGSGENDKGHTVGAGHKGHPEREFEAPIERHEHPRVMMTKVGCYWI VCGHHHEQTVIMKAFDAWKVGFLGPIVAWVIFPAVYLWGKSLCPWTN YDSPTTYLSTHCHRLTNRMVHENPVICPPQDFAKYLIQSGWEFPLVA KDPINQTGDTNVRNFNVGCFCGCYFQWERHDGTPMHFWFSQKLSLTW HMKKLFWGIMKHHILFDFVNQPAFTNKAKGPTPHKAEELIRNLGQEK FNDRQRLVCHTNQCCAYKNKVVCSGGGSEISTNAHTYHFLALGHQVG MYYSAWTEPYYPPTLQIWWWYWKYGCTACQTGPHTMVFVQPTCKCVH YYGYRQCSWCQRWTVRRMLCWIDVLHKALHWHVCLLFHQALYGFSHE WASIGAIMRSAKDMYESLEFHKTHCTYFVYMVCKEARPGWTFFIEWV".split(" ")
        val spectrum = listOf(0, 71, 87, 97, 97, 99, 101, 101, 101, 101, 101, 103, 103, 113, 113, 113, 113, 113, 113, 113, 113, 114, 114, 115, 115, 128, 128, 129, 129, 129, 129, 129, 129, 131, 131, 131, 137, 137, 147, 147, 156, 156, 156, 163, 163, 163, 186, 186, 198, 200, 200, 202, 202, 204, 214, 214, 216, 216, 216, 226, 228, 230, 234, 242, 242, 242, 242, 242, 242, 244, 245, 253, 257, 257, 257, 259, 260, 266, 266, 268, 269, 271, 276, 276, 276, 276, 278, 283, 284, 285, 287, 293, 294, 299, 301, 303, 313, 317, 317, 317, 327, 329, 331, 343, 343, 347, 354, 355, 355, 356, 359, 363, 363, 370, 370, 371, 371, 372, 379, 382, 384, 388, 389, 397, 397, 400, 405, 407, 408, 408, 413, 413, 415, 415, 416, 418, 418, 420, 422, 428, 430, 430, 432, 434, 439, 444, 455, 456, 458, 459, 473, 476, 484, 484, 485, 485, 487, 499, 500, 501, 506, 507, 510, 510, 511, 513, 515, 518, 522, 526, 527, 528, 529, 529, 531, 533, 533, 537, 540, 541, 544, 544, 545, 547, 558, 559, 562, 568, 569, 571, 572, 574, 585, 586, 588, 597, 598, 607, 610, 612, 616, 619, 620, 624, 624, 625, 626, 631, 636, 641, 644, 646, 650, 657, 657, 660, 662, 663, 669, 669, 670, 671, 672, 674, 675, 678, 681, 684, 685, 689, 691, 692, 696, 698, 699, 700, 700, 701, 711, 733, 733, 735, 738, 738, 739, 741, 753, 771, 772, 772, 773, 775, 775, 775, 778, 779, 782, 783, 783, 786, 788, 789, 794, 794, 797, 798, 798, 800, 801, 802, 804, 805, 806, 808, 810, 813, 815, 828, 837, 840, 846, 854, 862, 864, 864, 866, 869, 882, 884, 885, 886, 888, 889, 899, 901, 902, 902, 903, 904, 907, 908, 911, 911, 914, 914, 924, 925, 926, 928, 935, 935, 937, 937, 941, 941, 945, 952, 955, 961, 961, 975, 975, 977, 984, 987, 988, 992, 995, 999, 1002, 1013, 1015, 1016, 1017, 1017, 1017, 1022, 1025, 1032, 1032, 1038, 1039, 1039, 1040, 1044, 1051, 1055, 1058, 1058, 1059, 1062, 1065, 1066, 1070, 1070, 1072, 1074, 1084, 1088, 1097, 1099, 1100, 1101, 1103, 1104, 1105, 1106, 1118, 1118, 1121, 1130, 1133, 1135, 1142, 1150, 1151, 1153, 1153, 1154, 1154, 1156, 1165, 1171, 1172, 1186, 1187, 1194, 1196, 1198, 1200, 1201, 1201, 1202, 1207, 1212, 1213, 1214, 1215, 1216, 1217, 1217, 1218, 1218, 1219, 1231, 1233, 1234, 1234, 1236, 1248, 1255, 1259, 1264, 1267, 1272, 1279, 1281, 1285, 1298, 1300, 1301, 1309, 1311, 1315, 1315, 1315, 1316, 1318, 1319, 1319, 1321, 1325, 1330, 1331, 1334, 1335, 1338, 1341, 1343, 1344, 1346, 1347, 1348, 1352, 1363, 1363, 1372, 1379, 1395, 1396, 1398, 1402, 1410, 1414, 1414, 1422, 1422, 1428, 1429, 1429, 1430, 1431, 1432, 1433, 1434, 1435, 1435, 1438, 1447, 1448, 1450, 1452, 1460, 1461, 1471, 1472, 1472, 1476, 1476, 1478, 1481, 1494, 1499, 1509, 1515, 1517, 1521, 1525, 1532, 1534, 1535, 1535, 1535, 1543, 1546, 1548, 1551, 1557, 1557, 1561, 1561, 1561, 1562, 1566, 1566, 1573, 1577, 1581, 1585, 1585, 1589, 1603, 1605, 1606, 1608, 1609, 1609, 1612, 1618, 1628, 1634, 1635, 1636, 1638, 1647, 1663, 1664, 1664, 1664, 1672, 1672, 1674, 1674, 1677, 1680, 1682, 1686, 1690, 1694, 1699, 1702, 1708, 1713, 1715, 1716, 1718, 1718, 1722, 1723, 1724, 1740, 1741, 1743, 1748, 1749, 1756, 1763, 1777, 1777, 1778, 1781, 1785, 1792, 1801, 1803, 1803, 1805, 1810, 1811, 1811, 1815, 1819, 1819, 1822, 1823, 1827, 1830, 1837, 1837, 1842, 1844, 1849, 1850, 1853, 1869, 1872, 1876, 1878, 1878, 1879, 1887, 1905, 1906, 1906, 1912, 1916, 1916, 1916, 1924, 1925, 1929, 1934, 1936, 1939, 1940, 1940, 1940, 1948, 1948, 1950, 1965, 1972, 1979, 1979, 1991, 1993, 1996, 2000, 2000, 2005, 2007, 2007, 2009, 2017, 2019, 2019, 2026, 2031, 2034, 2035, 2039, 2042, 2053, 2053, 2058, 2061, 2064, 2076, 2079, 2079, 2087, 2087, 2094, 2096, 2097, 2103, 2113, 2118, 2120, 2120, 2123, 2125, 2132, 2132, 2135, 2135, 2136, 2138, 2140, 2147, 2150, 2167, 2171, 2171, 2192, 2193, 2200, 2200, 2200, 2205, 2209, 2216, 2216, 2219, 2224, 2226, 2226, 2232, 2233, 2233, 2233, 2235, 2237, 2250, 2252, 2262, 2266, 2266, 2268, 2276, 2284, 2287, 2296, 2300, 2303, 2313, 2318, 2334, 2334, 2334, 2336, 2336, 2337, 2339, 2347, 2349, 2355, 2355, 2356, 2363, 2363, 2365, 2366, 2379, 2380, 2389, 2397, 2397, 2413, 2413, 2416, 2418, 2434, 2435, 2437, 2446, 2447, 2449, 2449, 2452, 2466, 2468, 2468, 2473, 2476, 2490, 2492, 2493, 2494, 2494, 2494, 2502, 2510, 2511, 2519, 2526, 2533, 2536, 2542, 2547, 2549, 2550, 2553, 2560, 2578, 2579, 2579, 2586, 2597, 2605, 2605, 2615, 2620, 2622, 2623, 2624, 2624, 2625, 2627, 2631, 2632, 2634, 2648, 2650, 2650, 2650, 2651, 2655, 2689, 2691, 2706, 2710, 2711, 2715, 2733, 2733, 2733, 2735, 2738, 2742, 2742, 2744, 2747, 2751, 2753, 2756, 2761, 2763, 2768, 2771, 2779, 2802, 2807, 2813, 2820, 2824, 2825, 2828, 2836, 2836, 2836, 2843, 2846, 2862, 2862, 2866, 2871, 2873, 2876, 2876, 2881, 2882, 2900, 2903, 2907, 2924, 2933, 2933, 2937, 2938, 2944, 2944, 2949, 2953, 2957, 2965, 2972, 2975, 2975, 2975, 2987, 2995, 2999, 3004, 3018, 3034, 3034, 3037, 3037, 3038, 3039, 3058, 3062, 3066, 3066, 3070, 3073, 3078, 3078, 3082, 3088, 3101, 3104, 3112, 3119, 3130, 3135, 3138, 3149, 3151, 3152, 3163, 3163, 3171, 3172, 3179, 3190, 3191, 3193, 3195, 3217, 3217, 3220, 3229, 3238, 3241, 3241, 3243, 3244, 3248, 3250, 3251, 3276, 3286, 3291, 3292, 3300, 3308, 3318, 3320, 3321, 3330, 3335, 3342, 3351, 3351, 3354, 3354, 3357, 3358, 3358, 3372, 3387, 3405, 3407, 3420, 3422, 3429, 3431, 3433, 3433, 3434, 3439, 3448, 3455, 3471, 3483, 3485, 3486, 3486, 3488, 3510, 3514, 3521, 3533, 3534, 3534, 3535, 3546, 3551, 3552, 3568, 3585, 3596, 3599, 3599, 3600, 3611, 3611, 3614, 3615, 3622, 3634, 3635, 3647, 3649, 3664, 3664, 3681, 3682, 3696, 3697, 3708, 3713, 3727, 3728, 3728, 3728, 3735, 3748, 3750, 3751, 3771, 3777, 3797, 3809, 3812, 3827, 3828, 3837, 3841, 3841, 3842, 3851, 3857, 3864, 3864, 3868, 3868, 3884, 3898, 3913, 3940, 3940, 3942, 3943, 3955, 3965, 3969, 3970, 3970, 3977, 3981, 4013, 4014, 4027, 4027, 4044, 4053, 4054, 4056, 4057, 4083, 4096, 4098, 4099, 4110, 4126, 4140, 4140, 4140, 4145, 4155, 4158, 4167, 4171, 4184, 4209, 4211, 4212, 4223, 4253, 4255, 4259, 4268, 4272, 4284, 4296, 4296, 4299, 4303, 4313, 4352, 4368, 4373, 4374, 4397, 4397, 4397, 4400, 4409, 4409, 4416, 4428, 4465, 4469, 4487, 4501, 4510, 4510, 4526, 4529, 4538, 4560, 4566, 4572, 4584, 4630, 4639, 4639, 4639, 4643, 4651, 4673, 4673, 4681, 4685, 4752, 4752, 4752, 4768, 4782, 4786, 4786, 4802, 4829, 4853, 4867, 4881, 4881, 4883, 4915, 4915, 4942, 4968, 4968, 4982, 4994, 5028, 5044, 5069, 5069, 5071, 5095, 5097, 5157, 5157, 5170, 5184, 5198, 5210, 5258, 5270, 5299, 5311, 5313, 5371, 5373, 5412, 5426, 5474, 5486, 5527, 5575, 5587, 5642, 5688, 5743, 5844)
        val n = 5

        val result = trimLeaderboard(peptides, spectrum, n)

//       println(result)

        val expectedResult = "WASIGAIMRSAKDMYESLEFHKTHCTYFVYMVCKEARPGWTFFIEWV YYGYRQCSWCQRWTVRRMLCWIDVLHKALHWHVCLLFHQALYGFSHE WDTDTFFQKAMLKKDETADQIFNLRPYSLTCHNENILGNDNQEKQAG YDSPTTYLSTHCHRLTNRMVHENPVICPPQDFAKYLIQSGWEFPLVA MAPRDIRMYFDKYHETAALDSQWIIQQIYHLMNVRKLNRTNRFTSVG FEKYHQQQILIDAQRVRLVHTVARAGPGWVQTGGWQQTCPRYKPYAW".split(" ").sorted()
        assertEquals(expectedResult.sorted(), result.sorted())
    }*/

    /*@Test
    @DisplayName("test Trim Leaderboard 03")
    fun testTrimLeaderboard03() {
        val peptides = "ARRWNPILMMCYEPYIMKVSHSTLMNIFINKHYTTKHRSPLEKAYVK WEKVFWQDDKPAPYWIHRGKMTFHQCCQMVSKGAICMVFFFIVGIEN CPFDHDGYNQGGEAKAMFQDCALEEWLNEYPLNIKEWTRMETKVVGK VIGFRDVVTCQMDKIYAAQDWVDTIGRLMIGWKCGVPENRQYKSLFD ATSYEWAKGHGGMDNAYLNYMYIVLRLVYCNNHPATADWRLEIPMTH VSIIEVAFNMSIHSDMHFRVCGSFAKLMIVKSPTSNASNQLRWEYND TREKARECTWKIPLMHHVLIFNVRSAIVGQDWHKHYVDYRPQASNLV EEKGCPLQDVCIVVCEWQPTKKRHQIYQTFLCSRANRHVKEQMMHKM NQRAPHNLAEPRFSTLSGHNCHNWQRKSAFWSKWCRPCCECYVSIST QQCRWVRQSYGGEASQIKSDQINVHERAAAHFYVNIKDNQKDSNVTP PRMYAKNSKGADEGWICNMEGPGQKTRMNNDDMIWPGPNRYKDETTQ NTIQFDGDSQLHYETVSAMTNLHYTLQRIIWCYRMDDRCYFDQSTVS QLFVHSYIANHSEDHKEGLEYGTPQHYAFCGYEWTLVLQFNQHDKDD CPILTYPWPMVCRAAARFGYWEWPAHLTWPEDGLDLMSAPGAQENKF MHVTQESHLFEQQQYLCYDLLCHRYMPIIAARRGHTHQARVAISMLS".split(" ")

        val spectrum = listOf(0, 57, 57, 57, 71, 71, 71, 71, 87, 87, 97, 97, 99, 99, 99, 101, 103, 103, 103, 113, 113, 113, 113, 113, 113, 113, 114, 115, 115, 115, 128, 128, 128, 128, 128, 128, 129, 129, 129, 129, 129, 131, 131, 137, 147, 160, 163, 163, 168, 170, 170, 172, 174, 184, 184, 185, 186, 186, 196, 199, 200, 200, 200, 202, 210, 212, 214, 214, 216, 216, 218, 218, 230, 231, 241, 242, 242, 242, 243, 244, 250, 250, 256, 257, 257, 259, 259, 259, 267, 271, 273, 273, 275, 275, 276, 276, 281, 287, 289, 291, 292, 297, 298, 298, 299, 313, 313, 314, 315, 315, 317, 321, 327, 329, 330, 331, 344, 345, 346, 347, 349, 356, 358, 359, 363, 368, 371, 371, 372, 374, 374, 375, 379, 380, 384, 386, 386, 387, 387, 390, 391, 398, 400, 401, 402, 405, 410, 416, 418, 420, 422, 427, 427, 429, 432, 434, 436, 443, 445, 455, 458, 460, 462, 462, 462, 467, 468, 471, 474, 478, 481, 481, 484, 487, 489, 489, 490, 492, 492, 493, 499, 500, 511, 513, 514, 515, 515, 519, 526, 528, 530, 533, 546, 549, 549, 550, 551, 556, 557, 560, 561, 563, 563, 563, 565, 568, 573, 575, 578, 580, 581, 584, 586, 587, 590, 590, 597, 598, 602, 605, 606, 613, 618, 618, 621, 621, 625, 628, 634, 639, 639, 643, 643, 648, 650, 660, 664, 664, 666, 676, 677, 677, 678, 678, 679, 681, 686, 688, 693, 696, 699, 700, 704, 710, 710, 711, 712, 715, 718, 719, 721, 726, 731, 733, 734, 735, 737, 747, 750, 750, 750, 751, 752, 756, 757, 765, 765, 770, 777, 780, 781, 789, 792, 799, 805, 806, 806, 807, 808, 809, 813, 819, 822, 824, 825, 828, 829, 832, 836, 836, 839, 839, 839, 843, 847, 849, 849, 850, 863, 863, 863, 863, 867, 870, 878, 880, 884, 886, 894, 896, 896, 899, 902, 905, 910, 911, 920, 920, 923, 926, 934, 934, 936, 936, 947, 949, 949, 951, 951, 952, 952, 953, 954, 956, 957, 962, 966, 976, 977, 977, 978, 987, 992, 992, 995, 996, 997, 999, 999, 1008, 1009, 1023, 1025, 1027, 1038, 1039, 1039, 1041, 1048, 1049, 1049, 1052, 1055, 1062, 1065, 1065, 1068, 1070, 1075, 1079, 1080, 1080, 1081, 1081, 1086, 1086, 1095, 1097, 1100, 1105, 1105, 1106, 1106, 1110, 1112, 1112, 1120, 1120, 1124, 1124, 1126, 1136, 1136, 1138, 1141, 1152, 1152, 1155, 1167, 1167, 1169, 1177, 1178, 1180, 1193, 1195, 1196, 1199, 1199, 1199, 1199, 1204, 1208, 1209, 1210, 1223, 1223, 1225, 1225, 1226, 1227, 1229, 1234, 1237, 1237, 1238, 1239, 1240, 1249, 1249, 1249, 1251, 1264, 1267, 1268, 1270, 1283, 1294, 1295, 1296, 1297, 1298, 1307, 1312, 1312, 1314, 1320, 1321, 1323, 1325, 1328, 1336, 1337, 1337, 1338, 1343, 1350, 1352, 1352, 1354, 1354, 1362, 1362, 1367, 1367, 1368, 1370, 1378, 1379, 1383, 1385, 1395, 1396, 1396, 1409, 1411, 1414, 1422, 1424, 1424, 1426, 1433, 1441, 1442, 1443, 1449, 1449, 1450, 1452, 1465, 1466, 1467, 1467, 1468, 1470, 1471, 1475, 1479, 1480, 1481, 1482, 1495, 1497, 1498, 1499, 1507, 1511, 1514, 1520, 1520, 1524, 1527, 1537, 1539, 1541, 1542, 1548, 1551, 1555, 1562, 1570, 1571, 1579, 1580, 1594, 1594, 1595, 1596, 1596, 1597, 1598, 1600, 1604, 1610, 1612, 1613, 1614, 1617, 1626, 1627, 1628, 1633, 1633, 1635, 1638, 1642, 1642, 1652, 1655, 1661, 1666, 1667, 1669, 1670, 1683, 1683, 1699, 1699, 1704, 1709, 1709, 1710, 1711, 1715, 1716, 1718, 1723, 1725, 1725, 1726, 1729, 1730, 1741, 1748, 1753, 1754, 1755, 1757, 1764, 1766, 1770, 1773, 1780, 1783, 1786, 1786, 1790, 1796, 1800, 1801, 1812, 1812, 1812, 1812, 1824, 1824, 1825, 1829, 1838, 1838, 1846, 1847, 1851, 1857, 1870, 1877, 1877, 1882, 1883, 1885, 1885, 1886, 1886, 1888, 1889, 1892, 1895, 1900, 1901, 1904, 1909, 1911, 1925, 1925, 1928, 1928, 1939, 1941, 1948, 1949, 1951, 1960, 1975, 1975, 1980, 1985, 1985, 1986, 1987, 1988, 1991, 1996, 1998, 1999, 2001, 2005, 2006, 2008, 2012, 2014, 2016, 2024, 2029, 2031, 2032, 2033, 2047, 2054, 2056, 2062, 2062, 2077, 2083, 2085, 2088, 2088, 2088, 2095, 2099, 2100, 2100, 2100, 2101, 2102, 2104, 2119, 2120, 2125, 2134, 2144, 2145, 2145, 2157, 2159, 2159, 2161, 2161, 2161, 2175, 2176, 2178, 2186, 2191, 2191, 2192, 2196, 2196, 2198, 2198, 2199, 2201, 2203, 2215, 2215, 2217, 2232, 2248, 2248, 2248, 2258, 2260, 2264, 2267, 2272, 2272, 2273, 2274, 2285, 2291, 2292, 2293, 2299, 2302, 2304, 2304, 2305, 2306, 2307, 2311, 2314, 2329, 2344, 2345, 2347, 2354, 2361, 2363, 2364, 2370, 2373, 2375, 2375, 2376, 2377, 2392, 2398, 2398, 2401, 2417, 2418, 2419, 2420, 2422, 2433, 2433, 2434, 2435, 2450, 2463, 2467, 2467, 2469, 2474, 2474, 2476, 2476, 2482, 2488, 2490, 2491, 2491, 2504, 2505, 2511, 2523, 2537, 2546, 2547, 2547, 2548, 2548, 2550, 2561, 2562, 2562, 2563, 2566, 2566, 2582, 2589, 2589, 2594, 2595, 2596, 2604, 2605, 2613, 2619, 2619, 2634, 2651, 2651, 2660, 2663, 2665, 2666, 2674, 2675, 2675, 2675, 2679, 2691, 2697, 2704, 2710, 2718, 2720, 2722, 2722, 2724, 2726, 2732, 2741, 2745, 2747, 2747, 2748, 2763, 2764, 2766, 2778, 2794, 2796, 2804, 2812, 2821, 2825, 2833, 2834, 2835, 2835, 2837, 2838, 2838, 2842, 2851, 2854, 2855, 2860, 2861, 2865, 2869, 2876, 2891, 2892, 2909, 2924, 2925, 2931, 2936, 2938, 2941, 2947, 2948, 2950, 2963, 2964, 2966, 2967, 2969, 2972, 2980, 2982, 2983, 2990, 2995, 3004, 3018, 3028, 3037, 3037, 3049, 3051, 3054, 3061, 3061, 3072, 3076, 3085, 3087, 3091, 3093, 3094, 3095, 3097, 3097, 3111, 3115, 3127, 3147, 3148, 3150, 3153, 3164, 3168, 3174, 3186, 3190, 3200, 3200, 3204, 3207, 3214, 3214, 3225, 3225, 3226, 3244, 3256, 3258, 3261, 3261, 3275, 3281, 3296, 3299, 3301, 3303, 3313, 3328, 3329, 3332, 3343, 3345, 3353, 3353, 3354, 3370, 3372, 3374, 3384, 3386, 3390, 3412, 3414, 3425, 3428, 3429, 3443, 3445, 3456, 3466, 3471, 3473, 3474, 3481, 3482, 3499, 3503, 3515, 3528, 3540, 3542, 3542, 3543, 3553, 3556, 3571, 3574, 3585, 3586, 3594, 3602, 3602, 3611, 3612, 3641, 3643, 3643, 3657, 3659, 3666, 3668, 3670, 3671, 3681, 3700, 3714, 3715, 3725, 3730, 3737, 3740, 3770, 3771, 3771, 3772, 3772, 3785, 3787, 3794, 3799, 3811, 3812, 3834, 3843, 3853, 3865, 3868, 3874, 3886, 3900, 3900, 3900, 3901, 3914, 3924, 3925, 3933, 3940, 3957, 3962, 3971, 3981, 3985, 3989, 3996, 4011, 4013, 4029, 4053, 4061, 4064, 4068, 4070, 4084, 4086, 4088, 4093, 4100, 4124, 4124, 4142, 4171, 4181, 4185, 4192, 4192, 4192, 4195, 4199, 4199, 4203, 4221, 4252, 4284, 4286, 4292, 4298, 4300, 4305, 4320, 4320, 4323, 4349, 4355, 4362, 4385, 4391, 4399, 4413, 4433, 4448, 4451, 4451, 4452, 4470, 4490, 4498, 4500, 4522, 4551, 4564, 4567, 4569, 4579, 4579, 4613, 4621, 4650, 4666, 4666, 4682, 4684, 4692, 4707, 4749, 4763, 4765, 4781, 4797, 4810, 4820, 4820, 4877, 4880, 4896, 4923, 4925, 4980, 5011, 5024, 5038, 5095, 5137, 5139, 5194, 5252, 5309)
        val n = 5

        val result = trimLeaderboard(peptides, spectrum, n)

//       println(result.joinToString(separator = " "))

//        val expectedResult = "WASIGAIMRSAKDMYESLEFHKTHCTYFVYMVCKEARPGWTFFIEWV YYGYRQCSWCQRWTVRRMLCWIDVLHKALHWHVCLLFHQALYGFSHE WDTDTFFQKAMLKKDETADQIFNLRPYSLTCHNENILGNDNQEKQAG YDSPTTYLSTHCHRLTNRMVHENPVICPPQDFAKYLIQSGWEFPLVA MAPRDIRMYFDKYHETAALDSQWIIQQIYHLMNVRKLNRTNRFTSVG FEKYHQQQILIDAQRVRLVHTVARAGPGWVQTGGWQQTCPRYKPYAW".split(" ").sorted()
//        assertEquals(expectedResult, result.sorted())
    }*/

}