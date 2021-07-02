@file:Suppress("UnnecessaryVariable")

fun main() {


    val r = ResourceReader2()
    val g = r.getResourceAsText("VibrioCholerae.txt")

    println("Hello world, genome data length is ${g.length}")

    for (c in g) {
        when (c) {
            'A' -> {}
            'C' -> {}
            'G' -> {}
            'T' -> {}
            else -> {println("c is $c")}
        }
    }


}

private class ResourceReader2 {
    fun getResourceAsText(path: String): String {
        val ress = this.javaClass.getResource(path)
        val retStr = ress!!.readText()
        return retStr
    }
}