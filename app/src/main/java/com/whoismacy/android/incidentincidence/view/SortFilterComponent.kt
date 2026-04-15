package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val sortValues =
    listOf(
        "Newest first",
        "Oldest first",
        "Shuffle",
    )

private val filterSevereValues =
    listOf(
        "Low",
        "Medium",
        "High",
        "Severe",
    )

private val filterPeriodValues =
    listOf(
        "Today",
        "Past week",
        "Past month",
        "Past year",
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortFilterComponent() {
    var sortValues by remember { mutableStateOf(null) }
    var filterSevereValues by remember { mutableStateOf(null) }
    var filterPeriodValues by remember { mutableStateOf(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {},
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 24.dp,
                        horizontal = 16.dp,
                    ),
        ) {
            Text(
                "Sort and Filter Incidences",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Sort",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Button(onClick = {}) {
                    Text("Shuffle")
                }
                Button(onClick = {}) {
                    Text("Newest First")
                }
                Button(onClick = {}) {
                    Text("Oldest First")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Filter",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("by Severity", style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Button(onClick = {}) {
                    Text("Low")
                }
                Button(onClick = {}) {
                    Text("Medium")
                }
                Button(onClick = {}) {
                    Text("High")
                }
                Button(onClick = {}) {
                    Text("Severe")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("by Period", style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = {}) {
                    Text("Today")
                }
                Button(onClick = {}) {
                    Text("Past Week")
                }
                Button(onClick = {}) {
                    Text("Past Month")
                }
                Button(onClick = {}) {
                    Text("Past Year")
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Apply")
            }
        }
    }
}

@Composable
fun ConnectedButtonGroup(
    textList: List<String>,
    currentIndex: Int,
    changecurrentIndex: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
    ) { }
}

@Preview(showBackground = true)
@Composable
fun SortFilterPreview() {
    SortFilterComponent()
}
