package problems

val goldBugMessage = """
    53‡‡†305))6·;4826)4‡.)4‡);806·;48†8^60))85;161;:‡·8
    †83(88)5·†;46(;88·96·?;8)·‡(;485);5·†2:·‡(;4956·2(5
    ·—4)8^8·;4069285);)6†8)4‡‡;1(‡9;48081;8:8‡1;48†85;4
    )485†528806·81(‡9;48;(88;4(‡?34;48)4‡;1‡(;:188;‡?;

    """.trimIndent()

fun main() {

    data class CodeEntry(val letter: Char, var freq: Int)
    val entries = mutableListOf<CodeEntry>()

    for (c in goldBugMessage) {
        //println(c)
        val exists = entries.filter {
            it.letter == c
        }
        if (exists.isEmpty()) {
            entries.add(CodeEntry(c, 1))
        } else {
            entries.forEach {
                if (it.letter == c) {
                    it.freq += 1
                }
            }
        }
    }

    val sorted = entries.sortedByDescending { it.freq }
    for (s in sorted) {
        println("${s.letter} ${s.freq}")
    }

}