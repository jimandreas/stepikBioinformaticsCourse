@file:Suppress(
    "ControlFlowWithEmptyBody", "UNUSED_VARIABLE", "unused", "ReplaceManualRangeWithIndicesCalls",
    "LiftReturnOrAssignment", "UnnecessaryVariable", "MemberVisibilityCanBePrivate"
)

package algorithms

import java.lang.Integer.max

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


val aminoUniqueMassesList = listOf(
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

var aminoUniqueMasses = aminoUniqueMassesList // can be overridden by a test for a custom list

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
    //println("Prefix masses are $prefixMass")

    val massSpectrum: MutableList<Int> = mutableListOf(0)
    val cyclicEndValue = prefixMass[peptide.length]

    val loopEndOne = prefixMass.size - 1
    val loopEndTwo = prefixMass.size

    for (i in 0 until loopEndOne) {
        for (j in (i + 1) until loopEndTwo) {

            massSpectrum.add(prefixMass[j] - prefixMass[i])

            // add the masses that wrap around the end of cyclic Peptides
            //    This uses a subtractive "hack" from the cyclicEndValue - that is the
            //    summation of all masses in the peptide.

            if (isCyclicPeptide && i > 0 && j < loopEndTwo - 1) {
                val extra = cyclicEndValue - (prefixMass[j] - prefixMass[i])
                //println("ADDING: $extra")
                massSpectrum.add(cyclicEndValue - (prefixMass[j] - prefixMass[i]))
            }
        }
    }
    //println("num ${massSpectrum.size} $massSpectrum")
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
    if (massList.isEmpty()) {
        return listOf(0)
    }
    for (m in massList) {
        previousMass += m
        prefixMass.add(previousMass)
    }

    val massSpectrum: MutableList<Int> = mutableListOf(0)
    val totalMassValue = prefixMass[prefixMass.size - 1]

    val loopEndOne = prefixMass.size - 1
    val loopEndTwo = prefixMass.size

    for (i in 0 until loopEndOne) {
        for (j in (i + 1) until loopEndTwo) {

            massSpectrum.add(prefixMass[j] - prefixMass[i])

            // add the masses that wrap around the end of cyclic Peptides
            //    This uses a subtractive "hack" from the cyclicEndValue - that is the
            //    summation of all masses in the peptide.

            if (isCyclicPeptide && i > 0 && j < loopEndTwo - 1) {
                val extra = totalMassValue - (prefixMass[j] - prefixMass[i])
                //println("ADDING: $extra")
                massSpectrum.add(totalMassValue - (prefixMass[j] - prefixMass[i]))
            }
        }
    }
    //println("num ${massSpectrum.size} $massSpectrum")
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
    val massMatchesList = spectrum.filter { aminoUniqueMasses.contains(it) }

    // the maxMass is the mass of all peptides together - our target for the peptide
    val maxMass = spectrum.maxOrNull() ?: 0

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
        // have to use an iterator or the JVM gets upset
        val iter = candidatePeptides.iterator()
        while (iter.hasNext()) {
            val w = iter.next()

            // if this candidate amino combo adds up to the target peptide mass, then take a closer look
            if (w.sum() == maxMass) {
                val result = peptideMassSpectrumFromMassList(w, isCyclicPeptide = true)
                if (spectrum == result) {
                    finalPeptides.add(w)
                }
                iter.remove()
            } else {
                // otherwise check the aminos in the candidate to make sure
                // that the composition matches given spectrum, if that fails,
                // remove the candidate
                val result = peptideMassSpectrumFromMassList(w, isCyclicPeptide = false)
                if (!spectrum.containsAll(result)) {
                    iter.remove()
                }
            }
        }
    } while (candidatePeptides.isNotEmpty())
    return finalPeptides

}

/**
 *
 * Cyclopeptide Scoring Problem: Compute the score of a cyclic peptide against a spectrum.

Input: An amino acid string Peptide and a collection of integers Spectrum.
Output: The score of Peptide against Spectrum, Score(Peptide, Spectrum).

The score is simply the count of the common elements in both the
testSpectrum and the peptide being scored.
 */


fun cyclopeptideScore(peptide: String, testSpectrum: List<Int>, isCyclicPeptide: Boolean = true): Int {
    val peptideMassSpectrum = peptideMassSpectrum(peptide, isCyclicPeptide)

    val t = testSpectrum.toMutableList()

    var count = 0
    for (p in peptideMassSpectrum) {
        if (t.contains(p)) {
            count++
            t.remove(p)
        }
    }
    return count
}

fun cyclopeptideScoreFromMasses(peptide: List<Int>, testSpectrum: List<Int>, isCyclicPeptide: Boolean = true): Int {
    val peptideMassSpectrum = peptideMassSpectrumFromMassList(peptide, isCyclicPeptide)

    val t = testSpectrum.toMutableList()

    var count = 0
    for (p in peptideMassSpectrum) {
        if (t.contains(p)) {
            count++
            t.remove(p)
        }
    }
    return count
}

/**
 * Trim
 * trimLeaderboard()
 * @link http://rosalind.info/problems/ba4l/
 *
 * Trim a leaderboard of peptides.

Given: A leaderboard of linear peptides Leaderboard, a linear spectrum Spectrum, and an integer N.

Return: The top N peptides from Leaderboard scored against Spectrum. Remember to use LinearScore.
Note that ties at a givin scoring level are all included.
 */
