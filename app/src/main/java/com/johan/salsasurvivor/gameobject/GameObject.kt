package com.johan.salsasurvivor.gameobject

import android.graphics.Canvas
import kotlin.math.pow
import kotlin.math.sqrt

abstract class GameObject(protected var positionX : Double, protected var positionY : Double) {

    protected var velocityX : Double = 0.0
    protected var velocityY : Double = 0.0

    protected var directionX : Double = 1.0
    protected var directionY : Double = 0.0


    abstract fun draw(canvas: Canvas?);

    abstract fun update()

    protected fun setPosition(positionX: Double, positionY : Double) {
        this.positionX = positionX
        this.positionY = positionY

    }

    @JvmName("getPositionX1")
    public fun getPositionX() = positionX
    @JvmName("getPositionY1")
    public fun getPositionY() = positionY

    @JvmName("getDirectionX1")
    public fun getDirectionX() = directionX
    @JvmName("getDirectionY1")
    public fun getDirectionY() = directionY

    companion object{
        @JvmStatic
        protected fun getDistanceBetweenObjects(obj1: GameObject, obj2: GameObject): Double {
            return sqrt(
            (obj2.positionX - obj1.positionX).pow(2.0) + (obj2.positionY - obj1.positionY).pow(2.0)
            )
        }
    }



}