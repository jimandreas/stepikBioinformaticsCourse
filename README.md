# Bioinformatics Algorithms: An Active Learning Approach
https://stepik.org/course/55789/syllabus

Later I found the [Rosalind] resource.  In reading 
through the FAQ it asserts: 

> Can I post my solutions somewhere?

> No. The goal of Rosalind is to facilitate learning through problem solving. We encourage you to search the Internet or communicate with each other to find the best algorithms to solve our problem. However, there is a difference between looking for inspiration and copy-pasting someone else's code; we strongly advise you not to use others' source code. Once you have solved a problem, then we encourage you to post your code to the problem's comments section (which can be seen only by users who have also solved the problem). However, please do not publish your code outside of the Rosalind website.

My answer: I appear to be the first person to 
attempt solutions to the Bioinformatics (Stepik)
course in Kotlin.  I find that the question formulation
is in some cases *questionable*.   In addition, the
level of discussion about the *questionability*
of the questions themselves was, shall we say, 
minimal.  This drives one to examine the solutions
posted to Github for an unblocking hint.

This reached a crescendo for me at least with the
[Middle Edge in Linear Space Problem].  The solutions
were greatly varied and the problem description in 
chapter 5.13 of the [Bioinformatics Algorithms] 
made me think - does this really work?

So sorry, but I am posting my work to GitHub.  I encourage 
you to not "copy/paste" my Kotlin code (LOL) but 
instead to do your best work on your own and
seek guidance only when stuck.  Best wishes, Jim Andreas.

## Notes

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

`gradlew init build test`

All exercises contain a corresponding "Jupiter" (junit) test module located 
in the `src/test/kotlin` folder.

[Linear Space Alignment]: http://rosalind.info/problems/ba5l/
[Global Alignment]: http://rosalind.info/problems/ba5e/
[Rosalind]: http://rosalind.info/faq/
[Middle Edge in Linear Space Problem]: http://rosalind.info/problems/ba5k/
[Bioinformatics Algorithms]: https://www.bioinformaticsalgorithms.org/bioinformatics-chapter-5
[Jim Andreas]: https://www.jimandreas.com