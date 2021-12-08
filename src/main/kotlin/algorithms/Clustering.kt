@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

class Clustering {

    /**
     * See also:
     * Stepik: https://stepik.org/lesson/240358/step/2?unit=212704
     * Rosalind: https://rosalind.info/problems/ba8a/
     *
     * Youtube:
     * Clustering as an Optimization Problem (Farthest First Traversal)
     * https://youtu.be/JAC0GqadoiA?t=369
     */

    var farthestFirstTraversalPsuedocode = """
        FarthestFirstTraversal(Data, k) 
            Centers ← the set consisting of a single randomly chosen point from Data
            while |Centers| < k 
              DataPoint ← the point in Data maximizing d(DataPoint, Centers) 
                add DataPoint to Centers 
                
            return Centers 
    """.trimIndent()

    fun clustersFarthestFirstTraversal(
        numCentersK: Int,
        numDimensionsM: Int,
        points: List<List<Double>>
    ): List<List<Double>> {
        // take first point in multidimensional array as first center point
        val centerList: MutableList<List<Double>> = mutableListOf(points[0])

        while (centerList.size < numCentersK) {
            centerList.add(getMaxD(centerList, points))
        }
        return centerList
    }

    /**
     * iterate through the center list, computing the distance from all centers to all points.
     *   Find the point that represents the maximum distance to all centers.
     */
    private fun getMaxD(centers: MutableList<List<Double>>, points: List<List<Double>>): List<Double> {
        var theMaxD = 0.0
        var theMaxPoint: List<Double> = points[0]

        for (i in 0 until points.size) {
            val theDistance = whatIsTheMinDistanceToCenters(points[i], centers)
            if (theDistance > theMaxD) {
                theMaxD = theDistance
                theMaxPoint = points[i]
            }
        }
        return theMaxPoint
    }

    /**
     * return the min distance to a center.  The outerloop will then select the maximum of these values.
     */
    private fun whatIsTheMinDistanceToCenters(point: List<Double>, centers: MutableList<List<Double>>): Double {

        var theMinD = Double.MAX_VALUE

        for (j in 0 until centers.size) {
            val theDistance = apacheDistance(point, centers[j])
            if (theDistance < theMinD) {
                theMinD = theDistance
            }
        }
        return theMinD
    }


}
