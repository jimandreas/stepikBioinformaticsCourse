@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tables.codonUtilsFindLongestPeptide
import kotlin.test.assertEquals

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


internal class RosieFindOpenReadingFrameORFRTest {


    lateinit var u: Utility

    @BeforeEach
    fun setUp() {

        u = Utility()
    }

    @Test
    @DisplayName("Rosalind Find Open Reading Frame ORFR sample test")
    fun rosalindFindORFSampleTest() {
        val dnaString =
            "AGCCATGTAGCTAACTCAGGTTACATGGGGATGACCCCGCGACTTGGATTAGAGTCTCTTTTGGAATAAGCCTGAATGATCCGAGTAGCATCTCAG"

        val expectedResult = "MLLGSFRLIPKETLIQVAGSSPCNLS"

        val peptide = codonUtilsFindLongestPeptide(
            codonString = dnaString,
            isDnaString = true,
            scanReverseComplement = true,
            startCodon = "ATG",
            table = 0 // corresponds to canonical table 1
        )
        //println(peptide)
        assertEquals(expectedResult, peptide)

    }

    @Test
    @DisplayName("Rosalind Find Open Reading Frame ORFR quiz test")
    fun rosalindFindORFQuizTest() {
        val dnaString =
            "ACAAATAAGGTCTTTATTATGACCCTCACTCCGGCGGGTTGTAGAATAACAGGCATGATGTTGAGGGATGCATCGGGGACTAGCGAATCATCGGTCACTCCACGTTGCAGATCACAAAAGGTCGAAAATGGGAAGGGGACAGCTCGTGGAATATTTCGTGATTCCTACGTTTTGCCTATGCTACACTGAGGTATCACTGTTGATATGCGTCAGAGGGTTTATCCGTGGAACGAATGCAGTCGCCTTGTTGAGGCAAAGCCGGGGCTAAATCCGATGAGCTAACAGATGGTTTATGCATCGGCAATTTAATCAACTCTCTGATTTAGTGACGATATGGGCTATGCGACATACGTGCTATGTCTCGCAGAGCTAAAATATTTCCATTTAGCTTACCTCTTGTTAGCCCCAATACCGCAACGTGATGTAGTTTACCGGGATCGCCTGAATTCCGGTATGCTCAAGCAGACGCATTTACGGAGTGTGTAGCTACACACTCCGTAAATGCGTCTGCTTGAGCATGCTCAGACCATGTACGTTCTCGATCGCTTCACCTCGAAAACACGCCTACTCCTGTGACCCTAACCGCCGGTCGTTATTACGGTAACGATTGGAAGAGCAATGTTCATCAGTCTGAATATTGCACTCTTATCATAGTGGAAGACTTAGTGGAGCTGAAGTGATTATGTCTACCCGCCTGAGCGATGCGCCACAACCTGGCCGACGACGATTGTTAAAAAATGTTTGATCCCAAGCGCCCGCCACCGTTTTGATGTTGAACTCGAAGGTGTTGGCGTGACAGGGTCCACCGGTATCGTATACTAAACCCGCGTCTATCGGTCTCGTTGAACTTATTCACCTGACGAACATTCGGCCCATCTCGAGTACCCATAGTCTGGCGGACCACAACCTAGTGCATCCGCGGAAGTTGGTACAATGACAAGCGCGTCACTGAGTGTCACATAGTCGTGCATG"

        val expectedResult = "MHRGLANHRSLHVADHKRSKMGRGQLVEYFVIPTFCLCYTEVSLLICVRGFIRGTNAVALLRQSRG"

        val peptide = codonUtilsFindLongestPeptide(
            codonString = dnaString,
            isDnaString = true,
            scanReverseComplement = true,
            startCodon = "ATG",
            table = 0 // corresponds to canonical table 1
        )
        //println(peptide)
        assertEquals(expectedResult, peptide)

    }



}