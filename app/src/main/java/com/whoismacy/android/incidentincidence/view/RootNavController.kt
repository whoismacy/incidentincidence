package com.whoismacy.android.incidentincidence.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whoismacy.android.incidentincidence.viewScreens.CaptureImage
import kotlinx.serialization.Serializable

@Serializable
object IncidentIncidenceRoute

@Serializable
object CreateIncidentRoute

@Serializable
object CaptureImageRoute

@Composable
fun RootNavController() {
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
    }
}
