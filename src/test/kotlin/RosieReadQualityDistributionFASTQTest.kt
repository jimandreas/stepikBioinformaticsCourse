@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.FASTQutilities
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * See also:
 * http://rosalind.info/problems/phre/
 * Read Quality Distribution
 *
 * Problem

Given: A quality threshold, along with FASTQ entries for multiple reads.

Return: The number of reads whose average quality is below the threshold.
 */

internal class RosieReadQualityDistributionFASTQTest {

    lateinit var faq: FASTQutilities
    lateinit var u: Utility

    @BeforeEach
    fun setUp() {
        faq = FASTQutilities()
        u = Utility()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Rosalind ReadQuality FASTQ (PHRE) sample test")
    fun rosalindOverlapGraphSampleTest() {
        val sampleInput = """
28
@Rosalind_0041
GGCCGGTCTATTTACGTTCTCACCCGACGTGACGTACGGTCC
+
6.3536354;.151<211/0?::6/-2051)-*"40/.,+%)
@Rosalind_0041
TCGTATGCGTAGCACTTGGTACAGGAAGTGAACATCCAGGAT
+
AH@FGGGJ<GB<<9:GD=D@GG9=?A@DC=;:?>839/4856
@Rosalind_0041
ATTCGGTAATTGGCGTGAATCTGTTCTGACTGATAGAGACAA
+
@DJEJEA?JHJ@8?F?IA3=;8@C95=;=?;>D/:;74792.
        """.trimIndent().lines().toMutableList()

        val qualityLevelExpected = sampleInput[0].toInt()
        sampleInput.removeFirst()

        var numBelowLimitReads = 0
        while (sampleInput.size != 0) {
            val qualityLine = sampleInput[3]
            val averageQuality = faq.averageQuality(qualityLine)
            if (averageQuality < qualityLevelExpected.toFloat()) {
                numBelowLimitReads++
            }
            sampleInput.removeFirst()
            sampleInput.removeFirst()
            sampleInput.removeFirst()
            sampleInput.removeFirst()
        }

        //println(numBelowLimitReads)
        assertEquals(1, numBelowLimitReads) // sample answer


    }

    @Test
    @DisplayName("Rosalind ReadQuality FASTQ (PHRE) Quiz test")
    fun rosalindOverlapGraphQuizTest() {
        val sampleInput = """
21
@Rosalind_0000
CACCCCCCTTAGATATTAAGTTTGCTCAACCAGGCTCCTGCGCATGGAGGTCGAAATTACGCTAACTAGGACATACGAATTTCCCCACAAAAGGTGAGTGTGAGACTCACCATTCTGGGGCTTGCCCATTAAATGCTAGATCCACGCTCCCATAGTAGTTGTGTTCCCGTTTGATTACTCAAGCTAAAAGAGAGGAGCAA
+
DCI9H>=HCFE>@B>H8JAB?@@D:=DD;FF>C<@;EF??FJJ:7CHJ?BFC@?@D<ABCF:>GC?;JC=DCGAH=<?B?;?>:@>@7A>><9<<6??5AA>HJD;@=>><8*A>JI7;BA?>>D?B?9?EB>;;;@DC::<E=87?;G<?A:648B=><A??G29A96>;@;9E<>:=D;9;8:=:996965555=,0'
@Rosalind_0001
ATAAACCGATATTGGGCTGGCCTTTCACAGCTAGAGTTAGCGGTTGGCCTCTGCGAAATATTTAAGGCTGCACATAGGATGTCTATAATCAGCGTGGCTATCCATGAGGATATCGCACCCTAGCATGCTTCGCTTTGAAAAACGCATCGTCGCAATGTTGCGTAGAGTTCAGGCCTGTCGCAGTCTTATTGGCAGTTCTC
+
H=>H:@CC>G:=;?IC=>IBEG<DE>=;@F=JD8IHJCBHGA=F<@EHAHA:A<@@;:ABDH?AD?=CCHE;G=DF>8F;?D=IAE?;>H:C<;F?<CB?I@;H>A88B@C;F<D8?>@4D?9,>B@?@G@C?C@D>A;<CC/;@???<;35GA<A7A@B:@8DD9=9?7@9@877@<4487162?5401/04?4+34--
@Rosalind_0002
CTCGCTTAGACTATTACATATGATCTAATTTTATAAAAAGACACTTGACACGGCGTCGACCCCAAACTCCGCCCGAACGAGAGCGCTTATCTGCTGTACCTTGCCTTTAACCGAAGCCAATTGATATCAATATCTATCTAAGATTTAAGAGACCTAGGCAAGGCACAAGATCCAGTACGACGCGGCGGCGGGCACGGATC
+
JA<7?>=<?DJFB;??E:@CDFA;EA:D9:J??>:F@@FAE=?>>:CB=E@@B>==?9?>JG<;@@==;<@A?:;6E=?>C8A7BA5<=;A@@?@@A@48AAA>6=DB:??F@A1:<=<=><9>:F9:<6B=88;B<6@6A8=5984>==65@7><587B=7A::;>>@;2::859?382=;=6?9+467:3*654/37*
@Rosalind_0003
AGTCGGAAACAAACACATCTGTCCGACATATCATATCGAAGCCAAGACCAAGATCCCCCGGACTTAATGGCTTCTTGTGCGTCAGCGTGGTGTCCTGCTGGATTGGCGGTGTCACATTAAGGTCGAATTACTTTGGCAGTAAAGCCTCCCCCGAAGTTAGTTACGTGTCCATGCCGCATGTATTCACAATCCTGACGTTG
+
GCADADG<G:@D><B?BD>F<>;EF8HDH>BCGG<;<<>6G;I:H;JB<BCFAE6>?;CJGJH:>>DA6FE?=;H==@6@?<>@C@>C9EA:::BD@I>F8?D@?6E;A9@F@?<9?4AEC@;D9;J:46@;D5@6678>:@@58>B8B?;?G86=78;586::<5E=B9;85D>>58:50<@<67:;774<:646=/2*
@Rosalind_0004
GCACTGTACTCGTATGTCCATAGCTTCGGTTTAACTGCATTCCGTCACAGGCGTTCAGGTATCAAATTCTACTTCTTATGACCGATTCGGCAGTTCTTTAAATAATAAAATACCTAAAACTTCCCACTTCGTGGGCTCTAGCTTTATCATTTAAGAGCGGATGATGACGCTGAACCGACCCTGCATCTAGCAATACAAGG
+
7786:<;<6436<)747>972;3(9>6?=586262246276420;033=6.8@5;:3369896982;830381685353897:4:708576,39=44217/:5<.C37212*<?3(1424532532;*60:B838:7.4@9545/502-;305-1.-65,03.3--)${'$'}-.0/52/0*-05-(2!5960.//'3*+#!+##
@Rosalind_0005
TCCTGCTCTTGCTTGGTATAACCTATACCGGTTCGGCCGCATCCCCTGGCCCTTGGACGCCATGACGTTTAGGTTCTGACCGCCGTCCACACCTGTAGTAGATCCGGTACGCCCACCCATCGTACCACCCGTCCGCATCGTACATGATTCGGATGTAGTTGGCCTTTTTGTCGCTCGGATGGGAAATCTTCAGGGTTACT
+
AE=IJEG8FJJABDA8=?IG>>>>IBADEA?B@FD9E<9@7EBDCAHA@@G8;:A6I5AD;E>BA5=?<F@C?:9A;6@A>A?@==F;4@<4A:?AII@A>C?<>>@8=>8C2BB>>???<>3C;<A4A=E>?>9;F6A:>?@@;@;0CC;<?A@87=86:B><@E9;;4574@28>8<G6;;77835@<**:<@3,010
@Rosalind_0006
CACTTAGCGGAGAAGGCGTTATTACCGGACGTCGAGTAGCTGACGACGAGTATGTGCTGTTTAAATCAACCCCCTAAAACGTTTAACTATCTATCGACAAAGAGGGCGGTGACGCTAGAGCCATCAGGCGAGGATTTGTGCTAAGAGTCGAGATGTACCAAGTAGTGGACTGTTTCGAGCTATACAGTAGGTCCAATTTT
+
JJ6==>G@DJ:9H@6@6JJJ?BG=@CJC:EEED@=CJ@G?BAC:@BEB?A@>>JAJE=EB9JJEDHCBDB<A9BD>=>::<F@F:F9EAA>BEC@C>DG@9?B?<;GAA;E<9:689BD8?G>GH>?A==?EA@=;:G?@?:@>7A=>A<<7B@><:>?ADAAE<?1<;8=J;E7=<;>>D;625904<;8@71@0:50*
@Rosalind_0007
GAACTGAGGTTTAGTAGTGGAGGCGCGTTCGTGTCACATCATAGGGAGGTTGCCCCCCGACTCCTCTAAACTGACAAGGAGAGACCCCATCAACTTATAAATTAAACTTCGGGAACGCCAGACAACCCCGACGTGTTCTAACCTAATCGAGCTGAATCGAGCGCAGACCTGTTTACGAAAAGTACATTGCAAACTCCCCC
+
8GG;JDCAB@F=?@H??DAG<A?G8<CJ@?CBFJ=EEAH>HGDC=@JF;JAC9JE@C=BH?=<J?DCF=J;CGJC<:J:AEJJC=;>CBEFCB>@FG@A?CB=?>@DHB<A@@A@D3;9BDH;A>C7>@A;<A;=9>9>A?<2==4C?@F?>A=?BF4>;G:<??5<D:9>6<2:;8;CF@;1447=G6<8.5;478580
@Rosalind_0008
CTTCTGGGTCCCCATATGCGTGCCTACTACGGGACGCTGCAGAGACAGACCTCGCAGTTGTCTCACAACTCAATTACCTGGGGGGTATGATATTCCCGTGGTGGGTAGACTGCCCTTATTAGTATGGGCAGAAGACTAACCTAACGATATCCAGTGGTCGTGACTTCAAACATAGTACGATCAGGTCATCGAATTCCGTT
+
?:CJBD>ECC>B:B@?=I>BEIHG?JEB?G8=8EDDB=@GH8C<GFC@AC@JA?FBBEE@;?8<F<B2=>9BA>A9@C=:?=@>BF?D@A:AE@<?=7AI=>6EEFE9B>EGD58G<D<@?C====D>A?A5HEBF>9@<>G9?>3CEG?>BD@=9@7G::8>?>@>C3<88=>A:5;;0:3E6;4:5637874</,/3(
@Rosalind_0009
ATTCAAGATCCAACGTGTCGAGGTGACTCTGGGGCCGAGCAGTAATGCGCCGATTTCTAATGTAGCGCGACTAGTATCTAATATACCTGCATTATGCTCTGGATTCTACCTGTTCGTCACGAAAGGCAGGGGAACCACCTCCTAGCAGGCGCTTATGGTCTGGGTACCGCGTAAGAACAAGACTCCTACCCTACCTTCAC
+
=>>@?@;J;D?<D6H@IC><5H@D>HD>>>>F?BEI<A88BA?C@E>J9G:J;@E>>HE:@8;;<=@6>F8=<C:>C<=??H@=A@@AG8?<>87>=;B@AB;=CAA:GGF7D8=<?F>8A?A4J:<;E?7;6B9AB<=@I<69<A<9>@84;;>A28:9<;6E7=7<<6C:;?48:7<37A;6A6,9>:;5.33<)49-
@Rosalind_0010
GTTTGAACAGTATAAAAGTAGATGACAGGCTACAATACGACTTTGACTCGGCAACTTTAATATACGTAACTCAGTGTAGCTGTCTGTTGCATGTAACAGCGGTAAAATAGCTAGAGTCTTGTCTATGCTCTAGATACACCCCGAACTTCGATTAGTGGGTCGCGTGCCCAGCGACGATAGTCTGATACAATATCAAAAGC
+
@JJ@:>J@AE@?@A=D@;9AEHHBAAHHAI;FJ=FJDEI?E=F>CJCB@I>@HHCDE:B?D>BGE=<EJ@JHI7;=C<DAB;E<DFHB?JEB=B;JCHAD@J=JBA8>H>J?ED?FC9C@JF=@D?4=E:A<B>@A@>C6;9=GEE?>>B<;BA6=?<@5C98<:>:<48>CB<<;>9B7@68@9>16;57776669434
@Rosalind_0011
CTAAGTGTAACGAGCAGAAAGGTACCCCCTGGCCCTCTTCGCCCTTGTGGTCCCAAAGGCCTCCCGTAAACGCACGTCCCAGATCCGTCTCGCCCTCTTTCTGAAAGCCAGTTGTCAAGGCCAAATTATTCTAAGTCGTTTTTCGTGACGGCGTTATCAATAATGCAATTTCGGGTAAGATTGTCGTTGATCTCTTATAC
+
@=BE=J:FB=H>??DBABHD@>?B?=D9A<?@F@D??FB<BIFGE=9:@::6GI=D>>AA7@CCFFD@7;;AEJ>EF;>=C@>>C9EBA?D:>E?CF?:AF9A@>:88@:>@:=BI?G<=:AA9>?=@=BD>:BF>?<:;?<<48E<2F8:?CC;95?6A=<>;5;=:;58A;@6C75AA5;A:26:9:5=2545/864.
@Rosalind_0012
GCCGGGTACATGCGTGCCCTGTAGTCGCAGAACTCTGTTCCTTAAAGCGGATACTCCACTGTATAGGATTTTATTAATCCAACGCGTCATTTAGGCCGATGGTGGAAGGGACAAATTTGACTTGGACGTGACGTCGACCACGACTAAACGTGTTTAAGCACGGTATTCGGAAAGACACAGGACGGTTTTTAAATTTCATC
+
A3EDJCH9@E?;EEA>CJ<GD@FHA=FJ?EC@A<:=@G?HE<<I=GGFA8C<=ACG>D?DA9AECD@FHIHB<@?:?>8>3;@>DGCEBCE<B=@>@7F@9::C:D@F:E?B92>@CB?=DH;>=I9@A7==?=C@@@48AA?7@@AD8?66789:D;<779@8:?<>D8>>@<79463=84?A32>316835,4,;75)
@Rosalind_0013
GCGGAGTTGGAGCGTGAGGTTTAACACCCGGTATCGTTACACGGGGCTCATTCGTCCCCAACCATCTCACCAGGTGGGTACTGTTGGTGAGACCAATTCAGCGATTCAACTCTCCCTGTATACCTCAAGGAGAAGCGGGTAACCGTTTGGATGTTACACCTCCGGGGGAGTAGATTGCTGGACTAGTATGTGCCAGATCG
+
4?56B5A6076<759<9<<52::<B4:-@6<4;<7<183?<:3<<85=5;=<934>9747:A5<:=:77:<:4<,4+/7:8529:8.13.1:95:;7:;:578:06663-<>2.333384877997462:375/3-331.6-646/>/7?/45557/-/50-9-+/0=4-6)8/00.*.54/)(--&1-.)5,&&.'&'!
@Rosalind_0014
CTGGTATCGTCTTTGTCGGGGGAAATAGAGCGCTCCATATGTAACGGTCTCCGAAGCCACTGAGCAAAGCGGGACGACTAGTATGCCAATGTCCATTGCACTCAGCGTCGGTGACGTATTGTCAGGAAAGGAGTCCGACAAGCCAAGGAAGAAGCCGGGGGCATGCCAGAGACCCTATAGATTAAACTGTGCAAAATCCG
+
895:>79:13277787@?4>=-8980A<3>69639:66503;:33<9;?*655-?72151->0518,3.2*2*89;#-218478</431.6/A7529*:)13755-;/710655A8342.;:51,74)>1425:6&69-3*27A;2+634-+1-388)20*&5.-'475/042.*230,${'$'},,,'%-,0..,${'$'}/0&&!%&(
@Rosalind_0015
TCACAGTTCAATACGCACGGGTAGAAGAGATCATTCCACGCCATGGCAAGGCAAAGAGGAGTGCTGACGCTGCTTCACCACTGAAATGGGAATAGGGCACTCATCAGACGTTCAAAATTCTAAAGACTGCCCCTCGGAGCTTGCAGGCCGCCGTGGTGCTTGCCGTCAGACGCCGAGGGCCGCAGTTCGATGTTAACTAC
+
B@9A:BE=:<?8<=@G>=>6>@2B9;B<7?C6B<?@==7<=GGB4DH97=?<<:<?1E-/96<>636CEC;C;;9>6>78A=7<2A>.A8A4>418<7:CA;<>6B>8?85:C79@7A@6>9=4=>B:67;:7<>4<?<5848<03495:79AA:;65?5<?@55;2.;:796367;7,3.=732262621+3${'$'}/2-*!0
@Rosalind_0016
TTGATACGTAGAAGGGCGTGCACCGTTTCGCACAGCCTTTTATTGACCAATGTGTGTAAAGGGACGCACACTTCTCCCGAGTAGCAAGCAAGCGGGCCTCGTATGGGGTCTGATGCACGGATAAAAAAGCTTAGCCGCGACTAATTGTGAGCAGAAACTCTCTTCATGTGCAACATCACCCAGCATAGCGAGTTGGATTA
+
)2992/-B0;94;8)29240;73.683>3/.<-208;0?-476/<326;332@3@.063;)=35137*395215,23:9=.220290.003;/3734117-92-;.765/,9159-)45.(50/5+/+;43-3179-460(:*502)0/)20,65*(+-54(430*25.1)/*'-,.+5,03*/0/3*!,)0#*+/'#!!
@Rosalind_0017
CGTTTCCCATAGGGTCATTTACATATATAATCCAAGGGCCAACCATGTTTTAAAATGGGGTCCCTCAATCCTAACGCCTTCTGCAAGTATGATGATGGCCAGGAGCTCCCCAGGCAGCCGATTACATGAGAATTCAATCATGACATGGCCTGCGACGTGTCCGAAGGCTGGTTAACGTCGGCGCAGGTGGTGACGTCGTA
+
7<5?;=D2>A4165<6154714097;<525940=6154?-3;9/:89;6::>76@?64/4>49:981/123<.41.504<648?;=9:.5<858,940;353295;./5>>-62;877:976/>-52623722/479:4/"514/--164369/36.60031.&6+5".2-1'4038,042,/*920,1&+%12-*5,()
@Rosalind_0018
TAATACTGGCACTATGTAAGTACGTCCCAAGCTAAGACATTATTTCCCCGGAAAACTGGCAACCACAACCGTTCCTGACATGAAAACCAAGTATAGCAACTGACTGTTCAGATGCGTATTTGGATGTAATGCCGATCGGTCACTTCCTTGTAGTGACACGACTGCAACATTCTTTCCGTATCACAGACGAATGGTAGCAA
+
BAIDCJHHEBAGC@BCCE=?JG?DJE@JHIH;BCFIEEHHJEJBHICHAAA?;>E>EG?BBHED>?CGBJHHGIC8:=HAEICA<ECDB?>CGG?;F@E?<AIE@@A<@:?@J??:9>>EEE<F<HD>AF=9<?B><B7<EA?<77;BB;;?7D=B9D@EFCF6@;<54EB<;9BG?0=<<-:=<:9>A@8B693644:8
@Rosalind_0019
CCACTCGGAGTATTTCAGGTCAGCGGCGGCCCAGTTCACTTAATCCTGATAGAATGAGCCTAAATTGAACTGCCATAGGGCAGCTTGTGCCGATGCGCCGTAGGGTGCGCTTAGGCGGAAAGAAGGCTAGCTACAAGCAACTAGACACTAGAGGCTAGGGGAGCTAGACCGTGGGTTAGAGGACAAGGCTCAACCTCAGA
+
70=.91<9)639<?/<6/7:820473777619>:1661309-;422?847>3/26<0.89:59<8/39-(7<?;8-547795*92,56-25.42+,39-B933,;02*571997./-250203+09**"759<45,1:1<47.-4.58/57'</&,)<'/..0..-2*261-,35*&(0,.,+)3,44*,&0),/,(${'$'}!!
@Rosalind_0020
AGAACTACCCAGCAGCGTTCCTCTTGCTACAGTTGAAAGCCGGGTGTTATAATATGACAACTCGGGTAAATTTCGAGAGACGTGATCCATATAGCAACTGCCTCTGCCTCGAGTGCTGCTGCAGCCGGCACAGAAGCGGAATGCTTCTGCATATCCCATGCTTCTATTGCAGGCCTTGTCGCTAGTGTACTAATAGGGAA
+
9::6:56.7>8;3823:<::<6458697>63;75;9:A7/;28>6.559:7/59644=*366684=692@146684/7917>=03<99@/=9>1446>5664=854<;/583231628426=3165004725739555810227;28:5;75*6241;7-528/.03-25,5;(1<1-.258-//*1,*,'#/,%+%-0)
@Rosalind_0021
TCGAGCTACAAAGCCTCGCACCGTGAATACCGTAGTCCCATATCTCCATTTTACCCGAGAGTTACGGAAGCTAGTTAAGGCGTCCTTTTATGCTGATATCGCCACGTCTATCCAGTGCAGCTGACCTTGGCCAGCCCATCGGTACAACTGTAAGCGCTTTTTTTATTACTGGCAACCTTACAAGGGATTCTCTGGCGTGT
+
288>75711393=7268/43:1+>.9630<5..-'2/421+2:?<@3>4412*:;987.4=/15-01;4/572.-/32+895,209.)57;60-17/+(/<26/0-*)&01/4)20),2818/6-234099*5.,6;#10.40,/58**00:5/5.52+./41.,80*25/,/+)&.%./+%/-,00${'$'}+${'$'}))0+&#+!."
@Rosalind_0022
AAGTCCTCCCATACTGGACTTGGAGGTAGGACCATCATTAGGAGCTTCCTCCCGCTAAGTCAACTATAAGAGCACATTGGCGCATTCTCTTATGTCGTGGGGTCATCTAGTCGCAGGCAATGCCCCACCACCAGGGGATGTGTTTCGCCCGATCCCAGAGGGACATGGTTTATCACTGTACGAGCTATGCGCTCTATGCG
+
DCI:IEI>IDEIFH@JAHBCEFD=<HDAJC@JIHAB>GBJ@IBDDIFJCBH@E;B=F93:CA@HJ@??CB9A=;A@E<CAA?E>ABGDD<;?FBFCI>@E>=FIFF@;A>7BE<;AGCI9;F?;D=B?B3C>>F@@88=??E=D==?<<D=AE;@@>78;@D<D=B>=5:37B?02@@5:B@3.71:@8253?/574222
@Rosalind_0023
GAATTCTTGCAGCCAGGTGCGGGCTGATCCCAGTTGATGAGCCTCTGTGCGCCATATGTTCTCGACCCTTTGGAAGGAAACCGACTTAAACAGTGATTTTGGCGCAGTAGGGAAGTTCTGAGGACTAGTCGAATTGTGAGGTAGATAACCATTAAAGGGCCCACTCTGGAGCGAATAAATGTCCCCTCCTTCAGCATGAG
+
A<8C@8?1>?@G?C@>C?ICEJ;CGB<BE<D>=GG?:;AD?DFCFDD=DHBI?D<@ACBC<=AB@EHI>DA=C9BAD=>7:EJ;<49BE;BD3D>:CBB?E:>HHA;>AA9=?>J;B>:==AB4/:=A@=?8@<@=8@9>JI::;;D:;;A8>77AJCA95:5=H6>A47:6A/658<11:759726:423:71?27;.(
@Rosalind_0024
AGTATACTCAGCAGTTCTGTCAGAGTAGGCGTTTCATAAAATGGAAGGCATATCCCTGAATCCTACTACTCCATCAAGATCCTCTTCCCTTTCCCTCAGCCATTACAGCTGGGTCACCCGCACTCCCCTCTACTGTTCGCCGTACTGTATCGTCTTGCCAGACTTTATAATGAACCAGCTCGGCGCAGAACCGAGCGTAC
+
J8@=;?BF9<99C2D87?B.@ED<B3<9:=;5?9<@=8>=8ACA7479<880D:@4>.@25E8B>F:<;?<:/B?><=979:>=CD6A9B8BAEAE.8;9957B:5@399?;;6829=@:2395?<675;349=J6:;@>9@880=3=8C5<7.:==32;<5679;874/3185530/<1:54.68538303.6340;(%
@Rosalind_0025
CGTGTATATTCGCAAGCGCGGTAGCTAATCACTAAGCTCGTGTAGCCAACCCCTGACGGATTCGAAGAACACCCCCTATCCGCACCTTAAACCCAGTCCCAGTTGAACCTTAGACGCTATCGGCACGATTCATATTAAATGACCCGCTAATATTGATCTAGCCCGGGAGCTATCGTGAACGTGCATAGATCGTTAGTATG
+
9@=A>;DA;?A<FB:B@C??8<<>@<?G8>DC46A6><0A1=@:5<A>>8?GA8:7A<>D?9>;=<>>7<;:97:5@A>A??H=<;8;:5?B?><:6>33@A<958=3:=3>3?>2<DA9?486==91:77>4C<8;<>@>9;0A:1@.3582:98=598473:35.31//481;288380%<43,-*/64-17*0',9&
@Rosalind_0026
TACAATGAAGATCTAACGGGAGGCGTTAGGCTTGTAACGATTTCATGCGCCGTGATAATTGCACACGCCAGGCGTGTGTTGACGAGATTAAAGTCCTGAAGTACTCGGGGTTTGGGTACTGCCATTCGGCCTATTGCCAATATACAACGGCCGGAGTCGCAGCACAGAGATTTTATTTTGGGCTGCACCGTGTCGCAGTT
+
;EH9?@FBJC<;?GH=@ECJBJEJJAFD9@FDD9DD@<>IA=@<JDBEA8AIGB>@IHGC;@D>I;DBFECGGB>J2FHJ<CCJEA@>:5AIB<ID@?B?@C?A@BIB4HJDC=?AC@B98>>@IF<:8HG@;?6BC<BB4><<H@C<>A?EC;E:@B=8:=CE4>;:F=A6??3DA:=A=<<B>>>48@05=907,+2.
@Rosalind_0027
ATACATCTATATAAATTTCAATCCCCTGATCGTACAATCCCTCGACGACAGAGGTTTGCAACGTATCGCCCAGTCATTACCTGCGCCCATCGCTCCACTTCCATAGCCGCGGATGTACTATTTGTTACTAAGATCCCCTAGTCGGTGGCATGCGCCCTTGTGACGGCGACTAGCGATGACTAAGTGTGCTGGTTCACTAT
+
ED;E<:8AC?CCAE>BB@96B>AB>JGAC<D:=F<@G=6=<@;J@B===>FA>>;A<A:;:>B<A>8=FF>:47<EAB<8AA7@=ABJG>=<4@>?HB>;>9?=@86=<;6FB2A;F@E?;85E;578<8;A79@96;<798;A3?B>;@@:97E8;9?;B@<=A>9:;>;=237=19;A;,;-4.60888609:;02:1
@Rosalind_0028
CACCGTCATTTCCGGCCGATCCTGTAATAATCCGACTGATCGGTTGACGAGAGGTGCCCTCAACACAGGTCCGTTTCGTGACCTAGCTGAGACCCCCGCTACCCCAACACGAGCCTTACTGAGTTACCCCCCCGACTCGCACAGTAATGTAAACCTGAGCTTTCCGTACATTTAGAAAAGCCGGTCAACCTGGGTACTGA
+
BFD>A;;JDFHD=EAEA<>@@GD;AAA9GIIEHDE8D@>@9FB?B>;J9>7@;:<?BE>?=CE>B6>@J?CC>FIC??=B=>EBG8<>@:=E>298A;JAB>=;?BI3>;JAC><B86E=>;@?A;>>A<6:=??9?:@4?>>A7B@D:>A@7@<7==;6==;?7:68=A098>544A=:36>>=/7571321625570+
@Rosalind_0029
TCAGTCGTGCTTATTAGAGAATTAGATACGGATGGTCGGGCCTGCAGCTTTTCTACCGCTGAACCTGGTCCGAAGGGATTCACCCACGCGGCATCTCCGGCCAGCATCGTACTAAACAGGTAGAACTCGCATAAGAGAGTTGAGGTCCTTGAATATTTCGGTGACAACTTTTGTGCGGAACTATAAACCATCGGCGACTA
+
?6834:50487?5/89.2=30;465783877<0>=>.:1<53>988>:64:0*766;0A9946:544732<;678,7748=2428<4922A0:>984+789457892.01835<7;991:.6/964427<3-3766+=88458/68703,-/017+7-661-34*2021)7/1C22/4'4(.14(${'$'}0,/.&1+4%%"1*!
@Rosalind_0030
GGGGGCTCAAGAACTGACGGTCGTTGAAGCATAAGATCCTCAGCCGAGCATATACCCAAAGCATCCCTCAGTTCGCTGAGTCTAGGCACTCCTGCTAGAATGCTTACTTCACCGTATGTGTGGGCATTGACTAGACAAGGTTCAGCCAAGACAAAGCAAACAGGCACTTGCGGTGCATAGCGCGCGTAGGGGATCTTTAC
+
EE@@9GJDEJCJ>DJFAF9IBC@I@A8@FBD@JE>9BCJ?>@C?A9JCDECG>4CE9HGIBFDDG=JE>I8EEB>HD8<?<?CA=@@G:E9BB@>F@AA?A>?=AE9?;>D@CAC8JG<EC><FID9?EC9A@C;:56<@G@EE=>F?:B?CB78>HDD;?3@<C:57:J<8D?A<:81<==7;?9;=9651+<4A715,
@Rosalind_0031
TCGTGTTGTCGCCGCGTGATTCATAGGAGCCCCGAGAATATTGTGAAGGCAGTTTCAATGCAAATCCGAAAGGGCGTTGACGGTCATCCACTGCTTCGTGAGGCTTTCGATACTACCTTGCTGTCTGGGTGCTGAGAGTGAACAGCTGAAACACTGAGTCTAATGCTTCCCGCGGAAGGCCCTAATGTAAATACAACCGC
+
@56096124=1279511741664215>1-2436/742*)53.8867'564448,.*42426:<-44;25660805.462:510:/8:++:><.(3+:277632570,012//500--6;+4;,6473530/2515.-426,148+-6'./*)(/*${'$'}12/-.*-.004.(1&05),,,1%2'%,*1--00/&${'$'}${'$'},'+&&"${'$'}
@Rosalind_0032
CGGTGCGTGCGAGGAATAGGCCCGGTGCCACACCATCTAATACAGTATTTTATCACTAAACTGTCGATTAAACCGGTTAGCGGCCTAGCTCAATTAACGTCAGCGGATTGTTCTCGTATTAAAGGCCACGGGTTTGAGCGATATGACATTAGCACAATACCCGGTTTTACGCTCGAGCACGGTTACCGACTTCCGGCTAC
+
528/@/173=2771/57<607439D<12026-735-/7<771.71;91:51524D;28/8/3-6/53476/113/3833995;3=3.0:11346053469./108:59:15441+4.426011.(7216263/1+3+.21=2+<2744-/42(,27*)7-670*,.'92%*843%6.+()3)!1%.(!*),3'*.%%#!!
@Rosalind_0033
ACCCCTCCCTACCATCGCGCACTAACGCTCCTACCGGGGCGAGCGTGGAAAACCGCTCGCCGTATAGCTTCGTTCTCCGCTGTTGTCGTCGATGCGGTGGGTCCCGACAAACGGTCTCAGAGGGAAAGATGTCACGGCCATGAGTGCGACATTCTAGGATTACCTAATCCGCTAGTTCCTATCGCGCGTCTGACCCAACG
+
;>6<;6@@B7<=EDA:@B=?4ADB?G;C@=:?9;9=E:6B9@>D7@??@@2C@C7>J9:B85<=9A@A=<>3C74<>;9:99==;=47:7=>=D9=:F=;5BD@;9E878=3BE;9@:<=858:::?:<=4@>:9?A:7<=5G629=A=<=:@:7?;58;F026<?5773<14/806.515/5225<6>.30--+*,,.!
@Rosalind_0034
TTTAGTGGTGTTGCTCTACGTAAAGTGCCTAGCAGTTTGAAAGAACAGGGCGGAAGAGCTAATCTTTCACGCTTCATACCCGCGTGATAGGTCCCTGCGTACAATTTGTAAATGGCTATCGTAGGGCTACCGCTGTTGTCCTTCACTTGCTATGGCTTAATCTCGACTCATCGAAGATTTAGCGATGGCATTTCTCGTAC
+
47;16)592313;80:=/4:;.2377:2.65>=::85@.4934.06,51240657-515;09/2=2+7667,2/.30:9;3890//790656+1.334;8;.84154526/87113.3*61/+9/7116<1+9;/0001.2317//81-/6%+3.2<22.5.+3'0+33.0*+../.-).*1')./&/5)','!*'${'$'})!"
@Rosalind_0035
GACCCCTGCTGTCGCTTACAAGTACACGGGGTGCAATAAGCTTAGGCATGGCTATTCCCTCTGAAAAGAGCTGCAGCTGTTTGCACTGGGGAGCACGCATGTGCATTGTGCCGTATATTCTGAGATGAGGGGCTGAAGCGGATCTGTGGATAACTCTTCGAACCTTTTAGCTAGAATCGCAGATACCACGCGGTTCGCAT
+
5E11::<B7>@7<98@4395@?7823<4D5<=7664>6C@5:2:;8C321>.83>6@>56A63;24.?7<7<6;:=6:=38:486:88.73:15<928<6<<;25;4>-7717<684@:2582?58,97849400136778A5>16/7+:=0/66:6./--5024:,.0/17*.09433253,-3/3+1,4-/,.*&&))
@Rosalind_0036
CAGAAGTCAACATGACCAGGGATTGCGTCCGTATGTGCACTGGTAGAGATTTTCATTGCCCCTGCAGGTATGGCCATTCTCTCGAACGTGCCATGCCCAGGTAAGACTCCGCTAGAACGGAGGTTAGGGGGGCGCGGTTCTAGGCGTCTACGTACAATTGGCGAAGACTATTGTTTGTCGCGAACGGCCAAGTGCGTCTA
+
B@JBIFF>ADDJ?HFC:AEDIIJFECCEF>6JA@B>E@D<?H@?9@FA?<7>B:?8=@?4>F<=ICD@BFBFB@>F:A;:@=?==H4?JC>B=9;BJFFAG@@GG=7?I:G>9E@;>6?@D7@:9A<D95@@<3@=F:B;=B>HA7?D<>>=;F:AB@7:59><;H=7C8G??64>;>49>=6;;075@62.8><812:5
@Rosalind_0037
CGAGCTAGTAAAACCGTCGAAGGCCACGCATCAAGCCATATCTATTGCGCAACCTGCAATCCGATTCATCGTACATCAGGCTGTGTCGGCGTCGCTCATCATCGGCAAAAAGTCGAATGCATGGTAACCCGTCAAAGGGGACAGTACAGATAGACGTGAATCCTGAGAGCGTCTGTCGGCTAGGAATCACTCGGAACGGG
+
;<=;:7448795<<997C08:*/)669;6694/4/:9866661882+4:762203<37<789;8-.273?56@@/::9/;;86;332?:-43=6=>7;7606633;5605975/23<,677=19835222/728.752706.56-5-,:95:1080,-2-695/<-423-1&3,'*0767+..(1-/0/.504.*1&!')
@Rosalind_0038
GTTTACACATTATGGATAGGTAAAATCGCGTACAGATTGAGTGGGCTTACGGAGCCCAAGTAGAACTAGTGCCACATCTCGCGAATCGGGTCACCTAGCGAGTAACTACCACTCTGGAGGCAGGCTTCAAATCCGACGTTAACCAGAAACAACCCTTGCTCTGCCTGCCGGGACGTTTAAGACACAATGTTGCTAAGGTA
+
??H9I=;=F?A?JI9>CJ=GJCFEI>F<?I7A?JCI>AB=;BIAB>C;<HDDFBG@=A<?@AA;CB>DEIGF8=<E?>GBGHECAG@E:C=FH:>?=<?9HBJA;><DJ:DG7=9@;C>H=>DC8;CAAD?EG=G;@;<8J@:;>@<?79=<BEC8B95?=A9:?<9:A;@<A87<=/B?9@:907=4.6A>8.68349/
@Rosalind_0039
GTACCTAGAGTAGTTTGCTGTCGTGCAGAACACGTTGCATTGCTGCAAGTTTGCTAAGTAATATCCATCTCTAGGTTACAACGGTGTGAATAGTAGACACTATGCTAAGCATGCGGAACTGACCACCATGAGGTATGACTCGAACTAAAGTGTATGGATCACCATCGACCTACAACTCGCGTGTTGTCCACTCCCGCTAG
+
;AE<8GE;=IGID:?@B?@>==DJ;<<BC@;>BB=@B<EB;8A@D==;CF;9?A7J:F@CC<A<95<7;A@D@:AG@C;<8A==5J@6<7>@>?<?<<>G8@8A96=:G9<FE:8E;BA5G5?D8@6?=71J8?B979<;?=;@EE6=5:?A@:57797D8E<3;96:3=?.4?8:?8@:2<85<1.0695@;041/34-
@Rosalind_0040
ACCGACCGGCGCCTCAATCACGGGCAGCCGGGAAGCGGAGACTTTTAGCTCCATAATTTCTTTATATTGCGTCATGCGCACTCATCTCCCAAAAACCTTGCCTTCATCCCACCCGATGAATCCCTTGACAAGCGAACGATAGCCCATAATGGTGTCCGGCGCACGTATGAACGAAAAGCCGTGAGTCGTTACTCACCTAG
+
JHD??>@=EFDA;DFF@:@HC=?A<D?AAJBI>9?<G<9?BE?<EBD>:>EH;B>@>DAC<J@>E=B?>C=G>A@A?@4D=7@=6B:@@A=8@<=@;5ADC=>?>ACHC:E7<9>;>@C@AD:>B@?<>7;G<3>8?>>BBA??C8@<@A;<97?@?EB4>>@6<8:D<?5;3@B9>987>6:5>>C96,5.2704450(
@Rosalind_0041
CTTTAGTGGCCGACTGAAACAGCATTCAGGGTATTCGTCATATGTGGGCTCATTGTCGGGCTCCGCATTCTAAGTGAAAGTTAGCAGCGCCAGGTGTAGTGACGAAAGATTCCCCAGAAGCTGAGAAAAGTCCATGAACGGAAAAAGTAAGCCCCCAACCGCCGTTCAATCCGCCGCACTCTATTGCCCCAGAAAAAGAT
+
9<A69=,;4:.248054;94.;.0<@74:565::7783@-9430842977877<<8651424027)57;8<2-37/4<:5:>48866,05422<0038</5-.3,9107004.;7,52734.4088+15<5+2/.94(3-6351214=/+,7-+)4,//6'30-61/010(/3+/.2%+1+/4/-/(-'+'1(,0${'$'}!()-
@Rosalind_0042
TAATCCTGCCTGCGAAGTCGCCTCCTGTCACCGTGGTCCCAACCCTGAGTTACAGTGTGCTGAAAGATATCTCGCCTAAAAATGCCGAGGACTACCTTCTAATCGTGCCTCTTCCAACGCTGGCGAACACTCGCACTTCTGCTCTGGGATCATTTTGCTCTAGAATTGGAGTCTATTCTTTCGATGCCATAGAATCAAGG
+
I@2>?94I4BC@IAC4C:9=@4B@;?A>A>B8DC:7A@;8;=E>A>8>>1@>:>?:?C:=9;?<:9EC:77<99D7;?;<:9>7F=7;98<;69A==>9358;:6E7<68C;768<==7:4G;C<677665:852B89:78>178672;;448:799:>2/64<@49@.887<<36;214061863=/7*;7/40.22-(
@Rosalind_0043
TTAGTACGATACGCGACCGTTGTGGAGCCAGAGACCTTCTGTGCGTTCGGTTATTCCGCCTGGCAGCTCACTTCGGTCCATAGGGCCTGGAAGCCAGGAAAAATCCGCTTCGGACTTCGTCCAGTAAGGCCCTACCTGCTAACTCAGGTTACGTTTAGCAGCGGATCAAACAAACTAGAAACGGGTTGGGGATGTCGACG
+
GEF>>?I@C>=>B=?>I??<9@BC@:=@FI<;B;?<=D?A?BA><=AD<>>@EC;=2A>A;?::9HB9A@;@?8G@H>@<?G=:=B><>D@F?>ICD7C?>>F<AA<D?B58:A:9=?A?<C>==@@<4??:5E8A==E8><:8I=34<8;BE388<5B7AC7?B,DD5?A@:<=997?3899:E<37705324485-+-
@Rosalind_0044
CTACTATAAAATTTTACTTTGCACATGTCGAGGTTCGCTTGCCCTTGCGCCCTGTGTACTAGGAGCTTGACCCTGCATCCTCTGATCTTGTGCCCTAAGTAGGGCACTATGCGTCCTTACTAATGACCCACGACTTAATCCTTCTCAACCTCCTCGCCTATGGCTCGGAGGCGATATATAGTCCCAGATACGTGTGGCTG
+
BD:CJ<?==AB=@HAE<>BJA:D=JEA9?E>@AF>79EJ=3F@>8A9BE@@HJ;@HC;ABA>>=;C?J<=G:FA>99E;;5==D9C8CCA<@=HJ9A@C;D:B?>9<<C<:D??<<4;A7E=8@@9?8;66:=:=@9<9<C:84>D87:?:8@8;820:78E:;?65;69:>8B;18;258?/4<5<6;4-:02-1-((2
@Rosalind_0045
GTCATTGCCAGAGGCGCCCCAGCGTTGGGTGCCAGCGCGAGCCCACTAACCCCATTGTATACACACGGCAAGCAGCAGATGCAAGATCACGGTTACGAGCCAAAGGCGAGGAAGGTTCCAGGTAGTCTGATTCACTAGTTTCACACCCGGTTGTTGCACTCTTCGACGCTACTCGACCTCTCCAGTGCATTAGCCTTCCG
+
9=365<<;:C9C35>0>97;477>A5?6549745;5;888<912=3:5:4?;<9>7,8A=@B74;255B7748996:A<>>994;;1<69/4;52:6;871@;725487919?5&;97/326225@985389269;2833-4630<737.:9.&4')548<.0,)402-9.4570/17-0+/(2(.22+*,0+!,%.0(1
@Rosalind_0046
ACTTCAAGATGATCTGGCCGTGATCCGCGGGCCGGTAGTCAAAATGCTTGGCACCACCTTTTAGTATAATAAAGATATTGGCTTCATAACTTTCAACCTGAGCGATTACATGTTGGGCAGGATCCCTCAATCTCATCCTGAGCGTTGCACATTCCACCGTAATCGTCTGTAATGCACGGCCGCTGGGCTGTCATTACGCT
+
AC=ECBFBE>DAAJ?J:CEH?:EEBB3>=<CF8DA9AE>>?DJ:F<?<G>?=G@B?9DH6CE<FH:HD?DABC9<<H@;F=G@D5<4AEJAF;@=@A>DAC?CD;=JAD@?BAB8?>C97@4D?<=><9?;<7=@C@B<798@=A8;=6>49J3B6@?586>=9>??;<A3<<=:<9;D?78@A49455997/43+3,0/
@Rosalind_0047
CCGGGGATAACCACACCCAGGGACCCCGTCTAGTGAGGCAGCAGAAGCAGTGTGCTACCTCAGTTCCCAATACATGGTTGCGTTTCTCAATTGTCAAGGTGTTCTTGACGTCACAAATCAGAAGCTGCGCCCAGCTTTATTACGTAACACTTCTTTTGGAGTCATCCCAGAGTTAATCTAGGTACGCCTCAAACATACCC
+
B>CGBBC@J9FDJDGFBC;A8BDG?>@E=EE=@<>EAFBD8E>IC@IACE@A7A8=;C=AJ@G=F>@GG@@ADBA@>BCJJG=FD<C?<JHJFCDBA7@7EBB>C>=?@=B@JC9H:BCI=?B===B=C=>E7B<:=?<?A@:0H@@@J:7;D77@AG9A>E;:;?;8>;5H3>>:@53B;7>B65?67<29?98056+1
@Rosalind_0048
CCCCGTAAGCGGTCGGCAAATGTATTTCTCTACGCCCGGGCATTAGGGGCCGTAAACAGGGGCATGGCCATGCTTCTTTGCTACGATTAAATTCTAGCCCTGTCCGCGTATGTCGATTGTAACCATCGAACTGAGCACAACTGTTTTCTCACAGCCTGGCTCCAGACAGCCCTTGGGCGCATCTCCTGTGCGGAGTAGAA
+
>@:>=:>88;>DJGG8;:;<>8A;7@89GA3G;E>8=8@?:?33A<@>G:8@A8?=2<9?J?DHB8:=;:8<=99CA:H9<?8?7@67<@8@397B>87;17<8H<;>7>96B?1D;=5<?1996<;=<19<=7=;=>><@7/A<=A9/37=6876<:B53A7.4469593=8<;7<1897;/<2,8>/1144)*2+'-,
@Rosalind_0049
AGCATACTCCTTCGGGAAGGGCCTGCGAGCAGGTTCTAAGTCCGAGGTCATTTCTCGTAGTCCGTTGCACGACGATACTGCAAAGATTAAAATAGTAGTGGAGCCCATAATTAGTGAGCTCGTCCCTTGTAATGGACCTACGACCGGCTAGGTGAGCGAAATGCGCACTTATCACACGCATATTACGATTTAGATTCTAC
+
J=D7;??DC=BF;G=C6H9;FE:;FC;<@??67<9BDE=5AG??>DBCDACF=F>6?97/F<A<<CF?A=7C<:8J4;BAA4?579>=:=@<DJGA<88B@6?:9?;H=EA9=;;:@:;><:806EA7<=D4?B58A6?9C;:7?>==997<@@=<6F5<69H69@39;;9>2A63852.5169085?;49;<2=96-%!
@Rosalind_0050
TTTGTGTGTAGTTTACGATGACGACTGTATCTTTGAAGCGGCCTCATGTCAATTAACTCCCTATACTACATCTAAGTATCATATGGAGCGTGTTGAATACTATAGACTGCAACCGGACCGGCGCGCCGTCCGAAATCGTTCAAGAGGTCTACCTGAGGTTGTGAAAGCAAAGCGACCGCCGTGGCTCGGACCCAATAGCG
+
7601:/303=659/764243232=<<-5A(-:92)46352112922*/815011/:+42//7.624149/18:3/<58.//46.8/3=/57:.44:64+4002842/817?2+4/;:;781*38-9256B52-6.0360.-6/,350/,,5.5-/9,.2,/.-201/0/-12/2)1&*0,0.62,/*)/)*'#*',-''!
@Rosalind_0051
TGCACTAACCACTTAACCGAGTAACGCGAATGTGGCGATGCCTGGCATGTCTAAGGACGCAGCTCCAACGTCACCAAGTGATGATAATCCCGACTATAATGCCGTTTCAGCTTCGGATAAAGGCGAGCAACGGCGGGTACGGGAACAATGACCGTCGGAGCACCTCAAAGGCCAAGAGGGCAGCCTTATCCCACGTTCGT
+
A8<6A40::676<+8::;5=15098/0446/;372944=:44186;5/=9.7/-<=6A60;64+/3889938430<3/553466589/21/91/<82-66=33/84866/::/1-.38.99;63165++226.0/8859;4263727&132*084//523%.4=1802.6./1*(-(!1/.11)-908),+,-*(+)*+%
@Rosalind_0052
CCGACTTGCTGAGGTTCCCGACCTAGGATTACAAGTTCACCGTGTTCTCGTCAGTCGATCTTCGGTACGTTGTTTGCTGCCTCCGGTTTTTGACTAACCAGTGCAGCGTGAGACGGTATGATAACCCAGTTATGGACGTTGACAGCTCCGATTGGACGTGTTGGGCGAAGACACCCGATTAGGTACCGGATATAGCATAC
+
>FEFJC??DFCJE@JJ7@F9EHHCCC9CEF<>H<GJABDCFABC;E:E;FJHHHJE?D>?B<>ED;C9JF;D>@7AJ>CF;<D<E;=>FGJEJF;IG?@FA>:G?><D:?;AH@CH=BG;A7F8D8=?;9>9>CE5<2E::A=:<JB6<<A7@D@EA<>3>=A?2C8C9?@::8;7>:<=4787196?7=<52548.7.0
@Rosalind_0053
CTGCGGAAGTCATCGCACCGCAAAGTTGCTGTTCCGAAGTACGTTTTGCTCTAATACCGGGGTGGCGGAAACCATGACCTCATCACACGACAATTTGATTATACATCTTGATTCAAGCTGTATCAATATCATCTCTGCAAAGGATTTAAACACCTCGGACGTTGGATCCATTGGGTATAGCTGAGTCATGAACACGAGAA
+
:856>2546;20/362>:89.6<6.29D/7>259826920:567605-61224/7577243/:48.495:7482:.155.34;,99442;,-70.0;:5492461587/*/3164,08747316.-.-/8%<8780019/.512*25352251*5/(,+(-1/1-161++30(06.0,.%(#-&3,.-/1-*-%!"),*#
@Rosalind_0054
GCATGTCAAACTCCGCGTATGACGCATCTCGCTAATGGTCTTCGCCCTTAGCAATGAGTGGATCGACCCTATACCACACTAAAGCGTTGGAAACCCTCAGGCCGTGTACCGTCTGGCGAAGCGGTCCCGTTCGTAACGCTCACAGTCGCTAACAACGCTGTCCGCTACATCATGCACGCATACCTTCACTTCCTATCCCG
+
I;@DFB<8@>C>@>BCG57>A>8DB@>I8GA@E;C@C=HF@=>?9G?@GA?>?B>?CG@9:@CEF;DA?DA;5BFJ7AGABA?GC>5>>5EF9>7B<@<89FAA:ID>?6JH?>:<6BC8?B8<=;D@?A7@=D:=7>J:4<5@:;:>:=53>8=<6=AA;=7:@=@4;5=<1>382=9@8999>+4=:9:7<60:583)
@Rosalind_0055
ACTATCGTCACTGTGATGCGTAATAATCAGTAATACGTACAATCCTGGCATGTGGGGACAGTACAACACACCGCGGCAGTAACAATCGCTTGTTTCAGCCCACGGGCACCCAATAAGGGTCATATACCGCGGTGACGAGATACCCCCACATGTGCGCTCGACGCTCGCCTAGCCAGGACAAGCTAGCCCACCGGCAGGGA
+
<9A676<75:458;8<72:955-8D:9:==5:9?548->B;:6=?482587;7<6<726=:5742>/343?7767?=668/2B6?5>416;:47495::29<91:7=;349;8579795.=40==8A9675>80714509+795:35-554;=5<1%9262/952415124388334+2/,0')'.3)%118&-0..&(!
@Rosalind_0056
CGTGCCACGCTCAGAGGTTCACACATTTTTACGGGCTACGTCACGTCTCTATGCAGCTCTTAGCCGTGGGAAGCGGGTCATAGGGTTTTACATGACCTTATTCTGGCACTCAAACAGAAGCGGAGTCCTCGAGGCAAACTTTTAATATACTACCGCCCTCCATCCCGGACAATTTGCGTTTCCTTTCACCACGCGGAGTG
+
EA=ABCCFDEA;;?D<I:D:A=EDEE=FBB>C=DJ>B@EI;:@7E?<@C>J:6J>DCAA>=D;JD@C8:C8@;FCE@A?FJEJ3>@A<CD;@H?D<:;AI=49;<?=AD>E;><A=@@A9;I;G9D;7>99;<857==@FA:913B3?8><2?7@??888:94586888<;A:7<9>;>4B::475787B91.487.7-6
@Rosalind_0057
CGAAGAGCTGCTAATTCACAATGGCGAAGTGAGGGTGTGCGTTGTATAGAACGGAAGTATTCCCCCGCATGCGCGCTCTACGCGCTGGCTGTAAGTGTTCCTATTGAGGCTAGAACTAAGTATAATACGCGCTTTACCACGCAGGGGGTCCGTGGACGGCTATACTGCACAGCCCGCTCTCCTTTGCAAGGCGTGGTTTG
+
37=;89<6B2772+445?957-83?-25?22402833=10>2/2411;3;8615>:092146-31,9,465376:89335;40-/,,74425:50-433881:346=,45,;+6.617278-30:5/6///7547:15937206+2588-):=/-/730114)132+16/53,3,/+4,5-2..,/0/.${'$'}(#+..&%%${'$'}#
@Rosalind_0058
TTTATGAAGGAACTCCTACAGCGCATAGGTGCTGCTGACGAGAAAGATGCGGCACTGTATTAGACAACCGAATTACTAACGAGCGGTGGACGGCAGGCCTCGTTCTCACTGCGATTCGGCGCTTTACAGTACACCTTTGACTAACATCCCGGAAGCGTGTCTCGCTTGACGTTCGCTAAAAACCCACCGGGGGCCTCTGC
+
IFJDHJFED@CACB>CAA@FDGHB<HJG>DDE=B=CCHJF=>BAHI9?<>C9FB?JJ@HFH@AHFBCAC?:AJ:DD=>>H?CD>7ADB=??F<AHF=?BF?D=C@A<@?FDF:C@:;B:9@;DFBE:FBI;39:EF>BC:=??2?2@<@J<?:?C55=?3?D?E:D7A??:@9:=:2>;7??5<?7<6493981:5/313
@Rosalind_0059
TTATCCCCTACGTGAACCCTAATAGATAGTCCACAAAAAGTAGTAACATACGGGCTGATGTTTAACCTACTCGCTCGGAAGATCTACAGTAACCGGCTTTAATCACCTAAGTGAATTGCAGCTACGTTCGCGCGTCTAAGATTTCTTTTCGTTGCCCGTATCATGTTCGAGTCGTAAGCCCGCAGGATTGTACCCTAACG
+
JGG<D??<B@G7CA<@D6=?AHC@<99ADA>EAEDBCABA@EB?@7F:E?B7A>>?;?E;F<;8C@9EE>><<;<BAHC==>CG@A2>=FDEBABJ<7?GHG=A=A@=4AG;A<:EC9BE@D7<G?@>=;=@??<<=6;BABA:@@2G2=7B:=G=97C4?B=7C=<BA.1@78;4<69@C=<E8>4:8:832?0+-.40
@Rosalind_0060
CATATAGTCACCACCAACACGACGCAGACTTTGTACATCACTAATGTCGACATTAGAACTGGGCGTTACCTATTACAGGGTCAGAAGATGGGTCAAGGGATTATAAACGACTTCGAGGCAGTGTCGCTGGATGTTCATTATGCTATCAAGTATGCTTTACGTCTTGACTGAGGTCCCCTTGCGACTTATTACCTCGTAGA
+
13,867;8>.<>@+8C9158A>?=5;A24<5;47733;7>885B5:87C52575567>5899<97<4701:76-;96;9:>/5427?41065(/37073:;/33:61,?06589.8:2@:20;7;-524=/C2</7<,725:34.:33.-65675-545+2-:52,90-(20191006,'/3/,/,72.)+(/2)5-!%!
@Rosalind_0061
ACTGGCCTACAACACGAAAAGATCACAGTGGATGGAGCCGTTGTGTCATGGGATGTACTGAGGCCAGATCGGCGGGAGGTGCAAAAACCTGATTCAGCTGATCTAAGCCCGAATGGCATAAACCATGGTCCAAGCGGTTAGTACCTGCGGAACTTAGTAATCGTGTAAGAACGGCATGCGTAACAGAGCCTAACAGTGCC
+
6:2>2787/38=83989>769/:75<728:89,8781)5;:8@714011043>6493<<,@50270:72;:772:174<60+7853742088>7083:75085.<7:7+526.>893886:3?1207447125/+01326;8:-73-..82>/068,82.22)4:/0314.%1()2,.3(0:)7,.001-/.${'$'}''/+")${'$'}
@Rosalind_0062
CAATTAGCGTTTTTCAAGCGCGCAATCGTATCGGTTAGATTTGTTCATTTGCCTGCGATCCAATTCGACTCCCACCTGTTTGTGTATATAATGACCAAGCCTGTTTGGCACATATAGCGCTGATTCCAAAACTAACGATAGTTTTAAGCTCCCGTGCAGTTTAATGGCGCAGAGCAATTCGCGGGCTGTCGCTGGTTCAA
+
?C?JDB3BF>GDIJGCBEC?BH;ID>J@FD=CB@FCF=HE5:DAHG??=JBB@J@=D6AC><AD<JADG>:CEE==:E?>D>?A5:?CAI@E>?B?=>B9@<B<DD;=BB9?@A@8@FG;<CBE7=<@C@H?<BE8=@56CB>;<ADAC=99D<9;6:6D?=J;??;>A;96F8D0<>A287<=:/;386?*:.51,083
@Rosalind_0063
GAAGATGGTAGCAGGACACAAACTGTCACTTGCCTCATACTTAAGCCCATTGTAGAATGCCATTTAGCTTGTGAGACATACTGAGCGTCCGTCTCACATCGGCAATGCGTTCAAAATCTGTTGATGCGGCGGTGTTACTGACCTGCGGCTAAAGCGATGTAGTTTTTGTACCTGGATAGAGGTGAGTCCATCTTGCTGAA
+
BJCJ@C=AHIJFCEAECG?@F=AIGJ;C7F:BCFCBGEJEB@?:GJGABAEJGDBB>@JJ@B:EA<G@J=@A:BD?CDJD<GFBAHGGEF?@@>=I>;HDEDB>E<:>CD6D6@:E8;C;?@><B9I=AJ@DD@D>D6=J<;B@A=>==>A;9?<>@75CC<?B=;=7627@><7=:5*A:;81E>39;:661<<+2./0
@Rosalind_0064
CGCCCTAATAAGCCGGACGCTGGCGCTACCCGCGACATTGCGCCGACGAGCGACCTGTTGTAATTTCCATGAACTCTGTTTTAGTAGACGAGTCGGTAGCAAGCGCCGTCGCTCAACTCGCCTTCCGGTGTACTGGCTCGGGGTAAGTGGGGCGAGGGGTAGCTTATGAGCCCCGATGGGAGACAGTATTGGGCAGGCTT
+
<;7-529+28;/157;5:718>2-/7?9711878::391<<536:7/7-80::4/3259787/7487,2+0668+521=).43,0.+--03(.74604052.-.587,591,/.75513198'--.75*364/4+173117/502!,74%;)<7.-5./3-)+.-'28-(*:%)+2-+.(+.!--),)${'$'}'1%/.%,'(,!
@Rosalind_0065
GAACAGGCTCGCATCTCACCTACAGTAATCGTAGGGGGTTCCTTACCCGTTACACCAGGAGTTTGTCGACGTTAGGTCCGCGGTATTGGCGGTCTCGACCCGTATTACACCACTCGCTCTTCAGAAAATAAGCAGCCTTTGACCTCGGAATTGGACGAACGGAATTCCCAGAGCCTCTTTTAGTAGGACAGCTCAAAAGC
+
A>DEJ@G>A>D:@C?FEFBAJJJ:@DB>F?BB?<>=9>I7<H@;DC<GJ9>@<JEC9C>=@>;EA@BH>;:>GG==?H9/>>=D;==E9:C??>I>E:=?@;H9D4;:=DA<J<<A?;A=9=?B<4GB6:D:FC99?5>@=3?@=F;I57@;HB74:>6539>;A78>@293=38B19;6@3.;77.4296566:9.152
@Rosalind_0066
GTGATCTGGCATACTATGCGCGACGCTTCCGCGACCCCTAGTCTGCTGGCTGGTACTAGCAGGAAGGGGGTGCCCCTGAGGTGATTGCCGAGTAGGCAGATACGAACGTCTAAATGGCCCGAGATATGTCGTCCACAGTGAGCTGATTACACGCGGTCAAATACTCAATGCCTCCAACGTCTAGTTCTAGCGTTAGCCCC
+
BCGFDH?@DHG@AEJD<@C;G;>D?D<>HFFB>AC=E=;GB<?DGABAG;D:9AD@:;AFC;ABF:8:C@:?>C7C;?B<>3<A<B=@J>DBF>GF:G:A?A:>>A@>?98A=G49=<=B7@>95AB=:A:C@?FE=B<H86:=@4>E@@98::<A8=>64778;8B@8;8:B:4=?=7:;87-D784464-=7011/3/
@Rosalind_0067
CTCTCAGTCCGAGCCATATTTATTACCACGGCAGCCTGAGAGAATTTCCGACCCAAGGACCCGCCCTCTAGTTTGGGGAAGAAGCCCCATATGAGGTCTGGCGGCTGAGATTAGTAGAGGGGTACGTCGCAAAACGAGCTCGGTGTTACGACTCCGGTATTGCTGTAAATAATCTACGATCAGAAGGCGAAGAACAAACT
+
F=3HAJ9=5EDDB?:;G6A@:B@@;DJ9E>B:8DE>B==<?><8ABA6?;AJJ8@=HC6D@<4=E7=?:.;@?C>57;?4?D===C6?>A6B>A@F<=?;?AC>=;@D;>B??C>==>4;B:<8G<;=::?78A:;@:A>7CGC829><A=7A5@-:DC>;:F:98?=66;<9?@95::3:?45+3=/0,:;5236&1/-
@Rosalind_0068
TGCTCTCTGTCGACCTGCGTTGGCAGCACCGCGCCACTGGAATTTGTAGGATCGACAACTTTCCCTCAGCAACCGCGGCGGATATCGAGTGCTAATGTCCGCCGGTAATTACTACCGTGACCAGTCCAGCAGCGAGCTGAGATTTCTCTGACGGGAAGCTTTCAGAATGATGACCTCGTGATGGGGGGATTACAGTCTAG
+
4;7306D96702;67279.1511>2373>78585703.60684.674939.067561C18/072>;@305;496)4<>-..1;+78/03D7.,.2,,915;.2.30202842854/3.:951214.4/+/12/2.*1>84342344:-0)406..3.61411'1-+)301#502'0)-3-258+32'..17,(+${'$'}&(-+!
@Rosalind_0069
TTGATGAATCCTGGTGATGTCCCACTAGAGCAGGCAAGAACGAACCGTGCGTTACTTAAGGAGGGTGAGGGAACGTCTCATCGTGACTTAACTTTTAGAGTTCGCACAAAATTTAATAATTGACATTGGTACCCTAGCATTACCGCCGCTGGCGGACGAGCGCCACAGGACATAGTGGAACCGAAAAGACTGTCTATAAA
+
EBCA?<ADBIBCI?E>D>GCJ9?G<@E9DBB@F@>HAJCHEGFE=CFH:@>J?G;>>IACBFCHA=F?<=<BDGHCFCDJB@>B?D?C8JADB>F=@BA<GB=IC>?AA@F8B2<>C9=BEGBCC;;DJ>F;BBA>=H=;@:>?@:;>6;789<:=C=??A:DG:<<;H<G4?::<<<7@@B=@C:95F8>663?556;/
@Rosalind_0070
TCCGTTAGCCTCGAGGTATTCCATAAGGACATCCATACGAGTCCTGTGGAGGTTGTAGTTCATTGAGGTCAACGTCAACACTGCACTCGTGGACGAGCGTGGACCACATTGCCTATGCTTTAATAATCCTTGGATCCAATCATAAGTCCCATGATTAGATCTAAGGCCGGGGCGAATAGCATTGTGCGGCCGCAATGTAG
+
>F>A?JCD@I>E=A?E<@?8>;G=;<F;C3B<<DE@GB?HG:CG@9<<A@BC?GAB?<:C>D5;F@;8E5:<AEA=?I>G9F=:787EA;B<@><B=HB=<@<9@C?=G;C7CE@5<7<=;6;?7=B<=67>?==GJ9@<;@:A@:>4=69>049:A,;5C:6=7;::59+8548:>?;:?1;7623;:7@56(253,1%
@Rosalind_0071
ACATGCGTTCCCAGGTACTCCAGCTGTAACCAGCAGACCTAGGTTGTTTACAGCCCTGACACTTCCCAATGTTATGGTTCAACCCTGTGTTTCACTAAGTCAATAATACCTGATGCAACCTCTGGAGATTACCAGTGAGGGCACGGCCGGAAAAGGTCCTTTTGTCGCGAAGTTGACCTGACTAGACATTATCTCTGTAA
+
:;=6@3><??9;>6@827>9=5A5438=<9::=>09533A<;<1;36=7;>6;=8?;88;63:<7857955=@;<4<1536A6<7@8>=6.04;5;447A0276.<2525972?5959.53;7<@798.><404-5<4D452362672<877;3:06763<1:166,5336*80=0.).25-/43.047*/.70+-.&+'
@Rosalind_0072
GGGCGTTGCCGGTATAAAACCTTTCTCTAAAGTGAGAAATTCTCTCCGATGACGCAGAGAGCCTGGTACTTTTTCTCCCACCACACCCAAACTATGAAATCAGTGTCCGATGGACAGAACTCCCTTGAACTTCCCTGCGGTCTCATACTATGGAAGCTTAGAGCTTCTTCCAGGCTAAAGCCCGCCAGAGGTCCGGGGTG
+
53=4/51>:2-68:50=3<832873;3,<3;48/:68:A48824386654<+732854<8:723-;/07526=:895<03+587<59/50:075<3957;36;368375.910+13,856:8-532-53985*4<17)/1866-80,4.///31,.1-12124-.02183('/*/,8<2105#4410&--,.,+&"+'-!
@Rosalind_0073
AAGATGACCAGTGAGCATGCATGGATCACGGTCTCGACGAACACGGCTGGTGGGGCGGCAGAGATCGATCGAATTTGAAATGAACCCGCTAGTCCCTCGCTGCCGTATTAGTGGATATCAGACTAACTTCATTACACTGCCCTAGTTCGTTTACCAAAATGACCCTGATACCGGACGTGCGGGAATGTGACATGGCACCT
+
=9+.51+:C>3650/8:788B1:6A1:>5D8187976/A14994;8;8,:@;3.9>84,3(/1-17179;8180*4805;7<5948*2/6679323..307;46359:7:68+37269;+445A5;7034,.9437377849:86:72+43105+461(7.<221'153-(.572/10541*032&.4/02/+/"*/)+!
@Rosalind_0074
GCTCTTTTGGCGCCAAGATTTGGAAGATCAAGACTCCTAACCTTGGCCGCGAAGACAACGAGCACCAGAATCGGAGCCGGGGTGCCAATGGACTGCTGTGACAGCTTCAGCCTCTATGATAGCATCCGCCGGGTTTAATTTCAATGGTACGGAAGGTGGAAAGCTCCGCAAAAGACCGACTTTACCGACACAAAGGGTGC
+
2;604169988<606:1:=AA78<2/879634<757+:9/::0<;9078692=9030>18+40661;22454=336/4+87;333476,047131:.:1;3;06471:05433269:35706.81'2638852;503-39)1880.54,6386/=(*1>/17-/%52+3+3,,0672${'$'}0/0/05.%,1&1&)(("#0!&!
@Rosalind_0075
GTAGCCCGCAGATGTAGCTACCCAAATAGCGCCCTGTGACCTGATTGCAGCGCTCTAACGGTTATTGGCTGTGCGCTGTAAGATGGAAGGTAGGAATCTGCCTATCGCTTGCCATCGGGCTACAACTCCTTAACAACACTCCACATACATCGAGTTACTTCTTAGCAACTGACAAGGTTCGACACCCTGCAGCTTAACAC
+
E:D?>==>;:<@?FABBJA;:=C;>EF?A9:?8D@GBF=1;J=D;DDD7B=G;B<EJBC=C9<ACA>7CAB?B6?>;:<B;;@=E=>@:4C@C<C>?68=9>J?G<@E3DCAD>;BB9?=CA<>9;JBCD57C?2=@AC93578;;6D>5>4<;8<8<BC:>@@;;@6:3?=899:<<=63/39?8548718124300-*
@Rosalind_0076
GGCGACAACAGGTTGGCCATAAACCCAGGGGCTACAAGGTGGTGATACCTCAACCACTTATCAAGGCTTCTGTCTACCAGCCGAACCTAAAGTTTTTCCATCATGCCTGAGATAGCAAACGGCCCAGTAGGCAGTCTTTACCGAATCATTAGGCACTAGGGTCCTAATAGCTCCGCCACAGAAAGCAAGTACTAGTCTCC
+
87:59:<62@666?:-:8255-80.2.63>779.56/435;605800/5793:4<365:998:+634634167E201744102=462181,+,<,09373;379+5034892?821-457278/=,//524:35,.4?5+553:+1&4/.*,44<${'$'}*/-/,31/.+)86'*40(#-/0-4-24"-+00/(&&(&%%#*%#
@Rosalind_0077
TTTGTAAAAGAATGGAGATGGGGGCGATGATTGTCCCTAATTCGGTTGACCTTGGATGGCTCCTTTTTGAGTTTTCCGAGGCGAGTCTGCTCTAACCTCACACCTATCAAACATGATCCGGTGTATGATGAATCCCAACCATACCGGAGCATACGGCCTGGTCTATGGCAAAAAGATTCGTGTAGATTTGACCTATTACA
+
GAFA=A@GCFGA@<C6=7IAG=AIH:B>BJBAD?D=B<;@G<<G@BECCA>FJ>G<<DABE7?A7HBGBD?HC?CABA<:?>:J>BJJ=@EBCA<CCC?>A:D@4?ED@@?I@>C7=@BDB<;HC;GE>F>DB?GD>>EGFHB?<>=A<@;AFD@6@=66:B;A67<>GE>90A?E8;97J@7?<<062/:456<4>1*)
@Rosalind_0078
GCTGATGAGTCATCAGGAGGCCCGCCGTCATATCACTAGTATCTACGACCTCCGGGCCACTCGAACTCGTCCTTCCAACAAATCCCTGGGAGAGGGATCTAACGAACTACTTGGTCAGCCCAAGATTGGTCTTATCGTCGTATTTGTTCTTGAAATTCGGCCACATTCATTGGCGGGACACGAATCTCCCCTTAACGACG
+
B@IB<F>JJICA?CA=C9?CJJGFEEB>CDC<FFD>BE@BBB=?@?CBB=6CH9>G>;IJBC9=AAFIJEBH9<F@A>DBC>DJBCAE@<EJGJC4@?>BD>A?:D@=5>;>EEA;=B=GF7@D?2>5BAFA;EFE9:G<>I9:EC:D@C?55>8><A8<6>?;6>F=?8@E9:6=<?<B<;<::2<B8A3/5<4:0421
@Rosalind_0079
AAGGACTAGCTCCGTAGAGCTATCACCTCATTAAACACTTGGTATAAGATCTTCCTGTCGAGAACGACGTCGTATTAATTGTACGCTATGACCCGCCGTATACGGAGAAGAGTCGGTACTTTTCACAGTATAGCTCCGCCCTTTCTGTCGATCCCACGTCTTACCGCTCATAAGGCAGTCGGAACGCAGGCTAGGTTCGA
+
B>GDA??J8@?:@IAGI@6;><F<JJADCHAJHEEF>C=JFB=GDGDC=CG=@JAC?=@DGGI>:?@<B>F6CA<>I@EG?GE>=;A?;CAG@D?<=EF@@8F??B9;FD4DA=AE>6EE>98J8>5<?C=>9GC?D=C?@BC=:8=::<A?=?J4:=D<C;46:>4?34<>86C3>9?=?7;8166<95257>199/36
@Rosalind_0080
ATTAACATTCTTTGCGGTCAAAAGCGAACGTTACTTGGCTAAAGAGTCACAATCACTCTGGGAGGGGACAAATCTACCAACTCAGGTCGTTCAGGTAGCCCCACTCGCGCCCGTTCTATAAAATCCGGGGTCAATCGTCCGAAGTCCGTCCACTCACGACGTGCTGATGTTCAGTACGAAATCTGCGATAAAAATGTACT
+
8DJ?;8E@D>=JCHJIE>=G9AI???EHB?:FD@;8CAD5?>G=>@@:G=@DB88FC9@CD@E6B9@@A@EH>BIH<GCAAH>E?B<9D9CH;CAG8;GB@9EJE@?=A;?<530>>;6:8A:H>=B;=FJ>8DA7A@?F>CD@==@5@;66B<;8BG:F:8,<783:>>:35=@==28>7<<>4@.3653844270>-'
        """.trimIndent().lines().toMutableList()

        val qualityLevelExpected = sampleInput[0].toInt()
        sampleInput.removeFirst()

        var numBelowLimitReads = 0
        while (sampleInput.size != 0) {
            val qualityLine = sampleInput[3]
            val averageQuality = faq.averageQuality(qualityLine)
            if (averageQuality < qualityLevelExpected.toFloat()) {
                numBelowLimitReads++
            }
            sampleInput.removeFirst()
            sampleInput.removeFirst()
            sampleInput.removeFirst()
            sampleInput.removeFirst()
        }

        //println(numBelowLimitReads)
        val expectedResult = 29  // accepted answer
        assertEquals(expectedResult, numBelowLimitReads)

    }


}