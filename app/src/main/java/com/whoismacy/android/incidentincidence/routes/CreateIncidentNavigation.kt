package com.whoismacy.android.incidentincidence.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whoismacy.android.incidentincidence.view.NewIncident
import kotlinx.serialization.Serializable

@Serializable
object CreateIncidentRoute

fun NavGraphBuilder.createIncidentDestination(onNavigateHome: () -> Unit) {
    composable<CreateIncidentRoute> {
        NewIncident(onNavigateHome = onNavigateHome)
    }
}

fun NavController.navigateToNewIncidentDestination() {
    navigate(CreateIncidentRoute)
}
