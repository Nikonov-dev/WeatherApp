package com.example.weatherapp.weather

import android.util.Log
import com.example.weatherapp.BaseExceptionHandler
import com.example.weatherapp.BaseExceptionHandler.Companion.ERROR
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KFunction1
import kotlin.reflect.KSuspendFunction2

class WeatherExceptionHandler(
    override val key: CoroutineContext.Key<*>,
    private val retryAction: KSuspendFunction2<Double, Double, Unit>,
    private val errorAction: KFunction1<String, Unit>,
    private val throwableTag: String,
) : CoroutineExceptionHandler {
    companion object {
        private const val DELAY_BEFORE_SECOND_TRY = 1000L
    }

    private var isRelaunched = false
    private val baseExceptionHandler by lazy {
        BaseExceptionHandler(
            key,
            errorAction,
            throwableTag
        )
    }

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        when (exception) {
            is HttpException -> {
                val currentScope = context[key] as? CoroutineScope ?: return
                currentScope.launch(baseExceptionHandler) {
                    delay(DELAY_BEFORE_SECOND_TRY)
                    retryAction
                }
                isRelaunched = true
            }
            else -> {
                errorAction(ERROR)
            }
        }
        Log.e(throwableTag, "Exception: $exception")
    }
}