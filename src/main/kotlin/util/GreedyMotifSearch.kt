@file:Suppress("UnnecessaryVariable", "unused")

package util

/**
 *  reference:
 *  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-2
 */

/**
 *  @link: https://stepik.org/lesson/240241/step/4?unit=212587
 *  for each kmer in the first dna in the list,
 *  scan all other kmers in the following list entries and score them
 */
fun greedyMotifSearch(dnaList: List<String>, kmerLength: Int, applyLaplace: Boolean = false): List<String> {

    // initial conditions - just take the first kmer of each dna in the dnaList
    var bestMotifs: MutableList<String> = mutableListOf()
    for (d in dnaList) {
        bestMotifs.add(d.substring(0, kmerLength))
    }

    /*
     * now loop through each kmer substring in the first dnaList string.
     *   make a profile for it and then use the profile to get the most
     * probable match in each of the following strings in the dnaList.
     */
    for (i in 0..dnaList[0].length - kmerLength) {
        val motifs: MutableList<String> = mutableListOf()
        val currentMotif = dnaList[0].substring(i, i + kmerLength)
        motifs.add(currentMotif)
        var probabilityList = createProfile(motifs, applyLaplace)
        for (j in 1 until dnaList.size) {
            val bestMotifInThisString = mostProbableKmerGivenProbList(dnaList[j], kmerLength, probabilityList.toList())
            //println("best is $bestMotifInThisString for string ${dnaList[j]}")
            motifs.add(bestMotifInThisString)
            probabilityList = createProfile(motifs, applyLaplace)
        }

        //println("new best: $motifs score ${scoreTheMotifs(motifs)} vs ${scoreTheMotifs(bestMotifs)}")
        if (scoreTheMotifs(motifs) < scoreTheMotifs(bestMotifs)) {

            bestMotifs = motifs
        }
    }
    return bestMotifs
}

/**
 * for a list of candidate motif strings,
 * accumulate a 4 row matrix where each column contains the count
 * of the ACGT occurrences in the strings.   This count is then
 * normalized by the number of motifs in the list.
 *
 * Added: pseudoCounts -
 * @link: https://en.wikipedia.org/wiki/Additive_smoothing#Pseudocount
 * "In order to improve this unfair scoring, bioinformaticians often substitute zeroes with small numbers called pseudocounts."
 */
fun createProfile(motifsList: List<String>, applyLaplace: Boolean = false): FloatArray {
    val kmerLength = motifsList[0].length

    val profile = FloatArray(4 * kmerLength)
    if (applyLaplace) {
        for (i in profile.indices) {
            profile[i] = 1.0f  // all entries have a base probability
        }
    }

    for (motif in motifsList) {
        for (i in motif.indices) {
            val nucleotide = motif[i].fromNucleotide()
            profile[nucleotide * kmerLength + i]++
        }
    }
    var divisor = motifsList.size.toFloat()
    if (applyLaplace) {
        divisor += 4.0f
    }
    for (i in profile.indices) {
        profile[i] = profile[i] / divisor
    }
    return profile
}

fun Char.fromNucleotide(): Int {
    return when (this) {
        'A' -> 0
        'C' -> 1
        'G' -> 2
        'T' -> 3
        else -> 0
    }
}

fun Int.fromIdentifier(): Char {
    return when (this) {
        0 -> 'A'
        1 -> 'C'
        2 -> 'G'
        3 -> 'T'
        else -> ' '
    }
}


/**
 * the [scoreTheMotifs] of a set of motif candidates is the sum
 * of the mismatches across all letters.
 * The mismatches are determined by what is left over after subtracting
 * the dominant fraction in the profile.
 * @param motifs - the list of candidate motif strings
 */
fun scoreTheMotifs(motifs: List<String>): Int {
    val profileMatrix = createProfile(motifs)
    val mlen = motifs[0].length
    var score = 0
    for (i in 0 until motifs[0].length) {
        val maxVal = maxValueForColumn(profileMatrix, mlen, i)
        score += (motifs.size.toFloat() * (1.0f - maxVal)+0.00001).toInt()
    }
    return score
}

/**
 * return the max value in the profile matrix for [column]
 * @param len - the length of a rwo in the profile matrix
 */
private fun maxValueForColumn(profileMatrix: FloatArray, len: Int, column: Int): Float {
    val anuc = profileMatrix[len * 0 + column]
    val cnuc = profileMatrix[len * 1 + column]
    val gnuc = profileMatrix[len * 2 + column]
    val tnuc = profileMatrix[len * 3 + column]
    val maxVal = maxOf(anuc, maxOf(cnuc, maxOf(gnuc, tnuc)))
    return maxVal
}



