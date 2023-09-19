package com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.domain.HRMResultModel
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.LinearRoundedProgressIndicator
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.OutlinedTextField
import com.connectandheal.hrmsdk.view.theme.hrm.theme.BorderActive
import com.connectandheal.hrmsdk.view.theme.hrm.theme.BorderLight
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey100
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey200
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey300
import com.connectandheal.hrmsdk.view.theme.hrm.theme.LightCyan
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidGreen
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SecondaryStateViolet
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SelectPatientBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextSolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size12_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size18_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size20_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size34_Weight700


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartRateResult(
    hrmResultModel: HRMResultModel, onSaveClick: () -> Unit,
    onEnterNote: (String) -> Unit
) {
    val note = remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val activitySelected = remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        SelectPatientBar(onChangeClick = { /*TODO*/ })
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 22.dp)
                .fillMaxWidth()
                .background(color = Color.White),
        ) {
            Image(
                painter = painterResource(id = R.drawable.hrm_banner),
                contentDescription = "hrm",
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = hrmResultModel.activityDone,
                style = TextStyle_Size20_Weight700, color = Color.Black,
                modifier = Modifier
                    .padding(top = 56.dp)
                    .align(CenterHorizontally),
            )
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_heart_result),
                    contentDescription = "hear rate",
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .size(43.dp),
                )

                Text(
                    text = "${hrmResultModel.hrValue}",
                    style = TextStyle_Size34_Weight700,
                    color = Grey200,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = "BPM",
                    style = TextStyle_Size12_Weight400, color = Grey200,
                    modifier = Modifier.padding(start = 2.dp, top = 3.dp),
                )
            }
            Row(
                modifier = Modifier
                    .align(CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Heart Rate: High",
                    style = TextStyle_Size18_Weight400,
                    modifier = Modifier.padding(start = 12.dp),
                    color = PrimarySolidGreen
                )
                Image(
                    painter = painterResource(id = R.drawable.heart_rate_info),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 7.dp)
                        .size(18.dp),
                )
            }
            LinearRoundedProgressIndicator(
                progress = .8f,
                isLabelEnabled = false,
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                color = colorResource(R.color.primary_solid_green),
                trackColor = TertiaryPastelWhite,
                startFraction = 0.4f,
                endFraction = 0.6f
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                hrmResultModel.hrmValues.forEach { hr ->
                    Text(
                        text = "$hr",
                        style = TextStyle_Size12_Weight400,
                        color = SecondaryStateViolet,
                    )
                }
            }
            OutlinedTextField(
                value = note.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
                    .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)),
                onValueChange = {
                    onEnterNote(it)
//                note.value = it
                },
                isError = false,
                placeholder = {
                    Text(
                        text = "Add a note",
                        color = Grey100
                    )
                },
                enabled = true,
                maxLines = 3,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BorderActive,
                    unfocusedBorderColor = BorderLight
                )
            )

            MeasureButton(
                onClickMeasure = { },
                onClickSave = onSaveClick
            )

        }
        Column(
            modifier = Modifier
                .background(TertiaryPastelWhite)
                .padding(horizontal = 16.dp)
        ) {
            ConsultDoctor()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConsultDoctor() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 34.dp, bottom = 26.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightCyan
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp)
        ) {
            Row {
                val consultDocText = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W700)) {
                        append("Are you feeling")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W400)) {
                        append("\nShortness of Breath, Chest Pain or Palpitation")
                    }
                }
                Text(
                    text = consultDocText,
                    color = Grey300,
                    fontSize = 17.sp
                )
            }
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimarySolidGreen,
                    contentColor = PrimaryWhite
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "Proceed to Unlock",
                    style = TextStyle_Size14_Weight400
                )
            }

        }
    }
}

@Composable
private fun MeasureButton(
    onClickMeasure: () -> Unit,
    onClickSave: () -> Unit
) {
    OutlinedButton(
        border = BorderStroke(width = 1.dp, color = PrimarySolidBlue),
        shape = RoundedCornerShape(8.dp),
        onClick = {},
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Measure Again",
            style = TextStyle_Size16_Weight700,
            color = TextSolidBlue,

            )
    }
}
