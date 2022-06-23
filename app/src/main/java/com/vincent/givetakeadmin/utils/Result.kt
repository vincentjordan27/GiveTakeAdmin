package com.vincent.givetakeadmin.utils

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data:T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}