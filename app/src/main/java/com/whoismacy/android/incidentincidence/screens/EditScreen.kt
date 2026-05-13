package com.whoismacy.android.incidentincidence.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.whoismacy.android.incidentincidence.R
import com.whoismacy.android.incidentincidence.routes.LocalIncidentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    id: Int,
    onNavigateCaptureImage: () -> Unit,
    onNavigateHome: () -> Unit,
) {
    val viewModel = LocalIncidentViewModel.current
    val incidents = viewModel.displayIncidences.collectAsStateWithLifecycle().value
    val incident = incidents.find { it.id == id } ?: return

    val initialImageUri = remember { incident.imageUri }
    val initialContent = remember { incident.content }
    var contentValue by remember { mutableStateOf(incident.content) }

    val isContentChanged = contentValue != initialContent
    val isImageChanged = initialImageUri != incident.imageUri
    val canSave = isImageChanged || isContentChanged

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Edit Incident",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateHome) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                shape = CircleShape,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "ID: #$id",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    style =
                        MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                        ),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Card(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    ImageCompose(incident.imageUri)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "DESCRIPTION",
                    style =
                        MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.2.sp,
                        ),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                )

                OutlinedTextField(
                    value = contentValue,
                    onValueChange = { contentValue = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        ),
                    minLines = 5,
                    maxLines = 10,
                    placeholder = {
                        Text(
                            "Provide a detailed description of the incident...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        )
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            autoCorrectEnabled = true,
                        ),
                )
            }

            Spacer(Modifier.height(16.dp))

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                val hasImage = incident.imageUri != "NULL"

                FilledTonalButton(
                    onClick = {
                        if (!hasImage) onNavigateCaptureImage() else viewModel.updateImageUriNull(incident.id)
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors =
                        ButtonDefaults.filledTonalButtonColors(
                            containerColor =
                                if (!hasImage) {
                                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
                                } else {
                                    MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f)
                                },
                            contentColor =
                                if (!hasImage) {
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onErrorContainer
                                },
                        ),
                ) {
                    Icon(
                        imageVector = if (!hasImage) Icons.Default.Add else Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (!hasImage) "Add Photo" else "Remove Photo",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    )
                }

                Button(
                    enabled = canSave,
                    onClick = {
                        viewModel.updateIncidentContent(id = id, content = contentValue)
                        onNavigateHome()
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                ) {
                    Text(
                        "Save Changes",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageCompose(
    uri: String,
) {
    if (uri != "NULL") {
        GlideImage(
            model = uri.toUri(),
            contentDescription = "Incident Attachment",
            modifier = Modifier.fillMaxSize(),
            loading = placeholder(R.drawable.outline_cancel_24),
            failure = placeholder(R.drawable.outline_cancel_24),
            contentScale = ContentScale.Crop,
        )
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "No photo attached",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            )
        }
    }
}
