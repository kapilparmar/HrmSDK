package com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.domain.HRMResultModel
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.LinearRoundedProgressIndicator
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.filledButtonColors
import com.connectandheal.hrmsdk.view.theme.hrm.theme.ButtonSilveryGrey
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey200
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidGreen
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SecondaryStateViolet
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SolidGreen
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextSolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size12_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size18_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size20_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size34_Weight700


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartRateResult(hrmResultModel: HRMResultModel) {
    val listState = rememberLazyListState()
    val activitySelected = remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.height(185.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hrm_banner),
                contentDescription = "hrm",
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 58.dp)
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
        Text(
            text = "Activity in last 15 minutes",
            style = TextStyle_Size20_Weight400,
            modifier = Modifier.padding(top = 60.dp),
            color = Color.Black
        )
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(),
            state = listState

        ) {
            itemsIndexed(items = hrmResultModel.activities) { index, item ->
                ChipSurface(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    backgroundColor = TertiaryPastelWhite,
                    selectedBackgroundColor = PrimarySolidBlue,
                    title = item.activity.type,
                    image = item.image,
                    isSelected = activitySelected.value == index,
                    onClick = {
                        activitySelected.value = index
                    },
                )
            }
        }
        Text(
            text = "+ Add a Note",
            style = TextStyle_Size14_Weight400,
            modifier = Modifier.padding(vertical = 23.dp),
            color = Color.Black
        )
        MeasureButton(onClickMeasure = { },
            onClickSave = {})

    }
}

@Composable
private fun MeasureButton(
    onClickMeasure: () -> Unit,
    onClickSave: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 45.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            border = BorderStroke(width = 1.dp, color = PrimarySolidBlue),
            shape = RoundedCornerShape(8.dp),
            onClick = {}
        ) {
            Text(
                text = "Measure Again",
                style = TextStyle_Size16_Weight700,
                color = TextSolidBlue,

                )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            modifier = Modifier.weight(1f),
            onClick = {},
            shape = RoundedCornerShape(8.dp),
            colors = filledButtonColors(
                containerColor = SolidGreen,
                disabledColor = ButtonSilveryGrey
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = "Save",
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle_Size16_Weight700
            )
        }
    }
}
