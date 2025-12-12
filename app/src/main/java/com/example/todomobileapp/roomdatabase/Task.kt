package com.example.todomobileapp.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "notes")
    val notes: String? = null,

    @ColumnInfo(name = "category")
    val category: Int = 0,

    @ColumnInfo(name = "dateMillis")
    val dateMillis: Long? = null,

    @ColumnInfo(name = "timeMillis")
    val timeMillis: Long? = null,

    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean = false

)