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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Destination
import com.connectandheal.hrmsdk.view.theme.hrm.routing.FragmentRouteProtocol
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.SelectPatientBar
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.BottomInstructions
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.TopInstructions
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.VoiceInstructions
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.toBitmap
import com.connectandheal.hrmsdk.viewmodel.hrm.HRMViewState
import com.connectandheal.hrmsdk.viewmodel.hrm.HeartRateMeasureViewModel
import com.soscare.customer.view.common.theme.AppTheme
import com.soscare.customer.view.common.theme.TertiaryPastelWhite
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeartRateMeasureScreen(
    override val destination: Destination.Fragment = Destination.Fragment(R.id.heartRateMeasureScreen)
) : FragmentRouteProtocol

@AndroidEntryPoint
class HeartRateMeasureFragment : Fragment() {
    private val viewModel: HeartRateMeasureViewModel by viewModels()


    private var cameraController: LifecycleCameraController? = null

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
            setContent {
                AppTheme {
                    MainContent(
                        viewModel = viewModel,
                        cameraController = cameraController
                    )
                }
            }
        }
    }

    @Composable
    fun MainContent(
        viewModel: HeartRateMeasureViewModel,
        cameraController: LifecycleCameraController?
    ) {
        Scaffold(
            content = { paddingValues ->
                HeartRateMeasureContent(
                    paddingValues = paddingValues,
                    viewModel = viewModel,
                    cameraController = cameraController
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
    cameraController: LifecycleCameraController?
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val executor = ContextCompat.getMainExecutor(context)
    val viewState = viewModel.hrmViewState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val imageAnalysis = ImageAnalysis.Analyzer { frame: ImageProxy ->
        frame.image?.toBitmap()?.let {
            viewModel.processBitmap(it)
        }
        frame.close()
    }

    LaunchedEffect(key1 = viewState.value) {
        when (viewState.value) {
            is HRMViewState.MeasureHeartRate -> {
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

            is HRMViewState.Error -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (viewState.value) {
            is HRMViewState.MeasureHeartRate -> {
                MeasureHeartRateScreen(
                    viewModel = viewModel,
                    cameraController = cameraController
                )
            }

            is HRMViewState.ResultAvailable -> {
                HRMResultScreen(
                    viewModel.heartRateInBpm.collectAsState(),
                    cameraController = cameraController
                )
            }

            is HRMViewState.Error -> {}
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
    viewModel: HeartRateMeasureViewModel,
    cameraController: LifecycleCameraController?
) {
    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color = TertiaryPastelWhite),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectPatientBar(onChangeClick = { })
            TopInstructions()
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
            VoiceInstructions()
        }
        BottomInstructions()

    }
}
