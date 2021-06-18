package util

/**
 *
 */
fun reverseComplement(dnaString: String): String {
    var reverse = ""

    for (i in dnaString.length - 1 downTo 0) {
        when (dnaString[i]) {
            'A' -> reverse += 'T'
            'C' -> reverse +='G'
            'G' -> reverse +='C'
            'T' -> reverse +='A'
            else -> reverse += ' '
        }
    }
    return reverse
}