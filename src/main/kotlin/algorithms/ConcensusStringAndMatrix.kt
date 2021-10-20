@file:Suppress("unused")

package algorithms

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import kotlin.math.max

/**
 * See also:
 * http://rosalind.info/problems/cons/
 * Consensus and Profile
 *
 * Problem

A matrix is a rectangular table of values divided into rows and columns. An m×n
matrix has m rows and n columns. Given a matrix A, we write Ai,j to indicate
the value found at the intersection of row i and column j

Say that we have a collection of DNA strings, all having the same length n.
Their profile matrix is a 4×n matrix P in which P1,j represents the number
of times that 'A' occurs in the jth position of one of the strings,
P2,j represents the number of times that C occurs in the jth position, and so on (see below).

A consensus string c is a string of length n formed from our collection
by taking the most common symbol at each position; the jth symbol of c
therefore corresponds to the symbol having the maximum value in the j-th
column of the profile matrix. Of course, there may be more than one most common symbol,
leading to multiple possible consensus strings.

 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

class ConcensusStringAndMatrix {
    lateinit var concensusProfileMatrix: D2Array<Int>

    fun findMostLikelyCommonAnscestor(sList: List<String>): String {

        // error checking
        val len = sList[0].length
        for (s in sList) {
            if (s.length != len) {
                println("ERROR not all strings are the same length: expect $len found a len of ${s.length} for string $s")
                return ""
            }
        }

        concensusProfileMatrix = mk.d2array(4, len) { 0 }

        for (s in sList) {
            for (i in 0 until len) {
                when (s[i]) {
                    'A' -> concensusProfileMatrix[0, i] += 1
                    'C' -> concensusProfileMatrix[1, i] += 1
                    'G' -> concensusProfileMatrix[2, i] += 1
                    'T' -> concensusProfileMatrix[3, i] += 1
                }
            }
        }

        // now find the concensus

        val str = StringBuilder()
        for (i in 0 until len) {
            val maxAbundance = max(
                concensusProfileMatrix[0, i],
                max(
                    concensusProfileMatrix[1, i],
                    max(
                        concensusProfileMatrix[2, i],
                        concensusProfileMatrix[3, i]
                    )
                )
            )

            val codon = when (maxAbundance) {
                concensusProfileMatrix[0, i] -> 'A'
                concensusProfileMatrix[1, i] -> 'C'
                concensusProfileMatrix[2, i] -> 'G'
                concensusProfileMatrix[3, i] -> 'T'
                else -> ' '
            }
            str.append(codon)
        }
        return str.toString()
    }

}