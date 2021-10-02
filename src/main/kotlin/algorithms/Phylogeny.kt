@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants"
)

package algorithms

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import java.lang.Math.max
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

    var theCurrentConnectionTree: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()
    val limbLengthMap: MutableMap<Int, Int> = mutableMapOf()
    var verbose = false

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

    fun additivePhylogenyStart(matrixSize: Int, m: D2Array<Int>): Map<Int, Map<Int, Int>> {
        // first - save each nodes limbLength in a map for later
        for (i in 0 until matrixSize) {
            val t = calculateLimbLength(matrixSize, i, m)
            //println("$i $t")
            limbLengthMap[i] = t
        }
        val mapResult = additivePhylogenyIterative(matrixSize, m.clone())

        return sortMapAndDistanceLists(mapResult)

    }

    fun sortMapAndDistanceLists(unorderedMap: Map<Int, Map<Int, Int>>): Map<Int, Map<Int, Int>> {
        // now sort the keys, and then the distances in the lists
        val mapSortedResult = unorderedMap.toSortedMap()
        val returnSortedList: MutableMap<Int, Map<Int, Int>> = mutableMapOf()
        for (keyNode in mapSortedResult) {
            val theList = keyNode.value.toSortedMap()
            returnSortedList[keyNode.key] = theList
        }
        return returnSortedList
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
        val pathEndNodek: Int,
        val distanceX: Int
    )

    /**
     * @param matrixSize - the row/column size of the distance matrix
     * @param m - the distance matrix
     * @return - the mapping of nodes to other nodes with weights
     */
    fun additivePhylogenyIterative(matrixSize: Int, m: D2Array<Int>): Map<Int, Map<Int, Int>> {

        val nodeInfoStack = Stack<NodeInfo>()
        var dMatrix = m
        for (countDownIdx in (matrixSize - 1) downTo 2) {
            val limbLength = calculateLimbLength(/*matrixSize*/ countDownIdx, countDownIdx, m)

            // now subtract the limbLength from the matrixSize-1 entries
            val dBald = dMatrix
            for (idx in 0 until countDownIdx) {
                dBald[idx, countDownIdx] -= limbLength
                dBald[countDownIdx, idx] -= limbLength
            }
            val leafPair = getLeaves(countDownIdx, dBald)

            val x = dBald[leafPair.first, countDownIdx]  // save distance

            // remove row countDownIdx and col countDownIdx
            //dMatrix = dropLastRowAndColumn(countDownIdx, dMatrix)

            val nInfo = NodeInfo(
                iterCount = countDownIdx,
                limbLength = limbLength,
                baseNodei = leafPair.first,
                pathEndNodek = leafPair.second,
                distanceX = x
            )

            nodeInfoStack.push(nInfo)
        }

        /**
         *  Initial connection:
         *  create a connection of length "distance" between leaves 0 and 1
         */

        val distance = m[0, 1]  // straight from the matrix
        theCurrentConnectionTree[0] = mutableMapOf(Pair(1, distance))
        theCurrentConnectionTree[1] = mutableMapOf(Pair(0, distance))

        /**
         * We have the first two leaves now.
         *
         * now iterate through the nodeInfo List and adjust the connection tree
         * as necessary, making new internal nodes to get the distances right
         */
        while (nodeInfoStack.isNotEmpty()) {
            val nodeInfoFromStack = nodeInfoStack.pop()
            fixTree(matrixSize, nodeInfoFromStack!!)
        }

        return theCurrentConnectionTree
    }

    /**
     *  OK now the job here is to create internal nodes
     *  as necessary based on the info in NodeInfo.
     *  Then attach our new node with len to the tree
     */
    fun fixTree(matrixSize: Int, info: NodeInfo) {
        val nodeNum = info.iterCount
        val limbLength = info.limbLength
        val requireLen = info.distanceX
        val baseNodeForLength = info.baseNodei
        val endNodeForPath = info.pathEndNodek
        val internalNode = findNodeOrMakeOne(matrixSize, baseNodeForLength, endNodeForPath, requireLen)
        if (verbose) {
            println("FIXTREE: node to add = $nodeNum limb $limbLength from baseNode $baseNodeForLength len from first internal node $requireLen")
        }

        theCurrentConnectionTree[internalNode]!![nodeNum] = limbLength
        theCurrentConnectionTree[nodeNum] = mutableMapOf(Pair(internalNode, limbLength))

        if (verbose) {
            println(theCurrentConnectionTree)
        }
    }

    /**
     *  scan for an internal node where the accumulated distance
     *  matches the required distance from the initial node
     *    If not found, make a node and return its key.
     */
    fun findNodeOrMakeOne(
        matrixSize: Int,
        searchThisNodesConnections: Int,
        endNodeForPath: Int,
        requiredLenToNodeFromBaseNode: Int
    ): Int {

        // error checking, the tree MUST contain the base node number
        var baseNode = searchThisNodesConnections
        if (!theCurrentConnectionTree.containsKey(baseNode)) {
            println("findNodeOrMakeOn: Error baseNode is not in the current Tree")
            return 0
        }

        /**
         * traverse the graph searching for [endNodeForPath] - when found
         * form a list of path nodes
         */

        var notFound = true

        val pathStack: Stack<Int> = Stack()

        var curNodeMap = theCurrentConnectionTree[searchThisNodesConnections]
        var curNode = curNodeMap!!.keys.first() // leaf nodes have only one connection
        val nodesVisitedList: MutableList<Int> = mutableListOf(curNode)

        pathStack.push(searchThisNodesConnections)
        pathStack.push(curNode)

        while (notFound) {
            val connections = theCurrentConnectionTree[curNode]!!.keys
            if (connections.contains(endNodeForPath)) {
                // found the node, break out
                pathStack.push(endNodeForPath)
                notFound = false
                break
            }
            val internalNodeList = connections.filter { it >= matrixSize }.toList()
            var newNodeFound = false
            for (n in internalNodeList) {
                if (!nodesVisitedList.contains(n)) {
                    nodesVisitedList.add(n)
                    pathStack.push(n)
                    curNode = n
                    newNodeFound = true
                    break
                }
            }
            // didn't find the target node- path stack always has baseNode and first connecting node
            if (!newNodeFound) {
                if (pathStack.count() == 2) {
                    // check if all nodes have been examined
                    curNode = pathStack.peek()!!
                    // fancy way of doing intersection of nodes visited and intermediate nodes in current node connections:
                    if (nodesVisitedList.containsAll(theCurrentConnectionTree[curNode]!!.keys.filter { it >= matrixSize }
                            .toList())) {
                        if (verbose) {
                            println("Did NOT find the target node $endNodeForPath")
                        }
                        break
                    } else {
                        continue // keep searching the second node
                    }
                } else {
                    curNode = pathStack.pop()!!  // continue search on a previous node in the graph
                }
            }
        }

        // convert path stack to list of node numbers

        val pathList: MutableList<Int> = mutableListOf()
        for (i in 0 until pathStack.count()) {
            pathList.add(0, pathStack.pop()!!)
        }

        if (verbose) {
            println("path list is $pathList")
        }

        /**
         * now walk the `pathList` and return a node that matches [requiredLenToNodeFromBaseNode],
         * or create an internal node if necessary
         */
        var len = requiredLenToNodeFromBaseNode
        for (i in 0 until pathList.size - 1) {
            val nextLenMap = theCurrentConnectionTree[pathList[i]]!!
            val nextLen = nextLenMap[pathList[i + 1]]!!
            len = len - nextLen
            if (len == 0) {  // found the matching internal node?
                return pathList[i + 1]
            }
            if (len < 0) {
                // have to create an internal node
                len = len + nextLen
                val newNodeNum = max(matrixSize, theCurrentConnectionTree.keys.maxOf { it } + 1)
                val fromNodeNum = pathList[i]
                val toNodeNum = pathList[i + 1]
                val currentLen = theCurrentConnectionTree[fromNodeNum]!![toNodeNum]!!

                theCurrentConnectionTree[fromNodeNum]!!.remove(toNodeNum)
                theCurrentConnectionTree[fromNodeNum]!![newNodeNum] = len

                theCurrentConnectionTree[toNodeNum]!!.remove(fromNodeNum)
                theCurrentConnectionTree[toNodeNum]!![newNodeNum] = currentLen - len

                theCurrentConnectionTree[newNodeNum] = mutableMapOf(
                    Pair(fromNodeNum, len),
                    Pair(toNodeNum, currentLen - len)
                )
                return newNodeNum
            }
        }
        println("ERROR - should not reach this point in path walk")
        return 0
    }

    /**
     * find (i, k) which are two leaves such that Di,k = Di,n + Dn,k
     * Assumes that n = the last row / col of m (e.g. m[0, matrixSize])
     */
    fun getLeaves(matrixSize: Int, m: D2Array<Int>): Pair<Int, Int> {
        var minDistance = Int.MAX_VALUE
        var minPair: Pair<Int, Int> = Pair(0, 0)
        outerLoop@
        for (i in 0 until matrixSize) {
            for (k in 1 until matrixSize) {
                if (i != k) {
                    val dij = m[i, k]
                    val din = m[i, matrixSize]
                    val dnk = m[matrixSize, k]
                    if (dij == din + dnk) {
                        //if (dij < minDistance) {
                        minPair = Pair(i, k)
                        minDistance = dij
                        //}
                        break@outerLoop
                    }
                }
            }
        }
        if (minDistance == Int.MAX_VALUE) {
            println("ERROR: getLeaves: matching Di,k = Di,n + Dn,k NOT FOUND")
        }
        return minPair
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
