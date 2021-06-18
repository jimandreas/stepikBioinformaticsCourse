@file:Suppress("UnnecessaryVariable", "UNUSED_VARIABLE")

// 1.2 Hidden Messages in the Replication Origin

// Code Challenge: Solve the Frequent Words Problem.
//
//    Input: A string Text and an integer k.
//    Output: All most frequent k-mers in Text.

import java.lang.Integer.max



fun main() {

    val genome1 = "CGGAAGCGAGATTCGCGTGGCGTGATTCCGGCGGGCGTGGAGAAGCGAGATTCATTCAAGCCGGGAGGCGTGGCGTGGCGTGGCGTGCGGATTCAAGCCGGCGGGCGTGATTCGAGCGGCGGATTCGAGATTCCGGGCGTGCGGGCGTGAAGCGCGTGGAGGAGGCGTGGCGTGCGGGAGGAGAAGCGAGAAGCCGGATTCAAGCAAGCATTCCGGCGGGAGATTCGCGTGGAGGCGTGGAGGCGTGGAGGCGTGCGGCGGGAGATTCAAGCCGGATTCGCGTGGAGAAGCGAGAAGCGCGTGCGGAAGCGAGGAGGAGAAGCATTCGCGTGATTCCGGGAGATTCAAGCATTCGCGTGCGGCGGGAGATTCAAGCGAGGAGGCGTGAAGCAAGCAAGCAAGCGCGTGGCGTGCGGCGGGAGAAGCAAGCGCGTGATTCGAGCGGGCGTGCGGAAGCGAGCGG"
    val runLength1 = 12

    val genome2 = "ACGTTGCATGTCGCATGATGCATGAGAGCT"
    val runLength2 = 4

    val genomeClass = "GATCTCCCCAAAACCTAAATCCAAGATCTCCCCAAAACCTAAATCCAAAAATCCAAATTCTATTATTCTATTATTCTATTCAAAACCTGATCTCCCAATAGAAAATAGAAAATAGAAATTCTATTGATCTCCCATTCTATTAAATCCAAAATAGAACAAAACCTATTCTATTCAAAACCTAATAGAAAATAGAAAATAGAACAAAACCTGATCTCCCATTCTATTAAATCCAAGATCTCCCAAATCCAAAAATCCAACAAAACCTAATAGAAGATCTCCCATTCTATTAAATCCAAGATCTCCCATTCTATTAATAGAAAATAGAAATTCTATTAAATCCAAGATCTCCCAATAGAAGATCTCCCATTCTATTGATCTCCCAATAGAAAATAGAAGATCTCCCATTCTATTAAATCCAAAATAGAAAAATCCAAAATAGAAAAATCCAACAAAACCTGATCTCCCATTCTATTATTCTATTATTCTATTATTCTATTGATCTCCCATTCTATTAATAGAAATTCTATTATTCTATTCAAAACCTAATAGAAATTCTATTATTCTATTAATAGAAAATAGAAAATAGAAAAATCCAAAAATCCAAAATAGAAGATCTCCCGATCTCCCCAAAACCTGATCTCCCCAAAACCTAATAGAACAAAACCTAAATCCAACAAAACCTGATCTCCCATTCTATTATTCTATTAAATCCAACAAAACCTAAATCCAAAAATCCAAAATAGAACAAAACCTAATAGAAAAATCCAAATTCTATTATTCTATTCAAAACCTCAAAACCTAAATCCAAATTCTATTAATAGAAAATAGAAGATCTCCCCAAAACCTAAATCCAAGATCTCCC"
    val runLength3 = 11

    val genomeToTest = genomeClass
    val runLengthToTest = runLength3

    val returnedValue = scanForMatchesOfLength(genomeToTest, runLengthToTest)
    println(returnedValue)
}

/**
 *    Input: A string Text and an integer k. (kmerLength)
 *    Output: All most frequent k-mers in Text.
 */
fun scanForMatchesOfLength(genome: String, runLength: Int): String {
    val map: MutableMap<String, Int> = HashMap()
    var maxValue = 0

    for (i in 0..genome.length-runLength) {
        val substring = genome.substring(i, i+runLength)
        increment(map, substring)
        maxValue = max(maxValue, map[substring] ?:0)
    }

    val newMap = map.toSortedMap(compareBy<String> { it.length }.thenBy { it })

//    println(newMap.count {
//        it.value == maxValue
//    })

    var retStr = ""
    for (i in newMap) {
        if (i.value == maxValue) {
            retStr = "$retStr${i.key} "
        }
    }
    return retStr
}

// code credit: https://www.techiedelight.com/increment-value-map-kotlin/

/**
 * add the [key] to the [map].   Increment the count of they key in the map.
 * @param map - a mutable map of type K
 * @param key - they key of type K to add to the map
 * @link https://www.techiedelight.com/increment-value-map-kotlin/
 */
private fun <K> increment(map: MutableMap<K, Int>, key: K) {
    map.putIfAbsent(key, 0)
    map[key] = map[key]!! + 1
}