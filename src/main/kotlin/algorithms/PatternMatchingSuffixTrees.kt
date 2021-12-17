@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

class PatternMatchingSuffixTrees {

    /**
     * Construct the Suffix Tree of a String
     * Stepik: https://stepik.org/lesson/240378/step/4?unit=212724
     * Rosalind: https://rosalind.info/problems/ba9c/
     *
     * SEE ALSO for a tree example:
     * https://stepik.org/lesson/240388/step/2?unit=212734
     * also:
     * https://www.bioinformaticsalgorithms.org/faq-chapter-9
     * (see :  What are the edge labels in the suffix tree for "panamabananas$"?)
     *
     * Using the Trie 2/10
     * https://www.youtube.com/watch?v=9U0ynguwoNA
     *
     * From a Trie to a Suffix Tree (3/10)
     * https://www.youtube.com/watch?v=LB-ANFydv30
     *
     * String Compression and the Burrows-Wheeler Transform (4/10)
     * https://www.youtube.com/watch?v=G7YBi04HOEY
     *
     * Inverting Burrows-Wheeler (5/10)
     * https://www.youtube.com/watch?v=DqdjbK68l3s
     */


    /**
     * Code Challenge: Solve the Suffix Tree Construction Problem.I
     *
     * Input: A string Text.
     *
     * Output: A space-separated list of the edge labels of SuffixTree(Text). You may return these strings in any order.
     */


    /*
     * from:  https://stepik.org/lesson/240388/step/2?unit=212734
     The following pseudocode constructs the modified suffix trie
     of a string Text by traversing the suffixes of Text from
     longest to shortest. Given a suffix, it attempts to spell the
     suffix by moving downward in the tree, following edge labels
     as far as possible until it can go no further. At that point,
     it adds the rest of the suffix to the trie in the form of a
     path to a leaf, along with the position of each symbol in the suffix.
     */

    private val psuedoCode = """
    ModifiedSuffixTrieConstruction(Text)
        Trie ← a graph consisting of a single node root
        for i ← 0 to |Text| - 1
            currentNode ← root
            for j ← i to |Text| - 1
                currentSymbol ← j-th symbol of Text
                if there is an outgoing edge from currentNode labeled by currentSymbol
                    currentNode ← ending node of this edge
                else
                    add a new node newNode to Trie
                    add an edge newEdge connecting currentNode to newNode in Trie
                    Symbol(newEdge) ← currentSymbol
                    Position(newEdge) ← j
                    currentNode ← newNode
            if currentNode is a leaf in Trie
                assign label i to this leaf
        return Trie
    """.trimIndent()

    /*
     And collapsing the non-branching paths per:  https://stepik.org/lesson/240388/step/5?unit=212734
     The following pseudocode constructs a suffix tree using the
     modified suffix trie constructed by ModifiedSuffixTrieConstruction.
     This algorithm will consolidate each non-branching path of the modified
     suffix trie into a single edge.
     */

    private val modifiedSuffixTree = """
    ModifiedSuffixTreeConstruction(Text)
        Trie ← ModifiedSuffixTrieConstruction
        for each non-branching path Path in Trie
            substitute Path by a single edge e connecting the first and last nodes of Path
            Position(e) ← Position(first edge of Path)
            Length(e) ← number of edges of Path
        return Trie
    """.trimIndent()

    fun createSuffixTree(s: String) {

    }




}