package com.whoismacy.android.incidentincidence.view

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.whoismacy.android.incidentincidence.R

@Composable
fun Fab() {
    var displayOutlinedCard by rememberSaveable { mutableStateOf(false) }
    ExtendedFloatingActionButton(
        onClick = {
            displayOutlinedCard = !displayOutlinedCard
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

    if (displayOutlinedCard) {
        NewIncidentCard(
            onDismissRequest =
                { displayOutlinedCard = false },
            { newState: Boolean ->
                displayOutlinedCard = newState
            },
        )
    }
}
