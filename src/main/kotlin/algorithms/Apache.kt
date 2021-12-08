package algorithms

/**
 * leveraging various Apache nice things:
 * https://www.apache.org/licenses/
 */

/**
 * Calculates the L<sub>2</sub> (Euclidean) distance between two points.
 *
 * @param p1 the first point
 * @param p2 the second point
 * @return the L<sub>2</sub> distance between the two points
 *
 * source:   org.apache.commons.math3.ml.distance.EuclideanDistance
 * @link https://commons.apache.org/proper/commons-math/javadocs/api-3.4/org/apache/commons/math3/ml/distance/EuclideanDistance.html
 *
 */
fun apacheDistance(p1: List<Double>, p2: List<Double>): Double {
    var sum = 0.0
    for (i in p1.indices) {
        val dp = p1[i] - p2[i]
        sum += dp * dp
    }
    return kotlin.math.sqrt(sum)
}