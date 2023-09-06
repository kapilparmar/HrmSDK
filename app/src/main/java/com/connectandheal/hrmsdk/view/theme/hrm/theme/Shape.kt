package com.soscare.customer.view.common.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


val Shapes = androidx.compose.material3.Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp),
)

val StarShape: Shape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val baseWidth = 12f
        val baseHeight = 12.7119f
        val width = size.width
        val height = size.height

        val path = Path()
//11.8394f = width*0.986f,
        //5.4169f=height*0.4514f
        path.moveTo(width * 0.775f, height * 0.6415f)
        path.lineTo(width * 0.986f, height * 0.4261f)
        path.lineTo(width * 0.986f, height * 0.4261f)
        path.cubicTo(width * 0.9986f,
            height * 0.4137f,
            width * 1.003f,
            height * 0.3952f,
            width * 0.9977f,
            height * 0.3784f)
        path.cubicTo(width * 0.9925f,
            height * 0.3616f,
            width * 0.9786f,
            height * 0.3493f,
            width * 0.9619f,
            height * 0.3468f)
        path.lineTo(width * 0.6702f, height * 0.3023f)
        path.lineTo(width * 0.5397f, height * 0.0257f)
        path.cubicTo(width * 0.5322F, height * 0.0099f, width * 0.5168f, 0f, width * 0.5f, 0f)
        path.cubicTo(width * 0.4831f,
            0f,
            width * 0.4677f,
            height * 0.0099f,
            width * 0.4602f,
            height * 0.0257f)
        path.lineTo(width * 0.3298f, height * 0.3023f)
        path.lineTo(width * 0.0380f, height * 0.3468f)
        path.cubicTo(width * 0.0213f,
            height * 0.3493f,
            width * 0.0074f,
            height * 0.3616f,
            width * 0.0021f,
            height * 0.3784f)
        path.cubicTo(-width * 0.0030f,
            height * 0.4187f,
            width * 0.0012f,
            height * 0.4137f,
            width * 0.0133f,
            height * 0.4261f)
        path.lineTo(width * 0.2244f, height * 0.6415f)
        path.lineTo(width * 0.1747f, height * 0.9456f)
        path.cubicTo(width * 0.1718f,
            height * 0.9630f,
            width * 0.1786f,
            height * 0.9806f,
            width * 0.19f,
            height * 0.9911f)
        path.cubicTo(width * 0.2060f,
            height * 1.0015f,
            width * 0.2241f,
            height * 1.0028f,
            width * 0.23915f,
            height * 0.9946f)
        path.lineTo(width * 0.5f, 10.8196f)
        path.lineTo(width * 0.7608f, height * 0.9946f)
        path.lineTo(width * 0.7608f, height * 0.9946f)
        path.cubicTo(width * 0.7758f,
            height * 1.0028f,
            width * 0.7939f,
            height * 1.0015f,
            width * 0.8076f,
            height * 0.9911f)
        path.cubicTo(width * 0.8213f,
            height * 0.9806f,
            width * 0.8281f,
            height * 0.9630f,
            width * 0.8253f,
            height * 0.9456f)
        path.lineTo(width * 0.7755f, height * 0.6415f)
        path.close()

        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(android.graphics.Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}





