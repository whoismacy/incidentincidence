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
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.routes.extraroutes.CreateIncidentRoute
import com.whoismacy.android.incidentincidence.routes.extraroutes.captureImageDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.createIncidentDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.editDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.navigateToCaptureImage
import com.whoismacy.android.incidentincidence.routes.extraroutes.navigateToNewIncidentDestination
import com.whoismacy.android.incidentincidence.routes.mainapphost.MainAppHost
import com.whoismacy.android.incidentincidence.routes.mainapphost.navigateToTrendDestination
import com.whoismacy.android.incidentincidence.routes.mainapphost.trendDestination
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel
import kotlinx.serialization.Serializable

@Serializable
data object ExtrasNavigation

@Serializable
data object MainGraph

val LocalRootNavController =
    staticCompositionLocalOf<NavController> {
        error("ROOT NAV CONTROLLER NOT PROVIDED!!!")
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
            MainGraph,
        ) {
            composable<MainGraph> {
                MainAppHost(
                    {
                        rootNavController
                            .navigateToNewIncidentDestination()
                    },
                    {
                        rootNavController
                            .navigateToTrendDestination()
                    },
                    viewModel,
                )
            }
            extrasGraph(
                rootNavController,
                displayData,
                viewModel,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.extrasGraph(
    rootNavController: NavController,
    displayData: List<Incident>,
    viewModel: IncidentViewModel,
) {
    navigation<ExtrasNavigation>(startDestination = CreateIncidentRoute) {
        trendDestination()
        captureImageDestination()
        createIncidentDestination(onNavigateHome = {
            rootNavController
                .popBackStack()
        }, viewModel = viewModel)
        editDestination(
            incidents = displayData,
            viewModel = viewModel,
            onNavigateCaptureImage = { rootNavController.navigateToCaptureImage() },
            onNavigateHome = { rootNavController.popBackStack() },
        )
    }
}
