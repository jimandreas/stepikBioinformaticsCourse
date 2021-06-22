package util


/**
 * Profile-most Probable k-mer Problem: Find a Profile-most probable k-mer in a string.

Input: A string Text, an integer k, and a 4 × k matrix Profile.
Output: A Profile-most probable k-mer in Text.

 */

fun mostProbableKmer(genome: String, kmerLength: Int, probString: String): String {
    var maxProb = 0f
    var bestKmerString = ""

    val matrixList = parseProbabilityMatrix(kmerLength, probString)

    for (i in 0..genome.length-kmerLength) {
        val candidateKmer = genome.substring(i, i + kmerLength)
        val prob = probForGivenKmer(candidateKmer, matrixList)
        //println("$candidateKmer $prob")
        if (prob > maxProb) {
            maxProb = prob
            bestKmerString = candidateKmer
        }
    }
    return bestKmerString
}

/**
 *  calculate the probability of a kmer string given
 *  a prob matrix of the form:
 *
A 0.2 0.2 0.3 0.2 0.3
C 0.4 0.3 0.1 0.5 0.1
G 0.3 0.3 0.5 0.2 0.4
T 0.1 0.2 0.1 0.1 0.2

 ASSUMPTION: matrix is row then column and is equal to 4 * kmer.length
 */

fun probForGivenKmer(kmer: String, probMatrix: List<Float>): Float {
    val len = kmer.length
    if (probMatrix.size != len * 4) {
        println("ERROR mismatch of kmer and probMatrix lengths")
        return 0f
    }
    var prob: Float = 1.0f
    val nucMap: HashMap<Char, Int> = hashMapOf(Pair('a', 0), Pair('c', 1), Pair('g', 2), Pair('t', 3))
    val iter = kmer.iterator()

    for (i in kmer.indices) {
        val offset = nucMap[kmer[i].lowercaseChar()]
        if (offset == null) {
            println("ERROR nucleotide is not ACGT")
            return 0f
        }
        val probTemp = probMatrix[offset * len + i]
        prob *= probTemp
    }
    return prob
}

/**

parse ACGT prob matrix for kmer -
of the form:

0.2 0.2 0.3 0.2 0.3
0.4 0.3 0.1 0.5 0.1
0.3 0.3 0.5 0.2 0.4
0.1 0.2 0.1 0.1 0.2
 */

fun parseProbabilityMatrix(kmerLength: Int, matrixStringIn: String): List<Float> {
    var curIndex = 0
    val probMatrix : MutableList<Float> = mutableListOf()
    val matrixString = "$matrixStringIn " // make sure there is a terminating space for parsing
    for (i in matrixString.indices) {
        when (matrixString[i]) {
            ' ','\n' -> {
                val newFloat = parseFloat(matrixString.substring(curIndex, i))
                probMatrix.add(newFloat)
                curIndex = i
            }
        }
    }
    return probMatrix
}

private fun parseFloat(s: String): Float {
    return try {
        s.toFloat()
    } catch (e: RuntimeException) {
        0.toFloat()
    }

}
