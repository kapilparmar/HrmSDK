package com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.domain.HeartRateSummaryModel
import com.connectandheal.hrmsdk.domain.HeartRateSummaryType
import com.connectandheal.hrmsdk.domain.HeartRateZone
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey200
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SolidRed
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size20_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size20_Weight700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartRateSummaryCard(
    heartRateSummaryModel: HeartRateSummaryModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryWhite
        )
    ) {
        Column(modifier = Modifier
            .padding(start = 22.dp, top = 18.dp, bottom = 15.dp)
        ) {
            Text(
                text = heartRateSummaryModel.title,
                style = TextStyle_Size20_Weight700
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(57.dp)
            ) {
                HeartRateSummaryItem(
                    heartRateType = heartRateSummaryModel.lowest,
                    label = "lowest"
                )
                HeartRateSummaryItem(
                    heartRateType = heartRateSummaryModel.average,
                    label = "Average"
                )
                HeartRateSummaryItem(
                    heartRateType = heartRateSummaryModel.highest,
                    label = "Highest"
                )
            }
        }
    }
}

@Composable
fun HeartRateSummaryItem(
    heartRateType: HeartRateSummaryType,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = heartRateType.value.toString(),
            style = TextStyle_Size20_Weight400.copy(fontSize = 36.sp)
        )
        Text(
            text = label, style = TextStyle_Size14_Weight400.copy(lineHeight = 18.sp),
            color = when (heartRateType.heartRateZone) {
                HeartRateZone.HEALTHY -> Grey200
                HeartRateZone.HIGH -> SolidRed
                HeartRateZone.LOW -> Grey200 // should have another color for low heart rate
            }
        )
    }
}