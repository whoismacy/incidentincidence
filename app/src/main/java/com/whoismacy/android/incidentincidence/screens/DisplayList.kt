package com.whoismacy.android.incidentincidence.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.utils.shimmer
import com.whoismacy.android.incidentincidence.view.EmptyStateIndicator
import com.whoismacy.android.incidentincidence.view.IncidentItem
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@Composable
fun DisplayList(
    listContent: List<Incident>,
    modifier: Modifier = Modifier,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    var selectedIncidentId by remember { mutableStateOf<Int?>(null) }
    val changeDisplayVisibility: (Int?) -> Unit = { id: Int? -> selectedIncidentId = id }
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    if (isLoading) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(5) {
                ElevatedCard(
                    shape = MaterialTheme.shapes.medium,
                    modifier =
                        modifier
                            .shimmer(12.dp, isLoading)
                            .height(144.dp)
                            .fillMaxWidth(0.9f),
                ) {}
            }
        }
    } else if (listContent.isEmpty()) {
        EmptyStateIndicator("No Incidents found!!")
    }

    AnimatedVisibility(
        visible = !isLoading,
        enter =
            fadeIn(
                animationSpec =
                    tween(500, easing = LinearOutSlowInEasing),
            ) + expandVertically(animationSpec = tween(500, easing = LinearOutSlowInEasing)),
        exit =
            fadeOut(
                animationSpec =
                    tween(500, easing = LinearOutSlowInEasing),
            ) + shrinkVertically(animationSpec = tween(500, easing = LinearOutSlowInEasing)),
    ) {
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
                    modifier =
                        Modifier.shimmer(
                            12.dp,
                            isLoading = isLoading,
                        ),
                )
            }
        }
    }
}
