@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.FASTQtrimLeadingTrailing
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

/**
 * See also:
 * http://rosalind.info/problems/bfil/
 * Base Filtration by Quality
 *
 * Problem

Given: FASTQ file, quality cut-off value q, Phred33 quality score assumed.

Return: FASTQ file trimmed from the both ends (removed leading and trailing bases with quality lower than q)

 */

internal class RosieBaseFiltrationByQualityBFILTest {

    lateinit var bfbq: FASTQtrimLeadingTrailing
    lateinit var u: Utility

    @BeforeEach
    fun setUp() {
        bfbq = FASTQtrimLeadingTrailing()
        u = Utility()
    }

    @Test
    @DisplayName("Rosalind Base Filtration By Quality BFIL sample test")
    fun rosalindBaseFiltrationSampleTest() {
        val readList = """
20
@Rosalind_0049
GCAGAGACCAGTAGATGTGTTTGCGGACGGTCGGGCTCCATGTGACACAG
+
FD@@;C<AI?4BA:=>C<G=:AE=><A??>764A8B797@A:58:527+,
@Rosalind_0049
AATGGGGGGGGGAGACAAAATACGGCTAAGGCAGGGGTCCTTGATGTCAT
+
1<<65:793967<4:92568-34:.>1;2752)24')*15;1,.3*3+*!
@Rosalind_0049
ACCCCATACGGCGAGCGTCAGCATCTGATATCCTCTTTCAATCCTAGCTA
+
B:EI>JDB5=>DA?E6B@@CA?C;=;@@C:6D:3=@49;@87;::;;?8+
    """.trimIndent().lines().toMutableList()

        val expectedResult = """
@Rosalind_0049
GCAGAGACCAGTAGATGTGTTTGCGGACGGTCGGGCTCCATGTGACAC
+
FD@@;C<AI?4BA:=>C<G=:AE=><A??>764A8B797@A:58:527
@Rosalind_0049
ATGGGGGGGGGAGACAAAATACGGCTAAGGCAGGGGTCCT
+
<<65:793967<4:92568-34:.>1;2752)24')*15;
@Rosalind_0049
ACCCCATACGGCGAGCGTCAGCATCTGATATCCTCTTTCAATCCTAGCT
+
B:EI>JDB5=>DA?E6B@@CA?C;=;@@C:6D:3=@49;@87;::;;?8
        """.trimIndent().lines().toMutableList()

        val threshold = readList[0].toInt()
        readList.removeFirst()

        val qList : MutableList<String> = mutableListOf()
        val outList : MutableList<String> = mutableListOf()
        while (readList.size > 0) {
            qList.add(readList[0]) // name
            readList.removeFirst()
            qList.add(readList[0]) // dna data
            readList.removeFirst()
            readList.removeFirst() // toss the +
            qList.add(readList[0]) // quality line
            readList.removeFirst()
            outList.addAll(bfbq.trimLeadingTrailing(qList, threshold))
            qList.clear()
        }

//        println(outList.joinToString("\n"))

        assertContentEquals(expectedResult, outList)
    }


}