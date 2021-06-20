import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

internal class S01c08p04Test {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName( "test with match kMer pattern with hamming distance")
    fun testkMerPatternWithHammingDistance() {

        val genomeTest = "ACTTCATACAACTGACAGCGGACCCCAAGAAGGGTGTTCACTCCTGTTCACTATACCCATAGGGCGAACTTAAAGAGGACGTGATTGAGAACATCTCGACGTGCGATGACCTACTAACTGTCAGGAGCATCAATCACGTTGAAACCAAGGCCCTCGCTAATTATGCATTAGCATTAACTCTCACGAACACACGCGGTTGGCTCTCCCGGGAGTCCGCCAGAGGATGACCAGCTGGGACGCACGTGGTCAGGGTGAAGACCGCTGTTACGACATCAGCACTCATGTGTGGTGGCAAGCTACTGACTTTGAGCACGCCAAGACAGTACTGTCGAGGGTACACGAGCAATAGATCACAGCCTAATCGGGGTATTGGGAAATATTTTTAATAGTTGGGCAGGAGGTCTGGGGGGTCACATTATGCGAGAGCGTGCCGTATGAATATATAGGAAAATAGTTGCTAGGAGGCATTGCCAGGTCTAACCCGCTACCAACTCAGGTACTGCAACATCAGGCGGGAAGGCGAGTCGGGTTAATTTGCGGCATACGAGTACAATCTTATGATTCGCAACCTAATGCTTGTGAGCATGGCTTTATTCCGAAGGCGGCCGGAGGTCGAGCCGCCACGGTAAGCGGACCTACAACAGATTACCTTACCAGCTTGATATTGGCGACATAGACCACTGAGGGATAGCAGCTTTGTGCCTGCGATGTACTCACTGTGCGCCAGGAAACTAAATCTTATTACCCCCTTGTCTCTGAACACGAATGACGAGATTGGGATAGGACACAAGTAGTGTGGCGTGTAGGTCACGCCATATGTGCACACACTTGATCCAAGCCCAAAACTCCGAATGCTGAATAATGACACGTCGATGTTTCCCCTACCGAAATTACAGGGGGTGCCCTTCTCAATAGGGCCGACTGGACGCTGTCCCGCAGATTGTTTTAGTCCATTAACGTGGTGATGCTGCTACTAAACCAAACATTAACTACGGCGGCGGGAAATGCGCCTTAATTTAATCGAGGGCCGGCTGGGTTATCGAGTTTTACGGCTAGCTACGCGTGTGATCTCCCCCCGGTCCTGATTACCAGAGTCGCGTTCAATCCCAACCAGCTAAAGAGCGGAACACAAGTCGAATTGAGTGATGCAATGGGCTCGCAGCGTCCCATGGGGGTAGGTCCTCCCCCCGGTAAAACCCGCTCTTAATGTCCTGTCGCAGAATCCCCTAACTAAGGGACACAGCGCCGCTAACTTGGTCCTAACACAAGCAGATATCAGCTTAGCCTGCCTAATTGTGTTCAAGCCCTCAATCTGCACATGGGCAAAGGAATACGACCCTCAAATCTCTTTGGCTCACTATAGTGACTGCTCTTAAACCCCTGGCATCTCTATTTCAATGGCGAATCGAAAAGTCGCGCGCTAATCCTGAGAGACAGTTACAAATCTCCATCACACCGGCCGTCGCTCGCTCGATTTCTGCCACCGTCGTGTCATCTCGCCTTACCAGCACCCAAAGGGAAGACCGCAAGTGATATTAGGACAACGTTCATTCACATGCGGACTATATGCTGCTGGCCATCATTGAGCGGCCGGTATTAGCCCAAGCGGTCGCAGTCGATGTTGAACCACAGAAGGCATGTGTCTAATTTCCGGGGCGTATCGGGTTATTCTGCTCGTTTAGGAGCCGTTCGCGCCAGGACCGTAAGGGAACTTGGAATTTACGTGCGCCTCTTAGTCCGGTCCAACCGCGCGTGATATAGGTGCAAGTAAGTACAACCGCAATTGATTCACTCTTACCTCCGGACGGAAGCGACGGCTCACTTTCGGGATCAGTTTTGTCTCACTACGACTGATCGAAGGGTCTTAGGTCGGTATCGTTAATATCACGCAGACGTCAGTGAACCAGTACCACGAGGTAGCAAGGTAAATGCCATAGCTGGCACATCGGACCAGCTTTGTCCGAAACCGTTCTAGAAACGTGAAGCTCCCATGTCCGCTCAAGCTTATAGTTTTTTTAATGACATAAAGTGCCCGACGTCCACTCACGAATTGCGCAGACTTGGCCCACTCGGGAGACCTATAAATGAAACCAGATAGTCTCTAGTATCGGTTCACATGAGTAGGTACTGGAGGATTGATTTTATATTATAGTAGGGTCCTTTGATCGTGGTATAGAGCCGAGCATTTTCATCTAAAAAGCTATCTAAGCGGATCTGCCCATACGCGCTACTGGTGGCCTACGGAGATCAACCCCTTTTGGACGCAGCGTGAGCCATTAAACTAGCGATATGTGAACATCTCCCTTAGGGAGGGCGCCACGTTGTAGTTGGGGTCGGCCATCGCGATTATGAGGGCGAAGTGTGACTTGAGTTCGTCACGCGCAAAACCACAACCTCCGTTAGTCTCATGTTTCGACTACGGCCAAACGTTAGGTACAGCGATCTTAACCCTCTCAAAGTTGAAACACGCGCCTAGGCTGGTGGCTAAAACTAGCTGGATTCAGCTTGACCGCAGGACGGGCCGGAAGAGACGTTGCGTGATTACATTGGAACTTAGGTCAAATTTGACGCCTATCCCCAATCAAACCATATAATGTACGGTGGTTATAATGCTGGCCAATGAGCAAGCCAGCTAGACATTGTGTTGATGTGCCAATTGTAAGAATTCATCTATCGGCAACGGTCAATACCTTTCTTCATGGACTTGTTATCATGTAGAACCAGCGAAGGCACCTTACAAATTCGCCCGATGACTAGCTACTGCGTTCCCAAAACGACAGCAATTTCTATAGGCGGTACCCACGTCGCTTAAGAAGGCTGCACTCCCCCCCCCAAGCATTTACCACCGAGAACTAAAATCGCCTTATTCAGTGCACCGGCCGCACGAACCAATACAGTACCCTGTGTTTCGTGACTGAGACAGTGACACCTCTCGGGTGTAATTCGAGCTCAAGGTAGGTGTTTGTGGAGCTTCGACCGGTTATGATGCGCGAGTTGGGGCTCACTCACGGTTGATAAGCGCACAGGTACTGCACCTCGCCATCATGTCGTACCGTGGTCATTCCTAGGCAGTCGGACACTGAGGTTTTGCACCATCGCTCACTGAAATTCTACACTCGTACTCACTCAGCGCTGTTCATGTCCGGCCCTTCTGCCCAATGTTATCAGGCCACCTGAAAACTGGCTCGACAAGGCATGATATTAGGGATAACGGCTGCGACAACGCATCGGAAGACACCCTTCCTATTTAAGTAAAGGACGGTCTCTGGCGTCATTGTAGTGGAGATAATCAACCAAGGACGTTCTATGTGTAGCACGCAATTGGGCCATACCAACCGAATATTAGGACAATGCTATGCTTTAGTTACCTAGGTACGCATCTGCCTCTGCTGGTTCGCCACCCAGCCATTTAGTTTGAGGCGTTCGGTGTTACACTATCACTCTACGACCGTCACTTGAGCTATTGGGCGTGTCATACTGAATCTTTATACCGACGGCTGTAAGTGTATGGATACATGATCCCGGGTTACAATTCGAACCTTCCATCGGCTTAGCGTTGATATTTGGCAGTATCTTGGATTTGACCAGGCGTCTCCCGTTTAGGGAGGTCGTATTTAAATTCCATTTGCAGTAAAGCTTATTGTAACTTTTGTACCGGGGTAAATAGAGGCGGGTGTCCGCGAAGACTTCCCAGTAAAGCTCCAATCCAACTATCCCGAAGGCGAGTTTGTCGGTACCTATTCGAGGCCGCGGTTTCCAACACGCAGTGACAACCTGGAATTTTCATATCGAACCGAGATGCTGAACAAGGTGCGTACTGGTGTACAGAACCTGCTCTTGCAGCACCCCCTCAGGACGCCGGTAAATCGTTATGGCTTCCTGCACACCCTAGGAGGTCAATCTTAAAGCCTTGCGAAGGTCATAGGAGGAGTGAGTTCGCCATATGGCGCCCCAAGGTAAGCGCCGAAGCCTGGCGTATAGTGACGATTGGCACAGAAATCCCTTGCAGTAGTGCCCGAAAGGTAACTGCGGTACGTGTTTAGACCATACGAAACGGATTTGTGGGGAGTCGCAATAATAGCCGGGAGCTTTCCATAAGGTCCTATACACGGCGAAGACGCAATTGGCTGATAGTCAAGGTTTAGTTCCTAATCAATTCATATGGCCGCTTGCGTATTACAAACCAGGTGCCCCAAGGAGTTCGCGGGCCGCACCCTCGGCCGTCATTCGGCATGTTGGGAAGGTCGTGGGAGGGTCGATCGGTCCTGCCGTAGCGTAGAATAGTACGAGAGTAATAGTGAGTCAGCACAAGGTTTCGCCGACTTGGACCCATACGGTAATGATTACCCCAGTATCGCAGACCAGGTCTGAATCCGAGGTTTCCAGTCAGCAACATTTAGGATGGGAAAGTGGAGTGTAACCATGTGGAACTGAGACTCTAGAAACATAGGCATGGTCAACGAAATCGGTATCGGGTTCACACCGCTTTACAAACGTGACCGAGGTATGCAGAGCCTGCTCTCGAACGGAAAGTACAAGTGTAAAGGTAGACTCACTGATGAGCTTCATTGGACGATCTCGTAATGTCAACGGTGGGTCAGGTGAGCTAGCAAATGCCGCAGGGGGTTCGCTGTCCGATTAACAATTTCCAATTGGTGCCAGATGTGTCCGCCTCAGTGTACCTCGAAAGCCCATTCTATTGTGAGGTAAGTTCCTATGTGTCGCTGCGGACTGAAGGAACCAAAAATAACCGGGGTTTCCCTGTACATTTCCTAACAGCGAGGTATGGTAAATCAGTATGTCGTGCATATGTGTCTGATTGGGACGTTATATTTCTGTACACTAGTTAATATCTCCACTTTCCCAGTCAACTATTCTCGCGTCACACTAACAGTGGAAGACCCCGATACAGGGCGGCGACGCGTCTTTAACACCTGTGATCGGGATTCGAAACTGGGCCAACACATAGCGCATAATGGTACAGTGTATGTTCATAGGTGCGCGCGTCGGTGTGCCGGAAGAGGATGCCTGGAGCCGCGACTAGGTAGAGCAGGTGCCGTTACAGCACACCCTGCTAAACTCGCTGTATTGCTCTTGAATAAAGCTAACGATACCTCATATTTCGAGTGGAGTTATGTATCCCTCCAAGTGATGTTCGCTCACGTACGGAACCCGGCCAGGGGCATATAGTCCTACCCCGACTCCATAATATTGACCGCAGTGCTGCTCTACTCGTGGATGCTTTAAACATGGGGTTATACCTTTCGCGGCTTGCTTTTTCCGAACTTGCACTCAACTGAATTACTATGAAAGGAACAGCTGACTCAATCATTAGAAGTGTGCTGTTATGGGTACGGTTACAGTCAACCTCATTTCATCTGTTCTATATACTGTGCGGGGGCCTGGCACAGCGGTCAAACAGCCCCTTATTTGCCGGGTACTGGTGTGTCGGGCGACTAAAGAAAAGCACCGATGATGGACATCTGTGTTTTTTGACTGCGGACTAGTTATGAGTTCAAGATTAATATGACCCGCATTATCAGCCGTGGCTTAGCGTCCTGGTTAGTGAAACACATTGCTAGCCATCCTCCGATTTTCACTCCGAATGTTTGAGGAGCGCTCTCGACTTAGGTCATGGATCCAAAACAGTCGATTGAAGCCTCGCACATTCAAGCATAACGATGGCATGTATTGATGATGCACTCTGACTATTCAACGCGCCGCTGATACCATCGTTAATTCAGTTAATTTAGATAGAATGTGTCGACGTCGACACCTCTATTTGCATGGTGGTGGGGTAGGTTAAAGATTTGCCAATGCCAGAAGCATCACCCACCCTGAAGCCTACTCGCGCACAGTTGTAATGGTATGCCCGATGACCTCCTATTCGTAATCTGAATCGAATCAAGTTCCAAGTTCGTGCTGGATAGTAGGAGGTCTGCAACGCTCCGCTCGGTTTTCAGGCGCTTTTCCGAATCCCTGAACTCCCCGACAGTCGATGCGTAATTATCGTCCTTTCTCATCTCAAGAGTATTGCTAATCCTGAAATTCGTTACTTGTTCTTCTGCTCGTTCCTCGATTCGCTTCGACGTTGATGGTCGTCATGGTCACTGGAATGGGTGGGACTGCATTATTCGGGTACATTAGAGTCGCAGTAGCTAACGAGATGCATTGAGAGCTATACAGGCCAGTGGACGTCTCCCTGCCGTGTCCATAGTATGACGATCAATGGTGGGCGCTACACTTGGCCCTTACTCCGTCCGCGACATACTTACTGGGTCTTACTTCACCTTTTGCTGTTTGTATCAGCACTGTCAGAGGAAAGCACGAACCACCAGTCGGCGTGTGCACGAGTTTCCAACTACTAACCATGTGCCGGATAAAGATTCAATTGAGCGCCTAGCCCCATCATAGTCCCCGAGTATACCCATAGTTAGCGTTATGTTCATGAGTGGTGTGAATATCGTTGCGTTTTTGCGTGGAGTTGCGGATAGCCCCACATAAGATGTGTTCTTTTTTCGCCTAGGCCCGATCGTTCTGCGTGGGTAGAATTTGCTTAAACGGACGCTGAGGACACATATTGCGAAATGAGTCCAGGTATGCGCGTCTGGGTGCCACTACAAGGTACGTTCTATTAGTACGCACTTAGGCTTCTCAATCAAGGGTAACGTGCTTATGCTAGACGGCGGTAGGCTTACCTAGACTCCCACGTCTACAAATGGTAGAATGAAGCGGCCGGATAGTAGGCATAGGCGGAGCGCGTATTCTGTAATTCGCACATTAGATCCCCGACACTGTTAACGTCGTGTAACGGTCAAGGGGTTGATTACCGATCATCGTCGTTTGAACGCAGCTTAGTCACGAGAACGCGTCCCTCACGTACTAAGGATAGACTTCGCTGGAGCTGGCTGATACAAGAGAATTAGCCCTTCCTTGTGATAGGTACAT"
        val targetTest = "AGGTACAT"
        val hammingDistance = 5
        // note added trailing space because I am lazy
        val expectedResult = "3 5 10 17 18 26 27 29 30 31 33 34 43 44 48 50 52 54 60 61 62 63 66 67 72 74 75 78 81 83 86 92 94 98 99 104 105 108 111 112 116 122 125 126 131 135 136 139 146 147 153 154 158 160 162 166 169 171 172 176 182 184 186 190 193 194 197 206 209 214 217 220 223 229 232 234 236 240 242 243 248 249 251 254 255 262 265 271 273 275 277 281 286 287 289 290 293 294 298 302 305 307 311 315 316 317 320 321 325 331 332 338 339 341 343 346 347 348 354 362 363 364 370 371 372 374 376 378 379 383 387 389 391 394 395 398 403 406 407 408 418 421 425 426 428 430 432 434 436 438 440 443 444 448 451 452 455 459 460 462 466 468 472 482 486 490 494 497 500 503 509 512 513 516 517 522 525 526 527 532 535 537 539 543 545 546 549 551 558 560 563 566 567 570 572 578 584 586 599 605 609 615 618 622 623 627 629 630 633 636 638 642 643 644 648 652 655 657 659 666 668 670 672 673 674 681 683 685 688 689 692 696 703 705 707 711 716 719 724 725 726 729 734 735 739 743 749 755 759 761 764 765 767 768 771 773 775 777 780 781 782 784 788 789 794 796 798 800 803 804 805 808 810 812 816 818 820 822 825 829 835 836 843 851 852 854 855 860 862 866 870 872 873 879 883 884 888 894 897 898 899 905 913 914 921 922 928 933 937 941 946 947 952 956 958 959 962 964 965 968 971 976 978 982 983 984 987 998 999 1002 1004 1005 1008 1009 1013 1014 1023 1029 1032 1033 1034 1038 1042 1044 1048 1050 1054 1058 1061 1063 1067 1075 1076 1083 1084 1090 1092 1096 1097 1102 1108 1112 1117 1120 1122 1124 1126 1130 1131 1136 1139 1141 1143 1145 1150 1152 1157 1160 1161 1162 1168 1170 1172 1175 1176 1188 1189 1197 1200 1205 1206 1212 1214 1215 1218 1220 1224 1228 1233 1235 1241 1246 1247 1250 1253 1254 1258 1260 1264 1266 1268 1270 1274 1277 1283 1286 1287 1291 1295 1296 1301 1304 1310 1312 1318 1319 1320 1324 1325 1326 1328 1332 1335 1337 1341 1342 1343 1350 1351 1355 1360 1361 1366 1370 1374 1379 1381 1385 1387 1390 1391 1397 1398 1404 1405 1406 1410 1411 1417 1418 1427 1429 1431 1435 1437 1439 1443 1448 1455 1459 1463 1467 1471 1478 1482 1487 1489 1499 1503 1505 1506 1509 1514 1515 1516 1519 1523 1527 1528 1530 1536 1537 1539 1543 1544 1547 1549 1553 1555 1557 1558 1560 1564 1566 1567 1572 1575 1585 1587 1591 1597 1598 1606 1607 1612 1618 1619 1621 1622 1624 1631 1633 1637 1639 1640 1641 1645 1646 1651 1653 1655 1661 1662 1663 1670 1671 1674 1680 1681 1685 1690 1695 1696 1700 1704 1705 1706 1710 1712 1713 1717 1721 1723 1726 1733 1734 1738 1746 1749 1751 1753 1755 1759 1761 1765 1766 1769 1770 1775 1776 1777 1780 1781 1784 1786 1792 1796 1800 1804 1805 1809 1814 1815 1816 1820 1825 1826 1827 1832 1837 1842 1847 1851 1857 1858 1859 1862 1866 1870 1871 1875 1876 1877 1880 1886 1888 1892 1896 1897 1898 1899 1904 1905 1914 1915 1918 1921 1922 1926 1927 1929 1931 1935 1938 1945 1946 1949 1952 1956 1961 1966 1967 1969 1973 1977 1979 1982 1983 1984 1990 1994 1995 2000 2001 2002 2004 2007 2008 2012 2017 2019 2021 2026 2027 2031 2035 2041 2045 2048 2050 2052 2054 2061 2070 2072 2074 2076 2078 2082 2083 2084 2085 2088 2092 2095 2096 2102 2103 2108 2110 2114 2116 2118 2122 2128 2131 2132 2134 2137 2139 2144 2149 2150 2153 2154 2158 2161 2166 2168 2170 2174 2178 2180 2181 2184 2190 2194 2196 2198 2202 2206 2208 2209 2212 2214 2218 2222 2225 2231 2232 2234 2236 2240 2243 2244 2246 2249 2257 2259 2261 2262 2265 2269 2271 2274 2275 2279 2283 2285 2289 2291 2297 2306 2310 2311 2312 2314 2318 2319 2321 2325 2329 2330 2333 2339 2342 2351 2352 2353 2357 2358 2359 2360 2364 2368 2369 2373 2377 2379 2380 2383 2385 2388 2392 2396 2397 2401 2407 2408 2414 2418 2419 2420 2422 2426 2427 2431 2435 2437 2438 2441 2456 2457 2460 2462 2466 2474 2478 2481 2482 2488 2492 2496 2498 2502 2512 2513 2516 2517 2522 2526 2530 2531 2534 2535 2539 2546 2547 2551 2554 2555 2556 2561 2565 2567 2573 2579 2582 2584 2586 2588 2593 2597 2598 2600 2601 2602 2612 2613 2619 2622 2626 2630 2632 2634 2641 2647 2648 2649 2650 2654 2656 2657 2660 2662 2664 2666 2668 2672 2674 2675 2679 2680 2684 2685 2688 2689 2692 2698 2705 2706 2712 2715 2716 2722 2727 2728 2732 2734 2738 2743 2747 2749 2751 2755 2758 2762 2763 2772 2776 2777 2778 2781 2782 2784 2790 2793 2797 2801 2802 2805 2810 2811 2814 2815 2817 2831 2833 2837 2844 2846 2847 2848 2850 2851 2856 2857 2859 2863 2868 2870 2877 2879 2883 2884 2887 2889 2894 2895 2901 2904 2908 2909 2913 2915 2917 2921 2923 2934 2935 2936 2940 2945 2946 2952 2956 2958 2962 2964 2965 2968 2976 2977 2978 2983 2985 2988 2992 2995 2997 2998 3003 3008 3012 3016 3018 3024 3027 3029 3035 3038 3044 3047 3048 3054 3055 3058 3066 3070 3072 3073 3074 3078 3082 3085 3087 3088 3095 3096 3101 3102 3105 3106 3108 3112 3116 3120 3128 3132 3138 3142 3143 3151 3152 3157 3158 3159 3165 3166 3167 3171 3173 3174 3178 3181 3182 3184 3189 3191 3192 3194 3195 3196 3203 3204 3205 3212 3215 3220 3222 3228 3230 3232 3234 3237 3241 3245 3249 3250 3254 3255 3258 3259 3267 3268 3274 3277 3279 3281 3282 3283 3285 3288 3295 3296 3299 3300 3302 3306 3308 3309 3311 3315 3322 3324 3326 3330 3333 3335 3343 3344 3349 3351 3353 3356 3358 3362 3366 3370 3372 3374 3378 3387 3390 3395 3401 3403 3407 3411 3415 3417 3419 3420 3424 3427 3433 3440 3444 3447 3448 3449 3456 3458 3463 3465 3468 3470 3472 3475 3477 3478 3481 3483 3485 3489 3493 3495 3497 3501 3503 3505 3507 3509 3515 3516 3522 3523 3524 3530 3533 3534 3537 3538 3544 3546 3553 3555 3557 3564 3565 3567 3568 3572 3574 3580 3586 3595 3601 3604 3605 3606 3608 3612 3616 3617 3623 3628 3629 3633 3634 3640 3641 3645 3649 3655 3656 3657 3661 3665 3667 3671 3673 3679 3681 3685 3691 3692 3696 3697 3702 3704 3708 3718 3719 3720 3723 3724 3727 3731 3735 3744 3750 3751 3752 3755 3759 3761 3765 3768 3772 3774 3775 3779 3781 3785 3788 3792 3796 3798 3799 3801 3804 3808 3812 3818 3820 3825 3826 3829 3831 3835 3838 3840 3841 3851 3852 3855 3859 3863 3865 3867 3873 3877 3879 3889 3890 3892 3893 3899 3900 3904 3912 3915 3916 3917 3923 3926 3928 3929 3932 3933 3935 3937 3944 3947 3953 3954 3958 3959 3964 3971 3973 3979 3980 3986 3988 3992 3994 3996 3997 4001 4006 4009 4010 4016 4019 4020 4021 4024 4029 4033 4035 4037 4040 4044 4048 4049 4051 4053 4055 4062 4063 4067 4070 4073 4082 4083 4086 4088 4096 4097 4098 4100 4102 4104 4108 4109 4111 4115 4117 4121 4124 4128 4130 4131 4132 4137 4138 4139 4142 4143 4149 4150 4153 4155 4161 4162 4163 4167 4169 4171 4174 4178 4181 4185 4188 4194 4195 4197 4198 4204 4206 4208 4209 4216 4219 4220 4226 4228 4232 4236 4237 4240 4241 4247 4248 4251 4252 4256 4259 4260 4268 4269 4273 4274 4276 4277 4281 4282 4285 4288 4289 4290 4295 4296 4298 4300 4303 4304 4310 4311 4317 4321 4323 4324 4325 4327 4329 4333 4334 4339 4341 4349 4350 4354 4357 4363 4365 4376 4377 4378 4384 4388 4392 4394 4398 4401 4402 4403 4407 4408 4410 4412 4413 4414 4415 4416 4418 4422 4424 4425 4429 4431 4433 4436 4440 4442 4444 4446 4448 4452 4453 4458 4459 4462 4464 4466 4473 4475 4483 4485 4489 4493 4494 4498 4500 4502 4504 4506 4509 4510 4512 4521 4525 4526 4530 4531 4536 4537 4538 4544 4548 4550 4558 4561 4568 4569 4572 4578 4579 4582 4583 4590 4593 4594 4595 4599 4600 4604 4607 4608 4611 4614 4620 4623 4624 4630 4631 4635 4637 4640 4643 4644 4653 4654 4656 4660 4662 4664 4669 4674 4676 4680 4687 4688 4692 4700 4704 4705 4708 4709 4712 4716 4718 4722 4726 4734 4735 4736 4738 4739 4740 4742 4744 4745 4751 4753 4754 4755 4761 4766 4767 4771 4777 4779 4781 4785 4786 4790 4792 4794 4798 4801 4803 4805 4807 4809 4812 4820 4821 4824 4825 4827 4829 4831 4835 4840 4843 4844 4845 4847 4851 4853 4857 4864 4865 4869 4878 4880 4886 4891 4892 4894 4897 4898 4900 4904 4910 4915 4917 4919 4920 4921 4926 4928 4939 4941 4943 4947 4948 4954 4955 4957 4959 4961 4963 4966 4968 4969 4976 4981 4983 4987 4991 4995 4999 5003 5007 5009 5015 5018 5021 5022 5029 5032 5035 5038 5042 5045 5047 5051 5052 5057 5062 5072 5077 5081 5083 5088 5092 5094 5096 5098 5101 5102 5106 5108 5111 5113 5115 5119 5124 5125 5126 5128 5130 5132 5134 5136 5140 5146 5147 5151 5152 5155 5157 5161 5165 5166 5172 5178 5180 5182 5184 5186 5187 5188 5190 5199 5204 5208 5210 5218 5221 5224 5226 5232 5235 5236 5239 5241 5243 5249 5251 5252 5253 5255 5265 5268 5269 5272 5280 5284 5286 5290 5294 5295 5299 5300 5306 5310 5311 5317 5320 5321 5324 5329 5333 5335 5336 5337 5343 5347 5349 5353 5354 5355 5359 5360 5361 5362 5365 5366 5370 5378 5379 5381 5383 5385 5390 5396 5397 5403 5410 5411 5412 5414 5419 5420 5424 5428 5434 5435 5441 5449 5450 5451 5454 5458 5459 5460 5462 5464 5465 5469 5473 5475 5477 5479 5481 5483 5485 5486 5498 5499 5501 5505 5511 5512 5514 5517 5518 5520 5526 5529 5532 5535 5543 5544 5546 5552 5553 5559 5560 5563 5564 5566 5568 5572 5576 5578 5580 5592 5601 5605 5606 5612 5615 5621 5625 5628 5629 5632 5634 5635 5636 5638 5641 5646 5647 5650 5654 5657 5661 5665 5669 5671 5672 5675 5680 5682 5684 5686 5688 5691 5695 5697 5704 5708 5715 5720 5724 5725 5728 5732 5733 5736 5737 5738 5741 5742 5747 5751 5754 5758 5760 5762 5766 5768 5770 5774 5776 5780 5786 5787 5790 5793 5795 5799 5800 5802 5804 5806 5808 5810 5815 5817 5821 5823 5824 5830 5838 5840 5841 5842 5848 5850 5856 5858 5862 5864 5868 5869 5874 5875 5878 5883 5887 5888 5892 5897 5900 5902 5903 5906 5907 5913 5914 5917 5920 5922 5923 5924 5927 5928 5930 5931 5934 5936 5939 5943 5944 5949 5953 5955 5961 5973 5979 5982 5987 5992 5996 5998 5999 6000 6004 6005 6008 6009 6015 6021 6026 6027 6028 6033 6034 6041 6042 6045 6046 6049 6050 6056 6064 6065 6068 6069 6072 6076 6078 6081 6084 6088 6094 6095 6097 6103 6104 6105 6109 6111 6117 6120 6123 6126 6136 6141 6145 6147 6152 6153 6156 6160 6164 6166 6170 6172 6174 6176 6178 6183 6184 6188 6190 6194 6196 6207 6214 6215 6218 6221 6222 6229 6232 6233 6236 6238 6244 6245 6249 6252 6256 6261 6263 6265 6269 6275 6276 6279 6282 6284 6294 6296 6298 6300 6301 6304 6306 6307 6310 6312 6316 6317 6318 6322 6323 6326 6327 6329 6330 6339 6343 6345 6349 6352 6353 6359 6363 6364 6366 6370 6373 6375 6376 6377 6379 6383 6384 6385 6393 6394 6401 6404 6411 6412 6417 6419 6421 6423 6425 6427 6431 6435 6437 6441 6450 6452 6454 6456 6459 6460 6464 6468 6472 6476 6478 6482 6484 6486 6488 6492 6494 6498 6499 6503 6505 6507 6508 6524 6525 6528 6529 6532 6535 6539 6543 6544 6546 6549 6550 6553 6554 6560 6561 6564 6569 6570 6571 6573 6575 6581 6585 6589 6595 6599 6609 6610 6612 6615 6622 6626 6627 6635 6639 6643 6647 6648 6651 6660 6661 6662 6663 6667 6669 6676 6682 6686 6690 6692 6696 6700 6702 6704 6708 6710 6712 6719 6720 6723 6726 6730 6733 6735 6736 6737 6740 6742 6744 6750 6753 6755 6757 6759 6766 6771 6773 6778 6782 6787 6789 6794 6795 6796 6799 6800 6803 6805 6806 6810 6811 6816 6817 6819 6820 6824 6829 6832 6835 6836 6840 6843 6847 6848 6851 6856 6860 6862 6868 6873 6877 6884 6885 6886 6888 6892 6896 6898 6899 6902 6905 6909 6915 6916 6918 6920 6923 6926 6927 6934 6936 6940 "
        val matchList = matchPatternWithErrorRate(genomeTest, targetTest, hammingDistance)

        assertNotEquals(0, matchList.size)
        var convertList = ""
        for (i in matchList) {
            convertList += "$i "
        }
        assertEquals(convertList, expectedResult)

    }

