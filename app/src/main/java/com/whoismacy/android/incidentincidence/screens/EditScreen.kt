package com.whoismacy.android.incidentincidence.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@Composable
fun EditScreen(
    id: Int,
    incidents: List<Incident>,
    onNavigateCaptureImage: () -> Unit,
    onNavigateHome: () -> Unit,
    viewModel: IncidentViewModel,
) {
    viewModel.updateEditIncidentId(id)
    val incident = incidents.first { it.id == id }
    var contentValue by remember { mutableStateOf(if (id != -1) incident.content else "") }
    val changeContentValue: (String) -> Unit = { contentValue = it }

    Surface(
        color = MaterialTheme.colorScheme.background,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.background),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    "Incident #${incident.id}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(12.dp))

                TextFieldEdit(
                    content = contentValue,
                    changeContentValue = changeContentValue,
                    incidentId = id,
                )

                TextButton(
                    onClick = {
                        if (incident.imageUri == "NULL") {
                            onNavigateCaptureImage()
                        } else {
                            viewModel.updateImageUriNull(incident.id)
                        }
                    },
                    border = BorderStroke((0.8).dp, MaterialTheme.colorScheme.secondary),
                ) {
                    Text(if (incident.imageUri == "NULL") "Add Image" else "Delete Image")
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    enabled = contentValue != incident.content,
                    onClick = {
                        if (contentValue != incident.content) {
                            viewModel.updateIncidentContent(id = id, content = contentValue)
                            onNavigateHome()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Save changes")
                }
            }
        }
    }
}

@Composable
fun TextFieldEdit(
    content: String,
    incidentId: Int,
    changeContentValue: (String) -> Unit,
) {
    TextField(
        value = content,
        onValueChange = { changeContentValue(it) },
        label = {
            Text("Edit Incident #$incidentId")
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrectEnabled = true,
            ),
        singleLine = true,
        maxLines = 10,
        modifier = Modifier.fillMaxWidth(),
    )
}
