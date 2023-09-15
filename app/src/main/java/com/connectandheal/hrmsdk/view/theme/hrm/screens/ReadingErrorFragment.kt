package com.connectandheal.hrmsdk.view.theme.hrm.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Destination
import com.connectandheal.hrmsdk.view.theme.hrm.routing.FragmentRouteProtocol
import com.connectandheal.hrmsdk.view.theme.hrm.theme.AppTheme
import com.connectandheal.hrmsdk.view.theme.hrm.theme.DefaultAppBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey200
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey300
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey500
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SecondarySilverGray
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SolidGreen
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size18_Weight700
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReadingErrorScreen(
    override val destination: Destination.Fragment = Destination.Fragment(R.id.readingErrorScreen)
) : FragmentRouteProtocol

@AndroidEntryPoint
class ReadingErrorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    ReadingErrorScreen(onBackClick = {})
                }
            }
        }
    }

    @Composable
    private fun ReadingErrorScreen(onBackClick: () -> Unit) {
        Scaffold(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxSize(),
            topBar = {
                DefaultAppBar(
                    title = {
                        Text(
                            text = "Heart Rate",
                            style = TextStyle_Size16_Weight400.copy(lineHeight = 22.sp),
                            color = Grey500
                        )
                    },
                    showBackButton = true,
                    actions = {
                        OutlinedButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .defaultMinSize(1.dp, 1.dp)
                                .padding(horizontal = 16.dp),
                            border = BorderStroke(1.dp, PrimarySolidBlue),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White,
                                contentColor = PrimarySolidBlue
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "See Insights",
                                style = TextStyle_Size14_Weight400.copy(lineHeight = 30.sp),
                                color = PrimarySolidBlue
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                ReadingErrorScreenContent(paddingValues)
            }
        )
    }

    @Composable
    private fun ReadingErrorScreenContent(paddingValues: PaddingValues) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = TertiaryPastelWhite)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(color = TertiaryPastelWhite)
                    .verticalScroll(rememberScrollState())
            ) {
                ErrorTitle()
                ErrorDescription()
                ErrorImage()
                ErrorTipsHeader(screenWidth)
                Spacer(modifier = Modifier.height(24.dp))
                ErrorTips(screenWidth)
            }
            MeasureAgainCTA()
        }
    }

    @Composable
    private fun MeasureAgainCTA() {
        Button(
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SolidGreen,
                contentColor = PrimaryWhite,
                disabledContainerColor = SecondarySilverGray,
                disabledContentColor = PrimaryWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 18.dp),
            onClick = { },
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Measure Again",
                style = TextStyle_Size16_Weight700.copy(lineHeight = 24.sp),
                color = PrimaryWhite,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    @Composable
    private fun ColumnScope.ErrorTips(screenWidth: Int) {
        for (tip in getTipList()) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width((screenWidth * 0.7567).dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Top)
                        .padding(8.dp),
                    text = "·",
                    style = TextStyle_Size14_Weight400.copy(lineHeight = 18.sp),
                    color = Grey300
                )
                Text(
                    text = tip,
                    style = TextStyle_Size14_Weight400.copy(lineHeight = 18.sp),
                    color = Grey300,
                )
            }
        }
    }

    @Composable
    private fun ColumnScope.ErrorTipsHeader(screenWidth: Int) {
        Text(
            modifier = Modifier
                .width((screenWidth * 0.7574).dp)
                .padding(top = 70.dp)
                .align(Alignment.CenterHorizontally),
            text = "Try these steps to get an accurate reading",
            style = TextStyle_Size14_Weight700.copy(lineHeight = 18.sp),
            color = Grey500,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    private fun ColumnScope.ErrorImage() {
        Image(
            painter = painterResource(id = R.drawable.ic_error_heart),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 56.dp)
                .size(109.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillBounds
        )
    }

    @Composable
    private fun ColumnScope.ErrorDescription() {
        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = "We are unable to Measure your heart rate.\nPlease try one more time",
            style = TextStyle_Size16_Weight400.copy(lineHeight = 22.sp),
            color = Grey200,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    private fun ColumnScope.ErrorTitle() {
        Text(
            modifier = Modifier
                .padding(top = 61.dp)
                .align(Alignment.CenterHorizontally),
            text = "Unable to Measure",
            style = TextStyle_Size18_Weight700,
            color = Grey500,
            textAlign = TextAlign.Center
        )
    }

    private fun getTipList(): List<String> {
        return listOf(
            "Place your finger gently on the camera lens, don’t press too hard",
            "Try placing your device in a place with better lighting",
            "Keep your hand steady to get an accurate reading",
            "Cover both the camera lens and flashlight with your finger"
        )
    }
}