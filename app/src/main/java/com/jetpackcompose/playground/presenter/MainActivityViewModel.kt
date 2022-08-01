package com.jetpackcompose.playground.presenter

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpackcompose.playground.core.data.local.db.Student
import com.jetpackcompose.playground.core.domain.repository.DataState
import com.jetpackcompose.playground.core.interactors.AddStudent
import com.jetpackcompose.playground.core.interactors.DeleteStudent
import com.jetpackcompose.playground.core.interactors.GetStudents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject constructor(
    private val addStudent: AddStudent,
    private val getStudents: GetStudents,
    private val delStudent: DeleteStudent
) : ViewModel() {

    private val _isSplashScreen = MutableStateFlow(true)
    val isSplashScreen = _isSplashScreen

    private var _listStudents = SnapshotStateList<Student>()
    val listStudent = _listStudents



    init {
        viewModelScope.launch {
            delay(2000)
            _isSplashScreen.value = false
        }

        getStudents().onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    _listStudents.clear()
                    _listStudents.addAll(dataState.data)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun deleteStudent(student: Student){
        delStudent(student).onEach {
            when (it) {
                is DataState.Success -> {
                    _listStudents.clear()
                    _listStudents.addAll(it.data)

                }
                is DataState.Loading -> {
                }
                is DataState.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addStudent(name: String, lastName: String) {

        addStudent(Student(name = name, lastName = lastName)).onEach { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    _listStudents.clear()
                    _listStudents.addAll(dataState.data)

                }
                is DataState.Loading -> {
                }
                is DataState.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }
}