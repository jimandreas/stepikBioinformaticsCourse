# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kotlin implementations of bioinformatics algorithms from the Stepik course "Bioinformatics Algorithms" and Rosalind bioinformatics problems. Uses Gradle (Kotlin DSL) with JVM 17.

## Build & Test Commands

```bash
./gradlew build          # Build the project
./gradlew test           # Run all tests
./gradlew test --tests "S01c02p06PatternCountTest"  # Run a single test class
```

## Architecture

- **`src/main/kotlin/algorithms/`** — Core algorithm implementations (alignment, pattern matching, graph algorithms, clustering, motif finding, etc.). Most are standalone classes or functions.
- **`src/main/kotlin/problems/`** — Problem-specific solutions tied to Stepik course structure. Naming convention: `S01c02p06` = Section 1, Chapter 2, Problem 6.
- **`src/main/kotlin/tables/`** — Genetic code tables (27 NCBI codon tables) in `CodonHashMaps.kt` and utilities.
- **`src/main/kotlin/ResourceReader.kt`** — Loads scoring matrices and data files from resources.
- **`src/main/resources/`** — Data files including BLOSUM62 and PAM250 scoring matrices.
- **`src/test/kotlin/`** — JUnit 5 tests. Test files mirror the problem naming (e.g., `S01c02p06PatternCountTest.kt`).

## Key Patterns

- Alignment algorithms (Global, Local, Fitting, Overlap, Affine Gap) use dynamic programming with 2D matrices and backtracking.
- Algorithms support both simple match/mismatch scoring and matrix-based scoring (BLOSUM62/PAM250).
- Tests use JUnit 5 (`@Test`, `@DisplayName`).

## Dependencies

- Kotlin 2.0.21, kotlinx-multik (matrix ops), OkHttp, kotlin-math
