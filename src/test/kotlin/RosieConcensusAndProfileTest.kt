@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.ConcensusStringAndMatrix
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Consensus and Profile
 * http://rosalind.info/problems/cons/
 *    compute the consenus string for a pile of DNA strings
 */

internal class RosieConcensusAndProfileTest {

    lateinit var csam: ConcensusStringAndMatrix
    lateinit var u: Utility

    @BeforeEach
    fun setUp() {
        csam = ConcensusStringAndMatrix()
        u = Utility()
    }

    @Test
    @DisplayName("Rosalind Concensus sample test")
    fun rosalindConsensusSampleTest() {
        val sampleInput = """
test
ATCCAGCT
test
GGGCAACT
test
ATGGATCT
test
AAGCAACC
test
TTGGAACT
test
ATGCCATT
test
ATGGCACT
        """.trimIndent().lines()

        val dnaList: MutableList<String> = mutableListOf()
        for (i in 0 until sampleInput.size) {
            if (i % 2 == 1) {
                dnaList.add(sampleInput[i])
            }
        }

        val consensusString = csam.findMostLikelyCommonAnscestor(dnaList)

        println(consensusString)

        printConcensusMatrix(dnaList[0].length, csam)
    }

    fun printConcensusMatrix(strlen: Int, csam: ConcensusStringAndMatrix) {
        val matrix = csam.concensusProfileMatrix

        for (i in 0 until 4) {
            val prefix = "ACGT"[i]
            print("$prefix: ")
            for (j in 0 until strlen) {
                print(matrix[i, j])
                print(" ")
            }
            print("\n")
        }


    }

