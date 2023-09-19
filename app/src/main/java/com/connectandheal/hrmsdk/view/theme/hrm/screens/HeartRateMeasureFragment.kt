package com.connectandheal.hrmsdk.view.theme.hrm.screens


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.domain.Patient
import com.connectandheal.hrmsdk.domain.ScanHeartRateModel
import com.connectandheal.hrmsdk.domain.ScanStatus
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Destination
import com.connectandheal.hrmsdk.view.theme.hrm.routing.FragmentRouteProtocol
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Router
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.PPGChart
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.ActivitySheetContent
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.BottomInstructions
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.Disclaimer
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.HeartRateMeasuring
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.TopInstructions
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.toBitmap
import com.connectandheal.hrmsdk.view.theme.hrm.theme.AppTheme
import com.connectandheal.hrmsdk.view.theme.hrm.theme.DefaultAppBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey500
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SelectPatientBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight400
import com.connectandheal.hrmsdk.viewmodel.hrm.HRMViewState
import com.connectandheal.hrmsdk.viewmodel.hrm.HRMeasuredValues
import com.connectandheal.hrmsdk.viewmodel.hrm.HeartRateMeasureViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeartRateMeasureScreen(
    override val destination: Destination.Fragment = Destination.Fragment(R.id.heartRateMeasureScreen),
) : FragmentRouteProtocol

private var cameraController: LifecycleCameraController? = null

@AndroidEntryPoint
class HeartRateMeasureFragment : Fragment() {
    private val viewModel: HeartRateMeasureViewModel by viewModels()

    sealed class Action {
        object HistoryScreen : Action()
        object Back : Action()
        object ResultScreen : Action()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            cameraController = LifecycleCameraController(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            val router = Router(context = context)
            setContent {
                AppTheme {
                    val router = Router(context, findNavController())

                    MainContent(
                        viewModel = viewModel,
                        cameraController = cameraController,
                        onAction = {
                            when (it) {
                                is Action.HistoryScreen -> {
                                    router.navigate(
                                        HRMHistoryScreen(
                                            patient = Patient(
                                                patientId = "",
                                                patientName = ""
                                            )
                                        )
                                    )
                                }

                                is Action.Back -> {}
                                is Action.ResultScreen ->
                                    router.navigate(
                                        HeartRateResultScreen()
                                    )

                            }
                        },
                        onSaveClick = { router.navigate(ReadingErrorScreen()) }
                    )
                }
            }
        }
    }

