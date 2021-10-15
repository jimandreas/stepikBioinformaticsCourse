@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.DistancesBetweenLeaves
import algorithms.NeighborJoining
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.text.NumberFormat
import java.util.*
import kotlin.test.assertEquals

/**
 *
 * See also:  Neighbour Joining   Anders Gorm Pedersen
 * Youtube: https://www.youtube.com/watch?v=Dj24mCLQYUE
 *
 * Limb Length:
 * stepik: https://stepik.org/lesson/240340/step/7?unit=212686
 * rosalind: http://rosalind.info/problems/ba7e/
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */


internal class S07c07p07NeighborJoiningTest {

    lateinit var dbl: DistancesBetweenLeaves
    lateinit var nj : NeighborJoining


    @BeforeEach
    fun setUp() {
//        ll = Phylogeny()
        dbl = DistancesBetweenLeaves()
//        upgma = UPGMA()
        nj = NeighborJoining()
    }

    @AfterEach
    fun tearDown() {
    }

    /**
    example from:
    https://www.youtube.com/watch?v=Dj24mCLQYUE
    Neighbour Joining  Anders Gorm Pedersen
    */

    @Test
    @DisplayName("Neighbor Joining sample from youtube test")
    fun neighborJoiningSampleFromYoutubeTest() {
        val sampleInput = """
4
0 17 21 27
17 0 12 18
21 12 0 14
27 18 14 0
        """.trimIndent()

        val expectedOutputString = """
            0->4:13.000
            1->4:4.000
            2->5:4.000
            3->5:10.000
            4->0:13.000
            4->1:4.000
            4->5:4.000
            5->2:4.000
            5->3:10.000
            5->4:4.000
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = nj.neighborJoiningStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)

        // see if the graph is symetric -
        //  in other words if the graph can be turned into an identical distance matrix
        //  This should work for additive matrices.

        val theResultMatrix = dbl.distancesBetweenLeavesFloat(matrixSize, result)
        //printMatrix(matrixSize, theResultMatrix)
        assertEquals(m, theResultMatrix)


    }

    /**
    example from:
    https://www.youtube.com/watch?v=Dj24mCLQYUE
    Neighbour Joining  Anders Gorm Pedersen
     */

    @Test
    @DisplayName("Neighbor Joining sample from wikipedia test")
    fun neighborJoiningSampleFromWikipediaTest() {
        val sampleInput = """
5
0	5	9	9	8
5	0	10	10	9
9	10	0	8	7
9	10	8	0	3
8	9	7	3	0
        """.trimIndent()

        val expectedOutputString = """
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = nj.neighborJoiningStart(matrixSize, m)

        //printGraph(result)

        //checkEdgesAreEqual(expectedGraph, result)

        // see if the graph is symetric -
        //  in other words if the graph can be turned into an identical distance matrix
        //  This should work for additive matrices.

        val theResultMatrix = dbl.distancesBetweenLeavesFloat(matrixSize, result)
        //printMatrix(matrixSize, theResultMatrix)
        assertEquals(m, theResultMatrix)


    }

    // example from:
    // https://stepik.org/lesson/240340/step/7?unit=212686

