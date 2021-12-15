@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

/**
 *
 * See also:
 * Stepik: https://stepik.org/lesson/240343/step/6?unit=212689
 * Rosalind: no equivalent problem
 *
 * "Evolutionary Tree Reconstruction in the Modern Era"
 * Youtube: https://www.youtube.com/watch?v=A48MBlIyl5c
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

open class SmallParsimonyNearestNeighborsOfTree : SmallParsimonyUnrootedTree() {

    /**

    Code Challenge: Solve the Nearest Neighbors of a Tree Problem.

    Input: Two internal nodes a and b specifying an edge e,
    followed by an adjacency list of an unrooted binary tree.

    Output: Two adjacency lists representing the nearest neighbors
    of the tree with respect to e. Separate the adjacency lists with a blank line.
     */

    fun twoNearestNeighbors(
        a: Int,
        b: Int,
        edges: MutableMap<Int, MutableList<Int>>
    ): List<MutableMap<Int, MutableList<Int>>> {

/*
         * first group - the last edge in A swaps with the 1st edge in B
         */

        var connListA = edges[a]!!.filter { it != b }.toMutableList()
        var connListB = edges[b]!!.filter { it != a }.toMutableList()

        var newA = connListA.removeLast()
        var newB = connListB.removeFirst()

        connListA.add(newB)
        connListB.add(newA)
        connListA.add(b)
        connListB.add(a)

        var edgesVersionA = edges.deepCopyMap().toMutableMap()
        var edgesVersionB = edges.deepCopyMap().toMutableMap()

        edgesVersionA[a] = connListA
        edgesVersionA[b] = connListB

        edgesVersionA[newA] = edgesVersionA[newA]!!.filter { it != a }.toMutableList()
        edgesVersionA[newA]!!.add(b)
        edgesVersionA[newB] = edgesVersionA[newB]!!.filter { it != b }.toMutableList()
        edgesVersionA[newB]!!.add(a)

        /*
         * now the second group - the last edge in A swaps with the last edge in B
         */

        connListA = edges[a]!!.filter { it != b }.toMutableList()
        connListB = edges[b]!!.filter { it != a }.toMutableList()

        newA = connListA.removeLast()
        newB = connListB.removeLast()

        connListA.add(newB)
        connListB.add(newA)
        connListA.add(b)
        connListB.add(a)

        edgesVersionB[a] = connListA
        edgesVersionB[b] = connListB

        edgesVersionB[newA] = edgesVersionB[newA]!!.filter { it != a }.toMutableList()
        edgesVersionB[newA]!!.add(b)
        edgesVersionB[newB] = edgesVersionB[newB]!!.filter { it != b }.toMutableList()
        edgesVersionB[newB]!!.add(a)


        return listOf(edgesVersionA, edgesVersionB)
    }


    fun fourNearestNeighbors(
        a: Int,
        b: Int,
        edges: MutableMap<Int, MutableList<Int>>
    ): List<MutableMap<Int, MutableList<Int>>> {

        /*
         * first group - the last edge in A swaps with the 1st edge in B
         */

        var connListA = edges[a]!!.filter { it != b }.toMutableList()
        var connListB = edges[b]!!.filter { it != a }.toMutableList()

        var newA = connListA.removeLast()
        var newB = connListB.removeFirst()

        connListA.add(newB)
        connListB.add(newA)
        connListA.add(b)
        connListB.add(a)

        var edgesVersionA = edges.deepCopyMap().toMutableMap()


        edgesVersionA[a] = connListA
        edgesVersionA[b] = connListB

        edgesVersionA[newA] = edgesVersionA[newA]!!.filter { it != a }.toMutableList()
        edgesVersionA[newA]!!.add(b)
        edgesVersionA[newB] = edgesVersionA[newB]!!.filter { it != b }.toMutableList()
        edgesVersionA[newB]!!.add(a)

        /*
         * now the second group - the last edge in A swaps with the last edge in B
         */

        connListA = edges[a]!!.filter { it != b }.toMutableList()
        connListB = edges[b]!!.filter { it != a }.toMutableList()

        newA = connListA.removeLast()
        newB = connListB.removeLast()

        connListA.add(newB)
        connListB.add(newA)
        connListA.add(b)
        connListB.add(a)

        var edgesVersionB = edges.deepCopyMap().toMutableMap()
        edgesVersionB[a] = connListA
        edgesVersionB[b] = connListB

        edgesVersionB[newA] = edgesVersionB[newA]!!.filter { it != a }.toMutableList()
        edgesVersionB[newA]!!.add(b)
        edgesVersionB[newB] = edgesVersionB[newB]!!.filter { it != b }.toMutableList()
        edgesVersionB[newB]!!.add(a)


        /* -----------------*/

        /*
         * third group - the first edge in A swaps with the 1st edge in B
         */

        connListA = edges[a]!!.filter { it != b }.toMutableList()
        connListB = edges[b]!!.filter { it != a }.toMutableList()

        newA = connListA.removeFirst()
        newB = connListB.removeFirst()

        connListA.add(newB)
        connListB.add(newA)
        connListA.add(b)
        connListB.add(a)

        var edgesVersionC = edges.deepCopyMap().toMutableMap()

        edgesVersionC[a] = connListA
        edgesVersionC[b] = connListB

        edgesVersionC[newA] = edgesVersionC[newA]!!.filter { it != a }.toMutableList()
        edgesVersionC[newA]!!.add(b)
        edgesVersionC[newB] = edgesVersionC[newB]!!.filter { it != b }.toMutableList()
        edgesVersionC[newB]!!.add(a)

        /*
         * now the fourth group - the first edge in A swaps with the last edge in B
         */

        connListA = edges[a]!!.filter { it != b }.toMutableList()
        connListB = edges[b]!!.filter { it != a }.toMutableList()

        newA = connListA.removeFirst()
        newB = connListB.removeLast()

        connListA.add(newB)
        connListB.add(newA)
        connListA.add(b)
        connListB.add(a)

        var edgesVersionD = edges.deepCopyMap().toMutableMap()
        edgesVersionD[a] = connListA
        edgesVersionD[b] = connListB

        edgesVersionD[newA] = edgesVersionD[newA]!!.filter { it != a }.toMutableList()
        edgesVersionD[newA]!!.add(b)
        edgesVersionD[newB] = edgesVersionD[newB]!!.filter { it != b }.toMutableList()
        edgesVersionD[newB]!!.add(a)


        return listOf(edgesVersionA, edgesVersionB, edgesVersionC, edgesVersionD)
    }

    fun prettyPrintFourNearestNeighbors(
        a: Int,
        b: Int,
        edges: MutableMap<Int, MutableList<Int>>,
        outputList: List<MutableMap<Int, MutableList<Int>>> ) : List<String>
    {
        val outputStrings: MutableList<String> = mutableListOf()

        val keysList = edges.keys.sorted()

        if (keysList.size != outputList[0].keys.size) {
            println("prettyPrintFourNearestNeighbors: ERROR size of keys do not match")
            return emptyList()
        }
        if (outputList.size != 4) {
            println("prettyPrintFourNearestNeighbors: ERROR the list does not contain FOUR members")
            return emptyList()
        }
        for (k in edges.keys.sorted()) {
            val str = StringBuilder()
            str.append("$k: ")
            val eString = edges[k]!!.joinToString(",")
            str.append(eString)
            str.append(" ")
            for (i in 0 until 4) {
                val outputString = outputList[i][k]!!.joinToString(",")
                str.append(outputString)
                str.append(" ")
            }
            outputStrings.add(str.toString())
        }
        return outputStrings
    }


}
