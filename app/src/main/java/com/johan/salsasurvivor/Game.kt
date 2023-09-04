package com.johan.salsasurvivor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.johan.salsasurvivor.`object`.Circle
import com.johan.salsasurvivor.`object`.Enemy
import com.johan.salsasurvivor.`object`.Player
import com.johan.salsasurvivor.`object`.Spell

class Game(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private lateinit var gameLoop : GameLoop
    private lateinit var player : Player
    private lateinit var joystick : Joystick
    private var enemyList : MutableList<Enemy> = mutableListOf()
    private var spellList : MutableList<Spell> = mutableListOf()
    private var joystickPointerId : Int = 0
    private var numberOfSpellsToCast : Int = 0

    init {
        //get surface holder and add callback
        val surfaceHolder : SurfaceHolder = holder
        surfaceHolder.addCallback(this)

        gameLoop = GameLoop(this, surfaceHolder)

        joystick = Joystick(275, 700, 70, 40)
        player = Player(getContext(), joystick,500.0, 500.0, 20.0)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        //handle touch event actions
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                if (joystick.isPressed){
                    //joystick was pressed before this event -> cast spell
                    numberOfSpellsToCast++
                } else if (joystick.isPressed(event.x.toDouble(), event.y.toDouble())) {
                    //joystick is pressed in this event -> is pressed = true
                    joystickPointerId = event.getPointerId(event.actionIndex)
                    joystick.isPressed = true
                } else {
                    //joystick was not previously pressed, and is not pressed during this event -> cast spell
                    numberOfSpellsToCast++
                }
                return true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (joystick.isPressed){
                    //joystick was pressed before this event -> cast spell
                    spellList.add(Spell(context, player))
                } else if (joystick.isPressed(event.x.toDouble(), event.y.toDouble())) {
                    //joystick is pressed in this event -> is pressed = true
                    joystickPointerId = event.getPointerId(event.actionIndex)
                    joystick.isPressed = true
                } else {
                    //joystick was not previously pressed, and is not pressed during this event -> cast spell
                    spellList.add(Spell(context, player))
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                //joystick was pressed previously and is now moved
                if (joystick.isPressed){
                    joystick.setActuator(event.x.toDouble(), event.y.toDouble())
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                joystick.isPressed = false
                joystick.resetActuator()
            }
            MotionEvent.ACTION_POINTER_UP -> {
                if (joystickPointerId == event.getPointerId(event.actionIndex)) {
                    // Joystick was let go of -> reset actuator
                    joystick.isPressed = false
                    joystick.resetActuator()
                }

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
        canvas?.drawText("FPS : $averageUPS", 100F, 200F, paint)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun update() {
        joystick.update()
        player.update()

        if (Enemy.readyToSpawn()){
            enemyList.add(Enemy(context,  player))
        }

        while (numberOfSpellsToCast > 0) {
            spellList.add(Spell(context, player))
            numberOfSpellsToCast--
        }


       //Update state of each enemy
        enemyList.forEach {
            it.update()
        }

        spellList.forEach {
            it.update()
        }

       // enemyList.removeIf { Circle.isColliding(it, player) }
       /* spellList.forEach { spell ->
            enemyList.removeIf { Circle.isColliding(it,  spell) }
        }*/

        val enemyIt : MutableIterator<Enemy> = enemyList.iterator()


        while (enemyIt.hasNext()) {

            //delete an enemy if he collides the player
            val currEnemy : Circle = enemyIt.next()
            if (Circle.isColliding(currEnemy, player)){
                enemyIt.remove()
                continue
            }

            val spellIt : MutableIterator<Spell> = spellList.iterator()

            while (spellIt.hasNext()) {

                val currSpell : Circle = spellIt.next()
                //delete the enemy and the spell if a spell collides with an ennemy
                if (Circle.isColliding(currEnemy, currSpell)){
                    enemyIt.remove()
                    spellIt.remove()
                    break
                }
            }

        }


    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawUPS(canvas)
        drawFPS(canvas)

        joystick.draw(canvas)
        player.draw(canvas)

        //Draw all enemies
        enemyList.forEach {
            it.draw(canvas)
        }

        //Draw spells
        spellList.forEach {
            it.draw(canvas)
        }


    }
}
