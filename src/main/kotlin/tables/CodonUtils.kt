@file:Suppress("unused")

package tables

fun codonUtilsTranslate(
    codonString: String,
    isDnaString: Boolean = true,
    noStopCodons: Boolean = true,
    table: Int = 1
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
        } else if (amino == '*' && noStopCodons) {
            continue
        } else {
            str.append(amino)
        }
    }
    return str.toString()

}

fun codonUtilsConvertRnaToDna(rnaString: String) : String {
    val str = StringBuilder()
    for (c in rnaString) {
        str.append(
        when (c) {
            'U' -> 'T'
            else -> c
        })
    }
    return str.toString()
}

fun codonUtilsConvertDnaToRna(rnaString: String) : String {
    val str = StringBuilder()
    for (c in rnaString) {
        str.append(
            when (c) {
                'T' -> 'U'
                else -> c
            })
    }
    return str.toString()
}