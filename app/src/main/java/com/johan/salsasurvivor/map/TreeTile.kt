package com.johan.salsasurvivor.map

import android.graphics.Canvas
import android.graphics.Rect
import com.johan.salsasurvivor.graphics.Sprite
import com.johan.salsasurvivor.graphics.SpriteSheet

class TreeTile(spriteSheet: SpriteSheet, mapLocationRect: Rect) : Tile(mapLocationRect) {

    private val grassSprite : Sprite = spriteSheet.getGrassSprite()
    private val treeSprite : Sprite = spriteSheet.getTreeSprite()

    override fun draw(canvas: Canvas?) {

        grassSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top)
        treeSprite.draw(canvas, mapLocationRect.left, mapLocationRect.top)
    }

}
