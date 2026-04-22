package com.whoismacy.android.incidentincidence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.whoismacy.android.incidentincidence.ui.theme.IncidentIncidenceTheme
import com.whoismacy.android.incidentincidence.view.IncidentIncidenceScreen
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<IncidentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isAvailable.value
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IncidentIncidenceTheme {
                IncidentIncidenceScreen()
            }
        }
    }
}
