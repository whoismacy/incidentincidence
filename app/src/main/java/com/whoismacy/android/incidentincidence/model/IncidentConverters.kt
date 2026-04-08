package com.whoismacy.android.incidentincidence.model

import androidx.room.TypeConverter
import java.util.Date

class IncidentConverters {
    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun fromLong(long: Long): Date = Date(long)
}
