@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems


/**
 * rosalind: @link: http://rosalind.info/problems/ba5a/
 *
Code Challenge: Solve the Change Problem.

Input: An integer money and an array Coins = (coin1, ..., coind).
Output: The minimum number of coins with denominations Coins that changes money.
 */

fun main() {

    val coins = listOf(5, 3, 1)
    val money = 18662
    val result = dpChange(money, coins)
    println(result)

//    val coins2 = listOf(5, 4, 1)
//    val money2 = 22
//    val result2 = problems.dpChange(money2, coins2)
//    println(result2)
}

/**
 * [money] An integer money
 * [coins] array Coins = (coin1, ..., coin d) coins sizes available for change sorted high to low
 * @return  The minimum number of coins with denominations Coins that changes money.
 */
fun dpChange(money: Int, coins: List<Int>): Int {
    val minNumCoinsMap: MutableMap<Int, Int> = mutableMapOf()
    minNumCoinsMap[0] = 0

    for (m in 1..money) {
        minNumCoinsMap[m] = Int.MAX_VALUE
        for (i in 0 until coins.size) {
            if (m >= coins[i]) {
                if (minNumCoinsMap[m - coins[i]]!! + 1 < minNumCoinsMap[m]!!) {
                    minNumCoinsMap[m] = minNumCoinsMap[m - coins[i]]!! +1
                }
            }
        }
    }
    return minNumCoinsMap[money]!!

}



