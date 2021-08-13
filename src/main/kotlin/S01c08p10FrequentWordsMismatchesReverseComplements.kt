@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

import util.frequentWordsWithMismatches

/**
 * @link: https://stepik.org/lesson/240221/step/10?unit=212567

1.8 Some Hidden Messages are More Elusive than Others

We now redefine the Frequent Words Problem to account for both mismatches and reverse complements.
Recall that Patternrc refers to the reverse complement of Pattern.

Frequent Words with Mismatches and Reverse Complements Problem:
Find the most frequent k-mers (with mismatches and reverse complements) in a string.

Input: A DNA string Text as well as integers k and d.
Output: All k-mers Pattern maximizing the sum Countd(Text, Pattern)+ Countd(Text, Patternrc) over all possible k-mers.

Code Challenge: Solve the Frequent Words with Mismatches and Reverse Complements Problem.

Sample Input:

ACGTTGCATGTCGCATGATGCATGAGAGCT
4 1

Sample Output:

ATGT ACAT

See also:
stepik: https://stepik.org/lesson/240221/step/10?unit=212567
rosalind: http://rosalind.info/problems/ba1j/
 */

fun main() {
    val genome2 = "ACGTTGCATGTCGCATGATGCATGAGAGCT"
    val kmerSize2 = 4
    val mismatches2 = 1   // right answer: ATGT ACAT

    val examGenome = "AGCGACCCACAGCACCTCAGGGCAGCGACCCACAGCACCTCAGGGCACAGCACACAGCACACAGCACACAGCACAGCGACCCCTCAGGGCCTCAGGGCAGCGACCCCTCAGGGCAGCGTACAGTATTCCAACAGCACCTCAGGGCACAGCACCTCAGGGCAGCGTACAACAGCACACAGCACCTCAGGGCACAGCACGTATTCCAAGCGACCCAGCGACCCAGCGTACAAGCGACCCGTATTCCAACAGCACAGCGACCCAGCGTACAAGCGTACAGTATTCCAGTATTCCAACAGCACACAGCACGTATTCCACTCAGGGCACAGCACAGCGTACACTCAGGGCCTCAGGGCACAGCACACAGCACAGCGTACAAGCGACCCAGCGTACAACAGCACAGCGACCCGTATTCCAGTATTCCAAGCGTACAGTATTCCAACAGCACCTCAGGGCCTCAGGGCACAGCACAGCGACCCACAGCACGTATTCCACTCAGGGCAGCGACCCAGCGACCCGTATTCCAAGCGACCCGTATTCCACTCAGGGCAGCGTACACTCAGGGCAGCGACCCACAGCACCTCAGGGCAGCGACCCAGCGTACAACAGCACGTATTCCAAGCGTACAAGCGACCCAGCGTACAAGCGTACAACAGCACGTATTCCAACAGCACAGCGTACACTCAGGGCCTCAGGGCCTCAGGGCCTCAGGGCGTATTCCACTCAGGGCCTCAGGGCACAGCACACAGCACGTATTCCAAGCGTACAGTATTCCACTCAGGGCACAGCACAGCGACCCACAGCACGTATTCCA"
    val examkmerSize = 5
    val examMismatches = 3

    val g = examGenome
    val k = examkmerSize
    val m = examMismatches

    val matchList = frequentWordsWithMismatches(g, k, m, scanReverseComplements = true).sorted()

    println("quantity = ${matchList.size}")  // step 5
    println(matchList.joinToString(" "))
}

