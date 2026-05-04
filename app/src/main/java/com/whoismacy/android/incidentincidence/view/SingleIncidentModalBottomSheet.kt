package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.utils.dateToHumanReadable
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleIncidentMbs(
    incident: Incident,
    onDismissRequest: () -> Unit,
    rootNavController: NavController,
    modifier: Modifier = Modifier,
) {
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val viewModel = LocalIncidentViewModel.current

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier =
            modifier
                .fillMaxWidth(),
        sheetState = sheetState,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            Text(
                "Incident #${incident.id}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
            )
            TextDialog("Details", incident.content)
            TextDialog(
                "Created at",
                dateToHumanReadable(incident.dateAdded),
            )
            TextDialog(
                "Shares",
                if (incident.shared == 0) "None" else incident.shared.toString(),
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Status: ",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = if (!incident.resolved) "UNRESOLVED" else "RESOLVED",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (!incident.resolved) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                )
            }
            Spacer(Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(Modifier.height(16.dp))
            ButtonGroup(
                incident = incident,
                viewModel = viewModel,
                showDeleteDialog = showDeleteDialog,
                controlDeleteDialog = { showDeleteDialog = it },
                rootNavController = rootNavController,
            )
        }
    }
}

@Composable
fun TextDialog(
    label: String,
    content: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun ButtonGroup(
    incident: Incident,
    viewModel: IncidentViewModel,
    showDeleteDialog: Boolean,
    controlDeleteDialog: (Boolean) -> Unit,
    rootNavController: NavController,
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            FilledTonalButton(
                onClick = { controlDeleteDialog(true) },
                colors =
                    ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    ),
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(R.drawable.outline_delete_24),
                    contentDescription = "Delete",
                )
            }
            Text("Delete", style = MaterialTheme.typography.labelMedium)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            FilledTonalButton(
                onClick = {
                    shareIncident(context, incident)
                    viewModel.incrementShare(incident.id)
                },
                colors =
                    ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(R.drawable.outline_share_24),
                    contentDescription = "Share incident",
                )
            }
            Text("Share", style = MaterialTheme.typography.labelMedium)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            FilledTonalButton(
                onClick = {
                    rootNavController.navigate(CaptureImageRoute) {
                        popUpTo(IncidentIncidenceRoute) {
                            saveState = true
                        }
                    }
                },
                colors =
                    ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.Create,
                    contentDescription = "Edit incident",
                )
            }
            Text("Edit", style = MaterialTheme.typography.labelMedium)
        }

        if (!incident.resolved) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Button(
                    onClick = { viewModel.resolveIncident(incident.id) },
                ) {
                    Text("Resolve")
                }
                Text("Mark fixed", style = MaterialTheme.typography.labelMedium)
            }
        }
    }

    if (showDeleteDialog) {
        DeleteDialog(
            incident = incident,
            onDismissRequest = { controlDeleteDialog(false) },
            viewModel = viewModel,
        )
    }
}

@Composable
fun DeleteDialog(
    incident: Incident,
    onDismissRequest: () -> Unit,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    viewModel.deleteIncident(incident.id)
                    onDismissRequest()
                },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    ),
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text("Cancel")
            }
        },
        title = {
            Text(
                "Delete Incident",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Text(
                "Are you sure you want to delete incident #${incident.id}? This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium,
            )
        },
    )
}
