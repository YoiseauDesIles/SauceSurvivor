package com.johan.salsasurvivor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat

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

    private val SPEED_PIXELS_PER_SECONDS = 400.0
    private val MAX_SPEED : Double = SPEED_PIXELS_PER_SECONDS / GameLoop.MAX_UPS


    init {
        paint.color = ContextCompat.getColor(context, R.color.player)
    }


    override fun update() {
        velocityX = joystick.actuatorX * MAX_SPEED
        velocityY = joystick.actuatorY * MAX_SPEED

        positionX += velocityX
        positionY += velocityY
    }

}
