package com.connectandheal.hrmsdk.view.theme.hrm.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 40f,
    progressMax: Float = 100f,
    progressBarColor: Color = SolidRed,
    progressBarWidth: Dp = 5.dp,
    backgroundProgressBarColor: Color = TertiaryPastelGray,
    backgroundProgressBarWidth: Dp = 5.dp,
    roundBorder: Boolean = false,
    startAngle: Float = 0f,
    labelEnabled: Boolean = false,
    animationDuration: Int = 100,
    animationDelay: Int = 0
) {
    var progressValue by remember {
        mutableStateOf(progress)
    }
    val animateNumber = animateFloatAsState(
        targetValue = progressValue,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(modifier = modifier.fillMaxSize().background(color = Color.Red.copy(alpha = 0.47f),
            shape = CircleShape)) {
            val canvasSize = size.minDimension
            val radius =
                canvasSize / 2 - maxOf(backgroundProgressBarWidth, progressBarWidth).toPx() / 2

            drawCircle(
                color = backgroundProgressBarColor,
                radius = radius,
                center = size.center,
                style = Stroke(width = backgroundProgressBarWidth.toPx()+2)
            )
            drawArc(
                color = progressBarColor,
                startAngle = 270f + startAngle,
                sweepAngle = (progress / progressMax) * 360f,
                useCenter = false,
                topLeft = size.center - Offset(radius, radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(
                    width = progressBarWidth.toPx()+2,
                    cap = if (roundBorder) StrokeCap.Round else StrokeCap.Butt
                )
            )
        }
        when {
            labelEnabled -> Text(
                text = "${(animateNumber.value).toInt()}%",
                style = TextStyle_Size14_Weight700.copy(lineHeight = 20.sp),
                color = Color.White
            )
        }
    }
}