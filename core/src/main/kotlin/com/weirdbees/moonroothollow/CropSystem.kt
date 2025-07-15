package com.weirdbees.moonroothollow

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage

class CropSystem(private val worldTime: WorldTime) {
    private val plantedCrops = mutableListOf<Crop>()

    init {
        println("CropSystem Initialized.")
    }

    fun plantCrop(x: Float, y: Float) {
        plantedCrops.add(Crop(Vector2(x, y)))
        println("Planted a crop at ($x, $y)")
    }

    fun advanceGrowth() {
        for(crop in plantedCrops) {
            crop.advanceGrowth()
        }
    }

    fun printAllCrops() {
        println("Planters Log:")
        for (crop in plantedCrops) {
            println(" - (${crop.position.x}, ${crop.position.y}) -> Stage: ${crop.growthStage}")
        }
    }

    private data class Crop(
        val position: Vector2,
        var growthStage: Int = 0,
        val maxGrowthStage: Int = 3
    ) {
        fun advanceGrowth() {
            if (growthStage < maxGrowthStage) {
                growthStage++
                println("Your crop at (${position.x}, ${position.y}) grew to stage $growthStage")
            } else {
                println("Oh, look! Your crop at (${position.x}, ${position.y}) has completed its journey of growth!")
            }
        }
    }
}
