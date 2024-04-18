package com.example.weatherapp

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KFunction1

class BaseExceptionHandler(
    override val key: CoroutineContext.Key<*>,
    private val errorAction: KFunction1<String, Unit>,
    private val throwableTag: String,
) : CoroutineExceptionHandler {
    companion object {
        const val NETWORK_ERROR = "Network error"
        const val ERROR = "Error"
    }

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        when(exception) {
            is HttpException -> {
                errorAction(NETWORK_ERROR)
            }
            else -> {
                errorAction(ERROR)
            }

        }
        Log.e(throwableTag, "Exception: $exception")
    }
}