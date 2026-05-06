package com.whoismacy.android.incidentincidence.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.screens.EditScreen
import kotlinx.serialization.Serializable

@Serializable
data class EditRoute(
    val id: Int,
)

fun NavGraphBuilder.editDestination(
    incidents: List<Incident>,
) {
    composable<EditRoute> { backStackEntry ->
        val editRoute = backStackEntry.toRoute<EditRoute>()
        EditScreen(
            id = editRoute.id,
            incidents,
        )
    }
}

fun NavController.navigateToEditDestination(id: Int) {
    navigate(EditRoute(id))
}
