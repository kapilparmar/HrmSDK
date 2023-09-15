package com.connectandheal.hrmsdk.viewmodel.hrm

import androidx.constraintlayout.motion.utils.ViewState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectandheal.hrmsdk.domain.FilterType
import com.connectandheal.hrmsdk.domain.HeartRateSummaryModel
import com.connectandheal.hrmsdk.domain.HeartRateSummaryType
import com.connectandheal.hrmsdk.domain.HeartRateZone
import com.connectandheal.hrmsdk.domain.Patient
import com.connectandheal.hrmsdk.domain.PreviousReadingItem
import com.connectandheal.hrmsdk.view.theme.hrm.routing.InputArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed class ViewState {
    object Nothing: ViewState()
    object Loading: ViewState()
    object Loaded: ViewState()
}

sealed class BottomSheetState {
    object Nothing: BottomSheetState()
    object DeleteRecords : BottomSheetState()
    data class EditNote(
        val previousReadingItem: PreviousReadingItem
    ): BottomSheetState()
}

@HiltViewModel
class HeartRateHistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    interface Args: InputArgs {
        val patient: Patient
    }

    val args = (savedStateHandle.get(InputArgs.KEY) as? Args)

    private val _patient: MutableStateFlow<Patient?> = MutableStateFlow(args?.patient)
    val patient by lazy {
        _patient.asStateFlow()
    }

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(com.connectandheal.hrmsdk.viewmodel.hrm.ViewState.Nothing)
    val viewState by lazy {
        _viewState.asStateFlow()
    }

    private val _selectedFilterType: MutableStateFlow<FilterType> = MutableStateFlow(FilterType.DAILY)
    val selectedFilterType by lazy {
        _selectedFilterType.asStateFlow()
    }

    private val _bottomSheetState: MutableStateFlow<BottomSheetState> = MutableStateFlow(BottomSheetState.Nothing)
    val bottomSheetState by lazy {
        _bottomSheetState.asStateFlow()
    }

    private val _note: MutableStateFlow<String> = MutableStateFlow("")
    val note by lazy {
        _note.asStateFlow()
    }

    val filterList = listOf<FilterType>(
        FilterType.DAILY,
        FilterType.WEEKLY,
        FilterType.MONTHLY,
        FilterType.YEARLY
    )

    private val _previousReadings: MutableStateFlow<List<PreviousReadingItem>> = MutableStateFlow(
        listOf(
            PreviousReadingItem(
                id = "1",
                category = "Post Sleep",
                measuredOn = "3.00 PM, Tuesday, 4 July 2023 ",
                heartRateValue = "89",
                heartRateZone = HeartRateZone.HEALTHY
            ),
            PreviousReadingItem(
                id = "1",
                category = "Post Walking",
                measuredOn = "3.00 PM, Tuesday, 4 July 2023 ",
                heartRateValue = "110",
                heartRateZone = HeartRateZone.HIGH
            ),
            PreviousReadingItem(
                id = "1",
                category = "Post Walking",
                measuredOn = "3.00 PM, Tuesday, 4 July 2023 ",
                heartRateValue = "110",
                heartRateZone = HeartRateZone.HIGH,
                note = "Intermittent fasting for 4 hours when measuring hr"
            )
        )
    )
    val previousReadings by lazy {
        _previousReadings.asStateFlow()
    }

    private val _heartRateSummary: MutableStateFlow<HeartRateSummaryModel?> = MutableStateFlow(
        HeartRateSummaryModel(
            lowest = HeartRateSummaryType(60, HeartRateZone.HEALTHY),
            average = HeartRateSummaryType(90, HeartRateZone.HEALTHY),
            highest = HeartRateSummaryType(166, HeartRateZone.HIGH),
        )
    )
    val hearRateSummary by lazy {
        _heartRateSummary.asStateFlow()
    }

    init {
        viewModelScope.launch {
            patient.collectLatest {
                it?.let {
                    fetchHistory()
                }
            }
        }

        viewModelScope.launch {
            selectedFilterType.collectLatest {
                fetchHistory()
            }
        }
    }

    fun fetchHistory() {
        viewModelScope.launch {
            _viewState.value = com.connectandheal.hrmsdk.viewmodel.hrm.ViewState.Loaded
            //call use case with latest patientId and filterType
        }
    }

    fun onFilterChange(selectedFilter: FilterType) {
        _selectedFilterType.value = selectedFilter
    }

    fun changeBottomSheetState (sheetState: BottomSheetState) {
        _bottomSheetState.value = sheetState
        when(sheetState) {
            is BottomSheetState.EditNote -> {
                _note.value = sheetState.previousReadingItem.note
            }
            else -> {}
        }
    }

    fun onNoteChange(newNote: String) {
        _note.value = newNote
    }
}