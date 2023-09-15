package com.connectandheal.hrmsdk.domain

data class ScanHeartRateModel(
    val title : String,
    val description : String,
    var bottomInstruction : String,
    var tipsAndFacts : String? = null,
    var scanStatus :ScanStatus,
    var disclaimer : String = "Heartbeat is for reference purposes only. Consult your doctor if " +
            "required. Since this heart rate app uses flash, some devices may cause hot LED " +
            "flashes. Heart rate detection is not intended to be used as a medical device."
)
enum class ScanStatus{
    SCANNING, MEASURING,ERROR,NO_DETECTION,
}