    @Test
    @DisplayName("Rosalind Concensus quiz test")
    fun rosalindConsensusQuizTest() {
        val sampleInput = """
>Rosalind_3660
CGCACGATAAGGCCAAAGCCATTTACTTTAGTGGCGAAACCGCGCTCGAACGTAAAGTCC
CTTCCGGTGGGTTAACCCGATAGTTCAGTCCCGGGGGCCTTATAATGTTAGTCCTTTATG
CCGATTGTGGACAGGCATCAAAGGAGGTTTTATCCCATCGACCAGAAGGATGGGCGCCTG
CACGATCCTGCAGTGGGATCCCTAAACGTATGATGCCAACCGCCTCCTAGCCAGGACGGT
AGTACATACGCCTGCGCCTGGCGAGCTACCCATAGGATCCATAAACTAAGAGTACTCAGG
AGGCCGCACTCATTAATCTCATATTAAGTCAGCCGACAGACTATTCTGAGACAATTACGC
GAAGCAGCAGATATCATGTTTAGTCATGCTTACCGGCTGGTTACCCTTGGCACAGAACTA
GTGACGTTCGGGATCTCGCCCAGAGAAAATTTTGTCCGTATTGTAACGTGGTGCGAATGA
CTCTATCGCCCTGAGGGAATAGCGCTGACCAGTTTAGCGGAACTGCATAAGCAAATGAGC
TACCCTTAGTGACTCGAGCCAGCTATACTGCCCGTCTTAAAATTCAGATGTAATATGAAG
CCGCCACTCTACCGAACGCAGCAACGCGATGAACGAAAACGACAAGTGTCCACCAGAACT
TGCCGTGCGAGGCTTCGCCGGTATGTTAGGAAACATGCGGAATTTTAAATAAGGCGGGTT
GCACAGTAATGACCCGTACGCCTGGTCTGCGACGTTCAACTTGGTTTGAGGCACACGGGC
TAACCCGCCGGCCCTACATAGGTTGAATCTACCGTTAGGTTTATCAATAATGTTTTGGAA
TACTTGGAAAGGGAGAGCACGGCAGAACTAACTGTTAATCACTGGCTCAACGGGGTTTTG
AAGTAGGAGGCAGGATGTCACCCCTATACGATAAAGAGTGCATGTAAAATTCGGAGAACA
GAGTTACTCGCCCCG
>Rosalind_9085
GCGGTAGGCGACAGTCAACGAGTGCAGCGACGCGTGAGCTTGTGCATTTAACGTCTCATG
GTGTTGGGCAACACCTGACTATTGAGGGGTTTTCACACTACTAATGATGGAAGGAATTTC
TCAGCGAGGATTTCCCGAGAACCTCAAGTTTAAAACGTTGGACAATGTTAGACAATAGGC
TAGATATATATAGTTTTCTATAATGTTGAACGCTGTAAGAGCAACGCTGGAACTCGCTTG
GTGTTCGTTTTCAAAGCGCATACATACGAGGTGAATGCCAAGTCTCACGCTAAGTGCACA
GCAGACCAAACATTCATGCCCAGATCCTATGTAAGAGCACCTCTAGTAGTGATTTTCCCT
ACCCAATGATTAAGGTAAACGCTAACCGTCCAAATATGGTACCGACGAACGCGGACGCCC
GTCAAGCTCGACGATGCGATTGGTCCCACTCTTGTAGCAAGCGGCTTAGAAGCAACCTCG
GTGAGTCGTCACTCCGCTCACTGGATCGTGAATCGAATTAAACCATCGTGGCCGTAACTC
TATGGGTCTAACCGTGTAAAGAGTACCTGATTTAAGGAGTCTTAACTGACCATGCCCTTG
GTGGAATAGGCACACCTTAAGCGTATCGTAATTTCGTGCGACACAGTATTAAGTGTTGTA
GGTTAGTCCTACGTCATTGTAGCCACATGTACTGGAGTCAACCACGCAAGCCAGGCAGGG
TACACTTAGTCTATTCGACCATAAGACCTATGTGCCACGCTTATACGGTTCTGGTGTCAG
GCCAAGCGTAGGATGATGTATCCCGCGACTGTTTCGATCACACCTGAGCGGATCTCGGCG
TGATCATCCATGTCAACGTCCTCCAGGCCTGTTTAATTGCAAACAAATACTGAACTTTAC
CGATCGAGCCGCCTATCTTTCGTACGAAATTCGCCGCGCGGTTTCCACCAGATGACAATT
AGATCGGGGGAATAT
>Rosalind_9194
ATACACCACAGATGCAGTAATTTACTCTATCCTGCTCCCCGCGGGGGTCCGATCGAGCTT
ATCGGGGTACCATAGTAGAACCGCTTGACCCTCACCACAGTAAGACCTAGGACTGTGGTG
GGCGTAGATGCGCATAATTTGAAAGTCCAGGTGAGCCATATTAGCAATGGTCCGCGATTC
AGTTAGGTGTAAACATTTAACCCGATAACATATCTTTGACATTTCTTCACAGAAACCAAT
GGGGTGATGTAACCACGGGCGCGACCAGCGACTCTGCTTACAGCTTACCACAAAGACTTC
GAGACGGAAGCGGGGCTCCTCTGACAACGTGCGGCTCGGGCCCTTGACATACATGGAACA
ACTCACCACCAGTTACCATAAAAATTGCGGAATCTAAATGTCCGATCCTCGACTCAATGA
CCTAGCCTCTCGAGCGACATACGCCGCCTCCACAGGGGATGTCGCGTCCTGTTTCCAATA
TAAACATCGGTTTCCTTCCCAACTTTCCTCTGCCATGGCCAACTTTTTCTTTCCTAACCC
TCACCATTCTCTATGATGCCGAAAGCGGGCATAAGAGGGATGGCGCCAAAAATCGGCGCG
AGCGAAAATGCGCTGCTTCCATGACACAGGGTTTCCCTACGAGGCAATTTAGGTGCTAGT
ATCGACGTGGGATTGCGCCAAGATCCCTTTATACGGCGTGCTGGTAACGTCACATCTGTT
AATTGCCCCGTAAAGACACTTCGACGTCAGAGGTTCCGCTGCCCCATCACTATAGATCTG
TTTTAAAACTGGCGACTCACCAATCCTGAGGTTGATTAGAGGGCGTTAGGTAAACAACAC
CCCTACGTACACGGGGCCAGATAGTGCGGAAATCAATCAAGCTTGAAGTTCGCGAACCTT
GGCAATACGATCCCTAGGTGGATTTTTTTCAAACTCGCATCGGCCCTCGTTCCTCCGCCA
ACGCTCTATTGATTG
>Rosalind_3749
CTATGAGAAAAACAGCACAGCCCATACTGCTGTCTTAGGGTCGGATAGCACCCTTATCCA
CGTTGTGGAGGTCGAAGTGTCAGCCTCAGACCCTGCAGGGCCATAATCACTGTCCACACA
GGATCCGCCCCGGCTTTTTCAAGTAGGCGGAGCCTAGCTTCTGGCACCTCCAGTGATTTT
CAAGGTCTAGATATCCACGCGCTTGTGTGCTAGGAATGGGAAGGTCCATGAGAATCACCC
CACCCATCGGGTAGAGCTTATGAACGATCGTAGCATTCCCGTGCAAAGGGAGTAACTGCC
ATTGACTGCAATAGCACCTCTGCACTGGCACGTATTGTTCCTATCCGACGCGTCATGTCT
GGGTCTAGGCCTGTGCGGTGACTTTGTGATACGAGAGAGACTCTCAGCAAAGGATATTTC
TACTCCAAGATCGTGGTCAGATGAGCCTGAGCGTTGTTAGCCATCAGTGTAGCAACCGAC
CCAAAAGATCCAAGAAGGGGACGTGTAGAGCGTCTTATCCCGCGGCCCACACGTGAACAG
GTAACTAAGGGATCGAGAGTATCGGGGCTAAAATCGGCGGCGCGTATGTTATTGGAGGTT
AGTAGCGCATAACTACGTACCGGGTATCGTGGGTTGGCCGCTACACCTATCCGTGTTGTC
GACACTACACAGGGATTGTCGCCACGGTTGTAACACTGATACTGGACATATTACTAGTGA
CAGACGACGTGCGACGAGAAGGCGCACCTTGGTTAACGGATAAGAATGCAAGTCCGGTAT
GCTCTTGACGAGCGTAGGTCTTAGCTATTCACCGTGAGGGCCCTCGTCTTGGGACATCGT
AAGAAAGAGTTTCGGAGTAGGGTGCTCACCGCGCCATCCTTTTGCCCCAGAGAGGCTGAG
TCAATCCGCGTGTTTGGGATCTTTGTCAGCGTTTGCTAGTACCCGGTAGCGCGGACGTTT
GACTTAACACTCTAA
>Rosalind_1206
CAGTGACGGCCCCGCAGAAGTCCTACTGAGGGGTGTGGCTACTAAAAACCTAATAGGCGG
TCTTTAGTACGCGCACACACATCCCGGAGTTATCGAGTCAATCGGGGGACTTACGACTCA
CGTCTGTGCCTGAATTTAAATCCGGCAAATTGGCACTGGCACACGATGGATCTCCAGTCA
GTTTTCGCACCGCGTTGCATCCACCATTCCCTAGGCGACGTTCATCTCTGTAACCAAACC
GCCGATTTGGCTACACACGGTTTGCTTCGCGCTCATAACCTTAGATTTGCCGAGGGTGAA
CGACACTACGCGTATTAACACAATTGCCGATCAGGTCCAGCGTGGAGTGTTATAAAGGGC
AACCGAACGCACCGCAAGATACCGAAGCTGAGAGTTACTGTAGTATTCCTTCTCCTGGTC
GTCGTCGTTACGTGAACGCATTTGTGCGAGTACCGTTGCATGCCTAGCTCACACCACACC
CGATCACGGTTTATACGGCAGTAAAGCGCGGTGCATATGGGTGTACTACACAGGATTCCC
ACGTGGGGTTAGTGATGACCGCCCCGACAGCAATGGCTTGGGAAGGAGTTTTTACCTTTT
CCCAACAACAGATATGAGCAGTTTTAGTTCCTAGATCTACTCTTCTAATGTGTAACCTTG
CTTCGGTGTACTCCGCGTTTCTGTAGAAGCCAGTTGCGCGGAACATTCGGCGGGCAGTGT
CGTGTAGTCAGTAGTCCTCCTCAGTGTATATGCGACGTTTGACGTCGTAGTCCCGTAAAA
GACCGCCTTCCATCACCTAAAACTCTCAAATCAACGACTAGTCTACATAGATTATTCGGT
CGGATTGTTATCTTATCATTAGGCTGGAGTCAGGTTATGTGAATTAACCTAGGATTCGGC
GTGGTTTAAGTTCCTCTACCAACCATCGTAGTACCGCTCTTTAATTAAATCGAACTCATA
CGACCCGCGAGCTCC
>Rosalind_1476
CTGGCTAACTAGAAGCGGCACGCATACTTCGCCTGGACTGAGCACTCGCTGCACTGAAGG
GACGCGGTAGTTATGCGATTTACTGGGAAACTAGCGCCCCAGGGGTTCCATTCGCAGATC
CCCTCGATGTCGGCTCGAATTCTCCAGACGAGCTAGATACCGATGGCGACAAAGTCTGAG
TGCACTGATCAAACACCCGATAGATCAGGAGATTCAATTTTATGGGTTGTATCGCCATAT
GATTTTAGGTTCCATTCGACTGCAAACCGATAAATACTCGCCCCAAGCCTGATTACATTA
AGGGCCGTGGGATTCACGGACCTCGGATAAGTTCCCCAGCAACTGAGTAGGAAGGGTGGG
TAATTCCACGTATATCTGCGGGTCAGACACATGACCTGCCGCTGGAGCTCCACGATTCTT
CATTGGGGACGTACCGCACCCTGACAGTTATTGATCTCCTGATATCTAATGAACATTAAC
CCCCCGATATGTAATAGATATACCCATTTTGATATAGTAAGCTCCGGTATTGATCCAAAG
ACTGACGCTCCTCAATGAGCTGTTGATATCAGGCTAAACCCTGACTATCATTTCCTATCG
ATGACTGTAGCGTTCAGACAGATCGTAGACGTGGAACTCTATACAAGTCCTATTAGGCAT
CTTAAGGTTCATCTACGACTAAATATGCTTCCATTCGATCCCCGCACTAAAGCCTTTCTG
ATAGAGGGTATAAAGACAGACGCCGCCTTGAACGCACAAATCGGGATGGTAAGGTCTAAC
GCCTGCTGTCTAAAACCAACGAGTCGAAATTGGTTCGTGGATTCGAATCGGACTGCGTAC
TGGTTGGACGGGTCGTAATATGTATTACCCGGATAGGCAAAGGCTTGGTACGACTAGTTG
TTATAGTCGCAAGCTACATTGGTCGACATCTCCCATGAGCGCCTAATATAGTCAGTTGGC
AGACCGATTGGCGGT
>Rosalind_0278
CACCACTCTGTCATTCCTAGCCATAATGAGGGCCCCCAATCTCGATTAGATCTACCCACG
ACTTACCATCCACGCTAAAGTAATCTTGCTTAGCGAGGGTTTTTGAACCCCAGTTGCTCA
ATCATGCTTGCGAGTCTCAGCATCCCATCCGGCAAGCACGCGGGGGATCAACCTAAAGTC
ATTGGGAAAGGAGAGTCTCCGAACCTGTCCAACCCAGACCTCTCAGGAATCTGACAAAGT
GTTCATCTTTTTAGCGATCGGGACTAGCGCGACAGCCGATTTAGGATCCCTATACATCCT
AAACTTCTCCAAGAATCCGAGAGACAACTACACAAAAGTGACATCTTGCAAGTGTCCGGG
GTTCCGCCCATATCTCCAAATTGATCTGTGGAAGGTGTTATGCAACGGAATCTCTAAGAT
TTAATTGCCCAGTAGAGCTAGGATGCAAACACCTTGAGCATAGGGATGAAATCTACAAAC
GGTAAGTAGGCCTCTCAGTAATCGGACTTTGGCCGATAGGGGAGGATTGGCATGAGGTGG
TATGTGAATATCGTTGACGCATTCGGACAAACAGAAAGTGGGGGGCCCGACTCACCTACA
CGGTGCCCGGAATCCACACCTAGTGTACGCCTGCTAGTATCAGTGAGGAGAGTATCCACC
CCGCTGAATCATTGTCGCAAGGACTCTAAAAGAGCGGCCAAATGTGAGCTGAAGACATCG
GTGCAGAAATAAGTCTCCCCTGACAATACAACACTATGGGTGGATCGCATCGACACACCC
GGGGACAACTGAAGCCTGGCTATCATTACTACACCTAAGTTTTCGTACCTTCACAGGCGT
ATGGCTTGGGGTTCGGTGAAACCGGGGCGTTAACCTATTCACACATTGGGCGTACCGCAC
CGTGTGGCACAGGGACTATGAGGATCCGTTAAGGAGACAAAACTGTGTACCGTCTTAAAG
CGGCAGCCGTTGAAA
>Rosalind_3883
AGCGAAAAATGTGCAACTTGGATGCAAAAATGATCTTTGTGCATGTTTTGCAGTTACTAG
AAATCCGTAACTGGATGCCGATGCATTCCACGTAAATCGGAACTAGCACCAACAGGTTAT
ACTGCTTCAACTTTCCCGGACGTCCGTCGCGGGAATTCGGACGGAGAGGCGTTCTACAGA
ATAACAGAACGTCTGTATCGAGCAATACGGTGCTGTTACTTTAGAGTTCGTATCCGGGAT
GCTAATCAATTAAAAGCCCCTTTTGTCTCTAGTTATGCTTAGCGGCGGAGAAGTAGGGGC
AGCGTTGTATCTTAGCCAGGTAAATACCCTTAGCCGGTTTCGTCAAGGCTGGTTTATTCC
CACAAAATGACGCAACAAGCCAGCACCTAGAGTGTTCTCGGAGGGCAGCGACACTACGTG
ACCTATGCCAGACGCGGGAGCAGATACGCAGCGCCGTCAGGAAAGGAATCTAACAAGTGC
CTACCAACCACGAGTAGCAGGACCTCTCCCCCTGTTTACGCGAAAAGCGTGTTCATCCCA
GTTTTCATCATAAACAAGGAGCAGGCGAGATGTATGACAATATTGTCTCACTATTGTCCC
ACAATTTCGGTAACTACCCAACCGGTCAGCTTCGTAGGCTCTGTCCGGTAAACCCATAAG
TGGACAGCCTAAATGGAGACTAACATTACCAGCGCAAGGCACAGCGACGCGTTGTGTACA
ACTACCTATCACAGGAAGGAAGGTAATGGCGAGCACTGATTATTGAGCTCGTTAGACGCC
GATCGCAATTCTGCTAGGACCTTGAGTTTGCCAGTTACAGTGCGTTTTGCGCAGGTTATA
CAACCTGGGCATCTGCTGTTATTGGACGATCCCCTGCGCATTACATTACCGACCTGAGCG
ATCGCAGATCAGCCGATACCTACCGAGATCCTCGGGTTGGTTCACTTTTTCTTGTGTTGG
ACCCCAGACCAGCGA
>Rosalind_8080
CTAAAAGAAGCGGTACATCACAAGTCGCAGCGGCTACAACACCGATTCTTCACCGCGGAA
GACCTCACTTGAATACTTTGACTAATCGCAGGATATTGCTTGCTGCCATCTAGGGTGAGG
ACTTGATCTTCGGGTGCGCAGATAAGGCCTAAGGAGACTACTGGTAATTCGTCGCCAATT
ACGGGGGGGTCTTGTCGCGATCCAATCCGGATTTCACTGGTCAGGACCTGGAGTCCGTTC
CGACGCACGTCCAGTTATATTCGGCCACCCGCCGTTGCTCATTAACTAGATCCTTTAACT
GCCCTCGCCAGCACGGACACACTCGCGGATGACTCAATCACACCCCACACTAGCTCGGTC
GGTTAACGTTAGGCCCAGTAGCATTCGGGCTCGCCCAGCTTGGCCACAAACTTTTGAGAT
TGGCGTGGGTGCCAGGGCAATGTTTACCAACCGCCTGCAGACTCTCATAGTCTGCCGAAA
CGGAGATCAGGTCAGCTGCCATGCGGGGGGCCAACCGGTTATCTATTCTGGTCCAGGACC
CACCTGGTGAGTTCCATTAGTCCAAGTTGGGGATAGACATAATTTCTGCTCATTTATTTA
TCGGGAAAGAGCTTAATTCTCACTCATCCTCAATGTCTTGGACTGGCACACAACACATCA
TTTAACCGGATATATTTGCAACTCAGTAGCGGTATACTTACCAAGCGCAATCCGACCATC
ATAACAGCGCTATTCTCTGGCCGCCGTTGAGTTGGGCCTAGATGGGCCTTAGGATATGCT
TCGGTCCCATACGCTGCCCCACATATACGTTGCGCCAGCCGTGCTTAGGACTAGAGGGAC
ACAGTGATTACTATTAACGATAGTTCGTGATACGACACTTGGAGGCGCTAGTAAGATTCG
ATTAAGCCTCTTCCAACCACTGATGTGCGTACAAGTTATGTGTGTCCGGGTAGGCGTATT
TGACCGCACCGCATA
>Rosalind_7355
ATTATAGAGCTCCCAATGCGAAAATAGCCGAGTGTCTTCCAACGCGCATAAGAGCTCACC
AGGACTATTTTTCACCAGAGTCGAGCGATAAACGCCAAGGTCATGGGGGCAGGCTACTAA
CATGCAAGGCGTGATACAAAAGGGCTTCCAGCTATATTGAAGTAAGTCCGTGTTCATATT
CAGTCCAAGCGTCTTCGTAAACTAAAGCACGGAAGTACGGCATAAGATGATCCCAGACTA
TATCTAAGGATCGGTTTCAGGGACATTAGACAATTAGGGCGCTTTCATCACGTTGAATTA
AAGCTTCCTCGTGAGAGTGAGGATTTCGAATTGATCGCTGTGGTTTCTCCGACACCTGTG
TGGAGAGGATGCGAACATTACAAGTCCGTAGCGCCCTTTTATTATTTGAGCATTCATCGC
AGGCAGTTAATAACGGCCGGCGCCTTAAGATCGCGACAAATGCGGGATGGAGTACAACAT
GCCTTGTTCACCATCGTTAAGTTGTACAATAAGTGCCTCCCATAGGTCCGCGCCAGTAGA
ATGGTTATTGCAATTCCCGCCACACCACTGGGGGCTTGTTCACAATATGCATAGGTCTAC
ATACTTCACTGCTACTCCCCTCGCTTGCGCTCACATGAGAACGCGCATTCAATAGCGAAA
GCCCCCAATCGGGTTTATTGCTCAACCTTGCTTCGTGCGGCGTTGAATTTATTTTTTCTT
GTGCAGGATTTAGTACGGCCCGTGCCTTCTCTAGCACGCTCAGAGGGCAAGTTATAGCGA
ATATGATTCAGGCTTTCCCGAGGAATGCCTGGACAAAACGATGGGCGAGTGGTGAAGGGT
GAATCTTTCGTAAAGCTCAGGTAAGCAAGCGCGAGCTTTGTCTAATTTCCTGTAGGATTC
CGCTGGATTCAGTTCGTCCTCTACAAGCGGGAGCAAGCGTGGAAGGAGGCTAAAAGCATG
AATGTGTTGTGGGTT
        """.trimIndent().lines().toMutableList()

//        val dnaList: MutableList<String> = mutableListOf()
//        val str = StringBuilder()
//
//        for (i in 1 until sampleInput.size) {
//            if (sampleInput[i][0] == '>') {
//                dnaList.add(str.toString())
//                str.clear()
//                continue
//            }
//            str.append(sampleInput[i])
//        }
//
//        // add the last block:
//        dnaList.add(str.toString())

        val dnaList = u.utilityParseFASTA(sampleInput)

        val consensusString = csam.findMostLikelyCommonAnscestor(dnaList)

        //println(consensusString)
        val expectedString =
            "CTAAAAGAAAACCCAAATCGCATATACTAGGGCGCTAACCACCGATTACACAATCACACGAATTCGGTACGTAAACAAAGAAGCATGACACACCGCACCGTAATGGCCACTACCGACTTACCTGCGACGCCGGATCCAAAAATCCGGCCTGGGAACATTGACGGGAAGGATACGCAAATCAAGGCGGAACAAATTTGCAACCAAATACGATAATGAAAGGTATGAGCTAGAAAACCAAATGATCTTATGTTCAGAGCCAGTGAACAACCCGATAATGCCCATACACACCAAATAAAAACAAGGCTCCACACATAGACCGACAAATAACAAGACACACCTGCGCTCAGGATGATATCGGCCGACCAACGACAAGAACAGTAAAAATCCGTGAAGCTAATTGTCCGACGCAACATCTAACTCGTCAAGGTCAGGAACGCCAACGGATACAAATCGCTGTCAAGACGCATAAGAGACACAAACCCAACATCCCCTAACAGGCAATCGGTCGTGAGTCTAGTCGAACTACTCAGGCCCAAACCCTATGTGATTACAATCAAAGCGACAGCACGAAGAAAGACAACATAGCAGCACTTGCCTTCGACGAAAAAGGAATTCACTCAGCGTCTCCGCGTATAACTACAAGCAAATTCAATTACTAAACTCAAGGCTCAACTTCGCCAAGACACTAGCAAACGAGGCGACTGCAACATAAAGTCTGTTATAAAGGAGTTAATCACACCCGAGCATTTAGGCGAACGATTAGGGAGCATAGTATATCACGCCCGCAACTGGCCTACGACAAATATAACTACAGCTAAGGGTCCGTATGGGAAAAAGGATAAATCTGTCATTTCGACCAAAGCGGGGCGTGAGCAAACTAACACATTCAACGAAGATTTGAGATAGACGCAGCCAATACTCGTCGACATCATACAGGAGGGGCACCAAGTTAGGAGAATAAGACCGCAGCGCTAA"
        assertEquals(expectedString, consensusString)

        //printConcensusMatrix(dnaList[0].length,  csam)
    }


}