package com.whoismacy.android.incidentincidence.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.routes.LocalIncidentViewModel

@Composable
fun EditScreen(
    id: Int,
    onNavigateCaptureImage: () -> Unit,
    onNavigateHome: () -> Unit,
) {
    val viewModel = LocalIncidentViewModel.current
    val incidents = viewModel.displayIncidences.collectAsStateWithLifecycle().value
    val incident = incidents.first { it.id == id }
    var contentValue by remember { mutableStateOf(incident.content) }
    val changeContentValue: (String) -> Unit = { contentValue = it }

    Surface(
        color = MaterialTheme.colorScheme.background,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.background),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ImageCompose(incident.imageUri)
                Text(
                    "Incident #${incident.id}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(12.dp))

                TextFieldEdit(
                    content = contentValue,
                    changeContentValue = changeContentValue,
                    incidentId = id,
                )

                TextButton(
                    onClick = {
                        if (incident.imageUri == "NULL") {
                            viewModel.updateEditIncidentId(id)
                            onNavigateCaptureImage()
                        } else {
                            viewModel.updateImageUriNull(incident.id)
                        }
                    },
                    border = BorderStroke((0.8).dp, MaterialTheme.colorScheme.secondary),
                ) {
                    Text(if (incident.imageUri == "NULL") "Add Image" else "Delete Image")
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    enabled = contentValue != incident.content,
                    onClick = {
                        if (contentValue != incident.content && contentValue.isNotBlank()) {
                            viewModel.updateIncidentContent(id = id, content = contentValue)
                            onNavigateHome()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Save changes")
                }
            }
        }
    }
}

@Composable
fun TextFieldEdit(
    content: String,
    incidentId: Int,
    changeContentValue: (String) -> Unit,
) {
    TextField(
        value = content,
        onValueChange = { changeContentValue(it) },
        label = {
            Text("Edit Incident #$incidentId")
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrectEnabled = true,
            ),
        singleLine = true,
        maxLines = 10,
        modifier = Modifier.fillMaxWidth(),
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageCompose(
    uri: String,
) {
    if (uri != "NULL") {
        GlideImage(
            model = uri.toUri(),
            contentDescription = "Image of a captured Incident",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
            loading = placeholder(R.drawable.outline_cancel_24),
            failure = placeholder(R.drawable.outline_cancel_24),
        )
    }
}
