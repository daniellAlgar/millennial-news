package com.algar.remote.helpers

import com.algar.remote.model.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * This method handles the success/failure of a network call. Given a [dispatcher] the [apiCall] is
 * invoked.
 */
suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ApiResponse<T> {
    return withContext(dispatcher) {
        try {
            ApiResponse.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            parseError(exception = throwable)
        }
    }
}

private fun parseError(exception: Throwable): ApiResponse<Nothing> = when (exception) {
    is HttpException -> {
        val code = exception.code()
        val message = exception.response()?.errorBody()?.string()
        ApiResponse.Error(error = exception, code = code, message = message)
    }
    else -> {
        ApiResponse.Error(error = exception, code = null, message = null)
    }
}