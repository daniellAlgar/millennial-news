package com.algar.repository.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.algar.remote.model.ApiResponse
import com.algar.remote.model.ApiResponse.Error
import com.algar.remote.model.ApiResponse.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

/**
 * A generic class that can provide a resource backed by both the SQLite database and the network.
 * You can read more about it in the [Architecture Guide](https://developer.android.com/arch).
 *
 * Note 1: There is an optional function [onFetchFailed] that you can override.
 * Note 2: This class expects a network call to return an [ApiResponse].
 * Note 3: This class uses coroutines.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
abstract class NetworkBoundResource<ResultType, RequestType>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val result = MutableLiveData<Resource<ResultType>>()

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        withContext(Dispatchers.Main) {
            setValue(newValue = Resource.loading(data = null))
        }
        CoroutineScope(coroutineContext).launch(dispatcher) {
            val dbSource = loadFromDb()
            if (shouldFetch(dbSource)) {
                fetchFromNetwork(dbSource)
            } else {
                setValue(Resource.success(dbSource))
            }
        }
        return this
    }

    private suspend fun fetchFromNetwork(dbSource: ResultType) {
        setValue(Resource.loading(dbSource)) // Dispatch latest value quickly (UX purpose)

        when (val apiResponse = createCall()) {
            is Success -> {
                saveCallResults(data = processResponse(response = apiResponse))
                setValue(Resource.success(data = loadFromDb()))
            }
            is Error -> {
                setValue(Resource.error(apiResponse.error, dbSource))
                onFetchFailed()
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    /**
     * What to do when a network call fails (i.e. throws an error).
     */
    protected open fun onFetchFailed() {}

    @WorkerThread
    protected fun processResponse(response: Success<RequestType>): RequestType = response.body

    @WorkerThread
    protected abstract suspend fun saveCallResults(data: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType

    @MainThread
    protected abstract suspend fun createCall(): ApiResponse<RequestType>
}