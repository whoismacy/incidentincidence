package com.whoismacy.android.incidentincidence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "incidences")
class Incident {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "incidentId")
    var id: Int = 0

    var content: String = ""

    @ColumnInfo(name = "date_added")
    var dateAdded: Date = Date()

    @ColumnInfo(name = "incidence_resolved")
    var resolved: Boolean = false

    @ColumnInfo(name = "total_shares")
    var shared: Int = 0

    @ColumnInfo(name = "date_resolved", defaultValue = "")
    var dateResolved: Date? = Date()
}
