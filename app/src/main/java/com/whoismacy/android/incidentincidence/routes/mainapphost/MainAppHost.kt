package com.whoismacy.android.incidentincidence.routes.mainapphost

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.whoismacy.android.incidentincidence.screens.HomeScreen
import com.whoismacy.android.incidentincidence.screens.SolvedIncidentsScreen
import com.whoismacy.android.incidentincidence.view.BottomNavigation
import com.whoismacy.android.incidentincidence.view.Fab
import com.whoismacy.android.incidentincidence.view.SearchBar
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

val LocalIncidentViewModel =
    staticCompositionLocalOf<IncidentViewModel> {
        error("NO VIEWMODEL PROVIDED!!")
    }

@Composable
fun MainAppHost(
    onNavigateNewIncidentScreen: () -> Unit,
    onNavigateTrendScreen: () -> Unit,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val displayFilterState by viewModel
        .displayFilterState
        .collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val mainAppNavController = rememberNavController()

    val currentDestination =
        mainAppNavController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route ?: ""

    val onNavigateHome = {
        mainAppNavController
            .navigateToHomeDestination()
    }
    val onNavigateSolved = {
        mainAppNavController
            .navigateToSolvedIncidentDestination()
    }

    CompositionLocalProvider(
        LocalIncidentViewModel provides viewModel,
    ) {
        Scaffold(
            topBar = {
                SearchBar(displayFilterState.searchQuery, viewModel)
            },
            bottomBar = {
                BottomNavigation(
                    onNavigateHome = onNavigateHome,
                    onNavigateSolved = onNavigateSolved,
                    onNavigateTrend = onNavigateTrendScreen,
                    currentDestination = currentDestination,
                )
            },
            snackbarHost = {
                SnackbarHost(snackBarHostState) { snackbarData ->
                    Snackbar(
                        modifier =
                            Modifier
                                .padding(16.dp)
                                .clip(RoundedCornerShape(12.dp)),
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        actionColor = MaterialTheme.colorScheme.primary,
                        actionContentColor = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(12.dp),
                        snackbarData = snackbarData,
                    )
                }
            },
            floatingActionButton = {
                Fab { onNavigateNewIncidentScreen() }
            },
        ) { innerPadding ->
            NavHost(
                navController = mainAppNavController,
                startDestination = HomeRoute,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable<HomeRoute> {
                    HomeScreen()
                }

                composable<SolvedIncidentsRoute> {
                    SolvedIncidentsScreen()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvents.collect { event ->
            val result =
                snackBarHostState.showSnackbar(
                    message = event.message,
                    actionLabel = event.actionLabel,
                    duration = SnackbarDuration.Short,
                )

            if (result == SnackbarResult.ActionPerformed) {
                event.action()
            }
        }
    }
}
