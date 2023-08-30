package com.johan.salsasurvivor

import android.graphics.Canvas

abstract class GameObject( protected var positionX : Double, protected var positionY : Double) {

    protected var velocityX : Double = 0.0
    protected var velocityY : Double = 0.0

    abstract fun draw(canvas: Canvas?);

    abstract fun update()

    protected fun setPosition(positionX: Double, positionY : Double) {
        this.positionX = positionX
        this.positionY = positionY

    }
}