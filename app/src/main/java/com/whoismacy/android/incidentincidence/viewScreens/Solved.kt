package com.whoismacy.android.incidentincidence.viewScreens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@Composable
fun Solved(
    modifier: Modifier = Modifier,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val solvedCrimes =
        viewModel
            .solvedCrimes
            .collectAsStateWithLifecycle(emptyList())

    DisplayList(
        solvedCrimes.value,
        "Uh-oh!!\n" +
            "No solved incidents",
        modifier,
    )
}
