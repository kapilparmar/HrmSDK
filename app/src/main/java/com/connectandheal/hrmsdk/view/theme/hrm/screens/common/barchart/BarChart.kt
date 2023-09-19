package com.connectandheal.hrmsdk.view.theme.hrm.screens.common.barchart

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun BarChart(
    modifier: Modifier,
    barChartData: List<BarChartEntity>,
    verticalAxisValues: List<Float>,
    axisColor: Color = DefaultAxisColor,
    horizontalAxisLabelColor: Color = DefaultAxisLabelColor,
    horizontalAxisLabelFontSize: TextUnit = DefaultAxisLabelFontSize,
    verticalAxisLabelColor: Color = DefaultAxisLabelColor,
    verticalAxisLabelFontSize: TextUnit = DefaultAxisLabelFontSize,
    paddingBetweenBars: Dp = 10.dp,
    paddingBetweenBarSet: Dp = 30.dp,
    isShowVerticalAxis: Boolean = false,
    isShowHorizontalLines: Boolean = true,
) {
    val paddingBetweenBarsPx = dpToPx(paddingBetweenBars)
    val paddingBetweenBarSetPx = dpToPx(paddingBetweenBarSet)
    val axisThicknessPx = dpToPx(DefaultAxisThickness)

    Canvas(
        modifier = modifier
            .height(240.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        val bottomAreaHeight = horizontalAxisLabelFontSize.toPx()
        val leftAreaWidth =
            (verticalAxisValues[verticalAxisValues.size - 1].toString().length * verticalAxisLabelFontSize.toPx()
                .div(1.75)).toInt()

        val verticalAxisLength = (size.height - bottomAreaHeight)
        val horizontalAxisLength = size.width - leftAreaWidth

        val distanceBetweenVerticalAxisValues = (verticalAxisLength / (verticalAxisValues.size - 1))

        // Draw horizontal axis
        if (isShowHorizontalLines.not())
            drawRect(
                color = axisColor,
                topLeft = Offset(leftAreaWidth.toFloat(), verticalAxisLength),
                size = Size(horizontalAxisLength, axisThicknessPx)
            )

        // Draw vertical axis
        if (isShowVerticalAxis)
            drawRect(
                color = axisColor,
                topLeft = Offset(leftAreaWidth.toFloat(), 0.0f),
                size = Size(axisThicknessPx, verticalAxisLength)
            )

        // Draw vertical axis values & horizontal lines
        for (index in verticalAxisValues.indices) {

            val x = size.width - (leftAreaWidth / 2) + 10
            val y = verticalAxisLength - (distanceBetweenVerticalAxisValues).times(index)

            // Draw vertical axis value
            drawContext.canvas.nativeCanvas.apply {
                withSuffix(verticalAxisValues[index])?.let {
                    drawText(
                        it,
                        0f,
                        y + verticalAxisLabelFontSize.toPx() / 2,
                        Paint().apply {
                            textSize = verticalAxisLabelFontSize.toPx()
                            color = verticalAxisLabelColor.toArgb()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }

            // Draw horizontal line
            if (isShowHorizontalLines)
                drawRect(
                    color = axisColor,
                    topLeft = Offset(leftAreaWidth.toFloat(), y),
                    size = Size(horizontalAxisLength, axisThicknessPx)
                )
        }

        // Draw bars and it's labels
        val totalBarPadding = paddingBetweenBarsPx * (barChartData.size + 1)
        val barWidth = DefaultBarWidth.value
//            (drawContext.size.width - totalBarPadding - leftAreaWidth) / barChartData.size
        var x = barWidth + leftAreaWidth
        var spaceBetweenSet = 0f
        for (index in barChartData.indices) {
            val entity = barChartData[index]
            for (value in entity.hrm) {

                val maxAxisValue = verticalAxisValues[verticalAxisValues.size - 1].toFloat()
                val barHeightPercentage = (value / maxAxisValue)
                val barHeightInPixel = barHeightPercentage * verticalAxisLength

                // Draw vertical bar
                drawRoundRect(
                    color = entity.color,
                    topLeft = Offset(x, verticalAxisLength - barHeightInPixel),
                    size = Size(barWidth, barHeightInPixel),
                    cornerRadius = CornerRadius(8f)
                )
                x += paddingBetweenBarsPx
            }
            // Draw horizontal axis label
            if (barChartData[index].label?.isNotEmpty() == true) {
                drawContext.canvas.nativeCanvas.apply {
                    barChartData[index].label?.let {
                        drawText(
                            it,
                            x-paddingBetweenBarsPx- (barWidth*entity.hrm.size).div(2),
                            verticalAxisLength + horizontalAxisLabelFontSize.toPx(),
                            Paint().apply {
                                textSize = bottomAreaHeight
                                color = horizontalAxisLabelColor.toArgb()
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }
                }
            }
            drawRect(
                color = axisColor,
                topLeft = Offset((x-paddingBetweenBarsPx -barWidth*entity.hrm.size), 0f),
                size = Size( axisThicknessPx, verticalAxisLength)
            )
            x += paddingBetweenBarSetPx
        }
    }
}

