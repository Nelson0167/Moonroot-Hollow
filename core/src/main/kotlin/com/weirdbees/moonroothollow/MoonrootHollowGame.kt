package com.weirdbees.moonroothollow

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MoonrootHollowGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont
    lateinit var assetManager : AssetManager

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()

        assetManager = AssetManager()
        setScreen(LoadingScreen(this))
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        assetManager.dispose()
        super.dispose()
    }
}
