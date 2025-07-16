package com.weirdbees.moonroothollow

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class GameScreen(
    private val game: MoonrootHollowGame
) : ScreenAdapter() {
    private val worldTime = WorldTime()
    private val cropSystem = CropSystem(worldTime)

    private val batch: SpriteBatch
        get() = game.batch

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var cropAtlas: TextureAtlas

    private val assetManager get() = game.assetManager

    companion object {
        private const val WORLD_WIDTH = 800f
        private const val WORLD_HEIGHT = 480f
    }

    override fun show() {
        // 1) Load Your Crop Atlas
        if(!assetManager.isLoaded("crops.atlas", TextureAtlas::class.java)) {
            assetManager.load("crops.atlas", TextureAtlas::class.java)
        }
        assetManager.finishLoading()
        cropAtlas = assetManager.get("crops.atlas", TextureAtlas::class.java)

        // 2) Set Up the Camera and Viewport
        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0f)

        // 3) Plant On Touch/Click
        Gdx.input.inputProcessor = object  : InputAdapter() {
            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                val worldCoords = viewport.unproject(Vector2(screenX.toFloat(), screenY.toFloat()))
                cropSystem.plantCrop(worldCoords.x, worldCoords.y)
                return true
            }
        }
    }

    override fun render(delta: Float) {
        //Advance Time and Growth
        worldTime.update(delta)
        cropSystem.advanceGrowth()

        // Clear Screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Draw All Crops
        batch.projectionMatrix = camera.combined
        batch.begin()

        for (crop in cropSystem.plantedCrops) {
            val regionName = "crop_stage_${crop.growthStage}"
            val region = cropAtlas.findRegion(regionName)

            if (region != null) {
                batch.draw(region, crop.position.x, crop.position.y)
            }
        }

        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun pause() {
        // No-Op
    }

    override fun resume() {
        // No-Op
    }

    override fun hide() {
        Gdx.input.inputProcessor = null
    }

    override fun dispose() {
        assetManager.dispose()
    }
}
