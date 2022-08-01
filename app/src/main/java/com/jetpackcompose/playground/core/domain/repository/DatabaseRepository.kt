package com.jetpackcompose.playground.core.domain.repository

import android.content.Context
import com.jetpackcompose.playground.core.data.local.db.Student
import com.jetpackcompose.playground.core.data.local.db.StudentDao
import com.jetpackcompose.playground.core.data.local.db.StudentDatabase

class DatabaseRepository(var studentDao: StudentDao) {


    suspend fun getStudents() = studentDao.getAllStudents()
    suspend fun insertStudent(student : Student) = studentDao.insertStudent(student)
    suspend fun deleteStudent(student: Student) = studentDao.deleteStudent(student)
}