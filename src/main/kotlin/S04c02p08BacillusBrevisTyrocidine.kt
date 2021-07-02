@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

import util.*


/**
 * @link: https://stepik.org/lesson/240277/step/7?unit=212623
 *
Peptide Encoding Problem: Find substrings of a genome encoding a given amino acid sequence.

Input: A DNA string Text, an amino acid string Peptide, and the array GeneticCode.
Output: All substrings of Text encoding Peptide (if any such substrings exist).

Code Challenge: Solve the Peptide Encoding Problem.

Note: The solution may contain repeated strings if
the same string occurs more than once as a substring of Text and encodes Peptide.

 */

fun main() {

    val loader = JoinedStringLoader()
    val ecoliString = loader.getResourceAsJoinedString("Bacillus_brevis.txt")

    val peptide = "VKLFPWFNQY"

    val result = scanDnaStringForPeptideEncoding(ecoliString, peptide)

    println("result is $result")

    val reverseDnaString = reverseComplement(ecoliString)

    println("reverse complement completed")
    val resultR = scanDnaStringForPeptideEncoding(reverseDnaString, peptide, true)

    val total = result+resultR
    println(total)
    total.sorted().forEach { println(it) }

    // answer: 0 - none found - Correct!!

}

private class  JoinedStringLoader {

    fun getResourceAsJoinedString(path: String): String {
        val ress = this.javaClass.getResource(path)
        val lines = ress!!.readText(Charsets.UTF_8)
        val s = StringBuilder()

        for (line in lines.split('\n')) {
            s.append(line)
        }

        return s.toString()
    }

}


