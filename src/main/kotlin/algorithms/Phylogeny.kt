@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.junit.jupiter.params.shadow.com.univocity.parsers.fixed.FixedWidthFieldLengths
import java.lang.Math.min

/**
 *
 * See also:
 * Youtube: https://www.youtube.com/watch?v=HjDz2ak5BUk
 *
 * Limb Length:
 * stepik: https://stepik.org/lesson/240336/step/11?unit=212682
 * rosalind: http://rosalind.info/problems/ba7b/
 *
 * Additive:
 * stepik: https://stepik.org/lesson/240337/step/6?unit=212683
 * rosalind: http://rosalind.info/problems/ba7c/
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

class Phylogeny {

    /**

    Code Challenge: Implement AdditivePhylogeny to solve the Distance-Based Phylogeny Problem.

    Input: An integer n followed by a space-separated n x n distance matrix.

    Output: A weighted adjacency list for the simple tree fitting this matrix.

    Note on formatting: The adjacency list must have consecutive integer node labels
    starting from 0. The n leaves must be labeled 0, 1, ..., n - 1 in order
    of their appearance in the distance matrix.
    Labels for internal nodes may be labeled in any order but must start
    from n and increase consecutively.
     */

    /**
     * note that this code can be self-checking.
     *    An ending call to distancesBetweenLeaves() should produce a matrix
     *    identical to our starting matrix
     */
    fun additivePhylogenyStart(matrixSize: Int, m: D2Array<Int>) {
        for (i in 0 until matrixSize) {
            val t = calculateLimbLength(matrixSize, i, m)
            println("$i $t")
        }
        val mapResult = additivePhylogenyIterative(matrixSize, m)
    }

    val pseudoCodeAdditivePhylogeny = """
        NOTE: the indexing is ONE based here - but the implementation is ZERO-based.
        NOTE2: the actual implementation is NOT recursive, but instead iterative.
        
    AdditivePhylogeny(D)
        n ← number of rows in D
        if n = 2
            return the tree consisting of a single edge of length D1,2
        limbLength ← Limb(D, n)
        for j ← 1 to n - 1
            Dj,n ← Dj,n - limbLength
            Dn,j ← Dj,n
        (i, k) ← two leaves such that Di,k = Di,n + Dn,k
        x ← Di,n
        D ← D with row n and column n removed
        T ← AdditivePhylogeny(D)
        v ← the (potentially new) node in T at distance x from i on the path between i and k
        add leaf n back to T by creating a limb (v, n) of length limbLength
        return T
    """
    data class NodeInfo(
        val iterCount: Int,
        val limbLength: Int,
        val baseNodei: Int,
        val distanceX: Int
    )
    /**
     * @param matrixSize - the row/column size of the distance matrix
     * @param m - the distance matrix
     * @return - the mapping of nodes to other nodes with weights
     */
    fun additivePhylogenyIterative(matrixSize: Int, m: D2Array<Int>): Map<Int, List<Pair<Int, Int>>> {

        val nodeInfoStack = Stack<NodeInfo>()
        var dMatrix = m
        for (countDownIdx in (matrixSize - 1) downTo 2) {
            val limbLength = calculateLimbLength(matrixSize, countDownIdx, m)

            // now subtract the limbLength from the matrixSize-1 entries
            val dBald = dMatrix
            for (idx in 0 until countDownIdx) {
                dBald[idx, countDownIdx] -= limbLength
                dBald[countDownIdx, idx] -= limbLength
            }
            val leafPair = getLeaves(countDownIdx, dBald)

            val x = dBald[leafPair.first, countDownIdx]  // save distance

            // remove row countDownIdx and col countDownIdx
            dMatrix = dropLastRowAndColumn(countDownIdx, dMatrix)

            val nInfo = NodeInfo(
                iterCount = countDownIdx,
                limbLength = limbLength,
                baseNodei = leafPair.first,
                distanceX = x
            )

            nodeInfoStack.push(nInfo)
        }

        /*
         * now reconstruct the connection tree
         */
        val cTree: MutableMap<Int, List<Pair<Int, Int>>> = mutableMapOf()
        // first node is just the distance between 0 and 1
        val f = Pair(1, calculateLimbLength(matrixSize, 1, m))
        cTree[0] = listOf(f)

        /*
         * now iterate through the nodeInfo List and adjust the connection tree
         * as necessary, making new internal nodes to get the distances right
         */
        while (nodeInfoStack.isNotEmpty()) {

            val nS = nodeInfoStack.pop()
            println(nS)
            fixTree(cTree, nS!!)
        }

        return emptyMap()
    }

    /**
     *  OK now the job here is to create internal nodes
     *  as necessary based on the info in NodeInfo.
     *  Then attach our new node with len to the tree
     */
    fun fixTree(t: MutableMap<Int, List<Pair<Int, Int>>>, info: NodeInfo) {
        val nodeNum = info.iterCount+1
        val len = info.limbLength
        val requireLen = info.distanceX
        val requireLenFromNode = info.baseNodei
        val internalNode = gotNode(t, requireLenFromNode, requireLen)
    }

    /**
     *  scan for an internal node where the accumulated distance
     *  matches the required distance from the initial node
     *  (recursive)
     */
    fun gotNode(t: MutableMap<Int, List<Pair<Int, Int>>>, n: Int, len: Int): Int? {
        if (!t.containsKey(n)) {
            return null
        }
        for (pair in t[n]!!) {
            val nextNode = pair.first
            val newDistance = len - pair.second
            val internalNode = gotNode(t, nextNode, nextNode)
            if (internalNode != null) {
                return internalNode
            }
        }
        return null
    }

    /**
     * find (i, k) which are two leaves such that Di,k = Di,n + Dn,k
     * Assumes that n = the last row / col of m (e.g. m[0, matrixSize])
     */
    fun getLeaves(matrixSize: Int, m: D2Array<Int>): Pair<Int, Int> {
        for (i in 0 until matrixSize) {
            for (k in 1 until matrixSize) {
                if (i != k) {
                    val dij = m[i, k]
                    val din = m[i, matrixSize]
                    val dnk = m[matrixSize, k]
                    if (dij == din + dnk) {
                        return Pair(i, k)
                    }
                }
            }
        }
        return Pair(0, 0)
    }

    /**
     * copy the matrixSize number of rows and columns form m into a new matrix and return it
     */
    fun dropLastRowAndColumn(matrixSize: Int, m: D2Array<Int>): D2Array<Int> {
        val mySmaller2D = mk.d2array(matrixSize, matrixSize) {0}
        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                mySmaller2D[i, j] = m[i, j]
            }
        }
        return mySmaller2D
    }

    /**

    Code Challenge: Solve the Limb Length Problem.

    Input: An integer n, followed by an integer j between 0 and n - 1,
    followed by a space-separated additive distance matrix D (whose elements are integers).

    Output: The limb length of the leaf in Tree(D) corresponding
    to row j of this distance matrix (use 0-based indexing).

    For each j, we can compute LimbLength(j) by finding the minimum value of
    ( Di,j + Dj,k − Di,k ) / 2
    over all pairs of leaves i and k.

     (rowNumberJ = j)
     */
    fun calculateLimbLength(matrixSize: Int, rowNumberJ: Int, m: D2Array<Int>): Int {

        var minValue = Int.MAX_VALUE

        for (i in 0 until matrixSize) {
            for (k in 0 until matrixSize) {

                if (i != rowNumberJ && rowNumberJ != k) {

                    val dij = m[i, rowNumberJ]
                    val djk = m[rowNumberJ, k]
                    val dik = m[i, k]
                    var sum = dij + djk - dik
                    sum = sum / 2

                    minValue = min(minValue, sum)
//                    if (sum == 2) {
//                        println("$i $rowNumberJ $k  $dij $djk $dik")
//                    }
                }

            }
        }
        return minValue
    }

}
