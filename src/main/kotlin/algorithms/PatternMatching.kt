@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

class PatternMatching {

    /**
     * See also:
     * Stepik: https://stepik.org/lesson/240376/step/4?unit=212722
     * Rosalind: https://rosalind.info/problems/ba9a/
     *
     * Using the Trie 2/10
     * https://www.youtube.com/watch?v=9U0ynguwoNA
     *
     * From a Trie to a Suffix Tree (3/10)
     * https://www.youtube.com/watch?v=LB-ANFydv30
     */


    /**
     * Code Challenge: Solve the Trie Construction Problem.
     *
     * Input: A space-separated collection of strings Patterns.
     *
     * Output: The adjacency list corresponding to Trie(Patterns),
     * in the following format. If Trie(Patterns) has n nodes, first
     * label the root with 0 and then label the remaining nodes with
     * the integers 1 through n - 1 in any order you like.
     * Each edge of the adjacency list of Trie(Patterns)
     * will be encoded by a triple: the first two members of the
     * triple must be the integers labeling the initial and terminal
     * nodes of the edge, respectively; the third member of the triple
     * must be the symbol labeling the edge.
     */

    fun trieConstruction(patterns: List<String>): Map<Int, Map<Char, Int>> {

        // for each node - the list of characters at that node
        val trieDictionary: MutableMap<Int, MutableList<Char>> = mutableMapOf()

        // for each node - what node number the characters map to
        val trieMap: MutableMap<Int, MutableMap<Char, Int>> = mutableMapOf()

        var nextNodeNum = 1
        for (p in patterns) {
            var currentNode = 0
            outerloop@ for (idx in 0 until p.length) {

                var c = p[idx]
                val clist = trieDictionary[currentNode]
                if (clist == null || !clist.contains(c)) {

                    // if this is the first character, make a connection
                    //   from the root node (0) to the first
                    //   character of this string
                    if (idx == 0) {
                        if (trieMap.containsKey(0)) {
                            trieMap[0]!![c] = nextNodeNum
                        } else {
                            trieMap[0] = mutableMapOf(Pair(c, nextNodeNum))
                        }
                        trieDictionary.addTo(0, c)
                    } else {
                        // the character is not in the dictionary at this level.
                        //   Add the character to the dictionary for this node, and then
                        //   work through the rest of the string as a new set of nodes
                        trieDictionary.addTo(currentNode, c)
                        trieMap[currentNode]!![c] = nextNodeNum
                    }

                    // now create new nodes for all the remaining letters in the string

                    for (idx2 in idx + 1 until p.length) {
                        c = p[idx2]
                        trieDictionary.addTo(nextNodeNum, c)
                        trieMap[nextNodeNum] = mutableMapOf(Pair(c, nextNodeNum+1))
                        nextNodeNum++
                    }
                    nextNodeNum++
                    break@outerloop
                } else {
                    // found the character in the dictionary.
                    //   look up the next index
                    currentNode = trieMap[currentNode]!![c]!!
                }
            }
        }
        return trieMap
    }


}