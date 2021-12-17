@file:Suppress("UNUSED_VARIABLE", "unused", "UnnecessaryVariable")

import org.junit.jupiter.api.*
import algorithms.*
import java.lang.StringBuilder
import kotlin.test.assertEquals

internal class UtilTestsSpectralConvolution {

    /**
     * Spectral Convolution Problem: Compute the convolution of a spectrum.

    Input: A collection of integers Spectrum.
    Output: The list of elements in the convolution of Spectrum.
    If an element has multiplicity k, it should appear exactly k times;
    you may return the elements in any order.
     */

    @Test
    @DisplayName("test Spectral Convolution 01")
    fun testSpectralConvolution01() {
        val spectrum = listOf(0, 137, 186, 323)

        val result = spectralConvolution(spectrum)
//       println(result)
        
        val expectedResult = "49 323 137 137 186 186 "

        assertEquals(expectedResult, formatMap(result))


    }

    /**
     * extra dataset
     * @link: https://bioinformaticsalgorithms.com/data/extradatasets/antibiotics/spectral_convolution.txt
     */
/*
    @Test
    @DisplayName("test Spectral Convolution 02")
    fun testSpectralConvolution02() {
        val spectrum = listOf(465, 473, 998, 257, 0, 385, 664, 707, 147, 929, 87, 450, 748, 938, 998, 768, 234, 722, 851, 113, 700, 957, 265, 284, 250, 137, 317, 801, 128, 820, 321, 612, 956, 434, 534, 621, 651, 129, 421, 337, 216, 699, 347, 101, 464, 601, 87, 563, 738, 635, 386, 972, 620, 851, 948, 200, 156, 571, 551, 522, 828, 984, 514, 378, 363, 484, 855, 869, 835, 234, 1085, 764, 230, 885)

        val result = spectralConvolution(spectrum)
//       println(result)

//       println(formatMap(result))
    }
*/

    /**
     * quiz
     * @link https://stepik.org/lesson/240284/step/4?unit=212630
     * @link http://rosalind.info/problems/ba4h/
     */
/*    @Test
    @DisplayName("test Spectral Convolution 03")
    fun testSpectralConvolution03() {
        val spectrum = listOf(729, 590, 929, 320, 113, 1614, 1106, 1222, 1117, 470, 2067, 1486, 1218, 1903, 1819, 1696, 1891, 186, 333, 1220, 1760, 2037, 1032, 446, 1821, 1977, 598, 1593, 103, 599, 1003, 876, 931, 1294, 1303, 2037, 186, 427, 609, 2122, 496, 1494, 450, 101, 314, 1511, 1161, 1957, 163, 1385, 1119, 764, 1062, 287, 1657, 1104, 826, 877, 1587, 1459, 745, 1404, 505, 2037, 1346, 1383, 2109, 435, 1913, 1036, 1843, 1727, 2223, 1172, 1459, 1063, 1936, 601, 463, 1723, 2108, 892, 1936, 530, 633, 1649, 2110, 764, 291, 1373, 128, 1044, 1874, 933, 415, 716, 578, 217, 1810, 888, 1048, 1347, 2120, 1501, 1166, 722, 1532, 1532, 877, 2095, 310, 1909, 1466, 217, 1051, 1001, 712, 1750, 1218, 349, 838, 902, 1407, 1773, 404, 1622, 1753, 332, 840, 1179, 1890, 737, 1160, 0, 885, 505, 1191, 1777, 1005, 473, 1718, 816, 1005, 1622, 2060, 1436, 1939, 1808, 186, 1645, 1187, 266, 1740, 819, 1290, 1718, 1604, 2092, 636, 2076, 713, 1796, 218, 1486, 566, 601, 1633, 574, 1175, 163, 380, 413, 1057, 2095, 1624, 299, 1269, 920, 2005, 1567, 1335, 2120, 392, 1397, 1507, 1323, 1210, 241, 1199, 527, 1924, 900, 691, 103, 147, 954, 1851, 2060, 737, 115, 1220, 246, 128, 1982, 1024, 1693, 114, 1478, 1013, 619, 787, 483, 1625, 2006, 402, 1331, 500, 630, 1338, 656, 1292, 691, 850, 131, 1510, 1321, 2006, 1590, 1959, 1831, 1932, 1346, 372, 264, 747, 156, 1788, 1476, 757, 287, 1003, 284)

        val result = spectralConvolution(spectrum)
        //println(result)

//       println(formatMap(result))
    }*/
    
    private fun formatMap(map: Map<Int, Int>): String {
        val str = StringBuilder()
        for (key in map.keys) {
            for (m in 0 until map[key]!!) {
                str.append(key)
                str.append(" ")
            }
        }
        return str.toString()
    }

    /**
     * ConvolutionCyclopeptideSequencing
     * We now have the outline for a new cyclopeptide sequencing algorithm.
     * Given an experimental spectrum, we first compute the convolution of an
     * experimental spectrum. We then select the M most frequent elements
     * between 57 and 200 in the convolution to form an extended alphabet of
     * candidate amino acid masses. In order to be fair, we should include the top M
     * elements of the convolution "with ties". Finally, we run the algorithm
     * LeaderboardCyclopeptideSequencing, where the amino acid masses are
     * restricted to this alphabet. We call this algorithm ConvolutionCyclopeptideSequencing.
     */

    @Test
    @DisplayName("test ConvolutionCyclopeptideSequencing 01")
    fun testConvolutionCyclopeptideSequencing01() {
        val spectrum = listOf(57, 57, 71, 99, 129, 137, 170, 186, 194, 208, 228, 265, 285, 299, 307, 323, 356, 364, 394, 422, 493)

        val l = LeaderboardCyclopeptideSequencing()
        val result = convolutionCyclopeptideSequencing(l, 20, 60, spectrum)
//       println(result)
    }

