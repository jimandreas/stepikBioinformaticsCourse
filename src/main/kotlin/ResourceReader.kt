@file:Suppress("UnnecessaryVariable")

class ResourceReader {
    fun getResourceAsText(path: String): String {
        val ress = this.javaClass.getResource(path)
        val retStr = ress!!.readText()
        return retStr
    }
}