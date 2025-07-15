package com.weirdbees.moonroothollow

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MoonrootHollowGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()

        setScreen(MainMenuScreen(this)) // making next
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}
