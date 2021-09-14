@file:Suppress("LiftReturnOrAssignment")

package algorithms

/**
 *
 */
fun reverseComplement(dnaString: String): String {
    val reverse = StringBuilder()

    for (i in dnaString.length - 1 downTo 0) {
        when (dnaString[i]) {
            'A' -> reverse.append('T')
            'C' -> reverse.append('G')
            'G' -> reverse.append('C')
            'T' -> reverse.append('A')
            else -> reverse.append(' ')
        }
    }
    return reverse.toString()
}