package com.whoismacy.android.incidentincidence.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class SeverityCount(
    val low: Int = 0,
    val medium: Int = 0,
    val high: Int = 0,
    val severe: Int = 0,
)

data class TrendScreenObject(
    val totalIncidents: Int = 0,
    val totalShares: Flow<Int> = flow { emit(0) },
    val totalResolved: Int = 0,
    val severityCount: SeverityCount = SeverityCount(),
)
