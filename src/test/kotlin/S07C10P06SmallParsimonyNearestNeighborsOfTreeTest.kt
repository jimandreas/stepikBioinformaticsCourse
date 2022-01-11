@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls", "ReplaceSizeZeroCheckWithIsEmpty"
)

import algorithms.SmallParsimonyNearestNeighborsOfTree
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 *
 * See also:
 * Stepik: https://stepik.org/lesson/240343/step/6?unit=212689
 * Rosalind: no equivalent problem
 *
 * "Evolutionary Tree Reconstruction in the Modern Era"
 * Youtube: https://www.youtube.com/watch?v=A48MBlIyl5c
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */
internal class S07C10P06SmallParsimonyNearestNeighborsOfTreeTest {

    lateinit var nn: SmallParsimonyNearestNeighborsOfTree

    @BeforeEach
    fun setUp() {
        nn = SmallParsimonyNearestNeighborsOfTree()
    }

    /**
     * a practice test where one internal node (7) only has one leaf
     */
    @Test
    @DisplayName("Nearest Neighbor Of Tree practice test")
    fun nearestNeighborOfTreePracticeTest() {
        val sampleInput = """
5 7
0->5
5->0
1->5
5->1
2->6
6->2
3->6
6->3
4->7
7->4
5->7
7->5
6->7
7->6
        """.trimIndent().lines().toMutableList()

        val edge = sampleInput.removeFirst().split(" ")
        val a = edge[0].toInt()
        val b = edge[1].toInt()
        val m : MutableMap<Int, MutableList<Int>> = hashMapOf()
        for (s in sampleInput) {
            val conn = s.split("->")
            val from = conn[0].toInt()
            val to = conn[1].toInt()
            if (m.containsKey(from)) {
                m[from]!!.add(to)
            } else {
                m[from] = mutableListOf(to)
            }
        }

        val result = nn.twoNearestNeighbors(a, b, m)

        val expectedOutputString = """
0->5
1->7
2->6
3->6
4->5
5->0
5->4
5->7
6->2
6->3
6->7
7->1
7->5
7->6

0->5
1->7
2->6
3->6
4->7
5->0
5->6
5->7
6->2
6->3
6->5
7->1
7->4
7->5
        """.trimIndent().lines().toMutableList()

        // form the two expected result lists
        val expectedList1 : MutableList<String> = mutableListOf()
        val expectedList2 : MutableList<String> = mutableListOf()
        var currList = expectedList1
        for (i in 0 until expectedOutputString.size) {
            val s = expectedOutputString[i]
            if (s.length == 0) {
                currList = expectedList2
                continue
            }
            currList.add(s)
        }

        // now make two lists from the results
        val resultsList1 : MutableList<String> = mutableListOf()
        val resultsList2 : MutableList<String> = mutableListOf()
        var curDecodeOutput = resultsList1
        var curDecodeInput = result[0]
        for (l in 0..1) {
            for (k in curDecodeInput.keys) {
                for (to in curDecodeInput[k]!!)
                    curDecodeOutput.add("$k->$to")
            }
            curDecodeInput = result[1]
            curDecodeOutput = resultsList2
        }

//        println(resultsList1.sorted().joinToString("\n"))
//        println("")
//        println(resultsList2.sorted().joinToString("\n"))

        assertEquals(expectedList1.sorted(), resultsList1.sorted())
        assertEquals(expectedList2.sorted(), resultsList2.sorted())


    }



