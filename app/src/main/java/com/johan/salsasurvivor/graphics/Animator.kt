package com.johan.salsasurvivor.graphics

import android.graphics.Canvas
import com.johan.salsasurvivor.GameDisplay
import com.johan.salsasurvivor.gameobject.Player
import com.johan.salsasurvivor.gameobject.PlayerState
import java.util.Collections.max

class Animator(private val playerSpriteArray : MutableList<Sprite>) {


    private var updateBeforeNextMoveFrame: Int = 0
    private val idxNotMovingFrame: Int = 0
    private var idxMovingFrame: Int = 1


    fun draw(canvas: Canvas?, gameDisplay: GameDisplay, player: Player) {

        when (player.getPlayerState().getState()){
            PlayerState.State.NOT_MOVING -> {
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idxNotMovingFrame])
            }
            PlayerState.State.STARTED_MOVING -> {
                updateBeforeNextMoveFrame = MAX_UPDATE_BEFORE_NEXT_MOVE_FRAME
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idxMovingFrame])
            }
            PlayerState.State.IS_MOVING -> {
                updateBeforeNextMoveFrame--
                if (updateBeforeNextMoveFrame == 0){
                    updateBeforeNextMoveFrame = Companion.MAX_UPDATE_BEFORE_NEXT_MOVE_FRAME
                    toggleIdxMoveFrame()

                }
                drawFrame(canvas, gameDisplay, player, playerSpriteArray[idxMovingFrame])
            }
            else -> {}
        }
    }

    private fun toggleIdxMoveFrame() {
        idxMovingFrame = if (idxMovingFrame == 1) 2 else 1
    }

    fun drawFrame(canvas: Canvas?, gameDisplay: GameDisplay, player: Player, sprite: Sprite) {

        sprite.draw(
            canvas,
            gameDisplay.gameToDisplayCoordinatesX(player.getPositionX()).toInt() - sprite.getWidth()/2,
            gameDisplay.gameToDisplayCoordinatesY(player.getPositionY()).toInt() - sprite.getHeight()/2
        )
    }

    companion object {
        private const val MAX_UPDATE_BEFORE_NEXT_MOVE_FRAME: Int = 5
    }
}
