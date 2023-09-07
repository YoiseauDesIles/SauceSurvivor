package com.johan.salsasurvivor

import com.johan.salsasurvivor.gameobject.GameObject

class GameDisplay(private val centerObject : GameObject, widthPixels : Int, heightPixels : Int) {

    private var gameToDisplayCoordinatesOffsetX: Double = 0.0
    private var gameToDisplayCoordinatesOffsetY: Double = 0.0
    private var displayCenterX: Double = 0.0
    private var displayCenterY: Double = 0.0
    private var gameCenterX: Double = 0.0
    private var gameCenterY: Double = 0.0

    init {
        displayCenterX = widthPixels/2.0
        displayCenterY = heightPixels/2.0
    }

    public fun update() {
        gameCenterX = centerObject.getPositionX()
        gameCenterY = centerObject.getPositionY()

        gameToDisplayCoordinatesOffsetX = displayCenterX - gameCenterX
        gameToDisplayCoordinatesOffsetY = displayCenterY - gameCenterY
    }

    fun gameToDisplayCoordinatesX(x: Double): Double {
        return x + gameToDisplayCoordinatesOffsetX
    }

    fun gameToDisplayCoordinatesY(y: Double): Double {
        return y + gameToDisplayCoordinatesOffsetY
    }

}
