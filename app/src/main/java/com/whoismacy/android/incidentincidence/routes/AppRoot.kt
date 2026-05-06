package com.whoismacy.android.incidentincidence.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whoismacy.android.incidentincidence.screens.CaptureImage
import com.whoismacy.android.incidentincidence.screens.HomeScreen
import com.whoismacy.android.incidentincidence.screens.IncidentIncidenceScreen
import com.whoismacy.android.incidentincidence.screens.SolvedIncidentsScreen
import com.whoismacy.android.incidentincidence.screens.TrendScreen
import com.whoismacy.android.incidentincidence.view.NewIncident
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel
import kotlinx.serialization.Serializable

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AppRoot(
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val displayData = viewModel.displayIncidences.collectAsStateWithLifecycle()
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = IncidentIncidenceRoute,
    ) {
        composable<IncidentIncidenceRoute> {
            IncidentIncidenceScreen(rootNavController = rootNavController)
        }

        composable<CreateIncidentRoute> {
            NewIncident(rootNavController)
        }

        composable<CaptureImageRoute> {
            CaptureImage()
        }

        composable<HomeRoute> {
            HomeScreen(incidences = displayData, navController = rootNavController)
        }

        composable<SolvedIncidentsRoute> {
            SolvedIncidentsScreen(incidences = displayData, navController = rootNavController)
        }

        composable<TrendRoute> {
            TrendScreen()
        }
    }
}
