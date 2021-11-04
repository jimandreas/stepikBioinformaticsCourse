@file:Suppress("unused", "ReplaceManualRangeWithIndicesCalls")

package algorithms

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

/**
 * Various problems that occur in the Rosalind series that are not present
 * in the Stepik course.
 *    Information about these can be found online via presentations by
 *
Phillip Compeau & Pavel Pevzner
http://bioinformaticsalgorithms.org
 */

/**
 * SymbolToNumber Int extension function
 *   Takes an ACGT nucleotide character and returns the 0123 representation
 */
fun Char.symbolToNumber(): Int {
    return when (this) {
        'A' -> 0
        'C' -> 1
        'G' -> 2
        'T' -> 3
        else -> -1
    }
}

fun Int.numberToSymbol(): Char {
    return when (this) {
        0 -> 'A'
        1 -> 'C'
        2 -> 'G'
        3 -> 'T'
        else -> ' '
    }
}

fun Char.dnaToRna(): Char {
    return when (this) {
        'A' -> 'A'
        'C' -> 'C'
        'G' -> 'G'
        'T' -> 'U'
        else -> ' '
    }
}

/**
 * PatternToNumber
 *    Basically a hash function forms the result by shifting the nucleotide string by
 *    two positions and concatinating the next value.
 *
 *    In the lecture notes recursion is used but bascically this is faster as an
 *    iterative function
 */

fun patternToNumber(pattern: String): Long {
    var hashValue: Long = 0
    for (i in 0 until pattern.length) {
        hashValue = hashValue.shl(2) + pattern[i].symbolToNumber()
    }
    return hashValue
}

/**
 * NumberToPattern
 *   reverse the PatternToNumber hash function back to the nucleotide string
 *   http://rosalind.info/problems/ba1m/
 *   Implement NumberToPattern

Convert an integer to its corresponding DNA string.

Given: Integers index and k.

Return: NumberToPattern(index, k).
 *
 */

fun numberToPattern(num: Long, kLen: Int): String {
    val str = StringBuffer()
    var remainder = num
    for (i in 0 until kLen) {
        val sym = remainder%4
        str.insert(0, sym.toInt().numberToSymbol())
        remainder /= 4
    }

    return str.toString()
}

fun frequencyArray(genome: String, kLen: Int): List<Int> {
    val freqArray = mk.d1array(4.shl((kLen-1)*2)) {0} // size is 4 ** kLen

    for (i in 0..(genome.length - kLen)) {
        val s = genome.substring(i, i + kLen)
        val hash = patternToNumber(s).toInt()
        freqArray[hash]++
    }
    return freqArray.toList()
}

/**
 * add the [key] to the [map].   Increment the count of the key in the map.
 * @param map - a mutable map of type K
 * @param key - they key of type K to add to the map
 * @link https://www.techiedelight.com/increment-value-map-kotlin/
 */
private fun <K> increment(map: MutableMap<K, Int>, key: K) {
    map.putIfAbsent(key, 0)
    map[key] = map[key]!! + 1
}


/**
 * String Composition Problem

Generate the k-mer composition of a string.

Given: An integer k and a string Text.

Return: Compositionk(Text) (the k-mers can be provided in any order).
 */
fun kmerList(kLen: Int, genome: String): List<String> {
    val retList : MutableList<String> = mutableListOf()
    for (i in 0..genome.length - kLen) {
        retList.add(genome.substring(i, i+kLen))
    }
    return retList
}

/**
 * The following problems have no equivalent Stepik problems to my knowledge.
 * Rosalind:
 * Generate the Frequency Array of a String
 * http://rosalind.info/problems/ba1k/
 * Implement PatternToNumber
 * http://rosalind.info/problems/ba1l/
 * Implement NumberToPattern
 * http://rosalind.info/problems/ba1m/
 * String Composition Problem
 * http://rosalind.info/problems/ba3a/
 */
