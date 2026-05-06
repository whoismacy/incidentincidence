package com.whoismacy.android.incidentincidence.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.whoismacy.android.incidentincidence.model.Incident

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    incidences: List<Incident> = emptyList(),
) {
    DisplayList(
        listContent = incidences.filter { incident -> !incident.resolved },
        modifier,
    )
}
