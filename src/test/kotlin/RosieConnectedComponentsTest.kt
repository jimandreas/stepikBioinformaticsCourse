@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.RosalindSearch
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.collections.set
import kotlin.test.assertEquals

/**
 *
 * Connected Components
 * rosalind: http://rosalind.info/problems/cc/
 * Note: not part of the stepik cirriculum.
 */

internal class RosieConnectedComponentsTest {

    lateinit var rbfs: RosalindSearch

    @BeforeEach
    fun setUp() {
        rbfs = RosalindSearch()
    }

    // example from:
    // http://rosalind.info/problems/cc/

    @Test
    @DisplayName("Rosalind Connected Components sample test")
    fun rosalindConnectedComponentsSampleTest() {
        val sampleInput = """
12 13
1 2
1 5
5 9
5 10
9 10
3 4
3 7
3 8
4 8
7 11
8 11
11 12
8 12
        """.trimIndent()

        val expectedOutputString = """
            3
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val numNodes = input[0].split(" ").first().toInt()
        input.removeFirst()
        val m = parseGraphInput(numNodes, input)

        val expectedResult = expectedOutputString.toInt()

        // note that node numbers start at 1
        val result = rbfs.numberOfConnectedComponents(numNodes, m)

        assertEquals(expectedResult, result)
    }


    /**
     * take the list of edges (from to) and convert to a map of the base node to the list of connected
     * nodes.   Add the reverse edge to make it a fully connected graph.
     */
    private fun parseGraphInput(numNodes: Int, lines: List<String>): HashMap<Int, MutableList<Int>> {
        val map: HashMap<Int, MutableList<Int>> = hashMapOf()

        // make sure all nodes have a list
        for (i in 1..numNodes) {
            map[i] = mutableListOf()
        }

        for (l in lines) {
            val p = l.split(" ").map { it.toInt() }
            if (!map.containsKey(p[0])) {
                map[p[0]] = mutableListOf(p[1])
            } else {
                map[p[0]]!!.add(p[1])
            }

            if (!map.containsKey(p[1])) {
                map[p[1]] = mutableListOf(p[0])
            } else {
                map[p[1]]!!.add(p[0])
            }
        }

        return map
    }

    @Test
    @DisplayName("Rosalind Connected Components quiz test")
    fun rosalindConnectedComponentsQuizTest() {
        val sampleInput = """
857 1193
292 669
779 551
801 396
295 739
569 485
594 613
11 540
408 339
150 805
175 353
782 827
292 410
395 654
845 498
318 477
710 429
752 340
91 706
6 209
549 126
416 627
426 779
696 494
732 471
372 212
7 94
266 217
363 179
163 311
583 339
261 852
172 305
835 142
599 206
87 289
409 309
149 140
8 784
708 193
444 590
346 754
81 626
713 58
422 809
629 762
600 306
787 799
159 83
614 513
711 529
139 822
715 355
815 70
731 580
594 604
583 42
674 37
135 93
121 214
795 163
800 503
482 250
123 528
761 272
621 141
346 800
161 176
502 630
491 795
382 209
259 126
315 397
749 582
216 497
4 87
805 655
636 129
34 716
409 276
759 602
198 602
234 220
353 788
815 460
324 455
491 420
234 289
414 206
320 718
390 97
30 136
501 520
816 834
522 143
87 812
602 37
749 210
103 82
673 348
194 227
315 738
559 847
260 75
612 237
94 570
513 726
192 794
690 516
797 299
205 166
368 123
589 711
413 569
20 115
658 408
838 108
404 58
30 199
7 738
826 466
496 102
401 328
48 709
521 175
707 225
728 154
328 746
390 483
452 634
754 658
164 610
441 552
76 137
309 344
705 501
96 283
262 754
221 349
503 74
464 292
377 351
453 530
102 21
513 559
137 440
323 159
485 780
191 313
449 394
787 650
157 602
338 737
478 251
330 772
754 729
842 106
113 462
103 509
668 479
144 727
474 456
486 281
70 183
689 343
366 835
81 397
541 719
662 543
442 621
109 11
268 238
803 438
832 692
784 745
427 191
212 316
249 51
762 265
703 721
99 360
618 7
359 766
60 84
754 339
546 140
648 188
28 13
207 563
292 671
689 110
699 274
656 172
573 454
644 199
241 307
783 409
139 232
182 716
418 213
853 87
780 793
135 786
208 822
656 59
843 97
57 172
486 181
663 385
301 788
723 613
306 161
573 529
377 771
180 89
647 225
566 635
147 583
397 333
114 598
834 609
737 417
606 587
847 488
777 319
255 67
720 819
324 564
495 638
737 34
103 212
224 823
531 553
108 737
547 150
27 789
316 637
814 495
340 712
563 155
8 447
743 825
708 314
357 339
399 648
493 73
602 231
412 817
681 403
35 260
297 856
416 33
834 484
700 620
818 108
321 524
358 326
173 238
178 588
115 605
718 257
420 795
310 558
834 128
28 564
575 856
330 41
509 760
228 754
367 339
456 283
359 221
838 737
21 575
427 173
65 568
88 381
814 363
855 747
647 146
72 265
343 801
261 783
18 412
582 377
783 27
28 486
673 436
159 235
89 752
277 391
258 339
1 217
112 319
128 275
420 804
38 511
437 804
854 519
767 586
667 15
831 222
654 182
617 198
284 248
751 699
758 473
721 587
346 443
819 758
295 26
321 469
249 228
211 145
777 150
630 142
212 394
180 204
374 293
215 730
266 736
339 261
491 627
679 210
232 546
793 586
626 832
284 367
242 434
524 750
532 547
807 449
805 281
45 682
291 33
566 197
341 712
843 288
428 338
732 15
742 450
463 842
586 745
544 379
248 614
33 413
780 681
573 848
342 136
807 788
518 132
805 703
437 314
221 265
359 462
384 839
451 488
446 337
78 794
540 32
718 808
475 394
52 448
790 351
219 739
706 216
578 220
215 164
806 579
483 834
290 518
676 425
471 401
24 124
701 106
172 742
165 723
209 771
725 47
250 324
714 570
214 577
44 713
153 378
802 529
456 324
184 73
469 157
402 333
337 428
628 216
295 620
79 181
774 21
627 325
622 667
577 777
520 589
753 526
749 532
420 427
230 329
687 824
98 594
394 159
739 298
668 137
242 821
597 840
746 626
833 69
136 435
347 228
215 154
695 449
80 780
594 444
118 852
705 728
756 83
590 252
556 346
3 618
354 176
817 542
819 162
310 802
725 42
242 95
837 769
789 743
635 3
470 190
117 142
726 531
281 80
60 783
683 741
687 702
641 846
631 750
52 555
185 82
755 194
250 94
389 757
759 538
25 744
414 377
40 240
742 581
765 159
34 668
252 114
331 265
184 250
260 795
17 33
237 806
492 148
179 46
507 350
148 520
819 332
448 392
656 698
739 570
728 409
571 794
687 13
18 579
41 696
528 95
476 367
463 832
842 844
604 99
280 228
174 656
33 382
536 184
184 831
634 561
193 7
419 624
51 236
128 348
16 791
333 42
822 448
485 237
813 12
752 179
747 718
459 168
768 248
172 134
213 393
401 376
470 690
832 71
408 2
597 144
544 222
527 849
308 345
539 521
168 507
131 707
22 163
48 385
679 378
356 243
45 305
50 33
345 659
537 390
757 637
30 133
138 239
201 139
376 834
600 374
392 485
521 121
162 657
324 570
474 623
169 13
708 327
155 763
782 537
725 801
498 155
821 532
387 427
35 831
775 51
311 110
323 777
195 189
787 81
69 381
522 489
261 579
36 842
327 847
796 203
774 102
283 440
763 412
452 529
128 17
292 442
8 307
457 523
805 725
171 526
144 414
130 611
206 98
120 731
534 282
469 211
791 697
665 657
449 105
480 662
593 842
637 745
720 163
310 306
747 700
764 758
241 500
217 455
148 711
662 242
413 642
373 432
483 314
266 694
422 613
774 206
188 421
681 557
745 507
823 528
220 34
483 256
462 493
418 79
526 782
374 101
160 689
401 533
102 130
104 317
654 160
399 466
429 297
575 32
490 348
455 451
616 95
787 425
309 378
270 693
413 746
61 764
619 665
631 201
470 83
566 171
532 519
391 789
517 81
636 622
760 619
252 390
487 546
529 677
220 268
230 326
136 431
556 122
154 90
84 801
426 149
96 583
71 311
796 463
483 366
153 565
665 163
528 536
268 246
170 425
732 504
565 750
31 139
670 474
440 449
546 511
207 241
693 636
230 543
492 6
374 179
324 823
591 349
367 829
338 575
193 320
688 205
354 316
837 489
471 542
207 472
696 434
7 609
192 461
296 841
116 59
315 784
825 321
393 740
559 723
721 496
683 467
203 359
745 640
341 495
775 175
823 490
753 39
698 375
440 421
764 407
514 802
697 518
783 458
787 88
138 314
44 831
253 183
724 793
206 386
353 94
99 275
231 329
809 109
63 646
596 694
362 849
358 651
15 85
765 533
194 545
457 791
531 504
509 97
736 171
141 263
72 776
109 840
670 403
296 251
646 14
230 133
779 653
542 507
105 61
721 43
175 600
190 549
719 276
218 704
720 377
356 504
699 716
242 305
558 614
28 693
497 661
733 812
88 472
448 69
740 2
76 650
368 857
158 207
375 326
857 195
291 452
585 296
385 31
137 141
530 460
238 158
550 83
815 643
844 128
803 789
123 262
611 661
348 130
655 518
560 725
151 506
373 481
526 628
369 347
594 404
16 39
218 600
327 142
493 674
195 591
616 149
532 788
83 814
152 105
750 716
251 228
771 339
227 600
277 856
808 625
427 484
278 190
685 662
497 517
455 90
79 557
211 316
423 751
463 505
43 360
781 281
505 248
624 834
282 610
290 759
17 853
299 710
342 445
708 856
13 702
778 256
660 591
170 319
160 151
676 293
174 671
649 164
141 538
521 559
703 819
516 687
273 747
468 510
613 56
208 375
764 527
330 46
275 236
385 393
294 492
509 559
270 631
365 821
505 60
741 357
499 776
363 487
577 588
713 605
119 508
137 213
641 27
528 730
394 716
397 166
255 571
314 357
525 530
585 383
694 139
229 570
229 290
213 590
38 548
232 451
181 122
702 325
599 778
215 817
101 806
696 43
72 651
717 143
851 686
98 106
13 487
361 319
513 29
230 49
93 555
487 813
126 429
715 480
431 101
114 684
56 295
148 149
810 717
673 119
264 713
261 160
435 430
832 802
104 713
822 452
792 568
138 627
365 509
45 577
391 138
497 122
113 80
38 346
493 288
624 140
731 449
809 324
384 25
302 36
248 731
671 688
136 42
301 572
368 480
451 263
502 277
671 583
165 830
507 321
786 771
94 436
601 344
612 49
311 695
394 210
222 843
574 444
565 287
753 214
444 654
316 395
437 651
568 448
42 361
43 795
731 244
138 836
207 34
550 486
22 198
734 375
430 696
524 413
723 642
824 132
749 792
813 585
17 506
393 312
56 180
577 194
794 376
366 337
106 75
215 355
483 23
509 362
463 191
355 603
266 431
793 128
220 673
95 567
447 243
107 419
497 155
246 223
590 381
31 375
688 39
830 698
294 712
581 843
746 92
251 140
523 227
709 787
19 536
645 783
472 525
289 233
688 312
218 618
60 13
718 724
297 792
418 493
339 523
375 786
590 406
270 710
3 815
244 44
196 147
414 547
170 198
261 851
608 332
147 312
538 389
772 387
134 214
398 771
586 256
496 429
290 343
626 493
646 586
484 777
483 771
410 407
473 198
195 285
809 476
533 803
302 321
404 700
494 623
195 787
668 224
442 368
43 466
236 310
131 502
163 271
695 776
206 656
842 241
9 783
728 433
776 598
125 392
355 308
271 640
64 236
510 666
224 261
135 63
357 672
600 396
207 68
276 675
796 551
55 109
454 656
259 345
822 237
424 588
355 224
453 690
561 267
50 548
817 767
190 331
692 773
536 360
835 796
556 222
545 778
716 198
233 88
509 432
840 121
239 214
394 747
141 568
116 760
139 684
276 600
774 56
265 203
601 174
403 236
684 380
424 197
194 433
63 577
486 325
612 175
636 676
405 448
787 571
412 717
505 335
302 241
639 442
336 546
156 49
800 390
337 248
765 567
292 441
421 192
636 58
740 575
583 214
450 316
503 324
834 240
671 742
253 135
737 502
81 594
579 534
628 501
171 197
292 590
431 143
289 562
437 278
723 290
570 222
733 610
460 286
140 522
533 307
304 344
796 748
330 585
440 66
303 187
531 745
659 284
707 246
694 716
626 581
118 826
49 737
438 208
383 27
528 443
231 112
343 162
321 188
410 496
425 526
631 98
667 137
354 91
756 494
634 633
803 696
359 149
408 338
203 418
453 658
682 620
95 561
642 665
197 219
750 35
623 411
90 211
181 56
48 699
164 605
95 29
530 99
771 163
339 262
403 125
464 585
717 836
221 255
101 676
203 566
516 815
611 799
59 586
312 749
24 439
741 417
93 537
435 330
625 735
241 782
632 650
271 538
276 176
528 614
247 638
568 242
247 797
210 603
745 633
714 111
617 733
582 601
346 190
604 750
700 206
648 731
49 561
449 436
40 562
756 749
724 762
250 625
514 466
330 487
28 617
51 69
295 357
297 412
451 103
535 422
205 831
811 371
166 243
795 340
336 166
568 593
584 363
217 313
402 84
489 202
548 69
285 55
274 268
163 381
639 67
54 735
323 458
138 329
94 550
        """.trimIndent()

        val expectedOutputString = """
            3
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val numNodes = input[0].split(" ").first().toInt()
        input.removeFirst()
        val m = parseGraphInput(numNodes, input)

        val expectedResult = expectedOutputString.toInt()

        // note that node numbers start at 1
        val result = rbfs.numberOfConnectedComponents(numNodes, m)

        //println(result)

        //assertEquals(expectedResult, result)
    }

}