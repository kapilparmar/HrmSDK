package com.connectandheal.hrmsdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.connectandheal.hrmsdk.databinding.ActivityHeartRateMeasureBinding
import com.connectandheal.hrmsdk.view.theme.hrm.routing.ActivityRouteProtocol
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Destination
import com.connectandheal.hrmsdk.view.theme.hrm.routing.FragmentRouteProtocol
import com.connectandheal.hrmsdk.view.theme.hrm.routing.companionClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize


@AndroidEntryPoint
class HeartRateMeasureActivity : AppCompatActivity() {

    companion object
    @Parcelize
    data class Route(
        override val destination: Destination.Activity = Destination.Activity(this::class.companionClass),
        override val startDestination: FragmentRouteProtocol? = null
    ) : ActivityRouteProtocol

    private lateinit var binding: ActivityHeartRateMeasureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeartRateMeasureBinding.inflate(layoutInflater)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)
    }
}