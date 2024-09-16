@file:Suppress(
    "unused", "UNUSED_PARAMETER", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls",
    "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE",  "UnnecessaryVariable", "CanBeVal",
    "KotlinConstantConditions", "KotlinConstantConditions", "KotlinConstantConditions", "KotlinConstantConditions"
)

package tables

import algorithms.reverseComplement

fun codonUtilsFindLongestPeptide(
    codonString: String,
    isDnaString: Boolean = true,
    scanReverseComplement: Boolean = true,
    startCodon: String = "ATG",
    table: Int = 0 // corresponds to canonical table 1
): String? {

    val longestORF = codonUtilsFindLongestOpenReadingFrame(
        codonString,
        isDnaString,
        scanReverseComplement,
        startCodon,
        table
    )


    if (longestORF != null) {
        val peptide = codonUtilsTranslate(
            longestORF,
            isDnaString = true,
            skipStopCodons = false,
            table
        )
        return peptide
    }
    return null
}

/**
 * scan the [codonString] and accumulate
 * the longest string from the [startCodon]
 * to a "*" (stop codon) in the [table].
 * If [scanReverseComplement] is true, also
 * scan the reverse complement string.
 * Return the longest OpenReadingFrame found.
 */

fun codonUtilsFindLongestOpenReadingFrame(
    codonString: String,
    isDnaString: Boolean = true,
    scanReverseComplement: Boolean = true,
    startCodon: String = "ATG",
    table: Int = 0 // corresponds to canonical table 1
): String? {
    var debug = false
    val candidates: MutableList<String> = mutableListOf()

    var dnaString = codonString
    if (!isDnaString) {
        dnaString = codonUtilsConvertRnaToDna(codonString)
    }

    doScan(candidates, dnaString, isDnaString, startCodon, table)

    if (scanReverseComplement) {
        dnaString = reverseComplement(dnaString)
        doScan(candidates, dnaString, isDnaString, startCodon, table)
    }

    if (debug) {
        val peptideList : MutableList<String> = mutableListOf()
        for (s in candidates) {
            val peptide = codonUtilsTranslate(
                s,
                isDnaString = true,
                skipStopCodons = false,
                table
            )
            peptideList.add(peptide)
        }
        println(peptideList)
        //println(peptideList.joinToString("\n"))
    }

    return candidates.maxByOrNull { it.length }
}

private fun doScan(
    candidates: MutableList<String>,
    codonString: String,
    isDnaString: Boolean = true,
    startCodon: String = "ATG",
    table: Int = 0 // corresponds to canonical table 1
) {
    val chm = CodonHashMaps()
    val t = chm.codonTables[table]


    for (i in 0 until codonString.length-3) {
        if (codonString.substring(i, i + 3) == startCodon) {
            /*
             * found a possible start codon, scan for a stop codon
             */
            //for (j in i + 3 until codonString.length-3) {
            var j = i+3
            while (j < codonString.length-3) {
                val codon = codonString.substring(j, j + 3)
                val isStop = t[codon]
                if (isStop == '*') {
                    if (j > i + 6) {
                        val orf = codonString.substring(i, j)
                        candidates.add(orf)
                    }
                    break
                }
                j += 3
            }
        }
    }
}

/**
 * given a [codonString] that is either
 * an RNA or DNA string (as given by [isDnaString])
 * return a translated protein string.
 * The codon translation table number
 * is given by [table] and defaults
 * to the classic Table 1.
 */
fun codonUtilsTranslate(
    codonString: String,
    isDnaString: Boolean = true,
    skipStopCodons: Boolean = true,
    table: Int = 0 // corresponds to canonical table 1
): String {

    val chm = CodonHashMaps()
    val t = chm.codonTables[table]

    if (codonString.length % 3 != 0) {
        println("FAIL: codonString length is not multiple of 3 ($codonString.length: $codonString")
        return ""
    }

    val str = StringBuilder()

    var dnaString = codonString
    if (!isDnaString) {
        dnaString = codonUtilsConvertRnaToDna(codonString)
    }

    val chunkedString = dnaString.chunked(3)

    for (item in chunkedString) {
        val amino = t[item]
        if (amino == null) {
            println("no amino for $item!!")
        } else if (amino == '*' && !skipStopCodons) {
            break
        } else if (amino == '*' && skipStopCodons) {
            continue
        } else {
            str.append(amino)
        }
    }
    return str.toString()

}

fun codonUtilsConvertRnaToDna(rnaString: String): String {
    val str = StringBuilder()
    for (c in rnaString) {
        str.append(
            when (c) {
                'U' -> 'T'
                else -> c
            }
        )
    }
    return str.toString()
}

fun codonUtilsConvertDnaToRna(rnaString: String): String {
    val str = StringBuilder()
    for (c in rnaString) {
        str.append(
            when (c) {
                'T' -> 'U'
                else -> c
            }
        )
    }
    return str.toString()
}