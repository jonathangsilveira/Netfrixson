package br.edu.jgsilveira.portfolio.netfrixson.api.client

import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import br.edu.jgsilveira.portfolio.netfrixson.api.RetrofitResponseException
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies
import br.edu.jgsilveira.portfolio.netfrixson.api.service.MovieService
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiscoverClient {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    suspend fun movies(queries: Map<String, String> = mapOf()): DiscoverMovies? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(MovieService::class.java)
                    .discover(queries)
                    .execute()
            if (response.isSuccessful)
                response.body()
            else
                throw RetrofitResponseException(response.code(), response.message())
        }
    }

}