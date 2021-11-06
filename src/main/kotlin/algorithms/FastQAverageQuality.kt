package algorithms

/**
 * FASTQ functions
 * See also:
 * https://en.wikipedia.org/wiki/FASTQ_format
 */
class FastQ() {

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