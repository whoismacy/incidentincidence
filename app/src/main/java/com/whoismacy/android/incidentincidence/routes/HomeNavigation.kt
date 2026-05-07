package com.whoismacy.android.incidentincidence.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.screens.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeDestination() {
    composable<HomeRoute> {
        HomeScreen()
    }
}

fun NavController.navigateToHomeDestination() {
    navigate(HomeRoute) {
        popUpTo(HomeRoute) {
            inclusive = true
            saveState = true
        }
    }
}

/*
        TODO("fix the no incidents displaying in home and solved")
        TODO("Change app's font: Poppins")
        TODO("look into how to pass and receive parameters using NavGraphBuilder and NavController")
*/
