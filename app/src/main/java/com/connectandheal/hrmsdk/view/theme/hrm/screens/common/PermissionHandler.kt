package com.connectandheal.hrmsdk.view.theme.hrm.screens.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandleMultiplePermissions(
    permissions: List<String>,
    permissionRequested: MutableState<Boolean>,
    onPermissionStateChanged: (Boolean) -> Unit = {},
    showPermissionRequiredDialog: () -> Unit = {},
) {
    val context = LocalContext.current
    val permissionsState = rememberMultiplePermissionsState(
        permissions = permissions,
        onPermissionsResult = { permissionStateMap ->
            when {
                !permissionStateMap.containsValue(false) -> {
                    onPermissionStateChanged(true)
                }
                else -> {
                    onPermissionStateChanged(false)
                    Toast.makeText(context, "Permissions Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    )

    LaunchedEffect(key1 = permissionsState) {
        when {
            permissionsState.allPermissionsGranted -> {
                onPermissionStateChanged(true)
            }
            !permissionsState.allPermissionsGranted && !permissionsState.shouldShowRationale && !permissionRequested.value -> {
                permissionRequested.value = true
                permissionsState.launchMultiplePermissionRequest()
            }
            !permissionsState.allPermissionsGranted && permissionsState.shouldShowRationale -> {
                permissionRequested.value = true
                permissionsState.launchMultiplePermissionRequest()
            }
            else -> {
                showPermissionRequiredDialog()
            }
        }

//        if (permissionsState.allPermissionsGranted) {
//            onPermissionStateChanged(true)
//        }
//        askPermission?.value = false
    }
}
