package com.example.todomobileapp.roomdatabase

import androidx.lifecycle.LiveData

class Repository(private val taskDatabase: TaskDatabase) {

    fun getAllTask(): LiveData<List<Task>> = taskDatabase.getDao().getAllTasks()

    suspend fun Insert(task: Task) {
        return taskDatabase.getDao().Insert(task)
    }

    suspend fun Update(task: Task) {
        taskDatabase.getDao().Update(task)
    }

    suspend fun Delete(task: Task) {
        taskDatabase.getDao().Delete(task)
    }
}