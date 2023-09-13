package com.johan.salsasurvivor.gameobject

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.johan.salsasurvivor.GameDisplay
import com.johan.salsasurvivor.GameLoop
import com.johan.salsasurvivor.gamepanel.Joystick
import com.johan.salsasurvivor.R
import com.johan.salsasurvivor.Utils
import com.johan.salsasurvivor.gamepanel.HealthBar
import com.johan.salsasurvivor.graphics.Animator
import com.johan.salsasurvivor.graphics.Sprite

class Player(
    context: Context,
    private val joystick: Joystick,
    positionX: Double,
    positionY: Double,
    radius: Double,
    private val animator: Animator
) :
    Circle(
        context,
        ContextCompat.getColor(context, R.color.player),
        positionX,
        positionY,
        radius){

    private val healthBar : HealthBar = HealthBar(context, this)
    private val MAX_SPEED : Double = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
    private var healthPoints : Int = Player.MAX_HEALTH_POINTS
    private var playerState : PlayerState = PlayerState(this)


    init {
        paint.color = ContextCompat.getColor(context, R.color.player)
    }


    override fun update() {
        //Update velocity based on actuator of joystick
        velocityX = joystick.actuatorX * MAX_SPEED
        velocityY = joystick.actuatorY * MAX_SPEED

        //update position
        positionX += velocityX
        positionY += velocityY

        //update direction from velocity
        if (velocityX != 0.0 || velocityY != 0.0) {
            //Normalise velocity to get direction(unit vector of velocity)
            val distance : Double = Utils.getDistanceBetweenPoints(0.0, 0.0, velocityX, velocityY)
            directionX = velocityX / distance
            directionY = velocityY / distance
        }

        playerState.update()
    }

    public override fun draw(canvas: Canvas?, gameDisplay: GameDisplay) {

        animator.draw(canvas, gameDisplay, this);
        healthBar.draw(canvas, gameDisplay)

    }

    fun getHealthPoints(): Int {
        return this.healthPoints
    }

    fun setHealthPoint(healthPoints: Int) {
        if (healthPoints >= 0)
            this.healthPoints = healthPoints
    }

    fun getPlayerState() = playerState

    companion object{
        public const val SPEED_PIXELS_PER_SECOND = 400.0
        public const val MAX_HEALTH_POINTS = 10
    }

}
