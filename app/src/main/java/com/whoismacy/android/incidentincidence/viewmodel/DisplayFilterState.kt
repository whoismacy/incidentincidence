package com.whoismacy.android.incidentincidence.viewmodel

data class DisplayFilterState(
    val searchQuery: String = "",
    val sortValue: SortValues = SortValues.NEWEST,
    val filterSevere: FilterSevereValues = FilterSevereValues.LOW,
    val filtersPeriod: FilterPeriodValues = FilterPeriodValues.TODAY,
    val filtersEnabled: Boolean = false,
)
