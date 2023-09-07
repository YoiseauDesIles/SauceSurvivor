package com.johan.salsasurvivor.gamepanel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.johan.salsasurvivor.GameDisplay
import com.johan.salsasurvivor.R
import com.johan.salsasurvivor.gameobject.Player

/**
 * Healthbar displays the player health to the screen
 */
class HealthBar(context : Context, private val player : Player) {

    private val width = 100
    private val height = 20
    private val margin = 2
    private val borderPaint : Paint = Paint()
    private val borderColor : Int = ContextCompat.getColor(context, R.color.healthBarBorder)
    private val healthPaint : Paint = Paint()
    private val healthColor : Int = ContextCompat.getColor(context, R.color.healthBarHealth)

    init {
        borderPaint.color = borderColor
        healthPaint.color = healthColor
    }

    public fun draw(canvas : Canvas?, gameDisplay: GameDisplay) {
        val x : Float = player.getPositionX().toFloat()
        val y : Float = player.getPositionY().toFloat()
        val distanceToPlayer : Float = 30.0F
        val healthPointPourcentage : Float = player.getHealthPoints().toFloat() / Player.MAX_HEALTH_POINTS.toFloat()


        //draw border
        val borderLeft : Float = x - width / 2
        val borderRight : Float = x + width / 2
        val borderBottom : Float = y - distanceToPlayer
        val borderTop : Float = borderBottom - height

        canvas?.drawRect(
            gameDisplay.gameToDisplayCoordinatesX(borderLeft.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(borderTop.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesX(borderRight.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(borderBottom.toDouble()).toFloat(),
            borderPaint)


        //draw health
        val healthWidth : Float = (width - 2 * margin).toFloat()
        val healthHeight : Float = (height - 2 * margin).toFloat()
        val healthLeft : Float = (borderLeft + margin).toFloat()
        val healthRight : Float = (healthLeft + healthWidth * healthPointPourcentage).toFloat()
        val healthBottom : Float = (borderBottom - margin ).toFloat()
        val healthTop : Float = (healthBottom - healthHeight).toFloat()

        canvas?.drawRect(
            gameDisplay.gameToDisplayCoordinatesX(healthLeft.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(healthTop.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesX(healthRight.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(healthBottom.toDouble()).toFloat(),
            healthPaint)
    }
}
