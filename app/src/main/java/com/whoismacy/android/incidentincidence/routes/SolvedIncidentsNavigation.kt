package com.whoismacy.android.incidentincidence.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whoismacy.android.incidentincidence.screens.SolvedIncidentsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SolvedIncidentsRoute

fun NavGraphBuilder.solvedIncidentDestination() {
    composable<SolvedIncidentsRoute> {
        SolvedIncidentsScreen()
    }
}

fun NavController.navigateToSolvedIncidentDestination() {
    navigate(SolvedIncidentsRoute)
}
