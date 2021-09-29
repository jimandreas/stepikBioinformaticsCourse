@file:Suppress(
    "UNUSED_VARIABLE", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER", "unused",
    "ReplaceManualRangeWithIndicesCalls"
)

import algorithms.DistancesBetweenLeaves
import algorithms.Phylogeny
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach


/**
 *
 * See also:  Ultrametric Trees - Unweighted Pair Group Method with Arithmetic Mean  UPGMA
 * Youtube: https://www.youtube.com/watch?v=27aOeJ2hSwA
 *
 * and also: Creating a Phylogenetic Tree
 * https://www.youtube.com/watch?v=09eD4A_HxVQ
 *
 * Limb Length:
 * stepik: https://stepik.org/lesson/240339/step/8?unit=212685
 * rosalind: http://rosalind.info/problems/ba7d/
 *
 * Uses the Kotlin Multik multidimensional array library
 * @link: https://github.com/Kotlin/multik
 * @link: https://blog.jetbrains.com/kotlin/2021/02/multik-multidimensional-arrays-in-kotlin/
 */

internal class S07c06p08UPGMAtest {

    lateinit var ll: Phylogeny
    lateinit var dbl: DistancesBetweenLeaves


    @BeforeEach
    fun setUp() {
        ll = Phylogeny()
        dbl = DistancesBetweenLeaves()
    }

    @AfterEach
    fun tearDown() {
    }


}