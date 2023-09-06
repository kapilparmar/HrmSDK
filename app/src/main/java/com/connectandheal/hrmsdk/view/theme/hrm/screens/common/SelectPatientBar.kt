package com.connectandheal.hrmsdk.view.theme.hrm.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soscare.customer.view.common.theme.BadgeOrange
import com.soscare.customer.view.common.theme.Grey300
import com.soscare.customer.view.common.theme.PrimarySolidBlue
import com.soscare.customer.view.common.theme.TextStyle_Size14_Weight400
import com.soscare.customer.view.common.theme.TextStyle_Size16_Weight400
import com.soscare.customer.view.common.theme.TextStyle_Size16_Weight700

@Composable
fun SelectPatientBar(
    name: String = "",
    onChangeClick:()->Unit,
    labelText : String? = "Patient:"
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(BadgeOrange)
        .padding(horizontal = 16.dp, vertical = 16.dp)
        .clickable {
            onChangeClick()
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically) {
            labelText?.let {
                Text(
                    text = it,
                    style = TextStyle_Size16_Weight400,
                    color = Grey300
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text = name.capitalize(),
                style = TextStyle_Size16_Weight700,
                color = Grey300)
        }
        Text(text = "Change",
            style = TextStyle_Size14_Weight400,
            color = PrimarySolidBlue,
            modifier = Modifier.clickable(enabled = true, onClick = {
                onChangeClick()
            }))
    }
}