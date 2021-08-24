@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "unused", "RemoveEmptyClassBody",
    "ReplaceManualRangeWithIndicesCalls"
)

package util

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.sum
import kotlin.random.Random.Default.nextDouble

/**
 *

Code Challenge: Implement GibbsSampler.

Input: Integers k, t, and N, followed by a space-separated collection of strings Dna.
Output: The strings BestMotifs resulting from running GibbsSampler(Dna, k, t, N)
with 20 random starts. Remember to use pseudocounts!

Note: The next lesson features a detailed example of GibbsSampler,
so you may wish to return to this problem later.


 * See also:
 * stepik: @link: https://stepik.org/lesson/240245/step/4?unit=212591
 * rosalind: @link: http://rosalind.info/problems/ba2g/
 */

/**
 *  PseudoCode
 */
val psuedoCodeGibbsSampler = """
GIBBSSAMPLER(Dna, k, t, N) 

  Dna - list of strings
  k - kMer length
  t - size of Dna list (how many entries in the list)
  N - iterations
  
  randomly select k-mers Motifs = (Motif1, ..., Motift) in each string from Dna
  BestMotifs Motifs
  for j = 1 to N
    i = RANDOM(t)
    Profile = profile matrix formed from all strings in Motifs except for Motif(i)
    Motif(i) = Profile-randomly generated k-mer in the i-th sequence
    if SCORE(Motifs) < SCORE(BestMotifs)
      BestMotifs = Motifs
  return BestMotifs
""".trimIndent()

class GibbsSampler {

    var bestScore = 0
    fun doGibbsSampler(dnaStrings: List<String>, kLen: Int, iterations: Int): List<String> {

        var bestMotifs = dnaStrings.map {
            val start = (0 until it.length - kLen).random()
            it.substring(start, start + kLen)
        }.toList()

        for (i in 0..iterations) {
            // tempList has one random dnaString removed
            val removedIndex = (0 until dnaStrings.size).random()
            val tempMotifs = bestMotifs.toMutableList()
            tempMotifs.removeAt(removedIndex)

            val score2 = createProfile(tempMotifs, applyLaplace = true).toList()

            // now insert the gibbs sampled motif back into the test motifs list
            val gibbsMotif = gibbsSamplerSelection(score2, dnaStrings[removedIndex], kLen)
            tempMotifs.add(removedIndex, gibbsMotif)

            val tempScore = scoreTheMotifs(tempMotifs)
            if (tempScore < scoreTheMotifs(bestMotifs)) {
                bestMotifs = tempMotifs
                bestScore = tempScore
            }
        }
        return bestMotifs
    }

    fun gibbsSamplerSelection(profile: List<Float>, dnaString: String, kLen: Int): String {
        val candidates = dnaString.length - kLen
        val probabilityArray = mk.d1array(candidates){0.0f}
        val strLen = dnaString.length
        for (i in 0 until dnaString.length - kLen) {
            probabilityArray[i] = probForGivenKmer(dnaString.substring(i, i+kLen), profile)
        }

        val nextRandomDouble = nextDouble() // double between 0.0 and 1.0
        val totalProbability = probabilityArray.sum()
        val targetProbability = totalProbability * nextRandomDouble
        var currentSum = 0.0
        for (i in 0 until dnaString.length - kLen) {
            currentSum += probabilityArray[i]
            if (currentSum > targetProbability) {
                return dnaString.substring(i, i+kLen)
            }
        }
        return dnaString.substring(dnaString.length-kLen, dnaString.length)
    }


}
