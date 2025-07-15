package com.weirdbees.moonroothollow


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class OverworldScreen(private val game: MoonrootHollowGame) : Screen {

    // Allow the camera to see the world
    private val camera = OrthographicCamera()
    private val batch: SpriteBatch = game.batch

    private val background = Texture("farm_background.png") // Placeholder background image, will be updated.

    private val player = PlayerController() // Player controller handles the movement and drawing

    private val worldTime = WorldTime()
    private val sleepSystem = SleepSystem(worldTime)

    override fun show() {
        camera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()) // sets up camera with screen dimensions (world units)

        sleepSystem.addSleepZone(500f, 300f)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.3f, 0.6f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        player.update(delta) // update logic (movement, input, etc)
        worldTime.update(delta)

        sleepSystem.checkSleepTrigger(player.getPosition())

        // Set up camera and draw everything
        camera.update()
        batch.projectionMatrix = camera.combined

        batch.begin()
        batch.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        player.render(batch)
        batch.end()
    }

    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}

    override fun dispose() {
        background.dispose()
        player.dispose()
    }
}
