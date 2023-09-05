package com.johan.salsasurvivor.gamepanel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.johan.salsasurvivor.R

/**
 * Panel that xrite Game Over to the screen
 */
class GameOver(private val context : Context) {


    public fun draw(canvas : Canvas?) {
        val text : String= "Game Over"
        val x : Float = 800.0F
        val y : Float = 200.0F

        val paint : Paint = Paint()
        val color : Int = ContextCompat.getColor(context, R.color.gameOver)
        paint.color = color
        val textSize = 150.0F
        paint.textSize = textSize

        canvas?.drawText(text, x, y, paint)
    }
}
