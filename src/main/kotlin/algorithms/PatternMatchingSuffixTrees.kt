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
     * Wikipedia: Longest common substring problem
     * https://en.wikipedia.org/wiki/Longest_common_substring_problem
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
        var sourceBitMap = 0
        val nodeMap: MutableMap<String, Node> = mutableMapOf()
        var offset = -1
        var len = -1
    }

    val root = Node()
    var source = 1

    // for validation of non-shared string
    var string1: String = ""
    var string2: String = ""

    fun createSuffixTree(s1: String, s2: String = "") {
        root.offset = 0
        root.len = 0
        string1 = s1
        string2 = s2

        var theString = s1
        var tslen = theString.length
        source = 1  // to mark nodes derived from string 1
        // first add the letter of each substring to the tree
        for (i in 1..tslen) {
            addToTree(tslen - i, theString.substring(tslen - i, tslen))
        }

        if (s2.length != 0) {
            theString = s2
            tslen = theString.length
            source = 2  // to mark nodes derived from string 2
            // first add the letter of each substring to the tree
            for (i in 1..tslen) {
                addToTree(tslen - i, theString.substring(tslen - i, tslen))
            }
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
                curNode.sourceBitMap = curNode.sourceBitMap.or(source)
            } else {
                // add the letter to the tree
                val newNode = Node()
                curNode.nodeMap[c] = newNode
                newNode.offset = idx + i
                newNode.len = 1
                newNode.sourceBitMap = newNode.sourceBitMap.or(source)
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
     *
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
     * longest shared string -
     *    for each node recursively descend each key
     *    and accumulate the strings where the source
     *    is both string 1 and string 2 (source = 3).
     */
    fun longestSharedString(): String {
        val slist: MutableList<String> = mutableListOf("")

        findSharedStrings(slist, root, "")

        return slist.maxByOrNull { it.length }!!
    }

    /**
     *
     */
    fun findSharedStrings(slist: MutableList<String>, node: Node, keyString: String) {

        if (node.nodeMap.keys.size > 1) {
            for (key in node.nodeMap.keys) {
                var newKeyString: String
                if (node.nodeMap[key]!!.nodeMap.size > 1) {
                    // check that this node is sourced from both strings
                    if (node.nodeMap[key]!!.sourceBitMap == 3) {
                        newKeyString = keyString + key
                        //println("FOUND: $newKeyString")
                        slist.add(newKeyString)
                        findSharedStrings(slist, node.nodeMap[key]!!, newKeyString)
                    }
                }
            }
        }
    }

    /**
     * Stepik: https://stepik.org/lesson/240378/step/7?unit=212724
     * Rosalind: https://rosalind.info/problems/ba9f/
     * Shortest Non-Shared Substring Problem
     * Find the shortest substring of one string that does not appear in another string.
     *
     * Given: Strings Text1 and Text2.
     *
     * Return: The shortest substring of Text1 that does not
     * appear in Text2. (Multiple solutions may exist, in which case you may return any one.)
     */
    fun shortestNonsharedString(): String {
        val slist: MutableList<String> = mutableListOf()

        findNonsharedStrings(slist, root, "")

        //println(slist.sorted().joinToString(" "))
        val retVal = slist.minByOrNull { it.length }
        if (retVal == null) {
            return ""
        } else {
            return retVal
        }
    }

    /**
     *  Strategy: find a node that is shared, and connects to
     *    two nodes - one of which is string 1, and one of
     *    which is sourced from string 2.
     *
     *    Accumulate the first plus the string 1 node child.
     */
    fun findNonsharedStrings(slist: MutableList<String>, node: Node, keyString: String) {

        // iterate through the keys for this node.  (it might be the root node)
        for (key in node.nodeMap.keys) {
            // check to see if the child node is from both strings (source = 3)
            val thisChildNode = node.nodeMap[key]!!
            val sourceString = thisChildNode.sourceBitMap
            if (sourceString == 3) {
                // OK we have a shared node.   Check to see
                //   if there are the two child nodes that are needed.

                if (thisChildNode.nodeMap.size >= 2) {
                    val keys = thisChildNode.nodeMap.keys.toList()
                    var followonString = ""
                    for (k in keys) {

                        val n = thisChildNode.nodeMap[k]!!
                        if (n.sourceBitMap == 1) {
                            followonString = k

                            break
                        }
                    }
                    if (followonString != "" && followonString != "$") {
                        val nonSharedString = "$key$followonString"
                        validateCandidateString(nonSharedString, slist)
                    }
                }
            }
            findNonsharedStrings(slist, node.nodeMap[key]!!, "")
        }
    }

    /**
     * check to verify that the candidate non-shared string is:
     * 1) present in String 1
     * 2) absent in String 2
     * 3) as short as possible but not shorter
     */
    fun validateCandidateString(str: String, slist: MutableList<String>) {

        if (!string1.contains(str)) {
            println("Internal error : candidate is not present in String1")
            return
        }
        if (string2.contains(str)) {
            //println("Internal error: candate is present in String2")
            return
        }

        var baseString = str
        var shortestString = str
        // prune the string in both directions
        for (i in baseString.length - 1 downTo 1) {
            val shorterString = baseString.substring(0, i)
            if (string1.contains(shorterString) && !string2.contains(shorterString)) {
                shortestString = shorterString
            } else {
                break
            }
        }

        baseString = shortestString
        for (i in 1..baseString.length) {
            val shorterString = baseString.substring(i, baseString.length)
            if (string1.contains(shorterString) && !string2.contains(shorterString)) {
                shortestString = shorterString
            } else {
                break
            }
        }

        if (shortestString == "$") { // reject just a $
            return
        }

        //println(shortestString)
        slist.add(shortestString)
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
        slist.add(" Src:${node.sourceBitMap}")
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