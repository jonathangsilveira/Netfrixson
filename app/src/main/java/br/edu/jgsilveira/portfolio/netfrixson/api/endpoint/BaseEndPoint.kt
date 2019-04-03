package br.edu.jgsilveira.portfolio.netfrixson.api.endpoint

import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import br.edu.jgsilveira.portfolio.netfrixson.common.Result
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseEndPoint {

    protected val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    protected inline fun <reified T> Retrofit.service() = create(T::class.java)

    protected fun <T> safeApiCall(call: () -> Call<T>): Result<T> {
        return try {
            val response = call().execute()
            if (response.isSuccessful)
                Result.Success(response.body())
            else
                Result.Failure.Response(response.code(), response.message())
        } catch (e: Exception) {
            Result.Failure.Undefined(e)
        }
    }

}