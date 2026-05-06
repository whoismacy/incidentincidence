package com.whoismacy.android.incidentincidence.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whoismacy.android.incidentincidence.screens.TrendScreen
import kotlinx.serialization.Serializable

@Serializable
data object TrendRoute

fun NavGraphBuilder.trendDestination() {
    composable<TrendRoute> {
        TrendScreen()
    }
}

fun NavController.navigateToTrendDestination() {
    navigate(TrendRoute) {
        popUpTo(HomeRoute) {
            inclusive = true
            saveState = true
        }
    }
}