    @Test
    @DisplayName("Nearest Neighbor Of Tree sample test")
    fun nearestNeighborOfTreeSampleTest() {
        val sampleInput = """
5 4
0->4
4->0
1->4
4->1
2->5
5->2
3->5
5->3
4->5
5->4
        """.trimIndent().lines().toMutableList()

        val edge = sampleInput.removeFirst().split(" ")
        val a = edge[0].toInt()
        val b = edge[1].toInt()
        val m : MutableMap<Int, MutableList<Int>> = hashMapOf()
        for (s in sampleInput) {
            val conn = s.split("->")
            val from = conn[0].toInt()
            val to = conn[1].toInt()
            if (m.containsKey(from)) {
                m[from]!!.add(to)
            } else {
                m[from] = mutableListOf(to)
            }
        }

        val result = nn.twoNearestNeighbors(a, b, m)

        val expectedOutputString = """
1->4
0->5
3->4
2->5
5->2
5->4
5->0
4->1
4->5
4->3

1->5
0->4
3->4
2->5
5->2
5->4
5->1
4->0
4->5
4->3
        """.trimIndent().lines().toMutableList()

        // form the two expected result lists
        val expectedList1 : MutableList<String> = mutableListOf()
        val expectedList2 : MutableList<String> = mutableListOf()
        var currList = expectedList1
        for (i in 0 until expectedOutputString.size) {
            val s = expectedOutputString[i]
            if (s.length == 0) {
                currList = expectedList2
                continue
            }
            currList.add(s)
        }

        // now make two lists from the results
        val resultsList1 : MutableList<String> = mutableListOf()
        val resultsList2 : MutableList<String> = mutableListOf()
        var curDecodeOutput = resultsList1
        var curDecodeInput = result[0]
        for (l in 0..1) {
            for (k in curDecodeInput.keys) {
                for (to in curDecodeInput[k]!!)
                curDecodeOutput.add("$k->$to")
            }
            curDecodeInput = result[1]
            curDecodeOutput = resultsList2
        }

//        println(resultsList1.sorted().joinToString("\n"))
//        println("")
//        println(resultsList2.sorted().joinToString("\n"))

        assertEquals(expectedList1.sorted(), resultsList1.sorted())
        assertEquals(expectedList2.sorted(), resultsList2.sorted())


    }


