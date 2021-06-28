package util

import java.lang.StringBuilder

/**
 * from a [d] list of kmer strings,
 *   produce a map of kmer to kmer connections
 */
fun deBruijnGraphFromKmers(d: List<String>): Map<String, List<String>> {

    val strLen = d[0].length
    val resultMap : MutableMap<String, MutableList<String>> = mutableMapOf()

    for (s in d) {
        val prefix = s.substring(0, strLen-1)
        val suffix = s.substring(1, strLen)
        if (resultMap.containsKey(prefix)) {
            val list = resultMap[prefix]
            list!!.add(suffix)
        } else {
            resultMap.putIfAbsent(prefix, mutableListOf(suffix))
        }
    }
    return( resultMap.toSortedMap())
}

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