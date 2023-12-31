package com.johan.salsasurvivor.gameobject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.johan.salsasurvivor.GameDisplay

abstract class Circle(
    context : Context,
    color : Int,
    positionX : Double,
    positionY : Double,
    protected var radius : Double) : GameObject(positionX, positionY){

    protected var paint : Paint = Paint()


    init {
        paint.color = color
    }


    override fun draw(canvas: Canvas?, gameDisplay: GameDisplay) {

        canvas?.drawCircle(
            gameDisplay.gameToDisplayCoordinatesX(positionX).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(positionY).toFloat(),
            radius.toFloat(),
            paint)

    }

    companion object {

        @JvmStatic
        fun isColliding(obj1 : Circle, obj2 : Circle) : Boolean{
            val distance : Double = getDistanceBetweenObjects(obj1, obj2)
            val distanceToCollision : Double = obj1.radius + obj2.radius

            return distance < distanceToCollision

       }

    }

}