@file:Suppress("UNUSED_VARIABLE")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.aminoAcidsTable
import util.translateRnaCodonStringToAminoAcidString
import java.util.*
import kotlin.test.assertEquals

internal class CodonTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    /**
     * test codonToAminoAcid
     */
    @Test
    @DisplayName("util: test Codon to Amino Acid translation")
    fun testCodonToAminoAcid() {
        val str = "AUGGCCAUGGCGCCCAGAACUGAGAUCAAUAGUACCCGUAUUAACGGGUGA"
        val result = translateRnaCodonStringToAminoAcidString(str)
        val expectedResult = "MAMAPRTEINSTRING"
        assertEquals(expectedResult, result)
    }


    /**
     * test codonToAminoAcid
     */
    @Test
    @DisplayName("util: test Codon to Amino Acid translation 02")
    fun testCodonToAminoAcid02() {

        val str =
            "AUGCCCAUGGGAUUAGUGUGGCACAAACAAGGACCACUAGAAAGGAUAUCUAUAAGAGGAGUAAUAGGAGUUAGGAGCGGGUAUAACGAAACCAUUCGAAGGAAUUGGGUCAUGUUAGUAAGUAAAAGCGCCUUAUUCGUAUCCACAUGCUGCCAAUGUAACCCCCCUUACCUGACUUGUUAUAAGCAGUUGAAGAGUCCAGACGUGACACGUUUUGCGCGCGCUCAUGACAUGGAUCAUUUUAGAGACCACACUCAUAUGGCUGGGAGGACCAACUUGGAACAAACGUUUUGUGCUCAACCCGUACAUCUGACUAUGGACCUAGAGUAUUAUCAGGACCCUCCAGUGGCUUAUGUACUACAGUACAUGGUGCCGAGGCGAAUGCCAGCUCUGUCAGUAAUCACGAAUCCUCCCAACCAAGAAUUGCAUUCACUCUGGUCAUAUCUGCAUUUGUCGGUUAGUCAAAUCUCUCGGGGGCGUCUAACUUGCGUGACGCUGCUACUAGUGAUCUGGGACAACCAUUUAUUACGGUACUCCUAUCUUUGCCAGGCCUUCGAAAUGGUUAUGAGCUCGAACAUCCGCUCCUUGGGAGCUGAGACGUUAAAGGUGUUAACAGAUCCGAAUAGAGAGUGCGCUUCGGUCACCAUCCCACAUCUCACUCAGCAAUCCGCUCUGAGUAUUAGAGCACCCCCCAGGUACCUUCACACUGAAUUACGGCGUUGUAGAACAUCUUUAGCACGGACGACCCUUCGGUUCGUGUCCAGUUGUACCUCUCGAUUUGGUGCUUCGAUUCCGAGCUUUCAUGAACUCUACUGCUCGCUAUACACAGGGCUCUCCUUCGUUCAUAUAUCUCGAUCAAUUAAGAACGCGGUUGAUCAAACAUGCGGCAAACCUAUCGACAAUACUCAGAUUGUGAUAAGCUUCCAGAGCUCUCGUCUCCAUCGAUGCAGUCUCAUAGCUCGUCUACAGGCCGACCGUACUUUCUUCCUUUAUAAGCGAGGGUGGUGCCGGGGCGUGUGCUCUAUAGUUAUAUACUUGACCGGGAUCUGCUACGUCAAACUGUGUUGUUACGCUACGGGCAUUAGCACCGUUAGCGACAGCUAUAAACCAAGGGGUUACGUUCAUCUUCAGGUAUUUCUCUUCCGACACGUGACUGUGUACGUAAUUCGUACAUAUGAGAAUUCAGUUACCGGGGCUUCACGAUAUCAAGCGGACUACAUCAUGGGGCGCGACUUACCUACUCUCGUAGAUAGUUUGCCAGUGGUGUCGUAUAGUUGGAACCAGUACCUGCUUCUAGUCUGUGGACCACGUCGGGGAGACCUCUUAUGGCCCCUAUUAGGCAGGGAUCCAACAGUUAAAUUUAAACCGCGGACCUACCGUCAACGGAGGAGUUUUACAGCUUUCAUGACGCGGAAGUGCGUUUCACUAUGGGCGCAAUUGACAGCUCGCCGUCGGACAAGCCGCAAAUUAAUUCGGGCACUAACUCACGGGUGGAAAAUGCGAAUGUCAAGACACGCAGUGGUCUAUACAGGCAUAGAGGGGAUCCUCGGGUCGAAAACAGAGUUAACGGUCCCAACUCAUCCGCUGUACAUCUUGCUCCCGUGCUCGGGUCCAAGUUGCGUGCUCAUGACUGGGGUCCGACCAUUUUGGUCUUCGUCCCAAGAUAGUCGUUGGUCAACAACCAUCAGGCCGGAGGGUGGUGUACUGAGCGUAAGUUGCAGACAGAUAGCCAAUAGCACCUUCUCGGUGCUCGAGUCAUUAGGUCUCUUUAUAGAAGUUCGGCACGGCCACGGGAAAAUCCCGCUCUACCGUAGCUCCACCUGUAGUAAUUGUUCACAUGUCUGUCAGUCUAAUGAGUGGACAGCGUGGUUCUUGAACUCCCCUGCGGCCGGGCCGAACCAGUGCCAAAUUGUUUAUAAUACGAAAUACUGUAUUGCAGGGUACGCCCCGAGCCCGUUACUAAGCACAGCCCGCGCAGCGAGUUAUCGAUAUAAAUGCUCGUGGUACUUCCUUCUUCUCUUUUUGACGUGUUCCCUCAUCUCGGGGCAAUUCUUGGAACCGGGGAUUAUGAAGUGUGCACUGGGCAUGCUAAAGGUAGGCUCGGAAUGCAAUCUCGAUGAACCGCACGUUAGUUCCACCGCGGGUUACCACGGCCACGUGAACACCCUCUGUUGCAUUAUUGCAGUUAGCCCGCCGCUAAGCCUGCGGGUUAAAAGACCGGCGGGGAGAGUUGUCUUCGAAAUAGGUCCCGCAUGUGAUCAGGACGGUGGUCAACUUGCGUCAAAUAUAGUGAUAACUCGUAAUCCGACUGGGUGGCACCCAUGGACUUCCCGAGUCAGCGCCCUUACCAUACGCUCACUACAAUGCCCACAGUGUGUGAACGAAGGCUGGCACUGGUCGCACCCUGAGCCAGGACAUAUGGGACGAUACUUGCACCCAAACUCAAUCCCUUACGAAGCCUGCGUCUCUACCUAUACAAUUGCCGAAAAAUUCUCGGUUCAUUUUAGUACCCGGAGCAUUGACUGGGAGUGCCCGGUCCGAAUACUCGCGACGUACACAAGACAGCUGGAGUUGCGUUGCCUUACCGGAACGUGCUUUUCCUACUGGAAUACCCCAAAGAUUCCUACGGUAACACCCCAUGUAGCACGGAAAUCUAAGGAGACUGACCGAAACGCCUCGAUUCAGCCCCCGAUGGCUGCUACGGUACCAGCAACAGGCUUAUCGAAGCAAUGGAUUGUCAUCCAAUCUUCCAGGGAUCUCCGGACUGACGAUGAGAUGGAUACCACCUUUCAGACACAUCGGUAUGGCUUCGGAAUAUCAACAACGGAGUGCUACACUCUACCGAGCCUAUACUCUGCCCCAAAGAGUAGAAAUACCCACACUUGUCACGCAUCGCCUUCCAACGAUAGUUGGUCCUGCUUCAAGCUCUGUGGAGUAAGUAUACGUGAUAGGAAAAGACGAGUCUGGACUGCUUCGCGAUGUAAUGACCUAGCGUCAGAAAAGGGUGGCCGGCAUGGUACACCACCCACAAGAAUCCCACCGUCGUUGCCUAGAAAAACGACACAGACUGGUGCAGCGAACCUAGCUUACUGGUCCCAGUUCAUUAACGGCGACAAAUCGCCGGAUGGCUAUACUAAGCAUGUUGGCACGGACGCCAUAUAUGCAGCGCGUGUUCAAGCGCACAGCCUGACGUGCUCCCGGCUAUCCAGAAUUGUGUGUAGUGGAUCCGCCGUGGGUUGGGAUGGAGACGAUUUUCGUGAGUCGGACGUUGACCGAGAAUGCGAGGUUACGAGUGGCCCAAGGCCCCAGAGAUUUUGUAUUUACAUCAUGUCCUCCGUUUCGACCGACGGUGGAGUAUGUCGAAAGAUCUGGAUAUUCCACCUCUUAUCCAAUUCAUGUAGGAGCUUGUUUUCCCCGUCAUCAGAUAGGGAACAGAAGCUCGGAAUAAUGGCGCAGGCAGAGGUGUUCUGUCACGCCCUCUCAAAAUUUCUGUUUCGGUUGUGUUCUAUUUCAUCGCUCAGGGCGCUCAGUUCUUAUCCCAGAUCGGGGCGAGUUCUCUCUAUUGUGACAAACAUACCGCAGUGGGACAGCCGCCCUAGGCUACGAAUUGACUACCAUCAAUUUACACCAAUUUUUCAUAACCCGCCAGGUAAACUCCUGCAGCCUAACGAUACGUGCCUGGGCUGUUCAAACUGCCUCCAGAGCUCGCCUCUGCUUUUUACCCGGGCAGGUCUUUUGGGCCUAUUCAGAACCAUGGCCCCUAUAACAUACGGCUGCAUGACUCGCUCUGCUUGGUCUUCGUUACAGCGUGGAGGGCCACUCGACGUUAUCCUCUCUACGUCGAGAGCUACCCUAUUCAAGCGGUGUGUAAGAUCACGAUUCAGAAAUCCAUGUGGUAUUAAUGUUCUCACAAACUUUCACGAACCGCGAGCACUUCUAUUUAGCGCUCCACGCGGGAAUCCGAGCUAUACGGUCAUGUUCGCCAGUACGGCAGCCACGUGCGAGCGCCUGGCGUUAGCACGUCAUCGUACUUAUUCCCGAUUCCUCUUUGAACGUACUAGAAAACAUGCGCUGAAAGGGAGAACAAGUGAUCAAGACGGCCUAAGUGCCGGUGUCACGUGGAAGGGCGUGAAUUCUGGCCGGUACCUUUAUAGCACGUGUCGUCAUGGGCUGCGCAGGUUGAUCCGGACGAUGAUGAAGCAAAUUGGAAGACUAUUUUAUAGAACGAGGUGCACGCUUCUCGUCCACAGAGACCCUGGCGCGGAACACUACCUUAGUACGCCAAUAGUAAAGCAAGGACGCGGCUACCACCGGCGUACACGACUUCCAUUUCUGCUUUUUUACGACCAAUGCCCAGCGCGCGGAGUGCCGCUAUUAGUACGCUUAGUGACCAAUGCUUUAUCGCCUUAUAUUACCAUGUCAUAUGCAUGUCAGACUUGCAACAGAGCCCAGGAGAGCGUCAGCCUAUUACCGAGUACAUGUGCUUACCCCUACCGGUCAAUCAAUUCCGAUCCUGUUGGAGCUUGGAAAGUGGGCUCCCUAAAUACGCACUACAUUUGUAAGUUACCCAAGGUAUCACCGCCGAGAUGGGCAAGCUCACAGCAUAUCGCGAGCACAGUCGUUAGAAGCGAUACCCAUAGUAGAGACGAGUCGAUCAAUGGACAUGGCAGGCUUAAAGUUGCAAUCAUUGUUUGUAGGGUAACCGCAAGAAGUGUAGCAGAAGUCGGAGGUUACUGGAAAAAAAGACACAGGAGUCCACGAAGUAUAUUUGAUAUGAAAAAUAGAGACACUUGGCACGAUGGUGGGCGCGGCCCUACAUCAUCGUCGGACCAUUAUUAUCCCAUAACAGGCAUUCCUGUUCUCAGUACUCCCUCCCGGAGGCCUCAAGCUCGGCUCACCAUUAAAUCGUCAUCAAUUUGGGCUUAUCGAGAUCAACCGAUGCUCACACAGCCAUGCAUUACGUACCCGGUACUGCUCUUGCAAUGGGCCGUAGCUUCGCGAAUGAGUGCAAUAUCUGGCAUUCGGCUUGCGGGUCUGUGGGGAGCAGCUAAUGCACCAGUCCAACUCUUCCUCUACCUCAGUCAGUGCGCACACUUAGGAACUUAUUGUGUAUAUCACGAAAUGGGCCCAAUGUAUAUGGUUCUACCAAACCAACGGCGACUGCGACGCUUUAGUCUUGGAGGACAGAUUCUAUGUUACCCCUUCAGGACGACUUACAGUCGGGCUGUCGUUAGGCCUUAUCCUGAUACGCCGCAUGGUUACAGCGGUAACGGCGUAAACACCGGCUUCCUAAGACGCUGGCCUGGACCUGUCCCGUGUCUGUGGGUUAAGGGUGUUGCCCACCACGCGUCUAAGCUACGGUGGAAGACACUUGCUUGUACUGCGUUAUCCCUGCCUGCCCUACCUAGUCACCCUGGAUUAUUGAAAAGCCUUCUCAAAAGAGGCGAAGUGAAAAAACUGGAGGCAGCUCGCACCGUCUUGCGAGCUUUACAACCGCACCGAACAAAUGGCGGAUAUACUAAGGAACUCGGUCAGGAGGAGGAGCACUGGUUCAGGUACUCAUUCGGAGGUACGAUCGGGGCCUUGUACGAUCGUAAGGUUGCUGUGUGGUACCGCAGCUCACAGGUAAUCUACAAUGGUCCUAUUUAUCGUCUAGUACAGGAGCCGAGUUGCGCCUCUCCGAUCACCUUAUUCAGCCGAGUACUACUUAAUGUUGUAUCUUAUCUAGAGAUAACGGUUAAACGACAUCUGUUCACACCAAACACCAUCCCCGGACGAGGCCUUUCUCAUCGAAAGGGGGCAUUAUUUUUCCUAAUACCACCCUAUCCAAGCUCGUUCGAAUUAAUAAGAAAACCCUACCGCCGCACACAGCCUGCUUUUCCGGGAUCCCUAGGCGAUGAUCCCUUGCUCCCCUGUAUCUGUCGGUCUAAACUGCGACUCAGACGGAUAACUGUGUAUGGUUCAAGCCGGCCAGUUGAAGACGUGGGAACCACGGAGUCUAGAUGUCCAAAAACAUUUUACUCUCUCCCGCUCGCGCUGUGUUGGUGCCGGAGUACGGGGGUGGGCAUCUACGGCUAUCUGGAACCGGACUACCCGUAUUAUACGAGGCGCGCCCCCGCCAUACCAAUUUGGAGACACCCUGUAUAUCGCGCUUUCGUACAGUUAACGUUAGCGCAGGCGACGGUACCCGGGCCGAAGCCGUUUUUAUUGCUACCCGUGGGCACCGGACCGUUUAUAAAAAUUUUCGGCCGCCACGGUUUUGCAGUUUUAGCUGCCGUACGAUAUUGUACGCUGUGCUCGGACCCUAUACGGGGACCCGACAGCCGACUUAGCCUAGUAACAGAGAGUGCUCGCCAGGUUCUCACUCCGGUCGAGUAUACCGAUCUGCAACUGUGUUCACUGGGGAGCAUUCAAAAGUCAAAACGGCUCUCAAGACUAUCCCUAACCAAAUUUGAUGAAUUAGCCGCCCGAACAAAUGCUCGGUUCGUGCGUCCCGUAGGAUAUCAACAGGUUAAGCCCCGGGUGAUUGAAACUGAAACUCCUUAUAGGACGUAUCGGCCAAACCCGCCUACAGAUGUAGACGAGGAGCACAAACAUCGUUUACUAGCUGUCAAAAGAUACAUAGGUUUCAGAGGGACACUGUCGGACAGACCCCAGGCGGCGCUCAUUAAAGGUGAAUGCAAGGCCAGCAGGUCGGGGCAUCUUCUUGAAAUUCAUAGAGGAUCAGAGAGACUUGAUUGCCGUAUCACGCUAACGCUACUUAAGGACCCACUUGGCUACUUUUACGGAGUGGACAGUCAGCCACCGGCGGUGCAUUUGAGUUACGACGCGUUCUCGCAACUCAUCAAAAACAGAUGUUUUAGAAAUUACAAGUCGCGAGCUUUCCAAGGCACAAAUGUAGCACGAAAAUCAAUAUGGGAUCGGUCAAGUGACCCUGGUUUGAGGGAGCCAUUGUGUUGGACACUAGACAAGCGCUGGGCCAAGUACACACAUCAACCUCAACCCUGCCAGCCGGUUCCCACCUCCAAUGUAUUUGAAUGCAGGCGUGGCCAAACCGAGGUACGACAUAACGCGUUGGUCUAUAGUCCAACCUUCGCGAAUUUUCGUGUAUCAGCGGACAGUACUGUCUUACUUCCAGCCACCGGGUCAUUAAUCGUUCCAUCUGGAAGGUCCGAGCGUAACCCGGAUUAUCACGCGCCGUGCUCUUUAUGUCCCAACCUCAACUCUGGUUUGCCGGGUAGGAUAGUAGGGACGCCUCGAACCGACAAGCGGCGGGCCGUCACGCAACUCAGGGUGUCCGAAGCAAGUUGCCCUCAGCGGAAUAGAUCGCCUGACUCGUGCGCACUAGGUAAAUGCCCGCUUCCGUUAGAUAACUCUACAAGCCGAUUUUGCGGCCCUUGCGCACAUUAUUCAGACCUAACCACAUUUUUACGGCUCUUAUUCUCACAGACUUCGCCCGAUGGCAGGUUUUUCCAUCAGAUAAUAGCGGUACUCAAAGUGGGUAAAUGCAUUAGAGCUACUGCAGCAUUGAACCCCGCCUCGUACCUUGCCAGCCGUGCUGCACAGUCUGGAAAUAUUGUGGAAGGUCAUCGGGGACCGAUUUUAAGCAGGAGCAUGCUAGUAAAGCGAAACUGGCGUUGUCACGGGUCCUUACCAUGCAAUAGGACGACGGGAGAAACACUUACGAUAUUAGGCAUAAUGUACGGAUCCGUGGGUGGCUCCAGAAUAUUACAAGCAGUAGUGCAUUCGGUGGCUAGACGCGCCCCGCAGGCGACCGAUGGUUUUGGGCAUUUGCUGACCAAACCGUUCGUCAGAGCCAGAACAUCAGAAGAGGAUAAAGGGUGGGUGGUGACUUCUAUAUUCCCUCGACGGAACGCCUAUGACCCACUCGGUAAAGUGGGCCCAACAUGGCGUACGCCCGGCUAUCGCAUGAUACCUCAAGCAGAUAAGUGGGGGAUCGGACCGACUAACGGUGAUACAGCCAACUGCGUAGGCGGGUGCCUCAGGGUAAUUUGCCCGUCCAGGGCAUCGAAGCAUCACGCAGCAAUAUGCUUAUCCGGCACAAGCUUCGCACGUGUGGGAAAAGGAAGAGAGGAAAAAGUCCGUCAUACGGAGUCCCAAGCGUGGACACGCUUUAAUCUGGGUAAUGCGAGGCGAGGUGGGGUGGACCAGAUUUUACGGAAAUUAACCAUCGAUAGGGGUGGGCUACGUGGGCACGCGAGGAUAAGUACGAAAUACCGGCCCCGCCUACAGAGGAAUGCCACAAUGGCCGGGAAGGAUACCCGACAUUAUUUGAGUGCUUUCAAGUUCGAACUUGCCACGCGAGCGUACAGAUCGGCGCUAAUUCUCUGGGAACUUAUCCAGAGGAGUACUAGAACGCUGGCUUAUUCUACGGGGCGGAUUGAACUCCCAACAACAGUGCCCCACUCUUCGCCCGGUGUUGUGCUAUUAGGAGUAGUGAAUUUAAGAGGAGAAGCCACACAGCAUGCCCUAUUCUCCUAUCUGGAUUCUACUACGCCCUAUGAGUGCUUAGACAUAGCCCCCAAUAGCUGGUUCACGUCCGAGUCAUCCUGGCCACGUAGCGAUGCAAUACUAUCAUACCGGUUGGUUAAUGACCCGGCUGAACUUCUUAGUGCAGGGCCAUCAACGCUAUCACACCCGCUCUGCGGAUCCAUCGGCCGCUCCUUAACACGAGGAGACAGAUUAACGAAGGACUACUACGUAGUACACAAUUUGAGCUCCUCGCUUACGCGAUUGGACCGUCAUAGUUCAGCGACGCGCAGCUUGCGACCAUGCCUGUGGGUCACAACAGAAAAUACAAUUUAUCUUCAGAGGACCUGUUUCUAUUCUGAUUGGGAUUGUAUGCGGUGCCUUUCAGACAAGGGAGGCUACGAUGGCAAUUAUUCGAGGGUAACCGCUUAUCACUAUUGUCGGCCAAAAGUUCACGAUGAGGCUACAGUGUUAUACAAACUUAAGGGUACCCCCAUACGAUUUGGUCCGGCACACAACCACAGAGGUGCCUUAGCUCUUCGUCCUGUUGAAACCACCCCCCUGAGGACAUCUCCUGAAUAUGUGGAACACCCUUACGACGAGGAGUCUAAUACAGGUCGGGGGGCAGCUCGGCAGGAAGGGCUCGAAGUUCAGCUCAUUGCAGGCCAGUACGACGCUUGGCAAUGUAUGGAUAACUGGACGGUUCACAUCAGUCGACUAUGUACCGGGGUACAGUCCCCACCGUUGGCUCGUAGAGUGUCCUAUUUAGGAAUCAGUCCGGACAUUUGGGGCCAUAGUUUUAGUUUCCGCUACACUUGGCCCUUCAAAAGCUCGACCGGGGCGAUUGAUUGGCGCCACGGGUUGUACUCCUUCCUGUCUACCUUAAUAAUCGGUUGCUGCUCACGAGCUCUGACUGAGAGAUCACGUCGAGGACCAGCCCGUUAA"


        val result = translateRnaCodonStringToAminoAcidString(str)
        val expectedResult =
            "MPMGLVWHKQGPLERISIRGVIGVRSGYNETIRRNWVMLVSKSALFVSTCCQCNPPYLTCYKQLKSPDVTRFARAHDMDHFRDHTHMAGRTNLEQTFCAQPVHLTMDLEYYQDPPVAYVLQYMVPRRMPALSVITNPPNQELHSLWSYLHLSVSQISRGRLTCVTLLLVIWDNHLLRYSYLCQAFEMVMSSNIRSLGAETLKVLTDPNRECASVTIPHLTQQSALSIRAPPRYLHTELRRCRTSLARTTLRFVSSCTSRFGASIPSFHELYCSLYTGLSFVHISRSIKNAVDQTCGKPIDNTQIVISFQSSRLHRCSLIARLQADRTFFLYKRGWCRGVCSIVIYLTGICYVKLCCYATGISTVSDSYKPRGYVHLQVFLFRHVTVYVIRTYENSVTGASRYQADYIMGRDLPTLVDSLPVVSYSWNQYLLLVCGPRRGDLLWPLLGRDPTVKFKPRTYRQRRSFTAFMTRKCVSLWAQLTARRRTSRKLIRALTHGWKMRMSRHAVVYTGIEGILGSKTELTVPTHPLYILLPCSGPSCVLMTGVRPFWSSSQDSRWSTTIRPEGGVLSVSCRQIANSTFSVLESLGLFIEVRHGHGKIPLYRSSTCSNCSHVCQSNEWTAWFLNSPAAGPNQCQIVYNTKYCIAGYAPSPLLSTARAASYRYKCSWYFLLLFLTCSLISGQFLEPGIMKCALGMLKVGSECNLDEPHVSSTAGYHGHVNTLCCIIAVSPPLSLRVKRPAGRVVFEIGPACDQDGGQLASNIVITRNPTGWHPWTSRVSALTIRSLQCPQCVNEGWHWSHPEPGHMGRYLHPNSIPYEACVSTYTIAEKFSVHFSTRSIDWECPVRILATYTRQLELRCLTGTCFSYWNTPKIPTVTPHVARKSKETDRNASIQPPMAATVPATGLSKQWIVIQSSRDLRTDDEMDTTFQTHRYGFGISTTECYTLPSLYSAPKSRNTHTCHASPSNDSWSCFKLCGVSIRDRKRRVWTASRCNDLASEKGGRHGTPPTRIPPSLPRKTTQTGAANLAYWSQFINGDKSPDGYTKHVGTDAIYAARVQAHSLTCSRLSRIVCSGSAVGWDGDDFRESDVDRECEVTSGPRPQRFCIYIMSSVSTDGGVCRKIWIFHLLSNSCRSLFSPSSDREQKLGIMAQAEVFCHALSKFLFRLCSISSLRALSSYPRSGRVLSIVTNIPQWDSRPRLRIDYHQFTPIFHNPPGKLLQPNDTCLGCSNCLQSSPLLFTRAGLLGLFRTMAPITYGCMTRSAWSSLQRGGPLDVILSTSRATLFKRCVRSRFRNPCGINVLTNFHEPRALLFSAPRGNPSYTVMFASTAATCERLALARHRTYSRFLFERTRKHALKGRTSDQDGLSAGVTWKGVNSGRYLYSTCRHGLRRLIRTMMKQIGRLFYRTRCTLLVHRDPGAEHYLSTPIVKQGRGYHRRTRLPFLLFYDQCPARGVPLLVRLVTNALSPYITMSYACQTCNRAQESVSLLPSTCAYPYRSINSDPVGAWKVGSLNTHYICKLPKVSPPRWASSQHIASTVVRSDTHSRDESINGHGRLKVAIIVCRVTARSVAEVGGYWKKRHRSPRSIFDMKNRDTWHDGGRGPTSSSDHYYPITGIPVLSTPSRRPQARLTIKSSSIWAYRDQPMLTQPCITYPVLLLQWAVASRMSAISGIRLAGLWGAANAPVQLFLYLSQCAHLGTYCVYHEMGPMYMVLPNQRRLRRFSLGGQILCYPFRTTYSRAVVRPYPDTPHGYSGNGVNTGFLRRWPGPVPCLWVKGVAHHASKLRWKTLACTALSLPALPSHPGLLKSLLKRGEVKKLEAARTVLRALQPHRTNGGYTKELGQEEEHWFRYSFGGTIGALYDRKVAVWYRSSQVIYNGPIYRLVQEPSCASPITLFSRVLLNVVSYLEITVKRHLFTPNTIPGRGLSHRKGALFFLIPPYPSSFELIRKPYRRTQPAFPGSLGDDPLLPCICRSKLRLRRITVYGSSRPVEDVGTTESRCPKTFYSLPLALCWCRSTGVGIYGYLEPDYPYYTRRAPAIPIWRHPVYRAFVQLTLAQATVPGPKPFLLLPVGTGPFIKIFGRHGFAVLAAVRYCTLCSDPIRGPDSRLSLVTESARQVLTPVEYTDLQLCSLGSIQKSKRLSRLSLTKFDELAARTNARFVRPVGYQQVKPRVIETETPYRTYRPNPPTDVDEEHKHRLLAVKRYIGFRGTLSDRPQAALIKGECKASRSGHLLEIHRGSERLDCRITLTLLKDPLGYFYGVDSQPPAVHLSYDAFSQLIKNRCFRNYKSRAFQGTNVARKSIWDRSSDPGLREPLCWTLDKRWAKYTHQPQPCQPVPTSNVFECRRGQTEVRHNALVYSPTFANFRVSADSTVLLPATGSLIVPSGRSERNPDYHAPCSLCPNLNSGLPGRIVGTPRTDKRRAVTQLRVSEASCPQRNRSPDSCALGKCPLPLDNSTSRFCGPCAHYSDLTTFLRLLFSQTSPDGRFFHQIIAVLKVGKCIRATAALNPASYLASRAAQSGNIVEGHRGPILSRSMLVKRNWRCHGSLPCNRTTGETLTILGIMYGSVGGSRILQAVVHSVARRAPQATDGFGHLLTKPFVRARTSEEDKGWVVTSIFPRRNAYDPLGKVGPTWRTPGYRMIPQADKWGIGPTNGDTANCVGGCLRVICPSRASKHHAAICLSGTSFARVGKGREEKVRHTESQAWTRFNLGNARRGGVDQILRKLTIDRGGLRGHARISTKYRPRLQRNATMAGKDTRHYLSAFKFELATRAYRSALILWELIQRSTRTLAYSTGRIELPTTVPHSSPGVVLLGVVNLRGEATQHALFSYLDSTTPYECLDIAPNSWFTSESSWPRSDAILSYRLVNDPAELLSAGPSTLSHPLCGSIGRSLTRGDRLTKDYYVVHNLSSSLTRLDRHSSATRSLRPCLWVTTENTIYLQRTCFYSDWDCMRCLSDKGGYDGNYSRVTAYHYCRPKVHDEATVLYKLKGTPIRFGPAHNHRGALALRPVETTPLRTSPEYVEHPYDEESNTGRGAARQEGLEVQLIAGQYDAWQCMDNWTVHISRLCTGVQSPPLARRVSYLGISPDIWGHSFSFRYTWPFKSSTGAIDWRHGLYSFLSTLIIGCCSRALTERSRRGPAR"
        assertEquals(expectedResult, result)
    }

    /**
     * test codonToAminoAcid
     * Solution also for:
     * Translate an RNA String into an Amino Acid String
     * @link: http://rosalind.info/problems/ba4a/
     */
    @Test
    @DisplayName("util: test Codon to Amino Acid translation 03")
    fun testCodonToAminoAcid03() {

        val str =
            "AUGACACGCCGAAGUAGGAAGGCAUUUAAAGCUUUGAAUGUCGAUCACUCAGAGCCGGAUGCCAUACUGACGCCGUCAGCAGCCGAAGUCGCUCAGGCCUGUUUAGACGGAUCAUCGAUAUGGCAAAAGGAUCAAGACAUCACCGCUAUACAGAGCACUCGAUUGAGGCUAGAAAGGGACUCGGUUGCAUACUCCAACUACUGUAAGCGGUGUUCUUACCAUAAUUGGAGCCGUCCCGUUUUGGGAACUAUGCUAGAUCCCCAGCGAUGCCAUGAUACGACUGACCGAGGUCACAAUUUCGAACGCCUGAUAAUGUGUAGAUUAAGCCAAUGUGUGCAAGGAGUGGUGCGCCGAGCUCCGGCGAGCUCAGGAAAGCGCGUAUCAUCGGGUGACGAGAGCCCCGCCCGAAGGCAAUACACAGAUCUCAUCUCGACUCGUAGUACUAUAGAGUUAACCAGUCUAGCGCUGCGAACACGCUGCACAACGUCCAAGCACUGGGCUUCUUGGAACGCCGCAAUCGGUCACCGGGGGUGUUACUCUUUAAAACAAUCAGCUCAUUAUGCGGCGCCCGUAGGGCGGCCGUACCCAUUGAUCGAGCUUGCUUUUGUCUGGGUGAAACUGACUGCUAUUCAACUCCGCCUAUAUACUUCGUCCAUCUCAAUACCGUCCAGACUGAUAGGUCCACUGGUGGGUCCAACUCGGUUUGUAGCCGGACGGGCCAUGAAAUUGCGAUCCACGUUGUCUAGGCGUGUACAACUGUGCUUUAAUUGGGCAGGGGAUGUGGCCCAAAGGUGGCGGCUUAAGCACCGAGAUGAGUUAGUUACCACAAUCGGUCGUUUGCAAUUCGAGUUGCGCGCGCCUAGAAUCGCUAGUGCUCCAGGUCCUAAAAAAUGGCAUACGCUGAUAGUGCGGCCGCAUUCUAACAAUUGGCAUAGUGGAGCGCCUCACUUCGGAAAUUGCUCAGGGCGAACUAUAGUUGUACUAUCCCUUAAUACCCGAACAGACAUGGUACUUCAUCCUUCUCGGCAUUACGCGUCACAUCCUCGAGUGCAGCAGAGCAAAACGAAAUUAGCGGCAAACAUCUAUACGACAAUCUGUGAGACUUCUCUAGGCACCAGGGCUUGCGGCAAUACACUGAACCUUCGUGGGAAUUCAAUAGCUCCCCCGGUUCGACAUAACCACACGUGUCUCGGCCACCUUGACAGAUUGUGUCCGCCUCGGUGCCAUCUUCCCAUCGUAUCGGGUUCCGUGCCCCGAUUAGUAUCGGCUCGGGGGUGCUGUGCCUUUCAAUGUACUCGACCUCUACGUACUAUACAGUCCAUGUUCUAUCGGCACCGGCGAUACCGCCCUGAGGGUGGACAUUUACAAGGUUGUGCUAAAUCUCCAUAUGGUGACCAUCGGGAUCCAGUGCGAAGGUUAUCCCGGGCAUCGCGUAAGGAAAUUAAUAUAUUAGGAAAUAGAGUUGAUAGCUCACCCAAUGUGGAGCUGGUUCUUCCGUGGAUAGAGAAGCCCCCGAGACAAAACCGUGGAAACUCCUUGAUCCCUGCGAGAGUGGGCUAUGUCCAGAAAAAACACACACGGGUUAAUGCUCAGCUUGCCCCGAAUGAAUUCUACAUUGGAUGGCCUAUGUCCGUUAUUAGUUUCCAGACACGAUCGGCCCCGACGUAUAUACAGCCCCCAAUCAAGAUAUGGACGCACGCAACGCUGGAGAGAAAACUGGAUCAAGCCACUCACCUGGAUGGAACGUUCCACGCUUUGAGCCUGGACGUCGGUGCGGAUCAGCACUUGAAGGCUGCACAGUGGAAGAAUUAUAGGGGAUCCUGCCUGGACCUGUACCACAGUUCUAGUCACCAGCUAAACAGGCCCCCGACCUCUAGGAGUGAGUUGCCAUGGAGGACUUCUUGUCCAUCGAGUAUGCUUCCGCGAAACCGUAACUUUAAUCUGAAGCUCGGAAGAAAAACGCACCAGCGACGACCGCCAUGGGCUAUUUUCUCGUUAAGAGGAGUCCACGGUGUAGCUUUAUUUAGCAACUUUGGAUCGGACGGAUCUCCCCACGGUGUCGGCAUGUACAUCUCGAGAACUGCAAUCGUAGAAGGGUUUAGUUACCUGCAUUUGAAGCUAGGUUUGGUAGCGCUGUCAUACCAACGUGCAUUCAGACCCUGCGGGCGGACUCUAAAGAUACACUGGCGCCAACUACGACUAGUUUGCUCUAUUUGCAAGCGGCAUAGGCGCCUGGAUUACCGACCUACGUGCGACAACUCUGGGAAAGGCAGCAGGAUCUGUUAUUGUGCCGCAAACGCCAUGUAUGAGACGGGAGUGACUGGCACUUCGUUCUGUGCUUGGGGACCUACUACCAAACUAGACACUCUGCUCAUAGUCCCGCUCAAUGAGACUGAUAAUAUCAUGAAUCCAUGCAGCAACAGUAGCGUUCUAGUCAACCUGGAAGUACAAACAAUACAAAAGGUUCAAUACGUGACAUUUCUGAGCGGUUCAGGCUUGGCCGUCUCCGCCAGUCACGAAAACCCGCAUUACCAGCAACCGUGGCUAGACUUAUUACGCCGUCGGAGUGAUACAGAAGUCUUACAUGUACACGCCUUGUUUAGCAUGAUGCUGUGCUUUGGGGCAUUCUACUCCCUGGCAUCAACUCCUAAACAACAUACUCUCCUCCUUAUUAGCCGGCCUGGAACUCUGGAGCCCCAUUACGCCCUAAACGGGUGUCGUAGUGUCUCGAUGGACUGCUCAUUCAGUUGGGCUGCUCCACUUUAUGAGCCUUCUAGCUGGACAUUAAGCUAUUUGAGGAUGACCGAUUUAUCCCUGCCUCAAGCCUAUACUGUAAUGAAGGGGACUGCUAUGCCACUUACUAUCCAUUGUCCUAGCAUAGACCUGCAUCAAGAUAUUGAGGGUAUAAAAGUUGUUGACGUCACAGGGACCGACAGGUACUACCGAUUCUGUUUAUCCUACACUACGUUAAACGGUGAGUCAAGCCACAUUCGUAUUCCGAGCCCAUUACUGGUUCUAUACAUCCCUGGAGAGUUGUCUACCUUCCCCCACGUGAUAGCUCGUCCGAGCCCCAAAACACAUGUUCUCAGUUGCAGAGGGAUGGUAUGGGCACCGCGUAUAGCAUCGUCAGCCUCCAUCGUGGGCUAUGUUCAGAGCAGCUAUAUUAAGCCAACUGCGCGAACCAAUGGUCUUUAUAUCGAUACGGCACACGCUGCUGUGCGGUACAAAAGACCAUCACCGCCACUACCUAUCCAAGGCUCUACAGUCAUGUGUUUGGAGGUUAGGGUUGAGUCCUCACGCUCUUGUUGUUUGAGUGGGGCCGUGUUGCGCACUCAUGCAACAAGAUCGUUAUUGCCUAUUGAAGCCAGUGCAGUGUGCUGGCAAGUGAAUCUACUAGACCAUAUGAUCCCUACGCAUUUAAUCCUGGAGCGCGUAGACCCGUGUCGGCGAAGGGCUUCACGGUCCGGCGGCCUGAACUCAGGGGGAUAUGGUGGGCAGUACGAAGAUCACGUGGCGAGAGUUGUAAGCGCUAGCCCUCGCCGGCGGGCUUCCUUCCACUCUAGGUGGCACUGCAGACAAGUUACCAUUCCCUGUAAGGGGAUCAUCUACGCCACAGAGCCAUUUGGCAUUGGCUUACAUCCCCCAGUACUCAGAGAGUACGGCUCAAAUGGCAAACAUUGGUGCGACAUCCAUCGGGCGCUCUUUGUACGGACUAGCAAAGUAGUUAGGUUCCGCCCCAACGGGAGAAGUUCAACAGCCUAUACAAUCCGCCUAAGCACUCAGCCAAUUUAUGCCAUGGUUCGCCCAAGUGGCAUCAAUGCAGUACGGUUUAUUUAUGUCAGACCGCGCAGGGUAGUUCGCCGAUUCGUCAAGCGGGAUGUUUCGAUAGAAGGGAGCCCAUCCGAGCCGUCGCGGAACUAUAUACUCCAAGAAGGUCGUUGUAACCGGAAGAUGCAACAAGCGGGUCGGGUCGUAAAGUUACCGAAUGCGUGGUUGUUUCCACGGCGCCUCCUGAUUCCUCGUUUCUCAUUCAACAAACCUCCUACACCGGCGUUUGAAGCGAUUCUAUAUUCUCACUGGUUGGGGUCCGCUCAUCGUCAGAACUUUAAAUCCGUAGCCAAAAGCUCGGAACCUCUUCAAGUCUGUCAGCACUUUGUGUACCCACUGGUGCGACGAGUUCGAGCCCGUAGUUACCUCAGUCCUACACUGUCAUGCCAUAUUAAGCGCUCAGUGUCUUCUACAGAGGUGUGCUUUGGUAAGGAGAUACAGCAAAGGGAAGACAGCCCCCCAUAUGCAGCUUGCAAGGAAGUGCGGAGCAUUUCAGGCGCCUCGUCGUACCGUGAGCCAUCAUAUAUCAACGGAGUCUACUUUUGCCCUAUGAGUGAUCGUGCAUCCAGGCCUCCGGUCGAUACCGGAUCAAGUCCUCCGGACGUGCGCAUACUCGUGGCUACCCUCUUCUGUUUAUGUCGUAGUACUUGCCGCCGCGGCAUAUCAAAGCCGUGUACGCCCCGUAUCAUUCAAAGUAUGCUCAAGGCGAAGGUAAAAAUCUACCGUGUGCCGGAGUGGAUAUGGCGACUACCUGACGUACGGGUCGGGAGGUACGGCAUGAGUAAGUCGGUUCUCAAGAAAUGCACCUGGAACUACAUAGCAAACUGCCUCAUUGGUGCAAGAUAUUGGGUUCGGUGGCUGUGCGUCAUUUCAGGGCGAACAGCCGACGAGUAUUAUUUGAAGCGUCGUCUGAGUUACAUGUAUUUGUGGUCUUCAUACAACAUGGUAUGGCUUUCAAUACCAGUCCUGCGUGUACCCAUGUGGCGUACGCCAAUUCGUAGAUUCCUAGUGCGUGUUGAACACAUCUUAAUGCCAGAUUCACACGUUAUGUUUCAUCCCUCACAAAAAAAUAGACGAUCAGGAAAUAGCAUCAGAGGAGAUUUGAAGGGUACCCACUGCUUACACCUCGGCUGGCAGCAGGAACCCGUGUUAGGAACCACCCUCACGUUUUCUUACUCCUCACAGUUUCUCGCUACGACUGUGCGUUUACAUCUGUUAGUAGUAUGGUGCGCCAGAACUUAUGACGAACCAGUAGGCAAUGAGUCGCUCAGUGGUCCGGAAUUAAUCCCGGUCUUACCUGGUGCGAGUAUUUAUGUAGGCUCGCUGUAUGUAUCGUUCCGUCGAAACACCGUCACUACACCGUCCACGGUAGCGUGUACUUGGAAGAGCUUGCGCGUAAAGUGCUCCUUUGACAACAGGCACGCGCACACGUUUUCCGAUGAACUAGAACACAGCACAUGCAGUGAAGUACGACAUUCAAACCUGUUAAAUGAGGUAUGGUCCAGAGACUACACCAGGCGUACCGUUUCGGAUCAUGCUCUCCGGAGAUCAUGUCUGUCACUCAGCCAGGCGCUUUUGGGCGUCUGGUGUGGCGGGGGAGGCUUUAUUGGUGUGCAGGGGAGUCUGGAUCAUGCUACGGUUAGCUAUUGCAUCACUGUAGUUUAUGUCUCGAGGUUAGCUCUGACCGCGCAAACUAAUCUGGUGCUCUUGAGCUAUCAGGGUAUAACGUCUGAGUUUCUCAUAGUAUCAGUAAUCGUAAUCCUAAACGAUAGACCCUUCAAGUCGACCACAACUCUGAUACACCGUCAUUUAGCAAGCUUGUGCGGGACGAAUGGUCUCGGCGGCCAUCACAGUAGCUUCAUUCUCGUUACUUAUGGAAGCUUCAUAUAUCCGCGAAAACGUGGCUUGUUCCUCCGGUCAAGAGGCUUGUCAAAGGCUGACAUUUCAGUUGGCUUAAAUCCAGCUUAUCUCCAACGUAGCUCGGGUACCCUCUAUGCUGGCAGUCUUCAGACAACCUGUAGGACACGCAGAAUCCGGUACAGGAGACUUAGACCAAUUAUCUGCCACCAUCCUUGCGUACCGAAAUCCGUGGUGCUGCGCCUCACCCUCCCCCGGAGAAAGGGUGUUUGCGCCGGACCGCCCCAUCUCAUUGUAGGGAGGACAUUAGUCGGGAAUGUUCUGACUAUUCUACAACGAUUGCUCGGAAUCUUACAGGAGGCCCCAGGCUGCGCCGGCUACAACAGGAAGAAUCACUUUCAAGGACUGGCGCCUGUUACAAAGUCGGCUUCUCUCCGAAUGCCGAGACAUGAUCGUAUGUCAAGAGCUAGCUCACCUAAGGAACGACGUAUGCUACGGAUAUUAGACCACAGUCCUUGGGUGAUAGGUUCUACCUCGGUUGAGCUUAAUCAAUGUAAUGGAACUAAGGUGGUAGUCCGCCCCCCAGAAAUCAUUCUCGCAGUUUUGCCGGCGUACCGAAGCGUAUGUUAUCCGGUAGUACAAGGCAUUAGGGGCGCGUCUCGCCGUGCUGUGAAUUCAGAACAUGUCAGAUGCGCAGUUAUACUUCAAUCUUGGGAGGCGGUAAUCCUCGUCGCUGGCAUGUCACUUAUCAGGCCCCCACCCGAUACGGCCGUCACUGCGCCAAGGGCCGACGGCUGUCCUCCCGGGGGGUGUGUCUUAGACGAAAAUUCGACAAAAAGGUAUCCAAGUGAAUCUGUACGAAGGUCUAAGCUAGGAAUCAGACUGCGGCAGUUGCGUCUAGAUAUCGUGUUGGAUGGAACACUAGAUCGGCUAGUCCGACCGACUAAGAAUGCGCCGCAAUCGAGUCCUGUCCAUGAGCUCUUAACAUUUUAUGACCAGGCACCGACAUGUCAAGAUUGUCUCGUCUGCGAGACGAAUACCAAUCGCCCUAUCCACCAUUACAAUAGCCAAGAUUUCCGAUCGGAUGCCGGGUACACGGACGCCGCUUGUAGCUCUAAUGCUAUUCCUGCGUACUCCCAUGAUCUUUAUACAACUACGAUUUGUGAUAUAGAUCCGCAUUCAGACUCGGUGGCUAUCUUGCUUAAGCUAACUAGCCAUUCCUAUAUACGAGUAAUUUCGUGGAUGGAACUUGCCGGCUUUGGGCGUAUUUCUGUAGGUGUCGCAACUGCCGCGCAACUGAUCAGACGCAGCUCGAUCGCGUUGCCGUGGAAUGUACAAGGUCUGAGUUCAAACAGGGGGGCCGUCAGCGAGUGGCCGAGAUGUGCGGGGGUUGCACUCGAACCGAAGCUUGCUAGAGCUCAUACAGCGGUGCGUUAUCCGGGUAUUUCAAAUGGUGGACGGCCAGCGCGUCGCGAUGCAGCAGCUACCUCUAAUAGACGAACACAUAGGGUUUGUGAAUUGAUCAUUGACACACGCUUUAACAGUUCCGUACUACUGUCGGUGUACGGUCAAACCAAAACGAACAUCAUCUAUUUCCGCGGGGGCUCCAAAAAUAUCGAACGCAACAUCAAGUGGGCUUGCGCGUGUCACGCUGUCUCAGACUACGGUUCUACCUUACAACACGAGCCGUCUCCGACUACAACAUGUGGGCUACUAGGGCGGGAAAAUAAAACGCCUUCAGACGUCGGGGUGAUCGGCCUAAGGGACGUUUUGGUCGAAAGCUAUGCUGUUGACUUAACUAGUCAUCGUGUGACUGCAGGGCGUUGGACUACCGCACGUUACGGAGGCAAGGUAUGGGGGAUGCCCAUCGGUGUAGCAACAAAGAGUUCAGAACUGGGUUGGGCCCCUAAUCGCCGUGGAAGCCACCAACGUGUCUCCGUGGAGGAGAGAUAUCUGUCUGUCCAUCACGGUGCUCACACUACUGCUUGUUCUCCUUGUCACCAUAGAGGACGAGUAAGACGGCGAACUUGUGGGGGGGAAGCUAGCUGCCCCAUCUUUCCCGCAGUGGUUUGCUUUCUAACGAGCGGCUCCUAUUUACCGUUCGUUGAGUAUAGGCGAUCACAAGUCACCGCAGACACGGAUCUUACCACAACCAUUCUAUCUCUUGGUACAAUUGGCCGAUUAUUCGAGGGCUAUAUUCUCAUUGGCGCAUCGGAACCUGGGACAGUGGGAAUAGAUGUUCCGCAUCUAACAUCAUCAACUAAUUACCGUUGGGAGGGCACUAGUCCAACGCGACUCCUGAGGCGUAACGAGGGACUUGAUCUAAGUGUCGUAGAACUCCUCCGUACACCAGUCAAAACAACAAGACGGGCUAAUACACAUUGCUGGCCAAACGUUAGUAGUCACGUCAUUUGUUCCAGCACACAACUUGGUCCGACUACCACAAUCAGCACGUUGGCAGGUGUGAUGGCGUUAAACACAACAGCUUCGUAUAGCGCUCGUCGUGGGUCUGACCAUCUUGCCUCCCCUGCAGCGGGCAAGGGCCUAUGGGAUGCAAGAUCAACGUGCGCGCAGGUGAUUCUGGGACGAUAUACGCGCUUGUCGCACACUGAACCCACACGUGGAGCAACUAGAAGCAAAAACAACUUCGUUACCAUUCGCAAUGCGAGAGCCAGCAGAUCUUUCGCUAGGCUUUCCAUGUUUAGCGAACGCCCAGGAGCGGAAGACGGGUCUAAACAUUAUAUGCCGCGAGAGUCUGUGUACGCGAGUGUGUCUGACGGUCUUUGUUUCUUAGAGCCCCCUACGACUAAUUCUAUAAAAGAGCCAACCGCAGUUACUCUAAGCACACGGGGUACCGCAUCUGAAAGGAGAAUUGUCCUCCUGCUACGCUGUGAGGACGCUGGCUUUCCUAAGUUCGAUCGCUACGCCUUUAACCAUGUUUGUUUACUGCCCUGUUUGGCCAAGCCCGCCUCCGUGAGCUUCAGCAUUCCCUUGGCGCUGCUGGUCAUACUUGCAGCUAUGAUGGCCUUUAUCCUUACACGCUCUUUCCGGCCCAGACACCGAGUGAGAGCCAGAAAAACCGUCACCGGUAGCGUAGGUAUUCUUAGGCACGGAAUCCCGCAAAGGGCCACGCCCAAAAUUUGCUCGACACAUGGCGCGGGAGCUGGUCGCCAAACCUGGAGGUUCAAAGAUGCCUAUCAACCUAUGCGAGAACAUCACCCGGGUUUAAUAAGCAUACGUCAUACAAGUCGCAUAACUCGUACAACCCCCGCACCCAAAUGCCAGCUACGGCCUGUUGACGUUAAUUGUGCUACAUGCACAAAUCUUUACGAACCUACCCCAAUGACUAGGCAGAGAUUUCCUUACAAGAAGACUGCCUUCUGUUCAAUCGACGAAUCGGUGAUCUAUGGUUGGUGUCGGCGUCAGUGCCUCGGCCGCGGGAGUUUGGCUGGUAACCCUUGUCUAUUUCAGCGUCUCGUCAAUCAGCACAUAAAGUUCGAAACUAGGGCUGGAUGUGGGUUCCAGGACCCUUCGUGGAUGCCACUUUCCACUCUGUUAAUACACUGGCGGUGGAUCGGGGCUGGGGGAGUGCAGUCGUGGUAUUCCGUCGGAUCCGUACGUUAUCGACAGCUUGACGGGAAUAUGAUAAUUAACACCCUUACAUUACCUGGGCCGGUCGCCUCUAAGGGUAAUGUAAUCGACACAGCGACGAACAUGCGUCGACGUGGUCAUAUGAAGGCCUCUUUACUGAACAAACCUUCAAUACCCCGUAUGAACCGCGUCACAUGGUGCCUGAGAAUUCACACCACACUCGAGAACGGGACUGACACGCUAAUCGUGCCGGUGCGACUCAAGUCCUGCUGCGUCACGAGCUUGGGCGUGAAAUGUGUCAGAGUCAGUAUGCUUUUCUGCCCGCGUCUCGCCGCACUGUGCCUGCGAUCAUCGGCGAGGAUGGAUCGAUCCGUAUACCCAGCCGUAGUCCCAUCUAUCGUUACACUCCCACUACUCUUUAGUAUCGACCCAUUAGUGAAGACUCGACGCGUAGGAAUCAUCGACGUAAGUACGAAAACAAAAAAAGGAAGAAGUCCCGAAGAAACAUGUUGUUCUUUGCGUCUAUGCACAAGUAAUCGAGGAGUAAUUUGCCAUGAACAUAAAGACAACAGAGUUAGUUACCAUCAAUUGCGGGCGCCCUGGCCCCUGACCAGUAGAUGGCUCUAG"


        val result = translateRnaCodonStringToAminoAcidString(str)
        val expectedResult = "MTRRSRKAFKALNVDHSEPDAILTPSAAEVAQACLDGSSIWQKDQDITAIQSTRLRLERDSVAYSNYCKRCSYHNWSRPVLGTMLDPQRCHDTTDRGHNFERLIMCRLSQCVQGVVRRAPASSGKRVSSGDESPARRQYTDLISTRSTIELTSLALRTRCTTSKHWASWNAAIGHRGCYSLKQSAHYAAPVGRPYPLIELAFVWVKLTAIQLRLYTSSISIPSRLIGPLVGPTRFVAGRAMKLRSTLSRRVQLCFNWAGDVAQRWRLKHRDELVTTIGRLQFELRAPRIASAPGPKKWHTLIVRPHSNNWHSGAPHFGNCSGRTIVVLSLNTRTDMVLHPSRHYASHPRVQQSKTKLAANIYTTICETSLGTRACGNTLNLRGNSIAPPVRHNHTCLGHLDRLCPPRCHLPIVSGSVPRLVSARGCCAFQCTRPLRTIQSMFYRHRRYRPEGGHLQGCAKSPYGDHRDPVRRLSRASRKEINILGNRVDSSPNVELVLPWIEKPPRQNRGNSLIPARVGYVQKKHTRVNAQLAPNEFYIGWPMSVISFQTRSAPTYIQPPIKIWTHATLERKLDQATHLDGTFHALSLDVGADQHLKAAQWKNYRGSCLDLYHSSSHQLNRPPTSRSELPWRTSCPSSMLPRNRNFNLKLGRKTHQRRPPWAIFSLRGVHGVALFSNFGSDGSPHGVGMYISRTAIVEGFSYLHLKLGLVALSYQRAFRPCGRTLKIHWRQLRLVCSICKRHRRLDYRPTCDNSGKGSRICYCAANAMYETGVTGTSFCAWGPTTKLDTLLIVPLNETDNIMNPCSNSSVLVNLEVQTIQKVQYVTFLSGSGLAVSASHENPHYQQPWLDLLRRRSDTEVLHVHALFSMMLCFGAFYSLASTPKQHTLLLISRPGTLEPHYALNGCRSVSMDCSFSWAAPLYEPSSWTLSYLRMTDLSLPQAYTVMKGTAMPLTIHCPSIDLHQDIEGIKVVDVTGTDRYYRFCLSYTTLNGESSHIRIPSPLLVLYIPGELSTFPHVIARPSPKTHVLSCRGMVWAPRIASSASIVGYVQSSYIKPTARTNGLYIDTAHAAVRYKRPSPPLPIQGSTVMCLEVRVESSRSCCLSGAVLRTHATRSLLPIEASAVCWQVNLLDHMIPTHLILERVDPCRRRASRSGGLNSGGYGGQYEDHVARVVSASPRRRASFHSRWHCRQVTIPCKGIIYATEPFGIGLHPPVLREYGSNGKHWCDIHRALFVRTSKVVRFRPNGRSSTAYTIRLSTQPIYAMVRPSGINAVRFIYVRPRRVVRRFVKRDVSIEGSPSEPSRNYILQEGRCNRKMQQAGRVVKLPNAWLFPRRLLIPRFSFNKPPTPAFEAILYSHWLGSAHRQNFKSVAKSSEPLQVCQHFVYPLVRRVRARSYLSPTLSCHIKRSVSSTEVCFGKEIQQREDSPPYAACKEVRSISGASSYREPSYINGVYFCPMSDRASRPPVDTGSSPPDVRILVATLFCLCRSTCRRGISKPCTPRIIQSMLKAKVKIYRVPEWIWRLPDVRVGRYGMSKSVLKKCTWNYIANCLIGARYWVRWLCVISGRTADEYYLKRRLSYMYLWSSYNMVWLSIPVLRVPMWRTPIRRFLVRVEHILMPDSHVMFHPSQKNRRSGNSIRGDLKGTHCLHLGWQQEPVLGTTLTFSYSSQFLATTVRLHLLVVWCARTYDEPVGNESLSGPELIPVLPGASIYVGSLYVSFRRNTVTTPSTVACTWKSLRVKCSFDNRHAHTFSDELEHSTCSEVRHSNLLNEVWSRDYTRRTVSDHALRRSCLSLSQALLGVWCGGGGFIGVQGSLDHATVSYCITVVYVSRLALTAQTNLVLLSYQGITSEFLIVSVIVILNDRPFKSTTTLIHRHLASLCGTNGLGGHHSSFILVTYGSFIYPRKRGLFLRSRGLSKADISVGLNPAYLQRSSGTLYAGSLQTTCRTRRIRYRRLRPIICHHPCVPKSVVLRLTLPRRKGVCAGPPHLIVGRTLVGNVLTILQRLLGILQEAPGCAGYNRKNHFQGLAPVTKSASLRMPRHDRMSRASSPKERRMLRILDHSPWVIGSTSVELNQCNGTKVVVRPPEIILAVLPAYRSVCYPVVQGIRGASRRAVNSEHVRCAVILQSWEAVILVAGMSLIRPPPDTAVTAPRADGCPPGGCVLDENSTKRYPSESVRRSKLGIRLRQLRLDIVLDGTLDRLVRPTKNAPQSSPVHELLTFYDQAPTCQDCLVCETNTNRPIHHYNSQDFRSDAGYTDAACSSNAIPAYSHDLYTTTICDIDPHSDSVAILLKLTSHSYIRVISWMELAGFGRISVGVATAAQLIRRSSIALPWNVQGLSSNRGAVSEWPRCAGVALEPKLARAHTAVRYPGISNGGRPARRDAAATSNRRTHRVCELIIDTRFNSSVLLSVYGQTKTNIIYFRGGSKNIERNIKWACACHAVSDYGSTLQHEPSPTTTCGLLGRENKTPSDVGVIGLRDVLVESYAVDLTSHRVTAGRWTTARYGGKVWGMPIGVATKSSELGWAPNRRGSHQRVSVEERYLSVHHGAHTTACSPCHHRGRVRRRTCGGEASCPIFPAVVCFLTSGSYLPFVEYRRSQVTADTDLTTTILSLGTIGRLFEGYILIGASEPGTVGIDVPHLTSSTNYRWEGTSPTRLLRRNEGLDLSVVELLRTPVKTTRRANTHCWPNVSSHVICSSTQLGPTTTISTLAGVMALNTTASYSARRGSDHLASPAAGKGLWDARSTCAQVILGRYTRLSHTEPTRGATRSKNNFVTIRNARASRSFARLSMFSERPGAEDGSKHYMPRESVYASVSDGLCFLEPPTTNSIKEPTAVTLSTRGTASERRIVLLLRCEDAGFPKFDRYAFNHVCLLPCLAKPASVSFSIPLALLVILAAMMAFILTRSFRPRHRVRARKTVTGSVGILRHGIPQRATPKICSTHGAGAGRQTWRFKDAYQPMREHHPGLISIRHTSRITRTTPAPKCQLRPVDVNCATCTNLYEPTPMTRQRFPYKKTAFCSIDESVIYGWCRRQCLGRGSLAGNPCLFQRLVNQHIKFETRAGCGFQDPSWMPLSTLLIHWRWIGAGGVQSWYSVGSVRYRQLDGNMIINTLTLPGPVASKGNVIDTATNMRRRGHMKASLLNKPSIPRMNRVTWCLRIHTTLENGTDTLIVPVRLKSCCVTSLGVKCVRVSMLFCPRLAALCLRSSARMDRSVYPAVVPSIVTLPLLFSIDPLVKTRRVGIIDVSTKTKKGRSPEETCCSLRLCTSNRGVICHEHKDNRVSYHQLRAPWPLTSRWL"
        //println(result)
        assertEquals(expectedResult, result)
    }

    @Test
    fun tempTest() {
        val peptide = listOf("Val", "Lys", "Leu", "Phe", "Pro", "Trp", "Phe", "Asn", "Gln", "Tyr")
            .map {
                it.lowercase(Locale.getDefault())
            }

        val lookupList = peptide.map { amino ->
            aminoAcidsTable.find {
                it.abbreviation.lowercase(Locale.getDefault()) == amino
            }
        }.map {
            it!!.numCodons
        }

        var totalNum = 1
        lookupList.map {
            totalNum *= it
        }
//       println(totalNum)

        assertEquals(6144, totalNum)  // correct per stepik!!

        // now convert to the single letter code string

        val singleLetterString = peptide.map { amino ->
            aminoAcidsTable.find {
                it.abbreviation.lowercase(Locale.getDefault()) == amino
            }
        }.map {
            it!!.code
        }

//       println(singleLetterString.joinToString(separator = ""))
    }


}