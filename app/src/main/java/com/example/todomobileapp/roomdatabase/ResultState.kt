package com.example.todomobileapp.roomdatabase


import java.lang.Error

sealed class ResultState<out T> {
    object Loading : ResultState<Task>()
    data class Succses<T>(val succses: T) : ResultState<T>()
    data class Error(val error: Throwable) : ResultState<Task>()
}