package com.whoismacy.android.incidentincidence.viewmodel

import androidx.lifecycle.ViewModel
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.model.IncidentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class IncidentViewModel
    @Inject
    constructor(
        private val repository: IncidentRepository,
    ) : ViewModel() {
        val solvedCrimes: Flow<List<Incident>> = repository.solvedCrimes
        val unSolvedCrimes: Flow<List<Incident>> = repository.unsolvedCrimes

        fun add(
            content: String,
            severity: String,
        ) {
            repository.add(content, severity)
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
            repository.deleteIncident(id)
        }
    }
