# Bioinformatics Algorithms: An Active Learning Approach

~~https://stepik.org/course/55789/syllabus~~ *(course no longer available — stepik.org now returns 404 for this course)*

http://rosalind.info/problems/list-view/?location=bioinformatics-textbook-track

## Notes

Notes are available at:

https://jimandreas.github.io/stepikBioinformaticsCourse/

### Reflexivity

In examining a solution to the [Linear Space Alignment] problem the question 
of reflexivity came up.  The solution to the [Middle Edge in Linear Space Problem]
suggested using a reverse traversal of the scoring
matrix and to meet at a middle edge.   The question is:
 
*Is this always the same path?*

The answer is no.  Examining random strings and doing trials using the 
[Global Alignment] algorithm with forward and backwards strings, 
the score is 100% the same in both directions.  However, the alignment 
strings in both column and row solutions were equal less than 
50% of the time.

As an experiment - I changed the priority of the backtrack assignment direction
when the reverse solution did not match the forward solution.   The 
priority change moved the Match/Mismatch assignment from first to last
place in the decision tree.   On a condition that the first
match failed, this resulted in about an 80% recovery of the failed
solutions.  Here are the results from 10K trials of random amino acid strings:

    score 10000 row 4834 col 4573 equivalence in 10000 tries
    after priority change the following *matched*:  
    row 4624 of attempts 5166  col 4853 of attempts 5427

## Building and running

Note: the problems in this repo were solved using the Kotlin language.
The repo is configured for the Intellij IDEA system and Gradle.
Gradle can be used to build it

`./gradlew build test`

All exercises contain a corresponding "Jupiter" (junit) test module located 
in the `src/test/kotlin` folder.  

The repo is organized under src into `algorithms` and `problems` folders.
The `problems` folders only contain entries where the test solution
required a very large amount of data.

Most of the `algorithms` are exercised by test files located in the 
test folder.  The tests names match the sequencing found in
the stepik course.

## Branches

* Switched branch to "main"

    October 1, 2020
    All new Git repositories on GitHub will be named "main" instead of "master" starting October 1, 2020.

* gh-pages

This branch contains the compiled documentation in the `docs` folder.  It is compiled using 
the `mkdocs` and `mkdocs-material` facilities.

## Dependency notes

### multik (multidimensional array library)

`multik-api` and `multik-default` are pinned at **0.1.1**. Versions 0.2.x introduce an
off-by-one bug in `NDArrayIterator.next()` that causes `ArrayIndexOutOfBoundsException`
at runtime (e.g. `Index N out of bounds for length N`) in `SmallParsimony` and the
Hidden Markov Model profile alignment code. Do not upgrade multik until this is fixed
upstream.

## Other notes

TODO: The following problems need work:

* ShortestSuperstring, aka Introduction to Genome Sequencing (http://rosalind.info/problems/long/)

## Acknowledgements

* The Genetic Codes

The genetic code data was sourced from the NCBI at the following link:

https://www.ncbi.nlm.nih.gov/Taxonomy/Utils/wprintgc.cgi


[Linear Space Alignment]: http://rosalind.info/problems/ba5l/
[Global Alignment]: http://rosalind.info/problems/ba5e/
[Rosalind]: http://rosalind.info/faq/
[Middle Edge in Linear Space Problem]: http://rosalind.info/problems/ba5k/
[Bioinformatics Algorithms]: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
[Jim Andreas]: https://www.jimandreas.com

/* updated token */