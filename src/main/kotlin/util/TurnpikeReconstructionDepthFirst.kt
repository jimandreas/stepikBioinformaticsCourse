@file:Suppress(
    "ControlFlowWithEmptyBody", "UNUSED_VARIABLE", "unused", "ReplaceManualRangeWithIndicesCalls",
    "LiftReturnOrAssignment", "UnnecessaryVariable"
)

package util

/**
 * Turnpike Reconstruction Problem
 * also known as:
 * find set of X satisfying the Difference Multiset
 * Example:
 *
D=1 2 2 2 3 3 3 4 5 5 5 6 7 8 10
1 2 2 2 3 3 3 4 5 5 5 6 7 8 { 10 }
1   2 2 3 3 3 4 5 5 5 6 7   { 8, 10 }{ if 8 fails then 2 }
Test 10-8=2, OK remove 8, 2

2 2   3 3 4 5 5 5 6     { 7, 8, 10 }{ if 7 fails then 3 }
Test 10-7=3, 8-7=1 OK, remove 1, 3, 7

Try 6 then 4
2 2   3 3 4 5 5 5       { 6, 7, 8, 10 } try 6
10-6=4, 8-6=2 OK, but 7-6 = 1 not in delta set. **FAIL
2 2   3 3   5 5 5 6     { 4, 7, 8, 10 } try 4
10-4=6, 8-4-4 FAIL (as only one 4 is removed)  Both 6 and 4 fail, BACKTRACK

1   2 2   3 3 4 5 5 5 6 7   { 3, 8, 10 } try 3 as 7 has failed
1   2 2   3 3 4   5 5 6      10-3=7, 8-3=5 OK remove 5, 7

1     2     3     5 5       { 6, 3, 8, 10 } Now try 6 then 4
10-6=4, 8-6=2, 6-3=3  remove 2, 3, 4  OK

1     2     3       5       { 5, 6, 3, 8, 10 }  Now try 5 (no then)
try 5: 10-5=5, 8-5=3, 6-5=1, 5-3=2, 6-5=1 all OK
empty  SUCCESS!

Solution: 0,5,6,3,8,10

 *
 *  Stepik:  https://stepik.org/lesson/240294/step/1?unit=212640
 *  Turnpike Problem: Given all pairwise distances between points on a line segment, reconstruct the positions of those points.
 *
 *  Input: A collection of integers L.
 *  Output: A set of integers A such that ∆A = L.
 *
 *  Rosalind: http://rosalind.info/problems/pdpl/
 *  Given: A multiset L containing (n2) positive integers for some positive integer n
 *  Return: A set X containing n nonnegative integers such that ΔX=L.
 *
 *  other refs:
 *  https://en.wikipedia.org/wiki/NP-intermediate
 *  https://dl.acm.org/doi/10.1145/98524.98598 (1990)!!
 *  https://www.collectionscanada.gc.ca/obj/s4/f2/dsk1/tape3/PQDD_0014/NQ61635.pdf refs 1930s papers (PhD paper)
 */

/*
 * NOTES:
 * 1) if negative values are present then the multiset should be balanced.   This can be checked.
 * 2) the positive side of the multiset > 0 should evaluate such that N(N-2)/2 evaluates to an integer N
 * 3) N thus defines the set of 0 plus N values that satisfy the difference multiset
 */

class TurnpikeReconstructionDepthFirst {

    fun turnpikeReconstructionDepthFirst(values: List<Int>): List<Int> {

        // step 1 - do a quality analysis of the values, and verify that there is an n

        val cleanedValues = turnpikeQualityControl(values)
        val n = turnpikeSizeControl(cleanedValues)
        if (cleanedValues.isEmpty() || n == 0) {
            return emptyList()
        }

        val bruteForceList = cleanedValues.toMutableList()

        val solution: MutableList<Int> = mutableListOf()
        val initialValue = bruteForceList[bruteForceList.size - 1]
        bruteForceList.removeAt(bruteForceList.size - 1)
        val result = turnpikeBruteForceAttempt(1, bruteForceList, listOf(initialValue))

        if (!result.worked) {
            return listOf(0)
        }
        val r = result.solution.toMutableList()
        // need to add 0 to front and initialValue to end
        r.add(0, 0)
        r.add(r.size, initialValue)
        return r
    }

