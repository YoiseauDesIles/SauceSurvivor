package com.johan.salsasurvivor

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.hardware.display.DisplayManager
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import com.johan.salsasurvivor.gameobject.Circle
import com.johan.salsasurvivor.gameobject.Enemy
import com.johan.salsasurvivor.gameobject.Player
import com.johan.salsasurvivor.gameobject.Spell
import com.johan.salsasurvivor.gamepanel.GameOver
import com.johan.salsasurvivor.gamepanel.Joystick
import com.johan.salsasurvivor.gamepanel.Performance

class Game(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private lateinit var gameLoop : GameLoop
    private lateinit var player : Player
    private lateinit var joystick : Joystick
    private lateinit var gameOver : GameOver
    private lateinit var performance : Performance
    private lateinit var gameDisplay : GameDisplay
    private var enemyList : MutableList<Enemy> = mutableListOf()
    private var spellList : MutableList<Spell> = mutableListOf()
    private var joystickPointerId : Int = 0
    private var numberOfSpellsToCast : Int = 0

    init {
        //get surface holder and add callback
        val surfaceHolder : SurfaceHolder = holder
        surfaceHolder.addCallback(this)

        gameLoop = GameLoop(this, surfaceHolder)

        //initialize game panels
        performance = Performance(context, gameLoop)
        gameOver = GameOver(context)
        joystick = Joystick(275, 700, 70, 40)

         //initialize game objects
        player = Player(context, joystick,500.0, 500.0, 20.0)

        //initialize game display and center it around the player
        val displayMetrics : DisplayMetrics = DisplayMetrics()
        val display = (context as Activity).windowManager.defaultDisplay

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val displayMetrics = DisplayMetrics()
            val displayId = display.displayId
            val displayManager = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
            displayManager.getDisplay(displayId)?.getRealMetrics(displayMetrics)
        } else {
            display.getMetrics(displayMetrics)
        }

        gameDisplay = GameDisplay(player, displayMetrics.widthPixels, displayMetrics.heightPixels)
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
        Log.d("Game.java", "surfaceCreated()")

        if (gameLoop.state.equals(Thread.State.TERMINATED)){
            gameLoop = GameLoop(this, holder)
        }
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("Game.java", "surfaceChanged()")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d("Game.java", "surfaceDestroyed()")
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun update() {

        //Stop updating the game if the player is dead
        if (player.getHealthPoints() <= 0) {
            return
        }

        //Update game state
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

        //Check for enemy collision between the player or the spell and the enemy
        val enemyIt : MutableIterator<Enemy> = enemyList.iterator()
        while (enemyIt.hasNext()) {

            //delete an enemy if he collides the player
            val currEnemy : Circle = enemyIt.next()
            if (Circle.isColliding(currEnemy, player)){
                enemyIt.remove()
                player.setHealthPoint(player.getHealthPoints() - 1)
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

        gameDisplay.update()

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        performance.drawUPS(canvas)
        performance.drawFPS(canvas)

        //draw game objects
        player.draw(canvas, gameDisplay)

        //Draw all enemies
        enemyList.forEach {
            it.draw(canvas, gameDisplay)
        }

        //Draw spells
        spellList.forEach {
            it.draw(canvas, gameDisplay)
        }


        //draw game panels
        joystick.draw(canvas)
        performance.draw(canvas)

        // Draw Game over
        if (player.getHealthPoints() <= 0) {
            gameOver.draw(canvas)
        }

    }

    public fun pause() {
        gameLoop.stopLoop()
    }
}
