package com.johan.salsasurvivor.`object`

import android.content.Context
import androidx.core.content.ContextCompat
import com.johan.salsasurvivor.GameLoop
import com.johan.salsasurvivor.Joystick
import com.johan.salsasurvivor.R
import com.johan.salsasurvivor.Utils

class Player(context : Context,
             private val joystick: Joystick,
             positionX : Double,
             positionY : Double,
             radius : Double ) :
    Circle(
        context,
        ContextCompat.getColor(context, R.color.player),
        positionX,
        positionY,
        radius){


    private val MAX_SPEED : Double = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS


    init {
        paint.color = ContextCompat.getColor(context, R.color.player)
    }


    override fun update() {
        //Update velocity based on actuator of joystick
        velocityX = joystick.actuatorX * MAX_SPEED
        velocityY = joystick.actuatorY * MAX_SPEED

        //update position
        positionX += velocityX
        positionY += velocityY

        //update direction from velocity
        if (velocityX != 0.0 || velocityY != 0.0) {
            //Normalise velocity to get direction(unit vector of velocity)
            val distance : Double = Utils.getDistanceBetweenPoints(0.0, 0.0, velocityX, velocityY)
            directionX = velocityX / distance
            directionY = velocityY / distance
        }
    }

    companion object{
        public val SPEED_PIXELS_PER_SECOND = 400.0
    }

}
