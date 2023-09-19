package com.connectandheal.hrmsdk.view.theme.hrm.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.domain.HRMResultModel
import com.connectandheal.hrmsdk.domain.Patient
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Destination
import com.connectandheal.hrmsdk.view.theme.hrm.routing.FragmentRouteProtocol
import com.connectandheal.hrmsdk.view.theme.hrm.routing.Router
import com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure.HeartRateResult
import com.connectandheal.hrmsdk.view.theme.hrm.theme.AppTheme
import com.connectandheal.hrmsdk.view.theme.hrm.theme.DefaultAppBar
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey500
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight400
import com.connectandheal.hrmsdk.viewmodel.hrm.HeartRateMeasureViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeartRateResultScreen(
    override val destination: Destination.Fragment = Destination.Fragment(R.id.heartRateResultScreen),
) : FragmentRouteProtocol


@AndroidEntryPoint
class HeartRateResultFragment : Fragment() {
    private val viewModel: HeartRateMeasureViewModel by viewModels()

    sealed class Action {
        object HistoryScreen : Action()
        object Back : Action()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    val router = Router(context, findNavController())
                    MainContent(onAction = {
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

                            is Action.Back -> {
                                router.popBackStack()
                            }
                        }

                    })
                }
            }
        }
    }
}

@Composable
private fun MainContent(
    onAction: (HeartRateResultFragment.Action) -> Unit,
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
                                onAction(HeartRateResultFragment.Action.HistoryScreen)
                            }
                            .padding(end = 16.dp),
                        text = "See Insights",
                        style = TextStyle_Size14_Weight400.copy(lineHeight = 22.sp),
                        color = PrimarySolidBlue
                    )
                },
                onBackPressed = { onAction(HeartRateResultFragment.Action.Back) }
            )
        },
        content = {_->
            HeartRateResult(hrmResultModel = HRMResultModel(), onSaveClick = {},
                onEnterNote = {})
        })
}
