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
     * (squared error distorition): https://stepik.org/lesson/240359/step/3?unit=212705
     * (lloyd k-Means clustering): https://stepik.org/lesson/240360/step/3?unit=212706
     * (Soft k-Means Clustering Algorithm) - No equivalent stepik problem
     *
     * Rosalind: https://rosalind.info/problems/ba8a/
     * (squared error distorition): https://rosalind.info/problems/ba8b/
     * (lloyd k-Means clustering): https://rosalind.info/problems/ba8c/
     * (Soft k-Means Clustering Algorithm) https://rosalind.info/problems/ba8d/
     *
     * Youtube:
     * Clustering as an Optimization Problem (Farthest First Traversal)
     * https://youtu.be/JAC0GqadoiA?t=369
     *
     * The Lloyd Algorithm for k-Means Clustering 4/9
     * https://www.youtube.com/watch?v=9rp1pzYn3hY
     *
     * From Hard to Soft Clustering 5/9
     * https://www.youtube.com/watch?v=xtDMHPVDDKk
     *
     * From Coin Flipping to k-Means Clustering 6/9
     * https://www.youtube.com/watch?v=3gbIXxutE8E
     *
     * Expectation Maximization 7/9
     * https://www.youtube.com/watch?v=P1r4RR1goIU
     *
     * Soft k-Means Clustering 8/9
     * https://www.youtube.com/watch?v=fpM0iZTjLhM
     *
     * Hierarchical Clustering 9/9
     * https://www.youtube.com/watch?v=Aly7YiDjxZs
     */

    var farthestFirstTraversalPsuedocode = """
        FarthestFirstTraversal(Data, k) 
            Centers ← the set consisting of a single randomly chosen point from Data
            while |Centers| < k 
              DataPoint ← the point in Data maximizing d(DataPoint, Centers) 
                add DataPoint to Centers 
                
            return Centers 
    """.trimIndent()

    /**
     * k-Center Clustering Problem: Given a set of data points,
     * find k centers minimizing the maximum distance between
     * these data points and centers.
     *
     * Input: A set of [points] Data and an integer [numCentersK].
     * Output: A set Centers of k centers that minimize the distance
     * MaxDistance(DataPoints, Centers) over all possible choices of k centers.
     */

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
     * Compute the Squared Error Distortion
     *
     * Distortion(Data,Centers) = (1/n) ∑ all points DataPoint in Data d(DataPoint, Centers) exp 2
     *
     * Code Challenge: Solve the Squared Error Distortion Problem.
     * Input: Integers [numCentersK] and [numDimensionsM],
     * followed by a set of [centers] Centers and a set of [points] Data.
     *
     * Output: The squared error distortion Distortion(Data, Centers).
     */

    fun squaredErrorDistortion(
        numCentersK: Int,
        numDimensionsM: Int,
        centers: List<List<Double>>,
        points: List<List<Double>>
    ): Double {

        var totalDistance = 0.0

        for (p in points) {
            val distance = whatIsTheMinDistanceToCenters(p, centers)
            totalDistance += distance * distance
        }

        return totalDistance / points.size
    }

    /**
     * Code Challenge: Implement the Lloyd algorithm for k-means clustering.
     *
     * Input: Integers [numCentersK] and [numDimensionsM] followed by a
     * set of [points] Data in m-dimensional space.
     *
     * Output: A set Centers consisting of k points (centers)
     * resulting from applying the Lloyd algorithm to
     * Data and Centers, where the first k points from
     * Data are selected as the first k centers.
     */
    fun lloydAlgorithmKmeans(
        numCentersK: Int,
        numDimensionsM: Int,
        points: List<List<Double>>
    ): List<List<Double>> {

        // Phase 1: set up initial centers - just use the first "numCenters" points

        var centers = points.subList(0, numCentersK)

        // Phase 2: Looping, a) assign points to centers

        val newCenterList: MutableList<List<Double>> = mutableListOf()
        do {
            val clusterMap = assignPointsToClusters(numCentersK, numDimensionsM, centers, points)

            // b) for each set of points in a cluster, find the center of gravity
            //      and make this center the new "center"


            for (i in 0 until centers.size) {

                // we need to get the points from the clusterMap that
                //   are associated with cluster "i"

                val extractedList: MutableList<List<Double>> = mutableListOf()
                clusterMap[i]!!.forEach {
                    extractedList.add(points[it])
                }

                // now hand this set of multidimensional points to the center of gravity function
                //    It will return a new multidimensional point - which is the new "center"

                val newCenter = clusterCenterOfGravity(extractedList)
                newCenterList.add(newCenter)
            }

            // OK we are done with the loop.   Did the set of cluster centers of gravity change??

            var changed = true
            for (i in 0 until centers.size) {
                if (!isEqual(newCenterList[i], centers[i])) {
                    changed = false
                    break
                }
            }
            centers = newCenterList.toMutableList()
            newCenterList.clear()

            // if nothing changed, we are done.  Break out and return
            if (changed) {
                break
            }

        } while (true)


        return centers
    }


    /**
     * notes for following Soft k-Means Clustering Algorithm
     *
     */
    val hiddenMatrixFormula = """
         uses the following partition function from statistical physics:

           HiddenMatrixi,j = e−β⋅d(Dataj,xi) / ∑all centers Xi * exp(−β⋅d(Dataj,xi))
    """.trimIndent()

    /**
     * Code Challenge: Implement the Soft k-Means Clustering Algorithm
     *
     * Input: Integers [numCentersK] and [numDimensionsM] followed
     * followed by a stiffness parameter β [stiffnessParameterB]
     * and a set of [points] Data in m-dimensional space.
     *
     * Output: A set Centers consisting of k points (centers)
     * resulting from applying the soft k-means clustering algorithm.
     *
     * Select the first k points from Data as the first centers
     * for the algorithm and run the algorithm for 100 steps.
     * Results should be accurate up to three decimal places.
     */
    fun softKmeansClusteringAlgorithm(
        numCentersK: Int,
        numDimensionsM: Int,
        stiffnessParameterB: Int,
        points: List<List<Double>>
    ): List<List<Double>> {

        // Phase 1: set up initial centers - just use the first "numCenters" points

        var centers = points.subList(0, numCentersK)

        // Phase 2: Looping, a) assign points to centers

        var iteration = 0
        val newCenterList: MutableList<List<Double>> = mutableListOf()
        do {
            val clusterMap = assignPointsToClusters(numCentersK, numDimensionsM, centers, points)

            // b) for each set of points in a cluster, find the center of gravity
            //      and make this center the new "center"


            for (i in 0 until centers.size) {

                // we need to get the points from the clusterMap that
                //   are associated with cluster "i"

                val extractedList: MutableList<List<Double>> = mutableListOf()
                clusterMap[i]!!.forEach {
                    extractedList.add(points[it])
                }

                // now hand this set of multidimensional points to the center of gravity function
                //    It will return a new multidimensional point - which is the new "center"

                val newCenter = clusterCenterOfGravity(extractedList)
                newCenterList.add(newCenter)
            }

            // OK we are done with the loop.   Did the set of cluster centers of gravity change??

            var changed = true
            for (i in 0 until centers.size) {
                if (!isEqual(newCenterList[i], centers[i])) {
                    changed = false
                    break
                }
            }
            centers = newCenterList.toMutableList()
            newCenterList.clear()

            // if nothing changed, we are done.  Break out and return
            if (changed) {
                break
            }

        } while (true)


        return centers
    }


}
