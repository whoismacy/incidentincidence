package com.whoismacy.android.incidentincidence.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                TextField(
                    value = contentValue,
                    onValueChange = { changeContentValue(it) },
                    label = {
                        Text("Edit Incident #${incident.id}")
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            autoCorrectEnabled = true,
                        ),
                    singleLine = true,
                    maxLines = 10,
                    shape = MaterialTheme.shapes.large,
                    colors =
                        TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                    modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                )

                TextButton(
                    onClick = {
                        onNavigateCaptureImage()
                    },
                ) {
                    Text("Add Image")
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
                ) {
                    Text("Save changes")
                }
            }
        }
    }
}
