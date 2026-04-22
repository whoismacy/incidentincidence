package com.whoismacy.android.incidentincidence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.whoismacy.android.incidentincidence.ui.theme.IncidentIncidenceTheme
import com.whoismacy.android.incidentincidence.view.Fab
import com.whoismacy.android.incidentincidence.view.MainScreen
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
                MainScreen()
            }
        }
    }
}
