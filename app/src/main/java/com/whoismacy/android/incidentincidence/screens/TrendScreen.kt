package com.whoismacy.android.incidentincidence.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whoismacy.android.incidentincidence.utils.shimmer
import com.whoismacy.android.incidentincidence.viewmodel.IncidentViewModel

@Composable
fun TrendScreen(
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    val trendScreenObject =
        viewModel
            .trendsObject
            .collectAsStateWithLifecycle()
            .value

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Incident Insights",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
        )

        Row(
            modifier = Modifier.fillMaxWidth().height(180.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BentoBox(
                modifier = Modifier.weight(1.5f).fillMaxHeight(),
                title = "Total Incidents",
                value = "${trendScreenObject.totalIncidents}",
                icon = Icons.Rounded.Notifications,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            BentoBox(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                title = "Shares",
                value = "${trendScreenObject.totalShares.collectAsStateWithLifecycle(0).value}",
                icon = Icons.Rounded.Share,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(240.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BentoBox(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                title = "Resolved",
                value = "${trendScreenObject.totalResolved}",
                icon = Icons.Rounded.CheckCircle,
                containerColor = Color(0xFFE8F5E9),
                contentColor = Color(0xFF2E7D32),
            )
            Column(
                modifier = Modifier.weight(1.5f).fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                BentoBox(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    title = "Severe",
                    value = "${trendScreenObject.severityCount.severe}",
                    icon = Icons.Default.Warning,
                    contentColor = Color(0xFFFF2E2E),
                    containerColor = Color(0xFFFFC6AD),
                    horizontal = true,
                )
                BentoBox(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    title = "High",
                    value = "${trendScreenObject.severityCount.high}",
                    icon = Icons.Rounded.Star,
                    contentColor = Color(0xFFFFB234),
                    containerColor = Color(0xFFFFE0AE),
                    horizontal = true,
                )
                BentoBox(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    title = "Medium",
                    value = "${trendScreenObject.severityCount.medium}",
                    icon = Icons.Default.Warning,
                    contentColor = Color(0xFF99821F),
                    containerColor = Color(0xFFFFF0AE),
                    horizontal = true,
                )
                BentoBox(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    title = "Low",
                    value = "${trendScreenObject.severityCount.low}",
                    icon = Icons.Rounded.Star,
                    contentColor = Color(0xFFADD633),
                    containerColor = Color(0xFFDEEFAD),
                    horizontal = true,
                )
            }
        }
    }
}

@Suppress("kotlin:S107")
@Composable
fun BentoBox(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    horizontal: Boolean = false,
    viewModel: IncidentViewModel = hiltViewModel(),
) {
    Card(
        modifier =
            modifier.shimmer(
                12.dp,
                isLoading =
                    viewModel
                        .isLoading
                        .collectAsStateWithLifecycle()
                        .value,
            ),
        shape = RoundedCornerShape(24.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        if (horizontal) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.alpha(0.8f),
                )
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp).alpha(0.8f),
                )
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 36.sp,
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.alpha(0.7f),
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TrendScreenPreview() {
    TrendScreen()
}
