package com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey500
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight700

@Composable
fun DateSection(
    onBackArrowClick: () -> Unit,
    onNextArrowClick: () -> Unit,
    currentDateString: String
) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = { onBackArrowClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hrm_back_arrow),
                contentDescription = "back_arrow"
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hrm_calendar),
                contentDescription = "back_arrow"
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = currentDateString,
                style = TextStyle_Size16_Weight700,
                color = Grey500
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { onNextArrowClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hrm_next_arrow),
                contentDescription = "back_arrow"
            )
        }
    }
}