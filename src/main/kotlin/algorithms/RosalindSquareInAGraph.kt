@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls",  "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER",  "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "RedundantIf", "LiftReturnOrAssignment"
)

package algorithms

/**
 * Square in a Graph
 * rosalind: http://rosalind.info/problems/sq/
 * Note: not part of the stepik cirriculum
 *
 * Similar to:
 *  * Testing Bipartiteness
 * rosalind: http://rosalind.info/problems/bip/
 */

class RosalindSquareInAGraph {

    /**
    Problem

    Given: A positive integer k≤20 and k simple undirected graphs
    with n≤400 vertices in the edge list format.

    Return: For each graph, output "1" if it contains a simple cycle
    (that is, a cycle which doesn’t intersect itself) of length 4
    and "-1" otherwise.

     */

    var visited: MutableList<Int> = mutableListOf() // nodes visited in an iteration

    /**
     * return -1 if odd cycle found, and 1 otherwise
     */
    fun squareInAGraph(g: HashMap<Int, MutableList<Int>>): Int {
        val keys = g.keys
        val min = keys.minOrNull()
        val max = keys.maxOrNull()

        for (i in min!! until max!!) {
            val depthMap: HashMap<Int, Int> = hashMapOf()
            visited.clear()
            if (findCycleWithFourEdges(0, i, i, g, depthMap)) {
                return 1
            }
        }
        return -1
    }

    /**
     * walk the graph from [currentNodeNum] to [targetNodeNum]
     *   using the [visited] list to track the navigation.
     *
     * @return true if a four edge cycle found, false otherwise
     */
    fun findCycleWithFourEdges(
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
            return false
        }

        for (n in g[currentNodeNum]!!) {
            /*
             * if we have found our target node, then check to see
             * if this is a 4-edge cycle
             */
            if (n == targetNodeNum) {
                if (depth + 1 == 4) {
                    return true
                } else {
                    return false
                }
            }
            if (visited.contains(n)) {
                continue
            }
            visited.add(n)
            depthMap[n] = depth + 1

            val result = findCycleWithFourEdges(depth + 1, n, targetNodeNum, g, depthMap)
            if (result) {
                return true
            }
        }
        return false
    }
}


