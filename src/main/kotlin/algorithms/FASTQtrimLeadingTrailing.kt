@file:Suppress("unused", "ReplaceManualRangeWithIndicesCalls")

package algorithms

import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import kotlin.math.max

/**
 * FASTQ functions
 * See also:
 * https://en.wikipedia.org/wiki/FASTQ_format
 */

/**

Given: FASTQ file, quality cut-off value q, Phred33 quality score assumed.

Return: FASTQ file trimmed from the both ends (removed leading and trailing bases with quality lower than q)
 */
class FASTQtrimLeadingTrailing {

    private val q = """
        !"#${'$'}%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~
    """.trimIndent()
    private var qMap: HashMap<Char, Int> = hashMapOf()

    /*
     * set up the quality hash table
     */
    init {
        var quality = 0
        for (c in q) {
            qMap[c] = quality++
        }
    }

    /**
     * trim leading and trailing low quality below threshold
     * 0 - name
     * 1 - dna
     * 2 - quality line
     */
    fun trimLeadingTrailing(fastQlist: MutableList<String>, threshold: Int): MutableList<String> {

        val name = fastQlist[0]
        var dnaString = fastQlist[1]
        var qString = fastQlist[2]

        // do leading characters

        var breakPoint = 0
        for (i in 0 until qString.length) {
            if (qMap[qString[i]]!! >= threshold) {
                breakPoint = i
                break
            }
        }

        // trim

        qString = qString.substring(breakPoint, qString.length)
        dnaString = dnaString.substring(breakPoint, dnaString.length)


        breakPoint = qString.length-1
        for (i in qString.length-1 downTo 0) {
            if (qMap[qString[i]]!! >= threshold) {
                breakPoint = i
                break
            }
        }

        // trim the end

        qString = qString.substring(0, breakPoint+1)
        dnaString = dnaString.substring(0, breakPoint+1)

        val retList : MutableList<String> = mutableListOf()

        retList.add(name)
        retList.add(dnaString)
        retList.add("+")
        retList.add(qString)
        return retList
    }
}

