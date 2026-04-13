package com.whoismacy.android.incidentincidence.viewScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.view.EmptyStateIndicator
import com.whoismacy.android.incidentincidence.view.IncidentItem

@Composable
fun DisplayList(
    listContent: List<Incident>,
    stateText: String,
    modifier: Modifier = Modifier,
) {
    var selectedIncidentId by remember { mutableStateOf<Int?>(null) }
    val changeDisplayVisibility: (Int?) -> Unit = { id: Int? -> selectedIncidentId = id }

    if (listContent.isEmpty()) {
        EmptyStateIndicator(stateText)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(listContent) {
                IncidentItem(
                    isSelected = selectedIncidentId == it.id,
                    changeDisplayVisibility = changeDisplayVisibility,
                    incident = it,
                )
            }
        }
    }
}
