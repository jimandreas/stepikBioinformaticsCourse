@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused")

import algorithms.TwoBreakGenomesToBreakpointGraph
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 *

From Genomes to the Breakpoint Graph

Various functions that handle pieces of the breakpoint graph processing.

 * See also:
 * @link:
 *
 * rosalind:
 * @link: http://rosalind.info/problems/ba6f/ 6g, 6h, 6i
 *
 */

internal class S06c12GenomeToBreakpointGraphTest {

    lateinit var twoBreakFunctions: TwoBreakGenomesToBreakpointGraph

    @BeforeEach
    fun setUp() {
        twoBreakFunctions = TwoBreakGenomesToBreakpointGraph()
    }

    /**
     * ChromosomeToCycle 1:
     * Basic test of "Chromosome" (list of +/- digits) to pairs encoding
     * the order of breaks.
     *
     *  Sample Input:

    (+1 -2 -3 +4)

    Sample Output:

    (1 2 4 3 6 5 7 8)
     */
    @Test
    @DisplayName("chromosome To Cycle Test 01")
    fun chromosomeToCycleTest01() {

        val cString = """
            +1 -2 -3 +4
        """.trimIndent()
        val chromosome = cString.split(' ').map { it.toInt() }

        val resultString = """
            1 2 4 3 6 5 7 8
        """.trimIndent()
        val expectedResult = resultString.split(' ').map { it.toInt() }

        val result = twoBreakFunctions.chromosomeToCycle(chromosome)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("chromosome To Cycle Test 02")
    fun chromosomeToCycleTest02() {

        val cString = """
            +1 +2 -3 -4 +5 -6 +7 +8 -9 +10 -11 -12 +13 -14 +15 -16 +17 +18 +19 -20 +21 -22 -23 -24 +25 +26 +27 -28 +29 -30 -31 +32 +33 +34 -35 +36 +37 +38 -39 +40 -41 -42 +43 -44 -45 -46 -47 +48 +49 -50 +51 -52 -53 -54 +55 +56 +57 -58 -59 -60 +61 +62 +63 -64 +65 +66 +67 -68 -69
        """.trimIndent()
        val chromosome = cString.split(' ').map { it.toInt() }

        val resultString = """
            1 2 3 4 6 5 8 7 9 10 12 11 13 14 15 16 18 17 19 20 22 21 24 23 25 26 28 27 29 30 32 31 33 34 35 36 37 38 40 39 41 42 44 43 46 45 48 47 49 50 51 52 53 54 56 55 57 58 60 59 62 61 63 64 65 66 67 68 70 69 71 72 73 74 75 76 78 77 79 80 82 81 84 83 85 86 88 87 90 89 92 91 94 93 95 96 97 98 100 99 101 102 104 103 106 105 108 107 109 110 111 112 113 114 116 115 118 117 120 119 121 122 123 124 125 126 128 127 129 130 131 132 133 134 136 135 138 137
        """.trimIndent()
        val expectedResult = resultString.split(' ').map { it.toInt() }

        val result = twoBreakFunctions.chromosomeToCycle(chromosome)
        assertEquals(expectedResult, result)
    }

    /**
     * Code Challenge: Implement CycleToChromosome.

    Input: A sequence Nodes of integers between 1 and 2n.
    Output: The chromosome "Chromosome" containing n synteny blocks
    resulting from applying CycleToChromosome to Nodes.

    Sample Input:

    (1 2 4 3 6 5 7 8)

    Sample Output:

    (+1 -2 -3 +4)

     */

    @Test
    @DisplayName("cycle to chromosome Test 01")
    fun cycleToChromosomeTest01() {

        val cString = """
            1 2 4 3 6 5 7 8
        """.trimIndent()
        val cycle = cString.split(' ').map { it.toInt() }

        val resultString = """
            +1 -2 -3 +4
        """.trimIndent()
        val expectedResult = resultString.split(' ').map { it.toInt() }

        val result = twoBreakFunctions.cycleToChromosome(cycle)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("cycle to chromosome Test 02")
    fun cycleToChromosomeTest02() {

        val cString = """
            1 2 3 4 5 6 8 7 9 10 11 12 14 13 15 16 17 18 19 20 21 22 23 24 26 25 27 28 29 30 31 32 33 34 36 35 37 38 39 40 41 42 43 44 45 46 48 47 49 50 52 51 54 53 56 55 57 58 60 59 61 62 63 64 65 66 68 67 70 69 72 71 73 74 76 75 77 78 79 80 81 82 83 84 85 86 87 88 89 90 92 91 93 94 96 95 97 98 100 99 102 101 103 104 106 105 108 107 109 110 111 112 113 114 115 116 118 117 120 119 122 121 123 124 125 126 128 127 130 129 132 131 133 134 136 135 137 138 140 139
        """.trimIndent()
        val cycle = cString.split(' ').map { it.toInt() }

        val resultString = """
            +1 +2 +3 -4 +5 +6 -7 +8 +9 +10 +11 +12 -13 +14 +15 +16 +17 -18 +19 +20 +21 +22 +23 -24 +25 -26 -27 -28 +29 -30 +31 +32 +33 -34 -35 -36 +37 -38 +39 +40 +41 +42 +43 +44 +45 -46 +47 -48 +49 -50 -51 +52 -53 -54 +55 +56 +57 +58 -59 -60 -61 +62 +63 -64 -65 -66 +67 -68 +69 -70
        """.trimIndent()
        val expectedResult = resultString.split(' ').map { it.toInt() }

        val result = twoBreakFunctions.cycleToChromosome(cycle)
        assertEquals(expectedResult, result)
    }

    @Test
    @DisplayName("cycle to chromosome Test 03")
    fun cycleToChromosomeTest03() {

        val cString = """
            1 2 3 4 6 5 8 7 9 10 12 11 14 13 16 15 17 18 20 19 22 21 23 24 26 25 28 27 29 30 32 31 34 33 36 35 37 38 39 40 41 42 43 44 46 45 47 48 49 50 51 52 53 54 56 55 57 58 60 59 62 61 63 64 66 65 67 68 70 69 72 71 73 74 75 76 78 77 80 79 82 81 84 83 85 86 87 88 89 90 91 92 94 93 96 95 98 97 100 99 102 101 103 104 105 106 107 108 109 110 112 111 113 114 115 116 118 117 120 119 121 122 124 123 126 125 128 127 129 130 132 131 134 133 135 136 138 137
        """.trimIndent()
        val cycle = cString.split(' ').map { it.toInt() }


        val result = twoBreakFunctions.cycleToChromosome(cycle).joinToString(" ") { String.format("%+d", it) }
//       println("($result)")
    }


    /**
     * Code Challenge: Implement ColoredEdges.

    Input: A genome P.
    Output: The collection of colored edges in the genome graph of P in the form (x, y).

    Sample Input:

    (+1 -2 -3)(+4 +5 -6)

    Sample Output:

    (2, 4), (3, 6), (5, 1), (8, 9), (10, 12), (11, 7)
     */

    @Test
    @DisplayName("colored Edges Test 01")
    fun coloredEdgesTest01() {

        val chromosomeGivenString = """
            (+1 -2 -3)(+4 +5 -6)
        """.trimIndent()
        val expectedResultString = """
            (2, 4), (3, 6), (5, 1), (8, 9), (10, 12), (11, 7)
        """.trimIndent()
        dotestColoredEdges(chromosomeGivenString, expectedResultString)
    }

    @Test
    @DisplayName("colored Edges Extradataset Test 02")
    fun coloredEdgesTest02() {

        val chromosomeGivenString = """
            (+1 -2 -3 +4 -5 -6 -7 -8 +9 -10 -11 -12 +13 +14 +15 -16 -17 +18 +19 +20 +21)(-22 -23 -24 -25 -26 -27 -28 +29 +30 +31 -32 -33 +34 +35 -36 +37 +38 +39 -40 +41 +42 +43 +44)(+45 +46 +47 +48 -49 +50 -51 +52 -53 +54 +55 -56 +57 -58 +59 -60 -61 -62 -63 -64 +65 -66)(-67 +68 +69 +70 -71 -72 +73 +74 -75 +76 -77 -78 +79 +80 +81 +82 -83 +84 +85 +86 -87 -88 -89 +90)(-91 -92 +93 +94 -95 -96 +97 +98 -99 -100 -101 -102 +103 +104 -105 +106 -107 -108 +109 +110 +111 -112 +113 +114 -115 +116 +117 -118 +119)(-120 +121 +122 -123 -124 -125 +126 +127 +128 +129 -130 +131 -132 +133 -134 -135 -136 -137 +138 -139 +140 +141 +142 +143 -144 -145 +146 -147 +148)(-149 -150 +151 -152 -153 -154 -155 -156 +157 +158 +159 +160 -161 +162 +163 +164 +165 -166 +167 -168 -169 +170 -171 +172)
        """.trimIndent()
        val expectedResultString = """
            (2, 4), (3, 6), (5, 7), (8, 10), (9, 12), (11, 14), (13, 16), (15, 17), (18, 20), (19, 22), (21, 24), (23, 25), (26, 27), (28, 29), (30, 32), (31, 34), (33, 35), (36, 37), (38, 39), (40, 41), (42, 1), (43, 46), (45, 48), (47, 50), (49, 52), (51, 54), (53, 56), (55, 57), (58, 59), (60, 61), (62, 64), (63, 66), (65, 67), (68, 69), (70, 72), (71, 73), (74, 75), (76, 77), (78, 80), (79, 81), (82, 83), (84, 85), (86, 87), (88, 44), (90, 91), (92, 93), (94, 95), (96, 98), (97, 99), (100, 102), (101, 103), (104, 106), (105, 107), (108, 109), (110, 112), (111, 113), (114, 116), (115, 117), (118, 120), (119, 122), (121, 124), (123, 126), (125, 128), (127, 129), (130, 132), (131, 89), (133, 135), (136, 137), (138, 139), (140, 142), (141, 144), (143, 145), (146, 147), (148, 150), (149, 151), (152, 154), (153, 156), (155, 157), (158, 159), (160, 161), (162, 163), (164, 166), (165, 167), (168, 169), (170, 171), (172, 174), (173, 176), (175, 178), (177, 179), (180, 134), (181, 184), (183, 185), (186, 187), (188, 190), (189, 192), (191, 193), (194, 195), (196, 198), (197, 200), (199, 202), (201, 204), (203, 205), (206, 207), (208, 210), (209, 211), (212, 214), (213, 216), (215, 217), (218, 219), (220, 221), (222, 224), (223, 225), (226, 227), (228, 230), (229, 231), (232, 233), (234, 236), (235, 237), (238, 182), (239, 241), (242, 243), (244, 246), (245, 248), (247, 250), (249, 251), (252, 253), (254, 255), (256, 257), (258, 260), (259, 261), (262, 264), (263, 265), (266, 268), (267, 270), (269, 272), (271, 274), (273, 275), (276, 278), (277, 279), (280, 281), (282, 283), (284, 285), (286, 288), (287, 290), (289, 291), (292, 294), (293, 295), (296, 240), (297, 300), (299, 301), (302, 304), (303, 306), (305, 308), (307, 310), (309, 312), (311, 313), (314, 315), (316, 317), (318, 319), (320, 322), (321, 323), (324, 325), (326, 327), (328, 329), (330, 332), (331, 333), (334, 336), (335, 338), (337, 339), (340, 342), (341, 343), (344, 298)
        """.trimIndent()
        dotestColoredEdges(chromosomeGivenString, expectedResultString)
    }

    @Test
    @DisplayName("colored Edges Quiz Test 03")
    fun coloredEdgesTest03() {

        val chromosomeGivenString = """
            (-1 +2 -3 -4 +5 -6 -7 -8 -9 +10 -11 -12 +13 +14 +15 +16 -17 +18 +19 +20 -21 +22 +23 +24 +25)(-26 +27 -28 -29 -30 -31 -32 +33 +34 +35 -36 -37 -38 -39 +40 -41 +42 -43 -44 -45 +46 -47)(-48 -49 -50 -51 +52 +53 +54 +55 +56 -57 -58 +59 -60 -61 +62 +63 +64 -65 +66 +67 +68 -69 -70 -71 +72 -73)(+74 +75 -76 +77 +78 -79 +80 +81 -82 +83 +84 +85 -86 -87 -88 -89 +90 +91 +92 +93 -94 +95 -96 +97 +98)(-99 -100 +101 -102 +103 -104 -105 +106 -107 -108 -109 +110 +111 -112 -113 +114 +115 +116 -117 +118 -119 -120 +121 -122)(+123 +124 -125 -126 +127 -128 -129 -130 +131 +132 +133 +134 +135 -136 +137 -138 +139 +140 +141 +142 +143)(+144 +145 -146 -147 -148 -149 -150 -151 -152 +153 +154 +155 -156 +157 +158 +159 +160 -161 -162 -163 -164 -165 -166)(-167 +168 -169 -170 -171 -172 +173 -174 -175 -176 -177 +178 +179 +180 -181 +182 -183 -184 +185 +186 -187)
        """.trimIndent()
        val expectedResultString = """
            (1, 3), (4, 6), (5, 8), (7, 9), (10, 12), (11, 14), (13, 16), (15, 18), (17, 19), (20, 22), (21, 24), (23, 25), (26, 27), (28, 29), (30, 31), (32, 34), (33, 35), (36, 37), (38, 39), (40, 42), (41, 43), (44, 45), (46, 47), (48, 49), (50, 2), (51, 53), (54, 56), (55, 58), (57, 60), (59, 62), (61, 64), (63, 65), (66, 67), (68, 69), (70, 72), (71, 74), (73, 76), (75, 78), (77, 79), (80, 82), (81, 83), (84, 86), (85, 88), (87, 90), (89, 91), (92, 94), (93, 52), (95, 98), (97, 100), (99, 102), (101, 103), (104, 105), (106, 107), (108, 109), (110, 111), (112, 114), (113, 116), (115, 117), (118, 120), (119, 122), (121, 123), (124, 125), (126, 127), (128, 130), (129, 131), (132, 133), (134, 135), (136, 138), (137, 140), (139, 142), (141, 143), (144, 146), (145, 96), (148, 149), (150, 152), (151, 153), (154, 155), (156, 158), (157, 159), (160, 161), (162, 164), (163, 165), (166, 167), (168, 169), (170, 172), (171, 174), (173, 176), (175, 178), (177, 179), (180, 181), (182, 183), (184, 185), (186, 188), (187, 189), (190, 192), (191, 193), (194, 195), (196, 147), (197, 200), (199, 201), (202, 204), (203, 205), (206, 208), (207, 210), (209, 211), (212, 214), (213, 216), (215, 218), (217, 219), (220, 221), (222, 224), (223, 226), (225, 227), (228, 229), (230, 231), (232, 234), (233, 235), (236, 238), (237, 240), (239, 241), (242, 244), (243, 198), (246, 247), (248, 250), (249, 252), (251, 253), (254, 256), (255, 258), (257, 260), (259, 261), (262, 263), (264, 265), (266, 267), (268, 269), (270, 272), (271, 273), (274, 276), (275, 277), (278, 279), (280, 281), (282, 283), (284, 285), (286, 245), (288, 289), (290, 292), (291, 294), (293, 296), (295, 298), (297, 300), (299, 302), (301, 304), (303, 305), (306, 307), (308, 309), (310, 312), (311, 313), (314, 315), (316, 317), (318, 319), (320, 322), (321, 324), (323, 326), (325, 328), (327, 330), (329, 332), (331, 287), (333, 335), (336, 338), (337, 340), (339, 342), (341, 344), (343, 345), (346, 348), (347, 350), (349, 352), (351, 354), (353, 355), (356, 357), (358, 359), (360, 362), (361, 363), (364, 366), (365, 368), (367, 369), (370, 371), (372, 374), (373, 334)
        """.trimIndent()
        dotestColoredEdges(chromosomeGivenString, expectedResultString)
    }


    fun dotestColoredEdges(chromosomeGivenString: String, expectedResultString: String) {
        val expectedListOfPairs = expectedResultString.parsePairs()


        val listOfLists: MutableList<List<Int>> = mutableListOf()
        val cycle = chromosomeGivenString.split("(", ")(", ")")
        for (i in 1 until cycle.size - 1) {
            val tempList = cycle[i].split(" ").map { it.toInt() }
            listOfLists.add(tempList)
        }

        val resultList = twoBreakFunctions.coloredEdges(listOfLists)
        val resultPairs = pairTheList(resultList)

        //println(resultPairs)
        assertContentEquals(expectedListOfPairs, resultPairs)
    }

    /**
     * Code Challenge: Implement GraphToGenome.

    Input: The colored edges ColoredEdges of a genome graph.
    Output: The genome P corresponding to this genome graph.

    Extra Dataset

    Sample Input:

    (2, 4), (3, 6), (5, 1), (7, 9), (10, 12), (11, 8)

    Sample Output:

    (+1 -2 -3)(-4 +5 -6)

    NOTES: the graph is passed in as a list rather than a series of pairs
     */

    @Test
    @DisplayName("graph to Genome Test 01")
    fun graphToGenomeTest01() {

        val edgesGivenString = """
            (2, 4), (3, 6), (5, 1), (7, 9), (10, 12), (11, 8)
        """.trimIndent()
        val expectedResultString = """
            (+1 -2 -3)(-4 +5 -6)
        """.trimIndent()
        dotestGraphToGenome(edgesGivenString, expectedResultString)
    }

    @Test
    @DisplayName("graph to Genome Extradataset Test 02")
    fun graphToGenomeExtradatasetTest02() {

        val edgesGivenString = """
            (1, 4), (3, 6), (5, 7), (8, 9), (10, 11), (12, 14), (13, 15), (16, 18), (17, 19), (20, 22), (21, 24), (23, 26), (25, 27), (28, 29), (30, 32), (31, 33), (34, 36), (35, 38), (37, 39), (40, 42), (41, 44), (43, 2), (45, 48), (47, 50), (49, 51), (52, 54), (53, 56), (55, 57), (58, 60), (59, 62), (61, 63), (64, 66), (65, 68), (67, 69), (70, 72), (71, 74), (73, 76), (75, 78), (77, 79), (80, 81), (82, 84), (83, 86), (85, 87), (88, 90), (89, 91), (92, 94), (93, 46), (96, 98), (97, 99), (100, 102), (101, 103), (104, 106), (105, 107), (108, 109), (110, 111), (112, 114), (113, 115), (116, 117), (118, 119), (120, 122), (121, 123), (124, 125), (126, 127), (128, 130), (129, 131), (132, 134), (133, 135), (136, 138), (137, 139), (140, 141), (142, 144), (143, 145), (146, 147), (148, 149), (150, 152), (151, 95), (154, 155), (156, 158), (157, 159), (160, 161), (162, 163), (164, 166), (165, 168), (167, 169), (170, 171), (172, 174), (173, 175), (176, 178), (177, 180), (179, 182), (181, 184), (183, 186), (185, 187), (188, 190), (189, 191), (192, 194), (193, 196), (195, 153), (198, 200), (199, 201), (202, 204), (203, 205), (206, 207), (208, 210), (209, 211), (212, 214), (213, 215), (216, 217), (218, 219), (220, 222), (221, 223), (224, 226), (225, 227), (228, 230), (229, 232), (231, 234), (233, 235), (236, 238), (237, 239), (240, 241), (242, 244), (243, 245), (246, 197), (248, 250), (249, 252), (251, 253), (254, 255), (256, 258), (257, 260), (259, 262), (261, 263), (264, 265), (266, 267), (268, 270), (269, 272), (271, 273), (274, 276), (275, 278), (277, 280), (279, 281), (282, 283), (284, 286), (285, 247), (288, 290), (289, 292), (291, 293), (294, 295), (296, 297), (298, 299), (300, 302), (301, 303), (304, 306), (305, 307), (308, 310), (309, 312), (311, 314), (313, 316), (315, 318), (317, 319), (320, 322), (321, 323), (324, 325), (326, 327), (328, 330), (329, 332), (331, 333), (334, 336), (335, 338), (337, 339), (340, 287)
        """.trimIndent()
        val expectedResultString = """
            (-1 -2 -3 +4 +5 +6 -7 +8 -9 +10 -11 -12 -13 +14 +15 -16 +17 -18 -19 +20 -21 -22)(-23 -24 -25 +26 -27 -28 +29 -30 -31 +32 -33 -34 +35 -36 -37 -38 -39 +40 +41 -42 -43 +44 -45 +46 -47)(+48 -49 +50 -51 +52 -53 +54 +55 +56 -57 +58 +59 +60 -61 +62 +63 +64 -65 +66 -67 +68 -69 +70 +71 -72 +73 +74 +75 -76)(+77 +78 -79 +80 +81 +82 -83 -84 +85 +86 -87 +88 -89 -90 -91 -92 -93 +94 -95 +96 -97 -98)(+99 -100 +101 -102 +103 +104 -105 +106 -107 +108 +109 +110 -111 +112 -113 +114 -115 -116 -117 +118 -119 +120 +121 -122 +123)(+124 -125 -126 +127 +128 -129 -130 -131 +132 +133 +134 -135 -136 +137 -138 -139 -140 +141 +142 -143)(+144 -145 -146 +147 +148 +149 +150 -151 +152 -153 +154 -155 -156 -157 -158 -159 +160 -161 +162 +163 +164 -165 -166 +167 -168 -169 +170)
        """.trimIndent()
        dotestGraphToGenome(edgesGivenString, expectedResultString)
    }

    fun dotestGraphToGenome(edgesGivenString: String, expectedResultString: String) {
        val nodeList = edgesGivenString.parsePairsToList()

        val expectedListOfChromosomes: MutableList<List<Int>> = mutableListOf()
        val cycle = expectedResultString.split("(", ")(", ")")
        for (i in 1 until cycle.size - 1) {
            val tempList = cycle[i].split(" ").map { it.toInt() }
            expectedListOfChromosomes.add(tempList)
        }

        val resultListOfChromosomes = twoBreakFunctions.graphToGenome(nodeList)

        //printChromosomes(resultListOfChromosomes)
        assertContentEquals(expectedListOfChromosomes, resultListOfChromosomes)
    }

    // this is to print out the quiz answer
    fun printChromosomes(listC: List<List<Int>>) {
        val str = StringBuilder()

        for (l in listC) {
            str.append("(")
            str.append(l.joinToString(" ") { String.format("%+d", it) })
            str.append(")")
        }
//       println(str.toString())
    }

    /**
     * Kotlin: parsing a list of pairs delimited by parentheses into a list of Pairs()
     *  A solution kindly provided by @Joffrey via stackoverflow:
     *  @link: https://stackoverflow.com/a/68682137/3853712
     */
    private fun String.parsePairs(): List<Pair<Int, Int>> = removePrefix("(")
        .removeSuffix(")")
        .split("), (")
        .map { it.parsePair() }

    private fun String.parsePair(): Pair<Int, Int> =
        split(", ").let { it[0].toInt() to it[1].toInt() }

    private fun String.parsePairsToList(): List<Int> = removePrefix("(")
        .removeSuffix(")")
        .split("), (", ", ")
        .map { it.toInt() }

    private fun pairTheList(l: List<Int>): List<Pair<Int, Int>> {
        val outList: MutableList<Pair<Int, Int>> = mutableListOf()
        var i = 0
        while (i < l.size) {
            outList.add(Pair(l[i], l[i + 1]))
            i += 2
        }
        return outList
    }


}