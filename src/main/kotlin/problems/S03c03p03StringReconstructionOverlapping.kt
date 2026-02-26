@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

package problems

/**
 * @link:
 * rosalind: http://rosalind.info/problems/ba3b/
 *
String Reconstruction practice problem

 */


fun main() {


    val stringList = """
        ACCGA
        CCGAA
        CGAAG
        GAAGC
        AAGCT
    """.trimIndent().lines()

//    val reader = string.reader()
//    val stringList = reader.readLines()

    val outList: MutableList<String> = mutableListOf()

    var baseString = stringList[0]
    val strLen = baseString.length
    outList.add(stringList[0])
    for (i in 1 until stringList.size) {
        val str = stringList[i]
        if (baseString.substring(1, strLen) == str.substring(0, strLen - 1)) {
            outList.add(str[strLen - 1].toString()) // add last character in string
        } else {
            println("OOPSIE")
        }
        baseString = str
    }
    for (s in outList) {
        print(s)
    }
    print("\n")

    // success!
}



//    val outputMessagesFilePath = "stringsComposition.txt"
//
//    val outFile = File(outputMessagesFilePath)
//    val writer = outFile.bufferedWriter()

