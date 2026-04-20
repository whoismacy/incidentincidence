package com.whoismacy.android.incidentincidence.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.model.IncidentRepository
import com.whoismacy.android.incidentincidence.utils.filterAccordingToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

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

        @OptIn(ExperimentalCoroutinesApi::class)
        val displayIncidences =
            displayFilterState
                .flatMapLatest { state ->
                    if (state.filtersEnabled) {
                        repository.allIncidences.map { incidents ->
                            incidents
                                .filter { incident ->
                                    incident.severity == state.filterSevere.value
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
