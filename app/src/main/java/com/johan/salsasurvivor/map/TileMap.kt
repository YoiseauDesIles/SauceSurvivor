package com.johan.salsasurvivor.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.johan.salsasurvivor.GameDisplay
import com.johan.salsasurvivor.graphics.SpriteSheet
import com.johan.salsasurvivor.map.MapLayout.Companion.NUMBER_OF_COLUMN_TILES
import com.johan.salsasurvivor.map.MapLayout.Companion.NUMBER_OF_ROW_TILES
import com.johan.salsasurvivor.map.MapLayout.Companion.TILE_HEIGHT_PIXELS
import com.johan.salsasurvivor.map.MapLayout.Companion.TILE_WIDTH_PIXELS

class TileMap(private val spriteSheet: SpriteSheet) {

    private lateinit var mapBitmap: Bitmap
    private lateinit var tilemap: Array<Array<Tile?>>
    private val mapLayout : MapLayout = MapLayout()

    init {
        initializeTileMap()
    }

    private fun initializeTileMap() {

        val layout = mapLayout.getLayout()

        tilemap = Array(NUMBER_OF_ROW_TILES) { Array(NUMBER_OF_COLUMN_TILES) { null } }

        for (row in 0 until NUMBER_OF_ROW_TILES ) {
            for (col in 0 until NUMBER_OF_COLUMN_TILES ){

                tilemap[row][col] = Tile?.getTile(
                    layout[row][col],
                    spriteSheet,
                    getRectByIndex(row, col))

            }
        }

        val config : Bitmap.Config = Bitmap.Config.ARGB_8888
        mapBitmap = Bitmap.createBitmap(
            NUMBER_OF_COLUMN_TILES * TILE_WIDTH_PIXELS,
            NUMBER_OF_ROW_TILES * TILE_HEIGHT_PIXELS,
            config
        )

        val mapCanvas : Canvas? = Canvas(mapBitmap)

        for (row in 0 until NUMBER_OF_ROW_TILES) {
            for (col in 0 until NUMBER_OF_COLUMN_TILES){

                tilemap[row][col]?.draw(mapCanvas)

            }
        }

        println()

    }

    private fun getRectByIndex(idxRow: Int, idxCol: Int): Rect {

        return Rect(
            idxCol * TILE_WIDTH_PIXELS,
            idxRow * TILE_HEIGHT_PIXELS,
            (idxCol + 1 ) * TILE_WIDTH_PIXELS,
            (idxRow + 1) * TILE_HEIGHT_PIXELS,
        )

    }

    fun draw(canvas: Canvas?, gameDisplay: GameDisplay) {
            canvas?.drawBitmap(
                mapBitmap,
                gameDisplay.getGameRect(),
                gameDisplay.DISPLAY_RECT,
                null
            )

    }

}


