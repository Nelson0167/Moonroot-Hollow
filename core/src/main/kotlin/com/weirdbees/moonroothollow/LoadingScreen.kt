package com.weirdbees.moonroothollow

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.weirdbees.moonroothollow.MainMenuScreen


class LoadingScreen(private val game: MoonrootHollowGame
) : ScreenAdapter() {

    // Shared AssetManager from Game subclass
    private val assetManager: AssetManager
        get() = game.assetManager

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var stage: Stage
    private lateinit var loadingBar: ProgressBar
    private lateinit var skin: Skin

    companion object {
        private const val WORLD_WIDTH = 800f
        private const val WORLD_HEIGHT = 480f
    }

    override fun show() {
        // 1) Prepare camera and viewport for the stage
        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        stage = Stage(viewport)

        // 2) Queue Assets to Load
        assetManager.load("crops.atlas", com.badlogic.gdx.graphics.g2d.TextureAtlas::class.java)
        assetManager.load("uiskin.json", Skin::class.java)
        // Add any other assets here: tilemaps, fonts, audio, etc.

        // 3) Create a simple skin and progress bar after minimal loading
        assetManager.finishLoading()
        skin = assetManager.get("uiskin.json", Skin::class.java)

        val barStyle = ProgressBar.ProgressBarStyle().apply {
            // Background Drawable for the Empty Bar
            background = skin.getDrawable("default-round")
            // KnobBefore Drawable to Fill the Bar as progress Increases
            knobBefore = skin.getDrawable("default-round-filled")
        }
        loadingBar = ProgressBar(0f, 1f, 0.01f, false, barStyle).apply {
            // Set size relative to screen width
            width = WORLD_WIDTH * 0.8f
            height = 30f

            // Center on Screen
            setPosition((WORLD_WIDTH - width) / 2f, WORLD_HEIGHT / 2f)
        }

        // 5) Add the ProgressBar to a Table for Easy Centering
        Table().apply {
            setFillParent(true)
            add(loadingBar).width(loadingBar.width).height(loadingBar.height)
        }.also { table ->
            stage.addActor(table)
        }

        // 6) Direct Input (e.g back key) to our UI stage
        Gdx.input.inputProcessor = stage
    }

    // Called Once Per Frame; Update Loading and UI
    override fun render(delta: Float) {
        // Queue Continues Loading in the Background; update() returns true when all assets loaded
        if (assetManager.update()) {
            // All Required Assets are Ready; switch to the main menu
            game.screen = MainMenuScreen(game)
            return // Exit Early to Avoid Drawing this Frame
        }

        // Update Progress Value [0..1] On Our Loading Bar
        loadingBar.value = assetManager.progress

        // Clear the Screen to Black Before Drawing UI
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Let the Stage Update (animations, etc.) and Draw the UI
        stage.act(delta)
        stage.draw()
    }

    // Handle Window Resizing by Updating our Viewport - THIS IS AN ANDROID MOBILE GAME WHY IS IT WINDOW RESIZING???
    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true) // center camera after resize
    }

    // Called When this Screen is No Longer Visible; Clear Input
    override fun hide() {
        Gdx.input.inputProcessor = null
    }

    // Clean Up Screen-Specific Resources (Stage, Skin)
    override fun dispose() {
        stage.dispose()
        skin.dispose()
    }
}
