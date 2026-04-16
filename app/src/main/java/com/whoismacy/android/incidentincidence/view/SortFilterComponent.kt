package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

interface SortFilter {
    val value: String
}

enum class SortValues(
    override val value: String,
) : SortFilter {
    NEWEST("Newest first"),
    OLDEST("Oldest first"),
    SHUFFLE("Shuffle"),
}

enum class FilterSevereValues(
    override val value: String,
) : SortFilter {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    SEVERE("Severe"),
}

enum class FilterPeriodValues(
    override val value: String,
) : SortFilter {
    TODAY("Today"),
    PWEEK("Past Week"),
    PMONTH("Past Month"),
    PYEAR("Past Year"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortFilterComponent() {
    var currentSortValue by remember { mutableStateOf(SortValues.NEWEST) }
    var currentFilterSevereValue by remember { mutableStateOf(FilterSevereValues.LOW) }
    var currentFilterPeriodValue by remember { mutableStateOf(FilterPeriodValues.TODAY) }

    val changeSortValue: (SortValues) -> Unit = { value -> currentSortValue = value }
    val changeFilterSevereValue: (FilterSevereValues) -> Unit = { value -> currentFilterSevereValue = value }
    val changeFilterPeriodValue: (FilterPeriodValues) -> Unit = { value -> currentFilterPeriodValue = value }

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
            SortFilterButtonGroup(
                items = SortValues.entries.toList(),
                selectedItem = currentSortValue,
                changeSelectedItem = changeSortValue,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Filter",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("by Severity", style = MaterialTheme.typography.bodyMedium)
            SortFilterButtonGroup(
                items = FilterSevereValues.entries.toList(),
                selectedItem = currentFilterSevereValue,
                changeSelectedItem = changeFilterSevereValue,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("by Period", style = MaterialTheme.typography.bodyMedium)
            SortFilterButtonGroup(
                items = FilterPeriodValues.entries.toList(),
                selectedItem = currentFilterPeriodValue,
                changeSelectedItem = changeFilterPeriodValue,
            )
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
fun <T : SortFilter> SortFilterButtonGroup(
    items: List<T>,
    selectedItem: T,
    changeSelectedItem: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        items.forEachIndexed { index, item ->
            SegmentedButton(
                selected = item == selectedItem,
                onClick = { changeSelectedItem(item) },
                label = { Text(item.value) },
                shape =
                    SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = items.size,
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SortFilterPreview() {
    SortFilterComponent()
}
