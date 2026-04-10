package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@Composable
fun SingleIncidentDialog(
    incident: Incident,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        ElevatedCard(modifier = modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
            ) {
                Text("Incident ID: #${incident.id}")
                TextDialog("Incident", incident.content)
                TextDialog("Created at", incident.content)
                TextDialog("Share Count", incident.shared.toString())
                TextDialog("Resolved on", if (incident.resolved) "resolved" else "unresolved")
                Spacer(Modifier.height(16.dp))
                DialogButtonGroup(incident, viewModel)
            }
        }
    }
}

@Composable
fun TextDialog(
    label: String,
    content: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            "$label: ",
            style =
                TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                ),
        )

        Text(
            content,
            style =
                MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun DialogButtonGroup(
    incident: Incident,
    viewModel: IncidentViewModel,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FilledTonalButton(onClick = {
            viewModel.deleteIncident(incident.id)
        }) {
            Icon(
                painter = painterResource(R.drawable.outline_delete_24),
                contentDescription = null,
            )
        }
        FilledTonalButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.outline_share_24),
                contentDescription = null,
            )
        }
        ElevatedButton(
            onClick = {
                viewModel.resolveIncident(incident.id)
            },
        ) {
            Text("Resolve $incident.id")
        }
    }
}
