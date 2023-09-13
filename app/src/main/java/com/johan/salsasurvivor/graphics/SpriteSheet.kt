package com.johan.salsasurvivor.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.johan.salsasurvivor.R

class SpriteSheet(context: Context) {

    companion object {
        private const val SPRITE_WIDTH_PIXELS = 64
        private const val SPRITE_HEIGHT_PIXELS = 64
    }

    private var bitmap : Bitmap


    init {

        val bitmapOptions: BitmapFactory.Options? = BitmapFactory.Options()
        bitmapOptions?.inScaled = false
        bitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.sprite_sheet,
            bitmapOptions)
    }

    fun getPlayerSpriteArray() : MutableList<Sprite> {

        val spriteList = mutableListOf<Sprite>()

        repeat(3) {
            spriteList.add(it, Sprite(this, Rect(it * 64, 0, (it+1) * 64,  64)))
        }

        return spriteList
    }

    fun getBitmap(): Bitmap {
        return bitmap
    }

    fun getWaterSprite(): Sprite =  getSpriteByIndex(1, 0)
    fun getLavaSprite(): Sprite =  getSpriteByIndex(1, 1)
    fun getGroundSprite(): Sprite  = getSpriteByIndex(1, 2)
    fun getGrassSprite(): Sprite =  getSpriteByIndex(1, 3)
    fun getTreeSprite(): Sprite =  getSpriteByIndex(1, 4)




    private fun getSpriteByIndex(idxRow: Int, idxColumn: Int) : Sprite {

        val currSpriteRect = Rect(
            idxColumn * SPRITE_WIDTH_PIXELS,
            idxRow * SPRITE_HEIGHT_PIXELS,
            (idxColumn + 1 ) * SPRITE_WIDTH_PIXELS,
            (idxRow + 1) * SPRITE_HEIGHT_PIXELS,
        )

        return Sprite(this, currSpriteRect)
    }



}