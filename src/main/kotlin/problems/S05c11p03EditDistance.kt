@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems

import algorithms.EditDistance

/**
 *
Edit Distance Problem: Find the edit distance between two strings.

Input: Two strings.
Output: The edit distance between these strings.

Code Challenge: Solve the Edit Distance Problem.
These are set up when the class is instantiated.

This routine calls into the GlobalAlignment function to do the string to string work.

 * See also:
 * stepik: @link: https://stepik.org/lesson/240306/step/3?unit=212652
 * rosalind: @link: http://rosalind.info/problems/ba5g/
 * book (5.11):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */


fun main() {

    val sample = """
LLINHMNMYGTLVYWCNFEPILWKWQKHTHMFNDWKTPYVEERGGHMMAWPDLRRKDGAAENWNLDFYCHCMMDFLVPQWFIEAPVPHMQRAFWDRIMIDDHWIWNIECEWCTAYLDGGYKEADTIVGRTWEPTQCSQCKMQFITCFTFTCGYDVSVQRPWYQNVGCQRHLIWFMSEKLEWRARKRCMTQLIQWWWHSLKAMLYADPRQMLVSHQYQRDYVQSHDYETDTKDRQSCSAGENAQKGVPYWCTKYRHDKSYTAAVTHLEPAMWISGRDNMQVMVHDPVEELFERNGMQVHCCLTVLNYMQGGYQVYAMHAQSRHGPRPHPRIHTISDEHVFTHINCEKRAIGKCKWPFWPLSAARDPSRIQKGQGEWKFAWELEMKKMVKKIFFPQSVRFQVYAVRVYGHSHARHEQSHDILHHQFEESAHRTVLRQRIVPDWKKGCWYKDTFHTIAMCFNHVNDGLWLGSVMAHTAPCSRHAAYWRRCIEIDPTCCTQFPYYLEAQLNTTTAHCMHSEVHVDMTHEEKAVLLYMPEWWNYVHHQYVLPMPFQWPLNNYEMWTMPKDPDFLAGLKSRIKMWWQIWALTFDEFPTLKPGNWTQEKRWRLEINSDRQEKLLGLHQNADSEIEPTQQIALKMQWWVTHDVSQTKVPKQANGEDTSCGEFKLWNTVREYDYDPAMFPLHVKLWQNGPYTAFQMRRFAMSHNYKQDTATRMGSCWQKNKQRPEMERPYVWLIRNNYMFRRIIMFDHF
FKCHILINHKNMYGCNFEPSAMNVFTHAEWFFNDWKTPYVEERGGAWPDLRRKDGAAENWWKSRHLDFSCHMQRAFWDRENVHAGFCHIPTGRSPSRRIWNIECEWCTAYAGNGNDGGRAEAQTKVLKEADTIVGRDWDPTTCSQCKMQEITCFTFTCGYDVSVQRPWYQNSGCVIPRHLIIATNHTIMSEMFRVHMVTWARDRCMTQLIQWWWHLATSGGYQSHSVCTCYLQYVRDMVQSHDYENDTKDRQSCYAGEAQKGVPYWCTKYRHDKSFKMAHHANTAAVTHLEPAMWISGRENMFERNGMHTECIEVHCCLTVLNYMQGGYEVYAMISWTCRIAQSVWSRDFHGRPFQRIHTISDEVSFCKWGCKVDYANIPFNKFYQARQKGQGEWKFPGHSLDALEMIGLWQMVKKIFFPQSLTSWRFQYNRMDAENHYACRSHAKTPHRFAMSHILHFENNTIPILNSAHRTVLRQRIVPDWKKGCHYKDTFQTIAMCFNTKQLVVNDGMVWAHTAPCSRHAAYRCIEIDPTCCTQFPYYLEAQLNTTTAHCMHFEVHVDMTHMPEWWNYVHHQKVLPMPFQLNNYEMWTMPKDPDFLAGDWSIWALRPDEFPTLKPGNWCREGEQEKRWCLVWEKHKMICEEPTQQIAMTMCGWWVTHDVSQTKVPWVNALTQANGEDTSCGEFKLWNTVREYDYDPARFPLHVKLDIFANNNTAEQMRRFAMSHNYKQDTATRMGRPHVRTKHLQNRNNYMWRKIIMFDHS
           """.trimIndent()

    val reader = sample.reader()
    val lines = reader.readLines()

    val sRow = lines[0]
    val tCol = lines[1]

    val match = 0
    val mismatch = 1
    val gap = 1

    val ed = EditDistance(match, mismatch, gap, useBLOSUM62 = false)

    val resultEditDistance = ed.calcEditDistance(sRow, tCol)
    println(-resultEditDistance)


    // worked!!

}


