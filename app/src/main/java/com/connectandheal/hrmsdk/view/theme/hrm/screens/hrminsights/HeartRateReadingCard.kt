package com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.domain.HeartRateZone
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey300
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidGreen
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SolidRed
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size12_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size12_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400

enum class FlowType {
    BottomSheet, Normal, Editable
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HRMPreviousReadingCard(
    id: String,
    category: String,
    measuredOn: String,
    heartRate: String,
    heartRateZone: HeartRateZone,
    note: String = "",
    flowType: FlowType,
    onCardClick: () -> Unit,
    onEditClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryWhite
        ),
        onClick = onCardClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            HeartRateReadingInformation(
                category = category,
                measuredOn = measuredOn,
                heartRate = heartRate,
                heartRateZone = heartRateZone,
                flowType = flowType
            )

            if (flowType == FlowType.Editable) {
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W700,
                            color = Grey300
                        )
                    ) {
                        append("Note : ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W400,
                            color = Grey300
                        )
                    ) {
                        append(note)
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = TertiaryPastelWhite,
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.weight(0.75f),
                                text = annotatedString,
                                lineHeight = 18.sp,
                                textAlign = TextAlign.Start
                            )
                            Row {
                                Spacer(modifier = Modifier.width(32.dp))
                                IconButton(
                                    modifier = Modifier.padding(8.dp),
                                    content = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_hrm_edit),
                                            contentDescription = ""
                                        )
                                    },
                                    onClick = onEditClick
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HeartRateReadingInformation(
    category: String,
    measuredOn: String,
    heartRate: String,
    heartRateZone: HeartRateZone,
    flowType : FlowType,
) {
    val (tintColor, label) = when (heartRateZone) {
        HeartRateZone.HIGH -> Pair(SolidRed, "High")
        HeartRateZone.HEALTHY -> Pair(PrimarySolidGreen, "Normal")
        else -> Pair(PrimarySolidGreen, "Normal")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = TertiaryPastelWhite,
                content = {
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        text = category,
                        style = TextStyle_Size12_Weight700.copy(lineHeight = 18.sp),
                        color = PrimarySolidBlue
                    )
                }
            )
            Spacer(
                modifier = if(flowType == FlowType.Normal)
                    Modifier.height(16.dp) else Modifier.height(12.dp)
            )
            Text(text = measuredOn, style = TextStyle_Size12_Weight400)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = heartRate,
                style = TextStyle_Size14_Weight400.copy(fontSize = 42.sp, lineHeight = 30.sp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hrm_ecg_wave),
                    contentDescription = "",
                    tint = tintColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    style = TextStyle_Size12_Weight400,
                    color = tintColor
                )
            }
        }
    }
}
