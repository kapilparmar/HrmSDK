package com.connectandheal.hrmsdk.view.theme.hrm.screens


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Destination
import com.connectandheal.hrmsdk.view.theme.hrm.routing.FragmentRouteProtocol
import com.connectandheal.hrmsdk.viewmodel.hrm.HRMViewState
import com.connectandheal.hrmsdk.viewmodel.hrm.HeartRateMeasureViewModel
import com.soscare.customer.view.common.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import java.io.ByteArrayOutputStream

@Parcelize
data class HeartRateMeasureScreen(
    override val destination: Destination.Fragment = Destination.Fragment(R.id.heartRateMeasureScreen)
): FragmentRouteProtocol

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
                        viewModel
                    )
                }
            }
        }
    }

    @Composable
    fun MainContent(
        viewModel: HeartRateMeasureViewModel
    ) {
        Scaffold(
            content = { paddingValues ->
                HeartRateMeasureContent(
                    paddingValues = paddingValues,
                    viewModel = viewModel
                )
            }
        )
    }

    @OptIn(ExperimentalGetImage::class)
    @Composable
    fun HeartRateMeasureContent(
        paddingValues: PaddingValues,
        viewModel: HeartRateMeasureViewModel
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
                    cameraController?.imageAnalysisBackpressureStrategy = ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
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

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
            when (viewState.value) {
                is HRMViewState.MeasureHeartRate -> {
                    MeasureHeartRateScreen(viewModel)
                }
                is HRMViewState.ResultAvailable -> {
                    HRMResultScreen(
                        viewModel.heartRateInBpm.collectAsState()
                    )
                }
                is HRMViewState.Error -> {}
            }
        }
    }

    @Composable
    fun HRMResultScreen(
        heartRateInBpm: State<Int>
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
        viewModel: HeartRateMeasureViewModel
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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
            ProgressView(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }

    @kotlin.OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun ProgressView(
        viewModel: HeartRateMeasureViewModel,
        modifier: Modifier
    ) {
        val percentage = viewModel.percentageProgress.collectAsStateWithLifecycle()

        Column(modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Calculating...")
        }
    }

    //convert YUV_420_888 Image to bitmap ARGB_8888
    fun Image.toBitmap(): Bitmap {

        val yBuffer = planes[0].buffer // Y
        val vuBuffer = planes[2].buffer // VU

        val ySize = yBuffer.remaining()
        val vuSize = vuBuffer.remaining()

        val nv21 = ByteArray(ySize + vuSize)

        yBuffer.get(nv21, 0, ySize)
        vuBuffer.get(nv21, ySize, vuSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}