    @Test
    @DisplayName("Nearest Neighbor Of Tree extra dataset test")
    fun nearestNeighborOfTreeExtraDatasetTest() {
        val sampleInput = """
51 58
0->32
32->0
1->32
32->1
2->33
33->2
3->33
33->3
4->34
34->4
5->34
34->5
6->35
35->6
7->35
35->7
8->36
36->8
9->36
36->9
10->37
37->10
11->37
37->11
12->38
38->12
13->38
38->13
14->39
39->14
15->39
39->15
16->40
40->16
17->40
40->17
18->41
41->18
19->41
41->19
20->42
42->20
21->42
42->21
22->43
43->22
23->43
43->23
24->44
44->24
25->44
44->25
26->45
45->26
27->45
45->27
28->46
46->28
29->46
46->29
30->47
47->30
31->47
47->31
40->48
48->40
36->48
48->36
34->49
49->34
35->49
49->35
32->50
50->32
43->50
50->43
47->51
51->47
33->51
51->33
38->52
52->38
45->52
52->45
39->53
53->39
46->53
53->46
49->54
54->49
52->54
54->52
37->55
55->37
44->55
55->44
41->56
56->41
54->56
56->54
50->57
57->50
48->57
57->48
56->58
58->56
51->58
58->51
42->59
59->42
58->59
59->58
57->60
60->57
53->60
60->53
55->61
61->55
59->61
61->59
60->61
61->60
        """.trimIndent().lines().toMutableList()

        val edge = sampleInput.removeFirst().split(" ")
        val a = edge[0].toInt()
        val b = edge[1].toInt()
        val m : MutableMap<Int, MutableList<Int>> = hashMapOf()
        for (s in sampleInput) {
            val conn = s.split("->")
            val from = conn[0].toInt()
            val to = conn[1].toInt()
            if (m.containsKey(from)) {
                m[from]!!.add(to)
            } else {
                m[from] = mutableListOf(to)
            }
        }

        val result = nn.twoNearestNeighbors(a, b, m)

        val expectedOutputString = """
56->41
56->54
56->51
54->49
54->52
54->56
28->46
48->40
48->36
48->57
29->46
60->57
60->53
60->61
61->55
61->59
61->60
49->34
49->35
49->54
52->38
52->45
52->54
53->39
53->46
53->60
24->44
25->44
26->45
27->45
20->42
21->42
22->43
23->43
46->28
46->29
46->53
47->30
47->31
47->51
44->24
44->25
44->55
45->26
45->27
45->52
42->20
42->21
42->59
43->22
43->23
43->50
40->16
40->17
40->48
41->18
41->19
41->56
1->32
0->32
3->33
2->33
5->34
4->34
7->35
6->35
9->36
8->36
51->47
51->58
51->56
39->14
39->15
39->53
38->12
38->13
38->52
59->42
59->58
59->61
58->51
58->59
58->33
11->37
10->37
13->38
12->38
15->39
14->39
17->40
16->40
33->2
33->3
33->58
32->0
32->1
32->50
31->47
30->47
37->10
37->11
37->55
36->8
36->9
36->48
35->6
35->7
35->49
34->4
34->5
34->49
19->41
55->37
55->44
55->61
18->41
57->50
57->48
57->60
50->32
50->43
50->57

56->41
56->54
56->58
54->49
54->52
54->56
28->46
48->40
48->36
48->57
29->46
60->57
60->53
60->61
61->55
61->59
61->60
49->34
49->35
49->54
52->38
52->45
52->54
53->39
53->46
53->60
24->44
25->44
26->45
27->45
20->42
21->42
22->43
23->43
46->28
46->29
46->53
47->30
47->31
47->51
44->24
44->25
44->55
45->26
45->27
45->52
42->20
42->21
42->59
43->22
43->23
43->50
40->16
40->17
40->48
41->18
41->19
41->56
1->32
0->32
3->33
2->33
5->34
4->34
7->35
6->35
9->36
8->36
51->47
51->58
51->59
39->14
39->15
39->53
38->12
38->13
38->52
59->42
59->61
59->51
58->56
58->51
58->33
11->37
10->37
13->38
12->38
15->39
14->39
17->40
16->40
33->2
33->3
33->58
32->0
32->1
32->50
31->47
30->47
37->10
37->11
37->55
36->8
36->9
36->48
35->6
35->7
35->49
34->4
34->5
34->49
19->41
55->37
55->44
55->61
18->41
57->50
57->48
57->60
50->32
50->43
50->57
        """.trimIndent().lines().toMutableList()

        // form the two expected result lists
        val expectedList1 : MutableList<String> = mutableListOf()
        val expectedList2 : MutableList<String> = mutableListOf()
        var currList = expectedList1
        for (i in 0 until expectedOutputString.size) {
            val s = expectedOutputString[i]
            if (s.length == 0) {
                currList = expectedList2
                continue
            }
            currList.add(s)
        }

        // now make two lists from the results
        val resultsList1 : MutableList<String> = mutableListOf()
        val resultsList2 : MutableList<String> = mutableListOf()
        var curDecodeOutput = resultsList1
        var curDecodeInput = result[0]
        for (l in 0..1) {
            for (k in curDecodeInput.keys) {
                for (to in curDecodeInput[k]!!)
                    curDecodeOutput.add("$k->$to")
            }
            curDecodeInput = result[1]
            curDecodeOutput = resultsList2
        }

//        println(resultsList1.sorted().joinToString("\n"))
//        println("")
//        println(resultsList2.sorted().joinToString("\n"))

        assertEquals(expectedList1.sorted(), resultsList1.sorted())
        assertEquals(expectedList2.sorted(), resultsList2.sorted())


    }

