package com.whoismacy.android.incidentincidence.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.viewScreens.HomeScreen
import com.whoismacy.android.incidentincidence.viewScreens.SolvedIncidentsScreen
import com.whoismacy.android.incidentincidence.viewScreens.TrendScreen
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object SolvedIncidentsRoute

@Serializable
object TrendRoute

@Composable
fun IncidentIncidenceScreen(
    rootNavController: NavController,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val displayData by viewModel
        .displayIncidences
        .collectAsStateWithLifecycle(emptyList())

    val displayFilterState by viewModel
        .displayFilterState
        .collectAsStateWithLifecycle()

    val LocalNavigation =
        staticCompositionLocalOf<NavController> {
            error("No NavControllerProvided")
        }

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val navController = rememberNavController()
    val navBackStack by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStack?.destination?.route ?: ""
    val visible = !currentDestination.contains("TrendRoute")

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(durationMillis = 800, easing = LinearOutSlowInEasing)) + expandVertically(tween(durationMillis = 800, easing = LinearOutSlowInEasing)),
                exit = fadeOut(tween(durationMillis = 800, easing = LinearOutSlowInEasing)) + shrinkVertically(tween(durationMillis = 800, easing = LinearOutSlowInEasing)),
            ) {
                SearchBar(displayFilterState.searchQuery)
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination.contains("Home"),
                    onClick = {
                        navController.navigate(HomeRoute) {
                            popUpTo(HomeRoute) {
                                saveState = true
                            }
                        }
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
                    onClick = {
                        navController.navigate(SolvedIncidentsRoute) {
                            popUpTo(HomeRoute) {
                                saveState = true
                            }
                        }
                    },
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
                    onClick = {
                        navController.navigate(TrendRoute) {
                            popUpTo(HomeRoute) {
                                saveState = true
                            }
                        }
                    },
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
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(durationMillis = 800, easing = LinearOutSlowInEasing)) + expandVertically(tween(durationMillis = 800, easing = LinearOutSlowInEasing)),
                exit = fadeOut(tween(durationMillis = 800, easing = LinearOutSlowInEasing)) + shrinkVertically(tween(durationMillis = 800, easing = LinearOutSlowInEasing)),
            ) {
                Fab(rootNavController)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<HomeRoute> {
                HomeScreen(incidences = displayData, navController = rootNavController)
            }

            composable<SolvedIncidentsRoute> {
                SolvedIncidentsScreen(incidences = displayData, navController = rootNavController)
            }

            composable<TrendRoute> {
                TrendScreen()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvents.collect { event ->
            coroutineScope.launch {
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
}
