package com.whoismacy.android.incidentincidence.view

import com.whoismacy.android.incidentincidence.R

enum class Destinations(
    val icon: Int,
    val label: String,
    val contentDescription: String,
) {
    HOME(
        R.drawable.outline_free_breakfast_24,
        "Home",
        "An outline of a cup of tea",
    ),
    SOLVED(
        R.drawable.outline_star_shine_24,
        "Solved Crimes",
        "An outline of a shining star",
    ),
}
