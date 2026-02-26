@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "unused", "RemoveEmptyClassBody",
    "UNUSED_PARAMETER"
)

package algorithms

/**
 *
Code Challenge: Implement RandomizedMotifSearch.

Input: Integers k and t, followed by a space-separated collection of strings Dna.
Output: A collection BestMotifs resulting from running
RandomizedMotifSearch(Dna, k, t) 1,000 times. Remember to use pseudocounts!

 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba2f/
 *
 * youtube:
 * @link: https://www.youtube.com/watch?v=sUXe2G2I9IA
 * @link: https://www.youtube.com/watch?v=MP6O_Z2AUDU
 */

/**
 *  PseudoCode
 */
val pseudoCodeRanomizedMotifSearch = """
RANDOMIZEDMOTIFSEARCH(Dna, k, t)
  randomly select k-mers Motifs = (Motif1, . . . , Motift) in each string from Dna
  BestMotifs Motifs
  while forever
    Profile PROFILE(Motifs)
    Motifs MOTIFS(Profile, Dna)
    if SCORE(Motifs) < SCORE(BestMotifs)
       BestMotifs = Motifs
    else
       return BestMotifs
""".trimIndent()

class RandomizedMotifSearch {

    fun doRandomSearchOnePass(dnaStrings: List<String>, kLen: Int): List<String> {

        var bestMotifs = dnaStrings.map {
            val start = (0 until it.length - kLen).random()
            it.substring(start, start + kLen)
        }.toList()

        while (true) {
            val score2 = createProfile(bestMotifs, applyLaplace = true).toList()
            val newMotifs: MutableList<String> = mutableListOf()
            for (s in dnaStrings) {
                newMotifs.add(mostProbableKmerGivenProbList(s, kLen, score2))
            }
            if (scoreTheMotifs(newMotifs) < scoreTheMotifs(bestMotifs)) {
                bestMotifs = newMotifs
            } else {
                return bestMotifs
            }

        }
    }

    fun doRandomSearchMultipleRuns(dnaStrings: List<String>, kLen: Int, iterations: Int): List<String> {
        var bestMotifs = doRandomSearchOnePass(dnaStrings, kLen)
        var bestScore = scoreTheMotifs(bestMotifs)
        for (i in 0..iterations) {
            val newMotifs = doRandomSearchOnePass(dnaStrings, kLen)
            val newScore = scoreTheMotifs(newMotifs)
            if (newScore < bestScore) {
                bestScore = newScore
                bestMotifs = newMotifs
            }
        }
        return bestMotifs
    }
}
