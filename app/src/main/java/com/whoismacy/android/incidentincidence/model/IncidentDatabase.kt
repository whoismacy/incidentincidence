package com.whoismacy.android.incidentincidence.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Incident::class],
    exportSchema = false,
    version = 1,
)
@TypeConverters(IncidentConverters::class)
abstract class IncidentDatabase : RoomDatabase() {
    abstract fun incidentsDao(): IncidentDao

    companion object {
        private var INSTANCE: IncidentDatabase? = null

        fun getInstance(context: Context): IncidentDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance =
                        Room
                            .databaseBuilder(
                                context = context.applicationContext,
                                klass = IncidentDatabase::class.java,
                                name = "incidentDatabase",
                            ).fallbackToDestructiveMigration(false)
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
