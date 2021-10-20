fun utilityParseFASTA(sampleInput: List<String>): List<String> {
    val dnaList: MutableList<String> = mutableListOf()
    val str = StringBuilder()

    for (i in 1 until sampleInput.size) {
        if (sampleInput[i][0] == '>') {
            dnaList.add(str.toString())
            str.clear()
            continue
        }
        str.append(sampleInput[i])
    }

    // add the last block:
    dnaList.add(str.toString())

    return dnaList
}