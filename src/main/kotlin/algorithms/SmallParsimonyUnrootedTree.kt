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

open class SmallParsimonyUnrootedTree : SmallParsimony() {

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

    // the input lines that have Int -> Int
    val edgeMap: HashMap<Int, MutableList<Int>> = hashMapOf()
    var maxEdgeNum = 0

    // add all edges for use in Nearest Neighbor Interchange
    val allEdgesMap: HashMap<Int, MutableList<Int>> = hashMapOf()

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

        var nextLeafId = 0
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

                // this is a node -> node line, add to edge map

                val toNode = line[1].toInt()
                edgeMap.addTo(nodeNum, toNode)
                makeNodes(nodeNum, toNode)
                // add to all edges
                allEdgesMap.addTo(nodeNum, toNode)
            } else {

                // this an internal node connection to a leaf

                dnaLen = line[1].length
                val leafNode = Leaf(
                    id = nextLeafId++,
                    dnaString = line[1],
                    scoringArray = mkArrayWithScores(line[1])
                )
                leafMap[leafNode.id] = leafNode
                val parentNode = fetchNode(nodeNum)
                parentNode.leafList.add(leafNode.id)

                // add to all edges
                allEdgesMap.addTo(nodeNum, leafNode.id)
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
            id = maxEdgeNum + 1
        )

        val oldToNode = maxEdgeNum
        val oldFromNode = maxEdgeNum-1
        // prune out the edges between maxEdgeNum (at index iE)
        // and maxEdgeNum-1 (at index iEm1)

        val iE = edgeMap[maxEdgeNum]!!.indexOf(maxEdgeNum - 1)
        edgeMap[maxEdgeNum]!!.removeAt(iE)

        val iEm1 = edgeMap[maxEdgeNum - 1]!!.indexOf(maxEdgeNum)
        edgeMap[maxEdgeNum - 1]!!.removeAt(iEm1)

        root.left = nodeMap[oldFromNode]
        root.right = nodeMap[oldToNode]

        val visited: MutableList<Int> = mutableListOf()
        buildTree(root.left!!, visited)
        buildTree(root.right!!, visited)

    }

    /**
     * follow list of connections for node [n] in the
     * [edgeMap] list and build the connections in the nodes.
     */
    fun buildTree(n: Node, visited: MutableList<Int>) {
        if (edgeMap[n.id] == null || edgeMap[n.id]!!.size == 0) {
            return
        }
        visited.add(n.id)
        for (nodeId in edgeMap[n.id]!!) {
            if (visited.contains(nodeId)) { // don't save reverse links
                continue
            }
            if (nodeId >= numLeaves) { // if this is a node, not a leaf
                when {
                    n.left == null -> {
                        n.left = nodeMap[nodeId]
                        buildTree(n.left!!, visited)
                    }
                    n.right == null -> {
                        n.right = nodeMap[nodeId]
                        buildTree(n.right!!, visited)
                    }
                }
            }
        }
    }

    /**
     * descend ITERATIVELY through the nodes
     * starting at the "injected" root
     * until there are no unscored nodes
     * and therefore all scoring matrices
     * have been calculated.
     *
     * NOTE: the scores are additive - so add up the child nodes and then
     *   add in any leaf node scores.
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
                if (node.scoringArray == null) {
                    node.scoringArray = mk.d2array(4, dnaLen) { 0 }
                }
                foundUnscoredNode = true

                when {
                    left != null && left.isScored == false -> {
                        workList.add(left)
                        continue
                    }
                    right != null && right.isScored == false -> {
                        workList.add(right)
                        continue
                    }
                }
                if (left != null && left.isScored) {
                    node.scoringArray = scoreArrays(left.scoringArray!!, node.scoringArray!!)

                }
                if (right != null && right.isScored) {
                    node.scoringArray = scoreArrays(right.scoringArray!!, node.scoringArray!!)

                }
                for (leafId in node.leafList) {
                    val leaf = leafMap[leafId]!!
                    node.scoringArray = scoreArrays(leaf.scoringArray!!, node.scoringArray!!)
                }
                node.isScored = true
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

        for (leaf in n.leafList) {
            val str = StringBuilder()
            str.append(n.id)
            str.append("->")
            str.append(leafMap[leaf]!!.id)
            str.append(" ${n.dnaString} ${leafMap[leaf]!!.dnaString}")
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
        root.dnaString = parsimoniousString(root.scoringArray!!)
        workList.add(root)

        do {
            val node = workList.removeFirst()
            if (node.isOutput) {
                println("Node ${node.id} is already output")
                continue
            }

            val left = node.left
            val right = node.right

            // compose the dna strings for the child
            if (left != null) {
                left.dnaString = parsimoniusCompareString(node, left)
            }

            if (right != null) {
                // compose the dna strings for the child
                right.dnaString = parsimoniusCompareString(node, right)
            }

            // special case the root
            if (node == root) {

                val hammingRoot = hammingDistance(left!!.dnaString!!, right!!.dnaString!!)
                totalHammingDistance += hammingRoot

                val c1 = DnaTransform(right.dnaString!!, left.dnaString!!, hammingRoot)
                val c2 = DnaTransform(left.dnaString!!, right.dnaString!!, hammingRoot)
                changeList.add(c1)
                changeList.add(c2)


            } else {

                if (left != null) {
                    val hammingLeft = hammingDistance(left.dnaString!!, node.dnaString!!)
                    totalHammingDistance += hammingLeft

                    val c1 = DnaTransform(node.dnaString!!, left.dnaString!!, hammingLeft)
                    val c2 = DnaTransform(left.dnaString!!, node.dnaString!!, hammingLeft)
                    changeList.add(c1)
                    changeList.add(c2)
                }


                if (right != null) {
                    val hammingRight = hammingDistance(right.dnaString!!, node.dnaString!!)
                    totalHammingDistance += hammingRight

                    val c3 = DnaTransform(node.dnaString!!, right.dnaString!!, hammingRight)
                    val c4 = DnaTransform(right.dnaString!!, node.dnaString!!, hammingRight)
                    changeList.add(c3)
                    changeList.add(c4)
                }

                // now go through the leaf list
                for (leafId in node.leafList) {
                    val hammingLeaf = hammingDistance(leafMap[leafId]!!.dnaString!!, node.dnaString!!)
                    totalHammingDistance += hammingLeaf

                    val c3 = DnaTransform(node.dnaString!!, leafMap[leafId]!!.dnaString!!, hammingLeaf)
                    val c4 = DnaTransform(leafMap[leafId]!!.dnaString!!, node.dnaString!!, hammingLeaf)
                    changeList.add(c3)
                    changeList.add(c4)
                }

            }

            if (left != null && !left.isOutput) {
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
     * for the left {ACGT} columns ONLY
     *   find the min value
     * then for each of the {ACGT} value - if it equal
     * to the minimum copy it.
     * Otherwise bump it by one for the one letter change penalty.
     *
     */
    fun scoreArray(childArray: D2Array<Int>, parentArray: D2Array<Int>) {

        val resultArr = mk.d2array(4, dnaLen) { 0 }
        val result = mk.d2array(4, dnaLen) { 0 }

        if (dnaLen == -1) {
            println("scoreArrays: global variable dnaLen is not initialized.  Giving up.")
        }

        for (i in 0 until dnaLen) {
            val minVal = childArray[0..4, i].min()

            for (j in 0..3) {
                result[j, i] = childArray[j, i]
                if (childArray[j, i] != minVal) {
                    result[j, i] = minVal!! + 1
                }
            }

            for (j in 0..3) {
                resultArr[j, i] = result[j, i]
            }

            for (j in 0..3) {
                parentArray[j, i] += childArray[j, i]
            }
        }

    }

    /**
     * create the nodes [fromNode] and [toNode] if necessary
     */
    fun makeNodes(fromNode: Int, toNode: Int) {
        if (!nodeMap.containsKey(fromNode)) {
            nodeMap[fromNode] = Node(id = fromNode)
        }
        if (!nodeMap.containsKey(toNode)) {
            nodeMap[toNode] = Node(id = toNode)
        }
    }

    /**
     * create the node [nodeNum] if necessary and return it
     */
    fun fetchNode(nodeNum: Int): Node {
        if (nodeMap.containsKey(nodeNum)) {
            return nodeMap[nodeNum]!!
        }
        val newNode = Node(
            id = nodeNum,
            scoringArray = mk.d2array(4, dnaLen) { 0 }
        )
        nodeMap[nodeNum] = newNode
        return newNode
    }

}
