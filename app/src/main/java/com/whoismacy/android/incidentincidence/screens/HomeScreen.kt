package com.whoismacy.android.incidentincidence.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoismacy.android.incidentincidence.routes.LocalIncidentViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = LocalIncidentViewModel.current
    val incidences =
        viewModel
            .displayIncidences
            .collectAsStateWithLifecycle()
            .value
    DisplayList(
        listContent =
            incidences
                .filter { incident -> !incident.resolved },
        modifier,
    )
}
