@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

package problems

import algorithms.deBruijnGraphFromKmers
import java.io.File

/**
 * @link: https://stepik.org/lesson/240257/step/6?unit=212603
 *
Code Challenge: Solve the De Bruijn Graph from a String Problem.

Input: An integer k and a string Text.
Output: DeBruijnk(Text), in the form of an adjacency list.

 */

fun main() {

    val string = """
        GAGG
        CAGG
        GGGG
        GGGA
        CAGG
        AGGG
        GGAG
    """.trimIndent()


    /*
output:
AGG -> GGG
CAG -> AGG,AGG
GAG -> AGG
GGA -> GAG
GGG -> GGG,GGA
 */

    val reader = string.reader()
    val stringList = reader.readLines()
    val output = deBruijnGraphFromKmers(stringList)

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


