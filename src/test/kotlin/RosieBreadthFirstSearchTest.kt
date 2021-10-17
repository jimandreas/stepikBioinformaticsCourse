@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.RosieBreadthFirstSearch
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.hashMapOf
import kotlin.collections.map
import kotlin.collections.mutableListOf
import kotlin.collections.removeFirst
import kotlin.collections.set
import kotlin.collections.toMutableList
import kotlin.test.assertEquals

/**
 *
 * Breadth First Search
 * rosalind: http://rosalind.info/problems/bfs/
 * Note: not part of the stepik cirriculum.
 */

internal class RosieBreadthFirstSearchTest {

    lateinit var rbfs : RosieBreadthFirstSearch

    @BeforeEach
    fun setUp() {
        rbfs = RosieBreadthFirstSearch()
    }

    @AfterEach
    fun tearDown() {
    }

    // example from:
    // hhttp://rosalind.info/problems/bfs/

    @Test
    @DisplayName("Rosalind Breadth First sample test")
    fun rosalindBreadthFirstSampleTest() {
        val sampleInput = """
            6 6
            4 6
            6 5
            4 3
            3 5
            2 1
            1 4
        """.trimIndent()

        val expectedOutputString = """
            0 -1 2 1 3 2
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val numNodes = input[0].split(" ").first().toInt()
        input.removeFirst()
        val m = parseGraphInput(numNodes, input)

        val expectedResultIntegerList =
            expectedOutputString.reader().readLines()[0].split(" ")
                .map { it.toInt() }

        // note that node numbers start at 1
        val result = rbfs.doSearches(numNodes, m)

        assertEquals(expectedResultIntegerList, result)
    }


    // example from:
    // hhttp://rosalind.info/problems/bfs/

    @Test
    @DisplayName("Rosalind Breadth First cyclic graph test")
    fun rosalindBreadthFirstCyclicGraphTest() {
        val sampleInput = """
            6 6
            4 6
            6 5
            4 3
            3 5
            3 1
            1 4
        """.trimIndent()

        val expectedOutputString = """
            0 -1 2 1 3 2
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val numNodes = input[0].split(" ").first().toInt()
        input.removeFirst()
        val m = parseGraphInput(numNodes, input)

        val expectedResultIntegerList =
            expectedOutputString.reader().readLines()[0].split(" ")
                .map { it.toInt() }

        // note that node numbers start at 1
        val result = rbfs.doSearches(numNodes, m)

        assertEquals(expectedResultIntegerList, result)
    }

    /**
     * take the list of edges (from to) and convert to a map of the base node to the list of connected
     * nodes.
     */
    private fun parseGraphInput(numNodes: Int, lines: List<String>): HashMap<Int, MutableList<Int>> {
        val map: HashMap<Int, MutableList<Int>> = hashMapOf()

        // make sure all nodes have a list
        for (i in 0 until numNodes+1) {
            map[i] = mutableListOf()
        }

        for (l in lines) {
            val p = l.split(" ").map { it.toInt() }
            map[p[0]]!!.add(p[1])
        }

        return map
    }

    // example from:
    // hhttp://rosalind.info/problems/bfs/