    /**
     * test the input [testThisValue] against the differences in the [currentSolution]
     * All differences must be present in [b] for the [testThisValue] to be valid.
     */
    fun removeElementsWithDifferenceMatches(
        b: MutableList<Int>,
        testThisValue: Int,
        currentSolution: List<Int>
    ): Boolean {
        val removeList: MutableList<Int> = mutableListOf()
        for (entry in currentSolution) {
            val difference = abs(testThisValue - entry)
            if (!b.contains(difference)) {
                return false
            }
            removeList.add(difference)
        }
        for (e in removeList.distinct()) {
            b.remove(e)
        }
        return true
    }


    data class Result(val worked: Boolean, val solution: List<Int>)

    /**
     * The list [b] has been checked to be zero based.
     * This means that each entry in the solution is represented in the
     * list of differences by [itself - zero].
     * list = [0,] 2, 2, 3, 3, 4, 5, 6, 7, 8 [,10]
     */

    var debugIter = 0
    fun turnpikeBruteForceAttempt(lvl: Int, b: MutableList<Int>, currentSolution: List<Int>): Result {

        val initialSize = b.size
        val recurseSolution = mutableListOf<Int>().apply { addAll(currentSolution) } // make a copy of current solution

        for (i in 0 until initialSize) {


            /* if (++debugIter % 50000 == 0) {
            println("$debugIter level $lvl i=$i of $initialSize  curSoln ${currentSolution}")
        }*/

            val attemptSolution = b[initialSize - 1 - i] // work from high to low for candidates in the solution

            if (i == initialSize - 1 && lvl < 10) {
                println("level $lvl i=$i of $initialSize  attempt=$attemptSolution b $b")
            }

            val c = mutableListOf<Int>().apply { addAll(b) } // make a copy of current diff list

            c.remove(attemptSolution)
            val worked = removeElementsWithDifferenceMatches(c, attemptSolution, recurseSolution)
            if (worked) {
                recurseSolution.add(attemptSolution)

                /*
             * check for a solution.  This happens when all differences are consumed.
             */
                if (c.size == 0) {
                    return Result(true, listOf(attemptSolution))
                }

                // now recurse
                val resultFromRecursion = turnpikeBruteForceAttempt(lvl + 1, c, recurseSolution)
                if (resultFromRecursion.worked) {
                    val s = resultFromRecursion.solution.toMutableList()
                    s.add(attemptSolution)
                    return Result(true, s)
                } else {
                    recurseSolution.remove(attemptSolution)
                    c.add(attemptSolution)
                }
            } else {
                c.add(attemptSolution)
            }
        }
        // failed to find a solution that worked in the iteration.
        // Fail this subtree
        return Result(false, listOf())
    }

    private fun abs(a: Int): Int {
        if (a > 0) {
            return a
        } else {
            return -a
        }
    }

    /**
     * turnpikeQualityControl
     *
     * if the list starts with a negative value, then
     *    -> IT MUST BE SYMMETRIC, e.g. -10 -8 -7 -6 -5 -4 -3 -3 -2 -2 0 0 0 0 0 2 2 3 3 4 5 6 7 8 10
     *
     */
    fun turnpikeQualityControl(values: List<Int>): List<Int> {

        val newList = values.toMutableList()
        val size = values.size

        val iter = values.iterator().withIndex()
        if (values[0] < 0) {
            while (iter.hasNext()) {
                val next = iter.next()
                val i = next.index
                // check for symmetry while deleting the negative values
                if (values[i] < 0) {
                    if (-values[i] != values[size - i - 1]) {
                        println("FAIL: ${newList[i]} != (minus) ${newList[size - i - 1]} at index $i")
                        return emptyList()
                    }
                    newList.removeAt(0)  // trim each negative number
                }
            }
        }
        // demand at least one zero
        if (newList[0] != 0) {
            return emptyList()
        }

        /*
     * now turn to the new list.   All negative entries (if any) should have been pruned.
     * delete any leading 0's
     */
        while (newList[0] == 0) {
            newList.removeAt(0)
        }
        return newList
    }

    /**
     * turnpikeSizeControl
     *
     * find the n such that the diffence multiset has (n pick 2) elements.
     *    If no such n exists, this is an error
     *
     * @param values multiset
     * @return 0 if error, otherwise the n where n(n-1)/2 = values.size
     *
     * See also:
     * @link https://math.stackexchange.com/questions/103377/how-to-reverse-the-n-choose-k-formula
     *
     */
    fun turnpikeSizeControl(values: List<Int>): Int {
        var i = 1
        while (i * i / 2 < values.size) {
            i++
        }
        var j = i - 1
        val limit = i * i / 2

        do {
            val testValue = j * (j - 1) / 2
            if (testValue == values.size) {
                return j
            }
            j++
        } while (testValue < limit)

        println("ERROR no i such that i(i-1) = ${values.size} exists")

        return 0
    }

}