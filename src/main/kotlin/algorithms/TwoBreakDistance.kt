@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog")

package algorithms

/**
 *

Code Challenge: Solve the 2-Break Distance Problem.

Input: Genomes P and Q.
Output: The 2-break distance d(P, Q).


 * See also:
 * stepik: https://stepik.org/lesson/240324/step/4?unit=212670
 * rosalind: http://rosalind.info/problems/ba6c/
 *
 * Part 7 of 9 of a series of lectures on "Are There Fragile Regions in the Human Genome?"
 *
 * youtube: @link: https://www.youtube.com/watch?v=NXc_25w11P0
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

/**

Sample Input:

(+1 +2 +3 +4 +5 +6)
(+1 -3 -6 -5)(+2 -4)

Sample Output:

3
 */

/*

 Review:
 Basic algorithm is based on the theorem:

 2-Break Distance Theorem: The 2-break distance between genomes P and Q
 is equal to Blocks(P, Q)− Cycles(P, Q).

 Here "Block" refers to the single number +1 or -1 representing a gene that is
 syntenic in a study.   These "Blocks" are also referred to as "Synteny Blocks".
 The assumption in this problem statement is that a "Block N" in P is the same as
 a "Block N" in Q.  Therefore comparison of block numbers between the
 two genomes is valid.

 Therefore to solve this problem using the theorem:

 Blocks(P, Q) is just the quantity of blocks in either set, since they are identical in content in this case.
 Cycles(P, Q) is how many two break operations must be made to convert P into Q

 Ref: https://en.wikipedia.org/wiki/Synteny
 */

class TwoBreakDistance {

    val tbg = TwoBreakGenomesToBreakpointGraph()

    /**
     * @param p - genome p represented by a list of lists of synteny Block numbers
     * @param q - same as p
     * @return The Two Break distance
     */
    fun twoBreakDistance(p: List<List<Int>>, q: List<List<Int>>): Int {
        val edgesP = tbg.coloredEdges(p)
        val edgesQ = tbg.coloredEdges(q)
        return twoBreakDistanceFromRedEdges(edgesP, edgesQ)
    }

    fun twoBreakDistanceFromRedEdges(edgesP: List<Int>, edgesQ: List<Int>): Int {

        var allEdges = (edgesP + edgesQ)

        /*
         * now walk the list of edges pair-wise
         * Grab the first edge and remember it and delete it.
         * Delete any matching edges until a cycle is found.
         *    Repeat.
         */
        var cycleCount = 0

        do {
            val newList = deleteCycle(allEdges)
            cycleCount++
            allEdges = newList
        } while (allEdges.isNotEmpty())

        // Blocks(P, Q)− Cycles(P, Q)
        return edgesP.size / 2 - cycleCount

    }

    fun deleteCycle(eIn: List<Int>): List<Int> {
        val e = eIn.toMutableList()
        val first = e[0]
        var second = e[1]
        e.removeAt(0)
        e.removeAt(0)
        do {
            val idx = e.indexOf(second)

            if (idx % 2 == 0) {
                second = e[idx + 1]
                e.removeAt(idx)
                e.removeAt(idx)  // removes idx+1
            } else {
                second = e[idx - 1]
                e.removeAt(idx - 1)
                e.removeAt(idx - 1) // removes idx
            }
        } while (first != second)
        return e
    }

}
