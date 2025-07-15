package com.weirdbees.moonroothollow

class WorldTime {
    var day = 1
        private set

    var timeOfDay: TimeOfDay = TimeOfDay.MORNING
        private set

    private var timeAccumulator = 0f
    private val secondsPerTimeBlock = 30f

    fun update(delta: Float) {
        timeAccumulator += delta

        if (timeAccumulator >= secondsPerTimeBlock) {
            timeAccumulator = 0f
            progressTime()
            println(getDisplayText())
        }
    }

    fun progressTime() {
        timeOfDay = when (timeOfDay) {
            TimeOfDay.MORNING -> TimeOfDay.MIDDAY
            TimeOfDay.MIDDAY -> TimeOfDay.EVENING
            TimeOfDay.EVENING -> TimeOfDay.NIGHT
            TimeOfDay.NIGHT -> {
                advanceToNextDay()
                TimeOfDay.MORNING
            }
        }
    }

    private fun advanceToNextDay() {
        day += 1
    }

    fun forceSleep() {
        timeOfDay = TimeOfDay.MORNING
        day += 1
        timeAccumulator = 0f
        println("I think you fell asleep...in an uncomfortable spot mind you! You woke up on the morning of Day: $day")
    }

    fun getDisplayText(): String {
        return "Day $day - ${timeOfDay.name.lowercase().replaceFirstChar { it.uppercase() }}"
    }
}
