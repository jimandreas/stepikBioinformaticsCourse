@file:Suppress("MemberVisibilityCanBePrivate", "UnnecessaryVariable", "unused", "RemoveEmptyClassBody",
    "UNUSED_PARAMETER"
)

package util

/**
 *
Code Challenge: Implement RandomizedMotifSearch.

Input: Integers k and t, followed by a space-separated collection of strings Dna.
Output: A collection BestMotifs resulting from running
RandomizedMotifSearch(Dna, k, t) 1,000 times. Remember to use pseudocounts!

 * See also:
 * stepik: @link: https://stepik.org/lesson/240243/step/5?unit=212589
 * rosalind: @link: http://rosalind.info/problems/ba2f/
 *
 * youtube:
 * @link: https://www.youtube.com/watch?v=sUXe2G2I9IA
 * @link: https://www.youtube.com/watch?v=MP6O_Z2AUDU
 */

/**
 *  PseudoCode
 */
val psuedoCodeRanomizedMotifSearch= """
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

    fun doSearch(dnaStrings: List<String>, kLen: Int): List<String> {
        return emptyList()
    }
}
