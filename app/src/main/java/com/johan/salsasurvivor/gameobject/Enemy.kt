package com.johan.salsasurvivor.gameobject

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
        ContextCompat.getColor(context, R.color.enemy),
        positionX,
        positionY,
        radius)  {

    private val SPEED_PIXELS_PER_SECONDS = Player.SPEED_PIXELS_PER_SECOND * 0.2
    private val MAX_SPEED : Double = SPEED_PIXELS_PER_SECONDS / GameLoop.MAX_UPS


    constructor(context: Context, player: Player) : this(
        context,
        player,
        Math.random() * 1000,
        Math.random() * 1000,
        30.0) {

    }

    override fun update() {

        //Calculate vector from enemy to player (in x and y)
        val distanceToPlayerX : Double = player.getPositionX() - positionX
        val distanceToPlayerY : Double = player.getPositionY() - positionY

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

        //update the position of the enemy
        positionX += velocityX
        positionY += velocityY

    }

    companion object {

        private val UPDATES_PER_SPAWN : Double = GameLoop.MAX_UPS
        private var SPAWNS_PER_MINUTE : Double = 20.0
        private var SPAWNS_PER_SECOND : Double = SPAWNS_PER_MINUTE / 60.0
        private var updatesUntilNextSpawn : Double = UPDATES_PER_SPAWN/SPAWNS_PER_SECOND

        @JvmStatic
        public fun readyToSpawn() : Boolean {

            if (updatesUntilNextSpawn <= 0) {
                updatesUntilNextSpawn += UPDATES_PER_SPAWN
                return true
            } else {
                updatesUntilNextSpawn--
                return false
            }
        }
    }

}
