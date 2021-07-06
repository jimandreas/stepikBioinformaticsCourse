@file:Suppress("UNUSED_VARIABLE")

import org.junit.jupiter.api.*
import util.*
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit
import kotlin.math.exp
import kotlin.test.assertEquals

internal class UtilTestsLeaderboardCyclopeptideSequencing {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * test LeaderboardCyclopeptideSequencing
    Given: An integer N and a collection of integers Spectrum.

    Return: LeaderPeptide after running LeaderboardCyclopeptideSequencing(Spectrum, N).
     */

    @Test
    @DisplayName("test LeaderboardCyclopeptideSequencing 01")
    fun testLeaderboardCyclopeptideSequencing01() {
        val trimLevel = 10
        val spectrum = listOf(0, 71, 113, 129, 147, 200, 218, 260, 313, 331, 347, 389, 460)

        val result = leaderboardCyclopeptideSequencing(trimLevel, spectrum)
        println(result)

        val expectedResult = "113-147-71-129"
        val formattedResult = result.joinToString(separator = "-")
        assertEquals(expectedResult, formattedResult)
    }

    /**
     * Extra dataset :
     * @link https://bioinformaticsalgorithms.com/data/extradatasets/antibiotics/leaderboard_cyclopeptide_sequencing.txt
     */
/*
    @Test
    //@Timeout(value = 5, unit = TimeUnit.MINUTES) // note don't need this after fixing cutoff algo
    @DisplayName("test LeaderboardCyclopeptideSequencing 02")
    fun testLeaderboardCyclopeptideSequencing02() {
        val trimLevel = 325
        val spectrum = listOf(0, 71, 71, 71, 87, 97, 97, 99, 101, 103, 113, 113, 114, 115, 128, 128, 129, 137, 147, 163, 163, 170, 184, 184, 186, 186, 190, 211, 215, 226, 226, 229, 231, 238, 241, 244, 246, 257, 257, 276, 277, 278, 299, 300, 312, 316, 317, 318, 318, 323, 328, 340, 343, 344, 347, 349, 356, 366, 370, 373, 374, 391, 401, 414, 414, 415, 419, 427, 427, 431, 437, 441, 446, 453, 462, 462, 462, 470, 472, 502, 503, 503, 511, 515, 529, 530, 533, 533, 540, 543, 547, 556, 559, 569, 574, 575, 584, 590, 600, 600, 604, 612, 616, 617, 630, 640, 640, 643, 646, 648, 660, 671, 683, 684, 687, 693, 703, 703, 719, 719, 719, 729, 730, 731, 737, 740, 741, 745, 747, 754, 774, 780, 784, 790, 797, 800, 806, 818, 826, 827, 832, 833, 838, 846, 846, 847, 850, 868, 869, 877, 884, 889, 893, 897, 903, 908, 913, 917, 930, 940, 947, 956, 960, 960, 961, 964, 965, 966, 983, 983, 985, 1002, 1009, 1010, 1011, 1021, 1031, 1031, 1036, 1053, 1054, 1058, 1059, 1062, 1063, 1074, 1076, 1084, 1092, 1103, 1113, 1122, 1124, 1130, 1133, 1134, 1145, 1146, 1146, 1149, 1150, 1155, 1156, 1171, 1173, 1174, 1187, 1191, 1193, 1200, 1212, 1221, 1233, 1240, 1242, 1246, 1259, 1260, 1262, 1277, 1278, 1283, 1284, 1287, 1287, 1288, 1299, 1300, 1303, 1309, 1311, 1320, 1330, 1341, 1349, 1357, 1359, 1370, 1371, 1374, 1375, 1379, 1380, 1397, 1402, 1402, 1412, 1422, 1423, 1424, 1431, 1448, 1450, 1450, 1467, 1468, 1469, 1472, 1473, 1473, 1477, 1486, 1493, 1503, 1516, 1520, 1525, 1530, 1536, 1540, 1544, 1549, 1556, 1564, 1565, 1583, 1586, 1587, 1587, 1595, 1600, 1601, 1606, 1607, 1615, 1627, 1633, 1636, 1643, 1649, 1653, 1659, 1679, 1686, 1688, 1692, 1693, 1696, 1702, 1703, 1704, 1714, 1714, 1714, 1730, 1730, 1740, 1746, 1749, 1750, 1762, 1773, 1785, 1787, 1790, 1793, 1793, 1803, 1816, 1817, 1821, 1829, 1833, 1833, 1843, 1849, 1858, 1859, 1864, 1877, 1886, 1890, 1893, 1900, 1900, 1903, 1904, 1918, 1922, 1930, 1930, 1931, 1961, 1963, 1971, 1971, 1971, 1980, 1987, 1992, 1996, 2002, 2006, 2006, 2014, 2018, 2019, 2019, 2032, 2042, 2059, 2060, 2063, 2067, 2077, 2084, 2086, 2089, 2090, 2093, 2105, 2110, 2115, 2115, 2116, 2117, 2121, 2133, 2134, 2155, 2156, 2157, 2176, 2176, 2187, 2189, 2192, 2195, 2202, 2204, 2207, 2207, 2218, 2222, 2243, 2247, 2247, 2249, 2249, 2263, 2270, 2270, 2286, 2296, 2304, 2305, 2305, 2318, 2319, 2320, 2320, 2330, 2332, 2334, 2336, 2336, 2346, 2362, 2362, 2362, 2433)

        val result = leaderboardCyclopeptideSequencing(trimLevel, spectrum)
        println(result)

        val expectedResult = "97-129-97-147-99-71-186-71-113-163-115-71-113-128-103-87-128-101-137-163-114"
    }
*/

