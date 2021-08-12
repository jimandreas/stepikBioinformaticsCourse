@file:Suppress("MayBeConstant", "unused")

import util.reverseComplement

/**
 *  1.3 Some Hidden Messages are More Surprising than Others

Reverse Complement Problem: Find the reverse complement of a DNA string.

Input: A DNA string Pattern.
Output: Patternrc , the reverse complement of Pattern.


See also:
stepik: https://stepik.org/lesson/240215/step/2?unit=212562
rosalind: http://rosalind.info/problems/ba1c/
 */

/*
Sample Input:
*/
val sampleString = "AAAACCCGGT"

val classString = """
    GATAGATTGACTGAGATCTGGAGCCGCACTGTCTCGTAGCTCATATGCGAGCGCACTAGGGGTCAGCTGCAGACTAGCGACTATGCCCTTGTTAAGTCCTCATCACTTCGCGTACTGGTCCGAGCGTGGAGACCGGACTCTAATCGACCGACCCAGCGGAGTATCCTTCTCTACGACGAAAAGATGAGTCGAGCATTTTGCCTGGACCCTGCTATCCAATCATCGTTAGACATATCAATGGTTCCACCGTATTTAACATATTGGTCGTAGTGAGACCATAGCATCTCCGCATGAGGTGACCCCCCATTAGCGGTGAGGGAAGCTCCAATCGCAAATCTGTCAACGTTCATCGTATGTCAACATTGTAAAGTACAGGCATGTATAAACTTCATGGCAGCCACCGTGGGAAAAATCCCCCAGGTTAGGCAAGGAACATGGCATCCACAGAGAGATACCACATGAGGAGGCAGCTGAAACTAAGATCATGGGATAGTTTGAGTGTCATTTGCCACAGTGGCAGCACCCACCAACGCATATCCAGCGTATTAGCACTGTCAGGAATGTCTGACACAACTTTGTTGGCTTCTACACATAAAATTTTGCCCAGCTGGCTGTCTATTTTCACTATGCTTCCACAGGTAACGACGCAAGAGTCTATGCGGTAAGCCTCGTACGTGGTTTGCCGGAGGACCCGACTGGGGACGGCTGTCGGCTCATCTCATTGAGATGCCACCGCACCACCGCGGGGAATCCAACAAGACGCTTGCATTCAGTTGCCTCGGACTACACCCCCCTTGCTATGCAACTTCACCTAACAGGTATATTTTGACGCGCTCTAATTGCTCTTGCCTGTGATTGGTACAGGACACCTTCTGCAGGAACCTGTGTGACTGTTATTTGACACGTATTCTCAAGATTCGCGGAGGGATTCCTAAGATAGGTGTCTTCTTTTTGCAAGCGCCGAACTCTTAGGGTAGCCGATACGCAGAGGCTGATGGGTGGGCCGATCTGCGACCAATGGTTGCAGGAGCTTTACAGGTGACGTGACGTCAACTCGAAACAAAATTTCCGAGGCTATCGCTACAAGGGGTGTCAATTGTATACACCGGCTCCTAAATGGAGTTGGCATATTTGGGTGCTGACAATTTCTGCCCTTTGGGCTTAAAGGGAGTGGGGACATGCGAGTGGGATTTAATTCATCCCTCACATGTCATTGGCCAGATGGCCGGAGCTATCTACGCCCCTTACATCTCAATTATGACCAATCTCAAAACATCGCGTGCGTAAAGAAGCTCCGAGATAGCTAAAACCTGGCTAGCCACGAGTATATCCGCCCTTTGCAGTTTGAGCCTTCACAATCTTGGAATATATGCTCAGTCAGCCTAGCACGTGGATGGGTCGGCACCGACGGGTGGAGGGGGTATGCCTTAAATGAAATAGTGCTCATCTACGCCCCCCCCAGGGTATCATGTTGCGGAAGTGCATACCACGTTTATAAAGCGCCCTTCCATACGTTTCACCACGGTGCATGCGCCCCCGTAGACACCCGTGCACAGGTCATGCGAGATGTAAAACACTAAGCCCCATTACTTCCGCAAGGCCCGTAAATTTACTCTGGCCCGATTTCCTCCTCACTTAATGTAATCGTCGCTCACCCGGAATTCGTTCGCGAATTCAAAAAGAGGTCAAATGTTAACCACGTTGGGGACGTCGTCGTGCACAGTTTAGTTATACTTCTCTAGCTTTAGGGTGACCTAACTTAAGGGGGCGTTCTTGCCCATAGCTCATCCGGGACAACAGCCACTATTTTGTGTTTGATCCAAGGAGGTATTGGGACGCGCGTTAGCAAGTACGAGAACCAAGAGGTTTCGAACCACCTGGAAAACCGTGTCACTGCTTTAACCCGACCTGCTGGAACAGTTTTATGATGCCTACTTCAAAATGGTTGACTTCGATGCATTGTATGCGAGACGTCACAGACCTGGCAGCACAAGCTTTGGCCATATCGCATTTCCAGCCATTTTATTGCCTAACGCATGTGAAATGCTACTTAAGTGCCAATCATTATGAACTTGTCCGATGCTACATGAGATACCCGACGGCACTCGTCATCATGACCAGCAATAAAGAGCTCCTGTTATCGGTGTTGAGTACGTTCGCTCCGGTCCAGTCTGGCGGTTTCAACCACCTCCGCCCACACACTGTATTTCATTCTGCCATCAGTCTTCACCCAAGCGTGATAAAAATTTGAACTTAATACCGATCGCATGCAATCGAGTAAAGTATTTAGACGCGACTCCACCTTCCGTAAACCGCAGCCGCTCGCAACCCGTAGGGTACGGCTACGTTCGACAGGTTAACCCGTAAACTCTTGCTTGGGTTCCACACACTAATGAAAGATCAATAAACGTTGCTGTTCCTTTTTCACGTTGAAGCAAACTACATGTCATGCTGAAGAGGTGATAGTTCCTATACGCTCTCCTGTCATATAGAGTTTTGTAACCCGAACCTCCCCGTCGTGCTGCGTATCGACTTCAGATCTTGTGTTGCCCTGGAATGGGCTGCAACTAGAGGCTCTCGTTACACTGCGGATAGTTTGGCTTAACGAATGACTGTGTCAACAAAATAACCGGACCTCAGATCGCCTCGATTTGCGAATTAGGGGCGCAGGTCATACTGAGATTATAAGCCCCCAATGCATAAGTCTGTTACAGTGTCCAAAAGTAAGTGCGGAAACGGCACTCAGGGTGCTAACTCCCTCCTCAAAAGTATTATTGCGTCATAGCCCGCCTTTTCCAAACGCGACGAATCCGACGAGCCGAAGGCATAGACGACTGTATCCAGAGTTCGCTCCCGGAGCTTACAGCCTATAGTAGGACCCCTCAGAACAAGTGCCTAAGTTGCGCTAAGATCAGTGCAACTCAAGTGCTCTGGACCTCGGTGTCCTTCAGCATCACGTTAAGCCCGATTGTTTGGTGTAGCAGAAAACCTCCGACTTTAGTCACTAAACCACGCCGAGTCGTACGAGTAACGTTCTATTAAGCCCATCCTTTGAGTCACTCCGGGAGGAGAAATTCGGAAACCCACAGTCGCACCCAACTTCACATCTTGAGCAGAGAGATGATGTACGCTGAGTATACTACCGACTCTTTGATCCTTATTGACTAGGGACGCCTCAATGGTAACACCAGTCTAGTGGGCACGAAAACGTGGGCAGAGCTCTTTCGGGGAAATCAGGGTCGGGAGGATACTCAATGCGGTTTTCTCTGACCAGGGCGTAACAACCACGGCTCATCCGCAATGGGCTCTACGATAACCAGGCCTCCATGTAAGAGCATGTGTTTCTGACCGATCAACGTAGTAGTACCTGCGTTCCTTCTGGGGCCCCAAGAGCATGGCAGCGTGAGGGCCCATATGAGATTTAGATACAGGATCGGGAGATATCACATGACGCTGAGGATAGGCATTATCCGCCAAGAAGATCCCAAGGGGCTTCCCCCCTTAACTTGAGTGGTAGTTACATGGGTGCCACTGGTTGACGTTAGGACACGTGTGCTTTGAAGACAGAAGGTTGCCATACCCCCTTTCCGCGTAACGGAACTATGTGTTAGTTATGTAGATTTCTCCGTATTGGCCCCCTAACCCCTCTTTCTGTAAATTATACCTAGTATAACTCAGCGCATTCACGTATTGCGTTGTCGCCCTGCAATTAAAAGCGGCTTGGACCCTATCCAAGGGTTGGGAGGGAAGGGTTGGCTCATGGGCACATGTTAAATGCACGCCCTGCCTTCTGGGAGTATTAGTCTCCAAACCTAAACGAAATTTAGTCCAACAAGACTGTCTTCTAACAGACATTATCACTCACTTAACTATGATAGTTGGACGACCCAAAGAGGGCTCAGGGGCGCCATACATCTCGAGTGCGACCGTGGTCACAACGAGCTGTTCAGGCAAACTGGCCTGCTGAGTCACTCGCGTATCCGGCAGTTCACGGTTACTCTTATCATCGCAACAGCAGGTAACATGAGGCGTGTACACTCCAGTACGTCGCACAATGCCAATATGCTTTTTCCACTCTAGGACGGCGACTTGCTAGGCTGCCGTTTACTTTTTTTGTGACATAGGTAACGCGCATCCGCGCATAGTTACATGGTCCTCATTAAGGATTTTGGCAAGCACTCCAAGAAGGGCCGCCCTTCAGACTTGTGTGGTTTCTAATGGGATGACGAGTTGCGAATACCGACGCATTTACTTTACCCACGCAAAACGCCGCTAATTTGGAAAATCAGCAGCCGGGGCAGCTAGTAATTACGTATCGCTCTAGACATAATATTTTGCAGTTTTTCAGGACTTCCGTAACTTCCCTCCTAGTCATTGACCCCGCAGCGAGTTTTCGGGACCTAGAAGGGTTCGATCGTTGCGTTAGACACCGGGAGAACTTAGACATTGAGTAACGCATATTAATATGAGCATGCTCCTCGACACGGGATGAGATGTTCAACGTGGACGTATACATAAGCTGTAAGCCGAATCGAACGCTACGAGAAGTTCGTTTAAACCACACTTCAGTTAGACTTGGGGACCACGAGGTAGACAGGACCCCACAGTGTACCCTTGATTGCCGGGTAATAATTATGAGCTCTCGGACGTCGATTGGTCTCCCTAGCAGTAGGGCAATTGTCTATTTCTTAGCTAAACCTGCCCACCGCGGCACTACGCACTTCGACCTATATTATATTGTTTTAGCAAATTCTTGGCTCGTTAATGGGCCGCATTGCTTCAAGGCAATGAGAAGGACGTCTAGCGACCATCACATTGTTGATCCCATCTATTCAGCCGCTAGCTGATATAGGGATTAAATCGCATTAGGGGAGCACTCCGGACTTTATCAGGATTGTTAGGCCTTGGCTTCCCCGGCTTATTCGAGGATGGACCTTTCTTGCTTCCAGGGCTAACGTCGACTCGAAACTTGGTGCGCGAGATCTAGAGAGGTAGAACGGGGTTGCTCCGTGAAACCTAAGGGGGTCATGAACTGCCGTCACTGTCCTAGTGCACTCTCCAATCTTATTCGGTATATTCGGGCCACACAATTGTATCGGTAATGTGCCCTCACTACATCCCTGACAGTAAAACAAATTCAGCGTACAACGCGGATGGCGCCGATTTTGGTTCCCTTGTAGCAAATTCCTGTTCCTCATTAGCTGGTATTGAGGTACGGTGATTGTGATCATCGCAAGTACCAGTCGATAGACTTTCCGGGTCCATATGCACCGGAGAAGGCGGGGGCTACAGAACCGTCCTAGGGACGTGGAAGCTAGACGATTGGATGTCATAGCTCCTATCTGTCTGGTCTTAATGTTTTATCAACCGGAGTATTCAGGCTCTGCTGAAGTAGTCGCAACTTTCGGAATGACTCTTCTTGCTGGGACTTATAGTGTACGCGATGGTCTCGAAATGGCACAGGTCATTATTGGGATACCCAATTAGAGAACAATCCTCCCAGTCAATCCACTGGGACTAATTACTAGCCAACGCCTTAATAGGAGTGATCATCTCAGATACCGATCGCTCACAGCTGGTCACCCCACGTGCAGTTCTTGCCAGAAACTGGGACAGCCACATGTTCTATTCTGTCGTGATGGATGAAGATTCCACGCATCTTCAGGTGAATATCTAATGCTTCCCTGTAAACCACAATGACCCCTTAACATGCGATTATGTGGGGGCCCCCGCCTCAAGTGGCAACGCGAAGGGCGTTGATGATACGGTTCGGCGACGTGGTAGCCACGGGCACTTTCGTTATACCCGAACAAATCTAGACGGAGAGGCCTTGGAGAAGAACATTCCACCATTAAACCTGTGGGTCCGTCGGAGGACCCCGAAACAGAAGTGCAATTAGTGACGCATTCTGTGCTGCAGCTTAATAGTCAGAGACCTTAGCCGGGCACCATTCGTTTTCGGGGCCTAGCTACGCTTCTCTTGAGTACATCGAGTCTGTGGGGTGGTCTTCAGTTGGCTAGGTCTCCGCGGGACCTAGGGTACGTTATGCAGCCGGTACGTGTGCAGCTTCCTCAAGGAACCATCCGAGTCCAATTCCTGGTCATCAGGGCGGTAATCATCCACAAATGTCCGAACCGGAGTAGGTCCGTAGATTGCTATGGCAATATCCGCCGTCTAGACACGGACCCAGAGTGCACCCCCAGCTCGGTATACGTTTGCGATTTGGGCCATGGAGCTGGCCTACACTAACATTAGAAGATTTGCACCAACACACTGAGTAAGGTTAGGGCCCAGCTTCCGCTTCCAGCATACCGTACTGAGGAGCTACTTGAATAAAGATACTACCCCAGTGAAGAGGCTAAGTAAGATAACCTGGGCTTGCTGCTCCACCCCAGCCGAAGCCATGCGGTACCCGAATCGATGAACTGGTATGGGCTTATTTGGATGATAGGGAACCAAAATTAACTAGCTTTCGTTGGAAGACCGCATCCGACGCACCGATAAACCGCTGGAATAAATAGAGAGGGACATTCGAGGGTTGGTAGCTTGCCAGCAAGATAGAGTGTACTTTAATCAGTTGTGATCTGGAGAAGTCGGTATATTACGATAGCTGGGTGTACCTCGCAGTGTGATTAGTTGTTACGCCGACGCACGAGCCCAGATTCCATGCTTATGAAACACCGGTAGTTGTTGCCGTCGATGGAACCCGGCAGATGCTCGCCAACCTGCAGCCTATATTCGGTCAGCCGATAGCCGTTTTGGGTCAGCATACAGCGGTGCATTAGAAGGCGACTCATCCAAAGGCACTCCGTTCAGCTCGACGGCGGGGTCAGGAACATGCCGCGACAGCGAGCGGATTAGTGATTCTGTCTACAACACGTTCCATTTCGCGATCTCGGCGTAGAAATCCTTAAAGTCAGTGGGAGGGGCCTTGCTTGCATGATAATAAGAGCGGGGTAAGCGAACTCTTGCTCGTCTTCGAAATACCCGACTTCCAGCGCCAGTGATTTGAATTTTCTACTAAGGCTGAAGTACGCCGGCTCGCGGATTGCCTGTGCCGGGTTACTTGCAAGTTTAAGATTTCACCTGAAGCATATCCCCGCTTTGTGTCATGCGCGCGAGAATATTCGGACTGATTAGACGATCACGCACAACTCCTCGCAACTTGCCAGGTAAACCAACTACCAGGCACGTCACAGGGAGGTATGCTCCTTCAAAATTGTACTACCGGTGTTTCTTCAGTGGTAGTAGGTTCACACTGGCTGGACCCGAGGAGGATATACAAGTGCAGCCCGTCAGGATTAGCGGCCATTAGCCAGCTAGGCGCGCACTTGACGTATTAGTGGGCGCGGTGCTTGAATTAAACTGCGCTCGGTCCAGCTATCATACGCCACTGGTAACGGAATCAACTATCTTGATCGCAGCTATGCCGTAACTTATCCGTTAGGAAGCGGCCAGATAAGCGGTTAAAGATCTGCCTGGCTATACGATATGCTGCGTCCAGAAAGCTGGGCTGTCAATTCGCATTCGTACCAAACGTTATCTGTCTCGCTGACGTAATACCCCCCCATACGAGGACCTCGTACATGGACAGGATATGAACTCATTGGCTAGTGAGGGGGATGGTGGATTTCGGATTTCAATGATAGATTCTGGCGTACTTAGAATGAAACCATGTCACCAACTGGTTTCCTGCCTCTACCTACTCGAGATTATCACCGGCAGCAAGCACTCCGAAAGCACAGTAACATGAGTAAACGCCAGGCAGACTATCGTCAGTAATGTAATCTGTAGTACCATCACGGGCGATCTGGCAATCGGTTACGAAATGACACTAATGAATTCGCCACTAATTTACTACCTACCGGGTGGCATGTCCCATAATACGCGGCGAAAATTGGCATTAACGCGAATTACGCAATCGACTTCATCTGCTACTCAGCCTACTAAACCAAGCGTTTCCACACACATATTCAAGTGAATGGGGAGTAGTGGGTTAGCCTTCTACTTGCCGTAATATAGAAGCGACAGGATTGTCTAAGCGAGCGAAACCATGTGTTCACCAGTGATCTCCATCGATGTCGGTCCCGTGTAACATTTACAAGTCGGTGCTGCTTCTGAAAGCAAGTTCGAGCTTCCTTCCCATGGCTAAACTAGGACCCCAATGGCCTCAGTTCATTTCACCGCCCGTGGAATATGTACATAATAAGCAAAAAGTTAGCATTTAAAACCTGGGCGCCGATGTCACGGGATTTTGATCTATTGAGTTTACCAGTCCTGTGGT
""".trimIndent()
/*
Sample Output:

ACCGGGTTTT
 */

fun main() {
    val returnedValue = reverseComplement(classString)
    println(returnedValue)
}

