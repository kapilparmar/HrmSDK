package com.connectandheal.hrmsdk.domain

import androidx.compose.ui.graphics.Color
import com.connectandheal.hrmsdk.R

data class HRMResultModel(
    val hrValue: Int = 92,
    val color: Color = Color.Green,
    val activities: List<Activities> = getActivities(),
    val heartRateZone: HeartRateZone = HeartRateZone.HEALTHY,
    val hrmValues: List<Int> = mutableListOf(30, 50, 90, 120)
)
data class Activities(
    val  activity : ActivityType,
    val image : Int
)
 fun getActivities ():List<Activities>{
    return mutableListOf(
        Activities(ActivityType.RESTING, R.drawable.ic_resting),
        Activities(ActivityType.WALKING, R.drawable.ic_walking),
        Activities(ActivityType.EXERCISING, R.drawable.ic_excersice),
        Activities(ActivityType.SLEEPING, R.drawable.ic_sleeping),
        Activities(ActivityType.STRESSED, R.drawable.ic_stressed),
        Activities(ActivityType.OTHER, R.drawable.ic_other)
        )
}
enum class HeartRateZone(val heartRate: String) {
    LOW("Low"),
    HIGH("High"),
    HEALTHY("Healthy");

    companion object {
        private val VALUES = HeartRateZone.values()
        fun getActivityType(value: Int) = VALUES.firstOrNull { it.heartRate.equals(value) }
    }
}

enum class ActivityType(val type: String) {
    RESTING("Resting"),
    WALKING("Walking"),
    EXERCISING("Exercising"),
    SLEEPING("Sleeping"),
    STRESSED("Stressed"),
    OTHER("Other");

    companion object {
        private val VALUES = values()
        fun getActivityType(value: Int) = VALUES.firstOrNull { it.type.equals(value) }
    }

}