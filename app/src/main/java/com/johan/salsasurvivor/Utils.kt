package com.johan.salsasurvivor

import kotlin.math.pow
import kotlin.math.sqrt

class Utils {


    companion object {

        /**
         * getDistanceBetweenPoints returns the distance between 2D points P1 and P2
         * @param p1X
         * @param p1Y
         * @param p2X
         * @param p2Y
         * @return
         */
        @JvmStatic
        public fun getDistanceBetweenPoints(p1X : Double, p1Y : Double, p2X : Double, p2Y : Double) : Double{

            return sqrt(
                (p1X - p2X).pow(2.0) + (p1Y - p2Y).pow(2.0)
            )
        }
    }
}
