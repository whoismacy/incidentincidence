package com.whoismacy.android.incidentincidence.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@Composable
fun SolvedIncidentsScreen(
    modifier: Modifier = Modifier,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val incidences =
        viewModel
            .displayIncidences
            .collectAsStateWithLifecycle()
            .value
    DisplayList(
        listContent =
            incidences
                .filter { incident -> incident.resolved },
        modifier,
    )
}
