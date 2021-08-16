@file:Suppress("UNUSED_VARIABLE", "UnnecessaryVariable", "unused")

/**
1.7 Peculiar Statistics of the Forward and Reverse Half-Strands
Minimum Skew Problem: Find a position in a genome where the skew diagram attains a minimum.

Input: A DNA string Genome.
Output: All integer(s) i minimizing Skewi (Genome) among all values of i (from 0 to |Genome|).

Code Challenge: Solve the Minimum Skew Problem.

Sample Input:

TAAAGACTGCCGAGAGGCCAACACGAGTGCTAGAACGAGGGGCGTAAACGCGGGTCCGAT

Sample Output:

11 24

See also:
stepik: https://stepik.org/lesson/240220/step/10?unit=212566
rosalind: http://rosalind.info/problems/ba1f/
 */

// last test: find mins for Skew in the EcoliGenome.   It matched what
// was reported in class.

fun main() {

    val testString = "TAAAGACTGCCGAGAGGCCAACACGAGTGCTAGAACGAGGGGCGTAAACGCGGGTCCGAT"

    val examinationString = "TAAAGACTGCCGAGAGGCCAACACGAGTGCTAGAACGAGGGGCGTAAACGCGGGTCCGAT"


    val mins = scanForSkew(examinationString)
//    val loader = ResourceReader4()
//    val ecoliString = loader.getResourceAsText("EcoliGenome.txt")
//    val mins = scanForSkew(ecoliString)
    println(mins.joinToString(" "))
}

fun scanForSkew(str: String): List<Int> {
    var level = 0
    val skewList : MutableList<Int> = arrayListOf()

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
                //println("scanForSkew c is $c in str $str")
            }
        }
        skewList.add(level)
    }
    return(findMinimum(skewList))
}

fun findMinimum(skewList: List<Int>): List<Int> {
    var newMin = Integer.MAX_VALUE
    val minList : MutableList<Int> = arrayListOf()

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

private class ResourceReader4 {
    fun getResourceAsText(path: String): String {
        val ress = this.javaClass.getResource(path)
        val retStr = ress!!.readText()
        return retStr
    }
}

