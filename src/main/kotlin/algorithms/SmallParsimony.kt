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
 * Stepik: https://stepik.org/lesson/240342/step/10?unit=212688
 * Rosalind: http://rosalind.info/problems/ba7f/
 * Youtube: https://www.youtube.com/watch?v=h515dSZWEyM
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

class SmallParsimony {
    var verbose = false // for debug printout

    /**

    Code Challenge: Implement SmallParsimony to solve the Small Parsimony Problem.

    Input: An integer n followed by an adjacency list for a rooted binary
    tree with n leaves labeled by DNA strings.

    Output: The minimum parsimony score of this tree,
    followed by the adjacency list of a tree corresponding to
    labeling internal nodes by DNA strings in order to minimize
    the parsimony score of the tree.  You may break ties however you like.

    Note: Remember to run SmallParsimony on each individual index
    of the strings at the leaves of the tree.
     */

    private val pseudoCode = """
    SmallParsimony(T, Character)
    for each node v in tree T
        Tag(v) ← 0
        if v is a leaf
            Tag(v) ← 1
            for each symbol k in the alphabet
                if Character(v) = k
                    sk(v) ← 0
                else
                    sk(v) ← ∞
    while there exist ripe nodes in T
        v ← a ripe node in T
        Tag(v) ← 1
        for each symbol k in the alphabet
            sk(v) ← minimumall symbols i {si(Daughter(v))+αi,k} + minimumall symbols j {sj(Son(v))+αj,k}
    return minimum over all symbols k {sk(v)}
    """.trimIndent()

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
        var ripe: Boolean = false,
        var isScored: Boolean = false,
        var isOutput: Boolean = false,
        var dnaString: String? = null,
        var left: Node? = null,
        var right: Node? = null,
        var hdLeft : Int = 0,
        var hdRight : Int = 0,
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
    val nodeMap: HashMap<Int, Node> = hashMapOf()
    var dnaLen = -1
    var totalHammingDistance = 0
    var lastNode: Node? = null

    /**
     * parse the test input:
     * Input: An integer n followed by an adjacency list for a
     * rooted binary tree with n leaves labeled by DNA strings.
     * example:
    4
    4->CAAATCCC
    4->ATTGCGAC
    5->CTGCGCTG
    5->ATGGACGA
    6->4
    6->5
     */
    fun parseInputStringsRooted(inputStrings: MutableList<String>) {
//        val outputList: MutableList<Node> = mutableListOf()

        numLeaves = inputStrings[0].toInt()

        inputStrings.removeFirst()
        while (inputStrings.size > 0) {
            val line = inputStrings[0].split("->")
            val nodeNum = line[0].toInt()
            var nodeStruct: Node
            if (nodeMap.containsKey(nodeNum)) {
                nodeStruct = nodeMap[nodeNum]!!
            } else {
                nodeStruct = Node(id = nodeNum)
                nodeMap[nodeNum] = nodeStruct
//                outputList.add(nodeStruct)
            }
            val codon = "ACGT".indexOf(line[1][0])
            // see if this a node to node connection, or a DNA string definition
            if (codon == -1) {
                // this is a node to internal node line
                val edgeTo = line[1].toInt()
                var edgeToNode: Node
                if (!nodeMap.containsKey(edgeTo)) {
                    val newNode = Node(id = edgeTo)
                    nodeMap[edgeTo] = newNode
//                    outputList.add(newNode)
                    edgeToNode = newNode
                } else {
                    edgeToNode = nodeMap[edgeTo]!!
                }
                if (nodeStruct.left == null) {
                    nodeStruct.left = edgeToNode
                } else {
                    nodeStruct.right = edgeToNode
                }
            } else {
                // this is a dnaString
                dnaLen = line[1].length
                val leafNode = Node(
                    nodeType = NodeType.LEAF,
                    id = 0,
                    ripe = true,
                    dnaString = line[1],
                    scoringArray = mkArrayWithScores(line[1])
                )
//                outputList.add(leafNode)
                if (nodeStruct.left == null) {
                    nodeStruct.left = leafNode
                } else {
                    nodeStruct.right = leafNode
                }
            }
            inputStrings.removeFirst()
        }
//        return outputList
    }

    /**
     * loop through the nodes until there are no ripe pairs left,
     * and all scoring matrices have been calculated
     *
     * Note: also sets lastNode - this will be the root of
     * the binary tree at the end
     */
    fun doScoring() {

        if (dnaLen == -1) {
            println("iterateNodes: global variable dnaLen is not initialized.  Giving up.")
            return
        }

        do {
            var foundRipePair = false
            for (n in nodeMap) {
                val node = n.value
                if (node.scoringArray != null) {
                    continue
                }
                if (node.left != null && node.right != null) {
                    val left = node.left
                    val right = node.right
                    if (left!!.ripe && right!!.ripe) {
                        foundRipePair = true
                        lastNode = node
                        val scoredArray = scoreArrays(left.scoringArray!!, right.scoringArray!!)
                        node.scoringArray = scoredArray
//                        val bestString = parsimoniousString(scoredArray)
//                        node.dnaString = bestString
                        node.ripe = true
                    }
                }
            }
        } while (foundRipePair == true)
    }

    /**
     * loop again through the nodes
     *   starting with lastNode.
     *
     * find the dna letters that result in the
     * minimum change in the children nodes.
     */
    fun buildChangeList(): List<DnaTransform> {
        val changeList: MutableList<DnaTransform> = mutableListOf()
        if (dnaLen == -1 || lastNode == null) {
            println("iterateNodes: global variable dnaLen or lastNode is not initialized.  Giving up.")
            return changeList
        }

        // work from the root down, setting the dna strings on the way

        val workList: MutableList<Node> = mutableListOf(lastNode!!)
        lastNode!!.dnaString = parsimoniousString(lastNode!!.scoringArray!!)

        while (workList.isNotEmpty()) {
            val node = workList.removeFirst()

            if (node.left != null && node.right != null) {
                val left = node.left
                val right = node.right

                if (left!!.nodeType != NodeType.LEAF) {
                    // compose the dna strings for the children
                    left.dnaString = parsimoniusCompareString(node, left)
                    right!!.dnaString = parsimoniusCompareString(node, right)
                }

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

                workList.add(left)
                workList.add(right)

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
                    for (j in 3 downTo 0) {
                        if (parent[j, i] == mP && child[j, i] == mC) {
                            letter = "ACGT"[j]
                            foundMatch = true
                            break
                        }
                    }
                    if (foundMatch == false) { // set to the first min value
                        val minIndex = child[0..4,i].indexOf(mC!!)
                        letter = "ACGT"[minIndex]
                    }
                    str.append(letter)
                }
                /*
                 * choose the character at the child's first scoring min value
                 */
                else -> {
                    val minIndex = child[0..4,i].indexOf(mC!!)
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
            val score = arr[0..4, i].toList()
            val letterNum = lastMin(score)
            val letter = "ACGT"[letterNum]
            str.append(letter)
        }
        return str.toString()
    }

    fun lastMin(l: List<Int>): Int {
        var m = Int.MAX_VALUE
        var minIndex = -1
        for (i in 0 until l.size) {
            if (l[i] <= m) {
                m = l[i]
                minIndex = i
            }
        }
        return minIndex
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
