@file:Suppress("LiftReturnOrAssignment", "UnnecessaryVariable", "unused")

package util

/**
 *
 * NOTE: this code is now obsolete.
 * It has been replaced by the DeBruijn paired Graph algorithm coupled with the EulerianPathOverReadPairs processing.
 *
 *  https://stepik.org/lesson/240266/step/4?unit=212612
 *  Code Challenge: Implement StringSpelledByGappedPatterns.

Input: Integers k and d followed by a sequence of (k, d)-mers
(a1|b1), … , (an|bn) such that Suffix(ai|bi) = Prefix(ai+1|bi+1) for 1 ≤ i ≤ n-1.
Output: A string Text of length k + d + k + n - 1 such that
the i-th (k, d)-mer in Text is equal to (ai|bi)  for 1 ≤ i ≤ n (if such a string exists).

 */
fun stringSpelledByGappedPatterns(pairsString: List<Pair<String, String>>, d: Int, k: Int): String {

    val firstStringList = pairsString.map { it.first }
    val nStrings = firstStringList.size
    var outString = assemblePrefixString(firstStringList)

    // invariants - length of prefix string and length of fragment
    val prefixLen = outString.length
    val fragLen = k - 2

    // walk the prefix built string and do the matches with the suffix set
    for (i in 0 until nStrings) {
        val matchPrefix = outString.substring(i, i + k)
        val suffixesFound = pairsString.filter { it.first == matchPrefix }
        if (suffixesFound.size > 1) {
            println("oopsie $suffixesFound")
        }
        val s = suffixesFound[0].second  // this is an ASSUMPTION

        // verify overlap in outstring matches in the outstring as it is built

        val overlap = outString.substring(i + k + d, i + k + d + fragLen)
        val suffixFrag = s.substring(0, fragLen)
        if (overlap != suffixFrag) {
            println("no match of $overlap with ${suffixesFound[0].second}")
            break
        }
        if (i != nStrings-1) {
            val charToAdd = s[fragLen]
            outString += charToAdd
        } else {
            val charToAdd = s[fragLen]
            outString += charToAdd

            if (outString.length < k + prefixLen + d) {
                val charToAdd2 = s[fragLen+1]
                outString += charToAdd2
            }

            //done
        }
    }

    // TTTACGTTTGTATTT
    return outString
}

fun assemblePrefixString(stringList: List<String>): String {
    val output = deBruijnGraphFromKmers(stringList)
    val theList = deBruijnDirectedGraphConversion(output)
    // println("theList = $theList")

    val ep = EulerianPathStrings()
    val resultMap = ep.eulerCycleMap(theList)
    // println("resultMap = $resultMap")

    val path = ep.eulerCycleConvertData(resultMap)
    ep.setGraph(path)
    val solution = ep.solveEulerianPath()

    val resultString = pathToGenome(solution)
    //println(resultString)
    return resultString
}


fun parseGappedPatterns(s: String): List<ReadPair> {
    val reader = s.reader()
    val lines = reader.readLines()

    val pairList: MutableList<ReadPair> = mutableListOf()
    for (line in lines) {
        val pairs = line.split("|")
        if (pairs.size != 2) {
            println("Bad data in string: $s")
            return listOf(ReadPair("", ""))
        }
        pairList.add(ReadPair(pairs[0], pairs[1]))
    }
    return pairList

}