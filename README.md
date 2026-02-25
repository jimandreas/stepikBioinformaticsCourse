# Bioinformatics Algorithms: An Active Learning Approach

Kotlin implementations of bioinformatics algorithms from the
[Bioinformatics Algorithms] textbook/course and
[Rosalind](http://rosalind.info/problems/list-view/?location=bioinformatics-textbook-track)
bioinformatics problems.

> **Note:** The original Stepik course (course #55789) is no longer available.
> `stepik.org/course/55789/syllabus` returns 404. The site has become a
> Russian-language e-learning platform and the bioinformatics content has been removed.

## Building and Running

The project uses Kotlin and Gradle (Kotlin DSL), targeting JVM 17.

```bash
./gradlew build          # compile
./gradlew test           # run all 522 tests
./gradlew test --tests "S01c02p06PatternCountTest"  # run a single test class
```

All exercises have a corresponding JUnit 5 test in `src/test/kotlin/`. Test class names
follow the Stepik course numbering convention — e.g. `S05c10p03AlignmentGlobalTest` maps
to Section 5, Chapter 10, Problem 3.

## Repository Structure

```
src/
  main/kotlin/
    algorithms/   # core algorithm implementations (88 files)
    problems/     # problem-specific solutions requiring large input data
    tables/       # genetic code tables (27 NCBI codon tables)
    ResourceReader.kt
  main/resources/ # BLOSUM62, PAM250 scoring matrices and data files
  test/kotlin/    # JUnit 5 tests (118 files)
```

## Algorithm Notes

### Reflexivity in Linear Space Alignment

In examining a solution to the [Linear Space Alignment] problem the question
of reflexivity came up. The solution to the [Middle Edge in Linear Space Problem]
suggested using a reverse traversal of the scoring matrix and meeting at a middle
edge. The question:

*Is the reverse path always the same as the forward path?*

The answer is no. Examining random strings using the [Global Alignment] algorithm
with forward and backwards strings, the score is 100% identical in both directions.
However, the alignment strings themselves matched in less than 50% of trials.

As an experiment, changing the priority of the backtrack assignment direction
(moving Match/Mismatch from first to last in the decision tree) recovered about
80% of the non-matching cases. Results from 10K trials of random amino acid strings:

```
score 10000 row 4834 col 4573 equivalence in 10000 tries
after priority change the following matched:
row 4624 of attempts 5166  col 4853 of attempts 5427
```

## Dependency Notes

### Current versions (as of February 2026)

| Dependency | Version |
|---|---|
| Kotlin | 2.3.10 |
| JUnit Jupiter | 5.13.4 |
| OkHttp | 5.3.0 |
| kotlin-math | 1.7.0 |
| multik-api / multik-default | 0.1.1 (pinned — see below) |

### multik pinned at 0.1.1

`multik-api` and `multik-default` are pinned at **0.1.1**. Versions 0.2.x introduced
an off-by-one bug in `NDArrayIterator.next()` that causes `ArrayIndexOutOfBoundsException`
at runtime (e.g. `Index N out of bounds for length N`) in `SmallParsimony` and the
Hidden Markov Model profile alignment code. Do not upgrade multik until this is fixed
upstream.

## Known Issues

- **ShortestSuperstring** (Introduction to Genome Sequencing):
  [rosalind.info/problems/long/](http://rosalind.info/problems/long/) — needs work.

## Branches

- **main** — active development
- **gh-pages** — compiled documentation in `docs/`, built with `mkdocs` and `mkdocs-material`

## AI Assistance

This repository has been maintained with assistance from
[Claude Code](https://claude.ai/code) (Anthropic). Contributions include:

- **Dependency updates (February 2026):** Updated Kotlin to 2.3.10, OkHttp to 5.3.0,
  JUnit to 5.13.4, kotlin-math to 1.7.0, and kotlin-test to 2.3.10.
- **multik bug discovery:** Identified that multik 0.2.x has an off-by-one bug in
  `NDArrayIterator.next()` causing `ArrayIndexOutOfBoundsException` in `SmallParsimony`
  and HMM profile alignment. Pinned multik at 0.1.1 pending an upstream fix.
- **Dead link audit:** Confirmed that the Stepik course link (course #55789) is no
  longer accessible and updated documentation accordingly.

## Acknowledgements

- **Genetic code tables:** Sourced from the NCBI at
  https://www.ncbi.nlm.nih.gov/Taxonomy/Utils/wprintgc.cgi

[Linear Space Alignment]: http://rosalind.info/problems/ba5l/
[Global Alignment]: http://rosalind.info/problems/ba5e/
[Rosalind]: http://rosalind.info/faq/
[Middle Edge in Linear Space Problem]: http://rosalind.info/problems/ba5k/
[Bioinformatics Algorithms]: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
[Jim Andreas]: https://www.jimandreas.com
