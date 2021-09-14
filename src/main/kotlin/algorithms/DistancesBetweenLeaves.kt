@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.set


/**
 *

Distances Between Leaves Problem: Compute the distances between leaves in a weighted tree.

Input:  An integer n followed by the adjacency list of a weighted tree with n leaves.

Output: An n x n matrix (di,j), where di,j is the length of the path between leaves i and j.

 * See also:
 * stepik: https://stepik.org/lesson/240335/step/12?unit=212681
 * rosalind: http://rosalind.info/problems/ba7a/
 */

/**

Code Challenge: Solve the Distances Between Leaves Problem.
The tree is given as an adjacency list of a graph whose leaves
are integers between 0 and n - 1; the notation a->b:c
means that node a is connected to node b by an edge of weight c.
The matrix you return should be space-separated.

 */

class DistancesBetweenLeaves {

    var traversed: MutableList<Int> = mutableListOf()

    /**
     * @param leafCount - the row/column size of the distance matrix
     * @param g - the mapping of nodes to a list of connected nodes with connected weights
     *
     * NOTES: the leafCount can be less than the node numbers - but the traversal is limited to
     * the 0..(leafCount-1) node numbers.  Hence the returned 2D matrix has only
     * representatives from nodes { 0..(leafCount-1) }
     */
    fun distancesBetweenLeaves(leafCount: Int, g: Map<Int, List<Pair<Int, Int>>>): D2Array<Int> {
        val theMatrix = mk.d2array(leafCount, leafCount) {0}

        for (i in 0 until leafCount) {
            for (j in 0 until leafCount) {
                if (i == j) {
                    continue
                }
                traversed = mutableListOf()
                val weight = recursiveTraversal(j, i, i, leafCount, g)
                theMatrix[i, j] = weight
            }
        }

        return theMatrix
    }

    private fun recursiveTraversal(
        targetNode: Int,
        fromNode: Int,
        currentNode: Int,
        leafCount: Int,
        g: Map<Int, List<Pair<Int, Int>>>): Int {
        val listOfNodesAndWeights = g[currentNode]
        if (listOfNodesAndWeights == null) {
            println("recursive Travel - this shouldn't happen - null on a key")
            return 0
        }
        var weight = 0

        traversed.add(currentNode)
        for (p in listOfNodesAndWeights) {
            if (p.first == targetNode) {
                weight += p.second
                return weight
            }
            if (p.first < leafCount) {
                continue
            }
            if (!traversed.contains(p.first)) {
                val foundWeight = recursiveTraversal(targetNode, fromNode, p.first, leafCount, g)
                if (foundWeight != 0) {
                    return p.second + weight + foundWeight
                }
            }
        }
        return weight
    }
}
