package algorithms

/**
 * See also:
 * Rosalind: http://rosalind.info/problems/mprt/
 *
Problem

To allow for the presence of its varying forms,
a protein motif is represented by a shorthand as follows:
[ X Y ] means "either X or Y" and {X} means "any amino acid except X."
For example, the N-glycosylation motif is written as N{P}[ S or T ]{P}.

 */

class FindProteinMotif {



    /**
     * for a given [fastaString] return a
     * list of index where the motif (see above) is found
     *
     * There is probably a way cooler way to do this with RegEx in Kotlin - but
     * this works and is readable (by me.)
     */
    fun findMotifs(fastaString: String): List<Int> {

        val motifLen = 4
        val indexList : MutableList<Int> = mutableListOf()

        for (i in 0..fastaString.length - motifLen) {
            if (fastaString[i] == 'N') {
                if (fastaString[i + 1] == 'P') {
                    continue
                }
                if ((fastaString[i + 2] != 'S') && (fastaString[i + 2] != 'T')) {
                    continue
                }
                if (fastaString[i + 3] == 'P') {
                    continue
                }
                // have a match

                indexList.add(i+1) // 1 based indexing

            }
        }
        return indexList
    }



}