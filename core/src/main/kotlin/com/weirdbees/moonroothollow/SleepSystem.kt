package com.weirdbees.moonroothollow

import com.badlogic.gdx.math.Vector2

class SleepSystem(private val worldTime: WorldTime) {
    private val sleepZones: MutableList<Vector2> = mutableListOf() // List of positions the player can sleep at

    private val activationRadius = 32f // Distance to trigger sleep (will tweak as needed)

    fun addSleepZone(x: Float, y:Float) {
        sleepZones.add(Vector2(x, y))
    }

    fun checkSleepTrigger(playerPosition: Vector2): Boolean {
        for (zone in sleepZones) {
            if (playerPosition.dst(zone) <= activationRadius) {
                triggerSleep()
                return true
            }
        }
        return false
    }

    private fun triggerSleep() {
        println("Sleep well...see you in the morning")
    }

    fun clearSleepZones() {
        sleepZones.clear()
    }
}