fun main() {
//    val result2 = frequencyArray("CCCTGGTCTCGGGCGTCCATTTGCGTCCCCTGTGAAGCAGGGTCCACCAGTGTCACAATTACTCTCAGAGTCTTTTTGTTTTTAACCTCCTTTGTCTTAACTACATCTACAAGGTGGTGACGACTCCTTCCATACGATCGTTCCGCACACAGTGATGGTTTTCGTCGGCATGCCCCTGATACATGGGGTGTGCGGTGATCCCGAATCGTCCGACCAGCGAAACTCGATCGTGGACACGCTCGCTCGGAAGAGGAAAATTGTATACATGGTATCGACTTCGACCAACACACGGTGGTCTGTGATTCGGCAACAATGCCACGTCCGGATGATCGACTGGGTGTATGTTATCGGGAGATGTTCGTCGTTGCGTGGACTGCCACTGGCCGCATTAGCGTAGACCCCAAAGTGTTCTCCTAGCGGTAACTGCATCAGTTTTACACAGTGCAACCATGTCGGGTCACCTCTAGCGGATGTGAGTTGCCGTGCATATTCGCCATTTAAATGTCAGAGCTAAGATGCGTTACTCAACACCGTGCACCCACACAGGCAGACCGTAGAACCGCAATGCCAGTATACACGCCTACGATAAGCTACCGCTGCTTCATAACGTGCTCTGAGAGGGTTTGATGCACAGGTTCCGGTTTTTGCTTAGGTAACTAACCCGAGACGCCCACTCCTACTCCTGTAGGGTTAGACGATTTTGAGTTATCTATAAAGGCTCCAATACGCGCTATAAATTTCTGGTGAATTAGTGCCAAGAGGCAAAGTCTTCACCTGTGGGCCCA", 7)
//    println(result2.joinToString(" "))

//    println(patternToNumber("AAACTCGTCAGCTCTGTCAGAAGGGAACGG"))

//    println(numberToPattern(8409, 7))

//    val nlist = (neighbors("AGGGTGGAATCT", 3))
//    println(nlist.joinToString("\n"))
//    println(kmerList(50, "GTACTGGCTACTTATAATTCGGAGGATACATTTCACTTGCTCCGGTGGGAATCTACTCGCTAAATTGCCGGGGTGCGAATCGCGGAACCATAGGGTGATTTCAGGTAGCGACTCGGTGCTTATCGTTTCTTAAAGCCTCCCAAGGCAGCAATTGGTCCCGTGCCGTTAGTGGCGGACGACACGATCGGACACTATATAATCTACCCCAGCCACCGACTCACGGGAGTTGTTACGTCACCCAGACGCCTAAGGCTGACATGTTTCTCTAATCAAGCAAACATAGCGCTGGATTGCTGATCCCCGAATCTAGACGCATAAGTGGCAGGATGTATAGCCTACCCCCAATCGCTTCAGCCAAGATTAGACTACTAGTTGGGATTCGAAGATCTGCACTCTATATCCATCTCAAGTTAGTCCTAATAGTCGGACAGGAGTAATAAAGTGATAACTCTCCAAAGTTGCCCTTACACGGTTATTGCTTGTGCAAGATCCATAGCTGGTACCAACGGTCTGATTATCGAGTCTAGCTGAACAGCCGGCAGAGGTATGGTAGCGGCGCAGATGCTCCAGGCTAGCGTTAACAGCCCCGACGTATATGCGTGGGCCGAATGCCTACTGAGCACCGGCATTGCCCACGGAATACGACTTTCGATCTCGATCGATTCTCATTTTATATATTGAGTAATGCGACCGCCCTTTGTTGGGACCGCCGAATATTATCGATGGCTGGATGCTGTTGACCGGACGACAAGTGCCGGTGTGCTGCCGCTGATGGACACCAGAAGGTGCGAATTGGTCCTTGTTGGGTGTAATGAATTCTTAGCACTAGCTCACGGTTTGAGATTGCCGCCGCGTCGGGAAGGGGTCCCGTTCACGTTTGAAGCCGACGGCGTGCCCCAGCGACGGGTACAAATTCCGCAACAGAAGAGCATACGAAACCACGTCCGGCAGACGGTCTAAGCTACGGTGGGTGTGTCGAGCCGTCCAACCCGACCAAGAA").joinToString("\n"))

}