    @Test
    @DisplayName("Neighbor Joining sample test")
    fun neighborJoiningSampleTest() {
        val sampleInput = """
4
0	23	27	20
23	0	30	28
27	30	0	30
20	28	30	0
        """.trimIndent()

        val expectedOutputString = """
0->4:8.000
1->5:13.500
2->5:16.500
3->4:12.000
4->5:2.000
4->0:8.000
4->3:12.000
5->1:13.500
5->2:16.500
5->4:2.000
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = nj.neighborJoiningStart(matrixSize, m)

        //printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)

        val theResultMatrix = dbl.distancesBetweenLeavesFloat(matrixSize, result)
        //printMatrix(matrixSize, theResultMatrix)

        // This is a NON-ADDITIVE matrix - so this test will fail.
        //assertEquals(m, theResultMatrix)
    }

    /**
     * Exercise Break: Before implementing the neighbor-joining algorithm,
     * try applying it to the additive and non-additive distance matrices shown below.
     */
    // example from:
    // https://stepik.org/lesson/240340/step/6?unit=212686

    @Test
    @DisplayName("Neighbor Joining additive example test")
    fun neighborJoiningAdditiveExampleTest() {
        val sampleInput = """
4
0 13 21 22
13 0 12 13
21 12 0 13
22 13 13 0
        """.trimIndent()

        val expectedOutputString = """
0->4:11.000
1->4:2.000
2->5:6.000
3->5:7.000
4->0:11.000
4->1:2.000
4->5:4.000
5->2:6.000
5->3:7.000
5->4:4.000
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = nj.neighborJoiningStart(matrixSize, m)

//        printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)

        val theResultMatrix = dbl.distancesBetweenLeavesFloat(matrixSize, result)
        //printMatrix(matrixSize, theResultMatrix)

        // This is an ADDITIVE matrix - so this should work
        assertEquals(m, theResultMatrix)
    }

