@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls", "UNUSED_VALUE", "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER", "UNUSED_CHANGED_VALUE", "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms



    /**
     * iterate through the center list, computing the distance from all centers to all points.
     *   Find the point that represents the maximum distance to all centers.
     */
    fun getMaxD(centers: MutableList<List<Double>>, points: List<List<Double>>): List<Double> {
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
    fun whatIsTheMinDistanceToCenters(point: List<Double>, centers: List<List<Double>>): Double {

        var theMinD = Double.MAX_VALUE

        for (j in 0 until centers.size) {
            val theDistance = apacheDistance(point, centers[j])
            if (theDistance < theMinD) {
                theMinD = theDistance
            }
        }
        return theMinD
    }

    /**
     * partition the points into coordinate lists
     *  based on the nearest center
     *
     * Keep a map of the center index to a list of point indices.
     *    This forms the "cluster".
     */
    fun assignPointsToClusters(
        numCentersK: Int,
        numDimensionsM: Int,
        centers: List<List<Double>>,
        points: List<List<Double>>
    ): Map<Int, List<Int>> {

        val mapCenterIndexToPoints: MutableMap<Int, MutableList<Int>> = mutableMapOf()

        for (i in 0 until points.size) {
            val indexOfClosestCenter = findClosestIndex(numCentersK, numDimensionsM, points[i], centers)
            mapCenterIndexToPoints.addTo(indexOfClosestCenter, i)
        }

        return mapCenterIndexToPoints
    }

    /**
     * go through the centers and return the index of the center that is closest to the point
     */
    fun findClosestIndex(
        numCentersK: Int,
        numDimensionsM: Int,
        point: List<Double>,
        centers: List<List<Double>>
    ): Int {
        var theMinD = Double.MAX_VALUE
        var theMinIndex = 0

        for (j in 0 until centers.size) {
            val theDistance = apacheDistance(point, centers[j])
            if (theDistance < theMinD) {
                theMinD = theDistance
                theMinIndex = j
            }
        }
        return theMinIndex
    }


    /**
     * Multidimension Center Of Gravity
     * add all the coordinates of the points and
     * return the average value of each coordinate
     */
    fun clusterCenterOfGravity(
        points: List<List<Double>>
    ): List<Double> {

        val numdims = points[0].size
        val sums: MutableList<Double> = DoubleArray(numdims).toMutableList()

        for (p in points) {
            for (i in 0 until numdims) {
                sums[i] += p[i]
            }
        }

        for (i in 0 until numdims) {
            sums[i] = sums[i] / points.size
        }
        return sums
    }
}
