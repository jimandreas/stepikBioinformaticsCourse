@file:Suppress("unused", "ReplaceManualRangeWithIndicesCalls")

package algorithms

/**
 * See also:
 * http://rosalind.info/problems/tran/
 * Transitions and Transversions
 *
 * Problem

For DNA strings s1 and s2 having the same length,
their transition/transversion ratio R(s1,s2)

is the ratio of the total number of transitions to the
total number of transversions, where symbol substitutions
are inferred from mismatched corresponding symbols as when
calculating Hamming distance (see “Counting Point Mutations”).

Given: Two DNA strings s1 and s2 of equal length (at most 1 kbp).

Return: The transition/transversion ratio R(s1,s2)
 */


class TransitionsAndTransversions {
    // https://en.wikipedia.org/wiki/Transition_(genetics)
    fun transitionCount(dnaStrings: List<String>): Int {
        var count = 0
        for (i in 0 until dnaStrings[0].length) {
            val c1 = dnaStrings[0][i]
            val c2 = dnaStrings[1][i]
            when {
                c1 == 'A' && c2 == 'G' -> count++
                c1 == 'G' && c2 == 'A' -> count++
                c1 == 'C' && c2 == 'T' -> count++
                c1 == 'T' && c2 == 'C' -> count++
            }
        }
        return count
    }

    // https://en.wikipedia.org/wiki/Transversion

    fun transversionsCount(dnaStrings: List<String>): Int {
        var count = 0
        for (i in 0 until dnaStrings[0].length) {
            val c1 = dnaStrings[0][i]
            val c2 = dnaStrings[1][i]
            when {
                c1 == 'A' && c2 == 'C' -> count++
                c1 == 'C' && c2 == 'A' -> count++

                c1 == 'A' && c2 == 'T' -> count++
                c1 == 'T' && c2 == 'A' -> count++

                c1 == 'C' && c2 == 'G' -> count++
                c1 == 'G' && c2 == 'C' -> count++

                c1 == 'T' && c2 == 'G' -> count++
                c1 == 'G' && c2 == 'T' -> count++
            }
        }
        return count
    }
}

