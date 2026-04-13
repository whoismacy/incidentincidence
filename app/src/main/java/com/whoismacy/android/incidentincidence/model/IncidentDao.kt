package com.whoismacy.android.incidentincidence.model

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface IncidentDao {
    @Query("INSERT INTO `incidences` (content, date_added, incidence_resolved, date_resolved, total_shares, severity ) VALUES(:content, :date, :resolved, :dateResolved, :totalShares, :severity);")
    suspend fun addCrime(
        content: String,
        severity: String = "low",
        date: Date = Date(),
        resolved: Boolean = false,
        dateResolved: Date? = null,
        totalShares: Int = 0,
    )

    @Query("SELECT * FROM `incidences` WHERE `incidence_resolved` = 1;")
    fun getAllSolvedCrimes(): Flow<List<Incident>>

    @Query("SELECT * FROM `incidences` WHERE `incidence_resolved` = 0;")
    fun getAllUnsolvedCrimes(): Flow<List<Incident>>

    @Query("UPDATE `incidences` SET `total_shares` = `total_shares` + 1 WHERE incidentId = :id;")
    suspend fun incrementShare(id: Int)

    @Query("UPDATE `incidences` SET `incidence_resolved` = 1 WHERE incidentId = :id;")
    suspend fun resolveIncident(id: Int)

    @Query("UPDATE `incidences` SET `date_resolved` = :date;")
    suspend fun resolveDate(date: Date = Date())

    @Query("DELETE FROM `incidences` WHERE `incidentId` = :id")
    suspend fun deleteIncident(id: Int)
}
