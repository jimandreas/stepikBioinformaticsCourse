@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE")

package problems

/**
 * @link:
 *
 *
From composition to paired composition

Exercise Break: Generate the (3,2)-mer composition of TAATGCCATGGGATGTT in lexicographic order.
Include repeats, and return your answer as a list on a single line.
As a hint to help you with formatting, your answer should begin "(AAT|CAT) (ATG|ATG)..."

 */

fun main() {

    val dnaString = "TAATGCCATGGGATGTT"

    val outputList :MutableList<Pair<String, String>> = mutableListOf()

    val k = 3
    val sep = 2

    for (i in 0 until dnaString.length - k-k - sep+1) {
        val s1 = dnaString.substring(i, i+k)
        val s2 = dnaString.substring(i+k+sep, i+k+k+sep)
        outputList.add(Pair(s1, s2))
    }
    val sortedOutputList = outputList.sortedBy {
        it.first
    }

    for (p in sortedOutputList) {
        print("(${p.first}|${p.second}) ")
    }
    print("\n")
    // correct!

}

