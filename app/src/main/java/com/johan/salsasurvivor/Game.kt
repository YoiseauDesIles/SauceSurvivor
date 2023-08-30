package com.johan.salsasurvivor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.johan.salsasurvivor.`object`.Enemy
import com.johan.salsasurvivor.`object`.Player

class Game(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private lateinit var gameLoop : GameLoop
    private lateinit var player : Player
    private lateinit var joystick : Joystick
    private lateinit var enemy : Enemy

    init {
        //get surface holder and add callback
        val surfaceHolder : SurfaceHolder = holder
        surfaceHolder.addCallback(this)

        gameLoop = GameLoop(this, surfaceHolder)

        joystick = Joystick(275, 700, 70, 40)
        player = Player(getContext(), joystick,500.0, 500.0, 30.0)
        enemy = Enemy(getContext(),  player,700.0, 200.0, 30.0)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        //handle touch event actions
        when(event?.getAction()){
            MotionEvent.ACTION_DOWN -> {
                if (joystick.isPressed(event.x.toDouble(), event.y.toDouble())) {
                    joystick.isPressed = true
                }
                //player.setPosition(event.x.toDouble(), event.y.toDouble())
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (joystick.isPressed){
                    joystick.setActuator(event.x.toDouble(), event.y.toDouble())
                }
                //player.setPosition(event.x.toDouble(), event.y.toDouble())
                return true
            }
            MotionEvent.ACTION_UP -> {
                joystick.isPressed = false
                joystick.resetActuator()
                return true

            }

        }



        return super.onTouchEvent(event)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

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
        joystick.update()
        player.update()
        enemy.update()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawUPS(canvas)
        drawFPS(canvas)

        joystick.draw(canvas)
        player.draw(canvas)
        enemy.draw(canvas)
    }
}
