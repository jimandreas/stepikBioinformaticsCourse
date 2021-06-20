@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

import util.frequentWordsWithMismatches

/**
 * @link: https://stepik.org/lesson/240221/step/9?unit=212567
 *
1.8 Some Hidden Messages are More Elusive than Others
Code Challenge: Solve the Frequent Words with Mismatches Problem.

Input: A string Text as well as integers k and d. (You may assume k ≤ 12 and d ≤ 3.)
Output: All most frequent k-mers with up to d mismatches in Text.

Extra Dataset

Debug Datasets

Sample Input:
ACGTTGCATGTCGCATGATGCATGAGAGCT
4 1

Sample Output:
GATG ATGC ATGT
 */

fun main() {
    val genome2 = "ACGTTGCATGTCGCATGATGCATGAGAGCT"
    val kmerSize2 = 4
    val mismatches2 = 1
    // answer was: ATGC ATGT GATG - correct!

    val examGenome = "TCCACAATCAATACGCTCATCCACAATTCAACGCTTTGCAATCAATTCATCACAATACGCTTTGTCACAATTTTGTTTGTCATTTGTTTGACGCCAATTTTGTCATCAACGCTTTGTTTGTCCATCCACAATCAATCAATTCCAACGCTCCATTTGTTTGCAATTTTGACGCTTTGTCAACGCTTTGTCAACGCTCCACAATTCAACGCTTTGTCCATTTGCAATTCATCCACAATTCCAACGCTTTGTCCAACGCCAATTCATTTGTCCATCCACAATCAATACGCTCCATTTGCAATTTTGCAATACGCTCCAACGCTCATTTGACGCACGCTCATCATTTGTCCATCATCATCACAATCAATTCCATCCATCATTTGACGCCAATCAATCAAT"
    val examkmerSize = 6
    val examMismatches = 2  // answer was TCAATA - correct!

    val g = examGenome
    val k = examkmerSize
    val m = examMismatches

    val matchList = frequentWordsWithMismatches(g, k, m).sorted()
    println("quantity = ${matchList.size}")  // step 5
    for (i in matchList) {
        print("$i ")
    }
}

