package util

import java.lang.StringBuilder

fun pathToGenome(stringList: List<String>): String {

    var baseString = stringList[0]
    val strLen = baseString.length

    val outString = StringBuilder()

    outString.append(stringList[0])
    for (i in 1 until stringList.size) {
        val str = stringList[i]
        if (str.length == 1) {
            outString.append(str)
        } else {
            if (baseString.substring(1, strLen) == str.substring(0, strLen - 1)) {
                outString.append(str[strLen - 1].toString()) // add last character in string
            } else {
                println("OOPSIE")
            }
        }
        baseString = str
    }
    return outString.toString()
}