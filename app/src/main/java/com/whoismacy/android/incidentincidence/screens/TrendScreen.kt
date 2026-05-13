package com.whoismacy.android.incidentincidence.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.routes.LocalIncidentViewModel
import com.whoismacy.android.incidentincidence.ui.theme.IncidentIncidenceTheme
import com.whoismacy.android.incidentincidence.utils.shimmer

@Composable
fun TrendScreen() {
    val viewModel = LocalIncidentViewModel.current
    val trendScreenObject by viewModel.trendsObject.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = "Incident Insights",
                    style =
                        MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-0.5).sp,
                        ),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "Analytics and report overview",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                )
            }
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_bar_chart_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = "OVERVIEW",
                style =
                    MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                    ),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            )

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BentoBox(
                    modifier =
                        Modifier
                            .weight(1.3f)
                            .fillMaxHeight(),
                    title = "Total Active",
                    value = "${trendScreenObject.totalIncidents}",
                    icon = Icons.Rounded.Notifications,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    isLoading = isLoading,
                )
                Spacer(modifier = Modifier.width(16.dp))
                BentoBox(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    title = "Shares",
                    value = "${trendScreenObject.totalShares.collectAsStateWithLifecycle(0).value}",
                    icon = Icons.Rounded.Share,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    isLoading = isLoading,
                )
            }
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(280.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            BentoBox(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                title = "Resolved",
                value = "${trendScreenObject.totalResolved}",
                icon = Icons.Rounded.CheckCircle,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                isLoading = isLoading,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier =
                    Modifier
                        .weight(1.3f)
                        .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                SeverityItem(
                    title = "Severe",
                    value = "${trendScreenObject.severityCount.severe}",
                    icon = Icons.Default.Warning,
                    color = Color(0xFFE53935),
                    isLoading = isLoading,
                )
                SeverityItem(
                    title = "High",
                    value = "${trendScreenObject.severityCount.high}",
                    icon = Icons.Rounded.Star,
                    color = Color(0xFFF57C00),
                    isLoading = isLoading,
                )
                SeverityItem(
                    title = "Medium",
                    value = "${trendScreenObject.severityCount.medium}",
                    icon = Icons.Default.Warning,
                    color = Color(0xFFFBC02D),
                    isLoading = isLoading,
                )
                SeverityItem(
                    title = "Low",
                    value = "${trendScreenObject.severityCount.low}",
                    icon = Icons.Rounded.Star,
                    color = Color(0xFF7CB342),
                    isLoading = isLoading,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun BentoBox(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    isLoading: Boolean,
) {
    Card(
        modifier = modifier.shimmer(16.dp, isLoading = isLoading),
        shape = RoundedCornerShape(28.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(12.dp),
                color = contentColor.copy(alpha = 0.15f),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = contentColor,
                    )
                }
            }

            Column {
                Text(
                    text = value,
                    style =
                        MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-1).sp,
                        ),
                )
                Text(
                    text = title,
                    style =
                        MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    modifier =
                        Modifier
                            .padding(top = 4.dp)
                            .graphicsLayer(alpha = 0.8f),
                )
            }
        }
    }
}

@Composable
fun SeverityItem(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    isLoading: Boolean,
) {
    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .shimmer(12.dp, isLoading = isLoading),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.1f)),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = color,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TrendScreenPreview() {
    IncidentIncidenceTheme {
        TrendScreen()
    }
}
