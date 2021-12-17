@file:Suppress("UNUSED_VARIABLE", "UnnecessaryVariable", "CatchMayIgnoreException")

package problems

import algorithms.frequentWordsWithMismatches
import java.lang.StringBuilder

/*
 You should now have a good sense of the practical considerations involved in locating ori and DnaA boxes computationally. We will take the training wheels off and ask you to solve a Final Challenge by working with the Salmonella enterica genome.

Final Challenge: Find a DnaA box in Salmonella enterica.
 */

// find mins for Skew in the Salmonella enterica.


fun main() {

    val loader = Loader011002()
    val salmonellaGenome = loader.getResourceAsText("SalmonellaEnterica.txt")
//    val salmonellaGenome = loader.getResourceAsText("VibrioCholerae.txt")
    println("genome read - number of chars:  ${salmonellaGenome.length}")

    //val mins = problems.scanForSkew(examinationString)
    val mins = scanForSkew2(salmonellaGenome)
    println(mins)
    // answer: [3764856, 3764858]
    // box of 500 with 9-mers with reverse complements:

    val box = salmonellaGenome.substring(3764856-250, 3764856+250)

    val g = box
    val k = 12
    val m = 0

    val matchList = frequentWordsWithMismatches(g, k, m, scanReverseComplements = true).sorted()
    println("quantity = ${matchList.size}")  // step 5
    for (i in matchList) {
        print("$i ")
    }


}

fun scanForSkew2(str: String): List<Int> {
    var level = 0
    val skewList: MutableList<Int> = arrayListOf()

    // print("$level ")
    skewList.add(0)
    for (c in str) {
        when (c) {
            'A' -> {
                //print("$level ")
            }
            'C' -> {
                level -= 1
                //print("$level ")
            }
            'G' -> {
                level += 1
                //print("$level ")
            }
            'T' -> {
                //print("$level ")
            }
            else -> {
                //println("problems.scanForSkew2 c is $c in str $str")
            }
        }
        skewList.add(level)
    }
    return (findMinimum2(skewList))
}

fun findMinimum2(skewList: List<Int>): List<Int> {
    var newMin = Integer.MAX_VALUE
    val minList: MutableList<Int> = arrayListOf()

    for (skew in skewList) {
        if (newMin > skew) {
            newMin = skew
        }
    }
    val iter = skewList.iterator().withIndex()
    while (iter.hasNext()) {
        val next = iter.next()
        if (next.value == newMin) {
            minList.add(next.index)
        }
    }
    return minList
}

class Loader011002 {
    fun getResourceAsText(path: String): String {
        val ress = this.javaClass.getResource(path)
        val buf = StringBuilder()
        try {
            val foo = ress!!.openStream()
            val reader = foo.reader()

            reader.forEachLine {
                if (it.isNotEmpty()) {
                    if (it[0] != '>') {
                        buf.append(it)
                    }
                }
                //println(it)
            }
        } catch (e: Exception) {
        }

        return buf.toString()
    }

}