@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
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

    var nextNode = 0 // tracker for the next internal node number to create
    var theCurrentConnectionTree: MutableMap<Int, MutableList<Pair<Int, Int>>> = mutableMapOf()

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
    val limbLengthMap: MutableMap<Int, Int> = mutableMapOf()
    fun additivePhylogenyStart(matrixSize: Int, m: D2Array<Int>): Map<Int, List<Pair<Int, Int>>> {
        // first - save each nodes limbLenght in a map for later
        for (i in 0 until matrixSize) {
            val t = calculateLimbLength(matrixSize, i, m)
            println("$i $t")
            limbLengthMap[i] = t
        }
        val mapResult = additivePhylogenyIterative(matrixSize, m.clone())

        return sortMapAndDistanceLists(mapResult)

    }

    fun sortMapAndDistanceLists(unorderedList: Map<Int, List<Pair<Int, Int>>>): Map<Int, List<Pair<Int, Int>>> {
        // now sort the keys, and then the distances in the lists
        val mapSortedResult = unorderedList.toSortedMap()
        val returnSortedList: MutableMap<Int, List<Pair<Int, Int>>> = mutableMapOf()
        for (entry in mapSortedResult) {
            val theList = entry.value
            returnSortedList[entry.key] = theList.sorted()
        }
        return returnSortedList
    }

    private fun <A : Comparable<A>, B : Comparable<B>> List<Pair<A, B>>.sorted(): List<Pair<A, B>> =
        sortedWith(
            Comparator<Pair<A, B>> { a, b -> a.first.compareTo(b.first) }.thenBy { it.second }
        )

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


        /*
         * here is where this iterative algo deviates from the pseudoCode.
         *    There are two cases - where only one additional node is needed,
         * or where there needs to be two nodes
         */
        val distance = m[0, 1]  // straight from the matrix
        val limb0 = limbLengthMap[0]!!
        val limb1 = limbLengthMap[1]!!
        nextNode = matrixSize  // init node number to create to base value

        if (distance - limb0 - limb1 == 0) {
            // first edge from node 0 to internal node
            theCurrentConnectionTree[0] = mutableListOf(Pair(nextNode, limb0))
            // second edge from node 1 to internal node
            theCurrentConnectionTree[1] = mutableListOf(Pair(nextNode, limb1))
            // and the back pointers:
            theCurrentConnectionTree[nextNode] = mutableListOf(
                Pair(0, limb0),
                Pair(1, limb1)
            )
            nextNode++
        } else {
            // we need two nodes at the start
            // first edge from node 0 to node "matrixSize"
            val internodeDistance = distance - limb0 - limb1
            theCurrentConnectionTree[0] = mutableListOf(Pair(nextNode, limb0))
            // first internal node - back pointer to node 0, forward node to next internal node
            theCurrentConnectionTree[nextNode] = mutableListOf(
                Pair(0, limb0),
                Pair(nextNode + 1, internodeDistance)
            )
            // second internal node - back pointer to above node, forward node to node 1
            theCurrentConnectionTree[nextNode + 1] = mutableListOf(
                Pair(nextNode, internodeDistance),
                Pair(1, limb1)
            )

            theCurrentConnectionTree[1] = mutableListOf(Pair(nextNode + 1, limb1))
            nextNode += 2
        }


        /*
         * We have the first three nodes now - two leaf nodes and a connector node,
         *    or FOUR nodes - two leaf nodes and two connector nodes.
         *
         * now iterate through the nodeInfo List and adjust the connection tree
         * as necessary, making new internal nodes to get the distances right
         */
        while (nodeInfoStack.isNotEmpty()) {

            val nodeInfoFromStack = nodeInfoStack.pop()
            println(nodeInfoFromStack)
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
        val len = info.limbLength
        val requireLen = info.distanceX
        val baseNodeForLength = info.baseNodei
        val internalNode = findNodeOrMakeOne(matrixSize, baseNodeForLength, requireLen)
        println("internal node found is $internalNode")
        theCurrentConnectionTree[internalNode]!!.add(Pair(nodeNum, len))
        theCurrentConnectionTree[nodeNum] = mutableListOf(Pair(internalNode, len))
    }

    /**
     *  scan for an internal node where the accumulated distance
     *  matches the required distance from the initial node
     *    If not found, make a node and return its key.
     */
    fun findNodeOrMakeOne(
        matrixSize: Int,
        baseNodeForLen: Int,
        requiredLenToNodeFromBaseNode: Int
    ): Int {
        // error checking, the tree MUST contain the base node number

        var baseNode = baseNodeForLen
        if (!theCurrentConnectionTree.containsKey(baseNode)) {
            println("findNodeOrMakeOn: Error baseNode is not in the current Tree")
            return 0
        }
        // for the base node, traverse the list of connections, searching for a match for our
        // required length.  The match cannot be a leaf node, naturally - we must connect to
        // an internal node

        var notFound = true
        var maxDistance = 0
        var tempRequiredDistance = requiredLenToNodeFromBaseNode
        var candidateNodeStack = Stack<Pair<Int, Int>>()
        while (notFound) {
            for (pair in theCurrentConnectionTree[baseNode]!!) {
                val nextNodeNumber = pair.first   // pair is (NodeNum, Length)
                val distanceToNextNode = pair.second

                // if this is an internal node
                if (nextNodeNumber >= matrixSize) {
                    if (tempRequiredDistance == distanceToNextNode) {
                        return nextNodeNumber  // found it!
                    }
                    if (tempRequiredDistance > distanceToNextNode) { // if a possibility, save it for later
                        candidateNodeStack.push(Pair(nextNodeNumber, tempRequiredDistance - distanceToNextNode))
                    }
                }
            }
            if (candidateNodeStack.isNotEmpty()) {
                val candidate = candidateNodeStack.pop()
                baseNode = candidate!!.first
                tempRequiredDistance = candidate.second
            } else {
                notFound = false
            }
        }
        // TODO: create a node someplace
        println("ERROR node creation not yet implemented")
        return 0
    }

    /**
     * try to see if there is an edge that can be split into one that provides
     * the required distance
     *
     * @return  true if successful, false if not
     */
    fun addIntermediateNode(
        matrixSize: Int,
        baseNodeForLen: Int,
        requiredLenToNodeFromBaseNode: Int
    ): Boolean {
        val baseNodeConnList = theCurrentConnectionTree[baseNodeForLen]
        val innerNodeNum = baseNodeConnList!![0].first
        val conns = theCurrentConnectionTree[innerNodeNum]

        val possibleConns = conns!!.filter {
            val nodeNum = it.first
            val connDistance = it.second
            connDistance >= matrixSize
        }
        if (possibleConns.size > 1) {
            println("WARNING - multiple connection candidates to break.  Picking the first one")
        }
        if (possibleConns.isEmpty()) {
            return false
        }

        val endNode = possibleConns[0].first
        val distanceToEndNode = possibleConns[0].second

        val firstDistance = distanceToEndNode - requiredLenToNodeFromBaseNode
        val secondDistance = requiredLenToNodeFromBaseNode - firstDistance

        // update tree - add new node and add forward and backward links
        theCurrentConnectionTree[nextNode] = mutableListOf(
            Pair(innerNodeNum, firstDistance),
            Pair(endNode, secondDistance)
        )


        return true
    }

    /**
     * walk the connection list for fromNode looking for the toNode.
     * Set the distance for toNode to newDistance.
     */
    fun fixConnList(
        fromNode: Int,
        toNode: Int,
        newDistance: Int
    ) {
        val connList = theCurrentConnectionTree[fromNode]!!
        val newConnList : MutableList<Pair<Int, Int>> = mutableListOf()
        for (conn in connList) {
            if (conn.first == toNode) {
                newConnList.add(Pair(toNode, newDistance))
            } else {
                newConnList.add(conn)
            }
        }
        theCurrentConnectionTree[fromNode] = newConnList
    }

    /**
     * find (i, k) which are two leaves such that Di,k = Di,n + Dn,k
     * Assumes that n = the last row / col of m (e.g. m[0, matrixSize])
     *
     * MODIFIED: return the MINIMUM distance
     */
    fun getLeaves(matrixSize: Int, m: D2Array<Int>): Pair<Int, Int> {
        var minDistance = Int.MAX_VALUE
        var minPair: Pair<Int, Int> = Pair(0, 0)
        for (i in 0 until matrixSize) {
            for (k in 1 until matrixSize) {
                if (i != k) {
                    val dij = m[i, k]
                    val din = m[i, matrixSize]
                    val dnk = m[matrixSize, k]
                    if (dij == din + dnk) {
                        if (dij < minDistance) {
                            minPair = Pair(i, k)
                            minDistance = dij
                        }
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
     * copy the matrixSize number of rows and columns form m into a new matrix and return it
     */
    fun dropLastRowAndColumn(matrixSize: Int, m: D2Array<Int>): D2Array<Int> {
        val mySmaller2D = mk.d2array(matrixSize, matrixSize) { 0 }
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
