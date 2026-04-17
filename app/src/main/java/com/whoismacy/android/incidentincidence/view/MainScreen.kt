package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.whoismacy.android.incidentincidence.viewScreens.Home
import com.whoismacy.android.incidentincidence.viewScreens.Solved
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val displayData by viewModel.displayIncidences.collectAsStateWithLifecycle(emptyList())
    val searchBarState by viewModel.searchQuery.collectAsStateWithLifecycle()

    var destinations by rememberSaveable { mutableStateOf(Destinations.HOME) }
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            Destinations.entries.forEach {
                item(
                    selected = it == destinations,
                    onClick = { destinations = it },
                    icon = {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = it.contentDescription,
                        )
                    },
                    label = { Text(it.label) },
                )
            }
        },
        content = {
            Scaffold(
                topBar = {
                    SearchBar(searchBarState)
                },
                modifier = Modifier.fillMaxSize().padding(top = 32.dp),
                floatingActionButton = { Fab() },
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
            ) { innerPadding ->
                when (destinations) {
                    Destinations.HOME -> {
                        Home(
                            incidences = displayData,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }

                    Destinations.SOLVED -> {
                        Solved(
                            incidences = displayData,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        },
    )
}
