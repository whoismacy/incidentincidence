package com.whoismacy.android.incidentincidence.view

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.whoismacy.android.incidentincidence.R

@Composable
fun BottomNavigation(
    onNavigateHome: () -> Unit,
    onNavigateSolved: () -> Unit,
    onNavigateTrend: () -> Unit,
    currentDestination: String,
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentDestination.contains("Home"),
            onClick = {
                onNavigateHome()
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_free_breakfast_24),
                    contentDescription = null,
                )
            },
            label = {
                Text("Home")
            },
        )

        NavigationBarItem(
            selected = currentDestination.contains("SolvedIncidents"),
            onClick = { onNavigateSolved() },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_star_shine_24),
                    contentDescription = null,
                )
            },
            label = {
                Text("Solved")
            },
        )

        NavigationBarItem(
            selected = currentDestination.contains("Trend"),
            onClick = { onNavigateTrend() },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_trending_up_24),
                    contentDescription = null,
                )
            },
            label = {
                Text("Trend")
            },
        )
    }
}
