package com.johan.salsasurvivor.map

import android.graphics.Canvas
import android.graphics.Rect
import com.johan.salsasurvivor.graphics.Sprite
import com.johan.salsasurvivor.graphics.SpriteSheet

class GrassTile(spriteSheet: SpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {

    val sprite : Sprite = spriteSheet.getGrassSprite()

    override fun draw(canvas: Canvas?) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top)
    }

}
