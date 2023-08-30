package com.johan.salsasurvivor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat

class Game(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private lateinit var gameLoop : GameLoop
    private val currContext = context

    init {
        //get surface holder and add callback
        val surfaceHolder : SurfaceHolder = holder
        surfaceHolder.addCallback(this)

        gameLoop = GameLoop(this, surfaceHolder)

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawUPS(canvas)
        drawFPS(canvas)
    }

    public fun drawUPS(canvas : Canvas?) {

        val averageUPS : String = gameLoop.getAverageUPS().toString()
        val paint : Paint = Paint()
        val color : Int = ContextCompat.getColor(context, R.color.magenta)
        paint.color = color
        paint.textSize = 50F
        canvas?.drawText("UPS : " + averageUPS, 100F, 100F, paint)
    }

    public fun drawFPS(canvas : Canvas?) {

        val averageUPS : String = gameLoop.getAverageFPS().toString()
        val paint : Paint = Paint()
        val color : Int = ContextCompat.getColor(context, R.color.magenta)
        paint.color = color
        paint.textSize = 50F
        canvas?.drawText("FPS : " + averageUPS, 100F, 200F, paint)
    }

    fun update() {

    }
}
