package com.whoismacy.android.incidentincidence.viewScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.view.SingleIncidentDialog
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val crimes = viewModel.unSolvedCrimes.collectAsStateWithLifecycle(emptyList())
    var displaySingleIncidentDialog by remember { mutableStateOf(false) }
    val changeDisplayVisibility: (Boolean) -> Unit = { state: Boolean -> displaySingleIncidentDialog = state }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(crimes.value) {
            IncidentItem(
                displaySingleIncidentDialog,
                changeDisplayVisibility,
                it,
            )
        }
    }
}

@Composable
fun IncidentItem(
    displayDialog: Boolean,
    changeDisplayVisibility: (Boolean) -> Unit,
    incident: Incident,
    modifier: Modifier = Modifier,
) {
    if (displayDialog) {
        SingleIncidentDialog(
            incident,
            { changeDisplayVisibility(false) },
        )
    }
    OutlinedCard(
        shape = MaterialTheme.shapes.large,
        colors =
            CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
        modifier =
            modifier
                .fillMaxWidth(0.9f)
                .clickable(onClick = {
                    changeDisplayVisibility(true)
                }),
    ) {
        Box(
            modifier =
                Modifier
                    .padding(24.dp),
        ) {
            Text(
                incident.content,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
