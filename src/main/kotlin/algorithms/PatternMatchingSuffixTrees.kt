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
     * Find the Longest Repeat in a String
     * Stepik: https://stepik.org/lesson/240378/step/5?unit=212724
     * Rosalind: https://rosalind.info/problems/ba9d/
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


    var nextNodeNum = 0

    inner class Node {
        val nodeNum = nextNodeNum++
        var internalNode = true
        val nodeMap: MutableMap<String, Node> = mutableMapOf()
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
        for (i in 1..tslen) {
            addToTree(tslen - i, theString.substring(tslen - i, tslen))
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
        compressNode(node)

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
        var curNode = node

        val keyList = map.keys.toMutableList()
        for (originalKey in keyList) {

            var nextNode = map[originalKey]!!
            var oldKey = originalKey

            // if the next node is ALSO a single node connection, then compress it
            while (nextNode.nodeMap.keys.size == 1) {

                val nextKey = nextNode.nodeMap.keys.first()

                val secondNode = nextNode.nodeMap[nextKey]!!

                val newKey = oldKey + nextKey
                node.nodeMap[newKey] = secondNode
                node.nodeMap.remove(oldKey)

                oldKey = newKey
                nextNode = secondNode

                secondNode.len = nextNode.len + secondNode.len

            }
        }
    }

    /**
     * repeats -
     *    for each recursively descend each key
     *    and accumulate the strings that connect to
     *    another key where the child key has more than
     *    one key of its own (namely it is repeated).
     *
     */
    fun longestRepeat(): String {
        val slist: MutableList<String> = mutableListOf()

        findRepeats(slist, root, "")

        return slist.maxByOrNull { it.length }!!
    }

    /**
     * if this [key] has multiple
     */
    fun findRepeats(slist: MutableList<String>, node: Node, keyString: String) {

        if (node.nodeMap.keys.size > 1) {
            for (key in node.nodeMap.keys) {
                var newKeyString = keyString
                if (node.nodeMap[key]!!.nodeMap.size > 1) {
                    // if this daughter node has more than one child, then this key is repeated
                    newKeyString = keyString + key
                    slist.add(newKeyString)
                }
                findRepeats(slist, node.nodeMap[key]!!, newKeyString)
            }
        }
    }

    /**
     * recursive print utility function
     */
    fun printTree(node: Node): List<String> {
        val slist: MutableList<String> = mutableListOf()
        pTree(slist, node)
        return slist
    }

    fun pTree(slist: MutableList<String>, node: Node): List<String> {

        for (key in node.nodeMap.keys) {
            slist.add(key)
            pTree(slist, node.nodeMap[key]!!)
        }
        return slist
    }

    /**
     * DEBUG print utility function
     */
    fun printTreeDebug(node: Node): List<String> {
        val slist: MutableList<String> = mutableListOf()
        pTreeDebug(slist, node)
        return slist
    }

    fun pTreeDebug(slist: MutableList<String>, node: Node): List<String> {

        slist.add(node.nodeNum.toString())
        slist.add(":")
        for (key in node.nodeMap.keys) {
            slist.add(key)
            slist.add("(" + node.nodeMap[key]!!.nodeNum.toString() + ") ")

        }
        slist.add("\n")
        for (key in node.nodeMap.keys) {

            pTreeDebug(slist, node.nodeMap[key]!!)
        }

        return slist
    }
}