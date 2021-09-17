@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package algorithms

/**
 * Stack class
 *   Kindly provided by:
 *   https://gist.github.com/AndyBowes/cbb2763a8aadbf9f0248f5984bb3a94a
 *      slightly modified for usability
 */
class Stack<T>(vararg items: T) {
    val elements: MutableList<T> = items.toMutableList()
    fun isEmpty() = elements.isEmpty()
    fun isNotEmpty() = elements.isNotEmpty()
    fun count() = elements.size
    fun push(item: T) = elements.add(item)
    fun pop(): T? {
        val item = elements.lastOrNull()
        if (!isEmpty()) {
            elements.removeAt(elements.size - 1)
        }
        return item
    }

    fun peek(): T? = elements.lastOrNull()

    override fun toString(): String = elements.toString()
}

// extension function
fun <T> Stack<T>.push(items: Collection<T>) = items.forEach { this.push(it) }