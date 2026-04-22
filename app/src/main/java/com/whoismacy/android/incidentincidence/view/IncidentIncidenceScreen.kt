package com.whoismacy.android.incidentincidence.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.viewScreens.HomeScreen
import com.whoismacy.android.incidentincidence.viewScreens.SolvedIncidentsScreen
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object SolvedIncidents

@Serializable
object CreateIncident

@Composable
fun IncidentIncidenceScreen(
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val displayData by viewModel
        .displayIncidences
        .collectAsStateWithLifecycle(emptyList())

    val displayFilterState by viewModel
        .displayFilterState
        .collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var currentDestination: String by remember { mutableStateOf("Home") }
    val visible =
        remember {
            derivedStateOf {
                !currentDestination
                    .contains("CreateIncident")
            }
        }.value

    Scaffold(
        topBar = {
            Animate(visible = visible) {
                SearchBar(displayFilterState.searchQuery)
            }
        },
        bottomBar = {
            Animate(visible = visible) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentDestination.contains("Home"),
                        onClick = {
                            navController.navigate(Home) {
                                popUpTo(Home) {
                                    inclusive = true
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
                            navController.navigate(SolvedIncidents) {
                                popUpTo(SolvedIncidents) {
                                    inclusive = true
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
                }
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
            Animate(visible = visible) {
                Fab(navController)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<Home> {
                HomeScreen(incidences = displayData)
            }

            composable<SolvedIncidents> {
                SolvedIncidentsScreen(incidences = displayData)
            }

            composable<CreateIncident> {
                NewIncident(navController)
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

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentDestination = backStackEntry
                .destination.route
                ?.substringBefore("?") ?: ""
        }
    }
}

@Composable
fun Animate(
    visible: Boolean,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
    ) {
        content()
    }
}
