package com.connectandheal.hrmsdk.domain

import androidx.compose.ui.graphics.Color

data class HRMResultModel(
    val hrValue: Int = 92,
    val color: Color = Color.Green,
    val activities: List<ActivityTypeType> = mutableListOf(
        ActivityTypeType.RESTING,
        ActivityTypeType.WALKING, ActivityTypeType.EXERCISING, ActivityTypeType.SLEEPING,
        ActivityTypeType.STRESSED, ActivityTypeType.OTHER
    ),
    val heartRate: HeartRate = HeartRate.HEALTHY,
    val hrmValues: List<Int> = mutableListOf(30, 50, 90, 120)
)

enum class HeartRate(val heartRate: String) {
    LOW("Low"),
    HIGH("High"),
    HEALTHY("Healthy");

    companion object {
        private val VALUES = HeartRate.values()
        fun getActivityType(value: Int) = VALUES.firstOrNull { it.heartRate.equals(value) }
    }
}

enum class ActivityTypeType(val type: String) {
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