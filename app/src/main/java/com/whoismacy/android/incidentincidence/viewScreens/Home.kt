package com.whoismacy.android.incidentincidence.viewScreens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val crimes =
        viewModel
            .unSolvedCrimes
            .collectAsStateWithLifecycle(emptyList())

    DisplayList(
        crimes.value,
        "No Incidents found.\n Create new Incidents to see them here",
        modifier,
    )
}
