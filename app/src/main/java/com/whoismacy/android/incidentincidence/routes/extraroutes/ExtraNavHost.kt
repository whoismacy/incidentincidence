package com.whoismacy.android.incidentincidence.routes.extraroutes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

val LocalExtraNavController =
    staticCompositionLocalOf<NavController> {
        error("EXTRA NAVCONTROLLER NOT PROVIDED!!!")
    }

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ExtraHost(
    onReturnToMain: () -> Unit,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val displayData =
        viewModel
            .displayIncidences
            .collectAsStateWithLifecycle()
            .value
    val extraNavController = rememberNavController()
    CompositionLocalProvider(LocalExtraNavController provides extraNavController) {
        NavHost(
            navController = extraNavController,
            startDestination = CreateIncidentRoute,
        ) {
            createIncidentDestination { onReturnToMain() }
            captureImageDestination()
            editDestination(incidents = displayData)
        }
    }
}
