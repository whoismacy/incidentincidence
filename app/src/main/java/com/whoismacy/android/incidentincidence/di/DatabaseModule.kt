package com.whoismacy.android.incidentincidence.di

import android.content.Context
import com.whoismacy.android.incidentincidence.model.IncidentDao
import com.whoismacy.android.incidentincidence.model.IncidentDatabase
import com.whoismacy.android.incidentincidence.model.IncidentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): IncidentDatabase = IncidentDatabase.getInstance(context)

    @Provides
    fun provideIncidentDao(database: IncidentDatabase): IncidentDao = database.incidentsDao()

    @Provides
    @Singleton
    fun provideIncidentRepository(incidentDao: IncidentDao): IncidentRepository = IncidentRepository(incidentDao)
}
