package com.johan.salsasurvivor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat

class Player(context : Context, private var positionX : Double, private var positionY : Double, private var radius : Double ){

    private val SPEED_PIXELS_PER_SECONDS = 400.0
    private val MAX_SPEED : Double = SPEED_PIXELS_PER_SECONDS / GameLoop.MAX_UPS
    private var paint : Paint = Paint()
    private var velocityX : Double = 0.0
    private var velocityY : Double = 0.0

    public fun setPosition(positionX: Double, positionY : Double) {
        this.positionX = positionX
        this.positionY = positionY

    }


    init {
        paint.color = ContextCompat.getColor(context, R.color.player)
    }

    fun draw(canvas: Canvas?) {

        canvas?.drawCircle(positionX.toFloat(), positionY.toFloat(), radius.toFloat(), paint)
    }

    fun update(joystick: Joystick) {
        velocityX = joystick.actuatorX * MAX_SPEED
        velocityY = joystick.actuatorY * MAX_SPEED
        positionX += velocityX
        positionY += velocityY
    }

}
