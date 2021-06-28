@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

import util.deBruijnGraphFromKmers
import java.io.File

/**
 * @link: https://stepik.org/lesson/240257/step/6?unit=212603
 *
Code Challenge: Solve the De Bruijn Graph from a String Problem.

Input: An integer k and a string Text.
Output: DeBruijnk(Text), in the form of an adjacency list.

 */

fun main() {

    val input = listOf(
        "GAGG",
        "CAGG",
        "GGGG",
        "GGGA",
        "CAGG",
        "AGGG",
        "GGAG"
        )

    val output = deBruijnGraphFromKmers(input)

    val outputMessagesFilePath = "stringsDeBruijnGraph.txt"

    val outFile = File(outputMessagesFilePath)
    val writer = outFile.bufferedWriter()

    for (entry in output) {
        print("${entry.key} -> ")
        writer.write("${entry.key} -> ")
        val theList = entry.value
        print(theList.joinToString(separator = ","))
        writer.write(theList.joinToString(separator = ","))
        print("\n")
        writer.write("\n")
    }

    writer.close()

}

// moved to util folder
/*fun deBruijnGraphFromKmers(d: List<String>): Map<String, List<String>> {

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
}*/


//    val outputMessagesFilePath = "stringsComposition.txt"
//
//    val outFile = File(outputMessagesFilePath)
//    val writer = outFile.bufferedWriter()

