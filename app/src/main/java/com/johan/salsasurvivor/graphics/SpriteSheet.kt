package com.johan.salsasurvivor.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.johan.salsasurvivor.R

class SpriteSheet(context: Context) {

    private var bitmap : Bitmap

    init {

        val bitmapOptions: BitmapFactory.Options? = BitmapFactory.Options()
        bitmapOptions?.inScaled = false
        bitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.sprite_sheet,
            bitmapOptions)
    }

    public fun getPlayerSprite() : Sprite {
        return Sprite(this, Rect(0, 0, 64, 64))
    }

    fun getBitmap(): Bitmap {
        return bitmap
    }
}