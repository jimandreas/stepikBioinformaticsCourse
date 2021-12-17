@file:Suppress("UNUSED_VARIABLE", "UnnecessaryVariable")

package problems

import tables.codonUtilsFindLongestPeptide

/**
 * see also:
 * http://rosalind.info/problems/orfr/
 * https://www.ncbi.nlm.nih.gov/Taxonomy/Utils/wprintgc.cgi
 *
 * Problem

An ORF begins with a start codon and ends either at a stop codon
or at the end of the string. We will assume the standard
genetic code (i.e. Table 1) for translating an RNA string into a protein
string.

Given: A DNA string s of length at most 1 kbp.

Return: The longest protein string that can be translated from an ORF of s.
If more than one protein string of maximum length exists, then you may output any solution.
 */


fun main() {

    val dnaString1 =
        "ACAAATAAGGTCTTTATTATGACCCTCACTCCGGCGGGTTGTAGAATAACAGGCATGATGTTGAGGGATGCATCGGGGACTAGCGAATCATCGGTCACTCCACGTTGCAGATCACAAAAGGTCGAAAATGGGAAGGGGACAGCTCGTGGAATATTTCGTGATTCCTACGTTTTGCCTATGCTACACTGAGGTATCACTGTTGATATGCGTCAGAGGGTTTATCCGTGGAACGAATGCAGTCGCCTTGTTGAGGCAAAGCCGGGGCTAAATCCGATGAGCTAACAGATGGTTTATGCATCGGCAATTTAATCAACTCTCTGATTTAGTGACGATATGGGCTATGCGACATACGTGCTATGTCTCGCAGAGCTAAAATATTTCCATTTAGCTTACCTCTTGTTAGCCCCAATACCGCAACGTGATGTAGTTTACCGGGATCGCCTGAATTCCGGTATGCTCAAGCAGACGCATTTACGGAGTGTGTAGCTACACACTCCGTAAATGCGTCTGCTTGAGCATGCTCAGACCATGTACGTTCTCGATCGCTTCACCTCGAAAACACGCCTACTCCTGTGACCCTAACCGCCGGTCGTTATTACGGTAACGATTGGAAGAGCAATGTTCATCAGTCTGAATATTGCACTCTTATCATAGTGGAAGACTTAGTGGAGCTGAAGTGATTATGTCTACCCGCCTGAGCGATGCGCCACAACCTGGCCGACGACGATTGTTAAAAAATGTTTGATCCCAAGCGCCCGCCACCGTTTTGATGTTGAACTCGAAGGTGTTGGCGTGACAGGGTCCACCGGTATCGTATACTAAACCCGCGTCTATCGGTCTCGTTGAACTTATTCACCTGACGAACATTCGGCCCATCTCGAGTACCCATAGTCTGGCGGACCACAACCTAGTGCATCCGCGGAAGTTGGTACAATGACAAGCGCGTCACTGAGTGTCACATAGTCGTGCATG"

    val d = dnaString1

    val peptide = codonUtilsFindLongestPeptide(
        codonString = d,
        isDnaString = true,
        scanReverseComplement = true,
        startCodon = "ATG",
        table = 0 )// corresponds to canonical table 1)
    println(peptide)



}