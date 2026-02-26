@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "unused", "RemoveEmptyClassBody",
    "UNUSED_PARAMETER"
)

package algorithms

/**
Code Challenge: Implement MaximalNonBranchingPaths.

Input: The adjacency list of a graph whose nodes are integers.
Output: The collection of all maximal nonbranching paths in this graph.

 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba3m/
 */

/**
 *  PseudoCode - in a string so the formatter won't mess it up
 */
val pseudoCodeMaximalNonBranchingPaths = """
MaximalNonBranchingPaths(Graph)
    Paths ← empty list
    for each node v in Graph
        if v is not a 1-in-1-out node
            if out(v) > 0
                for each outgoing edge (v, w) from v
                    NonBranchingPath ← the path consisting of single edge (v, w)
                    while w is a 1-in-1-out node
                        extend NonBranchingPath by the edge (w, u) 
                        w ← u
                    add NonBranchingPath to the set Paths
    for each isolated cycle Cycle in Graph
        add Cycle to Paths
    return Paths
""".trimIndent()

class MaximalNonBranchingPaths {

    fun maximalNonBranchingPaths(connectionString: String): List<List<Int>> {
        val ep = EulerianPathSymbolicMap()

        val result = ep.eulerCycleMap(connectionString)
        val graph = ep.eulerCycleConvertData(result)
        ep.setGraph(graph)
        ep.initializeVariables()

        val graphWorking = graph.toMutableMap()
        val paths : MutableList<List<Int>> = mutableListOf()

        // traverse all nodes in original graph
        for (node in graph) {
            // look for an "unbalanced node" - edges in do not match edges out
            if (!(ep.inEdgesMap[node.key] == 1 && ep.outEdgesMap[node.key] == 1)) {
                graphWorking.remove(node.key)

                // for all outgoing edges
                for (dest in node.value) {
                    val nonBranchingPath: MutableList<Int> = mutableListOf(node.key, dest)
                    var nextDestNode = dest
                    // traverse any chain of nodes with one-in / one-out edges and add to the list
                    while (ep.inEdgesMap[nextDestNode] == 1 && ep.outEdgesMap[nextDestNode] == 1) {
                        val followingNode = graph[nextDestNode]!![0]
                        nonBranchingPath.add(followingNode)
                        nextDestNode = followingNode
                    }
                    paths.add(nonBranchingPath)
                }
            }
        }

        /*
         * unbalanced nodes have been removed, leaving only nodes with one-in and one-out edges.
         * Scan this list for a chain that forms a cycle and add it to the paths list.
         * Prune nodes as we go.
         */
        val graphIterator = graphWorking.toMutableMap()
        for (node in graphIterator) {
            val isolatedCycle: MutableList<Int> = mutableListOf(node.key)
            var nextNode = node.value[0]

            while (graphWorking.containsKey(nextNode)) {

                if (nextNode == node.key) {
                    isolatedCycle.add(node.key)
                    paths.add(isolatedCycle)
                    break
                }

                graphWorking.remove(nextNode)
                isolatedCycle.add(nextNode)
                nextNode = graphIterator[nextNode]!![0]
            }
        }

        return paths
    }
}
