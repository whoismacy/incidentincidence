package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whoismacy.android.incidentincidence.R

@Composable
fun Fab() {
    var displayOutlinedCard by rememberSaveable { mutableStateOf(false) }
    FloatingActionButton(
        onClick = {
            displayOutlinedCard = !displayOutlinedCard
        },
        modifier = Modifier.padding(bottom = 96.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_add_24),
            contentDescription = "Add",
        )
    }

    if (displayOutlinedCard) {
        NewIncidentCard(onDismissRequest = { displayOutlinedCard = false }, { newState: Boolean ->
            displayOutlinedCard = newState
        })
    }
}
