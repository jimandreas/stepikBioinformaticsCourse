@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused"
)

package algorithms

/**
 *

2-Break Sorting Problem: Find a shortest transformation of one genome into another by 2-breaks.

Input: Two genomes with circular chromosomes on the same set of synteny blocks.

Output: The sequence of genomes resulting from applying a shortest sequence of
2-breaks transforming one genome into the other.

Also Known As:  Shortest Rearrangement Scenario (see Pseudocode below)

 * See also:
 * rosalind: http://rosalind.info/problems/ba6d/
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

Sample Dataset

(+1 -2 -3 +4)
(+1 +2 -4 -3)

Sample Output

(+1 -2 -3 +4)
(+1 -2 -3)(+4)
(+1 -2 -4 -3)
(+1 +2 -4 -3)

 */

val pseudoCodeAsGiven = """
ShortestRearrangementScenario(P, Q)
     output P
     RedEdges ← ColoredEdges(P)
     BlueEdges ← ColoredEdges(Q)
     BreakpointGraph ← the graph formed by RedEdges and BlueEdges
     while BreakpointGraph has a non-trivial cycle Cycle  (Same as TwoBreakDistance!!)
          (i1 , i2 , i3 , i4 ) ← path starting at arbitrary red edge in nontrivial red-blue cycle
          RedEdges ← RedEdges with edges (i1, i2) and (i3, i4) removed
          RedEdges ← RedEdges with edges (i1, i4) and (i2, i3) added
          BreakpointGraph ← the graph formed by RedEdges and BlueEdges
          P ← 2-BreakOnGenome(P, i1 , i2 , i4 , i3 )
          output P
""".trimIndent()

val pseudoCodeAsImplemented = """
ShortestRearrangementScenario(P, Q)
     output P
     RedEdges ← ColoredEdges(P)
     BlueEdges ← ColoredEdges(Q)
     BreakpointGraph ← the graph formed by RedEdges and BlueEdges
     while BreakpointGraph has a non-trivial cycle Cycle  (Same as TwoBreakDistance!!)
          (i1 , i2 ) ← pair in BLUE edges not present in RED edges
          RedEdges ← RedEdges with edges (i1, i2) and CORRESPONDING (i3, i4) removed
          RedEdges ← RedEdges with edges (i1, i4) and (i2, i3) added
          BreakpointGraph ← the graph formed by RedEdges and BlueEdges
          NOT NECESSARY as this operation is done above:  P ← 2-BreakOnGenome(P, i1 , i2 , i4 , i3 )
          output P (as re-encoded from RedEdges)
""".trimIndent()

class TwoBreakSortingShortestTransformation {

    val tbg = TwoBreakGenomesToBreakpointGraph()
    val tbogg = TwoBreakOnGenomeGraph()
    val twoBrkDist = TwoBreakDistance()

    fun shortestRearrangementScenario(p: List<List<Int>>, q: List<List<Int>>): List<List<List<Int>>> {
        val redEdgesP = tbg.coloredEdges(p).toMutableList()
        val blueEdgesQ = tbg.coloredEdges(q)
        val blueEdgesCOPY = blueEdgesQ.toMutableList()

        val retList : MutableList<List<List<Int>>> = mutableListOf()
        retList.add(p)


        while (twoBrkDist.twoBreakDistanceFromRedEdges(redEdgesP, blueEdgesQ) > 0) {
            var i1 = 0
            var i2 = 0

            // find a pair that is not in the set of red edges
            var idx = 0
            while (idx < blueEdgesCOPY.size) {
                i1 = blueEdgesCOPY[idx]
                i2 = blueEdgesCOPY[idx+1]
                val result = tbogg.findPair(
                    i1, i2, redEdgesP, doNotRemove = true)
                if (result.second != TwoBreakOnGenomeGraph.DIR.NOTFOUND) {
                    idx += 2
                    continue
                }
                blueEdgesCOPY.removeAt(idx)
                blueEdgesCOPY.removeAt(idx)
                break
            }

            idx = 0
            var i3 = 0
            var i4 = 0
            while (idx < redEdgesP.size) {
                val temp3 = redEdgesP[idx]
                val temp4 = redEdgesP[idx+1]

                if (i1 == temp3) {
                    i3 = temp4
                    redEdgesP.removeAt(idx)
                    redEdgesP.removeAt(idx)
                    break
                } else if (i1 == temp4) {
                    i3 = temp3
                    redEdgesP.removeAt(idx)
                    redEdgesP.removeAt(idx)
                    break
                }
                idx += 2
            }

            idx = 0
            while (idx < redEdgesP.size) {
                val temp3 = redEdgesP[idx]
                val temp4 = redEdgesP[idx+1]

                if (i2 == temp3) {
                    i4 = temp4
                    redEdgesP.removeAt(idx)
                    redEdgesP.removeAt(idx)
                    break
                } else if (i2 == temp4) {
                    i4 = temp3
                    redEdgesP.removeAt(idx)
                    redEdgesP.removeAt(idx)
                    break
                }
                idx += 2
            }

            redEdgesP.add(i1)
            redEdgesP.add(i2)
            redEdgesP.add(i4)
            redEdgesP.add(i3)

            val thisStep = tbg.graphToGenomeImproved(redEdgesP)
            //printChromosomes(thisStep)
            retList.add(thisStep)
        }

        return retList
    }
    //
    fun printChromosomes(listC: List<List<Int>>) {
        val str = StringBuilder()

        for (l in listC) {
            str.append("(")
            str.append(l.joinToString(" ") { String.format("%+d", it) })
            str.append(")")
        }
        println(str.toString())
    }

}
