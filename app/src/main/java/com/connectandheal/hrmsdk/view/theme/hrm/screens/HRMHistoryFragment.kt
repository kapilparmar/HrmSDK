package com.connectandheal.hrmsdk.view.theme.hrm.screens

import android.os.Bundle
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.domain.HeartRateSummaryModel
import com.connectandheal.hrmsdk.domain.NavBarConstants.SAFE_AREA_HEIGHT
import com.connectandheal.hrmsdk.domain.Patient
import com.connectandheal.hrmsdk.domain.PreviousReadingItem
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Destination
import com.connectandheal.hrmsdk.view.theme.hrm.routing.FragmentRouteProtocol
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Router
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.FullPageCircularLoader
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.noRippleClickable
import com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights.BottomSheetDeleteRow
import com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights.DateSection
import com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights.EditNote
import com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights.FlowType
import com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights.HRMPreviousReadingCard
import com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights.HeartRateSummaryCard
import com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights.TabSection
import com.connectandheal.hrmsdk.view.theme.hrm.theme.AppTheme
import com.connectandheal.hrmsdk.view.theme.hrm.theme.DefaultAppBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey200
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey500
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidGreen
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SelectPatientBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight400
import com.connectandheal.hrmsdk.viewmodel.hrm.BottomSheetState
import com.connectandheal.hrmsdk.viewmodel.hrm.HeartRateHistoryViewModel
import com.connectandheal.hrmsdk.viewmodel.hrm.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class HRMHistoryScreen(
    override val destination: Destination.Fragment = Destination.Fragment(R.id.heartRateHistoryScreen),
    override val patient: Patient
) : FragmentRouteProtocol, HeartRateHistoryViewModel.Args

@AndroidEntryPoint
class HRMHistoryFragment : Fragment() {
    private val viewModel: HeartRateHistoryViewModel by viewModels()

    sealed class Action {
        object MeasureAgain : Action()
        object OpenSheetDeleteRecord : Action()
        data class OpenSheetEditNote(
            val previousReadingItem: PreviousReadingItem
        ) : Action()
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    val router = Router(context, findNavController())
                    val coroutineScope = rememberCoroutineScope()
                    val bottomSheetState = viewModel.bottomSheetState.collectAsState().value

                    val sheetState =
                        rememberModalBottomSheetState(
                            initialValue = ModalBottomSheetValue.Hidden
                        )

                    val closeBottomSheet = {
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                    }

                    val openBottomSheet = {
                        coroutineScope.launch {
                            sheetState.animateTo(targetValue = ModalBottomSheetValue.Expanded)
                        }
                    }

                    ModalBottomSheetLayout(
                        sheetState = sheetState,
                        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        sheetBackgroundColor = Color.White,
                        sheetContent = {
                            when (bottomSheetState) {
                                is BottomSheetState.DeleteRecords -> {
                                    BottomSheetDeleteRow(
                                        onClick = {
                                            viewModel.toggleDeleteButtonState()
                                            closeBottomSheet()
                                        }
                                    )
                                }
                                is BottomSheetState.EditNote -> {
                                    EditNote(
                                        bottomSheetState.previousReadingItem,
                                        onDelete = {
                                            //TODO
                                        },
                                        onSave = {
                                            //TODO
                                        },
                                        onNoteChange = viewModel::onNoteChange,
                                        note = viewModel.note.collectAsState()
                                    )
                                }
                                else -> {
                                    Box(modifier = Modifier.size(1.dp)) {}
                                }
                            }
                        }
                    ) {
                        HRMHistoryScreenContent(
                            onBackPressed = { router.popBackStack() },
                            viewModel = viewModel,
                            onAction = {
                                when (it) {
                                    is Action.OpenSheetDeleteRecord -> {
                                        viewModel.changeBottomSheetState(BottomSheetState.DeleteRecords)
                                        openBottomSheet()
                                    }

                                    is Action.OpenSheetEditNote -> {
                                        viewModel.changeBottomSheetState(
                                            BottomSheetState.EditNote(
                                                it.previousReadingItem
                                            )
                                        )
                                        openBottomSheet()
                                    }

                                    is Action.MeasureAgain -> {
                                        //TODO
                                    }
                                }
                            }
                        )
                    }
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
                            onAction(HRMHistoryFragment.Action.OpenSheetDeleteRecord)
                        },
                        painter = painterResource(id = R.drawable.vertical_menu),
                        contentDescription = "",
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
            )
        },
        content = { paddingValues ->
            when (viewState) {
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
    val showDeleteButton = viewModel.showDeleteButton.collectAsState()

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryWhite)
        ) {
            SelectPatientBar(
                name = viewModel.patient.collectAsState().value?.patientName ?: "",
                onChangeClick = { }
            )
            Spacer(modifier = Modifier.height(26.dp))

            TabSection(
                filterList = viewModel.filterList,
                selectedFilter = viewModel.selectedFilterType.collectAsState(),
                onTabClick = viewModel::onFilterChange
            )

            Column(
                modifier = Modifier
                    .background(TertiaryPastelWhite)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                DateSection(
                    onBackArrowClick = {
                        //TODO
                    },
                    onNextArrowClick = {
                        //TODO
                    },
                    currentDateString = "2 Jul 2023" //TODO
                )

                Spacer(modifier = Modifier.height(24.dp))

                HearRateReadingsContent(
                    heartRateSummaryModel = heartRateSummary,
                    previousReadings = previousReadings,
                    showDeleteButton = showDeleteButton,
                    onAction = onAction
                )
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                .fillMaxWidth(),
            onClick = {
                if (showDeleteButton.value) {
                    // TODO other action if any
                    viewModel.toggleDeleteButtonState()
                }
                else {
                    onAction(HRMHistoryFragment.Action.MeasureAgain)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimarySolidGreen,
                contentColor = PrimaryWhite
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = if (showDeleteButton.value) "Done" else "Measure Again",
                style = TextStyle_Size14_Weight400.copy(lineHeight = 21.sp)
            )
        }
    }
}

@Composable
fun HearRateReadingsContent(
    heartRateSummaryModel: State<HeartRateSummaryModel?>,
    previousReadings: State<List<PreviousReadingItem>>,
    showDeleteButton: State<Boolean>,
    onAction: (HRMHistoryFragment.Action) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        heartRateSummaryModel.value?.let {
            item {
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
                    item = item,
                    flowType = if (item.note.isNotEmpty()) FlowType.Editable else FlowType.Normal,
                    onCardClick = {
                        onAction(HRMHistoryFragment.Action.OpenSheetEditNote(item))
                    },
                    onDelete = {

                    },
                    showDelete = showDeleteButton
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
fun EmptyDataView() {

}