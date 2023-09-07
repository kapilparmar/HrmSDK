package com.connectandheal.hrmsdk.view.theme.hrm.screens.common

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.connectandheal.hrmsdk.view.theme.hrm.theme.ButtonSilveryGrey
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey300
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidGreen
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SolidRed
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size18_Weight700

@Composable
fun SettingsAlertDialogPermission(
    description: String?,
    showPermissionRequiredDialog: MutableState<Boolean>,
    canDismissDialog: Boolean = true
) {
    if (showPermissionRequiredDialog.value) {
        SettingsAlertDialog(
            onDismiss = {
                showPermissionRequiredDialog.value = false
            },
            title = "Permission are Required",
            description = description,
            canDismissDialog = canDismissDialog
        )
    }
}

@Composable
fun SettingsAlertDialog(
    title: String? = null,
    description: String? = null,
    onDismiss: () -> Unit = {},
    intent: Intent? = null,
    canDismissDialog: Boolean = true
) {
    val context = LocalContext.current
    ConfirmationAlertDialog(
        confirmationButton = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                onClick = {
                    onDismiss.invoke()
                    if (intent == null) {
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.parse("package:" + context.packageName)
                            context.startActivity(this)
                        }
                    } else {
                        context.startActivity(intent)
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimarySolidGreen,
                    contentColor = PrimarySolidGreen,

                    )
            ) {
                Text(
                    text = "Go To Settings",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = TextStyle_Size14_Weight700
                )
            }
        },
        cancelButton = {
            OutlinedButton(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                onClick = {
                    onDismiss.invoke()
                },
                border = BorderStroke(width = 1.dp, SolidRed),
                shape = RoundedCornerShape(8.dp),
                colors = filledButtonColors(
                    containerColor = Color.Transparent,
                    disabledColor = ButtonSilveryGrey,
                )
            ) {
                Text(
                    text = "Cancel",
                    color = SolidRed,
                    textAlign = TextAlign.Center,
                    style = TextStyle_Size14_Weight700
                )
            }
        },
        title = title,
        description = description,
        canDismissDialog = canDismissDialog
    )
}

@Composable
fun ConfirmationAlertDialog(
    confirmationButton: @Composable () -> Unit,
    cancelButton: @Composable () -> Unit,
    icon: Int? = null,
    title: String? = null,
    description: String? = null,
    canDismissDialog: Boolean = true
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = canDismissDialog, dismissOnClickOutside = canDismissDialog),
        content = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    icon?.let {
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = "icon",
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    title?.let {
                        Text(
                            text = it,
                            style = TextStyle_Size18_Weight700,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    description?.let {
                        Text(
                            text = it,
                            style = TextStyle_Size14_Weight400,
                            color = Grey300,
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)

                        )
                    }
                }

                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    confirmationButton()
                }
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    cancelButton()
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        },
    )
}

@Composable
fun filledButtonColors(containerColor: Color, disabledColor: Color): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = containerColor,
        disabledContentColor = disabledColor,
        disabledContainerColor = disabledColor
    )
}