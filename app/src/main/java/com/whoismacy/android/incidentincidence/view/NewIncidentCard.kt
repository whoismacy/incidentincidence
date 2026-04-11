package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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

@Composable
fun NewIncidentCard(
    onDismissRequest: () -> Unit,
    changeDisplayOutlinedCard: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IncidentViewModel = hiltViewModel<IncidentViewModel>(),
) {
    var incidentText by rememberSaveable { mutableStateOf("") }
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        OutlinedCard(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth().padding(24.dp),
            ) {
                Text(
                    "Create new Incident",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
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
                    singleLine = false,
                    maxLines = 4,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ElevatedButton(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            changeDisplayOutlinedCard(false)
                        },
                        colors =
                            ButtonDefaults.elevatedButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary,
                            ),
                    ) {
                        Text("Cancel")
                    }
                    ElevatedButton(
                        enabled = incidentText.isNotEmpty(),
                        onClick = {
                            if (incidentText.isNotEmpty()) {
                                viewModel.add(incidentText)
                                onDismissRequest()
                            }
                        },
                        colors =
                            ButtonDefaults.elevatedButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}