    @kotlin.OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MainContent(
        viewModel: HeartRateMeasureViewModel,
        cameraController: LifecycleCameraController?,
        onAction: (Action) -> Unit,
        onSaveClick: () -> Unit
    ) {
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
                        Text(
                            modifier = Modifier
                                .clickable {
                                    onAction(Action.HistoryScreen)
                                }
                                .padding(end = 16.dp),
                            text = "History",
                            style = TextStyle_Size14_Weight400.copy(lineHeight = 22.sp),
                            color = PrimarySolidBlue
                        )
                    },
                    onBackPressed = { onAction(Action.Back) }
                )
            },
            content = { paddingValues ->
                val coroutineScope = rememberCoroutineScope()
                val sheetState =
                    rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

                val openSheet: () -> Unit = {
                    coroutineScope.launch {
                        sheetState.show()
                    }
                }

                val closeSheet: () -> Unit = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                }

                LaunchedEffect(Unit) {
                    snapshotFlow { sheetState.currentValue }
                        .collect {
                            if (it == ModalBottomSheetValue.Hidden) {
                                closeSheet()
                            }
                        }
                }

                ModalBottomSheetLayout(
                    sheetState = sheetState,
                    sheetBackgroundColor = PrimaryWhite,
                    sheetContent = {
                        ActivitySheetContent(categoryList = viewModel.activities,
                            onAction = {
                                onAction(Action.ResultScreen)
                            })
                    },
                    sheetShape = RoundedCornerShape(10.dp)
                ) {
                    Box(Modifier.fillMaxSize()) {
                        HeartRateMeasureContent(
                            paddingValues = paddingValues,
                            viewModel = viewModel,
                            cameraController = cameraController,
                            onSaveClick = onSaveClick,
                            openSheet = { openSheet() }
                        )
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalGetImage::class)
@Composable
fun HeartRateMeasureContent(
    paddingValues: PaddingValues,
    viewModel: HeartRateMeasureViewModel,
    cameraController: LifecycleCameraController?,
    onSaveClick: () -> Unit,
    openSheet: (hrValue: Int) -> Unit,

    ) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val executor = ContextCompat.getMainExecutor(context)
    val viewState = viewModel.hrmViewState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    val imageAnalysis = ImageAnalysis.Analyzer { frame: ImageProxy ->
        frame.image?.toBitmap()?.let {
            viewModel.processBitmap(it)
        }
        frame.close()
    }

    LaunchedEffect(key1 = viewState) {
        when (viewState) {
            is HRMViewState.MeasureHeartRate,
            is HRMViewState.Scanning -> {
                cameraController?.bindToLifecycle(lifecycleOwner)
                cameraController?.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                cameraController?.enableTorch(true)
                cameraController?.imageAnalysisBackpressureStrategy =
                    ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
                cameraController?.setImageAnalysisAnalyzer(
                    executor,
                    imageAnalysis
                )
            }

            is HRMViewState.ResultAvailable -> {
                cameraController?.clearImageAnalysisAnalyzer()
                cameraController?.unbind()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (viewState) {
            is HRMViewState.MeasureHeartRate -> {
                MeasureHeartRateScreen(
                    cameraController = cameraController,
                    scanHeartRateModel = viewState.scanHeartRateModel,
                    hrValue = viewModel.hrValue
                )
            }

            is HRMViewState.ResultAvailable -> {
                openSheet(viewState.hrmResultModel.hrValue)
            }

            is HRMViewState.MotionDetected -> {
                MeasureHeartRateScreen(
                    cameraController = cameraController,
                    scanHeartRateModel = viewState.scanHeartRateModel,
                    hrValue = viewModel.hrValue
                )
            }

            is HRMViewState.Scanning -> {
                MeasureHeartRateScreen(
                    cameraController = cameraController,
                    scanHeartRateModel = viewState.scanHeartRateModel,
                    hrValue = viewModel.hrValue
                )
            }

            is HRMViewState.Error -> {}
            is HRMViewState.Initial -> {}
            else -> {}
        }
    }
}


@OptIn(ExperimentalGetImage::class)
@Composable
fun MeasureHeartRateScreen(
    cameraController: LifecycleCameraController?,
    scanHeartRateModel: ScanHeartRateModel,
    hrValue: StateFlow<HRMeasuredValues?>
) {
    val hrValueState = hrValue.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = TertiaryPastelWhite)
            .verticalScroll(rememberScrollState()),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = TertiaryPastelWhite),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectPatientBar(onChangeClick = { })
            TopInstructions(
                title = scanHeartRateModel.title,
                description = scanHeartRateModel.description
            )
            when (scanHeartRateModel.scanStatus) {
                ScanStatus.SCANNING -> {

                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(142.dp)
                            .background(color = Color.White, shape = CircleShape)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(112.dp)
                                .align(Alignment.Center)
                        ) {
                            AndroidView(
                                modifier = Modifier,
                                factory = { context ->
                                    val previewView = PreviewView(context).apply {
                                        this.scaleType = scaleType
                                        layoutParams = ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT
                                        )
                                    }.also {
                                        it.controller = cameraController
                                    }
                                    previewView
                                }
                            )
                        }
                    }
                    Disclaimer(disclaimer = scanHeartRateModel.disclaimer)
                }

                ScanStatus.MEASURING -> {
                    HeartRateMeasuring(hrValue = hrValueState, cameraController = cameraController)
                    hrValueState?.hrValueList?.let {
                        PPGChart(
                            hrValue = it,
                            modifier = Modifier,
                            lineColor = Color.Transparent,
                            lineWidth = 2f
                        )
                    }
                }

                ScanStatus.ERROR -> {}
                ScanStatus.NO_DETECTION -> {}
            }
        }
        BottomInstructions(scanHeartRateModel.bottomInstruction)

    }
}
