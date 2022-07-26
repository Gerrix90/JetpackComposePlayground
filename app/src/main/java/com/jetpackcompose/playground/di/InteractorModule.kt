package com.jetpackcompose.playground.di

import android.content.Context
import com.jetpackcompose.playground.core.interactors.AddStudent
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
    fun getAdStudent(@ApplicationContext appContext: Context) = AddStudent(appContext)

    @Singleton
    @Provides
    fun getStudents(@ApplicationContext appContext: Context) = GetStudents(appContext)
}