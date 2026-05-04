package com.whoismacy.android.incidentincidence.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.whoismacy.android.incidentincidence.model.Incident

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    incidences: List<Incident> = emptyList(),
) {
    DisplayList(
        navController,
        incidences.filter { incident -> !incident.resolved },
        modifier,
    )
}
