@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.*
import java.lang.StringBuilder
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.hashMapOf
import kotlin.collections.removeFirst
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

class SmallParsimonyUnrootedTree {
    var verbose = false // for debug printout

    /**
    Code Challenge: Solve the Small Parsimony in an Unrooted Tree Problem.

    Input: An integer n followed by an adjacency list for an
    unrooted binary tree with n leaves labeled by DNA strings.

    Output: The minimum parsimony score of this tree, followed
    by the adjacency list of the tree corresponding to labeling
    internal nodes by DNA strings in order to minimize the parsimony
    score of the tree.
     */
    /**
     * Nodes are either a internal NODE, or a LEAF
     *   Internal NODEs have an id number, and have a Left and Right component defined.
     *   LEAF nodes have a dnaString defined and that is all.
     *  The scoringArray is calculated as given in the pseudoCode.
     */
    enum class NodeType { NODE, LEAF }
    data class Node(
        var nodeType: NodeType = NodeType.NODE,
        val id: Int,
        var isScored: Boolean = false,
        var isOutput: Boolean = false,
        var dnaString: String? = null,
        var left: Node? = null,
        var right: Node? = null,
        var hdLeft: Int = 0,
        var hdRight: Int = 0,
        var scoringArray: D2Array<Int>? = null
    ) {
        override fun toString(): String {
            return "Node num $id type: $nodeType d:$dnaString l:${left?.id} r:${right?.id}"
        }
    }

    data class DnaTransform(
        val str1: String,
        val str2: String,
        val hammingDistance: Int
    ) {
        override fun toString(): String {
            return "$str1->$str2:$hammingDistance"
        }
    }

    var numLeaves = 0

    // this is the map set up after parsing the input - node number to Node structure
    val nodeMapParsed: HashMap<Int, Node> = hashMapOf()

    // this is the map form the input that has Int->Int records
    // note that there will (hopefully) be two nodes that have only one connection in the list.
    //   These are the two end nodes.
    val edgeMap: HashMap<Int, MutableList<Int>> = hashMapOf()

    var dnaLen = -1
    var totalHammingDistance = 0
    lateinit var lastNode: Node

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

