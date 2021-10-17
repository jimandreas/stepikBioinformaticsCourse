# Notes on various problems

Some topics in the cirriculum presented challenges in understanding and 
in algorithm development.   My notes on these topics are below. 

## Minimum Parsimony

This is a fully worked out solution of 
[The Small Parsimony Problem][SMP] as given in the stepik.org 
[Bioinformatics Algorithms: An Active Learning Approach][BioInfoClass] section 7.9.

Here the algorithm is examining one vertical slice of a set of {ACGT} codons
in a set of DNA strings.  The vertical slice is (expressed horizontally):

    C C A C G G T C

here is another try:

[![Minimal Tree figure 1][20]][20]

[20]: assets/SmallParsimony1.png

Minimal Mutation Trees of Sequences
-----------

[SIAM J. Appl. Math., 28(1), 35–42.][SIAM Sankoff] (8 pages)

    Publisher: Society for Industrial and Applied Mathematics
    Author: David Sankoff
    https://doi.org/10.1137/0128004

*Abstract:*

Given a finite tree, some of whose vertices are identified with given finite sequences, 
we show how to construct sequences for all the remaining vertices simultaneously, 
so as to minimize the total edge-length of the tree. 
Edge-length is calculated by a metric whose biological significance is the mutational distance between two sequences.

[SMP]: https://stepik.org/lesson/240342/step/5?unit=212688
[BioInfoClass]: https://stepik.org/course/55789/syllabus
[SIAM Sankoff]: https://epubs.siam.org/doi/pdf/10.1137/0128004


