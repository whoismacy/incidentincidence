package com.whoismacy.android.incidentincidence.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoismacy.android.incidentincidence.model.IncidentRepository
import com.whoismacy.android.incidentincidence.utils.filterAccordingToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

        private val _searchQuery = MutableStateFlow("")
        val searchQuery = _searchQuery.asStateFlow()

        private val _currentSortValue = MutableStateFlow(SortValues.NEWEST)
        private val _currentFilterSevereValue = MutableStateFlow(FilterSevereValues.LOW)
        private val _currentFilterPeriodValue = MutableStateFlow(FilterPeriodValues.TODAY)
        val currentSortValue = _currentSortValue.asStateFlow()
        val currentFilterSevereValue = _currentFilterSevereValue.asStateFlow()
        val currentFilterPeriodValue = _currentFilterPeriodValue.asStateFlow()
        var filtersPresent: Boolean = false

        @OptIn(ExperimentalCoroutinesApi::class)
        val displayIncidences =
            if (filtersPresent) {
                repository.allIncidences.map { incidents ->
                    incidents
                        .filter { incident ->
                            incident.severity == currentFilterSevereValue.value.value
                        }.filter { incident ->
                            filterAccordingToDate(incident, currentFilterPeriodValue.value)
                        }
                }
            } else {
                _searchQuery
                    .flatMapLatest { query ->
                        if (query.isBlank()) {
                            repository.allIncidences
                        } else {
                            repository.searchIncident(query)
                        }
                    }.stateIn(
                        viewModelScope,
                        SharingStarted.WhileSubscribed(5000),
                        emptyList(),
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
                            actionLabel = "Undo",
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
                            actionLabel = "Undo",
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

        fun changeFilter() {
            filtersPresent = !filtersPresent
        }

        fun updateSearchQuery(query: String) {
            _searchQuery.value = query
        }

        fun updateCurrentSortValue(value: SortValues) {
            _currentSortValue.value = value
        }

        fun updateCurrentFilterSevereValue(value: FilterSevereValues) {
            _currentFilterSevereValue.value = value
        }

        fun updateCurrentFilterPeriodValue(value: FilterPeriodValues) {
            _currentFilterPeriodValue.value = value
        }
    }
