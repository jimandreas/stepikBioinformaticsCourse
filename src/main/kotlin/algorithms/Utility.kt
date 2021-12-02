package algorithms


/**
 * for a given [HashMap] if the [key] entry doesn't exist,
 *   then create the new mutable list of the hash target.
 * otherwise add the newItem of type K to the list of existing targets.
 */
fun <K>HashMap<Int, MutableList<K>>.addTo(key: Int, newItem: K) {
    if (this.containsKey(key)) {
        this[key]!!.add(newItem)
    } else {
        this[key] = mutableListOf(newItem)
    }
}

fun <K>MutableMap<Int, MutableList<K>>.addTo(key: Int, newItem: K) {
    if (this.containsKey(key)) {
        this[key]!!.add(newItem)
    } else {
        this[key] = mutableListOf(newItem)
    }
}

/**
 * for a map with keys of type [K] mapping to lists of type [V],
 *    create a DEEP COPY of the map with copies of the lists.
 * this avoids side effects from modifying values of the lists in a map.
 */
fun <K, V> Map<K, MutableList<V>>.deepCopy(): Map<K, MutableList<V>> {
    val retMap : MutableMap<K, MutableList<V>> = mutableMapOf()
    for (k in this.keys) {
        val l = this[k]!!.toList().toMutableList()
        retMap[k] = l
    }
    return retMap
}