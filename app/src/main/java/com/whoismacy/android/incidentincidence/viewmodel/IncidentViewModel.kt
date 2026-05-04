package com.whoismacy.android.incidentincidence.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCapture.FLASH_MODE_AUTO
import androidx.camera.core.ImageCapture.OUTPUT_FORMAT_JPEG
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.repository.IncidentRepository
import com.whoismacy.android.incidentincidence.repository.MediaStoreUtil
import com.whoismacy.android.incidentincidence.utils.filterAccordingToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class SnackbarEvent(
    val message: String,
    val actionLabel: String? = null,
    val action: suspend () -> Unit = {},
)

@HiltViewModel
class IncidentViewModel
    @Inject
    constructor(
        private val repository: IncidentRepository,
    ) : ViewModel() {
        private val _snackbarEvents = Channel<SnackbarEvent>()
        val snackbarEvents = _snackbarEvents.receiveAsFlow()

        private val _displayFilterState = MutableStateFlow(DisplayFilterState())
        val displayFilterState = _displayFilterState.asStateFlow()

        private val _trendsObject = MutableStateFlow(TrendScreenObject())
        val trendsObject = _trendsObject.asStateFlow()

        private val _isLoading = MutableStateFlow(true)
        val isLoading = _isLoading.asStateFlow()

        private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
        val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest.asStateFlow()

        private val previewUseCase =
            Preview
                .Builder()
                .build()
                .apply {
                    setSurfaceProvider { newSurfaceRequest ->
                        _surfaceRequest.update { newSurfaceRequest }
                    }
                }

        private val imageCaptureUseCase =
            ImageCapture
                .Builder()
                .apply {
                    setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                    setFlashMode(FLASH_MODE_AUTO)
                    setOutputFormat(OUTPUT_FORMAT_JPEG)
                }.build()

        init {
            viewModelScope.launch {
                repository.allIncidences.collect { incidents ->
                    val totalCount = incidents.count()
                    val resolvedCount = incidents.count { it.resolved }
                    val lowCount = incidents.count { it.severity == "low" }
                    val mediumCount = incidents.count { it.severity == "medium" }
                    val highCount = incidents.count { it.severity == "high" }
                    val severeCount = incidents.count { it.severity == "severe" }

                    _trendsObject.value =
                        TrendScreenObject(
                            totalIncidents = totalCount,
                            totalShares = repository.totalShares,
                            totalResolved = resolvedCount,
                            severityCount =
                                SeverityCount(
                                    low = lowCount,
                                    medium = mediumCount,
                                    high = highCount,
                                    severe = severeCount,
                                ),
                        )
                    _isLoading.value = false
                }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        val displayIncidences =
            displayFilterState
                .flatMapLatest { state ->
                    if (state.filtersEnabled) {
                        repository.allIncidences.map { incidents ->
                            incidents
                                .filter { incident ->
                                    incident
                                        .severity
                                        .equals(state.filterSevere.value, ignoreCase = true)
                                }.filter { incident ->
                                    filterAccordingToDate(incident, state.filtersPeriod)
                                }.let {
                                    applySorting(it, state.sortValue)
                                }
                        }
                    } else {
                        if (state.searchQuery.isBlank()) {
                            repository.allIncidences
                        } else {
                            repository.searchIncident(state.searchQuery)
                        }
                    }
                }.stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    emptyList(),
                )

        private fun applySorting(
            incidents: List<Incident>,
            sortValue: SortValues,
        ): List<Incident> =
            when (sortValue) {
                SortValues.NEWEST -> incidents.sortedBy { it.dateAdded }.reversed()
                SortValues.OLDEST -> incidents.sortedBy { it.dateAdded }
            }

        suspend fun bindToCamera(
            appContext: Context,
            lifecycleOwner: LifecycleOwner,
        ) {
            val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
            processCameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                previewUseCase,
                imageCaptureUseCase,
            )

            try {
                awaitCancellation()
            } finally {
                processCameraProvider.unbindAll()
            }
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        fun capturePicture(context: Context) {
            val tempFile = File.createTempFile("incident_", ".jpg", context.cacheDir)
            val mediaStoreUtil = MediaStoreUtil(context)
            val outputFileOptions =
                ImageCapture
                    .OutputFileOptions
                    .Builder(tempFile)
                    .build()
            val executor = ContextCompat.getMainExecutor(context)

            imageCaptureUseCase.takePicture(
                outputFileOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(p0: ImageCapture.OutputFileResults) {
                        viewModelScope.launch {
                            try {
                                val bitMap =
                                    android
                                        .graphics.BitmapFactory
                                        .decodeFile(tempFile.absolutePath)
                                if (bitMap == null) {
                                    _snackbarEvents.send(SnackbarEvent("Error saving Image"))
                                    return@launch
                                }
                                mediaStoreUtil.saveImage(bitMap)
                                when (tempFile.delete()) {
                                    true -> {
                                        _snackbarEvents.send(
                                            SnackbarEvent("Image successfully captured!!"),
                                        )
                                    }

                                    false -> {
                                        _snackbarEvents.send(SnackbarEvent("Error saving image: Please try again"))
                                    }
                                }
                            } catch (e: Exception) {
                                _snackbarEvents.send(SnackbarEvent("Error saving image: ${e.message}"))
                                tempFile.delete()
                            }
                        }
                    }

                    override fun onError(p0: ImageCaptureException) {
                        viewModelScope.launch {
                            _snackbarEvents.send(
                                SnackbarEvent("Image Capture failed"),
                            )
                        }
                    }
                },
            )
        }

        fun add(
            content: String,
            severity: String,
        ) {
            viewModelScope.launch {
                try {
                    repository.add(content, severity)
                    _snackbarEvents.send(
                        SnackbarEvent(
                            message = "Incident added successfully",
                            action = {},
                        ),
                    )
                } catch (e: Exception) {
                    _snackbarEvents.send(
                        SnackbarEvent(
                            message = "Error: ${e.message}",
                        ),
                    )
                }
            }
        }

        fun incrementShare(id: Int) {
            repository.incrementShare(id)
        }

        fun resolveIncident(id: Int) {
            repository.resolveIncident(id)
            resolveDate()
        }

        fun resolveDate() {
            repository.resolveDate()
        }

        fun deleteIncident(id: Int) {
            viewModelScope.launch {
                try {
                    repository.deleteIncident(id)
                    _snackbarEvents.send(
                        SnackbarEvent(
                            message = "Incident deleted",
                            action = {},
                        ),
                    )
                } catch (e: Exception) {
                    _snackbarEvents.send(
                        SnackbarEvent("Error: ${e.message}"),
                    )
                }
            }
        }

        fun updateSearchQuery(query: String) {
            _displayFilterState.update {
                it.copy(searchQuery = query)
            }
        }

        fun updateSortValue(value: SortValues) {
            _displayFilterState.update {
                it.copy(sortValue = value)
            }
        }

        fun updateFilterSevereValue(value: FilterSevereValues) {
            _displayFilterState.update {
                it.copy(
                    filterSevere = value,
                )
            }
        }

        fun updateCurrentFilterPeriodValue(value: FilterPeriodValues) {
            _displayFilterState.update {
                it.copy(
                    filtersPeriod = value,
                )
            }
        }

        fun toggleFilterEnabled(value: Boolean) {
            _displayFilterState.update {
                it.copy(
                    filtersEnabled = value,
                )
            }
        }

        fun toggleFilters() {
            _displayFilterState.update {
                it.copy(
                    searchQuery = "",
                    filtersPeriod = FilterPeriodValues.TODAY,
                    filterSevere = FilterSevereValues.LOW,
                    sortValue = SortValues.NEWEST,
                    filtersEnabled = false,
                )
            }
        }
    }
