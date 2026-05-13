package com.whoismacy.android.incidentincidence.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
    val buttonEnabled = contentValue != incident.content

    Surface(
        color = MaterialTheme.colorScheme.background,
        contentColor =
            contentColorFor(backgroundColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Text(
                    "Edit Incident #$id",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        ImageCompose(incident.imageUri)
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.surface,
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            "Details",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )

                        TextFieldEdit(
                            content = contentValue,
                            changeContentValue = changeContentValue,
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    FilledTonalButton(
                        onClick = {
                            if (incident.imageUri == "NULL") {
                                onNavigateCaptureImage()
                            } else {
                                viewModel.updateImageUriNull(incident.id)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Icon(
                            imageVector =
                                if (incident.imageUri == "NULL") {
                                    Icons.Default.Add
                                } else {
                                    Icons.Default.Delete
                                },
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp),
                        )
                        Text(
                            if (incident.imageUri == "NULL") "Add Image" else "Delete Image",
                        )
                    }

                    Button(
                        enabled = buttonEnabled,
                        onClick = {
                            if ((buttonEnabled && contentValue.isNotBlank()) ||
                                (incident.imageUri != "NULL")
                            ) {
                                viewModel.updateIncidentContent(
                                    id = id,
                                    content = contentValue,
                                )
                                onNavigateHome()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text("Save Changes")
                    }
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TextFieldEdit(
    content: String,
    changeContentValue: (String) -> Unit,
) {
    OutlinedTextField(
        value = content,
        onValueChange = { changeContentValue(it) },
        label = {
            Text("Incident description")
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                autoCorrectEnabled = true,
            ),
        minLines = 5,
        maxLines = 8,
        modifier =
            Modifier
                .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
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
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
            loading = placeholder(R.drawable.outline_cancel_24),
            failure = placeholder(R.drawable.outline_cancel_24),
            contentScale = ContentScale.Crop,
        )
    } else {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                "No Image",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
