package com.jetpackcompose.playground.core.interactors

import android.content.Context
import com.jetpackcompose.playground.core.data.local.db.Student
import com.jetpackcompose.playground.core.domain.repository.DataState
import com.jetpackcompose.playground.core.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddStudent(context : Context) {
    private var dao = DatabaseRepository(context).getStudentDao()

    operator fun invoke(student: Student): Flow<DataState<List<Student>>> = flow {

        emit(DataState.Loading)
        dao.insertStudent(student)
        emit(DataState.Success(dao.getAllStudents()))
    }
}