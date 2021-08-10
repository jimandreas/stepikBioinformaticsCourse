@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog")

package util

import java.lang.Integer.min
import java.lang.Math.max

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

        val find1 = findPair(breakEdges[0], breakEdges[1], graph)
        val find2 = findPair(breakEdges[2], breakEdges[3], find1.third)
        val outList = find2.third.toMutableList()
        when {
            find1.second == DIR.FORWARD && find2.second == DIR.FORWARD -> {
                outList.add(breakEdges[0])
                outList.add(breakEdges[2])
                outList.add(breakEdges[1])
                outList.add(breakEdges[3])
            }
            find1.second == DIR.FORWARD && find2.second == DIR.REVERSE -> {
                outList.add(breakEdges[0])
                outList.add(breakEdges[2])
                outList.add(breakEdges[3])
                outList.add(breakEdges[1])
            }
            find1.second == DIR.REVERSE && find2.second == DIR.FORWARD -> {
                outList.add(breakEdges[2])
                outList.add(breakEdges[0])
                outList.add(breakEdges[1])
                outList.add(breakEdges[3])
            }
            find1.second == DIR.FORWARD && find2.second == DIR.REVERSE -> {
                outList.add(breakEdges[0])
                outList.add(breakEdges[2])
                outList.add(breakEdges[3])
                outList.add(breakEdges[1])
            }
        }
        return outList
    }





    private enum class DIR { FORWARD, REVERSE, NOTFOUND }

    private fun findPair(
        p1: Int,
        p2: Int,
        graph: List<Int>,
        doNotRemove: Boolean = false
    ): Triple<Int, DIR, List<Int>> {
        var i = 0
        val newGraph = graph.toMutableList()
        while (i < graph.size) {
            if (graph[i] == p1 && graph[i + 1] == p2) {
                if (!doNotRemove) {
                    newGraph.removeAt(i)
                    newGraph.removeAt(i)
                }
                return Triple(i, DIR.FORWARD, newGraph)
            } else if (graph[i] == p2 && graph[i + 1] == p1) {
                if (!doNotRemove) {
                    newGraph.removeAt(i)
                    newGraph.removeAt(i)
                }
                return Triple(i, DIR.REVERSE, newGraph)
            }
            i += 2
        }
        return Triple(0, DIR.NOTFOUND, emptyList())

    }

    /**
     * Code Challenge: Implement 2-BreakOnGenome.

    Input: A genome P, followed by indices i1 , i2 , i3 , and i4 .
    Output: The genome P' resulting from applying the 2-break operation
    2-BreakOnGenome(GenomeGraph i1 , i2 , i3 , i4 ).

    Sample Input:

    (+1 -2 -4 +3)
    1, 6, 3, 8

    NOTES:

    Sample Output:

    (+1 -2)(-3 +4)

     */

    fun twoBreakOnGenome(genome: List<Int>, breakEdges: List<Int>): List<List<Int>> {
        val twoBreakUtils = TwoBreakGenomesToBreakpointGraph()
        val cycleList = twoBreakUtils.coloredEdges(listOf(genome))
        val l = cycleToArrayListOfPairs(cycleList)

        val result = twoBreakOnGenomeGraphReplaceNode(l, breakEdges)
        val resultList = arrayListOfPairsToList(result)
        val tryResult = twoBreakUtils.graphToGenomeImproved(resultList)

        return tryResult
    }

    private fun arrayListOfPairsToList(l: ArrayList<Pair<Int, Int>>): List<Int> {
        val m: MutableList<Int> = mutableListOf()
        for (item in l) {
            m.add(item.first)
            m.add(item.second)
        }
        return m
    }

    private fun cycleToArrayListOfPairs(cycle: List<Int>): ArrayList<Pair<Int, Int>> {
        var i = 0
        val l: ArrayList<Pair<Int, Int>> = arrayListOf()
        while (i < cycle.size) {
            l.add(Pair(cycle[i], cycle[i + 1]))
            i += 2
        }
        return l
    }

    fun twoBreakOnGenomeGraphReplaceNode(g: ArrayList<Pair<Int, Int>>, breakEdges: List<Int>): ArrayList<Pair<Int, Int>> {

        var breakIndex1 = g.indexOf(Pair(breakEdges[0], breakEdges[1]))
        if (breakIndex1 == -1) {
            breakIndex1 = g.indexOf(Pair(breakEdges[1], breakEdges[0]))
        }
        var breakIndex2 = g.indexOf(Pair(breakEdges[2], breakEdges[3]))
        if (breakIndex2 == -1) {
            breakIndex2 = g.indexOf(Pair(breakEdges[3], breakEdges[2]))
        }
        if (breakIndex1 == -1 || breakIndex2 == -1) {
            println("OOOPS didn't find break edge in graph twoBreakOnGenomeGraphReplaceNode")
            return arrayListOf()
        }
        if (min(breakEdges[0], breakEdges[1]) < min(breakEdges[2], breakEdges[3])) {
            g[min(breakIndex1, breakIndex2)] = Pair(breakEdges[0], breakEdges[2])
            g[max(breakIndex1, breakIndex2)] = Pair(breakEdges[1], breakEdges[3])
        } else {
            g[max(breakIndex1, breakIndex2)] = Pair(breakEdges[0], breakEdges[2])
            g[min(breakIndex1, breakIndex2)] = Pair(breakEdges[1], breakEdges[3])
        }

        val gRecombined : ArrayList<Pair<Int, Int>> = arrayListOf()
        gRecombined.addAll(g.subList(0, min(breakIndex1, breakIndex2)+1))
        gRecombined.addAll(g.subList(max(breakIndex1, breakIndex2)+1, g.size))
        gRecombined.addAll(g.subList(min(breakIndex1, breakIndex2)+1, max(breakIndex1, breakIndex2 )))
        gRecombined.add(g[max(breakIndex1, breakIndex2)])

        return gRecombined
    }

}
