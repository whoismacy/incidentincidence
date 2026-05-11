package com.whoismacy.android.incidentincidence.routes.extraroutes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whoismacy.android.incidentincidence.view.NewIncident
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel
import kotlinx.serialization.Serializable

@Serializable
object CreateIncidentRoute

fun NavGraphBuilder.createIncidentDestination(
    onNavigateHome: () -> Unit,
    viewModel: IncidentViewModel,
) {
    composable<CreateIncidentRoute> {
        NewIncident(onNavigateHome = onNavigateHome, viewModel = viewModel)
    }
}

fun NavController.navigateToNewIncidentDestination() {
    navigate(CreateIncidentRoute)
}
