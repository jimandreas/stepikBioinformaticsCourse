// 1.2 Hidden Messages in the Replication Origin

// Code Challenge: Solve the Frequent Words Problem.

import java.lang.Integer.max

const val genome1 = "CGGAAGCGAGATTCGCGTGGCGTGATTCCGGCGGGCGTGGAGAAGCGAGATTCATTCAAGCCGGGAGGCGTGGCGTGGCGTGGCGTGCGGATTCAAGCCGGCGGGCGTGATTCGAGCGGCGGATTCGAGATTCCGGGCGTGCGGGCGTGAAGCGCGTGGAGGAGGCGTGGCGTGCGGGAGGAGAAGCGAGAAGCCGGATTCAAGCAAGCATTCCGGCGGGAGATTCGCGTGGAGGCGTGGAGGCGTGGAGGCGTGCGGCGGGAGATTCAAGCCGGATTCGCGTGGAGAAGCGAGAAGCGCGTGCGGAAGCGAGGAGGAGAAGCATTCGCGTGATTCCGGGAGATTCAAGCATTCGCGTGCGGCGGGAGATTCAAGCGAGGAGGCGTGAAGCAAGCAAGCAAGCGCGTGGCGTGCGGCGGGAGAAGCAAGCGCGTGATTCGAGCGGGCGTGCGGAAGCGAGCGG"
const val runLength1 = 12

const val genome2 = "ACGTTGCATGTCGCATGATGCATGAGAGCT"
const val runLength2 = 4

const val genome = genome1
const val runLength = runLength1

// code credit: https://www.techiedelight.com/increment-value-map-kotlin/

fun <K> increment(map: MutableMap<K, Int>, key: K) {
    map.putIfAbsent(key, 0)
    map[key] = map[key]!! + 1
}

fun main() {
    val returnedValue = scanForMatchesOfLength(genome, runLength)
    println(returnedValue)
}

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