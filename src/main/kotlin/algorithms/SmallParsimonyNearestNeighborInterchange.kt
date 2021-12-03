@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

import okhttp3.internal.toImmutableMap
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk

/**
 *
 * See also:
 * Stepik: https://stepik.org/lesson/240343/step/8?unit=212689
 * Rosalind: no equivalent problem
 *
 * Youtube:
 * Searching Tree Space (Anders Gorm Pedersen)
 * https://youtu.be/deywW9wJXmw


The nearest neighbor interchange heuristic for the
Large Parsimony Problem starts from an arbitrary
unrooted binary tree. It assigns input strings to
arbitrary leaves of this tree, assigns strings to
the internal nodes of the tree by solving the
Small Parsimony Problem in an Unrooted Tree,
and then moves to a nearest neighbor that provides
the best improvement in the parsimony score.
At each iteration, the algorithm explores all
internal edges of a tree and generates all nearest
neighbor interchanges for each internal edge. For each
of these nearest neighbors, the algorithm solves the
Small Parsimony Problem to reconstruct the labels of the
internal nodes and computes the parsimony score.
If a nearest neighbor with smaller parsimony score is found,
then the algorithm selects the one with smallest parsimony score
(ties are broken arbitrarily) and iterates again; otherwise, the algorithm terminates.

 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

class SmallParsimonyNearestNeighborInterchange : SmallParsimonyNearestNeighborsOfTree() {

    /**

    Code Challenge: Implement the nearest neighbor interchange
    heuristic for the Large Parsimony Problem.

    Input: An integer n, followed by an adjacency list
    for an unrooted binary tree whose n leaves are labeled by
    DNA strings and whose internal nodes are labeled by integers.

    Output: The parsimony score and unrooted labeled tree obtained
    after every step of the nearest neighbor interchange heuristic.
    Each step should be separated by a blank line.
     */

    val psuedoCode = """
        NearestNeighborInterchange(Strings)
     score ← ∞
     generate an arbitrary unrooted binary tree Tree with |Strings| leaves
     label the leaves of Tree by arbitrary strings from Strings
     solve  the  Small Parsimony in an Unrooted Tree Problem for Tree
     label the internal nodes of Tree according to a most parsimonious labeling
     newScore ← the parsimony score of Tree
     newTree ← Tree
     while newScore < score
          score ← newScore
          Tree ← newTree
          for each internal edge e in Tree
               for each nearest neighbor NeighborTree of Tree with respect to the edge e
                    solve the Small Parsimony in an Unrooted Tree Problem for NeighborTree
                    neighborScore ← the minimum parsimony score of NeighborTree
                    if neighborScore < newScore
                         newScore ← neighborScore
                         newTree ← NeighborTree
     return newTree
    """.trimIndent()

    /**
     * first calculate a base hamming distance value.
     *   Then perform a nearest neighbor break on all edges,
     *   and recalculate the hamming distance.   For each
     *   winning iteration save the changes.
     */

    /**
     * [resultDnaTransformList] - the dna string from / to list required by the problem
     * [resultHammingDistance] - the associated hamming distance with the above list
     * [resultEdgeList] - the "winning" list of edges resulting in a lower hamming distance than the previous set
     */
    val resultDnaTransformList: MutableList<List<DnaTransform>> = mutableListOf()
    val resultHammingDistance: MutableList<Int> = mutableListOf()
    val resultEdgeList: MutableList<MutableMap<Int, MutableList<Int>>> = mutableListOf()

    fun nearestNeighborExchangeHeuristic() {
        if (allEdgesMap.isEmpty()) {
            println("nearestNeighborExchangeHeuristic: ERROR tree must be parsed first")
            return
        }

        // get the baseline hamming distance
        voteOnDnaStringsAndBuildChangeList(outputRoot = false)
        val baseHammingDistance = totalHammingDistance
        println("initial hamming distance: $baseHammingDistance")

        var baseEdgesMap = allEdgesMap.deepCopy()

        // build a tree just from edges (experiment)
        buildTreeFromEdges(allEdgesMap)
        doUnrootedTreeScoring()
        voteOnDnaStringsAndBuildChangeList(outputRoot= false)

        println("What is distance now? : $totalHammingDistance")
        // the distances *should* match
        if (baseHammingDistance != totalHammingDistance) {
            println("nearestNeighborExchangeHeuristic: base hamming $baseHammingDistance doesn't match $totalHammingDistance")
        }

        var minHammingDistance = baseHammingDistance
        do {

            var foundNewMin = false

            // iterate through all edges
            for (fromNodeId in baseEdgesMap.keys.sorted().filter { it >= numLeaves}) {
                for (toNodeId in baseEdgesMap[fromNodeId]!!) {
                    if (toNodeId < numLeaves) {
                        continue
                    }
                    val twoCandidateMaps = twoNearestNeighbors(fromNodeId, toNodeId, baseEdgesMap.toMutableMap())
                    
                    buildTreeFromEdges(twoCandidateMaps[0])
                    doUnrootedTreeScoring()
                    val outputParsimonyList0 = voteOnDnaStringsAndBuildChangeList(outputRoot= false)
                    val outputHammingDistance0 = totalHammingDistance
                    val saveMap0 = twoCandidateMaps[0].toMutableMap()

                    buildTreeFromEdges(twoCandidateMaps[1])
                    doUnrootedTreeScoring()
                    val outputParsimonyList1 = voteOnDnaStringsAndBuildChangeList(outputRoot= false)
                    val outputHammingDistance1 = totalHammingDistance
                    val saveMap1 = twoCandidateMaps[1].toMutableMap()

                    // do we have a winner?

                    if (outputHammingDistance0 < minHammingDistance) {
                        println("Winner $outputHammingDistance0")
                        minHammingDistance = outputHammingDistance0
                        foundNewMin = true
                        resultHammingDistance.add(minHammingDistance)
                        resultDnaTransformList.add(outputParsimonyList0)
                        resultEdgeList.add(twoCandidateMaps[0])

                        baseEdgesMap = twoCandidateMaps[0].deepCopy()
                    }

                    if (outputHammingDistance1 < minHammingDistance) {
                        println("Winner $outputHammingDistance1")
                        minHammingDistance = outputHammingDistance1
                        foundNewMin = true
                        resultHammingDistance.add(minHammingDistance)
                        resultDnaTransformList.add(outputParsimonyList1)
                        resultEdgeList.add(twoCandidateMaps[1])

                        baseEdgesMap = twoCandidateMaps[1].deepCopy()
                    }
                }
            }
        } while (foundNewMin)
        
    }

    /**
     * convert the edge list into the tree data structure
     *
     * Process:
     *    Start at the edge of the edges.
     *    Make a root node at the edge, hooking up the last two edges.
     */
    fun buildTreeFromEdges(allEdges: MutableMap<Int, MutableList<Int>>) {
        maxEdgeNum = allEdges.keys.maxOrNull()!!
        root = Node(
            id = maxEdgeNum + 1,
            scoringArray = mk.d2array(4, dnaLen) { 0 }
        )

        // clear old internal node left/right entries
        for (i in numLeaves..maxEdgeNum) {
            val n = nodeMap[i]
            if (n == null) {
                println("buildTreeFromEdges: Error null node at node number $i")
                continue
            }
            n.left = null
            n.right = null
            n.isScored = false
            n.isOutput = false
            n.ripe = false
            n.dnaString = null
            nodeMap[i] = n
        }

        val oldToNode = maxEdgeNum
        val oldFromNode = allEdges[maxEdgeNum]!!.filter { it >= numLeaves }.first()

        // prune out the edges between maxEdgeNum (at index to)
        // and oldFromNode (at index from)

        val fromIndex = allEdges[oldToNode]!!.indexOf(oldFromNode)
        allEdges[maxEdgeNum]!!.removeAt(fromIndex)

        val toIndex = allEdges[oldFromNode]!!.indexOf(oldToNode)
        allEdges[oldFromNode]!!.removeAt(toIndex)

        root.left = nodeMap[oldFromNode]
        root.right = nodeMap[oldToNode]

        val visited: MutableList<Int> = mutableListOf()
        buildTree(root.left!!, visited, allEdges)
        buildTree(root.right!!, visited, allEdges)

        // the new tree is rooted at the global "root" node

    }
}
