package com.whoismacy.android.incidentincidence.routes.extraroutes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whoismacy.android.incidentincidence.routes.MainGraph
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
        popUpTo(MainGraph) {
            saveState = true
        }
    }
}
