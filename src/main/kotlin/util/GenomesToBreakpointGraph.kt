@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable")

package util

/**
 *

From Genomes to the Breakpoint Graph

Various functions that handle pieces of the breakpoint graph processing.

 * See also:
 * stepik:
 * @link: https://stepik.org/lesson/240327/step/4?unit=212673
 * https://stepik.org/lesson/240327/step/5?unit=212673
 * https://stepik.org/lesson/240327/step/7?unit=212673
 * https://stepik.org/lesson/240327/step/8?unit=212673
 *
 * rosalind:
 * @link: http://rosalind.info/problems/ba6f/ 6g, 6h, 6i
 *
 */

class GenomesToBreakpointGraph {

    /**

    Code Challenge: Implement ChromosomeToCycle.

    Input: A chromosome "Chromosome" containing n synteny blocks.
    Output: The sequence Nodes of integers between
    1 and 2n resulting from applying ChromosomeToCycle to Chromosome.


    Extra Dataset

    Sample Input:

    (+1 -2 -3 +4)

    Sample Output:

    (1 2 4 3 6 5 7 8)
     */
    fun chromosomeToCycle(c: List<Int>): List<Int> {
        val outList : MutableList<Int> = mutableListOf()
        for (i in c) {
            if (i < 0) {
                outList.add(-i * 2)
                outList.add(-i * 2 - 1)
            } else {
                outList.add(i * 2 - 1)
                outList.add(i * 2)
            }
        }
        return outList
    }

    /**
     * Code Challenge: Implement CycleToChromosome.

    Input: A sequence Nodes of integers between 1 and 2n.
    Output: The chromosome "Chromosome" containing n synteny blocks
    resulting from applying CycleToChromosome to Nodes.

    Sample Input:

    (1 2 4 3 6 5 7 8)

    Sample Output:

    (+1 -2 -3 +4)

     */

    fun cycleToChromosome(cycle: List<Int>) : List<Int> {

        val outList : MutableList<Int> = mutableListOf()
        for (i in 0 until cycle.size / 2) {
            val a = cycle[i*2]
            val b = cycle[i*2+1]
            if (b < a) {
                outList.add(-i-1)
                if (b != i * 2 + 1) {
                    println("oopsie")
                }
            } else {
                outList.add(i+1)
                if (a != i * 2 + 1) {
                    println("oopsie")
                }
            }

        }
        return outList
    }

    /**
     * Code Challenge: Implement ColoredEdges.

    Input: A genome P.
    Output: The collection of colored edges in the genome graph of P in the form (x, y).

    Sample Input:

    (+1 -2 -3)(+4 +5 -6)

    Sample Output:

    (2, 4), (3, 6), (5, 1), (8, 9), (10, 12), (11, 7)
     */

    fun coloredEdges(g: List<List<Int>>): List<Pair<Int, Int>> {

        return listOf(Pair(0,0))
    }
}
