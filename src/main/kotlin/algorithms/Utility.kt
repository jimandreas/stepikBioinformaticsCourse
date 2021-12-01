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