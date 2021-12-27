@file:Suppress("unused")

package algorithms


/**
 * for a given [HashMap] if the [key] entry doesn't exist,
 *   then create the new mutable list of the hash target.
 * otherwise add the newItem of type K to the list of existing targets.
 */
fun <K> HashMap<Int, MutableList<K>>.addTo(key: Int, newItem: K) {
    if (this.containsKey(key)) {
        this[key]!!.add(newItem)
    } else {
        this[key] = mutableListOf(newItem)
    }
}

fun <K> MutableMap<Int, MutableList<K>>.addTo(key: Int, newItem: K) {
    if (this.containsKey(key)) {
        this[key]!!.add(newItem)
    } else {
        this[key] = mutableListOf(newItem)
    }
}

fun <T, K> MutableMap<T, MutableList<K>>.addTo(key: T, newItem: K) {
    if (this.containsKey(key)) {
        this[key]!!.add(newItem)
    } else {
        this[key] = mutableListOf(newItem)
    }
}

/**
 * increment a counter for occurences of [key]
 */

fun MutableMap<Char, Int>.incrementMatches(key: Char) {
    if (this.containsKey(key)) {
        this[key] = this[key]!! + 1
    } else {
        this[key] = 1
    }
}

fun MutableMap<String, Int>.incrementMatches(key: String) {
    if (this.containsKey(key)) {
        this[key] = this[key]!! + 1
    } else {
        this[key] = 1
    }
}

fun MutableMap<String, Int>.decrementMatches(key: String) {
    if (this.containsKey(key)) {
        this[key] = this[key]!! - 1
    } else {
        this[key] = 1
    }
}

/**
 * for a map with keys of type [K] mapping to lists of type [V],
 *    create a DEEP COPY of the map with copies of the lists.
 * this avoids side effects from modifying values of the lists in a map.
 */
fun <K, V> Map<K, MutableList<V>>.deepCopyMap(): Map<K, MutableList<V>> {
    val retMap: MutableMap<K, MutableList<V>> = mutableMapOf()
    for (k in this.keys) {
        val l = this[k]!!.toList().toMutableList()
        retMap[k] = l
    }
    return retMap
}

/**
 * for two maps with keys of type [K] mapping to lists of type Int,
 *    compare the two maps, and return true if they are equivalent
 */
fun <K, Int> Map<K, List<Int>>.compareTwoMaps(target: Map<K, List<Int>>): Boolean {

    if (this.keys.size != target.keys.size) {
        return false
    }

    if (!this.keys.containsAll(target.keys)) {
        return false
    }

    var resultOfTest = true
    for (k in this.keys) {
        val targetList = target[k]
        if (targetList == null) {
            resultOfTest = false
        } else {
            val compareThis = this[k]!!
            if (compareThis.size != targetList.size) {
                resultOfTest = false
            } else {
                val boolResult = compareThis.containsAll(targetList)
                if (!boolResult) {
                    resultOfTest = false
                }
            }
        }
    }

    return resultOfTest

}


/**
 * Compare two lists for equality
 * https://www.techiedelight.com/compare-two-lists-kotlin-equality/
 */
fun <T> isEqual(first: List<T>, second: List<T>): Boolean {

    if (first.size != second.size) {
        return false
    }

    first.forEachIndexed { index, value ->
        if (second[index] != value) {
            return false
        }
    }
    return true
}
