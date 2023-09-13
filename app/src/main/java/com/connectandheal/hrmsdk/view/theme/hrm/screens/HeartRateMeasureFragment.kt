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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.domain.ScanHeartRateModel
import com.connectandheal.hrmsdk.domain.ScanStatus
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Destination
import com.connectandheal.hrmsdk.view.theme.hrm.routing.FragmentRouteProtocol
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Router
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.BottomInstructions
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.HeartRateMeasuring
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.HeartRateResult
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.TopInstructions
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.VoiceInstructions
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.toBitmap
import com.connectandheal.hrmsdk.view.theme.hrm.theme.AppTheme
import com.connectandheal.hrmsdk.view.theme.hrm.theme.DefaultAppBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey500
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SelectPatientBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight400
import com.connectandheal.hrmsdk.viewmodel.hrm.HRMViewState
import com.connectandheal.hrmsdk.viewmodel.hrm.HeartRateMeasureViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeartRateMeasureScreen(
    override val destination: Destination.Fragment = Destination.Fragment(R.id.heartRateMeasureScreen)
) : FragmentRouteProtocol

private var cameraController: LifecycleCameraController? = null

@AndroidEntryPoint
class HeartRateMeasureFragment : Fragment() {
    private val viewModel: HeartRateMeasureViewModel by viewModels()


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
                    MainContent(
                        viewModel = viewModel,
                        cameraController = cameraController,
                        onBackPress = {},
                        onSaveClick = { router.navigate(ReadingErrorScreen()) }
                    )
                }
            }
        }
    }

    @Composable
    fun MainContent(
        viewModel: HeartRateMeasureViewModel,
        cameraController: LifecycleCameraController?,
        onBackPress :()-> Unit,
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
                                    onBackPress()
                                }
                                .padding(end = 16.dp),
                            text = "History",
                            style = TextStyle_Size14_Weight400.copy(lineHeight = 22.sp),
                            color = PrimarySolidBlue
                        )
                    }
                )
            },
            content = { paddingValues ->
                HeartRateMeasureContent(
                    paddingValues = paddingValues,
                    viewModel = viewModel,
                    cameraController = cameraController,
                    onSaveClick = onSaveClick
                )
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
    onSaveClick: () -> Unit
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
                    scanHeartRateModel = viewState.scanHeartRateModel
                )
            }

            is HRMViewState.ResultAvailable -> {
                HeartRateResult(hrmResultModel = viewState.hrmResultModel, onSaveClick = onSaveClick)
            }

            is HRMViewState.MotionDetected -> {
                MeasureHeartRateScreen(
                    cameraController = cameraController,
                    scanHeartRateModel = viewState.scanHeartRateModel
                )
            }

            is HRMViewState.Scanning -> {
                MeasureHeartRateScreen(
                    cameraController = cameraController,
                    scanHeartRateModel = viewState.scanHeartRateModel
                )
            }

            is HRMViewState.Error -> {}
            is HRMViewState.Initial -> {}
        }
    }
}

@Composable
fun HRMResultScreen(
    heartRateInBpm: State<Int>,
    cameraController: LifecycleCameraController?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${heartRateInBpm.value}")
    }
}

@OptIn(ExperimentalGetImage::class)
@Composable
fun MeasureHeartRateScreen(
    cameraController: LifecycleCameraController?,
    scanHeartRateModel: ScanHeartRateModel
) {
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
            when (scanHeartRateModel.scanStatus ) {
                ScanStatus.SCANNING ->
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

                ScanStatus.MEASURING -> {HeartRateMeasuring()}
                ScanStatus.ERROR -> {}
                ScanStatus.NO_DETECTION -> {}
            }

            VoiceInstructions()
        }
        BottomInstructions(scanHeartRateModel.bottomInstruction)

    }
}

@Preview
@Composable
fun Preview() {
    MeasureHeartRateScreen(
        cameraController,
        scanHeartRateModel = ScanHeartRateModel(
            title = "Scanning",
            description = "Place your finger gently on the back camera and hold it there",
            bottomInstruction = "Place your finger on the camera to measure the heart rate",
            scanStatus = ScanStatus.SCANNING
        )
    )
}
