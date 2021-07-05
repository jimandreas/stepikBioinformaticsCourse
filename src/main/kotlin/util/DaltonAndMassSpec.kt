@file:Suppress("ControlFlowWithEmptyBody", "UNUSED_VARIABLE", "unused", "ReplaceManualRangeWithIndicesCalls")

package util

val aminoAcidToDaltonHashMap: HashMap<Char, Int> = hashMapOf(
    'G' to 57,
    'A' to 71,
    'S' to 87,
    'P' to 97,
    'V' to 99,
    'T' to 101,
    'C' to 103,
    'I' to 113,
    'L' to 113,
    'N' to 114,
    'D' to 115,
    'K' to 128,
    'Q' to 128,
    'E' to 129,
    'M' to 131,
    'H' to 137,
    'F' to 147,
    'R' to 156,
    'Y' to 163,
    'W' to 186
)

val aminoUniqueMasses = listOf(
    57,
    71,
    87,
    97,
    99,
    101,
    103,
    113,
    114,
    115,
    128,
    129,
    131,
    137,
    147,
    156,
    163,
    186
)




/**
 *
Generating Theoretical Spectrum Problem: Generate the theoretical spectrum of a cyclic peptide.

Input: An amino acid string Peptide.
Output: Cyclospectrum(Peptide).

The theoretical spectrum of a cyclic peptide Peptide, denoted Cyclospectrum(Peptide),
is the collection of all of the masses of its subpeptides,
in addition to the mass 0 and the mass of the entire peptide,
with masses ordered from smallest to largest.

 */
fun peptideMassSpectrum(peptide: String, isCyclicPeptide: Boolean = false): List<Int> {
    val prefixMass: MutableList<Int> = mutableListOf()

    when (peptide.length) {
        0 -> return listOf(0)
        1 -> {
            val retVal = aminoAcidToDaltonHashMap[peptide[0]]
            return listOf(retVal ?: 0)
        }
    }
    prefixMass.add(0) // initial mass list is seeded with zero

    // the mass of each individual peptide
    // plus its SUCCESSOR is added to the base mass array
    for (i in 0 until peptide.length) {
        val mass = aminoAcidToDaltonHashMap[peptide[i]]
        if (mass != null) {
            prefixMass.add(prefixMass[i] + mass)
        } else {
            println("ERROR THE PEPTIDE GIVEN (${peptide[i]}) is not in the mass table!!")
        }
    }
    println("Prefix masses are $prefixMass")

    val massSpectrum: MutableList<Int> = mutableListOf(0)
    val cyclicEndValue = prefixMass[peptide.length]

    val loopEndOne = prefixMass.size-1
    val loopEndTwo = prefixMass.size

    for (i in 0 until loopEndOne) {
        for (j in (i + 1) until loopEndTwo) {

            massSpectrum.add(prefixMass[j] - prefixMass[i])

            // add the masses that wrap around the end of cyclic Peptides
            //    This uses a subtractive "hack" from the cyclicEndValue - that is the
            //    summation of all masses in the peptide.

            if (isCyclicPeptide && i > 0 && j < loopEndTwo-1) {
                val extra = cyclicEndValue - (prefixMass[j] - prefixMass[i])
                println("ADDING: $extra")
                massSpectrum.add(cyclicEndValue - (prefixMass[j] - prefixMass[i]))
            }
        }
    }
    println("num ${massSpectrum.size} $massSpectrum")
    return massSpectrum.sorted()
}

/**
 * special case of computing a spectrum given a list of masses.
 * These are presumably masses of individual amino acids but for the
 * purpose of this algo - they are just numbers.   Treatment is similar to
 * the function preceeding.
 * @param massList  integer masses
 * @param isCyclicPeptide - wrap the ends or not
 * @return a mass list of all posssible combintations
 */

