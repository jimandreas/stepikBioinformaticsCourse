@file:Suppress("unused", "UnnecessaryVariable", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE")

package algorithms


val rnaCodonToAminoHashMap: HashMap<String, String> = hashMapOf(
    Pair("AAA", "K"),
    Pair("AAC", "N"),
    Pair("AAG", "K"),
    Pair("AAU", "N"),
    Pair("ACA", "T"),
    Pair("ACC", "T"),
    Pair("ACG", "T"),
    Pair("ACU", "T"),
    Pair("AGA", "R"),
    Pair("AGC", "S"),
    Pair("AGG", "R"),
    Pair("AGU", "S"),
    Pair("AUA", "I"),
    Pair("AUC", "I"),
    Pair("AUG", "M"),
    Pair("AUU", "I"),
    Pair("CAA", "Q"),
    Pair("CAC", "H"),
    Pair("CAG", "Q"),
    Pair("CAU", "H"),
    Pair("CCA", "P"),
    Pair("CCC", "P"),
    Pair("CCG", "P"),
    Pair("CCU", "P"),
    Pair("CGA", "R"),
    Pair("CGC", "R"),
    Pair("CGG", "R"),
    Pair("CGU", "R"),
    Pair("CUA", "L"),
    Pair("CUC", "L"),
    Pair("CUG", "L"),
    Pair("CUU", "L"),
    Pair("GAA", "E"),
    Pair("GAC", "D"),
    Pair("GAG", "E"),
    Pair("GAU", "D"),
    Pair("GCA", "A"),
    Pair("GCC", "A"),
    Pair("GCG", "A"),
    Pair("GCU", "A"),
    Pair("GGA", "G"),
    Pair("GGC", "G"),
    Pair("GGG", "G"),
    Pair("GGU", "G"),
    Pair("GUA", "V"),
    Pair("GUC", "V"),
    Pair("GUG", "V"),
    Pair("GUU", "V"),
    Pair("UAA", ""),
    Pair("UAC", "Y"),
    Pair("UAG", ""),
    Pair("UAU", "Y"),
    Pair("UCA", "S"),
    Pair("UCC", "S"),
    Pair("UCG", "S"),
    Pair("UCU", "S"),
    Pair("UGA", ""),
    Pair("UGC", "C"),
    Pair("UGG", "W"),
    Pair("UGU", "C"),
    Pair("UUA", "L"),
    Pair("UUC", "F"),
    Pair("UUG", "L"),
    Pair("UUU", "F")
)

val dnaCodonToAminoHashMap: HashMap<String, String> = hashMapOf(
    Pair("AAA", "K"),
    Pair("AAC", "N"),
    Pair("AAG", "K"),
    Pair("AAT", "N"),
    Pair("ACA", "T"),
    Pair("ACC", "T"),
    Pair("ACG", "T"),
    Pair("ACT", "T"),
    Pair("AGA", "R"),
    Pair("AGC", "S"),
    Pair("AGG", "R"),
    Pair("AGT", "S"),
    Pair("ATA", "I"),
    Pair("ATC", "I"),
    Pair("ATG", "M"),
    Pair("ATT", "I"),
    Pair("CAA", "Q"),
    Pair("CAC", "H"),
    Pair("CAG", "Q"),
    Pair("CAT", "H"),
    Pair("CCA", "P"),
    Pair("CCC", "P"),
    Pair("CCG", "P"),
    Pair("CCT", "P"),
    Pair("CGA", "R"),
    Pair("CGC", "R"),
    Pair("CGG", "R"),
    Pair("CGT", "R"),
    Pair("CTA", "L"),
    Pair("CTC", "L"),
    Pair("CTG", "L"),
    Pair("CTT", "L"),
    Pair("GAA", "E"),
    Pair("GAC", "D"),
    Pair("GAG", "E"),
    Pair("GAT", "D"),
    Pair("GCA", "A"),
    Pair("GCC", "A"),
    Pair("GCG", "A"),
    Pair("GCT", "A"),
    Pair("GGA", "G"),
    Pair("GGC", "G"),
    Pair("GGG", "G"),
    Pair("GGT", "G"),
    Pair("GTA", "V"),
    Pair("GTC", "V"),
    Pair("GTG", "V"),
    Pair("GTT", "V"),
    Pair("TAA", ""),
    Pair("TAC", "Y"),
    Pair("TAG", ""),
    Pair("TAT", "Y"),
    Pair("TCA", "S"),
    Pair("TCC", "S"),
    Pair("TCG", "S"),
    Pair("TCT", "S"),
    Pair("TGA", ""),
    Pair("TGC", "C"),
    Pair("TGG", "W"),
    Pair("TGT", "C"),
    Pair("TTA", "L"),
    Pair("TTC", "F"),
    Pair("TTG", "L"),
    Pair("TTT", "F")
)

data class AminoAcids(val code: Char, val abbreviation: String, val numCodons: Int, val aminoacid: String)

val aminoAcidToRnaCodonList: HashMap<String, List<String>> = hashMapOf(
    Pair("*", listOf("UAA", "UAG", "UGA")), // stop codons
    Pair("A", listOf("GCA", "GCC", "GCG", "GCU")),
    Pair("C", listOf("UGU", "UGC")),
    Pair("D", listOf("GAC", "GAU")),
    Pair("E", listOf("GAA", "GAG")),
    Pair("F", listOf("UUC", "UUU")),
    Pair("G", listOf("GGA", "GGC", "GGG", "GGU")),
    Pair("H", listOf("CAC", "CAU")),
    Pair("I", listOf("AUA", "AUC", "AUU")),
    Pair("K", listOf("AAA", "AAG")),
    Pair("L", listOf("CUA", "CUC", "CUG", "UUA", "CUU", "UUG")),
    Pair("M", listOf("AUG")),
    Pair("N", listOf("AAC", "AAU")),
    Pair("P", listOf("CCA", "CCC", "CCG", "CCU")),
    Pair("Q", listOf("CAA", "CAG")),
    Pair("R", listOf("AGA", "CGA", "CGC", "AGG", "CGG", "CGU")),
    Pair("S", listOf("UCU", "AGC", "AGU", "UCA", "UCC", "UCG")),
    Pair("T", listOf("ACA", "ACC", "ACG", "ACU")),
    Pair("V", listOf("GUA", "GUC", "GUG", "GUU")),
    Pair("W", listOf("UGG")),
    Pair("Y", listOf("UAC", "UAU"))
)

val aminoAcidToDnaCodonList: HashMap<String, List<String>> = hashMapOf(
    Pair("*", listOf("TAA", "TAG", "TGA")), // stop codons
    Pair("A", listOf("GCA", "GCC", "GCG", "GCT")),
    Pair("C", listOf("TGT", "TGC")),
    Pair("D", listOf("GAC", "GAT")),
    Pair("E", listOf("GAA", "GAG")),
    Pair("F", listOf("TTC", "TTT")),
    Pair("G", listOf("GGA", "GGC", "GGG", "GGT")),
    Pair("H", listOf("CAC", "CAT")),
    Pair("I", listOf("ATA", "ATC", "ATT")),
    Pair("K", listOf("AAA", "AAG")),
    Pair("L", listOf("CTA", "CTC", "CTG", "TTA", "CTT", "TTG")),
    Pair("M", listOf("ATG")),
    Pair("N", listOf("AAC", "AAT")),
    Pair("P", listOf("CCA", "CCC", "CCG", "CCT")),
    Pair("Q", listOf("CAA", "CAG")),
    Pair("R", listOf("AGA", "CGA", "CGC", "AGG", "CGG", "CGT")),
    Pair("S", listOf("TCT", "AGC", "AGT", "TCA", "TCC", "TCG")),
    Pair("T", listOf("ACA", "ACC", "ACG", "ACT")),
    Pair("V", listOf("GTA", "GTC", "GTG", "GTT")),
    Pair("W", listOf("TGG")),
    Pair("Y", listOf("TAC", "TAT"))
)

val aminoAcidsTable = listOf(
    AminoAcids('A', "ALA", 4, "alanine"),
    AminoAcids('B', "ASX", 2, "asparagine"),
    AminoAcids('C', "CYS", 2, "cystine"),
    AminoAcids('D', "ASP", 2, "aspartate"),
    AminoAcids('E', "GLU", 2, "glutamate  "),
    AminoAcids('F', "PHE", 2, "phenylalanine"),
    AminoAcids('G', "GLY", 4, "glycine"),
    AminoAcids('H', "HIS", 2, "histidine"),
    AminoAcids('I', "ILE", 3, "isoleucine"),
    AminoAcids('K', "LYS", 2, "lysine"),
    AminoAcids('L', "LEU", 6, "leucine"),
    AminoAcids('M', "MET", 1, "methionine"),
    AminoAcids('N', "ASN", 2, "asparagine "),
    AminoAcids('P', "PRO", 4, "proline"),
    AminoAcids('Q', "GLN", 2, "glutamine"),
    AminoAcids('R', "ARG", 4, "arginine"),
    AminoAcids('S', "SER", 2, "serine"),
    AminoAcids('T', "THR", 4, "threonine"),
    AminoAcids('V', "VAl", 4, "valine"),
    AminoAcids('W', "TRP", 1, "tryptophan"),
    AminoAcids('Y', "TYR", 2, "tyrosine")
)


val baseValue = aminoAcidToDaltonHashMap['G']


fun translateRnaCodonStringToAminoAcidString(codonString: String): String {

    if (codonString.length % 3 != 0) {
        println("FAIL: codonString length is not multiple of 3 ($codonString.length: $codonString")
        return ""
    }

    val alist: MutableList<String> = mutableListOf()

    val chunkedString = codonString.chunked(3)

    for (item in chunkedString) {
        val amino = rnaCodonToAminoHashMap[item]
        if (amino == null) {
            println("no amino for $item!!")
        } else {
            alist.add(amino)
        }
    }
    val retVal = alist.joinToString(separator = "")
    return retVal

}

fun translateDnaCodonStringToAminoAcidString(codonString: String, stopAtStopCodon: Boolean = false): String {

    if (codonString.length % 3 != 0) {
        println("FAIL: codonString length is not multiple of 3 ($codonString.length: $codonString")
        return ""
    }

    val alist: MutableList<String> = mutableListOf()

    val chunkedString = codonString.chunked(3)

    var foundStopCodon = false
    for (item in chunkedString) {
        if (stopAtStopCodon) {
            when (item) {
                "TAA", "TAG", "TGA" -> {
                    foundStopCodon = true
                    break
                }
            }
        }
        val amino = dnaCodonToAminoHashMap[item]
        if (amino == null) {
            println("no amino for $item!!")
        } else {
            alist.add(amino)
        }
    }
    val retVal = alist.joinToString(separator = "")
    return retVal

}

fun indexSearchDnaStringAndPeptide(dnaString: String, peptide: String): Int {

    var idx = -1
    for (i in 0 until peptide.length) {

        val testString = dnaString.substring(i * 3, dnaString.length)
        val emittedProtein = translateDnaCodonStringToAminoAcidString(testString, stopAtStopCodon = true)
        if (emittedProtein.contains(peptide)) {
            idx = i
            break
        }
    }
    if (idx != -1) {
        println("success at $idx")
    }

    return idx+1
}
