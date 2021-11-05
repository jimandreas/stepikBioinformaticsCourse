@file:Suppress("unused", "UseWithIndex", "SameParameterValue")

package tables

import algorithms.dnaToRna
import org.jetbrains.kotlinx.multik.api.d3array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import java.io.BufferedWriter
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
        listOf(
            /* 2, "The Vertebrate Mitochondrial Code" */
            "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSS**VVVVAAAADDEEGGGG",
            "----------**--------------------MMMM----------**---M------------",
            "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
            "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
            "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
        ),

listOf( /* 3, "The Yeast Mitochondrial Code" */
    "FFLLSSSSYY**CCWWTTTTPPPPHHQQRRRRIIMMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "----------**----------------------MM---------------M------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 4, "The Mold, Protozoan, and Coelenterate Mitochondrial Code and the Mycoplasma/Spiroplasma Code" */
    "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "--MM------**-------M------------MMMM---------------M------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
            listOf( /* 5, "The Invertebrate Mitochondrial Code" */
                "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSSSSVVVVAAAADDEEGGGG",
                "---M------**--------------------MMMM---------------M------------",
                "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
                "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
                "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
            ),
listOf( /* 6, "The Ciliate, Dasycladacean and Hexamita Nuclear Code" */
                    "FFLLSSSSYYQQCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
        "--------------*--------------------M----------------------------",
        "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
        "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
        "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
    ),
listOf( /* 9, "The Echinoderm and Flatworm Mitochondrial Code" */
    "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNNKSSSSVVVVAAAADDEEGGGG",
    "----------**-----------------------M---------------M------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 10, "The Euplotid Nuclear Code" */
    "FFLLSSSSYY**CCCWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "----------**-----------------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 11, "The Bacterial, Archaeal and Plant Plastid Code" */
    "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "---M------**--*----M------------MMMM---------------M------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 12, "The Alternative Yeast Nuclear Code" */
    "FFLLSSSSYY**CC*WLLLSPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "----------**--*----M---------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 13, "The Ascidian Mitochondrial Code" */
    "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSSGGVVVVAAAADDEEGGGG",
    "---M------**----------------------MM---------------M------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 14, "The Alternative Flatworm Mitochondrial Code" */
    "FFLLSSSSYYY*CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNNKSSSSVVVVAAAADDEEGGGG",
    "-----------*-----------------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 16, "Chlorophycean Mitochondrial Code" */
    "FFLLSSSSYY*LCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "----------*---*--------------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 21, "Trematode Mitochondrial Code" */
    "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNNKSSSSVVVVAAAADDEEGGGG",
    "----------**-----------------------M---------------M------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 22, "Scenedesmus obliquus Mitochondrial Code" */
    "FFLLSS*SYY*LCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "------*---*---*--------------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 23, "Thraustochytrium Mitochondrial Code" */
    "FF*LSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "--*-------**--*-----------------M--M---------------M------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 24, "Rhabdopleuridae Mitochondrial Code" */
    "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSSKVVVVAAAADDEEGGGG",
    "---M------**-------M---------------M---------------M------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 25, "Candidate Division SR1 and Gracilibacteria Code" */
    "FFLLSSSSYY**CCGWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "---M------**-----------------------M---------------M------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 26, "Pachysolen tannophilus Nuclear Code" */
    "FFLLSSSSYY**CC*WLLLAPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "----------**--*----M---------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 27, "Karyorelict Nuclear Code" */
    "FFLLSSSSYYQQCCWWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "--------------*--------------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 28, "Condylostoma Nuclear Code" */
    "FFLLSSSSYYQQCCWWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "----------**--*--------------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 29, "Mesodinium Nuclear Code" */
    "FFLLSSSSYYYYCC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "--------------*--------------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 30, "Peritrich Nuclear Code" */
    "FFLLSSSSYYEECC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "--------------*--------------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 31, "Blastocrithidia Nuclear Code" */
    "FFLLSSSSYYEECCWWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG",
    "----------**-----------------------M----------------------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
),
listOf( /* 33, "Cephalodiscidae Mitochondrial UAA-Tyr Code" */
    "FFLLSSSSYYY*CCWWLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSSKVVVVAAAADDEEGGGG",
    "---M-------*-------M---------------M---------------M------------",
    "TTTTTTTTTTTTTTTTCCCCCCCCCCCCCCCCAAAAAAAAAAAAAAAAGGGGGGGGGGGGGGGG",
    "TTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGGTTTTCCCCAAAAGGGG",
    "TCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAGTCAG"
    )
)

    val aminoToNameHash: HashMap<Char, String> = hashMapOf(
        Pair('A', "alanine"),
        Pair('B', "asparagine"),
        Pair('C', "cystine"),
        Pair('D', "aspartate"),
        Pair('E', "glutamate  "),
        Pair('F', "phenylalanine"),
        Pair('G', "glycine"),
        Pair('H', "histidine"),
        Pair('I', "isoleucine"),
        Pair('K', "lysine"),
        Pair('L', "leucine"),
        Pair('M', "methionine"),
        Pair('N', "asparagine "),
        Pair('P', "proline"),
        Pair('Q', "glutamine"),
        Pair('R', "arginine"),
        Pair('S', "serine"),
        Pair('T', "threonine"),
        Pair('V', "valine"),
        Pair('W', "tryptophan"),
        Pair('Y', "tyrosine")
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
 * Note: special controls print out the Amino code to Amino name inset at the right side at the start.
 */

private fun writeCodonMatrix() {
    val w = Writer()
    val tgc = TheGeneticCodesNCBI()
    val outputMessagesFilePath = "codonMatrix.txt"
    w.setFile(outputMessagesFilePath)

    // when the inset is in play, add on a amino acid char and name string to the table
    w.listener = {

        val keys = tgc.aminoToNameHash.keys.toList()
        if (it == keys.size-1) {
            w.setTableEnd()
        }
        val aminoChar = keys[it]
        val aminoName = tgc.aminoToNameHash[aminoChar]
        val str = StringBuilder()
        str.append("  $aminoChar: $aminoName").toString()
    }


    w.wrt("/** Generated file from TheGeneticCodeNCBI.kt:\n")

    /*
     * fill out a 3D array with the First, Second, and Third letter of the
     * codon as coordinates and the Amino Acid as the cell contents
     */
    val keys = tgc.tableTableNumberToTableName.keys.toList()
    var keyIndex = 0

    var tableStarted = false

    for (l in tgc.rawCodesFromNCBI) {

        val theMatrix = mk.d3array(4, 4, 4) { -1 }

        w.wrt("\n")
        if (!tableStarted) {
            w.setTableStart()
            tableStarted = true
        }
        w.wrt("Table ${keys[keyIndex]} ${tgc.tableTableNumberToTableName[keys[keyIndex++]]}\n")

        val codon = "ATCG"
        for (i in 0 until l[0].length) {
            theMatrix[
                    codon.indexOf(l[2][i]),
                    codon.indexOf(l[3][i]),
                    codon.indexOf(l[4][i])] = l[0][i].code

        }
        w.wrt("  second letter is:                \n")
        w.wrt("  -A-     -U-     -C-     -G-      \n")
        for (leftSide in 0..3) {
            for (rightSide in 0..3) {

                for (top in 0..3) {
                    // left side prefix
                    if (top == 0 && rightSide == 0) {
                        w.wrt("${codon[leftSide].dnaToRna()} ")
                    } else {
                        w.wrt("  ")
                    }
                    val l1 = codon[leftSide].dnaToRna()
                    val l2 = codon[top].dnaToRna()
                    val l3 = codon[rightSide].dnaToRna()

                    w.wrt(
                        "$l1$l2$l3:${theMatrix[leftSide, top, rightSide].toChar()} "
                    )
                }
                w.wrt("(${codon[rightSide].dnaToRna()})\n")
            }
        }
    }
    w.close()
}

private fun writeHashTables() {
    val w = Writer()
    val tgc = TheGeneticCodesNCBI()
    val outputMessagesFilePath = "codonTables.txt"
    w.setFile(outputMessagesFilePath)
//    val outFile = File(outputMessagesFilePath)
//    val writer = outFile.bufferedWriter()
    w.wrt("Generated file from TheGeneticCodeNCBI.kt:\n")

    w.wrt("val codonTables: MutableList<HashMap<String, Char>> = mutableListOf(\n")

    val keys = tgc.tableTableNumberToTableName.keys.toList()
    var keyIndex = 0

    for (l in tgc.rawCodesFromNCBI) {
        w.wrt("\nhashMapOf(/* Table ${keys[keyIndex]} ${tgc.tableTableNumberToTableName[keys[keyIndex++]]} */\n")
        var doComma = ","
        for (i in 0 until l[0].length) {
            val str = StringBuilder()
            val codon = str.append(l[2][i], l[3][i], l[4][i]).toString()
            val aminoAcid = l[0][i]
            if (i == l[0].length - 1) {
                doComma = ""
            }
            w.wrt("Pair(\"$codon\", \'$aminoAcid\')$doComma\n")
        }
        w.wrt("),\n")
    }
    //writer.close()
    w.close()
}

class Writer {
    private var currentColumn = 0
    private lateinit var outFile: File
    private lateinit var wrt: BufferedWriter
    fun setFile(fn: String) {
        outFile = File(fn)
        wrt = outFile.bufferedWriter()
    }


    fun close() {
        wrt.close()
    }

    private var printTable = false
    private var tableIndex = 0
    fun setTableStart() {
        printTable = true
        tableIndex = 0
    }
    fun setTableEnd() {
        printTable = false
    }

    /**
     * includes code to do call back to a listener to
     * include a right inset in the printout
     */
    fun wrt(s: String) {
        val str = StringBuilder()

        if (printTable && s.contains("\n")) {
            val allButEndline = s.substring(0, s.length - 1)
            currentColumn += allButEndline.length
            val rightInsetString = listener?.invoke(tableIndex++)
            str.append(allButEndline)
            indentTo(50, str)  // place inset at column 50
            str.append(rightInsetString)
            str.append("\n")
        } else {
            str.append(s)
        }
        val toWrite = str.toString()
        if (!toWrite.contains("\n")) {
            currentColumn += toWrite.length
        } else {
            currentColumn = 0
        }
        wrt.append(toWrite)
    }

    private fun indentTo(desiredIndent: Int, str: StringBuilder) {
        while (currentColumn < desiredIndent) {
            str.append(" ")
            currentColumn++
        }
    }

    var listener: ((Int)->String)? = null


}