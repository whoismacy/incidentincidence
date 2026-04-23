package com.whoismacy.android.incidentincidence.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.unveilIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@Composable
fun SearchBar(
    state: String,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    var showSortFilterModal by remember { mutableStateOf(false) }
    val changeSortFilterState: (Boolean) -> Unit = { showSortFilterModal = it }
    val showFilter =
        viewModel
            .displayFilterState
            .collectAsStateWithLifecycle()
            .value.filtersEnabled

    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 48.dp, bottom = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                value = state,
                onValueChange = { viewModel.updateSearchQuery(it) },
                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeholder = {
                    Text("Search")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_search_24),
                        contentDescription = null,
                    )
                },
                colors =
                    TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (!showFilter) {
                Icon(
                    painter = painterResource(R.drawable.sharp_filter_alt_24),
                    contentDescription = "Filter Icon",
                    modifier =
                        Modifier
                            .size(32.dp)
                            .clickable(
                                onClickLabel = "Filter",
                                onClick = {
                                    changeSortFilterState(true)
                                },
                            ),
                )
            } else {
                TextButton(onClick = {
                    viewModel.toggleFilters()
                }) {
                    Text(
                        "Clear Filters",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }
        }
    }

    if (showSortFilterModal) {
        SortFilterComponent({ changeSortFilterState(false) })
    }
}
