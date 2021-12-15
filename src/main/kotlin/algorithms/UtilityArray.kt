@file:Suppress("MemberVisibilityCanBePrivate", "unused", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VARIABLE",
    "UNCHECKED_CAST"
)

package algorithms

fun <T> deepCopyArray(matrix: List<List<T>>): MutableList<MutableList<T>> {
    val newMatrix : MutableList<MutableList<T>> = mutableListOf()

    for (i in 0 until matrix.size) {
        val newRow : List<T> = matrix[i]
        newMatrix.add(newRow.toMutableList())
    }
    return newMatrix
}

fun <T> removeColumn(column: Int, matrix: MutableList<MutableList<T>>): MutableList<MutableList<T>> {
    val newMatrix = matrix.toMutableList()
    for (i in 0 until matrix.size) {
        newMatrix[i].removeAt(column)
    }
    return newMatrix
}

fun <T> removeRow(row: Int, matrix: MutableList<MutableList<T>>): MutableList<MutableList<T>> {
    val newMatrix = matrix.toMutableList()
    newMatrix.removeAt(row) as MutableList<MutableList<T>>
    return newMatrix
}

