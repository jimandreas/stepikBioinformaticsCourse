@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

import algorithms.SmallParsimony.*
import algorithms.SmallParsimony.NodeType.LEAF
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.min
import kotlin.collections.set

/**
 *
 * See also:
 * Stepik: https://stepik.org/lesson/240342/step/12?unit=212688
 * Rosalind: http://rosalind.info/problems/ba7g/
 * Youtube: https://www.youtube.com/watch?v=Rfa-2SvxslE
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

class SmallParsimonyUnrootedTree(val sp: SmallParsimony) {

    /**
    Code Challenge: Solve the Small Parsimony in an Unrooted Tree Problem.

    Input: An integer n followed by an adjacency list for an
    unrooted binary tree with n leaves labeled by DNA strings.

    Output: The minimum parsimony score of this tree, followed
    by the adjacency list of the tree corresponding to labeling
    internal nodes by DNA strings in order to minimize the parsimony
    score of the tree.
     */

    lateinit var root: Node  // the hacked up root of this tree

    var numLeaves = 0 // this is specified in line 1 of the test input

    // this is the map set up after parsing the input - node number to Node structure
    val leafHashMap: HashMap<Int, Node> = hashMapOf()

    // from the test input - the lines that have Int -> Int
    val edgeMap: HashMap<Int, MutableList<Int>> = hashMapOf()

    var dnaLen = -1
    var totalHammingDistance = 0
    var maxEdgeNum = 0

    /**
     * parse the test input:
     * Input: An integer n followed by an adjacency list for an
     * *UNrooted* binary tree with n leaves labeled by DNA strings.
     * example:
    4
    TCGGCCAA->4
    4->TCGGCCAA
    CCTGGCTG->4
    4->CCTGGCTG
    CACAGGAT->5
    5->CACAGGAT
    TGAGTACC->5
    5->TGAGTACC
    4->5
    5->4

    Note: this parser simply ignores lines beginning with a codon
     */

    fun parseInputStringsUnrooted(inputStrings: MutableList<String>) {

        numLeaves = inputStrings[0].toInt()  // line 1 of test input specifies how many leaf DNA strings
        inputStrings.removeFirst()

        var leafNumber = 0
        while (inputStrings.size > 0) {
            val line = inputStrings[0].split("->")

            // skip lines beginning with a codon:
            val testcodon = "ACGT".indexOf(line[0][0])
            if (testcodon != -1) {
                inputStrings.removeFirst()
                continue
            }
            // OK have a line beginning with a number:
            val nodeNum = line[0].toInt()
            val codon = "ACGT".indexOf(line[1][0])

            // see if this a node to node connection, or a DNA string definition
            if (codon == -1) {

                // this is a node -> node line, add to edge list
                //   assumes dataset provides reverse edges

                val edgeTo = line[1].toInt()

                if (edgeMap.containsKey(nodeNum)) {
                    edgeMap[nodeNum]!!.add(edgeTo)
                } else {
                    edgeMap[nodeNum] = mutableListOf(edgeTo)
                }

            } else {

                // this is a node num -> dnaString

                var nodeStruct: Node
                if (leafHashMap.containsKey(nodeNum)) {
                    nodeStruct = leafHashMap[nodeNum]!!
                } else {
                    nodeStruct = Node(id = nodeNum)
                    leafHashMap[nodeNum] = nodeStruct
                }

                dnaLen = line[1].length
                sp.dnaLen = dnaLen
                val leafNode = Node(
                    nodeType = LEAF,
                    id = leafNumber++,
                    isScored = true,
                    isOutput = true,
                    dnaString = line[1],
                    scoringArray = mkArrayWithScores(line[1])
                )

                if (nodeStruct.left == null) {
                    nodeStruct.left = leafNode
                } else {
                    nodeStruct.right = leafNode
                }
            }
            inputStrings.removeFirst()
        }

        treeHacking()

        doUnrootedTreeScoring()
    }

    /**
     * transform this mess into a binary tree suitable for traversing.
     *
     * Process:
     *    Start at the edge of the edges.
     *    Make a root node at the edge, hooking up the last two edges.
     */
    fun treeHacking() {
        maxEdgeNum = edgeMap.keys.maxOrNull()!!
        root = Node(
            nodeType = NodeType.NODE,
            id = maxEdgeNum+1
        )
        // prune out the edges between maxEdgeNum and maxEdgeNum-1
        val iE = edgeMap[maxEdgeNum]!!.indexOf(maxEdgeNum - 1)
        edgeMap[maxEdgeNum]!!.removeAt(iE)

        val iEm1 = edgeMap[maxEdgeNum - 1]!!.indexOf(maxEdgeNum)
        edgeMap[maxEdgeNum - 1]!!.removeAt(iEm1)

        val left = descent(maxEdgeNum - 1, maxEdgeNum)
        val right = descent(maxEdgeNum, 0)

        root.left = left
        root.right = right

        // finished for now
    }

    /**
     *  recursively descend the edges from parent to child.
     *    Stop the descent at LEAF nodes -
     *       i.e. nodes that are present in the
     *       leafHashMap.
     */
    fun descent(myNodeNum: Int, parentNodeNum: Int): Node? {
        val conns = edgeMap[myNodeNum]
        conns!!.remove(parentNodeNum)
        if (conns.isEmpty()) {
            if (leafHashMap.containsKey(myNodeNum)) {
                return leafHashMap[myNodeNum]
            } else {
                return null
            }
        }

        var left: Node
        if (leafHashMap.containsKey(conns[0])) {
            left = leafHashMap[conns[0]]!!
        } else {
            left = descent(conns[0], myNodeNum)!!
        }

        var right: Node? = null
        if (conns.size > 1) {
            if (leafHashMap.containsKey(conns[1])) {
                right = leafHashMap[conns[1]]!!
            } else {
                right = descent(conns[1], myNodeNum)!!
            }
        }

        val thisNode = Node(
            nodeType = NodeType.NODE,
            id = myNodeNum,
            left = left,
            right = right
        )
        return thisNode
    }


    /**
     * descend ITERATIVELY through the nodes
     * starting at the "injected" root
     * until there are no unscored nodes
     * and therefore all scoring matrices
     * have been calculated.
     */
    fun doUnrootedTreeScoring() {

        if (dnaLen == -1) {
            println("iterateNodes: global variable dnaLen is not initialized.  Giving up.")
            return
        }

        do {
            var foundUnscoredNode = false
            val workList: MutableList<Node> = mutableListOf()
            workList.add(root)  // start at root for each iteration

            do {
                val node = workList.removeFirst()
                val left = node.left
                val right = node.right

                if (node.isScored) {
                    continue
                }

                if (left!!.isScored && right != null && right.isScored) {
                    foundUnscoredNode = true

                    val scoredArray = scoreArrays(left.scoringArray!!, right.scoringArray!!)
                    node.scoringArray = scoredArray
                    node.isScored = true
                } else if (left.isScored) {
                    foundUnscoredNode = true
                    val scoredArray = scoreLeftArray(left.scoringArray!!)
                    node.scoringArray = scoredArray
                    node.isScored = true
                }
                if (!left.isScored) {
                    workList.add(left)
                    foundUnscoredNode = true
                }
                if (right != null && !right.isScored) {
                    workList.add(right)
                    foundUnscoredNode = true
                }
            } while (workList.isNotEmpty())

        } while (foundUnscoredNode == true)
    }

    /**
     * pretty print the tree.
     */
    fun printTree(): List<String> {
        val outList: MutableList<String> = mutableListOf()
        p(root, outList)
        return outList
    }

    private fun p(n: Node, l: MutableList<String>) {

        if (n.left != null) {
            val str = StringBuilder()
            str.append(n.id)
            str.append("->")
            str.append(n.left!!.id)
            str.append(" ${n.dnaString} ${n.left!!.dnaString}")
            l.add(str.toString())
        }

        if (n.right != null) {
            val str = StringBuilder()
            str.append(n.id)
            str.append("->")
            str.append(n.right!!.id)
            str.append(" ${n.dnaString} ${n.right!!.dnaString}")
            l.add(str.toString())
        }

        if (n.left != null) {
            p(n.left!!, l)
        }

        if (n.right != null) {
            p(n.right!!, l)
        }
    }

    /**
     *
     */
    fun voteOnDnaStringsAndBuildChangeList(): List<DnaTransform> {
        val changeList: MutableList<DnaTransform> = mutableListOf()

        if (dnaLen == -1) {
            println("iterateNodes: global variable dnaLen is not initialized.  Giving up.")
            return changeList
        }

        // work from the root down, setting the dna strings on the way

        val workList: MutableList<Node> = mutableListOf()
        root.dnaString = sp.parsimoniousString(root.scoringArray!!)
        workList.add(root)

        do {
            val node = workList.removeFirst()
            if (node.isOutput) {
                println("Node ${node.id} is already output")
                continue
            }

            val left = node.left
            val right = node.right

            if (left!!.nodeType != LEAF) {
                // compose the dna strings for the child
                left.dnaString = sp.parsimoniusCompareString(node, left)
            }

            if (right != null && right.nodeType != LEAF) {
                // compose the dna strings for the child
                right.dnaString = sp.parsimoniusCompareString(node, right)
            }

            if (node == root) {
                // calculate the hamming distance from root to left child
                val hammingLeft = hammingDistance(left.dnaString!!, right!!.dnaString!!)
                totalHammingDistance += hammingLeft

                val c1 = DnaTransform(right.dnaString!!, left.dnaString!!, hammingLeft)
                val c2 = DnaTransform(left.dnaString!!, right.dnaString!!, hammingLeft)
                changeList.add(c1)
                changeList.add(c2)
            } else {
                // calculate the hamming distance from root to left child
                val hammingLeft = hammingDistance(left.dnaString!!, node.dnaString!!)
                totalHammingDistance += hammingLeft

                val c1 = DnaTransform(node.dnaString!!, left.dnaString!!, hammingLeft)
                val c2 = DnaTransform(left.dnaString!!, node.dnaString!!, hammingLeft)
                changeList.add(c1)
                changeList.add(c2)

                if (right != null) {
                    val hammingRight = hammingDistance(right.dnaString!!, node.dnaString!!)
                    totalHammingDistance += hammingRight

                    val c3 = DnaTransform(node.dnaString!!, right.dnaString!!, hammingRight)
                    val c4 = DnaTransform(right.dnaString!!, node.dnaString!!, hammingRight)
                    changeList.add(c3)
                    changeList.add(c4)
                }

            }

            if (!left.isOutput) {
                workList.add(left)
            }
            if (right != null && !right.isOutput) {
                workList.add(right)
            }

            node.isOutput = true

        } while (workList.size != 0)

        // check change list hamming distance and compare to accumulated hamming distance

        var checkHamming = 0
        for (entry in changeList) {
            checkHamming += entry.hammingDistance
        }
        checkHamming = checkHamming / 2
        //println("Accumulated Hamming $totalHammingDistance checks with $checkHamming")
        return changeList

    }

    /**
     * algorithm in English, rather than mathematics:
     * for each of the left and right {ACGT} columns
     *   find the min value
     * then for each of the {ACGT} value - if it equal
     * to the minimum copy it.
     * Otherwise bump it by one for the one letter change penalty.
     *
     * Then add the two intermediate arrays to get the result for the
     * next upper node.
     */
    fun scoreArrays(larr1: D2Array<Int>, rarr2: D2Array<Int>): D2Array<Int> {

        val resultArr = mk.d2array(4, dnaLen) { 0 }
        val resultLeft = mk.d2array(4, dnaLen) { 0 }
        val resultRight = mk.d2array(4, dnaLen) { 0 }

        if (dnaLen == -1) {
            println("scoreArrays: global variable dnaLen is not initialized.  Giving up.")
            return resultArr
        }

        for (i in 0 until dnaLen) {
            val minValLeft = larr1[0..4, i].min()
            val minValRight = rarr2[0..4, i].min()

            for (j in 0..3) {
                resultLeft[j, i] = larr1[j, i]
                if (larr1[j, i] != minValLeft) {
                    resultLeft[j, i] = minValLeft!! + 1
                }
            }
            for (j in 0..3) {
                resultRight[j, i] = rarr2[j, i]
                if (rarr2[j, i] != minValRight) {
                    resultRight[j, i] = minValRight!! + 1
                }
            }
            for (j in 0..3) {
                resultArr[j, i] = resultLeft[j, i] + resultRight[j, i]
            }
        }
        return resultArr
    }

    /**
     * algorithm in English, rather than mathematics:
     * for the left {ACGT} columns ONLY
     *   find the min value
     * then for each of the {ACGT} value - if it equal
     * to the minimum copy it.
     * Otherwise bump it by one for the one letter change penalty.
     *
     */
    fun scoreLeftArray(larr1: D2Array<Int>): D2Array<Int> {

        val resultArr = mk.d2array(4, dnaLen) { 0 }
        val resultLeft = mk.d2array(4, dnaLen) { 0 }
        val resultRight = mk.d2array(4, dnaLen) { 0 }

        if (dnaLen == -1) {
            println("scoreArrays: global variable dnaLen is not initialized.  Giving up.")
            return resultArr
        }

        for (i in 0 until dnaLen) {
            val minValLeft = larr1[0..4, i].min()

            for (j in 0..3) {
                resultLeft[j, i] = larr1[j, i]
                if (larr1[j, i] != minValLeft) {
                    resultLeft[j, i] = minValLeft!! + 1
                }
            }

            for (j in 0..3) {
                resultArr[j, i] = resultLeft[j, i]
            }
        }
        return resultArr
    }


    fun mkArrayWithScores(dnaString: String): D2Array<Int> {
        val len = dnaString.length
        val arr = mk.d2array(4, len) { Int.MAX_VALUE }
        for (i in 0 until len) {
            when (dnaString[i]) {
                'A' -> arr[0, i] = 0
                'C' -> arr[1, i] = 0
                'G' -> arr[2, i] = 0
                'T' -> arr[3, i] = 0
            }
        }
        return arr
    }

    fun mkArray(): D2Array<Int> {
        return mk.d2array(numLeaves, 4) { 0 }
    }

    fun hammingDistance(str1: String, str2: String): Int {

        var hammingCount = 0
        if (str1.length != str2.length) {
            println("ERROR hamming distance - strings do not match lengths str1 ${str1.length} str2 ${str2.length}")
            return 0
        }
        for (i in 0 until str1.length) {
            if (str1[i] != str2[i]) {
                hammingCount++
            }
        }
        return hammingCount
    }
}
