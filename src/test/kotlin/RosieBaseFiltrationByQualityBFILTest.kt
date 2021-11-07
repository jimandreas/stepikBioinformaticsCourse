@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.FASTQtrimLeadingTrailing
import algorithms.RosalindReverseComplement
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.math.exp
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 * See also:
 * http://rosalind.info/problems/bfil/
 * Base Filtration by Quality
 *
 * Problem

Given: FASTQ file, quality cut-off value q, Phred33 quality score assumed.

Return: FASTQ file trimmed from the both ends (removed leading and trailing bases with quality lower than q)

 */

internal class RosieBaseFiltrationByQualityBFILTest {

    lateinit var bfbq: FASTQtrimLeadingTrailing
    lateinit var u: Utility

    @BeforeEach
    fun setUp() {
        bfbq = FASTQtrimLeadingTrailing()
        u = Utility()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("Rosalind Base Filtration By Quality BFIL sample test")
    fun rosalindBaseFiltrationSampleTest() {
        val readList = """
20
@Rosalind_0049
GCAGAGACCAGTAGATGTGTTTGCGGACGGTCGGGCTCCATGTGACACAG
+
FD@@;C<AI?4BA:=>C<G=:AE=><A??>764A8B797@A:58:527+,
@Rosalind_0049
AATGGGGGGGGGAGACAAAATACGGCTAAGGCAGGGGTCCTTGATGTCAT
+
1<<65:793967<4:92568-34:.>1;2752)24')*15;1,.3*3+*!
@Rosalind_0049
ACCCCATACGGCGAGCGTCAGCATCTGATATCCTCTTTCAATCCTAGCTA
+
B:EI>JDB5=>DA?E6B@@CA?C;=;@@C:6D:3=@49;@87;::;;?8+
    """.trimIndent().lines().toMutableList()

        val expectedResult = """
@Rosalind_0049
GCAGAGACCAGTAGATGTGTTTGCGGACGGTCGGGCTCCATGTGACAC
+
FD@@;C<AI?4BA:=>C<G=:AE=><A??>764A8B797@A:58:527
@Rosalind_0049
ATGGGGGGGGGAGACAAAATACGGCTAAGGCAGGGGTCCT
+
<<65:793967<4:92568-34:.>1;2752)24')*15;
@Rosalind_0049
ACCCCATACGGCGAGCGTCAGCATCTGATATCCTCTTTCAATCCTAGCT
+
B:EI>JDB5=>DA?E6B@@CA?C;=;@@C:6D:3=@49;@87;::;;?8
        """.trimIndent().lines().toMutableList()

        val threshold = readList[0].toInt()
        readList.removeFirst()

        val qList : MutableList<String> = mutableListOf()
        val outList : MutableList<String> = mutableListOf()
        while (readList.size > 0) {
            qList.add(readList[0]) // name
            readList.removeFirst()
            qList.add(readList[0]) // dna data
            readList.removeFirst()
            readList.removeFirst() // toss the +
            qList.add(readList[0]) // quality line
            readList.removeFirst()
            outList.addAll(bfbq.trimLeadingTrailing(qList, threshold))
            qList.clear()
        }

        println(outList.joinToString("\n"))

        assertContentEquals(expectedResult, outList)
    }

    @Test
    @DisplayName("Rosalind Base Filtration By Quality BFIL practice test")
    fun rosalindBaseFiltrationPracticeTest() {
        val readList = """
22
@Rosalind_0000
TCACCAAAGAATCGGGGTGGTCGGATCCATCATACAGCATAACTAGCATCAGACGTTTGATGACTTGGAATGGTTGAGCAGACCGTAGACCATCGGATATGAATGGGCCAGGCCGCAACTCGCTCGAGATGGAAGAACTTTCAGGGGGGAAATTTCTGCA
+
?<F7=@?JFHFB;EIBGEDBAI?=CB?B>IAE@9ABE?EC<:C9<@;@=GGHC:J@DI@4:@?;B@B5F:><=>E;JA>>D9H>I6=:?7?8H?@=@AC329>F;>>@CBF7??>C8@796?16@@86;5F8;=@<??0;5:5?852279;3:6/8*5=/
@Rosalind_0001
ACAAGCACGGCTCGTTTAGTTCATGATAGTACGGAATCCCATCAGAGCATCCATTCACCGTGGTATGCATCATTAAAAACACAATTTGGTTGGTGCACGCCCTCGTTAGTCTGAATACTTGGTATCAGCGCCCTCGGAATATTATTTTTAGCGGATGGTC
+
>DJDA?CHG<CAD:<=?FJBJDJIFEBA:AE7DH@H>JE>>C<I=<EC@D=@J?JC@I58>IAH9?C>B?B8B3@DE6C=D@?JGA?AB9FA9<899B4<:FH:?:35976>BA;B@6>8D<<9E>@95@;<A;;>;B5>40;<A/4696:504-:7-40
@Rosalind_0002
ACTAGAGCCCAATTGTCCCATTAAGTATACTTCGCGTCCAGTACAAGGTCTGGTGGCCTTGGCTCTTACCAAAAGAATAATCTGCAGGGAGTGCCACCCCTCACAACAGTTGGTATCGATGGTACTCAAACGATATGGTGTTAACTAGTTACTGATGGAA
+
/@:A@=8B9<??:?9E8J9?A@9>9>C68D=I>:BF871=>7?;>;6?D;@>A?=<A9=7?J=@@;A@=:;E6AC:888739<?25;=B9579E>>9@?6>9@=889<4@:/7=>799A5816=094535180:;8375.423<3*6-/77364,5)05&
@Rosalind_0003
TGATACGGGGTTACAATAATGCCACGTGACGATTGGATAACTCATCATTGATCCATGGGTCAGTCCGGAAGAAGGCTTGGCGATGCATTTAGCAAAGATACGCTACCACATTTGACGTGTGATTAGCGGAGTGCTGCGTGATGACACGTCAACTATGACT
+
617=5?3=52E9/9<:3C645253B7:?8<72B965324/@34174/83?56;<97.86574<21:324=934381340650${'$'};44,?4943927/48&6144<53726-762?11+,-239-(*;034,:7*/<.)0..+(21/./01&.'*(/*/${'$'}&!
@Rosalind_0004
TCCTGTACTCGTCGTACCGGCATAGCTAATATTACTGTCACCATGGACATGTCAATGGGAGCTAACACGGTGAAAAAGTAGATTAACAGGTTTTTCGGTACGTCTTGTTGTATCTCCACCGTCTGCCAGTGAGTCCAATCACCCGAACACTCCCGGTATT
+
C@I=A6<892HC5J7;9AA:A=7;6I9>:>?:<6:49@7?><=?7:8=8@99:=;>9EB7;?A4?,;FF87=?8:<::6E166:;'=9>81C9>:?>=923=F8@C<74:685<3:82?768;900/9744:41447986957/34@4/822846,*-"0
@Rosalind_0005
GTAAGTAATCGGATTAGATACTTATCCGGTTGCCAGAGATGGCTAACACTTATGGCAGGTTGACGCATCTCATTGGGAGAATCCGTAACTCGAACCGAAAACACAGCGTGACGTGGCTACCGCGCCAGAAGATCTGGTGATGGACTTCCGCGTACCTATC
+
DJFGHE=EE?G@ACDD>?C>AABGAJAJC?;?C=JC;DF=:F>FC?JABG>B?IBHH=?C@FDAE?>;D=A?96CB?>J@<A?@JAD@?GE@AI?G:F7>=;?@;CE@AB:?99<9?<<@@G:D@7ADBC>57;F:C<=>?A3788;947:7,6034122
@Rosalind_0006
CTTATGGGAAGGTTTAGTTAATGCCTACGATGGATGGACGTGGTTATGAAAACGCATTGTTGTGCTGATCTCCACTGTGCGTGCCCGTGTTCGTAGGCGCTAGGCTAAGCCGCATACTGAATTTGGGAACTTCTCGATCTTACTTGAGTAGGGCTTGAAC
+
HJHACFBCFE?<HAEEICJB=J@G@AJCGBGEICGJH:CDE9JD>>?:BB@C:C:A?JC;8DCBHGAE=D?:9ECC;DDCB<=<BABE;A:CJ@?FDA;F7>JBC?B><8@?8:98>A5;=F889=>?B809C?6<?;8?8;B>4;4B=69<7@9324+5
@Rosalind_0007
ATGTGATAGGACAATCATGTAGCGTTTCGCGGGTACGACGTAGCTGTCTCAGAATTTCCTAAATTCTGAATAGCCTTATGCTAAAGAATAGTAGCCCAATATCCGCCTCTACGACCTAATAATCAAGGCCGTGGAAGCCAAGCGCGTTAGGTAAAAGTAG
+
JFDJ@D?BH>HABGGGE@A7GGCB8ICF?JC?7H?>>GE79EB>@A<JD5@G:J?CGJACF@?C@;HE9;EAHBB9I@D>DIBAE@@><EI>@AB<F>=CD>=;<BDDC?J>:@4:::J4@>D?BA;9:;75E>>B?:@8;CA?@746=<539=?93302
@Rosalind_0008
GTTCAGTTGACTTGCCAGCCGTTAGTCTCCGGTTCCTCACGACACAGGACGGCTCATTGTTCATAATCCACCTGGAGGACAGGGAATATGTCTTGTCACAGCTATCCCTGGCCCCCGGTGGTTTATTTCAACGCGGAGGTAGGTGAACTACGTTACCTAC
+
6;8:;<?:B92784:<46411>:1719247160@;820<24/=+2<0)877;><2B7>85287678-162/;:::9?;944B8<.-6+,+-127;11944/4-3/7/4366,;8382?72;/3497**4.93+/2.32,442(7**-(&,/*)3+,,)&(
@Rosalind_0009
CGACCCGCCACAAACAGACCCTGTGCATTGTATGCCATTGTACGCTGACATCCAGCATTGTACGTATTGTTCGTAGGGATTGTGGTTCTCTCCAGGCACACGTCGGGAGAATACGCCTCCGGTTCGTACAGATCTATCTAGATCACCATTCATAAAGGGG
+
0396@11>8435???B88;?4:43=7:520;94<5>>69?C:43?7899,;<;8;0<3;.6?A73182<==3-8:7=B;A5;177<:;56?94755/69:7+/-*342;645;34416/442/97/:./4/95740,63222.360*.%3+4*,,+')${'$'}&
@Rosalind_0010
AACGAGGGTACTAGCGGTCGCTCCCTCCCGGCCCCCGCATCCGCAATGTCACACTTAAGATGAGTTCTTCCGCCTATAATGCAGTGTAGCTCGTTTACGGTACTACAGGTGATGATAGCTAGATACAATATTAATGAATAAGGTTACCGAAACATACCCC
+
@C?83>0?55=;;8=:@87<D9A;HA;A6><:8BDA;CDD::>3C:A===<4;=993A@?9@76@676<<69>>?BB<5@5>39@44<B<1:6E0:7/C1;27:679)1:3<84??=8?7>7888/6<B=95<6<546584>9:9153545410(+)5!&
@Rosalind_0011
GCGCTCGGGCAGGCTTTCTCTCGGGTTAGTAGACACACGTATGGCTTTCGCACGGTGGTGGCGAAGAGTGGCCCTTACATGCTAAGGCGCCACACCCGGAAATATACCCGGCACATCTCGCCTTCGTTCTACATAAGCGCGGAGTACTAAGAGGATTAGG
+
EJCIADFEGE<I<E?>@HAGBHBB@;ECE<CJ>D@JJC>HAAIE@BDBHCAFDHJ@>7::AGE?>=@G;FE7BC=@=BH>E8=?B79EIA;C@BD:B<J35GE<@7AB>C==;87A7EAB;><:H>?A?4;8@@:AC=;A;><:<@>6B:<,06.3D0.0
@Rosalind_0012
TTGGATCTAGCAGTCGGAGGAGCTTATGAACCGTCTCCTCACCTCACTGACCGGGATACAATTACTTGATCCCTTCTGCAGTGGAATCCATTACCTACATTGAGGTTGCGTGGAGCCCTTCGGGAGCCTTAGCCCTCTTCCCAGTGGTGAACTATCATCT
+
./345-9;495.26/6721612-;>;848,954<63666786/948;92/:6;37A95211;198.68/<1610:22*=076:444419-22?/0048)601-1/4+894-1,'6;938,566+/229+/6//02/5(+&),2),(%1('1**./("('#
@Rosalind_0013
ATATCTTCCACCCACCTGTACGAACCAGTTTCTGAGGCTTCAAGAGGCGATCCATGTTGAGCTCTACCGACGATTGGCGATGTCGCCCTTGTTTAGTGGTATGTGCGCGGCAAAGGAGAATACCTAGTCAACCAGCCGCTTCGAATACATGACCACCCCA
+
@@><@AA2B7A@AD5?H=BD?F<=G8C6BF>>26CB>6=:>;85A6=@;=@94>63:E5=><?;7;;;6A3B98?7?>:B@?6>835>::<>>59;;F=@7735D789/<:8538;74=84:5.4=2376->1;<46715751?1512/.0.)0302-2'
@Rosalind_0014
CATGCGCACCCAGGACACGCTGTTCCAGAAATAGCTGGCTCTTGTTCCGACTATCCCCCTGCCCTCCAAGTGGCACCCTGGCACTCTCCTATCAGAGAGCACTTCTTGGTACGTTTGCAACGCACTGGTGAAACGTCTTCGATTCAATCAACATTGGTGT
+
02873687;@>7<9;:5B44454<5198:79529439886,35=<046<.96929885/965/>197750915/8360-?10@&;1338.31033/4-:384,,04746*13,4-04671?916-3/4,2'6-31.792//:20*+.(1-*+,/,(&##"
@Rosalind_0015
CGGGGCAATTTGTCACCTAATACGGATGCACAGGAAGTGTCACGTTCGTTTCAGACAGGTGAATTTCGTAGGCCGATTACGAACACTGTTTAATCGAGCGTGTAAGAGCTTTGGATGTGCTTCTGTTAGTATGTGTCAGGAGAGATTTACATAGCTCCCT
+
AH@6:CBBCE;EHGE>F@DH=FJE@>?DC@J@FEI:?=?AE:B?GDBI9E8JI6EDI<IF<JJ:>DCF5DBIB;F<IA?ECB>D=B@>>J;E=IC<AFBF@8F>?C>@=H=;4=9;CD<@@8=H6=B:>D@>=?C8;<5;IG7=7@=6/5@56>:5+7-2
@Rosalind_0016
TGTGAAGTCAACCCAAGGTAGCAGGAACCTTTAGCCAAATATTGTCGAGCTTTCCTAGTGCGTAGTGGTGCAGTCCATTACAGGTCATGGTATTGTACACTTTTGAATTTTATTTTGCTCAGTCCTACTTAGGAATGCTATAACCATAAGGTCTGATCGA
+
=EIGAJCDJ8J9ABB@>?:F?GBCA?=BG>>B>=I@FEEBE@I?CGDHBF=IEBCF?A77IAI?;D;BEC6?F?FEJBE7?D;:?F<<>ADB:IB?>@HG=>;C5BE@@:;A5?;;@C79J><<<F7?.:5?;=<>;B;<65184;:8I:.?7347;+13
@Rosalind_0017
GATCTTGACTCGGAGTACCCATCGAGCACTGTGATGGAGCTTCTACGCTGTTAGTCTGAACGGAATGGTTGCCGAGTCTCCGTTTTATTCCTAGAGAATTTCGGACAGGAGGTCACCAACCCTTAACGCATCGAGCTGAGTCGAAGCAGGGCAGCATATT
+
B989689@:;.6*51-3889888/62:9014<0;8/:942,59;<-7679;5.-5:879;.36174;148.;@82<,326560'458;23813709455..6,+2)51+-/.-+0047.500+-.34+/5+/-.2.)4003+35-&,,,++.)&-/*%%${'$'}
@Rosalind_0018
CTGGCCAAAAAGATTTCAGTGTATTTCGACGACTGAACTCGACTCCATAGTCAAATTTTGGCCAATTGCTCATTGACCTACGACCAGTGCTTCAGCTTGCATCGACCGAGTATTGTGTTCATAGGCGCGCAGCGTGATTTGCTGGGCGTGAGGGCCGCTT
+
<;BEH>BEJF>BDECJ>:BCH==CG<GDJ@=J<=DJEFHGDGJ=A=>@A8CI==AEFEDHDJEED>CEECJ??=DF:GCDF=EBDCC>><;4?83D?;@=@9C;@A?=;:6JC9@;=B<>D7EAD69<A<=:98<9A7D>7C>477>;?@71;6?-101.
@Rosalind_0019
CAGTACACTCTCTCCCTGATGTATAAATGAATACGTAAGAAGTGTGTTATCTTGCGCGAGAGCGAGGAGACGAGATGACTGATATAGTGGCTGTACGAACCGTCTCCTCGGACGTGGATGGTCGCATACTGTCAATCTCCTAGCTTCAGACAGCTGATGC
+
6=8A;@D@:B9>@?=DDA:F;F@B9BCBB;==?=B?8=D8A?=<999A=9HE>D:>G=J=@CB8E:D8@<=C;JCE:>G?B<3HBE==9;B78?FB69@;@?D:E63=/<:;=;8<;:9@88:0@:>:;77=27498>;<?33=53@.68:5:372*4*2
@Rosalind_0020
AACTAGATTATAACACTATCTGGGGGCAGCCCGAAAACGTTCATAAACATCTCGCAGTTATACGTATCTCGCTTATACCTATAGTGGATAATCCAGGATGGTCTAACCCCCCCCTTACATAGTTAGAGTCCGTTGGTATCAGTGTGTGATGCAAAATTTT
+
61;7;286:52)760+=.3;4233588/555:0087<373@2456:8986573&;0:?316.4:44.8544347396774,(+>4/732198;010.362:.8+3./+.'5:+(50</.'3)74.(*.*21)+3,3*2+:08+!(${'$'}#.!(,.-/5(!!)!
@Rosalind_0021
TGAGTTTCGTTCATAATATTGTAATGATCCGGGATGCCGACTAGTAAGCGGTGTGCTATAACATCTGAACCAACACGATTATCCGCCAGAGCGATCGGGACAAGGCGGGAGGGCCGTATTATCGGCTGTACCGCAGTATGGAATCTGAGCAGATGTGCCC
+
B:<?BGE>FHDDJAG=E:F;;AF=BDD;JHC9@CAABDCHJE>DE;H6D?J<@CD@D@A<<?B=ABECH?@@EF?@JD=FIAEE>CBE;AE0E:GD;DGJAA<A>AEBDBD=;C<>:A;4B>?<AIGA3><>9B?E;5.23:?=9=>;8:B/74;<501${'$'}
@Rosalind_0022
ATTATCCGCAGAGCTTTTCAGAGCGAGTCACGAAGTGCTAGCGATCCTACTAACGAGCCTCTGTTTAGAGATCCGCGCCCTCATGTTTACTAATCTGTGGTGGGAGTAAAAGGCAAATCGATCGGTCTGCATACCTAGTTTTGAGGCAGCCAGGGTCGTC
+
/2<335>/3E89564*1661;0173412.05:55@;.3.304=1?2.4016;2359816528=;13.73/>70;4085612.8:.1591,-17/4327/11&30421.+7060.4(006<4*0)09*.-/0%4)4)01-)4/071&*,(,"(')-(/-"!
@Rosalind_0023
GAAAAAACGGCTGTTGTAACGGTCGAGCCTATAGGTCCGGGGGAGCTACGTCCTGTGTGCCCTTAGTTCCGCTCGAAGCAATGGAAATGCGACCGGCCCCCGATCTTGATTATGACACTCTAACCTCTTACCTCGAGAATCAAACGGGCATTCCGCGCCG
+
@@6E:<941E4457:761888A:<2;,2<:427;<6486413<96;@/24:4/:93>58;B67A6-*=798116:13:*964@5-<5;7233/*96.3;/A305=./:)2:5410%7-20+.+54856'/)+22160&5+11*0(%76)))+-1-.+#%4
@Rosalind_0024
AGGTGGTCAAGTGGTGGCGCCGGATCCACACTCGACCAGAGTGAGTCATAGTGAACGACACAGCGAGTGACTAGGTTCCGAGTGGAATCGTGTGTTTATGGGCCTATCCGGTCATAGACGCGCTATGCCTCGCGCGTTTGTATATGGATAGGGACATGTC
+
DCB?@ECCAA>HGA@A?H<9BDC:BGECFD>@A7AAB?GE@AE?EE;CE>?>D?<<D;H5AG?BC?;?DI89AG@@F8D:7DC><<F6J6>9D?C@66A;C?>@?A>D?6C8A<E:?;A==@:F759B@=3??<9/8:4>E898;96>9673<1>;1020
@Rosalind_0025
GTCCCGGTACCACCTTATCTAACAGATGGGACGCAATGCGGCCTTGCTACGTCCGGCATAGCGGCCACGCTAGGTACTCACCGACCCGTCGTGCTAATTCCCTGGCACGGCAATCTTCACCGTTAGGTAACAGTTTTAGTTTAAGTGTACATCAATGTTA
+
@B;>CIJ:?C:<G9DCD>F=:D<<C9?FIBJ>;9?F>=?@HE<6J;AD;D<AC9F=>CJ@;?DA>7B?9@.6D7=DE9<=?6;:A9<6<AG;A>@C8A5=:>BC>8;:?F78A613A?;:::2;D<:=9;6=96;7:;?<:8599268/:8;65:0-4.*
@Rosalind_0026
CGGCACAAGGCGAACATCCCAGTCTCGAAGATGTGTCTTAGTAATGCACGCGTCCATCAACCATAATATTCGGTAACGTATTGGAAAAGTGTTCTCATGCGTGCAGAGCTGTAGGATCGCTCCGGATGGTTAGTACTAAAGCTGTTACACGAGTAGCTGT
+
;BJ@EJCGBD>B=A<H:B:AGBD>J?HA;BAFC@DDC:EJ6DCB=@??GC9HAJ@@BEA@DCJFDCDJB?BEAJC>E<>JI<EJH>A7BC;@>;H6FEA<=<BE:<:=:J><8<>?C64JB:?;?E>BD>9@;:9D1:<6>6=:84:<E>=883+93/6.
@Rosalind_0027
ATACCGCCGATTGCTAAGGACAACTGTCGCTTGGCTACTAATAAGTGGCATTTATATTCGCCGGCTATGGTGAATAATTGCTGCGGTCATCTGGACATAGGCTGTCTTGCGATCGATGAAGACCCTTGACGACGCAGGGTCAACGTCCGCGCTATGTAAG
+
85;94-<241<299/=-3<:8=85:2??:6?39=3..;092914*4;68A${'$'}68=:/04556/B152228:95?,8-.01428264-62/-712430;6/8704:(${'$'}343=86-802/3+/322.1191./6128;43125:,67)#051!-.'''.'!&!
@Rosalind_0028
TTAAGCCCGATCCTATGACGTGCACCACGACGCATCTACATGTGCCCCAAAGTCTGACTCCTATTGCACCAATTGGATTAGTCATCGATAGAACAGGGGGGCAGTGCGAAGTGTTATGCCGAACTGTGACACTGCCTCATTAGACTCATAGCGTTCGCCG
+
EACHFFH8>;JA<=<C<?=??@CA<J<;D5>=C;E?@>81=J@B5J>D@BE?=C@GI;C?G=B=@6<;D8:?9>?3;8B:G>@E><B8H=HE?I@>D;D4<5@:><>:<@>=@<@:A7<C@9===<@=967=?7>65256=5:47@18<36:97>3+)/3
@Rosalind_0029
CCGCTTCAAACTTGAGCTGACACGCTCACTCTACTGCCCGAAGCGTTATGTTTGTATGATTGATTAGGTCGAAGGCTCCTACGTTACTCACGACCGGTTGTAGGTCATTTATTGACGGCTCTGAAGAGCTAGCCATAGGACTCTACACCCGGTGCCGGGC
+
3155@9/-.83;47:148?150=>8A97A7.,=45:0189<51575/98,7.945207</313:213?6850@-5B0)2464=9097322;:85148+6707=4468,4*.844708,2...1597.94115-252,4/*/1.6/+0,30."%&0${'$'},5-!
@Rosalind_0030
TAGTTCTCACGCTAGTCCAGCCGCTAAAGAGGCATCCTCCTAAGTCAGTATTGGCGGAAAAAGAACCTGACATCCGATCGTGTACACACCTTAGTCTCCGAATTATAGAACCAACAGACGTTTATATAACACTAACGGCATACAGAGACCTCAGACAGTG
+
?19CD9>:C5A6;G1=:6?45<C5:7:<5<5=;>8=:/6<05<735545@8=@7:=/782=>7029:@<::75?77=87/:1884>071=9?,5631;386074885=?0<2<23:8/:242972,,016-3062-521//=23+/#,68'01,)1).&%
@Rosalind_0031
CATCATCGATCGAGCCATATTGACGTAGTATGGTTGGGCGGTTTATCAGCTGCAAATAACATATACGAGTATACATTGGTATGAGGGCTCTTTCGCGTCACAGGCAAGGTCTGATGACAAGTAGTCCACATGAGTCGCGTGCGAGGTGAGATGGCATATC
+
67;6<529;856644,88-07/;:6(598=39109798,5322=/6860<3,7:7730803-47.307;2*<4>3867=%,816*-'-82.91(./44*25,12-,+,("32+*0920!--,.20/${'$'}/2*.56-(04;*.,%3/&6)()%*-0(,&+0!%
@Rosalind_0032
GTTACGCCGCCCCCGAAGACAGACGTTTATTGCAGCTCAATTTCCCTAAATCCGTAGCTATGGCGAGGGCGGAAGCCCGACATACTTCAGCCCGATACTTCGAGCGATCCTGCGGTCCCGCACGGACGGCCGCACGTTGCGGAAGACGAATAATGCACCC
+
6?72<0=<2:@.2;1,;;27/5==0499:296>-55=72204,1>2A59:64.7/<10784+.21:6/0/5;.*6316//33+*936324-D*/;04.*59.4///0/,82115340-21-.71/1,03,)34,-18-,203)-${'$'}*4/.0)&0&48%'${'$'}#
@Rosalind_0033
ATACGTCTTATAGTGACGTATACAACCACCTCCCGTGACGTCTCCCGGGCGTCGCAGGCCTATGATTGTACGTTTCCGTAGGCTCAGGAATCATAGCGGCGCGGCTTTTAAAGTCCGTCCGATGACACGACTCACCGTGATTAAATATAAGGGAAGGAAA
+
B:CHBID>FHGJE<?FGFCJ@AJECA@JDD=EG@AA@@C?9CE>EIIG?@BBBDAC=JFA@BD>8B;AC?A>;FG;>CEGD=<BE=@D>B8A=D9J9:D>@E@B>=B=C@@:E??@<D<E>?JDDAJ9;9HA@C:98;5;?4:9;84>09->943=20/)
@Rosalind_0034
CAGGCCGGTTTGGAGCTTCCGTGGTCTAACACCGAAGTTCCAGCTGGCAGCTATACCCGGGACCTCGGGTTTCAATTCTGGGACGAGCCTCCACGCTCGGAGCGACGGGACCCTGCCTGCCCGAGGACTGAACATCCGACTGAGAACCTCCCATGTAAGT
+
9;99<>@5=;8698-@9860E6=9@86134@4C58A985:42;:@667=62@B9:=?275:9=:<360558958/>72477D836:/4292?06;=99883;/07::61,621<3*65176${'$'}75:-42-482873+/6/254-7/19)*,.-3)5${'$'})#-/
@Rosalind_0035
CCCTTCAGCCGACGGCCTGGTAAGATGGACTATAATAGGCCTAGCGTCCATAATCACCTGAGCATTCGGCTGGCCCCGCGTCGGTGGAATGATCGGTGGGTATTTTTGTCGTTTGAGGTATTAATGTGTGCTGCCAGAAGTGAATCATCCTTATGTATCT
+
DEA?AIEB?@;GEBIGHD<EHBED:GDG8GB49@A?@<@DEB<H@D?EI??8<HGG=@GEC>D<@?H@F;DC<=?EC>>A;D?8A:=E@<=>47EBCCH4@<;989=?BA>B;C8?:<@8@>EA9A7@<?C?=@:A1F9@5E48=EC<9<64979=;.40
@Rosalind_0036
AGCGCGTGATAGAAGGTGAGACAACGCGAGCTAGGGTACTAGAGAGGGTGCATGAGCAGGTGTGTGTAGTCTTAGAACTTGAAACCTTAACGTGATGATGAGGATTCGTGCTTCATCCCTAGCCTGTTGCGAGTCTTCTGGAGCAGCTGATCTGACCCAT
+
G@B=B;=FD<>FA;B9CC<JC;F:;=>?CI??G@89;D<?GC<9BIE<75FHJFG:F?<CF@C<;9FDA@>>@@>@;5A@>63;<A@@B6C:@>=?:C==:CB=><A?8<7@;D@96;JE>;;;D7;73;;5979;?8B2C@27??6?:/508<7328-/
@Rosalind_0037
TTGTATTGTTCGAAGCCTTCACGGGCAGGCACGCGGTCTCCAGCATGACGTTACCGTAGACATTGCTCGGTACACATTAGTACTTCACAGTGAAGCAGAGCGAACTGTGGTGTGATTTCATAACTGTGCCCCGTAGGTCCGTGAGGGGCGTTTGCGTATA
+
BJGCG=DCDFC<CFCBE:E9DC6EH@H7G=@EC:E?@BD=JD:BEJ9D@D><;6EE?JAJC>6:FDJJ?C;B95=?HC?BEFC<B<BHDECA==D;<D=AEFEGBBI=CG<J=><F:==@;:FCJ<E<F;9@@?9::97666<8:1;86;?2=@7:;650
@Rosalind_0038
ACAGTAGTAACGATGAACAGTTGAGGCCTAAAAATACTAGGCAGCCGCGCTAGTCCAGCCCGTCCTCGGAGTTGGTAGTAATGCCGTCAAAATGAGGGACATCGTTATACCATCTCTAGTACTACTCATGCTTAAGGCCACGCACACGTTGACGCAAAGG
+
=G@JBHIDDDGCGBABCHAFCDEJHGIC@=ABAFGHFAHF?JJB=FGBA3AH?:I==IA:FHCFJ??G<7CDH@@?CH=<HF:<GCAEA<7<=@DJ:<<B<?@FA?>12B>CEEGDGG<@:A7C<=?<9@?C::9C?39C=@9>D>=:;?A4938943<3
@Rosalind_0039
ATTCGTATAGTGGGGAATCACGTGCTCTATATGCCATCGTCACCTGTTAGGGCTGTCACTACACTAGTAGATGAACTGTGTTACTCTATGTGGGCAGTTCCGCTTAGTGGCACCACGAATCGGTCGTTGTTGGTGAGAACTATTATTTTTCCCCGGTTGC
+
CA@H<D6CB=D<5DIH;D?E;HFH=@A@E@BJ??FAJ:=<<;C?<;=<EG:A@?78>?<BE=>=?A:@JDCF9>;9>8<8>B;9@CED?;3CA>C8E2:B;H8?9>E@?8>?95<@;9B9A>@??>19:4765C=06485298<968=<36034?560+5
@Rosalind_0040
GTGATAAGTGCGTCCTTGCTTGGGCCCATTAAGGTAATGCCCCTCGATTTTGGTCATCACCCGGATTCCTCTGTATTTATTGTCAAAACGGTTCACAGCGATCGCGAAATTATTGCACGCGCACGCGGTCAGACTGTAAGTAATACGCCAAGGCAGCGAG
+
EFCHJCJ<D:GAE?CCDEHA?AJD@>=8GF8AGJC??JGA:G@A;?C?BA>H?D;J?EHFC@CG0?DHJA?@H>B=?AGA?<E@J>G=A9DCI>F><B:=;GBCA?=:<D:B@A7;@D:::=>B@?79:CA7>:F8;?=8:=>;=A5:4:6;<276/282
@Rosalind_0041
TTGCATCAACCATATGTAGCCCGTTGGCCTTTAATCTTCTCACACGCATGACCTGACGTAATATTAAGGACAAAACAGCAGTACAACCGGCTTACTCATGTGTCCCGACTGCTAGTGGCTTTTCTTCATGTGAGAAAATGTTGTCTAGCTAAATTCGCAA
+
G<HG<?@JB@BC:HE:H@CF>?C<DFD?AD??D@GBDJJ@C:CDA?@FAEAC=D?FEBC?9C;>==?CGCEA><JAE8@HGI>@FFE;JD>BBJ=@<A<8;E;CFD>D?:EID@<;3EADG;B:99:D?;=BD<:67<?D?8E766@<7>>:1>735/..
@Rosalind_0042
ATTCCTAATTCCATCCCTTGATAGTCCAGAGATGTATGTTTGGTACCCTGCATTTTTGTTTAAGGATTGTTTAGTCTACAGAATTTTCTCTTGTCACATTTCTCCGTAGAGTTCGCGAAACATCTTCATTTCCCGAGAGAAGTAGTTCCGCCTCGCTAGA
+
5/6698@633,;3620?+:15484=88403514373=;:5/-1.8-34516(90/208213346.20;.04.22014+,17-,:3,/@2(4,'9;2*+.660**4-625,303:'+.7,2/8).0+4%0,,+))8;3+6(${'$'}24'--..+1++%0+!%!*!
@Rosalind_0043
TGTGATGGTGTATGAGTCGGCGATTTGTACCCAATATATACGCTGCTACATTAACCGCGGAGACAAGCGTAATTTGCATGACCCCATTATGTGTCACCCGGTTATACGAATATACTGCTCCCCAGGCTCACGCCTTTTTTCCCTGTATATCTGGGAGTTC
+
>E=B9=B@7E9BH3<96=6BJ8<6B=78@?:7<B:5<AB3<:>>?6;;;69A?CB7<>?6>@07@6A88B=:9<?:;9CC@A:=>>C63:7;;6;=/597==;D=A=6:43:;2<9?49;1A.:B:7931.7<:<573+15:;20.<85-631,*(1..'
@Rosalind_0044
ATCCTCAATGTAGTGGTATCGTATTAGTGGAGAATATATTACAAGAAAGAACACTAATGGGCGTCGCTCTTCGCCTTTGAACCCTAGTAGCAGTATTTTTAGAGACTATCTCGCGGCCAGAGACGCTTTTACCCTTCGGATCGCATTATCTTATGGTGGG
+
G?EG@CFJD@D>@BF;@JDDB@@=BIC@C;;D>GG:HAC;7EBIG?>BIA:B>B?B?JFA8A@>H@D>B?=6A@J9;HD<?>D9A=:@A9@BE79?>CG>:;9;C4DA:>FDD@65=E9=9@6?:==@HB<?4==EB<4;47<=187:9695;.:8309,
@Rosalind_0045
GCATAGGCCTAGTTATGCGGCACCGCCCGAGAATGTAATACTAAGGATACTTCAACACGTCCGTCACTTCCCCCCTATCTAGCGACTAAGTCAGCCTCACGCGAAAAAACGGGTAAGGCATGCGAGGAAATGGTGTAGGGGTTTATGCCAGATAACCATT
+
0=3.127?28884.4/7264.:3538127:>401.>424,547238=;+734?*.3713.461531.16250610+,3006/815060.429:,${'$'}699524+'8,85+208805?1.4.774+*22,+/1//2-=1-01)%1..-+.*),)#)&(/,'+!
@Rosalind_0046
ACGAAAACTCCCACGTCAGGGAGGATTAAGGACGCACCAGCCGGGAGAGTGTATACCGGAGTGCCTTACAACAGGTGCTGATATTAACCGCAGTCGTACACTTAGCTTTATCGATTCATACCATTGGGTACCTAGGTAGTGGAAAATGCGCTTCAACGGA
+
>EC==E=DI<?CCA9:;<??AHC=B<AC@C@CD;8@B2BDBBE5@9JD;?>B@BFE<JF@<@=ADD<E<??9CH989B;<CB==;<B8@9<D>5>7>??H5B@???@H;A:7=;:<65><>=2:4?4@69<A=:;8:6:=7C7<17A>/?403--4'*/(
@Rosalind_0047
TTAGATTTTACGTGACGTGCGTTGATAAGGCTGCTGTTCTCAGGACGGGGGGCTACGGCTGCGGAGTATTCCACTAAGATGTGCGCCGGAGATAACAGAGCTCCTGCCGTCAGCACGCCCAGCAAGCACGCGACTACTTAAGCCACGCTTTTATGTCGGA
+
>A=6B?6CBG@><I;BB?A;BDC<;=AA3@AED>B=E>?H@<C7CF<>D98AE=@9=<FB79<;;=?8;7;<8<@>;55J:?7C=;6?C?9@>7<H8>7?@=<9:9I56?=9@>93>9C9E<GB7;:07=:>;:7:7:<<5C6.@,;.14(19,5373-&
@Rosalind_0048
CCAGAAAAGTCTTCCCATCACACTTTAGCGGAGAAGTGTCCTTTCTTATTCAGAAGGCTGGACTTGCTCGCTCTATACGCGGGATCCCAACCCCAGCTCTAAGGGCGTTTGGCCATCCGCACCGCCGAGGCACAAATTGCACTCTCGAGAGGCTGTCTCA
+
;99;@@>@=@AF<E/>:@>;;E8?2<;3:<>DB9BF@>@G@887=<<<=HAA678C4795;4757AC6=;=9>9;9;::787=584?85;49:39:?=926;6<8>?9?B9@A69A:?858:;4778479;0/4066<647(4:0046-557/*701+,!
@Rosalind_0049
CCATTCTCTTGGCCGTCTATTTCCCTACGTTACAATTTTTTTACCTCTTCGCCTCCTATGCAATATTACTAGTGCGTTCGGAGGCGCAGGTGGTGTAAATTGGGGATTACCGTAACGGTACATCCTCAGATACGGATTACATGGGGGGTCTATGTAGGAT
+
@;HEAA=DEJFDIH<=HGB@6J<?A?J9>J?EFE::<BC<C?FJE?>=@HG=?;:GAFDBJGHBABBE<AJC?>JB:FE>C?C@:?H:@=CD@?D>:?3D@=CB@;F@<E:FA??DA=AAB>ACI2@E>?B;<86D2G49?@0552=:5<:9DBBA554/
@Rosalind_0050
TTTAACTACGTGATAGCTTGTTTGTTGCGGAATCAGAGTGCTTTATGGGAGCGGACGTTTACACATAAGCCGGTGAAACCTAATTGTCATCACGGGAACTCTGAGCGAGGTGAGCTTTCAGTATGATGCGTTAGGATTTCTTCACTCGGAAGTAATCATC
+
;<<C<H6?CA@EDF?A<EGDFCC6D@@@B?>?>B9G:C@DCE>AB@=??G?9=CFA;AH@G=E9BGC:=<:><D:C=<AD<?6E:>A<9>E@9<7@F;/8D4;BEA?;>0>A7<A><9=>?;@C?6<<A:6.8H;<<19125=8432.8519.39.+.1&
@Rosalind_0051
GAATGGCGCTGAAGAGCCATTGATGAAGTCTTTGACGCGTCGGACACAAGACTGCGTGGGTGGCTCACCAGCCAGGACCTCAAGTATTCAAGGTGCTCTGTTCTATGACGCTTTGGTCGTTGTATTTAGAATGAATCCGCTGACCGGCGGTTTGACTGGT
+
;JJJJIHGACDJJJJ@=E:DG?A>DEGJGCJEJ@3C6><H<B>JBAGFA?;H@@JBGDA<HDB<H=>BDA>D<@>B@@:<9F?6H9DCD@<@<DCC?CBBIBG:G?H=7>I<>C@=DE6DCDD;=E239<I7>:?:999B32;==6>7868:773/1380
@Rosalind_0052
TTCATGGCCAAATGGTTCTGAAGAACAATTGGCCCAACCGTATACGCCCTCATGAACAGTTTAAGATAAGGCCATCATTCATATCGTGAGGAGCTGAGGCGTGAGTCTACGCCCGCACCGCTTGTCAGTCTCCGTTTTAACTTATGCTTTTTCGAACGCT
+
JECDDFACJ>D:J?FCCC?CCD=BJ?>>?AJB9EEBF>DF<>8:AEJECGI=AD?G?<>IA@HB?C@A?>;JF?6??>AC<=GC?EE>BCAB<F<:HGBC::8<=7??@DC@I9?9=@;J<@=?DC=<:DCF:8>=F@@=9:.99489787;7?661)02
@Rosalind_0053
ATTTCTCTCTTCGCTTTTAGGTAATAGTCGAGGTACGTCGTAATGTTGCACGTTCCCCTCTGTCCTGGGTGCGGAGAAAAGTCCCACGCCGATTAGAGTGCTAAGTTGCTCACAGCAAAGACCTTTCAAAGCTTATGCTGATTACACCAAGGATATACTA
+
<8<:>;8;H4<52>59241274998</4<<,<95671@5037;-4<:<@664=325563;28B89:9A68-:5517/C<208B806&:54379@92476/121;A80.+7<97812142160685::18.6216'%8491+6,-,30.%'1${'$'}0-,4"#)+
@Rosalind_0054
ATGTAATCTGGCAGCACCGCTACCCGAAGTGCAACCCAACGGGGTCTCAGCACCCGAAGGTTTTTGGCGCCGGCGGCACTCCGCTGTGGATCCCCCTCCCCATGAGACTCTGTTCGAACATACAGGCATGTGATCTTGCTGCGGCGACTCCCCCCGCACT
+
C<><C>H:AG:=HB?;IE7:DC=D7<?=6?;C@F=?5B>A:E?HBE@E<HJDGBA8AHD9<FB??:F=@59C>G?9E>D@<>7A>9E97;=4<1?<A>6?<5D=@>:?8?H37CE<;@/7<@=:=A4<;=AA;F299:2==8C38A938>42<04;,/1'
@Rosalind_0055
AAACCTCAGGGCGCAACTGCAGTAGCTGCCGTAGGTTTTGGAAGTCTCGTATTATCTCCCAGATGCCCCACACATATTCTCTACTCCTTTAACGACCCGGCATGAGTCGAACCATACGTCTTATCTATTGAGCACCATACAGATTGATTATAACCCCGAG
+
;BJ>=GGEC>9?JD:C><ACJ@F:E>C=>?5BBCAD8ED?@AHH@J:;<>G@?CDC?CI>IA?BBA?G<DB77FD1A<B9?C;7;=BD8C=97;?9<==6=4;<;5=;=AD:@7F;C;>@:?;4=4=<9<AB5::;1058=268<316<7:8<:811813
@Rosalind_0056
GGTTCATCCGTGCGGCTCTGTGCTTCGCGGTAGCGTGGCATCCTGACCGCGAGTCCTGAAACCGCACCAATTATTTCCTATTATGTCCAGGCATAAAGTGGGGTTTCTAAAGAGTAAATCTGTTTACATTTGACAGGATAGAAAATAGGGATTCACTATA
+
,7;?<7491.385;8?-24@711/'-.46-,6.86/85-3975-1=-68253/47.+-?63+446>828089=6180=/431.52<//7*20..49&5241,6<+?9-4+*0.,,85162"003413${'$'}5120${'$'}0+.'-.*:)%)3+0)(.)+.!'*!'${'$'}#
@Rosalind_0057
ATAGTGGGGATATTGTAGAAATGCATATCGCAGAACGCTTGCCCAAAGTGAGGCCGGGTTAGAGGTCGGTGAATTGTGATAGTTGGGTCGTTCTTTTCTCCATGATTAGCCTATAACTACTCCATGTTACACTCACGTACATTATACGGGGGAAGTTGTC
+
AHC?D=@A>HECAEFHFGGFCAEFJ>EJ@CE:;=DJG<HG>@D;CBD<JJE>DA?GIF;EEJB>;C;DF;F:A@G?IH?J@D?C@??B=C@EHI>>>@JG<C?CGC<:A>9=:F;DA>B8;>@9A:@74=595:<77>A?64?5;C;H>;D712/5115${'$'}
@Rosalind_0058
CCACACCAGCGTAAGAGCCGTCGTGGGGAGCGCAGAACAGCCTTGGTACCGGTACCTAGAAATAGGCACCACCCACTATAGTTTCATCTTCCTCAGGTTCTCACGCAGTTTAATGTCTCGATGCCGTTTTAGACACATTTAGAAACGTGGTATATCTCCT
+
6<CFH;D;<?EF?E>;F@C<J:?<?=@CH:C:C@BB??>;<:=F;><6A:<;?9=BC@H;@CD@>?7;6D<;:==D5??8<B::::D=?E@4=EH>>7@76@8@;8DC;>@60:G<A8;8A;>A;@9@?<:6:8;2;5:::A;2@324?4:10:8:0-)-
@Rosalind_0059
TTCAATATGCGTAGCACTCCAAAACCGGATTGAAGGGACTTCAAATAGCCCCGGACTAATGGCCGGTATCTACAACCTTTTCTCAGCGTCCGACCACGCAAGACGCTGTGCCAGCGGAAGGCTCGCGAAGTTTTGTCTAGGGCAGGGCTTAGTTACGTGG
+
5244874?-5<+782/53/58326;<8*'9?90;4061695<75.3.09/36930621,=,2?78.3:.C95-7707/3?183686.4.405/614/(/2=10/545301413+,&1)/*/4/142%0?.#*'33)&22641..42+)'0*+-.*+-'*!
@Rosalind_0060
GCGTTAAAAGGAATTTCATTGTGGAAAGGGTTTACCACTCGGAACCAAGTACATCGACGGTGCTTGCAGCTACGCGTCCTCCACTGGCGGTATAGTTACGATCCGATTTACTGTAATTGCTGACGGGGAGTGCCCATTATGGAATGGCAAACCCCACATC
+
I:>EECJ<@B>AEH9@EHBA;>HC<AE=BIC=;<;C???F<IB@E??G@D@<G6H<4H@?6?EHD<D:@BBA?@EEHAGEF=B=4ACJD7I@E;EBA@DC>=H:BD>7?E:@@??5<G>B<?99@78A49A@@715:=:7=?@8:7?1661.56+483;*
@Rosalind_0061
CCCCCCCCACTCCCACCGCTCGTGGCAGGACCCAACTCAAGGTAGGTATCAAGCGCAGTTAATAGCACATTACTACGAAAACTCTCCAGTAGCTTATTACGCGCTTTGCGGCTCTCGTGTGGCTAATAGCGTACAGTAATTTAGGATTGGGAAAGTTCCC
+
BJJ;@G?CBEJ?@FCJBA<EAG<>JC?FE@=JFF8BE;G?EF>BDEJ?FECJ=@7@GAD7@?C:ADA<;JFC;C>?G?AA;?C@FBDFE@@;<D:<<;8E<;=A??C:??A<;99A@>;=J=959:;A?6@@C88D=7:85<4<5:092;?39554/=71
@Rosalind_0062
TGATTCGCCCGACTGATCAACCCGGACCCTCCCAAGATAAGCTTCCTCCAGACCTACCGCGCATCTGCGAATTACTCTTAATCTATGGATTGTTTCCCCGGGAGGGTTGCTTAACGTACGCAACCGATTAGATGCTTGTAAAGGGCTCAGGGGCTTGGCC
+
GDE?EAGG@DBG@AC@FGBB>>JAE@=HH;J9>JGAACEC?GFC?;AFB9F;@;GGHA8J5CA<BC><<;:I?=;E@AJ=DJCFDB96=B>==?G>=2;A?A;@HG88CCFGJB9BC9>>?@B=>;7<==@;5@@=93?>G<7A7==@731<4:2413,2
@Rosalind_0063
CTCTGCTGAGGATCCTAAGAAATGACGCTGTCAGAGCAACAAAGACTATGATCTTGGACTAAACCGCGATTTGTTGTGGATTAGCACGGGTTGGAGACCGTTACACGTTTAGTGATGGACCGATTGCCCAGAATTTAGTTGTTAAGTTAGTTTCCGGGCA
+
IC=@CEJC@FBJ:;E?C?GBC=:F>>;6AC9?B9:=9=AB?6>?>IF6BC>B;<@>D9;CDG:=:39=;9A<DFE=:@<9I:9F?A@>78=C64C@:<9:BB?D;=@>;6@6><8><6:7B8<6:=E7?-8<::;421<8?>9A6>17<0592<6.40/0
@Rosalind_0064
CTTTCCTCAGCGAAGCTTGTCAGGCGAGCCTTACGGAACATAACTCTCGCAGCATGCCGCTTAGAGTCGGTGTGTTGAAACAAAGTGGAATCCTTCCTGAGTAGTGTAGCCTCCCGTATTAGTACCGCTCTACAATTTTAGTCAAAAATGCCAGCAGGTG
+
E?E@@GJ9;@A?EGBB7C>;G@=CBB9A@;DDB?>ID@=CDD=1CCCEAF>@DF@A@C7CI<>DCBG;B?@@CE8C>HC7<;@6=88=E?F5D=E=?><H??;E=@6A>J9;;@;?CD8@<7?B@8:9@B<@J==768B5=6779:96312B;6*2<9.6
@Rosalind_0065
AGAGATTGGAGTGCTCATCCAGTCGTGTACGATATGCGGGGTGCTGGGCTACAAAGGCGTGAAGACTATGGAAACGTCTGCGAATAGGACCAGACAGATTAAGAGCATTGGCGTGCAAACGGACCAAACGGATGGAATCCTTGGAATTGAGGAGCTTGGC
+
=?;845:63:677/74483;;;;>6>0/9797.38230:7+746<7:9446?4;6;38:.;0;/@33696=0<406,7216229A42,4<(4.3*2742:.126253@,375:+2.43/04+1A-2,,'4,5+0'(1.9.6,366,)'-1/.&*/)&${'$'},!
@Rosalind_0066
TTTATCGTGGCTTTTACTCTGGACCAATGGGGTGCGCTGAGTTGAATGAAGTAAGATCCTGGGAGCTTGGCCCCTCTGAGATACTTTAAAAGGCGTTTCAAAATGTATCTGCAACAGACCGAGAGGCTTCAACCTTCGCGTATGCCGGTCCACTATGACG
+
47<>36629?;42=22:88.7/78?7/30996=<;298=8;92-C592:659333569:.0717.835;2933950456423933;5/841527/265;;/0@6/13>.42413'-673/-822618/2..923/3!1//63/5.1+2()+!${'$'},**!*%!
@Rosalind_0067
TTGCGGCAAGGCCCACGGCCACAAACAAGTTATGGGCATTGGACTCCACACCTACTGATGATACCTTGCGAGCCTCGACTGAAGTCTGATTACCGAGCCGCTCCCTTTCCGGTAAAATATGGAATCGCGTTTTATGGCGACAAACCGACGTGGGAACAGA
+
8;:46>788;926698/957:734.2127-:37=2039;6@.=933236439666<=/2<2787;;8:93529/15<0136>0/3:-180.564=+612265*034;:-4:.:.36&:046+1+/0/.,9.*65*/0-5+++*86110.2"-&!,,!${'$'}!!
@Rosalind_0068
CGCCCGATGTGCCAATTTATGACATACCCCAGTATGTGCTATGACAATAGAATATCTGTTACTACCTCATGCCCCTAAGCTGGTTCCCCAGTACGGACAGAAATCGAAAAGTAATCGTCCGTATTCACTAGCCCACGCCGCGTATCTCCGAAATAGCTTG
+
E<DB8FAH?ECGB9GHGBGFJ?:9?>GCHAE7H@AJ=AJA??>@G:JABAI9F;:C5FJDBAD@><CD9@J?JDF;@C=>EA;:=:B9E8?A@<<=G4A;>EG8>B>@6?C8=8E?@<69J>56=74;9=<>E:;CAE;98<;<:646@0011.712<1(
@Rosalind_0069
TATTCGTTATTGCAATGGCGGTCAACCTAAGCGCAAGCCTACCGTCTGAGAGGACTGAGTACAGCCCGGGAACTCTTCGAAAGCACCTACGATCGGACTGCCCGTTTACGATCTACTCAAATGCAATGTATTTCTGCCAGATTCTTGGACCCCAGTTACC
+
@EJDFAADC=J<@J=;@=?E:@F=A@CF@DB@B?:>CEBDH>9J;>9B>E9DDG@?G>=B<=BI<JH29IC9;C==F=@J>>=19??;@B342A7?E;I9<9C27?5@>@;;1<;7A@D4826=88?:;@@75=86859;:97=4/61?4?:58-/:11,
@Rosalind_0070
ACCCCGGACTGAGGCGGCACTCTGGTCAATCCGAAAGACCATTCGGATTACGACAGGGGATCTATAGGATAGCCCCTGCGAACAGACAAGTTGATCAGTTGCACGTCTGCGATTAGCTGTTTCGCGTCCCTGTGCGTCTGTACGGGCTGGTACCACGCAC
+
FDEJ>A>EAF?E6@?AJ<=BJJ=@6<E9AB@@F<DD@C>D;>AAABAEGJA@GEJ>;8><GAA?IAJ;JCG<A>DHG=>>>H>?H8E@>=EEH988=8?<@B=CF?@AA<A<;<9B<9=?>D>@;@C:95?9B;8A0<7B7888A>;5>00;0:;71/9+
@Rosalind_0071
TCATCTACTGTAGAAAAGATTCGTCAGGGCCCTGGCTGAGCGACGTTCTAGCTCGGGAATCTAGATTTTCGCAAGGTGTACTGCGTTCCAGAGTTGGATCACTATATGAGAGAAGCTTAGCTGGACGAGTAGTGGGACTTGGCCGGGGGCTTATGTCGCC
+
3.=A3.4>5;409<72-9406?5730>9A2128>52566<.1//?<?0:<:1/50-07<23343675,21173,9/>3279-54:,841(194+3/>:<-57/*.8+72/*43,0,1.26314!2-76+43/(516'5'/.,//7-32'4/)(,,.0)%!
@Rosalind_0072
TTAATACTCCTAGAAAATGAACATCGTGGAGTGTCAACTCACCTGTGCTGCGCTTCTCACATACTAGACTACATTATTCAGTATGCCTTCCATGTTATGCCGAAACACAGCCACCGGGTGAAGGGGGCGCTGATACTTTCCGTCCCGCTCGTTCCGTTCC
+
GBHG?<=@=C>EH@9IDA>;>@E?@;=G?<?CB>GHGH=BGJEBC;G?H<;IBBG;AG=@>E<=3ADBAD>C??69B=AB<;>;<A:?6??;<<BB;7>?8@=@?<GAH@8C>6J<7;3>D<?6:8=<7:B98F9>9<7:>7==6A=8>B;2/-:.924/
@Rosalind_0073
AGGGGGTTTCATTAGACAATCACTCAGGCTCCCTTCTCGTATGTAGGAACATTAATTTGCATTCAGACGTTAGGTGTTGGTGGTCGAGCTGCTGTGAGCAGCTTGTTTTGTCCCGAACTTGCACTAAGAAAAATGGTGCATGATAGCCACCGCATAGAAA
+
6.45,>2;13987E8689,213571>73633697;3603,/892134:.334:1/-+60:0;9-384453-69.63:7+62175.*42+8)4:)34+-0.3.212035/8-54//,,.5/12107+4/43/1+02*/%%42'8+)-(0(,%#!.${'$'}3"!&!
@Rosalind_0074
ACCCCATACTGGATAGCCCACAGCCAGCTTATTCTCTGATGTTTTGCTTAAGATCGTCGAACCCAGGCATTATGTGACGACCGAGCACTACTAAATTTGTGTTAGAGCGGGTGTTGGGAATGTCTAAGGGTCGGTCCTCCACCAGACTTTGATAACGTGG
+
016:3889;471*4:<9:<4;7653/52467224098;579;<35=90045'817//7.5;2,.66=390552@4)4+/=0;,421:&820*621//3-211)0/3162...%1./5355(307.:73.)0${'$'}14032;*%+(0&,.*+1*${'$'}/*/,"&!)&
@Rosalind_0075
GGCAGCTTGTCCTGGGTCAGAGTTGGCCAGGTGCTACGTGCAGTAGACGTCGAAGCACTTGCTGCTTTCTTCTTGGAACTCCGGTCCTGTATTAATGGTCCCTATCTAATTTACTTTGCTGTATTCCGGTAGCCCATAGGCCTTTTGGAGCATTTGCTGA
+
7=478??968353+4039;8:476=5622<9/4971,74569-7<342982.2A6881(8<=.3-84;-2<?5323214-2-/*3G5-9:84613..1353515128152,/11.439+2005-43;&0-05',06-6)''*,/)*-+*'-+${'$'}+%!**'#
@Rosalind_0076
GAATGCCTAAAAATATAGATCGGCCTTAAGAGGGCCATTTTCATGACTGCTCCGCACGTTACAACCCGTGGATAGGACTGTTTACGTTTAGCTAGCGGAAAACATAGTTGAATATCCGAATGGCGGCGCATGTCTTTGTAGGACACGGGTCCACGGCTGC
+
@F<4B9>>D8=;=E=A3><=8D153C7?=238G87=>C>GE?<?<>=6=<B<@>69>5:96:@:5AF=B@A34>@8366=>:8;<4=8:?88=/94;36<=@83;8=?997>;5;896/90856;6:748=4/->924582+-@06512*914:458(5-
@Rosalind_0077
CGTCGGGAGTTTTCTCGAGTCAGGCACCCTAGTATACACTCCGCTGTTAGACGGCATTTCCAACTACCCCCTTTTGGTGGACAACCATCTATAACCCTCGGACCCGGCCACCGCGCGCAATGGACCTCTCTCAAGCCACGCGTCTACCTGGCGTCCTGCC
+
HHI:8IB>GJB?B;<HAJIFJID;EA>E:CH;FCGD;BEABFH@;>=FHAHFCA>G>@=ID<@9?E9D?6ACEBA=;<AA@E>7<DCB@8<A8=>J<DE>4<<?;;BB=:<99C;HBI94;C;69E999;6;9:88;:7<87:97689;59<7A>8-615
@Rosalind_0078
GCGATCGGGGCTTTTACGCTGGTCGCTTGGATGTATTGCTACAGGCTTGTTCTGCCAGACCCGCTGGTCATGCGACCGTCTTAAGGGAGGCCTAGCTTTAAGGGAGTTTCCAACATGTCGTCTGGAGCTTAGGACTAGCGGAAACTCTATGAAGTACCGA
+
G5EA;IB=FDG?>AA;H9<BICA;8F<9D:;E7HA:ABJ@BDCFEJCCG<ACDIE7CCD:FJ;@EDAC><GB=DECD:>DBDCJC<><CEB@<IGA?J6JG??;:DBH8C=@=?8>A<?@:>ADDA9@=E3=79@>96C?9;99C>9D6A668-2.305-
@Rosalind_0079
GTGATTTGAAACAACCATGTAGCATTGCGGTAGAAACTAAGAGGCGTGCACGCTGTTGTCTTCCACGTTCGCCATGGAAGCATCGCAGATGGCTTGCCGTTCTCTTCTAAACATCTTTCCTACGAGGACAACGACGCTACCTACTAACCTAATACTGTGT
+
?J<88@=<45AD;EA9>AB::G:;750@59;>:G>>?A?B<9B9?GH=@@C81;>28=7DC.>=?8/>@<=B:::>?7>7F<?482C=><;<>.;<9;:A;?A>>53;>>D<785/*6??;21688592C?<8-2<>.><*548>5/-993-320/)*"+
@Rosalind_0080
AAGTACACATCAATACTAATTGTTGTCCGTAGCTGAGATTGAGACTACACTATCAGCGGGCCGTTTGTAGAGGCTGCAACGGCCAGCGGCTCGGGTAGACCTCCGATGTCTCGGGTGCGACGCCATGCAGCATAGAGACATTGCACCTGAATTGAGTGTT
+
@CBDJFFCJAH<E=@;G=F?>CA@@BFJ@<6<<?D?I>GBEJ9G?>:;A@FF<A==F?F@AAID8D:@AC@BEAJ?=A7@DA>>9A=@HAB=F<>G>E@A@EEC<FH=AC=;><AI>D@E;@56:?C>><78G==@:>78>G6A3/9988:92;63><7-
@Rosalind_0081
CATGTACGAATCCGGCCATTTATTAAGACATCCCCGTTAATAATAGCAGCGGGTCATGGAGCACGCCTTATGAATCACGTCGGGAGAAATCCTCAATGTTCGAACAGTCACATTAGTCAAGCGCATTCATCTTTGAGCCTTTCCAGTGGATGTCTGTGAA
+
<A=AA;@B=<7@DG<8ED@F@CJA<=@@?B=9;><<GG?CA88EEG;>C?:@:ACI@D=<D699CBA8I<<@B>AA3<H>:AE6E?AF;BA<>>;F:AC=3;=9=>884?=?>=7B4991<66C5>8<:?3=:79@>8>835:4883795649<3671**
@Rosalind_0082
ACTGAAGAGCGGGGACGATCGAGATATAGGTACTCCTGCTGCGCCGCGACCTAAAAGAAAATTAACGGGGTGACACATCTCGTCACTGAAAAAGTCAGTAATCATAACCGTAGAGGAGGGGGTGCACCTAGTACAATCCCTACAGGGATAACGGCTCGCT
+
DEFBF<DD=F?C@EH=>8?@B?C?J<BC>HHD?EID=E>EHJ5G?>?DF?<?E@IAB>9DD?;;BGFD;9F><C;@CEFE7;??>;D@:=<BC9>89C<<<EI:C?CI:JA==>=>BCD?=@;<A@=:B=7<@ACC@9:?8:89<::>/9;9981032,.
@Rosalind_0083
GGTATCAACTTCACACACGGTACTACATACCAGATACGACGCGAAAATGGTGACTAATCTGGACGAAGTTAATAACGTATACAAGTTTGACAACCATTCCGGCATTTGATTCCGACCAGCATTTAATGATATACGTGGTATTAGAGCCTCGCGCGGAAAC
+
C;@AFD@CABIBI=CC=@JJCFGFFC>EAFF<@7><GDBJJJA9?G:C<=A=AF;:?C@EFJD@HCC9IDD???H?B95@A6AG@>J<B7>2;;D:>:<C<=@;DF<?@>=;?><4F=97=<43<D9=?;4;A<B59>A6952<87A3869238:47232
    """.trimIndent().lines().toMutableList()

        val expectedResult = """
@Rosalind_0049
GCAGAGACCAGTAGATGTGTTTGCGGACGGTCGGGCTCCATGTGACAC
+
FD@@;C<AI?4BA:=>C<G=:AE=><A??>764A8B797@A:58:527
@Rosalind_0049
ATGGGGGGGGGAGACAAAATACGGCTAAGGCAGGGGTCCT
+
<<65:793967<4:92568-34:.>1;2752)24')*15;
@Rosalind_0049
ACCCCATACGGCGAGCGTCAGCATCTGATATCCTCTTTCAATCCTAGCT
+
B:EI>JDB5=>DA?E6B@@CA?C;=;@@C:6D:3=@49;@87;::;;?8
        """.trimIndent().lines().toMutableList()

        val threshold = readList[0].toInt()
        readList.removeFirst()

        val qList : MutableList<String> = mutableListOf()
        val outList : MutableList<String> = mutableListOf()
        while (readList.size > 0) {
            qList.add(readList[0]) // name
            readList.removeFirst()
            qList.add(readList[0]) // dna data
            readList.removeFirst()
            readList.removeFirst() // toss the +
            qList.add(readList[0]) // quality line
            readList.removeFirst()
            outList.addAll(bfbq.trimLeadingTrailing(qList, threshold))
            qList.clear()
        }

        println(outList.joinToString("\n"))

        assertContentEquals(expectedResult, outList)
    }


}