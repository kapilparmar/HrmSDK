package com.connectandheal.hrmsdk.view.theme.hrm.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.connectandheal.hrmsdk.R

@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    bottomContent: @Composable () -> Unit = {},
    backgroundColor: Color = colorResource(R.color.white),
    elevation: Dp = 0.dp,
    showBackButton: Boolean = true,
    onBackPressed: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .background(backgroundColor)
            .statusBarsPadding()
            .fillMaxWidth(),
        color = backgroundColor,
        shadowElevation = elevation
    ) {
        Column {
            SmallTopAppBar(
                title = title, navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = onBackPressed ?: {}) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "Back"
                            )
                        }
                    }
                }, actions = actions, colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = backgroundColor
                )
            )

            bottomContent()
        }
    }
}