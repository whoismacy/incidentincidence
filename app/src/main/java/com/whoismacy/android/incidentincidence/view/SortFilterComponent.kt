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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoismacy.android.incidentincidence.viewmodel.FilterPeriodValues
import com.whoismacy.android.incidentincidence.viewmodel.FilterSevereValues
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel
import com.whoismacy.android.incidentincidence.viewmodel.SortFilter
import com.whoismacy.android.incidentincidence.viewmodel.SortValues

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortFilterComponent(
    onDismissRequest: () -> Unit,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val currentSortValue =
        viewModel
            .currentSortValue
            .collectAsStateWithLifecycle()
            .value
    val currentFilterSevereValue =
        viewModel
            .currentFilterSevereValue
            .collectAsStateWithLifecycle()
            .value
    val currentFilterPeriodValue =
        viewModel
            .currentFilterPeriodValue
            .collectAsStateWithLifecycle()
            .value

    val changeSortValue: (SortValues) -> Unit = { viewModel.updateCurrentSortValue(it) }
    val changeFilterSevereValue: (FilterSevereValues) -> Unit = { viewModel.updateCurrentFilterSevereValue(it) }
    val changeFilterPeriodValue: (FilterPeriodValues) -> Unit = { viewModel.updateCurrentFilterPeriodValue(it) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() },
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
    items: List<T>?,
    selectedItem: T,
    changeSelectedItem: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier.fillMaxWidth()) {
        items?.forEachIndexed { index, item ->
            SegmentedButton(
                selected = item == selectedItem,
                onClick = { changeSelectedItem(item) },
                label = {
                    Text(
                        item.value,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                shape =
                    SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = items.size,
                    ),
            )
        }
    }
}