    private fun printGraph(g: Map<Int, Map<Int, Float>>) {

        val fmt = NumberFormat.getNumberInstance(Locale.ROOT)
        fmt.maximumFractionDigits = 3
        fmt.minimumFractionDigits = 3

        for (e in g) {
            val key = e.key
            val theMap = e.value

            for (c in theMap) {
                print("$key->")
                val f = fmt.format(c.value)
                println("${c.key}:$f")
            }
        }
    }


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
    @DisplayName("Neighbor Joining extraDataset test")
    fun neighborJoiningExtraDatasetTest() {
        val sampleInput = """
32
0 1614 1256 1160 1422 1967 1480 1505 1681 1149 1543 1073 1313 1774 1775 1608 1246 1479 1380 1770 1760 1891 1332 1611 1793 1746 1042 1488 1095 1945 1638 1789
1614 0 1778 1722 1574 1747 1626 1961 1874 1627 1162 1424 2019 2026 1707 1378 1501 1560 1616 1413 1520 1593 1978 1992 1269 1589 1518 1104 1335 1367 1748 1886
1256 1778 0 1087 1732 1772 1078 1509 1651 1749 1993 1304 1252 1556 1444 1257 1364 1416 1896 1864 1632 2034 1460 1911 1152 1399 1482 1093 1996 1142 1027 1531
1160 1722 1087 0 1855 1979 1322 1497 1575 1619 1394 2041 1570 1425 1317 1029 1349 1372 1676 1521 1701 1128 1206 1975 1954 1807 1649 1989 1714 1844 1567 1982
1422 1574 1732 1855 0 2018 1155 1702 1595 1339 1667 1835 1645 1517 1703 2008 1236 1633 1650 1321 1250 1587 1581 1987 1311 1391 1264 1935 1464 2024 1882 1843
1967 1747 1772 1979 2018 0 1037 1715 1266 1294 1640 1708 1680 1810 1196 1931 2015 1868 1856 1510 1383 1697 1137 1119 1140 1584 1914 1686 1346 1530 1657 1244
1480 1626 1078 1322 1155 1037 0 1192 1456 1783 1466 1836 1895 1226 1220 1512 1998 1329 1968 1995 1610 1223 1056 1455 1765 1261 1862 1755 1239 1942 1324 1209
1505 1961 1509 1497 1702 1715 1192 0 1360 1472 1069 1358 1947 1971 1447 1240 1865 1348 1502 1163 1293 1924 2032 1873 1180 1946 1114 2035 1255 1147 1763 1195
1681 1874 1651 1575 1595 1266 1456 1360 0 2013 1280 1738 2020 1586 1493 1057 1899 1161 1728 1984 1477 1441 1780 1344 1923 1496 1085 1563 1883 1994 1429 1458
1149 1627 1749 1619 1339 1294 1783 1472 2013 0 1193 2023 2047 1764 1599 1796 1439 1538 1867 1591 1320 1658 1377 1981 1094 1516 1736 1153 1459 1828 1654 1890
1543 1162 1993 1394 1667 1640 1466 1069 1280 1193 0 1820 1809 1048 1486 1259 1720 1837 1489 1291 1457 1696 1106 2009 1776 1664 1103 1400 1387 1912 1643 1565
1073 1424 1304 2041 1835 1708 1836 1358 1738 2023 1820 0 1157 1448 1397 1963 1825 1314 1064 1922 1698 1549 1637 1699 2028 1571 1788 1436 1857 1282 1243 1672
1313 2019 1252 1570 1645 1680 1895 1947 2020 2047 1809 1157 0 1331 1847 1845 1503 1183 1296 1787 1478 1927 1507 1300 1806 1950 1292 2007 1612 1929 1491 1620
1774 2026 1556 1425 1517 1810 1226 1971 1586 1764 1048 1448 1331 0 1741 1465 1131 1126 1617 1384 1691 1097 1253 1766 1918 1598 1342 1318 1951 1568 1557 1201
1775 1707 1444 1317 1703 1196 1220 1447 1493 1599 1486 1397 1847 1741 0 1401 1594 1710 1813 1272 1136 1943 1513 1468 1343 1247 1420 1579 1181 1035 1143 1124
1608 1378 1257 1029 2008 1931 1512 1240 1057 1796 1259 1963 1845 1465 1401 0 1202 1435 1030 1814 1351 1376 1514 1122 1777 1615 1385 1932 1032 1786 1892 1443
1246 1501 1364 1349 1236 2015 1998 1865 1899 1439 1720 1825 1503 1131 1594 1202 0 1823 1490 1426 1271 1066 1227 1675 1235 1145 1647 1353 1276 1297 1522 1656
1479 1560 1416 1372 1633 1868 1329 1348 1161 1538 1837 1314 1183 1126 1710 1435 1823 0 1937 1735 1059 1944 1771 1215 1398 1146 1754 1171 1863 1499 1590 1795
1380 1616 1896 1676 1650 1856 1968 1502 1728 1867 1489 1064 1296 1617 1813 1030 1490 1937 0 1529 1730 1135 1463 1756 1337 1453 1072 1524 1274 1328 1600 1663
1770 1413 1864 1521 1321 1510 1995 1163 1984 1591 1291 1922 1787 1384 1272 1814 1426 1735 1529 0 1966 1829 1134 1217 1249 1473 1396 1876 1613 1641 1721 1957
1760 1520 1632 1701 1250 1383 1610 1293 1477 1320 1457 1698 1478 1691 1136 1351 1271 1059 1730 1966 0 1438 1267 1955 2014 1997 1550 1053 1630 1251 1790 1082
1891 1593 2034 1128 1587 1697 1223 1924 1441 1658 1696 1549 1927 1097 1943 1376 1066 1944 1135 1829 1438 0 1138 1182 1815 1370 1854 1449 1305 1605 1901 1582
1332 1978 1460 1206 1581 1137 1056 2032 1780 1377 1106 1637 1507 1253 1513 1514 1227 1771 1463 1134 1267 1138 0 1906 1960 1421 1363 2048 2044 1767 1839 2010
1611 1992 1911 1975 1987 1119 1455 1873 1344 1981 2009 1699 1300 1766 1468 1122 1675 1215 1756 1217 1955 1182 1906 0 1084 1336 1545 1853 1323 1534 1948 1231
1793 1269 1152 1954 1311 1140 1765 1180 1923 1094 1776 2028 1806 1918 1343 1777 1235 1398 1337 1249 2014 1815 1960 1084 0 1054 1734 1909 1371 1308 1151 1492
1746 1589 1399 1807 1391 1584 1261 1946 1496 1516 1664 1571 1950 1598 1247 1615 1145 1146 1453 1473 1997 1370 1421 1336 1054 0 1959 1130 1154 1132 1717 1852
1042 1518 1482 1649 1264 1914 1862 1114 1085 1736 1103 1788 1292 1342 1420 1385 1647 1754 1072 1396 1550 1854 1363 1545 1734 1959 0 1700 1639 1811 1977 1511
1488 1104 1093 1989 1935 1686 1755 2035 1563 1153 1400 1436 2007 1318 1579 1932 1353 1171 1524 1876 1053 1449 2048 1853 1909 1130 1700 0 1713 1500 1286 1389
1095 1335 1996 1714 1464 1346 1239 1255 1883 1459 1387 1857 1612 1951 1181 1032 1276 1863 1274 1613 1630 1305 2044 1323 1371 1154 1639 1713 0 1225 1519 1412
1945 1367 1142 1844 2024 1530 1942 1147 1994 1828 1912 1282 1929 1568 1035 1786 1297 1499 1328 1641 1251 1605 1767 1534 1308 1132 1811 1500 1225 0 1290 1578
1638 1748 1027 1567 1882 1657 1324 1763 1429 1654 1643 1243 1491 1557 1143 1892 1522 1590 1600 1721 1790 1901 1839 1948 1151 1717 1977 1286 1519 1290 0 1167
1789 1886 1531 1982 1843 1244 1209 1195 1458 1890 1565 1672 1620 1201 1124 1443 1656 1795 1663 1957 1082 1582 2010 1231 1492 1852 1511 1389 1412 1578 1167 0 
        """.trimIndent()

        val expectedOutputString = """
0->37:535.180
1->33:583.052
2->34:483.348
3->39:559.810
4->46:643.500
5->35:565.065
6->46:511.500
7->52:657.127
8->49:685.382
9->36:570.721
10->41:524.655
11->32:555.917
12->32:601.083
13->41:523.345
14->44:456.521
15->39:469.190
16->42:500.387
17->43:546.559
18->38:534.995
19->40:593.574
20->43:512.441
21->42:565.612
22->40:540.426
23->35:553.935
24->36:523.279
25->53:590.083
26->37:506.820
27->33:520.948
28->53:563.917
29->44:578.479
30->34:543.652
31->45:593.445
32->12:601.083
32->38:66.505
32->11:555.917
33->27:520.948
33->50:147.482
33->1:583.052
34->51:165.321
34->2:483.348
34->30:543.652
35->5:565.065
35->45:84.555
35->23:553.935
36->9:570.721
36->60:185.876
36->24:523.279
37->0:535.180
37->48:90.196
37->26:506.820
38->32:66.505
38->48:95.054
38->18:534.995
39->49:116.118
39->3:559.810
39->15:469.190
40->47:99.233
40->19:593.574
40->22:540.426
41->47:68.267
41->13:523.345
41->10:524.655
42->21:565.612
42->54:137.676
42->16:500.387
43->20:512.441
43->50:97.018
43->17:546.559
44->29:578.479
44->51:58.429
44->14:456.521
45->59:156.655
45->35:84.555
45->31:593.445
46->4:643.500
46->57:153.393
46->6:511.500
47->40:99.233
47->41:68.267
47->56:132.253
48->37:90.196
48->38:95.054
48->55:131.146
49->52:49.123
49->8:685.382
49->39:116.118
50->43:97.018
50->33:147.482
50->61:118.300
51->59:85.720
51->44:58.429
51->34:165.321
52->49:49.123
52->55:40.401
52->7:657.127
53->25:590.083
53->54:26.324
53->28:563.917
54->57:56.982
54->53:26.324
54->42:137.676
55->48:131.146
55->52:40.401
55->56:26.677
56->47:132.253
56->55:26.677
56->58:41.110
57->54:56.982
57->46:153.393
57->58:28.948
58->57:28.948
58->56:41.110
58->61:38.608
59->51:85.720
59->60:25.812
59->45:156.655
60->59:25.812
60->36:185.876
60->61:14.060
61->60:14.060
61->50:118.300
61->58:38.608
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = nj.neighborJoiningStart(matrixSize, m)

//        printGraph(result)

        // the algorithm produces a tree that actually matches the
        //  extra dataset graph given!!
        checkEdgesAreEqual(expectedGraph, result)

        val theResultMatrix = dbl.distancesBetweenLeavesFloat(matrixSize, result)
        //printMatrix(matrixSize, theResultMatrix)

        // This is a NON-ADDITIVE matrix - so this test will fail.
        //assertEquals(m, theResultMatrix)
    }


    @Test
    @DisplayName("Neighbor Joining quiz test")
    fun neighborJoiningQuizTest() {
        val sampleInput = """
32
0 1967 1246 1243 1732 1047 2035 1214 1880 1641 1680 1112 1506 2040 1317 1378 1728 1744 1988 1422 1179 1947 1824 1463 1165 1391 1701 1601 1736 1105 1305 1099 
1967 0 1293 1961 1723 1381 1097 1546 1507 1669 1432 1063 1727 1207 1516 2003 1280 1560 1190 1263 1709 1211 2029 1562 1183 1428 1414 1593 1729 2043 1358 1474 
1246 1293 0 1678 1392 1677 1613 1055 1538 1501 1756 1938 1140 1031 1051 1510 1757 1150 1103 1912 1804 1252 1668 1444 1385 1517 1153 1663 1349 1028 1431 1737 
1243 1961 1678 0 2031 1999 1469 1981 2027 1752 1688 2011 1805 1096 1111 2008 1830 1717 1930 1117 1046 1234 1223 1610 1365 1693 2004 1843 1210 1406 1573 1657 
1732 1723 1392 2031 0 1970 1740 1294 1481 1115 1276 1082 2005 1400 1511 1403 1315 1301 1060 1724 1455 1635 1120 1849 1815 1435 2030 1629 1149 2007 1123 2038 
1047 1381 1677 1999 1970 0 1862 1607 1025 1819 1221 1543 1528 1118 1382 1941 1291 1775 1766 1630 1903 1441 1940 1673 2006 1325 1206 2046 1832 1402 1817 1764 
2035 1097 1613 1469 1740 1862 0 1249 1337 1884 1986 1822 1545 1169 1410 1307 1065 1434 1044 1969 1778 1893 1147 1812 1083 1722 1119 1306 1355 1267 1353 1527 
1214 1546 1055 1981 1294 1607 1249 0 2022 1320 1048 1071 1750 1470 1423 1920 1838 1323 1299 1374 1127 1350 1900 1367 1383 1490 1242 1530 1578 1186 1864 1386 
1880 1507 1538 2027 1481 1025 1337 2022 0 1587 1289 1437 1666 1026 1377 2034 1045 1114 1062 1993 1645 1074 1916 1886 1976 1327 1851 1288 1166 1376 1807 1985 
1641 1669 1501 1752 1115 1819 1884 1320 1587 0 1193 1852 1532 1905 1711 1308 1751 1647 1177 1360 1201 2026 1972 1412 1743 1559 1848 1700 1734 1368 1439 1029 
1680 1432 1756 1688 1276 1221 1986 1048 1289 1193 0 1472 1774 1496 1718 1467 1066 1081 1745 1829 1779 1926 1290 1357 1548 1195 1281 1138 1466 1768 1883 1730 
1112 1063 1938 2011 1082 1543 1822 1071 1437 1852 1472 0 1230 1898 1270 1144 1279 1571 1038 1215 1966 1175 1170 1265 1050 1827 1698 1426 1145 1384 1275 2024 
1506 1727 1140 1805 2005 1528 1545 1750 1666 1532 1774 1230 0 1389 2002 1856 1264 1575 1492 1765 2018 1101 1803 1229 1836 1794 1818 1057 1984 1754 1036 1703 
2040 1207 1031 1096 1400 1118 1169 1470 1026 1905 1496 1898 1389 0 1375 1286 1554 1934 1322 1354 1067 1237 1878 1142 1842 1650 1881 1203 2044 1493 1820 1205 
1317 1516 1051 1111 1511 1382 1410 1423 1377 1711 1718 1270 2002 1375 0 1795 1468 1329 1771 1603 1553 1285 1109 1726 1733 1348 1749 1781 1480 1654 1784 1846 
1378 2003 1510 2008 1403 1941 1307 1920 2034 1308 1467 1144 1856 1286 1795 0 1198 1695 1303 2013 1465 1485 1176 1637 1040 1906 1790 1901 1667 1844 1049 1131 
1728 1280 1757 1830 1315 1291 1065 1838 1045 1751 1066 1279 1264 1554 1468 1198 0 1623 1977 1611 1227 1244 1763 1253 2014 1159 1633 1336 1902 1514 1702 1456 
1744 1560 1150 1717 1301 1775 1434 1323 1114 1647 1081 1571 1575 1934 1329 1695 1623 0 1924 1408 1534 2045 1929 1282 1806 1758 1148 1994 1339 1799 1826 1634 
1988 1190 1103 1930 1060 1766 1044 1299 1062 1177 1745 1038 1492 1322 1771 1303 1977 1924 0 1561 1398 1228 1448 1155 1446 1061 1917 1332 1196 1959 1596 1589 
1422 1263 1912 1117 1724 1630 1969 1374 1993 1360 1829 1215 1765 1354 1603 2013 1611 1408 1561 0 1707 1194 1491 1796 1639 1100 1643 1715 1143 1072 1180 1158 
1179 1709 1804 1046 1455 1903 1778 1127 1645 1201 1779 1966 2018 1067 1553 1465 1227 1534 1398 1707 0 1577 1983 1512 1343 1868 1261 1174 2021 1860 1602 1919 
1947 1211 1252 1234 1635 1441 1893 1350 1074 2026 1926 1175 1101 1237 1285 1485 1244 2045 1228 1194 1577 0 1950 1997 1233 1971 1212 1236 1979 1141 1859 1789 
1824 2029 1668 1223 1120 1940 1147 1900 1916 1972 1290 1170 1803 1878 1109 1176 1763 1929 1448 1491 1983 1950 0 1415 1847 1665 1574 1032 1430 1588 1642 1863 
1463 1562 1444 1610 1849 1673 1812 1367 1886 1412 1357 1265 1229 1142 1726 1637 1253 1282 1155 1796 1512 1997 1415 0 1584 1504 1102 1268 1172 1932 1217 1189 
1165 1183 1385 1365 1815 2006 1083 1383 1976 1743 1548 1050 1836 1842 1733 1040 2014 1806 1446 1639 1343 1233 1847 1584 0 1542 1450 1302 1110 1520 1068 1313 
1391 1428 1517 1693 1435 1325 1722 1490 1327 1559 1195 1827 1794 1650 1348 1906 1159 1758 1061 1100 1868 1971 1665 1504 1542 0 1651 1525 2042 2009 1271 1908 
1701 1414 1153 2004 2030 1206 1119 1242 1851 1848 1281 1698 1818 1881 1749 1790 1633 1148 1917 1643 1261 1212 1574 1102 1450 1651 0 1681 1785 1739 2037 1161 
1601 1593 1663 1843 1629 2046 1306 1530 1288 1700 1138 1426 1057 1203 1781 1901 1336 1994 1332 1715 1174 1236 1032 1268 1302 1525 1681 0 1042 1331 2019 1783 
1736 1729 1349 1210 1149 1832 1355 1578 1166 1734 1466 1145 1984 2044 1480 1667 1902 1339 1196 1143 2021 1979 1430 1172 1110 2042 1785 1042 0 1674 1594 1059 
1105 2043 1028 1406 2007 1402 1267 1186 1376 1368 1768 1384 1754 1493 1654 1844 1514 1799 1959 1072 1860 1141 1588 1932 1520 2009 1739 1331 1674 0 1897 1043 
1305 1358 1431 1573 1123 1817 1353 1864 1807 1439 1883 1275 1036 1820 1784 1049 1702 1826 1596 1180 1602 1859 1642 1217 1068 1271 2037 2019 1594 1897 0 1185 
1099 1474 1737 1657 2038 1764 1527 1386 1985 1029 1730 2024 1703 1205 1846 1131 1456 1634 1589 1158 1919 1789 1863 1189 1313 1908 1161 1783 1059 1043 1185 0
        """.trimIndent()

        // accepted solution:
        val expectedOutputString = """
0->37:544.960
1->51:565.460
2->45:478.574
3->32:550.283
4->46:565.484
5->33:548.328
6->43:523.605
7->50:568.279
8->33:476.672
9->35:548.065
10->41:516.345
11->49:459.197
12->34:553.054
13->39:468.853
14->45:572.427
15->38:561.385
16->43:541.395
17->41:564.655
18->54:547.164
19->40:536.653
20->32:495.717
21->52:566.134
22->36:565.885
23->42:510.431
24->38:478.615
25->40:563.347
26->42:591.569
27->36:466.115
28->46:583.516
29->37:560.040
30->34:482.946
31->35:480.935
32->3:550.283
32->20:495.717
32->39:89.647
33->5:548.328
33->8:476.672
33->52:178.866
34->12:553.054
34->30:482.946
34->53:207.672
35->9:548.065
35->31:480.935
35->44:136.205
36->22:565.885
36->27:466.115
36->48:160.402
37->0:544.960
37->29:560.040
37->44:84.545
38->15:561.385
38->24:478.615
38->49:117.803
39->13:468.853
39->32:89.647
39->56:172.387
40->19:536.653
40->25:563.347
40->58:165.855
41->10:516.345
41->17:564.655
41->47:107.204
42->23:510.431
42->26:591.569
42->47:68.296
43->6:523.605
43->16:541.395
43->51:90.540
44->35:136.205
44->37:84.545
44->55:108.442
45->2:478.574
45->14:572.427
45->56:131.113
46->4:565.484
46->28:583.516
46->48:54.348
47->41:107.204
47->42:68.296
47->50:43.221
48->36:160.402
48->46:54.348
48->54:59.211
49->11:459.197
49->38:117.803
49->53:78.203
50->7:568.279
50->47:43.221
50->55:64.683
51->1:565.460
51->43:90.540
51->59:95.465
52->21:566.134
52->33:178.866
52->57:60.995
53->34:207.672
53->49:78.203
53->60:62.753
54->18:547.164
54->48:59.211
54->60:74.138
55->44:108.442
55->50:64.683
55->58:64.333
56->39:172.387
56->45:131.113
56->57:16.880
57->52:60.995
57->56:16.880
57->59:59.973
58->40:165.855
58->55:64.333
58->61:47.660
59->51:95.465
59->57:59.973
59->61:41.396
60->53:62.753
60->54:74.138
60->61:28.176
61->58:47.660
61->59:41.396
61->60:28.176
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val matrixSize = input[0].trim().toInt()
        input.removeFirst()
        val m = parseMatrixInput(matrixSize, input)

        val expectedResultStrings = expectedOutputString.reader().readLines().toMutableList()
        val expectedGraph = parseSampleInput(expectedResultStrings)

        val result = nj.neighborJoiningStart(matrixSize, m)

//        printGraph(result)

        checkEdgesAreEqual(expectedGraph, result)

        val theResultMatrix = dbl.distancesBetweenLeavesFloat(matrixSize, result)
        //printMatrix(matrixSize, theResultMatrix)

        // This is a NON-ADDITIVE matrix - so this test will fail.
        // assertEquals(m, theResultMatrix)
    }
}