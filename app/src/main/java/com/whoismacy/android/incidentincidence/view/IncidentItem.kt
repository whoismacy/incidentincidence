package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.utils.dateToHumanReadable

@Composable
fun IncidentItem(
    navController: NavController,
    isSelected: Boolean,
    changeDisplayVisibility: (Int?) -> Unit,
    incident: Incident,
    modifier: Modifier = Modifier,
) {
    if (isSelected) {
        SingleIncidentMbs(
            incident,
            { changeDisplayVisibility(null) },
            navController,
        )
    }

    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        modifier =
            modifier
                .fillMaxWidth(0.9f)
                .clickable(onClick = {
                    changeDisplayVisibility(incident.id)
                }),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Text(
                text = "Incident #${incident.id}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = incident.content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Severity: ${incident.severity.uppercase()}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier.weight(1f))

                Text(
                    text = dateToHumanReadable(incident.dateAdded),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
