package com.connectandheal.hrmsdk.domain

import android.os.Parcelable
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient(
    val patientName: String,
    val patientId: String
) : Parcelable

enum class FilterType {
    DAILY, WEEKLY, MONTHLY, YEARLY
}

data class HeartRateSummaryType(
    val value: Int,
    val heartRateZone: HeartRateZone
)

data class HeartRateSummaryModel(
    val title: String = "Heart Rate Summary",
    val lowest: HeartRateSummaryType,
    val average: HeartRateSummaryType,
    val highest: HeartRateSummaryType
)

data class PreviousReadingItem(
    val id: String,
    val category: String,
    val measuredOn: String,
    val heartRateValue: String,
    val heartRateZone: HeartRateZone,
    val note: String = ""
)

object NavBarConstants {
    val SAFE_AREA_HEIGHT = 120.dp
}

