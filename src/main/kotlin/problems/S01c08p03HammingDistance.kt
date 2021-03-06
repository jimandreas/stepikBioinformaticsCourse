@file:Suppress("UNUSED_VARIABLE", "LocalVariableName")

package problems

import algorithms.hammingDistance


/**
1.8 Some Hidden Messages are More Elusive than Others

We say that position i in k-mers p1 … pk and q1 … qk is a mismatch if pi ≠ qi.
For example, CGAAT and CGGAC have two mismatches.
The number of mismatches between strings p and q is called the Hamming distance
between these strings and is denoted HammingDistance(p, q).

Hamming Distance Problem: Compute the Hamming distance between two strings.

Input: Two strings of equal length.
Output: The Hamming distance between these strings.

Code Challenge: Solve the Hamming Distance Problem.

Sample Input:

GGGCCGTTGGT
GGACCGTTGAC

Sample Output:  3

See also:
stepik: https://stepik.org/lesson/240221/step/3?unit=212567
rosalind: http://rosalind.info/problems/ba1g/
 */


fun main() {

    val testString1 = "GGGCCGTTGGT"
    val testString2 = "GGACCGTTGAC"

    val t2_1 = "CCGAAGCAATTGAAACCCCCCCGGCCTGGGAGGCGCAAAAATCTGACCTCTTTGTGAGTTGACCACTTAATTTATGTCTGACCACGAGAAGGGCTACTGATTTGGTACGTCGGGTCATGACCCCCAGTTCTTAGCCGCCTGCTCCAATCTCTGACTTGTTTATCGAGGGGATGGAGTAACGAAATGCGATTCGCCCGCTCAGGCCAAGGTATATATTTGAGTAGCGGAAGGTTGCACTACCTACAACCACGGCACACCGGCACGTTGTCGTGCCCTGGCGGCCTGCGCACTTTCGCCACTGTCAAGTACGACTTCCCAAGCTCAACCAACATTCATAATCCGGTGCAATTCATACCGTATCATCGTGCTATAAGCGACGCCGATTCTCGGGGCCTGATAATTGAGACTGGACTACATAGTGGGTGCCCTCTCTGCGAGTAAGTGACGGAACAACGGAGATCAGGGACCAAATGGTAGCAAAACAGATCGAGGTACACGCAGGTAGCTGTCCGTGGAGTAGACCGCGCTTAGCGTCTGTTAGAGTATCATCGGGGTATTAGACACAGGAACCTCTATGCTGTTAAAAGGCCATACCCCGTAATTGTGCAAATTTGTTACGTTCAAATCTACGCAGTGAGGGTCCTAAGGTGATGGCAGGGATTGGAACTTCTCCGCTGGCTCTTAGATTACTTAGCCAGTCTACCCTCGAAGATACAAATCCTTCCACCAGAGGGAGCTCATTGAAATTCATTCCATGCTACTCGACCGCGCGTATGGGTGCGGGGCTCTATGGGATCTAACTCGATCCTTCAGAGTCCTTATTCAAATGCATTTCCGTCCCCGTATGTTTCGACGAAGCCGAAGCCCAAACCCTGGGATGGACGAATTAAGGACAGTACAGGCAATAGTGTTCTCCCATACTCGGAACAGACGCCTCATTTTTTCGCGAAATCGATCTGGGTTGGAAGAAGTTCCAGTGCAGAGTTCCTATCACACAATTCGTTCTCGGGGCTTCCGGCCCATAAGCGATACTACTGTCTTTGCGAGCTAACGATTACATTCGGGGGAACTTAGCTCGGACTGGACCAGGTACATGATCCAAAGCGCGATGTCTGTCTGTTACCCTCACCGCCGCTCTTTTATCGGGTA"
    val t2_2 = "GCGTAGTAGGTTCGCGTACCTAGTTCCGCCGAAAAGACAAAGGAGAAGGGAATGCTCCTAGTAGTTTCAGTCTAGCAAACATGTTATAACGCTAACTGTGTGCTGCAAAAAGGATTTGAACCCAAATTTTAAAGCGCTGATCGACAGAACGCTGTTGAAGAGGCGATGGTACTGAGATTCCCCAGAAACCACCTCCGCGCTATGTGCTCAAGACAACCCGCATTCGTTTTTACTAGATTTGGAGCCGAGTTGTGATTTGGATATTTTCACATAAGACCGAGCAGGAAATATACCTTGTTGCAGCTATTGACCCCGTTCTCTCGGAAATCCATGGAATAGTCTTCGGATATTCGTACCAATGGGCGCGATGTTGCGATAAGAGAGCACATTTCATTAAGTGGTGCTCCGCCGCTAAGATGGGAAGGGGCGAGTCTATCGCAGCATCGAAGGCTGAGTTGGCCATTGCCGAGAGTATACATATTTACGATCACACTCGCATAGTCCCACGCATTACGTCCGAGATAGTATGTCCCAATGCAACCTAAAGCCGCGAGATTCCCTAAGGAGAAAATTAAACACTGGAAATTAGGTGATGCTACATCCCATGGACACTTTCGGAACAATATCGGTGACACACATCATCCGTGATCCCGTGATATTTCATCCATGGAGAGAGTATGGTTTTACTACACCTGGTCTAGGCCAAGCCTAACCCCCTGTTCATCCGTTTTATACGAGTATTACCTTGACGACCATAGAGGATAGACTCGGTATCCCGCACACTCTACACACACGACTTAATCCGCTCCACGACCTTCCTAGCGATCTTTGGCGCAGCCGGTTCGCGTATTTTACGACCAACTCGATGGATCCCAATTATCCCCCTGGTAGTGCCCCTCCGCCTGAGAATTCGACGGGCGAGGTCCGGGGGACCGACATAGAGTGGAATGCTTCTTTCCGGGATAACACGTGATTGACATAAAAATGTAGGGCAGATAGGCATCGTTAGCACCTCTCTCCTTGCTGCACTGCGTTTATCGATCGAATTCAAGACTTGTGCATGTTGAAAACAACCTCGCGTTATCCCTGCTATTTGCTTCAGAGCCGTAGGAGGGGACCATGCGTGAGTCCTCCTGAGCAACCTCAATT"

    // result is 844, correct
    val exam_string1 = "CGGGACAGCTTTCTTCTCTCCCCTCTTAAGGCGTGGCGTGGTCAGGTAGGCCTGAACTAGCAACCCTAATACTACTCCGTCTATTAGACAAACAGACAAACATTAATGCGCAACGTGGTCATGGAGTTTACGCCGTTCGCTATTGACCAGACTTACCTAAATATAGATAAGCAGTGATCTCAGGCGTGTAGAGCAGGCCCCTGCGCGGGATTTGTTGCAGCCGCGCCCCTCTGGCCGGGGGTGTGCATGTATGCATGCGGATACGCCAGCTGCTGGAATCGATCAACTCTAAAATTCACAGCCATCAGGGGACATTACGTCCTTACTTCCTACTCAAACTTCCTGGGTCTTCGAGCGCAAATCCTGAATGTTACGATCTTACCTATAGACTGATTTTACCTTCTAGCGCGTTCCGGCGAAGTGTGGTTCGGATGAGAGCATTCCGTATGAAATCATCATATAGCCTTTGAGTCTGAGATTTCCCAGAATCACGCTAGGCGAGCCAGCTAGCAGGCCGTGTCTCGTGGTTCAGTTAGAATCCCTTTCTTTTACTCGTCGATGGTAGTAGGCAGTACCTCGCCGTGCACATGAGGTCCCATCACTCTCCAATAGGTATTATTACTTGCCATATGGCCTGAACGAAAGCAAGACTATATTCTCAGGGGCAGATAGGCCACTAAGATTGCGTGTGTCACAATCAAGTGCCCCGACTCTTGGTCACTTCGGCTGTTGGCAATTTCATCGCATACTCCGTAGGAGGGGATGGTCGCAAATAAAGAACCCTAATCTCTGTATTTAGTGAGGGCCTTAGCCGCGAAGTTACTGAATTCTCCGACTTTGTTCCGAGCTTGAAGGGCTATTCAAGTAGGAACTCGGCGTTGATCACGGAAAGCCCTACTGTCAGACGCGACTCCAATTCCGTCGTCTAAGGAATTTCATGAAAACTCATTAGGCAAGGGTGTTGCCAGCTAGGTGTGGCCCTCGGGCATATATACACAAGCCAAGTTTATCTCACCCGGAACATGTTGTCTTGCTGGATTGCCGGCGTGCCAACATCAAGAATCCATTCATAAACACGTGTGGATAAGAAGTGATTAAATGTCTTCTTTGTGCTGTTTAGGCTGTAAAACGAGAAATCACGAAACCGAGCATCGTCTGGCGAGTCGCGTGACCGCTGCG"
    val exam_string2 = "TCTGACTCCTAGACGCACCTACATGTGCTTTTAGGGCCACGGCGGGTCTCTCCGAGCAAATGTTCGCTCCCTTACGACTCGTCTGGGTGCGGAGAGATAACAGACGCTGCGCTGGAGGTTTTGGGATGGCATAGTTACTTGAACCACGCACCCATTACTAATGCGTCTCCACCATAAGCTCTAAAACGCCTCTCGGCTAAGCGCATTCGCGTCTATGCCTGCGTCGAGGCTACATTCTGAACTATTATCAGCATCGCCGTTGACGACCGGTTACTCTTCGTCGTACTAAAAGCGTTAGCAGCTCGGCAGACATTTTCTTCACTTCGTGGCATCGCGACGTCCCTGTATCTCAACCACGTCTGCGAGGTAAATATCGGATGGGGGAGATTTCTGCGACGACAGCTGCCGCGTTCTATGACGCGGGCACGAAAATTTCACAGGCTCGCCTTTAGCAGCCCGTTTCCCCCCATGTCCTAAGGCTCTGTCATGGTATTACCATACGACGGAGCCTGGTTATCATGTATGGTTCATCGGACAGACTCAGATGTACAAGTAGGCGCGCGAGTGCGATTCACGCGCCACCTGGGCCCCGCCAAATCTTTATTGTGCATCTCAGCAGGGTCCAAGGTTTCCGTATATACATGATCCAACGTCAAGCTGGATGAAGGACGGTTTCAAGTATCACCGGACGAATTTCACAATCGCATCTCCCACAAGTGCAGCACCCCTGGCCAGAGATACGCTGGCGATGCTTCGCAAGCGTGGCACTGAAAATGGTTTAAAGTATAACCATTACGGCCCTCACCGGGGAGCTTATTAAAAAGACGCCCATCCCACCTCCCATGAAGGGGAACGAAATGGGTGGCCAGTCACAATCTTTATGTCGCACCAACCCCAACGTAAAGTTCGTAGGCAACGTCTGCCCATGGTACACTCGCCACCAACCCGATCGGACATTGACGAGCCCAAAGGCTCGTGTGGCAAATAAAACATGTTGCGGACGACACGAGCTTTCTCCTTTCGTAAGGCGTACGGCCCACCGCTTTCGGGTGTAAATGCCATATGGCTGTCCGTTGTTCGAGTACCCGCCAAGTATGTTGCACCCAGACAGAGCCCTGGTTGTAAATGTTACCAGCGTCGGAAGAGAGACGTTTCTCATCAACTATCAACGGAGCGAGC"
    println(hammingDistance(exam_string1, exam_string2))

    val rosalind_example1 = "GAGCCTACTAACGGGAT"
    val rosalind_example2 = "CATCGTAATGACGGCCT"
    println(hammingDistance(rosalind_example1, rosalind_example2))

    val rosalind_quiz1 = "AGTACACGACCGCGAGGGACGCCAATTAATTGGATCAACAGATTGCAGCTATCACCATGGTATCAATAGGCCGGGAGCCGATCCGAGCATGAAATAAGGGTAGATATACTGCGCCCAAGCAATAACTAGGCACGGCGATTGTGGTAGAGATCCGTAACCGAATGTACCCCGCAACTCTGCGGAAATAGGCTTTACGATGCGACTAACCTGTCCGGAGGTGGACTAGCTGGGTCACTCTTTGATACCAAAGAAAGCCGTAAACCATTAATCGGCAAGGGACAGAACAATGGGCGAGGAATTATCATCTGTATTCTCAGCCTGCTTTATCGGCACGCCCACATAGTAGGCATCGTTTTAGCTGTACACATATTGGCGATGGGCAGAACCTACAAGCTCACTAGCGGCGGGACGGTGAATACGTTGGCTGCCGTAGGTGCCAACTGCCACATATTGATCGTTAAAAATCGCGATGAAATTCTCACACAGACTGGACAGCCTAGTCGTCGACTCGACGTTTGGCTCCGAAGATTCGACCTTGGTGATATGTGTTAGCCGGATTTTTGTGTGGTCACTACTGTGCTAGTTTACACCATAAAAATCAACAAATGACTATTACATCTTCACCTGGTTATTGCCAGACTTATACCAGGGGCAACGGGACATTGGAGCGTATCATTGAACAAGTGCGAACCTGATTATCCCAACCCGTATTCGCTGCCAGTGCCTGCATTACCGACGCCCTAGTTGAGTATTTTGCGATAGCCGGATCACTGTCAACATGCGATAACTCACCTGCCGCGTACTGAGGATCACCTACGGTTAGGAGCTATAGTAACCTGTCGGTAAACCGATCCAACTTGGTCTGTGGTCTCGGCCGGAGTTGGACTTCTAGTAGACCGTACCATCCCTGTGCATTATAGGCTTGTTCAATCTTCATATGCTTCGTCCTTACGATTAGCCCCTCCTCA"
    val rosalind_quiz2 = "AGTACGTTTCCCCCTGGTACGCCATTTTACAATTAGCAATTAATGGAGAAAAACTGATCGTTTGTGGAAATCGGTAGCCGTCCCGTGCCTTGTCCACGGGAGTAAGTAATCTGGCCTGTCGATATCCGGTCGCGATCGTCGATGTTTTAATCCGACATTGCGACTCCCCATTACCTTTAGTACGTTCCTCGTTACGGTCAGACTTGCATGTCTCTTGGCGGACTAGCAGCGTATCCCTCTGATTCCACTAAGGTCCGCAAACCTGGAAACGGTTGTTGACCTATCTCGCAGCGCTAGACTCTCTTGGACAGAGACAGTGCAAGTTCACCTCACGGTTGTATCGTTTGCCTCCGTTCAGCTAGTCAGATATGTCTTATCTGCAACACTTTAGCGCTACCGTGCAGCCAGAAGGGCCCTCCCAGTGCTGCCCTAGGTGGCGTATAGCTGATGTTCCCCGTTGATACGCTCAGTCAAATGCCATAAAGGAATCATGAGCGAGGTCTAGGTCTCGAATTTGGTCTCCTAAGTGTCGTACTGTGTATTATCGTTACGACCTACTTTTAGGTGATTGTTACATTGATCTTCTGCACTATAGTATAGAGGAAATGGCTACCCAAAAATAGCTTGGGAACCGACGATAGTGGTCCTTGAGCAACCGGGCAATTGCTCGCATCTTAATATGTCTGGTACTCTGATCGTCGACACACGTGTTGAGATTCCGTCGCGGCATCAGTAAACGTGCAGGAGATAGTTATACTTAAGCGAGATGAGTACGGATACGAGATATCACACAGCCTCTTTTTTTTGGCCGCCCTAAGTATAGGCTGTAAATGAACCCCTCGGCCATACAAACCAGTTAAGTCCATGCCCACTCTCGGTGTTGTAAGCTGAGTTGACTGGACCATCTTTATTCAGGCTCGGCGATTTCATTGAACTTAGACGTCCTTCAAAAAACTGGCCGATACCAA"
    println(hammingDistance(rosalind_quiz1, rosalind_quiz2))


}

