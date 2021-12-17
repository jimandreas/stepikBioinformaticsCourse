@file:Suppress("UNUSED_VARIABLE", "UNUSED_VALUE")

import algorithms.cyclopeptideSequencing
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class UtilTestsCyclopeptideSequencing {


    /**
     * Code Challenge: Implement CyclopeptideSequencing
     *
     *  Sample Input:

    0 113 128 186 241 299 314 427

    Sample Output:

    186-128-113 186-113-128 128-186-113 128-113-186 113-186-128 113-128-186
     */
    @Test
    @DisplayName("test CyclopeptideSequencing base case")
    fun testCyclopeptideSequencingBaseCase() {
        val spectrum = listOf(0, 113, 128, 186, 241, 299, 314, 427)

        val results = cyclopeptideSequencing(spectrum)

        val expectedResultUnsorted = "186-128-113 186-113-128 128-186-113 128-113-186 113-186-128 113-128-186"
        val expectedResultSorted = expectedResultUnsorted.split(" ").sorted().joinToString(separator = " ")

        val success = formatCyclopeptideResults(results, expectedResultUnsorted)
    }

    /**
     * dataset from Rosalind:
     * @link https://bioinformaticsalgorithms.com/data/extradatasets/antibiotics/cyclopeptide_sequencing.txt
     */
    @Test
    @DisplayName("test CyclopeptideSequencing extra dataset from Rosalind")
    fun testCyclopeptideSequencingExtraDataset() {
        val spectrum = listOf(
            0, 71, 97, 99, 103, 113, 113, 114, 115, 131, 137, 196, 200, 202, 208, 214, 226, 227, 228, 240, 245, 299, 311, 311, 316, 327, 337, 339, 340, 341, 358, 408, 414, 424, 429, 436, 440, 442, 453, 455, 471, 507, 527, 537, 539, 542, 551, 554, 556, 566, 586, 622, 638, 640, 651, 653, 657, 664, 669, 679, 685, 735, 752, 753, 754, 756, 766, 777, 782, 782, 794, 848, 853, 865, 866, 867, 879, 885, 891, 893, 897, 956, 962, 978, 979, 980, 980, 990, 994, 996, 1022, 1093)

        val results = cyclopeptideSequencing(spectrum)

        val expectedResultUnsorted = "103-137-71-131-114-113-113-115-99-97 103-97-99-115-113-113-114-131-71-137 113-113-114-131-71-137-103-97-99-115 113-113-115-99-97-103-137-71-131-114 113-114-131-71-137-103-97-99-115-113 113-115-99-97-103-137-71-131-114-113 114-113-113-115-99-97-103-137-71-131 114-131-71-137-103-97-99-115-113-113 115-113-113-114-131-71-137-103-97-99 115-99-97-103-137-71-131-114-113-113 131-114-113-113-115-99-97-103-137-71 131-71-137-103-97-99-115-113-113-114 137-103-97-99-115-113-113-114-131-71 137-71-131-114-113-113-115-99-97-103 71-131-114-113-113-115-99-97-103-137 71-137-103-97-99-115-113-113-114-131 97-103-137-71-131-114-113-113-115-99 97-99-115-113-113-114-131-71-137-103 99-115-113-113-114-131-71-137-103-97 99-97-103-137-71-131-114-113-113-115"

        val success = formatCyclopeptideResults(results, expectedResultUnsorted)

    }

    /**
     *test dataset
     */
    @Test
    @DisplayName("test CyclopeptideSequencing test Dataset")
    fun testCyclopeptideSequencingTestDataset() {
        val spectrum = listOf(
            0, 87, 87, 115, 129, 131, 137, 147, 163, 186, 216, 218, 224, 250, 262, 268, 276, 301, 347, 349, 355, 355, 363, 387, 391, 436, 442, 448, 464, 478, 484, 494, 518, 551, 571, 573, 577, 605, 609, 611, 631, 664, 688, 698, 704, 718, 734, 740, 746, 791, 795, 819, 827, 827, 833, 835, 881, 906, 914, 920, 932, 958, 964, 966, 996, 1019, 1035, 1045, 1051, 1053, 1067, 1095, 1095, 1182)

        val results = cyclopeptideSequencing(spectrum)
        
        var resultStrings : MutableList<String> = mutableListOf()
        for (l in results) {
            resultStrings.add(l.joinToString(separator = "-"))
        }
        resultStrings = resultStrings.sorted().distinct().toMutableList()
//       println(resultStrings.joinToString(separator = " "))
    }


    private fun formatCyclopeptideResults(results: List<List<Int>>, expectedResultUnsorted: String): Boolean {
        var resultStrings : MutableList<String> = mutableListOf()

        for (l in results) {
            resultStrings.add(l.joinToString(separator = "-"))
        }
        resultStrings = resultStrings.sorted().distinct().toMutableList()
//       println(resultStrings.joinToString(separator = " "))

        val expectedResult = expectedResultUnsorted.split(" ").sorted()

//       println("counts: result Strings ${resultStrings.size} expected ${expectedResult.size}")

        val iter = resultStrings.iterator().withIndex()
        var passing = true
        while (iter.hasNext()) {
            val i = iter.next()
            val res = resultStrings[i.index]
            val exp = expectedResult[i.index]
            if (res != exp) {
        //       println("oops")
                passing = false
            }

        }
        return passing
    }

}