package com.algar.remote.model

/**
 * Helper model to represent an api response. Typically used with safeApiCall().
 */
sealed class ApiResponse<out T> {
    data class Success<out T>(val body: T): ApiResponse<T>()
    data class Error(
        val error: Throwable,
        val code: Int? = null,
        val message: String? = null
    ): ApiResponse<Nothing>()
}