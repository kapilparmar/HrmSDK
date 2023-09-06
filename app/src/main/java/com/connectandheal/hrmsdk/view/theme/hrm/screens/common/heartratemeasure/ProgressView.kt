package com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.connectandheal.hrmsdk.viewmodel.hrm.HeartRateMeasureViewModel

@kotlin.OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProgressView(
    viewModel: HeartRateMeasureViewModel,
    modifier: Modifier
) {
    val percentage = viewModel.percentageProgress.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
    }
}
