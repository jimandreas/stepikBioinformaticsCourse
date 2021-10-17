@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty"
)

package algorithms

import java.util.*
import kotlin.collections.HashMap

/**
 *
 * Breadth First Search
 * rosalind: http://rosalind.info/problems/bfs/
 * Note: not part of the stepik cirriculum.
 *
 * For examples of operation, see:
 * RosieBreadthFirstSearchTest.kt
 */

class RosieBreadthFirstSearch {

    /**
    The task is to use breadth-first search to compute
    single-source shortest distances in an unweighted directed graph.

    Given: A simple directed graph with vertices in the edge list format.

    Return: An array D[1..n] where D[ i ] is the length of a shortest path
    from the vertex 1 to the vertex i

    Note: (D[1]=0).

    If i is not reachable from 1 set D[ i ] to −1

     */

    var visited: MutableList<Int> = mutableListOf() // nodes visited in an iteration

    /**
     * search from node 1 to all other nodes in the graph.
     *   Distance from node 1 to node 1 is zero
     *   Node numbering is one based.
     */
    fun doSearches(numNodes: Int, g: HashMap<Int, MutableList<Int>>): List<Int> {
        val resultList : MutableList<Int> = mutableListOf()

//        resultList.add(0)

//        for (i in 2..numNodes) {
//            visited = mutableListOf() // reset list
//            val pathLength = depthFirstSearch(1, i, g)
//            if (pathLength == Int.MAX_VALUE) {
//                resultList.add(-1)
//            } else {
//                resultList.add(pathLength)
//            }
//        }

        val distances = distancesHashTable(numNodes, g)

        for (i in 1..numNodes) {
            if (distances.containsKey(i)) {
                resultList.add(distances[i]!!)
            } else {
                resultList.add(-1)
            }
        }
        return resultList
    }

    /**
     * prepare hash table of distances
     */
    fun distancesHashTable(numNodes: Int, g: HashMap<Int, MutableList<Int>>): HashMap<Int, Int> {
        val depthList: HashMap<Int, Int> = hashMapOf()
        val foundList: MutableList<Int> = mutableListOf(0, 1)

        depthList[1] = 0
        var distance = 1
        var newList: MutableList<Int> = g[1]!!
        while (true) {
            val tempList: MutableList<Int>  = mutableListOf()
            for (n in newList) {
                if (!foundList.contains(n)) {
                    foundList.add(n)
                    depthList[n] = distance
                }
                for (newNode in g[n]!!) {
                    if (!foundList.contains(newNode)) {
                        tempList.add(newNode)
                    }
                }
            }

            if (tempList.size == 0) {
                break
            }
            distance++
            newList = tempList

        }

        return depthList
    }

    /**
     * recursive search for the [targetNodeNum] from the [currentNodeNum]
     *  in the graph map in [g]
     *  @return the length of the shortest path
     *
     *  Note that this is REALLY a depth first search :-)
     */
    fun depthFirstSearch(currentNodeNum: Int, targetNodeNum: Int, g: HashMap<Int, MutableList<Int>>): Int {

        var minPath = Int.MAX_VALUE
        visited.add(currentNodeNum)

        if (g[currentNodeNum]!!.contains(targetNodeNum)) {
            if (targetNodeNum == 2)println("Found node 2 in node $currentNodeNum")
            return 1
        }
        for (n in g[currentNodeNum]!!) {

            if (currentNodeNum==1 && targetNodeNum == 2) {
                println("current node $n in nodes to search ${g[currentNodeNum]!!}")
            }


            if (visited.contains(n)) {
                //println("have a cycle at $n")
                continue
            }
            visited.add(n)
            //if (targetNodeNum == 2)println("searching $n for node 2")
            var len = depthFirstSearch(n, targetNodeNum, g)

            if (currentNodeNum==1 && targetNodeNum == 2) {
                println("len for node1 target 2 is $len")
            }
            if (len == Int.MAX_VALUE) {
                continue
            }

            if (len+1 < minPath) {
                minPath = len+1
            }
        }
        return minPath
    }

}
