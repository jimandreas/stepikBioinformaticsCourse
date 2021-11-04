@file:Suppress("unused", "UnnecessaryVariable")

package algorithms

/**
 * See also:
 * http://rosalind.info/problems/splc/
 * RNA Splicing
 *
 * Problem

After identifying the exons and introns of an RNA string,
we only need to delete the introns and concatenate the
exons to form a new string ready for translation.

Given: A DNA string s (of length at most 1 kbp) and a collection of substrings of s acting as introns.
All strings are given in FASTA format.

Return: A protein string resulting from transcribing and translating the exons of s
(Note: Only one solution will exist for the dataset provided.)
 */

class RnaSplicing {

    fun rnaSplice(dnaString: String, introns: List<String>): String {
        var workString = dnaString

        // quality / process check are all introns present?

        for (s in introns) {
            if (!workString.contains(s)) {
                println("Intron $s is not contain in dna String!!")
            }
        }

        for (s in introns) {
            val index = workString.indexOf(s)
            workString = workString.removeRange(index, index+s.length )
        }

        val peptide = translateDnaCodonStringToAminoAcidString(workString)
        return peptide
    }

}