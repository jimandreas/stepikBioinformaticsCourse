@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog")

package util

/**
 *

2-Break Sorting Problem: Find a shortest transformation of one genome into another by 2-breaks.

Input: Two genomes with circular chromosomes on the same set of synteny blocks.

Output: The sequence of genomes resulting from applying a shortest sequence of
2-breaks transforming one genome into the other.

Also Known As:  Shortest Rearrangement Scenario (see Pseudocode below)

 * See also:
 * stepik: https://stepik.org/lesson/240324/step/5?unit=212670
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

// see: https://stepik.org/lesson/240328/step/5?unit=212674
val pseudoCode = """
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

class TwoBreakSortingShortestTransformation {

    val tbg = TwoBreakGenomesToBreakpointGraph()
    val tbogg = TwoBreakOnGenomeGraph()
    val twoBrkDist = TwoBreakDistance()

    fun shortestRearrangementScenario(p: List<List<Int>>, q: List<List<Int>>): List<List<List<Int>>> {
        var copyP = p
        var redEdgesP = tbg.coloredEdges(p)
        val blueEdgesP = tbg.coloredEdges(q)
//        val blueEdgesQ = tbg.coloredEdges(q)
//
//        var allEdges = (redEdgesP + blueEdgesQ)

        var twoBreakDistance = twoBrkDist.twoBreakDistanceFromEdges(redEdgesP, blueEdgesP)
        var nonTrivialCycles = twoBrkDist.collectRedEdges.filter { it.size > 1 }

        while (nonTrivialCycles.isNotEmpty()) {
            val cyc = nonTrivialCycles[0]
            val i1 = cyc[0]
            val i2 = cyc[1]
            val i3 = cyc[2]
            val i4 = cyc[3]

            //redEdgesP = hackRedEdges(redEdgesP, i1, i2, i3, i4)
            redEdgesP = tbogg.twoBreakOnGenomeGraph(redEdgesP, listOf(i1, i2, i3, i4))

            val thisStep = tbg.graphToGenome(redEdgesP)
            printChromosomes(thisStep)

            twoBreakDistance = twoBrkDist.twoBreakDistanceFromEdges(redEdgesP, blueEdgesP)
            nonTrivialCycles = twoBrkDist.collectRedEdges.filter { it.size > 1 }

        }

        return emptyList()
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

    fun hackRedEdges(red: List<Int>, i1: Int, i2: Int, i3: Int, i4: Int): List<Int> {
        val newRed :MutableList<Int> = red.toMutableList()
        val idx1 = red.indexOf(i1)
        val idx2 = red.indexOf(i2)
        if (idx2 != idx1 + 1) {
            println("hackRedEdges: internal problem!! idx1 $idx1 idx2 $idx2")
            return emptyList()
        }
        newRed.removeAt(idx1)
        newRed.removeAt(idx1)

        val idx3 = newRed.indexOf(i3)
        val idx4 = newRed.indexOf(i4)
        if (idx4 != idx3 + 1) {
            println("hackRedEdges: internal problem!! idx1 $idx3 idx2 $idx4")
            return emptyList()
        }
        newRed.removeAt(idx3)
        newRed.removeAt(idx3)

        newRed.add(i1)
        newRed.add(i4)
        newRed.add(i2)
        newRed.add(i3)

        return newRed

    }


}
