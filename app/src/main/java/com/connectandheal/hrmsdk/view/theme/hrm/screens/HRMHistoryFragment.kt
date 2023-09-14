package com.connectandheal.hrmsdk.view.theme.hrm.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Destination
import com.connectandheal.hrmsdk.view.theme.hrm.routing.FragmentRouteProtocol
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.FullPageCircularLoader
import com.connectandheal.hrmsdk.view.theme.hrm.theme.AppTheme
import com.connectandheal.hrmsdk.view.theme.hrm.theme.DefaultAppBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey500
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight400
import com.connectandheal.hrmsdk.viewmodel.hrm.HeartRateHistoryViewModel
import com.connectandheal.hrmsdk.viewmodel.hrm.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.findNavController
import com.connectandheal.hrmsdk.domain.PreviousReadingItem
import com.connectandheal.hrmsdk.domain.HeartRateSummaryModel
import com.connectandheal.hrmsdk.domain.NavBarConstants.SAFE_AREA_HEIGHT
import com.connectandheal.hrmsdk.domain.Patient
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Router
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.noRippleClickable
import com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights.FlowType
import com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights.HRMPreviousReadingCard
import com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights.HeartRateSummaryCard
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey200
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidGreen
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SelectPatientBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight700

@Parcelize
data class HRMHistoryScreen(
    override val destination: Destination.Fragment = Destination.Fragment(R.id.heartRateHistoryScreen),
    override val patient: Patient
) : FragmentRouteProtocol, HeartRateHistoryViewModel.Args

@AndroidEntryPoint
class HRMHistoryFragment : Fragment() {
    private val viewModel: HeartRateHistoryViewModel by viewModels()

    sealed class Action {
        object MeasureAgain: Action()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    val router = Router(context, findNavController())
                    HRMHistoryScreenContent(
                        onBackPressed = { router.popBackStack() },
                        viewModel = viewModel,
                        onAction = {

                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun HRMHistoryScreenContent(
    onBackPressed: () -> Unit,
    viewModel: HeartRateHistoryViewModel,
    onAction: (HRMHistoryFragment.Action) -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultAppBar(
                title = {
                    Text(
                        text = "Insights",
                        style = TextStyle_Size16_Weight400,
                        lineHeight = 24.sp,
                        color = Grey500
                    )
                },
                onBackPressed = onBackPressed,
                elevation = 5.dp,
                actions = {
                    Icon(
                        modifier = Modifier.noRippleClickable {
                            //TODO
                        },
                        painter = painterResource(id = R.drawable.vertical_menu),
                        contentDescription = "",
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
            )
        },
        content = { paddingValues ->
            when (val state = viewState) {
                is ViewState.Loading -> {
                    FullPageCircularLoader()
                }

                is ViewState.Loaded -> {
                    MainContent(
                        paddingValues = paddingValues,
                        viewModel = viewModel,
                        onAction = onAction
                    )
                }
                else -> {}
            }
        }
    )
}

@Composable
fun MainContent(
    paddingValues: PaddingValues,
    viewModel: HeartRateHistoryViewModel,
    onAction: (HRMHistoryFragment.Action) -> Unit
) {
    val previousReadings = viewModel.previousReadings.collectAsState()
    val heartRateSummary = viewModel.hearRateSummary.collectAsState()

    Box(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SelectPatientBar(
                name = viewModel.patient.collectAsState().value?.patientName ?: "",
                onChangeClick = { }
            )
            Spacer(modifier = Modifier.height(26.dp))
            TabSection()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(TertiaryPastelWhite)
            ) {
                DateSection()
                Spacer(modifier = Modifier.height(16.dp))
                HearRateReadingsContent(
                    heartRateSummaryModel = heartRateSummary,
                    previousReadings = previousReadings
                )
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                .fillMaxWidth(),
            onClick = {
                onAction(HRMHistoryFragment.Action.MeasureAgain)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimarySolidGreen,
                contentColor = PrimaryWhite
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Measure Again",
                style = TextStyle_Size14_Weight400.copy(lineHeight = 21.sp)
            )
        }
    }
}

@Composable
fun HearRateReadingsContent(
    heartRateSummaryModel : State<HeartRateSummaryModel?>,
    previousReadings: State<List<PreviousReadingItem>>
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)
    ) {
        heartRateSummaryModel.value?.let {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                HeartRateSummaryCard(
                    heartRateSummaryModel = it
                )
            }
        }

        if (previousReadings.value.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Previous Readings",
                    style = TextStyle_Size14_Weight700,
                    color = Grey200
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            itemsIndexed(previousReadings.value) { _, item ->
                HRMPreviousReadingCard(
                    id = item.id,
                    category = item.category,
                    heartRate = item.heartRateValue,
                    heartRateZone = item.heartRateZone,
                    measuredOn = item.measuredOn,
                    flowType = if(item.note.isNotEmpty()) FlowType.Editable else FlowType.Normal,
                    note = item.note,
                    onCardClick = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        item {
            Spacer(modifier = Modifier.height(SAFE_AREA_HEIGHT))
        }
    }
}

@Composable
fun TabSection() {

}

@Composable
fun DateSection() {

}