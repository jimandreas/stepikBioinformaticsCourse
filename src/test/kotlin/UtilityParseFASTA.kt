
class Utility {

    /**
     * parse FASTA format text
     *   SIDE EFFECT - save the names of the FAST files in an associated global
     *   list
     */

    val stringToNameMap: HashMap<String, String> = hashMapOf()

    fun utilityParseFASTA(sampleInput: List<String>): List<String> {
        stringToNameMap.clear()
        val dnaList: MutableList<String> = mutableListOf()
        val str = StringBuilder()
        var currentName = sampleInput[0].removePrefix(">")

        for (i in 1 until sampleInput.size) {
            if (sampleInput[i][0] == '>') {
                dnaList.add(str.toString())
                // add the dna String to FASTA name hash
                stringToNameMap[str.toString()] = currentName
                str.clear()
                currentName = sampleInput[i].removePrefix(">")
                continue
            }
            str.append(sampleInput[i])
        }

        // add the last block:
        dnaList.add(str.toString())
        // add the dna String to FASTA name hash
        stringToNameMap[str.toString()] = currentName

        return dnaList
    }
}