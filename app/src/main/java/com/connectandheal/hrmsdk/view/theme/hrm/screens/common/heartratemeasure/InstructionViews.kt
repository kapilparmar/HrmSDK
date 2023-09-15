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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.view.theme.hrm.theme.CircularProgressBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey200
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidGreen
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size12_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size18_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size34_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Vermilion
import com.connectandheal.hrmsdk.viewmodel.hrm.HRMeasuredValues

@Composable
fun BottomInstructions(
    instruction: String
) {
    Surface(
        shape = RoundedCornerShape(
            topEnd = 8.dp, topStart = 8.dp
        ),
        color = PrimaryWhite,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 45.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = instruction,
                modifier = Modifier.padding(top = 29.dp),
                style = TextStyle_Size16_Weight400,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.ic_hrm_instructions),
                contentDescription = "instructions",
                modifier = Modifier
                    .padding(top = 23.dp)
                    .size(
                        width = 139.dp, height = 173.dp
                    )
            )
        }
    }
}

@Composable
fun TopInstructions(
    title: String, description: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(top = 22.dp),
                style = TextStyle_Size18_Weight700,
                color = Color.Black
            )
            Text(
                text = description,
                modifier = Modifier.padding(top = 12.dp),
                style = TextStyle_Size14_Weight400.copy(lineHeight = 22.sp),
                color = Grey200,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun VoiceInstructions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp, end = 16.dp, top = 138.dp, bottom = 14.dp
            ), verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Voice Instructions", style = TextStyle_Size14_Weight400, color = Grey200
            )
            Image(
                painter = painterResource(id = R.drawable.ic_voice_instruction),
                contentDescription = "instructions",
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(
                        width = 18.dp, height = 20.dp
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
                text = "How it works", style = TextStyle_Size14_Weight400, color = Grey200
            )
        }
    }
}

@Composable
fun HeartRateMeasuring(hrValue: HRMeasuredValues?) {
    Surface(
        modifier = Modifier
            .padding(top = 12.dp)
            .size(width = 170.dp, height = 250.dp),
        shape = RoundedCornerShape(
            topEnd = 85.dp, topStart = 85.dp, bottomEnd = 85.dp, bottomStart = 85.dp
        ),
        color = PrimaryWhite,
        elevation = 5.dp
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.padding(top = 22.dp)
        ) {
            hrValue?.completed?.let {
                CircularProgressBar(
                    progress = it,
                    modifier = Modifier
                        .size(128.dp),
                    progressMax = 100f,
                    progressBarColor = PrimarySolidGreen,
                    progressBarWidth = 8.dp,
                    backgroundProgressBarColor = Color.White,
                    backgroundProgressBarWidth = 8.dp,
                    roundBorder = true,
                    labelEnabled = true
                )
            }
            Row(
                modifier = Modifier.padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "instructions",
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .size(43.dp)
                )
                Text(
                    text = "${hrValue?.hrValue?: "--"}",
                    style = TextStyle_Size34_Weight700, color = Grey200
                )
            }
        }
    }
}
@Composable
fun Disclaimer(disclaimer : String){
    Column(Modifier.padding(top = 80.dp, bottom = 20.dp)
        .padding(horizontal = 16.dp)) {
        Text(
            text = "Disclaimer!",
            style = TextStyle_Size14_Weight700,
            color = Vermilion
        )
        Text(
            text = disclaimer,
            style = TextStyle_Size12_Weight400.copy(lineHeight = 15.sp)
        )
    }
}
