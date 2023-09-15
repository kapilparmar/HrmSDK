package com.connectandheal.hrmsdk.view.theme.hrm.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey100
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidBlue

@Composable
fun FullPageCircularLoader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = PrimarySolidBlue, modifier = Modifier.size(64.dp)
        )
    }
}

@Composable
fun TransparentFullPageLoader() {
    FullPageCircularLoader(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Grey100.copy(alpha = 0.5f))
            .clickable(enabled = false, onClick = {})
    )
}

@Composable
fun FullTransparentPageLoader(
    progress: Color = PrimarySolidBlue
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.Transparent)
            .clickable(enabled = false, onClick = {}),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = progress, modifier = Modifier.size(64.dp)
        )
    }
}

