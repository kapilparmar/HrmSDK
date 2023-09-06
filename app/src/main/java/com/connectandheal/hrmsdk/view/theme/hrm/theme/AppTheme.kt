package com.connectandheal.hrmsdk.view.theme.hrm.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

// Default theme for application, to be used to wrap material theme
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme{
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null,
            content = content
        )
    }
}