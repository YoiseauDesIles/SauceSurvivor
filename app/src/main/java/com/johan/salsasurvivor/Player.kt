package com.johan.salsasurvivor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat

class Player(context : Context, private var positionX : Double, private var positionY : Double, private var radius : Double ){



    public fun setPosition(positionX: Double, positionY : Double) {
        this.positionX = positionX
        this.positionY = positionY

    }
    private var paint : Paint = Paint()

    init {
        paint.color = ContextCompat.getColor(context, R.color.player)
    }

    fun draw(canvas: Canvas?) {

        canvas?.drawCircle(positionX.toFloat(), positionY.toFloat(), radius.toFloat(), paint)
    }

    fun update() {

    }

}
