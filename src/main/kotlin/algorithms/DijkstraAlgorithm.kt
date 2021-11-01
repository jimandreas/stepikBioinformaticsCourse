@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "RedundantIf", "LiftReturnOrAssignment"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set

/**
 *
 * Dijkstra's Algorithm
 * rosalind: http://rosalind.info/problems/dij/
 * Note: not part of the stepik cirriculum
 *
 * See also:
 * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 *
 * Graph Data Structure 4. Dijkstra’s Shortest Path Algorithm
 * https://www.youtube.com/watch?v=pVfj6mxhdMw
 */

class DijkstraAlgorithm {

    // note: graph numbering tends to start at one
    private val visited: MutableList<Int> = mutableListOf()
    fun dijkstraAlgorithmFindShortestPaths(graph: HashMap<Int, HashMap<Int, Int>>): List<Int> {

        visited.clear()
        val distance = mk.d1array(graph.keys.size+1) { Int.MAX_VALUE }
        distance[0] = 0
        distance[1] = 0

        walkGraph(fromNode = 1, graph, distance)

        val retList: MutableList<Int> = mutableListOf()
        for (i in 1..graph.keys.size) {
            if (distance[i] == Int.MAX_VALUE) {
                retList.add(-1)
            } else {
                retList.add(distance[i])
            }
        }

        return retList
    }

    private fun walkGraph(fromNode: Int, graph: HashMap<Int, HashMap<Int, Int>>, distance: D1Array<Int>) {
        visited.add(fromNode)
        if (graph[fromNode]!!.keys.size == 0) {
            return
        }
        var minDistance = Int.MAX_VALUE
        var minNode = 0
        val d = distance[fromNode]
        for (e in graph[fromNode]!!.keys) {
            val edgeLength = graph[fromNode]!![e]!! + d
            if (distance[e] > edgeLength) {
                distance[e] = edgeLength
            }
            if (minDistance > edgeLength) {
                minDistance = edgeLength
                minNode = e
            }
        }

        walkGraph(minNode, graph, distance)
    }
}

