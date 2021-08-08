@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable")

package util

import java.lang.Integer.max

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

class TwoBreakGenomesToBreakpointGraph {

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
        val outList: MutableList<Int> = mutableListOf()
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

    fun cycleToChromosome(cycle: List<Int>, baseValue: Int = 0): List<Int> {

        val outList: MutableList<Int> = mutableListOf()

        for (i in 0 until cycle.size / 2) {
            val a = cycle[i * 2]
            val b = cycle[i * 2 + 1]
            if (b < a) {
                outList.add(-i - 1 - baseValue)
                if (b != (i + baseValue) * 2 + 1) {
                    println("oopsie")
                }
            } else {
                outList.add(i + 1 + baseValue)
                if (a != (i + baseValue) * 2 + 1) {
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

    NOTES: this simply removes the first edge coordinate from each chromosome
    and tacks it onto the back of the chromosome thus circularizing it.
    The appended circular chromosomes are returned as a contatenated list
    for ease of future processing.
     */

    fun coloredEdges(g: List<List<Int>>): List<Int> {

        val allEdges: MutableList<Int> = mutableListOf()

        for (chro in g) {
            val cycleList = chromosomeToCycle(chro)
            val allButZeroElement = cycleList.filterIndexed { index, i -> index != 0 }
            allEdges.addAll(allButZeroElement)
            allEdges.add(cycleList[0])
        }

        return allEdges
    }

    /**
     * Code Challenge: Implement GraphToGenome.

    Input: The colored edges ColoredEdges of a genome graph.
    Output: The genome P corresponding to this genome graph.

    Extra Dataset

    Sample Input:

    (2, 4), (3, 6), (5, 1), (7, 9), (10, 12), (11, 8)

    Sample Output:

    (+1 -2 -3)(-4 +5 -6)

    NOTES: the graph is passed in as a list rather than a series of pairs
     */

    fun graphToGenome(graph: List<Int>): List<List<Int>> {
        val listOfChromosomes: MutableList<List<Int>> = mutableListOf()
        val currentCycle: MutableList<Int> = mutableListOf()

        var chromosomeBaseValue = 0
        var firstNumber = graph[0]
        currentCycle.add(graph[0])
        currentCycle.add(graph[1])
        var i = 2
        while (i < graph.size) {
            val ev = graph[i + 1]
            if (i == graph.size - 2 || ev + 1 == firstNumber || ev - 1 == firstNumber) {
                currentCycle.add(graph[i])
                currentCycle.add(0, graph[i + 1]) // insert at start of list
                if (i != graph.size - 2) {
                    val chromosome = cycleToChromosome(currentCycle, chromosomeBaseValue)
                    listOfChromosomes.add(chromosome)
                    chromosomeBaseValue += chromosome.size
                    currentCycle.clear()

                    firstNumber = graph[i + 2]
                    currentCycle.add(graph[i + 2])
                    currentCycle.add(graph[i + 3])
                    i += 2
                }
            } else {
                currentCycle.add(graph[i])
                currentCycle.add(graph[i + 1])
            }
            i += 2
        }
        listOfChromosomes.add(cycleToChromosome(currentCycle, chromosomeBaseValue))
        return listOfChromosomes
    }
}
