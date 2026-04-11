package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.utils.dateToHumanReadable
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleIncidentMbs(
    incident: Incident,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        sheetState = sheetState,
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.surface,
        scrimColor = MaterialTheme.colorScheme.scrim.copy(0.6f),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
        ) {
            Card(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                ) {
                    Text(
                        "Incident ID: #${incident.id}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                    TextDialog("Incident", incident.content)
                    TextDialog(
                        "Created at",
                        dateToHumanReadable(incident.dateAdded),
                    )
                    TextDialog(
                        "Share Count",
                        if (incident.shared == 0) "You haven't shared this Incident" else incident.shared.toString(),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Status: ",
                            style =
                                TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                        )
                        if (!incident.resolved) {
                            Text(
                                "UNRESOLVED",
                                style =
                                    TextStyle(
                                        color = Color.Red,
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.SemiBold,
                                    ),
                            )
                        } else {
                            Text(
                                "RESOLVED",
                                style =
                                    TextStyle(
                                        color = Color.Green,
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.SemiBold,
                                    ),
                            )
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                    ButtonGroup(
                        incident,
                        viewModel,
                        showDeleteDialog = showDeleteDialog,
                    ) { showDeleteDialog = it }
                }
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
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
    ) {
        Text(
            "$label: ",
            style =
                TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
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
fun ButtonGroup(
    incident: Incident,
    viewModel: IncidentViewModel,
    showDeleteDialog: Boolean,
    controlDeleteDialog: (Boolean) -> Unit,
) {
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
                onClick = {
                    controlDeleteDialog(true)
                },
                colors =
                    ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    ),
                modifier = Modifier.height(48.dp),
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
                onClick = {},
                colors =
                    ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    ),
                modifier = Modifier.height(48.dp),
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(R.drawable.outline_share_24),
                    contentDescription = "Share incident",
                )
            }
            Text("Share", style = MaterialTheme.typography.labelMedium)
        }

        Button(
            onClick = {
                viewModel.resolveIncident(incident.id)
            },
            modifier = Modifier.padding(12.dp),
        ) {
            Text("Resolve #${incident.id}")
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
                },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    ),
            ) {
                Text("Delete #${incident.id}")
            }
        },
        dismissButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("Cancel")
            }
        },
        title = {
            Text("Deletion of Incident #${incident.id}", style = MaterialTheme.typography.headlineLarge)
        },
        text = {
            Text("Are you sure you want to delete this incident?", style = MaterialTheme.typography.labelMedium)
        },
    )
}
