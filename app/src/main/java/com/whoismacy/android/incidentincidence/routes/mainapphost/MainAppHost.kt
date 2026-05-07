package com.whoismacy.android.incidentincidence.routes.mainapphost

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.routes.extraroutes.navigateToNewIncidentDestination
import com.whoismacy.android.incidentincidence.view.Fab
import com.whoismacy.android.incidentincidence.view.SearchBar
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

val LocalIncidentViewModel =
    staticCompositionLocalOf<IncidentViewModel> {
        error("NO VIEWMODEL PROVIDED!!")
    }

@Composable
fun MainAppHost(
    rootNavController: NavController,
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
    val onNavigateTrend = {
        rootNavController
            .navigateToTrendDestination()
    }

    CompositionLocalProvider(
        LocalIncidentViewModel provides viewModel,
    ) {
        Scaffold(
            topBar = {
                SearchBar(displayFilterState.searchQuery)
            },
            bottomBar = {
                BottomNavigation(
                    onNavigateHome = onNavigateHome,
                    onNavigateSolved = onNavigateSolved,
                    onNavigateTrend = onNavigateTrend,
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
                Fab { rootNavController.navigateToNewIncidentDestination() }
            },
        ) { innerPadding ->
            NavHost(
                navController = mainAppNavController,
                startDestination = HomeRoute,
                modifier = Modifier.padding(innerPadding),
            ) {
                homeDestination()
                solvedIncidentDestination()
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

@Composable
fun BottomNavigation(
    onNavigateHome: () -> Unit,
    onNavigateSolved: () -> Unit,
    onNavigateTrend: () -> Unit,
    currentDestination: String,
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentDestination.contains("Home"),
            onClick = {
                onNavigateHome()
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_free_breakfast_24),
                    contentDescription = null,
                )
            },
            label = {
                Text("Home")
            },
        )

        NavigationBarItem(
            selected = currentDestination.contains("SolvedIncidents"),
            onClick = { onNavigateSolved() },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_star_shine_24),
                    contentDescription = null,
                )
            },
            label = {
                Text("Solved")
            },
        )

        NavigationBarItem(
            selected = currentDestination.contains("Trend"),
            onClick = { onNavigateTrend() },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_trending_up_24),
                    contentDescription = null,
                )
            },
            label = {
                Text("Trend")
            },
        )
    }
}
