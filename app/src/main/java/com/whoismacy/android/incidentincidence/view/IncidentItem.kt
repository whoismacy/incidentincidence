package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.utils.dateToHumanReadable

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IncidentItem(
    isSelected: Boolean,
    changeDisplayVisibility: (Int?) -> Unit,
    incident: Incident,
    modifier: Modifier = Modifier,
) {
    if (isSelected) {
        IncidentMbs(
            incident,
            { changeDisplayVisibility(null) },
        )
    }

    val severityColor =
        when (incident.severity.lowercase()) {
            "severe" -> Color(0xFFE53935)
            "high" -> Color(0xFFF57C00)
            "medium" -> Color(0xFFFBC02D)
            "low" -> Color(0xFF7CB342)
            else -> MaterialTheme.colorScheme.outline
        }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable { changeDisplayVisibility(incident.id) },
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border =
            androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
            ),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(16.dp)
                        .weight(1f),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "INCIDENT #${incident.id}",
                        style =
                            MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                            ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = dateToHumanReadable(incident.dateAdded),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = incident.content,
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            lineHeight = 22.sp,
                        ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // Severity Badge
                    Surface(
                        color = severityColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = incident.severity.uppercase(),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = severityColor,
                        )
                    }

                    if (incident.resolved) {
                        Surface(
                            color = Color(0xFF4CAF50).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "RESOLVED",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF2E7D32),
                            )
                        }
                    }
                }
            }

            if (incident.imageUri.isNotEmpty()) {
                Box(
                    modifier =
                        Modifier
                            .padding(end = 16.dp, top = 16.dp, bottom = 16.dp)
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    GlideImage(
                        model = incident.imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
