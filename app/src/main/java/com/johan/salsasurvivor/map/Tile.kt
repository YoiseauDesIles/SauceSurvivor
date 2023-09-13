package com.johan.salsasurvivor.map

import android.graphics.Canvas
import android.graphics.Rect
import com.johan.salsasurvivor.graphics.SpriteSheet

abstract class Tile(protected val mapLocationRect: Rect) {

    enum class TileType {
        WATER_TILE,
        LAVA_TILE,
        GROUND_TILE,
        GRASS_TILE,
        TREE_TILE
    }

    companion object{

        fun getTile(idxTileType: Int, spriteSheet : SpriteSheet, mapLocationRect : Rect): Tile? {

            val tile : Tile? = when (TileType.values()[idxTileType]) {
                TileType.WATER_TILE -> WaterTile(spriteSheet, mapLocationRect)
                TileType.LAVA_TILE -> LavaTile(spriteSheet, mapLocationRect)
                TileType.GROUND_TILE -> GroundTile(spriteSheet, mapLocationRect)
                TileType.GRASS_TILE -> GrassTile(spriteSheet, mapLocationRect)
                TileType.TREE_TILE -> TreeTile(spriteSheet, mapLocationRect)
                else -> null

            }

            return tile
        }
    }

    abstract fun draw(canvas: Canvas?)

}
