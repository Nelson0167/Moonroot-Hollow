package com.weirdbees.moonroothollow

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import kotlin.math.min

class PlayerController {

    val position = Vector2(100f, 100f) // this tracks players current condition

    private var targetPosition = Vector2(position) // this is where the player is trying to move forward

    private val speed = 100f // this is the players movement speed in pixels per second

    private val texture = Texture("player.png") // this is just a placeholder image

    fun update(delta: Float) {
        // If the screen was touched, set a new target position

        if (Gdx.input.justTouched()) {
            val screenX = Gdx.input.x.toFloat()
            val screenY = Gdx.input.y.toFloat()

            val worldY  = Gdx.graphics.height - screenY // Flip Y for Android coordinate system

            targetPosition.set(screenX, worldY)
        }

        val direction = Vector2(targetPosition).sub(position)

        if (direction.len() > 1f) {
            direction.nor()
            val distance = speed * delta
            position.mulAdd(direction, min(distance, direction.len()))
        }
    }

    fun getPosition(): Vector2 {
        return position
    }

    fun render(batch: SpriteBatch) {
        batch.draw(texture, position.x, position.y)
    }

    fun dispose() {
        texture.dispose()
    }
}
