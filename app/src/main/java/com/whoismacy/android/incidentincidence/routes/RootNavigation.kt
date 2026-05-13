package com.whoismacy.android.incidentincidence.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.whoismacy.android.incidentincidence.routes.extraroutes.CreateIncidentRoute
import com.whoismacy.android.incidentincidence.routes.extraroutes.captureImageDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.createIncidentDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.editDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.navigateToCaptureImage
import com.whoismacy.android.incidentincidence.routes.extraroutes.navigateToNewIncidentDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.navigateToTrendDestination
import com.whoismacy.android.incidentincidence.routes.extraroutes.trendDestination
import com.whoismacy.android.incidentincidence.routes.mainapphost.MainAppHost
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

val LocalIncidentViewModel =
    staticCompositionLocalOf<IncidentViewModel> {
        error("NO VIEWMODEL PROVIDED!!")
    }

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun RootNavigation(
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val rootNavController = rememberNavController()

    CompositionLocalProvider(
        LocalRootNavController provides rootNavController,
        LocalIncidentViewModel provides viewModel,
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
                )
            }
            extrasGraph(
                rootNavController,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.extrasGraph(
    rootNavController: NavController,
) {
    navigation<ExtrasNavigation>(startDestination = CreateIncidentRoute) {
        trendDestination()
        captureImageDestination { rootNavController.popBackStack() }
        createIncidentDestination(onNavigateHome = {
            rootNavController
                .popBackStack()
        })
        editDestination(
            onNavigateCaptureImage = { id -> rootNavController.navigateToCaptureImage(id) },
            onNavigateHome = { rootNavController.popBackStack() },
        )
    }
}
