@file:Suppress("LiftReturnOrAssignment", "UnnecessaryVariable", "UNUSED_CHANGED_VALUE")

package util

/**
 *

Peptide Encoding Problem: Find substrings of a genome encoding a given amino acid sequence.

Input: A DNA string Text, an amino acid string Peptide, and the array GeneticCode.
Output: All substrings of Text encoding Peptide (if any such substrings exist).

 */
fun scanDnaStringForPeptideEncoding(
    dnaString: String,
    peptideString: String,
    answerIsReverseComplement: Boolean = false
): List<String> {
    if (peptideString == "") {
        return listOf("")
    }
    val firstAminoAcid = peptideString[0].toString()
    if (!aminoAcidToDnaCodonList.containsKey(firstAminoAcid)) {
        println("ERROR in peptideString $firstAminoAcid not found in table")
        return listOf("")
    }

    val matchingDnaStrings: MutableList<String> = mutableListOf()
    val len = dnaString.length

    for (i in 0..len - 3) {
        var matchCount = 0
        val codon = dnaString.substring(i, i + 3)

        if (aminoAcidToDnaCodonList[firstAminoAcid]!!.contains(codon)) {

            /*
             * now try to match the reset of the peptide string against this position
             * in the dnaString codons.
             */
            var j = i

            while (j <= dnaString.length - 3) {
                matchCount++
                j += 3
                /*
                 * if we hit the end of the peptide string, we are finished and there
                 * is a match.
                 */
                if (matchCount == peptideString.length) {
                    val match = dnaString.substring(i, i + peptideString.length * 3)
                    matchingDnaStrings.add(match)
                    //println(match)
                    break
                }
                val nextCodon = dnaString.substring(j, j + 3)
                val nextAminoAcid = peptideString[matchCount]
                if (!aminoAcidToDnaCodonList.containsKey(nextAminoAcid.toString())) {
                    println("ERROR in peptideString $nextAminoAcid not found in table")
                    break
                }
                /*
                 * keep scanning as long as there are matches
                 */
                if (!aminoAcidToDnaCodonList[nextAminoAcid.toString()]!!.contains(nextCodon)) {
                    break
                }
            }
        }
    }

    if (answerIsReverseComplement) {
        return matchingDnaStrings.map { reverseComplement(it)}
    }
    return matchingDnaStrings
}