@file:Suppress("unused")

package util

/**
 * from a [d] list of kmer strings,
 *   produce a map of kmer to kmer connections
 *
 *   Example:
val input = listOf(
"GAGG",
"CAGG",
"GGGG",
"GGGA",
"CAGG",
"AGGG",
"GGAG"
)

output: (as a map of string to list of strings)
AGG -> GGG
CAG -> AGG,AGG
GAG -> AGG
GGA -> GAG
GGG -> GGG,GGA
 */

fun deBruijnGraphFromKmers(d: List<String>): Map<String, List<String>> {

    val strLen = d[0].length
    val resultMap: MutableMap<String, MutableList<String>> = mutableMapOf()

    for (s in d) {
        val prefix = s.substring(0, strLen - 1)
        val suffix = s.substring(1, strLen)
        if (resultMap.containsKey(prefix)) {
            val list = resultMap[prefix]
            list!!.add(suffix)
        } else {
            resultMap.putIfAbsent(prefix, mutableListOf(suffix))
        }
    }
    return (resultMap.toSortedMap())
}

/**
 * for a [input] map of kmer to kmer list, iterate over the
 * list and output a string with the representation.
 */
fun deBruijnDirectedGraphConversion(input: Map<String, List<String>>): String {

    val buf = StringBuilder()
    for (entry in input) {
        buf.append("${entry.key} -> ")
        val theList = entry.value
        buf.append(theList.joinToString(separator = ","))
        buf.append("\n")
    }
    return buf.toString()
}

/**
 * for a [s] string of kmers (e.g. text from a problem description -
 * return a list of parsed lines.
 * Assumptions: the string is a trimmed list of just kmers - no whitespace
 */
fun parseKmerString(s: String): List<String> {
    val reader = s.reader()
    val lines = reader.readLines()

    val stringList: MutableList<String> = mutableListOf()
    for (line in lines) {
        stringList.add(line)
    }
    return stringList

}