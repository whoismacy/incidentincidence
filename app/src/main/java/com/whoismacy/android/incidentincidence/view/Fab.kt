package com.whoismacy.android.incidentincidence.view

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.routes.navigateToNewIncidentDestination

@Composable
fun Fab(navController: NavController) {
    ExtendedFloatingActionButton(
        onClick = {
            navController
                .navigateToNewIncidentDestination()
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
