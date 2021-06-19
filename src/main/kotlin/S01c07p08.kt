@file:Suppress("UNUSED_VARIABLE")

// 1.7 Peculiar Statistics of the Forward and Reverse Half-Strands

/**

Exercise Break: Give all values of Skewi (GAGCCACCGCGATA) for i ranging from 0 to 14.

Sample Input:
CATGGGCATCGGCCATACGCC

Sample Output:
0 -1 -1 -1 0 1 2 1 1 1 0 1 2 1 0 0 0 0 -1 0 -1 -2
0 -1 -1 -1 0 1 2 1 1 1 0 1 2 1 0 0 0 0 -1 0 -1 -2  // test checks.
 */



fun main() {

    val testString = "CATGGGCATCGGCCATACGCC"
    val exerciseString = "GAGCCACCGCGATA"  // answer: 0 1 1 2 1 0 0 -1 -2 -1 -2 -1 -1 -1 -1  checks OK

    var level = 0

    print("$level ")
    for (c in exerciseString) {
        when (c) {
            'A' -> {
                print("$level ")
            }
            'C' -> {
                level -= 1
                print("$level ")
            }
            'G' -> {
                level += 1
                print("$level ")
            }
            'T' -> {
                print("$level ")
            }
            else -> {println("c is $c")}
        }
    }
}