@file:Suppress(
    "SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody", "unused",
    "MemberVisibilityCanBePrivate"
)

package algorithms

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.max

/**
 * rosalind:
 * http://rosalind.info/problems/lgis/
 * Longest Increasing Subsequence (and Decreasing)
 *
 * See also:
 * https://www.youtube.com/watch?v=CE2b_-XfVDk
 * Longest Increasing Subsequence
 * Tushar Roy - Coding Made Simple
 *
 */
class LongestSubsequence {
    /**
     * Problem

    A subsequence of a permutation is a collection of elements of the permutation
    in the order that they appear. For example, (5, 3, 4) is a subsequence of (5, 1, 3, 4, 2).

    A subsequence is increasing if the elements of the subsequence increase,
    and decreasing if the elements decrease. For example, given the permutation
    (8, 2, 1, 6, 5, 7, 4, 3, 9), an increasing subsequence is (2, 6, 7, 9),
    and a decreasing subsequence is (8, 6, 5, 4, 3). You may verify that
    these two subsequences are as long as possible.

    Given: A positive integer n≤10000 followed by a permutation π of length n

    Return: A longest increasing subsequence of π, followed by a longest decreasing
    subsequence of π.
     */


    /**
     * this algorithm follows what I call the "voting" mechanism
     * where each number in the list has a corresponding
     * vote in the 1D array.   The votes are tabulated by
     * comparing a value with previously voted components
     * where the previous value is less than the current value.
     * The votes are then scanned in reverse order to build
     * the subsequence.
     * See the youtube video referenced above for a walk through of
     * this technique.
     */
    fun findLongestIncreasingSubsequence(l: List<Int>): List<Int> {

        val voteArray = mk.d1array(l.size) { 1 }

        for (i in 1 until l.size) {
            var maxVote = Int.MIN_VALUE
            for (j in 0 until i) {
                if (l[j] < l[i]) {
                    if (voteArray[j] > maxVote) {
                        maxVote = voteArray[j]
                        voteArray[i] = maxVote + 1
                    }
                }
            }
        }

        /**
         * the voting for ascending array entries is completed.
         * Now walk the list backwards and pick the highest
         * first arrives of each vote for the list
         */

        var maxVote = voteArray.max()
        val outList: MutableList<Int> = mutableListOf()

        for (i in l.size - 1 downTo 0) {
            if (voteArray[i] == maxVote) {
                outList.add(0, l[i])
                if (--maxVote == 0) {
                    break
                }
            }
        }
        return outList
    }


    /**
     * for a list [l] determine the longest
     * increasing and decreasing subsequences and then
     * @return these two lists
     */
    fun longestSequences(l: List<Int>): List<List<Int>> {

        val increasingSeq = findLongestIncreasingSubsequence(l)

        // for the descending list - invert the list, find the increasing list
        //   using negative numbers, then re-invert and sort descending
        val invertedList = l.map { it * -1 }
        val invertedListIncreasing = findLongestIncreasingSubsequence(invertedList)
        val decreasingSeq = invertedListIncreasing.map { it * -1 }.sortedDescending()

        return listOf(increasingSeq, decreasingSeq)
    }
}