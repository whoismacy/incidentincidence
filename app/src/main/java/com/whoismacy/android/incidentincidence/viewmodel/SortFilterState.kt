package com.whoismacy.android.incidentincidence.viewmodel

import com.whoismacy.android.incidentincidence.model.Incident

interface SortFilter {
    val value: String
}

enum class SortValues(
    override val value: String,
) : SortFilter {
    NEWEST("Newest first"),
    OLDEST("Oldest first"),
}

enum class FilterSevereValues(
    override val value: String,
) : SortFilter {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    SEVERE("Severe"),
}

enum class FilterPeriodValues(
    override val value: String,
) : SortFilter {
    TODAY("Today"),
    PWEEK("Past Week"),
    PMONTH("Past Month"),
    PYEAR("Past Year"),
}

fun applySorting(
    incidents: List<Incident>,
    sortValue: SortValues,
): List<Incident> =
    when (sortValue) {
        SortValues.NEWEST -> incidents.sortedBy { it.dateAdded }.reversed()
        SortValues.OLDEST -> incidents.sortedBy { it.dateAdded }
    }
