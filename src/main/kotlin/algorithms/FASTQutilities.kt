package algorithms

/**
 * FASTQ functions
 * See also:
 * https://en.wikipedia.org/wiki/FASTQ_format
 */
class FASTQutilities {

    private val q = """
        !"#${'$'}%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~
    """.trimIndent()
    private var qMap : HashMap<Char, Int> = hashMapOf()

    /*
     * set up the quality hash table
     */
    init {
        var quality = 0
        for (c in q) {
            qMap[c] = quality++
        }
    }

    /**
     * given a FASTQ quality line [qLine]
     * if the [percentage] of the reads are above
     * the [threshold] quality level, then
     * return true.
     */
    fun testQuality(qLine: String, threshold: Int, percentage: Int): Boolean {
        var numOverThreshold = 0f
        var len = qLine.length
        for (c in qLine) {
            val q = qMap[c]
            if (q == null) {
                println("Quality FASTQ char '$c' NOT FOUND!!")
                len--
                continue
            }
            if (q >= threshold) {
                numOverThreshold++
            }
        }
        // OK did the qLine pass the percentage test?

        val testPercentage = numOverThreshold.toFloat() / len.toFloat() * 100f
        if (testPercentage >= percentage.toFloat()) {
            return true
        }
        return false
    }

    /**
     * given a FASTQ quality line [qLine] return the Float representing the
     * average quality
     */
    fun averageQuality(qLine: String): Float {
        var sum = 0f
        var len = qLine.length
        for (c in qLine) {
            val q = qMap[c]
            if (q == null) {
                println("Quality FASTQ char '$c' NOT FOUND!!")
                len--
                continue
            }
            sum += q
        }
        return sum / len.toFloat()
    }
}