fun trimLeaderboard(leaderPeptides: List<String>, givenSpectrum: List<Int>, trimLevel: Int): List<String> {

    val leaderMap: MutableList<Pair<Int, String>> = mutableListOf()

    for (p in leaderPeptides) {
        leaderMap.add(Pair(cyclopeptideScore(p, givenSpectrum, false), p))
    }

    val levels = leaderMap.map { it.first }.sortedDescending().distinct()
    val cutoff = levels[trimLevel - 1]

    val outputPeptides = leaderMap.filter { it.first >= cutoff }
    val outputPeptidesSorted = outputPeptides.sortedByDescending { it.first }
    val outputStrings = outputPeptidesSorted.map { it.second }
    val leaders = outputStrings

    return leaders
}

fun trimLeaderboardMasses(
    leaderPeptides: List<List<Int>>,
    givenSpectrum: List<Int>,
    trimLevel: Int
): MutableList<List<Int>> {

    if (leaderPeptides.size < trimLevel) {
        return leaderPeptides.toMutableList()
    }
    val leaderMap: MutableList<Pair<Int, List<Int>>> = mutableListOf()

    for (p in leaderPeptides) {
        leaderMap.add(Pair(cyclopeptideScoreFromMasses(p, givenSpectrum, false), p))
    }

    val levels = leaderMap.map { it.first }.sortedDescending()
    val index = if (trimLevel < levels.size - 1) {
        trimLevel - 1
    } else {
        levels.size - 1
    }
    val cutoff = levels[index]



    val outputPeptides = leaderMap.filter { it.first >= cutoff }
    val outputPeptidesSorted = outputPeptides.sortedByDescending { it.first }
    val outputMasses = outputPeptidesSorted.map { it.second }

    //println("Cutoff = $cutoff levels size ${levels.size} $trimLevel size after trim ${outputMasses.size}")

    return outputMasses.toMutableList()
}


/**
 * LeaderboardCyclopeptideSequencing
 * @link http://rosalind.info/problems/ba4g/
 *
 * NOTE: in stepik the leaderboard pruning function is called "Trim", in rosalind "Cut"
 * Here the usage is trimLeaderboard()
 *
Given: An integer N and a collection of integers Spectrum.

Return: LeaderPeptide after running LeaderboardCyclopeptideSequencing(Spectrum, N).
 */

class LeaderboardCyclopeptideSequencing {

    var matchingStrings = StringBuilder()
    val matchingLists: MutableList<List<Int>> = mutableListOf()
    var countOfEightySevens = 0
    var countOfEightyTwos = 0
    var maxScore = 0

    fun leaderboardCyclopeptideSequencing(trimLevel: Int, spectrum: List<Int>): List<Int> {

        // the parentMass is the mass of all peptides together - our target for the peptide
        val parentMass = spectrum.maxOrNull() ?: 0

        var peptideCandidateLeaderboard: MutableList<List<Int>> = arrayListOf(listOf())
        var peptideLeadingCandidate = listOf(0)

        do {
            // expand the leaderboard by all amino acids
            // - this is exponential(!) but it is immediately trimmed
            val expandedPeptides: MutableList<List<Int>> = ArrayList()
            for (p in peptideCandidateLeaderboard) {
                for (m in aminoUniqueMasses) {
                    val newPeptide: MutableList<Int> = ArrayList(p)
                    newPeptide.add(m)
                    expandedPeptides.add(newPeptide)
                }
            }
            peptideCandidateLeaderboard = expandedPeptides

            // now evaluate the candidates
            // have to use an iterator or the JVM gets upset
            val iter = peptideCandidateLeaderboard.iterator()
            while (iter.hasNext()) {
                val p = iter.next()

                // if this candidate amino combo adds up to the target peptide mass, then take a closer look
                if (p.sum() == parentMass) {

                    val thisPeptideScore = cyclopeptideScoreFromMasses(p, spectrum, true)

                    val leaderScore = cyclopeptideScoreFromMasses(peptideLeadingCandidate, spectrum, true)

                    if (thisPeptideScore == 87) {
                        //println(thisPeptideScore)
                        countOfEightySevens++
                        matchingLists.add(p)
                        matchingStrings.append(p.joinToString("-"))
                        matchingStrings.append(" ")
                    }

                    if (thisPeptideScore == 82) {
                        //println(thisPeptideScore)
                        countOfEightyTwos++
                        matchingLists.add(p)
                        matchingStrings.append(p.joinToString("-"))
                        matchingStrings.append(" ")
                    }

                    if (thisPeptideScore > leaderScore) {
                        peptideLeadingCandidate = p
                        maxScore = max(maxScore, thisPeptideScore)
                    }
                } else {
                    // otherwise if the peptide is too big, then remove it
                    if (p.sum() > parentMass) {
                        iter.remove()
                    }
                }
            }
            peptideCandidateLeaderboard = trimLeaderboardMasses(peptideCandidateLeaderboard, spectrum, trimLevel)
        } while (peptideCandidateLeaderboard.isNotEmpty())

        return peptideLeadingCandidate
    }
}
