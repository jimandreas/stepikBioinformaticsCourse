@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "LiftReturnOrAssignment")

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

    fun cycleToChromosomeDivideBy2(cycle: List<Int>): List<Int> {

        val outList: MutableList<Int> = mutableListOf()

        for (i in 0 until cycle.size / 2) {
            val first = cycle[i * 2]
            val second = cycle[i * 2 + 1]
            if (first < second) {
                outList.add(cycle[i * 2 + 1] / 2)
            } else {
                outList.add(-cycle[i * 2] / 2)
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

    fun graphToGenome(graph: List<Int>, divideByTwo: Boolean = false): List<List<Int>> {
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
                    val chromosome: List<Int>
                    if (divideByTwo) {
                        chromosome = cycleToChromosomeDivideBy2(currentCycle)
                    } else {
                        chromosome = cycleToChromosome(currentCycle, chromosomeBaseValue)
                    }
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

        if (divideByTwo) {
            listOfChromosomes.add(cycleToChromosomeDivideBy2(currentCycle))
        } else {
            listOfChromosomes.add(cycleToChromosome(currentCycle, chromosomeBaseValue))
        }
        return listOfChromosomes
    }

    /**
     *  modified to divide the elements by two rather than running a linear count
     */
    fun graphToGenomeImproved(graph: List<Int>): List<List<Int>> {
        val g = graph.toMutableList()
        val listOfChromosomes: MutableList<List<Int>> = mutableListOf()
        val currentCycle: MutableList<Int> = mutableListOf()

        var startOfChromosome = true
        var firstElement = 0
        var bindingElement = 0

        var i = 0
        while (g.size > 0) {
            var first = 0
            var second = 0
            if (startOfChromosome) {
                first = g[i]
                second = g[i+1]
                i = 0
            } else {
                // find the bindingElement
                val index = g.indexOf(bindingElement)
//                +-
                if (index % 2 == 0) {
                    first = g[index]
                    second = g[index+1]
                    i = index
                } else {
                    first = g[index-1]
                    second = g[index]
                    i = index-1
                }
            }
            g.removeAt(i)
            g.removeAt(i)

            if (startOfChromosome) {
                startOfChromosome = false
                firstElement = first
                bindingElement = second + 1
                if (second % 2 == 0) {
                    bindingElement = second - 1
                }
                currentCycle.add(first)
                currentCycle.add(second)

            } else {

                if (bindingElement == first) {
                    currentCycle.add(first)
                    currentCycle.add(second)
                    bindingElement = second + 1
                    if (second % 2 == 0) {
                        bindingElement = second - 1
                    }
                } else {
                    currentCycle.add(second)
                    currentCycle.add(first)
                    bindingElement = first + 1
                    if (first % 2 == 0) {
                        bindingElement = first - 1
                    }
                }
                //println(bindingElement)
                if (bindingElement == firstElement) {
                    // rotate the cycle one position
                    val size = currentCycle.size
                    val lastElement = currentCycle[size - 1]
                    currentCycle.add(0, lastElement)
                    currentCycle.removeAt(size)

                    val chromosome = cycleToChromosomeDivideBy2(currentCycle)

                    listOfChromosomes.add(chromosome)
                    currentCycle.clear()

                    startOfChromosome = true
                }

            }
        }

        return listOfChromosomes
    }
}
