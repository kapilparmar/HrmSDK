package com.connectandheal.hrmsdk.viewmodel.hrm

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectandheal.hrmsdk.domain.ScanHeartRateModel
import com.connectandheal.hrmsdk.domain.ScanStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Arrays
import javax.inject.Inject

sealed class HRMViewState {
    object Initial : HRMViewState()
    data class Scanning(val scanHeartRateModel: ScanHeartRateModel) : HRMViewState()
    data class MeasureHeartRate(val scanHeartRateModel: ScanHeartRateModel) : HRMViewState()
    data class ResultAvailable(val scanHeartRateModel: ScanHeartRateModel) : HRMViewState()
    data class Error(val scanHeartRateModel: ScanHeartRateModel) : HRMViewState()
    data class MotionDetected(val scanHeartRateModel: ScanHeartRateModel) : HRMViewState()
}

@HiltViewModel
class HeartRateMeasureViewModel @Inject constructor(
) : ViewModel() {

    private var count: Int = 0
    private var mCurrentRollingAverage: Int = 0
    private var mLastRollingAverage: Int = 0
    private var mLastLastRollingAverage: Int = 0
    private val mTimeArray = LongArray(15)

    private val _hrmViewState: MutableStateFlow<HRMViewState> =
        MutableStateFlow(HRMViewState.Initial)
    val hrmViewState by lazy {
        _hrmViewState.asStateFlow()
    }

    private val _percentageProgress: MutableStateFlow<Int> = MutableStateFlow(0)
    val percentageProgress by lazy {
        _percentageProgress.asStateFlow()
    }

    private val _warningMessage: MutableStateFlow<String> = MutableStateFlow("")
    val warningMessage by lazy {
        _warningMessage.asStateFlow()
    }

    private val _heartRateInBpm: MutableStateFlow<Int> = MutableStateFlow(0)
    val heartRateInBpm by lazy {
        _heartRateInBpm.asStateFlow()
    }

    private val _mNumBeats: MutableStateFlow<Int> = MutableStateFlow(0)
    val mNumBeats by lazy {
        _mNumBeats.asStateFlow()
    }

    init {
        _hrmViewState.value = HRMViewState.Scanning(
            ScanHeartRateModel(
                title = "Scanning....",
                description = "Place your finger gently on the back camera and hold it there",
                bottomInstruction = "Place your finger on the camera to measure the heart rate",
                scanStatus = ScanStatus.SCANNING
            )
        )
        viewModelScope.launch {
            mNumBeats.collectLatest {
                _percentageProgress.value = it / 15
            }
        }
    }

    // reference : https://github.com/shivaneej/HeartRate/tree/master/app
    fun processBitmap(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.Default) {
            val width: Int = bitmap.width
            val height: Int = bitmap.height
            val pixels = IntArray(height * width)
            // Get pixels from the bitmap, starting at (x,y) = (width/2,height/2)
            // and totaling width/20 rows and height/20 columns
            bitmap.getPixels(pixels, 0, width, width / 2, height / 2, width / 20, height / 20)
            var sum = 0
            for (i in 0 until height * width) {
                val red = pixels[i] shr 16 and 0xFF
                sum += red
            }

            // Waits 20 captures, to remove startup artifacts.  First average is the sum.
            if (count == 20) {
                mCurrentRollingAverage = sum
            } else if (count in 21..48) {
                mCurrentRollingAverage =
                    (mCurrentRollingAverage * (count - 20) + sum) / (count - 19)
            } else if (count >= 49) {
                mCurrentRollingAverage = (mCurrentRollingAverage * 29 + sum) / 30
                if (mLastRollingAverage > mCurrentRollingAverage && mLastRollingAverage > mLastLastRollingAverage && mNumBeats.value < 15) {
                    mTimeArray[mNumBeats.value] = System.currentTimeMillis()
                    _mNumBeats.value = mNumBeats.value + 1
                    if (mNumBeats.value == 15) {
                        calcBPM()
                    }
                }
            }
            // Another capture
            increaseCount()
            // Save previous two values
            mLastLastRollingAverage = mLastRollingAverage
            mLastRollingAverage = mCurrentRollingAverage
        }
    }

    fun increaseCount() {
        count++
    }

    private fun calcBPM() {
        val med: Int
        val timedist = LongArray(14)
        for (i in 0..13) {
            timedist[i] = mTimeArray[i + 1] - mTimeArray[i]
        }
        Arrays.sort(timedist)
        med = timedist[timedist.size / 2].toInt()
        _heartRateInBpm.value = 60000 / med
        _hrmViewState.value = HRMViewState.MeasureHeartRate(
            ScanHeartRateModel(
                title = "Measuring...",
                description = "Keep calm and take deep breaths",
                bottomInstruction = "Keep your hands warm. Blood flow in your fingers decreases " +
                        "when your hands are cold causing a weak signal.",
                scanStatus = ScanStatus.MEASURING
            )
        )
//        _hrmViewState.value = HRMViewState.ResultAvailable
    }
}