package com.connectandheal.hrmsdk.view.theme.hrm.screens.common.barchart

import androidx.compose.ui.graphics.Color

data class BarChartEntity(
    val hrm: List<Float>,
    val color: Color,
    val label: String? = ""
)