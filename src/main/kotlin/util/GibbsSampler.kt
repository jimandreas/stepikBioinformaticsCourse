@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "unused", "RemoveEmptyClassBody")

package util

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
  randomly select k-mers Motifs = (Motif1, ..., Motift) in each string from Dna
  BestMotifs Motifs
  for j 1 to N
    i = RANDOM(t)
   Profile = profile matrix formed from all strings in Motifs except for Motifi
   Motifi = Profile-randomly generated k-mer in the i-th sequence
   if SCORE(Motifs) < SCORE(BestMotifs)
      BestMotifs = Motifs
  return BestMotifs
""".trimIndent()

class GibbsSampler {

}
