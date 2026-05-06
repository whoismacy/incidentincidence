package com.whoismacy.android.incidentincidence.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whoismacy.android.incidentincidence.screens.EditScreen
import kotlinx.serialization.Serializable

@Serializable
data class EditRoute(
    val id: Int,
)

fun NavGraphBuilder.editDestination() {
    composable<EditRoute> {
        EditScreen()
    }
}