    /**
     Quiz
     */
    @Test

    @DisplayName("test LeaderboardCyclopeptideSequencing 03")
    fun testLeaderboardCyclopeptideSequencing03() {
        val trimLevel = 2000
        val spectrum = listOf(
            0, 97, 99, 114, 147, 163, 186, 227, 241, 242, 244, 260, 261, 283, 291, 333, 340, 357, 389, 405, 430, 447, 485, 487, 543, 544, 552, 575, 577, 584, 671, 672, 690, 691, 738, 770, 804, 818, 819, 835, 917, 932, 982, 1031, 1060, 1095, 1159, 1223, 1322
        )
        val result = leaderboardCyclopeptideSequencing(trimLevel, spectrum)
        val str = StringBuilder()
        for (i in result) {
            str.append(aminoAcidToDaltonHashMap.filter {
                i == it.value
            }.keys)
        }
        println(str.toString())
        println("MassSum is ${result.sum()}")
        println(result.joinToString("-"))


    }


    /**
     * @link https://stepik.org/lesson/240282/step/10?unit=212628
     * Exercise Break: Run LeaderboardCyclopeptideSequencing on Spectrum25 with N = 1000.
     */
    @Test
    @DisplayName("test LeaderboardCyclopeptideSequencing 04")
    fun testLeaderboardCyclopeptideSequencing04() {
        val trimLevel = 1000
        val spectrum = listOf(
            0, 97, 99, 113, 114, 115, 128, 128, 147, 147, 163, 186, 227, 241, 242, 244, 244, 256, 260, 261, 262, 283, 291, 309, 330, 333, 340, 347, 385, 388, 389, 390, 390, 405, 435, 447, 485, 487, 503, 504, 518, 544, 552, 575, 577, 584, 599, 608, 631, 632, 650, 651, 653, 672, 690, 691, 717, 738, 745, 770, 779, 804, 818, 819, 827, 835, 837, 875, 892, 892, 917, 932, 932, 933, 934, 965, 982, 989, 1039, 1060, 1062, 1078, 1080, 1081, 1095, 1136, 1159, 1175, 1175, 1194, 1194, 1208, 1209, 1223, 1322
        )
        val result = leaderboardCyclopeptideSequencing(trimLevel, spectrum)
        val str = StringBuilder()
        for (i in result) {
            str.append(aminoAcidToDaltonHashMap.filter {
                i == it.value
            }.keys)
        }
        println(str.toString())
        println("MassSum is ${result.sum()}")
        println(result.joinToString("-"))

        println(countOfEightyThrees)
        val sizes = matchingLists.map { it.size }
        println("sizes are $sizes")
        println(matchingStrings)


    }

    /**
     *
     * Spectrum10 :
     * @link https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-4
     * @link http://bioinformaticsalgorithms.com/data/realdatasets/Antibiotics/Tyrocidine_B1_Spectrum_10.txt
     */

    @Test
    @DisplayName("test LeaderboardCyclopeptideSequencing Extended Amino Acid Alphabet")
    fun testLeaderboardCyclopeptideSequencingExtendedAminoAlphabet() {
        val trimLevel = 1000
        // see links above for SPECTRUM10
        val spectrum10 = listOf(0, 97, 99, 114, 128, 147, 147, 163, 186, 227, 241, 242, 244, 260, 261, 262, 283, 291, 333, 340, 357, 385, 389, 390, 390, 405, 430, 430, 447, 485, 487, 503, 504, 518, 543, 544, 552, 575, 577, 584, 632, 650, 651, 671, 672, 690, 691, 738, 745, 747, 770, 778, 779, 804, 818, 819, 820, 835, 837, 875, 892, 917, 932, 932, 933, 934, 965, 982, 989, 1030, 1039, 1060, 1061, 1062, 1078, 1080, 1081, 1095, 1136, 1159, 1175, 1175, 1194, 1194, 1208, 1209, 1223, 1225, 1322)
        val result = leaderboardCyclopeptideSequencing(trimLevel, spectrum)
        val str = StringBuilder()
        for (i in result) {
            str.append(aminoAcidToDaltonHashMap.filter {
                i == it.value
            }.keys)
        }
        println(str.toString())
        println("MassSum is ${result.sum()}")
        println(result.joinToString("-"))

        println(countOfEightyThrees)
        val sizes = matchingLists.map { it.size }
        println("sizes are $sizes")
        println(matchingStrings)


    }



}