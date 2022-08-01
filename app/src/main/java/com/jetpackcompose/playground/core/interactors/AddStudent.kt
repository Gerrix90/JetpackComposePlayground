package com.jetpackcompose.playground.core.interactors

import android.content.Context
import com.jetpackcompose.playground.core.data.local.db.Student
import com.jetpackcompose.playground.core.domain.repository.DataState
import com.jetpackcompose.playground.core.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddStudent(private val databaseRepository: DatabaseRepository) {
//    private var dao = DatabaseRepository(context).getStudentDao()

    operator fun invoke(student: Student): Flow<DataState<List<Student>>> = flow {

        emit(DataState.Loading)
//        dao.insertStudent(student)
        databaseRepository.insertStudent(student)
        emit(DataState.Success(databaseRepository.getStudents()))
    }
}