package com.johan.salsasurvivor.gamepanel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.johan.salsasurvivor.GameLoop
import com.johan.salsasurvivor.R

class Performance(private val context : Context, private val gameLoop: GameLoop) {

    public fun draw (canvas : Canvas?) {
         drawUPS(canvas)
         drawFPS(canvas)
    }

    public fun drawUPS(canvas : Canvas?) {

        val averageUPS : String = gameLoop.getAverageUPS().toString()
        val paint : Paint = Paint()
        val color : Int = ContextCompat.getColor(context, R.color.magenta)
        paint.color = color
        paint.textSize = 50F
        canvas?.drawText("UPS : $averageUPS", 100F, 100F, paint)
    }

    public fun drawFPS(canvas : Canvas?) {

        val averageUPS : String = gameLoop.getAverageFPS().toString()
        val paint : Paint = Paint()
        val color : Int = ContextCompat.getColor(context, R.color.magenta)
        paint.color = color
        paint.textSize = 50F
        canvas?.drawText("FPS : $averageUPS", 100F, 200F, paint)
    }
}
