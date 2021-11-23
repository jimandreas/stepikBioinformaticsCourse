@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

/**
 *
 * See also:
 * Stepik: https://stepik.org/lesson/240343/step/8?unit=212689
 * Rosalind: no equivalent problem
 *
The nearest neighbor interchange heuristic for the
Large Parsimony Problem starts from an arbitrary
unrooted binary tree. It assigns input strings to
arbitrary leaves of this tree, assigns strings to
the internal nodes of the tree by solving the
Small Parsimony Problem in an Unrooted Tree,
and then moves to a nearest neighbor that provides
the best improvement in the parsimony score.
At each iteration, the algorithm explores all
internal edges of a tree and generates all nearest
neighbor interchanges for each internal edge. For each
of these nearest neighbors, the algorithm solves the
Small Parsimony Problem to reconstruct the labels of the
internal nodes and computes the parsimony score.
If a nearest neighbor with smaller parsimony score is found,
then the algorithm selects the one with smallest parsimony score
(ties are broken arbitrarily) and iterates again; otherwise, the algorithm terminates.

 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

class NearestNeighborInterchange {

    /**

    Code Challenge: Implement the nearest neighbor interchange
    heuristic for the Large Parsimony Problem.

    Input: An integer n, followed by an adjacency list
    for an unrooted binary tree whose n leaves are labeled by
    DNA strings and whose internal nodes are labeled by integers.

    Output: The parsimony score and unrooted labeled tree obtained
    after every step of the nearest neighbor interchange heuristic.
    Each step should be separated by a blank line.
     */

    val psuedoCode = """
        NearestNeighborInterchange(Strings)
     score ← ∞
     generate an arbitrary unrooted binary tree Tree with |Strings| leaves
     label the leaves of Tree by arbitrary strings from Strings
     solve  the  Small Parsimony in an Unrooted Tree Problem for Tree
     label the internal nodes of Tree according to a most parsimonious labeling
     newScore ← the parsimony score of Tree
     newTree ← Tree
     while newScore < score
          score ← newScore
          Tree ← newTree
          for each internal edge e in Tree
               for each nearest neighbor NeighborTree of Tree with respect to the edge e
                    solve the Small Parsimony in an Unrooted Tree Problem for NeighborTree
                    neighborScore ← the minimum parsimony score of NeighborTree
                    if neighborScore < newScore
                         newScore ← neighborScore
                         newTree ← NeighborTree
     return newTree
    """.trimIndent()

}
