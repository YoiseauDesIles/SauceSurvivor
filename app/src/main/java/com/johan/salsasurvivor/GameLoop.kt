package com.johan.salsasurvivor

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameLoop(private val game: Game, private val surfaceHolder: SurfaceHolder) : Thread() {



    private val UPS_PERIOD : Double = 1E+3/MAX_UPS
    private var isRunning : Boolean = false
    private var averageUPS : Double = 0.0
    private var averageFPS : Double = 0.0

    companion object{
        const val MAX_UPS : Double = 60.0
    }

    fun getAverageUPS(): Any {
    return averageUPS
    }

    fun getAverageFPS(): Any {
        return averageFPS
    }

    fun startLoop() {
        isRunning = true
        start()
    }

    override fun run() {
        super.run()

        //Declare time and cycle count variables
        var updateCount : Int = 0
        var frameCount : Int = 0

        var startTime : Long
        var elapsedTime : Long
        var sleepTime : Long




        //Game loop
        var canvas : Canvas? = null
        startTime = System.currentTimeMillis()

        while(isRunning) {

            try {

                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    game.update()
                    updateCount++
                    game.draw(canvas)
                }

            }catch (e: IllegalArgumentException){
                e.printStackTrace()
            } finally {
                if (canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                        frameCount++
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    }
                }

            }



            //Pause game Loop to not exceed target UPS
            elapsedTime = System.currentTimeMillis() - startTime
            sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
            if (sleepTime > 0) {
                sleep(sleepTime)
            }
            //Skip frames to keep up with target UPS
            while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                game.update()
                updateCount++
                elapsedTime = System.currentTimeMillis() - startTime
                sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
            }

            //Calculate average UPS and FPS
            if (elapsedTime >=  1000){
                averageUPS = (updateCount / (1E-3 * elapsedTime)).toDouble()
                averageFPS = (updateCount / (1E-3 * elapsedTime)).toDouble()
                updateCount = 0
                frameCount = 0
                startTime = System.currentTimeMillis()
            }
        }
    }



}