    @Test
    @DisplayName("Rosalind Breadth First cyclic quiz test")
    fun rosalindBreadthFirstQuizGraphTest() {
        val sampleInput = """
839 2874
506 568
716 782
33 629
96 536
407 169
182 1
728 420
133 579
44 469
525 557
177 252
681 700
403 504
190 415
562 348
736 663
735 380
278 152
120 700
725 310
191 51
800 408
508 488
715 192
181 437
208 654
319 618
675 741
218 238
643 396
492 78
166 732
174 187
552 737
622 640
58 807
592 435
81 36
559 716
121 304
68 806
342 333
700 457
403 187
646 478
8 94
264 40
525 381
546 25
785 205
168 418
276 493
78 49
769 206
338 281
356 200
356 517
655 675
180 600
236 837
807 702
383 13
424 445
482 536
671 181
594 700
15 108
185 659
367 569
208 265
373 119
452 55
663 418
755 694
596 740
381 104
734 227
33 838
11 390
503 794
678 81
147 59
537 726
517 477
187 534
757 494
832 118
446 448
671 178
450 584
90 149
809 23
368 146
740 44
282 708
250 237
329 679
412 574
682 101
576 736
313 510
759 113
545 368
730 361
272 22
276 808
557 30
188 711
445 122
818 756
375 178
213 142
450 720
436 761
453 549
682 753
185 498
424 598
479 549
72 432
746 815
468 489
325 416
1 679
2 330
577 20
55 756
122 782
583 655
801 748
549 70
74 38
478 90
115 229
794 456
339 515
156 512
606 455
593 5
208 464
651 222
107 451
493 647
243 246
500 719
763 668
360 442
693 408
233 519
198 531
826 59
39 520
616 140
122 777
141 519
199 127
474 81
826 170
646 556
564 538
746 389
429 303
712 269
659 262
646 831
771 545
95 79
353 420
188 725
411 4
716 380
238 573
78 625
706 166
286 803
202 522
220 725
354 159
69 232
579 262
1 685
818 179
838 570
200 409
449 827
780 742
86 302
275 139
72 30
255 790
309 735
832 414
405 236
131 757
337 238
255 638
449 121
231 67
551 623
560 130
136 223
193 648
325 411
372 204
737 75
627 788
591 826
44 744
245 724
100 700
391 32
569 293
71 493
491 572
274 529
518 739
78 417
650 110
230 781
194 162
361 517
380 153
290 196
92 659
493 150
115 105
777 308
727 779
506 822
488 298
696 184
131 838
762 528
727 760
158 685
822 25
695 261
22 820
117 636
298 166
322 288
624 608
89 833
177 429
576 136
82 10
446 140
357 132
105 833
142 645
76 748
700 105
629 445
298 472
155 522
163 401
715 684
37 125
330 386
645 807
65 364
429 78
430 420
808 735
271 193
407 635
747 691
516 713
193 293
522 71
781 389
655 801
776 592
210 788
266 514
447 412
621 652
803 396
490 737
201 660
3 353
264 478
214 488
548 472
309 529
455 93
538 428
710 808
61 806
408 382
616 632
338 3
301 141
608 102
621 430
696 710
414 706
805 525
815 118
614 578
831 216
392 722
671 84
304 14
196 116
473 380
456 260
494 757
9 693
215 169
437 159
100 705
361 282
495 835
47 427
105 336
251 145
363 443
491 213
652 725
616 384
448 757
334 604
771 180
20 382
79 422
264 808
797 66
508 826
686 671
586 566
251 817
555 16
41 109
666 53
64 136
423 800
616 2
545 26
468 703
272 705
659 217
742 507
181 170
749 694
255 61
661 282
439 785
776 779
615 781
622 193
474 324
628 680
98 772
389 5
514 201
240 552
205 327
427 512
636 84
67 124
328 656
402 84
367 209
740 490
352 31
603 463
620 545
429 638
665 672
338 306
818 437
485 439
269 800
587 331
274 301
148 140
539 347
242 395
368 123
530 122
197 601
552 19
165 410
419 595
807 503
350 467
407 461
173 21
191 769
602 664
701 425
641 199
813 453
69 719
352 258
637 370
605 702
56 240
353 485
81 152
206 634
193 156
285 676
471 594
312 404
166 818
445 625
834 415
449 525
394 723
604 342
569 748
804 176
272 353
793 590
115 771
425 255
63 547
797 260
25 218
327 221
74 107
55 586
334 804
578 94
502 467
106 792
70 156
630 673
280 597
447 422
824 583
150 404
146 408
601 596
94 55
668 309
523 337
547 262
123 581
366 189
370 631
116 119
335 791
191 50
525 782
320 413
54 624
255 191
340 341
395 499
324 427
53 174
109 833
768 152
628 121
369 73
573 712
835 372
671 456
585 700
674 810
711 36
694 100
159 822
257 359
131 48
78 201
238 134
282 172
407 562
393 314
158 748
144 266
648 747
422 746
498 529
319 443
291 174
452 88
290 773
607 704
80 433
354 176
411 756
280 606
255 272
420 827
478 586
390 45
679 579
559 724
248 70
264 207
455 37
39 159
374 593
122 281
260 406
568 691
132 402
427 771
157 438
30 188
625 204
370 331
116 720
49 59
395 382
90 439
36 786
292 356
718 391
535 215
131 62
424 52
23 500
80 726
506 779
456 613
111 730
93 827
593 242
650 255
132 71
173 312
155 567
249 50
40 556
513 366
474 642
799 829
168 541
426 545
597 817
747 108
14 470
49 795
42 593
389 135
746 812
339 142
358 820
752 571
624 320
53 298
296 239
662 573
771 720
421 321
612 31
795 92
634 236
155 374
154 754
269 450
162 284
405 825
536 454
772 495
572 22
620 94
41 119
437 498
768 323
759 836
408 638
61 412
145 27
191 167
412 381
514 725
198 330
292 208
256 785
810 661
579 541
485 599
624 327
734 443
129 341
55 670
352 725
49 556
171 574
9 487
407 136
740 1
87 41
732 290
571 177
771 476
193 4
810 33
264 688
229 718
203 456
149 705
331 546
336 669
787 43
598 179
394 398
108 401
53 69
729 177
98 299
537 345
833 296
445 759
636 155
231 156
358 620
108 284
695 515
739 130
334 16
104 54
251 825
124 41
457 48
222 447
765 229
387 499
826 404
683 5
773 838
300 748
724 638
489 240
625 377
661 139
680 310
222 279
24 239
244 800
562 351
63 54
270 771
734 825
110 119
241 689
644 747
577 213
389 106
230 294
291 619
543 768
95 205
78 576
779 782
744 124
452 574
548 427
273 165
248 774
231 146
242 626
424 337
722 830
173 277
339 85
799 60
105 269
802 445
179 457
271 34
3 234
745 234
218 528
770 264
831 30
106 495
100 732
471 509
239 458
831 694
523 148
836 792
230 59
33 384
786 139
30 379
663 254
385 515
48 335
50 544
13 421
735 415
433 666
274 454
69 321
481 266
303 620
514 688
515 761
432 182
479 526
338 113
11 224
31 603
178 189
766 564
357 611
80 338
46 305
505 478
694 365
453 812
568 826
610 815
232 402
84 530
303 644
234 669
217 273
499 818
794 795
485 81
532 139
112 73
39 389
816 500
817 336
124 358
622 587
769 600
86 303
835 116
658 432
14 794
421 737
115 266
833 587
580 702
501 344
193 31
485 388
17 139
771 82
499 366
741 211
742 745
69 501
620 497
383 149
365 394
55 775
124 398
98 7
722 512
284 43
624 574
695 727
149 542
24 750
764 772
723 44
580 483
28 32
377 53
46 450
696 10
132 457
244 504
615 780
229 364
820 427
288 268
270 347
569 363
316 685
479 163
30 500
835 155
837 163
251 650
31 141
376 285
293 316
719 623
703 810
753 683
296 383
666 354
27 804
449 135
275 72
149 180
649 705
525 110
504 38
251 530
739 435
575 66
118 491
301 95
417 85
509 711
436 593
504 260
638 171
290 131
754 767
828 90
519 374
371 651
483 325
319 614
107 574
808 22
826 477
510 158
154 380
318 17
525 103
551 566
682 252
292 683
795 567
78 689
63 733
815 119
229 523
375 29
199 754
554 434
648 766
663 366
187 145
741 21
579 797
653 574
126 824
272 497
24 370
754 384
750 603
164 791
491 833
553 222
39 540
244 722
461 116
351 725
306 470
751 802
735 489
210 561
192 401
447 635
526 449
711 368
821 208
52 584
695 332
39 232
24 191
408 756
484 508
527 98
459 444
291 714
384 775
578 800
371 665
655 407
836 248
656 270
824 736
641 510
474 146
525 409
765 746
463 70
400 790
811 398
562 158
343 254
588 244
664 626
294 422
446 310
116 453
657 504
159 205
292 85
772 386
439 390
404 164
315 323
124 772
266 305
235 165
737 366
60 726
540 680
562 765
190 650
818 156
130 772
481 419
85 400
395 639
481 408
269 549
308 395
132 246
776 306
613 640
264 781
564 527
781 63
696 160
239 376
370 505
388 805
321 334
413 736
432 156
509 560
133 506
725 749
302 662
88 83
86 482
700 829
682 208
689 130
9 605
391 62
582 664
146 21
611 347
774 517
370 42
836 838
216 700
472 550
365 46
392 335
238 582
21 745
527 238
509 336
398 822
516 775
3 281
497 6
795 174
619 41
732 437
567 482
455 750
354 376
139 531
546 177
303 305
322 647
458 266
573 94
685 207
374 238
412 463
72 18
91 648
258 46
332 233
780 820
638 399
463 257
625 645
330 518
149 808
711 444
270 585
548 496
661 225
610 250
205 705
68 723
805 25
799 612
344 124
548 113
454 387
554 462
521 414
831 185
233 748
515 527
143 342
826 283
332 603
480 302
522 589
604 48
427 316
68 109
270 282
693 680
6 12
511 468
98 110
138 127
638 257
143 441
538 3
760 143
78 742
727 178
268 491
343 801
804 309
327 61
132 61
395 112
117 84
7 469
248 285
569 403
823 87
769 772
129 562
98 246
302 621
444 283
265 82
159 650
274 160
150 534
583 50
272 736
810 474
794 432
601 183
136 741
451 335
676 487
438 491
268 548
306 713
92 218
148 687
512 432
460 35
595 744
83 543
121 139
237 248
285 136
754 452
59 205
519 452
727 762
671 153
233 338
825 809
313 432
365 494
417 47
271 363
554 117
24 258
646 429
800 616
564 46
69 648
179 19
363 359
164 41
538 378
760 454
336 643
572 533
178 816
17 547
397 18
609 362
691 251
479 353
480 674
502 217
397 728
272 265
458 781
459 476
313 441
204 627
667 77
564 585
480 71
485 256
140 784
161 800
329 627
455 578
637 339
322 424
80 53
705 476
137 639
86 416
604 51
623 635
637 799
3 355
260 773
530 474
678 247
594 392
13 76
324 60
471 362
772 434
132 452
439 712
214 691
11 58
609 250
438 479
296 282
412 797
234 813
698 534
826 425
810 303
351 502
8 296
192 517
89 221
157 675
787 723
511 764
447 787
90 617
72 212
821 734
73 5
654 811
489 13
252 575
680 418
774 779
314 224
142 783
80 645
823 287
599 798
715 415
646 544
207 732
433 695
604 807
733 581
314 719
137 263
760 40
688 330
82 628
160 787
124 92
343 584
698 226
486 114
303 634
667 251
17 82
291 527
626 13
37 31
421 432
80 766
83 689
63 304
302 141
819 728
828 84
364 318
78 727
366 217
762 89
201 649
643 192
55 630
342 614
657 449
564 9
378 36
33 28
311 568
593 527
151 740
8 334
285 681
327 487
511 813
262 440
83 694
123 585
566 384
325 360
18 234
219 416
22 791
207 674
281 210
154 832
211 218
46 775
675 619
715 282
91 593
129 6
651 734
557 393
280 331
83 404
411 413
158 364
364 475
424 630
386 213
78 519
183 167
139 311
128 516
373 468
164 516
430 464
451 374
735 368
684 229
299 724
366 109
449 700
837 513
824 416
713 536
733 592
5 191
587 579
141 720
62 824
491 832
184 53
187 459
540 398
563 788
821 537
348 126
370 175
210 558
484 752
729 250
415 822
587 244
553 593
703 287
529 469
606 35
795 124
184 379
388 397
506 772
34 175
437 462
136 333
250 544
162 75
825 761
560 98
317 344
542 190
100 661
737 728
362 529
153 498
281 165
726 528
503 290
694 339
629 459
483 676
728 383
601 247
253 216
436 408
49 533
837 143
214 423
646 325
615 14
323 662
125 286
586 784
91 89
598 41
424 649
296 606
535 230
227 531
798 389
138 759
472 544
471 231
326 229
127 577
222 449
99 780
574 165
706 402
333 729
86 538
819 356
810 115
287 785
534 130
295 266
464 238
7 150
544 104
620 323
606 250
678 167
274 665
763 380
578 414
724 353
290 397
581 115
68 791
272 640
285 496
623 809
310 395
345 479
184 282
371 623
427 193
654 117
692 168
308 689
826 76
94 589
576 47
693 87
549 269
321 388
353 137
636 135
601 483
658 353
821 750
127 171
20 684
419 716
355 129
466 161
272 469
807 138
538 359
408 29
145 16
266 610
729 61
610 735
534 22
795 627
667 806
634 319
267 750
92 423
167 783
332 456
116 45
209 72
341 571
487 646
489 803
588 678
579 403
228 67
839 424
54 451
739 236
718 588
119 700
360 82
432 727
608 43
84 589
58 651
150 235
480 670
63 480
33 258
414 129
788 601
634 170
668 361
744 458
338 26
530 701
766 605
508 91
437 299
457 421
114 254
22 223
388 448
317 486
134 740
818 483
486 381
734 122
773 376
223 305
223 135
317 87
105 95
30 804
551 616
1 449
821 686
313 752
775 661
280 125
738 793
620 423
353 135
711 807
271 252
717 796
409 594
284 121
103 674
217 599
192 141
636 342
89 571
164 406
827 580
80 17
287 754
473 415
165 12
349 490
704 588
835 668
513 423
73 504
133 109
735 586
150 665
814 266
444 625
78 218
785 587
146 113
148 704
738 32
668 238
227 102
533 723
236 770
522 742
186 596
172 615
87 835
435 601
60 506
523 57
790 411
684 12
473 41
821 715
384 227
352 354
556 547
463 539
529 583
640 590
423 275
79 102
135 415
364 216
456 367
383 225
516 413
702 338
834 447
28 727
359 376
309 457
3 499
11 537
84 750
217 29
97 365
722 814
209 318
671 538
157 54
362 795
117 360
385 651
498 788
621 237
451 524
206 229
289 343
309 417
402 411
3 363
521 156
106 124
636 526
352 773
182 682
437 13
413 237
632 307
226 166
484 593
107 105
696 5
507 691
286 648
57 26
779 533
63 84
11 505
451 187
749 587
649 725
383 132
29 245
529 308
681 241
573 366
75 116
796 198
119 114
168 107
393 541
637 55
450 131
468 230
471 204
322 426
77 341
244 506
762 789
746 465
127 421
473 774
6 665
578 310
759 160
669 109
724 563
162 808
107 396
69 169
487 377
471 387
642 621
310 466
425 837
102 16
671 266
305 242
144 550
181 431
199 64
759 733
547 594
335 15
48 737
567 111
117 394
311 230
66 506
573 698
832 636
297 701
816 411
566 311
137 87
433 132
647 576
282 609
781 590
799 115
692 95
654 658
567 520
398 166
112 310
130 397
793 763
467 52
503 818
382 161
292 137
258 489
28 214
174 173
414 522
201 702
717 264
667 395
78 695
355 603
238 330
490 145
312 131
6 510
600 67
546 3
692 38
306 242
254 260
127 200
475 835
539 445
246 514
743 320
229 389
467 390
361 21
70 801
769 657
185 474
386 173
675 369
32 733
732 467
12 142
329 378
457 832
521 161
498 505
81 39
155 824
445 137
355 426
598 815
31 559
495 655
252 373
309 580
534 7
826 708
186 298
48 341
728 63
268 533
568 680
810 557
463 1
598 147
701 113
505 379
509 323
454 251
368 120
630 703
668 69
119 190
784 484
736 253
489 117
818 692
497 85
410 778
748 605
732 679
737 130
666 142
561 476
588 710
19 322
111 253
442 420
190 825
46 727
160 642
657 399
1 86
39 449
5 652
405 759
85 712
235 308
19 544
613 730
702 146
560 114
689 708
7 809
389 766
49 40
324 691
777 582
98 426
449 330
319 429
376 674
767 404
566 379
496 753
468 533
417 763
756 391
663 757
626 705
544 676
564 754
378 262
359 247
440 819
718 127
578 415
410 419
414 753
436 329
93 824
8 112
245 621
584 462
31 725
238 580
539 409
44 115
527 232
33 201
568 503
579 599
492 797
114 577
62 481
598 279
431 180
787 17
310 272
441 26
813 391
45 594
623 817
595 807
349 786
686 222
547 715
87 130
452 176
4 694
355 273
433 591
451 168
34 176
14 529
643 492
442 759
422 508
372 414
769 263
415 728
821 581
718 803
575 576
591 545
304 787
476 510
615 451
583 640
419 756
367 351
111 167
4 432
557 326
504 717
543 669
217 764
533 711
662 789
665 650
813 780
751 469
248 722
194 530
174 115
568 570
594 19
744 113
137 101
429 95
442 609
385 677
228 305
362 501
727 130
12 741
592 212
386 527
439 827
775 264
707 110
601 759
147 666
189 608
171 157
545 392
447 365
255 318
419 113
772 254
738 369
320 213
746 772
285 830
405 525
221 214
339 688
261 687
389 437
193 829
621 628
307 724
550 270
638 80
648 205
665 817
408 551
118 347
28 572
17 482
695 558
510 383
682 468
481 765
811 713
675 696
655 315
624 272
257 314
813 346
281 428
264 468
428 325
733 254
640 298
53 267
630 543
121 376
408 703
742 681
528 656
18 37
583 483
368 418
439 777
24 167
64 233
64 517
211 163
768 200
22 532
46 482
118 694
121 709
270 237
112 507
369 601
27 456
794 146
730 716
23 228
491 74
73 571
270 124
168 769
153 56
829 498
800 364
587 632
180 348
220 480
276 676
817 114
12 123
454 171
454 380
690 6
621 211
34 239
362 672
401 379
666 47
83 211
391 833
197 754
218 649
827 557
419 520
573 1
98 483
530 8
265 146
105 339
674 55
694 714
277 437
758 182
244 501
281 728
122 163
690 772
603 109
18 477
825 27
608 389
77 307
793 194
192 549
705 96
374 574
437 782
235 785
240 105
410 266
523 54
335 764
301 113
433 502
179 259
179 246
28 72
477 570
369 784
103 314
803 663
436 257
283 440
317 2
290 592
488 806
40 462
45 270
273 119
542 371
699 330
665 385
651 569
323 718
272 368
594 121
422 156
598 5
497 707
126 479
399 201
700 621
20 146
455 358
578 113
561 15
27 800
376 382
583 303
435 519
679 792
95 154
280 580
723 216
523 410
414 537
399 837
404 822
259 600
561 794
685 364
686 496
744 535
489 195
255 538
112 161
157 791
336 570
35 389
715 359
813 153
594 258
799 762
484 337
674 599
660 444
273 409
555 245
403 384
467 602
557 84
776 624
816 82
619 525
712 590
620 756
566 279
752 170
823 330
533 27
475 504
256 610
31 260
105 190
30 560
200 326
716 23
497 335
618 171
375 26
675 621
584 609
388 103
772 782
222 828
337 153
743 330
30 281
409 593
770 263
312 94
735 742
115 92
335 244
686 836
268 326
453 153
221 290
39 426
131 669
461 702
671 799
471 751
343 702
291 451
240 351
628 344
612 207
261 743
30 398
546 599
514 396
360 538
572 807
330 722
770 636
64 385
780 463
673 131
675 739
158 341
645 25
681 278
277 648
38 745
488 732
583 92
264 356
348 563
628 304
221 230
54 322
199 330
335 729
229 561
657 107
17 91
641 721
398 6
636 236
14 651
435 63
53 530
765 374
216 142
36 483
662 299
25 483
689 772
392 537
384 600
491 389
717 313
700 343
475 633
136 137
505 33
362 506
429 724
604 715
741 585
460 783
30 483
52 351
552 213
254 669
19 760
494 236
703 407
739 650
294 344
70 147
771 417
542 161
503 578
804 90
808 755
588 804
19 108
628 262
517 352
500 452
65 254
323 664
209 827
738 131
250 225
445 691
93 371
663 374
372 540
124 1
70 77
759 363
141 492
776 79
170 290
725 633
554 21
512 687
345 325
76 550
114 583
501 133
393 772
488 134
490 99
589 725
455 803
69 338
683 8
169 766
648 210
259 134
704 324
371 704
200 221
483 282
249 617
499 839
260 175
353 539
541 298
190 808
708 411
262 64
710 569
552 679
149 812
758 725
569 676
399 692
127 787
684 51
380 512
795 80
381 359
269 510
142 425
734 71
594 682
39 724
459 615
780 283
51 186
108 441
654 209
491 150
792 600
153 372
430 290
361 678
57 744
682 741
4 838
410 328
151 220
409 485
190 105
266 501
12 211
808 257
452 343
330 660
402 466
719 691
353 128
315 585
15 247
180 200
132 49
618 248
653 28
671 461
820 454
325 544
697 194
642 709
240 248
191 222
115 83
832 65
360 279
698 630
221 821
274 369
517 307
579 380
28 256
43 649
456 61
789 628
521 206
157 628
301 623
360 53
821 771
346 677
310 402
590 143
41 337
134 145
140 536
66 379
194 164
104 399
33 575
152 473
164 38
510 373
517 427
258 63
703 558
117 663
808 787
675 201
416 650
550 45
553 351
534 235
661 74
65 802
86 663
199 219
312 336
406 595
796 788
64 837
175 329
802 79
330 162
591 635
425 654
768 614
380 745
243 793
705 553
631 26
745 658
174 713
12 105
693 589
839 628
398 476
398 785
3 374
521 367
830 483
98 680
700 256
389 690
411 134
231 313
434 464
622 698
366 597
58 266
441 386
632 288
339 507
281 233
751 724
535 523
130 469
115 657
295 504
98 321
463 540
209 661
122 331
123 603
244 279
635 468
51 592
427 277
256 71
301 478
172 752
327 585
308 525
226 146
122 483
468 798
752 796
771 60
257 105
701 504
761 597
298 317
580 692
88 818
801 450
587 497
806 93
661 379
510 803
277 284
443 601
137 55
231 560
444 826
164 277
386 827
353 308
462 139
500 567
193 291
733 20
833 709
282 574
100 793
220 504
720 305
494 60
386 93
152 317
774 205
706 530
301 334
801 475
87 738
98 44
276 425
379 422
34 285
486 494
363 656
644 457
476 542
704 595
669 479
619 509
196 346
658 691
492 331
681 722
97 267
616 175
708 138
139 234
752 778
20 148
721 1
212 670
154 40
597 772
193 582
39 41
123 624
577 133
358 732
162 638
472 453
551 519
789 397
440 697
647 112
425 817
108 739
240 299
59 404
520 130
684 251
567 574
355 34
610 737
776 471
551 118
595 234
23 621
140 535
102 563
287 253
108 385
202 263
425 646
349 84
81 734
742 222
444 723
1 317
630 704
742 356
66 572
381 287
793 583
184 257
150 690
392 434
780 144
450 152
270 834
552 250
455 547
591 517
209 295
17 447
734 348
106 814
40 445
646 205
480 164
557 542
638 735
725 7
323 558
110 299
608 672
401 358
761 792
562 676
177 536
397 754
70 408
76 713
244 256
421 834
189 709
703 234
781 23
674 51
508 464
609 443
704 401
151 483
787 385
474 339
398 648
721 133
333 172
373 288
290 496
241 190
696 378
515 734
206 320
762 7
10 96
156 652
657 558
454 425
440 317
833 38
191 48
358 414
512 401
142 52
258 465
649 280
192 247
836 253
328 279
551 300
5 205
311 599
49 419
736 355
310 266
292 734
376 114
125 767
589 698
399 191
337 690
558 399
389 501
447 756
588 113
801 256
234 604
1 639
756 12
269 187
703 35
807 548
330 455
349 297
574 361
426 774
134 32
523 534
600 281
376 558
68 343
51 313
53 331
739 721
596 731
193 88
30 480
548 168
254 201
179 448
666 322
479 684
600 567
122 464
270 258
454 559
92 147
438 387
791 222
814 37
413 223
217 364
183 579
777 63
672 264
411 348
747 147
379 342
830 48
201 800
272 784
789 346
439 209
238 307
426 142
708 270
304 567
505 410
398 128
238 14
368 25
595 715
355 814
223 103
530 195
677 741
201 719
39 392
699 178
221 95
189 768
650 274
632 185
660 772
20 561
331 784
198 426
677 453
41 381
834 761
639 693
52 302
257 419
276 505
171 741
264 171
47 629
545 814
196 176
270 268
402 329
756 581
478 228
300 406
447 449
605 242
146 505
667 108
333 613
669 566
584 573
141 520
694 550
93 324
612 454
45 172
129 620
324 562
552 203
272 456
510 328
368 23
497 543
481 744
28 576
359 828
69 545
271 655
277 692
345 115
812 686
512 246
501 150
47 30
654 242
124 246
749 336
418 192
59 734
336 628
45 585
181 332
9 627
105 140
767 316
218 494
149 449
225 827
634 99
320 306
428 632
325 559
641 422
346 420
603 99
571 37
538 806
777 268
373 366
488 664
285 765
162 585
133 82
810 332
393 830
320 615
802 194
714 800
490 93
678 207
330 668
528 513
501 469
637 603
14 55
485 684
490 275
787 142
728 401
477 55
615 753
323 347
252 83
805 286
716 181
22 77
297 15
208 606
779 578
180 103
34 801
687 592
263 724
253 711
269 749
438 172
593 267
805 524
245 777
376 163
837 506
17 343
324 754
358 208
533 727
27 538
496 65
4 250
717 247
546 271
235 371
490 745
494 703
65 826
393 808
429 79
103 583
567 723
668 653
355 666
144 710
307 329
515 355
598 271
259 234
317 501
392 383
339 549
605 505
110 3
292 98
794 236
116 656
270 702
570 829
94 66
44 837
192 421
836 67
838 347
688 757
839 765
207 414
721 562
352 257
351 29
172 494
106 102
792 417
500 786
636 359
93 683
250 396
639 234
235 653
35 376
453 612
5 230
79 99
812 839
373 791
355 308
268 792
285 150
52 135
733 482
20 627
55 780
337 100
233 320
715 552
39 280
497 417
632 740
743 772
425 507
821 721
382 498
475 188
695 249
836 710
48 767
255 816
273 97
60 367
206 608
482 610
763 168
122 16
433 411
839 23
435 245
757 791
494 287
498 473
298 10
706 328
282 317
174 369
801 548
573 295
202 59
658 561
738 659
100 500
287 358
697 305
653 561
442 832
629 663
166 743
502 595
361 747
654 815
170 738
206 619
617 666
184 442
228 748
708 568
321 598
709 418
152 260
744 200
552 527
519 803
205 652
538 558
675 118
136 652
28 490
47 446
801 379
768 39
700 623
679 645
237 635
540 333
360 175
184 775
760 486
328 726
616 778
234 334
555 440
96 716
479 608
390 211
232 750
384 339
827 249
594 804
616 595
672 325
641 381
790 736
311 370
810 575
585 122
290 660
320 693
605 703
143 223
77 220
776 626
689 791
816 665
84 370
654 2
583 104
418 743
609 643
187 635
762 438
565 744
470 691
31 243
336 563
        """.trimIndent()

        val expectedOutputString = """
            0 -1 2 1 3 2
        """.trimIndent()

        val input = sampleInput.reader().readLines().toMutableList()
        val numNodes = input[0].split(" ").first().toInt()
        input.removeFirst()
        val m = parseGraphInput(numNodes, input)

        val expectedResultIntegerList =
            expectedOutputString.reader().readLines()[0].split(" ")
                .map { it.toInt() }

        // note that node numbers start at 1
        val result = rbfs.doSearches(numNodes, m)

        println(result.joinToString(" "))

       //assertEquals(expectedResultIntegerList, result)
    }


}