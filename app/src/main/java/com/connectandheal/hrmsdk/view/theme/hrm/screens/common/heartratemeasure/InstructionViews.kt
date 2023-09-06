package com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.R
import com.soscare.customer.view.common.theme.Grey200
import com.soscare.customer.view.common.theme.PrimaryWhite
import com.soscare.customer.view.common.theme.TextStyle_Size14_Weight400
import com.soscare.customer.view.common.theme.TextStyle_Size16_Weight400
import com.soscare.customer.view.common.theme.TextStyle_Size18_Weight700

@Composable
fun BottomInstructions() {
    Surface(
        color = PrimaryWhite,
        shape = RoundedCornerShape(
            topEnd = 8.dp,
            topStart = 8.dp
        ),

    ) {
        Column(
            modifier = Modifier.padding(horizontal = 45.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Place your finger on the camera to measure the heart rate",
                modifier = Modifier.padding(top = 29.dp),
                style = TextStyle_Size16_Weight400
            )
            Image(
                painter = painterResource(id = R.drawable.ic_hrm_instructions),
                contentDescription = "instructions",
                modifier = Modifier
                    .padding(top = 23.dp)
                    .size(
                        width = 139.dp,
                        height = 173.dp
                    )
            )
        }
    }
}

@Composable
fun TopInstructions() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Scanning....",
                modifier = Modifier.padding(top = 22.dp),
                style = TextStyle_Size18_Weight700,
                color = Color.Black
            )
            Text(
                text = "Place your finger gently on the back camera and hold it there",
                modifier = Modifier.padding(top = 12.dp),
                style = TextStyle_Size14_Weight400.copy(lineHeight = 22.sp),
                color = Grey200
            )
        }
    }
}

@Composable
fun VoiceInstructions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 138.dp, bottom = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Voice Instructions",
                style = TextStyle_Size14_Weight400,
                color = Grey200
            )
            Image(
                painter = painterResource(id = R.drawable.ic_voice_instruction),
                contentDescription = "instructions",
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(
                        width = 18.dp,
                        height = 20.dp
                    )
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_how_it_works),
                contentDescription = "How it works",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(16.dp)
            )
            Text(
                text = "How it works",
                style = TextStyle_Size14_Weight400,
                color = Grey200
            )
        }
    }
}