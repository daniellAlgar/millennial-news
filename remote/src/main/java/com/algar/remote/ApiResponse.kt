package com.algar.remote

sealed class ApiResponse<out T> {
    data class Success<out T>(val body: T): ApiResponse<T>()
    data class Error(val code: Int? = null, val message: String? = null): ApiResponse<Nothing>()
    object NetworkError: ApiResponse<Nothing>()
}