package com.whoismacy.android.incidentincidence.utils

import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
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
            .ofLocalizedDate(FormatStyle.MEDIUM)
    return localDate.format(formatter)
}
