package com.johan.salsasurvivor.graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect

class Sprite(private val spriteSheet: SpriteSheet, private val rect: Rect) {

    public fun draw(canvas: Canvas?, x : Int, y : Int ) {
        canvas?.drawBitmap(
            spriteSheet.getBitmap(),
            rect,
            Rect(x, y,x+getWidth(), y+getHeight()),
            null
        )
    }

    fun getHeight(): Int {
        return rect.height()
    }

    fun getWidth(): Int {
        return rect.width()
    }
}
