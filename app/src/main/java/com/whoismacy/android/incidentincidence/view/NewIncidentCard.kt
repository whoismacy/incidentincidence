package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewIncidentCard(
    onDismissRequest: () -> Unit,
    changeDisplayOutlinedCard: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IncidentViewModel = hiltViewModel<IncidentViewModel>(),
) {
    var incidentText by rememberSaveable { mutableStateOf("") }
    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() },
        modifier = modifier.fillMaxWidth(),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth().padding(24.dp),
            ) {
                Text(
                    "Create new Incident",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                OutlinedTextField(
                    value = incidentText,
                    onValueChange = { text: String -> incidentText = text },
                    keyboardOptions =
                        KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text,
                        ),
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Describe the Incident") },
                    singleLine = false,
                    minLines = 3,
                    maxLines = 5,
                    shape = MaterialTheme.shapes.medium,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            changeDisplayOutlinedCard(false)
                            onDismissRequest()
                        },
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        enabled = incidentText.isNotBlank(),
                        onClick = {
                            if (incidentText.isNotBlank()) {
                                viewModel.add(incidentText)
                                changeDisplayOutlinedCard(false)
                                onDismissRequest()
                            }
                        },
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}
