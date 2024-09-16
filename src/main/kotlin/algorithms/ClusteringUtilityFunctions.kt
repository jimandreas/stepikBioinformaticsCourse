@file:Suppress(
    "MemberVisibilityCanBePrivate", "UnnecessaryVariable", "ReplaceJavaStaticMethodWithKotlinAnalog",
    "unused", "UNUSED_VARIABLE", "ReplaceManualRangeWithIndicesCalls",  "ReplaceWithOperatorAssignment",
    "UNUSED_PARAMETER",  "CanBeVal", "SimplifyBooleanWithConstants",
    "ConvertTwoComparisonsToRangeCheck", "ReplaceSizeCheckWithIsNotEmpty", "LiftReturnOrAssignment"
)

package algorithms

import kotlin.math.exp
import kotlin.math.pow


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


/**
 * Hidden Matrix - "Newtonian"
 * HiddenMatrixi,j = (1 / d(Dataj,xi)2) / (∑all centers xi1/d(Dataj,xi)2)
 * @link: https://rosalind.info/problems/ba8d/
 *
 * Returned: a map (key is the index number of the center)
 * to a weighting of the center on the point.
 *
 *    For a given center: (List<List<Int>>)
 *       The first list is the weighting for the first point (n -dimensions)
 *       and the second list is the weighting for the second point, and so forth.
 */

fun hiddenMatrixNewtonian(
    centers: List<List<Double>>,
    points: List<List<Double>>
): Map<Int, List<Double>> {

    val retMap: MutableMap<Int, List<Double>> = mutableMapOf()
    val retList: MutableList<MutableList<Double>> = mutableListOf()

    for (centerNum in 0 until centers.size) {

        val pointList: MutableList<Double> = mutableListOf()
        for (pointNum in 0 until points.size) {

            val thisPointNumerator = 1.0 / (apacheDistance(points[pointNum], centers[centerNum]).pow(2.0))

            // normalize to the sum of all distances squared (inverted)
            var allCentersSum = 0.0
            for (allCentersNum in 0 until centers.size) {
                allCentersSum += 1.0 / (apacheDistance(points[pointNum], centers[allCentersNum]).pow(2.0))
            }

            val weighting = thisPointNumerator / allCentersSum
            pointList.add(weighting)
        }
        retMap[centerNum] = pointList
    }
    return retMap
}

/**
 * Hidden Matrix - "Parition Function from statistical physics"
 *
 * HiddenMatrixi,j =  (exp() is e to the power of function)
 *
 *                exp (−β - distance(Dataj,xi))
 *               --------------------------------------
 *         ∑ all centers xt of  exp (-β - distance(Dataj,xt))
 *
 * @link: https://rosalind.info/problems/ba8d/
 *
 * Returned: a map (key is the index number of the center)
 * to a weighting of the center on the point.
 *
 *    For a given center: (List<List<Int>>)
 *       The first list is the weighting for the first point (n -dimensions)
 *       and the second list is the weighting for the second point, and so forth.
 */

fun hiddenMatrixPartitionFunction(
    stiffmessB: Double,
    centers: List<List<Double>>,
    points: List<List<Double>>
): Map<Int, List<Double>> {

    val retMap: MutableMap<Int, List<Double>> = mutableMapOf()
    val retList: MutableList<MutableList<Double>> = mutableListOf()

    for (centerNum in 0 until centers.size) {

        val pointList: MutableList<Double> = mutableListOf()
        for (pointNum in 0 until points.size) {

            val debugExponent = -stiffmessB * apacheDistance(points[pointNum], centers[centerNum])
            val thisPointNumerator = exp(-stiffmessB * apacheDistance(points[pointNum], centers[centerNum]))

            // normalize to the sum of all distances squared (inverted)
            var allCentersSum = 0.0
            for (allCentersNum in 0 until centers.size) {
                allCentersSum += Math.exp(-stiffmessB * apacheDistance(points[pointNum], centers[allCentersNum]))
            }

            val weighting = thisPointNumerator / allCentersSum
            pointList.add(weighting)
        }
        retMap[centerNum] = pointList
    }
    return retMap
}

