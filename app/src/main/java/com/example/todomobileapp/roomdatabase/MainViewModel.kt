package com.example.todomobileapp.roomdatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    val allNotes: LiveData<List<Task>> = repository.getAllTask()

    suspend fun Insert(task: Task) {
        viewModelScope.launch {
            repository.Insert(task)
        }
    }

    suspend fun Update(task: Task) {
        viewModelScope.launch {
            repository.Update(task)
        }
    }

    suspend fun Delete(task: Task) {
        repository.Delete(task)
    }

   fun updateTaskCompletion(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.Update(task.copy(isCompleted = isCompleted))
        }
    }


}