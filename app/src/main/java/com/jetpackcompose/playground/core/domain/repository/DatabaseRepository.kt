package com.jetpackcompose.playground.core.domain.repository

import android.content.Context
import com.jetpackcompose.playground.core.data.local.db.StudentDatabase

class DatabaseRepository(var context : Context) {

    fun getStudentDao() =
        StudentDatabase.getDatabse(context).studentDao()
}