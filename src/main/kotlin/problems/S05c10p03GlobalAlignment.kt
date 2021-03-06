@file:Suppress("SameParameterValue", "UnnecessaryVariable", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls")

package problems

import algorithms.AlignmentGlobal

/**
Code Challenge: Solve the Global Alignment Problem.

Input: Two protein strings written in the single-letter amino acid alphabet.

Output: The maximum alignment score of these strings followed by an alignment
achieving this maximum score. Use the BLOSUM62 scoring matrix for matches
and mismatches as well as the indel penalty σ = 5.
 *
 * See also:
 * stepik: @link: https://stepik.org/lesson/240305/step/3?unit=212651
 * rosalind: @link: http://rosalind.info/problems/ba5e/
 * book (5.10):  https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 */


fun main() {

    val sample = """
VAVYKIDNATGKANIFGGNIWENKDEPICCDLTIVFSHHIYQMDYCEIGYFYSFPFVCCSSHCHVGQNRVQGHEGMGVPHSYWWGYLRTLCLWYASYREVKEHCPFHMDERRLNETVQAADYVRVRRTHCIWWGHGIKPNPPWDDKEPDSGHPWMPNNRAQLRTWCGTTSAMLIWTDHFDQWMLQQFFCGWMGEIVDTCDVCPQARIACEVSCLWCMIEDVNWSPSSNHEKKEDYPMPSECYNEMLTHLHMYCTATEFNWMRQDQKEVHFGPYPSPNFMECYQWPGMVPKTSIEQILEDWQTCGMSMWIKDWVLNRPTVFRYKIYLMAFSDEYSKKCHKPRWHFLNPPVHSHAQVLIDHNLTRTQNGWKDHSCDAQILPFDCPYKYHCWFPMAGTVEWCQCNYASGIQCYATANVVLPSGYINSSPLVQNWWRIGMWNWLLVEDQQIGIYSIQQLDGLDYPGEKTEKVLYKVECDGIYLCQFIIIYRYNNCIPQRTPITNEWHFIKADCHVRNPWDVHNDHDFMCKPSNWEQRGIYYRKYWANFNNAAYISDVADAQDRINPEHKERHMHKMRCKYWWYLHQKCMCRVEYETQWAIFTLHWSEICGFSFNELTDQEAPACNDNEDHFILKHGCKSLMLPHTMATTMHRPVMDMYTQHADMPKKWQLGYFMHYTSKEMVRHIFYQMGQLCDKEYMPKSLADLSAPKECWQYRNDMKFMHLDSSMWIEFGYACAEGNEQCALFQGFAIGPKRKPNTIGVFWNPIGFDKGYVRQEIPWNVTGIRLDMCMTVMDEYLVWFTHPDWDVDFRLDRADPHEMDCIFKEQEFNRLCTCFSNFPHRSTPVIGVPQSTIQDRGRHTAGMAAYQ
VAVYKIDNQRMSLHQCESSDATGKANIFENKDEPICCDETGLIHFHTIHPFSHFTNNLDDPHPFVCCCSHCHVGQNRVTGHEFMGVPMAPVWGDSYWWGYLRTLCLCYAEYREVKEHFHMYDMIAAFYTSAYNSRAVRFDDEDNGQNFWGNDWEIQAADYVRVRRTHWGHGIKPNPPWDDNAEPFPYHCPNNRAQLRTWIIHKPSTTTSAMLIQLRAMCWETMDQWMLQQFFCGQMGNIVRTCDVFKQAYCAREPCDRHEDIKVVWCMIHDVNWSPSSNHEKKEDYPMKAECYNEMLTHLHMYCTATEFNWMRQDQKEVHFGPYPSPNFMECYSWSMRDMLNDWGADMVGSCIPQIEQILEDWQTCGMSMWIKIWALNRPTVFRYKIYLMAFSDEYSKKCEKRKHPRVYRGHNWGFLNPPVHSHDQVLIDHCLEPDDWRTQNGWKDHSKDAKSLPFDFPYKYHCYWSMLVNSRRVTKYVKNASSGIQCYATANVTLPSGYVQFRKWRMNWWRIGKWPTGDRHQLDGKDYPGECTEKVLYKCECDGIYLCQFIDVYRYNQVDHFCIPQRTPFIKANPWDVHDFMCKPSNWEIRGIYYRRYHENFNNAAYISDDRINPEHTERWYCLTMTCRVEYTLHWSEICGFSFNPITDQETGCKTLMLPRYKFLTMATSMHRPVMDMQHADMPKKWQLGYFGFHYTMAEKEMVPEIEYQMGKEFIVCMTADCDKEYCPIGHWCCSLADLSAPKECWQYRNDMKFMHLDLGNMRFHMSMWIEFCLKIQDDMTYACAERNEFAPNALFQWFAIGPKRWSGVFWNCYKIFSTARNKINVTGMTVYWFTHPDWDVDFSWELKMLDRADWCVIQHPPHKHMDLFINRLCSTEQRYIGVPQSTGMAAYL
            """.trimIndent()

    val reader = sample.reader()
    val lines = reader.readLines()

    val ga = AlignmentGlobal(0, 0, 5, useBLOSUM62 = true)
    val sRow = lines[0]
    val tCol = lines[1]

    val result = ga.globalAlignment(sRow, tCol)
    val scoreResult = result.first
    val sRowResult = result.second
    val tColResult = result.third

    println(scoreResult)
    println(sRowResult)
    println(tColResult)

    // worked!!

}


