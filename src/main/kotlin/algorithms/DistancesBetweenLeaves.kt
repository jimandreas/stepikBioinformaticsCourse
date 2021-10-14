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
     * @param matrixSize - the row/column size of the distance matrix
     * @param g - the mapping of nodes to a list of connected nodes with connected weights
     *
     * NOTES: the leafCount can be less than the node numbers - but the traversal is limited to
     * the 0..(leafCount-1) node numbers.  Hence the returned 2D matrix has only
     * representatives from nodes { 0..(leafCount-1) }
     */
    fun distancesBetweenLeaves(matrixSize: Int, g: Map<Int, Map<Int, Int>>): D2Array<Int> {
        val theMatrix = mk.d2array(matrixSize, matrixSize) {0}

        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                if (i == j) {
                    continue
                }
                traversed = mutableListOf()
                val weight = recursiveTraversal(matrixSize, j, i, i,  g)
                theMatrix[i, j] = weight
            }
        }

        return theMatrix
    }

    /**
     * recursively descend into the [g] matrix searching for the [targetNode]
     *   accumulate the total distance across the recursions until the [targetNode]
     *   is found in the true.
     *   @return  the accumulated distance
     */
    private fun recursiveTraversal(
        matrixSize: Int,
        targetNode: Int,
        fromNode: Int,
        currentNode: Int,
        g: Map<Int, Map<Int, Int>>): Int {
        val mapOfConnectionsAndDistances = g[currentNode]
        if (mapOfConnectionsAndDistances == null) {
            println("recursive Travel - this shouldn't happen - null on a key")
            return 0
        }
        var distance = 0

        traversed.add(currentNode)

        for (p in mapOfConnectionsAndDistances) {
            if (p.key == targetNode) {
                distance += p.value
                return distance
            }
            if (p.key < matrixSize) {
                continue
            }
            if (!traversed.contains(p.key)) {
                val foundWeight = recursiveTraversal(matrixSize, targetNode, fromNode, p.key, g)
                if (foundWeight != 0) {
                    return p.value + distance + foundWeight
                }
            }
        }
        return distance
    }

    /**
     * @param matrixSize - the row/column size of the distance matrix
     * @param g - the mapping of nodes to a list of connected nodes with connected weights
     *
     * NOTES: the leafCount can be less than the node numbers - but the traversal is limited to
     * the 0..(leafCount-1) node numbers.  Hence the returned 2D matrix has only
     * representatives from nodes { 0..(leafCount-1) }
     */
    fun distancesBetweenLeavesFloat(matrixSize: Int, g: Map<Int, Map<Int, Float>>): D2Array<Float> {
        val theMatrix = mk.d2array(matrixSize, matrixSize) {0f}

        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                if (i == j) {
                    continue
                }
                traversed = mutableListOf()
                val weight = recursiveTraversalFloat(matrixSize, j, i, i,  g)
                theMatrix[i, j] = weight
            }
        }

        return theMatrix
    }

    /**
     * recursively descend into the [g] matrix searching for the [targetNode]
     *   accumulate the total distance across the recursions until the [targetNode]
     *   is found in the true.
     *   @return  the accumulated distance
     */
    private fun recursiveTraversalFloat(
        matrixSize: Int,
        targetNode: Int,
        fromNode: Int,
        currentNode: Int,
        g: Map<Int, Map<Int, Float>>): Float {
        val mapOfConnectionsAndDistances = g[currentNode]
        if (mapOfConnectionsAndDistances == null) {
            println("recursive Travel - this shouldn't happen - null on a key")
            return 0f
        }
        var distance = 0f

        traversed.add(currentNode)

        for (p in mapOfConnectionsAndDistances) {
            if (p.key == targetNode) {
                distance += p.value
                return distance
            }
            if (p.key < matrixSize) {
                continue
            }
            if (!traversed.contains(p.key)) {
                val foundWeight = recursiveTraversalFloat(matrixSize, targetNode, fromNode, p.key, g)
                if (foundWeight != 0f) {
                    return p.value + distance + foundWeight
                }
            }
        }
        return distance
    }
}
