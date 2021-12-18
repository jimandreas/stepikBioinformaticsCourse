@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

class PatternMatchingSuffixTrees {

    /**
     * Construct the Suffix Tree of a String
     * Stepik: https://stepik.org/lesson/240378/step/4?unit=212724
     * Rosalind: https://rosalind.info/problems/ba9c/
     *
     * SEE ALSO for a tree example:
     * https://stepik.org/lesson/240388/step/2?unit=212734
     * also:
     * https://www.bioinformaticsalgorithms.org/faq-chapter-9
     * (see :  What are the edge labels in the suffix tree for "panamabananas$"?)
     *
     * Using the Trie 2/10
     * https://www.youtube.com/watch?v=9U0ynguwoNA
     *
     * From a Trie to a Suffix Tree (3/10)
     * https://www.youtube.com/watch?v=LB-ANFydv30
     *
     * String Compression and the Burrows-Wheeler Transform (4/10)
     * https://www.youtube.com/watch?v=G7YBi04HOEY
     *
     * Inverting Burrows-Wheeler (5/10)
     * https://www.youtube.com/watch?v=DqdjbK68l3s
     */


    /**
     * Code Challenge: Solve the Suffix Tree Construction Problem.I
     *
     * Input: A string Text.
     *
     * Output: A space-separated list of the edge labels of SuffixTree(Text). You may return these strings in any order.
     */

    class Node {
        var internalNode = true
        val nodeMap : MutableMap<String, Node> = mutableMapOf()
        var offset = -1
        var len = -1
    }

    val root = Node()

    var theString = ""

    fun createSuffixTree(s: String) {
        theString = s
        val tslen = theString.length
        root.offset = 0
        root.len = 0

        // first add the letter of each substring to the tree
        for (i in 1 ..tslen) {
            addToTree(tslen-i, theString.substring(tslen-i, tslen))
        }

        // now compress the tree by collapsing single-node connections
        compressTree(root)
    }

    /**
     * add each character in the substring to the tree.
     */
    fun addToTree(idx: Int, substring: String) {
        var curString = substring

        var curNode = root

        for (i in 0 until curString.length) {
            val c = curString[i].toString()
            // if the letter is already in the tree, just follow the tree
            if (curNode.nodeMap.containsKey(c)) {
                curNode = curNode.nodeMap[c]!!
            } else {
                // add the letter to the tree
                val newNode = Node()
                curNode.nodeMap[c] = newNode
                newNode.offset = idx + i
                newNode.len = 1
                curNode = newNode
            }
        }
    }

    /**
     * traverse the tree and compress the nodes
     */
    fun compressTree(node: Node) {
        if (node.nodeMap.keys.size > 0) {
            for (key in node.nodeMap.keys) {
                compressNode(node.nodeMap[key]!!)
            }

            // repeat the for loop as the key set may have changed

            for (key in node.nodeMap.keys) {
                compressTree(node.nodeMap[key]!!)
            }
        }
    }

    /**
     * compress nodes that have a single-node connection to the
     * next node
     */
    fun compressNode(node: Node) {
        val map = node.nodeMap
        // if this node is not a single node connection, then don't go on
        if (map.keys.size != 1) {
            return
        }

        var curNode = node
        var curKey = curNode.nodeMap.keys.first()
        var nextNode = curNode.nodeMap[curKey]!!


        // if the next node is ALSO a single node connection, then compress it
        while (nextNode.nodeMap.keys.size == 1) {

            val nextKey = nextNode.nodeMap.keys.first()

            val secondNode = nextNode.nodeMap[nextKey]!!

            val newKey = node.nodeMap.keys.first() + nextKey
            node.nodeMap[newKey] = secondNode

            nextNode = secondNode

            node.len = node.len + nextNode.len

        }

    }

    /**
     * recursive print utility function for debugging
     */
    fun printTree(node: Node) {
        val curString = theString.substring(node.offset, node.offset+node.len)
        print(curString)
        if (node.nodeMap.keys.size != 0) {
            println(" ->")
            for (key in node.nodeMap.keys) {
                printTree(node.nodeMap[key]!!)
            }
        } else {
            println("")
        }
    }
}