fun peptideMassSpectrumFromMassList(massList: List<Int>, isCyclicPeptide: Boolean = false): List<Int> {
    val prefixMass: MutableList<Int> = mutableListOf(0)
    var previousMass = 0
    for (m in massList) {
        previousMass += m
        prefixMass.add(previousMass)
    }

    val massSpectrum: MutableList<Int> = mutableListOf(0)
    val totalMassValue = prefixMass[prefixMass.size-1]

    val loopEndOne = prefixMass.size-1
    val loopEndTwo = prefixMass.size

    for (i in 0 until loopEndOne) {
        for (j in (i + 1) until loopEndTwo) {

            massSpectrum.add(prefixMass[j] - prefixMass[i])

            // add the masses that wrap around the end of cyclic Peptides
            //    This uses a subtractive "hack" from the cyclicEndValue - that is the
            //    summation of all masses in the peptide.

            if (isCyclicPeptide && i > 0 && j < loopEndTwo-1) {
                val extra = totalMassValue - (prefixMass[j] - prefixMass[i])
                println("ADDING: $extra")
                massSpectrum.add(totalMassValue - (prefixMass[j] - prefixMass[i]))
            }
        }
    }
    println("num ${massSpectrum.size} $massSpectrum")
    return massSpectrum.sorted()
}


/**
 * Code Challenge: Implement CyclopeptideSequencing
 * @link https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-4
 * Section 4.6
 *
 * Given an experimental spectrum Spectrum, we will form a
 * collection Peptides of candidate linear peptides
 * initially consisting of the empty peptide,
 * which is just an empty string (denoted "")
 * having mass 0. At the next step, we will expand
 * Peptides to contain all linear peptides of length 1.
 * We continue this process, creating 18 (aminoUniqueMasses.size) new peptides of
 * length k + 1 for each amino acid string Peptide of length k
 * in Peptides by appending every possible amino acid mass to the end of Peptide.
 *
 * To prevent the number of candidate peptides from increasing exponentially,
 * every time we expand Peptides, we trim it by keeping only those linear
 * peptides that remain consistent with the experimental spectrum.
 * We then check if any of these new linear peptides has mass equal to Mass(Spectrum).
 * If so, we circularize this peptide and check whether it provides a solution
 * to the Cyclopeptide Sequencing Problem.
 *
 * @param spectrum a list of integers giving the mass spectrum measurment of an unknown peptide
 * @return a list of lists of candidate peptide constituents
 */

fun cyclopeptideSequencing(spectrum: List<Int>): List<List<Int>> {

    // scan the spectrum mass list of values for matches to amino masses
    val massMatchesList = spectrum.filter { aminoUniqueMasses.contains( it )}

    // the maxMass is the mass of all peptides together - our target for the peptide
    val maxMass = spectrum.maxOrNull()?: 0

    // two lists to work on.   One is the proposed list, and the other is the winners list
    var candidatePeptides: MutableList<List<Int>> = arrayListOf(listOf())
    val finalPeptides: ArrayList<List<Int>> = arrayListOf()

    // loop until all proposed lists fail and are removed
    do {

        // expand the list - this is exponential(!) but it is immediately trimmed
        val expandedPeptides: MutableList<List<Int>> = ArrayList()
        for (p in candidatePeptides) {
            for (m in massMatchesList) {
                val newPeptide: MutableList<Int> = ArrayList(p)
                newPeptide.add(m)
                expandedPeptides.add(newPeptide)
            }
        }
        candidatePeptides = expandedPeptides

        // now evaluate the candidates
        for (w in candidatePeptides) {

            // if this candidate amino combo adds up to the target peptide mass, then take a closer look
            if (w.sum() == maxMass) {
                val result = peptideMassSpectrumFromMassList(w, isCyclicPeptide = true)
                if (spectrum == result) {
                    finalPeptides.add(w)
                }
                candidatePeptides.remove(w)
            } else {
                // otherwise check the aminos in the candidate to make sure
                // that the composition matches given spectrum, if that fails,
                // remove the candidate
                val result = peptideMassSpectrumFromMassList(w, isCyclicPeptide = false)
                if (!spectrum.containsAll(result)) {
                    candidatePeptides.remove(w)
                }
            }
        }
    } while (candidatePeptides.isNotEmpty())
    return finalPeptides

}