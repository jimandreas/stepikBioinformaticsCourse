@file:Suppress("unused", "UseWithIndex")

package tables

import algorithms.dnaToRna
import okio.utf8Size
import org.jetbrains.kotlinx.multik.api.d3array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import problems.printRepeatedTargetIndexesWithOverlap
import java.io.File
import java.lang.StringBuilder

/**
 * These tables are derived from:
 * The Genetic Codes - Compiled by Andrzej (Anjay) Elzanowski
 * and Jim Ostell at National Center for Biotechnology Information (NCBI),
 * Bethesda, Maryland, U.S.A.
 * Last update of the Genetic Codes: Jan. 7, 2019
 * https://www.ncbi.nlm.nih.gov/Taxonomy/Utils/wprintgc.cgi
 */

class TheGeneticCodesNCBI {


    val tableTableNumberToTableName: HashMap<Int, String> = hashMapOf(
        Pair(1, "The Standard Code"),
        Pair(2, "The Vertebrate Mitochondrial Code"),
        Pair(3, "The Yeast Mitochondrial Code"),
        Pair(4, "The Mold, Protozoan, and Coelenterate Mitochondrial Code and the Mycoplasma/Spiroplasma Code"),
        Pair(5, "The Invertebrate Mitochondrial Code"),
        Pair(6, "The Ciliate, Dasycladacean and Hexamita Nuclear Code"),
        Pair(9, "The Echinoderm and Flatworm Mitochondrial Code"),
        Pair(10, "The Euplotid Nuclear Code"),
        Pair(11, "The Bacterial, Archaeal and Plant Plastid Code"),
        Pair(12, "The Alternative Yeast Nuclear Code"),
        Pair(13, "The Ascidian Mitochondrial Code"),
        Pair(14, "The Alternative Flatworm Mitochondrial Code"),
        Pair(16, "Chlorophycean Mitochondrial Code"),
        Pair(21, "Trematode Mitochondrial Code"),
        Pair(22, "Scenedesmus obliquus Mitochondrial Code"),
        Pair(23, "Thraustochytrium Mitochondrial Code"),
        Pair(24, "Rhabdopleuridae Mitochondrial Code"),
        Pair(25, "Candidate Division SR1 and Gracilibacteria Code"),
        Pair(26, "Pachysolen tannophilus Nuclear Code"),
        Pair(27, "Karyorelict Nuclear Code"),
        Pair(28, "Condylostoma Nuclear Code"),
        Pair(29, "Mesodinium Nuclear Code"),
        Pair(30, "Peritrich Nuclear Code"),
        Pair(31, "Blastocrithidia Nuclear Code"),
        Pair(33, "Cephalodiscidae Mitochondrial UAA-Tyr Code")
    )

    val rawCodesFromNCBI: List<List<String>> = listOf(
        listOf( /* 1, "The Standard Code" */
            "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
            "---M------**--*----M---------------M----------------------------",
            "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
            "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
            "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
        ),
        listOf( /* 2, "The Vertebrate Mitochondrial Code" */
            "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSS**VVVVAAAADDEEGGGG",
            "----------**--------------------MMMM----------**---M------------",
            "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
            "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
            "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG",
            )
    )
}

fun main() {
    writeCodonMatrix()
    writeHashTables()

}

/**
 * output a "traditional" RNA codon to Amino Acid translation table in AUCG 3 dimensional format
 * See:
 * @link https://web.archive.org/web/20200529000711/http://sites.science.oregonstate.edu/genbio/otherresources/aminoacidtranslation.htm
 *
 */
private fun writeCodonMatrix() {
    val tgc = TheGeneticCodesNCBI()
    val outputMessagesFilePath = "codonMatrix.txt"
    val outFile = File(outputMessagesFilePath)
    val writer = outFile.bufferedWriter()
    writer.append("/** Generated file from TheGeneticCodeNCBI.kt:\n")

    /*
     * fill out a 3D array with the First, Second, and Third letter of the
     * codon as coordinates and the Amino Acid as the cell contents
     */
    val keys = tgc.tableTableNumberToTableName.keys.toList()
    var keyIndex = 0


    for (l in tgc.rawCodesFromNCBI) {

        var theMatrix = mk.d3array(4, 4, 4) { -1 }

        writer.append("Table ${keys[keyIndex]} ${tgc.tableTableNumberToTableName[keys[keyIndex++]]}\n")

        val codon = "ATCG"
        for (i in 0 until l[0].length) {
            theMatrix[
                codon.indexOf(l[2][i]),
                codon.indexOf(l[3][i]),
                codon.indexOf(l[4][i])]= l[0][i].code

        }
        writer.append("  second letter is:\n")
        writer.append("  -A-     -U-     -C-     -G-\n")
        for (leftSide in 0..3) {
            for (rightSide in 0..3) {

                for (top in 0..3) {
                    // left side prefix
                    if (top == 0 && rightSide == 0) {
                        writer.append("${codon[leftSide].dnaToRna()} ")
                    } else {
                        writer.append("  ")
                    }
                    val l1 = codon[leftSide].dnaToRna()
                    val l2 = codon[top].dnaToRna()
                    val l3 = codon[rightSide].dnaToRna()

                    writer.append(
                        "$l1$l2$l3:${theMatrix[leftSide, top, rightSide].toChar()} "
                    )
                }
                writer.append("(${codon[rightSide].dnaToRna()})\n")
            }
        }
    }
    writer.close()
}

private fun writeHashTables() {
    val tgc = TheGeneticCodesNCBI()
    val outputMessagesFilePath = "codonTables.txt"
    val outFile = File(outputMessagesFilePath)
    val writer = outFile.bufferedWriter()
    writer.append("Generated file from TheGeneticCodeNCBI.kt:\n")

    writer.append("val codonTables: MutableList<HashMap<String, Char>> = mutableListOf(\n")

    val keys = tgc.tableTableNumberToTableName.keys.toList()
    var keyIndex = 0

    for (l in tgc.rawCodesFromNCBI) {
        writer.append("hashMapOf(  /* Table ${keys[keyIndex]} ${tgc.tableTableNumberToTableName[keys[keyIndex++]]} */\n")
        var doComma = ","
        for (i in 0 until l[0].length) {
            val str = StringBuilder()
            val codon = str.append(l[2][i], l[3][i], l[4][i]).toString()
            val aminoAcid = l[0][i]
            if (i == l[0].length - 1) {
                doComma = ""
            }
            writer.append("Pair(\"$codon\", \'$aminoAcid\')$doComma\n")
        }
        writer.append("),\n")
    }
    writer.close()
}