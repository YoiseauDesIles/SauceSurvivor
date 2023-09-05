package com.johan.salsasurvivor.gameobject

import android.content.Context
import androidx.core.content.ContextCompat
import com.johan.salsasurvivor.GameLoop
import com.johan.salsasurvivor.R

class Spell(context : Context,
            private val spellCaster: Player,
) :
    Circle(
        context,
        ContextCompat.getColor(context, R.color.spell),
        spellCaster.getPositionX(),
        spellCaster.getPositionY(),
        40.0
    ){



    init {
        velocityX = spellCaster.getDirectionX() * MAX_SPEED
        velocityY = spellCaster.getDirectionY() * MAX_SPEED
    }

    override fun update() {
        positionX += velocityX
        positionY += velocityY
    }

    companion object{
        @JvmStatic
        val SPEED_PIXELS_PER_SECOND : Double = 200.0
        val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
    }
}
