fun main() {
    println("Hello world, genome data length is ${GenomeData.genomeDataVibrioCholerae.length}")

    for (c in GenomeData.genomeDataVibrioCholerae) {
        when (c) {
            'A' -> {}
            'C' -> {}
            'G' -> {}
            'T' -> {}
            else -> {println("c is $c")}
        }
    }
}