    @Test
    @DisplayName("Nearest Neighbor Of Tree quiz test")
    fun nearestNeighborOfTreeQuizTest() {
        val sampleInput = """
46 57
0->32
32->0
1->32
32->1
2->33
33->2
3->33
33->3
4->34
34->4
5->34
34->5
6->35
35->6
7->35
35->7
8->36
36->8
9->36
36->9
10->37
37->10
11->37
37->11
12->38
38->12
13->38
38->13
14->39
39->14
15->39
39->15
16->40
40->16
17->40
40->17
18->41
41->18
19->41
41->19
20->42
42->20
21->42
42->21
22->43
43->22
23->43
43->23
24->44
44->24
25->44
44->25
26->45
45->26
27->45
45->27
28->46
46->28
29->46
46->29
30->47
47->30
31->47
47->31
41->48
48->41
35->48
48->35
43->49
49->43
47->49
49->47
39->50
50->39
42->50
50->42
34->51
51->34
32->51
51->32
38->52
52->38
37->52
52->37
33->53
53->33
44->53
53->44
49->54
54->49
45->54
54->45
36->55
55->36
40->55
55->40
55->56
56->55
54->56
56->54
56->57
57->56
46->57
57->46
51->58
58->51
57->58
58->57
48->59
59->48
58->59
59->58
50->60
60->50
59->60
60->59
52->61
61->52
53->61
61->53
60->61
61->60
        """.trimIndent().lines().toMutableList()

        val edge = sampleInput.removeFirst().split(" ")
        val a = edge[0].toInt()
        val b = edge[1].toInt()
        val m : MutableMap<Int, MutableList<Int>> = hashMapOf()
        for (s in sampleInput) {
            val conn = s.split("->")
            val from = conn[0].toInt()
            val to = conn[1].toInt()
            if (m.containsKey(from)) {
                m[from]!!.add(to)
            } else {
                m[from] = mutableListOf(to)
            }
        }

        val result = nn.twoNearestNeighbors(a, b, m)
        // accepted answer
        val expectedOutputString = """
0->32
1->32
10->37
11->37
12->38
13->38
14->39
15->39
16->40
17->40
18->41
19->41
2->33
20->42
21->42
22->43
23->43
24->44
25->44
26->45
27->45
28->46
29->57
3->33
30->47
31->47
32->0
32->1
32->51
33->2
33->3
33->53
34->4
34->5
34->51
35->48
35->6
35->7
36->55
36->8
36->9
37->10
37->11
37->52
38->12
38->13
38->52
39->14
39->15
39->50
4->34
40->16
40->17
40->55
41->18
41->19
41->48
42->20
42->21
42->50
43->22
43->23
43->49
44->24
44->25
44->53
45->26
45->27
45->54
46->28
46->56
46->57
47->30
47->31
47->49
48->35
48->41
48->59
49->43
49->47
49->54
5->34
50->39
50->42
50->60
51->32
51->34
51->58
52->37
52->38
52->61
53->33
53->44
53->61
54->45
54->49
54->56
55->36
55->40
55->56
56->46
56->54
56->55
57->29
57->46
57->58
58->51
58->57
58->59
59->48
59->58
59->60
6->35
60->50
60->59
60->61
61->52
61->53
61->60
7->35
8->36
9->36

0->32
1->32
10->37
11->37
12->38
13->38
14->39
15->39
16->40
17->40
18->41
19->41
2->33
20->42
21->42
22->43
23->43
24->44
25->44
26->45
27->45
28->46
29->57
3->33
30->47
31->47
32->0
32->1
32->51
33->2
33->3
33->53
34->4
34->5
34->51
35->48
35->6
35->7
36->55
36->8
36->9
37->10
37->11
37->52
38->12
38->13
38->52
39->14
39->15
39->50
4->34
40->16
40->17
40->55
41->18
41->19
41->48
42->20
42->21
42->50
43->22
43->23
43->49
44->24
44->25
44->53
45->26
45->27
45->54
46->28
46->57
46->58
47->30
47->31
47->49
48->35
48->41
48->59
49->43
49->47
49->54
5->34
50->39
50->42
50->60
51->32
51->34
51->58
52->37
52->38
52->61
53->33
53->44
53->61
54->45
54->49
54->56
55->36
55->40
55->56
56->54
56->55
56->57
57->29
57->46
57->56
58->46
58->51
58->59
59->48
59->58
59->60
6->35
60->50
60->59
60->61
61->52
61->53
61->60
7->35
8->36
9->36
        """.trimIndent().lines().toMutableList()

        // form the two expected result lists
        val expectedList1 : MutableList<String> = mutableListOf()
        val expectedList2 : MutableList<String> = mutableListOf()
        var currList = expectedList1
        for (i in 0 until expectedOutputString.size) {
            val s = expectedOutputString[i]
            if (s.length == 0) {
                currList = expectedList2
                continue
            }
            currList.add(s)
        }

        // now make two lists from the results
        val resultsList1 : MutableList<String> = mutableListOf()
        val resultsList2 : MutableList<String> = mutableListOf()
        var curDecodeOutput = resultsList1
        var curDecodeInput = result[0]
        for (l in 0..1) {
            for (k in curDecodeInput.keys) {
                for (to in curDecodeInput[k]!!)
                    curDecodeOutput.add("$k->$to")
            }
            curDecodeInput = result[1]
            curDecodeOutput = resultsList2
        }

//        println(resultsList1.sorted().joinToString("\n"))
//        println("")
//        println(resultsList2.sorted().joinToString("\n"))

        assertEquals(expectedList1.sorted(), resultsList1.sorted())
        assertEquals(expectedList2.sorted(), resultsList2.sorted())


    }

}