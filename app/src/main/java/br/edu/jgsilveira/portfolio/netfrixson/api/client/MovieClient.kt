package br.edu.jgsilveira.portfolio.netfrixson.api.client

import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.api.service.MovieService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieClient {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    suspend fun detail(movieId: Int) : Movie? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(MovieService::class.java).detail(movieId).execute()
            response.body()
        }
    }

}