package com.johan.salsasurvivor.`object`

import android.graphics.Canvas
import kotlin.math.pow
import kotlin.math.sqrt

abstract class GameObject(positionX : Double, positionY : Double) {

    protected var positionX : Double = 0.0
    protected var positionY : Double = 0.0

    protected var velocityX : Double = 0.0
    protected var velocityY : Double = 0.0

    var posX : Double
        get() = positionX
        set(value) {
            positionX = value
        }

    var posY : Double
        get() = positionY
        set(value) {
            positionY = value
        }

    init {
        this.positionX = positionX
        this.positionY = positionY

    }
    abstract fun draw(canvas: Canvas?);

    abstract fun update()

    protected fun setPosition(positionX: Double, positionY : Double) {
        this.positionX = positionX
        this.positionY = positionY

    }


    companion object{
        @JvmStatic
        protected fun getDistanceBetweenObjects(obj1: GameObject, obj2: GameObject): Double {
            return sqrt(
            (obj2.posX - obj1.posX).pow(2.0) + (obj2.posY - obj1.posY).pow(2.0)
            )
        }
    }



}