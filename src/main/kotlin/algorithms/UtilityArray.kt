@file:Suppress("MemberVisibilityCanBePrivate", "unused", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VARIABLE",
    "UNCHECKED_CAST"
)

package algorithms

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

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

/**
 * convert a 2 dim array [arr] into a list of lists of the same type
 */
fun <T> convertToListOfLists(arr: D2Array<T>): List<List<T>> {
    val rows = arr.shape[0]
    val cols = arr.shape[1]

    val listOfLists: MutableList<List<T>> = mutableListOf()
    for (row in 0 until rows) {
        val rowList = arr[row, 0..cols].toList()
        listOfLists.add(rowList)
    }

    return listOfLists
}

