package com.whoismacy.android.incidentincidence.view

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.routes.CreateIncidentRoute
import com.whoismacy.android.incidentincidence.routes.IncidentIncidenceRoute

@Composable
fun Fab(rootNavController: NavController) {
    ExtendedFloatingActionButton(
        onClick = {
            rootNavController.navigate(CreateIncidentRoute) {
                popUpTo(IncidentIncidenceRoute) {
                    saveState = true
                }
            }
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "Add",
            )
        },
        text = { Text("Report") },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        expanded = true,
    )
}
