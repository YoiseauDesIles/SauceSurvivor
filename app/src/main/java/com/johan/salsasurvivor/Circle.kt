package com.johan.salsasurvivor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat

abstract class Circle(
    context : Context,
    color : Int,
    positionX : Double,
    positionY : Double,
    protected var radius : Double) : GameObject(positionX, positionY){

    protected lateinit var paint : Paint


    init {
        paint = Paint()
        paint.color = color
    }


    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(positionX.toFloat(), positionY.toFloat(), radius.toFloat(), paint)
    }

}