    Note: the parser simply ignores lines beginning with a codon
     */
    fun parseInputStrings(inputStrings: MutableList<String>) {

        numLeaves = inputStrings[0].toInt()
        inputStrings.removeFirst()

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
                if (nodeMapParsed.containsKey(nodeNum)) {
                    nodeStruct = nodeMapParsed[nodeNum]!!
                } else {
                    nodeStruct = Node(id = nodeNum)
                    nodeMapParsed[nodeNum] = nodeStruct
                }

                dnaLen = line[1].length
                val leafNode = Node(
                    nodeType = NodeType.LEAF,
                    id = 0,
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

        // complete the nodes in the edge list

        for (key in edgeMap.keys) {
            if (!nodeMapParsed.containsKey(key)) {
                val connList = edgeMap[key]

                if (connList == null || connList.size == 0) {
                    println("OOPSie1")
                    continue
                }
                val conns: List<Int> =
                    connList.filter {
                        nodeMapParsed.containsKey(it)
                    }

                if (conns.isEmpty()) {
                    println("oopsie")
                    continue
                }
                var right: Node? = null
                if (conns.size > 1) {
                    right = nodeMapParsed[conns[1]]
                }
                val newNode = Node(
                    nodeType = NodeType.NODE,
                    id = key,
                    left = nodeMapParsed[conns[0]],
                    right = right
                )
                nodeMapParsed[key] = newNode

            }
        }

        // now score all the nodes
        doScoring()

        // find end nodes

//        val ends: MutableList<Int> = mutableListOf()
//        for (key in edgeMap.keys) {
//            if (edgeMap[key]!!.size == 1) {
//                ends.add(key)
//            }
//        }

        // are any nodes not scored?

        for (n in nodeMapParsed.keys) {
            if (nodeMapParsed[n]!!.scoringArray == null) {
                println("not scored: node ${nodeMapParsed[n]!!.id}")
            } else {
                lastNode = nodeMapParsed[n]!!
            }
        }
    }


    /**
     * loop through the nodes until there are no unscored nodes
     * and all scoring matrices have been calculated.
     *
     * This is different from the SmallParsimony algo
     *   in that the nodes don't necessarily have a left and right child
     *
     */
    fun doScoring() {

        if (dnaLen == -1) {
            println("iterateNodes: global variable dnaLen is not initialized.  Giving up.")
            return
        }

        do {
            var foundUnscoredNode = false
            for (n in nodeMapParsed) {
                val node = n.value
                if (node.isScored) {
                    continue
                }
                // if this node doesn't have a pair of sub-nodes,
                //   then skip it
                if (node.right == null) {
                    println("node ${node.id} - no right side, skipping")
                    continue
                }
                if (node.left == null) {
                    println("node ${node.id} - no LEFT side, skipping")
                    continue
                }

                val left = node.left
                val right = node.right
                if (left!!.isScored && right!!.isScored) {
                    foundUnscoredNode = true

                    val scoredArray = scoreArrays(left.scoringArray!!, right.scoringArray!!)
                    node.scoringArray = scoredArray
                    node.isScored = true
                }

            }
        } while (foundUnscoredNode == true)
    }

    /**
     *
     */
    fun buildChangeList(): List<DnaTransform> {
        val changeList: MutableList<DnaTransform> = mutableListOf()
        if (dnaLen == -1) {
            println("iterateNodes: global variable dnaLen or lastNode is not initialized.  Giving up.")
            return changeList
        }

        // work from the root down, setting the dna strings on the way

        val workList: MutableList<Node> = mutableListOf(lastNode)
        lastNode.dnaString = parsimoniousString(lastNode.scoringArray!!)

        while (workList.isNotEmpty()) {
            val node = workList.removeFirst()

            // if no more work, then scan the edge list for the next node that
            //  hasn't been scored
            if (workList.isEmpty()) {
                val conn = edgeMap[lastNode.id]
                if (conn != null) {
                    for (n in conn) {
                        val nextNode = nodeMapParsed[n]!!
                        if (!nextNode.isOutput) {
                            workList.add(nextNode)
                            // TODO: calculate cross-DNA string here!!
                            nextNode.dnaString = parsimoniusCompareString(lastNode, nextNode)
                        }
                    }
                }
            }

            if (node.left != null && node.right != null) {
                val left = node.left
                val right = node.right

                if (left!!.nodeType != NodeType.LEAF) {
                    // compose the dna strings for the children
                    left.dnaString = parsimoniusCompareString(node, left)
                    right!!.dnaString = parsimoniusCompareString(node, right)
                }
                lastNode = node

                // calculate the hamming distance from root to children
                val hammingLeft = hammingDistance(left.dnaString!!, node.dnaString!!)
                val hammingRight = hammingDistance(right!!.dnaString!!, node.dnaString!!)
                node.hdLeft = hammingLeft
                node.hdRight = hammingRight
                totalHammingDistance += hammingLeft + hammingRight

                val c1 = DnaTransform(node.dnaString!!, left.dnaString!!, hammingLeft)
                val c2 = DnaTransform(left.dnaString!!, node.dnaString!!, hammingLeft)
                val c3 = DnaTransform(node.dnaString!!, right.dnaString!!, hammingRight)
                val c4 = DnaTransform(right.dnaString!!, node.dnaString!!, hammingRight)
                changeList.add(c1)
                changeList.add(c2)
                changeList.add(c3)
                changeList.add(c4)

                if (!left.isOutput) {
                    workList.add(left)

                }
                if (!right.isOutput) {
                    workList.add(right)

                }

                node.isOutput = true

            }
        }


        // are any nodes not printed?

        for (n in nodeMapParsed.keys) {
            if (!nodeMapParsed[n]!!.isOutput) {
                println("not printed: node ${nodeMapParsed[n]!!.id}")
            }
        }

        return changeList
    }

    /**
     *
     */
    fun parsimoniusCompareString(p: Node, c: Node): String {

        val parent = p.scoringArray!!
        val child = c.scoringArray!!

        val str = StringBuilder()
        for (i in 0 until dnaLen) {

            val mP = parent[0..4, i].min()
            val mC = child[0..4, i].min()

            val parentCodon = p.dnaString!![i]
            val pidx = "ACGT".indexOf(parentCodon)

            // if the parent codon letter is equal to a minimum score in the child,
            // then pass this codon to the child.
            if (child[pidx, i] == mC) {
                str.append(parentCodon)
                continue
            }

            val nP = parent[0..4, i].count { it == mP }
            val nC = child[0..4, i].count { it == mC }

            when {
                /*
                 * if there is one min value in the child,
                 * then choose that letter for the child.
                 */
                nC == 1 -> {
                    val letterNum = child[0..4, i].indexOf(mC!!)
                    val letter = "ACGT"[letterNum]
                    str.append(letter)
                }

                /*
                 * if there is one min value in the parent and the child,
                 * then choose the first letter where both are a min
                 */
                nP > 1 && nC > 1 -> {
                    var foundMatch = false
                    var letter = 'A'
                    for (j in 0..3) {
                        if (parent[j, i] == mP && child[j, i] == mC) {
                            letter = "ACGT"[j]
                            foundMatch = true
                            break
                        }
                    }
                    if (foundMatch == false) { // set to the first min value
                        val minIndex = child[0..4, i].indexOf(mC!!)
                        letter = "ACGT"[minIndex]
                    }
                    str.append(letter)
                }
                /*
                 * choose the character at the child's first scoring min value
                 */
                else -> {
                    val minIndex = child[0..4, i].indexOf(mC!!)
                    val letter = "ACGT"[minIndex]
                    str.append(letter)
                }
            }
        }
        return str.toString()
    }

    /**
     * for the parsimonious result array,
     *   build the DnaString that corresponds
     *   to the minimum array values.
     */
    fun parsimoniousString(arr: D2Array<Int>): String {
        val str = StringBuilder()
        for (i in 0 until dnaLen) {
            val minValLeft = arr[0..4, i].min()
            val letterNum = arr[0..4, i].indexOf(minValLeft as Int)
            val letter = "ACGT"[letterNum]
            str.append(letter)
        }
        return str.toString()
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
