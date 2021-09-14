@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

package problems

/**
 * @link: https://stepik.org/lesson/240255/step/3?unit=212601
 *
 *
String Overlap Graph problem

Code Challenge: Solve the Overlap Graph Problem (restated below).

Input: A collection Patterns of k-mers.
Output: The overlap graph Overlap(Patterns), in the form of an adjacency list.
(You may return the nodes and their edges in any order.)

 */

fun main() {

    val string = """
ATGCG
GCATG
CATGC
AGGCA
GGCAT
    """.trimIndent()


    val reader = string.reader()
    val stringList = reader.readLines()
    for (baseString in stringList) {
        val outList: MutableList<String> = mutableListOf()
        val strLen = baseString.length
        // examine all strings for a match
        for (str in stringList) {
            if (baseString != str) {
                if (baseString.substring(1, strLen) == str.substring(0, strLen - 1)) {
                    outList.add(str)
                }
            }
        }
        if (outList.isNotEmpty()) {
            print("$baseString -> ")
            print(outList.joinToString(separator = ","))
            print("\n")
        }
    }
    // correct!
}


//    val outputMessagesFilePath = "stringsComposition.txt"
//
//    val outFile = File(outputMessagesFilePath)
//    val writer = outFile.bufferedWriter()

