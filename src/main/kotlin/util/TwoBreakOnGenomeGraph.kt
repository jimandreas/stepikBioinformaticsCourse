@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable")

package util

import java.lang.Integer.max

/**
 *

6.13 CS: Solving the 2-Break Sorting Problem

Various functions that handle pieces of the breakpoint graph processing.

 * See also:
 * stepik:
 * @link: https://stepik.org/lesson/240328/step/2?unit=212674
 * https://stepik.org/lesson/240328/step/3?unit=212674
 *
 * rosalind:
 * @link: http://rosalind.info/problems/ba6j/ 6k
 *
 */

class TwoBreakOnGenomeGraph {

    /**

    Code Challenge: Implement 2-BreakOnGenomeGraph.

    Input: The colored edges of a genome graph GenomeGraph,
    followed by indices i1 , i2 , i3 , and i4 .

    Output: The colored edges of the genome graph resulting from
    applying the 2-break operation 2-BreakOnGenomeGraph(GenomeGraph, i1 , i2 , i3 , i4 ).


    Sample Input:

    (2, 4), (3, 8), (7, 5), (6, 1)
    1, 6, 3, 8

    Sample Output:

    (2, 4), (3, 1), (7, 5), (6, 8)
     */
    fun twoBreakOnGenomeGraph(graph: List<Int>, breakEdges: List<Int>): List<Int> {

        return listOf(0)
    }

    /**
     * Code Challenge: Implement 2-BreakOnGenome.

    Input: A genome P, followed by indices i1 , i2 , i3 , and i4 .
    Output: The genome P' resulting from applying the 2-break operation
    2-BreakOnGenome(GenomeGraph i1 , i2 , i3 , i4 ).

    Sample Input:

    (+1 -2 -4 +3)
    1, 6, 3, 8

    Sample Output:

    (+1 -2)(-3 +4)

     */

    fun twoBreakOnGenome(genome: List<Int>, breakEdges: List<Int>): List<List<Int>> {

        return emptyList()
    }

}
