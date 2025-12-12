package com.example.todomobileapp.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(task: Task)

    @Query("SELECT * FROM Task WHERE isCompleted = 0 ORDER BY id DESC")
    fun getActiveTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE isCompleted = 1 ORDER BY id DESC")
    fun getCompletedTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task ORDER BY id DESC")
    fun getAllTasks(): LiveData<List<Task>>


    @Update
    suspend fun Update(task: Task)

    @Delete
    suspend fun Delete(task: Task)

}