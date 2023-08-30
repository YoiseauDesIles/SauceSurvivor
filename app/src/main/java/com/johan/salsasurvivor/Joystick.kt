package com.johan.salsasurvivor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.pow
import kotlin.math.sqrt

class Joystick(centerPositionX : Int,centerPositionY : Int, private var outerCircleRadius : Int, private var innerCircleRadius : Int ) {

    var isPressed: Boolean = false
        get() = field
        set(value) {
            field = value
        }


    private var innerCircleCenterPositionX : Int
    private var innerCircleCenterPositionY : Int
    private var outerCircleCenterPositionX : Int
    private var outerCircleCenterPositionY : Int
    private lateinit var innerCirclePaint : Paint
    private lateinit var outerCirclePaint : Paint

    var actuatorX : Double = 0.0
        get() = field
        set(value) {
            field = value
        }
    var actuatorY : Double = 0.0
        get() = field
        set(value) {
            field = value
        }


    init {
        outerCircleCenterPositionX = centerPositionX
        outerCircleCenterPositionY = centerPositionY
        innerCircleCenterPositionX = centerPositionX
        innerCircleCenterPositionY = centerPositionY

        //paint of circles
        outerCirclePaint = Paint()
        outerCirclePaint.color = Color.GRAY
        outerCirclePaint.style = Paint.Style.FILL_AND_STROKE

        innerCirclePaint = Paint()
        innerCirclePaint.color = Color.BLUE
        innerCirclePaint.style = Paint.Style.FILL_AND_STROKE
    }


    fun draw(canvas: Canvas?) {
        canvas?.drawCircle(
            outerCircleCenterPositionX.toFloat(),
            outerCircleCenterPositionY.toFloat(),
            outerCircleRadius.toFloat(),
            outerCirclePaint)


        canvas?.drawCircle(
            innerCircleCenterPositionX.toFloat(),
            innerCircleCenterPositionY.toFloat(),
            innerCircleRadius.toFloat(),
            innerCirclePaint)
    }

    fun update() {
        updateInnerCirclePosition()
    }

    private fun updateInnerCirclePosition() {
        innerCircleCenterPositionX = ((outerCircleCenterPositionX + actuatorX*outerCircleRadius).toInt())
        innerCircleCenterPositionY = ((outerCircleCenterPositionY + actuatorY*outerCircleRadius).toInt())
    }

    fun isPressed(touchPositionX: Double, touchPositionY: Double): Boolean {
        val joystickCenterToTouchDistance = sqrt(
             (outerCircleCenterPositionX - touchPositionX).pow(2.0) +
                (outerCircleCenterPositionY - touchPositionY).pow(2.0)
        )
        return joystickCenterToTouchDistance < outerCircleRadius

    }

    fun setActuator(touchPositionX: Double, touchPositionY: Double) {
        val deltaX : Double = touchPositionX - outerCircleCenterPositionX
        val deltaY : Double = touchPositionY - outerCircleCenterPositionY
        val deltaDistance = sqrt(deltaX.pow(2.0) + deltaY.pow(2.0))

        if (deltaDistance < outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius
            actuatorY = deltaY/outerCircleRadius
        }else {
            actuatorX = deltaX/deltaDistance
            actuatorY = deltaY/deltaDistance
        }
    }

    fun resetActuator() {
        actuatorX = 0.0
        actuatorY = 0.0
    }

}
