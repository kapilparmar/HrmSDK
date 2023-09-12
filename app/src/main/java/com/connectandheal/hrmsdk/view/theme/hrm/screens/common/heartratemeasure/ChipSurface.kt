package com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Purple500
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size12_Weight400

@Composable
fun ChipSurface(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Purple500,
    image: Int,
    title : String,
    selectedBackgroundColor: Color,
    isSelected: Boolean,
    onClick : ()-> Unit
) {
    Box(
        modifier = modifier
              .background(
                color = if (isSelected) selectedBackgroundColor else backgroundColor,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
           verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 5.dp)
        ) {
            Image(
                painter = painterResource(id = image),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(alignment = Alignment.CenterVertically),
                contentDescription = "trailingIcon"
            )
            Text(
                text = title,
                style = TextStyle_Size12_Weight400,
                maxLines = 1,
                color = if (isSelected) Color.White else Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )

        }
    }
}