    @Test
    @DisplayName("test ConvolutionCyclopeptideSequencing ExtraDataset 01")
    fun testConvolutionCyclopeptideSequencingExtraDataset01() {
        val loader = Foo()
        val spectrum = loader.getResourceAsList("S04c09p07ConvolutionCyclopeptideExtra.txt")

        val l = LeaderboardCyclopeptideSequencing()
        val result = convolutionCyclopeptideSequencing(l, 17, 366, spectrum)
//       println(result.joinToString("-"))

        // Note: result expected is:
        // 113-115-114-128-97-163-131-129-129-147-57-57-129
        // answer from this algorithm is rotated:

        val expectedResult = listOf(129, 129, 147, 57, 57, 129, 113, 115, 114, 128, 97, 163, 131)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("test ConvolutionCyclopeptideSequencing Quiz 01")
    fun testConvolutionCyclopeptideSequencingQuizDataset01() {
        val loader = Foo()
        val spectrum = loader.getResourceAsList("S04c09p07ConvolutionCyclopeptideQuiz.txt")

        //val result = convolutionCyclopeptideSequencing(20, 373, spectrum) stepik
        val l = LeaderboardCyclopeptideSequencing()
        val result = convolutionCyclopeptideSequencing(l, 18, 325, spectrum)
//       println(result.joinToString("-"))

        // note: worked!!   passed both stepik and rosalind quizzes.
    }

    /**
     * link: https://stepik.org/lesson/240284/step/8
     * Exercise Break: Run ConvolutionCyclopeptideSequencing on Spectrum25
     * with N = 1000 and M = 20. Identify the 86 highest-scoring peptides
     * using the cyclic scoring function. (Return the peptides in integer
     * format separated by a single space, e.g., 123-57-200-143 199-143-121-60)
     *
     * ** Note that the spectrum that seems to **WORK** is sourced from the
     * earlier problem:
     * @link https://stepik.org/lesson/240282/step/10?unit=212628
     *
     */

    @Test
    @DisplayName("test ConvolutionCyclopeptideSequencing Spectrum25 01")
    fun testConvolutionCyclopeptideSequencingSpectrum25() {

        val spectrum25 = listOf(
            0, 97, 99, 113, 114, 115, 128, 128, 147, 147, 163, 186,
            227, 241, 242, 244, 244, 256, 260, 261, 262, 283, 291, 309,
            330, 333, 340, 347, 385, 388, 389, 390, 390, 405, 435,
            447, 485, 487, 503, 504, 518, 544, 552, 575,
            577, 584, 599, 608, 631, 632, 650, 651, 653, 672, 690,
            691, 717, 738, 745, 770, 779, 804, 818, 819, 827,
            835, 837, 875, 892, 892, 917, 932, 932, 933, 934, 965, 982,
            989, 1039, 1060, 1062, 1078, 1080, 1081, 1095, 1136, 1159,
            1175, 1175, 1194, 1194, 1208, 1209, 1223, 1322
        )
        
        val spectrum25perQuiz = listOf(
            0, 97, 99, 113, 114, 115, 128, 128, 147, 147, 163, 186,
            227, 241, 242, 244, 244, 256, 260, 261, 262, 283, 291, 309,
            330, 333, 340, 347, 385, 388, 389, 390, 390, 405, 435,
            447, 485, 487, 503, 504, 518, 544, 552, 575,
            577, 584, 599, 608, 631, 632, 650, 651, 653, 672, 690,
            691, 717, 738, 745, 770, 779, 804, 818, 819, 827,
            835, 837, 875, 892, 892, 917, 932, 932, 933, 934, 965, 982,
            989, 1039, 1060, 1062, 1078, 1080, 1081, 1095, 1136, 1159,
            1175, 1175, 1194, 1194, 1208, 1209, 1223, 1322)


        val cmp = spectrum25 == spectrum25perQuiz
        val l = LeaderboardCyclopeptideSequencing()
        val result = convolutionCyclopeptideSequencing(
            l,
            topElementsM = 20,
            leaderBoardN = 1000,
            spectrum = spectrum25)


        // note that these values are from wired-in variables that capture the
        //  solutions in question.
        //  An efficient hack but a hack nonetheless.
//       println("max score is ${l.maxScore}")
//       println("Count of 82s is ${l.countOfEightyTwos}")

//       println(l.matchingStrings)

        // for curiosity try the spectrum provided.
        // I get 172 matches to the max score.

        l.maxScore = 0
        l.countOfEightyTwos = 0
        l.matchingStrings = StringBuilder()
        val result2 = convolutionCyclopeptideSequencing(
            l,
            topElementsM = 20,
            leaderBoardN = 1000,
            spectrum = spectrum25perQuiz)
        // note that these values are from wired-in variables that capture the
        //  solutions in question.
        //  An efficient hack but a hack nonetheless.
//       println("max score is ${l.maxScore}")
//       println("Count of 82s is ${l.countOfEightyTwos}")

//       println(l.matchingStrings)
    }



    class Foo {
        fun getResourceAsList(path: String): List<Int> {
            val ress = this.javaClass.getResource(path)
            val retStr = ress!!.readText()
            val list = retStr.split(" ").map { it.toInt() }.toList()
            return list
        }
    }

    private fun doDiffs(list: List<Int>): List<Int> {
        val accum : MutableList<Int> = mutableListOf()

        for (i in list) {
            for (j in list) {
                accum.add(i-j)
            }
        }
        //println(accum.sorted())
        return accum
    }

}