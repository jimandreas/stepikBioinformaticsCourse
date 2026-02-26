@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems

import algorithms.SharedKmers

/**
 *

Shared k-mers Problem: Given two strings, find all their shared k-mers.

Input: An integer k and two strings.

Output: All k-mers shared by these strings, in the form of ordered
pairs (x, y) corresponding to starting positions of these k-mers in the respective strings.

 * See also:
 * rosalind: http://rosalind.info/problems/ba6e/
 *
 * Part 9 of 9 of a series of lectures on "Are There Fragile Regions in the Human Genome?"
 * covering Chapter 6 of Bioinformatics Algorithms:
 * An Active Learning Approach, by Phillip Compeau and Pavel Pevzner.
 *
 * youtube: https://www.youtube.com/watch?v=vTbQA8vV3lY
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

/**

Since downloading long human and mouse chromosomes is time-consuming,
we will instead solve the Shared k-mers Problem for the bacteria E. coli and S. enterica.
Download E. coli Genome      (same as earlier file)
Download S. enterica Genome  (a one-string form)

Exercise Break: How many shared 30-mers do the E. coli and S. enterica genomes share?

 */

/*
 * !!!!
 * Be sure to add the VM option to the run configuration:
 *
 * -Xmx4048m
 *
 * Or this will hit a Heap overflow.  Yuck.  Changing the IDEA setting has no effect.  Yuck.
 */

fun main() {

    val sk = SharedKmers()
    val reader = ReadGenomeFromResources0611p07()
    val eColiGenome = reader.getResourceAsText("EcoliGenome.txt")
    val sEntericaGenome = reader.getResourceAsText("SalmonellaEntericaOneString.txt")

    val result = sk.findSharedKmersHashed(30, eColiGenome, sEntericaGenome)

    println(result.size)

}

class ReadGenomeFromResources0611p07 {
    fun getResourceAsText(path: String): String {
        val ress = this.javaClass.getResource(path)
        val retStr = ress!!.readText()
        return retStr
    }
}


