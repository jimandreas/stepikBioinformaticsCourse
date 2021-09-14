@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "unused", "RemoveEmptyClassBody",
    "UNUSED_PARAMETER"
)

package algorithms

/**
Contig Generation Problem: Generate the contigs from a collection of reads (with imperfect coverage).

Input: A collection of k-mers Patterns.
Output: All contigs in DeBruijn(Patterns).

 * See also:
 * stepik: @link: https://stepik.org/lesson/240263/step/5?unit=212609
 * rosalind: @link: http://rosalind.info/problems/ba3k/
 */


class ContigGeneration {

    fun contigGeneration(kMerList: List<String>): List<String> {

        val eps = EulerianPathStrings()

        val graph = deBruijnGraphFromKmers(kMerList)
        eps.setGraph(graph)
        eps.initializeVariables()

        val graphWorking = graph.toMutableMap()
        val paths: MutableList<List<String>> = mutableListOf()

        // traverse all nodes in original graph
        for (node in graph) {
            // look for an "unbalanced node" - edges in do not match edges out
            if (!(eps.inEdgesMap[node.key] == 1 && eps.outEdgesMap[node.key] == 1)) {
                graphWorking.remove(node.key)

                // for all outgoing edges
                for (dest in node.value) {
                    val nonBranchingPath: MutableList<String> = mutableListOf(node.key, dest)
                    var nextDestNode = dest
                    // traverse any chain of nodes with one-in / one-out edges and add to the list
                    while (eps.inEdgesMap[nextDestNode] == 1 && eps.outEdgesMap[nextDestNode] == 1) {
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
            val isolatedCycle: MutableList<String> = mutableListOf(node.key)
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

        // re-concatenate the resulting kmer lists - just adding the last letter of
        //   the second and subsequent strings
        val outList: MutableList<String> = mutableListOf()

        for (p in paths) {
            val str = StringBuilder()
            val len = p[0].length
            str.append(p[0])
            for (i in 1 until p.size) {
                str.append(p[i].substring(len - 1, len))
            }
            outList.add(str.toString())
        }
        return outList.sorted()

    }
}
