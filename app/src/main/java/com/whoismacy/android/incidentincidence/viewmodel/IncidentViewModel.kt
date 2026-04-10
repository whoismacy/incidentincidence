package com.whoismacy.android.incidentincidence.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.model.IncidentDatabase
import com.whoismacy.android.incidentincidence.model.IncidentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class IncidentViewModel(
    application: Application,
) : ViewModel() {
    private val repository: IncidentRepository
    var solvedCrimes: Flow<List<Incident>>
    val unSolvedCrimes: Flow<List<Incident>>

    init {
        val incidentDatabase = IncidentDatabase.getInstance(application)
        val incidentDao = incidentDatabase.incidentsDao()
        repository = IncidentRepository(incidentDao)
        solvedCrimes = repository.solvedCrimes
        unSolvedCrimes = repository.unsolvedCrimes
    }

    fun add(content: String) {
        repository.add(content)
    }

    fun incrementShare(id: Int) {
        repository.incrementShare(id)
    }

    fun resolveIncident(id: Int) {
        repository.resolveIncident(id)
    }

    fun resolveDate() {
        repository.resolveDate()
    }
}
