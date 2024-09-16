@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls",  "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER",  "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "RedundantIf", "LiftReturnOrAssignment"
)

package algorithms

/**
 *
 * Testing Bipartiteness
 * rosalind: http://rosalind.info/problems/bip/
 * Note: not part of the stepik cirriculum
 */

class RosalindBipartiteGraph {

    /**
    Problem

    A bipartite graph is a graph G=(V,E) whose vertices can be partitioned
    into two sets (V=V1∪V2 and V1∩V2=∅) such that there are no edges between
    vertices in the same set (for instance, if u,v∈V1, then there is no edge between u and v).

    There are many other ways to formulate this property. For instance,
    an undirected graph is bipartite if and only if it can be colored with
    just two colors. Another one: an undirected graph is bipartite if and
    only if it contains no cycles of odd length.

    Given: A positive integer k≤20 and k simple graphs in the
    edge list format with at most 103 vertices each.

    Return: For each graph, output "1" if it is bipartite and "-1" otherwise.

     */

    var visited: MutableList<Int> = mutableListOf() // nodes visited in an iteration

    /**
     * return -1 if odd cycle found, and 1 otherwise
     */
    fun testBipartiteness(g: HashMap<Int, MutableList<Int>>): Int {
        val keys = g.keys
        val min = keys.minOrNull()
        val max = keys.maxOrNull()

        for (i in min!! until max!!) {
            val depthMap: HashMap<Int, Int> = hashMapOf()
            visited.clear()
            if (findOddCyclesRecursive(0, i, i, g, depthMap)) {
                return -1
            }
        }
        return 1
    }

    /**
     * walk the graph from [currentNodeNum] to [targetNodeNum]
     *   using the [visited] list to track the navigation.
     *
     * @return true if odd cycle found, false otherwise
     */
    fun findOddCyclesRecursive(
        depth: Int,
        currentNodeNum: Int,
        targetNodeNum: Int,
        g: HashMap<Int, MutableList<Int>>,
        depthMap: HashMap<Int, Int>
    ): Boolean {

        visited.add(currentNodeNum)
        depthMap[currentNodeNum] = depth

        if (g[currentNodeNum] == null) {
            println("OOPSIE")
        }
        if (g[currentNodeNum]!!.contains(targetNodeNum) && depth > 1) {
            if ((depth + 1) % 2 == 1) {
                return true
            } else {
                return false
            }

        }
        for (n in g[currentNodeNum]!!) {

            if (visited.contains(n)) {
                if (depthMap[n] == null) {
                    println("OOPSIE")
                }
                val diff = depth - depthMap[currentNodeNum]!!
                if (diff % 2 == 1) {
                    return true
                }
                continue
            }
            visited.add(n)
            depthMap[n] = depth + 1

            val result = findOddCyclesRecursive(depth + 1, n, targetNodeNum, g, depthMap)
            if (result) {
                return true
            }
        }
        return false
    }
}

