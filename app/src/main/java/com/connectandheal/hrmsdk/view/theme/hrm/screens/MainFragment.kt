package com.connectandheal.hrmsdk.view.theme.hrm.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.connectandheal.hrmsdk.HeartRateMeasureActivity
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Destination
import com.connectandheal.hrmsdk.view.theme.hrm.routing.FragmentRouteProtocol
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Router
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.HandleMultiplePermissions
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.SettingsAlertDialogPermission
import com.connectandheal.hrmsdk.view.theme.hrm.theme.AppTheme
import com.soscare.customer.view.common.theme.PrimarySolidGreen
import com.soscare.customer.view.common.theme.PrimaryWhite
import com.soscare.customer.view.common.theme.TextStyle_Size18_Weight700
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainScreen(
    override val destination: Destination.Fragment = Destination.Fragment(R.id.mainScreen)
): FragmentRouteProtocol

@AndroidEntryPoint
class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    val router = Router(context, findNavController())
                    ScreenContent(
                        onStartMeasuring = {
                            router.navigate(
                                HeartRateMeasureActivity.Route()
                            )
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    onStartMeasuring: () -> Unit
) {
    val checkPermissions = remember { mutableStateOf(false) }
    val cameraPermissionRequested = remember { mutableStateOf(false) }
    val showPermissionRequiredDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        content = {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = PrimaryWhite,
                        containerColor = PrimarySolidGreen
                    ),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        if (ContextCompat.checkSelfPermission(
                                context, Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            onStartMeasuring()
                        } else {
                            checkPermissions.value = true
                        }
                    }
                ) {
                    Text(
                        text = "Start Measuring", style = TextStyle_Size18_Weight700
                    )
                }
            }
        }
    )

    if (checkPermissions.value) {
        HandleMultiplePermissions(
            permissions = listOf(Manifest.permission.CAMERA),
            permissionRequested = cameraPermissionRequested,
            onPermissionStateChanged = {
                if(it) {
                    onStartMeasuring()
                }
                checkPermissions.value = false
            },
            showPermissionRequiredDialog = {
                showPermissionRequiredDialog.value = true
                checkPermissions.value = false
            }
        )
    }

    SettingsAlertDialogPermission(
        description = "Camera permissions is required to Measure Heart Rate. Click on Go to setting and enable the Permissions",
        showPermissionRequiredDialog
    )
}