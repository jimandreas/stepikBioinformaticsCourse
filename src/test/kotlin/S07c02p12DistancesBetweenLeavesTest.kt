@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.DistancesBetweenLeaves
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 *

Distances Between Leaves Problem: Compute the distances between leaves in a weighted tree.

Input:  An integer n followed by the adjacency list of a weighted tree with n leaves.

Output: An n x n matrix (di,j), where di,j is the length of the path between leaves i and j.

 * See also:
 * stepik: https://stepik.org/lesson/240335/step/12?unit=212681
 * rosalind: http://rosalind.info/problems/ba7a/
 */

/**

Code Challenge: Solve the Distances Between Leaves Problem.
The tree is given as an adjacency list of a graph whose leaves
are integers between 0 and n - 1; the notation a->b:c
means that node a is connected to node b by an edge of weight c.
The matrix you return should be space-separated.

 */
internal class S07c02p12DistancesBetweenLeavesTest {

    lateinit var dbl: DistancesBetweenLeaves


    @BeforeEach
    fun setUp() {
        dbl = DistancesBetweenLeaves()
    }

    @Test
    @DisplayName("Distances Between Leaves sample test")
    fun distancesBetweenLeavesSampleTest() {
        val sampleInput = """
            4
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

        val expectedOutput = """
            0	13	21	22
            13	0	12	13
            21	12	0	13
            22	13	13	0
        """.trimIndent()

        val r = sampleInput.reader().readLines().toMutableList()
        val matrixSize = r[0].toInt()
        r.removeAt(0)
        val edges = parseSampleInput(r)

        // distancesBetweenLeaves(leafCount: Int, g: MutableMap<Int, MutableList<Pair<Int, Int>>>)
        val result = dbl.distancesBetweenLeaves(matrixSize, edges)
        printit(matrixSize, result)

        val expectedMatrix = parseExpectedOutput(matrixSize, expectedOutput)
        assertEquals(expectedMatrix, result)
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

    private fun parseExpectedOutput(matrixSize: Int, m: String): D2Array<Int> {
        val theMatrix = mk.d2array(matrixSize, matrixSize) {0}
        val lines = m.reader().readLines()

        for (i in 0 until matrixSize) {
            val l = lines[i].trim().split("\t", " ")
            for (j in 0 until matrixSize) {
                theMatrix[i, j] = l[j].toInt()
            }
        }
        return theMatrix
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

    fun parseSampleInput(edges: List<String>): MutableMap<Int, MutableMap<Int, Int>> {
        val edgeMap: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()
        for (e in edges) {
            val sourceDest = e.split("->")
            val nodeAndWeight = sourceDest[1].split(":")

            val key = sourceDest[0].toInt()
            if (edgeMap.containsKey(key)) {
                edgeMap[sourceDest[0].toInt()]!![nodeAndWeight[0].toInt()] = nodeAndWeight[1].toInt()
            } else {
                edgeMap[sourceDest[0].toInt()] = mutableMapOf(Pair(nodeAndWeight[0].toInt(), nodeAndWeight[1].toInt()))
            }
        }
        return edgeMap
    }


    @Test
    @DisplayName("Distances Between Leaves extra dataset test")
    fun distancesBetweenLeavesExtraDatasetTest() {
        val sampleInput = """
32
0->54:15
1->41:12
2->35:6
3->42:10
4->34:7
5->58:5
6->55:12
7->39:6
8->36:12
9->38:15
10->53:13
11->56:12
12->51:5
13->48:8
14->60:14
15->46:12
16->57:6
17->52:9
18->40:11
19->61:10
20->43:10
21->44:6
22->33:8
23->45:10
24->50:7
25->32:6
26->32:5
27->37:6
28->59:9
29->49:15
30->47:11
31->33:8
32->25:6
32->38:14
32->26:5
33->31:8
33->22:8
33->34:7
34->4:7
34->35:14
34->33:7
35->36:7
35->2:6
35->34:14
36->8:12
36->35:7
36->37:12
37->36:12
37->42:10
37->27:6
38->39:6
38->9:15
38->32:14
39->40:13
39->7:6
39->38:6
40->39:13
40->18:11
40->41:9
41->1:12
41->40:9
41->45:11
42->37:10
42->43:8
42->3:10
43->42:8
43->44:7
43->20:10
44->48:15
44->43:7
44->21:6
45->23:10
45->46:9
45->41:11
46->15:12
46->47:8
46->45:9
47->46:8
47->30:11
47->54:15
48->44:15
48->13:8
48->49:11
49->48:11
49->29:15
49->50:5
50->24:7
50->49:5
50->51:11
51->12:5
51->52:7
51->50:11
52->17:9
52->53:11
52->51:7
53->57:11
53->10:13
53->52:11
54->47:15
54->55:6
54->0:15
55->56:8
55->54:6
55->6:12
56->11:12
56->58:10
56->55:8
57->16:6
57->53:11
57->61:8
58->59:5
58->56:10
58->5:5
59->58:5
59->60:8
59->28:9
60->59:8
60->14:14
60->61:5
61->57:8
61->60:5
61->19:10
        """.trimIndent()

        val expectedOutput = """
0 70 186 161 201 44 33 86 185 101 89 41 99 129 66 50 71 96 78 67 153 142 209 57 112 106 105 167 53 125 41 209
70 0 226 201 241 84 73 40 225 55 129 81 139 169 106 44 111 136 32 107 193 182 249 33 152 60 59 207 93 165 51 249
186 226 0 45 27 152 177 242 25 257 123 169 97 73 148 206 127 108 234 139 53 56 35 213 88 262 261 31 151 91 197 35
161 201 45 0 60 127 152 217 44 232 98 144 72 48 123 181 102 83 209 114 28 31 68 188 63 237 236 26 126 66 172 68
201 241 27 60 0 167 192 257 40 272 138 184 112 88 163 221 142 123 249 154 68 71 22 228 103 277 276 46 166 106 212 22
44 84 152 127 167 0 35 100 151 115 55 27 65 95 32 64 37 62 92 33 119 108 175 71 78 120 119 133 19 91 55 175
33 73 177 152 192 35 0 89 176 104 80 32 90 120 57 53 62 87 81 58 144 133 200 60 103 109 108 158 44 116 44 200
86 40 242 217 257 100 89 0 241 27 145 97 155 185 122 60 127 152 30 123 209 198 265 49 168 32 31 223 109 181 67 265
185 225 25 44 40 151 176 241 0 256 122 168 96 72 147 205 126 107 233 138 52 55 48 212 87 261 260 30 150 90 196 48
101 55 257 232 272 115 104 27 256 0 160 112 170 200 137 75 142 167 45 138 224 213 280 64 183 35 34 238 124 196 82 280
89 129 123 98 138 55 80 145 122 160 0 72 36 66 51 109 30 33 137 42 90 79 146 116 49 165 164 104 54 62 100 146
41 81 169 144 184 27 32 97 168 112 72 0 82 112 49 61 54 79 89 50 136 125 192 68 95 117 116 150 36 108 52 192
99 139 97 72 112 65 90 155 96 170 36 82 0 40 61 119 40 21 147 52 64 53 120 126 23 175 174 78 64 36 110 120
129 169 73 48 88 95 120 185 72 200 66 112 40 0 91 149 70 51 177 82 40 29 96 156 31 205 204 54 94 34 140 96
66 106 148 123 163 32 57 122 147 137 51 49 61 91 0 86 33 58 114 29 115 104 171 93 74 142 141 129 31 87 77 171
50 44 206 181 221 64 53 60 205 75 109 61 119 149 86 0 91 116 52 87 173 162 229 31 132 80 79 187 73 145 31 229
71 111 127 102 142 37 62 127 126 142 30 54 40 70 33 91 0 37 119 24 94 83 150 98 53 147 146 108 36 66 82 150
96 136 108 83 123 62 87 152 107 167 33 79 21 51 58 116 37 0 144 49 75 64 131 123 34 172 171 89 61 47 107 131
78 32 234 209 249 92 81 30 233 45 137 89 147 177 114 52 119 144 0 115 201 190 257 41 160 50 49 215 101 173 59 257
67 107 139 114 154 33 58 123 138 138 42 50 52 82 29 87 24 49 115 0 106 95 162 94 65 143 142 120 32 78 78 162
153 193 53 28 68 119 144 209 52 224 90 136 64 40 115 173 94 75 201 106 0 23 76 180 55 229 228 34 118 58 164 76
142 182 56 31 71 108 133 198 55 213 79 125 53 29 104 162 83 64 190 95 23 0 79 169 44 218 217 37 107 47 153 79
209 249 35 68 22 175 200 265 48 280 146 192 120 96 171 229 150 131 257 162 76 79 0 236 111 285 284 54 174 114 220 16
57 33 213 188 228 71 60 49 212 64 116 68 126 156 93 31 98 123 41 94 180 169 236 0 139 69 68 194 80 152 38 236
112 152 88 63 103 78 103 168 87 183 49 95 23 31 74 132 53 34 160 65 55 44 111 139 0 188 187 69 77 27 123 111
106 60 262 237 277 120 109 32 261 35 165 117 175 205 142 80 147 172 50 143 229 218 285 69 188 0 11 243 129 201 87 285
105 59 261 236 276 119 108 31 260 34 164 116 174 204 141 79 146 171 49 142 228 217 284 68 187 11 0 242 128 200 86 284
167 207 31 26 46 133 158 223 30 238 104 150 78 54 129 187 108 89 215 120 34 37 54 194 69 243 242 0 132 72 178 54
53 93 151 126 166 19 44 109 150 124 54 36 64 94 31 73 36 61 101 32 118 107 174 80 77 129 128 132 0 90 64 174
125 165 91 66 106 91 116 181 90 196 62 108 36 34 87 145 66 47 173 78 58 47 114 152 27 201 200 72 90 0 136 114
41 51 197 172 212 55 44 67 196 82 100 52 110 140 77 31 82 107 59 78 164 153 220 38 123 87 86 178 64 136 0 220
209 249 35 68 22 175 200 265 48 280 146 192 120 96 171 229 150 131 257 162 76 79 16 236 111 285 284 54 174 114 220 0
        """.trimIndent()

        val r = sampleInput.reader().readLines().toMutableList()
        val matrixSize = r[0].toInt()
        r.removeAt(0)
        val edges = parseSampleInput(r)

        // distancesBetweenLeaves(leafCount: Int, g: MutableMap<Int, MutableList<Pair<Int, Int>>>)
        val result = dbl.distancesBetweenLeaves(matrixSize, edges)
        //printit(matrixSize, result)

        val expectedMatrix = parseExpectedOutput(matrixSize, expectedOutput)
        assertEquals(expectedMatrix, result)
    }

    @Test
    @DisplayName("Distances Between Leaves quiz test")
    fun distancesBetweenLeavesQuizTest() {
        val sampleInput = """
32
60->61:5
60->5:7
60->58:13
61->60:5
61->20:7
61->59:7
20->61:7
59->61:7
59->12:9
59->51:11
5->60:7
58->60:13
58->13:11
58->57:5
12->59:9
51->59:11
51->23:13
51->50:15
13->58:11
57->58:5
57->4:13
57->56:11
4->57:13
56->57:11
56->31:14
56->55:13
31->56:14
55->56:13
55->10:11
55->54:9
10->55:11
54->55:9
54->15:14
54->53:7
15->54:14
53->54:7
53->6:5
53->52:6
6->53:5
52->53:6
52->3:10
52->42:13
3->52:10
42->52:13
42->0:14
42->9:7
23->51:13
50->51:15
50->11:13
50->49:13
11->50:13
49->50:13
49->24:11
49->48:6
24->49:11
48->49:6
48->17:8
48->47:8
17->48:8
47->48:8
47->28:11
47->46:15
28->47:11
46->47:15
46->18:7
46->45:12
18->46:7
45->46:12
45->7:13
45->44:5
7->45:13
44->45:5
44->19:9
44->43:7
19->44:9
43->44:7
43->26:13
43->41:13
26->43:13
41->43:13
41->16:13
41->40:9
0->42:14
9->42:7
16->41:13
40->41:9
40->22:14
40->39:15
22->40:14
39->40:15
39->8:8
39->38:7
8->39:8
38->39:7
38->2:13
38->37:15
2->38:13
37->38:15
37->25:7
37->36:6
25->37:7
36->37:6
36->27:6
36->35:9
27->36:6
35->36:9
35->1:13
35->34:9
1->35:13
34->35:9
34->14:9
34->33:15
14->34:9
33->34:15
33->29:5
33->32:10
29->33:5
32->33:10
32->21:6
32->30:13
21->32:6
30->32:13
        """.trimIndent()

        // accepted computed answer
        val expectedOutput = """
0 282 252 37 86 98 38 196 240 21 60 142 112 89 287 54 221 156 178 197 103 309 231 127 153 261 208 266 167 298 316 76
282 0 56 265 222 198 254 112 58 275 244 166 188 215 31 256 87 142 118 103 193 53 79 181 151 35 100 28 137 42 60 234
252 56 0 235 192 168 224 82 28 245 214 136 158 185 61 226 57 112 88 73 163 83 49 151 121 35 70 40 107 72 90 204
37 265 235 0 69 81 21 179 223 30 43 125 95 72 270 37 204 139 161 180 86 292 214 110 136 244 191 249 150 281 299 59
86 222 192 69 0 38 58 136 180 79 48 82 52 29 227 60 161 96 118 137 43 249 171 67 93 201 148 206 107 238 256 38
98 198 168 81 38 0 70 112 156 91 60 58 28 31 203 72 137 72 94 113 19 225 147 43 69 177 124 182 83 214 232 50
38 254 224 21 58 70 0 168 212 31 32 114 84 61 259 26 193 128 150 169 75 281 203 99 125 233 180 238 139 270 288 48
196 112 82 179 136 112 168 0 70 189 158 80 102 129 117 170 51 56 32 27 107 139 61 95 65 91 38 96 51 128 146 148
240 58 28 223 180 156 212 70 0 233 202 124 146 173 63 214 45 100 76 61 151 85 37 139 109 37 58 42 95 74 92 192
21 275 245 30 79 91 31 189 233 0 53 135 105 82 280 47 214 149 171 190 96 302 224 120 146 254 201 259 160 291 309 69
60 244 214 43 48 60 32 158 202 53 0 104 74 51 249 34 183 118 140 159 65 271 193 89 115 223 170 228 129 260 278 38
142 166 136 125 82 58 114 80 124 135 104 0 48 75 171 116 105 40 62 81 53 193 115 41 37 145 92 150 51 182 200 94
112 188 158 95 52 28 84 102 146 105 74 48 0 45 193 86 127 62 84 103 23 215 137 33 59 167 114 172 73 204 222 64
89 215 185 72 29 31 61 129 173 82 51 75 45 0 220 63 154 89 111 130 36 242 164 60 86 194 141 199 100 231 249 41
287 31 61 270 227 203 259 117 63 280 249 171 193 220 0 261 92 147 123 108 198 40 84 186 156 40 105 33 142 29 47 239
54 256 226 37 60 72 26 170 214 47 34 116 86 63 261 0 195 130 152 171 77 283 205 101 127 235 182 240 141 272 290 50
221 87 57 204 161 137 193 51 45 214 183 105 127 154 92 195 0 81 57 42 132 114 36 120 90 66 39 71 76 103 121 173
156 142 112 139 96 72 128 56 100 149 118 40 62 89 147 130 81 0 38 57 67 169 91 55 25 121 68 126 27 158 176 108
178 118 88 161 118 94 150 32 76 171 140 62 84 111 123 152 57 38 0 33 89 145 67 77 47 97 44 102 33 134 152 130
197 103 73 180 137 113 169 27 61 190 159 81 103 130 108 171 42 57 33 0 108 130 52 96 66 82 29 87 52 119 137 149
103 193 163 86 43 19 75 107 151 96 65 53 23 36 198 77 132 67 89 108 0 220 142 38 64 172 119 177 78 209 227 55
309 53 83 292 249 225 281 139 85 302 271 193 215 242 40 283 114 169 145 130 220 0 106 208 178 62 127 55 164 21 19 261
231 79 49 214 171 147 203 61 37 224 193 115 137 164 84 205 36 91 67 52 142 106 0 130 100 58 49 63 86 95 113 183
127 181 151 110 67 43 99 95 139 120 89 41 33 60 186 101 120 55 77 96 38 208 130 0 52 160 107 165 66 197 215 79
153 151 121 136 93 69 125 65 109 146 115 37 59 86 156 127 90 25 47 66 64 178 100 52 0 130 77 135 36 167 185 105
261 35 35 244 201 177 233 91 37 254 223 145 167 194 40 235 66 121 97 82 172 62 58 160 130 0 79 19 116 51 69 213
208 100 70 191 148 124 180 38 58 201 170 92 114 141 105 182 39 68 44 29 119 127 49 107 77 79 0 84 63 116 134 160
266 28 40 249 206 182 238 96 42 259 228 150 172 199 33 240 71 126 102 87 177 55 63 165 135 19 84 0 121 44 62 218
167 137 107 150 107 83 139 51 95 160 129 51 73 100 142 141 76 27 33 52 78 164 86 66 36 116 63 121 0 153 171 119
298 42 72 281 238 214 270 128 74 291 260 182 204 231 29 272 103 158 134 119 209 21 95 197 167 51 116 44 153 0 28 250
316 60 90 299 256 232 288 146 92 309 278 200 222 249 47 290 121 176 152 137 227 19 113 215 185 69 134 62 171 28 0 268
76 234 204 59 38 50 48 148 192 69 38 94 64 41 239 50 173 108 130 149 55 261 183 79 105 213 160 218 119 250 268 0
        """.trimIndent()

        val r = sampleInput.reader().readLines().toMutableList()
        val matrixSize = r[0].toInt()
        r.removeAt(0)
        val edges = parseSampleInput(r)

        // distancesBetweenLeaves(leafCount: Int, g: MutableMap<Int, MutableList<Pair<Int, Int>>>)
        val result = dbl.distancesBetweenLeaves(matrixSize, edges)
        //printit(matrixSize, result)

        val expectedMatrix = parseExpectedOutput(matrixSize, expectedOutput)
        assertEquals(expectedMatrix, result)
    }

}