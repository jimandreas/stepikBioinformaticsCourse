import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import util.RandomizedMotifSearch

/**
 *
Code Challenge: Implement RandomizedMotifSearch.

Input: Integers k and t, followed by a space-separated collection of strings Dna.
Output: A collection BestMotifs resulting from running
RandomizedMotifSearch(Dna, k, t) 1,000 times. Remember to use pseudocounts!

 * See also:
 * stepik: @link: https://stepik.org/lesson/240243/step/5?unit=212589
 * rosalind: @link: http://rosalind.info/problems/ba2f/
 *
 * youtube:
 * @link: https://www.youtube.com/watch?v=sUXe2G2I9IA
 * @link: https://www.youtube.com/watch?v=MP6O_Z2AUDU
 */
internal class S02c07p07RandomizedMotifSearchTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    val rms = RandomizedMotifSearch()

    /**
     * Case 1
    Description: A small and hand-solvable dataset taken from the example problem on Stepik.
     */
    @Test
    @DisplayName("test RandomizedMotifSearch 1")
    fun testRandomizedMotifSearch() {

        val dnaStrings = """
            
        """.trimIndent()

        val k = 6 // kmer length

        val expectedResult = """
            
        """.trimIndent()

        val reader = dnaStrings.reader()
        val lines = reader.readLines()

        val result = rms.doSearch(lines, k)

        assertEquals(expectedResult, result)

    }

}