@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ControlFlowWithEmptyBody", "unused")

package util

import kotlin.math.max

/**
 * Code Challenge: Find the length of a longest path in the Manhattan Tourist Problem.
 *
 * See also:
 * stepik: @link: https://stepik.org/lesson/240301/step/10?unit=212647
 * rosalind: @link: http://rosalind.info/problems/ba5b/
 * book (5.6):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */

class ManhattanTouristProblem {

    data class Manhattan(val row: Int, val col: Int, val downList: List<Int>, val rightList: List<Int>)

    /**
     * iterate to find the longest (weighted) path in the Manhattan matrix of down and right
     * edges.
     * @return weight of longest path
     */
    fun findLongestPathInManhatten(m: Manhattan): Int {
        val nRows: Int = m.row
        val mCols: Int = m.col

        val d = m.downList
        val r = m.rightList

        val s : MutableList<Int> = arrayListOf()
        for (i in 0 until (nRows+1) * (mCols+1)) {
            s.add(0)
        }


        // fill out first column of weights from down list
        for (i in 1..nRows) {
            s[i*(mCols+1)] = s[(i-1)*(mCols+1)] + d[(i-1)*(mCols+1)]
        }
        // fill out first row of weights from right list
        for (j in 1..mCols) {
            s[j] = s[j-1] + r[j-1]
        }

        /*
         * now walk the interior matrix and fill out the
         * summ of the path winners (maximizing the weights)
         */

        for (i in 1..nRows) {
            for (j in 1..mCols) {
                val down = s[(i-1)*(mCols+1) + j] + d[(i-1)*(mCols+1) + j]
                val right = s[i*(mCols+1) + j-1] + r[i*(mCols+0) + j-1]
                s[i*(mCols+1) + j] = max(down, right)
            }
        }

        val bottomRightCell = s[(nRows+1)*(mCols+1)-1]
        return bottomRightCell
    }

    /**
     * scan a well formed string of
     * row col
     * col cols of depth row-1
     * "hyphen"
     * col-1 cols of depth row
     * @link: http://rosalind.info/problems/ba5b/
     *
     * No error checking performed.
     */
    fun readManhattanMatrices(connList: String): Manhattan? {
        val ep = EulerianPathArrayBased()
        val list: MutableList<EulerConnectionData> = mutableListOf()
        val reader = connList.reader()
        val lines = reader.readLines()

        val rowcol = lines[0].split(" ")
        val row = rowcol[0].toInt()
        val col = rowcol[1].toInt()

        val downMatrix : MutableList<Int> = mutableListOf()
        var offset = 1
        for (i in 0 until row) {
            val rowLine = lines[i+offset].split(" ")
            for (j in 0..col) {
                downMatrix.add(rowLine[j].toInt())
            }
        }
        // error check.  Should be a hyphen here.
        offset += row
        if (lines[offset] != "-") {
            return null
        }
        // read down matrix.  row rows and col-1 cols
        offset++
        val rightMatrix : MutableList<Int> = mutableListOf()
        for (i in 0..row) {
            val downLine = lines[i+offset].split(" ")
            for (j in 0 until col) {
                rightMatrix.add(downLine[j].toInt())
            }
        }
        val theMatrix = Manhattan(row, col, downMatrix, rightMatrix)
        return theMatrix
    }


}