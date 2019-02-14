package br.edu.jgsilveira.portfolio.netfrixson.api.endpoint

import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseEndPoint {

    protected val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    protected fun <T> async(call: Call<T>): Deferred<Response<T>> {
        val deferred = CompletableDeferred<Response<T>>()
        deferred.invokeOnCompletion {
            if (deferred.isCancelled)
                call.cancel()
        }
        call.enqueue(object: Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                deferred.completeExceptionally(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                deferred.complete(response)
            }
        })
        return deferred
    }

}