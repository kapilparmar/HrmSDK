package com.connectandheal.hrmsdk.view.theme.hrm.screens.common.barchart

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun dpToPx(value: Dp): Float = LocalDensity.current.run { value.toPx() }
@Composable
fun textUnitToPx(value: TextUnit): Float = LocalDensity.current.run { value.toPx() }
val DefaultAxisLabelFontSize = 16.sp
val DefaultAxisThickness = 1.dp
val DefaultBarWidth = 14.dp
val DefaultGridLinesColor = Color(0xFFd9d9d9)
val DefaultAxisColor = Color(0xFFd9d9d9)
val DefaultAxisLabelColor = Color(0xFF5B5E5B)
fun withSuffix(count: Float): String? {
    if (count < 1000) return "" + count
    val exp = (Math.log(count.toDouble()) / Math.log(1000.0)).toInt()
    return String.format(
        "%.1f %c",
        count / Math.pow(1000.0, exp.toDouble()),
        "kMGTPE"[exp - 1]
    )
}