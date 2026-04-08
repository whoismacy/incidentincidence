package com.whoismacy.android.incidentincidence.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NewIncidentCard(modifier: Modifier = Modifier) {
    var displayOutlinedCard by rememberSaveable { mutableStateOf(false) }
    var cardContent by rememberSaveable { mutableStateOf("") }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        OutlinedCard {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp),
            ) {
                Text("Create new Incident")
                TextField(
                    value = cardContent,
                    onValueChange = { text: String -> cardContent = text },
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    ElevatedButton(
                        onClick = {
                            displayOutlinedCard = false
                        },
                        colors = ButtonDefaults.elevatedButtonColors(),
                    ) { }
                    ElevatedButton(onClick = {}) { }
                }
            }
        }
    }
}
