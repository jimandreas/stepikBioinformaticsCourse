package algorithms

fun hammingDistance(str1: String, str2: String): Int {

    var hammingCount = 0
    str1.filterIndexed { index, char ->
        if (str2[index] != char) {
            hammingCount += 1
        }
        true
    }
    return hammingCount
}