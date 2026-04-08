package com.whoismacy.android.incidentincidence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whoismacy.android.incidentincidence.ui.theme.IncidentIncidenceTheme
import com.whoismacy.android.incidentincidence.viewScreens.Home
import com.whoismacy.android.incidentincidence.viewScreens.Solved

enum class Destinations(
    val icon: Int,
    val label: String,
    val contentDescription: String,
) {
    HOME(R.drawable.outline_free_breakfast_24, "Home", "An outline of a cup of tea"),
    SOLVED(R.drawable.outline_star_shine_24, "Solved Crimes", "An outline of a shining star"),
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IncidentIncidenceTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {},
                            modifier = Modifier.padding(bottom = 96.dp),
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_add_24),
                                contentDescription = "Add",
                            )
                        }
                    },
                ) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var destinations by rememberSaveable { mutableStateOf(Destinations.HOME) }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            Destinations.entries.forEach {
                item(
                    selected = it == destinations,
                    onClick = { destinations = it },
                    icon = {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = it.contentDescription,
                        )
                    },
                    label = { Text(it.label) },
                )
            }
        },
        content = {
            when (destinations) {
                Destinations.HOME -> Home(modifier)
                Destinations.SOLVED -> Solved(modifier)
            }
        },
    )
}
