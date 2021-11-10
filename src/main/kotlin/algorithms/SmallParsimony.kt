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
import org.jetbrains.kotlinx.multik.ndarray.operations.min
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.hashMapOf
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
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
        var dnaString: String? = null,
        var left: Node? = null,
        var right: Node? = null,
        var scoringArray: D2Array<Int>? = null
    ) {
        override fun toString(): String {
            return "Node num $id type: $nodeType d:$dnaString l:${left?.id} r:${right?.id}"
        }
    }

    var numLeaves = 0
    val nodeMap: HashMap<Int, Node> = hashMapOf()
    var dnaLen = -1

    fun smallParsimonyStart(inputStrings: List<String>): MutableMap<String, MutableMap<String, Int>> {

        return mutableMapOf()
    }

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
    fun parseInputStrings(inputStrings: MutableList<String>) {
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
     */
    fun iterateNodes() {

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
                        val scoredArray = scoreArrays(left.scoringArray!!, right.scoringArray!!)
                        node.scoringArray = scoredArray
                        node.ripe = true
                    }
                }
            }
        } while (foundRipePair == true)
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


}
