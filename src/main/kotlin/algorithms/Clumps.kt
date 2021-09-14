@file:Suppress("UnnecessaryVariable")

package algorithms


/**
 * Returns a new character sequence that is a subsequence of this character sequence
 *
 * @param stringToSearch where to look for clumps
 * @param occurenceMin filter level for number of clumps in a window
 * @param windowLen length of window to search
 * @param clumpLengthMin threshold of clump length
 */
fun findClumps(stringToSearch: String, clumpLengthMin: Int, windowLen: Int, occurenceMin: Int )
    : List<Pair<String, Int>> {

    val accumulationMap : MutableMap<String, Int> = HashMap()

    for (i in 0..stringToSearch.length-windowLen) {
        val substring = stringToSearch.substring(i, i+windowLen)
        val foundStrings = frequencyTable(substring, clumpLengthMin)

        // filter out any with occurence less than occurenceMin
        val passingStrings = foundStrings.filter { it.value >= occurenceMin }

        //if (passingStrings.isNotEmpty()) println(passingStrings)
        accumulationMap += passingStrings
    }

    val retMap = accumulationMap.toList().distinct()
    return retMap
}

/**
 * Returns a map of all unique substrings that are of [kmerLength]
 * and a frequency of the occurrence.  The list is unsorted.
 *
 * @param windowString the string to scan
 * @param kmerLength length of the scan window
 * @return map of substring to occurence count
 */
fun frequencyTable(windowString: String, kmerLength: Int): Map<String, Int> {

    val map: MutableMap<String, Int> = HashMap()
    // var maxValue = 0

    for (i in 0..windowString.length-kmerLength) {
        val substring = windowString.substring(i, i+kmerLength)
        increment(map, substring)
        //maxValue = Integer.max(maxValue, map[substring] ?: 0)
    }
    return map.toMap()
}

/**
 * add the [key] to the [map].   Increment the count of the key in the map.
 * @param map - a mutable map of type K
 * @param key - they key of type K to add to the map
 * @link https://www.techiedelight.com/increment-value-map-kotlin/
 */
private fun <K> increment(map: MutableMap<K, Int>, key: K) {
    map.putIfAbsent(key, 0)
    map[key] = map[key]!! + 1
}
