@file:Suppress(
    "ControlFlowWithEmptyBody", "UNUSED_VARIABLE", "unused", "ReplaceManualRangeWithIndicesCalls",
    "LiftReturnOrAssignment", "UnnecessaryVariable", "MemberVisibilityCanBePrivate"
)

package algorithms

/*
 * Turnpike Reconstruction Problem
 * also known as:
 * find set of X satisfying the Difference Multiset



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

        // step 1 - do a quality analysis of the values

        val cleanedValues = turnpikeQualityControl(values)

        // step 2 - verify that there is an n where n(n-1)=values.size

        val n = turnpikeSizeControl(cleanedValues)
        if (cleanedValues.isEmpty() || n == 0) {
            return emptyList()
        }

        // now prepare the working list of deltas.
        //    The end value of the list is the initial solution value in the solution list

        val deltaList = cleanedValues.toMutableList()
        val solutionList: MutableList<Int> = mutableListOf()
        val baseSolutionValue = deltaList.removeAt(deltaList.size - 1)

        // solve

        val result = turnpikeDepthFirstAlgorithm(1, baseSolutionValue, deltaList, mutableListOf(baseSolutionValue))

        if (!result.worked) {
            return listOf(0)
        }
        val r = result.solution.toMutableList()
        // need to add 0 to front and the baseSolutionValue to end
        r.add(0, 0)
        r.add(r.size, baseSolutionValue)
        return r
    }

    data class Result(val worked: Boolean, val solution: List<Int>)

    var debugIter = 0

    /**
     * Depth first algorithm.
     *   pull the end value of the delta list and find its inverse (last value - end value)
     *   then try each value as a solution through recursion.
     *   If they fail, add the value back to the delta list and try the next value
     */
    fun turnpikeDepthFirstAlgorithm(lvl: Int, base: Int, deltas: MutableList<Int>, solution: MutableList<Int>): Result {

        val deltaSize = deltas.size

        // work from high to low for candidates in the solution
        val attemptHi = deltas[deltaSize - 1]
        val attemptLo = base - attemptHi

        val deltasHi = mutableListOf<Int>().apply { addAll(deltas) } // make a copy of current diff list
        deltasHi.remove(attemptHi)
        val recurseSolution = mutableListOf<Int>().apply { addAll(solution) } // make a copy of current solution

        // now attempt to remove all diffs from attemptHi and the recurseSolution
        val worked = removeElementsWithDifferenceMatches(deltasHi, attemptHi, recurseSolution)
        if (worked) {  // then the removal was successful
            recurseSolution.add(attemptHi)
            /*
             * check for a solution.  This happens when all differences are consumed.
             */
            if (deltasHi.size == 0) {
                return Result(true, listOf(attemptHi))
            }

            // now recurse depth first
            val resultFromRecursion = turnpikeDepthFirstAlgorithm(lvl + 1, base, deltasHi, recurseSolution)
            if (resultFromRecursion.worked) {
                val s = resultFromRecursion.solution.toMutableList()
                s.add(attemptHi)
                return Result(true, s)
            }
            // did not work
            recurseSolution.remove(attemptHi)
        }

        /*
         * the attemptHi value didn't work.  Now try attemptLo
         */
        val deltasLo = mutableListOf<Int>().apply { addAll(deltas) } // make a copy of current diff list
        deltasLo.remove(attemptLo)

        // now attempt to remove all diffs from attemptLo and the recurseSolution
        val workedLo = removeElementsWithDifferenceMatches(deltasLo, attemptLo, recurseSolution)
        if (workedLo) {  // then the removal was successful
            recurseSolution.add(attemptLo)
            /*
             * check for a solution.  This happens when all differences are consumed.
             */
            if (deltasLo.size == 0) {
                return Result(true, listOf(attemptLo))
            }

            // now recurse depth first
            val resultFromRecursion = turnpikeDepthFirstAlgorithm(lvl + 1, base, deltasLo, recurseSolution)
            if (resultFromRecursion.worked) {
                val s = resultFromRecursion.solution.toMutableList()
                s.add(attemptLo)
                return Result(true, s)
            }
            // did not work
            recurseSolution.remove(attemptLo)
        }

        // failed to find a solution that worked in the iteration.
        // Fail this subtree
        return Result(false, listOf())
    }

    /**
     * test the input [testThisValue] against the differences in the [currentSolution]
     * All differences must be present in [b] for the [testThisValue] to be valid.
     */
    private fun removeElementsWithDifferenceMatches(
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
        for (e in removeList) {
            b.remove(e)
        }
        return true
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
                        println("turnpikeQualityControl FAIL: ${newList[i]} != (minus) ${newList[size - i - 1]} at index $i")
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