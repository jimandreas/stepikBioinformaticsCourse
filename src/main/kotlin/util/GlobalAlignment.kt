@file:Suppress("UnnecessaryVariable", "unused")

package util

import ResourceReader

/**
 * Code Challenge: Solve the Global Alignment Problem.

Input: Two protein strings written in the single-letter amino acid alphabet.
Output: The maximum alignment score of these strings followed by an alignment
achieving this maximum score. Use the BLOSUM62 scoring matrix for matches and
mismatches as well as the indel penalty σ = 5.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240305/step/3?unit=212651
 * rosalind: @link: http://rosalind.info/problems/ba5e/
 * book (5.9):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

class GlobalAlignment() {

    private var blosum62 : Array<IntArray> = Array(20) { IntArray(20) }

    init {
        readBLOSUM62()
    }

    private val aminos =
        listOf('A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y')

    fun aminoToOffset(amino: Char): Int {
        return aminos.indexOf(amino)
    }

    fun score(amino1: Char, amino2: Char): Int {
        val a1 = aminoToOffset(amino1)
        val a2 = aminoToOffset(amino2)
        val result = blosum62[a1][a2]
        return result
    }


    private fun readBLOSUM62() {

        val r = ResourceReader()
        val matrix = r.getResourceAsText("BLOSUM62.txt")

        val reader = matrix.reader()
        val lines = reader.readLines()

        for (i in 1 until lines.size) {
            val values = lines[i].split("  ", " ")
            //println(values)
            if (values.size != 21) {
                println("BAD ${values.size}")
                break
            }
            for (j in 1 until 21) {
                blosum62[i - 1][j - 1] = values[j].toInt()
            }
        }

    }
}