package com.algar.repository.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.coroutineContext

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * You can read more about it in the [Architecture Guide](https://developer.android.com/arch).
 *
 * Obs: It uses coroutines.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
abstract class NetworkBoundResource<ResultType, RequestType>(private val dispatcher: CoroutineDispatcher) {

    private val result = MutableLiveData<Resource<ResultType>>()

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        withContext(Dispatchers.Main) {
//            result.value = Resource.loading(null)
            setValue(newValue = Resource.loading(data = null))
        }
        CoroutineScope(coroutineContext).launch(dispatcher) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork(dbResult)
                } catch (e: Exception) {
                    Log.e("NetworkBoundResource", "An error happened: $e")
                    setValue(Resource.error(e, loadFromDb()))
                }
            } else {
                Log.d(NetworkBoundResource::class.java.name, "Return data from local database")
                setValue(Resource.success(dbResult))
            }
        }
        return this
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    private suspend fun fetchFromNetwork(dbResult: ResultType) {
        Log.d(NetworkBoundResource::class.java.name, "Fetch data from network")
        setValue(Resource.loading(dbResult)) // Dispatch latest value quickly (UX purpose)
        val apiResponse = createCall()
        Log.e(NetworkBoundResource::class.java.name, "Data fetched from network")
        saveCallResults(processResponse(apiResponse))
        setValue(Resource.success(loadFromDb()))
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        Log.d(NetworkBoundResource::class.java.name, "Resource: "+newValue)
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    @WorkerThread
    protected abstract fun processResponse(response: RequestType): ResultType

    @WorkerThread
    protected abstract suspend fun saveCallResults(items: ResultType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): ResultType

    @MainThread
    protected abstract suspend fun createCall(): RequestType
}