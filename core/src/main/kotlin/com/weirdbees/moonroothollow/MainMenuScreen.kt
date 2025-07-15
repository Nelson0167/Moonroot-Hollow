package com.weirdbees.moonroothollow

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20

class MainMenuScreen(private val game: MoonrootHollowGame) : Screen {
    override fun show() {}

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        game.batch.begin()
        game.font.draw(game.batch, "Welcome to Moonroot Hollow!", 100f, 150f)
        game.batch.end()
    }

    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    override fun dispose() {}
}
