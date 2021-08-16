@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

import org.jetbrains.kotlinx.multik.api.toNDArray
import util.GreedySorting

/**
 * Implement GreedySorting

Given: A signed permutation P.

Return: The sequence of permutations corresponding to applying
GreedySorting to P, ending with the identity permutation.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240319/step/4?unit=212665
 * rosalind: @link: http://rosalind.info/problems/ba6a/
 *
 * Transforming Men into Mice
 *
 * youtube: @link: https://www.youtube.com/watch?v=lCoUp2Bq8OA
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */


fun main() {

    val sampleInput = "+97 -8 -45 +33 -93 +30 -29 +48 +21 +95 -125 +112 +16 -19 +88 -86 -65 +44 +68 +35 -77 +22 -107 +122 +56 -74 -41 +105 +24 +87 -102 -43 +53 +5 -108 -71 +96 +57 -72 -47 +82 -2 +126 -104 -37 -103 -64 +73 -100 -46 +106 +39 -67 +31 -117 +101 +113 +9 +11 +14 -66 -42 +36 -7 -84 +51 -12 -17 -13 -76 +124 +54 -120 -4 +6 +114 -99 +94 -109 +27 -89 -61 -78 -58 -38 -119 -15 +80 -52 -26 +110 +111 -69 -85 -1 -28 -75 -23 +62 +18 -123 +70 +90 -3 +60 -118 +50 +98 +55 -79 -83 +25 -32 -59 -115 +10 -40 +81 +34 -121 -92 -116 +63 -91 -20 +49"
    val permutation = sampleInput.split(' ').map { it.toInt() }

    val gs = GreedySorting(permutation.size)
    gs.gArr = permutation.toNDArray()
    gs.sort()

    val resultString = gs.outStr.toString()
    println(resultString)

//    val lines = resultString.lines()
//    val str = StringBuilder()
//    val outString = lines.map {
//        str.append("(")
//        str.append(it)
//        str.append(")\n")
//    }
//    println(str.toString())

    // correct!

}


