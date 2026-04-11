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
import com.whoismacy.android.incidentincidence.view.SingleIncidentMbs
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

    var selectedIncidentId by remember { mutableStateOf<Int?>(null) }

    val changeDisplayVisibility: (Int?) -> Unit = { id: Int? -> selectedIncidentId = id }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(crimes.value) {
            IncidentItem(
                isSelected = selectedIncidentId == it.id,
                changeDisplayVisibility = changeDisplayVisibility,
                incident = it,
            )
        }
    }
}

@Composable
fun IncidentItem(
    isSelected: Boolean,
    changeDisplayVisibility: (Int?) -> Unit,
    incident: Incident,
    modifier: Modifier = Modifier,
) {
    if (isSelected) {
        SingleIncidentMbs(
            incident,
            { changeDisplayVisibility(null) },
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
                    changeDisplayVisibility(incident.id)
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
