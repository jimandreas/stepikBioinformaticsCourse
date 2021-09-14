@file:Suppress("UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import algorithms.AlignmentLinearSpace

/**
 * Code Challenge: Solve the Middle Edge in Linear Space Problem (for protein strings).

Input: Two amino acid strings.
Output: A middle edge in the alignment graph in the form "(i, j) (k, l)", where (i, j) connects to (k, l).

 * See also:
 * stepik: @link:
 * https://stepik.org/lesson/240308/step/12?unit=212654  MiddleEdge problem
 * https://stepik.org/lesson/240308/step/14?unit=212654  LinearSpaceAlignment
 * rosalind: @link:
 * http://rosalind.info/problems/ba5k/  MiddleEdge
 * http://rosalind.info/problems/ba5l/  LinearSpaceAlignment
 * book (5.13): @link: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
 * youtube: @link: https://www.youtube.com/watch?v=3TfDm8GpWRU  Space-Efficient Sequence Alignment
 */

internal class S05C13p14AlignmentLinearSpace {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }


    /**
     * TEST DATASET 1:
     * This test makes sure that your code can handle runs of indels in the reconstructed alignment.
     */
    @Test
    @DisplayName("linear space alignment test 01")
    fun findMiddleEdgeTest01() {

        val sample = """
            Input:
            1 5 1
            TT
            CC
            Output:
            -4
            TT--
            --CC
            
            was originally:
            --TT
            CC--

        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 2:
     * This test makes sure that your code correctly mismatches characters when the ideal alignment requires it.
     */
    @Test
    @DisplayName("linear space alignment test 02")
    fun findMiddleEdgeTest02() {

        val sample = """
            Input:
            1 1 5
            TT
            CC
            Output:
            -2
            TT
            CC
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 3:
     * This test makes sure that your code correctly aligns the
     * upper and lower sub-matrices after recursive calls.
     */
    @Test
    @DisplayName("linear space alignment test 03")
    fun findMiddleEdgeTest03() {

        val sample = """
            Input:
            1 5 1
            GAACGATTG
            GGG
            Output:
            -3
            GAACGATTG
            G---G---G
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 4:
     * This test makes sure that your code correctly handles
     * inputs in which the match score is not equal to one.
     */

    @Test
    @DisplayName("linear space alignment test 04")
    fun findMiddleEdgeTest04() {

        val sample = """
            Input:
            2 3 1
            GCG
            CT
            Output:
            -1
            GCG-
            -C-T
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 5:
     * This test makes sure that your code correctly handles
     * inputs in which string t is one character long.
     */

    @Test
    @DisplayName("linear space alignment test 05")
    fun findMiddleEdgeTest05() {

        val sample = """
            Input:
            1 2 3
            ACAGCTA
            G
            Output:
            -17
            ACAGCTA
            ---G---
        """.trimIndent()

        runTest(sample)
    }

    /**
     * TEST DATASET 6:
     * This test makes sure that your code correctly
     * handles inputs in which string s is one character long.
     */

    @Test
    @DisplayName("linear space alignment test 06")
    fun findMiddleEdgeTest06() {

        val sample = """
            Input:
            3 4 1
            A
            CGGAGTGCC
            Output:
            -5
            ---A-----
            CGGAGTGCC
        """.trimIndent()

        runTest(sample)
    }

    /**
     * SAMPLE PROBLEM
     */
    @Test
    @DisplayName("sample dataset from word file")
    fun findMiddleEdgeSampleTest() {

        val sample = """
            Input:
            1 1 2
            GAGA
            GAT
            Output:
            -1
            GAGA
            GA-T
        """.trimIndent()

        runTest(sample)
    }

    /**
    SAMPLE GIVEN DATASET PROBLEM

     */
    @Test
    @DisplayName("sample dataset extra")
    fun findMiddleEdgeGivenProblemTest() {

        val sample = """
            Input:
            0 0 5
            MEANLY
            PLEASANTLY
            Output:
            8
            -MEA--N-LY
            PLEASANTLY
        """.trimIndent()

        runTest(sample, true)
    }

    /**
    Extra Dataset

     */
    @Test
    @DisplayName("extra dataset extra")
    fun findMiddleEdgeExtraDatasetTest() {

        val sample = """
Input:
0 0 5
PTGQSYVTTARTTAECRVLHVMPFNYHMASIMDSYVFLNFGPALCMHEWYLCTMRCGWSKVGLGYMTCFCKNYHMSVKDAAYDGDKEMDGMTKWCVMPNCMWENEAQDQMQAWDSKGWQDFCDDIKAGMQFIWDSEPHGNFSEIMSMPFDIDVTIFHMQEPEIVQWTMNPQHSPHRPKSCTMASWRTQHHTAWNHCPVSASAFQPQVDVCDNVRFYGETAMNIVGGQAEAEKMKIHPSYQGHIHLCIGNEDTDGQQLWCQNHMQHEPFRYNDSDGDVTYQKHPACAAIPNIHSWFQPWGIDYQSNRQFGNQMDECYDLWALRVWDEPSVTWYYRHDLHDHSESWQRCETNVMWYKGAKDMRGDLWSPRVMIMVPFLTVWRCGVTCGWLWPKSFSKAMMRAQKIHEFPQQRIKTNGAKPDNEREWQAHHAFNTECKFVGPKPILLSKPWRQVDYDYCSFSDDMHFRKCVLTDEFFNVVSTKMVSQCWFWADTLNPEVSNQFMTQEYIVKMTSVCEVLNGVGGLPFVTADSCSSPVIEWGLWTNDQWEGFFKLYWVMLDNDKNPVKWPHNRGIVHGMWPIWWIEQNPIKVGQACMWYPLIDNYWEDNRDVLKPKEDMMAIDISGQVKGWATDIRPSSWSLYIIPDMVWRGSLCDLARVEYEHKPWHNCTTYHMRCVIFYYFAPIGNHNDATIPGWAEWCYWPKMWEGYVMVNCFTEQQHQAEAAVAWGWYGCTPNVPPVSPIMQSFKMFICPNQFQDLKLMQDPCWVLNKFSVNERQLDHCPMDASDHWSPSHNRWNLTFQAWPGRQEFAWPVLFFFSDVWWDAHDYIYVNVMGYTVYHAWSASWVVTQLGNIHGECWNCMVPPEIVMSNTNQKYEHYMIASREMVTPHRRRYAVCTFRNLAWKSFDQQFFCRENFIGIFPADCGIIKCEVFRDLQEFFDRENSKCDQNSQKNMHKFKYCFQFQPQDPVKQRLNPVHPWCRSEEDGLRTQEDIVRPAQYNEWPMHQNDAKLVQGCCIYKYKRKWIPRKYLKTYGTNMPEHFYYQRQVLSRYGSMRRMWIKNEQYVDHRDRYVMLEPGCETFFYSFVMEWDEINDNNSRSKEVAPPKEFDYMYNNTCHDTWRFSEQVKNDNQTQFFVKQTFVRLHLQLDQILPEAIFMSFTLDWPQYGYQIAKGNTFKCMQFTNYKGSTFGWLDVGPGNRPRHWWKTVFWQKWWISMWLDVQDLSKDAFDNMWEKQAMQKPKFHDTRFLQAESKDTRSKEADSKVDPWWRQHSQERFYPGGSECCWMDALHPLKLRNFVEFVVVTKLPNCLWHAFFQYFPEMWLCFMDHASPKQKVWRMNCYRADFCYFMCELGYETDDRSAETAIVMYEPMQMGWNHWWWLTWLHMACTLIIDHIMMNLQVALYGCIQPLNFWMATFHLVWQAKVFFFFAFERFHTHVIMCQKAKENESHRLQPEERMSKWHYTCCGTMFHVNWHAEQGKSGMYTQALRLTHFTVWDQGSHLMCTGIYMDMPQNHCSWARHRTDPCALVVHWGPKVPKPNDTFGCHPNNSEIEPFPPRDDAQANHIEDCHEYRFCGMTHNAYTDHPGFLRNCTENVTEKIMEGPLYPWDNDRGSHAQLVMWCRVASEAVQWVSSGYKGINSAYRYVNLWGKHICRAWQDWDWVGVHIQCNHIWGQETDPDEQWLCIHENGINFFDSNLADYTAEQEDFGDWYCQKSHLHSKVDVKQYSQIATIIWTWQHTNCGCSTCWVPLHRIFSLDNDVPPCIQVYMGDKRQMWRNKDNHNKSQMTYMKLECMFPDKDFRQQSTGERPVTELMCKNIWTVHYCYIAMFYDVEPKCDIEDCYMGVAYMMSFAEGFMHMYKALVCPKSGSMYDWTVVQIIYTWQYFWHRPETTESTWTNQRHPLQLGWNTSLMENIFAIESMKKMTCYAKEPTMRRAAIWLVQMSSYMVHHKCPRHYNEHLRLLVPCSWCQQDKWNESCQWHHPDPYIMKPSYAWWDLLNTCDPVWRRNTYCCKMANRAAHQDWSSNGDRHNYPVIRMENTSDTHNMNMYESVPERPDTFCGLNSSLQGHEWQMYSQAHHPDMFTENMQDYYYGTIVFCHAGICWCWLMHIQYSCCHYACCIPLKPLCAFIESQCQIVNQSFASRTTCQDQSFPHYLIYEDFVIAYEIWDKTAPQMFPFYYYWRWVDRTDCHVQDETDGSWTKEDCAGCSCSRELSYMGFNWVFPYSRTVQLMMEHVPGWCYMSGVFLKLHPFVGMIQKGKTHHIWHGDRWHGKGYNVSTDYYDCVYYEPCLRNKYMSDVIGYTGWLGWVQTLTDHVKSSPSKGRIPVWNQFTQVKKYQVMEHLFYKGAHQDHICVTCEGWVMPPNQCFWFQDQDSQCSLQSDQMERLEAVCYPTMWYRGAWKRHNHTRLWLTTYDPGYCRNRDWAWVTCCNCIAALMQQESNRKYQWCWCYWSTNHPMHNSDIYVVWDDDGERPDGCSNEIRQAKRPCTCDISDARPLKIYMIYCFPCEGKYIDIWMGKMRAFDFLNFMDGKFTIRDGAIFPPQMVPCNVLVFELVYKSVWAETPTIRGWYQCWPAQKVYANGWISMLVIMDFAQKKFVGHDLSTATCMNHRVDCFKNNVRRHVEPPLMLIMNKIWCEHDFMTAMDVIIYASPDMYMPGKPYLGTFQYPYLYKHGSSDYVELEASKINGYMPYWCHESEDSTCHALHDPAHCDLRWMFMCCPRTTKPYVWMCNTWIRYDKQDLAPVNSFIPAHQDVHPYCTCGRAVWTQKRFWKAWWFLITCPDPHDSYRSFDEVGEPIETACRDDCVINIYHSQYNMSSWAKAVAFIKWMTPLQPYEPCFCQKMEFKQWWEKLCVAWSQPVFNFSIPKHVFIVERYIEDEHWEVIYWMKKFVIPKLHMGPWQSCTIGYREYACIGIDAHDPYRCGDKNMANMRFPWWDIISFLLFQPLPMECSYHGQGTFCLKWIAARNGTYQFRIVEVYKFSSAEVNRNTQYFSHHHMMLMPHNFYHMGTFDYCWLKYPFPTMDWNVSTTSPNILGLENHKDLCIMVMNCEREMTPERIMQYKVLLSLWRENVVMPCCLIALVNLLGQNKENTPLDCPKMPMVMDYHPRKFWLSPGFIGKYHIAQRTRQWRLIFCPAQTKMDVCASYPFPGPRTDHTRSMWLMGHSTAPEFMFMTNKNMQIGCPPVGAQGHVEPPTRQRKGKHQYVCEPWKMWKHKPQWRAWAINWKKVITCWSVSFDFPWDSIFTVKDCELRGGSFAMMRKAYQPPRMSQLPWVVKCNFSPKQGYEQYITVDGKTQKTRVIDPMPDPHHATYGIMFSHQYTVNWIHNCERLTMAKINRVYFTIIADRWGHYCVNINHQTLQMDDMMCYDDSVSGQGYLCMCCTIIPWGQCVNAYIHRCWHCTDVYIHRLLPEQETVFQFCDNHMMAQMHLMPTLNEKGSFSWQRVMSGGVFWIVNGCNMYAFSHHWLAPHHDRTNQGVYMLSQPQMCWALNDDHTYHKNNINAWEPPIGTHTGWLRAEEMTGSPDRLLLIWGFMCRAMYSCHLDACARNLQFNFLMKVGHHNQHQYWAWCEQCLDCKSWDTNASSKLEFNYETLTDLTGHPPQRPDVFFCDDCVAYCEFLKHTSPLDRWYEPRPRRLGQWVKSLGSGNPPACFEWCYIRYDCWYCNVVPIEHTEDPMHWHENWDNNCIGQQHWINVMCQMMTPNNAGIHPVRPCIHPDDNVRMPYECHNMEPERVQFVDQVTGAPYRANATLPCDHDGFEAFMAPDLTETYVQDQKYCKGVPFQMSKPNQASIPLWSYILYSCEMACIEIYIMKGWMLKQSFHGSPHKTVTCIGTHCMIRHQACCNNDKFAVANRAHEFRWYWARLNGQKMIEFFESFRDMIKISPCMRWRDDAPGSGLHIWAAHIFMEVEKLVWTLAIMNCAYAAYPVMEPHPLGWVDTGYVKSHFQLAYSICFCGQIINRIMILQARYQYVAPATCRLHSCGDDAALTPVNWSFNMGHGMPNINYILNWNRKRWGNFRHQMHIPPGQQCRCWRALKDDNVMHEDTT
QVPFPTVDVIVCCTGIKCEPMNVGYDQQMKDCFICTREYDIRRLHTIVCGSEWACRLWIEADWEDCEKSFRDFDAPINIVQYAVWRANVETQCPGYLNRTQWIMIGYWFIGTWNAVLIVPKSPAQIETDGIVYKIPCNRYFEHGPYFWRSPWAGPYPTVDRHDSVCHGHLKYGSLPSCQNWEFARPHDLGDACMWEKPQLQLNWNPRPRAIISTGTFSPEQTFWDGMPWKYFWKCPSSVQANKRLYKVLTVVCRQENHGYKETHRKFHIKCLVGQLNQPKPWCVYCVVYRSDYPPPQRWTFWGTPQYIMCFVKPHKLSDESAIGNWWNIGPCDRLVASAWEHCKRLGWYPHGWAKSMFPHMNIMGCSRKFRKASIEWPIMSHVGYCAHWHPFSRRVQFESNINQSLRWVVMSSFKDTDDHVALVCLTPAGEIPVTNVGQALAEQSYRIWSAQEHRAPFTGWMNLFCSIGMTMYIEKCSREPIIKDHDCFNDTADPSDTKVTSWMRKYWIEEDPTWRSNMIHMMGSIFSCNRMSNFMCYPESVRADWPIELWPGRLAIGFMNMGVASLEHYFPFIGFWVDYAPSPSEEHQWRHDAYAYDEVYAMVPMDCKLEGQTYTQCMMWKIDLVLLWSGNSEICIEQHESFSRSIYGHVSKAQAVMKYARRGPAHEQFVTGKSQHSQDCTHISPKIMLHSSIRIVAKHDMLRKEPHSDYHMLKTEFQDKYERMTTMMWGFPDWELPHTEQRHKLAGEVRQATASHYQQYYKPDHGTHEYVCPQPCLIAPWASGTPEFEMAYQLTCNGMFAKCYNRRTGQQVLQISVSHSCMRTKMANWYPSMDMFLEMSNGNADLASNRIGHFSYGHEFVEHPNVMWRPDGGRCHGHEAICNGLAYQYMWPVYHNRCNAKWVEVVHHQDSNFLPMIHGAGSHLHHQLAICYLLVCPVTGARCVGENLINFLVIICNWELIVFLIIEMVAEGLRRPMRNKCQATSFNLETYFRKKRMQCTLNRPYMTRTRRPHLWGPELRATNKQRDLPVTAVPCNQAQCKKFWGGVQDQSNDDVNWRDTKWDFSWGFSPAKVHWHQCVYDQGFHNLEYNPCLHWIWYMYTWMIAFERTVGKACHNWEQIPIDSLNNFQVHTDIWIELHCMNMSPYAFVNYSTCNAVAAKWYLELAIAHQSEPQKWFYFVSFILDSRFSPHNMVFYATSDGYRDKLKPLEFDIMMKRGTWTPEHWQSFTPHRKISPVHSTGIHEAVDIYQYFHEPFAMEPACKCMVMIYTVAIVHFKCIANHEVSGGTEINLVRCFHIWHCEEWKYMCHSWFEYNAIFRCEAMLCWKLFCGQSPIDMLTVEVKILWAVTPQMIACADAYLRPFMDWIGAFSLCQQTFCDLFAWPPQVQRFYWTVKEVEEQWYSHWVGKSVNINSSSDHNNRWVLWPYFKLLFNVANHQPDHCREAVWYNVASDRPHVFCMMAGGVPQKTMINQFRHSIIFSVQNPHFYGMQPTWCSERVALVCPKWHAPNAIPPPKFMHARAFWAVPTKCVYQEHDHYWHNHKTSHFPGTSPDIYEVRAQFRSAETHNHPYNDYKPLMFVKTHITIAKFIGGKMHMMGTQGYAMRPCDWESKMMVTFVKVVPPALTCIFFIPAQPHTMTGWALYDRYMVCRMCHEVEPCKWFVIDVDHNQNDSIMRSHPSERGTTGICDQKHHNLQHCNWELDGPPEISMTYPILNSELDLGWYHLWCGDGPMHPKFGRDRGVTEWKVTIKTPFNLAPTIENIDAQSITRWSQYMINKADMWQLQRVPHKCTPKDCYFGQQSFNERELCIWLADPLMAIAMFYKPLVDPPIEMEPKIESFAMYKAVPKSGSWIVVQIIYTPETTGWIEHYDTSTWTNQRHPLLLGWFSMWSCFENIFAWESMKKMTDYAKEPTMRRAAIWLGEDSQQMSSYFVHHKCCRHYNEHLRLLVPCSVIQRCQQDKWNESCQWHHPDPREPWQFGIKKPSYAWWDLLNTCAPFYPDHKNTYCCKMANRAASQDWKSNGDRHNAPVIRMENMYKSVPERPDTSKNDQHPDMFTENTQTIVFCHHDIGWCWLGHIQYSCCHYACVIICIEMLKPLCNWMHRIESQCTIVNQSFASRTTCQDQSFYLIYEMFVIAYEIWDKNAPQMFPYYYYWRWVDRTDCHVQDETDGGCWTKEDCAGCSCSRELSYIEERGYYWVFPYSRTVQAVMMEHVPGWCYMSGVFLKLHPLFHYDIHQTYIMRAVPTEWTQDMPKDHDWKLYQWDLQRKWSYQVGDELDVGPGCLRPAVAAAYFQTTCILCATAYEDYSEKNKEYRHYTACMSGGLFNHGQMESYKFDWMDWHRQGGDEKPDGGDIEHCYYCSNEASYTPATYGYMCNENGSALGRFMVMFVRMFVRASCSNRDLGDRWQWIFTDYWHCDNERAGCEKDMNQNFGGHWPLDYCFEFGWPCCEQRDCMHLCMCSYMVRVSQDFKSIWDERLGMIRDWRFFVSRNLQCMAWTTYKMEFCLQTYSQFILPARLQEVCDGLWLSDCHNYNWGRIMKWGQLKKVMVPRWEMMIAMRWDTRERWMYYVSHSDAEVAEPSVELNLGGMHAIKSTTWMWVKSTKTCRECMNNIYSVFTTCKKKIAMTFTHKPIKHDKPHTFRCMSNQTEPVCHCHDFHCIWKGFGLMHSGLESQFVDIHCKRPWIIHDKRQHSLGIAALKTCYCGVRKNGRGAQTNGRETGDGAEGIQLQIHLHAVRLKVVAHFSNAVLYDGSKRYMENQKHHMTLKSPSNMPYTNGCENGWYRPCHVQAYVANADFASPLEPMVYTKWWDEGSSWLKNRRGCQATQKSQKPKSMRELWLVTHLVGAHEGNCDHRLLFQYPRWIFHSNKYPDGWKHAHRAWDPDSYGDFWVKHGHHDLLLACKEEVLCTLYNFCKQELENLGWVCCVLMNVCWISHFGNGNYPYWHHRHWLHMENDCDIAEELKRVQGYLYRHKWWVGWELCDFFQANQDNHESRLQRLHQHRLTHNHCRFPKVRQQDIAVFWEVVSICGANRLVHIFACMIIKDAHMVERVHNLCDPTWWPQHLAMNSMGWYMQKLVEFMTPCGNLWSRKFCQIQMHGWVYFPRHWWYSIPDEAMVGVRNNESTASVIRVYFDEDINPTNSNPNRKPENHCKELLLNMMGCVRCAFNRKLTPHKEQSVSYMFIHQCYPLCAYNMRCMTTRCESHMTHEFQPGWRRQMETLVICRDAIVQFVMTVMIKRRMTDYSMSITYQEWTFKCLRHNFALRCKSGCAFVLEDQDVQLHGLPMKWYAMFNDMYMFKCIRTYIDEYASPDYNWNQPRWLITYATNTGSHAKTRQSNENCRRRIFYDYNRGMWLVLCTRERIHWWSLPYRKVHVLIPGHISASEHYQNLNNPPMYKAGMAEKSPGWQVTICRIEDVRPFDDDHLYGDEQEVHNSGCAQDSVHVKKMTPVLIDCGDRPIEWTCFQADYYNKPTHRFWRPDVKHPLNKYCHGGCDPDSNRSYCKWEDTCEDTTIKYSRTHSDFNVGSMATKYIDREHNKGSEKWFEGLGQRCNQPGEMKVNFTLEIMTFKPRMRSFDHEPESAMHNQYEFLNDGTTCMGFEKKGIHFFYKNICRNLQQYQCHCPLCYRMLPGQCECQNIIVSPRSVLQHLNCKQNENMKTSSACHRKIMHYKMKIYGVSIERRDQTFAVRMPNFECECWDMWEGSSWLKKWIHRCNDCNLDAPLERPAHFKHDSFWCTFGIWLQYCCCYSGFFCSMAHMMNYCLWCWEPLFPDKWDEYFSLTYDGVEGWCHDLIEQIDGEMLYGLTIPEIMPEGYPRVADDHFPYPEPSDDDSHNDKSEKKRSYLIPSFWQACHCCHFKTQQKCWACNSRIYLEADWLKHAILPIGRRLKRVVTKMHRQVERPVSLMRTFFNPVGCPDTSDNTGLPDLMNWVGQGITVGQCWHETIYGLSAVCWSPMLNTQTAEWTGGKYKTMDGGIARKEGYLGVKKLTQFGDTAWCTWEGHCDTWMRDYMMHWWYATEDMYQKLIGIG
Output:
1387
--PTGQSYVTTARTTAECRVLHVMPFNYHMAS--IMD-SYVFLNFGPALCMHEWYLCTM--RCGWSKVGLGYMTCFCKNYHMSVKDAAYDGDKEMD--G-M--TKWCVMPNC-M--WENEAQ--DQMQAW-DSKG--WQDFCDD-IKAGMQFIWDSEPH-GNFSEIMSMPFDIDVTIFHMQEPEIVQWTMNPQHS-PHR-PKSCTMASW-RTQHHTAWNHCP--V-SASAFQPQVDVCDNV--RFYGETAMNIVGGQAEAEKMKI--HP-S--Y-QGH--IHL-C-IGNEDTDGQQ-LWCQNHMQ-HEP-FRYNDSDGDVTYQKHPACAAIPN-I--HSWFQPW-GIDYQSNRQFGNQMDECYDL-WALRVWDEPSVTWYYRH-DLHDHSESWQRCETNVMW-YKGAKDMRGDLWSPRVMIMVPFLTVWRCGVTCGWLWPKSFSKAMMRAQKIHEFPQQRIK-TN-G-AKPDNE-REWQAHHAFNTECKFVGPKPILLSKPWRQVDY-DYCSFSDDMHFRKCVLTDEFFNVVSTKMVSQCW-FWADTLNPEV-SNQF-MTQE-YIV-KMTS-VCEVLNGV-GGLPF-VTADSCSSPVIEWGLWTNDQWEGFFKLYWV-MLDNDKNPVKWPHNR-GI--VHGMWPI-WWIEQNPIKVGQACM-W-YPLIDNYWEDNRDV-LKPKEDMM-AI-D-IS-GQ-V-K----GWATD--IR-PSSWS--L-YIIPDMVWRGSLCDLARVEYEHK-PWHNCTTYHMRCVIFY-YFAPIGNHNDATIPGWAEWCYWPKMWEGYVMVNCFTEQQHQAEAAVAWGW--YGCTPNVPP-VSPIMQSFKMFICPNQFQDLKLMQDPCWVLNKFS-VNERQLDH-CPMDASDHWSPSHNRWNLTFQAWPGRQEFAWPVLFFFSDVWWDAHDYI-YVNVM----GYTVY-HAWSASWVVTQ-LGNI-HGECWNCMVPPEIVMSNTNQKYEHYMIASREMVTPHRRRYAVCTFRNLAWKSFDQQFFC-RENFIGIFPADCG--IIKCEVFRDLQEFFDRE-NSKCDQNSQKNMHK-F-KYCFQFQPQDPVKQRLN-PVHPWCRSEEDGLRTQEDIVRPAQYNEWPMHQNDAKLVQGCCIYKYKRKWIPRKYLKTYGTNMPEHFYYQRQVLSRYGSMRRMWIKNEQYVDHRDRYVMLEPGCETFFYSFVMEWDEINDNNSRSKEVAPPKEFDYMYNNTCHDTWRFSEQVKNDNQTQFFVK-QTFVRLHLQLDQILPEAIFMSF-TLDW-PQYGY-QIAKGNTFKCMQFTNYKGSTFGWLDVGPGNRPRHWWKTVFWQKWWISMWLDVQDLSKDAFDNMWEKQAMQKPKFHDTRFL-QAESKDTRSKEADSKVDPWWRQHSQERFYPGGSECCWM--D-AL-HPLK-LRNFVEFVVVTKLPNCLWHAFFQYFPEMWLCFMDHA-SPKQKVWRMNCYRADFCY-FMCELGYETDDR-SAETAIVM-YEPMQMGW-NHWW--WLTWLH-MA-CT-LIIDHIMMNLQVA-LYGCIQPLNF-WMATFHLVWQAK-VFFFFAFERFHTHVIMCQ-KAKENESHRLQPEE-RMSKWHYTCCGTMFHVNWHAEQGKSGMYTQALRLTHFTVWD-QGSHLM-CTGIYM-DMPQNHC-SW-ARHRTDPCALV-VH--WG-P-K-VPKPNDTFGCHPNNSEIEPFPPRDDAQANHIEDCHEYRFCGMTHN-AYTDH-P-GFLRNCTENVT-EKIMEGPLYPWDNDRGSHAQLVMWCRVASEA-VQWVSSGYKGINSAYRYVNLWGKHICRAWQDWDWVGVHIQCNHIWGQETDPDEQWLCIHENGINFFDSNLADYTAEQEDFGDWYC-QKSH-L-HSKVDVKQYSQIA-TI-IWTWQHTNCGCSTCWV---PLHRIFSLDNDVPP--C-IQV-Y-MG---DK--RQ-MWR-NKDNHNKSQMTYM-KL--ECMFPDKD--FRQQSTGERPVTELMCKNIWTVH-YCYIAMFYDVEPKCDIEDCYMGVAYMMSFAEGFMHMYKALVCPKSGSMYDWTVVQIIYTWQYF-WHRPETTESTWTNQRHPLQLGW-NT-SLMENIFAIESMKKMTCYAKEPTMRRAAIWL-V--Q-MSSYMVHHKCPRHYNEHLRLLVPCS--W-CQQDKWNESCQWHHPDP-----Y-IMKPSYAWWDLLNTCDPVW--RRNTYCCKMANRAAHQDWSSNGDRHNYPVIRMENTSDTHNMNMYESVPERPDTFCGLNSSLQGHEWQMYSQAHHPDMFTENMQDYYYGTIVFCHAGICWCWLMHIQYSCCHYAC---CIP-LKPLCAF---IESQCQIVNQSFASRTTCQDQSFPHYLIYEDFVIAYEIWDKTAPQMFPFYYYWRWVDRTDCHVQDETDGS-WTKEDCAGCSCSRELSYM---GFNWVFPYSRTVQ-LMMEHVPGWCYMSGVFLKLHPFVGMIQKGKTHHIWHGDRWHGKGYNVSTDYYDCVYYEPCLRNKYMSDVIGYTGWLGWVQTLTDHVKSSPSKGRIPVWNQFTQVKKY-QV-MEHLFYKGAHQDHICVTCEGWVMPPNQCF-WFQDQDSQCSLQS-DQMERLEAVCYPTMWYRGAWKRHNHTRLWLTTYDPGYCRNRDWAWVTCCNCIAALMQQESNRKYQWCWC-YWSTNHPMHNSDIYVVWDDDGERP-DGCSNEIRQAKRPCTCDISDARPLKI--YMIYCFPCEGKYI-DIWMGKMRAFDF-L--NFMDGKFTI-R-DGAIFP-PQMV-PCNVLVFELVYKSVW-AETPTIR-GWYQCW-PAQKVYANGWISMLVIMDF-AQKKFVGH-DLSTATCMNHRVDCFKNNVRRH-VEPPL-MLIMNKIWCEHDFMTAM-DVIIYASPDMYMP-G-KPYLGTFQYPYLYK-HGS-SDYV-EL-EASKI-NGY-MPYWCHESE--DSTC-HA-L-HDPA-HC-DLRWMFMC-CPRTTKPYVWMCNTWIRYDKQDLAP-VNSFIPAHQ-DVHPYCTCGRAV-WT-QKRFWKAWWFLITCPDPHDSYRSFDEVGEPIETACRDDCVINIYHSQYNMSSWAKAVAFIKWMTPLQPY-EPCF-CQKMEFKQWWEKLCVAWSQPVFNFSIPKHVFIVE-RYI-EDEHWEVIYWMKKFVIP-K-LHMGPWQSCTIGYREYACIGIDAHDPY-RCGDKNMANMRFPWWDIISFLLFQPLPMECSYHGQGTFCLKWIAA-RNGTYQFRIVEVYKFSSAEVNRNTQYFSHHHMMLMPHNFYHMGTFDYCWLKYPFPT-MDWNVSTTSPNILGLENHKDLCIMVMNCE-REMTPERIMQYKVLLSLWREN--V-VMPCCLIALVNLLGQNKENT--PLDCPK-MPM-VMDYHPRKF--WLSP-GFIGKYHIAQ-RTRQWRLIFCPAQTKMDVCASYPFPGPRTDH-TRSMW-L-MGHSTAPEFMFMTNKNMQIGCPPV--GAQGHVEPPTRQRKGKHQYVCEPWKMWKHKPQWRAWAINWKKVIT-CWS-VSFDF-P-WD-SIFTVKDCELRGGSFAM--MRKAYQPP-RMS-QLP-WVVKC---NFS-P-KQGYEQYITVDGKTQKTRVIDPMPDPHHATYGIMFSHQYTVNWIHNCERLTMAKINRVYFTIIADRWG-HYCVNI-NH-QTLQMDDMMCYDDSVSGQGYLCMCCTIIPWGQCVNAYIHRCWHCTDVYIHRLLPEQETVFQFCDNHMMAQMHLMPTLNEKGSFSWQRVMSGGVFWI--VNGCNMYAFS---HH-WLAPH--HDRTNQGVYM-LS-QPQMCWA-LNDDHTYHKNNINAWEPPIGTHTGWLRAEEMTG-SPD--RLLLIWGFMCR-A-M-YS-CHLDACARNLQFNFLMKVGHHNQHQYWAWCEQCLDCKSWDTNASSKLEFNYETLTDLTGHPPQRP-DVFFCDDCV--AYCEFLKHTSPLDRWYEPRPRRLGQWVKSLGSG-NPPACF-EWCY--IRYDCWYC-NVVPIEHTEDPMHWHENWDNNCIGQQHWINVMCQMMTPN-NAGIHPVRPCIHPDDN--VRMP-YECHNMEP-ERVQFVDQVTGAPYRANATLPCDHDGFEAFMAPDLTETY-VQDQKYCKGVP-FQMSKPNQASIPLWSYI-LYSCEM-ACIEI-YI-MKGWM--LKQSFHGSPHKTVTCIGTHCMIRHQACCNNDKFAVANRAHEFRWY-WARLNGQKMI-EFFESFRDM-IKISP-CMRWRDDAPGSGLHI-WAAHIFMEV-EKLVWTLAIMNCAYAAYPV--MEP--HPLGWVDTGYVKSHFQLAYSICFCGQIINRIMILQARYQ--YVAPATCRLHSCGDDAA-LTPVNWSFNMGHGMPNIN-Y--I--LN-WNRKRWGNFR-H-QMHIPPGQQCRCWRALKDDNVMHEDTT
QVPFPTVDVIVCCTGIKCEPMNV-GYDQQMKDCFICTREYDIRRLHTIVCGSEW-ACRLWIEADWEDCEKSFRD-FDAPINI-VQYAVWRANVETQCPGYLNRTQWIMIGYWFIGTW-NAVLIVPKSPAQIETDGIVYKIPCNRYFEHGPYF-WRS-PWAGPYPTV-DR-HD-SVCHGHLKYGSLPS-CQNWEFARPHDLGDAC-M--WEKPQLQLNWNPRPRAIISTGTFSPEQTFWDGMPWKYFWKCPSSVQANKRLYKVLTVVCRQENHGYKETHRKFHIKCLVGQLNQPKPWCVYCVVYRSDYPPPQRWT-FWG--TPQ-YIMCFVKPHKLSDESAIGNWWNIG-PCDRLVASAWEHCKRLGWYPHGWAK-SM---FPHMNIMGCSRKF-R-KASIEWPIMSHVGYCAH-WHP-FSRRVQFES--NINQSLRWVVMSSFKDTDDHVALVCLTPAGEIPVTNVGQALAEQSYRIWSAQE--H-RAPFTGWMNLFCS-IGMTM-YIEKCSREPIIKDHDC-FNDT-ADPSDTKVTSWMRKYWIEE-DPTWRSNMIHMMGSIFSCNRMSNFMC-YPESVRADWPIELWPGRLAIGFMNMGVASLEHYFPFIG-FWVDYAPSPSEEHQWRHDAYAYDEVYAMVPMDCKLE-GQTYT-Q-CMMWKIDLV-LLWSGNSEICIEQHESFSRSIYGHVSKAQAVMKYARRGPAHEQFVTGKSQHSQDCTHISPKIMLHSSIRIVAKHDMLRKEP-H--SDYHMLKTEFQDKYERMTTMMWG-FPDW-ELPHTEQRHKLAGEVRQATASHYQQYYKPDHGTHEYVC-PQ-PCLIAPWASGTPEFEMAYQL-TCNGMFAKCYNRRTGQQVLQISVSHSCMRTKMANWYPSMDMF-LE-MS-NGNADLASNRIGHFS---Y-GHEFVEHPNVMWRPDGGRCHGHEAICNGLAYQYMWPVYHNRC-NAKW-VEVV-HHQDSNFLP-MIHGAGSHL-H-HQLAIC-YL-LVCPVTGAR--CVGENLINFLVIICNWELIVFLIIEMVAEGLRRPMRNKC-QATSFNLETYFRKKRMQCTLNRPYMTRTRRP-HLW-GPELRATNKQRDL--PV--TAVPC--NQA---Q--C--K-K-FW--GG-VQDQ-SN--DDVNW-RD--TKW-DF--SWGFSPAKV-HWHQCV-YDQGFHNLEYNPCLHW--I--WYMYTWMIA----FERTVGKACHN-W---EQIPIDSLNNFQVHTDIWIELHC-MN-MSPYA-FVNYSTCNAVAAKWYLELAIAHQSEPQKWF-YFVS-F-ILD-SRFS-P-H--NMVFYAT--SDGYRD-K-LKPLEFDIMM-KRGTWTPE-HWQSFTPHRKISPVHSTGIHEAVDIYQYFHEPFAMEP-ACKCMVMIYTVAIVH-FKCIANH-EVSGGTEI-N-LVRCFHIWHCEEWK-YMCHSWFEYNAIFR--C-EAMLCWKLFC--GQSPIDMLTVEVKILWAVTPQMIACADAYLRPFMDWIGAFSLCQQTFCDLFAWPPQVQRFYWTVKEVEEQWY-S-H--WVGKSVNINSSSDHNNRWVLWPYFKLLFNVANH-QPDHCREAVW-YNVASDRPHV-FCMMAGGVPQKTMINQFRHSIIFSVQNPHFYGMQPTWCSERVALVCPKWHAPNAIPPPKFMHARAFWAVPTKCVYQEHDHY-WH-NH-KTSHF-PGTSPDIYEVR-A-QFR-SAETHNHPYNDYKPLMFVK--T-HITIAKFIGGKMHMM-GTQG-YA-M-RPCDWESKMMVTFVKVVPPALTCIF-FIPA-QPHTMTGWALYDRY-M-V-C-RMC-HEVEPC-KWFVIDVDH-NQNDSIMRSHPSERGTTG--ICDQKHHNLQHCNWELDGPPEISMTYPILN-SELDLGWYHLWCGDGPMHPKFGRDRGVTEWKVTIKTPFNLAPTIENIDAQSITRWSQYMINKADMWQLQRVPHKCT-P-KDCYFGQQSFNER---EL-C--IWLADPLMAIAMFY--KP-L-V-D--PPIE-MEPKIESFA-MYKA-V-PKSGS---WIVVQIIYTPETTGWIEHYDT-STWTNQRHPLLLGWFSMWSCFENIFAWESMKKMTDYAKEPTMRRAAIWLGEDSQQMSSYFVHHKCCRHYNEHLRLLVPCSVIQRCQQDKWNESCQWHHPDPREPWQFGIKKPSYAWWDLLNTCAPFYPDHKNTYCCKMANRAASQDWKSNGDRHNAPVIRME--------NMYKSVPERPDT------S-K-ND-----Q--HPDMFTENTQ-----TIVFCHHDIGWCWLGHIQYSCCHYACVIICIEMLKPLCNWMHRIESQCTIVNQSFASRTTCQDQSF--YLIYEMFVIAYEIWDKNAPQMFPYYYYWRWVDRTDCHVQDETDGGCWTKEDCAGCSCSRELSYIEERGYYWVFPYSRTVQAVMMEHVPGWCYMSGVFLKLHPLF-HYDIHQT-YIMRAVPTEWT-QDMPKD-HDWKLYQWDLQRKW-SYQVGDELDVG-PGCLRPAVAAAYFQTTC-ILCA-TAYEDYSEKNKEYRHYTACMSGGL-FN-HG-QMESYK-FDW-MDWHRQGGDEKPDGGD-IEH-CYYCS-NEASYTPATY-G-YMCN-ENGSALGRFMVMFVRMFVRASCSNRDLGDRWQWIFTDYWHCDNERAGCEKDMNQNFGGHWPLDYCF-EF--G-WPC-CEQRDCMHLCMCSYMVRVSQ-DFKSIWDERLGMIRDWRFFVSRNLQCMAWTTYKMEFCLQTYSQFILPAR-LQ-E-VCDGLWLSDCHNYNWGRIMKWGQLKKVMVPRW-EMMIAMRWDTRERWMYYVSHSDAEVAEPSVE-L-NLGGMHAIKSTTWMWVKSTKTC-RECMNNIYSVFTTCKKKIAMTFTHKP-I-KHDKPHTFRCMSNQTEPVCHCHDFHCIWKGFGLMHSGLESQFVDIHCKRPWIIHDKRQHSLGIAALKTCYC-GVRKNGRG-AQTNGR-ETGDGAEGIQLQIHLHAVRLKVVAHFSNAVLYDGSKRYMENQKHHMTLKSP--SNMPYTN-G--CENGWYRPCHVQAYVANADFASPLEPMVYTKWWDEGSSWLKNRRGCQATQKSQKPKSMRELW-L-VTHL-VGAHEGNCDHRLLFQYPRW--IFHSNKYPDGWKHAHRA-WDP-D-SYGDF-WVKHGHHDLLLACKEEVLCTL-Y---NFCKQEL-ENLGWVCCVL-MNV-C--WISHFGNGNYPYWHHRHWLHMENDCD-IAEELKRVQGYLYRHKWW-VG-WELC--DF-FQANQD-N-H-ES-RLQRLHQHR-L-TH-NHCRFPKVRQQDIAVFWEVVSICGANRLVHIFACMIIKDAHMV-ERVHNLCDPTWWPQHLAMNSMGWYMQKLVEFMTPCGNLWSRKFCQIQMHGW-VYF-PRHWWYSI-PDEAMVGVRNNESTASVIRVYFDEDINPT-NSNPNRKPENHCKELLLNMMGCVRCAFNRKLTPHKEQSVSY-MFIHQ-CYPLCAYNMRCMTTRCESHMTHEFQPGWRRQMETLVICRDAIVQFVMTVMIKRRMTDYSMSITYQEWTFKCLRHNFALRCKSGC-AFVLEDQDVQ-LHGL-PM-----KWYA-MFNDMY-M-F--KCIRTYIDEYASPDYNWNQPRWLITYATNTGSHAKTRQSNE-NC-RRRIF-YDY-NRGMWLV---LCTRERIH-WWSLPYRKVHVLIPGHISASEHYQNLNNPPMY-KAGMAEK-SPGWQ-VTICRIEDVRPFDDDHLYGDEQEVHNSGCAQDSVHVKKMTPVLIDCGDRP-IEWTCFQADY-YNKPTHRFWRPDV-KHP--LNKYCHGGCDPDSNRSYCKWEDTCEDTTIKYSRTHSDFNVGSMATKYIDR--EHNKGSE-KWFEG-LG-QR--CNQPGEMKVNF-TLEIMTFKPRMRSFD-HEPESAMHNQY-EFLNDGTTC-MGFE-K-KGIHFFYKNICRNLQQYQCHCPLCYRMLPGQC-ECQNII-VS-PRSVLQ-HLNCKQN-ENMK--TSSACHRKIMHYKMKIYGV-SIERRDQTFAVRMPNFECECWDMWEGSSWLKKWIHRCNDCNLDAPLERPA-H-FKHDSFWCTFGIWLQ-YCCCYSGFFCSMAHMMNYCLWCWEPLFPDKWDEYFSLTYDGVEGWCHDLIEQIDGEMLYGLT-I-PEIMPEGYPRVADDHFPYPEPSDDDSHNDKSEKKRSYLIPSFWQACHCCHFKTQQKC--WACNS-RIYLEADWLKHAILPIGRRLKRVVTKMH-RQVERPVSLMRTFFNPVGCPDT---SDNTGLPDLMNWVGQ--G-ITVGQCWHETIYGLSAVCWSPMLNTQTAEWTGGKYK-TMDGGIARKEGYLGVKKLTQFGDTAWCTWEGHCDTWM-RDYMMHWWYATEDMYQKLIGIG
        """.trimIndent()

        runTest(sample, true)
    }

    fun runTest(sample: String, useBLOSUM62: Boolean = false) {
        val reader = sample.reader()
        val lines = reader.readLines()
        val parms = lines[1].split(" ").map { it.toInt() }
        val match = parms[0]
        val mismatch = parms[1]
        val gap = parms[2]

        val linear = AlignmentLinearSpace(match, mismatch, gap, useBLOSUM62)

        val sRow = lines[2]
        val tCol = lines[3]
        val result = linear.alignmentLinearSpace(sRow, tCol)

        val sRowAlignedExpected = lines[6]
        val tColAlignedExpected = lines[7]
        val sRowResult = result.second
        val tColResult = result.third

        val scoreExpected = lines[5].toInt()
        val scoreResult = result.first

//       println("RowE: $sRowAlignedExpected")
//       println("RowR: $sRowResult")
//       println("ColE: $tColAlignedExpected")
//       println("ColR: $tColResult")
//       println("ScoreE: $scoreExpected  ScoreR: $scoreResult")

        assertEquals(sRowAlignedExpected, sRowResult)
        assertEquals(tColAlignedExpected, tColResult)
        assertEquals(scoreExpected, scoreResult)

    }

}