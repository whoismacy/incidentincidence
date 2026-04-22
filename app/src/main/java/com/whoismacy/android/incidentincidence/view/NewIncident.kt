package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

enum class SeverityOptions {
    LOW,
    MEDIUM,
    HIGH,
    SEVERE,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewIncident(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: IncidentViewModel = hiltViewModel<IncidentViewModel>(),
) {
    var incidentText by rememberSaveable { mutableStateOf("") }
    var checkBoxState by rememberSaveable { mutableStateOf(SeverityOptions.LOW) }
    val changeCheckBoxState: (SeverityOptions) -> Unit = { option -> checkBoxState = option }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(64.dp),
    ) {
        Text(
            "Create new Incident",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(vertical = 8.dp),
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
        HorizontalDivider()
        Spacer(modifier = Modifier.height(24.dp))
        SeverityOptions(checkBoxState, changeCheckBoxState)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = {
                    navController.popBackStack()
                },
            ) {
                Text("Cancel")
            }
            Button(
                enabled = incidentText.isNotBlank(),
                onClick = {
                    if (incidentText.isNotBlank()) {
                        viewModel.add(incidentText, checkBoxState.name.lowercase())
                        navController.popBackStack()
                    }
                },
            ) {
                Text("Create")
            }
        }
    }
}

@Composable
fun SeverityOptions(
    selectedOption: SeverityOptions,
    onChangeSelection: (SeverityOptions) -> Unit,
) {
    Column {
        Text(
            "Severity Options",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
        )
        SeverityOptions.entries.forEach { option ->
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .selectable(
                            selected = option == selectedOption,
                            onClick = { onChangeSelection(option) },
                            role = Role.Checkbox,
                        ).padding(vertical = 8.dp, horizontal = 16.dp),
            ) {
                Checkbox(
                    checked = option == selectedOption,
                    onCheckedChange = null,
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text =
                        option
                            .name
                            .lowercase()
                            .replaceFirstChar { it.uppercaseChar() },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
