package com.whoismacy.android.incidentincidence.view

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whoismacy.android.incidentincidence.viewScreens.HomeScreen
import com.whoismacy.android.incidentincidence.viewScreens.SolvedIncidentsScreen
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object SolvedIncidents

@Composable
fun MainScreen(
    viewModel: IncidentViewModel = hiltViewModel(),
) {
//    var destinations by rememberSaveable { mutableStateOf(Destinations.HOME) }
    val displayData by viewModel
        .displayIncidences
        .collectAsStateWithLifecycle(emptyList())

    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Home,
    ) {
        composable<Home> {
            HomeScreen(incidences = displayData)
        }

        composable<SolvedIncidents> {
            SolvedIncidentsScreen(incidences = displayData)
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

//    NavigationSuiteScaffold(
//        navigationSuiteItems = {
//            Destinations.entries.forEach {
//                item(
//                    selected = it == destinations,
//                    onClick = { destinations = it },
//                    icon = {
//                        Icon(
//                            painter = painterResource(it.icon),
//                            contentDescription = it.contentDescription,
//                        )
//                    },
//                    label = { Text(it.label) },
//                )
//            }
//        },
//        content = {
//            Scaffold(
//                topBar = {
//                    SearchBar(displayFilterState.searchQuery)
//                },
//                modifier = Modifier.fillMaxSize().padding(top = 32.dp),
//                floatingActionButton = { Fab() },
//                snackbarHost = {
//                    SnackbarHost(snackBarHostState) { snackbarData ->
//                        Snackbar(
//                            modifier =
//                                Modifier
//                                    .padding(16.dp)
//                                    .clip(RoundedCornerShape(12.dp)),
//                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
//                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                            actionColor = MaterialTheme.colorScheme.primary,
//                            actionContentColor = MaterialTheme.colorScheme.onPrimary,
//                            shape = RoundedCornerShape(12.dp),
//                            snackbarData = snackbarData,
//                        )
//                    }
//                },
//            ) { innerPadding ->
//                when (destinations) {
//                    Destinations.HOME -> {
//                        Home(
//                            incidences = displayData,
//                            modifier = Modifier.padding(innerPadding),
//                        )
//                    }
//
//                    Destinations.SOLVED -> {
//                        Solved(
//                            incidences = displayData,
//                            modifier = Modifier.padding(innerPadding),
//                        )
//                    }
//                }
//            }
//        },
//    )
}
