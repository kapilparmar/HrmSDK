package com.connectandheal.hrmsdk.view.theme.hrm.screens.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey500
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SolidLemon
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SolidOrange
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SolidRed
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelGray
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.bodyBoldExtraSmall

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
        mutableStateOf(0f)
    }
    val animateNumber = animateFloatAsState(
        targetValue = progressValue,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )
    progressValue = progress

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val canvasSize = size.minDimension
            val radius =
                canvasSize / 2 - maxOf(backgroundProgressBarWidth, progressBarWidth).toPx() / 2

            drawCircle(
                color = backgroundProgressBarColor,
                radius = radius,
                center = size.center,
                style = Stroke(width = backgroundProgressBarWidth.toPx())
            )
            drawArc(
                color = progressBarColor,
                startAngle = 270f + startAngle,
                sweepAngle = (progress / progressMax) * 360f,
                useCenter = false,
                topLeft = size.center - Offset(radius, radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(
                    width = progressBarWidth.toPx(),
                    cap = if (roundBorder) StrokeCap.Round else StrokeCap.Butt
                )
            )
        }
        when {
            labelEnabled -> Text(
                text = "${(animateNumber.value).toInt()}%",
                style = TextStyle_Size14_Weight700.copy(lineHeight = 20.sp)
            )
        }
    }
}

@Composable
fun LinearRoundedProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    isLabelEnabled: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = TertiaryPastelGray,
    labelMinWidth: Dp = 24.dp
) {
    Column {
        if (isLabelEnabled) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val offset = getProgressBarOffset(
                    value = progress,
                    valueRange = 0f..1f,
                    boxWidth = maxWidth,
                    labelWidth = labelMinWidth + 8.dp
                )
                ProgressBarLabel(
                    label = "${(progress * 100).toInt()}%",
                    minWidth = labelMinWidth,
                    modifier = Modifier
                        .padding(start = offset)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
        Canvas(
            modifier
                .progressSemantics(progress)
                .height(4.dp)
                .focusable()
        ) {
            val strokeWidth = size.height
            drawRoundedLinearIndicatorBackground(trackColor, strokeWidth)
            drawRoundedLinearIndicator(0f, progress, color, strokeWidth)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressBarLabel(
    modifier: Modifier = Modifier,
    label: String,
    minWidth: Dp,
    colors: CardColors = CardDefaults.cardColors(containerColor = Color.White)
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = colors,
        modifier = modifier,
        content = {
            Text(
                text = label,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyBoldExtraSmall,
                color = Grey500,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 1.dp)
            )
        }
    )
}

private fun getProgressBarOffset(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    boxWidth: Dp,
    labelWidth: Dp
): Dp {
    val coerced = value.coerceIn(valueRange.start, valueRange.endInclusive)
    val positionFraction = calculateFraction(valueRange.start, valueRange.endInclusive, coerced)

    return (boxWidth - labelWidth) * positionFraction
}

fun calculateFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)

fun DrawScope.drawRoundedLinearIndicatorBackground(
    color: Color,
    strokeWidth: Float
) = drawRoundedLinearIndicator(0f, 1f, color, strokeWidth)

private fun DrawScope.drawRoundedLinearIndicator(
    startFraction: Float,
    endFraction: Float,
    color: Color,
    strokeWidth: Float
) {
    val width = size.width
    val height = size.height
    // Start drawing from the vertical center of the stroke
    val yOffset = height / 2

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width

    // Progress line
    drawLine(
        color = color,
        start = Offset(barStart, yOffset),
        end = Offset(barEnd, yOffset),
        strokeWidth = strokeWidth,
        cap = when {
            barEnd>0 -> StrokeCap.Round
            else -> Stroke.DefaultCap
        }
    )
}

@Composable
@Preview(showBackground = true)
fun ProgressIndicatorPreview() {
    Column(modifier = Modifier.padding(20.dp)) {
        CircularProgressBar(
            modifier = Modifier
                .size(64.dp),
            roundBorder = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        CircularProgressBar(
            modifier = Modifier
                .size(64.dp),
            roundBorder = true,
            labelEnabled = true
        )
        Spacer(modifier = Modifier.height(10.dp))

        LinearRoundedProgressIndicator(
            progress = 0f,
            isLabelEnabled = true,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(5.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            color = SolidLemon,
            trackColor = TertiaryPastelGray
        )
        Spacer(modifier = Modifier.height(10.dp))

        LinearRoundedProgressIndicator(
            progress = 0.65f,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(5.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            color = SolidOrange,
            trackColor = TertiaryPastelGray
        )
    }
}