    // step 6 - seems identical to 4



    @Test
    @DisplayName( "test with match kMer pattern sample at step 6")
    fun testkMerPatternStep6() {

        val sampleGenome = "GAATCCGCCAAGTACCAAGATGTAAGTGAGGAGCGCTTAGGTCTGTACTGCGCATAAGCCTTAACGCGAAGTATGGATATGCTCCCCGGATACAGGTTTGGGATTTGGCGGTTACCTAAGCTAACGGTGAGACCGATATGACGAGGTTCCTATCTTAATCATATTCACATACTGAACGAGGCGCCCAGTTTCTTCTCACCAATATGTCAGGAAGCTACAGTGCAGCATTATCCACACCATTCCACTTATCCTTGAACGGAAGTCTTATGCGAAGATTATTCTGAGAAGCCCTTGTGCCCTGCATCACGATTTGCAGACTGACAGGGAATCTTAAGGCCACTCAAA"
        val sampleTarget = "TACAG"
        val sampleMismatches = 2
        val sampleExpectedOutput = 27

        // note added trailing space because I am lazy
        val expectedResult = "3 5 10 17 18 26 27 29 30 31 33 34 43 44 48 50 52 54 60 61 62 63 66 67 72 74 75 78 81 83 86 92 94 98 99 104 105 108 111 112 116 122 125 126 131 135 136 139 146 147 153 154 158 160 162 166 169 171 172 176 182 184 186 190 193 194 197 206 209 214 217 220 223 229 232 234 236 240 242 243 248 249 251 254 255 262 265 271 273 275 277 281 286 287 289 290 293 294 298 302 305 307 311 315 316 317 320 321 325 331 332 338 339 341 343 346 347 348 354 362 363 364 370 371 372 374 376 378 379 383 387 389 391 394 395 398 403 406 407 408 418 421 425 426 428 430 432 434 436 438 440 443 444 448 451 452 455 459 460 462 466 468 472 482 486 490 494 497 500 503 509 512 513 516 517 522 525 526 527 532 535 537 539 543 545 546 549 551 558 560 563 566 567 570 572 578 584 586 599 605 609 615 618 622 623 627 629 630 633 636 638 642 643 644 648 652 655 657 659 666 668 670 672 673 674 681 683 685 688 689 692 696 703 705 707 711 716 719 724 725 726 729 734 735 739 743 749 755 759 761 764 765 767 768 771 773 775 777 780 781 782 784 788 789 794 796 798 800 803 804 805 808 810 812 816 818 820 822 825 829 835 836 843 851 852 854 855 860 862 866 870 872 873 879 883 884 888 894 897 898 899 905 913 914 921 922 928 933 937 941 946 947 952 956 958 959 962 964 965 968 971 976 978 982 983 984 987 998 999 1002 1004 1005 1008 1009 1013 1014 1023 1029 1032 1033 1034 1038 1042 1044 1048 1050 1054 1058 1061 1063 1067 1075 1076 1083 1084 1090 1092 1096 1097 1102 1108 1112 1117 1120 1122 1124 1126 1130 1131 1136 1139 1141 1143 1145 1150 1152 1157 1160 1161 1162 1168 1170 1172 1175 1176 1188 1189 1197 1200 1205 1206 1212 1214 1215 1218 1220 1224 1228 1233 1235 1241 1246 1247 1250 1253 1254 1258 1260 1264 1266 1268 1270 1274 1277 1283 1286 1287 1291 1295 1296 1301 1304 1310 1312 1318 1319 1320 1324 1325 1326 1328 1332 1335 1337 1341 1342 1343 1350 1351 1355 1360 1361 1366 1370 1374 1379 1381 1385 1387 1390 1391 1397 1398 1404 1405 1406 1410 1411 1417 1418 1427 1429 1431 1435 1437 1439 1443 1448 1455 1459 1463 1467 1471 1478 1482 1487 1489 1499 1503 1505 1506 1509 1514 1515 1516 1519 1523 1527 1528 1530 1536 1537 1539 1543 1544 1547 1549 1553 1555 1557 1558 1560 1564 1566 1567 1572 1575 1585 1587 1591 1597 1598 1606 1607 1612 1618 1619 1621 1622 1624 1631 1633 1637 1639 1640 1641 1645 1646 1651 1653 1655 1661 1662 1663 1670 1671 1674 1680 1681 1685 1690 1695 1696 1700 1704 1705 1706 1710 1712 1713 1717 1721 1723 1726 1733 1734 1738 1746 1749 1751 1753 1755 1759 1761 1765 1766 1769 1770 1775 1776 1777 1780 1781 1784 1786 1792 1796 1800 1804 1805 1809 1814 1815 1816 1820 1825 1826 1827 1832 1837 1842 1847 1851 1857 1858 1859 1862 1866 1870 1871 1875 1876 1877 1880 1886 1888 1892 1896 1897 1898 1899 1904 1905 1914 1915 1918 1921 1922 1926 1927 1929 1931 1935 1938 1945 1946 1949 1952 1956 1961 1966 1967 1969 1973 1977 1979 1982 1983 1984 1990 1994 1995 2000 2001 2002 2004 2007 2008 2012 2017 2019 2021 2026 2027 2031 2035 2041 2045 2048 2050 2052 2054 2061 2070 2072 2074 2076 2078 2082 2083 2084 2085 2088 2092 2095 2096 2102 2103 2108 2110 2114 2116 2118 2122 2128 2131 2132 2134 2137 2139 2144 2149 2150 2153 2154 2158 2161 2166 2168 2170 2174 2178 2180 2181 2184 2190 2194 2196 2198 2202 2206 2208 2209 2212 2214 2218 2222 2225 2231 2232 2234 2236 2240 2243 2244 2246 2249 2257 2259 2261 2262 2265 2269 2271 2274 2275 2279 2283 2285 2289 2291 2297 2306 2310 2311 2312 2314 2318 2319 2321 2325 2329 2330 2333 2339 2342 2351 2352 2353 2357 2358 2359 2360 2364 2368 2369 2373 2377 2379 2380 2383 2385 2388 2392 2396 2397 2401 2407 2408 2414 2418 2419 2420 2422 2426 2427 2431 2435 2437 2438 2441 2456 2457 2460 2462 2466 2474 2478 2481 2482 2488 2492 2496 2498 2502 2512 2513 2516 2517 2522 2526 2530 2531 2534 2535 2539 2546 2547 2551 2554 2555 2556 2561 2565 2567 2573 2579 2582 2584 2586 2588 2593 2597 2598 2600 2601 2602 2612 2613 2619 2622 2626 2630 2632 2634 2641 2647 2648 2649 2650 2654 2656 2657 2660 2662 2664 2666 2668 2672 2674 2675 2679 2680 2684 2685 2688 2689 2692 2698 2705 2706 2712 2715 2716 2722 2727 2728 2732 2734 2738 2743 2747 2749 2751 2755 2758 2762 2763 2772 2776 2777 2778 2781 2782 2784 2790 2793 2797 2801 2802 2805 2810 2811 2814 2815 2817 2831 2833 2837 2844 2846 2847 2848 2850 2851 2856 2857 2859 2863 2868 2870 2877 2879 2883 2884 2887 2889 2894 2895 2901 2904 2908 2909 2913 2915 2917 2921 2923 2934 2935 2936 2940 2945 2946 2952 2956 2958 2962 2964 2965 2968 2976 2977 2978 2983 2985 2988 2992 2995 2997 2998 3003 3008 3012 3016 3018 3024 3027 3029 3035 3038 3044 3047 3048 3054 3055 3058 3066 3070 3072 3073 3074 3078 3082 3085 3087 3088 3095 3096 3101 3102 3105 3106 3108 3112 3116 3120 3128 3132 3138 3142 3143 3151 3152 3157 3158 3159 3165 3166 3167 3171 3173 3174 3178 3181 3182 3184 3189 3191 3192 3194 3195 3196 3203 3204 3205 3212 3215 3220 3222 3228 3230 3232 3234 3237 3241 3245 3249 3250 3254 3255 3258 3259 3267 3268 3274 3277 3279 3281 3282 3283 3285 3288 3295 3296 3299 3300 3302 3306 3308 3309 3311 3315 3322 3324 3326 3330 3333 3335 3343 3344 3349 3351 3353 3356 3358 3362 3366 3370 3372 3374 3378 3387 3390 3395 3401 3403 3407 3411 3415 3417 3419 3420 3424 3427 3433 3440 3444 3447 3448 3449 3456 3458 3463 3465 3468 3470 3472 3475 3477 3478 3481 3483 3485 3489 3493 3495 3497 3501 3503 3505 3507 3509 3515 3516 3522 3523 3524 3530 3533 3534 3537 3538 3544 3546 3553 3555 3557 3564 3565 3567 3568 3572 3574 3580 3586 3595 3601 3604 3605 3606 3608 3612 3616 3617 3623 3628 3629 3633 3634 3640 3641 3645 3649 3655 3656 3657 3661 3665 3667 3671 3673 3679 3681 3685 3691 3692 3696 3697 3702 3704 3708 3718 3719 3720 3723 3724 3727 3731 3735 3744 3750 3751 3752 3755 3759 3761 3765 3768 3772 3774 3775 3779 3781 3785 3788 3792 3796 3798 3799 3801 3804 3808 3812 3818 3820 3825 3826 3829 3831 3835 3838 3840 3841 3851 3852 3855 3859 3863 3865 3867 3873 3877 3879 3889 3890 3892 3893 3899 3900 3904 3912 3915 3916 3917 3923 3926 3928 3929 3932 3933 3935 3937 3944 3947 3953 3954 3958 3959 3964 3971 3973 3979 3980 3986 3988 3992 3994 3996 3997 4001 4006 4009 4010 4016 4019 4020 4021 4024 4029 4033 4035 4037 4040 4044 4048 4049 4051 4053 4055 4062 4063 4067 4070 4073 4082 4083 4086 4088 4096 4097 4098 4100 4102 4104 4108 4109 4111 4115 4117 4121 4124 4128 4130 4131 4132 4137 4138 4139 4142 4143 4149 4150 4153 4155 4161 4162 4163 4167 4169 4171 4174 4178 4181 4185 4188 4194 4195 4197 4198 4204 4206 4208 4209 4216 4219 4220 4226 4228 4232 4236 4237 4240 4241 4247 4248 4251 4252 4256 4259 4260 4268 4269 4273 4274 4276 4277 4281 4282 4285 4288 4289 4290 4295 4296 4298 4300 4303 4304 4310 4311 4317 4321 4323 4324 4325 4327 4329 4333 4334 4339 4341 4349 4350 4354 4357 4363 4365 4376 4377 4378 4384 4388 4392 4394 4398 4401 4402 4403 4407 4408 4410 4412 4413 4414 4415 4416 4418 4422 4424 4425 4429 4431 4433 4436 4440 4442 4444 4446 4448 4452 4453 4458 4459 4462 4464 4466 4473 4475 4483 4485 4489 4493 4494 4498 4500 4502 4504 4506 4509 4510 4512 4521 4525 4526 4530 4531 4536 4537 4538 4544 4548 4550 4558 4561 4568 4569 4572 4578 4579 4582 4583 4590 4593 4594 4595 4599 4600 4604 4607 4608 4611 4614 4620 4623 4624 4630 4631 4635 4637 4640 4643 4644 4653 4654 4656 4660 4662 4664 4669 4674 4676 4680 4687 4688 4692 4700 4704 4705 4708 4709 4712 4716 4718 4722 4726 4734 4735 4736 4738 4739 4740 4742 4744 4745 4751 4753 4754 4755 4761 4766 4767 4771 4777 4779 4781 4785 4786 4790 4792 4794 4798 4801 4803 4805 4807 4809 4812 4820 4821 4824 4825 4827 4829 4831 4835 4840 4843 4844 4845 4847 4851 4853 4857 4864 4865 4869 4878 4880 4886 4891 4892 4894 4897 4898 4900 4904 4910 4915 4917 4919 4920 4921 4926 4928 4939 4941 4943 4947 4948 4954 4955 4957 4959 4961 4963 4966 4968 4969 4976 4981 4983 4987 4991 4995 4999 5003 5007 5009 5015 5018 5021 5022 5029 5032 5035 5038 5042 5045 5047 5051 5052 5057 5062 5072 5077 5081 5083 5088 5092 5094 5096 5098 5101 5102 5106 5108 5111 5113 5115 5119 5124 5125 5126 5128 5130 5132 5134 5136 5140 5146 5147 5151 5152 5155 5157 5161 5165 5166 5172 5178 5180 5182 5184 5186 5187 5188 5190 5199 5204 5208 5210 5218 5221 5224 5226 5232 5235 5236 5239 5241 5243 5249 5251 5252 5253 5255 5265 5268 5269 5272 5280 5284 5286 5290 5294 5295 5299 5300 5306 5310 5311 5317 5320 5321 5324 5329 5333 5335 5336 5337 5343 5347 5349 5353 5354 5355 5359 5360 5361 5362 5365 5366 5370 5378 5379 5381 5383 5385 5390 5396 5397 5403 5410 5411 5412 5414 5419 5420 5424 5428 5434 5435 5441 5449 5450 5451 5454 5458 5459 5460 5462 5464 5465 5469 5473 5475 5477 5479 5481 5483 5485 5486 5498 5499 5501 5505 5511 5512 5514 5517 5518 5520 5526 5529 5532 5535 5543 5544 5546 5552 5553 5559 5560 5563 5564 5566 5568 5572 5576 5578 5580 5592 5601 5605 5606 5612 5615 5621 5625 5628 5629 5632 5634 5635 5636 5638 5641 5646 5647 5650 5654 5657 5661 5665 5669 5671 5672 5675 5680 5682 5684 5686 5688 5691 5695 5697 5704 5708 5715 5720 5724 5725 5728 5732 5733 5736 5737 5738 5741 5742 5747 5751 5754 5758 5760 5762 5766 5768 5770 5774 5776 5780 5786 5787 5790 5793 5795 5799 5800 5802 5804 5806 5808 5810 5815 5817 5821 5823 5824 5830 5838 5840 5841 5842 5848 5850 5856 5858 5862 5864 5868 5869 5874 5875 5878 5883 5887 5888 5892 5897 5900 5902 5903 5906 5907 5913 5914 5917 5920 5922 5923 5924 5927 5928 5930 5931 5934 5936 5939 5943 5944 5949 5953 5955 5961 5973 5979 5982 5987 5992 5996 5998 5999 6000 6004 6005 6008 6009 6015 6021 6026 6027 6028 6033 6034 6041 6042 6045 6046 6049 6050 6056 6064 6065 6068 6069 6072 6076 6078 6081 6084 6088 6094 6095 6097 6103 6104 6105 6109 6111 6117 6120 6123 6126 6136 6141 6145 6147 6152 6153 6156 6160 6164 6166 6170 6172 6174 6176 6178 6183 6184 6188 6190 6194 6196 6207 6214 6215 6218 6221 6222 6229 6232 6233 6236 6238 6244 6245 6249 6252 6256 6261 6263 6265 6269 6275 6276 6279 6282 6284 6294 6296 6298 6300 6301 6304 6306 6307 6310 6312 6316 6317 6318 6322 6323 6326 6327 6329 6330 6339 6343 6345 6349 6352 6353 6359 6363 6364 6366 6370 6373 6375 6376 6377 6379 6383 6384 6385 6393 6394 6401 6404 6411 6412 6417 6419 6421 6423 6425 6427 6431 6435 6437 6441 6450 6452 6454 6456 6459 6460 6464 6468 6472 6476 6478 6482 6484 6486 6488 6492 6494 6498 6499 6503 6505 6507 6508 6524 6525 6528 6529 6532 6535 6539 6543 6544 6546 6549 6550 6553 6554 6560 6561 6564 6569 6570 6571 6573 6575 6581 6585 6589 6595 6599 6609 6610 6612 6615 6622 6626 6627 6635 6639 6643 6647 6648 6651 6660 6661 6662 6663 6667 6669 6676 6682 6686 6690 6692 6696 6700 6702 6704 6708 6710 6712 6719 6720 6723 6726 6730 6733 6735 6736 6737 6740 6742 6744 6750 6753 6755 6757 6759 6766 6771 6773 6778 6782 6787 6789 6794 6795 6796 6799 6800 6803 6805 6806 6810 6811 6816 6817 6819 6820 6824 6829 6832 6835 6836 6840 6843 6847 6848 6851 6856 6860 6862 6868 6873 6877 6884 6885 6886 6888 6892 6896 6898 6899 6902 6905 6909 6915 6916 6918 6920 6923 6926 6927 6934 6936 6940 "
        val matchList = matchPatternWithErrorRate(sampleGenome, sampleTarget, sampleMismatches)

        assertNotEquals(0, matchList.size)

        assertEquals(sampleExpectedOutput, matchList.size)

    }
}