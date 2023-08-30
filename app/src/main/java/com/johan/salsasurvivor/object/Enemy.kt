package com.johan.salsasurvivor.`object`

import android.content.Context
import androidx.core.content.ContextCompat
import com.johan.salsasurvivor.GameLoop
import com.johan.salsasurvivor.R

class Enemy(context : Context,
            private val player: Player,
            positionX : Double,
            positionY : Double,
            radius : Double ) :
    Circle(
        context,
        ContextCompat.getColor(context, R.color.player),
        positionX,
        positionY,
        radius)  {

    private val SPEED_PIXELS_PER_SECONDS = Player.SPEED_PIXELS_PER_SECOND * 0.6
    private val MAX_SPEED : Double = SPEED_PIXELS_PER_SECONDS / GameLoop.MAX_UPS


    init {
        paint.color = ContextCompat.getColor(context, R.color.enemy)
    }

    override fun update() {

        //Calculate vector from enemy to player (in x and y)
        val distanceToPlayerX : Double = player.posX - positionX
        val distanceToPlayerY : Double = player.posY - positionY

        //Calculate (absolute) distance between enemy and player
        val distanceToPlayer : Double = getDistanceBetweenObjects(this, player)

        //Calculate direction from enemy to player
        val directionX : Double = distanceToPlayerX / distanceToPlayer
        val directionY : Double = distanceToPlayerY / distanceToPlayer

        //Set velocity in the direction to the player
        if (distanceToPlayer > 0) {
            velocityX = directionX * MAX_SPEED
            velocityY = directionY * MAX_SPEED
        }else {
            velocityX = 0.0
            velocityY = 0.0
        }

        positionX += velocityX
        positionY += velocityY
        //update the position of the enemy
    }

}
