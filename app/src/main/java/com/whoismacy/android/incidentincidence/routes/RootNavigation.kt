package com.whoismacy.android.incidentincidence.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.whoismacy.android.incidentincidence.routes.extraroutes.captureImageDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.createIncidentDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.editDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.navigateToCaptureImage
import com.whoismacy.android.incidentincidence.routes.mainapphost.HomeRoute
import com.whoismacy.android.incidentincidence.routes.mainapphost.MainAppHost
import com.whoismacy.android.incidentincidence.routes.mainapphost.navigateToHomeDestination
import com.whoismacy.android.incidentincidence.routes.mainapphost.trendDestination
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel
import kotlinx.serialization.Serializable

@Serializable
data object HomeNavigation

val LocalRootNavController =
    staticCompositionLocalOf<NavController> {
        error("ROOT NAV CONTROLLER NOT PROVIDED")
    }

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun RootNavigation(
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val rootNavController = rememberNavController()

    val displayData =
        viewModel
            .displayIncidences
            .collectAsStateWithLifecycle()
            .value

    CompositionLocalProvider(
        LocalRootNavController provides rootNavController,
    ) {
        NavHost(
            rootNavController,
            HomeNavigation,
        ) {
            navigation<HomeNavigation>(HomeRoute) {
                mainAppHost(rootNavController)
            }
            createIncidentDestination {
                rootNavController
                    .navigateToHomeDestination()
            }
            trendDestination()
            captureImageDestination()
            editDestination(
                displayData,
            ) { rootNavController.navigateToCaptureImage() }
        }
    }
}

fun NavGraphBuilder.mainAppHost(rootNavController: NavController) {
    composable<HomeNavigation> {
        MainAppHost(
            rootNavController = rootNavController,
        )
    }
}
