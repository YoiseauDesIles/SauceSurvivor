package com.johan.salsasurvivor.gameobject

class PlayerState(private val player: Player) {

    enum class State{
        NOT_MOVING,
        STARTED_MOVING,
        IS_MOVING
    }

    private var state : State = State.NOT_MOVING

    fun getState() = state

    fun update() {

        when (state) {
            State.NOT_MOVING -> {
                if (player.getVelocityX() != 0.0 || player.getVelocityY() != 0.0) {
                    state = State.STARTED_MOVING
                }
            }
            State.STARTED_MOVING -> {
                if (player.getVelocityX() != 0.0 || player.getVelocityY() != 0.0) {
                    state = State.IS_MOVING
                }
            }
            State.IS_MOVING -> {
                if (player.getVelocityX() == 0.0 && player.getVelocityY() == 0.0) {
                    state = State.NOT_MOVING
                }
            }
            else -> {}
        }
    }




}