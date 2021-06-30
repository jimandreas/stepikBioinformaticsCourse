package util

data class ReadPair(val p1: String, val p2: String) {
    override fun toString(): String {
        return "$p1|$p2"
    }

    /**
     *  create a prefix pair from a ReadPair
     */
    fun prefix(): ReadPair {
        val prefix1 = p1.substring(0, p1.length-1)
        val prefix2 = p2.substring(0, p2.length-1)
        return ReadPair(prefix1, prefix2)
    }

    /**
     *  create a suffix pair from a ReadPair
     */
    fun suffix(): ReadPair {
        val suffix1 = p1.substring(1, p1.length)
        val suffix2 = p2.substring(1, p2.length)
        return ReadPair(suffix1, suffix2)
    }
}