@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

package problems

import algorithms.ContigGeneration


/**
Contig Generation Problem: Generate the contigs from a collection of reads (with imperfect coverage).

Input: A collection of k-mers Patterns.
Output: All contigs in DeBruijn(Patterns).

 * See also:
 * rosalind: @link: http://rosalind.info/problems/ba3k/
 */

/**
 *  Sample Input:

ATG
ATG
TGT
TGG
CAT
GGA
GAT
AGA

Sample Output:

AGA ATG ATG CAT GAT TGGA TGT
 */
fun main() {

    val cg = ContigGeneration()
    val kMerList = """  // replace with test kmers
            GAGA
            AGAG
            AACG
            ACGT
            ACGG
        """.trimIndent()

    val reader = kMerList.reader()
    val lines = reader.readLines()
    val result = cg.contigGeneration(lines)

    println(result.joinToString("\n"))
}




