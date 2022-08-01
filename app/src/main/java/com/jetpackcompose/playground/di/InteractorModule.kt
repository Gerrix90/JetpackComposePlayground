package com.jetpackcompose.playground.di

import android.content.Context
import com.jetpackcompose.playground.core.domain.repository.DatabaseRepository
import com.jetpackcompose.playground.core.interactors.AddStudent
import com.jetpackcompose.playground.core.interactors.DeleteStudent
import com.jetpackcompose.playground.core.interactors.GetStudents
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {

    @Singleton
    @Provides
    fun getAdStudent(databaseRepository: DatabaseRepository) = AddStudent(databaseRepository)

    @Singleton
    @Provides
    fun getStudents(databaseRepository: DatabaseRepository) = GetStudents(databaseRepository)

    @Singleton
    @Provides
    fun getDeleteStudent(databaseRepository: DatabaseRepository) = DeleteStudent(databaseRepository)
}