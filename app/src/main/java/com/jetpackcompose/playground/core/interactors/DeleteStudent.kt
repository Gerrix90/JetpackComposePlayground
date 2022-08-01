package com.jetpackcompose.playground.core.interactors

import com.jetpackcompose.playground.core.data.local.db.Student
import com.jetpackcompose.playground.core.domain.repository.DataState
import com.jetpackcompose.playground.core.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteStudent(private val databaseRepository: DatabaseRepository) {

    operator fun invoke(student: Student) : Flow<DataState<List<Student>>> = flow {
        emit(DataState.Loading)
        databaseRepository.deleteStudent(student)
        emit(DataState.Success(databaseRepository.getStudents()))
    }
}