package com.whoismacy.android.incidentincidence.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Incident::class],
    exportSchema = false,
    version = 2,
)
@TypeConverters(IncidentConverters::class)
abstract class IncidentDatabase : RoomDatabase() {
    abstract fun incidentsDao(): IncidentDao

    companion object {
        private val Migration_1_2 =
            object : Migration(1, 2) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE `incidences` ADD COLUMN `severity` TEXT NOT NULL DEFAULT 'low'")
                }
            }
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
                            ).addMigrations(Migration_1_2)
                            .fallbackToDestructiveMigration(false)
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
