package com.connectandheal.hrmsdk.domain

data class ScanHeartRateModel(
    val title : String,
    val description : String,
    var bottomInstruction : String,
    var tipsAndFacts : String? = null,
    var scanStatus :ScanStatus
)
enum class ScanStatus{
    SCANNING, MEASURING,ERROR,NO_DETECTION,
}
