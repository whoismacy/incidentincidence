package com.whoismacy.android.incidentincidence.model

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class IncidentRepository(
    private val incidentsDao: IncidentDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val allIncidences: Flow<List<Incident>> = incidentsDao.getAllIncidences()

    fun add(
        content: String,
        severity: String,
    ) {
        coroutineScope.launch(dispatcher) {
            incidentsDao.addCrime(content, severity)
        }
    }

    fun searchIncident(
        searchContent: String,
    ): Flow<List<Incident>> = incidentsDao.searchIncident(searchContent)

    fun incrementShare(id: Int) {
        coroutineScope.launch(dispatcher) {
            incidentsDao.incrementShare(id)
        }
    }

    fun resolveIncident(id: Int) {
        coroutineScope.launch(dispatcher) {
            incidentsDao.resolveIncident(id)
        }
    }

    fun resolveDate() {
        coroutineScope.launch(dispatcher) {
            incidentsDao.resolveDate()
        }
    }

    fun deleteIncident(id: Int) {
        coroutineScope.launch(dispatcher) {
            incidentsDao.deleteIncident(id)
        }
    }
}
