package com.johan.salsasurvivor.`object`

import android.content.Context
import androidx.core.content.ContextCompat
import com.johan.salsasurvivor.GameLoop
import com.johan.salsasurvivor.Joystick
import com.johan.salsasurvivor.R

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
        velocityX = joystick.actuatorX * MAX_SPEED
        velocityY = joystick.actuatorY * MAX_SPEED

        positionX += velocityX
        positionY += velocityY
    }

    companion object{
        public val SPEED_PIXELS_PER_SECOND = 400.0
    }

}
