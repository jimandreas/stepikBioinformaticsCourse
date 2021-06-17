fun main() {
    println("Hello world, genome data length is ${GenomeData.genomeData.length}")

    for (c in GenomeData.genomeData) {
        when (c) {
            'A' -> {}
            'C' -> {}
            'G' -> {}
            'T' -> {}
            else -> {println("c is $c")}
        }
    }
}