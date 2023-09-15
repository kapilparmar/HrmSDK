package com.connectandheal.hrmsdk.view.theme.hrm.screens.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp

@Composable
fun PPGChart(
    hrValue: List<Float>,
    modifier: Modifier = Modifier,
    lineColor: Color,
    lineWidth: Float
) {
    Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState(0))) {
        DrawLineChart(yPoints = hrValue)
    }
}

@Composable
private fun DrawLineChart(
    yPoints: List<Float>,
) {

    val spacing = 70
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.dp, horizontal = 16.dp)
    ) {
        val spacePerHour = 20.dp.toPx()
        val normX = mutableListOf<Float>()
        val normY = mutableListOf<Float>()

        val strokePath = Path().apply {
            for (i in yPoints.indices) {
                val currentX = spacing + i * spacePerHour
                lineTo(x = currentX, y = -yPoints[i])//-ve to make it graph inverted
                normX.add(currentX)
                normY.add(yPoints[i])
            }
        }
        this.translate(left = strokePath.getBounds().left) {
            drawPath(
                path = strokePath,
                color = Color.Red,

                style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.cornerPathEffect(10.dp.toPx())
                )
            )
        }

    }
}