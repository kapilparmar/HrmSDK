package com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectandheal.hrmsdk.domain.FilterType
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey500
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight700


@Composable
fun TabSection(
    filterList: List<FilterType>,
    selectedFilter: State<FilterType>,
    onTabClick: (FilterType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        filterList.forEachIndexed { _, filterType ->
            TabChip(
                modifier = Modifier.weight(1f),
                isSelected = filterType == selectedFilter.value,
                chipText = filterType.key,
                onClick = {
                    onTabClick(filterType)
                }
            )
        }
    }
}

@Composable
fun TabChip(
    modifier: Modifier,
    isSelected: Boolean,
    chipText: String,
    onClick: () -> Unit
) {
    val textStyle = if(isSelected) TextStyle_Size14_Weight700
    else TextStyle_Size14_Weight400
    val color = if(isSelected) TertiaryPastelWhite else PrimaryWhite

    /*  Surface(
          shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
          color = if(isSelected) TertiaryPastelWhite else PrimaryWhite,
          onClick = onClick
      ) {*/
    Column(modifier = modifier
        .background(color = color,
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = chipText,
            style = textStyle,
            color = Grey500
        )
    }
    // }
}