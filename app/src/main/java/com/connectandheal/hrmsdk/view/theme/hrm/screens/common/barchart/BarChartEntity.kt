package com.connectandheal.hrmsdk.view.theme.hrm.screens.common.barchart

import androidx.compose.ui.graphics.Color
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SecondaryMarineBlue

data class BarChartEntity(
    val hrm: List<Float>,
    val color: Color = SecondaryMarineBlue,
    val label: String? = ""
)