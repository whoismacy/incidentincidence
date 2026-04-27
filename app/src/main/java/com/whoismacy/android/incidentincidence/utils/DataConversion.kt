package com.whoismacy.android.incidentincidence.utils

import androidx.annotation.RequiresApi
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.viewmodel.FilterPeriodValues
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.util.Date

@RequiresApi(0)
fun dateToHumanReadable(date: Date): String {
    val localDate =
        date
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    val formatter =
        DateTimeFormatter
            .ofLocalizedDate(FormatStyle.FULL)
    return localDate.format(formatter)
}

fun filterAccordingToDate(
    incident: Incident,
    date: FilterPeriodValues,
): Boolean {
    val dateAdded =
        incident.dateAdded
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    val now = LocalDateTime.now()
    var difference: Long
    when (date.value) {
        "Today" -> {
            difference =
                ChronoUnit
                    .DAYS
                    .between(dateAdded, now)
            return difference.toInt() == 0
        }

        "Past Week" -> {
            difference =
                ChronoUnit.WEEKS.between(
                    dateAdded,
                    now,
                )
            return difference < 1
        }

        "Past Month" -> {
            difference =
                ChronoUnit
                    .MONTHS
                    .between(dateAdded, now)
            return difference < 1
        }

        "Past Year" -> {
            difference =
                ChronoUnit
                    .YEARS
                    .between(dateAdded, now)
            return difference < 1
        }

        else -> {
            return false
        }
    }
}
